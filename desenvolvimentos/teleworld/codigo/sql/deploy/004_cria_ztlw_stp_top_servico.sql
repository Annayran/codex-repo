-- Regra de TOP de Servico - Teleworld
-- Banco: Oracle / Sankhya 4.35
-- Execucao: aplicar primeiro em homologacao, com backup do objeto de mesmo nome.
-- Transacao: nao incluir COMMIT. O fluxo de confirmacao deve controlar commit/rollback.

CREATE OR REPLACE PROCEDURE ZTLW_STP_TOP_SERVICO (
    P_NUNOTA    NUMBER,
    P_SUCESSO   OUT VARCHAR,
    P_MENSAGEM  OUT VARCHAR2,
    P_CODUSULIB OUT NUMBER
) AS
    V_ATIVA              TSIPAR.LOGICO%TYPE;
    V_APROVADA           TSIPAR.LOGICO%TYPE;
    V_TOPS_ELEGIVEIS     TSIPAR.TEXTO%TYPE;
    V_TOP_RETEM          TSIPAR.INTEIRO%TYPE;
    V_TOP_NAO_RETEM      TSIPAR.INTEIRO%TYPE;
    V_CATEGORIAS_SIM     TSIPAR.TEXTO%TYPE;
    V_CATEGORIAS_NAO     TSIPAR.TEXTO%TYPE;
    V_TIPO_IMA_PARCEIRO  TSIPAR.TEXTO%TYPE;

    V_TOP_ATUAL          TGFCAB.CODTIPOPER%TYPE;
    V_TOP_DESTINO        TGFCAB.CODTIPOPER%TYPE;
    V_TOP_ITEM           TGFCAB.CODTIPOPER%TYPE;
    V_DH_TOP_DESTINO     TGFTOP.DHALTER%TYPE;
    V_NUMCONTRATO        TGFCAB.NUMCONTRATO%TYPE;
    V_CODPARC            TGFCAB.CODPARC%TYPE;
    V_CONFIRMADA         TGFCAB.CONFIRMADA%TYPE;
    V_SIMPLES            TGFPAR.SIMPLES%TYPE;
    V_OUTROS_IMPOSTOS    NUMBER := 0;
    V_QTD_ITENS          NUMBER := 0;
    V_CATEGORIA          VARCHAR2(4000);
    V_LISTA_SIM          VARCHAR2(4000);
    V_LISTA_NAO          VARCHAR2(4000);
    V_LISTA_TOPS         VARCHAR2(4000);
    V_EH_CSLL_SIM        NUMBER;
    V_EH_CSLL_NAO        NUMBER;

    FUNCTION NORMALIZAR(P_VALOR VARCHAR2) RETURN VARCHAR2 IS
    BEGIN
        RETURN TRANSLATE(
            REGEXP_REPLACE(UPPER(TRIM(P_VALOR)), '[[:space:]]+', ' '),
            'ÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÕÖÚÙÛÜÇ',
            'AAAAAEEEEIIIIOOOOOUUUUC'
        );
    END NORMALIZAR;

    FUNCTION CONTEM(P_LISTA VARCHAR2, P_VALOR VARCHAR2) RETURN NUMBER IS
    BEGIN
        IF P_LISTA IS NULL OR P_VALOR IS NULL THEN
            RETURN 0;
        END IF;

        IF INSTR(';' || P_LISTA || ';', ';' || P_VALOR || ';') > 0 THEN
            RETURN 1;
        END IF;

        RETURN 0;
    END CONTEM;
BEGIN
    P_SUCESSO   := 'S';
    P_MENSAGEM  := NULL;
    P_CODUSULIB := NULL;

    SELECT MAX(CASE WHEN CHAVE = 'ZTLWTOPAT' THEN LOGICO END),
           MAX(CASE WHEN CHAVE = 'ZTLWTOPAP' THEN LOGICO END),
           MAX(CASE WHEN CHAVE = 'ZTLWTOPOR' THEN TEXTO END),
           MAX(CASE WHEN CHAVE = 'ZTLWTOPRE' THEN INTEIRO END),
           MAX(CASE WHEN CHAVE = 'ZTLWTOPNR' THEN INTEIRO END),
           MAX(CASE WHEN CHAVE = 'ZTLWCSLLS' THEN TEXTO END),
           MAX(CASE WHEN CHAVE = 'ZTLWCSLLN' THEN TEXTO END),
           MAX(CASE WHEN CHAVE = 'ZTLWIMAPAR' THEN TEXTO END)
      INTO V_ATIVA,
           V_APROVADA,
           V_TOPS_ELEGIVEIS,
           V_TOP_RETEM,
           V_TOP_NAO_RETEM,
           V_CATEGORIAS_SIM,
           V_CATEGORIAS_NAO,
           V_TIPO_IMA_PARCEIRO
      FROM TSIPAR
     WHERE CODUSU = 0
       AND CHAVE IN (
           'ZTLWTOPAT', 'ZTLWTOPAP', 'ZTLWTOPOR', 'ZTLWTOPRE',
           'ZTLWTOPNR', 'ZTLWCSLLS', 'ZTLWCSLLN', 'ZTLWIMAPAR'
       );

    IF NVL(UPPER(TRIM(V_ATIVA)), 'N') <> 'S' THEN
        RETURN;
    END IF;

    SELECT CODTIPOPER, NUMCONTRATO, CODPARC, CONFIRMADA
      INTO V_TOP_ATUAL, V_NUMCONTRATO, V_CODPARC, V_CONFIRMADA
      FROM TGFCAB
     WHERE NUNOTA = P_NUNOTA;

    IF NVL(UPPER(TRIM(V_CONFIRMADA)), 'N') = 'S' THEN
        P_SUCESSO  := 'N';
        P_MENSAGEM := 'A Regra TOP de Servico nao altera notas ja confirmadas.';
        RETURN;
    END IF;

    IF V_TOPS_ELEGIVEIS IS NULL THEN
        P_SUCESSO  := 'N';
        P_MENSAGEM := 'Regra TOP de Servico ativa sem TOPs elegiveis no parametro ZTLWTOPOR.';
        RETURN;
    END IF;

    V_LISTA_TOPS := ';' || REPLACE(
        REGEXP_REPLACE(TRIM(V_TOPS_ELEGIVEIS), '[[:space:]]+', ''), ',', ';'
    ) || ';';

    IF INSTR(
        V_LISTA_TOPS,
        ';' || TRIM(TO_CHAR(V_TOP_ATUAL, 'FM99999999999999999990')) || ';'
    ) = 0 THEN
        RETURN;
    END IF;

    IF NVL(V_NUMCONTRATO, 0) <> 0 THEN
        P_MENSAGEM := 'TOP preservada: nota vinculada ao contrato '
            || TO_CHAR(V_NUMCONTRATO) || '.';
        RETURN;
    END IF;

    IF NVL(UPPER(TRIM(V_APROVADA)), 'N') <> 'S' THEN
        P_SUCESSO  := 'N';
        P_MENSAGEM := 'Matriz fiscal da Regra TOP de Servico ainda nao aprovada. Verifique ZTLWTOPAP.';
        RETURN;
    END IF;

    IF V_TOP_RETEM IS NULL OR V_TOP_NAO_RETEM IS NULL
       OR V_TOP_RETEM <= 0 OR V_TOP_NAO_RETEM <= 0
       OR V_TOP_RETEM = V_TOP_NAO_RETEM THEN
        P_SUCESSO  := 'N';
        P_MENSAGEM := 'Parametros ZTLWTOPRE e ZTLWTOPNR estao ausentes ou invalidos.';
        RETURN;
    END IF;

    IF V_CATEGORIAS_SIM IS NULL OR V_CATEGORIAS_NAO IS NULL THEN
        P_SUCESSO  := 'N';
        P_MENSAGEM := 'Categorias CSLL nao configuradas em ZTLWCSLLS/ZTLWCSLLN.';
        RETURN;
    END IF;

    IF V_TIPO_IMA_PARCEIRO IS NULL THEN
        P_SUCESSO  := 'N';
        P_MENSAGEM := 'Discriminador de Outros Impostos do parceiro nao configurado em ZTLWIMAPAR.';
        RETURN;
    END IF;

    V_LISTA_SIM  := NORMALIZAR(V_CATEGORIAS_SIM);
    V_LISTA_NAO  := NORMALIZAR(V_CATEGORIAS_NAO);

    SELECT NVL(UPPER(TRIM(SIMPLES)), 'N')
      INTO V_SIMPLES
      FROM TGFPAR
     WHERE CODPARC = V_CODPARC;

    SELECT COUNT(*)
      INTO V_OUTROS_IMPOSTOS
      FROM TGFIMA
     WHERE CODIGO = V_CODPARC
       AND UPPER(TRIM(TIPO)) = UPPER(TRIM(V_TIPO_IMA_PARCEIRO));

    FOR R_ITEM IN (
        SELECT ITE.SEQUENCIA,
               ITE.CODPROD,
               PRO.AD_CATFISCAL AS CATEGORIA_FISCAL
          FROM TGFITE ITE
          JOIN TGFPRO PRO
            ON PRO.CODPROD = ITE.CODPROD
         WHERE ITE.NUNOTA = P_NUNOTA
         ORDER BY ITE.SEQUENCIA
    ) LOOP
        V_QTD_ITENS := V_QTD_ITENS + 1;
        V_CATEGORIA := NORMALIZAR(R_ITEM.CATEGORIA_FISCAL);

        IF V_CATEGORIA IS NULL THEN
            P_SUCESSO  := 'N';
            P_MENSAGEM := 'Categoria Fiscal ausente no produto '
                || TO_CHAR(R_ITEM.CODPROD) || ', item '
                || TO_CHAR(R_ITEM.SEQUENCIA) || '.';
            RETURN;
        END IF;

        V_EH_CSLL_SIM := CONTEM(V_LISTA_SIM, V_CATEGORIA);
        V_EH_CSLL_NAO := CONTEM(V_LISTA_NAO, V_CATEGORIA);

        IF V_EH_CSLL_SIM = 1 AND V_EH_CSLL_NAO = 1 THEN
            P_SUCESSO  := 'N';
            P_MENSAGEM := 'Categoria Fiscal ' || R_ITEM.CATEGORIA_FISCAL
                || ' configurada simultaneamente como CSLL Sim e Nao.';
            RETURN;
        ELSIF V_EH_CSLL_SIM = 1 THEN
            -- Matriz recebida: CSLL = Sim direciona para TOP NAO RETEM.
            V_TOP_ITEM := V_TOP_NAO_RETEM;
        ELSIF V_EH_CSLL_NAO = 1 THEN
            IF V_SIMPLES = 'S' OR V_OUTROS_IMPOSTOS = 0 THEN
                V_TOP_ITEM := V_TOP_NAO_RETEM;
            ELSE
                V_TOP_ITEM := V_TOP_RETEM;
            END IF;
        ELSE
            P_SUCESSO  := 'N';
            P_MENSAGEM := 'Categoria Fiscal ' || R_ITEM.CATEGORIA_FISCAL
                || ' do produto ' || TO_CHAR(R_ITEM.CODPROD)
                || ' nao esta parametrizada.';
            RETURN;
        END IF;

        IF V_TOP_DESTINO IS NULL THEN
            V_TOP_DESTINO := V_TOP_ITEM;
        ELSIF V_TOP_DESTINO <> V_TOP_ITEM THEN
            P_SUCESSO  := 'N';
            P_MENSAGEM := 'Itens da nota resultam em TOPs diferentes. Revise as Categorias Fiscais.';
            RETURN;
        END IF;
    END LOOP;

    IF V_QTD_ITENS = 0 THEN
        P_SUCESSO  := 'N';
        P_MENSAGEM := 'A nota nao possui itens para definir a TOP de servico.';
        RETURN;
    END IF;

    SELECT MAX(DHALTER)
      INTO V_DH_TOP_DESTINO
      FROM TGFTOP
     WHERE CODTIPOPER = V_TOP_DESTINO;

    IF V_DH_TOP_DESTINO IS NULL THEN
        P_SUCESSO  := 'N';
        P_MENSAGEM := 'Versao vigente da TOP ' || TO_CHAR(V_TOP_DESTINO)
            || ' nao localizada na TGFTOP.';
        RETURN;
    END IF;

    IF V_TOP_ATUAL <> V_TOP_DESTINO THEN
        UPDATE TGFCAB
           SET CODTIPOPER = V_TOP_DESTINO,
               DHTIPOPER  = V_DH_TOP_DESTINO
         WHERE NUNOTA = P_NUNOTA;

        IF SQL%ROWCOUNT <> 1 THEN
            P_SUCESSO  := 'N';
            P_MENSAGEM := 'Nao foi possivel atualizar a TOP da nota.';
            RETURN;
        END IF;
    END IF;

    P_MENSAGEM := 'TOP de servico definida como '
        || TO_CHAR(V_TOP_DESTINO) || '.';
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        P_SUCESSO   := 'N';
        P_MENSAGEM  := 'Dados obrigatorios da nota, parceiro ou TOP nao foram localizados.';
        P_CODUSULIB := NULL;
    WHEN TOO_MANY_ROWS THEN
        P_SUCESSO   := 'N';
        P_MENSAGEM  := 'Foram encontrados registros duplicados em dados obrigatorios da Regra TOP de Servico.';
        P_CODUSULIB := NULL;
    WHEN OTHERS THEN
        P_SUCESSO   := 'N';
        P_MENSAGEM  := 'Falha na Regra TOP de Servico: '
            || SUBSTR(SQLERRM, 1, 3500);
        P_CODUSULIB := NULL;
END;
/

-- Nao adicionar COMMIT neste arquivo.
