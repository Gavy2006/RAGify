import numpy as np
import faiss

def create_index(dimension):
    return faiss.IndexFlatL2(dimension)

def add_embeddings(index, embeddings):
    embeddings = np.array(embeddings).astype("float32")
    index.add(embeddings)

def search(index, query_embedding, k=3):
    query_embedding = np.array(query_embedding).astype("float32")
    distances, indices = index.search(query_embedding, k)
    return distances, indices

