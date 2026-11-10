-- O rollback nao pode prosseguir se houver item em processamento, pois
-- PROCESSANDO possui 11 caracteres e a coluna de producao aceita somente 10.
DECLARE
    V_QTD_PROCESSANDO NUMBER;
BEGIN
    SELECT COUNT(*)
      INTO V_QTD_PROCESSANDO
      FROM AD_NOTIFFLOW
     WHERE STATUS = 'PR';

    IF V_QTD_PROCESSANDO > 0 THEN
        RAISE_APPLICATION_ERROR(
            -20001,
            'Rollback cancelado: existem registros com status PR (Processado).'
        );
    END IF;

    UPDATE AD_NOTIFFLOW
       SET STATUS = CASE STATUS
           WHEN 'PE' THEN 'PENDENTE'
           WHEN 'EN' THEN 'ENVIADO'
           WHEN 'ER' THEN 'ERRO'
           ELSE STATUS
       END
     WHERE STATUS IN ('PE', 'EN', 'ER');

    EXECUTE IMMEDIATE
        'ALTER TABLE AD_NOTIFFLOW MODIFY (STATUS DEFAULT ''PENDENTE'')';

    COMMIT;
END;
/
