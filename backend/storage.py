import json
import faiss

def save_index(index):
    faiss.write_index(index, "uploads/index.faiss")

def load_index():
    return faiss.read_index("uploads/index.faiss")


def save_chunks(chunks):
    with open("uploads/chunks.json", "w", encoding="utf-8") as f:
        json.dump(chunks, f, ensure_ascii=False, indent=4)


def load_chunks():
    with open("uploads/chunks.json", "r", encoding="utf-8") as f:
        return json.load(f)