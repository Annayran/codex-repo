CREATE OR REPLACE PROCEDURE "STP_SETSEQPREVEST_PRA" (
       P_CODUSU NUMBER,
       P_IDSESSAO VARCHAR2,
       P_QTDLINHAS NUMBER,
       P_MENSAGEM OUT VARCHAR2
) AS
       PARAM_P_NEWSEQ NUMBER;
       PARAM_P_QTDNECESSARIA FLOAT;

       FIELD_CODPROD NUMBER;
       FIELD_NUNOTA NUMBER;
       FIELD_SEQPREV NUMBER;
       FIELD_SEQUENCIA NUMBER;

       V_CODUSUINC INT;
       V_CODUSU INT;
       V_CODUSUINC_ORIG INT;
       V_CODUSU_ORIG INT;
       V_QTDORIGEM TGFITE.QTDNEG%TYPE;
       V_QTDDESTINO TGFITE.QTDNEG%TYPE;
       V_SEQRESERVA_DEST INT;
       V_NUNOTA TGFCAB.NUNOTA%TYPE;
       V_SEQUENCIA TGFITE.SEQUENCIA%TYPE;
       V_QTDALTERACAO TGFITE.QTDNEG%TYPE;
       V_ORDEMOLD INT;
       V_DTPREVORIG AD_TGFSEQENT.DTPREV%TYPE;
       V_CODUSUACESS TSIUSU.CODVEND%TYPE;
       V_CODUSUVEN TSIUSU.CODVEND%TYPE;
       V_CONT INT := 0;
       V_IDIPROC INT;
       V_CODPROD TGFPRO.CODPROD%TYPE;
       V_CODUSULOGADO INT;
       V_GESCOM INT;
       V_SEQ_TEMP INT;
       V_SEQ_AUX INT;
       V_SEQ_AUX2 INT;

       FUNCTION F_SEQENT_LIVRE (
              P_NUNOTA AD_TGFSEQENT.NUNOTA%TYPE,
              P_SEQUENCIA AD_TGFSEQENT.SEQUENCIA%TYPE,
              P_SEQ_DESEJADA AD_TGFSEQENT.SEQENT%TYPE
       ) RETURN AD_TGFSEQENT.SEQENT%TYPE
       IS
              V_SEQ AD_TGFSEQENT.SEQENT%TYPE;
              V_EXISTE INT;
       BEGIN
              V_SEQ := P_SEQ_DESEJADA;

              LOOP
                  SELECT COUNT(1)
                    INTO V_EXISTE
                    FROM AD_TGFSEQENT
                   WHERE NUNOTA = P_NUNOTA
                     AND SEQUENCIA = P_SEQUENCIA
                     AND SEQENT = V_SEQ;

                  IF V_EXISTE = 0 THEN
                      RETURN V_SEQ;
                  END IF;

                  V_SEQ := V_SEQ - 1;
              END LOOP;
       END;

       PROCEDURE P_LIBERA_SEQENT (
              P_NUNOTA AD_TGFSEQENT.NUNOTA%TYPE,
              P_SEQUENCIA AD_TGFSEQENT.SEQUENCIA%TYPE,
              P_SEQENT AD_TGFSEQENT.SEQENT%TYPE
       )
       IS
              V_EXISTE INT;
              V_SEQ_LIVRE AD_TGFSEQENT.SEQENT%TYPE;
       BEGIN
              IF P_SEQENT IS NULL THEN
                  RETURN;
              END IF;

              SELECT COUNT(1)
                INTO V_EXISTE
                FROM AD_TGFSEQENT
               WHERE NUNOTA = P_NUNOTA
                 AND SEQUENCIA = P_SEQUENCIA
                 AND SEQENT = P_SEQENT;

              IF V_EXISTE > 0 THEN
                  V_SEQ_LIVRE := F_SEQENT_LIVRE(P_NUNOTA, P_SEQUENCIA, P_SEQENT - 1000000);

                  UPDATE AD_TGFSEQENT E
                     SET E.SEQENT = V_SEQ_LIVRE
                   WHERE E.NUNOTA = P_NUNOTA
                     AND E.SEQUENCIA = P_SEQUENCIA
                     AND E.SEQENT = P_SEQENT;
              END IF;
       END;
BEGIN
       V_CODUSULOGADO := NVL(P_CODUSU, STP_GET_CODUSULOGADO);

       PARAM_P_NEWSEQ := ACT_INT_PARAM(P_IDSESSAO, 'P_NEWSEQ');
       PARAM_P_QTDNECESSARIA := ACT_DEC_PARAM(P_IDSESSAO, 'P_QTDNECESSARIA');

       IF P_QTDLINHAS > 1 THEN
            P_MENSAGEM := '<b>Selecione apenas uma linha.</b>';
            RETURN;
       END IF;

       FOR I IN 1..P_QTDLINHAS
       LOOP
           FIELD_CODPROD := ACT_INT_FIELD(P_IDSESSAO, I, 'CODPROD');
           FIELD_NUNOTA := ACT_INT_FIELD(P_IDSESSAO, I, 'NUNOTA');
           FIELD_SEQPREV := ACT_INT_FIELD(P_IDSESSAO, I, 'SEQPREV');
           FIELD_SEQUENCIA := ACT_INT_FIELD(P_IDSESSAO, I, 'SEQUENCIA');

           SELECT O.CODUSUINC,
                  O.CODUSU,
                  O.QTDPENDENTE,
                  O.AD_SEQRESERVA,
                  O.NUNOTA,
                  O.SEQUENCIA,
                  O.DTPREVENT,
                  ACE.CODUSU,
                  VEN.CODUSU,
                  O.IDIPROC,
                  O.CODPROD
             INTO V_CODUSUINC,
                  V_CODUSU,
                  V_QTDDESTINO,
                  V_SEQRESERVA_DEST,
                  V_NUNOTA,
                  V_SEQUENCIA,
                  V_DTPREVORIG,
                  V_CODUSUACESS,
                  V_CODUSUVEN,
                  V_IDIPROC,
                  V_CODPROD
             FROM AD_VGFRESEST O
             JOIN TGFCAB CAB ON O.NUNOTA = CAB.NUNOTA
             JOIN TGFPAR PAR ON CAB.CODPARC = PAR.CODPARC
             LEFT JOIN TGFVEN ACE ON PAR.CODASSESSOR = ACE.CODVEND
             LEFT JOIN TGFVEN VEN ON CAB.CODVEND = VEN.CODVEND
            WHERE O.CODPROD = FIELD_CODPROD
              AND O.ORDEM = PARAM_P_NEWSEQ;

           SELECT E.QTDPENDENTE,
                  E.ORDEM,
                  E.CODUSUINC,
                  E.CODUSU
             INTO V_QTDORIGEM,
                  V_ORDEMOLD,
                  V_CODUSUINC_ORIG,
                  V_CODUSU_ORIG
             FROM AD_VGFRESEST E
            WHERE E.CODPROD = FIELD_CODPROD
              AND E.NUNOTA = FIELD_NUNOTA
              AND E.SEQPREV = FIELD_SEQPREV
              AND E.SEQUENCIA = FIELD_SEQUENCIA;

           SELECT COUNT(1)
             INTO V_GESCOM
             FROM TSIUSU
            WHERE CODUSU = V_CODUSULOGADO
              AND AD_GESCOM = 'S';

           -- GESCOM pode mover qualquer pedido em qualquer direcao.
           -- Usuario comum so pode mover o proprio pedido para baixo na fila.
           IF V_GESCOM = 0 THEN
               IF V_CODUSULOGADO <> NVL(V_CODUSUINC_ORIG, -1)
                  AND V_CODUSULOGADO <> NVL(V_CODUSU_ORIG, -1)
               THEN
                   P_MENSAGEM := '<b>Usuario comum so pode alterar o proprio pedido na fila.</b>';
                   RETURN;
               END IF;

               IF PARAM_P_NEWSEQ <= V_ORDEMOLD THEN
                   P_MENSAGEM := '<b>Usuario comum so pode mover o pedido para baixo na fila, cedendo/emprestando material. Para mover para cima, acione um usuario de gestao.</b>';
                   RETURN;
               END IF;
           END IF;

           IF PARAM_P_QTDNECESSARIA > V_QTDDESTINO THEN
               P_MENSAGEM := '<b>A quantidade necessÃ¡ria nÃ£o pode ser maior que a quantidade do pedido!</b>';
               RETURN;
           ELSE
               V_QTDALTERACAO := CASE
                                      WHEN PARAM_P_QTDNECESSARIA IS NULL THEN V_QTDORIGEM
                                      ELSE PARAM_P_QTDNECESSARIA
                                  END;

               IF V_QTDDESTINO < V_QTDALTERACAO THEN
                   P_MENSAGEM := '<b>A quantidade disponÃ­vel no destino Ã© menor que a quantidade necessÃ¡ria.</b>';
                   RETURN;
               END IF;
           END IF;

           IF V_QTDORIGEM = V_QTDDESTINO
              AND V_QTDORIGEM = V_QTDALTERACAO
           THEN
               V_SEQ_TEMP := F_SEQENT_LIVRE(V_NUNOTA, V_SEQUENCIA, -999999999);

               UPDATE AD_TGFSEQENT E
                  SET SEQENT = V_SEQ_TEMP
                WHERE E.NUNOTA = V_NUNOTA
                  AND E.SEQENT = V_SEQRESERVA_DEST
                  AND E.SEQUENCIA = V_SEQUENCIA;

               V_SEQ_AUX := V_SEQRESERVA_DEST;
               P_LIBERA_SEQENT(FIELD_NUNOTA, FIELD_SEQUENCIA, V_SEQ_AUX);

               UPDATE AD_TGFSEQENT E
                  SET SEQENT = V_SEQ_AUX
                WHERE E.NUNOTA = FIELD_NUNOTA
                  AND E.SEQENT = FIELD_SEQPREV
                  AND E.SEQUENCIA = FIELD_SEQUENCIA;

               V_SEQ_AUX2 := FIELD_SEQPREV;
               P_LIBERA_SEQENT(V_NUNOTA, V_SEQUENCIA, V_SEQ_AUX2);

               UPDATE AD_TGFSEQENT E
                  SET SEQENT = V_SEQ_AUX2
                WHERE E.NUNOTA = V_NUNOTA
                  AND E.SEQENT = V_SEQ_TEMP
                  AND E.SEQUENCIA = V_SEQUENCIA;

               V_CONT := 1;

           ELSIF V_QTDORIGEM > V_QTDALTERACAO
                 AND V_QTDDESTINO = V_QTDALTERACAO
           THEN
               V_SEQ_AUX := FIELD_SEQPREV - 2;
               P_LIBERA_SEQENT(V_NUNOTA, V_SEQUENCIA, V_SEQ_AUX);

               UPDATE AD_TGFSEQENT E
                  SET SEQENT = V_SEQ_AUX
                WHERE E.NUNOTA = V_NUNOTA
                  AND E.SEQENT = V_SEQRESERVA_DEST
                  AND E.SEQUENCIA = V_SEQUENCIA;

               UPDATE AD_TGFSEQENT E
                  SET QTDENT = QTDENT - V_QTDALTERACAO
                WHERE E.NUNOTA = FIELD_NUNOTA
                  AND E.SEQENT = FIELD_SEQPREV
                  AND E.SEQUENCIA = FIELD_SEQUENCIA;

               V_SEQ_AUX2 := V_SEQRESERVA_DEST - 2;
               P_LIBERA_SEQENT(FIELD_NUNOTA, FIELD_SEQUENCIA, V_SEQ_AUX2);

               INSERT INTO AD_TGFSEQENT (
                   NUNOTA,
                   SEQUENCIA,
                   CODPROD,
                   SEQENT,
                   QTDENT,
                   DTPREV,
                   DTLANC,
                   CODUSU
               ) VALUES (
                   FIELD_NUNOTA,
                   FIELD_SEQUENCIA,
                   V_CODPROD,
                   V_SEQ_AUX2,
                   V_QTDALTERACAO,
                   V_DTPREVORIG,
                   SYSDATE,
                   V_CODUSULOGADO
               );

               V_CONT := 1;

           ELSIF V_QTDORIGEM = V_QTDALTERACAO
                 AND V_QTDDESTINO > V_QTDALTERACAO
           THEN
               V_SEQ_AUX := V_SEQRESERVA_DEST - 2;
               P_LIBERA_SEQENT(FIELD_NUNOTA, FIELD_SEQUENCIA, V_SEQ_AUX);

               UPDATE AD_TGFSEQENT E
                  SET E.SEQENT = V_SEQ_AUX
                WHERE E.NUNOTA = FIELD_NUNOTA
                  AND E.SEQENT = FIELD_SEQPREV
                  AND E.SEQUENCIA = FIELD_SEQUENCIA;

               UPDATE AD_TGFSEQENT E
                  SET QTDENT = E.QTDENT - V_QTDALTERACAO
                WHERE E.NUNOTA = V_NUNOTA
                  AND E.SEQENT = V_SEQRESERVA_DEST
                  AND E.SEQUENCIA = V_SEQUENCIA;

               V_SEQ_AUX2 := FIELD_SEQPREV + 1;
               P_LIBERA_SEQENT(V_NUNOTA, V_SEQUENCIA, V_SEQ_AUX2);

               INSERT INTO AD_TGFSEQENT (
                   NUNOTA,
                   SEQUENCIA,
                   CODPROD,
                   SEQENT,
                   QTDENT,
                   DTPREV,
                   DTLANC
               ) VALUES (
                   V_NUNOTA,
                   V_SEQUENCIA,
                   V_CODPROD,
                   V_SEQ_AUX2,
                   V_QTDALTERACAO,
                   V_DTPREVORIG,
                   SYSDATE
               );

               V_CONT := 1;

           ELSIF V_QTDDESTINO > V_QTDALTERACAO
                 AND V_QTDALTERACAO < V_QTDORIGEM
           THEN
               V_SEQ_AUX := FIELD_SEQPREV + 2;
               P_LIBERA_SEQENT(FIELD_NUNOTA, FIELD_SEQUENCIA, V_SEQ_AUX);

               UPDATE AD_TGFSEQENT E
                  SET E.QTDENT = E.QTDENT - V_QTDALTERACAO,
                      E.SEQENT = V_SEQ_AUX
                WHERE E.NUNOTA = FIELD_NUNOTA
                  AND E.SEQENT = FIELD_SEQPREV
                  AND E.SEQUENCIA = FIELD_SEQUENCIA;

               V_SEQ_AUX2 := V_SEQRESERVA_DEST - 2;
               P_LIBERA_SEQENT(FIELD_NUNOTA, FIELD_SEQUENCIA, V_SEQ_AUX2);

               INSERT INTO AD_TGFSEQENT (
                   NUNOTA,
                   SEQUENCIA,
                   CODPROD,
                   SEQENT,
                   QTDENT,
                   DTPREV,
                   DTLANC
               ) VALUES (
                   FIELD_NUNOTA,
                   FIELD_SEQUENCIA,
                   V_CODPROD,
                   V_SEQ_AUX2,
                   V_QTDALTERACAO,
                   V_DTPREVORIG,
                   SYSDATE
               );

               UPDATE AD_TGFSEQENT E
                  SET QTDENT = QTDENT - V_QTDALTERACAO
                WHERE E.NUNOTA = V_NUNOTA
                  AND E.SEQENT = V_SEQRESERVA_DEST
                  AND E.SEQUENCIA = V_SEQUENCIA;

               V_SEQ_AUX := FIELD_SEQPREV;
               P_LIBERA_SEQENT(V_NUNOTA, V_SEQUENCIA, V_SEQ_AUX);

               INSERT INTO AD_TGFSEQENT (
                   NUNOTA,
                   SEQUENCIA,
                   CODPROD,
                   SEQENT,
                   QTDENT,
                   DTPREV,
                   DTLANC
               ) VALUES (
                   V_NUNOTA,
                   V_SEQUENCIA,
                   V_CODPROD,
                   V_SEQ_AUX,
                   V_QTDALTERACAO,
                   V_DTPREVORIG,
                   SYSDATE
               );

               V_CONT := 1;
           END IF;
       END LOOP;

       IF V_CONT <> 0 THEN
           P_MENSAGEM := 'SequÃªncia atualizada com sucesso!<br> Nova sequÃªncia: ' ||
                         PARAM_P_NEWSEQ || '<br> SequÃªncia anterior: ' || V_ORDEMOLD;
       ELSE
           P_MENSAGEM := '<b>Houve algum erro ao trocar a sequÃªncia, a alteraÃ§Ã£o nÃ£o estÃ¡ dentro das possibilidades mapeadas!</b>';
       END IF;
END;
/
