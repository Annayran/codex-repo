# Teste Manual da API Genesis

Este projeto possui um smoke test Java para validar se o endpoint de estoque da Genesis responde com as credenciais configuradas.

## Importante

O teste primeiro valida a API com `GET /api/v1/estoques?page=0`. O `POST /api/v1/estoques/` so roda quando `GENESIS_SMOKE_CREATE_ENABLED=true`. Portanto, pode criar registro na Genesis. Execute POST somente em ambiente de homologacao/teste e com alinhamento do cliente.

Nenhuma credencial deve ser salva no repositorio.

## Variaveis de ambiente

Configure em uma sessao local do PowerShell:

```powershell
$env:GENESIS_SMOKE_ENABLED = 'true'
$env:GENESIS_BASE_URL = 'https://host-da-genesis/G4VAPI'
$env:GENESIS_TOKEN = '<token do cofre>'
```

Campos opcionais do payload de teste:

```powershell
$env:GENESIS_SMOKE_CODIGO = 'PRD-TESTE-CODEX'
$env:GENESIS_SMOKE_SERIE = 'SER-CODEX-001'
$env:GENESIS_SMOKE_ANVISA = '00000000000'
```

## Execucao

```powershell
gradle smokeTest
```

Se `GENESIS_SMOKE_ENABLED` nao for `true`, o teste e ignorado automaticamente.

## Resultado esperado

- HTTP 201: teste passou.
- HTTP 400: payload rejeitado; revisar campos obrigatorios no Swagger Genesis.
- HTTP 401: token invalido/expirado ou header incorreto.
- HTTP 5xx/timeout: indisponibilidade ou falha transitoria da Genesis.

## Criacao de registro

Por seguranca, o smoke test primeiro executa apenas autenticacao via GET. Para permitir tambem o POST de criacao de estoque de teste, defina:

```powershell
$env:GENESIS_SMOKE_CREATE_ENABLED = 'true'
```

Se a autenticacao retornar HTTP 401, o POST nao deve ser executado. Confirme o token antes de criar registros.
