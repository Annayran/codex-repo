-- Execucao manual segura da procedure ZTLW_STP_TOP_SERVICO.
-- Ferramenta: SQL Developer (Executar Script/F5) ou SQLcl.
-- Este script sempre executa ROLLBACK ao final e nao confirma alteracoes.
-- Substitua somente o valor abaixo por uma NUNOTA controlada de homologacao.

SET SERVEROUTPUT ON SIZE UNLIMITED;
SET VERIFY OFF;

DEFINE NUNOTA_TESTE = 0;

PROMPT ============================================================
PROMPT 1. PROCEDURE E PARAMETROS
PROMPT ============================================================

SELECT OBJECT_NAME,
       STATUS,
       LAST_DDL_TIME
  FROM USER_OBJECTS
 WHERE OBJECT_NAME = 'ZTLW_STP_TOP_SERVICO'
   AND OBJECT_TYPE = 'PROCEDURE';

SELECT CHAVE,
       LOGICO,
       INTEIRO,
       TEXTO
  FROM TSIPAR
 WHERE CODUSU = 0
   AND CHAVE IN (
       'ZTLWTOPAT', 'ZTLWTOPAP', 'ZTLWTOPOR', 'ZTLWTOPRE',
       'ZTLWTOPNR', 'ZTLWCSLLS', 'ZTLWCSLLN', 'ZTLWIMAPAR'
   )
 ORDER BY CHAVE;

PROMPT ============================================================
PROMPT 2. CABECALHO ANTES DA EXECUCAO
PROMPT ============================================================

SELECT NUNOTA,
       NUMNOTA,
       CODPARC,
       CODTIPOPER,
       DHTIPOPER,
       NUMCONTRATO,
       CONFIRMADA
  FROM TGFCAB
 WHERE NUNOTA = &NUNOTA_TESTE;

PROMPT ============================================================
PROMPT 3. PARCEIRO E OUTROS IMPOSTOS
PROMPT ============================================================

SELECT CAB.NUNOTA,
       CAB.CODPARC,
       PAR.NOMEPARC,
       NVL(PAR.SIMPLES, '<NULO>') AS SIMPLES,
       (SELECT COUNT(*)
          FROM TGFIMA IMA
         WHERE IMA.CODIGO = CAB.CODPARC
           AND UPPER(TRIM(IMA.TIPO)) = UPPER(TRIM((
               SELECT MAX(TEXTO)
                 FROM TSIPAR
                WHERE CODUSU = 0
                  AND CHAVE = 'ZTLWIMAPAR'
           )))) AS OUTROS_IMPOSTOS
  FROM TGFCAB CAB
  JOIN TGFPAR PAR
    ON PAR.CODPARC = CAB.CODPARC
 WHERE CAB.NUNOTA = &NUNOTA_TESTE;

PROMPT ============================================================
PROMPT 4. ITENS E CATEGORIAS FISCAIS
PROMPT ============================================================

WITH CONFIG AS (
    SELECT TRANSLATE(
               REGEXP_REPLACE(UPPER(TRIM(MAX(CASE
                   WHEN CHAVE = 'ZTLWCSLLS' THEN TEXTO
               END))), '[[:space:]]+', ' '),
               'ÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÕÖÚÙÛÜÇ',
               'AAAAAEEEEIIIIOOOOOUUUUC'
           ) AS LISTA_SIM,
           TRANSLATE(
               REGEXP_REPLACE(UPPER(TRIM(MAX(CASE
                   WHEN CHAVE = 'ZTLWCSLLN' THEN TEXTO
               END))), '[[:space:]]+', ' '),
               'ÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÕÖÚÙÛÜÇ',
               'AAAAAEEEEIIIIOOOOOUUUUC'
           ) AS LISTA_NAO
      FROM TSIPAR
     WHERE CODUSU = 0
       AND CHAVE IN ('ZTLWCSLLS', 'ZTLWCSLLN')
), ITENS AS (
    SELECT ITE.NUNOTA,
           ITE.SEQUENCIA,
           ITE.CODPROD,
           PRO.DESCRPROD,
           PRO.AD_CATFISCAL,
           TRANSLATE(
               REGEXP_REPLACE(UPPER(TRIM(PRO.AD_CATFISCAL)), '[[:space:]]+', ' '),
               'ÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÕÖÚÙÛÜÇ',
               'AAAAAEEEEIIIIOOOOOUUUUC'
           ) AS CATEGORIA_NORMALIZADA
      FROM TGFITE ITE
      JOIN TGFPRO PRO
        ON PRO.CODPROD = ITE.CODPROD
     WHERE ITE.NUNOTA = &NUNOTA_TESTE
)
SELECT I.NUNOTA,
       I.SEQUENCIA,
       I.CODPROD,
       I.DESCRPROD,
       I.AD_CATFISCAL,
       CASE
           WHEN I.CATEGORIA_NORMALIZADA IS NULL THEN 'AUSENTE'
           WHEN INSTR(';' || C.LISTA_SIM || ';',
                      ';' || I.CATEGORIA_NORMALIZADA || ';') > 0
            AND INSTR(';' || C.LISTA_NAO || ';',
                      ';' || I.CATEGORIA_NORMALIZADA || ';') > 0
               THEN 'CONFLITO DE PARAMETRIZACAO'
           WHEN INSTR(';' || C.LISTA_SIM || ';',
                      ';' || I.CATEGORIA_NORMALIZADA || ';') > 0
               THEN 'CSLL SIM'
           WHEN INSTR(';' || C.LISTA_NAO || ';',
                      ';' || I.CATEGORIA_NORMALIZADA || ';') > 0
               THEN 'CSLL NAO'
           ELSE 'NAO PARAMETRIZADA'
       END AS CLASSIFICACAO
  FROM ITENS I
 CROSS JOIN CONFIG C
 ORDER BY I.SEQUENCIA;

PROMPT ============================================================
PROMPT 5. EXECUCAO SOB SAVEPOINT
PROMPT ============================================================

VARIABLE V_SUCESSO VARCHAR2(1);
VARIABLE V_MENSAGEM VARCHAR2(4000);
VARIABLE V_CODUSULIB NUMBER;

SAVEPOINT ANTES_REGRA_TOP_SERVICO;

BEGIN
    ZTLW_STP_TOP_SERVICO(
        P_NUNOTA    => &NUNOTA_TESTE,
        P_SUCESSO   => :V_SUCESSO,
        P_MENSAGEM  => :V_MENSAGEM,
        P_CODUSULIB => :V_CODUSULIB
    );

    DBMS_OUTPUT.PUT_LINE('P_SUCESSO   = ' || NVL(:V_SUCESSO, '<NULO>'));
    DBMS_OUTPUT.PUT_LINE('P_MENSAGEM  = ' || NVL(:V_MENSAGEM, '<NULO>'));
    DBMS_OUTPUT.PUT_LINE('P_CODUSULIB = ' || NVL(TO_CHAR(:V_CODUSULIB), '<NULO>'));
END;
/

PROMPT ============================================================
PROMPT 6. CABECALHO DEPOIS DA PROCEDURE E ANTES DO ROLLBACK
PROMPT ============================================================

SELECT NUNOTA,
       NUMNOTA,
       CODTIPOPER,
       DHTIPOPER,
       NUMCONTRATO,
       CONFIRMADA
  FROM TGFCAB
 WHERE NUNOTA = &NUNOTA_TESTE;

PROMPT ============================================================
PROMPT 7. ROLLBACK OBRIGATORIO DO TESTE
PROMPT ============================================================

ROLLBACK TO ANTES_REGRA_TOP_SERVICO;

SELECT NUNOTA,
       NUMNOTA,
       CODTIPOPER,
       DHTIPOPER,
       NUMCONTRATO,
       CONFIRMADA
  FROM TGFCAB
 WHERE NUNOTA = &NUNOTA_TESTE;

PROMPT TESTE ENCERRADO COM ROLLBACK. NENHUMA ALTERACAO FOI CONFIRMADA.

UNDEFINE NUNOTA_TESTE;
