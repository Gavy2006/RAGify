from fastapi import FastAPI, File, UploadFile
from pydantic import BaseModel
import os
from gemini import ask_gemini
from embedding import create_embeddings
from extract import text_return
from chunk import chunk_with_overlap
from vectordb import create_index, add_embeddings, search
from storage import (
    save_index,
    save_chunks,
    load_index,
    load_chunks
)
from gemini import ask_gemini

app = FastAPI()


class QuestionRequest(BaseModel):
    question: str


@app.get("/")
def read_root():
    return "hello world"


@app.post("/upload")
async def upload_pdf(file: UploadFile = File(...)):

    if not validate(file):
        return {"error": "Only PDF allowed"}

    BASE_DIR = os.path.dirname(os.path.abspath(__file__))
    UPLOAD_DIR = os.path.join(BASE_DIR, "uploads")

    os.makedirs(UPLOAD_DIR, exist_ok=True)

    file_path = os.path.join(UPLOAD_DIR, file.filename)

    content = await file.read()

    with open(file_path, "wb") as f:
        f.write(content)

    text = text_return(file_path)

    chunks = chunk_with_overlap(text)

    embeddings = create_embeddings(chunks)

    index = create_index(len(embeddings[0]))

    add_embeddings(index, embeddings)

    save_index(index)

    save_chunks(chunks)

    return {
        "message": "PDF uploaded successfully",
        "total_chunks": len(chunks),
        "embedding_dimension": len(embeddings[0])
    }


@app.post("/ask")
def ask(request: QuestionRequest):

    query_embedding = create_embeddings([request.question])

    index = load_index()

    distances, indices = search(index, query_embedding)

    chunks = load_chunks()

    relevant_chunks = []

    for i in indices[0]:
        relevant_chunks.append(chunks[i])

    context = "\n\n".join(relevant_chunks)

    answer = ask_gemini(context, request.question)

    return {
        "question": request.question,
        "answer": answer,
        "relevant_chunks": relevant_chunks
    }


def validate(file: UploadFile):
    return file.content_type == "application/pdf"