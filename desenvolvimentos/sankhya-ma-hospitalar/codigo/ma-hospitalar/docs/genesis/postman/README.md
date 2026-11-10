# Teste Postman - Genesis HML

Este kit reproduz o fluxo do movimento `2|93517|1|13482|400` sem expor o token
ou o CNPJ no repositorio.

## Diagnostico comprovado pelo log de 14/09/2026 11:11

- `GET /clientes/?codigoExterno=0081470002` retorna `[]`.
- `GET /clientes/95` retorna uma pessoa diferente; esse ID local esta incorreto.
- `GET /clientes/?cnpj=...` nao e aceito pela API. Ela exige `nome` ou
  `codigoExterno`.
- `POST /clientes/` falha porque o CNPJ ja existe.

Logo, o caminho correto para este caso e buscar por `nome`, selecionar somente
o resultado cujo CNPJ seja exatamente igual ao da TGFPAR e executar `PUT` nesse
ID para registrar `codigoExterno=0081470002`.

## Como executar

1. Desative temporariamente a acao agendada ou impeça que ela processe o mesmo
   movimento durante o teste.
2. Execute `01_preparar_movimento_93517.sql` no Sankhya e preencha as variaveis
   `PREENCHER_*` do ambiente.
3. Importe o environment e a collection no Postman.
4. Informe o token de HML apenas no campo secreto `token` do environment.
5. Execute manualmente as requisicoes `01` a `06`, conferindo cada resposta.
6. Execute `07A` somente se a busca de setor retornar vazio; use `07B` somente
   se o setor existir com cliente divergente.
7. Confirme em `08` que o modelo `3605` e realmente o produto `13482`.
8. Execute `09`. Use `10A` apenas se o item nao existir; use `10B` se existir.

Nao use o Collection Runner de forma cega: a colecao contem caminhos alternativos
de `POST` e `PUT` para permitir diagnostico controlado.

## Cabecalhos reais

Todas as chamadas usam:

```text
Accept: application/json
Content-Type: application/json; charset=UTF-8
token: <token de homologacao>
```

O cabecalho e `token`, nao `Authorization` nem `Bearer`.

## Evidencias

Para cada chamada, salve metodo, URL sem token, horario, status HTTP, corpo da
resposta e IDs retornados. Nao compartilhe token, CNPJ, CPF, endereco ou telefone
em grupos ou chamados sem mascaramento.
