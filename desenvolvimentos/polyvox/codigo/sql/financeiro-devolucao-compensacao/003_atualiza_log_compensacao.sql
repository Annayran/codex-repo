CREATE OR REPLACE PROCEDURE ACTION_ATUALIZA_LOG_COMP_DEV
AS
BEGIN
    UPDATE AD_LOGCOMPDEV L
       SET CODUSUVISUALIZOU = (
               SELECT MIN(RAV.CODUSU)
                 FROM TSIRAV RAV
                WHERE RAV.NUAVISO = L.NUAVISO
           ),
           DHVISUALIZACAO = NVL(DHVISUALIZACAO, SYSDATE),
           STATUS = CASE
                        WHEN EXISTS (
                            SELECT 1
                              FROM TGFFIN FIN
                             WHERE FIN.NUNOTA = L.NUNOTADEV
                               AND FIN.NUCOMPENS IS NOT NULL
                        ) THEN 'COMPENSADO'
                        ELSE 'VISUALIZADO'
                    END,
           DHCOMPENSACAO = CASE
                               WHEN EXISTS (
                                   SELECT 1
                                     FROM TGFFIN FIN
                                    WHERE FIN.NUNOTA = L.NUNOTADEV
                                      AND FIN.NUCOMPENS IS NOT NULL
                               ) THEN NVL(DHCOMPENSACAO, SYSDATE)
                               ELSE DHCOMPENSACAO
                            END,
           DHALTERACAO = SYSDATE
     WHERE EXISTS (
               SELECT 1
                 FROM TSIRAV RAV
                WHERE RAV.NUAVISO = L.NUAVISO
           )
       AND L.STATUS IN ('AVISADO', 'VISUALIZADO');

    UPDATE AD_LOGCOMPDEV L
       SET STATUS = 'COMPENSADO',
           DHCOMPENSACAO = NVL(DHCOMPENSACAO, SYSDATE),
           DHALTERACAO = SYSDATE
     WHERE EXISTS (
               SELECT 1
                 FROM TGFFIN FIN
                WHERE FIN.NUNOTA = L.NUNOTADEV
                  AND FIN.NUCOMPENS IS NOT NULL
           )
       AND L.STATUS <> 'COMPENSADO';
END;
/
