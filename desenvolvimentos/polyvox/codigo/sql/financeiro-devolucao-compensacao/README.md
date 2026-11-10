# Compensacao Financeira de Notas de Devolucao

## Objetivo

Criar apoio tecnico para:

- Exibir pop-up ao responsavel pelo financeiro informando a necessidade de compensar o financeiro da nota de devolucao `YYYY` na nota de origem `XXXX`.
- Registrar em log o usuario que visualizou a mensagem.
- Disponibilizar relatorio com registros pendentes, avisados, visualizados e compensados.

## Premissas Confirmadas

- O ambiente usa Oracle PL/SQL.
- Os exemplos existentes usam `TSIAVI` para criacao de avisos/pop-ups.
- Os exemplos existentes usam `TSIRAV` para verificar visualizacao de aviso.
- Os exemplos controlam numeracao de `TSIAVI` via `TGFNUM`.
- Para teste, o pop-up sera enviado aos usuarios `CODUSU IN (20, 0, 37)`.
- As TOPs consideradas pela regra sao `CODTIPOPER IN (1200, 1201)`.
- A relacao entre nota de devolucao e nota de origem esta confirmada como `TGFVAR.NUNOTA = nota de devolucao` e `TGFVAR.NUNOTAORIG = nota de origem`.
- A compensacao financeira e identificada em `TGFFIN` pelo vinculo direto `TGFFIN.NUNOTA = NUNOTA da nota` e `TGFFIN.NUCOMPENS IS NOT NULL`.
- Nao e necessaria uma action manual para marcar compensacao.

## Pontos a Confirmar

- Se a verificacao de compensacao deve olhar apenas `TGFFIN.NUNOTA = NUNOTA da devolucao` ou tambem considerar a nota de origem para algum cenario especifico.
- Periodicidade da rotina de sincronizacao do log (`ACTION_ATUALIZA_LOG_COMP_DEV`).

## Arquivos

- `001_create_log_table.sql`: cria a tabela de controle/log.
- `002_action_notifica_compensacao_dev.sql`: procedure para gerar aviso ao financeiro.
- `003_atualiza_log_compensacao.sql`: procedure para sincronizar visualizacao por `TSIRAV` e compensacao por `TGFFIN.NUCOMPENS`.
- `004_relatorio_devolucoes_compensadas.sql`: consulta base para relatorio de pendentes, avisados, visualizados e compensados.

## Fluxo Sugerido

```txt
Nota de devolucao TOP 1200/1201 vinculada a nota de origem
  -> Procedure identifica devolucao ainda sem TGFFIN.NUCOMPENS
  -> Insere log
  -> Cria aviso em TSIAVI para CODUSU 20
  -> Usuario visualiza pop-up
  -> Rotina consulta TSIRAV para registrar visualizacao
  -> Rotina consulta TGFFIN.NUCOMPENS para identificar compensacao
  -> Relatorio apresenta pendentes, avisados, visualizados e compensados
```

## Ordem de Aplicacao

1. Executar `001_create_log_table.sql`.
2. Compilar `002_action_notifica_compensacao_dev.sql`.
3. Compilar `003_atualiza_log_compensacao.sql`.
4. Configurar job/action para executar a notificacao.
5. Configurar job/action para executar a sincronizacao de log.
6. Publicar relatorio baseado em `004_relatorio_devolucoes_compensadas.sql`.
