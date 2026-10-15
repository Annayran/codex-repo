param([ValidateRange(1024,65535)][int]$Port = 18080)
$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path $PSScriptRoot -Parent
$appJar = Join-Path $projectRoot 'target/quarkus-app/quarkus-run.jar'
if (!(Test-Path -LiteralPath $appJar)) { throw 'Compile o serviço com mvn verify antes de executar.' }
$listener = [Net.Sockets.TcpListener]::new([Net.IPAddress]::Loopback, $Port)
try { $listener.Start() } finally { $listener.Stop() }
$runDir = Join-Path $projectRoot ('target/connection-check-' + [Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $runDir | Out-Null
$service = $null
try {
    $arguments = @('-Djdk.net.unixdomain.tmpdir=target', '-Dquarkus.profile=dev',
        '-Dquarkus.http.host=127.0.0.1', "-Dquarkus.http.port=$Port", '-Dnexo.ai.enabled=false',
        ('"-Dnexo.storage=' + $runDir + '"'), '-jar', 'target/quarkus-app/quarkus-run.jar')
    $service = Start-Process -FilePath 'java' -ArgumentList $arguments -WorkingDirectory $projectRoot `
        -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $runDir 'stdout.log') `
        -RedirectStandardError (Join-Path $runDir 'stderr.log')
    $base = "http://127.0.0.1:$Port"
    $headers = @{Authorization = 'Basic ' + [Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes('demo-a:fixture-only-a'))}
    $ready = $false
    for ($attempt = 0; $attempt -lt 60; $attempt++) {
        if ($service.HasExited) { throw 'Serviço encerrou durante a inicialização. Consulte os logs locais em target/connection-check-*.' }
        try {
            Invoke-RestMethod "$base/investigations/00000000-0000-0000-0000-000000000000" -Headers $headers -TimeoutSec 2 | Out-Null
        } catch {
            if ($_.Exception.Response -and [int]$_.Exception.Response.StatusCode -eq 404) { $ready = $true; break }
        }
        Start-Sleep -Milliseconds 500
    }
    if (!$ready) { throw 'O receptor local não ficou pronto no prazo.' }
    $packet = Get-Content (Join-Path $projectRoot 'docs/examples/package-simulated.json') -Raw | ConvertFrom-Json
    $packet.collectedAt = [DateTime]::UtcNow.ToString('o')
    $receipt = Invoke-RestMethod "$base/investigations/packages?receipt=true" -Method Post -Headers $headers `
        -ContentType 'application/json; charset=utf-8' -Body ([Text.Encoding]::UTF8.GetBytes(($packet | ConvertTo-Json -Depth 20))) -TimeoutSec 10
    $parsedId = [Guid]::Empty
    if (![Guid]::TryParse([string]$receipt.id, [ref]$parsedId) -or !$receipt.simulated) { throw 'Recibo inválido ou sem indicador de simulação.' }
    $diagnosis = Invoke-RestMethod "$base/investigations/$($receipt.id)" -Headers $headers -TimeoutSec 10
    $context = Invoke-RestMethod "$base/investigations/$($receipt.id)/context" -Headers $headers -TimeoutSec 10
    if ($diagnosis.id -ne $receipt.id -or !$diagnosis.simulated -or !$context) { throw 'Diagnóstico ou contexto não corresponde ao envio.' }
    [PSCustomObject]@{Result='PASS'; Investigation=$receipt.id; State=$diagnosis.state; Simulated=$diagnosis.simulated; ERPQueried=$false; AIEnabled=$false}
} finally {
    if ($service -and !$service.HasExited) { Stop-Process -Id $service.Id; $service.WaitForExit() }
}
