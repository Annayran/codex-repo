-- Rollback da Regra TOP de Servico.
-- Antes de executar, desative/desvincule a regra no cadastro Sankhya.
-- Se ja existia uma versao anterior desta procedure, restaure-a a partir do backup.

DROP PROCEDURE ZTLW_STP_TOP_SERVICO;

-- Os parametros ZTLW* nao sao removidos automaticamente.
-- A exclusao deve ser avaliada e feita pelo cadastro administrativo aprovado.
-- Nao alterar notas processadas por DML de rollback.
