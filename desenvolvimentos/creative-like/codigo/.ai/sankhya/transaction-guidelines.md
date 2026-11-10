# Diretrizes de Transacao Sankhya

## Principios

- Nao tratar transacao Sankhya como aplicacao Java comum sem verificar o contexto.
- Verificar se a Action, job ou rotina ja roda dentro de transacao gerenciada.
- Nao fazer commit/rollback manual sem padrao existente.
- Em erro critico, lancar excecao para permitir rollback.
- Nao engolir excecoes.
- Nao deixar alteracao parcial em `TGFCAB`, `TGFITE`, `TGFVAR`, `TGFFIN` ou `TGFEST`.
- Fechar recursos como `NativeSql`, `ResultSet`, `JdbcWrapper` ou equivalentes conforme padrao do projeto.
- Evitar multiplas conexoes/transacoes independentes dentro da mesma regra.
- Evitar updates diretos em documentos confirmados sem regra clara.

## Checklist

- A rotina ja esta dentro de uma transacao do Sankhya?
- O projeto possui padrao de abertura/fechamento de sessao?
- Existe risco de alteracao parcial?
- Se ocorrer erro no meio, tudo deve voltar?
- Ha recursos que precisam ser fechados?
- Ha updates dentro de loop?
- Ha integracao externa no meio da transacao?
- Ha log suficiente para diagnostico sem expor dado sensivel?

## Confirmado neste projeto

- `CancelarDocumentoNegociacaoAction` chamada pelo JSP via `ActionButtonsSP.executeJava` nao deve abrir `JapeSession.open()` nem `execWithTX(...)`.
- Ja ocorreu erro real "Ja existe uma transacao em andamento" quando a Action tentou abrir transacao propria.
- DAO usa `NativeSql.releaseResources(sql)` e fecha `ResultSet` no `finally`.

## A confirmar

- Padrao transacional oficial para outras Actions que sejam chamadas diretamente pelo botao nativo Sankhya.

