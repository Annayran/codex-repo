-- Read-only baseline extracted from TESTE on 2026-08-13.
-- Source: Resultado_da_Query (24).xlsx; do not edit as deployment source.
CREATE OR REPLACE FUNCTION            STP_GET_CODUSULOGADO
RETURN NUMBER
AS
BEGIN
RETURN(TSIUSU_LOG_PKG.V_CODUSULOG);
END;


/
