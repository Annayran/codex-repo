/*
===============================================================================
Objeto       : ZAY_VW_COM_CHEQ_DEV_TESTE
Finalidade   : Diagnosticar e simular comissão para recebimentos originados
               pela TOP 1712 - Cheque Devolvido.
Owner        : esquema de customizações da Rio Grande (confirmar antes de criar)
Dependências : TGFFIN, TGFTOP, TGFPAR, TGFVEN e VGFCOM_FECH
Impacto      : somente leitura; não altera objetos ou dados padrão Sankhya.

IMPORTANTE
-------------------------------------------------------------------------------
1. Esta view NÃO substitui a VGFCOM_FECH e NÃO cria registros na TGFCOM.
2. O valor de comissão é uma simulação percentual para validação funcional.
3. A taxa é aceita somente quando a VGFCOM_FECH possui exatamente uma taxa e
   uma fórmula para a mesma empresa, vendedor, parceiro e número de nota.
4. Se não houver referência segura, a comissão permanece nula e o motivo é
   informado em STATUS_CALCULO.
===============================================================================
*/

/*
Pré-validação: deve retornar zero antes da primeira criação.

SELECT OWNER, OBJECT_NAME, OBJECT_TYPE, STATUS
FROM ALL_OBJECTS
WHERE OBJECT_NAME = 'ZAY_VW_COM_CHEQ_DEV_TESTE';
*/

CREATE OR REPLACE VIEW ZAY_VW_COM_CHEQ_DEV_TESTE AS
WITH
TOP_ATUAL AS (
    SELECT
        TOP.CODTIPOPER,
        MAX(TOP.DESCROPER)
            KEEP (DENSE_RANK LAST ORDER BY TOP.DHALTER) AS DESCROPER
    FROM TGFTOP TOP
    GROUP BY
        TOP.CODTIPOPER
),

REFERENCIA_COMISSAO AS (
    SELECT
        VCOM.CODEMP,
        VCOM.CODVEND,
        VCOM.CODPARC,
        VCOM.NUMNOTA,

        MIN(VCOM.PERCCOM) AS MENOR_PERCCOM,
        MAX(VCOM.PERCCOM) AS MAIOR_PERCCOM,
        COUNT(DISTINCT VCOM.PERCCOM) AS QTD_TAXAS,

        MIN(VCOM.CODFORM) AS MENOR_CODFORM,
        MAX(VCOM.CODFORM) AS MAIOR_CODFORM,
        COUNT(DISTINCT VCOM.CODFORM) AS QTD_FORMULAS

    FROM VGFCOM_FECH VCOM

    WHERE NVL(VCOM.PERCCOM, 0) <> 0

    GROUP BY
        VCOM.CODEMP,
        VCOM.CODVEND,
        VCOM.CODPARC,
        VCOM.NUMNOTA
)

SELECT
    FIN.CODEMP,
    FIN.NUFIN,
    FIN.NUNOTA,
    FIN.NUMNOTA,
    FIN.CODPARC,
    PAR.NOMEPARC,
    FIN.CODVEND,
    VEN.APELIDO,

    FIN.CODTIPOPER AS TOP_TITULO,
    TOP_TITULO.DESCROPER AS DESC_TOP_TITULO,
    FIN.CODTIPOPERBAIXA AS TOP_BAIXA,
    TOP_BAIXA.DESCROPER AS DESC_TOP_BAIXA,

    FIN.DTNEG,
    FIN.DTVENC,
    FIN.DHBAIXA,
    FIN.DESDOBRAMENTO,
    FIN.HISTORICO,
    FIN.RECDESP,
    FIN.VLRDESDOB,
    FIN.VLRBAIXA,

    CASE
        WHEN REF.QTD_TAXAS = 1
            THEN REF.MAIOR_PERCCOM
        ELSE NULL
    END AS PERCCOM_REFERENCIA,

    CASE
        WHEN REF.QTD_FORMULAS = 1
            THEN REF.MAIOR_CODFORM
        ELSE NULL
    END AS CODFORM_REFERENCIA,

    CASE
        WHEN REF.QTD_TAXAS = 1
         AND REF.QTD_FORMULAS = 1
            THEN ROUND(
                     NVL(FIN.VLRBAIXA, 0)
                     * REF.MAIOR_PERCCOM
                     / 100,
                     4
                 )
        ELSE NULL
    END AS VLR_COMISSAO_CALCULADO,

    NVL(REF.QTD_TAXAS, 0) AS QTD_TAXAS_REFERENCIA,
    NVL(REF.QTD_FORMULAS, 0) AS QTD_FORMULAS_REFERENCIA,

    CASE
        WHEN REF.CODEMP IS NULL
            THEN 'SEM REFERENCIA NA VGFCOM_FECH'
        WHEN REF.QTD_TAXAS = 0
            THEN 'SEM TAXA DE COMISSAO'
        WHEN REF.QTD_TAXAS > 1
            THEN 'MULTIPLAS TAXAS PARA A NOTA'
        WHEN REF.QTD_FORMULAS = 0
            THEN 'SEM FORMULA DE REFERENCIA'
        WHEN REF.QTD_FORMULAS > 1
            THEN 'MULTIPLAS FORMULAS PARA A NOTA'
        ELSE 'CALCULO PERCENTUAL PARA TESTE'
    END AS STATUS_CALCULO

FROM TGFFIN FIN

INNER JOIN TGFPAR PAR
    ON PAR.CODPARC = FIN.CODPARC

LEFT JOIN TGFVEN VEN
    ON VEN.CODVEND = FIN.CODVEND

LEFT JOIN TOP_ATUAL TOP_TITULO
    ON TOP_TITULO.CODTIPOPER = FIN.CODTIPOPER

LEFT JOIN TOP_ATUAL TOP_BAIXA
    ON TOP_BAIXA.CODTIPOPER = FIN.CODTIPOPERBAIXA

LEFT JOIN REFERENCIA_COMISSAO REF
    ON REF.CODEMP = FIN.CODEMP
   AND REF.CODVEND = FIN.CODVEND
   AND REF.CODPARC = FIN.CODPARC
   AND REF.NUMNOTA = FIN.NUMNOTA

WHERE FIN.CODTIPOPER = 1712
  AND FIN.RECDESP = 1
  AND FIN.DHBAIXA IS NOT NULL
  AND NVL(FIN.VLRBAIXA, 0) <> 0;

/*
===============================================================================
VALIDAÇÃO 1 - títulos informados pelo cliente
===============================================================================
*/

SELECT
    NUFIN,
    NUMNOTA,
    CODVEND,
    APELIDO,
    CODPARC,
    NOMEPARC,
    TOP_TITULO,
    DESC_TOP_TITULO,
    TOP_BAIXA,
    DESC_TOP_BAIXA,
    DHBAIXA,
    VLRBAIXA,
    PERCCOM_REFERENCIA,
    CODFORM_REFERENCIA,
    VLR_COMISSAO_CALCULADO,
    STATUS_CALCULO
FROM ZAY_VW_COM_CHEQ_DEV_TESTE
WHERE NUFIN IN (199162, 201717)
ORDER BY NUFIN;

/*
Resultado esperado, caso a nota 36847 tenha uma única taxa/fórmula de referência:
- duas linhas de R$ 10.000,00;
- percentual recuperado da VGFCOM_FECH;
- comissão simulada por título;
- STATUS_CALCULO = 'CALCULO PERCENTUAL PARA TESTE'.

Se STATUS_CALCULO indicar ausência ou multiplicidade, não utilizar o valor para
produção antes de definir a regra correta de vínculo com as notas originais.
*/

/*
===============================================================================
VALIDAÇÃO 2 - totalização por parceiro para o período de setembro/2026
===============================================================================
*/

SELECT
    CODVEND,
    CODPARC,
    NOMEPARC,
    COUNT(*) AS QTD_RECEBIMENTOS,
    SUM(VLRBAIXA) AS TOTAL_VLR_BAIXA,
    SUM(VLR_COMISSAO_CALCULADO) AS TOTAL_COMISSAO_CALCULADA
FROM ZAY_VW_COM_CHEQ_DEV_TESTE
WHERE CODVEND = 3
  AND DHBAIXA >= DATE '2026-09-01'
  AND DHBAIXA <  DATE '2026-10-01'
GROUP BY
    CODVEND,
    CODPARC,
    NOMEPARC
ORDER BY
    NOMEPARC;

/*
===============================================================================
ROLLBACK
===============================================================================

Executar somente se a view de teste precisar ser removida:

DROP VIEW ZAY_VW_COM_CHEQ_DEV_TESTE;
*/
