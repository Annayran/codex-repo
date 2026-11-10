CREATE OR REPLACE TRIGGER TRG_IUD_TGFITE_FILRES_PRA
BEFORE INSERT OR UPDATE OR DELETE ON TGFITE
FOR EACH ROW
DECLARE
    V_CODTIPOPER   TGFTOP.CODTIPOPER%TYPE;
    V_DHTIPOPER   TGFTOP.DHALTER%TYPE;
    V_TIPMOV       TGFCAB.TIPMOV%TYPE;
    V_FILARESERVA  VARCHAR2(1);
    V_DTINICIO     TGFCAB.DTPREVENT%TYPE;
    V_PROVISAO     VARCHAR2(1) := 'N';
    V_OPERACAO     VARCHAR2(6);
BEGIN
    IF STP_GET_ATUALIZANDO OR STP_GET_FILA THEN
        RETURN;
    END IF;

    -- O WHEN com :NEW impedia que DELETE chegasse a este bloco.
    IF DELETING THEN
        DELETE FROM AD_TGFSEQENT
         WHERE NUNOTA = :OLD.NUNOTA
           AND SEQUENCIA = :OLD.SEQUENCIA;
        RETURN;
    END IF;

    IF NVL(:NEW.STATUSNOTA, 'N') <> 'L' THEN
        RETURN;
    END IF;

    SELECT CAB.CODTIPOPER,
           CAB.DHTIPOPER,
           CAB.TIPMOV,
           NVL(TOP.AD_FILARESERVA, 'N')
      INTO V_CODTIPOPER,
           V_DHTIPOPER,
           V_TIPMOV,
           V_FILARESERVA
      FROM TGFCAB CAB
      JOIN TGFTOP TOP
        ON TOP.CODTIPOPER = CAB.CODTIPOPER
       AND TOP.DHALTER = CAB.DHTIPOPER
     WHERE CAB.NUNOTA = :NEW.NUNOTA;

    -- Gate funcional: restringe a criação da fila ao mesmo contrato da view.
    IF PRA_PKG_FILARES.TOP_CRIAR_RESERVA(V_FILARESERVA, V_TIPMOV) <> 'S' THEN
        RETURN;
    END IF;

    BEGIN
        SELECT OP.AD_DTSEPARACAO
          INTO V_DTINICIO
          FROM TGFCAB CAB
          JOIN TPRIPROC OP
            ON OP.IDIPROC = CAB.IDIPROC
         WHERE CAB.NUNOTA = :NEW.NUNOTA;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            V_DTINICIO := NULL;
    END;

    IF V_DTINICIO IS NOT NULL THEN
        :NEW.DTINICIO := V_DTINICIO;

        UPDATE TGFCAB
           SET DTPREVENT = V_DTINICIO
         WHERE NUNOTA = :NEW.NUNOTA;
    END IF;

    -- Uma única chamada, escolhendo INSERT ou DELETE pela direção da variação.
    IF UPDATING
       AND NVL(:NEW.QTDENTREGUE, 0) <> NVL(:OLD.QTDENTREGUE, 0) THEN
        IF NVL(:NEW.QTDENTREGUE, 0) < NVL(:OLD.QTDENTREGUE, 0) THEN
            V_OPERACAO := 'DELETE';
        ELSE
            V_OPERACAO := 'INSERT';
        END IF;

        STP_VALIDAFAT_FILARES_PRA(
            :NEW.NUNOTA,
            :NEW.SEQUENCIA,
            :NEW.QTDENTREGUE - NVL(:OLD.QTDENTREGUE, 0),
            V_OPERACAO
        );
    END IF;

    IF UPDATING AND :NEW.QTDNEG > :OLD.QTDNEG THEN
        STP_MSGERRO(
            'Não é possível aumentar a quantidade!',
            'Pedido/sequência faz parte da fila de reserva de estoque',
            'Insira uma nova linha'
        );
    END IF;

    -- Preserva o comportamento legado: redução de entregue é tratada pela
    -- validação e não chama STP_GRAVAFILA_PRA no mesmo evento.
    IF UPDATING
       AND NVL(:NEW.QTDENTREGUE, 0) < NVL(:OLD.QTDENTREGUE, 0) THEN
        RETURN;
    END IF;

    IF :NEW.DTINICIO - TRUNC(SYSDATE) > 120 THEN
        V_PROVISAO := 'S';
    END IF;

    STP_GRAVAFILA_PRA(
        P_NUNOTA    => :NEW.NUNOTA,
        P_SEQUENCIA => :NEW.SEQUENCIA,
        P_QTDNEG    => :NEW.QTDNEG,
        P_DTINICIO  => :NEW.DTINICIO,
        P_CODPROD   => :NEW.CODPROD,
        P_PROVISAO  => V_PROVISAO
    );
END;
/
