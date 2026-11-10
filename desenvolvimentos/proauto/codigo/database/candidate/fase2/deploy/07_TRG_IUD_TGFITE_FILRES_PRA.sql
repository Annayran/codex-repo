CREATE OR REPLACE TRIGGER TRG_IUD_TGFITE_FILRES_PRA
FOR INSERT OR UPDATE OR DELETE ON TGFITE
COMPOUND TRIGGER
    TYPE T_EVENTO IS RECORD (
        OPERACAO       VARCHAR2(1),
        NUNOTA         TGFITE.NUNOTA%TYPE,
        SEQUENCIA      TGFITE.SEQUENCIA%TYPE,
        CODPROD        TGFITE.CODPROD%TYPE,
        QTDNEG_NOVA    TGFITE.QTDNEG%TYPE,
        QTDNEG_ANTIGA  TGFITE.QTDNEG%TYPE,
        QTDENT_NOVA    TGFITE.QTDENTREGUE%TYPE,
        QTDENT_ANTIGA  TGFITE.QTDENTREGUE%TYPE,
        DTINICIO       TGFITE.DTINICIO%TYPE,
        CODEMP         TGFCAB.CODEMP%TYPE,
        ELEGIVEL       VARCHAR2(1)
    );

    TYPE T_EVENTOS IS TABLE OF T_EVENTO INDEX BY PLS_INTEGER;
    G_EVENTOS T_EVENTOS;
    G_TOTAL   PLS_INTEGER := 0;

    BEFORE EACH ROW IS
        V_CODEMP       TGFCAB.CODEMP%TYPE;
        V_TIPMOV       TGFCAB.TIPMOV%TYPE;
        V_STATUSNOTA   TGFCAB.STATUSNOTA%TYPE;
        V_FILARESERVA  VARCHAR2(1);
        V_DTPREVENT    TGFCAB.DTPREVENT%TYPE;
        V_DTINICIO     TGFITE.DTINICIO%TYPE;
    BEGIN
        IF NOT (STP_GET_ATUALIZANDO OR STP_GET_FILA) THEN
            G_TOTAL := G_TOTAL + 1;

            IF DELETING THEN
                G_EVENTOS(G_TOTAL).OPERACAO := 'D';
                G_EVENTOS(G_TOTAL).NUNOTA := :OLD.NUNOTA;
                G_EVENTOS(G_TOTAL).SEQUENCIA := :OLD.SEQUENCIA;
                G_EVENTOS(G_TOTAL).ELEGIVEL := 'S';
            ELSE
                SELECT CAB.CODEMP,
                       CAB.TIPMOV,
                       CAB.STATUSNOTA,
                       CAB.DTPREVENT,
                       NVL(TOP.AD_FILARESERVA, 'N')
                  INTO V_CODEMP,
                       V_TIPMOV,
                       V_STATUSNOTA,
                       V_DTPREVENT,
                       V_FILARESERVA
                  FROM TGFCAB CAB
                  JOIN TGFTOP TOP
                    ON TOP.CODTIPOPER = CAB.CODTIPOPER
                   AND TOP.DHALTER = CAB.DHTIPOPER
                 WHERE CAB.NUNOTA = :NEW.NUNOTA;

                BEGIN
                    SELECT NVL(OP.AD_DTSEPARACAO, NVL(:NEW.DTINICIO, V_DTPREVENT))
                      INTO V_DTINICIO
                      FROM TGFCAB CAB
                      JOIN TPRIPROC OP
                        ON OP.IDIPROC = CAB.IDIPROC
                     WHERE CAB.NUNOTA = :NEW.NUNOTA;
                EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                        V_DTINICIO := NVL(:NEW.DTINICIO, V_DTPREVENT);
                END;

                IF V_DTINICIO IS NOT NULL THEN
                    :NEW.DTINICIO := V_DTINICIO;

                    IF NVL(V_DTPREVENT, DATE '1900-01-01') <> V_DTINICIO THEN
                        UPDATE TGFCAB
                           SET DTPREVENT = V_DTINICIO
                         WHERE NUNOTA = :NEW.NUNOTA;
                    END IF;
                END IF;

                G_EVENTOS(G_TOTAL).OPERACAO := CASE WHEN INSERTING THEN 'I' ELSE 'U' END;
                G_EVENTOS(G_TOTAL).NUNOTA := :NEW.NUNOTA;
                G_EVENTOS(G_TOTAL).SEQUENCIA := :NEW.SEQUENCIA;
                G_EVENTOS(G_TOTAL).CODPROD := :NEW.CODPROD;
                G_EVENTOS(G_TOTAL).QTDNEG_NOVA := :NEW.QTDNEG;
                G_EVENTOS(G_TOTAL).QTDENT_NOVA := NVL(:NEW.QTDENTREGUE, 0);
                G_EVENTOS(G_TOTAL).DTINICIO := V_DTINICIO;
                G_EVENTOS(G_TOTAL).CODEMP := V_CODEMP;
                G_EVENTOS(G_TOTAL).ELEGIVEL := CASE
                    WHEN NVL(V_STATUSNOTA, 'N') = 'L'
                    THEN PRA_PKG_FILARES.TOP_CRIAR_RESERVA(V_FILARESERVA, V_TIPMOV)
                    ELSE 'N'
                END;

                IF UPDATING THEN
                    G_EVENTOS(G_TOTAL).QTDNEG_ANTIGA := :OLD.QTDNEG;
                    G_EVENTOS(G_TOTAL).QTDENT_ANTIGA := NVL(:OLD.QTDENTREGUE, 0);
                ELSE
                    G_EVENTOS(G_TOTAL).QTDNEG_ANTIGA := 0;
                    G_EVENTOS(G_TOTAL).QTDENT_ANTIGA := 0;
                END IF;
            END IF;
        END IF;
    END BEFORE EACH ROW;

    AFTER STATEMENT IS
        V_DELTA_ENTREGA NUMBER;
        V_PROVISAO     VARCHAR2(1);
    BEGIN
        IF G_TOTAL > 0 THEN
          FOR I IN 1 .. G_TOTAL LOOP
            IF G_EVENTOS(I).OPERACAO = 'D' THEN
                DELETE FROM AD_TGFSEQENT
                 WHERE NUNOTA = G_EVENTOS(I).NUNOTA
                   AND SEQUENCIA = G_EVENTOS(I).SEQUENCIA;
            ELSIF G_EVENTOS(I).ELEGIVEL = 'S' THEN
                V_DELTA_ENTREGA := NVL(G_EVENTOS(I).QTDENT_NOVA, 0)
                                   - NVL(G_EVENTOS(I).QTDENT_ANTIGA, 0);

                IF G_EVENTOS(I).OPERACAO = 'U' AND V_DELTA_ENTREGA <> 0 THEN
                    STP_VALIDAFAT_FILARES_PRA(
                        G_EVENTOS(I).NUNOTA,
                        G_EVENTOS(I).SEQUENCIA,
                        V_DELTA_ENTREGA,
                        CASE WHEN V_DELTA_ENTREGA < 0 THEN 'DELETE' ELSE 'INSERT' END
                    );
                END IF;

                IF G_EVENTOS(I).OPERACAO = 'U'
                   AND NVL(G_EVENTOS(I).QTDNEG_NOVA, 0)
                       > NVL(G_EVENTOS(I).QTDNEG_ANTIGA, 0) THEN
                    STP_MSGERRO(
                        'Nao e possivel aumentar a quantidade!',
                        'Pedido/sequencia faz parte da fila de reserva de estoque',
                        'Insira uma nova linha'
                    );
                END IF;

                -- No estorno, a validacao ja devolveu o delta a fila.
                IF NOT (G_EVENTOS(I).OPERACAO = 'U' AND V_DELTA_ENTREGA < 0) THEN
                    V_PROVISAO := PRA_PKG_FILARES.CALCULAR_PROVISAO(
                        G_EVENTOS(I).DTINICIO,
                        G_EVENTOS(I).CODEMP,
                        TRUNC(SYSDATE)
                    );

                    STP_GRAVAFILA_PRA(
                        P_NUNOTA    => G_EVENTOS(I).NUNOTA,
                        P_SEQUENCIA => G_EVENTOS(I).SEQUENCIA,
                        P_QTDNEG    => G_EVENTOS(I).QTDNEG_NOVA,
                        P_DTINICIO  => G_EVENTOS(I).DTINICIO,
                        P_CODPROD   => G_EVENTOS(I).CODPROD,
                        P_PROVISAO  => V_PROVISAO
                    );
                END IF;
            END IF;
          END LOOP;
        END IF;
    END AFTER STATEMENT;
END TRG_IUD_TGFITE_FILRES_PRA;
/

SHOW ERRORS TRIGGER TRG_IUD_TGFITE_FILRES_PRA;
