-- 1. Distribuicao final. TIPO e PODER nao podem ser derivados do CNPJ.
SELECT
    TIPO,
    PODER,
    TIPO_ESTOQUE,
    COUNT(*) AS TOTAL
FROM AD_VW_GENESIS_ESTOQUE
GROUP BY TIPO, PODER, TIPO_ESTOQUE
ORDER BY TIPO, PODER, TIPO_ESTOQUE

-- 2. Distribuicao dos equipamentos elegiveis por tipo de estoque.
SELECT
    TIPO_ESTOQUE,
    COUNT(*) AS TOTAL
FROM AD_VW_GENESIS_ESTOQUE
GROUP BY TIPO_ESTOQUE
ORDER BY TIPO_ESTOQUE

-- 3. Validar que a classificacao por CNPJ afeta somente TIPO_ESTOQUE.
WITH VALIDACAO AS (
    SELECT
        V.NUNOTA,
        V.SERIE,
        V.TIPO,
        V.PODER,
        V.TIPO_ESTOQUE,
        CASE
            WHEN V.CNPJ_CPF_PARCEIRO IS NULL THEN 'NAO_CLASSIFICADO'
            WHEN EXISTS (
                SELECT 1
                  FROM TSIEMP E
                 WHERE LPAD(
                           REGEXP_REPLACE(TO_CHAR(E.CGC), '[^0-9]', ''),
                           14,
                           '0'
                       ) =
                       LPAD(
                           REGEXP_REPLACE(
                               TO_CHAR(V.CNPJ_CPF_PARCEIRO),
                               '[^0-9]',
                               ''
                           ),
                           14,
                           '0'
                       )
            ) THEN 'PROPRIO'
            ELSE 'TERCEIRO'
        END AS TIPO_ESPERADO
    FROM AD_VW_GENESIS_ESTOQUE V
)
SELECT
    TIPO_ESTOQUE AS TIPO_ATUAL,
    TIPO_ESPERADO,
    COUNT(*) AS TOTAL
FROM VALIDACAO
GROUP BY TIPO_ESTOQUE, TIPO_ESPERADO
ORDER BY TIPO_ESTOQUE, TIPO_ESPERADO

-- 4. A consulta deve retornar zero divergencias.
WITH VALIDACAO AS (
    SELECT
        V.*,
        CASE
            WHEN V.CNPJ_CPF_PARCEIRO IS NULL THEN 'NAO_CLASSIFICADO'
            WHEN EXISTS (
                SELECT 1
                  FROM TSIEMP E
                 WHERE LPAD(
                           REGEXP_REPLACE(TO_CHAR(E.CGC), '[^0-9]', ''),
                           14,
                           '0'
                       ) =
                       LPAD(
                           REGEXP_REPLACE(
                               TO_CHAR(V.CNPJ_CPF_PARCEIRO),
                               '[^0-9]',
                               ''
                           ),
                           14,
                           '0'
                       )
            ) THEN 'PROPRIO'
            ELSE 'TERCEIRO'
        END AS TIPO_ESPERADO
    FROM AD_VW_GENESIS_ESTOQUE V
)
SELECT COUNT(*) AS TOTAL_DIVERGENCIAS
FROM VALIDACAO
WHERE NVL(TIPO_ESTOQUE, '#') <> NVL(TIPO_ESPERADO, '#')

-- 5. Depois do deploy, deve retornar zero: um movimento nao pode ser
-- multiplicado pelos joins auxiliares da view.
SELECT
    CHAVE_MOVIMENTO,
    COUNT(*) AS QUANTIDADE
FROM AD_VW_GENESIS_ESTOQUE
GROUP BY CHAVE_MOVIMENTO
HAVING COUNT(*) > 1
ORDER BY QUANTIDADE DESC

-- 6. Conferir a classificacao dos produtos que seriam avaliados sem a trava M1.
SELECT
    NVL(UPPER(COALESCE(TRIM(PRO.HSP_TIPOPRODUTO), TRIM(PRO.AD_TIPOPRODUTO))), 'SEM_TIPO') AS TIPO_PRODUTO,
    COUNT(*) AS TOTAL
FROM TGFCAB CAB
JOIN TGFITE ITE ON ITE.NUNOTA = CAB.NUNOTA
JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD
JOIN TGFSER SER
  ON SER.NUNOTA = ITE.NUNOTA
 AND SER.CODPROD = ITE.CODPROD
 AND SER.SEQUENCIA = ITE.SEQUENCIA
WHERE SER.SERIE IS NOT NULL
  AND EXISTS (
      SELECT 1
        FROM AD_CENTPARAMTOP CPT
       WHERE CPT.NUPAR = 1
         AND CPT.CODTIPOPER = CAB.CODTIPOPER
  )
GROUP BY NVL(UPPER(COALESCE(TRIM(PRO.HSP_TIPOPRODUTO), TRIM(PRO.AD_TIPOPRODUTO))), 'SEM_TIPO')
ORDER BY TIPO_PRODUTO

-- 7. Depois do deploy, deve retornar zero: a view nao pode conter tipo diferente de M1.
SELECT COUNT(*) AS TOTAL_TIPOS_INDEVIDOS
FROM AD_VW_GENESIS_ESTOQUE V
JOIN TGFPRO PRO ON PRO.CODPROD = V.CODPROD
WHERE NVL(UPPER(COALESCE(TRIM(PRO.HSP_TIPOPRODUTO), TRIM(PRO.AD_TIPOPRODUTO))), '#') <> 'M1'

-- 8. O consumivel CODPROD=13331 nao pode mais aparecer na view.
SELECT COUNT(*) AS TOTAL_CODPROD_13331
FROM AD_VW_GENESIS_ESTOQUE
WHERE CODPROD = 13331

-- 9. A view deve conter somente registros efetivamente elegiveis por serie.
-- O esperado e zero nas duas colunas.
SELECT
    NVL(SUM(CASE WHEN SERIE IS NULL THEN 1 ELSE 0 END), 0) AS SEM_SERIE,
    NVL(SUM(CASE WHEN CHAVE_MOVIMENTO IS NULL THEN 1 ELSE 0 END), 0) AS SEM_CHAVE
FROM AD_VW_GENESIS_ESTOQUE

-- 10. TOTAL_VIEW e CHAVES_UNICAS devem ser iguais. Na amostra de 25/09/2026,
-- o resultado esperado depois da correcao e 14 / 14.
SELECT
    COUNT(*) AS TOTAL_VIEW,
    COUNT(DISTINCT CHAVE_MOVIMENTO) AS CHAVES_UNICAS
FROM AD_VW_GENESIS_ESTOQUE
