from pathlib import Path
import re,xml.etree.ElementTree as ET,sys
root=Path(__file__).resolve().parents[1]
for path in [*root.glob("datadictionary/*.xml"),*root.glob("dbscripts/V*.xml"),root/"model/src/main/resources/META-INF/parameter.xml"]:
 raw=path.read_bytes();raw.decode("utf-8");tree=ET.fromstring(raw)
 if tree.tag=="metadata":
  assert tree.find("exportInfo") is not None
  for name in ["exportTime","systemVersion","systemCharSet","dbMetadata"]:assert tree.find("exportInfo/"+name) is not None
  for name in ["dbUser","urlConnection","jdbcDriver","DBMS"]:assert tree.find("exportInfo/dbMetadata/"+name) is not None
  assert list(tree).index(tree.find("exportInfo"))<list(tree).index(tree.find("instances"))
 for table in tree.findall(".//table"):
  assert table.findtext("description","").strip()
  fields={x.attrib["name"] for x in table.findall("fields/field")}
  for pk in table.findall("primaryKey/field"):assert pk.attrib["name"] in fields
for base in [root/"model/src",root/"vc/src",root/"datadictionary",root/"dbscripts"]:
 for p in base.rglob("*"):
  if p.is_file() and p.suffix in [".java",".xml",".js",".html",".css",".body",".include"]:
   text=p.read_text(encoding="utf-8")
   assert not re.search(r"br\.com\.fabricante|TMP_|TPL_|PTESTE|FN_SOMA|ExemploController|ExemploHTML5",text),str(p)
assert 'autoDDL=false' in (root/"build.gradle").read_text()
assert "rootProject.name = 'zaya-api-boletos'" in (root/"settings.gradle").read_text()
print("Contrato de estrutura, XML, UTF-8 e remoção dos demonstrativos: OK")

assert not list((root/'vc/src/main/webapp').rglob('icon.png'))

import json
contract=json.loads((root/'docs/contracts/itau-2.75.150.openapi.json').read_text())
def references(node):
 if isinstance(node,dict):
  if '$ref' in node:
   ref=node['$ref'];assert ref.startswith('#/');value=contract
   for key in ref[2:].split('/'):value=value[key]
  for child in node.values():references(child)
 elif isinstance(node,list):
  for child in node:references(child)
references(contract)
print('Referências internas do contrato Itaú: OK')
