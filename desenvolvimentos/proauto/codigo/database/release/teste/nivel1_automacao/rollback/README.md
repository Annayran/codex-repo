# Rollback do Nível 1

Executar somente se o Nível 1 falhar.

1. Desabilitar a ação agendada.
2. Restaurar/publicar o JAR anterior no Cadastro de Módulos Java.
3. Executar, nesta ordem:
   - `../../../../baseline/2026-08-13/triggers/TRG_IUD_TGFITE_FILRES_PRA.sql`;
   - `../../../../baseline/2026-08-13/procedures/STP_GRAVAFILA_PRA.sql`;
   - `../../../../baseline/2026-08-13/procedures/STP_ATUALIZA_PROV_FILA_PRA.sql`;
   - `../../../../baseline/2026-08-17/procedures/STP_ATUALIZA_PROV_FILA_BTN.sql`;
   - `../../../../baseline/2026-08-13/procedures/STP_SETSEQPREVEST_PRA.sql`.
4. Depois de restaurar os consumidores, executar:

```sql
DROP PACKAGE PRA_PKG_FILARES;
```

5. Reexecutar `../validation/00_PRE_NIVEL1.sql` e conferir os objetos.

O rollback não remove nem altera dados de `AD_TGFSEQENT`.
