"""Ferramenta de validação local; não integra dependências do módulo Sankhya."""
from pathlib import Path
import urllib.request,hashlib,json
target=Path(__file__).resolve().parent.parent/'build/tools/jsp'
target.mkdir(parents=True,exist_ok=True)
items=[('org/apache/tomcat',n,'9.0.98') for n in ['tomcat-jasper','tomcat-jasper-el','tomcat-el-api','tomcat-jsp-api','tomcat-servlet-api','tomcat-juli','tomcat-util','tomcat-util-scan','tomcat-api','tomcat-annotations-api']]
items += [('org/apache/ant','ant','1.10.15'),('org/apache/ant','ant-launcher','1.10.15'),('org/eclipse/jdt','ecj','3.38.0')]
manifest=[]
for group,name,version in items:
    filename=f'{name}-{version}.jar'
    url=f'https://repo.maven.apache.org/maven2/{group}/{name}/{version}/{filename}'
    path=target/filename
    if not path.exists():path.write_bytes(urllib.request.urlopen(url,timeout=40).read())
    manifest.append({'url':url,'sha256':hashlib.sha256(path.read_bytes()).hexdigest()})
(target/'manifest.json').write_text(json.dumps(manifest,indent=2),encoding='utf-8')
print('Compilador JSP local preparado em build/tools/jsp.')
