-- Alinha os valores armazenados aos codigos da lista de opcoes do Sankhya:
-- PE=Pendente, PR=Processado, EN=Enviado, ER=Erro.
UPDATE AD_NOTIFFLOW
   SET STATUS = CASE STATUS
       WHEN 'PENDENTE'   THEN 'PE'
       WHEN 'PROCESSANDO' THEN 'PR'
       WHEN 'ENVIADO'    THEN 'EN'
       WHEN 'ERRO'       THEN 'ER'
       ELSE STATUS
   END
 WHERE STATUS IN ('PENDENTE', 'PROCESSANDO', 'ENVIADO', 'ERRO');

ALTER TABLE AD_NOTIFFLOW
    MODIFY (STATUS DEFAULT 'PE');

COMMIT;

