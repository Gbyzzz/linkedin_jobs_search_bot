import sys
import fitz  # PyMuPDF

def pdf_to_text(file_path):
    text = ""
    with fitz.open(file_path) as doc:
        for page in doc:
            text += page.get_text()
    return text

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: pdf_to_text.py <pdf_file>")
        sys.exit(1)
    file_path = sys.argv[1]
    result = pdf_to_text(file_path)

    # Use UTF-8 safe output
    sys.stdout.buffer.write(result.encode('utf-8'))
