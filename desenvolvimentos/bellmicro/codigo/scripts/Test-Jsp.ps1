param([string]$ToolsDir = 'build/tools/jsp')
$ErrorActionPreference = 'Stop'
if (-not (Test-Path -LiteralPath (Join-Path $ToolsDir 'tomcat-jasper-9.0.98.jar'))) {
    throw 'Prepare o compilador local com scripts/Prepare-JspCompiler.py.'
}
& java -cp (Join-Path $ToolsDir '*') org.apache.jasper.JspC -uriroot build/dashboard315 -d build/jsp-compiled -compile -source 1.8 -target 1.8 RateioDespesas.jsp
if ($LASTEXITCODE -ne 0) { throw 'Compilacao do JSP falhou.' }
