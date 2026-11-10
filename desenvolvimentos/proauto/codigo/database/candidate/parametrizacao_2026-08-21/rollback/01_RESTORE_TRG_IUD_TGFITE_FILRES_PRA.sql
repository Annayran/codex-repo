-- ATENCAO: fonte de rollback baseado no conteúdo recebido em 21/08/2026.
-- Antes do deploy, substituir este arquivo pelo DDL extraído do próprio ambiente
-- se houver qualquer divergência de LAST_DDL_TIME ou fonte.
CREATE OR REPLACE TRIGGER TRG_IUD_TGFITE_FILRES_PRA
BEFORE INSERT OR UPDATE OR DELETE ON TGFITE
FOR EACH ROW
WHEN (NEW.STATUSNOTA = 'L')
DECLARE
    V_CODTIPOPER   TGFTOP.CODTIPOPER%TYPE;
    V_DHTIPOPER   TGFTOP.DHALTER%TYPE;
    V_FILARESERVA CHAR(1);
    V_SEQPREV      INT;
    V_DTINICIO     TGFCAB.DTPREVENT%TYPE;
    V_PROVISAO     CHAR(1);
BEGIN
    IF STP_GET_ATUALIZANDO OR STP_GET_FILA THEN
        RETURN;
    END IF;

    IF DELETING THEN
        DELETE FROM AD_TGFSEQENT
         WHERE NUNOTA = :OLD.NUNOTA
           AND SEQUENCIA = :OLD.SEQUENCIA;
        RETURN;
    END IF;

    SELECT CAB.CODTIPOPER,
           CAB.DHTIPOPER
      INTO V_CODTIPOPER,
           V_DHTIPOPER
      FROM TGFCAB CAB
     WHERE CAB.NUNOTA = :NEW.NUNOTA;

    SELECT NVL(TP.AD_FILARESERVA, 'N')
      INTO V_FILARESERVA
      FROM TGFTOP TP
     WHERE TP.CODTIPOPER = V_CODTIPOPER
       AND TP.DHALTER = V_DHTIPOPER;

    IF V_FILARESERVA <> 'S' THEN
        RETURN;
    END IF;

    V_PROVISAO := 'N';

    BEGIN
        SELECT OP.AD_DTSEPARACAO
          INTO V_DTINICIO
          FROM TGFCAB CAB
          JOIN TPRIPROC OP
            ON CAB.IDIPROC = OP.IDIPROC
         WHERE CAB.NUNOTA = :NEW.NUNOTA;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            V_PROVISAO := 'N';
    END;

    IF V_DTINICIO IS NOT NULL THEN
        :NEW.DTINICIO := V_DTINICIO;

        UPDATE TGFCAB
           SET DTPREVENT = V_DTINICIO
         WHERE NUNOTA = :NEW.NUNOTA;
    END IF;

    IF :NEW.QTDENTREGUE <> :OLD.QTDENTREGUE THEN
        STP_VALIDAFAT_FILARES_PRA(
            :NEW.NUNOTA,
            :NEW.SEQUENCIA,
            :NEW.QTDENTREGUE - NVL(:OLD.QTDENTREGUE, 0),
            'INSERT'
        );
    END IF;

    IF :NEW.QTDNEG > :OLD.QTDNEG THEN
        STP_MSGERRO(
            'Não é possivel aumentar a quantidade!',
            'Pedido/sequencia faz parte da fila de reserva de estoque',
            'Insira uma nova linha'
        );
    END IF;

    IF :NEW.QTDENTREGUE < :OLD.QTDENTREGUE THEN
        STP_VALIDAFAT_FILARES_PRA(
            :NEW.NUNOTA,
            :NEW.SEQUENCIA,
            :NEW.QTDENTREGUE - NVL(:OLD.QTDENTREGUE, 0),
            'DELETE'
        );
    ELSE
        IF :NEW.DTINICIO - TRUNC(SYSDATE) > 120 THEN
            V_PROVISAO := 'S';
        END IF;

        STP_GRAVAFILA_PRA(
            p_NUNOTA    => :NEW.NUNOTA,
            p_SEQUENCIA => :NEW.SEQUENCIA,
            p_QTDNEG    => :NEW.QTDNEG,
            p_DTINICIO  => :NEW.DTINICIO,
            p_CODPROD   => :NEW.CODPROD,
            p_PROVISAO  => V_PROVISAO
        );
    END IF;
END;
/

SHOW ERRORS TRIGGER TRG_IUD_TGFITE_FILRES_PRA;

