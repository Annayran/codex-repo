import json
import sys
import zipfile
from pathlib import Path

from docx import Document


def iter_block_items(doc):
    body = doc.element.body
    for child in body.iterchildren():
        if child.tag.endswith('}p'):
            for p in doc.paragraphs:
                if p._p is child:
                    yield {"type": "paragraph", "style": p.style.name, "text": p.text}
                    break
        elif child.tag.endswith('}tbl'):
            for idx, table in enumerate(doc.tables):
                if table._tbl is child:
                    yield {
                        "type": "table",
                        "index": idx,
                        "rows": [[cell.text for cell in row.cells] for row in table.rows],
                    }
                    break


def inspect(path):
    doc = Document(path)
    data = {
        "path": str(Path(path).resolve()),
        "sections": [],
        "blocks": list(iter_block_items(doc)),
        "headers": [],
        "footers": [],
    }
    for section in doc.sections:
        data["sections"].append({
            "width": section.page_width,
            "height": section.page_height,
            "top": section.top_margin,
            "bottom": section.bottom_margin,
            "left": section.left_margin,
            "right": section.right_margin,
            "header_distance": section.header_distance,
            "footer_distance": section.footer_distance,
            "different_first_page": section.different_first_page_header_footer,
        })
        for name, part in (("header", section.header), ("first_header", section.first_page_header)):
            data["headers"].append({"name": name, "paragraphs": [p.text for p in part.paragraphs]})
        for name, part in (("footer", section.footer), ("first_footer", section.first_page_footer)):
            data["footers"].append({"name": name, "paragraphs": [p.text for p in part.paragraphs]})
    with zipfile.ZipFile(path) as zf:
        data["parts"] = sorted(zf.namelist())
    return data


if __name__ == "__main__":
    print(json.dumps(inspect(sys.argv[1]), ensure_ascii=False, indent=2))
