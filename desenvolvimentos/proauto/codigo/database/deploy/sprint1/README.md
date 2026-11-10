# Deploy Sprint 1 — bloqueado por gates técnicos

Ainda não existe DDL liberado para execução.

## Motivo

- Validar como o calendário empresarial representa os 120 dias úteis na versão 4.36.
- Verificar o saneamento dos 135 itens ativos sem previsão real.
- Corrigir a concorrência de `SEQENT` e as transações autônomas.
- Homologar inclusão, provisão, troca manual, faturamento, estorno e rollback.

A resposta do cliente confirmou que a prioridade automática usa a data prometida e que a decisão manual permanece gravada em `SEQENT`. Portanto, `DTPREVENT` deve orientar a posição automática de entrada/reentrada, enquanto a view preserva a ordem final de `SEQENT`. Publicar uma view que reordene dinamicamente tudo por data quebraria a ação manual existente.

Quando os gates forem fechados, cada objeto deste diretório deverá possuir:

1. Pré-condições;
2. DDL/DML controlado;
3. Recompilação explícita quando necessária;
4. Consulta em `ALL_ERRORS`;
5. Smoke test;
6. Rollback correspondente.
