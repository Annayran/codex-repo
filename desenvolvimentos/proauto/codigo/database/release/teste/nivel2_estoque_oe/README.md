# Nível 2 — estoque futuro e proteção no faturamento/OE

Pré-requisito: Nível 1 aprovado e estável.

## Antes de começar

1. Manter a ação agendada desabilitada durante o deploy.
2. Não faturar nem usar o botão durante a substituição dos objetos.
3. Exportar o DDL atual de:
   - `STP_VALIDAFAT_FILARES_PRA`;
   - `AD_VGFRESEST`;
   - `AD_VGFPROVEST`;
   - `AD_VGFFRPROD`.
4. Executar `validation/00_PRE_NIVEL2.sql` e guardar o resultado.

## Arquivos para executar, nesta ordem

1. `../../../candidate/fase2/deploy/06_STP_VALIDAFAT_FILARES_PRA.sql`
2. `../../../candidate/fase2/deploy/08_AD_VGFRESEST.sql`
3. `../../../candidate/fase2/deploy/10_AD_VGFPROVEST.sql`
4. `../../../candidate/fase2/deploy/11_AD_VGFFRPROD.sql`
5. Executar `validation/01_POS_NIVEL2.sql`.

Não executar `02_PRA_FNC_ESTOQUE_FUTURO.sql`: o cálculo foi incorporado às
views existentes e nenhum objeto auxiliar é criado.

## Teste controlado

1. Registrar compra confirmada e pendente (`TIPMOV = C`) pela interface.
2. Comparar o saldo das duas views antes/depois.
3. Faturar parcialmente um pedido liberado.
4. Tentar faturar acima do saldo livre e confirmar o bloqueio.
5. Estornar a quantidade parcial e conferir a devolução do delta.
6. Testar a TOP 906 preservada.
7. Executar um caso real de OE e comprovar o vínculo com o pedido de origem.
8. Executar a matriz das 72 TOPs.

## Critério de aprovação

- quatro objetos `VALID`;
- `USER_ERRORS` vazio;
- nenhuma coluna foi removida ou renomeada nas views;
- estoque futuro aparece nas duas filas;
- corte parcial e estorno preservam quantidades;
- OE acima do saldo livre é bloqueada pelo fluxo comprovado.

## Se quebrar

Executar `rollback/README.md`. O rollback do Nível 2 preserva o Nível 1.

