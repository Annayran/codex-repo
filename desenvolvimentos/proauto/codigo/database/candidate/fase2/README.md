# Fila de Reserva Fase 2 - pacote candidato

> Não executar diretamente em produção. Publicação somente após coleta, revisão,
> backup restaurável e homologação no schema TESTE.

## Publicação em dois níveis

Não executar esta pasta inteira. Seguir os roteiros independentes:

1. `database/release/teste/nivel1_automacao/README.md`;
2. `database/release/teste/nivel2_estoque_oe/README.md`.

Os arquivos `deploy/00_PRA_FILARES_LOCK.sql` e
`deploy/02_PRA_FNC_ESTOQUE_FUTURO.sql` foram descontinuados e contêm somente
avisos. Nenhuma tabela, índice, view auxiliar ou função auxiliar será criada.

Após o deploy, executar `deploy/09_SANEAR_SEM_PREVISAO.sql` somente como
auditoria pós-Fase 1; ele não altera dados.

## Comportamento implementado

- janela confirmada em 120 dias úteis,
  nacional e importado, pelo calendário `TSIFER` e localização da empresa;
- `DTPREVENT` define a posição automática de entrada/reentrada;
- `SEQENT` continua sendo a ordem final e preserva a decisão manual;
- bloqueio transacional sobre os registros existentes da fila;
- botão e scheduler compartilham a procedure existente
  `STP_ATUALIZA_PROV_FILA_PRA`;
- compras `TIPMOV = 'C'`, confirmadas e pendentes, formam estoque futuro;
- `AD_VGFRESEST` e `AD_VGFPROVEST` incorporam o estoque futuro sem alterar
  seus contratos de colunas;
- faturamento valida toda a quantidade antes do DML;
- estorno devolve somente o delta entregue;
- nenhuma rotina candidata executa `COMMIT` ou transação autônoma; a ação manual
  mantém apenas os `ROLLBACK TO SAVEPOINT` já existentes para colisões tratadas;
- auditoria somente leitura dos registros sem previsão; o documento atribui o
  saneamento à Fase 1 (Q13), mas a exportação atual diverge dessa declaração e
  por isso nenhum DML de saneamento foi incluído no deploy.

## Coleta técnica concluída

Os DDLs de `AD_VGFRESEST`, `AD_VGFPROVEST` e `AD_VGFFRPROD`, a estrutura da
`TSIFER`, os limites de `SEQENT` e as dependências das views foram recebidos.
As fontes originais estão preservadas em `database/baseline`.

Antes do deploy ainda é obrigatório executar a validação consolidada para
confirmar os registros sem previsão, fragmentos mistos, TOPs de OE e saldos
projetados no estado atual do schema TESTE.

- `validation/06_VALIDAR_EVIDENCIAS_RECEBIDAS.sql`: confirma os totais sem o
  limite de exportação;
- `validation/07_COLETA_FLUXO_OE.sql`: comprova como a OE atualiza o pedido de
  origem e em qual objeto a validação é efetivamente chamada.

Para a regressão cadastral, execute também
`validation/05_MATRIZ_TESTE_72_TOPS.sql`; o resultado detalhado já informa o
cenário mínimo esperado para cada TOP marcada.

## Riscos que exigem homologação

- feriados municipais, estaduais e recorrentes da empresa 1/Sorocaba;
- exceção legada da TOP 906, preservada no faturamento;
- fragmentos criados pela ação manual;
- posições repetidas já existentes;
- pelo menos 5.000 registros ativos sem `DTPREV`, dos quais 135 apresentaram
  fonte candidata na exportação recebida;
- 15 itens fragmentados com estados mistos de provisão, preservados pelo pacote;
- 23 TOPs atuais de Ordem de Expedição com `AD_FILARESERVA = 'N'`;
- concorrência entre ação 161, botão 195 e scheduler;
- volume e plano de execução das views e da atualização de provisões;
- comportamento de faturamento parcial, estorno e cancelamento.
