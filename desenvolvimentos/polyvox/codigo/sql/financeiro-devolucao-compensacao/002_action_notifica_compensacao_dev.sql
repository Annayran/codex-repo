CREATE OR REPLACE PROCEDURE ACTION_NOTIF_COMP_DEV
AS
    v_NULOG         AD_LOGCOMPDEV.NULOG%TYPE;
    v_NUAVISO       TSIAVI.NUAVISO%TYPE;
    v_MAXNUAVISO    TSIAVI.NUAVISO%TYPE;
    v_ASSUNTO       TSIAVI.TITULO%TYPE;
    v_MENSAGEM      TSIAVI.DESCRICAO%TYPE;

BEGIN
    FOR X IN (
        SELECT
            DEV.NUNOTA  AS NUNOTADEV,
            DEV.NUMNOTA AS NUMNOTADEV,
            ORIG.NUNOTA AS NUNOTAORIG,
            ORIG.NUMNOTA AS NUMNOTAORIG,
            DEV.CODEMP,
            DEV.CODPARC,
            USU.CODUSU
        FROM TGFCAB DEV
        JOIN TGFVAR VAR
          ON VAR.NUNOTA = DEV.NUNOTA
        JOIN TGFCAB ORIG
          ON ORIG.NUNOTA = VAR.NUNOTAORIG
        CROSS JOIN (
            SELECT 20 AS CODUSU FROM DUAL
            UNION ALL
            SELECT 0 AS CODUSU FROM DUAL
            UNION ALL
            SELECT 37 AS CODUSU FROM DUAL
        ) USU
        WHERE NOT EXISTS (
            SELECT 1
              FROM AD_LOGCOMPDEV L
             WHERE L.NUNOTADEV = DEV.NUNOTA
               AND L.NUNOTAORIG = ORIG.NUNOTA
               AND L.CODUSURESPFIN = USU.CODUSU
               AND L.STATUS IN ('AVISADO', 'VISUALIZADO', 'FECHADO')
        )
          AND NOT EXISTS (
              SELECT 1
                FROM TGFFIN FIN
               WHERE FIN.NUNOTA = DEV.NUNOTA
                 AND FIN.NUCOMPENS IS NOT NULL
          )
          AND DEV.CODTIPOPER IN (1200, 1201)
    )
    LOOP
        SELECT NVL(MAX(NULOG), 0) + 1
          INTO v_NULOG
          FROM AD_LOGCOMPDEV;

        SELECT NVL(ULTCOD, 0) + 1
          INTO v_NUAVISO
          FROM TGFNUM
         WHERE ARQUIVO = 'TSIAVI';

        SELECT NVL(MAX(NUAVISO), 0)
          INTO v_MAXNUAVISO
          FROM TSIAVI;

        IF v_MAXNUAVISO >= v_NUAVISO THEN
            v_NUAVISO := v_MAXNUAVISO + 1;
        END IF;

        v_ASSUNTO := 'Compensação financeira de devolução';
        v_MENSAGEM :=
            '<b>ATENÇÃO</b><br/><br/>' ||
            'É necessário compensar o financeiro da nota de devolução <b>' ||
            X.NUMNOTADEV ||
            '</b> em sua nota de origem <b>' ||
            X.NUMNOTAORIG ||
            '</b>.<br/><br/>' ||
            'Nota devolução (NUNOTA): ' || X.NUNOTADEV || '<br/>' ||
            'Nota origem (NUNOTA): ' || X.NUNOTAORIG || '<br/><br/>' ||
            'Após visualizar este aviso, realize a conferência/compensação financeira.';

        INSERT INTO AD_LOGCOMPDEV (
            NULOG,
            NUNOTADEV,
            NUMNOTADEV,
            NUNOTAORIG,
            NUMNOTAORIG,
            CODEMP,
            CODPARC,
            CODUSURESPFIN,
            NUAVISO,
            DHAVISO,
            STATUS,
            DHCRIACAO
        ) VALUES (
            v_NULOG,
            X.NUNOTADEV,
            X.NUMNOTADEV,
            X.NUNOTAORIG,
            X.NUMNOTAORIG,
            X.CODEMP,
            X.CODPARC,
            X.CODUSU,
            v_NUAVISO,
            SYSDATE,
            'AVISADO',
            SYSDATE
        );

        INSERT INTO TSIAVI (
            NUAVISO,
            TITULO,
            DESCRICAO,
            SOLUCAO,
            IDENTIFICADOR,
            IMPORTANCIA,
            CODUSU,
            CODGRUPO,
            TIPO,
            DHCRIACAO,
            CODUSUREMETENTE,
            NUAVISOPAI
        ) VALUES (
            v_NUAVISO,
            v_ASSUNTO,
            v_MENSAGEM,
            '',
            'COMP:DEV:' || X.NUNOTADEV,
            0,
            X.CODUSU,
            NULL,
            'P',
            SYSDATE,
            -1,
            NULL
        );

        UPDATE TGFNUM
           SET ULTCOD = v_NUAVISO
         WHERE ARQUIVO = 'TSIAVI';
    END LOOP;
END;
/
