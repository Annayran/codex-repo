-- Read-only baseline extracted from TESTE on 2026-08-13.
-- Source: Resultado_da_Query (24).xlsx; do not edit as deployment source.
CREATE OR REPLACE TRIGGER "TESTE"."TRG_IU_AD_TGFSEQENT_PRA"
BEFORE INSERT OR UPDATE ON AD_TGFSEQENT
FOR EACH ROW
BEGIN
:NEW.DTLANC := SYSDATE;
:NEW.CODUSU := STP_GET_CODUSULOGADO;
END;

/
