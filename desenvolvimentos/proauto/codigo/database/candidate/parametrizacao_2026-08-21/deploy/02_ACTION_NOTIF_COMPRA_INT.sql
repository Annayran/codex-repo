  CREATE OR REPLACE PROCEDURE PRA_ACTION_NOTIF_COMPRA_INT
  AS
      V_DIAS_NOTIFICACAO TSIPAR.INTEIRO%TYPE;
      V_GRUPOS           TSIPAR.TEXTO%TYPE;
      V_NUAVISO          TSIAVI.NUAVISO%TYPE;
      V_MAXAVISO         TSIAVI.NUAVISO%TYPE;
      V_ASSUNTO          TSIAVI.TITULO%TYPE;
      V_MENSAGEM         TSIAVI.DESCRICAO%TYPE;
      V_IDENTIFICADOR    TSIAVI.IDENTIFICADOR%TYPE;
      V_QTD_GRUPO        NUMBER;
  BEGIN
      BEGIN
          SELECT P.INTEIRO
            INTO V_DIAS_NOTIFICACAO
            FROM TSIPAR P
           WHERE P.CHAVE = 'DTNOTINT';
      EXCEPTION
          WHEN NO_DATA_FOUND THEN
              RAISE_APPLICATION_ERROR(-20011, 'Parametro TSIPAR DTNOTINT nao cadastrado.');
          WHEN TOO_MANY_ROWS THEN
              RAISE_APPLICATION_ERROR(-20012, 'Parametro TSIPAR DTNOTINT duplicado.');
      END;

      BEGIN
          SELECT P.TEXTO
            INTO V_GRUPOS
            FROM TSIPAR P
           WHERE P.CHAVE = 'GRPCOMINT';
      EXCEPTION
          WHEN NO_DATA_FOUND THEN
              RAISE_APPLICATION_ERROR(-20013, 'Parametro TSIPAR GRPCOMINT nao cadastrado.');
          WHEN TOO_MANY_ROWS THEN
              RAISE_APPLICATION_ERROR(-20014, 'Parametro TSIPAR GRPCOMINT duplicado.');
      END;

      IF V_DIAS_NOTIFICACAO IS NULL OR V_DIAS_NOTIFICACAO < 0 THEN
          RAISE_APPLICATION_ERROR(-20015, 'DTNOTINT deve ser inteiro maior ou igual a zero.');
      END IF;

      V_GRUPOS := REPLACE(REPLACE(TRIM(V_GRUPOS), ' ', ''), ';', ',');

      IF V_GRUPOS IS NULL
         OR NOT REGEXP_LIKE(V_GRUPOS, '^[0-9]+(,[0-9]+)*$') THEN
          RAISE_APPLICATION_ERROR(
              -20016,
              'GRPCOMINT deve conter CODGRUPO numerico separado por virgula ou ponto e virgula.'
          );
      END IF;

      FOR G IN (
          SELECT DISTINCT
                 TO_NUMBER(REGEXP_SUBSTR(V_GRUPOS, '[^,]+', 1, LEVEL)) AS CODGRUPO
            FROM DUAL
          CONNECT BY LEVEL <= REGEXP_COUNT(V_GRUPOS, ',') + 1
      )
      LOOP
          SELECT COUNT(*)
            INTO V_QTD_GRUPO
            FROM TSIUSU U
           WHERE U.CODGRUPO = G.CODGRUPO;

          IF V_QTD_GRUPO = 0 THEN
              RAISE_APPLICATION_ERROR(
                  -20017,
                  'CODGRUPO ' || G.CODGRUPO || ' configurado em GRPCOMINT nao possui usuarios.'
              );
          END IF;

          FOR X IN (
              SELECT DISTINCT
                     CAB.NUNOTA,
                     CAB.NUMNOTA,
                     CAB.CODEMP,
                     CAB.CODPARC,
                     CAB.DTPREVENT,
                     TRUNC(CAB.DTPREVENT) - TRUNC(SYSDATE) AS DIAS_RESTANTES
                FROM TGFCAB CAB
                JOIN TGFITE ITE
                  ON ITE.NUNOTA = CAB.NUNOTA
                JOIN TGFPRO PRO
                  ON PRO.CODPROD = ITE.CODPROD
               WHERE CAB.TIPMOV = 'C'
                 AND CAB.STATUSNOTA = 'L'
                 AND ITE.PENDENTE = 'S'
                 AND PRO.ORIGPROD IN (1, 2, 6, 7)
                 AND CAB.DTPREVENT IS NOT NULL
                 AND TRUNC(CAB.DTPREVENT) - TRUNC(SYSDATE)
                     BETWEEN 0 AND V_DIAS_NOTIFICACAO
          )
          LOOP
              V_IDENTIFICADOR :=
                  'FRI:' || X.NUNOTA || ':' || G.CODGRUPO || ':' ||
                  TO_CHAR(X.DTPREVENT, 'YYYYMMDD');

              SELECT COUNT(*)
                INTO V_QTD_GRUPO
                FROM TSIAVI A
               WHERE A.IDENTIFICADOR = V_IDENTIFICADOR;

              IF V_QTD_GRUPO = 0 THEN
                  SELECT NVL(ULTCOD, 0) + 1
                    INTO V_NUAVISO
                    FROM TGFNUM
                   WHERE ARQUIVO = 'TSIAVI'
                     FOR UPDATE;

                  SELECT NVL(MAX(NUAVISO), 0)
                    INTO V_MAXAVISO
                    FROM TSIAVI;

                  IF V_MAXAVISO >= V_NUAVISO THEN
                      V_NUAVISO := V_MAXAVISO + 1;
                  END IF;

                  V_ASSUNTO := 'Compra internacional próxima da data prevista';
                  V_MENSAGEM :=
                      '<b>ATENÇÃO</b><br><br>' ||
                      'A compra internacional <b>' || X.NUMNOTA ||
                      '</b> (NUNOTA ' || X.NUNOTA || ') possui previsão para <b>' ||
                      TO_CHAR(X.DTPREVENT, 'DD/MM/YYYY') || '</b>.<br>' ||
                      'Faltam <b>' || X.DIAS_RESTANTES ||
                      '</b> dia(s) para a data prevista.<br><br>' ||
                      'Realize o acompanhamento comercial da compra.';

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
                      V_NUAVISO,
                      V_ASSUNTO,
                      V_MENSAGEM,
                      '',
                      V_IDENTIFICADOR,
                      0,
                      NULL,
                      G.CODGRUPO,
                      'P',
                      SYSDATE,
                      -1,
                      NULL
                  );

                  UPDATE TGFNUM
                     SET ULTCOD = V_NUAVISO
                   WHERE ARQUIVO = 'TSIAVI';
              END IF;
          END LOOP;
      END LOOP;
  END PRA_ACTION_NOTIF_COMPRA_INT;
  /

  SHOW ERRORS PROCEDURE PRA_ACTION_NOTIF_COMPRA_INT;
