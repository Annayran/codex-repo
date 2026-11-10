/*
    Validação somente leitura - Botão de geração de Nota de Compra/OS

    Projeto analisado: PortoMag / LancamentoOS
    Banco esperado: Oracle

    IMPORTANTE
    - Este arquivo não contém INSERT, UPDATE, DELETE, MERGE, DDL ou COMMIT.
    - Execute cada bloco separadamente.
    - Substitua :P_NUNOTA, :P_SEQUENCIA, :P_CODPARC, :P_SITEDEF e
      :P_NUOS pelos valores que deseja validar.
    - Não compartilhe resultados que contenham dados pessoais ou configurações
      sensíveis.
*/


/* ========================================================================== */
/* 1. CONFIRMAR SE AS TABELAS UTILIZADAS PELO BOTÃO EXISTEM                   */
/* ========================================================================== */

SELECT OWNER,
       TABLE_NAME
  FROM ALL_TABLES
 WHERE TABLE_NAME IN (
       'TSIPAR',
       'TGFTOP',
       'TGFCAB',
       'TGFITE',
       'AD_APONTAMENTO',
       'AD_TGFTECNICO',
       'AD_SITE'
 )
 ORDER BY TABLE_NAME, OWNER;


/* ========================================================================== */
/* 2. CONFIRMAR OS CAMPOS UTILIZADOS PELO CÓDIGO                              */
/* ========================================================================== */

WITH CAMPOS_ESPERADOS AS (
    SELECT 'TSIPAR' TAB, 'CHAVE' CAMPO FROM DUAL UNION ALL
    SELECT 'TSIPAR', 'TEXTO' FROM DUAL UNION ALL

    SELECT 'TGFTOP', 'CODTIPOPER' FROM DUAL UNION ALL
    SELECT 'TGFTOP', 'DHALTER' FROM DUAL UNION ALL
    SELECT 'TGFTOP', 'TIPMOV' FROM DUAL UNION ALL
    SELECT 'TGFTOP', 'ATUALEST' FROM DUAL UNION ALL
    SELECT 'TGFTOP', 'ATUALESTMP' FROM DUAL UNION ALL
    SELECT 'TGFTOP', 'ADIARATUALEST' FROM DUAL UNION ALL
    SELECT 'TGFTOP', 'AD_MODNOTA' FROM DUAL UNION ALL

    SELECT 'TGFCAB', 'NUNOTA' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'CODEMP' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'CODPARC' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'CODTIPOPER' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'CODTIPVENDA' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'CODNAT' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'CODCENCUS' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'CODVEND' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_DTABREOS' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_DTFECHAOS' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_UF' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_RELFOT' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_NOMETEC' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_CODTECNICO' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_VINCULO' FROM DUAL UNION ALL

    SELECT 'TGFITE', 'NUNOTA' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'SEQUENCIA' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'CODPROD' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'QTDNEG' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'VLRUNIT' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'VLRTOT' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'AD_SITEDEF' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'AD_NOMECID' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'AD_UF' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'AD_CEP' FROM DUAL UNION ALL

    SELECT 'AD_APONTAMENTO', 'NUNOTA' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'SEQUENCIA' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'CODPARC' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'CODTECNICO' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'EMPRESA' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'DTNEG' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'DTABRCRQ' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'DTPROG' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'UF' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'OBSINSTALACAO' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'CODPROD' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'QTDNEG' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'VLRUNIT' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'VLRTOT' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'SITEDEF' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'NUOS' FROM DUAL UNION ALL

    SELECT 'AD_TGFTECNICO', 'CODPARC' FROM DUAL UNION ALL
    SELECT 'AD_TGFTECNICO', 'CODTECNICO' FROM DUAL UNION ALL
    SELECT 'AD_TGFTECNICO', 'NOMETECNICO' FROM DUAL UNION ALL

    SELECT 'AD_SITE', 'SITEDEF' FROM DUAL UNION ALL
    SELECT 'AD_SITE', 'NOMECID' FROM DUAL UNION ALL
    SELECT 'AD_SITE', 'COSPOSTAL' FROM DUAL
),
CAMPOS_EXISTENTES AS (
    SELECT DISTINCT TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH
      FROM ALL_TAB_COLUMNS
     WHERE TABLE_NAME IN (
           'TSIPAR', 'TGFTOP', 'TGFCAB', 'TGFITE',
           'AD_APONTAMENTO', 'AD_TGFTECNICO', 'AD_SITE'
     )
)
SELECT E.TAB AS TABELA,
       E.CAMPO,
       CASE WHEN X.COLUMN_NAME IS NULL THEN 'AUSENTE' ELSE 'OK' END AS STATUS,
       X.DATA_TYPE,
       X.DATA_LENGTH
  FROM CAMPOS_ESPERADOS E
  LEFT JOIN CAMPOS_EXISTENTES X
    ON X.TABLE_NAME = E.TAB
   AND X.COLUMN_NAME = E.CAMPO
 ORDER BY CASE WHEN X.COLUMN_NAME IS NULL THEN 0 ELSE 1 END,
          E.TAB,
          E.CAMPO;


/* Somente os campos ausentes. */
WITH CAMPOS_ESPERADOS AS (
    SELECT 'TGFTOP' TAB, 'AD_MODNOTA' CAMPO FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_DTABREOS' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_DTFECHAOS' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_UF' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_RELFOT' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_NOMETEC' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_CODTECNICO' FROM DUAL UNION ALL
    SELECT 'TGFCAB', 'AD_VINCULO' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'AD_SITEDEF' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'AD_NOMECID' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'AD_UF' FROM DUAL UNION ALL
    SELECT 'TGFITE', 'AD_CEP' FROM DUAL UNION ALL
    SELECT 'AD_APONTAMENTO', 'NUOS' FROM DUAL
)
SELECT E.TAB AS TABELA,
       E.CAMPO AS CAMPO_AUSENTE
  FROM CAMPOS_ESPERADOS E
 WHERE NOT EXISTS (
       SELECT 1
         FROM ALL_TAB_COLUMNS C
        WHERE C.TABLE_NAME = E.TAB
          AND C.COLUMN_NAME = E.CAMPO
 )
 ORDER BY E.TAB, E.CAMPO;


/* ========================================================================== */
/* 3. VALIDAR O PARÂMETRO QUE DEFINE A TOP                                    */
/* ========================================================================== */

/* O código Java usa MGECoreParameter.getParameterAsString(). */
SELECT CHAVE,
       TEXTO
  FROM TSIPAR
 WHERE UPPER(CHAVE) = 'CODTIPOPERMODCA';

/* Esperado: exatamente um registro e um valor numérico no campo TEXTO. */
SELECT CASE
           WHEN COUNT(*) = 0 THEN 'PARÂMETRO AUSENTE'
           WHEN COUNT(*) > 1 THEN 'PARÂMETRO DUPLICADO'
           WHEN MAX(TEXTO) IS NULL THEN 'PARÂMETRO SEM VALOR'
           WHEN NOT REGEXP_LIKE(TRIM(MAX(TEXTO)), '^[0-9]+$') THEN 'VALOR NÃO NUMÉRICO'
           ELSE 'OK'
       END AS STATUS_PARAMETRO,
       COUNT(*) AS QUANTIDADE_REGISTROS,
       MAX(TEXTO) AS VALOR_CONFIGURADO
  FROM TSIPAR
 WHERE UPPER(CHAVE) = 'CODTIPOPERMODCA';


/* ========================================================================== */
/* 4. VALIDAR A TOP E TODAS AS SUAS VERSÕES                                   */
/* ========================================================================== */

/*
   O Java consulta TGFTOP somente por CODTIPOPER e utiliza o primeiro registro
   retornado, sem ordenar por DHALTER. Este SELECT mostra todas as versões para
   detectar AD_MODNOTA divergente.
*/
WITH PARAMETRO AS (
    SELECT TO_NUMBER(TRIM(TEXTO)) AS CODTIPOPER
      FROM TSIPAR
     WHERE UPPER(CHAVE) = 'CODTIPOPERMODCA'
       AND REGEXP_LIKE(TRIM(TEXTO), '^[0-9]+$')
)
SELECT T.CODTIPOPER,
       T.DHALTER,
       T.DESCROPER,
       T.TIPMOV,
       T.ATUALEST,
       T.ATUALESTMP,
       T.ADIARATUALEST,
       T.AD_MODNOTA
  FROM TGFTOP T
  JOIN PARAMETRO P
    ON P.CODTIPOPER = T.CODTIPOPER
 ORDER BY T.DHALTER DESC;

/* Identifica quantidade de versões e quantidade de modelos diferentes. */
WITH PARAMETRO AS (
    SELECT TO_NUMBER(TRIM(TEXTO)) AS CODTIPOPER
      FROM TSIPAR
     WHERE UPPER(CHAVE) = 'CODTIPOPERMODCA'
       AND REGEXP_LIKE(TRIM(TEXTO), '^[0-9]+$')
)
SELECT T.CODTIPOPER,
       COUNT(*) AS QTD_VERSOES_TOP,
       COUNT(DISTINCT T.AD_MODNOTA) AS QTD_MODELOS_DIFERENTES,
       MAX(T.DHALTER) AS VERSAO_MAIS_RECENTE
  FROM TGFTOP T
  JOIN PARAMETRO P
    ON P.CODTIPOPER = T.CODTIPOPER
 GROUP BY T.CODTIPOPER;


/* ========================================================================== */
/* 5. IDENTIFICAR A NOTA-MODELO DA VERSÃO MAIS RECENTE DA TOP                 */
/* ========================================================================== */

WITH PARAMETRO AS (
    SELECT TO_NUMBER(TRIM(TEXTO)) AS CODTIPOPER
      FROM TSIPAR
     WHERE UPPER(CHAVE) = 'CODTIPOPERMODCA'
       AND REGEXP_LIKE(TRIM(TEXTO), '^[0-9]+$')
),
TOP_ATUAL AS (
    SELECT T.*,
           ROW_NUMBER() OVER (
               PARTITION BY T.CODTIPOPER
               ORDER BY T.DHALTER DESC
           ) AS RN
      FROM TGFTOP T
      JOIN PARAMETRO P
        ON P.CODTIPOPER = T.CODTIPOPER
)
SELECT T.CODTIPOPER,
       T.DHALTER AS VERSAO_TOP,
       T.AD_MODNOTA,
       C.NUNOTA AS NUNOTA_MODELO,
       C.NUMNOTA,
       C.CODEMP,
       C.CODPARC,
       C.CODTIPOPER AS TOP_GRAVADA_NO_MODELO,
       C.DHTIPOPER,
       C.CODTIPVENDA,
       C.DHTIPVENDA,
       C.CODNAT,
       C.CODCENCUS,
       C.CODVEND,
       C.DTNEG,
       C.DTMOV,
       C.STATUSNOTA,
       C.TIPMOV
  FROM TOP_ATUAL T
  LEFT JOIN TGFCAB C
    ON REGEXP_LIKE(TRIM(T.AD_MODNOTA), '^[0-9]+$')
   AND C.NUNOTA = TO_NUMBER(TRIM(T.AD_MODNOTA))
 WHERE T.RN = 1;

/*
   Resultado esperado:
   - AD_MODNOTA preenchido e numérico;
   - NUNOTA_MODELO preenchido;
   - revisar principalmente CODEMP, CODTIPVENDA, CODNAT, CODCENCUS e CODVEND.
*/


/* ========================================================================== */
/* 6. VALIDAR OS ITENS EXISTENTES NA NOTA-MODELO                              */
/* ========================================================================== */

WITH PARAMETRO AS (
    SELECT TO_NUMBER(TRIM(TEXTO)) AS CODTIPOPER
      FROM TSIPAR
     WHERE UPPER(CHAVE) = 'CODTIPOPERMODCA'
       AND REGEXP_LIKE(TRIM(TEXTO), '^[0-9]+$')
),
TOP_ATUAL AS (
    SELECT T.*,
           ROW_NUMBER() OVER (
               PARTITION BY T.CODTIPOPER
               ORDER BY T.DHALTER DESC
           ) AS RN
      FROM TGFTOP T
      JOIN PARAMETRO P
        ON P.CODTIPOPER = T.CODTIPOPER
)
SELECT I.NUNOTA,
       I.SEQUENCIA,
       I.CODPROD,
       I.CODVOL,
       I.QTDNEG,
       I.VLRUNIT,
       I.VLRTOT,
       I.CODLOCALORIG,
       I.ATUALESTOQUE,
       I.RESERVA
  FROM TOP_ATUAL T
  JOIN TGFITE I
    ON REGEXP_LIKE(TRIM(T.AD_MODNOTA), '^[0-9]+$')
   AND I.NUNOTA = TO_NUMBER(TRIM(T.AD_MODNOTA))
 WHERE T.RN = 1
 ORDER BY I.SEQUENCIA;


/* ========================================================================== */
/* 7. VALIDAR UM APONTAMENTO ESPECÍFICO                                       */
/* ========================================================================== */

SELECT A.NUNOTA,
       A.SEQUENCIA,
       A.NUOS,
       A.EMPRESA,
       A.CODPARC,
       A.CODTECNICO,
       A.DTNEG,
       A.DTABRCRQ,
       A.DTPROG,
       A.UF,
       A.CODPROD,
       A.QTDNEG,
       A.VLRUNIT,
       A.VLRTOT,
       A.SITEDEF,
       CASE
           WHEN A.NUOS IS NOT NULL THEN 'JÁ POSSUI NOTA/OS VINCULADA'
           WHEN A.EMPRESA IS NULL THEN 'EMPRESA AUSENTE'
           WHEN A.CODPARC IS NULL THEN 'PARCEIRO AUSENTE'
           WHEN A.CODPROD IS NULL THEN 'PRODUTO AUSENTE'
           WHEN A.QTDNEG IS NULL OR A.QTDNEG <= 0 THEN 'QUANTIDADE INVÁLIDA'
           WHEN A.SITEDEF IS NULL THEN 'SITE AUSENTE'
           ELSE 'DADOS MÍNIMOS OK'
       END AS STATUS_VALIDACAO
  FROM AD_APONTAMENTO A
 WHERE A.NUNOTA = :P_NUNOTA
   AND A.SEQUENCIA = :P_SEQUENCIA;


/* ========================================================================== */
/* 8. VALIDAR O VÍNCULO DO TÉCNICO/PARCEIRO                                   */
/* ========================================================================== */

SELECT T.CODPARC,
       T.CODTECNICO,
       T.NOMETECNICO
  FROM AD_TGFTECNICO T
 WHERE T.CODPARC = :P_CODPARC;

/* Detecta mais de um vínculo para o mesmo parceiro. */
SELECT T.CODPARC,
       COUNT(*) AS QUANTIDADE_VINCULOS
  FROM AD_TGFTECNICO T
 WHERE T.CODPARC = :P_CODPARC
 GROUP BY T.CODPARC
HAVING COUNT(*) > 1;


/* ========================================================================== */
/* 9. VALIDAR O SITE, CIDADE E CEP                                             */
/* ========================================================================== */

SELECT S.SITEDEF,
       S.NOMECID,
       S.COSPOSTAL
  FROM AD_SITE S
 WHERE S.SITEDEF = :P_SITEDEF;


/* ========================================================================== */
/* 10. VALIDAR UMA NOTA/OS JÁ GERADA                                          */
/* ========================================================================== */

SELECT C.NUNOTA,
       C.NUMNOTA,
       C.CODEMP,
       C.CODPARC,
       C.CODTIPOPER,
       C.DHTIPOPER,
       C.CODTIPVENDA,
       C.CODNAT,
       C.CODCENCUS,
       C.CODVEND,
       C.DTNEG,
       C.DTMOV,
       C.STATUSNOTA,
       C.TIPMOV,
       C.AD_DTABREOS,
       C.AD_DTFECHAOS,
       C.AD_UF,
       C.AD_RELFOT,
       C.AD_NOMETEC,
       C.AD_CODTECNICO,
       C.AD_VINCULO
  FROM TGFCAB C
 WHERE C.NUNOTA = :P_NUOS;

SELECT I.NUNOTA,
       I.SEQUENCIA,
       I.CODPROD,
       I.QTDNEG,
       I.VLRUNIT,
       I.VLRTOT,
       I.CODVOL,
       I.CODLOCALORIG,
       I.ATUALESTOQUE,
       I.RESERVA,
       I.AD_SITEDEF,
       I.AD_NOMECID,
       I.AD_UF,
       I.AD_CEP
  FROM TGFITE I
 WHERE I.NUNOTA = :P_NUOS
 ORDER BY I.SEQUENCIA;


/* ========================================================================== */
/* 11. LOCALIZAR APONTAMENTOS QUE REFERENCIAM A NOTA/OS GERADA                */
/* ========================================================================== */

SELECT A.NUNOTA AS NUNOTA_ORIGEM,
       A.SEQUENCIA,
       A.NUOS,
       A.CODPARC,
       A.CODTECNICO,
       A.CODPROD,
       A.QTDNEG,
       A.VLRTOT
  FROM AD_APONTAMENTO A
 WHERE A.NUOS = :P_NUOS
 ORDER BY A.NUNOTA, A.SEQUENCIA;


/* ========================================================================== */
/* 12. PROCURAR RISCOS DE DUPLICIDADE E INCONSISTÊNCIA                        */
/* ========================================================================== */

/* Uma mesma NUOS ligada a mais de um documento de origem. */
SELECT A.NUOS,
       COUNT(DISTINCT A.NUNOTA) AS QTD_ORIGENS,
       COUNT(*) AS QTD_APONTAMENTOS
  FROM AD_APONTAMENTO A
 WHERE A.NUOS IS NOT NULL
 GROUP BY A.NUOS
HAVING COUNT(DISTINCT A.NUNOTA) > 1
    OR COUNT(*) > 1
 ORDER BY QTD_APONTAMENTOS DESC;

/* Apontamentos que referenciam uma NUOS inexistente em TGFCAB. */
SELECT A.NUNOTA AS NUNOTA_ORIGEM,
       A.SEQUENCIA,
       A.NUOS
  FROM AD_APONTAMENTO A
  LEFT JOIN TGFCAB C
    ON C.NUNOTA = A.NUOS
 WHERE A.NUOS IS NOT NULL
   AND C.NUNOTA IS NULL
 ORDER BY A.NUNOTA, A.SEQUENCIA;

/* Divergência entre a empresa do apontamento e a empresa da nota gerada. */
SELECT A.NUNOTA AS NUNOTA_ORIGEM,
       A.SEQUENCIA,
       A.NUOS,
       A.EMPRESA AS EMPRESA_APONTAMENTO,
       C.CODEMP AS EMPRESA_NOTA
  FROM AD_APONTAMENTO A
  JOIN TGFCAB C
    ON C.NUNOTA = A.NUOS
 WHERE NVL(A.EMPRESA, -1) <> NVL(C.CODEMP, -1)
 ORDER BY A.NUNOTA, A.SEQUENCIA;


/* ========================================================================== */
/* 13. LOCALIZAR CONFIGURAÇÕES RELACIONADAS A SMTP SEM EXPOR VALORES          */
/* ========================================================================== */

/*
   Este bloco mostra somente nomes/descrições de parâmetros. Não consulte nem
   compartilhe senhas, tokens, usuários ou outros valores sensíveis.
*/
SELECT CHAVE,
       DESCRICAO
  FROM TSIPAR
 WHERE UPPER(CHAVE) LIKE '%SMTP%'
    OR UPPER(CHAVE) LIKE '%EMAIL%'
    OR UPPER(DESCRICAO) LIKE '%SMTP%'
 ORDER BY CHAVE;


/* ========================================================================== */
/* 14. RESUMO FINAL DO FLUXO CONFIGURADO                                      */
/* ========================================================================== */

WITH PARAMETRO AS (
    SELECT TO_NUMBER(TRIM(TEXTO)) AS CODTIPOPER
      FROM TSIPAR
     WHERE UPPER(CHAVE) = 'CODTIPOPERMODCA'
       AND REGEXP_LIKE(TRIM(TEXTO), '^[0-9]+$')
),
TOP_ATUAL AS (
    SELECT T.*,
           ROW_NUMBER() OVER (
               PARTITION BY T.CODTIPOPER
               ORDER BY T.DHALTER DESC
           ) AS RN
      FROM TGFTOP T
      JOIN PARAMETRO P
        ON P.CODTIPOPER = T.CODTIPOPER
)
SELECT P.CODTIPOPER AS TOP_PARAMETRIZADA,
       T.DHALTER AS VERSAO_TOP,
       T.TIPMOV,
       T.AD_MODNOTA,
       CASE
           WHEN T.CODTIPOPER IS NULL THEN 'TOP NÃO ENCONTRADA'
           WHEN T.AD_MODNOTA IS NULL THEN 'MODELO NÃO CONFIGURADO NA TOP'
           WHEN NOT REGEXP_LIKE(TRIM(T.AD_MODNOTA), '^[0-9]+$') THEN 'MODELO NÃO NUMÉRICO'
           WHEN C.NUNOTA IS NULL THEN 'NOTA-MODELO NÃO ENCONTRADA'
           ELSE 'CONFIGURAÇÃO BÁSICA LOCALIZADA'
       END AS STATUS,
       C.NUNOTA AS NUNOTA_MODELO,
       C.CODEMP AS EMPRESA_MODELO,
       C.CODTIPVENDA,
       C.CODNAT,
       C.CODCENCUS,
       C.CODVEND,
       C.STATUSNOTA AS STATUS_MODELO
  FROM PARAMETRO P
  LEFT JOIN TOP_ATUAL T
    ON T.CODTIPOPER = P.CODTIPOPER
   AND T.RN = 1
  LEFT JOIN TGFCAB C
    ON REGEXP_LIKE(TRIM(T.AD_MODNOTA), '^[0-9]+$')
   AND C.NUNOTA = TO_NUMBER(TRIM(T.AD_MODNOTA));
