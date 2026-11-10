SELECT DISTINCT
    DEST.CODCON AS CODCON,
    USU.EMAIL AS EMAIL,
    USU.NOMEUSU AS NOMEUSU,
    USU.CODVEND AS CODVEND,

    CAST(
        ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)
        AS TIMESTAMP
    ) AS "PERIODO.INI",

    CAST(
        TRUNC(SYSDATE, 'MM')
        AS TIMESTAMP
    ) - INTERVAL '1' SECOND AS "PERIODO.FIN"

FROM TSIUSU USU

INNER JOIN TMDCON DEST
    ON DEST.CODUSU = USU.CODUSU

WHERE DEST.ATIVO = 'S'
  AND USU.CODVEND IS NOT NULL
  AND TRIM(USU.EMAIL) IS NOT NULL

  AND EXISTS (
      SELECT 1

      FROM VGFCOM_FECH VCOM

      INNER JOIN TGFCOM COM
          ON COM.CODVEND = VCOM.CODVEND
         AND NVL(COM.NUNOTAORIG, -1)
             = NVL(VCOM.NUNOTAORIG, -1)
         AND NVL(COM.NUFINORIG, -1)
             = NVL(VCOM.NUFINORIG, -1)
         AND NVL(COM.NUMOSORIG, -1)
             = NVL(VCOM.NUMOSORIG, -1)
         AND NVL(COM.NUMITEMORIG, -1)
             = NVL(VCOM.NUMITEMORIG, -1)
         AND NVL(COM.TIPO, '#')
             = NVL(VCOM.TIPO, '#')
         AND NVL(COM.CODFORM, -1)
             = NVL(VCOM.CODFORM, -1)

      WHERE VCOM.CODVEND = USU.CODVEND
        AND NVL(COM.AD_REVISADO, 'N') = 'S'

        AND (
              (
                  VCOM.DTNEG >=
                      ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)
                  AND VCOM.DTNEG <
                      TRUNC(SYSDATE, 'MM')
                  AND VCOM.POSICAO_QUERY = '1'
              )

              OR

              (
                  (
                      (
                          VCOM.TIPCALC = 'N'
                          AND VCOM.DTNEG >=
                              ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)
                          AND VCOM.DTNEG <
                              TRUNC(SYSDATE, 'MM')
                      )
                      OR
                      (
                          VCOM.DHBAIXA >=
                              ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)
                          AND VCOM.DHBAIXA <
                              TRUNC(SYSDATE, 'MM')
                      )
                  )
                  AND VCOM.POSICAO_QUERY = '2'
              )

              OR

              (
                  VCOM.DHBAIXA >=
                      ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)
                  AND VCOM.DHBAIXA <
                      TRUNC(SYSDATE, 'MM')
                  AND VCOM.POSICAO_QUERY IN ('3', '4', '5', '6')
              )

              OR

              (
                  (
                      (
                          VCOM.DHBAIXA >=
                              ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)
                          AND VCOM.DHBAIXA <
                              TRUNC(SYSDATE, 'MM')
                      )
                      OR
                      (
                          VCOM.TIPFATSERV IN ('S', 'U')
                          AND VCOM.DHBAIXA <
                              ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)
                          AND VCOM.INICEXEC >=
                              ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)
                          AND VCOM.INICEXEC <
                              TRUNC(SYSDATE, 'MM')
                      )
                  )
                  AND VCOM.POSICAO_QUERY IN ('7', '8')
              )
            )
  )

ORDER BY
    USU.CODVEND,
    DEST.CODCON
