param([ValidateRange(1024,65535)][int]$Port=18083)
$ErrorActionPreference='Stop'
$projectRoot=Split-Path $PSScriptRoot -Parent
$ollama=Join-Path $projectRoot 'target/tools/ollama/ollama.exe'
if (!(Test-Path $ollama)) {throw 'Ollama portátil ausente em target/tools/ollama. Consulte docs/local-homologation.md.'}
if (!(Test-Path (Join-Path $projectRoot 'target/quarkus-app/quarkus-run.jar'))) {throw 'Execute mvn verify primeiro.'}
$runDir=Join-Path $projectRoot ('target/local-session-'+[Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory $runDir | Out-Null
$listener=[Net.Sockets.TcpListener]::new([Net.IPAddress]::Loopback,$Port)
try {$listener.Start()} finally {$listener.Stop()}
$engine=$null; $receiver=$null
$previousHost=$env:OLLAMA_HOST; $previousModels=$env:OLLAMA_MODELS
try {
    $env:OLLAMA_HOST='127.0.0.1:11434'; $env:OLLAMA_MODELS=Join-Path $projectRoot 'target/ollama-models'
    try {$tags=Invoke-RestMethod 'http://127.0.0.1:11434/api/tags' -TimeoutSec 2} catch {
        $engine=Start-Process $ollama -ArgumentList 'serve' -WorkingDirectory $projectRoot -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $runDir 'ollama.out.log') -RedirectStandardError (Join-Path $runDir 'ollama.err.log')
        for($i=0;$i -lt 30;$i++) {
            try {$tags=Invoke-RestMethod 'http://127.0.0.1:11434/api/tags' -TimeoutSec 2; break} catch {Start-Sleep -Milliseconds 500}
        }
    }
    if (!$tags -or !($tags.models | Where-Object name -eq 'qwen2.5:3b')) {throw 'Modelo qwen2.5:3b ausente. Prepare-o com ollama pull qwen2.5:3b usando OLLAMA_MODELS=target/ollama-models.'}
    $arguments=@('-Djdk.net.unixdomain.tmpdir=target','-Dquarkus.profile=local','-Dquarkus.http.host=127.0.0.1',"-Dquarkus.http.port=$Port",'-jar','target/quarkus-app/quarkus-run.jar')
    $receiver=Start-Process java -ArgumentList $arguments -WorkingDirectory $projectRoot -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $runDir 'receiver.out.log') -RedirectStandardError (Join-Path $runDir 'receiver.err.log')
    $ready=$false
    for($i=0;$i -lt 60;$i++) {
        if($receiver.HasExited){throw 'Receptor encerrou; consulte logs em target/local-session-*.'}
        try {Invoke-WebRequest "http://127.0.0.1:$Port/nexo.html" -TimeoutSec 2 | Out-Null; $ready=$true; break} catch {Start-Sleep -Milliseconds 500}
    }
    if(!$ready){throw 'Receptor não ficou pronto.'}
    $session=[ordered]@{receiverPid=$receiver.Id;receiverStarted=$receiver.StartTime.ToUniversalTime().ToString('o');ollamaPid=if($engine){$engine.Id}else{$null};ollamaStarted=if($engine){$engine.StartTime.ToUniversalTime().ToString('o')}else{$null};url="http://127.0.0.1:$Port/nexo.html"}
    $session | ConvertTo-Json | Set-Content (Join-Path $runDir 'session.json')
    [pscustomobject]@{URL=$session.url;Session=(Join-Path $runDir 'session.json');User='demo-a';Password='fixture-only-a';Scope='synthetic/local-only'}
} catch {
    foreach($ownedProcess in @($receiver,$engine)){if($ownedProcess -and !$ownedProcess.HasExited){Stop-Process -Id $ownedProcess.Id}}
    throw
} finally {$env:OLLAMA_HOST=$previousHost; $env:OLLAMA_MODELS=$previousModels}
