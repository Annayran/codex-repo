/*
  Fila de Reserva - Fase 2
  Aceite tecnico pos-deploy, somente leitura.

  Executar somente depois de um deploy homologado. Os resultados devem ser
  anexados a evidencia da OS. Este arquivo nao executa DML.
*/

-- 1. Todos os objetos publicados devem estar VALID.
SELECT OWNER,
       OBJECT_TYPE,
       OBJECT_NAME,
       STATUS,
       LAST_DDL_TIME
  FROM ALL_OBJECTS
 WHERE OWNER = 'TESTE'
   AND OBJECT_NAME IN (
       'AD_TGFSEQENT',
       'AD_VGFRESEST',
       'STP_GRAVAFILA_PRA',
       'STP_SETSEQPREVEST_PRA',
       'STP_VALIDAFAT_FILARES_PRA',
       'STP_ATUALIZA_PROV_FILA_PRA',
       'TRG_IU_AD_TGFSEQENT_PRA',
       'TRG_IUD_TGFITE_FILRES_PRA'
   )
 ORDER BY OBJECT_TYPE,
          OBJECT_NAME;

-- 2. A consulta deve retornar zero linhas.
SELECT OWNER,
       NAME,
       TYPE,
       LINE,
       POSITION,
       TEXT
  FROM ALL_ERRORS
 WHERE OWNER = 'TESTE'
   AND NAME IN (
       'AD_VGFRESEST',
       'STP_GRAVAFILA_PRA',
       'STP_SETSEQPREVEST_PRA',
       'STP_VALIDAFAT_FILARES_PRA',
       'STP_ATUALIZA_PROV_FILA_PRA',
       'TRG_IU_AD_TGFSEQENT_PRA',
       'TRG_IUD_TGFITE_FILRES_PRA'
   )
 ORDER BY NAME,
          SEQUENCE;

-- 3. Cada linha deve possuir uma ORDEM unica dentro do produto.
SELECT CODPROD,
       ORDEM,
       COUNT(*) AS QUANTIDADE
  FROM TESTE.AD_VGFRESEST
 GROUP BY CODPROD,
          ORDEM
HAVING COUNT(*) > 1
 ORDER BY CODPROD,
          ORDEM;

-- 4. O saldo deve acompanhar exatamente a mesma ordem exibida.
WITH FILA AS (
    SELECT V.*,
           ROW_NUMBER() OVER (
               PARTITION BY V.CODPROD
               ORDER BY V.ORDEM,
                        V.AD_SEQRESERVA,
                        V.NUNOTA,
                        V.SEQUENCIA
           ) AS RN,
           LAG(V.SALDO) OVER (
               PARTITION BY V.CODPROD
               ORDER BY V.ORDEM,
                        V.AD_SEQRESERVA,
                        V.NUNOTA,
                        V.SEQUENCIA
           ) AS SALDO_ANTERIOR
      FROM TESTE.AD_VGFRESEST V
)
SELECT CODPROD,
       ORDEM,
       AD_SEQRESERVA,
       NUNOTA,
       SEQUENCIA,
       QTDPENDENTE,
       SALDO_ANTERIOR,
       SALDO
  FROM FILA
 WHERE ABS(
           SALDO -
           CASE
               WHEN RN = 1
                   THEN FNC_ESTCOMERCIAL_PRA(CODPROD) - QTDPENDENTE
               ELSE SALDO_ANTERIOR - QTDPENDENTE
           END
       ) > 0.000001
 ORDER BY CODPROD,
          ORDEM;

-- 5. Quebras cronologicas devem ser justificadas por prioridade manual.
-- A regra definitiva depende das decisoes RN-06, RN-09 e RN-10.
WITH ORDENADA AS (
    SELECT V.*,
           LAG(V.DTPREVENT) OVER (
               PARTITION BY V.CODPROD
               ORDER BY V.ORDEM,
                        V.AD_SEQRESERVA,
                        V.NUNOTA,
                        V.SEQUENCIA
           ) AS DTPREVENT_ANTERIOR
      FROM TESTE.AD_VGFRESEST V
)
SELECT CODPROD,
       ORDEM,
       AD_SEQRESERVA,
       NUNOTA,
       SEQUENCIA,
       DTPREVENT_ANTERIOR,
       DTPREVENT
  FROM ORDENADA
 WHERE DTPREVENT < DTPREVENT_ANTERIOR
 ORDER BY CODPROD,
          ORDEM;

