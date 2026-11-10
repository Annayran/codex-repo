-- SOMENTE LEITURA.
-- Exporte o CLOB retornado integralmente e salve-o como rollback oficial da janela.
-- Se DBMS_METADATA não estiver liberado ao usuário, solicitar o DDL ao DBA.

SELECT DBMS_METADATA.GET_DDL(
           'TRIGGER',
           'TRG_IUD_TGFITE_FILRES_PRA',
           SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA')
       ) AS DDL_TRIGGER_ATUAL
  FROM DUAL;

-- Fonte alternativa para comparação por linha.
SELECT OWNER,
       NAME,
       TYPE,
       LINE,
       TEXT
  FROM ALL_SOURCE
 WHERE OWNER = SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA')
   AND NAME = 'TRG_IUD_TGFITE_FILRES_PRA'
 ORDER BY LINE;

