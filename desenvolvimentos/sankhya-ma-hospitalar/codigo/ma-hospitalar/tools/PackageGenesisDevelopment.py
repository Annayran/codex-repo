from pathlib import Path
import hashlib
import json
import os
import re
import zipfile

ROOT = Path(__file__).resolve().parents[1]
DEST = ROOT / 'output/entrega-completa-20261029'
DEST.mkdir(parents=True, exist_ok=True)
archive = DEST / 'MA-Hospitalar-Genesis-Desenvolvimento-Completo-20261029.zip'
excluded_dirs = {'.git', 'node_modules', '.pnpm-store', '.gradle', '.gradle-user-home', '__pycache__', 'qa', 'tmp', 'analysis'}
excluded_ext = {'.xlsx', '.xls', '.csv', '.log', '.args', '.class', '.png', '.jpg', '.jpeg', '.mp4', '.db', '.zip'}
selected = {}
omitted = []

def consider(path, base=None):
    rel = path.relative_to(ROOT)
    if any(part in excluded_dirs for part in rel.parts):
        return
    if path.suffix.lower() in excluded_ext or path.name in {'.DS_Store'}:
        return
    if re.search(r'(^\.env($|\.)|credentials|secrets|postman_environment|\.pem$|\.pfx$|\.key$)', path.name, re.I):
        omitted.append(str(rel)); return
    # Exclude populated secret assignments, never print their values.
    if path.suffix.lower() in {'.md', '.txt', '.json', '.xml', '.properties', '.yml', '.yaml', '.jsp', '.js', '.cjs', '.java', '.sql', '.ps1', '.py', '.gradle'}:
        body = path.read_text(encoding='utf-8-sig', errors='replace')
        patterns = [r'-----BEGIN (?:RSA |EC |OPENSSH )?PRIVATE KEY-----', r'gh[pousr]_[A-Za-z0-9]{30,}', r'Bearer\s+eyJ[A-Za-z0-9._-]{30,}', r'(?i)(?:senha|password|token|api_key)\s*[:=]\s*[\"\x27]([^\"\x27\r\n]{8,})[\"\x27]']
        for pattern in patterns:
            if 'vendor' in rel.parts and '(?:senha|password|token|api_key)' in pattern:
                continue
            for match in re.finditer(pattern, body):
                value = match.group(1) if match.lastindex else match.group(0)
                if any(x in value.lower() for x in ['preencher', 'placeholder', 'example', 'exemplo', '${', '{{', 'getenv', 'system.', 'token', 'senha', 'password', 'masked', 'redacted', 'teste', 'test-', 'fake', 'dummy']):
                    continue
                omitted.append(str(rel)); return
    selected['ma-hospitalar/' + rel.as_posix()] = path

for folder in ['src', 'test', 'sql', 'metadata', 'docs', 'tools', 'webapp', 'gradle', 'prototypes']:
    for directory, dirs, files in os.walk(ROOT/folder):
        dirs[:] = [name for name in dirs if name not in excluded_dirs]
        for name in files: consider(Path(directory)/name)
for name in ['README.md', '.gitignore', 'build.gradle', 'settings.gradle', 'gradlew', 'gradlew.bat']:
    if (ROOT/name).is_file(): consider(ROOT/name)
for folder in ['genesis-m1-m3-hotfix-20261023', 'manual-genesis', 'levantamento-clientes']:
    for path in (ROOT/'output'/folder).glob('*'):
        if path.is_file() and path.suffix.lower() in {'.jar', '.sql', '.pdf', '.md'}: consider(path)

readme = '''# MA Hospitalar - desenvolvimento completo

Snapshot local em 29/10/2026, incluindo alteracoes ainda nao commitadas.
Conteudo: fontes Java, testes, SQL, metadados, dashboard JSP/React,
scripts de build e validacao, documentacao e ultimas entregas selecionadas.

## Retomada
- Java 8 e Gradle conforme build.gradle. Configure SNK_LIB externamente
  para as bibliotecas licenciadas do Sankhya; o SDK nao foi redistribuido.
- React: package.json e pnpm-lock.yaml preservados. Dependencias nao incluidas.
- JAR M1/M3+hotfix de 23/10 incluido como artefato ja existente; nao houve
  recompilacao nem teste funcional novo ao gerar este ZIP.
- Consultar README e runbooks antes de implantar. Este ZIP nao e instalador
  automatico. Nao executar todos os SQLs em lote: ha diagnosticos e rollbacks.
- Clientes: levantamento, nao desenvolvimento concluido. Escopo inclui
  parceiros da base instalada e necessarios aos itens-materiais sem cliente,
  saneamento, telas, bidirecional, exclusoes e inativacao/setores.
  Apenas carga inicial em massa excluida. Ultima definicao do solicitante:
  16h de previsao para a evolucao, separadas de 7h30 ja executadas.
  PDF v1.3 anterior ainda informa reestimativa: esta nota registra a errata.

## Seguranca e limites
Sem .git, caches, temporarios, node_modules, credenciais, ambientes locais,
planilhas/exportacoes de dados ou capturas brutas do sistema. PDFs publicados
incluem as imagens do manual. Nao inclui SDK externo nem historico Git.
Arquivos suspeitos na verificacao preventiva foram omitidos e relacionados
no manifesto, sem valores. Verificar as omissoes antes de assumir completude.
Nenhum commit, push, deploy ou chamada a API foi executado nesta entrega.
'''
manifest = {
    'snapshot_date': '2026-10-29',
    'branch': 'codex/ma-hospitalar-integracao-completa',
    'files': [{'path':name,'bytes':path.stat().st_size,'sha256':hashlib.sha256(path.read_bytes()).hexdigest()} for name,path in sorted(selected.items())],
    'security_omissions': sorted(set(omitted)),
    'new_build_or_functional_tests': False,
}
with zipfile.ZipFile(archive,'w',zipfile.ZIP_DEFLATED) as z:
    z.writestr('LEIA-ME-ENTREGA.md',readme)
    z.writestr('MANIFESTO.json',json.dumps(manifest,ensure_ascii=False,indent=2))
    for name,path in sorted(selected.items()): z.write(path,name)
with zipfile.ZipFile(archive) as z:
    assert z.testzip() is None
    for item in manifest['files']:
        assert hashlib.sha256(z.read(item['path'])).hexdigest()==item['sha256']
sha=hashlib.sha256(archive.read_bytes()).hexdigest()
(DEST/'SHA256.txt').write_text(sha+'  '+archive.name+'\n',encoding='ascii')
print(json.dumps({'archive':str(archive),'files':len(selected),'bytes':archive.stat().st_size,'sha256':sha,'omitted':manifest['security_omissions']},ensure_ascii=False))
