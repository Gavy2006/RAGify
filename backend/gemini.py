import os
from dotenv import load_dotenv
from google import genai

load_dotenv()

client = genai.Client(
    api_key=os.getenv("GEMINI_API_KEY")
)

def ask_gemini(context, question):
    prompt = f"""
You are a helpful AI assistant.

Answer ONLY using the provided context.

If the answer is not present in the context, reply exactly:
"I couldn't find the answer in the uploaded document."

Context:
{context}

Question:
{question}
"""

    try:
        response = client.models.generate_content(
    model="gemini-3.5-flash",
    contents=prompt,
)
        return response.text

    except Exception as e:
        return f"Error: {e}"