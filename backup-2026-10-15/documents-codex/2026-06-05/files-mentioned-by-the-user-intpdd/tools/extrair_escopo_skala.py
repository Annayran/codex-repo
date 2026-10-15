from docx import Document
from email import policy
from email.parser import BytesParser
import json
import re
import openpyxl


docx_path = r"C:\Users\annayran.ferreira\Downloads\1.0-Levantamento - Escopo Técnico Orçamento - Skala - Particionamento de Pedidos V2.docx"
eml_path = r"C:\Users\annayran.ferreira\Downloads\ENC_ Projeto Particionamento de Pedidos - Adição de Fluxos atual e proposto.eml"
xlsx_path = r"C:\Users\annayran.ferreira\Downloads\Clientes Paletização Especial.xlsx"


def read_docx(path):
    doc = Document(path)
    out = []
    for para in doc.paragraphs:
        text = para.text.strip()
        if text:
            out.append(text)
    for index, table in enumerate(doc.tables, start=1):
        out.append("[TABELA %s]" % index)
        for row in table.rows:
            values = [cell.text.strip().replace("\n", " | ") for cell in row.cells]
            if any(values):
                out.append(" || ".join(values))
    return "\n".join(out)


def read_eml(path):
    msg = BytesParser(policy=policy.default).parse(open(path, "rb"))
    parts = []
    for part in msg.walk():
        if part.get_content_type() in ("text/plain", "text/html"):
            try:
                parts.append(part.get_content())
            except Exception:
                pass
    text = " ".join(parts)
    text = re.sub("<[^>]+>", " ", text)
    text = re.sub(r"\s+", " ", text)
    return text


def read_xlsx(path):
    wb = openpyxl.load_workbook(path, data_only=True)
    ws = wb.active
    headers = [ws.cell(1, c).value for c in range(1, ws.max_column + 1)]
    rows = []
    for r in range(2, ws.max_row + 1):
        row = {headers[c - 1]: ws.cell(r, c).value for c in range(1, ws.max_column + 1)}
        rows.append(row)
    return {"sheet": ws.title, "rows": ws.max_row, "columns": ws.max_column, "headers": headers, "sample": rows[:12]}


def snippets(text, terms):
    result = {}
    low = text.lower()
    for term in terms:
        idx = low.find(term.lower())
        result[term] = text[max(0, idx - 500): idx + 1500] if idx >= 0 else ""
    return result


terms = [
    "mix de produtos",
    "produtos priorizados",
    "quantidade de paletes",
    "percentual que o seu grupo representa",
    "Paletização por Clientes",
    "Multicom",
    "Companhia Brasileira",
    "2h Distribuição",
    "Aceita Saldo",
]

payload = {
    "docx_text": read_docx(docx_path),
    "eml_snippets": snippets(read_eml(eml_path), terms),
    "xlsx": read_xlsx(xlsx_path),
}

print(json.dumps(payload, ensure_ascii=False, indent=2, default=str))
