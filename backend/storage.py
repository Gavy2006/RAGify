import os
import json
import faiss

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
UPLOAD_DIR = os.path.join(BASE_DIR, "uploads")

os.makedirs(UPLOAD_DIR, exist_ok=True)


def save_index(index):
    path = os.path.join(UPLOAD_DIR, "index.faiss")
    faiss.write_index(index, path)


def load_index():
    path = os.path.join(UPLOAD_DIR, "index.faiss")

    if not os.path.exists(path):
        raise FileNotFoundError("index.faiss not found")

    return faiss.read_index(path)


def save_chunks(chunks):
    path = os.path.join(UPLOAD_DIR, "chunks.json")

    with open(path, "w", encoding="utf-8") as f:
        json.dump(chunks, f, ensure_ascii=False, indent=4)


def load_chunks():
    path = os.path.join(UPLOAD_DIR, "chunks.json")

    if not os.path.exists(path):
        raise FileNotFoundError("chunks.json not found")

    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)