CREATE OR REPLACE PACKAGE PRA_PKG_FILARES AS
    /**
     * Retorna S quando a TOP deve criar/manter uma posição na fila ativa.
     * Reproduz o contrato atualmente comprovado na AD_VGFRESEST.
     */
    FUNCTION TOP_CRIAR_RESERVA (
        P_AD_FILARESERVA IN VARCHAR2,
        P_TIPMOV         IN VARCHAR2
    ) RETURN VARCHAR2 DETERMINISTIC;

    /**
     * Retorna S quando a operação está marcada para respeitar/proteger a reserva.
     * Reproduz o uso amplo atual do campo sem afirmar qual bloqueio deve ocorrer.
     */
    FUNCTION TOP_PROTEGER_ESTOQUE (
        P_AD_FILARESERVA IN VARCHAR2
    ) RETURN VARCHAR2 DETERMINISTIC;
END PRA_PKG_FILARES;
/

CREATE OR REPLACE PACKAGE BODY PRA_PKG_FILARES AS
    FUNCTION TOP_CRIAR_RESERVA (
        P_AD_FILARESERVA IN VARCHAR2,
        P_TIPMOV         IN VARCHAR2
    ) RETURN VARCHAR2 DETERMINISTIC IS
    BEGIN
        IF NVL(UPPER(TRIM(P_AD_FILARESERVA)), 'N') = 'S'
           AND NVL(UPPER(TRIM(P_TIPMOV)), '?') IN ('P', 'J') THEN
            RETURN 'S';
        END IF;

        RETURN 'N';
    END TOP_CRIAR_RESERVA;

    FUNCTION TOP_PROTEGER_ESTOQUE (
        P_AD_FILARESERVA IN VARCHAR2
    ) RETURN VARCHAR2 DETERMINISTIC IS
    BEGIN
        IF NVL(UPPER(TRIM(P_AD_FILARESERVA)), 'N') = 'S' THEN
            RETURN 'S';
        END IF;

        RETURN 'N';
    END TOP_PROTEGER_ESTOQUE;
END PRA_PKG_FILARES;
/

