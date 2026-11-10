from __future__ import print_function

import hashlib
import json
from pathlib import Path
from zipfile import ZIP_DEFLATED, ZipFile

from openpyxl import load_workbook

import generate_genesis_base_sql as base


OUTPUT = Path(__file__).resolve().parents[1] / "sql" / "genesis" / "carga-unica-sem-blocos"
ZIP_PATH = OUTPUT.parent / "MAHospitalar-Inserts-Unicos.zip"
FILES = [
    "03_gen_cliente.sql",
    "04_gen_setor.sql",
    "05_gen_material.sql",
    "07_gen_modelo.sql",
    "08_gen_itemmat.sql",
]


def sha256(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def main():
    OUTPUT.mkdir(parents=True, exist_ok=True)
    base.OUTPUT = OUTPUT
    base.ROWS_PER_INSERT = 1000000000
    base.ROWS_PER_COMMIT = 1000000000
    base.report = {
        "input": str(base.INPUT),
        "idBase": base.IDBASE,
        "mode": "single-insert-all-per-table",
        "tables": {},
        "warnings": [],
    }

    workbook = load_workbook(base.INPUT, read_only=True, data_only=True)
    base.generate_clients(workbook)
    base.generate_sectors(workbook)
    base.generate_materials(workbook)
    base.generate_models(workbook)
    base.generate_items(workbook)

    report_path = OUTPUT / "generation-report.json"
    report_path.write_text(
        json.dumps(base.report, ensure_ascii=False, indent=2),
        encoding="utf-8",
    )

    readme = """# Carga Genesis com INSERT unico por tabela

Esta variante foi gerada por solicitacao expressa para conter exatamente um
`INSERT ALL` por tabela. Cada arquivo deve ser executado integralmente.

## Arquivos

1. `03_gen_cliente.sql` - 24.324 clientes.
2. `04_gen_setor.sql` - 24.326 setores.
3. `05_gen_material.sql` - 308 materiais.
4. `07_gen_modelo.sql` - 6.102 modelos.
5. `08_gen_itemmat.sql` - 65.093 itens instalados.

## Pre-requisitos

- `AD_GENBASE` deve conter `IDBASE=1`.
- Empresas e dominios Genesis devem ter sido carregados quando suas relacoes
  forem exigidas pelo modelo fisico.
- Execute primeiro em homologacao, sem jobs concorrentes.
- Mantenha backup restauravel.

## Riscos desta variante

- O maior comando possui 65.093 clausulas `INTO` e dezenas de megabytes.
- O Executor de Scripts pode exceder limite de requisicao, memoria ou timeout.
- Qualquer erro invalida o comando completo daquela tabela.
- Nao execute novamente apos sucesso, pois os IDs serao duplicados.

Os scripts em `carga-base-instalada` permanecem como alternativa recomendada em
lotes menores.
"""
    (OUTPUT / "README.md").write_text(readme, encoding="utf-8")

    with ZipFile(ZIP_PATH, "w", ZIP_DEFLATED) as archive:
        for name in FILES + ["README.md", "generation-report.json"]:
            archive.write(OUTPUT / name, name)

    result = {
        "output": str(OUTPUT),
        "zip": str(ZIP_PATH),
        "zipSha256": sha256(ZIP_PATH),
        "tables": base.report["tables"],
    }
    print(json.dumps(result, ensure_ascii=False, indent=2))


if __name__ == "__main__":
    main()
