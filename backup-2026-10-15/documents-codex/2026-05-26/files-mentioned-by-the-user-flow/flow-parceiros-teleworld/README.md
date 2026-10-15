# flow-parceiros-teleworld

Evento programavel e tarefas Java para iniciar automaticamente o Flow de Parceiros quando um novo parceiro for inserido na `TGFPAR`, seguindo o mesmo padrao do flow antigo: o Flow trabalha pela tabela `AD_PARCPEND` e a rotina Java atualiza o cadastro nativo.

## Classes

- `br.com.zaya.teleworld.events.IniciarFlowParceiroEvent`
  - Evento programavel da `TGFPAR`.
  - No `beforeInsert`, marca `Parceiro.AD_REVISADO` como `P` quando o campo vier vazio.
  - No `afterInsert`, inicia o processo pela chave `flow_parceiros`, enviando `CODPARC` e `AD_REVISADO`.
- `br.com.zaya.teleworld.events.PopularParceiroPendenteEvent`
  - Evento Java do Flow, para usar na inicializacao do processo.
  - Cria a linha da tabela/formulario `AD_PARCPEND` com `IDINSTPRN`, `CODPARC` e `AD_REVISADO = P`.
- `br.com.zaya.teleworld.events.AtualizarRevisaoParceiroTask`
  - Tarefa Java do Flow.
  - Le as linhas de `AD_PARCPEND`.
  - Atualiza `TGFPAR.AD_REVISADO` com o valor informado no Flow (`P` ou `N`).

## Configuracao no Sankhya

1. Gerar o modulo Java do projeto.
2. Vincular a classe `br.com.zaya.teleworld.events.IniciarFlowParceiroEvent` como Evento Programavel Java na tabela `TGFPAR`.
3. Marcar a execucao nos momentos `beforeInsert` e `afterInsert`.
4. Ajustar a constante `PROCESS_KEY_PARCEIROS` na classe Java para a chave real do modelo BPMN publicado no SankhyaFlow.
5. No modelo do Flow de Parceiros, usar a tabela/formulario `AD_PARCPEND`.
6. Adicionar `br.com.zaya.teleworld.events.PopularParceiroPendenteEvent` como evento Java de inicializacao do processo.
7. Adicionar `br.com.zaya.teleworld.events.AtualizarRevisaoParceiroTask` na tarefa Java que deve gravar a decisao de revisao no parceiro.

## Campos esperados

Tabela/formulario do Flow: `AD_PARCPEND`

```text
CODPARC
AD_REVISADO
IDINSTPRN
```

Valores usados em `AD_REVISADO`:

```text
P
N
```
