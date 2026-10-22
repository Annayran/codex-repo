<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!doctype html><html lang="pt-BR"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>MA Genesis - Consulta</title><snk:load/><style>/*__APP_CSS__*/</style></head><body>
<div id="genesis-bootstrap" hidden data-mode="sankhya-readonly">
<c:catch var="configurationError"><snk:query var="configuration">
SELECT 'CENTRAL' ID,'Central de parametros' NAME,'Cadastrada' STATUS,'AD_CENTPARAM / NUPAR=1' ORIGIN,'Agrupa configuracoes da integracao' PURPOSE FROM AD_CENTPARAM WHERE NUPAR=1
UNION ALL
SELECT 'ENDERECO','Endereco da API',CASE WHEN TRIM(URLGENESIS) IS NULL THEN 'Nao preenchido' ELSE 'Preenchido; valor protegido' END,'AD_CENTPARAM / NUPAR=1','Define o destino das chamadas; valor nao exposto neste componente' FROM AD_CENTPARAM WHERE NUPAR=1
UNION ALL
SELECT 'CREDENCIAL','Credencial da API',CASE WHEN TRIM(TOKENGENESIS) IS NULL THEN 'Nao preenchida' ELSE 'Preenchida; valor protegido' END,'AD_CENTPARAM / NUPAR=1','Autentica as chamadas; a presenca nao comprova validade ou permissao' FROM AD_CENTPARAM WHERE NUPAR=1
</snk:query></c:catch>
<section data-source="configuration"><c:if test="${not empty configurationError}"><span data-error>Consulta de parametros indisponivel.</span></c:if><c:if test="${empty configurationError}"><c:forEach items="${configuration.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span><span data-field="name"><c:out value="${r.NAME}" /></span><span data-field="status"><c:out value="${r.STATUS}" /></span><span data-field="origin"><c:out value="${r.ORIGIN}" /></span><span data-field="purpose"><c:out value="${r.PURPOSE}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="domainsError"><snk:query var="domains">
SELECT * FROM (SELECT IDREGDOM ID,TIPODOMINIO KIND,IDGENESIS REMOTE_ID,NOME NAME,CODIGOSANKHYA LOCAL_CODE,STATUSGENESIS STATUS,STATUSCONC CONCILIATION,MENSAGEMCONC MESSAGE FROM AD_GENDOMINIO WHERE IDBASE=1 ORDER BY TIPODOMINIO,IDREGDOM) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="domains"><c:if test="${not empty domainsError}"><span data-error>Consulta de dominios indisponivel.</span></c:if><c:if test="${empty domainsError}"><c:forEach items="${domains.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span><span data-field="kind"><c:out value="${r.KIND}" /></span><span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span><span data-field="name"><c:out value="${r.NAME}" /></span><span data-field="local_code"><c:out value="${r.LOCAL_CODE}" /></span><span data-field="status"><c:out value="${r.STATUS}" /></span><span data-field="conciliation"><c:out value="${r.CONCILIATION}" /></span><span data-field="message"><c:out value="${r.MESSAGE}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="equipmentError"><snk:query var="equipment">
SELECT * FROM (SELECT I.IDREGITEM ID,I.IDGENESIS REMOTE_ID,I.CODPROD PRODUCT,I.NSERIE SERIAL,I.NUNOTA NOTE,I.STATUSGENESIS STATUS,I.STATUSCONC CONCILIATION,I.IDCLIENTEGEN CLIENT_ID,I.IDMODELOGEN MODEL_ID,I.IDSETORGEN SECTOR_ID,P.DESCRPROD NAME,PAR.RAZAOSOCIAL CLIENT,TO_CHAR(I.ULTIMAINSTALACAO,'DD/MM/YYYY HH24:MI') UPDATED FROM AD_GENITEMMAT I LEFT JOIN TGFPRO P ON P.CODPROD=I.CODPROD LEFT JOIN TGFPAR PAR ON PAR.CODPARC=I.CODPARC WHERE I.IDBASE=1 ORDER BY I.IDREGITEM DESC) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="equipment"><c:if test="${not empty equipmentError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty equipmentError}"><c:forEach items="${equipment.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="product"><c:out value="${r.PRODUCT}" /></span>
<span data-field="serial"><c:out value="${r.SERIAL}" /></span>
<span data-field="note"><c:out value="${r.NOTE}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="conciliation"><c:out value="${r.CONCILIATION}" /></span>
<span data-field="client_id"><c:out value="${r.CLIENT_ID}" /></span>
<span data-field="model_id"><c:out value="${r.MODEL_ID}" /></span>
<span data-field="sector_id"><c:out value="${r.SECTOR_ID}" /></span>
<span data-field="name"><c:out value="${r.NAME}" /></span>
<span data-field="client"><c:out value="${r.CLIENT}" /></span>
<span data-field="updated"><c:out value="${r.UPDATED}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="movementsError"><snk:query var="movements">
SELECT * FROM (SELECT D.IDINTGENESIS ID,D.CHAVE_MOVIMENTO CONTEXT,D.NUNOTA NOTE,REGEXP_SUBSTR(D.CHAVE_MOVIMENTO,'[^|]+',1,4) PRODUCT,REGEXP_SUBSTR(D.CHAVE_MOVIMENTO,'[^|]+',1,5) SERIAL,D.STATUS_INTEGRACAO STATUS,D.PROTOCOLO_GENESIS REMOTE_ID,D.ETAPA_ATUAL STAGE,D.ENTIDADE_BLOQ ENTITY,D.ULT_OPERACAO METHOD,D.HTTP_STATUS HTTP,D.TENTATIVAS ATTEMPTS,TO_CHAR(D.DH_ATUALIZACAO,'DD/MM/YYYY HH24:MI') UPDATED,TO_CHAR(D.PROX_TENTATIVA,'DD/MM/YYYY HH24:MI') NEXT_ATTEMPT,DBMS_LOB.SUBSTR(D.MENSAGEM_ERRO,2000,1) MESSAGE FROM (SELECT M.*,ROW_NUMBER() OVER(PARTITION BY M.CHAVE_MOVIMENTO ORDER BY M.DH_ATUALIZACAO DESC NULLS LAST,M.IDINTGENESIS DESC) RN FROM AD_GENMOVEST M WHERE M.CHAVE_MOVIMENTO NOT LIKE 'API|%') D WHERE D.RN=1 ORDER BY D.DH_ATUALIZACAO DESC NULLS LAST,D.IDINTGENESIS DESC) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="movements"><c:if test="${not empty movementsError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty movementsError}"><c:forEach items="${movements.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="context"><c:out value="${r.CONTEXT}" /></span>
<span data-field="note"><c:out value="${r.NOTE}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="product"><c:out value="${r.PRODUCT}" /></span>
<span data-field="serial"><c:out value="${r.SERIAL}" /></span>
<span data-field="stage"><c:out value="${r.STAGE}" /></span>
<span data-field="entity"><c:out value="${r.ENTITY}" /></span>
<span data-field="method"><c:out value="${r.METHOD}" /></span>
<span data-field="http"><c:out value="${r.HTTP}" /></span>
<span data-field="attempts"><c:out value="${r.ATTEMPTS}" /></span>
<span data-field="updated"><c:out value="${r.UPDATED}" /></span>
<span data-field="next_attempt"><c:out value="${r.NEXT_ATTEMPT}" /></span>
<span data-field="message"><c:out value="${r.MESSAGE}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="historyError"><snk:query var="history">
SELECT * FROM (SELECT IDREQ ID,EXECUCAO EXECUTION,FLUXO FLOW,CONTEXTO CONTEXT,CHAVE_MOVIMENTO MOVEMENT,CHAVE_CONTROLE CONTROL,METODO METHOD,ENDPOINT,HTTP_STATUS HTTP,CLASSIFICACAO STATUS,IDCONSULTADO QUERIED_ID,IDRETORNO REMOTE_ID,IDENTIDADE IDENTITY_STATE,PERSISTENCIA PERSISTENCE,DBMS_LOB.SUBSTR(REQUISICAO,4000,1) REQUEST_BODY,DBMS_LOB.SUBSTR(RESPOSTA,4000,1) RESPONSE_BODY,DBMS_LOB.SUBSTR(DETALHE,2000,1) DETAIL,TO_CHAR(DH_INICIO,'DD/MM/YYYY HH24:MI:SS') UPDATED,DURACAO_MS DURATION FROM AD_GENREQLOG ORDER BY DH_INICIO DESC,IDREQ) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="history"><c:if test="${not empty historyError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty historyError}"><c:forEach items="${history.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="execution"><c:out value="${r.EXECUTION}" /></span>
<span data-field="flow"><c:out value="${r.FLOW}" /></span>
<span data-field="context"><c:out value="${r.CONTEXT}" /></span>
<span data-field="movement"><c:out value="${r.MOVEMENT}" /></span>
<span data-field="control"><c:out value="${r.CONTROL}" /></span>
<span data-field="method"><c:out value="${r.METHOD}" /></span>
<span data-field="endpoint"><c:out value="${r.ENDPOINT}" /></span>
<span data-field="http"><c:out value="${r.HTTP}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="queried_id"><c:out value="${r.QUERIED_ID}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="identity_state"><c:out value="${r.IDENTITY_STATE}" /></span>
<span data-field="persistence"><c:out value="${r.PERSISTENCE}" /></span>
<span data-field="updated"><c:out value="${r.UPDATED}" /></span>
<span data-field="request_body"><c:out value="${r.REQUEST_BODY}" /></span>
<span data-field="response_body"><c:out value="${r.RESPONSE_BODY}" /></span>
<span data-field="detail"><c:out value="${r.DETAIL}" /></span>
<span data-field="duration"><c:out value="${r.DURATION}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="stockError"><snk:query var="stock">
SELECT * FROM (SELECT C.CODPROD ID,P.DESCRPROD NAME,C.CODIGOEXTERNO EXTERNAL_CODE,C.IDGENESIS REMOTE_ID,C.STATUS,C.STATUSREMOTO REMOTE_STATUS,C.OPERACAO METHOD,C.HTTP_STATUS HTTP,C.TENTATIVAS ATTEMPTS,TO_CHAR(C.DH_ATUALIZACAO,'DD/MM/YYYY HH24:MI') UPDATED,TO_CHAR(C.PROXIMATENTATIVA,'DD/MM/YYYY HH24:MI') NEXT_ATTEMPT,C.MENSAGEM MESSAGE FROM AD_GENESTCTRL C LEFT JOIN TGFPRO P ON P.CODPROD=C.CODPROD ORDER BY C.DH_ATUALIZACAO DESC NULLS LAST,C.CODPROD) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="stock"><c:if test="${not empty stockError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty stockError}"><c:forEach items="${stock.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="name"><c:out value="${r.NAME}" /></span>
<span data-field="external_code"><c:out value="${r.EXTERNAL_CODE}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="remote_status"><c:out value="${r.REMOTE_STATUS}" /></span>
<span data-field="method"><c:out value="${r.METHOD}" /></span>
<span data-field="http"><c:out value="${r.HTTP}" /></span>
<span data-field="attempts"><c:out value="${r.ATTEMPTS}" /></span>
<span data-field="updated"><c:out value="${r.UPDATED}" /></span>
<span data-field="next_attempt"><c:out value="${r.NEXT_ATTEMPT}" /></span>
<span data-field="message"><c:out value="${r.MESSAGE}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="cancellationsError"><snk:query var="cancellations">
SELECT * FROM (SELECT IDINTGENESIS ID,CHAVE_MOVIMENTO CONTEXT,NUNOTA_ORIGEM NOTE,IDGENESIS REMOTE_ID,STATUS,TENTATIVAS ATTEMPTS,HTTP_STATUS HTTP,TO_CHAR(DH_ATUALIZACAO,'DD/MM/YYYY HH24:MI') UPDATED,MENSAGEM MESSAGE FROM AD_GENEXCFILA ORDER BY DH_ATUALIZACAO DESC NULLS LAST,IDINTGENESIS DESC) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="cancellations"><c:if test="${not empty cancellationsError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty cancellationsError}"><c:forEach items="${cancellations.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="context"><c:out value="${r.CONTEXT}" /></span>
<span data-field="note"><c:out value="${r.NOTE}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="attempts"><c:out value="${r.ATTEMPTS}" /></span>
<span data-field="http"><c:out value="${r.HTTP}" /></span>
<span data-field="updated"><c:out value="${r.UPDATED}" /></span>
<span data-field="message"><c:out value="${r.MESSAGE}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="scopeError"><snk:query var="scope">
SELECT * FROM (SELECT 'EMPRESA' KIND,TO_CHAR(C.CODEMP) ID,E.NOMEFANTASIA NAME,'AD_CENTPARAMEMP / NUPAR=1' STATUS FROM AD_CENTPARAMEMP C LEFT JOIN TSIEMP E ON E.CODEMP=C.CODEMP WHERE C.NUPAR=1 UNION ALL SELECT 'LOCAL',TO_CHAR(C.CODLOCAL),L.DESCRLOCAL,'AD_CENTPARAMLOC / NUPAR=1' FROM AD_CENTPARAMLOC C LEFT JOIN TGFLOC L ON L.CODLOCAL=C.CODLOCAL WHERE C.NUPAR=1 UNION ALL SELECT 'TOP',TO_CHAR(CODTIPOPER),NULL,'AD_CENTPARAMTOP / NUPAR=1' FROM AD_CENTPARAMTOP WHERE NUPAR=1 ORDER BY KIND,ID) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="scope"><c:if test="${not empty scopeError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty scopeError}"><c:forEach items="${scope.rows}" var="r"><div data-row>
<span data-field="kind"><c:out value="${r.KIND}" /></span>
<span data-field="name"><c:out value="${r.NAME}" /></span>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="summaryError"><snk:query var="summary">
SELECT COUNT(*) TOTAL,NVL(SUM(CASE WHEN STATUS_INTEGRACAO='ENVIADO' THEN 1 ELSE 0 END),0) SENT,NVL(SUM(CASE WHEN STATUS_INTEGRACAO IN ('ERRO','REVISAO','ENVIO_INCERTO') THEN 1 ELSE 0 END),0) ERRORS,NVL(SUM(CASE WHEN STATUS_INTEGRACAO IN ('PENDENTE','REPROCESSAR') THEN 1 ELSE 0 END),0) WAITING FROM (SELECT M.STATUS_INTEGRACAO,ROW_NUMBER() OVER(PARTITION BY M.CHAVE_MOVIMENTO ORDER BY M.DH_ATUALIZACAO DESC NULLS LAST,M.IDINTGENESIS DESC) RN FROM AD_GENMOVEST M WHERE M.CHAVE_MOVIMENTO NOT LIKE 'API|%') WHERE RN=1
</snk:query></c:catch>
<section data-source="summary"><c:if test="${not empty summaryError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty summaryError}"><c:forEach items="${summary.rows}" var="r"><div data-row>
<span data-field="total"><c:out value="${r.TOTAL}" /></span>
<span data-field="sent"><c:out value="${r.SENT}" /></span>
<span data-field="errors"><c:out value="${r.ERRORS}" /></span>
<span data-field="waiting"><c:out value="${r.WAITING}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="clientsError"><snk:query var="clients">
SELECT * FROM (SELECT IDREGCLI ID,IDGENESIS REMOTE_ID,CODPARC LOCAL_CODE,RAZAOSOCIAL NAME,CODIGOEXTERNO EXTERNAL_CODE,STATUSGENESIS STATUS,STATUSCONC CONCILIATION FROM AD_GENCLIENTE WHERE IDBASE=1 ORDER BY IDREGCLI DESC) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="clients"><c:if test="${not empty clientsError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty clientsError}"><c:forEach items="${clients.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="local_code"><c:out value="${r.LOCAL_CODE}" /></span>
<span data-field="name"><c:out value="${r.NAME}" /></span>
<span data-field="external_code"><c:out value="${r.EXTERNAL_CODE}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="conciliation"><c:out value="${r.CONCILIATION}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="sectorsError"><snk:query var="sectors">
SELECT * FROM (SELECT IDREGSET ID,IDGENESIS REMOTE_ID,CODPARC LOCAL_CODE,NOME NAME,IDCLIENTEGEN CLIENT_ID,CODIGOEXTERNO EXTERNAL_CODE,STATUSGENESIS STATUS,STATUSCONC CONCILIATION FROM AD_GENSECTOR WHERE IDBASE=1 ORDER BY IDREGSET DESC) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="sectors"><c:if test="${not empty sectorsError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty sectorsError}"><c:forEach items="${sectors.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="local_code"><c:out value="${r.LOCAL_CODE}" /></span>
<span data-field="name"><c:out value="${r.NAME}" /></span>
<span data-field="client_id"><c:out value="${r.CLIENT_ID}" /></span>
<span data-field="external_code"><c:out value="${r.EXTERNAL_CODE}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="conciliation"><c:out value="${r.CONCILIATION}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="materialsError"><snk:query var="materials">
SELECT * FROM (SELECT IDREGMAT ID,IDGENESIS REMOTE_ID,CODGRUPOPROD LOCAL_CODE,NOME NAME,CODIGOEXTERNO EXTERNAL_CODE,STATUSGENESIS STATUS,STATUSCONC CONCILIATION FROM AD_GENMATERIAL WHERE IDBASE=1 ORDER BY IDREGMAT DESC) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="materials"><c:if test="${not empty materialsError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty materialsError}"><c:forEach items="${materials.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="local_code"><c:out value="${r.LOCAL_CODE}" /></span>
<span data-field="name"><c:out value="${r.NAME}" /></span>
<span data-field="external_code"><c:out value="${r.EXTERNAL_CODE}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="conciliation"><c:out value="${r.CONCILIATION}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="manufacturersError"><snk:query var="manufacturers">
SELECT * FROM (SELECT IDREGEMP ID,IDGENESIS REMOTE_ID,MARCASANKHYA LOCAL_CODE,RAZAOSOCIAL NAME,CODIGOEXTERNO EXTERNAL_CODE,STATUSGENESIS STATUS,STATUSCONC CONCILIATION,CASE WHEN FABRICANTE=1 THEN 'Sim' WHEN FABRICANTE=0 THEN 'Nao' ELSE 'Nao informado' END MANUFACTURER,CASE WHEN FORNECEDOR=1 THEN 'Sim' WHEN FORNECEDOR=0 THEN 'Nao' ELSE 'Nao informado' END SUPPLIER FROM AD_GENEMPRESA WHERE IDBASE=1 ORDER BY IDREGEMP DESC) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="manufacturers"><c:if test="${not empty manufacturersError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty manufacturersError}"><c:forEach items="${manufacturers.rows}" var="r"><div data-row>
<span data-field="manufacturer"><c:out value="${r.MANUFACTURER}" /></span>
<span data-field="supplier"><c:out value="${r.SUPPLIER}" /></span>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="local_code"><c:out value="${r.LOCAL_CODE}" /></span>
<span data-field="name"><c:out value="${r.NAME}" /></span>
<span data-field="external_code"><c:out value="${r.EXTERNAL_CODE}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="conciliation"><c:out value="${r.CONCILIATION}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="modelsError"><snk:query var="models">
SELECT * FROM (SELECT IDREGMOD ID,IDGENESIS REMOTE_ID,CODPROD LOCAL_CODE,NOME NAME,IDEMPRESAGEN MANUFACTURER_ID,IDMATERIALGEN MATERIAL_ID,CODIGOEXTERNO EXTERNAL_CODE,STATUSGENESIS STATUS,STATUSCONC CONCILIATION FROM AD_GENMODELO WHERE IDBASE=1 ORDER BY IDREGMOD DESC) WHERE ROWNUM<=100
</snk:query></c:catch>
<section data-source="models"><c:if test="${not empty modelsError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty modelsError}"><c:forEach items="${models.rows}" var="r"><div data-row>
<span data-field="id"><c:out value="${r.ID}" /></span>
<span data-field="remote_id"><c:out value="${r.REMOTE_ID}" /></span>
<span data-field="local_code"><c:out value="${r.LOCAL_CODE}" /></span>
<span data-field="name"><c:out value="${r.NAME}" /></span>
<span data-field="manufacturer_id"><c:out value="${r.MANUFACTURER_ID}" /></span>
<span data-field="material_id"><c:out value="${r.MATERIAL_ID}" /></span>
<span data-field="external_code"><c:out value="${r.EXTERNAL_CODE}" /></span>
<span data-field="status"><c:out value="${r.STATUS}" /></span>
<span data-field="conciliation"><c:out value="${r.CONCILIATION}" /></span>
</div></c:forEach></c:if></section>
<c:catch var="dailyError"><snk:query var="daily">
SELECT TO_CHAR(D.BUCKET_DATE,'DD/MM') LABEL,TO_CHAR(D.BUCKET_DATE,'YYYY-MM-DD') CHART_DATE,NVL(H.SUCCESS,0) SUCCESS,NVL(H.ERRORS,0) ERRORS FROM (SELECT TRUNC(SYSDATE)-90+LEVEL BUCKET_DATE FROM DUAL CONNECT BY LEVEL<=90) D LEFT JOIN (SELECT TRUNC(DH_INICIO) BUCKET_DATE,SUM(CASE WHEN HTTP_STATUS BETWEEN 200 AND 299 THEN 1 ELSE 0 END) SUCCESS,SUM(CASE WHEN HTTP_STATUS>=400 THEN 1 ELSE 0 END) ERRORS FROM AD_GENREQLOG WHERE DH_INICIO>=TRUNC(SYSDATE)-89 AND DH_INICIO<TRUNC(SYSDATE)+1 GROUP BY TRUNC(DH_INICIO)) H ON H.BUCKET_DATE=D.BUCKET_DATE ORDER BY D.BUCKET_DATE
</snk:query></c:catch>
<section data-source="daily"><c:if test="${not empty dailyError}"><span data-error>Consulta indisponivel. Verifique estrutura e permissao do componente.</span></c:if><c:if test="${empty dailyError}"><c:forEach items="${daily.rows}" var="r"><div data-row>
<span data-field="label"><c:out value="${r.LABEL}" /></span>
<span data-field="date"><c:out value="${r.CHART_DATE}" /></span>
<span data-field="success"><c:out value="${r.SUCCESS}" /></span>
<span data-field="errors"><c:out value="${r.ERRORS}" /></span>
</div></c:forEach></c:if></section>
</div><div id="root"></div><script>/*__APP_JS__*/</script></body></html>
