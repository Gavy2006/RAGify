from pypdf import PdfReader


def text_return(pdf_path: str):

    reader = PdfReader(pdf_path)

    text = ""

    for page in reader.pages:
        text += page.extract_text() + "\n"

    return text