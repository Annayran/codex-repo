-- SQL Server: somente leitura de estrutura, sem DML ou DDL.
-- Executar na mesma base de homologacao utilizada pelo Sankhya.

-- 1. Cliente manteve DESCRICAO varchar(100); Java7.14 fragmenta sem truncar.
SELECT TABLE_SCHEMA, COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH,
       IS_NULLABLE, COLUMN_DEFAULT,
       CASE WHEN COLUMN_NAME = 'DESCRICAO'
                 AND DATA_TYPE = 'varchar' AND CHARACTER_MAXIMUM_LENGTH >= 100
            THEN 'COMPATIVEL COM JAVA7.14; REGISTROS ATE99 CARACTERES'
            WHEN COLUMN_NAME = 'DESCRICAO'
            THEN 'CONFERIR TIPO TEXTO E LIMITE MINIMO100'
            ELSE 'CONFERIR COM O MANUAL' END AS CONFERENCIA
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'AD_RATEVT'
ORDER BY TABLE_SCHEMA, ORDINAL_POSITION;

-- 2. Estrutura real do rateio nativo, especialmente a precisao de PERCRATEIO.
SELECT TABLE_SCHEMA, COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH,
       NUMERIC_PRECISION, NUMERIC_SCALE, IS_NULLABLE, COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'TGFRAT'
ORDER BY TABLE_SCHEMA, ORDINAL_POSITION;

-- 3. Chave fisica do rateio: nao inferir unicidade apenas do dicionario DWF.
SELECT S.name AS TABLE_SCHEMA, T.name AS TABLE_NAME, I.name AS INDEX_NAME,
       I.is_primary_key, I.is_unique, C.name AS COLUMN_NAME, IC.key_ordinal
FROM sys.tables T
JOIN sys.schemas S ON S.schema_id = T.schema_id
JOIN sys.indexes I ON I.object_id = T.object_id
JOIN sys.index_columns IC ON IC.object_id = I.object_id AND IC.index_id = I.index_id
JOIN sys.columns C ON C.object_id = IC.object_id AND C.column_id = IC.column_id
WHERE T.name = 'TGFRAT' AND IC.key_ordinal > 0
ORDER BY S.name, I.name, IC.key_ordinal;

-- 4. Campos envolvidos na criacao financeira: apenas definicoes, sem titulos.
SELECT TABLE_SCHEMA, COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH,
       NUMERIC_PRECISION, NUMERIC_SCALE, IS_NULLABLE, COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'TGFFIN'
  AND COLUMN_NAME IN ('NUFIN','CODEMP','CODNAT','CODPARC','CODTIPOPER',
                      'DHTIPOPER','CODTIPTIT','RECDESP','RATEADO','CODCENCUS',
                      'VLRDESDOB','DTNEG','DTVENC','DTENTSAI','PROVISAO')
ORDER BY TABLE_SCHEMA, ORDINAL_POSITION;
