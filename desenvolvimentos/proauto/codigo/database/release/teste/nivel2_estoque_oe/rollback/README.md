# Rollback do Nível 2

O rollback abaixo mantém o Nível 1 instalado.

1. Desabilitar temporariamente a ação agendada.
2. Executar, nesta ordem:
   - `../../../../baseline/2026-08-13/procedures/STP_VALIDAFAT_FILARES_PRA.sql`;
   - `../../../../baseline/2026-08-13/views/AD_VGFRESEST.sql`;
   - `../../../../baseline/2026-08-18/views/AD_VGFPROVEST.sql`;
   - `../../../../baseline/2026-08-18/views/AD_VGFFRPROD.sql`.
3. Executar `../validation/00_PRE_NIVEL2.sql`.
4. Reabilitar a ação agendada apenas se o Nível 1 continuar válido.

Nenhum objeto do Nível 1 deve ser removido neste rollback.

