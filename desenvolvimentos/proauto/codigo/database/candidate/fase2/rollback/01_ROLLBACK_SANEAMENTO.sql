/*
  Nao ha rollback de saneamento na Fase 2.

  O documento ZY-PROA-2026-VALFIL-001, Q13, confirma que o saneamento ocorreu
  na Fase 1, e o arquivo deploy/09_SANEAR_SEM_PREVISAO.sql e somente leitura.
*/

SELECT 'SEM DML DE SANEAMENTO NA FASE 2' AS RESULTADO
  FROM DUAL;
