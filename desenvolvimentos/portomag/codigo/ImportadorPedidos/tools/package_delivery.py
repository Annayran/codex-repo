"""Monta a entrega de homologacao sem incluir PDFs ou XMLs de cliente."""
from pathlib import Path
import hashlib
import json
import zipfile

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / "out"
BUNDLE = OUT / "PORTOMAG-IMPORTADOR-V1.5-HOMOLOGACAO.zip"

files = {
    "01-dashboard/HTML5_PORTOMAGPDF_V1.4_ACAO68.zip": OUT / "HTML5_PORTOMAGPDF_V1.4_ACAO68.zip",
    "02-metadados/Metadados_ZPM_PEDIDOS_V1.1_HOMOLOGACAO.zip": OUT / "Metadados_ZPM_PEDIDOS_V1.1_HOMOLOGACAO.zip",
    "03-backend/ZAYA-PORTOMAGPDF-V1.3-HOMOLOGACAO.jar": OUT / "ZAYA-PORTOMAGPDF-V1.3-HOMOLOGACAO.jar",
    "03-backend/backend-build.json": OUT / "backend-build.json",
    "04-banco/deploy.sql": ROOT / "database" / "deploy.sql",
    "04-banco/validacao.sql": ROOT / "database" / "validacao.sql",
    "04-banco/rollback.md": ROOT / "database" / "rollback.md",
    "05-documentacao/CADASTRO_ACAO.md": ROOT / "docs" / "CADASTRO_ACAO.md",
    "05-documentacao/IMPLANTACAO.md": ROOT / "docs" / "IMPLANTACAO.md",
    "05-documentacao/DICIONARIO.md": ROOT / "docs" / "DICIONARIO.md",
    "05-documentacao/VALIDACAO.md": ROOT / "docs" / "VALIDACAO.md",
}
missing = [str(path) for path in files.values() if not path.is_file()]
if missing:
    raise SystemExit("Arquivos obrigatorios ausentes: " + ", ".join(missing))

manifest = {
    name: {"bytes": path.stat().st_size, "sha256": hashlib.sha256(path.read_bytes()).hexdigest()}
    for name, path in files.items()
}
manifest_bytes = json.dumps(manifest, ensure_ascii=False, indent=2).encode("utf-8")
with zipfile.ZipFile(BUNDLE, "w", zipfile.ZIP_DEFLATED) as archive:
    for name, path in files.items():
        info = zipfile.ZipInfo(name, (2026, 9, 4, 0, 0, 0))
        info.compress_type = zipfile.ZIP_DEFLATED
        archive.writestr(info, path.read_bytes())
    info = zipfile.ZipInfo("SHA256-ENTREGA.json", (2026, 9, 4, 0, 0, 0))
    info.compress_type = zipfile.ZIP_DEFLATED
    archive.writestr(info, manifest_bytes)

print("Entrega:", BUNDLE)
print("SHA-256:", hashlib.sha256(BUNDLE.read_bytes()).hexdigest())
