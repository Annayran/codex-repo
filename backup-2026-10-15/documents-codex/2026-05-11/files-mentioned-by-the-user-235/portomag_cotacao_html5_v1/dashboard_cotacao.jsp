<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Zaya.it Dashboard - Cotacao</title>
    <link rel="stylesheet" type="text/css" href="${BASE_FOLDER}/css/dashboardCotacao.css">
    <link rel="stylesheet" type="text/css" href="css/dashboardCotacao.css">
    <snk:load/>
</head>
<body>
    <snk:query var="prazoRows">
        <%
            String queryPrazo =
                "SELECT " +
                "CASE WHEN DIAS <= DIASPRAZO THEN 'No Prazo' ELSE 'Fora do Prazo' END PRAZO, " +
                "COUNT(NUNOTA) AS QTREQUISICAO " +
                "FROM ( " +
                "SELECT CAB.NUNOTA, " +
                "(NVL(CAP.DTENTSAI,TRUNC(SYSDATE)) - CAB.DTENTSAI) AS DIAS, " +
                ":DIASPRAZO AS DIASPRAZO " +
                "FROM TGFCAB CAB " +
                "INNER JOIN TGFITE ITE ON CAB.NUNOTA = ITE.NUNOTA " +
                "LEFT JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA " +
                "LEFT JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA " +
                "LEFT JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA " +
                "LEFT JOIN TGFVAR ITA ON ITA.NUNOTAORIG = ITC.NUNOTA AND ITA.SEQUENCIAORIG = ITV.SEQUENCIA " +
                "LEFT JOIN TGFCAB CAP ON ITA.NUNOTA = CAP.NUNOTA " +
                "WHERE CAB.CODTIPOPER IN (400) " +
                "AND CAB.DTNEG BETWEEN :PERIODO.INI AND :PERIODO.FIN " +
                "GROUP BY CAB.NUNOTA, (NVL(CAP.DTENTSAI,TRUNC(SYSDATE)) - CAB.DTENTSAI) " +
                ") " +
                "GROUP BY CASE WHEN DIAS <= DIASPRAZO THEN 'No Prazo' ELSE 'Fora do Prazo' END";
            out.println(queryPrazo);
        %>
    </snk:query>

    <snk:query var="statusRows">
        <%
            String queryStatus =
                "SELECT STATUS, COUNT(NUNOTA) QTREQUISICAO " +
                "FROM ( " +
                "SELECT CAB.NUNOTA, " +
                "CASE WHEN CAP.NUNOTA IS NOT NULL THEN 'Fechado' " +
                "     WHEN ITC.NUNOTA IS NOT NULL THEN 'Em andamento' " +
                "     ELSE 'Aberto' END STATUS " +
                "FROM TGFCAB CAB " +
                "INNER JOIN TGFITE ITE ON CAB.NUNOTA = ITE.NUNOTA " +
                "LEFT JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA " +
                "LEFT JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA " +
                "LEFT JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA " +
                "LEFT JOIN TGFVAR ITA ON ITA.NUNOTAORIG = ITC.NUNOTA AND ITA.SEQUENCIAORIG = ITV.SEQUENCIA " +
                "LEFT JOIN TGFCAB CAP ON ITA.NUNOTA = CAP.NUNOTA " +
                "WHERE CAB.CODTIPOPER IN (400) " +
                "AND CAB.DTNEG BETWEEN :PERIODO.INI AND :PERIODO.FIN " +
                "GROUP BY CAB.NUNOTA, CASE WHEN CAP.NUNOTA IS NOT NULL THEN 'Fechado' WHEN ITC.NUNOTA IS NOT NULL THEN 'Em andamento' ELSE 'Aberto' END " +
                ") GROUP BY STATUS";
            out.println(queryStatus);
        %>
    </snk:query>

    <snk:query var="cotacaoRows">
        <%
            String queryGrid =
                "SELECT CAB.NUNOTA, CAB.DTNEG, TOP.CODTIPOPER, TOP.DESCROPER, PRO.CODPROD, PRO.DESCRPROD, " +
                "(NVL(CAP.DTENTSAI,TRUNC(SYSDATE)) - CAB.DTENTSAI) AS DIAS, " +
                "CASE WHEN (NVL(CAP.DTENTSAI,TRUNC(SYSDATE)) - CAB.DTENTSAI) <= :DIASPRAZO THEN 'No Prazo' ELSE 'Fora do Prazo' END PRAZO " +
                "FROM TGFCAB CAB " +
                "INNER JOIN TGFITE ITE ON CAB.NUNOTA = ITE.NUNOTA " +
                "INNER JOIN TGFTOP TOP ON TOP.CODTIPOPER = CAB.CODTIPOPER AND TOP.DHALTER = CAB.DHTIPOPER " +
                "INNER JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD " +
                "LEFT JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA " +
                "LEFT JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA " +
                "LEFT JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA " +
                "LEFT JOIN TGFVAR ITA ON ITA.NUNOTAORIG = ITC.NUNOTA AND ITA.SEQUENCIAORIG = ITV.SEQUENCIA " +
                "LEFT JOIN TGFCAB CAP ON ITA.NUNOTA = CAP.NUNOTA " +
                "WHERE CAB.CODTIPOPER IN (400) " +
                "AND CAB.DTNEG BETWEEN :PERIODO.INI AND :PERIODO.FIN " +
                "AND CASE WHEN CAP.NUNOTA IS NOT NULL THEN 'Fechado' WHEN ITC.NUNOTA IS NOT NULL THEN 'Em andamento' ELSE 'Aberto' END = 'Aberto' " +
                "GROUP BY CAB.NUNOTA, CAB.DTNEG, TOP.CODTIPOPER, TOP.DESCROPER, PRO.CODPROD, PRO.DESCRPROD, (NVL(CAP.DTENTSAI,TRUNC(SYSDATE)) - CAB.DTENTSAI) " +
                "ORDER BY CAB.DTNEG";
            out.println(queryGrid);
        %>
    </snk:query>

    <main class="dashboard-shell">
        <header class="topbar">
            <div class="topbar-left">
                <div class="brand-mark">Z</div>
                <div><div class="brand-text">Zaya.it</div><span class="brand-sub">Dashboard</span></div>
                <div class="topbar-divider"></div>
                <div><h1>Cotacao</h1><p>Conversao HTML5 fiel ao gadget 301</p></div>
            </div>
            <div class="topbar-right"><span class="clock" id="clockDisplay">--:--:--</span><button class="ghost-button" onclick="window.location.reload()">Atualizar</button></div>
        </header>

        <section class="summary-grid">
            <article class="metric-card metric-card-strong"><span class="metric-label">Em cotacao</span><strong class="metric-value" id="totalCot">0</strong><small class="metric-foot">Registros abertos</small></article>
            <article class="metric-card"><span class="metric-label">No prazo</span><strong class="metric-value" id="totalNoPrazo">0</strong><small class="metric-foot">Conforme DIASPRAZO</small></article>
            <article class="metric-card"><span class="metric-label">Fora do prazo</span><strong class="metric-value metric-danger" id="totalForaPrazo">0</strong><small class="metric-foot">Acima do prazo</small></article>
            <article class="metric-card"><span class="metric-label">Status</span><strong class="metric-value" id="totalStatus">0</strong><small class="metric-foot">Total do grafico</small></article>
        </section>

        <section class="dashboard-layout">
            <aside class="side-column">
                <div class="chart-card"><div class="card-title">Prazo</div><canvas id="prazoChart" width="280" height="190"></canvas></div>
                <div class="chart-card"><div class="card-title">Status</div><canvas id="statusChart" width="280" height="190"></canvas></div>
            </aside>
            <section class="dashboard-content">
                <div class="panel-card">
                    <div class="panel-head"><div><h2>Em Cotacao</h2><p>Mesma base da grid do XML original.</p></div></div>
                    <div class="tabs-toolbar"><div class="tabs-nav"><button class="tab-button is-active"><span>Aberto</span><span class="tab-count" id="tabCount">0</span></button></div><div class="table-search"><input id="searchInput" placeholder="Buscar nota, produto, TOP..." autocomplete="off"><button class="ghost-button" id="clearSearch" type="button">Limpar</button></div></div>
                    <div class="search-info" id="searchInfo"></div>
                    <div id="tableArea"></div>
                </div>
            </section>
        </section>

        <table id="prazoSource" style="display:none"><tbody><c:forEach items="${prazoRows.rows}" var="r"><tr data-label="<c:out value="${r.PRAZO}" />" data-value="<c:out value="${r.QTREQUISICAO}" />"></tr></c:forEach></tbody></table>
        <table id="statusSource" style="display:none"><tbody><c:forEach items="${statusRows.rows}" var="r"><tr data-label="<c:out value="${r.STATUS}" />" data-value="<c:out value="${r.QTREQUISICAO}" />"></tr></c:forEach></tbody></table>
        <table id="gridSource" style="display:none"><tbody><c:forEach items="${cotacaoRows.rows}" var="r"><tr data-nunota="<c:out value="${r.NUNOTA}" />" data-dtneg="<c:out value="${r.DTNEG}" />" data-codtipoper="<c:out value="${r.CODTIPOPER}" />" data-descroper="<c:out value="${r.DESCROPER}" />" data-codprod="<c:out value="${r.CODPROD}" />" data-descrprod="<c:out value="${r.DESCRPROD}" />" data-dias="<c:out value="${r.DIAS}" />" data-prazo="<c:out value="${r.PRAZO}" />"></tr></c:forEach></tbody></table>
    </main>

    <script>
        function t(v){return v==null?'':String(v)} function n(v){var x=Number(String(v||'').replace(',','.'));return isNaN(x)?0:x} function e(s){return t(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;')} function f(v){return Number(v||0).toLocaleString('pt-BR')} function p(x){return x<10?'0'+x:x} function tick(){var d=new Date();document.getElementById('clockDisplay').textContent=p(d.getHours())+':'+p(d.getMinutes())+':'+p(d.getSeconds())}
        function rows(id){var out=[],rs=document.querySelectorAll('#'+id+' tr');for(var i=0;i<rs.length;i++)out.push({label:rs[i].getAttribute('data-label'),value:n(rs[i].getAttribute('data-value'))});return out}
        function grid(){var out=[],rs=document.querySelectorAll('#gridSource tr');for(var i=0;i<rs.length;i++){var r=rs[i];out.push({nunota:r.getAttribute('data-nunota'),dtneg:r.getAttribute('data-dtneg'),codtipoper:r.getAttribute('data-codtipoper'),descroper:r.getAttribute('data-descroper'),codprod:r.getAttribute('data-codprod'),descrprod:r.getAttribute('data-descrprod'),dias:r.getAttribute('data-dias'),prazo:r.getAttribute('data-prazo')})}return out}
        function draw(id,data){var c=document.getElementById(id),ctx=c.getContext('2d'),tot=0;ctx.clearRect(0,0,c.width,c.height);for(var i=0;i<data.length;i++)tot+=data[i].value;var colors=['#22c55e','#ef4444','#f97316','#3b82f6'],cx=74,cy=84,r=52,st=-Math.PI/2;ctx.lineWidth=18;for(var j=0;j<data.length;j++){var sl=tot?Math.PI*2*data[j].value/tot:0;ctx.strokeStyle=colors[j%colors.length];ctx.beginPath();ctx.arc(cx,cy,r,st,st+sl);ctx.stroke();st+=sl}if(!tot){ctx.strokeStyle='#e2e8f0';ctx.beginPath();ctx.arc(cx,cy,r,0,Math.PI*2);ctx.stroke()}ctx.fillStyle='#0f172a';ctx.font='800 22px Arial';ctx.textAlign='center';ctx.fillText(tot?Math.round(data[0].value/tot*100)+'%':'0%',cx,cy+8);ctx.textAlign='left';for(var k=0;k<data.length;k++){var y=52+k*33;ctx.fillStyle=colors[k%colors.length];ctx.fillRect(158,y-9,11,11);ctx.fillStyle='#64748b';ctx.font='700 10px Arial';ctx.fillText(data[k].label||'-',178,y);ctx.fillStyle='#0f172a';ctx.font='800 12px Arial';ctx.fillText(f(data[k].value),178,y+16)}}
        var prazo=rows('prazoSource'),status=rows('statusSource'),itens=grid();document.getElementById('totalCot').textContent=f(itens.length);document.getElementById('tabCount').textContent=f(itens.length);document.getElementById('totalStatus').textContent=f(status.reduce(function(a,b){return a+b.value},0));document.getElementById('totalNoPrazo').textContent=f(prazo.filter(function(x){return x.label==='No Prazo'})[0]?.value||0);document.getElementById('totalForaPrazo').textContent=f(prazo.filter(function(x){return x.label==='Fora do Prazo'})[0]?.value||0);draw('prazoChart',prazo);draw('statusChart',status);
        function table(){var h='<div class="table-wrap"><table class="monitor-table"><thead><tr><th>NUNOTA</th><th>Data</th><th>TOP</th><th>Produto</th><th>Dias</th><th>Prazo</th></tr></thead><tbody>';for(var i=0;i<itens.length;i++){var x=itens[i];h+='<tr data-search="'+e(Object.values(x).join(' ').toUpperCase())+'"><td><strong>'+e(x.nunota)+'</strong></td><td>'+e(x.dtneg)+'</td><td><strong>'+e(x.codtipoper)+'</strong><small>'+e(x.descroper)+'</small></td><td><strong>'+e(x.codprod)+'</strong><small>'+e(x.descrprod)+'</small></td><td><strong>'+e(x.dias)+'</strong></td><td><span class="status-badge '+(x.prazo==='No Prazo'?'status-success':'status-danger')+'">'+e(x.prazo)+'</span></td></tr>'}document.getElementById('tableArea').innerHTML=h+'</tbody></table></div>';search()}
        function search(){var term=t(document.getElementById('searchInput').value).toUpperCase(),rs=document.querySelectorAll('#tableArea tbody tr'),v=0;for(var i=0;i<rs.length;i++){var ok=!term||t(rs[i].getAttribute('data-search')).indexOf(term)>=0;rs[i].style.display=ok?'':'none';if(ok)v++}document.getElementById('searchInfo').textContent=v+' de '+rs.length+' registros exibidos'} document.getElementById('searchInput').oninput=search;document.getElementById('clearSearch').onclick=function(){document.getElementById('searchInput').value='';search()};table();tick();setInterval(tick,1000);
    </script>
</body>
</html>
