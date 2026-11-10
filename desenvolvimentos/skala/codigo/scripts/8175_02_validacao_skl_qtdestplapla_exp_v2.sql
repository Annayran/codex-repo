/*
 * Validacao posterior ao deploy da SKL_QTDESTPLAPLA_EXP_V2.
 * Executar em homologacao antes de publicar os campos calculados.
 */

/* 1. A consulta deve retornar STATUS = VALID. */
SELECT
    OBJECT_NAME,
    OBJECT_TYPE,
    STATUS
FROM USER_OBJECTS
WHERE OBJECT_NAME = 'SKL_QTDESTPLAPLA_EXP_V2';

/* 2. A consulta nao deve retornar erros. */
SELECT
    NAME,
    TYPE,
    LINE,
    POSITION,
    TEXT
FROM USER_ERRORS
WHERE NAME = 'SKL_QTDESTPLAPLA_EXP_V2'
ORDER BY
    SEQUENCE;

/* 3. Conferencia das colunas publicadas pela view. */
SELECT
    COLUMN_ID,
    COLUMN_NAME,
    DATA_TYPE,
    DATA_LENGTH,
    DATA_PRECISION,
    DATA_SCALE
FROM USER_TAB_COLUMNS
WHERE TABLE_NAME = 'SKL_QTDESTPLAPLA_EXP_V2'
ORDER BY
    COLUMN_ID;

/*
 * 4. Nao deve retornar duplicidade por item.
 * A SEQUENCIA faz parte da identidade do item da nota.
 */
SELECT
    NUNOTA,
    SEQUENCIA,
    CODPROD,
    CODEMP,
    CODLOCAL,
    COUNT(*) AS QUANTIDADE
FROM SKL_QTDESTPLAPLA_EXP_V2
GROUP BY
    NUNOTA,
    SEQUENCIA,
    CODPROD,
    CODEMP,
    CODLOCAL
HAVING COUNT(*) > 1;

/*
 * 5. Validacao do exemplo fornecido: produto 26957 e local 107000.
 * O resultado representa:
 * estoque da empresa 110 - reservas/bloqueios das empresas 110 e 2.
 */
SELECT
    NVL(
        SUM(
            NVL(
                NVL(
                    CASE
                        WHEN EST.CODEMP = 110 THEN EST.ESTOQUE
                        ELSE 0
                    END,
                    0
                )
                - (
                    NVL(EST.RESERVADO, 0)
                    + NVL(EST.WMSBLOQUEADO, 0)
                ),
                0
            )
        ),
        0
    ) AS ESTDISPVOLPAD_ESPERADO
FROM TGFEST EST
WHERE EST.CODPROD = 26957
  AND EST.CODEMP IN (110, 2)
  AND EST.CODLOCAL = 107000
  AND EST.CODPARC = 0
  AND EST.TIPO = 'P';

/*
 * 6. Produtos pendentes sem uma unica conversao valida para CX.
 * Esses itens terao ESTDISPSEMPED nulo e exigem saneamento cadastral.
 */
SELECT DISTINCT
    ITE.CODPROD,
    COUNT(VOA.CODPROD) AS QUANTIDADE_CONVERSOES_CX
FROM TGFITE ITE
LEFT JOIN TGFVOA VOA
  ON VOA.CODPROD = ITE.CODPROD
 AND VOA.CODVOL = 'CX'
WHERE ITE.PENDENTE = 'S'
GROUP BY
    ITE.CODPROD
HAVING COUNT(VOA.CODPROD) <> 1
    OR MAX(NVL(VOA.QUANTIDADE, 0)) = 0;

