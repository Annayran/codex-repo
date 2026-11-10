"""Gera metadados legados usando exportInfo de um export nativo da versao alvo."""
import argparse
import copy
from pathlib import Path
import json
import xml.etree.ElementTree as E
import zipfile

ROOT = Path(__file__).resolve().parents[1]
EXPECTED_SYSTEM_VERSION = "4.35b811"

master = {
    "ID": ("I", "Identificador"),
    "NUMPED": ("S", "Numero do pedido externo"),
    "DTEMISSAO": ("D", "Data de emissao"),
    "CNPJFORN": ("S", "CNPJ fornecedor no PDF"),
    "FORNECEDOR": ("S", "Fornecedor no PDF"),
    "COMPRADOR": ("S", "Comprador no PDF"),
    "CONDPAG": ("S", "Condicao de pagamento no PDF"),
    "TOTALPDF": ("F", "Total impresso no PDF"),
    "LAYOUT": ("S", "Versao do leitor"),
    "CNPJCLI": ("S", "CNPJ de faturamento"),
    "ARQUIVO": ("S", "Nome do arquivo de origem"),
    "SHA256": ("S", "SHA256 informado pelo leitor"),
    "PAGINAS": ("I", "Quantidade de paginas"),
    "CHAVEIMP": ("S", "Chave unica de recebimento"),
    "SITUACAO": ("S", "Situacao do recebimento"),
    "CODUSU": ("I", "Usuario da importacao"),
    "DHIMPORT": ("D", "Data e hora da importacao - validar precisao no ambiente"),
}

detail = {
    "IDPED": ("I", "Pedido recebido"),
    "SEQEXT": ("S", "Sequencia no PDF"),
    "TIPO": ("S", "Material ou servico"),
    "CODEXT": ("S", "Codigo externo Claro"),
    "DESCRICAO": ("S", "Descricao e execucao no PDF"),
    "CENTROEXT": ("S", "Centro externo Claro"),
    "DTENTREGA": ("D", "Entrega prevista"),
    "QTDE": ("F", "Quantidade"),
    "UNEXT": ("S", "Unidade no PDF"),
    "VLRUNIT": ("F", "Preco unitario no PDF"),
    "ALIQICMS": ("F", "Aliquota ICMS ou ISS informada"),
    "VLRICMS": ("F", "Valor ICMS ou ISS informado"),
    "ALIQST": ("F", "Aliquota ST informada"),
    "VLRST": ("F", "Valor ST informado"),
    "ALIQIPI": ("F", "Aliquota IPI informada"),
    "VLRIPI": ("F", "Valor IPI informado"),
    "TOTALITEM": ("F", "Total impresso do item"),
    "SITE": ("S", "Identificadores de site no PDF"),
    "MUNICIPIO": ("S", "Municipio de execucao"),
    "INICIO": ("S", "Mes e ano inicial"),
    "TERMINO": ("S", "Mes e ano final"),
    "PAGINA": ("I", "Pagina do item"),
    "CNPJCLI": ("S", "CNPJ de faturamento do item"),
    "FATURAMENTO": ("S", "Bloco de faturamento do anexo"),
    "LOCENTREGA": ("S", "Bloco de entrega do anexo"),
    "PAGANEXO": ("I", "Pagina do anexo"),
}


def load_export_info(reference_path):
    if not reference_path.is_file():
        raise SystemExit("Export nativo para exportInfo nao encontrado: " + str(reference_path))
    with zipfile.ZipFile(reference_path) as archive:
        if archive.testzip() is not None or "metadata.xml" not in archive.namelist():
            raise SystemExit("Export nativo invalido ou sem metadata.xml.")
        reference_root = E.fromstring(archive.read("metadata.xml"))
    export_info = reference_root.find("exportInfo")
    expected_export_order = ["exportTime", "systemVersion", "systemCharSet", "dbMetadata"]
    if reference_root.tag != "metadata" or export_info is None or [node.tag for node in export_info] != expected_export_order:
        raise SystemExit("Estrutura exportInfo nao reconhecida no export nativo.")
    if export_info.findtext("systemVersion") != EXPECTED_SYSTEM_VERSION:
        raise SystemExit("O export nativo nao pertence a versao Sankhya esperada " + EXPECTED_SYSTEM_VERSION + ".")
    if (export_info.findtext("systemCharSet") or "").upper() != "ISO-8859-1":
        raise SystemExit("Charset do export nativo nao compativel com o pacote.")
    db_metadata = export_info.find("dbMetadata")
    expected_db_order = ["dbUser", "urlConnection", "jdbcDriver", "DBMS"]
    if db_metadata is None or [node.tag for node in db_metadata] != expected_db_order:
        raise SystemExit("dbMetadata incompleto no export nativo.")
    if not all((node.text or "").strip() for node in list(export_info)[:3] + list(db_metadata)):
        raise SystemExit("exportInfo possui valor obrigatorio vazio.")
    return copy.deepcopy(export_info)


def add_fields(instance, fields, schema, lines):
    field_list = E.SubElement(instance, "fields")
    for field, (data_type, label) in fields.items():
        size = 2000 if field in {"DESCRICAO", "FATURAMENTO", "LOCENTREGA"} else 255 if field in {"ARQUIVO", "CONDPAG", "SITE"} else 100
        attrs = {
            "name": field,
            "systemField": "N",
            "dataType": data_type,
            "presentationType": "P",
            "calculated": "N",
            "allowSearch": "N",
            "allowDefault": "S",
            "visibleOnSearch": "S",
            "allowNull": "N",
        }
        if data_type == "S":
            attrs["size"] = str(size)
        xml_field = E.SubElement(field_list, "field", attrs)
        E.SubElement(xml_field, "description").text = label
        schema[field] = {"type": data_type, "size": size if data_type == "S" else None, "label": label}
        lines.append(f"| {field} | {data_type} | {label} |")


parser = argparse.ArgumentParser()
parser.add_argument(
    "--export-info-from",
    type=Path,
    default=Path.home() / "Downloads" / "Metadados_AD_PEDREC.zip",
    help="Export nativo legado da PortoMag 4.35b811 usado somente como origem do exportInfo.",
)
args = parser.parse_args()
export_info = load_export_info(args.export_info_from)

root = E.Element("metadata")
# O XML fonte permanece sanitizado. O exportInfo nativo entra apenas no ZIP ignorado pelo Git.
instances = E.SubElement(root, "instances")
schema = {}
lines = [
    "# Dicionario proposto — V1.1",
    "",
    "Metadados novos. O XML fonte nao contem conexao, identificadores internos, acoes ou possiveis credenciais dos exports de referencia. O ZIP de homologacao recebe somente o exportInfo do export nativo 4.35b811 e permanece ignorado pelo Git.",
    "",
    "O detalhe usa chave composta `IDPED + SEQEXT`; o mestre possui a ligacao para o detalhe. Esse desenho segue a topologia comprovada no export legado 4.36b126, mas a importacao na versao PortoMag continua pendente de homologacao.",
    "",
    "Os campos tecnicos ID, IDPED, CHAVEIMP, SITUACAO, CODUSU, DHIMPORT, ARQUIVO, SHA256, PAGINAS, LAYOUT e paginas de evidencia sao definidos pela solucao. Os demais representam informacoes observadas no PDF.",
    "",
]

# O export nativo observado lista os detalhes antes do mestre que os referencia.
detail_instance = E.SubElement(instances, "instance", name="ZPM_PEDITE", isUpdate="false")
E.SubElement(detail_instance, "instanceDescription").text = "PortoMag - Itens do pedido recebido"
detail_table = E.SubElement(detail_instance, "tableInfo", name="ZPM_PEDITE", sequenceType="M")
E.SubElement(detail_table, "tableDescription").text = "PortoMag - Itens do pedido recebido"
detail_pk = E.SubElement(detail_table, "primaryKey")
E.SubElement(detail_pk, "IDPED")
E.SubElement(detail_pk, "SEQEXT")
schema["ZPM_PEDITE"] = {}
lines.extend(["## ZPM_PEDITE", "", "| Campo | Tipo | Descricao |", "|---|---|---|"])
add_fields(detail_instance, detail, schema["ZPM_PEDITE"], lines)
lines.append("")

master_instance = E.SubElement(instances, "instance", name="ZPM_PEDREC", isUpdate="false")
E.SubElement(master_instance, "instanceDescription").text = "PortoMag - Pedidos recebidos"
master_table = E.SubElement(master_instance, "tableInfo", name="ZPM_PEDREC", sequenceType="A", sequenceField="ID", presentationField="NUMPED")
E.SubElement(master_table, "tableDescription").text = "PortoMag - Pedidos recebidos"
E.SubElement(E.SubElement(master_table, "primaryKey"), "ID")
schema["ZPM_PEDREC"] = {}
lines.extend(["## ZPM_PEDREC", "", "| Campo | Tipo | Descricao |", "|---|---|---|"])
add_fields(master_instance, master, schema["ZPM_PEDREC"], lines)
relationship = E.SubElement(master_instance, "relationShip")
relation = E.SubElement(relationship, "relation", entityName="ZPM_PEDITE", type="I", insert="N", update="N", remove="S")
E.SubElement(relation, "targetInfo", systemInstance="N", tableName="ZPM_PEDITE")
E.SubElement(E.SubElement(relation, "fields"), "field", localName="ID", targetName="IDPED")
lines.append("")

metadata_dir = ROOT / "metadata"
metadata_dir.mkdir(exist_ok=True)
E.indent(root, space="  ")
xml = E.tostring(root, encoding="ISO-8859-1", xml_declaration=True)
xml = xml.replace(
    b"<?xml version='1.0' encoding='ISO-8859-1'?>",
    b'<?xml version="1.0" encoding="ISO-8859-1"?>',
    1,
)
(metadata_dir / "metadata.xml").write_bytes(xml)
(metadata_dir / "schema.json").write_text(json.dumps(schema, ensure_ascii=False, indent=2), encoding="utf-8")
(ROOT / "docs" / "DICIONARIO.md").write_text("\n".join(lines), encoding="utf-8")

dist = ROOT / "out"
dist.mkdir(exist_ok=True)
artifact_root = copy.deepcopy(root)
artifact_root.insert(0, export_info)
E.indent(artifact_root, space="  ")
artifact_xml = E.tostring(artifact_root, encoding="ISO-8859-1", xml_declaration=True)
artifact_xml = artifact_xml.replace(
    b"<?xml version='1.0' encoding='ISO-8859-1'?>",
    b'<?xml version="1.0" encoding="ISO-8859-1"?>',
    1,
)
with zipfile.ZipFile(dist / "Metadados_ZPM_PEDIDOS_V1.1_HOMOLOGACAO.zip", "w", zipfile.ZIP_DEFLATED) as archive:
    info = zipfile.ZipInfo("metadata.xml", (2026, 9, 4, 0, 0, 0))
    info.compress_type = zipfile.ZIP_DEFLATED
    archive.writestr(info, artifact_xml)

print("Metadados gerados: exportInfo nativo " + EXPECTED_SYSTEM_VERSION + ", 2 instancias, 43 campos e detalhe com PK composta.")
