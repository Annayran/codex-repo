from pypdf import PdfReader

pdf_path = r"C:\Users\annayran.ferreira\Downloads\ZY-OSPG-2026-DASH-INTPDD-001.pdf"
reader = PdfReader(pdf_path)

print("pages", len(reader.pages))
for index, page in enumerate(reader.pages, start=1):
    text = (page.extract_text() or "").strip()
    print("--- PAGE %s ---" % index)
    print(text[:3500])
