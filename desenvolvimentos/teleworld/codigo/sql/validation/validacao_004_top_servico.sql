-- Validacao da Regra TOP de Servico - somente leitura.

-- 1. Confirma a existencia dos campos usados pela procedure.
SELECT TABLE_NAME,
     COLUMN_NAME,
       DATA_TYPE,
       DATA_LENGTH,
       DATA_PRECISION
  FROM USER_TAB_COLUMNS
 WHERE (TABLE_NAME = 'TGFCAB' AND COLUMN_NAME IN (
            'NUNOTA', 'CODTIPOPER', 'DHTIPOPER', 'NUMCONTRATO', 'CODPARC',
            'CONFIRMADA'
       ))
    OR (TABLE_NAME = 'TGFITE' AND COLUMN_NAME IN (
            'NUNOTA', 'SEQUENCIA', 'CODPROD'
       ))
    OR (TABLE_NAME = 'TGFPRO' AND COLUMN_NAME = 'AD_CATFISCAL')
    OR (TABLE_NAME = 'TGFPAR' AND COLUMN_NAME IN ('CODPARC', 'SIMPLES'))
    OR (TABLE_NAME = 'TGFIMA' AND COLUMN_NAME IN ('CODIGO', 'TIPO'))
    OR (TABLE_NAME = 'TGFTOP' AND COLUMN_NAME IN ('CODTIPOPER', 'DHALTER'))
    OR (TABLE_NAME = 'TSIPAR' AND COLUMN_NAME IN (
            'CHAVE', 'CODUSU', 'LOGICO', 'INTEIRO', 'TEXTO'
       ))
 ORDER BY TABLE_NAME, COLUMN_NAME;

-- 2. Confere os parametros globais. Criacao/correcao deve ocorrer pelo cadastro
-- administrativo aprovado; este script nao executa DML em TSIPAR.
SELECT CHAVE,
       TIPO,
       LOGICO,
       INTEIRO,
       TEXTO
  FROM TSIPAR
 WHERE CODUSU = 0
   AND CHAVE IN (
       'ZTLWTOPAT', 'ZTLWTOPAP', 'ZTLWTOPOR', 'ZTLWTOPRE',
       'ZTLWTOPNR', 'ZTLWCSLLS', 'ZTLWCSLLN', 'ZTLWIMAPAR'
   )
 ORDER BY CHAVE;

-- Valores esperados antes da ativacao:
-- ZTLWTOPAT  (LOGICO)  = N durante deploy/homologacao inicial.
-- ZTLWTOPAP  (LOGICO)  = N ate o aceite fiscal formal.
-- ZTLWTOPOR  (TEXTO)   = TOPs de origem elegiveis, separadas por ponto e virgula.
-- ZTLWTOPRE  (INTEIRO) = 21, apos confirmacao no ambiente.
-- ZTLWTOPNR  (INTEIRO) = 28, apos confirmacao no ambiente.
-- ZTLWCSLLS  (TEXTO)   = CESSAO DIREITO;CONTRATO HARDWARE;ERGONOMIA;
--                         SUPORTE, MANUT. E INSTAL. SOFTWARE;TREINAMENTO
-- ZTLWCSLLN  (TEXTO)   = HARDWARE;LIMPEZA/LUBRIFICACAO;LOCACAO;
--                         SOFTWARE EMBARCADO
-- A procedure normaliza maiusculas e acentos, mas preserva o ponto e virgula
-- como separador. Nao usar virgula como separador de categorias.
-- ZTLWIMAPAR (TEXTO)   = valor de TGFIMA.TIPO que identifica registros de
--                         parceiro. O valor deve ser confirmado pelo resultado
--                         da consulta 6 e por um parceiro conhecido.

-- 3. Confere as versoes cadastradas das TOPs parametrizadas.
SELECT P.CHAVE,
       P.INTEIRO AS CODTIPOPER,
       MAX(T.DHALTER) AS DHTIPOPER_VIGENTE,
       COUNT(T.CODTIPOPER) AS QUANTIDADE_VERSOES
  FROM TSIPAR P
  LEFT JOIN TGFTOP T
    ON T.CODTIPOPER = P.INTEIRO
 WHERE P.CODUSU = 0
   AND P.CHAVE IN ('ZTLWTOPRE', 'ZTLWTOPNR')
 GROUP BY P.CHAVE, P.INTEIRO
 ORDER BY P.CHAVE;

-- 4. Inventaria as categorias efetivamente usadas pelos produtos.
SELECT TRIM(AD_CATFISCAL) AS CATEGORIA_FISCAL,
       COUNT(*) AS QUANTIDADE_PRODUTOS
  FROM TGFPRO
 WHERE AD_CATFISCAL IS NOT NULL
 GROUP BY TRIM(AD_CATFISCAL)
 ORDER BY TRIM(AD_CATFISCAL);

-- 5. Confere os valores existentes para Simples Nacional.
SELECT NVL(SIMPLES, '<NULO>') AS SIMPLES,
       COUNT(*) AS QUANTIDADE_PARCEIROS
  FROM TGFPAR
 GROUP BY NVL(SIMPLES, '<NULO>')
 ORDER BY SIMPLES;

-- 6. Confere os discriminadores existentes na tabela Outros Impostos.
-- Validar com o cadastro do parceiro antes de preencher ZTLWIMAPAR.
SELECT NVL(TIPO, '<NULO>') AS TIPO,
       COUNT(*) AS QUANTIDADE_REGISTROS,
       COUNT(DISTINCT CODIGO) AS QUANTIDADE_CODIGOS
  FROM TGFIMA
 GROUP BY NVL(TIPO, '<NULO>')
 ORDER BY TIPO;

-- 7. Confirma a compilacao da procedure.
SELECT OBJECT_NAME,
       OBJECT_TYPE,
       STATUS,
       LAST_DDL_TIME
  FROM USER_OBJECTS
 WHERE OBJECT_NAME = 'ZTLW_STP_TOP_SERVICO';

SELECT NAME,
       TYPE,
       LINE,
       POSITION,
       TEXT
  FROM USER_ERRORS
 WHERE NAME = 'ZTLW_STP_TOP_SERVICO'
 ORDER BY SEQUENCE;

-- 8. A execucao funcional deve ser feita somente com nota controlada e dentro
-- de transacao de homologacao. Nao chamar a procedure por este script generico.
