from pathlib import Path
import hashlib,json,zipfile,re,xml.etree.ElementTree as E
root=Path(__file__).resolve().parents[1]
# Empacotamento JSP conforme referencia: CSS e codigo da tela no proprio index.jsp.
# Fontes modulares sao mantidos apenas no projeto; nao sao publicados como .mjs.
view=(root/'web'/'view'/'dashboard.html').read_text(encoding='utf-8')
assert 'href="styles.css"' in view
assert 'src="../controller/dashboard.mjs"' in view
css=(root/'web'/'view'/'styles.css').read_text(encoding='utf-8')
model=(root/'web'/'model'/'claro.mjs').read_text(encoding='utf-8')
model=re.sub(r'^export (?=const |function )','',model,flags=re.MULTILINE)
controller=(root/'web'/'controller'/'dashboard.mjs').read_text(encoding='utf-8')
controller=controller.replace("import {parseClaro,toXml} from '../model/claro.mjs';",'',1)
script='(function () {\n"use strict";\n'+model+'\n'+controller+'\n})();'
# EL habilitada no JSP, como na referencia. Nao deixar interpolacao JS ser interpretada no servidor.
assert '${' not in script and '#{' not in script and '<%' not in script
assert '</script' not in script.lower() and '</style' not in css.lower()
assert 'import.meta' not in script and not re.search(r'^\s*(export|import)\s',script,re.MULTILINE)
entry=view.replace('<link rel="stylesheet" href="styles.css">','<style type="text/css">\n'+css+'\n</style>')
entry=entry.replace('<script type="module" src="../controller/dashboard.mjs"></script>','<script type="text/javascript">\n'+script+'\n</script>')
entry=entry.replace('<body data-action-id="68">','<body data-action-id="68" data-pdf-base="${BASE_FOLDER}/" data-pdf-script="pdf.js" data-pdf-worker="pdf.worker.js">',1)
entry=entry.replace('</head>','<snk:load />\n</head>',1)
directives='<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>\n<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>\n<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>\n'
(root/'web'/'index.jsp').write_text(directives+entry,encoding='utf-8',newline='\n')
lock=json.loads((root/'vendor-lock.json').read_text(encoding='utf-8'))
for name,wanted in lock['files'].items():
 p=root/'web'/'vendor'/name
 if not p.is_file() or hashlib.sha256(p.read_bytes()).hexdigest()!=wanted:
  raise SystemExit('Dependencia ausente/divergente: '+name)
tree=E.parse(root/'metadata'/'metadata.xml')
instances=tree.findall('./instances/instance')
assert [i.attrib['name'] for i in instances]==['ZPM_PEDITE','ZPM_PEDREC']
assert sum(len(i.findall('./fields/field')) for i in instances)==43
assert instances[0].find('./tableInfo').attrib['sequenceType']=='M'
assert [n.tag for n in instances[0].findall('./tableInfo/primaryKey/*')]==['IDPED','SEQEXT']
relation=instances[1].find('./relationShip/relation')
assert relation.attrib=={'entityName':'ZPM_PEDITE','type':'I','insert':'N','update':'N','remove':'S'}
assert relation.find('./fields/field').attrib=={'localName':'ID','targetName':'IDPED'}
assert instances[1].find('./tableInfo/primaryKey/ID') is not None
metadata_zip=root/'out'/'Metadados_ZPM_PEDIDOS_V1.1_HOMOLOGACAO.zip'
with zipfile.ZipFile(metadata_zip) as archive:
 metadata_root=E.fromstring(archive.read('metadata.xml'))
assert [node.tag for node in metadata_root]==['exportInfo','instances']
export_info=metadata_root.find('./exportInfo')
assert [node.tag for node in export_info]==['exportTime','systemVersion','systemCharSet','dbMetadata']
assert export_info.findtext('systemVersion')=='4.35b811'
assert [node.tag for node in export_info.find('./dbMetadata')]==['dbUser','urlConnection','jdbcDriver','DBMS']
out=root/'out';out.mkdir(exist_ok=True)
with zipfile.ZipFile(out/'HTML5_PORTOMAGPDF_V1.4_ACAO68.zip','w',zipfile.ZIP_DEFLATED) as z:
 # A biblioteca continua byte a byte identica ao lock. Apenas extensoes publicadas mudam.
 # Incluir biblioteca/worker inteiros no JSP aumentaria desnecessariamente a unidade compilada.
 for target,source in [('index.jsp','index.jsp'),('pdf.js','vendor/pdf.mjs'),('pdf.worker.js','vendor/pdf.worker.mjs'),('LICENSE-PDFJS.txt','vendor/LICENSE')]:
  info=zipfile.ZipInfo(target,(2026,9,4,0,0,0));info.compress_type=zipfile.ZIP_DEFLATED;z.writestr(info,(root/'web'/source).read_bytes())
artifact_candidates=[
 out/'HTML5_PORTOMAGPDF_V1.4_ACAO68.zip',
 metadata_zip,
 out/'ZAYA-PORTOMAGPDF-V1.3-HOMOLOGACAO.jar',
]
artifacts=[path for path in artifact_candidates if path.is_file()]
manifest={p.name:{'bytes':p.stat().st_size,'sha256':hashlib.sha256(p.read_bytes()).hexdigest()} for p in artifacts}
(out/'SHA256.json').write_text(json.dumps(manifest,indent=2),encoding='utf-8')
print('Pacotes gerados e estrutura local validada. Importacao no Sankhya NAO executada.')
