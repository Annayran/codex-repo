# Sprint / Ciclo Atual

## Objetivo Atual

Homologar a execucao da acao agendada Sankhya para enviar registros da `AD_VW_GENESIS_ESTOQUE` para a API Genesis e controlar o resultado na `AD_GENMOVEST`.

## Ja Entregue

- Projeto Gradle Java 8 estruturado.
- DTO completo do contrato de estoque Genesis criado.
- Builder de payload implementado.
- Cliente HTTP Genesis implementado para GET/POST com header `token`.
- Smoke tests Java criados para API Genesis.
- POST real validado com HTTP 201.
- View `AD_VW_GENESIS_ESTOQUE` validada/subida.
- Tabela `AD_GENMOVEST` modelada e usada pelo repository.
- Busca de pendentes ligada na view com `SERIE IS NOT NULL`.
- Controle de status implementado: `PENDENTE`, `ENVIADO`, `ERRO`, `REPROCESSAR`.
- `IntegracaoGenesisScheduleAction` implementa `org.cuckoo.core.ScheduledAction`.
- JAR `MA-HOSPITALAR-GENESIS-1.0-SNAPSHOT.jar` gerado com build/testes passando.

## Tarefas em Andamento

- Aguardando liberacao/configuracao da acao agendada pela Hiaria no sistema da MA Hospitalar.
- Preparar execucao assistida da acao no ambiente do cliente.

## Proximas Acoes

- Configurar parametros seguros de URL/token Genesis no ambiente Sankhya.
- Implementar/ajustar `lerParametros()` conforme o local escolhido para esses parametros.
- Subir o JAR no ambiente da MA Hospitalar.
- Executar a acao agendada manualmente em homologacao/controlado.
- Validar registros criados/atualizados na `AD_GENMOVEST`.
- Conferir retornos HTTP e registros criados na Genesis.
- Decidir se `IDINTGENESIS` deve trocar de `MAX + 1` para sequence Oracle.

## Bloqueios

- Acao agendada ainda depende de liberacao/configuracao pela Hiaria.
- Local definitivo de armazenamento de URL/token ainda precisa ser definido.

## Riscos

- Sem sequence, `IDINTGENESIS = MAX + 1` pode falhar em execucoes concorrentes.
- Se a view retornar muitos registros, a primeira execucao pode gerar grande volume de POSTs.
- Campos opcionais/IDs Genesis podem virar obrigatorios em cenarios nao testados.
- Token expirado ou incorreto gera erro 401 e pode manter registros pendentes/reprocessando.

## Observacoes

Atualizar este arquivo ao fim de cada sessao relevante de homologacao ou ajuste do fluxo Sankhya.