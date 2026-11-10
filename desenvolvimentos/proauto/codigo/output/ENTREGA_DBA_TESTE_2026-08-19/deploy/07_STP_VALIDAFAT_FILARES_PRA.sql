CREATE OR REPLACE PROCEDURE STP_VALIDAFAT_FILARES_PRA (
    P_NUNOTA      IN NUMBER,
    P_SEQUENCIA   IN NUMBER,
    P_QTDENTREGUE IN NUMBER,
    V_ACAO        IN VARCHAR2
) AS
    V_CODTIPOPER    TGFCAB.CODTIPOPER%TYPE;
    V_CODPROD       TGFITE.CODPROD%TYPE;
    V_DESCRPROD     TGFPRO.DESCRPROD%TYPE;
    V_DISPONIVEL    NUMBER := 0;
    V_RESTANTE      NUMBER := ABS(NVL(P_QTDENTREGUE, 0));
    V_APLICAR       NUMBER;
    V_LIVRE_ANTES   NUMBER;
    V_ALOCAVEL      NUMBER;
BEGIN
    IF NVL(P_QTDENTREGUE, 0) = 0 THEN
        RETURN;
    END IF;

    SELECT CAB.CODTIPOPER,
           ITE.CODPROD,
           PRO.DESCRPROD
      INTO V_CODTIPOPER,
           V_CODPROD,
           V_DESCRPROD
      FROM TGFCAB CAB
      JOIN TGFITE ITE
        ON ITE.NUNOTA = CAB.NUNOTA
      JOIN TGFPRO PRO
        ON PRO.CODPROD = ITE.CODPROD
     WHERE ITE.NUNOTA = P_NUNOTA
       AND ITE.SEQUENCIA = P_SEQUENCIA;

    -- Excecao legada de pedido direto para nota, preservada nesta entrega.
    IF V_CODTIPOPER = 906 THEN
        RETURN;
    END IF;

    IF P_QTDENTREGUE > 0 THEN
        -- Primeiro valida toda a quantidade; nenhum DML ocorre antes do aceite.
        FOR REG IN (
            SELECT RES.AD_SEQRESERVA,
                   RES.QTDPENDENTE,
                   RES.SALDO
              FROM AD_VGFRESEST RES
             WHERE RES.NUNOTA = P_NUNOTA
               AND RES.SEQUENCIA = P_SEQUENCIA
             ORDER BY RES.AD_SEQRESERVA
        ) LOOP
            V_LIVRE_ANTES := NVL(REG.SALDO, 0) + NVL(REG.QTDPENDENTE, 0);
            V_ALOCAVEL := GREATEST(
                0,
                LEAST(NVL(REG.QTDPENDENTE, 0), V_LIVRE_ANTES)
            );
            V_DISPONIVEL := V_DISPONIVEL + V_ALOCAVEL;
        END LOOP;

        IF P_QTDENTREGUE > V_DISPONIVEL THEN
            STP_MSGERRO_SNK(
                'Erro ao gerar faturamento.',
                'O item <b>' || V_CODPROD || '-' || V_DESCRPROD
                || '</b> do pedido <b>' || P_NUNOTA
                || '</b> possui saldo liberado de ' || TO_CHAR(V_DISPONIVEL)
                || ' e a quantidade solicitada e ' || TO_CHAR(P_QTDENTREGUE),
                'Fature a quantidade liberada ou negocie a posicao na fila'
            );
        END IF;

        -- Consome somente o delta efetivamente faturado, na ordem da fila.
        FOR REG IN (
            SELECT RES.AD_SEQRESERVA,
                   RES.QTDPENDENTE,
                   RES.SALDO
              FROM AD_VGFRESEST RES
             WHERE RES.NUNOTA = P_NUNOTA
               AND RES.SEQUENCIA = P_SEQUENCIA
             ORDER BY RES.AD_SEQRESERVA
        ) LOOP
            EXIT WHEN V_RESTANTE <= 0;

            V_LIVRE_ANTES := NVL(REG.SALDO, 0) + NVL(REG.QTDPENDENTE, 0);
            V_ALOCAVEL := GREATEST(
                0,
                LEAST(NVL(REG.QTDPENDENTE, 0), V_LIVRE_ANTES)
            );
            V_APLICAR := LEAST(V_RESTANTE, V_ALOCAVEL);

            IF V_APLICAR > 0 THEN
                UPDATE AD_TGFSEQENT ENT
                   SET ENT.QTDENTREGUE = NVL(ENT.QTDENTREGUE, 0) + V_APLICAR
                 WHERE ENT.NUNOTA = P_NUNOTA
                   AND ENT.SEQUENCIA = P_SEQUENCIA
                   AND ENT.SEQENT = REG.AD_SEQRESERVA;

                IF SQL%ROWCOUNT <> 1 THEN
                    RAISE_APPLICATION_ERROR(-20020, 'Fragmento da fila nao localizado durante o faturamento.');
                END IF;

                V_RESTANTE := V_RESTANTE - V_APLICAR;
            END IF;
        END LOOP;

        IF V_RESTANTE > 0.000001 THEN
            RAISE_APPLICATION_ERROR(-20021, 'Quantidade faturada nao foi integralmente aplicada na fila.');
        END IF;
    ELSE
        -- Estorno/cancelamento: devolve o delta na ordem inversa do consumo.
        FOR REG IN (
            SELECT ENT.ROWID AS RID,
                   NVL(ENT.QTDENTREGUE, 0) AS QTDENTREGUE
              FROM AD_TGFSEQENT ENT
             WHERE ENT.NUNOTA = P_NUNOTA
               AND ENT.SEQUENCIA = P_SEQUENCIA
               AND NVL(ENT.QTDENTREGUE, 0) > 0
             ORDER BY ENT.SEQENT DESC
               FOR UPDATE OF ENT.QTDENTREGUE
        ) LOOP
            EXIT WHEN V_RESTANTE <= 0;

            V_APLICAR := LEAST(V_RESTANTE, REG.QTDENTREGUE);

            UPDATE AD_TGFSEQENT
               SET QTDENTREGUE = NVL(QTDENTREGUE, 0) - V_APLICAR
             WHERE ROWID = REG.RID;

            V_RESTANTE := V_RESTANTE - V_APLICAR;
        END LOOP;

        IF V_RESTANTE > 0.000001 THEN
            RAISE_APPLICATION_ERROR(
                -20022,
                'Estorno superior a quantidade entregue registrada na fila.'
            );
        END IF;
    END IF;
END STP_VALIDAFAT_FILARES_PRA;
/

SHOW ERRORS PROCEDURE STP_VALIDAFAT_FILARES_PRA;
