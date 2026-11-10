-- Read-only baseline extracted from TESTE on 2026-08-17.
-- Source: B3_03_02_FONTES_ACOES_COMPLEMENTARES; do not edit as deployment source.
CREATE OR REPLACE PROCEDURE STP_VALINVFILRES_PRA (
    P_NUNOTA TGFCAB.NUNOTA%TYPE,
    P_SUCESSO OUT VARCHAR2,
    P_MENSAGEM OUT VARCHAR2,
    P_CODUSULIB OUT NUMERIC
) AS
    /* Autor: Angelo Bortolini
     * Data: 19/11/2024
     * Objetivo: Validação da baixa de inventário
     */
    V_CODTIPOPER TGFCAB.CODTIPOPER%TYPE;
    V_COUNT INT;
BEGIN
    SELECT COUNT(1)
      INTO V_COUNT
      FROM AD_VGFRESEST EST
     WHERE EST.CODPROD IN (
           SELECT ITE.CODPROD
             FROM TGFITE ITE
            WHERE ITE.NUNOTA = P_NUNOTA
     );

    IF V_COUNT <> 0 THEN
        P_SUCESSO := 'N';
        P_MENSAGEM := 'Para realizar a baixa de estoque de produtos que constam na fila de reserva, é necessário autorização.';
    END IF;
END STP_VALINVFILRES_PRA;
/

