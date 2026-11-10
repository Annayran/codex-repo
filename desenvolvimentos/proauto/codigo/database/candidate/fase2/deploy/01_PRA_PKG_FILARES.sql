CREATE OR REPLACE PACKAGE PRA_PKG_FILARES AS
    C_DIAS_JANELA_PADRAO CONSTANT PLS_INTEGER := 120;

    FUNCTION OBTER_DIAS_JANELA RETURN PLS_INTEGER;

    FUNCTION TOP_CRIAR_RESERVA (
        P_AD_FILARESERVA IN VARCHAR2,
        P_TIPMOV         IN VARCHAR2
    ) RETURN VARCHAR2 DETERMINISTIC;

    FUNCTION EH_DIA_UTIL (
        P_DATA   IN DATE,
        P_CODEMP IN NUMBER
    ) RETURN VARCHAR2;

    FUNCTION SOMAR_DIAS_UTEIS (
        P_DATA_BASE IN DATE,
        P_DIAS      IN PLS_INTEGER,
        P_CODEMP    IN NUMBER
    ) RETURN DATE;

    FUNCTION CALCULAR_PROVISAO (
        P_DTPREV    IN DATE,
        P_CODEMP    IN NUMBER,
        P_DATA_BASE IN DATE DEFAULT TRUNC(SYSDATE)
    ) RETURN VARCHAR2;

    PROCEDURE BLOQUEAR_PRODUTO (
        P_CODPROD IN NUMBER
    );

    PROCEDURE OBTER_SEQ_AUTOMATICA (
        P_CODPROD   IN NUMBER,
        P_DTPREV    IN DATE,
        P_NUNOTA    IN NUMBER,
        P_SEQUENCIA IN NUMBER,
        P_SEQENT     OUT NUMBER
    );

    PROCEDURE GRAVAR_FILA (
        P_NUNOTA    IN AD_TGFSEQENT.NUNOTA%TYPE,
        P_SEQUENCIA IN AD_TGFSEQENT.SEQUENCIA%TYPE,
        P_QTDNEG    IN AD_TGFSEQENT.QTDENT%TYPE,
        P_DTINICIO  IN AD_TGFSEQENT.DTPREV%TYPE,
        P_CODPROD   IN AD_TGFSEQENT.CODPROD%TYPE,
        P_PROVISAO  IN AD_TGFSEQENT.PROVISAO%TYPE
    );

    PROCEDURE ATUALIZAR_PROVISOES (
        P_QTD_PROCESSADA OUT NUMBER
    );

END PRA_PKG_FILARES;
/

CREATE OR REPLACE PACKAGE BODY PRA_PKG_FILARES AS
    C_DATA_SEM_PREVISAO CONSTANT DATE := DATE '2999-12-12';

    FUNCTION OBTER_DIAS_JANELA RETURN PLS_INTEGER IS
        V_DIAS PLS_INTEGER;
    BEGIN
        -- Regra confirmada para a primeira homologacao. A externalizacao deve
        -- usar o mecanismo de parametros compativel com o modelo Sankhya do
        -- ambiente, sem criar uma tabela de configuracao invisivel ao usuario.
        V_DIAS := C_DIAS_JANELA_PADRAO;
        RETURN V_DIAS;
    END OBTER_DIAS_JANELA;

    FUNCTION TOP_CRIAR_RESERVA (
        P_AD_FILARESERVA IN VARCHAR2,
        P_TIPMOV         IN VARCHAR2
    ) RETURN VARCHAR2 DETERMINISTIC IS
    BEGIN
        IF NVL(UPPER(TRIM(P_AD_FILARESERVA)), 'N') = 'S'
           AND NVL(UPPER(TRIM(P_TIPMOV)), '?') IN ('P', 'J') THEN
            RETURN 'S';
        END IF;

        RETURN 'N';
    END TOP_CRIAR_RESERVA;

    FUNCTION EH_DIA_UTIL (
        P_DATA   IN DATE,
        P_CODEMP IN NUMBER
    ) RETURN VARCHAR2 IS
        V_CODCID   TSIEMP.CODCID%TYPE;
        V_CODUF    TSICID.UF%TYPE;
        V_CODPAIS  TSIUFS.CODPAIS%TYPE;
        V_FERIADOS NUMBER;
    BEGIN
        IF P_DATA IS NULL THEN
            RETURN 'N';
        END IF;

        -- ISO week: 0 a 4 = segunda a sexta; 5 e 6 = sabado e domingo.
        IF TRUNC(P_DATA) - TRUNC(P_DATA, 'IW') >= 5 THEN
            RETURN 'N';
        END IF;

        BEGIN
            SELECT EMP.CODCID,
                   CID.UF,
                   UFS.CODPAIS
              INTO V_CODCID,
                   V_CODUF,
                   V_CODPAIS
              FROM TSIEMP EMP
              JOIN TSICID CID
                ON CID.CODCID = EMP.CODCID
              JOIN TSIUFS UFS
                ON UFS.CODUF = CID.UF
             WHERE EMP.CODEMP = P_CODEMP;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                V_CODCID := NULL;
                V_CODUF := NULL;
                V_CODPAIS := NULL;
        END;

        SELECT COUNT(*)
          INTO V_FERIADOS
          FROM TSIFER FER
         WHERE (
                   TRUNC(FER.DTFERIADO) = TRUNC(P_DATA)
                OR (
                       NVL(UPPER(TRIM(FER.RECORRENTE)), 'N') = 'S'
                   AND TO_CHAR(FER.DTFERIADO, 'MMDD') = TO_CHAR(P_DATA, 'MMDD')
                )
               )
           AND NVL(UPPER(TRIM(FER.OBRIGATORIO)), 'S') = 'S'
           AND (NVL(FER.CODPAIS, 0) = 0 OR FER.CODPAIS = V_CODPAIS)
           AND (NVL(FER.CODUF, 0) = 0 OR FER.CODUF = V_CODUF)
           AND (NVL(FER.CODCID, 0) = 0 OR FER.CODCID = V_CODCID);

        RETURN CASE WHEN V_FERIADOS > 0 THEN 'N' ELSE 'S' END;
    END EH_DIA_UTIL;

    FUNCTION SOMAR_DIAS_UTEIS (
        P_DATA_BASE IN DATE,
        P_DIAS      IN PLS_INTEGER,
        P_CODEMP    IN NUMBER
    ) RETURN DATE IS
        V_DATA     DATE := TRUNC(P_DATA_BASE);
        V_CONTADOR PLS_INTEGER := 0;
    BEGIN
        IF P_DATA_BASE IS NULL THEN
            RAISE_APPLICATION_ERROR(-20001, 'A data base dos dias uteis e obrigatoria.');
        END IF;

        IF P_DIAS IS NULL OR P_DIAS < 0 OR P_DIAS > 10000 THEN
            RAISE_APPLICATION_ERROR(-20002, 'Quantidade de dias uteis fora do limite permitido.');
        END IF;

        WHILE V_CONTADOR < P_DIAS LOOP
            V_DATA := V_DATA + 1;

            IF EH_DIA_UTIL(V_DATA, P_CODEMP) = 'S' THEN
                V_CONTADOR := V_CONTADOR + 1;
            END IF;
        END LOOP;

        RETURN V_DATA;
    END SOMAR_DIAS_UTEIS;

    FUNCTION CALCULAR_PROVISAO (
        P_DTPREV    IN DATE,
        P_CODEMP    IN NUMBER,
        P_DATA_BASE IN DATE DEFAULT TRUNC(SYSDATE)
    ) RETURN VARCHAR2 IS
        V_DATA_LIMITE DATE;
    BEGIN
        IF P_DTPREV IS NULL THEN
            -- Compatibilidade com o legado: data nula permanece na fila ativa.
            -- O saneamento de datas deve ser uma operacao explicita e auditada.
            RETURN 'N';
        END IF;

        IF TRUNC(P_DTPREV) <= TRUNC(P_DATA_BASE) THEN
            RETURN 'N';
        END IF;

        V_DATA_LIMITE := SOMAR_DIAS_UTEIS(
            TRUNC(P_DATA_BASE),
            OBTER_DIAS_JANELA,
            P_CODEMP
        );

        RETURN CASE
                   WHEN TRUNC(P_DTPREV) > V_DATA_LIMITE THEN 'S'
                   ELSE 'N'
               END;
    END CALCULAR_PROVISAO;

    PROCEDURE BLOQUEAR_PRODUTO (
        P_CODPROD IN NUMBER
    ) IS
    BEGIN
        IF P_CODPROD IS NULL THEN
            RAISE_APPLICATION_ERROR(-20003, 'O produto e obrigatorio para bloquear a fila.');
        END IF;

        -- Serializa os fluxos que ja possuem fila sem criar tabela tecnica.
        -- O lock permanece ate o commit/rollback controlado pelo Sankhya.
        FOR REG IN (
            SELECT ROWID AS RID
              FROM AD_TGFSEQENT
             WHERE CODPROD = P_CODPROD
             ORDER BY SEQENT, NUNOTA, SEQUENCIA
               FOR UPDATE
        ) LOOP
            NULL;
        END LOOP;
    END BLOQUEAR_PRODUTO;

    PROCEDURE ABRIR_ESPACO (
        P_CODPROD       IN NUMBER,
        P_SEQ_SUCESSORA IN NUMBER
    ) IS
    BEGIN
        FOR REG IN (
            SELECT ENT.ROWID AS RID
              FROM AD_TGFSEQENT ENT
              JOIN TGFITE ITE
                ON ITE.NUNOTA = ENT.NUNOTA
               AND ITE.SEQUENCIA = ENT.SEQUENCIA
              JOIN TGFCAB CAB
                ON CAB.NUNOTA = ITE.NUNOTA
              JOIN TGFTOP TOP
                ON TOP.CODTIPOPER = CAB.CODTIPOPER
               AND TOP.DHALTER = CAB.DHTIPOPER
             WHERE ENT.CODPROD = P_CODPROD
               AND ENT.SEQENT >= P_SEQ_SUCESSORA
               AND NVL(ENT.PROVISAO, 'N') = 'N'
               AND ITE.PENDENTE = 'S'
               AND CAB.STATUSNOTA = 'L'
               AND CAB.TIPMOV IN ('P', 'J')
               AND NVL(TOP.AD_FILARESERVA, 'N') = 'S'
             ORDER BY ENT.SEQENT DESC,
                      ENT.NUNOTA DESC,
                      ENT.SEQUENCIA DESC
               FOR UPDATE OF ENT.SEQENT
        ) LOOP
            UPDATE AD_TGFSEQENT
               SET SEQENT = SEQENT + 10
             WHERE ROWID = REG.RID;
        END LOOP;
    END ABRIR_ESPACO;

    PROCEDURE OBTER_SEQ_AUTOMATICA (
        P_CODPROD   IN NUMBER,
        P_DTPREV    IN DATE,
        P_NUNOTA    IN NUMBER,
        P_SEQUENCIA IN NUMBER,
        P_SEQENT     OUT NUMBER
    ) IS
        V_SEQ_SUCESSORA AD_TGFSEQENT.SEQENT%TYPE;
        V_SEQ_ANTERIOR  AD_TGFSEQENT.SEQENT%TYPE;
        V_DATA_ENTRADA  DATE := NVL(P_DTPREV, C_DATA_SEM_PREVISAO);
        V_MAX_SEQ       AD_TGFSEQENT.SEQENT%TYPE;
    BEGIN
        BLOQUEAR_PRODUTO(P_CODPROD);

        BEGIN
            SELECT SEQENT
              INTO V_SEQ_SUCESSORA
              FROM (
                    SELECT ENT.SEQENT,
                           ENT.NUNOTA,
                           ENT.SEQUENCIA
                      FROM AD_TGFSEQENT ENT
                      JOIN TGFITE ITE
                        ON ITE.NUNOTA = ENT.NUNOTA
                       AND ITE.SEQUENCIA = ENT.SEQUENCIA
                      JOIN TGFCAB CAB
                        ON CAB.NUNOTA = ITE.NUNOTA
                      JOIN TGFTOP TOP
                        ON TOP.CODTIPOPER = CAB.CODTIPOPER
                       AND TOP.DHALTER = CAB.DHTIPOPER
                     WHERE ENT.CODPROD = P_CODPROD
                       AND NOT (
                               ENT.NUNOTA = NVL(P_NUNOTA, -1)
                           AND ENT.SEQUENCIA = NVL(P_SEQUENCIA, -1)
                       )
                       AND NVL(ENT.PROVISAO, 'N') = 'N'
                       AND ITE.PENDENTE = 'S'
                       AND CAB.STATUSNOTA = 'L'
                       AND CAB.TIPMOV IN ('P', 'J')
                       AND NVL(TOP.AD_FILARESERVA, 'N') = 'S'
                       AND NVL(ENT.DTPREV, NVL(CAB.DTPREVENT, C_DATA_SEM_PREVISAO))
                           > V_DATA_ENTRADA
                     ORDER BY ENT.SEQENT,
                              ENT.NUNOTA,
                              ENT.SEQUENCIA
                   )
             WHERE ROWNUM = 1;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                V_SEQ_SUCESSORA := NULL;
        END;

        IF V_SEQ_SUCESSORA IS NULL THEN
            SELECT NVL(MAX(SEQENT), 0)
              INTO V_MAX_SEQ
              FROM AD_TGFSEQENT
             WHERE CODPROD = P_CODPROD;

            IF V_MAX_SEQ > 9999999989 THEN
                RAISE_APPLICATION_ERROR(-20004, 'Limite de SEQENT atingido para o produto ' || P_CODPROD || '.');
            END IF;

            P_SEQENT := V_MAX_SEQ + 10;
            RETURN;
        END IF;

        SELECT MAX(SEQENT)
          INTO V_SEQ_ANTERIOR
          FROM AD_TGFSEQENT
         WHERE CODPROD = P_CODPROD
           AND SEQENT < V_SEQ_SUCESSORA;

        IF V_SEQ_ANTERIOR IS NULL THEN
            P_SEQENT := V_SEQ_SUCESSORA - 10;
        ELSIF V_SEQ_SUCESSORA - V_SEQ_ANTERIOR > 1 THEN
            P_SEQENT := FLOOR((V_SEQ_SUCESSORA + V_SEQ_ANTERIOR) / 2);
        ELSE
            ABRIR_ESPACO(P_CODPROD, V_SEQ_SUCESSORA);
            P_SEQENT := V_SEQ_SUCESSORA;
        END IF;
    END OBTER_SEQ_AUTOMATICA;

    PROCEDURE GRAVAR_FILA (
        P_NUNOTA    IN AD_TGFSEQENT.NUNOTA%TYPE,
        P_SEQUENCIA IN AD_TGFSEQENT.SEQUENCIA%TYPE,
        P_QTDNEG    IN AD_TGFSEQENT.QTDENT%TYPE,
        P_DTINICIO  IN AD_TGFSEQENT.DTPREV%TYPE,
        P_CODPROD   IN AD_TGFSEQENT.CODPROD%TYPE,
        P_PROVISAO  IN AD_TGFSEQENT.PROVISAO%TYPE
    ) IS
        V_QUANTIDADE     NUMBER;
        V_QTD_TOTAL      NUMBER;
        V_CODPROD_ATUAL  AD_TGFSEQENT.CODPROD%TYPE;
        V_OLD_PROVISAO   AD_TGFSEQENT.PROVISAO%TYPE;
        V_MAX_PROVISAO   AD_TGFSEQENT.PROVISAO%TYPE;
        V_SEQENT_ATUAL   AD_TGFSEQENT.SEQENT%TYPE;
        V_QTDENTREGUE    AD_TGFSEQENT.QTDENTREGUE%TYPE;
        V_NOVA_SEQ       AD_TGFSEQENT.SEQENT%TYPE;
        V_PROVISAO       VARCHAR2(1) := NVL(UPPER(TRIM(P_PROVISAO)), 'N');
    BEGIN
        IF P_NUNOTA IS NULL OR P_SEQUENCIA IS NULL OR P_CODPROD IS NULL THEN
            RAISE_APPLICATION_ERROR(-20005, 'Nota, sequencia e produto sao obrigatorios para gravar a fila.');
        END IF;

        IF P_QTDNEG IS NULL OR P_QTDNEG < 0 THEN
            RAISE_APPLICATION_ERROR(-20006, 'Quantidade invalida para gravar a fila.');
        END IF;

        IF V_PROVISAO NOT IN ('N', 'S') THEN
            RAISE_APPLICATION_ERROR(-20007, 'Valor de provisao deve ser N ou S.');
        END IF;

        BLOQUEAR_PRODUTO(P_CODPROD);

        SELECT COUNT(*),
               NVL(SUM(QTDENT), 0),
               MIN(CODPROD),
               MIN(NVL(PROVISAO, 'N')),
               MAX(NVL(PROVISAO, 'N'))
          INTO V_QUANTIDADE,
               V_QTD_TOTAL,
               V_CODPROD_ATUAL,
               V_OLD_PROVISAO,
               V_MAX_PROVISAO
          FROM AD_TGFSEQENT
         WHERE NUNOTA = P_NUNOTA
           AND SEQUENCIA = P_SEQUENCIA;

        IF V_QUANTIDADE = 0 THEN
            OBTER_SEQ_AUTOMATICA(
                P_CODPROD,
                CASE WHEN V_PROVISAO = 'S' THEN C_DATA_SEM_PREVISAO ELSE P_DTINICIO END,
                P_NUNOTA,
                P_SEQUENCIA,
                V_NOVA_SEQ
            );

            INSERT INTO AD_TGFSEQENT (
                NUNOTA,
                SEQUENCIA,
                SEQENT,
                QTDENT,
                DTPREV,
                DTLANC,
                CODPROD,
                PROVISAO
            ) VALUES (
                P_NUNOTA,
                P_SEQUENCIA,
                V_NOVA_SEQ,
                P_QTDNEG,
                P_DTINICIO,
                SYSDATE,
                P_CODPROD,
                V_PROVISAO
            );
            RETURN;
        END IF;

        IF V_CODPROD_ATUAL IS NULL OR V_CODPROD_ATUAL <> P_CODPROD THEN
            RAISE_APPLICATION_ERROR(-20008, 'Produto da fila diverge do produto informado para a nota/item.');
        END IF;

        IF V_QUANTIDADE = 1 THEN
            SELECT SEQENT,
                   NVL(PROVISAO, 'N'),
                   NVL(QTDENTREGUE, 0)
              INTO V_SEQENT_ATUAL,
                   V_OLD_PROVISAO,
                   V_QTDENTREGUE
              FROM AD_TGFSEQENT
             WHERE NUNOTA = P_NUNOTA
               AND SEQUENCIA = P_SEQUENCIA
               FOR UPDATE;

            IF P_QTDNEG < V_QTDENTREGUE THEN
                RAISE_APPLICATION_ERROR(-20009, 'Quantidade da fila nao pode ser menor que a quantidade entregue.');
            END IF;

            IF V_OLD_PROVISAO = 'S' AND V_PROVISAO = 'N' THEN
                OBTER_SEQ_AUTOMATICA(
                    P_CODPROD,
                    P_DTINICIO,
                    P_NUNOTA,
                    P_SEQUENCIA,
                    V_NOVA_SEQ
                );
            ELSE
                V_NOVA_SEQ := V_SEQENT_ATUAL;
            END IF;

            UPDATE AD_TGFSEQENT
               SET QTDENT = P_QTDNEG,
                   DTPREV = P_DTINICIO,
                   CODPROD = P_CODPROD,
                   PROVISAO = V_PROVISAO,
                   SEQENT = V_NOVA_SEQ
             WHERE NUNOTA = P_NUNOTA
               AND SEQUENCIA = P_SEQUENCIA
               AND SEQENT = V_SEQENT_ATUAL;
            RETURN;
        END IF;

        -- Fragmentos criados pela troca manual nao podem ser consolidados por MERGE.
        IF ABS(V_QTD_TOTAL - P_QTDNEG) > 0.000001 THEN
            RAISE_APPLICATION_ERROR(
                -20010,
                'Quantidade total dos fragmentos diverge do item. Revise a troca manual antes de atualizar.'
            );
        END IF;

        -- A troca manual pode dividir um item entre fila ativa e provisionada.
        -- Nessa situacao, a trigger atualiza apenas os dados comuns e preserva
        -- a decisao individual gravada em PROVISAO/SEQENT para cada fragmento.
        IF V_OLD_PROVISAO <> V_MAX_PROVISAO THEN
            UPDATE AD_TGFSEQENT
               SET DTPREV = P_DTINICIO,
                   CODPROD = P_CODPROD
             WHERE NUNOTA = P_NUNOTA
               AND SEQUENCIA = P_SEQUENCIA;
            RETURN;
        END IF;

        IF V_OLD_PROVISAO = 'S' AND V_PROVISAO = 'N' THEN
            FOR REG IN (
                SELECT ROWID AS RID
                  FROM AD_TGFSEQENT
                 WHERE NUNOTA = P_NUNOTA
                   AND SEQUENCIA = P_SEQUENCIA
                 ORDER BY SEQENT
                   FOR UPDATE
            ) LOOP
                OBTER_SEQ_AUTOMATICA(
                    P_CODPROD,
                    P_DTINICIO,
                    P_NUNOTA,
                    P_SEQUENCIA,
                    V_NOVA_SEQ
                );

                UPDATE AD_TGFSEQENT
                   SET DTPREV = P_DTINICIO,
                       CODPROD = P_CODPROD,
                       PROVISAO = 'N',
                       SEQENT = V_NOVA_SEQ
                 WHERE ROWID = REG.RID;
            END LOOP;
        ELSE
            UPDATE AD_TGFSEQENT
               SET DTPREV = P_DTINICIO,
                   CODPROD = P_CODPROD,
                   PROVISAO = V_PROVISAO
             WHERE NUNOTA = P_NUNOTA
               AND SEQUENCIA = P_SEQUENCIA;
        END IF;
    END GRAVAR_FILA;

    PROCEDURE ATUALIZAR_PROVISOES (
        P_QTD_PROCESSADA OUT NUMBER
    ) IS
        V_NOVA_SEQ       AD_TGFSEQENT.SEQENT%TYPE;
        V_AINDA_ELEGIVEL PLS_INTEGER;
        V_DTPREV_ATUAL   AD_TGFSEQENT.DTPREV%TYPE;
    BEGIN
        P_QTD_PROCESSADA := 0;

        FOR REG IN (
            SELECT ENT.ROWID AS RID,
                   ENT.CODPROD,
                   ENT.NUNOTA,
                   ENT.SEQUENCIA,
                   ENT.DTPREV,
                   CAB.CODEMP
              FROM AD_TGFSEQENT ENT
              JOIN TGFITE ITE
                ON ITE.NUNOTA = ENT.NUNOTA
               AND ITE.SEQUENCIA = ENT.SEQUENCIA
              JOIN TGFCAB CAB
                ON CAB.NUNOTA = ITE.NUNOTA
              JOIN TGFTOP TOP
                ON TOP.CODTIPOPER = CAB.CODTIPOPER
               AND TOP.DHALTER = CAB.DHTIPOPER
             WHERE NVL(ENT.PROVISAO, 'N') = 'S'
               AND ENT.DTPREV IS NOT NULL
               AND ITE.PENDENTE = 'S'
               AND CAB.STATUSNOTA = 'L'
               AND TOP_CRIAR_RESERVA(TOP.AD_FILARESERVA, CAB.TIPMOV) = 'S'
               AND CALCULAR_PROVISAO(ENT.DTPREV, CAB.CODEMP, TRUNC(SYSDATE)) = 'N'
             ORDER BY ENT.CODPROD,
                      ENT.DTPREV,
                      ENT.NUNOTA,
                      ENT.SEQUENCIA,
                      ENT.SEQENT
        ) LOOP
            -- Botao e scheduler podem concorrer. O lock e a segunda leitura
            -- impedem que o segundo processo abra espaco para uma linha que o
            -- primeiro ja ativou.
            BLOQUEAR_PRODUTO(REG.CODPROD);

            SELECT COUNT(*),
                   MAX(ENT.DTPREV)
              INTO V_AINDA_ELEGIVEL,
                   V_DTPREV_ATUAL
              FROM AD_TGFSEQENT ENT
              JOIN TGFITE ITE
                ON ITE.NUNOTA = ENT.NUNOTA
               AND ITE.SEQUENCIA = ENT.SEQUENCIA
              JOIN TGFCAB CAB
                ON CAB.NUNOTA = ITE.NUNOTA
              JOIN TGFTOP TOP
                ON TOP.CODTIPOPER = CAB.CODTIPOPER
               AND TOP.DHALTER = CAB.DHTIPOPER
             WHERE ENT.ROWID = REG.RID
               AND NVL(ENT.PROVISAO, 'N') = 'S'
               AND ENT.DTPREV IS NOT NULL
               AND ITE.PENDENTE = 'S'
               AND CAB.STATUSNOTA = 'L'
               AND TOP_CRIAR_RESERVA(TOP.AD_FILARESERVA, CAB.TIPMOV) = 'S'
               AND CALCULAR_PROVISAO(ENT.DTPREV, CAB.CODEMP, TRUNC(SYSDATE)) = 'N';

            IF V_AINDA_ELEGIVEL = 1 THEN
                OBTER_SEQ_AUTOMATICA(
                    REG.CODPROD,
                    V_DTPREV_ATUAL,
                    REG.NUNOTA,
                    REG.SEQUENCIA,
                    V_NOVA_SEQ
                );

                UPDATE AD_TGFSEQENT
                   SET PROVISAO = 'N',
                       SEQENT = V_NOVA_SEQ
                 WHERE ROWID = REG.RID
                   AND NVL(PROVISAO, 'N') = 'S';

                IF SQL%ROWCOUNT = 1 THEN
                    P_QTD_PROCESSADA := P_QTD_PROCESSADA + 1;
                END IF;
            END IF;
        END LOOP;
    END ATUALIZAR_PROVISOES;

END PRA_PKG_FILARES;
/

SHOW ERRORS PACKAGE PRA_PKG_FILARES;
SHOW ERRORS PACKAGE BODY PRA_PKG_FILARES;
