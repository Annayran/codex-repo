# Candidatos locais — Sprint 1

> **NÃO EXECUTAR NO ORACLE.** Estes arquivos são propostas para revisão e homologação.

## Objetivo

1. Centralizar as duas interpretações atuais de `AD_FILARESERVA`.
2. Fazer a trigger criar reserva somente para `TIPMOV IN ('P','J')`, igual à view ativa.
3. Permitir que a exclusão alcance a limpeza de `AD_TGFSEQENT`.
4. Evitar a chamada duplicada da validação de faturamento em reduções de `QTDENTREGUE`.

## Comportamentos preservados

- `AD_FILARESERVA = 'S'` continua obrigatório.
- A view continua considerando apenas `P/J`.
- A janela foi confirmada como 120 dias úteis, igual para nacional e importado; a implementação permanece em pacote separado até validar o calendário empresarial.
- `STP_GRAVAFILA_PRA` e `STP_VALIDAFAT_FILARES_PRA` não foram alteradas neste pacote.

## Gates antes do deploy

- Executar a matriz de testes das 72 TOPs.
- Homologar inclusão, alteração, exclusão, faturamento e estorno.
- Tratar as transações autônomas nas rotinas consumidoras.
- Compilar em homologação e consultar `ALL_ERRORS`.

## Validação de inventário

`STP_VALINVFILRES_PRA` não será ampliada neste pacote. O documento
`ZY-PROA-2026-VALFIL-001` determina manter o comportamento atual, sem nova
separação por empresa, local, lote, série ou controle e sem nova alçada. A
consulta `database/tests/04_preview_inventory_validation.sql` permanece somente
como evidência diagnóstica; não constitui requisito de alteração.
