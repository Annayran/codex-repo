/*
 * Chamado 8175 - Estoque disponivel para pedidos de exportacao.
 *
 * Regra de saldo:
 *   estoque da empresa 110
 *   - reservado das empresas 110 e 2
 *   - WMS bloqueado das empresas 110 e 2.
 *
 * A view preserva a estrutura da AD_QTDESTPLAPLA_V2, mas utiliza uma
 * composicao de estoque exclusiva para exportacao e consolidada por
 * produto/local.
 */
CREATE OR REPLACE VIEW SKL_QTDESTPLAPLA_EXP_V2 AS
WITH C AS (
    SELECT
        NUNOTA,
        SEQUENCIA,
        CODPROD,
        CODEMP,
        CODLOCALORIG AS CODLOCAL,
        ESTDISPSEMPED,
        ESTDISPVOLPAD,
        TRUNC(
            CASE
                WHEN ESTDISPSEMPED > 0
                 AND ESTDISPSEMPED < QTDPENDENTE
                    THEN ESTDISPSEMPED
                ELSE ESTDISPSEMPED - QTDPENDENTE
            END
        ) AS ESTDISPCOMPED,
        CASE
            WHEN ESTDISPSEMPED > 0
             AND ESTDISPSEMPED < QTDPENDENTE
                THEN 'Parcialmente'
            WHEN ESTDISPSEMPED > QTDPENDENTE
                THEN 'Sim'
            ELSE 'Nao'
        END AS DISP,
        ROUND(
            CASE
                WHEN TRUNC(ESTDISPSEMPED) < 0
                    THEN QTDPENDENTE
                WHEN TRUNC(ESTDISPSEMPED) > QTDPENDENTE
                    THEN 0
                ELSE (TRUNC(ESTDISPSEMPED) - QTDPENDENTE) * -1
            END * VLRUNIT_CX,
            2
        ) AS VLRPREVCORTE,
        QTDPENDENTE,
        CASE
            WHEN TRUNC(ESTDISPSEMPED) > QTDPENDENTE
                THEN 0
            ELSE (TRUNC(ESTDISPSEMPED) - QTDPENDENTE) * -1
        END * FATOR AS QTDCORTE,
        CASE
            WHEN TRUNC(ESTDISPSEMPED) > QTDPENDENTE
                THEN 0
            ELSE (TRUNC(ESTDISPSEMPED) - QTDPENDENTE) * -1
        END AS QTDCORTE_CX,
        ROUND(
            (
                CASE
                    WHEN (
                        CASE
                            WHEN ESTDISPSEMPED > 0
                             AND ESTDISPSEMPED < QTDPENDENTE
                                THEN ESTDISPSEMPED
                            ELSE ESTDISPSEMPED - QTDPENDENTE
                        END - QTDPENDENTE
                    ) > 0
                        THEN 0
                    ELSE (
                        CASE
                            WHEN ESTDISPSEMPED > 0
                             AND ESTDISPSEMPED < QTDPENDENTE
                                THEN ESTDISPSEMPED
                            ELSE ESTDISPSEMPED - QTDPENDENTE
                        END - QTDPENDENTE
                    ) * -1
                END
            )
            / CASE
                WHEN QTDPENDENTE = 0 THEN 1
                ELSE QTDPENDENTE
              END
            * 100,
            2
        ) AS PERCCORTE,
        VLRUNIT_CX
    FROM (
        SELECT DISTINCT
            ITE.NUNOTA,
            ITE.SEQUENCIA,
            ITE.CODPROD,
            ITE.CODEMP,
            ITE.CODLOCALORIG,

            ROUND(
                NVL(EST.ESTDISPVOLPAD, 0)
                / NULLIF(
                    CASE
                        WHEN (
                            SELECT COUNT(V.CODPROD)
                            FROM TGFVOA V
                            WHERE V.CODVOL = 'CX'
                              AND V.CODPROD = ITE.CODPROD
                        ) = 1
                            THEN (
                                SELECT VOA.QUANTIDADE
                                FROM TGFVOA VOA
                                WHERE VOA.CODVOL = 'CX'
                                  AND VOA.CODPROD = ITE.CODPROD
                            )
                        ELSE 0
                    END,
                    0
                ),
                2
            ) AS ESTDISPSEMPED,

            NVL(EST.ESTDISPVOLPAD, 0) AS ESTDISPVOLPAD,

            (ITE.QTDNEG - ITE.QTDENTREGUE)
            / NULLIF(
                CASE
                    WHEN (
                        SELECT COUNT(V.CODPROD)
                        FROM TGFVOA V
                        WHERE V.CODVOL = 'CX'
                          AND V.CODPROD = ITE.CODPROD
                    ) = 1
                        THEN (
                            SELECT VOA.QUANTIDADE
                            FROM TGFVOA VOA
                            WHERE VOA.CODVOL = 'CX'
                              AND VOA.CODPROD = ITE.CODPROD
                        )
                    ELSE 0
                END,
                0
            ) AS QTDPENDENTE,

            CASE
                WHEN ROUND(
                    NVL(EST.ESTDISPVOLPAD, 0)
                    / NULLIF(
                        CASE
                            WHEN (
                                SELECT COUNT(V.CODPROD)
                                FROM TGFVOA V
                                WHERE V.CODVOL = 'CX'
                                  AND V.CODPROD = ITE.CODPROD
                            ) = 1
                                THEN (
                                    SELECT VOA.QUANTIDADE
                                    FROM TGFVOA VOA
                                    WHERE VOA.CODVOL = 'CX'
                                      AND VOA.CODPROD = ITE.CODPROD
                                )
                            ELSE 0
                        END,
                        0
                    ),
                    2
                )
                - (ITE.QTDNEG - ITE.QTDENTREGUE)
                / NULLIF(
                    CASE
                        WHEN (
                            SELECT COUNT(V.CODPROD)
                            FROM TGFVOA V
                            WHERE V.CODVOL = 'CX'
                              AND V.CODPROD = ITE.CODPROD
                        ) = 1
                            THEN (
                                SELECT VOA.QUANTIDADE
                                FROM TGFVOA VOA
                                WHERE VOA.CODVOL = 'CX'
                                  AND VOA.CODPROD = ITE.CODPROD
                            )
                        ELSE 0
                    END,
                    0
                ) < 0
                    THEN 'Nao'
                ELSE 'Sim'
            END AS DISP,

            CASE
                WHEN ITE.QTDCONFERIDA = 0
                    THEN CASE
                        WHEN (
                            NVL(EST.ESTDISPVOLPAD, 0)
                            - (ITE.QTDNEG - ITE.QTDENTREGUE)
                        ) < 0
                            THEN (
                                NVL(EST.ESTDISPVOLPAD, 0)
                                - (ITE.QTDNEG - ITE.QTDENTREGUE)
                            ) * ITE.VLRUNIT * -1
                        ELSE 0
                    END
                ELSE 0
            END AS VLRPREVCORTE,

            CASE
                WHEN ITE.QTDCONFERIDA = 0
                    THEN CASE
                        WHEN (
                            NVL(EST.ESTDISPVOLPAD, 0)
                            - (ITE.QTDNEG - ITE.QTDENTREGUE)
                        ) < 0
                            THEN ROUND(
                                (
                                    NVL(EST.ESTDISPVOLPAD, 0)
                                    - (ITE.QTDNEG - ITE.QTDENTREGUE)
                                ) * ITE.VLRUNIT * -1
                                / CASE
                                    WHEN ITE.VLRTOT = 0 THEN 1
                                    ELSE ITE.VLRTOT
                                  END,
                                2
                            )
                        ELSE 0
                    END
                ELSE 0
            END AS PERCPREVCORTE,

            ITE.VLRUNIT * (
                CASE
                    WHEN (
                        SELECT COUNT(V.CODPROD)
                        FROM TGFVOA V
                        WHERE V.CODVOL = 'CX'
                          AND V.CODPROD = ITE.CODPROD
                    ) = 1
                        THEN (
                            SELECT VOA.QUANTIDADE
                            FROM TGFVOA VOA
                            WHERE VOA.CODVOL = 'CX'
                              AND VOA.CODPROD = ITE.CODPROD
                        )
                    ELSE 0
                END
            ) AS VLRUNIT_CX,

            ITE.QTDENTREGUE AS QTDENTREGUE,
            0 AS PERCCORTE,

            CASE
                WHEN (
                    SELECT COUNT(V.CODPROD)
                    FROM TGFVOA V
                    WHERE V.CODVOL = 'CX'
                      AND V.CODPROD = ITE.CODPROD
                ) = 1
                    THEN (
                        SELECT VOA.QUANTIDADE
                        FROM TGFVOA VOA
                        WHERE VOA.CODVOL = 'CX'
                          AND VOA.CODPROD = ITE.CODPROD
                    )
                ELSE 0
            END AS FATOR

        FROM TGFCAB CAB

        JOIN TGFITE ITE
          ON CAB.NUNOTA = ITE.NUNOTA

        JOIN AD_TPLTTOPCART CART
          ON CAB.CODTIPOPER = CART.CODTIPOPER
         AND CART.TIPO IN ('EE', 'E', 'EB')

        LEFT OUTER JOIN (
            SELECT
                EST.CODPROD,
                EST.CODLOCAL,
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
                ) AS ESTDISPVOLPAD
            FROM TGFEST EST
            WHERE EST.CODEMP IN (110, 2)
              AND EST.CODPARC = 0
              AND EST.TIPO = 'P'
            GROUP BY
                EST.CODPROD,
                EST.CODLOCAL
        ) EST
          ON EST.CODPROD = ITE.CODPROD
         AND EST.CODLOCAL = ITE.CODLOCALORIG

        WHERE ITE.PENDENTE = 'S'
    )
)
SELECT
    NUNOTA,
    SEQUENCIA,
    CODPROD,
    CODEMP,
    CODLOCAL,
    ESTDISPSEMPED,
    ESTDISPVOLPAD,
    ESTDISPCOMPED,
    DISP,
    VLRPREVCORTE,
    QTDPENDENTE,
    QTDCORTE,
    QTDCORTE_CX,
    CASE
        WHEN PERCCORTE > 100 THEN 100
        ELSE PERCCORTE
    END AS PERCCORTE,
    VLRUNIT_CX
FROM C;

