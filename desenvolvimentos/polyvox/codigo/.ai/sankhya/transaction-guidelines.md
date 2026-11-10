# Diretrizes de Transacao Sankhya

## Principios

- Nao tratar transacao como aplicacao Java comum sem verificar o contexto Sankhya.
- Verificar se action, job, evento ou procedure ja roda dentro de transacao gerenciada.
- Nao fazer commit/rollback manual sem padrao existente no projeto.
- Em erro critico, lancar excecao para permitir rollback.
- Nao engolir excecoes.
- Nao deixar alteracao parcial em `TGFCAB`, `TGFITE`, `TGFVAR`, `TGFFIN`, `TGFEST` ou tabelas `AD_`.
- Fechar recursos como `NativeSql`, `ResultSet`, `JdbcWrapper`, `PreparedStatement` ou equivalentes conforme padrao do projeto.
- Evitar multiplas conexoes/transacoes independentes dentro da mesma regra.
- Evitar updates diretos em documentos confirmados sem regra clara.
- Validar se o projeto usa `JapeSession`, `TransactionContext`, `JdbcWrapper`, `EntityFacade` ou outro padrao especifico.

## Checklist

- A rotina ja esta dentro de uma transacao do Sankhya?
- O projeto possui padrao de abertura/fechamento de sessao?
- Existe risco de alteracao parcial?
- Se ocorrer erro no meio, tudo deve voltar?
- Ha recursos que precisam ser fechados?
- Ha updates dentro de loop?
- Ha integracao externa no meio da transacao?
- Ha log suficiente para diagnostico sem expor dado sensivel?

## No Repositorio Atual

Confirmado:

- Os scripts PL/SQL atuais nao fazem `COMMIT`/`ROLLBACK` explicito.
- `ACTION_NOTIF_COMP_DEV` insere em `AD_LOGCOMPDEV` e `TSIAVI` e atualiza `TGFNUM`.
- `ACTION_ATUALIZA_LOG_COMP_DEV` atualiza `AD_LOGCOMPDEV` com base em `TSIRAV` e `TGFFIN`.

A confirmar:

- Contexto de execucao das procedures no Sankhya.
- Se ha controle transacional externo pelo job/action.
- Concorrencia de numeracao de `AD_LOGCOMPDEV.NULOG` e `TSIAVI.NUAVISO`.
