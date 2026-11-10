-- 1. Confere a estrutura da tabela antes de compilar as procedures.
SELECT COLUMN_ID,
       COLUMN_NAME,
       DATA_TYPE,
       DATA_LENGTH,
       DATA_PRECISION,
       NULLABLE
  FROM USER_TAB_COLUMNS
 WHERE TABLE_NAME = 'AD_NOTIFFLOW'
 ORDER BY COLUMN_ID;

-- 2. Confirma a existencia e a validade dos objetos.
SELECT OBJECT_NAME, OBJECT_TYPE, STATUS
  FROM USER_OBJECTS
 WHERE OBJECT_NAME IN (
       'AD_NOTIFFLOW',
       'AD_SEQ_NOTIF_FLOW',
       'AD_ESTOQUE_NOTIFICA',
       'AD_CONTROLE_DIFAL'
 )
 ORDER BY OBJECT_TYPE, OBJECT_NAME;

-- 3. Exibe eventuais erros de compilacao das procedures.
SELECT NAME, TYPE, LINE, POSITION, TEXT
  FROM USER_ERRORS
 WHERE NAME IN ('AD_ESTOQUE_NOTIFICA', 'AD_CONTROLE_DIFAL')
 ORDER BY NAME, SEQUENCE;

-- 4. Consulta operacional da fila para a integracao Java.
SELECT IDNOTIF,
       NUNOTA,
       CODTIPOPER,
       ORIGEM,
       CODUSU,
       ASSUNTO,
       STATUS,
       IDTAREFAFLOW,
       DHCRIACAO,
       DHENVIOFLOW,
       DHULTTENTATIVA,
       QTDTENTATIVAS,
       ULTIMOERRO
  FROM AD_NOTIFFLOW
 ORDER BY IDNOTIF DESC;

-- 5. Verifica se ha duplicidade por nota e origem.
SELECT NUNOTA, ORIGEM, COUNT(*) AS QUANTIDADE
  FROM AD_NOTIFFLOW
 GROUP BY NUNOTA, ORIGEM
HAVING COUNT(*) > 1;
