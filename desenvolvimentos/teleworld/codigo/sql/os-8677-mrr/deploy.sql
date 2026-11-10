CREATE OR REPLACE VIEW VW_ZAYA_EVOLUCAO_MRR AS
SELECT
'ALTERACAO' AS TIPO,
CON.CODPROJ,
CON.CODCENCUS,
CON.CODNAT,
CON.AD_CODVEND,
CON.CODEMP,
CON.NUMCONTRATO,
PSC.CODPROD,
PRO.DESCRPROD,
OCC.CODOCOR,
OCO.DESCRICAO,
OCC.DTOCOR,
OCO.AD_CLASSIFICACAO,
CASE 
    WHEN OCO.AD_CONSIDERARDIFERENCA = 'S'  THEN ( PRE2.VALOR - PRE.VALOR)
     
    ELSE PRE.VALOR 
END/CASE 
    WHEN CON.TIPO = 'M' THEN 1
     
    WHEN CON.TIPO = 'B' THEN 2
     
    WHEN CON.TIPO = 'T' THEN 3
     
    WHEN CON.TIPO = 'Q' THEN 4
     
    WHEN CON.TIPO = 'S' THEN 6
     
    WHEN CON.TIPO = 'A' THEN 12
     
    WHEN CON.TIPO = 'L' THEN 1
END AS VLR_CAN,
0 AS VLR
FROM TCSCON CON
JOIN TCSPSC PSC 
        ON CON.NUMCONTRATO = PSC.NUMCONTRATO
JOIN TCSOCC OCC 
            ON CON.NUMCONTRATO = OCC.NUMCONTRATO 
            AND OCC.CODPROD = PSC.CODPROD
JOIN TCSOCO OCO 
                ON OCC.CODOCOR = OCO.CODOCOR
JOIN TGFPRO PRO 
                    ON PSC.CODPROD = PRO.CODPROD
LEFT 
            JOIN
                TCSPRE PRE 
                    ON CON.NUMCONTRATO = PRE.NUMCONTRATO 
                    AND PRE.CODPROD = PSC.CODPROD
LEFT 
            JOIN
                TCSPRE PRE2 
                    ON CON.NUMCONTRATO = PRE2.NUMCONTRATO 
                    AND PRE2.CODPROD = PSC.CODPROD


WHERE CON.RECDESP = 1
AND NVL(OCO.AD_DESCONSIDERARMRR,
                'N') = 'N'
--
                AND NVL(OCO.AD_CONSIDERARDIFERENCA,
                'N') = 'S'
AND OCO.CODOCOR <> 3
AND OCO.CODOCOR <> 14
AND ( OCO.SITPROD IN ( 'B',
                'C' ) 
                OR OCO.AD_CLASSIFICACAO IN ( 1,
                2,
                3,
                4,
                5,
                6,
                7,
                8 ,
                14 ))
AND PRE.REFERENCIA IN (   SELECT
                    MAX(P1.REFERENCIA)
                          
                FROM
                    TCSPRE P1
                          
                INNER JOIN
                    TCSOCC OC1 
                        ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                          
                        AND OC1.CODPROD = P1.CODPROD
                          
                        AND OC1.DTOCOR = P1.REFERENCIA
                          
                INNER JOIN
                    TCSOCO O1 
                        ON  O1.CODOCOR = OC1.CODOCOR
                          
                WHERE
                    P1.NUMCONTRATO = PRE.NUMCONTRATO
                          
                    AND P1.CODPROD = PRE.CODPROD
                          
                    AND P1.REFERENCIA <= OCC.DTOCOR
                          
                    AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N' )

AND PRE2.REFERENCIA IN (   SELECT
                    MAX(P1.REFERENCIA)
                           
                FROM
                    TCSPRE P1
                           
                INNER JOIN
                    TCSOCC OC1 
                        ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                                                    
                        AND OC1.CODPROD = P1.CODPROD
                                                    
                        AND OC1.DTOCOR = P1.REFERENCIA
                           
                INNER JOIN
                    TCSOCO O1 
                        ON  O1.CODOCOR = OC1.CODOCOR
                           
                WHERE
                    P1.NUMCONTRATO = PRE2.NUMCONTRATO
                           
                    AND P1.CODPROD = PRE2.CODPROD
                           
                    AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                           --
                    AND NVL(O1.AD_CONSIDERARDIFERENCA, 'N') = 'N'
                           
                    AND TRUNC(P1.REFERENCIA, 'MM') <= PRE.REFERENCIA)

UNION ALL

-- REDUÇÃO
SELECT
'REDUCAO' AS TIPO,
                
CON.CODPROJ,
                
CON.CODCENCUS,
                
CON.CODNAT,
                
CON.AD_CODVEND,
                
CON.CODEMP,
                
CON.NUMCONTRATO,
                
PSC.CODPROD,
                
PRO.DESCRPROD,
                
OCC.CODOCOR,
                
OCO.DESCRICAO,
                
OCC.DTOCOR,
                
OCO.AD_CLASSIFICACAO,
                
CASE 
                WHEN OCO.AD_CONSIDERARDIFERENCA = 'S'  THEN (PRE2.VALOR - PRE.VALOR)
     
                ELSE PRE.VALOR 
            END / CASE 
                WHEN CON.TIPO = 'M' THEN 1
     
                WHEN CON.TIPO = 'B' THEN 2
     
                WHEN CON.TIPO = 'T' THEN 3
     
                WHEN CON.TIPO = 'Q' THEN 4
     
                WHEN CON.TIPO = 'S' THEN 6
     
                WHEN CON.TIPO = 'A' THEN 12
     
                WHEN CON.TIPO = 'L' THEN 1
END AS VLR_CAN,
                
0 AS VLR
FROM TCSCON CON
JOIN TCSPSC PSC 
                    ON CON.NUMCONTRATO = PSC.NUMCONTRATO
JOIN TCSOCC OCC 
                        ON CON.NUMCONTRATO = OCC.NUMCONTRATO 
                        AND OCC.CODPROD = PSC.CODPROD
JOIN TCSOCO OCO 
                            ON OCC.CODOCOR = OCO.CODOCOR
JOIN TGFPRO PRO 
                                ON PSC.CODPROD = PRO.CODPROD
LEFT 
                        JOIN
                            TCSPRE PRE 
                                ON CON.NUMCONTRATO = PRE.NUMCONTRATO 
                                AND PRE.CODPROD = PSC.CODPROD
LEFT 
                        JOIN
                            TCSPRE PRE2 
                                ON CON.NUMCONTRATO = PRE2.NUMCONTRATO 
                                AND PRE2.CODPROD = PSC.CODPROD


WHERE CON.RECDESP = 1
AND NVL(OCO.AD_DESCONSIDERARMRR,
                            'N') = 'N'
AND OCO.AD_CONSIDERARDIFERENCA = 'S'
AND OCO.CODOCOR <> 3
AND ( OCO.SITPROD IN ( 'B',
                            'C' ) 
                            OR OCO.AD_CLASSIFICACAO IN ( 1,
                            2,
                            3,
                            4,
                            5,
                            6,
                            7,
                            8 ,
                            14 ))
AND PRE.REFERENCIA IN (   SELECT
                                MAX(P1.REFERENCIA)
                          
                            FROM
                                TCSPRE P1
                          
                            INNER JOIN
                                TCSOCC OC1 
                                    ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                          
                                    AND OC1.CODPROD = P1.CODPROD
                          
                                    AND OC1.DTOCOR = P1.REFERENCIA
                          
                            INNER JOIN
                                TCSOCO O1 
                                    ON  O1.CODOCOR = OC1.CODOCOR
                          
                            WHERE
                                P1.NUMCONTRATO = PRE.NUMCONTRATO
                          
                                AND P1.CODPROD = PRE.CODPROD
                          
                                AND P1.REFERENCIA <= OCC.DTOCOR
                          
                                AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                          --
                                AND O1.AD_CONSIDERARDIFERENCA = 'S'
                          )
AND PRE2.REFERENCIA IN (   SELECT
                                MAX(P1.REFERENCIA)
                           
                            FROM
                                TCSPRE P1
                           
                            INNER JOIN
                                TCSOCC OC1 
                                    ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                                                    
                                    AND OC1.CODPROD = P1.CODPROD
                                                    
                                    AND OC1.DTOCOR = P1.REFERENCIA
                           
                            INNER JOIN
                                TCSOCO O1 
                                    ON  O1.CODOCOR = OC1.CODOCOR
                           
                            WHERE
                                P1.NUMCONTRATO = PRE2.NUMCONTRATO
                           
                                AND P1.CODPROD = PRE2.CODPROD
                           
                                AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                           
                                AND P1.REFERENCIA < PRE.REFERENCIA )



UNION ALL

-- NOVOS CONTRATOS,
                            -- BUSCA PELA DATA DO CONTRATO
SELECT
'NOVOS' AS TIPO,
                            
CON.CODPROJ,
                            
CON.CODCENCUS,
                            
CON.CODNAT,
                            
CON.AD_CODVEND,
                            
CON.CODEMP,
                            
CON.NUMCONTRATO,
                            
PSC.CODPROD,
                            
PRO.DESCRPROD,
                            
OCC.CODOCOR,
                            
OCO.DESCRICAO,
                            
OCC.DTOCOR,
                            
'10' AS AD_CLASSIFICACAO,
                            
0 AS VLR_CAN,
                            
PRE.VALOR/
 CASE 
                                WHEN CON.TIPO = 'M' THEN 1
     
                                WHEN CON.TIPO = 'B' THEN 2
     
                                WHEN CON.TIPO = 'T' THEN 3
     
                                WHEN CON.TIPO = 'Q' THEN 4
     
                                WHEN CON.TIPO = 'S' THEN 6
     
                                WHEN CON.TIPO = 'A' THEN 12
     
                                WHEN CON.TIPO = 'L' THEN 1
END  AS VLR
FROM TCSCON CON
INNER 
                            JOIN
                                TCSPSC PSC 
                                    ON CON.NUMCONTRATO = PSC.NUMCONTRATO
INNER 
                            JOIN
                                TCSOCC OCC 
                                    ON CON.NUMCONTRATO = OCC.NUMCONTRATO 
                                    AND OCC.CODPROD = PSC.CODPROD
INNER 
                            JOIN
                                TCSOCO OCO 
                                    ON OCC.CODOCOR = OCO.CODOCOR
INNER 
                            JOIN
                                TGFPRO PRO 
                                    ON PSC.CODPROD = PRO.CODPROD
INNER 
                            JOIN
                                TCSPRE PRE 
                                    ON CON.NUMCONTRATO = PRE.NUMCONTRATO 
                                    AND PRE.CODPROD = PSC.CODPROD 
                                    AND OCC.DTOCOR = PRE.REFERENCIA



WHERE CON.RECDESP = 1
AND OCO.SITPROD IN (
                                        'A' 
                                    )
AND NVL(OCO.AD_DESCONSIDERARMRR,
                                'N') = 'N'
AND CON.NUMCONTRATO <> 0
AND PRE.REFERENCIA IN (   SELECT
                                    MIN(P1.REFERENCIA)
                          
                                FROM
                                    TCSPRE P1
                          
                                INNER JOIN
                                    TCSOCC OC1 
                                        ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                                                    
                                        AND OC1.CODPROD = P1.CODPROD
                                                    
                                        AND OC1.DTOCOR = P1.REFERENCIA
                           
                                INNER JOIN
                                    TCSOCO O1 
                                        ON  O1.CODOCOR = OC1.CODOCOR
                          
                                WHERE
                                    P1.NUMCONTRATO = PRE.NUMCONTRATO
                          
                                    AND P1.CODPROD = PRE.CODPROD
                          
                                    AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                          
                                    AND NVL(O1.AD_CONSIDERARDIFERENCA, 'N') = 'N'
                          )


UNION ALL

-- GANHOS
SELECT
'GANHOS' AS TIPO,
                                
CON.CODPROJ,
                                
CON.CODCENCUS,
                                
CON.CODNAT,
                                
CON.AD_CODVEND,
                                
CON.CODEMP,
                                
CON.NUMCONTRATO,
                                
PSC.CODPROD,
                                
PRO.DESCRPROD,
                                
OCC.CODOCOR,
                                
OCO.DESCRICAO,
                                
OCC.DTOCOR,
                                
NVL(OCO.AD_CLASSIFICACAO,
                                7) AS AD_CLASSIFICACAO,
                                
0 AS VLR_CAN,
                                
CASE 
                                WHEN (OCO.AD_CONSIDERARDIFERENCA = 'S'  
                                OR OCC.CODOCOR = 1) THEN PRE.VALOR -
NVL(( SELECT
                                    PRE3.VALOR
  FROM
  TCSPRE PRE3
  WHERE
  CON.NUMCONTRATO = PRE3.NUMCONTRATO 
                                    AND PRE3.CODPROD = PSC.CODPROD
  
                                    AND PRE3.REFERENCIA IN (  SELECT
                                        MAX(P1.REFERENCIA)
                          
                                    FROM
                                        TCSPRE P1
                          
                                    INNER JOIN
                                        TCSOCC OC1 
                                            ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                          
                                            AND OC1.CODPROD = P1.CODPROD
                          
                                            AND OC1.DTOCOR = P1.REFERENCIA
                          
                                    INNER JOIN
                                        TCSOCO O1 
                                            ON  O1.CODOCOR = OC1.CODOCOR
                          
                                    WHERE
                                        P1.NUMCONTRATO = PRE.NUMCONTRATO
                          
                                        AND P1.CODPROD = PRE.CODPROD
                          
                                        AND (
                                            NVL(O1.AD_CONSIDERARDIFERENCA,'N') = 'S' 
                                            OR OC1.CODOCOR = 1
                                        )
                          
                                        AND P1.REFERENCIA < PRE.REFERENCIA  )
),
                                    0)

     
                                ELSE PRE.VALOR 
                            END/CASE 
                                WHEN CON.TIPO = 'M' THEN 1
     
                                WHEN CON.TIPO = 'B' THEN 2
     
                                WHEN CON.TIPO = 'T' THEN 3
     
                                WHEN CON.TIPO = 'Q' THEN 4
     
                                WHEN CON.TIPO = 'S' THEN 6
     
                                WHEN CON.TIPO = 'A' THEN 12
     
                                WHEN CON.TIPO = 'L' THEN 1
END AS VLR
FROM TCSCON CON
JOIN TCSPSC PSC 
                                    ON CON.NUMCONTRATO = PSC.NUMCONTRATO
JOIN TCSOCC OCC 
                                        ON CON.NUMCONTRATO = OCC.NUMCONTRATO 
                                        AND OCC.CODPROD = PSC.CODPROD
JOIN TCSOCO OCO 
                                            ON OCC.CODOCOR = OCO.CODOCOR
JOIN TGFPRO PRO 
                                                ON PSC.CODPROD = PRO.CODPROD
JOIN TCSPRE PRE 
                                                    ON CON.NUMCONTRATO = PRE.NUMCONTRATO 
                                                    AND PRE.CODPROD = PSC.CODPROD
--
                                            LEFT JOIN
                                                TCSPRE PRE2 
                                                    ON CON.NUMCONTRATO = PRE2.NUMCONTRATO 
                                                    AND PRE2.CODPROD = PSC.CODPROD


WHERE CON.RECDESP = 1
AND OCO.SITPROD IN (
                                                        'A' 
                                                    )
AND NVL(OCO.AD_DESCONSIDERARMRR,
                                                'N') = 'N'
AND OCC.CODOCOR NOT IN 14
AND OCC.DTOCOR >  (  SELECT
                                                    MIN(OC1.DTOCOR)
                     FROM
                     TCSOCC OC1
                     
                                                INNER JOIN
                                                    TCSOCO O1 
                                                        ON  O1.CODOCOR = OC1.CODOCOR
                     WHERE
                     CON.NUMCONTRATO = OC1.NUMCONTRATO
                     
                                                        AND OC1.CODPROD = PSC.CODPROD
                     
                                                        AND (
                                                            NVL(O1.AD_DESCONSIDERARMRR,
                                                        'N') = 'N'
                      
                                                        OR  O1.CODOCOR  =  15 )
                     )  -- PARA NÃO TRAZER O PRIMEIRO LANÇAMENTO QUE JÁ ESTÁ NO 
                                                -- UNION DE CONTRATOS
AND PRE.REFERENCIA IN (
                                                    SELECT
                                                        MAX(P1.REFERENCIA)
                          
                                                    FROM
                                                        TCSPRE P1
                          
                                                    INNER JOIN
                                                        TCSOCC OC1 
                                                            ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                          
                                                            AND OC1.CODPROD = P1.CODPROD
                          
                                                            AND OC1.DTOCOR = P1.REFERENCIA
                          
                                                    INNER JOIN
                                                        TCSOCO O1 
                                                            ON  O1.CODOCOR = OC1.CODOCOR
                          
                                                    WHERE
                                                        P1.NUMCONTRATO = PRE.NUMCONTRATO
                          
                                                        AND P1.CODPROD = PRE.CODPROD
                          
                                                        AND P1.REFERENCIA <= OCC.DTOCOR
                          
                                                        AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                                                )

                          /*
AND PRE2.REFERENCIA IN (
                                                    SELECT
                                                        MAX(P1.REFERENCIA)
                          
                                                    FROM
                                                        TCSPRE P1
                          
                                                    INNER JOIN
                                                        TCSOCC OC1 
                                                            ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                          
                                                            AND OC1.CODPROD = P1.CODPROD
                          
                                                            AND OC1.DTOCOR = P1.REFERENCIA
                          
                                                    INNER JOIN
                                                        TCSOCO O1 
                                                            ON  O1.CODOCOR = OC1.CODOCOR
                          
                                                    WHERE
                                                        P1.NUMCONTRATO = PRE2.NUMCONTRATO
                          
                                                        AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                          
                                                        AND P1.CODPROD = PRE2.CODPROD
                          
                                                        AND TRUNC(P1.REFERENCIA, 'MM') < PRE.REFERENCIA 
                                                )
                          */


UNION ALL

-- CANCELAMENTO
SELECT
'CANCELAMENTO' AS TIPO,
CON.CODPROJ,
CON.CODCENCUS,
CON.CODNAT,
CON.AD_CODVEND,
CON.CODEMP,
CON.NUMCONTRATO,
PSC.CODPROD,
PRO.DESCRPROD,
OCC.CODOCOR,
OCO.DESCRICAO,
OCC.DTOCOR,
OCO.AD_CLASSIFICACAO,
CASE 
                                                WHEN OCO.AD_CONSIDERARDIFERENCA = 'S'  THEN PRE2.VALOR - PRE.VALOR
     
                                                ELSE PRE.VALOR 
                                            END/CASE 
                                                WHEN CON.TIPO = 'M' THEN 1
     
                                                WHEN CON.TIPO = 'B' THEN 2
     
                                                WHEN CON.TIPO = 'T' THEN 3
     
                                                WHEN CON.TIPO = 'Q' THEN 4
     
                                                WHEN CON.TIPO = 'S' THEN 6
     
                                                WHEN CON.TIPO = 'A' THEN 12
     
                                                WHEN CON.TIPO = 'L' THEN 1
END AS VLR_CAN,
0 AS VLR
FROM TCSCON CON
JOIN TCSPSC PSC 
                                                    ON CON.NUMCONTRATO = PSC.NUMCONTRATO
JOIN TCSOCC OCC 
                                                        ON CON.NUMCONTRATO = OCC.NUMCONTRATO 
                                                        AND OCC.CODPROD = PSC.CODPROD
JOIN TCSOCO OCO 
                                                            ON OCC.CODOCOR = OCO.CODOCOR
JOIN TGFPRO PRO 
                                                                ON PSC.CODPROD = PRO.CODPROD
LEFT 
                                                        JOIN
                                                            TCSPRE PRE 
                                                                ON CON.NUMCONTRATO = PRE.NUMCONTRATO 
                                                                AND PRE.CODPROD = PSC.CODPROD
LEFT 
                                                        JOIN
                                                            TCSPRE PRE2 
                                                                ON CON.NUMCONTRATO = PRE2.NUMCONTRATO 
                                                                AND PRE2.CODPROD = PSC.CODPROD


WHERE CON.RECDESP = 1
AND NVL(OCO.AD_DESCONSIDERARMRR,
                                                            'N') = 'N'
--
                                                            AND OCO.AD_CONSIDERARDIFERENCA = 'S'
--
                                                            AND CON.NUMCONTRATO = 681
--
                                                            AND PRE.CODPROD = 2478
AND OCO.CODOCOR = 3
AND PRE.REFERENCIA IN (   SELECT
                                                                MAX(P1.REFERENCIA)
                          
                                                            FROM
                                                                TCSPRE P1
                          
                                                            INNER JOIN
                                                                TCSOCC OC1 
                                                                    ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                          
                                                                    AND OC1.CODPROD = P1.CODPROD
                          
                                                                    AND OC1.DTOCOR = P1.REFERENCIA
                          
                                                            INNER JOIN
                                                                TCSOCO O1 
                                                                    ON  O1.CODOCOR = OC1.CODOCOR
                          
                                                            WHERE
                                                                P1.NUMCONTRATO = PRE.NUMCONTRATO
                          
                                                                AND P1.CODPROD = PRE.CODPROD
                          
                                                                AND P1.REFERENCIA <= OCC.DTOCOR
                          
                                                                AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                          
                                                                AND O1.AD_CONSIDERARDIFERENCA = 'S'
                          )

AND PRE2.REFERENCIA IN (   SELECT
                                                                MAX(P1.REFERENCIA)
                           
                                                            FROM
                                                                TCSPRE P1
                           
                                                            INNER JOIN
                                                                TCSOCC OC1 
                                                                    ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                                                    
                                                                    AND OC1.CODPROD = P1.CODPROD
                                                    
                                                                    AND OC1.DTOCOR = P1.REFERENCIA
                           
                                                            INNER JOIN
                                                                TCSOCO O1 
                                                                    ON  O1.CODOCOR = OC1.CODOCOR
                           
                                                            WHERE
                                                                P1.NUMCONTRATO = PRE2.NUMCONTRATO
                           
                                                                AND P1.CODPROD = PRE2.CODPROD
                           
                                                                AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                           
                                                                AND P1.REFERENCIA <= PRE.REFERENCIA )


UNION ALL

-- CANCELAMENTO
SELECT
'CANCELAMENTO QUANTO NÃO TEM OUTROS LANÇAMENTOS' AS TIPO,
                                                            
CON.CODPROJ,
                                                            
CON.CODCENCUS,
                                                            
CON.CODNAT,
                                                            
CON.AD_CODVEND,
                                                            
CON.CODEMP,
                                                            
CON.NUMCONTRATO,
                                                            
PSC.CODPROD,
                                                            
PRO.DESCRPROD,
                                                            
OCC.CODOCOR,
                                                            
OCO.DESCRICAO,
                                                            
OCC.DTOCOR,
                                                            
OCO.AD_CLASSIFICACAO,
                                                            
CASE 
                                                            WHEN OCO.AD_CONSIDERARDIFERENCA = 'S'  THEN PRE2.VALOR - PRE.VALOR
     
                                                            ELSE PRE.VALOR 
                                                        END/CASE 
                                                            WHEN CON.TIPO = 'M' THEN 1
     
                                                            WHEN CON.TIPO = 'B' THEN 2
     
                                                            WHEN CON.TIPO = 'T' THEN 3
     
                                                            WHEN CON.TIPO = 'Q' THEN 4
     
                                                            WHEN CON.TIPO = 'S' THEN 6
     
                                                            WHEN CON.TIPO = 'A' THEN 12
     
                                                            WHEN CON.TIPO = 'L' THEN 1
END AS VLR_CAN,
                                                            
0 AS VLR
FROM TCSCON CON
JOIN TCSPSC PSC 
                                                                ON CON.NUMCONTRATO = PSC.NUMCONTRATO
JOIN TCSOCC OCC 
                                                                    ON CON.NUMCONTRATO = OCC.NUMCONTRATO 
                                                                    AND OCC.CODPROD = PSC.CODPROD
JOIN TCSOCO OCO 
                                                                        ON OCC.CODOCOR = OCO.CODOCOR
JOIN TGFPRO PRO 
                                                                            ON PSC.CODPROD = PRO.CODPROD
LEFT 
                                                                    JOIN
                                                                        TCSPRE PRE 
                                                                            ON CON.NUMCONTRATO = PRE.NUMCONTRATO 
                                                                            AND PRE.CODPROD = PSC.CODPROD
LEFT 
                                                                    JOIN
                                                                        TCSPRE PRE2 
                                                                            ON CON.NUMCONTRATO = PRE2.NUMCONTRATO 
                                                                            AND PRE2.CODPROD = PSC.CODPROD


WHERE CON.RECDESP = 1
AND NVL(OCO.AD_DESCONSIDERARMRR,
                                                                        'N') = 'N'
--
                                                                        AND OCO.AD_CONSIDERARDIFERENCA = 'S'

AND OCO.CODOCOR = 3
AND PRE.REFERENCIA IN (   SELECT
                                                                            MAX(P1.REFERENCIA)
                          
                                                                        FROM
                                                                            TCSPRE P1
                          
                                                                        INNER JOIN
                                                                            TCSOCC OC1 
                                                                                ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                          
                                                                                AND OC1.CODPROD = P1.CODPROD
                          
                                                                                AND OC1.DTOCOR = P1.REFERENCIA
                          
                                                                        INNER JOIN
                                                                            TCSOCO O1 
                                                                                ON  O1.CODOCOR = OC1.CODOCOR
                          
                                                                        WHERE
                                                                            P1.NUMCONTRATO = PRE.NUMCONTRATO
                          
                                                                            AND P1.CODPROD = PRE.CODPROD
                          
                                                                            AND P1.REFERENCIA <= OCC.DTOCOR
                         -- 
                                                                            AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'N'
                          --
                                                                            AND O1.AD_CONSIDERARDIFERENCA = 'S'
                          )

AND PRE2.REFERENCIA IN (   SELECT
                                                                            MAX(P1.REFERENCIA)
                           
                                                                        FROM
                                                                            TCSPRE P1
                           
                                                                        INNER JOIN
                                                                            TCSOCC OC1 
                                                                                ON OC1.NUMCONTRATO = P1.NUMCONTRATO
                                                    
                                                                                AND OC1.CODPROD = P1.CODPROD
                                                    
                                                                                AND OC1.DTOCOR = P1.REFERENCIA
                           
                                                                        INNER JOIN
                                                                            TCSOCO O1 
                                                                                ON  O1.CODOCOR = OC1.CODOCOR
                           
                                                                        WHERE
                                                                            P1.NUMCONTRATO = PRE2.NUMCONTRATO
                           
                                                                            AND P1.CODPROD = PRE2.CODPROD
                           
                                                                            AND NVL(O1.AD_DESCONSIDERARMRR,'N') = 'S'
                           
                                                                            AND O1.AD_CONSIDERARDIFERENCA = 'S'
                           
                                                                            AND P1.REFERENCIA <= PRE.REFERENCIA );
