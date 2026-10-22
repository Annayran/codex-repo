-- Artefato de referencia revisado por hash; NAO executar sem deploy/validacao/rollback autorizados.
-- 1. Deve retornar zero antes da criacao do backup.
SELECT COUNT(*) AS BACKUP_EXISTENTE
FROM USER_TABLES
WHERE TABLE_NAME = 'ZYB_GSETLNK_20260910'

-- 2. Execute somente uma vez e valide a quantidade copiada.
CREATE TABLE ZYB_GSETLNK_20260910 AS
SELECT IDREGSET, IDBASE, CODPARC, PADRAO, STATUSCONC, MENSAGEMCONC, DHCONCILIACAO
FROM AD_GENSECTOR
WHERE IDBASE = 1

-- 3. Quantidade do backup deve ser igual ao total de setores da base 1.
SELECT
    (SELECT COUNT(*) FROM AD_GENSECTOR WHERE IDBASE = 1) AS TOTAL_ORIGEM,
    (SELECT COUNT(*) FROM ZYB_GSETLNK_20260910 WHERE IDBASE = 1) AS TOTAL_BACKUP
FROM DUAL

