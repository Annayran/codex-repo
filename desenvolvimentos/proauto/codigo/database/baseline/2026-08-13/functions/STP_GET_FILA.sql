-- Read-only baseline extracted from TESTE on 2026-08-13.
-- Source: Resultado_da_Query (24).xlsx; do not edit as deployment source.
CREATE OR REPLACE FUNCTION STP_GET_FILA
RETURN BOOLEAN
IS
BEGIN
RETURN VARIAVEIS_PKG.V_DISABLEFILA;
END;


/
