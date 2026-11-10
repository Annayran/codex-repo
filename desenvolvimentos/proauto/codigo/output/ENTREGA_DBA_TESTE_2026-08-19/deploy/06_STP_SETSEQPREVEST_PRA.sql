-- Fase 2: fonte conferida contra o baseline TESTE de 2026-08-13.
-- A chamada BLOQUEAR_PRODUTO serializa a prioridade manual com os fluxos automaticos.
CREATE OR REPLACE PROCEDURE "STP_SETSEQPREVEST_PRA" (
       P_CODUSU NUMBER,        -- Código do usuário logado
       P_IDSESSAO VARCHAR2,    -- Identificador da execução. Serve para buscar informações dos parâmetros/campos da execução.
       P_QTDLINHAS NUMBER,     -- Informa a quantidade de registros selecionados no momento da execução.
       P_MENSAGEM OUT VARCHAR2 -- Caso seja passada uma mensagem aqui, ela será exibida como uma informação ao usuário.
) AS
       PARAM_P_NEWSEQ NUMBER;
       PARAM_P_QTDNECESSARIA FLOAT;
       FIELD_CODPROD NUMBER;
       FIELD_NUNOTA NUMBER;
       FIELD_SEQPREV NUMBER;
       FIELD_SEQUENCIA NUMBER;
       V_CODUSUINC      INT;
       V_CODUSU       INT;
       V_QTDORIGEM   TGFITE.QTDNEG%TYPE;
       V_QTDDESTINO     TGFITE.QTDNEG%TYPE;
       V_SEQRESERVA_DEST    INT;
       V_NUNOTA         TGFCAB.NUNOTA%TYPE;
       V_SEQUENCIA TGFITE.SEQUENCIA%TYPE;   
       V_QTDALTERACAO   TGFITE.QTDNEG%TYPE;
       V_ORDEMOLD   INT;
       V_DTPREVORIG AD_TGFSEQENT.DTPREV%TYPE;
       V_CODUSUACESS   TSIUSU.CODVEND%TYPE;
       V_CODUSUVEN  TSIUSU.CODVEND%TYPE;
       V_CONT INT :=0 ;
        V_IDIPROC INT;
        V_COUNT INT;
        V_CODPROD   TGFPRO.CODPROD%TYPE;
        V_SEQ_TEMP  AD_TGFSEQENT.SEQENT%TYPE;
        V_EXISTE_SEQ INT;
BEGIN

       -- Os valores informados pelo formulário de parâmetros, podem ser obtidos com as funções:
       --     ACT_INT_PARAM
       --     ACT_DEC_PARAM
       --     ACT_TXT_PARAM
       --     ACT_DTA_PARAM
       -- Estas funções recebem 2 argumentos:
       --     ID DA SESSÃO - Identificador da execução (Obtido através de P_IDSESSAO))
       --     NOME DO PARAMETRO - Determina qual parametro deve se deseja obter.

       PARAM_P_NEWSEQ := ACT_INT_PARAM(P_IDSESSAO, 'P_NEWSEQ');
       PARAM_P_QTDNECESSARIA := ACT_DEC_PARAM(P_IDSESSAO, 'P_QTDNECESSARIA');
       
       IF P_QTDLINHAS > 1 THEN 
            P_MENSAGEM := '<b>Selecione apenas uma linha.</b>';
            RETURN;
       END IF;

       FOR I IN 1..P_QTDLINHAS -- Este loop permite obter o valor de campos dos registros envolvidos na execução.
       LOOP                    -- A variável "I" representa o registro corrente.
           -- Para obter o valor dos campos utilize uma das seguintes funções:
           --     ACT_INT_FIELD (Retorna o valor de um campo tipo NUMÉRICO INTEIRO))
           --     ACT_DEC_FIELD (Retorna o valor de um campo tipo NUMÉRICO DECIMAL))
           --     ACT_TXT_FIELD (Retorna o valor de um campo tipo TEXTO),
           --     ACT_DTA_FIELD (Retorna o valor de um campo tipo DATA)
           -- Estas funções recebem 3 argumentos:
           --     ID DA SESSÃO - Identificador da execução (Obtido através do parâmetro P_IDSESSAO))
           --     NÚMERO DA LINHA - Relativo a qual linha selecionada.
           --     NOME DO CAMPO - Determina qual campo deve ser obtido.
           FIELD_CODPROD := ACT_INT_FIELD(P_IDSESSAO, I, 'CODPROD');
           FIELD_NUNOTA := ACT_INT_FIELD(P_IDSESSAO, I, 'NUNOTA');
           FIELD_SEQPREV := ACT_INT_FIELD(P_IDSESSAO, I, 'SEQPREV');
           FIELD_SEQUENCIA := ACT_INT_FIELD(P_IDSESSAO, I, 'SEQUENCIA');

           PRA_PKG_FILARES.BLOQUEAR_PRODUTO(FIELD_CODPROD);

       --CARREGA AS VARIAVEIS DO DESTINO DO PEDIDO (QUE IRÁ CEDER A FILA)
       SELECT O.CODUSUINC
            , O.CODUSU
            , O.QTDPENDENTE
            , O.AD_SEQRESERVA
            , O.NUNOTA
            , O.SEQUENCIA
            ,O.DTPREVENT
            , ACE.CODUSU
            , VEN.CODUSU
            , O.IDIPROC
            , O.CODPROD
       INTO V_CODUSUINC
           ,V_CODUSU
           ,V_QTDDESTINO
           ,V_SEQRESERVA_DEST
           ,V_NUNOTA
           ,V_SEQUENCIA
           ,V_DTPREVORIG
           ,V_CODUSUACESS
           ,V_CODUSUVEN
           ,V_IDIPROC
           ,V_CODPROD
       FROM AD_VGFRESEST O
        JOIN TGFCAB CAB ON O.NUNOTA = CAB.NUNOTA
        JOIN TGFPAR PAR ON CAB.CODPARC = PAR.CODPARC
        LEFT JOIN TGFVEN ACE ON PAR.CODASSESSOR = ACE.CODVEND
        LEFT JOIN TGFVEN VEN ON CAB.CODVEND = VEN.CODVEND
       WHERE O.CODPROD = FIELD_CODPROD
        AND O.ORDEM = PARAM_P_NEWSEQ;
        
        --VARIAVEIS DO PEDIDO ORIGEM (QUE IRÁ OCUPAR A FILA)
      SELECT E.QTDPENDENTE
            ,E.ORDEM
       INTO V_QTDORIGEM
            ,V_ORDEMOLD
       FROM AD_VGFRESEST E
       WHERE E.CODPROD = FIELD_CODPROD
        AND E.NUNOTA = FIELD_NUNOTA
        AND E.SEQPREV = FIELD_SEQPREV
        AND E.SEQUENCIA = FIELD_SEQUENCIA
        ;
        
        SELECT COUNT(1)
        INTO V_COUNT
        FROM TSIUSU
        WHERE CODUSU = STP_GET_CODUSULOGADO
            AND AD_GESCOM = 'S';
        

        
        --VALIDA O USUÁRIO
        IF V_COUNT = 0
            AND STP_GET_CODUSULOGADO <> NVL(V_CODUSUINC, -1)
            AND STP_GET_CODUSULOGADO <> NVL(V_CODUSU, -1)
            AND STP_GET_CODUSULOGADO <> NVL(V_CODUSUACESS, -1)
            AND STP_GET_CODUSULOGADO <> NVL(V_CODUSUVEN, -1)
        THEN
            P_MENSAGEM := '<b>Somente o usuário da inclusão da fila destino pode realizar a alteração.</b>';
            RETURN;
        END IF;

        --USUARIO COMUM SO PODE MOVER PARA BAIXO NA FILA
        IF V_COUNT = 0 AND PARAM_P_NEWSEQ <= V_ORDEMOLD THEN
            P_MENSAGEM := '<b>Usuario comum so pode mover o pedido para baixo na fila, cedendo/emprestando material. Para mover para cima, acione um usuario de gestao.</b>';
            RETURN;
        END IF;

        --Valida quantidade
        IF PARAM_P_QTDNECESSARIA > V_QTDDESTINO THEN
            P_MENSAGEM := '<b>A quantidade necessária não pode ser maior que a quantidade do pedido!</b>';
            RETURN;
         ELSE   
             V_QTDALTERACAO := CASE WHEN PARAM_P_QTDNECESSARIA IS NULL 
                                    THEN V_QTDORIGEM
                                    ELSE PARAM_P_QTDNECESSARIA 
                            END;
                            
            IF V_QTDDESTINO < V_QTDALTERACAO THEN
                P_MENSAGEM := '<b>A quantidade disponivel no destino é menor que a quantidade necessária.</b>';
                RETURN;
            END IF;
        END IF;


        
        --AJUSTA A QUANTIDADE
        SAVEPOINT SP_SETSEQPREVEST;
  
        --VERIFICA SE É UMA ALTERAÇÃO TOTAL (FORMA MAIS SIMPLES)
        IF V_QTDORIGEM = V_QTDDESTINO AND
           V_QTDORIGEM = V_QTDALTERACAO  
        THEN
  
         SELECT NVL(MIN(SEQENT), 0) - 1000000
           INTO V_SEQ_TEMP
           FROM AD_TGFSEQENT
          WHERE NUNOTA = V_NUNOTA
            AND SEQUENCIA = V_SEQUENCIA;

         --RETIRA TEMPORARIAMENTE O DESTINO DA POSICAO PARA EVITAR PK DUPLICADA
         UPDATE AD_TGFSEQENT E
         SET SEQENT = V_SEQ_TEMP
         WHERE E.NUNOTA = V_NUNOTA
            AND E.SEQENT = V_SEQRESERVA_DEST
            AND E.SEQUENCIA = V_SEQUENCIA ;

         SELECT COUNT(1)
           INTO V_EXISTE_SEQ
           FROM AD_TGFSEQENT E
          WHERE E.NUNOTA = FIELD_NUNOTA
            AND E.SEQUENCIA = FIELD_SEQUENCIA
            AND E.SEQENT = V_SEQRESERVA_DEST;

         IF V_EXISTE_SEQ > 0 THEN
             ROLLBACK TO SP_SETSEQPREVEST;
             P_MENSAGEM := '<b>Nao foi possivel alterar a sequencia: a posicao de destino ja esta ocupada por outro registro. Verifique a fila ou acione um usuario de gestao.</b>';
             RETURN;
         END IF;

         --ATUALIZA O PEDIDO ORIGEM
         UPDATE AD_TGFSEQENT E
         SET SEQENT = V_SEQRESERVA_DEST
         WHERE E.NUNOTA = FIELD_NUNOTA
            AND E.SEQENT = FIELD_SEQPREV
            AND E.SEQUENCIA = FIELD_SEQUENCIA ;

         SELECT COUNT(1)
           INTO V_EXISTE_SEQ
           FROM AD_TGFSEQENT E
          WHERE E.NUNOTA = V_NUNOTA
            AND E.SEQUENCIA = V_SEQUENCIA
            AND E.SEQENT = FIELD_SEQPREV;

         IF V_EXISTE_SEQ > 0 THEN
             ROLLBACK TO SP_SETSEQPREVEST;
             P_MENSAGEM := '<b>Nao foi possivel alterar a sequencia: a posicao original ja esta ocupada por outro registro. Verifique a fila ou acione um usuario de gestao.</b>';
             RETURN;
         END IF;
            
          --ATUALIZA O PEDIDO DESTINO
         UPDATE AD_TGFSEQENT E
         SET SEQENT = FIELD_SEQPREV
         WHERE E.NUNOTA = V_NUNOTA
            AND E.SEQENT = V_SEQ_TEMP
            AND E.SEQUENCIA = V_SEQUENCIA ;
        
        V_CONT := 1;
 
     --SE A QUANTIDADE DO DESTINO(VAI TOMAR LUGAR) FOR MENOR QUE DA ORIGEM(VAI DAR LUGAR)
     -- E A QUANTIDADE ALTERADA FOR O MENOR QUE A ORIGEM E IGUAL O DESTINO
        ELSIF V_QTDORIGEM > V_QTDALTERACAO AND V_QTDDESTINO = V_QTDALTERACAO THEN  
 
            --ATUALIZA O PEDIDO DESTINO (deu lugar)
             UPDATE AD_TGFSEQENT E
             SET SEQENT = FIELD_SEQPREV - 2
             WHERE E.NUNOTA = V_NUNOTA
                AND E.SEQENT = V_SEQRESERVA_DEST
                AND E.SEQUENCIA = V_SEQUENCIA ;
          
         --ATUALIZA O PEDIDO ORIGEM (tomou lugar)
         UPDATE AD_TGFSEQENT E
         SET QTDENT = QTDENT - V_QTDALTERACAO
         WHERE E.NUNOTA = FIELD_NUNOTA
            AND E.SEQENT = FIELD_SEQPREV
            AND E.SEQUENCIA = FIELD_SEQUENCIA ;
                        
          
              --COLOCA A DIFERENÇA DO DESTINO NO LOCAL DA ORIGEM   
                INSERT INTO AD_TGFSEQENT (NUNOTA, 
                                        SEQUENCIA, 
                                        CODPROD,
                                        SEQENT, 
                                        QTDENT, 
                                        DTPREV, 
                                        DTLANC,
                                        CODUSU)
                VALUES (FIELD_NUNOTA
                        ,FIELD_SEQUENCIA
                        ,V_CODPROD
                        ,V_SEQRESERVA_DEST - 2
                        ,V_QTDALTERACAO
                        ,V_DTPREVORIG
                        ,SYSDATE
                        ,STP_GET_CODUSULOGADO
                        );  
        V_CONT := 1;
        
        --SE A QUANTIDADE DO DESTINO FOR MAIOR QUE DA ORIGEM E A QUANTIDADE ALTERADA FOR O PEDIDO ORIGEM TODO
        ELSIF V_QTDORIGEM = V_QTDALTERACAO AND V_QTDDESTINO > V_QTDALTERACAO THEN    
            
                   
             --ATUALIZA O PEDIDO ORIGEM
             UPDATE AD_TGFSEQENT E
             SET  E.SEQENT = V_SEQRESERVA_DEST - 2
            -- SET  E.QTDENT = E.QTDENT - V_QTDALTERACAO
             WHERE E.NUNOTA = FIELD_NUNOTA
                AND E.SEQENT = FIELD_SEQPREV
                AND E.SEQUENCIA = FIELD_SEQUENCIA ;
                
                              
             --ATUALIZA O DESTINO
                 UPDATE AD_TGFSEQENT E
                 SET QTDENT = E.QTDENT - V_QTDALTERACAO
                 WHERE E.NUNOTA = V_NUNOTA
                    AND E.SEQENT = V_SEQRESERVA_DEST
                    AND E.SEQUENCIA = V_SEQUENCIA ; 
                 
                 --COLOCA A DIFERENÇA DO DESTINO NO LOCAL DA ORIGEM   
                INSERT INTO AD_TGFSEQENT (NUNOTA, 
                                        SEQUENCIA, 
                                        CODPROD,
                                        SEQENT, 
                                        QTDENT, 
                                        DTPREV, 
                                        DTLANC)
                VALUES (V_NUNOTA
                        ,V_SEQUENCIA
                        ,V_CODPROD
                        ,FIELD_SEQPREV + 1
                        ,V_QTDALTERACAO
                        ,V_DTPREVORIG
                        ,SYSDATE
                        );
        V_CONT := 1;
        
         --SE A QUANTIDADE DO DESTINO FOR MAIOR QUE DA ORIGEM E A QUANTIDADE ALTERADA FOR MENOR QUE O PEDIDO ORIGEM 
        ELSIF V_QTDDESTINO > V_QTDALTERACAO  AND V_QTDALTERACAO < V_QTDORIGEM THEN    
        
                 --ATUALIZA O PEDIDO ORIGEM, DEIXANDO O SALDO MENOS O QUE VAI SER ALTERADO
                 UPDATE AD_TGFSEQENT E
                 SET E.QTDENT = E.QTDENT - V_QTDALTERACAO
                    , E.SEQENT = E.SEQENT + 2
                 WHERE E.NUNOTA = FIELD_NUNOTA
                    AND E.SEQENT = FIELD_SEQPREV
                    AND E.SEQUENCIA = FIELD_SEQUENCIA ;
                    
                --COLOCA A DIFERENÇA DO DESTINO NO LOCAL DA ORIGEM   
                INSERT INTO AD_TGFSEQENT (NUNOTA, 
                                        SEQUENCIA, 
                                        CODPROD,
                                        SEQENT, 
                                        QTDENT, 
                                        DTPREV, 
                                        DTLANC)
                VALUES (FIELD_NUNOTA
                        ,FIELD_SEQUENCIA
                        ,V_CODPROD
                        ,V_SEQRESERVA_DEST - 2
                        ,V_QTDALTERACAO
                        ,V_DTPREVORIG
                        ,SYSDATE
                        );
                    
                
             --ATUALIZA O DESTINO
                 UPDATE AD_TGFSEQENT E
                 SET QTDENT = QTDENT - V_QTDALTERACAO
                 WHERE E.NUNOTA = V_NUNOTA
                    AND E.SEQENT = V_SEQRESERVA_DEST
                    AND E.SEQUENCIA = V_SEQUENCIA ; 
                 
                 --COLOCA A DIFERENÇA DO DESTINO NO LOCAL DA ORIGEM   
                INSERT INTO AD_TGFSEQENT (NUNOTA, 
                                        SEQUENCIA, 
                                        CODPROD,
                                        SEQENT, 
                                        QTDENT, 
                                        DTPREV, 
                                        DTLANC)
                VALUES (V_NUNOTA
                        ,V_SEQUENCIA
                        ,V_CODPROD
                        ,FIELD_SEQPREV
                        ,V_QTDALTERACAO
                        ,V_DTPREVORIG
                        ,SYSDATE
                        );
 
         V_CONT := 1;
         
        END IF;
        
       END LOOP;



    IF V_CONT <> 0 THEN
      P_MENSAGEM := 'Sequencia atualizada com sucesso!<br> Nova sequência: ' ||
                    PARAM_P_NEWSEQ || '<br> Sequência anterior: ' || V_ORDEMOLD
                    ;
    ELSE
      P_MENSAGEM := '<b>Ouve algum erro ao trocar a sequencia, a alteração não está dentro das possibilidades mapeadas!</b>' 
                    ;
    END IF;

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        ROLLBACK TO SP_SETSEQPREVEST;
        P_MENSAGEM := '<b>Nao foi possivel alterar a sequencia: a movimentacao geraria posicao duplicada na fila. Verifique se a ordem escolhida ja esta ocupada ou acione um usuario de gestao.</b>';
END;
/
