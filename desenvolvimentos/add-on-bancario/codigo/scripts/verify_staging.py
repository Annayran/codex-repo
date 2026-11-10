from pathlib import Path
from xml.etree import ElementTree as ET
root=Path(__file__).resolve().parents[1]
metadata=root/'build/dist/datadictionary/metadata.xml'
raw=metadata.read_bytes()
tree=ET.fromstring(raw)
assert tree.tag=='MetadadosExtensionXML'
# O exportador 2.18 lê XMLs de origem como Latin-1. Repara somente sequências UTF-8
# duplamente interpretadas; texto Latin-1 correto permanece intacto.
def fix(value):
 if not value:return value
 try:return value.encode('latin-1').decode('utf-8')
 except (UnicodeError,ValueError):return value
for item in tree.iter():
 item.text=fix(item.text)
 item.tail=fix(item.tail)
 for key,value in list(item.attrib.items()):item.attrib[key]=fix(value)
for table in tree.findall('tables/table'):
 assert table.findtext('description','').strip()
assert len(tree.findall('tables/table'))==5
assert len(tree.findall('instances/instance'))>=5
assert not any(t.attrib.get('name')=='ZYCB_CRED' for t in tree.findall('tables/table'))
metadata.write_bytes(ET.tostring(tree,encoding='ISO-8859-1',xml_declaration=True))
assert 'Operações bancárias' in metadata.read_text(encoding='ISO-8859-1')
for name in ['oracle.sql','mssqlserver.sql']:
 script=root/'build/dist/dbscripts'/name
 assert script.exists() and script.stat().st_size>1000
 text=script.read_text(encoding='ISO-8859-1')
 for table in ['ZYCB_CFG','ZYCB_COB','ZYCB_OPER','ZYCB_RESERVA','ZYCB_CRED','ZYCB_REC','ZYCB_CURSOR','ZYCB_SCAN']:
  assert table in text
print('Staging validado: módulos, dois SGBDs, dicionário e acentuação.')
