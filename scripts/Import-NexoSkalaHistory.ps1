$ErrorActionPreference='Stop'
$root=Split-Path $PSScriptRoot -Parent
$desk=Join-Path $root 'config/private/desk-skala'
$access=Get-Content (Join-Path $desk 'access.json') -Raw|ConvertFrom-Json
$session=Get-Content (Join-Path $desk 'session.json') -Raw|ConvertFrom-Json
$headers=@{Authorization='Basic '+[Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes($access.principal+':'+$access.password))}
# Only explicitly known local homologations; no recursive scan, no raw logs transmitted.
$folders=@(
 'session-1c30666b0c4b40c6870e11305de0890b/data/trial-262d3e2ddc104a56a2ad0a3fbaabeee7/fixture',
 'session-568cd73cd1924577b27fa916e197cee5/data/trial-6ff950039b7e41caa6279e0603f052cf/fixture',
 'session-085614ae55db41ed972f0a85342b4fd7/data/trial-716dd04038e746d89500e18f0b8004dc/fixture')
$imported=0;$duplicates=0
foreach($folder in $folders) {
    $path=Join-Path $root ('config/private/'+$folder)
    if(!(Test-Path $path)){continue}
    foreach($file in Get-ChildItem -LiteralPath $path -File -Filter '*.json') {
        $stored=Get-Content $file.FullName -Raw|ConvertFrom-Json
        $diagnosis=if($stored.diagnosis){$stored.diagnosis}else{$stored}
        if(!$diagnosis.id -or !($diagnosis.tools|Where-Object tool -eq 'receiveEvidencePackage')){continue}
        # Reject obvious smoke fixtures; this import is for the existing ERP note only.
        if(!($diagnosis.facts|Where-Object {$_ -eq 'Selected note NUNOTA=5031796'})){continue}
        $message=([string]$diagnosis.symptom).Normalize([Text.NormalizationForm]::FormD) -replace '\p{M}',''
        $category=if($message -match '(?i)produto.*(inativo|nao esta ativo)'){'PRODUCT_INACTIVE'}elseif($message -match '(?i)quantidade entregue'){'DELIVERED_QTY'}elseif($message -match '(?i)particionamento ativo'){'NO_ACTIVE_PARTITION'}elseif($message -match '(?i)combinacao global'){'NO_GLOBAL_COMBINATION'}else{'UNCLASSIFIED'}
        $event=@{eventId=$diagnosis.id;executionId=$diagnosis.id;component='skala.particionamento.HistoricalReport';version='UNVERIFIED';category=$category;exceptionType='HistoricalReportedFailure';occurredAt=$file.LastWriteTimeUtc.ToString('o');impact='UNKNOWN';origin='HISTORICAL';investigationId=$null}
        $receipt=Invoke-RestMethod "http://127.0.0.1:$($session.port)/monitoring/events" -Headers $headers -Method Post -ContentType 'application/json' -Body ($event|ConvertTo-Json) -TimeoutSec 10
        if($receipt.duplicate){$duplicates++}else{$imported++}
    }
}
[pscustomobject]@{Imported=$imported;DuplicateDeliveries=$duplicates;RawLogsTransmitted=$false;Origin='HISTORICAL'}
