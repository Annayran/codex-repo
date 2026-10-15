<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Zaya.it Dashboard - Lead Time</title>
    <link rel="stylesheet" type="text/css" href="${BASE_FOLDER}/css/dashboardLeadTime.css">
    <link rel="stylesheet" type="text/css" href="css/dashboardLeadTime.css">
    <snk:load/>
</head>
<body>
    <snk:query var="usoRows">
        <%
            String base =
                " FROM ( SELECT CAB.NUNOTA, ITE.SEQUENCIA, TOP.CODTIPOPER, TOP.DESCROPER, PAR.CODPARC, PAR.RAZAOSOCIAL, PRO.CODPROD, PRO.DESCRPROD, PRO.USOPROD, " +
                "(SELECT OPC.OPCAO FROM TDDCAM CAM INNER JOIN TDDOPC OPC ON CAM.NUCAMPO = OPC.NUCAMPO WHERE CAM.NOMETAB = 'TGFPRO' AND CAM.NOMECAMPO = 'USOPROD' AND OPC.VALOR = PRO.USOPROD) USOPRODUTO, " +
                "PRO.CODPARCFORN, NVL(ITE.DTINICIO, CAB.DTPREVENT) AS DATAPEDIDO, MAX(ITC.DTENTSAI) AS DATANOTA, " +
                "NVL(MAX(ITC.DTENTSAI), TRUNC(SYSDATE))- NVL(ITE.DTINICIO, CAB.DTPREVENT) AS DIAS, " +
                "CASE WHEN PRO.USOPROD = 'C' AND NVL(MAX(ITC.DTENTSAI), TRUNC(SYSDATE))- NVL(ITE.DTINICIO, CAB.DTPREVENT) > 15 THEN 'N' " +
                "WHEN PRO.USOPROD <> 'C' AND NVL(MAX(ITC.DTENTSAI), TRUNC(SYSDATE))- NVL(ITE.DTINICIO, CAB.DTPREVENT) > 30 THEN 'N' ELSE 'S' END AS NOPRAZO " +
                "FROM TGFCAB CAB " +
                "INNER JOIN TGFITE ITE ON CAB.NUNOTA = ITE.NUNOTA " +
                "INNER JOIN TGFPRO PRO ON ITE.CODPROD = PRO.CODPROD " +
                "INNER JOIN TGFPAR PAR ON CAB.CODPARC = PAR.CODPARC " +
                "INNER JOIN TGFTOP TOP ON TOP.CODTIPOPER = CAB.CODTIPOPER AND TOP.DHALTER = CAB.DHTIPOPER " +
                "INNER JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA " +
                "INNER JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA " +
                "INNER JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA " +
                "WHERE CAB.TIPMOV = 'O' AND ITC.DTENTSAI BETWEEN :PERIODO.INI AND :PERIODO.FIN " +
                "AND PAR.CODPARC = NVL(:CODPARC,PAR.CODPARC) AND PRO.USOPROD IN ('M','C') " +
                "GROUP BY CAB.NUNOTA, TOP.CODTIPOPER, TOP.DESCROPER, PRO.CODPROD, PRO.DESCRPROD, PAR.CODPARC, PAR.RAZAOSOCIAL, PRO.USOPROD, PRO.CODPARCFORN, ITE.SEQUENCIA, NVL(ITE.DTINICIO, CAB.DTPREVENT), NVL(ITE.DTINICIO, CAB.DTENTSAI) ) ";
            out.println("SELECT USOPRODUTO, ROUND(SUM(DIAS) / COUNT(NUNOTA)) AS LEADTIME " + base + " GROUP BY USOPRODUTO");
        %>
    </snk:query>

    <snk:query var="produtoRows">
        <%
            String base2 =
                " FROM ( SELECT CAB.NUNOTA, ITE.SEQUENCIA, TOP.CODTIPOPER, TOP.DESCROPER, PAR.CODPARC, PAR.RAZAOSOCIAL, PRO.CODPROD, PRO.DESCRPROD, PRO.USOPROD, " +
                "(SELECT OPC.OPCAO FROM TDDCAM CAM INNER JOIN TDDOPC OPC ON CAM.NUCAMPO = OPC.NUCAMPO WHERE CAM.NOMETAB = 'TGFPRO' AND CAM.NOMECAMPO = 'USOPROD' AND OPC.VALOR = PRO.USOPROD) USOPRODUTO, " +
                "PRO.CODPARCFORN, NVL(ITE.DTINICIO, CAB.DTPREVENT) AS DATAPEDIDO, MAX(ITC.DTENTSAI) AS DATANOTA, " +
                "NVL(MAX(ITC.DTENTSAI), TRUNC(SYSDATE))- NVL(ITE.DTINICIO, CAB.DTPREVENT) AS DIAS, " +
                "CASE WHEN PRO.USOPROD = 'C' AND NVL(MAX(ITC.DTENTSAI), TRUNC(SYSDATE))- NVL(ITE.DTINICIO, CAB.DTPREVENT) > 15 THEN 'N' WHEN PRO.USOPROD <> 'C' AND NVL(MAX(ITC.DTENTSAI), TRUNC(SYSDATE))- NVL(ITE.DTINICIO, CAB.DTPREVENT) > 30 THEN 'N' ELSE 'S' END AS NOPRAZO " +
                "FROM TGFCAB CAB INNER JOIN TGFITE ITE ON CAB.NUNOTA = ITE.NUNOTA INNER JOIN TGFPRO PRO ON ITE.CODPROD = PRO.CODPROD INNER JOIN TGFPAR PAR ON CAB.CODPARC = PAR.CODPARC INNER JOIN TGFTOP TOP ON TOP.CODTIPOPER = CAB.CODTIPOPER AND TOP.DHALTER = CAB.DHTIPOPER INNER JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA INNER JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA INNER JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA " +
                "WHERE CAB.TIPMOV = 'O' AND ITC.DTENTSAI BETWEEN :PERIODO.INI AND :PERIODO.FIN AND PAR.CODPARC = NVL(:CODPARC,PAR.CODPARC) AND PRO.USOPROD IN ('M','C') " +
                "GROUP BY CAB.NUNOTA, TOP.CODTIPOPER, TOP.DESCROPER, PRO.CODPROD, PRO.DESCRPROD, PAR.CODPARC, PAR.RAZAOSOCIAL, PRO.USOPROD, PRO.CODPARCFORN, ITE.SEQUENCIA, NVL(ITE.DTINICIO, CAB.DTPREVENT), NVL(ITE.DTINICIO, CAB.DTENTSAI) ) ";
            out.println("SELECT CODPROD, DESCRPROD, ROUND(SUM(DIAS) / COUNT(NUNOTA)) AS LEADTIME " + base2 + " GROUP BY CODPROD, DESCRPROD");
        %>
    </snk:query>

    <snk:query var="fornecedorRows">
        <%
            String base3 =
                " FROM ( SELECT CAB.NUNOTA, ITE.SEQUENCIA, PAR.CODPARC, PAR.RAZAOSOCIAL, PRO.USOPROD, NVL(ITE.DTINICIO, CAB.DTPREVENT) AS DATAPEDIDO, MAX(ITC.DTENTSAI) AS DATANOTA, " +
                "NVL(MAX(ITC.DTENTSAI), TRUNC(SYSDATE))- NVL(ITE.DTINICIO, CAB.DTPREVENT) AS DIAS " +
                "FROM TGFCAB CAB INNER JOIN TGFITE ITE ON CAB.NUNOTA = ITE.NUNOTA INNER JOIN TGFPRO PRO ON ITE.CODPROD = PRO.CODPROD INNER JOIN TGFPAR PAR ON CAB.CODPARC = PAR.CODPARC INNER JOIN TGFTOP TOP ON TOP.CODTIPOPER = CAB.CODTIPOPER AND TOP.DHALTER = CAB.DHTIPOPER INNER JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA INNER JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA INNER JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA " +
                "WHERE CAB.TIPMOV = 'O' AND CAB.DTNEG > '01/08/2025' AND PAR.CODPARC = NVL(:CODPARC,PAR.CODPARC) AND PRO.USOPROD IN ('M','C') " +
                "GROUP BY CAB.NUNOTA, PAR.CODPARC, PAR.RAZAOSOCIAL, PRO.USOPROD, ITE.SEQUENCIA, NVL(ITE.DTINICIO, CAB.DTPREVENT), NVL(ITE.DTINICIO, CAB.DTENTSAI) ) ";
            out.println("SELECT CODPARC, RAZAOSOCIAL, ROUND(SUM(DIAS) / COUNT(NUNOTA)) AS LEADTIME " + base3 + " GROUP BY CODPARC, RAZAOSOCIAL");
        %>
    </snk:query>

    <main class="dashboard-shell">
        <header class="topbar">
            <div class="topbar-left"><div class="brand-mark">Z</div><div><div class="brand-text">Zaya.it</div><span class="brand-sub">Dashboard</span></div><div class="topbar-divider"></div><div><h1>Lead Time</h1><p>Conversao HTML5 fiel ao gadget 300</p></div></div>
            <div class="topbar-right"><span class="clock" id="clockDisplay">--:--:--</span><button class="ghost-button" onclick="window.location.reload()">Atualizar</button></div>
        </header>
        <section class="summary-grid">
            <article class="metric-card metric-card-strong"><span class="metric-label">Usos de produto</span><strong class="metric-value" id="totalUso">0</strong><small class="metric-foot">Agrupado por uso</small></article>
            <article class="metric-card"><span class="metric-label">Produtos</span><strong class="metric-value" id="totalProd">0</strong><small class="metric-foot">Agrupado por produto</small></article>
            <article class="metric-card"><span class="metric-label">Fornecedores</span><strong class="metric-value" id="totalForn">0</strong><small class="metric-foot">Analise por fornecedor</small></article>
            <article class="metric-card"><span class="metric-label">Lead medio</span><strong class="metric-value" id="leadMedio">0</strong><small class="metric-foot">Media fornecedores</small></article>
        </section>
        <section class="dashboard-content">
            <div class="panel-card">
                <div class="panel-head"><div><h2>Lead Time</h2><p>Mesmas visoes do XML: uso, produto e fornecedor.</p></div></div>
                <div class="tabs-toolbar"><div class="tabs-nav" id="tabsNav"></div><div class="table-search"><input id="searchInput" placeholder="Buscar..." autocomplete="off"><button class="ghost-button" id="clearSearch" type="button">Limpar</button></div></div>
                <div class="search-info" id="searchInfo"></div><div id="tableArea"></div>
            </div>
        </section>
        <table id="usoSource" style="display:none"><tbody><c:forEach items="${usoRows.rows}" var="r"><tr data-usoproduto="<c:out value="${r.USOPRODUTO}" />" data-leadtime="<c:out value="${r.LEADTIME}" />"></tr></c:forEach></tbody></table>
        <table id="produtoSource" style="display:none"><tbody><c:forEach items="${produtoRows.rows}" var="r"><tr data-codprod="<c:out value="${r.CODPROD}" />" data-descrprod="<c:out value="${r.DESCRPROD}" />" data-leadtime="<c:out value="${r.LEADTIME}" />"></tr></c:forEach></tbody></table>
        <table id="fornecedorSource" style="display:none"><tbody><c:forEach items="${fornecedorRows.rows}" var="r"><tr data-codparc="<c:out value="${r.CODPARC}" />" data-razaosocial="<c:out value="${r.RAZAOSOCIAL}" />" data-leadtime="<c:out value="${r.LEADTIME}" />"></tr></c:forEach></tbody></table>
    </main>
    <script>
        var tab='uso';function t(v){return v==null?'':String(v)}function n(v){var x=Number(t(v).replace(',','.'));return isNaN(x)?0:x}function e(s){return t(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;')}function f(v){return Number(v||0).toLocaleString('pt-BR')}function p(x){return x<10?'0'+x:x}function tick(){var d=new Date();document.getElementById('clockDisplay').textContent=p(d.getHours())+':'+p(d.getMinutes())+':'+p(d.getSeconds())}
        function collect(id,fields){var rs=document.querySelectorAll('#'+id+' tr'),out=[];for(var i=0;i<rs.length;i++){var o={};for(var f0=0;f0<fields.length;f0++)o[fields[f0]]=rs[i].getAttribute('data-'+fields[f0]);out.push(o)}return out}
        var uso=collect('usoSource',['usoproduto','leadtime']),prod=collect('produtoSource',['codprod','descrprod','leadtime']),forn=collect('fornecedorSource',['codparc','razaosocial','leadtime']);
        document.getElementById('totalUso').textContent=f(uso.length);document.getElementById('totalProd').textContent=f(prod.length);document.getElementById('totalForn').textContent=f(forn.length);document.getElementById('leadMedio').textContent=f(forn.reduce(function(a,b){return a+n(b.leadtime)},0)/Math.max(forn.length,1));
        function tabs(){var arr=[['uso','Uso Produto',uso.length],['produto','Produto',prod.length],['fornecedor','Fornecedor',forn.length]],h='';for(var i=0;i<arr.length;i++)h+='<button class="tab-button '+(tab===arr[i][0]?'is-active':'')+'" data-tab="'+arr[i][0]+'"><span>'+arr[i][1]+'</span><span class="tab-count">'+arr[i][2]+'</span></button>';document.getElementById('tabsNav').innerHTML=h;document.querySelectorAll('.tab-button').forEach(function(b){b.onclick=function(){tab=this.getAttribute('data-tab');render()}})}
        function table(){var data=tab==='uso'?uso:(tab==='produto'?prod:forn),h='<div class="table-wrap"><table class="monitor-table"><thead><tr>';if(tab==='uso')h+='<th>Uso Produto</th><th>Lead Time</th>';if(tab==='produto')h+='<th>Cod Produto</th><th>Produto</th><th>Lead Time</th>';if(tab==='fornecedor')h+='<th>Cod Parceiro</th><th>Razao Social</th><th>Lead Time</th>';h+='</tr></thead><tbody>';for(var i=0;i<data.length;i++){var x=data[i],idx=Object.values(x).join(' ').toUpperCase();h+='<tr data-search="'+e(idx)+'">';if(tab==='uso')h+='<td><strong>'+e(x.usoproduto)+'</strong></td><td><strong>'+e(x.leadtime)+'</strong></td>';if(tab==='produto')h+='<td><strong>'+e(x.codprod)+'</strong></td><td>'+e(x.descrprod)+'</td><td><strong>'+e(x.leadtime)+'</strong></td>';if(tab==='fornecedor')h+='<td><strong>'+e(x.codparc)+'</strong></td><td>'+e(x.razaosocial)+'</td><td><strong>'+e(x.leadtime)+'</strong></td>';h+='</tr>'}document.getElementById('tableArea').innerHTML=h+'</tbody></table></div>';search()}
        function search(){var term=t(document.getElementById('searchInput').value).toUpperCase(),rs=document.querySelectorAll('#tableArea tbody tr'),v=0;for(var i=0;i<rs.length;i++){var ok=!term||t(rs[i].getAttribute('data-search')).indexOf(term)>=0;rs[i].style.display=ok?'':'none';if(ok)v++}document.getElementById('searchInfo').textContent=v+' de '+rs.length+' registros exibidos'}function render(){tabs();table()}document.getElementById('searchInput').oninput=search;document.getElementById('clearSearch').onclick=function(){document.getElementById('searchInput').value='';search()};render();tick();setInterval(tick,1000);
    </script>
</body>
</html>
