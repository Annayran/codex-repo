# Rollback — Sprint 1

Executar somente após aprovação e backup do ambiente.

Ordem recomendada:

1. executar `02_restore_TRG_IUD_TGFITE_FILRES_PRA.sql`;
2. validar a compilação e o comportamento da trigger restaurada;
3. executar `01_drop_PRA_PKG_FILARES.sql`;
4. consultar `USER_ERRORS` e confirmar que não restaram dependências inválidas.

O rollback restaura apenas os objetos alterados por este pacote. Não corrige nem
remove dados que eventualmente tenham sido gerados durante um teste. Qualquer
tratamento de dados deve ser analisado separadamente e não deve executar DML em
tabelas padrão Sankhya sem autorização explícita.
