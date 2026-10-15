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
    <snk:query var="reqDetalhe">
        <%
            String queryReq =
                "SELECT " +
                "    CAB.NUNOTA, " +
                "    ITE.SEQUENCIA, " +
                "    TOP.CODTIPOPER, " +
                "    TOP.DESCROPER, " +
                "    PRO.CODPROD, " +
                "    PRO.DESCRPROD, " +
                "    TRUNC(CAB.DTNEG) AS DT_REQUISICAO, " +
                "    MIN(TRUNC(NVL(ITC.DTNEG, ITC.DTENTSAI))) AS DT_INICIO_COTACAO, " +
                "    MAX(TRUNC(CAP.DTENTSAI)) AS DT_ATENDIMENTO, " +
                "    TRUNC(:PERIODO.FIN) AS DT_CORTE_USADA, " +
                "    MAX(ITC.NUNOTA) AS NUNOTA_COTACAO, " +
                "    MAX(CAP.NUNOTA) AS NUNOTA_ATENDIMENTO, " +
                "    TRUNC(NVL(MAX(CAP.DTENTSAI), :PERIODO.FIN)) - TRUNC(NVL(CAB.DTENTSAI, CAB.DTNEG)) AS DIAS_CALCULADOS, " +
                "    NVL(:DIASPRAZO, 7) AS PRAZO_LIMITE, " +
                "    CASE WHEN MAX(CAP.NUNOTA) IS NOT NULL THEN 'Fechado' " +
                "         WHEN MAX(ITC.NUNOTA) IS NOT NULL THEN 'Em andamento' " +
                "         ELSE 'Aberto' END AS STATUS_REQUISICAO, " +
                "    CASE WHEN TRUNC(NVL(MAX(CAP.DTENTSAI), :PERIODO.FIN)) - TRUNC(NVL(CAB.DTENTSAI, CAB.DTNEG)) <= NVL(:DIASPRAZO, 7) " +
                "         THEN 'No Prazo' ELSE 'Fora do Prazo' END AS CLASSIFICACAO_PRAZO " +
                "FROM TGFCAB CAB " +
                "INNER JOIN TGFITE ITE ON CAB.NUNOTA = ITE.NUNOTA " +
                "INNER JOIN TGFTOP TOP ON TOP.CODTIPOPER = CAB.CODTIPOPER AND TOP.DHALTER = CAB.DHTIPOPER " +
                "INNER JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD " +
                "LEFT JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA " +
                "LEFT JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA " +
                "LEFT JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA " +
                "LEFT JOIN TGFVAR ITA ON ITA.NUNOTAORIG = ITC.NUNOTA AND ITA.SEQUENCIAORIG = ITV.SEQUENCIA " +
                "LEFT JOIN TGFCAB CAP ON ITA.NUNOTA = CAP.NUNOTA " +
                "WHERE CAB.CODTIPOPER IN (400, 401) " +
                "  AND CAB.DTNEG BETWEEN :PERIODO.INI AND :PERIODO.FIN " +
                "GROUP BY " +
                "    CAB.NUNOTA, ITE.SEQUENCIA, TOP.CODTIPOPER, TOP.DESCROPER, PRO.CODPROD, PRO.DESCRPROD, " +
                "    TRUNC(CAB.DTNEG), TRUNC(NVL(CAB.DTENTSAI, CAB.DTNEG)) " +
                "ORDER BY TRUNC(CAB.DTNEG), CAB.NUNOTA, ITE.SEQUENCIA";
            out.println(queryReq);
        %>
    </snk:query>

    <main class="dashboard-shell">
        <header class="topbar">
            <div class="topbar-left">
                <div class="brand-mark">Z</div>
                <div>
                    <div class="brand-text">Zaya.it</div>
                    <span class="brand-sub">Dashboard</span>
                </div>
                <div class="topbar-divider"></div>
                <div>
                    <h1>Cotacao - Lead Time de Requisicoes</h1>
                    <p>OS 7259 | TOP 400/401 | Corte historico por periodo final</p>
                </div>
            </div>
            <div class="topbar-right">
                <span class="clock" id="clockDisplay">--:--:--</span>
                <button class="ghost-button" type="button" onclick="window.location.reload()">Atualizar</button>
            </div>
        </header>

        <section class="summary-grid">
            <article class="metric-card metric-card-strong">
                <span class="metric-label">Requisicoes</span>
                <strong class="metric-value" id="totalReq">0</strong>
                <small class="metric-foot">Total por item</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">No prazo</span>
                <strong class="metric-value" id="pctPrazo">0,00%</strong>
                <small class="metric-foot">Dentro do prazo parametrizado</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Fora do prazo</span>
                <strong class="metric-value metric-danger" id="foraPrazo">0</strong>
                <small class="metric-foot">Itens acima do limite</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Lead time medio</span>
                <strong class="metric-value" id="leadMedio">0,00</strong>
                <small class="metric-foot">Dias calculados</small>
            </article>
        </section>

        <section class="dashboard-layout">
            <aside class="side-column">
                <div class="chart-card">
                    <div class="card-title">Prazo</div>
                    <canvas id="prazoChart" width="280" height="190"></canvas>
                </div>
                <div class="chart-card">
                    <div class="card-title">Status</div>
                    <canvas id="statusChart" width="280" height="190"></canvas>
                </div>
            </aside>
            <section class="dashboard-content">
                <div class="panel-card">
                    <div class="panel-head">
                        <div>
                            <h2>Detalhamento auditavel</h2>
                            <p>Base por requisicao/item, com data de corte e classificacao de prazo.</p>
                        </div>
                    </div>
                    <div class="tabs-toolbar">
                        <div class="tabs-nav" id="tabsNav"></div>
                        <div class="table-search">
                            <input type="text" id="searchInput" placeholder="Buscar nota, produto, TOP, status..." autocomplete="off">
                            <button class="ghost-button" type="button" id="clearSearch">Limpar</button>
                        </div>
                    </div>
                    <div class="search-info" id="searchInfo"></div>
                    <div id="tablesArea"></div>
                </div>
            </section>
        </section>

        <table id="reqSource" style="display:none">
            <tbody>
                <c:forEach items="${reqDetalhe.rows}" var="row">
                    <tr class="js-req"
                        data-nunota="<c:out value="${row.NUNOTA}" />"
                        data-sequencia="<c:out value="${row.SEQUENCIA}" />"
                        data-codtipoper="<c:out value="${row.CODTIPOPER}" />"
                        data-descroper="<c:out value="${row.DESCROPER}" />"
                        data-codprod="<c:out value="${row.CODPROD}" />"
                        data-descrprod="<c:out value="${row.DESCRPROD}" />"
                        data-dtrequisicao="<c:out value="${row.DT_REQUISICAO}" />"
                        data-dtiniciocotacao="<c:out value="${row.DT_INICIO_COTACAO}" />"
                        data-dtatendimento="<c:out value="${row.DT_ATENDIMENTO}" />"
                        data-dtcorte="<c:out value="${row.DT_CORTE_USADA}" />"
                        data-nunotacotacao="<c:out value="${row.NUNOTA_COTACAO}" />"
                        data-nunotaatendimento="<c:out value="${row.NUNOTA_ATENDIMENTO}" />"
                        data-dias="<c:out value="${row.DIAS_CALCULADOS}" />"
                        data-prazo="<c:out value="${row.PRAZO_LIMITE}" />"
                        data-status="<c:out value="${row.STATUS_REQUISICAO}" />"
                        data-classificacao="<c:out value="${row.CLASSIFICACAO_PRAZO}" />"></tr>
                </c:forEach>
            </tbody>
        </table>
    </main>

    <script>
        var state = { tab: 'todos' };
        function trimText(v){return v == null ? '' : String(v).replace(/^\s+|\s+$/g,'');}
        function toNumber(v){var s=trimText(v); if(s.indexOf(',')>=0&&s.indexOf('.')>=0)s=s.replace(/\./g,'').replace(',','.'); else if(s.indexOf(',')>=0)s=s.replace(',','.'); var n=Number(s); return isNaN(n)?0:n;}
        function esc(s){return String(s||'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');}
        function norm(s){s=trimText(s).toUpperCase(); if(s.normalize)s=s.normalize('NFD').replace(/[\u0300-\u036f]/g,''); return s;}
        function fmtNum(v){return Number(v||0).toLocaleString('pt-BR');}
        function fmtDec(v){return Number(v||0).toLocaleString('pt-BR',{minimumFractionDigits:2,maximumFractionDigits:2});}
        function fmtPct(v){return fmtDec(v)+'%';}
        function pad(n){return n<10?'0'+n:String(n);}
        function tick(){var d=new Date();document.getElementById('clockDisplay').textContent=pad(d.getHours())+':'+pad(d.getMinutes())+':'+pad(d.getSeconds());}
        function collect(){var rows=document.querySelectorAll('#reqSource .js-req'),out=[];for(var i=0;i<rows.length;i++){var r=rows[i];out.push({nunota:r.getAttribute('data-nunota')||'',sequencia:r.getAttribute('data-sequencia')||'',codtipoper:r.getAttribute('data-codtipoper')||'',descroper:r.getAttribute('data-descroper')||'',codprod:r.getAttribute('data-codprod')||'',descrprod:r.getAttribute('data-descrprod')||'',dtrequisicao:r.getAttribute('data-dtrequisicao')||'',dtiniciocotacao:r.getAttribute('data-dtiniciocotacao')||'',dtatendimento:r.getAttribute('data-dtatendimento')||'',dtcorte:r.getAttribute('data-dtcorte')||'',nunotacotacao:r.getAttribute('data-nunotacotacao')||'',nunotaatendimento:r.getAttribute('data-nunotaatendimento')||'',dias:toNumber(r.getAttribute('data-dias')),prazo:toNumber(r.getAttribute('data-prazo')),status:r.getAttribute('data-status')||'',classificacao:r.getAttribute('data-classificacao')||''});}return out;}
        var items=collect();
        function count(field,value,arr){arr=arr||items;var n=0;for(var i=0;i<arr.length;i++)if(arr[i][field]===value)n++;return n;}
        function avg(arr,field){if(!arr.length)return 0;var s=0;for(var i=0;i<arr.length;i++)s+=Number(arr[i][field]||0);return s/arr.length;}
        function rowIndex(o){var s='';for(var k in o)if(o.hasOwnProperty(k))s+=' '+o[k];return norm(s);}
        function badgeClass(v){return v==='No Prazo'||v==='Fechado'?'status-success':(v==='Em andamento'?'status-warning':'status-danger');}
        function updateMetrics(){var no=count('classificacao','No Prazo'),fora=items.length-no;document.getElementById('totalReq').textContent=fmtNum(items.length);document.getElementById('pctPrazo').textContent=fmtPct(items.length?no/items.length*100:0);document.getElementById('foraPrazo').textContent=fmtNum(fora);document.getElementById('leadMedio').textContent=fmtDec(avg(items,'dias'));drawDonut('prazoChart',[{label:'No Prazo',value:no,color:'#22c55e'},{label:'Fora',value:fora,color:'#ef4444'}]);drawDonut('statusChart',[{label:'Aberto',value:count('status','Aberto'),color:'#3b82f6'},{label:'Em andamento',value:count('status','Em andamento'),color:'#f97316'},{label:'Fechado',value:count('status','Fechado'),color:'#22c55e'}]);}
        function drawDonut(id,data){var c=document.getElementById(id);if(!c||!c.getContext)return;var ctx=c.getContext('2d'),total=0;ctx.clearRect(0,0,c.width,c.height);for(var i=0;i<data.length;i++)total+=data[i].value;var cx=74,cy=84,r=52,start=-Math.PI/2;ctx.lineWidth=18;if(!total){ctx.strokeStyle='#e2e8f0';ctx.beginPath();ctx.arc(cx,cy,r,0,Math.PI*2);ctx.stroke();}for(var j=0;j<data.length;j++){var sl=total?Math.PI*2*data[j].value/total:0;ctx.strokeStyle=data[j].color;ctx.beginPath();ctx.arc(cx,cy,r,start,start+sl);ctx.stroke();start+=sl;}ctx.fillStyle='#0f172a';ctx.font='800 22px Inter, Arial';ctx.textAlign='center';ctx.fillText(total?fmtPct(data[0].value/total*100).replace(',00',''):'0%',cx,cy+8);ctx.textAlign='left';for(var k=0;k<data.length;k++){var y=52+k*33;ctx.fillStyle=data[k].color;ctx.fillRect(158,y-9,11,11);ctx.fillStyle='#64748b';ctx.font='700 10px Inter, Arial';ctx.fillText(data[k].label,178,y);ctx.fillStyle='#0f172a';ctx.font='800 12px Inter, Arial';ctx.fillText(fmtNum(data[k].value),178,y+16);}}
        function filtered(){if(state.tab==='todos')return items; if(state.tab==='aberto')return items.filter(function(x){return x.status==='Aberto';}); if(state.tab==='andamento')return items.filter(function(x){return x.status==='Em andamento';}); if(state.tab==='fechado')return items.filter(function(x){return x.status==='Fechado';}); if(state.tab==='fora')return items.filter(function(x){return x.classificacao==='Fora do Prazo';}); return items;}
        function renderTabs(){var tabs=[['todos','Todos',items.length],['aberto','Aberto',count('status','Aberto')],['andamento','Em andamento',count('status','Em andamento')],['fechado','Fechado',count('status','Fechado')],['fora','Fora prazo',count('classificacao','Fora do Prazo')]],h='';for(var i=0;i<tabs.length;i++)h+='<button class=\"tab-button'+(state.tab===tabs[i][0]?' is-active':'')+'\" data-tab=\"'+tabs[i][0]+'\"><span>'+tabs[i][1]+'</span><span class=\"tab-count\">'+tabs[i][2]+'</span></button>';document.getElementById('tabsNav').innerHTML=h;var btns=document.querySelectorAll('.tab-button');for(var b=0;b<btns.length;b++)btns[b].onclick=function(){state.tab=this.getAttribute('data-tab');render();};}
        function tableHtml(arr){var h='<div class=\"table-wrap\"><table class=\"monitor-table\"><thead><tr><th>NUNOTA</th><th>TOP</th><th>Produto</th><th>Datas</th><th>Dias</th><th>Status</th><th>Prazo</th></tr></thead><tbody>';for(var i=0;i<arr.length;i++){var it=arr[i];h+='<tr data-search=\"'+esc(rowIndex(it))+'\"><td><strong>'+esc(it.nunota)+'</strong><small>Seq. '+esc(it.sequencia)+'</small></td><td><strong>'+esc(it.codtipoper)+'</strong><small>'+esc(it.descroper)+'</small></td><td><strong>'+esc(it.codprod)+'</strong><small>'+esc(it.descrprod)+'</small></td><td>Req. '+esc(it.dtrequisicao||'-')+'<small>Atend. '+esc(it.dtatendimento||'-')+' | Corte '+esc(it.dtcorte||'-')+'</small></td><td><strong>'+esc(fmtNum(it.dias))+'</strong><small>Limite '+esc(fmtNum(it.prazo))+'</small></td><td><span class=\"status-badge '+badgeClass(it.status)+'\">'+esc(it.status)+'</span></td><td><span class=\"status-badge '+badgeClass(it.classificacao)+'\">'+esc(it.classificacao)+'</span></td></tr>';}return h+'</tbody></table></div>';}
        function render(){renderTabs();document.getElementById('tablesArea').innerHTML=tableHtml(filtered());applySearch();}
        function applySearch(){var term=norm(document.getElementById('searchInput').value||''),rows=document.querySelectorAll('#tablesArea tbody tr'),vis=0;for(var i=0;i<rows.length;i++){var ok=!term||norm(rows[i].getAttribute('data-search')).indexOf(term)>=0;rows[i].style.display=ok?'':'none';if(ok)vis++;}document.getElementById('searchInfo').textContent=rows.length?vis+' de '+rows.length+' registros exibidos':'';}
        document.getElementById('searchInput').oninput=applySearch;document.getElementById('clearSearch').onclick=function(){document.getElementById('searchInput').value='';applySearch();};
        updateMetrics();render();tick();setInterval(tick,1000);
    </script>
</body>
</html>
