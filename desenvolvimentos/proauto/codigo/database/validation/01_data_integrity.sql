/*
  Fila de Reserva - Fase 2
  Diagnostico de integridade do legado, somente leitura.

  Execucao esperada: schema TESTE, Oracle 19c.
  Este arquivo nao executa DML nem altera objetos.
*/

-- 1. Linhas da fila sem item de documento correspondente.
SELECT COUNT(*) AS QTD_ORFAOS_SEM_TGFITE
  FROM TESTE.AD_TGFSEQENT ENT
  LEFT JOIN TGFITE ITE
    ON ITE.NUNOTA = ENT.NUNOTA
   AND ITE.SEQUENCIA = ENT.SEQUENCIA
 WHERE ITE.NUNOTA IS NULL;

-- 2. Produto divergente entre a fila e o item do documento.
SELECT ENT.NUNOTA,
       ENT.SEQUENCIA,
       ENT.CODPROD AS CODPROD_FILA,
       ITE.CODPROD AS CODPROD_ITEM
  FROM TESTE.AD_TGFSEQENT ENT
  JOIN TGFITE ITE
    ON ITE.NUNOTA = ENT.NUNOTA
   AND ITE.SEQUENCIA = ENT.SEQUENCIA
 WHERE NVL(ENT.CODPROD, -1) <> NVL(ITE.CODPROD, -1)
 ORDER BY ENT.NUNOTA,
          ENT.SEQUENCIA;

-- 3. Quantidade total dos fragmentos divergente da quantidade do item.
-- A interpretacao funcional da divergencia deve considerar cancelamentos e
-- historico antes de qualquer correcao de dados.
WITH FILA_AGRUPADA AS (
    SELECT ENT.NUNOTA,
           ENT.SEQUENCIA,
           SUM(NVL(ENT.QTDENT, 0)) AS QTD_FILA,
           SUM(NVL(ENT.QTDENTREGUE, 0)) AS QTD_ENTREGUE_FILA
      FROM TESTE.AD_TGFSEQENT ENT
     GROUP BY ENT.NUNOTA,
              ENT.SEQUENCIA
)
SELECT F.NUNOTA,
       F.SEQUENCIA,
       F.QTD_FILA,
       F.QTD_ENTREGUE_FILA,
       ITE.QTDNEG AS QTD_ITEM,
       F.QTD_FILA - NVL(ITE.QTDNEG, 0) AS DIFERENCA
  FROM FILA_AGRUPADA F
  JOIN TGFITE ITE
    ON ITE.NUNOTA = F.NUNOTA
   AND ITE.SEQUENCIA = F.SEQUENCIA
 WHERE ABS(F.QTD_FILA - NVL(ITE.QTDNEG, 0)) > 0.000001
 ORDER BY ABS(F.QTD_FILA - NVL(ITE.QTDNEG, 0)) DESC,
          F.NUNOTA,
          F.SEQUENCIA;

-- 4. Valores de provisao fora do dominio conhecido.
SELECT NVL(PROVISAO, '<NULL>') AS PROVISAO,
       COUNT(*) AS QUANTIDADE
  FROM TESTE.AD_TGFSEQENT
 WHERE PROVISAO IS NULL
    OR PROVISAO NOT IN ('N', 'S')
 GROUP BY NVL(PROVISAO, '<NULL>')
 ORDER BY PROVISAO;

-- 5. Quantidades nulas, negativas ou com entregue acima da entrada.
SELECT NUNOTA,
       SEQUENCIA,
       SEQENT,
       CODPROD,
       QTDENT,
       QTDENTREGUE
  FROM TESTE.AD_TGFSEQENT
 WHERE QTDENT IS NULL
    OR QTDENT < 0
    OR NVL(QTDENTREGUE, 0) < 0
    OR NVL(QTDENTREGUE, 0) > NVL(QTDENT, 0)
 ORDER BY NUNOTA,
          SEQUENCIA,
          SEQENT;

-- 6. Posicoes repetidas na fila ativa atual.
SELECT CODPROD,
       AD_SEQRESERVA,
       COUNT(*) AS QUANTIDADE
  FROM TESTE.AD_VGFRESEST
 GROUP BY CODPROD,
          AD_SEQRESERVA
HAVING COUNT(*) > 1
 ORDER BY CODPROD,
          AD_SEQRESERVA;

