SELECT CASE
           WHEN EXISTS (
               SELECT 1
               FROM TGFFIN FIN
               JOIN TGFTIT TIT
                 ON TIT.CODTIPTIT = FIN.CODTIPTIT
               JOIN TSIEMP EMP
                 ON EMP.CODEMP = FIN.CODEMP
               JOIN TSIEMP EMPMAT
                 ON EMPMAT.CODEMP = EMP.CODEMPMATRIZ
               JOIN TGFVEN VEN
                 ON VEN.CODVEND = FIN.CODVEND
               JOIN TGFPAR PAR
                 ON PAR.CODPARC = FIN.CODPARC
               WHERE FIN.DTVENCINIC <=
                     CASE
                         WHEN TO_NUMBER(TO_CHAR(TRUNC(SYSDATE - 1), 'D')) = 1
                             THEN TRUNC(SYSDATE - 3)
                         WHEN TO_NUMBER(TO_CHAR(TRUNC(SYSDATE - 1), 'D')) = 7
                             THEN TRUNC(SYSDATE - 2)
                         ELSE TRUNC(SYSDATE - 1)
                     END
                 AND FIN.RECDESP = 1
                 AND FIN.CODNAT NOT IN (
                     3070400, 3071000, 7060000, 7070000,
                     7080000, 7300000, 7350000, 7030000,
                     2030400, 7130000, 7280000, 7550000,
                     7560000, 7570000
                 )
                 AND FIN.DHBAIXA IS NULL
                 AND FIN.CODTIPOPER NOT IN (
                     SELECT DISTINCT TOP.CODTIPOPER
                     FROM TGFTOP TOP
                     WHERE TOP.TIPMOV = 'E'
                 )
                 AND FIN.CODEMP = 1
                 AND FIN.CODPARC = PAR_CTX.CODPARC
                 AND FIN.PROVISAO <> 'S'
                 AND TIT.CODTIPTIT <> 56
           ) THEN
               '<span style="color:#d32f2f;font-size:32px;font-weight:700;">Inadimplente</span>'
           ELSE
               '<span style="color:#1565c0;font-size:32px;font-weight:700;">Adimplente</span>'
       END AS SITUACAO_HTML
FROM TGFPAR PAR_CTX
WHERE PAR_CTX.CODPARC = :CODPARC
  AND PAR_CTX.ATIVO = 'S'

