<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%!
  private static int inteiroSeguro(String valor, int padrao) {
    try { return Math.max(1, Integer.parseInt(valor)); }
    catch (Exception ignore) { return padrao; }
  }
  private static String filtroSql(String valor) {
    if (valor == null) return "";
    String limpo = valor.trim();
    if (limpo.length() > 100) limpo = limpo.substring(0, 100);
    return limpo.replace("'", "''").toUpperCase();
  }
%>
<%
  boolean baseInstaladaAtiva = "installed".equals(request.getParameter("view"));
  String area = request.getParameter("area");
  if (area == null) area = request.getParameter("view") == null ? "overview" : "equipment";
  if (!java.util.Arrays.asList("overview","equipment","stock","cancellations","diagnostics","registrations","refresh","history").contains(area)) area = "overview";
  request.setAttribute("dashboardArea", area);
  int paginaBase = inteiroSeguro(request.getParameter("page"), 1);
  int tamanhoSolicitado = inteiroSeguro(request.getParameter("pageSize"), 25);
  int tamanhoBase = tamanhoSolicitado == 50 || tamanhoSolicitado == 100 || tamanhoSolicitado == 200 ? tamanhoSolicitado : 25;
  request.setAttribute("dashboardView", baseInstaladaAtiva ? "installed" : "sent");
  request.setAttribute("basePage", paginaBase);
  request.setAttribute("basePageSize", tamanhoBase);
  request.setAttribute("baseStart", ((paginaBase - 1) * tamanhoBase) + 1);
  request.setAttribute("baseEnd", paginaBase * tamanhoBase);
  int paginaCatalogo = Math.min(100000, inteiroSeguro(request.getParameter("catalogPage"), 1));
  request.setAttribute("catalogPage", paginaCatalogo);
  request.setAttribute("catalogStart", (paginaCatalogo - 1) * 25 + 1);
  request.setAttribute("catalogEnd", paginaCatalogo * 25);
  request.setAttribute("filtroEquipamento", filtroSql(request.getParameter("equipment")));
  request.setAttribute("filtroCodigo", filtroSql(request.getParameter("code")));
  request.setAttribute("filtroDocumento", filtroSql(request.getParameter("document")));
  request.setAttribute("filtroCliente", filtroSql(request.getParameter("customer")));
  request.setAttribute("filtroSerie", filtroSql(request.getParameter("serial")));
  request.setAttribute("buscaGeral", filtroSql(request.getParameter("search")));
  request.setAttribute("historyContext", filtroSql(request.getParameter("historyContext")));
%>
<!doctype html>
<html lang="pt-BR">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>MA Hospitalar - Integração Genesis</title>
  <link rel="stylesheet" href="assets/genesis.css">
  <snk:load />
</head>
<body data-area="${dashboardArea}" data-view="${dashboardView}" data-page="${basePage}" data-page-size="${basePageSize}">
<div class="brand-ribbon">Tecnologia e controle para uma operação hospitalar mais segura</div>
<div class="brand-shell">
  <div class="brand-lockup" aria-label="MA Hospitalar">
    <img class="brand-logo" src="https://d19yc5pi7p696q.cloudfront.net/Custom/Content/Themes/MaHospitalar/Imagens/MALogo/logo-ma-vertical.png" alt="MA Hospitalar">
  </div>
  <div class="system-name"><span></span>Integração Genesis</div>
</div>
<c:if test="${dashboardArea == 'overview'}">
<snk:query var="monitorIntegracao">
  SELECT COUNT(*) TOTAL,
         SUM(CASE WHEN STATUS_INTEGRACAO='ENVIADO' THEN 1 ELSE 0 END) ENVIADOS,
         SUM(CASE WHEN STATUS_INTEGRACAO IN ('ERRO','REVISAO','ENVIO_INCERTO') THEN 1 ELSE 0 END) ERROS,
         SUM(CASE WHEN STATUS_INTEGRACAO='REPROCESSAR' THEN 1 ELSE 0 END) REPROCESSAR,
         SUM(CASE WHEN STATUS_INTEGRACAO='PENDENTE' THEN 1 ELSE 0 END) PENDENTES,
         SUM(CASE WHEN STATUS_INTEGRACAO='CANCELADO' THEN 1 ELSE 0 END) CANCELADOS,
         CASE WHEN COUNT(*)=0 THEN 0 ELSE
           ROUND(100 * SUM(CASE WHEN STATUS_INTEGRACAO='ENVIADO' THEN 1 ELSE 0 END) / COUNT(*),1)
         END PCT_SUCESSO,
         CASE WHEN COUNT(*)=0 THEN 0 ELSE
           ROUND(360 * SUM(CASE WHEN STATUS_INTEGRACAO='ENVIADO' THEN 1 ELSE 0 END) / COUNT(*),0)
         END ANGULO_SUCESSO,
         TO_CHAR(MAX(DH_ATUALIZACAO),'DD/MM/YYYY HH24:MI') ULTIMA_ATUALIZACAO
    FROM (
      SELECT GM.STATUS_INTEGRACAO,GM.DH_ATUALIZACAO,
             ROW_NUMBER() OVER (
               PARTITION BY GM.CHAVE_MOVIMENTO
               ORDER BY GM.DH_ATUALIZACAO DESC NULLS LAST,GM.IDINTGENESIS DESC
             ) RN
        FROM AD_GENMOVEST GM
       WHERE GM.CHAVE_MOVIMENTO NOT LIKE 'API|%'
    )
   WHERE RN=1
</snk:query>
</c:if>
<c:if test="${dashboardArea == 'overview' or dashboardArea == 'cancellations'}">
<snk:query var="monitorCancelamentos">
  SELECT NVL(SUM(CASE WHEN STATUS='PENDENTE' THEN 1 ELSE 0 END),0) PENDENTES,
         NVL(SUM(CASE WHEN STATUS='CONCLUIDO' THEN 1 ELSE 0 END),0) CONCLUIDOS,
         NVL(SUM(CASE WHEN STATUS='ERRO' THEN 1 ELSE 0 END),0) ERROS,
         NVL(SUM(CASE WHEN STATUS='REVISAO' THEN 1 ELSE 0 END),0) REVISOES
    FROM AD_GENEXCFILA WHERE ('${buscaGeral}' IS NULL OR TO_CHAR(NUNOTA_ORIGEM) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(CHAVE_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(STATUS) LIKE '%'||'${buscaGeral}'||'%')
</snk:query>
</c:if>
<c:if test="${dashboardArea == 'stock'}">
<snk:query var="catalogoDisponivel">
  SELECT COUNT(*) TOTAL FROM USER_TABLES WHERE TABLE_NAME='AD_GENESTCTRL'
</snk:query>
<c:if test="${catalogoDisponivel.rows[0].TOTAL > 0}">
  <snk:query var="catalogoTotal">
    SELECT COUNT(*) TOTAL FROM AD_GENESTCTRL C LEFT JOIN TGFPRO P ON P.CODPROD=C.CODPROD WHERE ('${buscaGeral}' IS NULL OR TO_CHAR(C.CODPROD) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(P.DESCRPROD) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(C.STATUS) LIKE '%'||'${buscaGeral}'||'%' OR TO_CHAR(C.IDGENESIS) LIKE '%'||'${buscaGeral}'||'%')
  </snk:query>
  <snk:query var="catalogoEstoques">
    SELECT * FROM (
      SELECT C.CODPROD,P.DESCRPROD,C.IDGENESIS,C.STATUS,C.STATUSREMOTO,C.OPERACAO,
             C.HTTP_STATUS,C.TENTATIVAS,C.MENSAGEM,
             TO_CHAR(C.DH_CONFIRMACAO,'DD/MM/YYYY HH24:MI') CONFIRMACAO,
             TO_CHAR(C.PROXIMATENTATIVA,'DD/MM/YYYY HH24:MI') PROXIMA,
             ROW_NUMBER() OVER (ORDER BY C.DH_ATUALIZACAO DESC NULLS LAST,C.CODPROD) RN
        FROM AD_GENESTCTRL C LEFT JOIN TGFPRO P ON P.CODPROD=C.CODPROD WHERE ('${buscaGeral}' IS NULL OR TO_CHAR(C.CODPROD) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(P.DESCRPROD) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(C.STATUS) LIKE '%'||'${buscaGeral}'||'%' OR TO_CHAR(C.IDGENESIS) LIKE '%'||'${buscaGeral}'||'%')
    ) WHERE RN BETWEEN ${catalogStart} AND ${catalogEnd}
  </snk:query>
</c:if>
</c:if>
<c:if test="${dashboardArea == 'overview'}">
<snk:query var="monitorCooldown">
  SELECT TO_CHAR(MAX(ATE),'DD/MM/YYYY HH24:MI:SS') ATE,
         CEIL(NVL((MAX(ATE)-SYSDATE)*86400,0)) SEGUNDOS
    FROM (
      SELECT DH_ATUALIZACAO+NVL(TO_NUMBER(REGEXP_SUBSTR(MSG,
             'COOLDOWN_MS=([0-9]{1,15});',1,1,NULL,1)),60000)/86400000 ATE
        FROM (
          SELECT DH_ATUALIZACAO,DBMS_LOB.SUBSTR(MENSAGEM_ERRO,1000,1) MSG
            FROM AD_GENMOVEST WHERE HTTP_STATUS=429
          UNION ALL
          SELECT DH_ATUALIZACAO,MENSAGEM MSG FROM AD_GENEXCFILA WHERE HTTP_STATUS=429
        )
    ) WHERE ATE>SYSDATE
</snk:query>
</c:if>
<c:if test="${dashboardArea == 'equipment' and dashboardView == 'sent'}">
<snk:query var="movimentos">
  SELECT D.* FROM (
      SELECT V.NUNOTA,V.NUMNOTA,V.CODTIPOPER,V.CODPARC,V.RAZAO_PARCEIRO,V.CNPJ_CPF_PARCEIRO,V.CIDADE,
             V.CODPROD,V.DESCRPROD,V.PRODUTO,V.SERIE,V.TIPO,V.PODER,V.TIPO_ESTOQUE,
             V.DESCRLOCAL,V.STATUS_ESTOQUE_DESC,V.VLRUNIT,V.CHAVE_MOVIMENTO,
             M.IDINTGENESIS,M.STATUS_INTEGRACAO,M.TENTATIVAS,M.HTTP_STATUS,
             DBMS_LOB.SUBSTR(M.MENSAGEM_ERRO,3000,1) MENSAGEM_ERRO,
             M.PROTOCOLO_GENESIS,M.DH_ENVIO,M.DH_ATUALIZACAO,V.DTNEG,
             TO_CHAR(V.DTNEG,'DD/MM/YYYY') ULTIMA_MOVIMENTACAO,
             TO_CHAR(V.DTNEG,'YYYYMMDDHH24MISS') ULTIMA_ORDEM,V.SEQUENCIA,
             ROW_NUMBER() OVER (
               PARTITION BY V.CODPROD,UPPER(TRIM(V.SERIE))
               ORDER BY CASE WHEN M.IDINTGENESIS IS NULL THEN 1 ELSE 0 END,
                        M.DH_ATUALIZACAO DESC NULLS LAST,
                        M.IDINTGENESIS DESC NULLS LAST,
                        V.DTNEG DESC,V.SEQUENCIA DESC,V.CHAVE_MOVIMENTO DESC
             ) RN
        FROM AD_VW_GENESIS_ESTOQUE V
        LEFT JOIN (
          SELECT X.* FROM (
            SELECT GM.*,
                   ROW_NUMBER() OVER (
                     PARTITION BY GM.CHAVE_MOVIMENTO
                     ORDER BY GM.DH_ATUALIZACAO DESC NULLS LAST,
                              GM.IDINTGENESIS DESC
                   ) RN_MOV
              FROM AD_GENMOVEST GM
          ) X
          WHERE X.RN_MOV=1
        ) M ON M.CHAVE_MOVIMENTO=V.CHAVE_MOVIMENTO
       WHERE V.SERIE IS NOT NULL
    ) D
    WHERE D.RN=1
  ORDER BY D.DTNEG DESC,D.NUNOTA DESC,D.SEQUENCIA DESC
</snk:query>
</c:if>
<c:if test="${dashboardArea == 'equipment' and dashboardView == 'installed'}">
<snk:query var="itensInstaladosTotal">
  SELECT COUNT(*) TOTAL
    FROM AD_GENITEMMAT I
    LEFT JOIN AD_GENCLIENTE CLI ON CLI.IDBASE=I.IDBASE AND CLI.IDGENESIS=I.IDCLIENTEGEN
    LEFT JOIN TGFPAR PAR ON PAR.CODPARC=I.CODPARC
    LEFT JOIN TGFPRO PRO ON PRO.CODPROD=I.CODPROD
   WHERE I.IDBASE=1
       AND ('${filtroSerie}' IS NULL OR UPPER(I.NSERIE) LIKE '%'||'${filtroSerie}'||'%')
     AND ('${filtroEquipamento}'='' OR UPPER(NVL(PRO.DESCRPROD,'')) LIKE '%' || '${filtroEquipamento}' || '%')
     AND ('${filtroCodigo}'='' OR TO_CHAR(I.CODPROD) LIKE '%' || '${filtroCodigo}' || '%')
     AND ('${filtroDocumento}'='' OR UPPER(NVL(TO_CHAR(PAR.CGC_CPF),NVL(CLI.CNPJ,''))) LIKE '%' || '${filtroDocumento}' || '%')
     AND ('${filtroCliente}'='' OR UPPER(NVL(PAR.RAZAOSOCIAL,NVL(CLI.RAZAOSOCIAL,''))) LIKE '%' || '${filtroCliente}' || '%')
</snk:query>
<snk:query var="itensInstalados">
  SELECT * FROM (
    SELECT D.*,ROW_NUMBER() OVER (ORDER BY D.ULTIMAINSTALACAO DESC NULLS LAST,D.IDREGITEM DESC) RN
      FROM (
        SELECT I.IDREGITEM,I.IDGENESIS,I.IDMODELOGEN,I.IDCLIENTEGEN,I.IDSETORGEN,
           I.CODPROD,I.NSERIE,I.NUNOTA,
           I.ULTIMAINSTALACAO,
           TO_CHAR(I.ULTIMAINSTALACAO,'DD/MM/YYYY') ULTIMA_MOVIMENTACAO,
           TO_CHAR(I.ULTIMAINSTALACAO,'YYYYMMDDHH24MISS') ULTIMA_ORDEM,
           I.STATUSGENESIS,I.STATUSCONC,
           NVL(PAR.RAZAOSOCIAL,CLI.RAZAOSOCIAL) AS RAZAO_PARCEIRO,
           NVL(PAR.CGC_CPF,CLI.CNPJ) AS CNPJ_CPF_PARCEIRO,
           NVL(CID.NOMECID,CLI.ENDERECOCIDADE) AS CIDADE,
           TRIM(CLI.ENDERECO ||
             CASE WHEN CLI.ENDERECONUMERO IS NOT NULL THEN ', ' || CLI.ENDERECONUMERO END ||
             CASE WHEN CLI.ENDERECOBAIRRO IS NOT NULL THEN ' - ' || CLI.ENDERECOBAIRRO END ||
             CASE WHEN CLI.ENDERECOCIDADE IS NOT NULL THEN ' - ' || CLI.ENDERECOCIDADE END ||
             CASE WHEN CLI.ENDERECOUF IS NOT NULL THEN '/' || CLI.ENDERECOUF END ||
             CASE WHEN CLI.CEP IS NOT NULL THEN ' - CEP ' || CLI.CEP END) AS ENDERECO_CLIENTE,
           PRO.DESCRPROD
      FROM AD_GENITEMMAT I
      LEFT JOIN AD_GENCLIENTE CLI
        ON CLI.IDBASE=I.IDBASE AND CLI.IDGENESIS=I.IDCLIENTEGEN
      LEFT JOIN TGFPAR PAR ON PAR.CODPARC=I.CODPARC
      LEFT JOIN TSICID CID ON CID.CODCID=PAR.CODCID
      LEFT JOIN TGFPRO PRO ON PRO.CODPROD=I.CODPROD
     WHERE I.IDBASE=1
       AND ('${filtroSerie}' IS NULL OR UPPER(I.NSERIE) LIKE '%'||'${filtroSerie}'||'%')
       AND ('${filtroEquipamento}'='' OR UPPER(NVL(PRO.DESCRPROD,'')) LIKE '%' || '${filtroEquipamento}' || '%')
       AND ('${filtroCodigo}'='' OR TO_CHAR(I.CODPROD) LIKE '%' || '${filtroCodigo}' || '%')
       AND ('${filtroDocumento}'='' OR UPPER(NVL(TO_CHAR(PAR.CGC_CPF),NVL(CLI.CNPJ,''))) LIKE '%' || '${filtroDocumento}' || '%')
       AND ('${filtroCliente}'='' OR UPPER(NVL(PAR.RAZAOSOCIAL,NVL(CLI.RAZAOSOCIAL,''))) LIKE '%' || '${filtroCliente}' || '%')
      ) D
  ) WHERE RN BETWEEN ${baseStart} AND ${baseEnd}
</snk:query>
</c:if>
<c:if test="${dashboardArea == 'cancellations' or dashboardArea == 'diagnostics'}">
  <snk:query var="operacoesTotal">
    <c:choose><c:when test="${dashboardArea == 'cancellations'}">
      SELECT COUNT(*) TOTAL FROM AD_GENEXCFILA WHERE ('${buscaGeral}' IS NULL OR TO_CHAR(NUNOTA_ORIGEM) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(CHAVE_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(STATUS) LIKE '%'||'${buscaGeral}'||'%')
    </c:when><c:otherwise>
      SELECT COUNT(*) TOTAL FROM AD_GENMOVEST WHERE CHAVE_MOVIMENTO NOT LIKE 'API|%' AND STATUS_INTEGRACAO IN ('PENDENTE','REPROCESSAR','ERRO','REVISAO','ENVIO_INCERTO') AND ('${buscaGeral}' IS NULL OR TO_CHAR(NUNOTA) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(CHAVE_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(STATUS_INTEGRACAO) LIKE '%'||'${buscaGeral}'||'%')
    </c:otherwise></c:choose>
  </snk:query>
  <snk:query var="operacoes">
    SELECT * FROM (
      <c:choose><c:when test="${dashboardArea == 'cancellations'}">
        SELECT IDINTGENESIS,CHAVE_MOVIMENTO,NUNOTA_ORIGEM NOTA,IDGENESIS PROTOCOLO,STATUS,TENTATIVAS,HTTP_STATUS,
               MENSAGEM,TO_CHAR(DH_ATUALIZACAO,'DD/MM/YYYY HH24:MI') ATUALIZACAO,
               ROW_NUMBER() OVER (ORDER BY DH_ATUALIZACAO DESC NULLS LAST,IDINTGENESIS DESC) RN
          FROM AD_GENEXCFILA WHERE ('${buscaGeral}' IS NULL OR TO_CHAR(NUNOTA_ORIGEM) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(CHAVE_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(STATUS) LIKE '%'||'${buscaGeral}'||'%')
      </c:when><c:otherwise>
        SELECT IDINTGENESIS,CHAVE_MOVIMENTO,NUNOTA NOTA,PROTOCOLO_GENESIS PROTOCOLO,STATUS_INTEGRACAO STATUS,TENTATIVAS,HTTP_STATUS,
               CASE WHEN CHAVE_MOVIMENTO LIKE 'API|%' THEN CHAVE_MOVIMENTO || ' - ' END ||
               'Etapa: '||ETAPA_ATUAL||'; entidade: '||ENTIDADE_BLOQ||'; operacao: '||ULT_OPERACAO||
               '; proxima tentativa estimada: '||TO_CHAR(PROX_TENTATIVA,'DD/MM/YYYY HH24:MI:SS')||' - '||
               DBMS_LOB.SUBSTR(MENSAGEM_ERRO,2500,1) MENSAGEM,
               TO_CHAR(DH_ATUALIZACAO,'DD/MM/YYYY HH24:MI') ATUALIZACAO,
               ROW_NUMBER() OVER (ORDER BY DH_ATUALIZACAO DESC NULLS LAST,IDINTGENESIS DESC) RN
          FROM AD_GENMOVEST WHERE CHAVE_MOVIMENTO NOT LIKE 'API|%' AND STATUS_INTEGRACAO IN ('PENDENTE','REPROCESSAR','ERRO','REVISAO','ENVIO_INCERTO') AND ('${buscaGeral}' IS NULL OR TO_CHAR(NUNOTA) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(CHAVE_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(STATUS_INTEGRACAO) LIKE '%'||'${buscaGeral}'||'%')
      </c:otherwise></c:choose>
    ) WHERE RN BETWEEN ${catalogStart} AND ${catalogEnd}
  </snk:query>
</c:if>
<div class="workspace-shell">
<nav class="workspace-nav" aria-label="Áreas da integração">
  <span class="nav-heading">Genesis</span>
  <button type="button" data-area-target="overview" class="workspace-link ${dashboardArea == 'overview' ? 'is-active' : ''}" aria-current="${dashboardArea == 'overview' ? 'page' : 'false'}">Visão geral</button>
  <button type="button" data-area-target="equipment" class="workspace-link ${dashboardArea == 'equipment' ? 'is-active' : ''}" aria-current="${dashboardArea == 'equipment' ? 'page' : 'false'}">Equipamentos</button>
  <button type="button" data-area-target="registrations" class="workspace-link ${dashboardArea == 'registrations' ? 'is-active' : ''}">Cadastros</button>
  <button type="button" data-area-target="refresh" class="workspace-link ${dashboardArea == 'refresh' ? 'is-active' : ''}">Reconsultas</button>
  <button type="button" data-area-target="stock" class="workspace-link ${dashboardArea == 'stock' ? 'is-active' : ''}" aria-current="${dashboardArea == 'stock' ? 'page' : 'false'}">Estoques e preços</button>
  <button type="button" data-area-target="cancellations" class="workspace-link ${dashboardArea == 'cancellations' ? 'is-active' : ''}" aria-current="${dashboardArea == 'cancellations' ? 'page' : 'false'}">Cancelamentos</button>
  <button type="button" data-area-target="diagnostics" class="workspace-link ${dashboardArea == 'diagnostics' ? 'is-active' : ''}" aria-current="${dashboardArea == 'diagnostics' ? 'page' : 'false'}">Diagnóstico</button>
  <button type="button" data-area-target="history" class="workspace-link ${dashboardArea == 'history' ? 'is-active' : ''}">Requisições</button>
</nav>
<main>
  <div class="action-bar">
    <button type="button" id="themeToggle" class="icon-button theme-toggle" aria-label="Ativar tema escuro" title="Ativar tema escuro" aria-pressed="false"></button>
    <button type="button" onclick="location.reload()">Atualizar dados</button>
  </div>
  <c:if test="${dashboardArea != 'overview' and dashboardArea != 'equipment' and dashboardArea != 'history'}">
    <form class="unified-filter filter-bar" method="get" role="search"><input type="hidden" name="area" value="${dashboardArea}"><label><span>Pesquisar registros</span><input type="search" name="search" maxlength="100" value="${fn:escapeXml(param.search)}" placeholder="Código, nota, série, entidade ou estado"></label><button type="submit">Pesquisar</button><button type="button" data-clear-search class="secondary-button">Limpar</button></form>
  </c:if>
  <c:if test="${dashboardArea == 'overview'}">
  <section class="dashboard-section monitor-section" aria-labelledby="monitorTitle">
    <header class="section-heading">
      <div><span class="section-kicker">Monitor da integração</span><h2 id="monitorTitle">Saúde das requisições</h2></div>
      <span class="section-note">Último estado de cada envio</span>
    </header>
    <c:forEach items="${monitorCooldown.rows}" var="cooldown">
      <c:if test="${cooldown.SEGUNDOS > 0}">
        <div class="cooldown-status" role="status">
          <span class="section-kicker">Limite da API</span>
          <strong>Em espera até <c:out value="${cooldown.ATE}" /></strong>
          <span class="section-note"><c:out value="${cooldown.SEGUNDOS}" /> segundos restantes</span>
        </div>
      </c:if>
    </c:forEach>
    <c:forEach items="${monitorIntegracao.rows}" var="row">
      <div class="overview-kpis">
        <div class="overview-kpi"><span>Equipamentos processados</span><strong><c:out value="${row.TOTAL}" /></strong></div>
        <div class="overview-kpi is-success"><span>Envios confirmados</span><strong><c:out value="${row.ENVIADOS}" /></strong></div>
        <div class="overview-kpi is-attention"><span>Erros e revisão</span><strong><c:out value="${row.ERROS}" /></strong></div>
      </div>
      <div class="monitor-body">
        <article class="monitor-chart">
          <div class="status-donut" style="--success-angle: ${row.ANGULO_SUCESSO}deg"><div><strong><c:out value="${row.PCT_SUCESSO}" />%</strong><span>sucesso</span></div></div>
          <div class="monitor-copy"><span>Taxa acumulada</span><strong><c:out value="${row.ENVIADOS}" /> de <c:out value="${row.TOTAL}" /> enviados</strong><small>Atualizado em <c:out value="${row.ULTIMA_ATUALIZACAO}" /></small></div>
        </article>
        <article class="status-breakdown">
          <div class="status-line total"><span>Total processado</span><b><c:out value="${row.TOTAL}" /></b></div>
          <div class="status-line success"><span>Enviados</span><b><c:out value="${row.ENVIADOS}" /></b></div>
          <div class="status-line danger"><span>Erros</span><b><c:out value="${row.ERROS}" /></b></div>
          <div class="status-line warning"><span>Reprocessar</span><b><c:out value="${row.REPROCESSAR}" /></b></div>
          <div class="status-line pending"><span>Pendentes</span><b><c:out value="${row.PENDENTES}" /></b></div>
          <div class="status-line pending"><span>Envios cancelados</span><b><c:out value="${row.CANCELADOS}" /></b></div>
        </article>
      </div>
    </c:forEach>
  </section>
  </c:if>
  <c:if test="${dashboardArea == 'overview' or dashboardArea == 'cancellations'}">
  <section class="panel">
    <div class="panel-title"><div><span class="section-kicker">Cancelamentos</span><h2>Inativações no Genesis</h2></div></div>
    <c:forEach items="${monitorCancelamentos.rows}" var="row">
      <dl class="cancelamento-resumo">
        <div><dt>Pendentes</dt><dd><c:out value="${row.PENDENTES}" /></dd></div>
        <div><dt>Concluídas</dt><dd><c:out value="${row.CONCLUIDOS}" /></dd></div>
        <div><dt>Erros</dt><dd><c:out value="${row.ERROS}" /></dd></div>
        <div><dt>Revisão</dt><dd><c:out value="${row.REVISOES}" /></dd></div>
      </dl>
    </c:forEach>
  </section>
  </c:if>
  <c:if test="${dashboardArea == 'equipment'}">
  <section class="panel">
    <div class="panel-title"><div><span class="section-kicker">Base instalada</span><h2>Clientes e equipamentos</h2></div><span class="panel-caption">Última movimentação por produto e série</span></div>
    <div class="subtabs" role="tablist" aria-label="Origem dos equipamentos">
      <button id="sentTab" class="subtab${dashboardView == 'sent' ? ' is-active' : ''}" type="button" role="tab" aria-selected="${dashboardView == 'sent'}" data-source="sent">Envios</button>
      <button id="installedTab" class="subtab${dashboardView == 'installed' ? ' is-active' : ''}" type="button" role="tab" aria-selected="${dashboardView == 'installed'}" data-source="installed">Base instalada</button>
    </div>
    <form id="equipmentFilters" class="filter-bar" role="search" method="get" aria-label="Filtros dos equipamentos">
      <input type="hidden" name="view" value="${dashboardView}">
      <input type="hidden" name="area" value="equipment">
      <input type="hidden" name="page" value="1">
      <input id="filterPageSize" type="hidden" name="pageSize" value="${basePageSize}">
      <label class="filter-equipment"><span>Equipamento</span><input id="equipmentFilter" name="equipment" type="search" value="${fn:escapeXml(param.equipment)}" placeholder="Modelo ou descrição"></label>
      <label class="filter-serial"><span>Número de série</span><input id="serialFilter" name="serial" type="search" maxlength="100" value="${fn:escapeXml(param.serial)}" placeholder="Série do equipamento"></label>
      <label class="filter-code"><span>Código</span><input id="codeFilter" name="code" type="search" inputmode="numeric" value="${fn:escapeXml(param.code)}" placeholder="Produto"></label>
      <label class="filter-document"><span>CNPJ</span><input id="documentFilter" name="document" type="search" inputmode="numeric" value="${fn:escapeXml(param.document)}" placeholder="Cliente"></label>
      <label class="filter-customer"><span>Razão social</span><input id="customerFilter" name="customer" type="search" value="${fn:escapeXml(param.customer)}" placeholder="Nome do cliente"></label>
      <button id="searchFilters" class="primary-filter-button" type="submit">Pesquisar</button>
      <button id="clearFilters" class="secondary-button" type="button" aria-label="Limpar todos os filtros">Limpar</button>
    </form>
    <c:set var="quantidadeRegistros" value="0" />
    <c:if test="${dashboardView == 'installed'}">
      <c:set var="quantidadeRegistros" value="${itensInstaladosTotal.rows[0].TOTAL}" />
    </c:if>
    <div class="table-wrap"><table id="queue" data-total="${quantidadeRegistros}"><thead><tr>
      <th><button class="sort-button" type="button" data-column="0">Razão social / cliente</button></th>
      <th><button class="sort-button" type="button" data-column="1">CNPJ</button></th>
      <th><button class="sort-button" type="button" data-column="2">Cidade</button></th>
      <th><button class="sort-button" type="button" data-column="3">Endereço</button></th>
      <th><button class="sort-button" type="button" data-column="4">Código</button></th>
      <th><button class="sort-button" type="button" data-column="5">Item / modelo</button></th>
      <th><button class="sort-button" type="button" data-column="6">Número de série</button></th>
      <th><button class="sort-button" type="button" data-column="7">Última</button></th>
      <th><button class="sort-button" type="button" data-column="8">O.S.</button></th>
      <th><button class="sort-button" type="button" data-column="9">Integração</button></th>
      <th><button class="sort-button" type="button" data-column="10">Situação</button></th>
      <th>Detalhes / vínculos</th>
    </tr></thead><tbody>
    <c:forEach items="${movimentos.rows}" var="row"><c:if test="${row.STATUS_INTEGRACAO == 'ENVIADO'}"><tr data-source="sent">
      <td class="customer-cell"><strong><c:out value="${row.RAZAO_PARCEIRO}" /></strong></td>
      <td><c:out value="${row.CNPJ_CPF_PARCEIRO}" /></td><td><c:out value="${row.CIDADE}" /></td><td>-</td>
      <td><c:out value="${row.CODPROD}" /></td><td class="model-cell"><c:out value="${row.DESCRPROD}" /></td><td><c:out value="${row.SERIE}" /></td>
      <td data-sort="${row.ULTIMA_ORDEM}"><c:out value="${row.ULTIMA_MOVIMENTACAO}" /></td><td><c:out value="${row.NUNOTA}" /></td>
      <td><span class="status"><c:choose><c:when test="${empty row.STATUS_INTEGRACAO}">NÃO PROCESSADO</c:when><c:otherwise><c:out value="${row.STATUS_INTEGRACAO}" /></c:otherwise></c:choose></span></td>
      <td><c:choose><c:when test="${empty row.STATUS_ESTOQUE_DESC or row.STATUS_ESTOQUE_DESC == 'A_CONFIRMAR'}"><span class="value-unknown">Não classificado</span></c:when><c:otherwise><c:out value="${row.STATUS_ESTOQUE_DESC}" /></c:otherwise></c:choose></td>
      <td class="error-cell"><c:choose>
        <c:when test="${not empty row.MENSAGEM_ERRO or row.HTTP_STATUS >= 400 or row.STATUS_INTEGRACAO == 'ERRO' or row.STATUS_INTEGRACAO == 'REPROCESSAR'}">
          <button class="error-detail-button" type="button">Ver detalhes</button>
          <span class="error-raw"><c:out value="${row.MENSAGEM_ERRO}" /></span>
          <span class="error-http"><c:out value="${row.HTTP_STATUS}" /></span>
          <span class="error-status"><c:out value="${row.STATUS_INTEGRACAO}" /></span>
        </c:when>
        <c:otherwise>-</c:otherwise>
      </c:choose><button type="button" data-history-context="${fn:escapeXml(row.CHAVE_MOVIMENTO)}">Histórico</button></td>
    </tr></c:if></c:forEach>
    <c:forEach items="${itensInstalados.rows}" var="row"><tr data-source="installed">
      <td class="customer-cell"><strong><c:out value="${row.RAZAO_PARCEIRO}" /></strong></td>
      <td><c:out value="${row.CNPJ_CPF_PARCEIRO}" /></td><td><c:out value="${row.CIDADE}" /></td><td class="address-cell"><c:out value="${row.ENDERECO_CLIENTE}" /></td>
      <td><c:out value="${row.CODPROD}" /></td><td class="model-cell"><c:out value="${row.DESCRPROD}" /></td><td><c:out value="${row.NSERIE}" /></td>
      <td data-sort="${row.ULTIMA_ORDEM}"><c:out value="${row.ULTIMA_MOVIMENTACAO}" /></td><td><c:out value="${row.NUNOTA}" /></td>
      <td><span class="status">INSTALADO</span></td>
      <td><c:choose><c:when test="${empty row.STATUSGENESIS}"><span class="value-unknown">Não classificado</span></c:when><c:otherwise><c:out value="${row.STATUSGENESIS}" /></c:otherwise></c:choose></td>
      <td class="reference-cell" title="Vínculos utilizados pela integração">
        <span>Item <c:out value="${row.IDGENESIS}" /></span>
        <span>Modelo <c:out value="${row.IDMODELOGEN}" /></span>
        <span>Cliente <c:out value="${row.IDCLIENTEGEN}" /></span>
        <span>Setor <c:out value="${row.IDSETORGEN}" /></span>
      </td>
    </tr></c:forEach>
    </tbody></table></div>
    <footer class="pagination" aria-label="Paginação da consolidação">
      <label>Linhas por página <select id="pageSize"><option value="25">25</option><option value="50">50</option><option value="100">100</option><option value="200">200</option></select></label>
      <span id="paginationSummary" aria-live="polite"></span>
      <div class="pagination-actions">
        <button id="firstPage" class="page-button" type="button" aria-label="Primeira página" title="Primeira página">&laquo;</button>
        <button id="previousPage" class="page-button" type="button" aria-label="Página anterior" title="Página anterior">&lsaquo;</button>
        <span id="pageIndicator"></span>
        <button id="nextPage" class="page-button" type="button" aria-label="Próxima página" title="Próxima página">&rsaquo;</button>
        <button id="lastPage" class="page-button" type="button" aria-label="Última página" title="Última página">&raquo;</button>
      </div>
    </footer>
  </section>
  </c:if>
  <dialog id="errorDialog" class="error-dialog" aria-labelledby="errorDialogTitle">
    <form method="dialog" class="error-dialog-shell">
      <header class="error-dialog-header">
        <div><span class="section-kicker">Diagnóstico da integração</span><h2 id="errorDialogTitle">Não foi possível concluir o envio</h2></div>
        <button class="dialog-close" type="submit" aria-label="Fechar" title="Fechar">&times;</button>
      </header>
      <div class="error-dialog-body">
        <p id="errorSummary" class="error-summary"></p>
        <dl class="error-metadata">
          <div><dt>Entidade</dt><dd id="errorEntity">-</dd></div>
          <div><dt>Operação</dt><dd id="errorStage">-</dd></div>
          <div><dt>HTTP</dt><dd id="errorHttp">-</dd></div>
          <div><dt>Classificação</dt><dd id="errorClassification">-</dd></div>
        </dl>
        <section class="error-guidance"><h3>Evidência disponível</h3><p id="errorApiMessage">-</p></section>
        <section class="error-guidance"><h3>O que verificar</h3><p id="errorAction">-</p></section>
        <details class="error-technical"><summary>Detalhe técnico</summary><pre id="errorTechnical"></pre></details>
      </div>
      <footer class="error-dialog-footer"><button class="primary-button" type="submit">Fechar</button></footer>
    </form>
  </dialog>
  <c:if test="${dashboardArea == 'stock' and catalogoDisponivel.rows[0].TOTAL > 0}">
    <section class="panel" id="catalogo">
      <div class="panel-title"><div><span class="section-kicker">Catálogo Genesis</span><h2>Estoques, peças e serviços</h2></div>
        <span class="panel-caption">Tabela de preço 101</span></div>
      <div class="table-wrap"><table><thead><tr>
        <th>Produto</th><th>Descrição</th><th>ID Genesis</th><th>Confirmado</th><th>Última tentativa</th>
        <th>Operação</th><th>HTTP</th><th>Tentativas</th><th>Confirmação</th><th>Próxima tentativa</th><th>Mensagem</th>
      </tr></thead><tbody>
        <c:forEach items="${catalogoEstoques.rows}" var="item">
          <tr><td><c:out value="${item.CODPROD}" /></td><td><c:out value="${item.DESCRPROD}" /></td>
            <td><c:out value="${item.IDGENESIS}" /></td><td><c:out value="${item.STATUSREMOTO}" /></td>
            <td><c:out value="${item.STATUS}" /></td><td><c:out value="${item.OPERACAO}" /></td>
            <td><c:out value="${item.HTTP_STATUS}" /></td><td><c:out value="${item.TENTATIVAS}" /></td>
            <td><c:out value="${item.CONFIRMACAO}" /></td><td><c:out value="${item.PROXIMA}" /></td>
            <td class="operation-message"><c:choose><c:when test="${not empty item.MENSAGEM}"><details><summary>Ver mensagem</summary><pre><c:out value="${item.MENSAGEM}" /></pre></details></c:when><c:otherwise>-</c:otherwise></c:choose><button type="button" data-history-context="CODPROD=${fn:escapeXml(item.CODPROD)},">Histórico</button></td></tr>
        </c:forEach>
        <c:if test="${empty catalogoEstoques.rows}"><tr><td colspan="11" class="empty-state">Nenhum registro encontrado.</td></tr></c:if>
      </tbody></table></div>
      <div class="pagination">
        <span><c:out value="${catalogoTotal.rows[0].TOTAL}" /> registros · página <c:out value="${catalogPage}" /></span>
        <button type="button" aria-label="Página anterior" title="Página anterior" data-catalog-page="${catalogPage - 1}" ${catalogPage == 1 ? 'disabled' : ''}>&lsaquo;</button>
        <button type="button" aria-label="Próxima página" title="Próxima página" data-catalog-page="${catalogPage + 1}" ${catalogEnd >= catalogoTotal.rows[0].TOTAL ? 'disabled' : ''}>&rsaquo;</button>
      </div>
    </section>
  </c:if>
  <c:if test="${dashboardArea == 'stock' and catalogoDisponivel.rows[0].TOTAL == 0}">
    <div class="empty-area">Controle de estoques ainda não disponível neste ambiente.</div>
  </c:if>
  <c:if test="${dashboardArea == 'cancellations' or dashboardArea == 'diagnostics'}">
    <section class="panel operation-panel">
      <div class="panel-title"><h2>${dashboardArea == 'cancellations' ? 'Fila de inativação' : 'Pendências dos equipamentos'}</h2></div>
      <div class="table-wrap"><table><thead><tr><th>Controle</th><th>Nota</th><th>ID Genesis</th><th>Status</th><th>Tentativas</th><th>HTTP</th><th>Atualização</th><th>Mensagem</th></tr></thead><tbody>
        <c:forEach items="${operacoes.rows}" var="item"><tr>
          <td><c:out value="${item.IDINTGENESIS}" /></td><td><c:out value="${item.NOTA}" /></td><td><c:out value="${item.PROTOCOLO}" /></td>
          <td><span class="status" data-state="${fn:escapeXml(item.STATUS)}"><c:out value="${item.STATUS}" /></span></td><td><c:out value="${item.TENTATIVAS}" /></td><td><c:out value="${item.HTTP_STATUS}" /></td>
          <td><c:out value="${item.ATUALIZACAO}" /></td><td class="operation-message"><c:choose><c:when test="${not empty item.MENSAGEM}"><details><summary>Ver mensagem</summary><pre><c:out value="${item.MENSAGEM}" /></pre></details></c:when><c:otherwise>-</c:otherwise></c:choose><button type="button" data-history-context="${fn:escapeXml(item.CHAVE_MOVIMENTO)}">Histórico</button></td>
        </tr></c:forEach>
        <c:if test="${empty operacoes.rows}"><tr><td colspan="8">Nenhum registro encontrado.</td></tr></c:if>
      </tbody></table></div>
      <footer class="pagination"><span><c:out value="${operacoesTotal.rows[0].TOTAL}" /> registros · página <c:out value="${catalogPage}" /></span>
        <button type="button" aria-label="Página anterior" title="Página anterior" data-catalog-page="${catalogPage - 1}" ${catalogPage == 1 ? 'disabled' : ''}>&lsaquo;</button>
        <button type="button" aria-label="Próxima página" title="Próxima página" data-catalog-page="${catalogPage + 1}" ${catalogEnd >= operacoesTotal.rows[0].TOTAL ? 'disabled' : ''}>&rsaquo;</button>
      </footer>
    </section>
  </c:if>
  <c:if test="${dashboardArea == 'registrations' or dashboardArea == 'refresh' or dashboardArea == 'history'}">
    <snk:query var="rastreioDisponivel">SELECT COUNT(*) TOTAL FROM USER_TABLES WHERE TABLE_NAME IN ('AD_GENAPICTRL','AD_GENREQLOG')</snk:query>
    <c:choose><c:when test="${rastreioDisponivel.rows[0].TOTAL == 2}">
      <c:choose><c:when test="${dashboardArea == 'history'}">
        <snk:query var="historicoTotal">
          SELECT COUNT(*) TOTAL FROM AD_GENREQLOG WHERE ('${historyContext}' IS NULL OR UPPER(CONTEXTO) LIKE '%'||'${historyContext}'||'%' OR UPPER(CHAVE_CONTROLE)='${historyContext}')
        </snk:query>
        <snk:query var="historico">
          SELECT * FROM (SELECT IDREQ,EXECUCAO,FLUXO,CONTEXTO,CHAVE_CONTROLE,CHAVE_MOVIMENTO,METODO,ENDPOINT,HTTP_STATUS,CLASSIFICACAO,IDRETORNO,IDENTIDADE,PERSISTENCIA,
            TO_CHAR(DH_INICIO,'DD/MM/YYYY HH24:MI:SS') INICIO,DURACAO_MS,
            DBMS_LOB.SUBSTR(REQUISICAO,4000,1) REQUISICAO,DBMS_LOB.SUBSTR(RESPOSTA,4000,1) RESPOSTA,
            DBMS_LOB.SUBSTR(DETALHE,4000,1) DETALHE,
            DBMS_LOB.GETLENGTH(REQUISICAO) TAMREQ,DBMS_LOB.GETLENGTH(RESPOSTA) TAMRESP,
            ROW_NUMBER() OVER(ORDER BY DH_INICIO DESC,IDREQ) RN
            FROM AD_GENREQLOG WHERE ('${historyContext}' IS NULL OR UPPER(CONTEXTO) LIKE '%'||'${historyContext}'||'%' OR UPPER(CHAVE_CONTROLE)='${historyContext}'))
          WHERE RN BETWEEN ${catalogStart} AND ${catalogEnd}
        </snk:query>
        <section class="panel operation-panel">
          <div class="panel-title"><h2>Histórico de requisições</h2></div>
          <form method="get" class="trace-filter"><input type="hidden" name="area" value="history"><label>Movimento ou produto <input name="historyContext" value="${fn:escapeXml(param.historyContext)}" maxlength="100"></label><button type="submit">Pesquisar</button></form>
          <div class="table-wrap"><table><thead><tr><th>Início</th><th>Fluxo / contexto</th><th>Requisição</th><th>HTTP</th><th>Identidade</th><th>Persistência</th><th>Duração</th><th>Evidência</th></tr></thead><tbody>
          <c:forEach items="${historico.rows}" var="h"><tr>
            <td><c:out value="${h.INICIO}" /></td><td><c:out value="${h.FLUXO}" /><br><c:out value="${h.CONTEXTO}" /></td>
            <td><c:out value="${h.METODO}" /> <c:out value="${h.ENDPOINT}" /></td><td><c:out value="${h.HTTP_STATUS}" /><br><c:out value="${h.CLASSIFICACAO}" /></td>
            <td><c:out value="${h.IDENTIDADE}" /></td><td><c:choose><c:when test="${h.PERSISTENCIA != 'CONFIRMADA' and fn:startsWith(h.DETALHE, 'FALHA_PERSISTENCIA_LOCAL:')}">Falha local</c:when><c:otherwise><c:out value="${h.PERSISTENCIA}" /></c:otherwise></c:choose></td><td><c:out value="${h.DURACAO_MS}" /> ms</td>
            <td><details class="trace-detail"><summary>Ver detalhes</summary><dl><dt>Execução</dt><dd><c:out value="${h.EXECUCAO}" /></dd><dt>Controle</dt><dd><c:out value="${h.CHAVE_CONTROLE}" /></dd><dt>Movimento</dt><dd><c:out value="${h.CHAVE_MOVIMENTO}" /></dd><dt>ID retornado</dt><dd><c:out value="${h.IDRETORNO}" /></dd></dl>
              <h3>Enviado</h3><pre><c:out value="${h.REQUISICAO}" default="Sem corpo" /></pre><h3>Recebido</h3><pre><c:out value="${h.RESPOSTA}" default="Aguardando resposta" /></pre>
              <h3>Aplicação local</h3><pre><c:out value="${h.DETALHE}" default="Sem aplicação local confirmada nesta chamada" /></pre>
              <c:if test="${h.TAMREQ > 4000 or h.TAMRESP > 4000}"><p>Prévia limitada a 4.000 caracteres. Conteúdo sanitizado disponível no histórico da requisição.</p></c:if>
            </details></td>
          </tr></c:forEach><c:if test="${empty historico.rows}"><tr><td colspan="8">Nenhuma requisição encontrada.</td></tr></c:if>
          </tbody></table></div>
          <footer class="pagination"><span><c:out value="${historicoTotal.rows[0].TOTAL}" /> requisições</span><button type="button" data-catalog-page="${catalogPage - 1}" ${catalogPage == 1 ? 'disabled' : ''} aria-label="Página anterior">&lsaquo;</button><button type="button" data-catalog-page="${catalogPage + 1}" ${catalogEnd >= historicoTotal.rows[0].TOTAL ? 'disabled' : ''} aria-label="Próxima página">&rsaquo;</button></footer>
        </section>
      </c:when><c:otherwise>
        <snk:query var="controlesTotal">SELECT COUNT(*) TOTAL FROM AD_GENAPICTRL WHERE
          <c:choose><c:when test="${dashboardArea == 'refresh'}">CHAVE_MOVIMENTO LIKE 'API|REFRESH|%'</c:when><c:otherwise>CHAVE_MOVIMENTO NOT LIKE 'API|REFRESH|%'</c:otherwise></c:choose> AND ('${buscaGeral}' IS NULL OR UPPER(CHAVE_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(TIPO_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(STATUS_INTEGRACAO) LIKE '%'||'${buscaGeral}'||'%')
        </snk:query>
        <snk:query var="controlesApi">
          SELECT * FROM (SELECT CHAVE_MOVIMENTO,TIPO_MOVIMENTO,STATUS_INTEGRACAO,PROTOCOLO_GENESIS,HTTP_STATUS,TENTATIVAS,
            TO_CHAR(DH_ATUALIZACAO,'DD/MM/YYYY HH24:MI:SS') ATUALIZACAO,
            TO_CHAR(DH_ATUALIZACAO+CASE WHEN STATUS_INTEGRACAO='API_SINCRONIZADO' THEN 1 ELSE LEAST(60,POWER(2,LEAST(NVL(TENTATIVAS,0),6)))/1440 END,'DD/MM/YYYY HH24:MI:SS') ELEGIVEL,
            DBMS_LOB.SUBSTR(MENSAGEM_ERRO,3000,1) MENSAGEM,
            ROW_NUMBER() OVER(ORDER BY DH_ATUALIZACAO DESC,IDINTGENESIS DESC) RN FROM AD_GENAPICTRL WHERE
            <c:choose><c:when test="${dashboardArea == 'refresh'}">CHAVE_MOVIMENTO LIKE 'API|REFRESH|%'</c:when><c:otherwise>CHAVE_MOVIMENTO NOT LIKE 'API|REFRESH|%'</c:otherwise></c:choose> AND ('${buscaGeral}' IS NULL OR UPPER(CHAVE_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(TIPO_MOVIMENTO) LIKE '%'||'${buscaGeral}'||'%' OR UPPER(STATUS_INTEGRACAO) LIKE '%'||'${buscaGeral}'||'%')
          ) WHERE RN BETWEEN ${catalogStart} AND ${catalogEnd}
        </snk:query>
        <section class="panel operation-panel"><div class="panel-title"><h2>${dashboardArea == 'refresh' ? 'Reconsultas da base instalada' : 'Controle de cadastros'}</h2></div>
          <div class="table-wrap"><table><thead><tr><th>Entidade / chave</th><th>ID Genesis</th><th>Estado</th><th>HTTP</th><th>Atualização</th><c:if test="${dashboardArea == 'refresh'}"><th>Próxima elegibilidade</th></c:if><th>Detalhes</th></tr></thead><tbody>
          <c:forEach items="${controlesApi.rows}" var="a"><tr><td><c:out value="${a.TIPO_MOVIMENTO}" /><br><c:out value="${a.CHAVE_MOVIMENTO}" /></td><td><c:out value="${a.PROTOCOLO_GENESIS}" /></td><td><c:out value="${a.STATUS_INTEGRACAO}" /></td><td><c:out value="${a.HTTP_STATUS}" /></td><td><c:out value="${a.ATUALIZACAO}" /></td><c:if test="${dashboardArea == 'refresh'}"><td><c:out value="${a.ELEGIVEL}" /></td></c:if><td><details><summary>Ver controle</summary><pre><c:out value="${a.MENSAGEM}" /></pre><span>Tentativas: <c:out value="${a.TENTATIVAS}" /></span></details><button type="button" data-history-context="${fn:escapeXml(a.CHAVE_MOVIMENTO)}">Histórico</button></td></tr></c:forEach>
          <c:if test="${empty controlesApi.rows}"><tr><td colspan="7">Nenhum controle encontrado.</td></tr></c:if></tbody></table></div>
          <footer class="pagination"><span><c:out value="${controlesTotal.rows[0].TOTAL}" /> controles</span><button type="button" data-catalog-page="${catalogPage - 1}" ${catalogPage == 1 ? 'disabled' : ''} aria-label="Página anterior">&lsaquo;</button><button type="button" data-catalog-page="${catalogPage + 1}" ${catalogEnd >= controlesTotal.rows[0].TOTAL ? 'disabled' : ''} aria-label="Próxima página">&rsaquo;</button></footer>
        </section>
      </c:otherwise></c:choose>
    </c:when><c:otherwise><p role="status">Rastreabilidade indisponível: cadastrar AD_GENAPICTRL e AD_GENREQLOG pelo Construtor.</p></c:otherwise></c:choose>
  </c:if>
</main>
</div>
<dialog id="evidenceDialog" class="evidence-dialog" aria-labelledby="evidenceTitle"><header><h2 id="evidenceTitle">Detalhes do registro</h2><button type="button" id="closeEvidence" aria-label="Fechar detalhes" title="Fechar">&times;</button></header><div id="evidenceContent"></div></dialog>
<script src="assets/diagnosis.js"></script>
<script src="assets/genesis.js"></script>
</body>
</html>
