"""Compila e empacota o backend Java 8 sem incluir bibliotecas Sankhya no artefato."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess
import zipfile

ROOT = Path(__file__).resolve().parents[1]
WORK = ROOT / "build" / "fallback-javac"
OUTPUT = ROOT / "out" / "ZAYA-PORTOMAGPDF-V1.3-HOMOLOGACAO.jar"


def sha256(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def run(command):
    completed = subprocess.run(command, cwd=str(ROOT), text=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    if completed.stdout:
        print(completed.stdout.rstrip())
    if completed.returncode:
        raise SystemExit(completed.returncode)


parser = argparse.ArgumentParser()
parser.add_argument("--java8-home", type=Path, required=True)
parser.add_argument("--extensions-jar", type=Path, required=True)
parser.add_argument("--dependencies-dir", type=Path, required=True)
parser.add_argument("--test-input", type=Path, default=ROOT / "build" / "test-input.xml")
args = parser.parse_args()

javac = args.java8_home / "bin" / ("javac.exe" if os.name == "nt" else "javac")
java = args.java8_home / "bin" / ("java.exe" if os.name == "nt" else "java")
if not javac.is_file() or not java.is_file():
    raise SystemExit("Java 8 invalido.")
if not args.extensions_jar.is_file():
    raise SystemExit("SankhyaW-extensions.jar nao encontrado.")
jape = sorted(args.dependencies_dir.glob("jape-*.jar"))
modelcore = sorted(args.dependencies_dir.glob("mge-modelcore-*.jar"))
if len(jape) != 1 or len(modelcore) != 1:
    raise SystemExit("Informe um diretorio com exatamente um jape-*.jar e um mge-modelcore-*.jar.")

run([str(javac), "-version"])
if WORK.exists():
    resolved = WORK.resolve()
    resolved.relative_to((ROOT / "build").resolve())
    shutil.rmtree(resolved)
main_classes = WORK / "main"
test_classes = WORK / "test"
main_classes.mkdir(parents=True)
test_classes.mkdir(parents=True)

classpath_items = [args.extensions_jar.resolve(), jape[0].resolve(), modelcore[0].resolve()]
classpath = os.pathsep.join(str(path) for path in classpath_items)
main_sources = sorted((ROOT / "src" / "main" / "java").rglob("*.java"))
test_sources = sorted((ROOT / "src" / "test" / "java").rglob("*.java"))
if not main_sources:
    raise SystemExit("Fontes Java principais nao encontrados.")

run([
    str(javac), "-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint:unchecked",
    "-classpath", classpath, "-d", str(main_classes),
] + [str(path) for path in main_sources])

tests_executed = False
if test_sources and args.test_input.is_file():
    run([
        str(javac), "-encoding", "UTF-8", "-source", "1.8", "-target", "1.8",
        "-classpath", os.pathsep.join([str(main_classes), classpath]), "-d", str(test_classes),
    ] + [str(path) for path in test_sources])
    run([
        str(java), "-classpath", os.pathsep.join([str(test_classes), str(main_classes), classpath]),
        "br.com.zaya.portomag.importador.model.LeituraXmlTest", str(args.test_input),
    ])
    tests_executed = True

OUTPUT.parent.mkdir(exist_ok=True)
manifest = (
    "Manifest-Version: 1.0\r\n"
    "Implementation-Version: 1.3\r\n"
    "Implementation-Vendor: Zaya.IT\r\n"
    "Implementation-Status: HOMOLOGACAO-PENDENTE\r\n"
    "\r\n"
).encode("utf-8")
with zipfile.ZipFile(OUTPUT, "w", zipfile.ZIP_DEFLATED) as archive:
    info = zipfile.ZipInfo("META-INF/MANIFEST.MF", (2026, 9, 4, 0, 0, 0))
    info.compress_type = zipfile.ZIP_DEFLATED
    archive.writestr(info, manifest)
    for path in sorted(main_classes.rglob("*.class")):
        info = zipfile.ZipInfo(path.relative_to(main_classes).as_posix(), (2026, 9, 4, 0, 0, 0))
        info.compress_type = zipfile.ZIP_DEFLATED
        archive.writestr(info, path.read_bytes())

build_info = {
    "artifact": OUTPUT.name,
    "artifactSha256": sha256(OUTPUT),
    "java": "8",
    "extensionsJar": {"name": args.extensions_jar.name, "sha256": sha256(args.extensions_jar)},
    "compileOnly": [
        {"name": jape[0].name, "sha256": sha256(jape[0])},
        {"name": modelcore[0].name, "sha256": sha256(modelcore[0])},
    ],
    "dependenciesEmbedded": False,
    "modelTestsExecuted": tests_executed,
    "runtimeCompatibility": "PENDENTE: compilar novamente com Jape/modelcore homologados da PortoMag 4.35b811.",
}
(OUTPUT.parent / "backend-build.json").write_text(json.dumps(build_info, ensure_ascii=False, indent=2), encoding="utf-8")
print("JAR gerado:", OUTPUT)
print("SHA-256:", build_info["artifactSha256"])
