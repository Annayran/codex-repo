from __future__ import print_function

import hashlib
import json
import re
from pathlib import Path
from zipfile import ZIP_DEFLATED, ZipFile


ROOT = Path(__file__).resolve().parents[1]
SOURCE = ROOT / "sql" / "genesis" / "carga-base-instalada"
OUTPUT = ROOT / "sql" / "genesis" / "executor-sankhya-100"
ZIP_PATH = OUTPUT.parent / "MAHospitalar-Executor-Sankhya-100.zip"

TABLES = [
    ("03_gen_cliente.sql", "03_cliente", "AD_GENCLIENTE", 24324),
    ("04_gen_setor.sql", "04_setor", "AD_GENSECTOR", 24326),
    ("05_gen_material.sql", "05_material", "AD_GENMATERIAL", 308),
    ("07_gen_modelo.sql", "07_modelo", "AD_GENMODELO", 6102),
    ("08_gen_itemmat.sql", "08_itemmat", "AD_GENITEMMAT", 65093),
]


def sha256(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def extract_blocks(path):
    content = path.read_text(encoding="utf-8")
    blocks = re.findall(
        r"INSERT ALL\r?\n.*?SELECT 1 FROM DUAL;",
        content,
        flags=re.DOTALL,
    )
    return [block[:-1] if block.endswith(";") else block for block in blocks]


def main():
    OUTPUT.mkdir(parents=True, exist_ok=True)
    manifest = {
        "format": "one-statement-per-file-no-semicolon",
        "maxRowsPerFile": 100,
        "tables": [],
    }

    for source_name, folder_name, table_name, expected_rows in TABLES:
        table_dir = OUTPUT / folder_name
        table_dir.mkdir(parents=True, exist_ok=True)
        blocks = extract_blocks(SOURCE / source_name)
        actual_rows = 0

        for index, block in enumerate(blocks, start=1):
            rows = sum(1 for line in block.splitlines() if line.startswith("  INTO "))
            actual_rows += rows
            part_path = table_dir / "parte_{0:04d}.sql".format(index)
            part_path.write_text(block + "\n", encoding="utf-8")

        if actual_rows != expected_rows:
            raise RuntimeError(
                "{0}: esperado {1}, encontrado {2}".format(
                    table_name, expected_rows, actual_rows
                )
            )

        manifest["tables"].append({
            "table": table_name,
            "folder": folder_name,
            "rows": actual_rows,
            "parts": len(blocks),
        })

    readme = """# Carga para o Executor de Scripts Sankhya

Cada arquivo `.sql` contem exatamente uma instrucao:

```sql
INSERT ALL
  ... ate 100 registros ...
SELECT 1 FROM DUAL
```

Os arquivos nao possuem comentarios de cabecalho, `SET`, `WHENEVER`, `COMMIT`,
`PROMPT`, consulta de contagem ou ponto e virgula no final.

## Ordem

1. Execute todas as partes de `03_cliente`, em ordem numerica.
2. Execute todas as partes de `04_setor`.
3. Execute todas as partes de `05_material`.
4. Confirme que `AD_GENEMPRESA` ja foi carregada.
5. Execute todas as partes de `07_modelo`.
6. Execute todas as partes de `08_itemmat`.

## Retomada

Anote cada parte concluida. Se uma parte falhar, corrija o problema e retome
naquela parte. Nao repita partes concluidas, pois os identificadores sao fixos.

Antes de iniciar, confirme que `AD_GENBASE` possui `IDBASE=1`, execute primeiro
em homologacao e mantenha a acao agendada Genesis desabilitada durante a carga.
"""
    (OUTPUT / "README.md").write_text(readme, encoding="utf-8")
    (OUTPUT / "manifest.json").write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2),
        encoding="utf-8",
    )

    with ZipFile(ZIP_PATH, "w", ZIP_DEFLATED) as archive:
        for path in sorted(OUTPUT.rglob("*")):
            if path.is_file():
                archive.write(path, path.relative_to(OUTPUT).as_posix())

    print(json.dumps({
        "output": str(OUTPUT),
        "zip": str(ZIP_PATH),
        "zipSha256": sha256(ZIP_PATH),
        "manifest": manifest,
    }, ensure_ascii=False, indent=2))


if __name__ == "__main__":
    main()
