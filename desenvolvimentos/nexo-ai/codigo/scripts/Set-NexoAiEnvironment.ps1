param([switch]$Disable)
$ErrorActionPreference = 'Stop'
if ($Disable) {
    $env:NEXO_AI_ENABLED = 'false'
    Remove-Item Env:OPENAI_API_KEY -ErrorAction SilentlyContinue
    Write-Host 'IA desativada neste terminal. Reinicie o receptor para aplicar.'
    return
}
$secureKey = Read-Host 'Chave da API OpenAI (entrada oculta; somente neste processo)' -AsSecureString
$keyPointer = [IntPtr]::Zero
try {
    $keyPointer = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($secureKey)
    $keyValue = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($keyPointer)
    if ([string]::IsNullOrWhiteSpace($keyValue) -or $keyValue -eq 'disabled') { throw 'Chave vazia ou inválida.' }
    $env:OPENAI_API_KEY = $keyValue
    $env:NEXO_AI_ENABLED = 'true'
    Write-Host 'Ambiente preparado neste terminal. Inicie o receptor pelo mesmo terminal. Nenhuma chamada foi feita.'
} finally {
    $keyValue = $null
    if ($keyPointer -ne [IntPtr]::Zero) { [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($keyPointer) }
    $secureKey.Dispose()
}
