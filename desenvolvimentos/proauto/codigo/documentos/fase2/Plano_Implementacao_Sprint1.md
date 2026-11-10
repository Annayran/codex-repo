# Plano de implementação — Sprint 1

## Objetivo

Entregar ordenação por `DTPREVENT`, provisão parametrizada e coexistência segura da troca manual, preservando idempotência e rollback.

## Objetos impactados

| Objeto | Mudança pretendida | Gate |
|---|---|---|
| `AD_VGFRESEST` | Manter `SEQENT` como ordem final e usar a mesma chave no cálculo de posição e saldo | Homologação da ordenação |
| `STP_GRAVAFILA_PRA` | Definir a posição automática de entrada por `DTPREVENT`, validar parâmetros, tratar fragmentos e eliminar commit parcial | Configuração transacional e testes |
| `STP_ATUALIZA_PROV_FILA_PRA` | Aplicar 120 dias úteis, ativar provisões em lote e posicioná-las por `DTPREVENT` | Calendário empresarial e concorrência |
| `STP_SETSEQPREVEST_PRA` | Preservar a decisão manual já persistida em `SEQENT` e não copiar previsão entre pedidos | Regressão da ação 161 |
| `TRG_IUD_TGFITE_FILRES_PRA` | Corrigir ramos de insert/update/delete e evitar chamada duplicada | Regressão de TGFITE |
| `STP_VALIDAFAT_FILARES_PRA` | Reconciliar quantidade faturada na transação correta | Regressão de faturamento |

## Algoritmo confirmado para prioridade

A chave automática define a posição de entrada ou reentrada na fila:

1. Data efetiva: `COALESCE(ENT.DTPREV, CAB.DTPREVENT, DATE '2999-12-12')`.
2. Data/hora de confirmação.
3. Sequência legada como desempate de compatibilidade.
4. `NUNOTA`.
5. `SEQUENCIA`.

Depois da entrada automática, a ação manual continua prevalecendo pela alteração de `SEQENT`, conforme resposta do cliente. A view não deve recalcular dinamicamente toda a fila somente por `DTPREVENT`, pois isso apagaria a decisão do usuário. `ORDEM` e `SALDO` devem reutilizar a ordenação final por `SEQENT`, com desempates determinísticos.

## Estratégia de transação

- Não executar `COMMIT` dentro de trigger/evento sem contrato comprovado.
- Confirmar se a ação usa controle manual de transação.
- Fazer falhas propagarem para permitir rollback.
- Testar inclusão, atualização, exclusão, faturamento e estorno em uma única transação funcional.

## Estratégia de concorrência

`MAX(SEQENT) + 10` deve ser substituído por uma atribuição serializada por produto. A solução candidata deve bloquear o produto durante a inclusão/reentrada, preservar a ordem relativa já definida manualmente e recalcular espaços de sequência quando não houver intervalo disponível.

## Limite desta entrega local

As regras funcionais de prioridade e provisão foram fechadas no documento `ZY-PROA-2026-VALFIL-001`. O DDL final permanece condicionado somente à validação técnica do calendário empresarial, ao saneamento dos dados legados e aos testes de concorrência, rollback e regressão da ação manual.
