/*
  PONTO TECNICO AINDA NECESSARIO PARA HOMOLOGAR O BLOQUEIO DA OE
  Somente leitura.

  As 23 TOPs atuais de OE possuem AD_FILARESERVA = 'N'. Portanto, o bloqueio
  deve ocorrer pela atualizacao do pedido de origem durante o faturamento, e nao
  pela inclusao da propria OE na fila. Esta coleta comprova o vinculo real.
*/

-- 01. Estrutura de vinculo entre documento destino e origem nesta versao.
SELECT OWNER,
       TABLE_NAME,
       COLUMN_ID,
       COLUMN_NAME,
       DATA_TYPE,
       NULLABLE
  FROM ALL_TAB_COLUMNS
 WHERE OWNER = 'TESTE'
   AND TABLE_NAME = 'TGFVAR'
 ORDER BY COLUMN_ID;

-- 02. Todos os chamadores compilados da validacao de faturamento.
SELECT OWNER,
       NAME,
       TYPE,
       LINE,
       TEXT
  FROM ALL_SOURCE
 WHERE UPPER(TEXT) LIKE '%STP_VALIDAFAT_FILARES_PRA%'
 ORDER BY OWNER, NAME, TYPE, LINE;

-- 03. Triggers que atuam nos itens e podem participar do fluxo.
SELECT OWNER,
       TRIGGER_NAME,
       STATUS,
       TRIGGER_TYPE,
       TRIGGERING_EVENT,
       TABLE_OWNER,
       TABLE_NAME
  FROM ALL_TRIGGERS
 WHERE TABLE_OWNER = 'TESTE'
   AND TABLE_NAME IN ('TGFITE', 'TGFCAB', 'TGFVAR')
 ORDER BY TABLE_NAME, TRIGGER_NAME;
