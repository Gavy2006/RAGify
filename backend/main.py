from fastapi import FastAPI
from fastapi import FastAPI, File, UploadFile
from embedding import create_embeddings
from extract import text_return
from chunk import chunk_with_overlap

import os
from pydantic import BaseModel
from storage import load_index, load_chunks
from vectordb import search

app = FastAPI()

class QuestionRequest(BaseModel):
    question: str


# yaad decorator , root url "/" pr aaye to nichee wala func do 
@app.get("/")  
def read_root():
    return "hello world" 


@app.post("/upload")
async def upload_pdf(file : UploadFile = File()):


#validation valid pdf or not ---> Gavy
   result = validate(file)

   if not result :

        return {
        "error": "Only PDF allowed"
    }

# if valid give file name ---> Gavy
   filename = file.filename

# read file ---> Gavy

   content = await file.read()

   BASE_DIR = os.path.dirname(os.path.abspath(__file__))

   uploaded = os.path.join(BASE_DIR, "upload", filename)


   with open(uploaded ,"wb") as f:
    f.write(content) 

# send file to extract.py 
   text = text_return(uploaded)

   chunks = chunk_with_overlap(text)

   embeddings = create_embeddings(chunks)



   return {
    "total_chunks": len(chunks),
    "embedding_dimension": len(embeddings[0]) if embeddings is not None and len(embeddings) > 0 else 0
}

@app.post("/ask")
def ask(request: QuestionRequest):
    query_embedding = create_embeddings([request.question])

    index = load_index()

    distances, indices = search(index, query_embedding)
    

def validate(file :UploadFile) :
        if(file.content_type != "application/pdf" ) :
            return False

        else :
            return True

