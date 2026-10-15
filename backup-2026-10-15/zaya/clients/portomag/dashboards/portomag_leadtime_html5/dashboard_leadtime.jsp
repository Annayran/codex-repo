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
    <snk:query var="entDetalhe">
        <%
            String queryEnt =
                "SELECT " +
                "    CAB.NUNOTA AS NUNOTA_PEDIDO, " +
                "    ITE.SEQUENCIA AS SEQ_PEDIDO, " +
                "    MAX(ITC.NUNOTA) AS NUNOTA_ENTRADA, " +
                "    MAX(ITV.SEQUENCIA) AS SEQ_ENTRADA, " +
                "    TOP.CODTIPOPER, " +
                "    TOP.DESCROPER, " +
                "    PAR.CODPARC, " +
                "    PAR.RAZAOSOCIAL, " +
                "    PRO.CODPROD, " +
                "    PRO.DESCRPROD, " +
                "    PRO.USOPROD, " +
                "    CASE WHEN PRO.USOPROD = 'C' THEN 'Uso/consumo' WHEN PRO.USOPROD = 'M' THEN 'Materia-prima' ELSE 'Outros' END AS TIPO_PRODUTO, " +
                "    NVL(ITE.DTINICIO, CAB.DTPREVENT) AS DATA_PEDIDO, " +
                "    CAB.DTPREVENT AS DATA_PREVISTA, " +
                "    MAX(ITC.DTENTSAI) AS DATA_ENTRADA, " +
                "    :PERIODO.FIN AS DATA_CORTE_REFERENCIA, " +
                "    NVL(MAX(ITC.DTENTSAI), :PERIODO.FIN) - NVL(ITE.DTINICIO, CAB.DTPREVENT) AS DIAS_CALCULADOS, " +
                "    CASE WHEN PRO.USOPROD = 'C' THEN 15 ELSE 30 END AS PRAZO_ESPERADO, " +
                "    CASE WHEN PRO.USOPROD = 'C' AND NVL(MAX(ITC.DTENTSAI), :PERIODO.FIN) - NVL(ITE.DTINICIO, CAB.DTPREVENT) > 15 THEN 'Fora do Prazo' " +
                "         WHEN PRO.USOPROD <> 'C' AND NVL(MAX(ITC.DTENTSAI), :PERIODO.FIN) - NVL(ITE.DTINICIO, CAB.DTPREVENT) > 30 THEN 'Fora do Prazo' " +
                "         ELSE 'No Prazo' END AS CLASSIFICACAO_PRAZO, " +
                "    CASE WHEN PRO.USOPROD = 'C' AND NVL(MAX(ITC.DTENTSAI), :PERIODO.FIN) - NVL(ITE.DTINICIO, CAB.DTPREVENT) > 15 THEN 'N' " +
                "         WHEN PRO.USOPROD <> 'C' AND NVL(MAX(ITC.DTENTSAI), :PERIODO.FIN) - NVL(ITE.DTINICIO, CAB.DTPREVENT) > 30 THEN 'N' " +
                "         ELSE 'S' END AS NOPRAZO " +
                "FROM TGFCAB CAB " +
                "INNER JOIN TGFITE ITE ON CAB.NUNOTA = ITE.NUNOTA " +
                "INNER JOIN TGFPRO PRO ON ITE.CODPROD = PRO.CODPROD " +
                "INNER JOIN TGFPAR PAR ON CAB.CODPARC = PAR.CODPARC " +
                "INNER JOIN TGFTOP TOP ON TOP.CODTIPOPER = CAB.CODTIPOPER AND TOP.DHALTER = CAB.DHTIPOPER " +
                "INNER JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA " +
                "INNER JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA " +
                "INNER JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA " +
                "WHERE CAB.TIPMOV = 'O' " +
                "  AND ITC.DTENTSAI BETWEEN :PERIODO.INI AND :PERIODO.FIN " +
                "  AND PAR.CODPARC = NVL(:CODPARC, PAR.CODPARC) " +
                "  AND PRO.USOPROD IN ('M', 'C') " +
                "GROUP BY " +
                "    CAB.NUNOTA, ITE.SEQUENCIA, TOP.CODTIPOPER, TOP.DESCROPER, PAR.CODPARC, PAR.RAZAOSOCIAL, " +
                "    PRO.CODPROD, PRO.DESCRPROD, PRO.USOPROD, NVL(ITE.DTINICIO, CAB.DTPREVENT), CAB.DTPREVENT " +
                "ORDER BY PAR.RAZAOSOCIAL, MAX(ITC.DTENTSAI), CAB.NUNOTA, ITE.SEQUENCIA";
            out.println(queryEnt);
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
                    <h1>Lead Time - Entregas On-time</h1>
                    <p>OS 7260 | Fornecedor | Uso/consumo 15 dias | Materia-prima 30 dias</p>
                </div>
            </div>
            <div class="topbar-right">
                <span class="clock" id="clockDisplay">--:--:--</span>
                <button class="ghost-button" type="button" onclick="window.location.reload()">Atualizar</button>
            </div>
        </header>

        <section class="summary-grid">
            <article class="metric-card metric-card-strong">
                <span class="metric-label">Entregas</span>
                <strong class="metric-value" id="totalEnt">0</strong>
                <small class="metric-foot">Notas de entrada no periodo</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">On-time</span>
                <strong class="metric-value" id="pctOnTime">0,00%</strong>
                <small class="metric-foot">No prazo / total</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Fora do prazo</span>
                <strong class="metric-value metric-danger" id="foraPrazo">0</strong>
                <small class="metric-foot">Entregas acima do limite</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Lead time medio</span>
                <strong class="metric-value" id="leadMedio">0,00</strong>
                <small class="metric-foot">Dias ate entrada</small>
            </article>
        </section>

        <section class="dashboard-layout">
            <aside class="side-column">
                <div class="chart-card">
                    <div class="card-title">Entregas on-time</div>
                    <canvas id="onTimeChart" width="280" height="190"></canvas>
                </div>
                <div class="chart-card">
                    <div class="card-title">Tipo de produto</div>
                    <canvas id="tipoChart" width="280" height="190"></canvas>
                </div>
            </aside>
            <section class="dashboard-content">
                <div class="panel-card">
                    <div class="panel-head">
                        <div>
                            <h2>Performance por fornecedor</h2>
                            <p>Visao macro por fornecedor e drill-down por pedido, nota, produto e datas.</p>
                        </div>
                    </div>
                    <div class="tabs-toolbar">
                        <div class="tabs-nav" id="tabsNav"></div>
                        <div class="table-search">
                            <input type="text" id="searchInput" placeholder="Buscar fornecedor, nota, produto..." autocomplete="off">
                            <button class="ghost-button" type="button" id="clearSearch">Limpar</button>
                        </div>
                    </div>
                    <div class="context-filter" id="contextFilter" style="display:none"></div>
                    <div class="search-info" id="searchInfo"></div>
                    <div id="tablesArea"></div>
                </div>
            </section>
        </section>

        <table id="entSource" style="display:none">
            <tbody>
                <c:forEach items="${entDetalhe.rows}" var="row">
                    <tr class="js-ent"
                        data-codparc="<c:out value="${row.CODPARC}" />"
                        data-razaosocial="<c:out value="${row.RAZAOSOCIAL}" />"
                        data-nunotapedido="<c:out value="${row.NUNOTA_PEDIDO}" />"
                        data-seqpedido="<c:out value="${row.SEQ_PEDIDO}" />"
                        data-nunotaentrada="<c:out value="${row.NUNOTA_ENTRADA}" />"
                        data-seqentrada="<c:out value="${row.SEQ_ENTRADA}" />"
                        data-codtipoper="<c:out value="${row.CODTIPOPER}" />"
                        data-descroper="<c:out value="${row.DESCROPER}" />"
                        data-codprod="<c:out value="${row.CODPROD}" />"
                        data-descrprod="<c:out value="${row.DESCRPROD}" />"
                        data-usoprod="<c:out value="${row.USOPROD}" />"
                        data-tipoproduto="<c:out value="${row.TIPO_PRODUTO}" />"
                        data-datapedido="<c:out value="${row.DATA_PEDIDO}" />"
                        data-dataprevista="<c:out value="${row.DATA_PREVISTA}" />"
                        data-dataentrada="<c:out value="${row.DATA_ENTRADA}" />"
                        data-dtcorte="<c:out value="${row.DATA_CORTE_REFERENCIA}" />"
                        data-dias="<c:out value="${row.DIAS_CALCULADOS}" />"
                        data-prazo="<c:out value="${row.PRAZO_ESPERADO}" />"
                        data-classificacao="<c:out value="${row.CLASSIFICACAO_PRAZO}" />"
                        data-noprazo="<c:out value="${row.NOPRAZO}" />"></tr>
                </c:forEach>
            </tbody>
        </table>
    </main>

    <script>
        var state={tab:'fornecedor', fornecedor:null};
        function trimText(v){return v==null?'':String(v).replace(/^\s+|\s+$/g,'');}
        function toNumber(v){var s=trimText(v);if(s.indexOf(',')>=0&&s.indexOf('.')>=0)s=s.replace(/\./g,'').replace(',','.');else if(s.indexOf(',')>=0)s=s.replace(',','.');var n=Number(s);return isNaN(n)?0:n;}
        function esc(s){return String(s||'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');}
        function norm(s){s=trimText(s).toUpperCase();if(s.normalize)s=s.normalize('NFD').replace(/[\u0300-\u036f]/g,'');return s;}
        function fmtNum(v){return Number(v||0).toLocaleString('pt-BR');}
        function fmtDec(v){return Number(v||0).toLocaleString('pt-BR',{minimumFractionDigits:2,maximumFractionDigits:2});}
        function fmtPct(v){return fmtDec(v)+'%';}
        function pad(n){return n<10?'0'+n:String(n);}
        function tick(){var d=new Date();document.getElementById('clockDisplay').textContent=pad(d.getHours())+':'+pad(d.getMinutes())+':'+pad(d.getSeconds());}
        function collect(){var rows=document.querySelectorAll('#entSource .js-ent'),out=[];for(var i=0;i<rows.length;i++){var r=rows[i];out.push({codparc:r.getAttribute('data-codparc')||'',razaosocial:r.getAttribute('data-razaosocial')||'',nunotapedido:r.getAttribute('data-nunotapedido')||'',seqpedido:r.getAttribute('data-seqpedido')||'',nunotaentrada:r.getAttribute('data-nunotaentrada')||'',seqentrada:r.getAttribute('data-seqentrada')||'',codtipoper:r.getAttribute('data-codtipoper')||'',descroper:r.getAttribute('data-descroper')||'',codprod:r.getAttribute('data-codprod')||'',descrprod:r.getAttribute('data-descrprod')||'',usoprod:r.getAttribute('data-usoprod')||'',tipoproduto:r.getAttribute('data-tipoproduto')||'',datapedido:r.getAttribute('data-datapedido')||'',dataprevista:r.getAttribute('data-dataprevista')||'',dataentrada:r.getAttribute('data-dataentrada')||'',dtcorte:r.getAttribute('data-dtcorte')||'',dias:toNumber(r.getAttribute('data-dias')),prazo:toNumber(r.getAttribute('data-prazo')),classificacao:r.getAttribute('data-classificacao')||'',noprazo:r.getAttribute('data-noprazo')||''});}return out;}
        var items=collect();
        function rowIndex(o){var s='';for(var k in o)if(o.hasOwnProperty(k))s+=' '+o[k];return norm(s);}
        function count(field,value,arr){arr=arr||items;var n=0;for(var i=0;i<arr.length;i++)if(arr[i][field]===value)n++;return n;}
        function avg(arr,field){if(!arr.length)return 0;var s=0;for(var i=0;i<arr.length;i++)s+=Number(arr[i][field]||0);return s/arr.length;}
        function badgeClass(v){return v==='No Prazo'||v==='S'?'status-success':'status-danger';}
        function groupFornecedor(){var map={},arr=[];for(var i=0;i<items.length;i++){var it=items[i],k=it.codparc;if(!map[k]){map[k]={codparc:it.codparc,razaosocial:it.razaosocial,total:0,noPrazo:0,fora:0,somaDias:0,melhor:null,pior:null};arr.push(map[k]);}var g=map[k];g.total++;if(it.noprazo==='S')g.noPrazo++;else g.fora++;g.somaDias+=it.dias;g.melhor=g.melhor==null?it.dias:Math.min(g.melhor,it.dias);g.pior=g.pior==null?it.dias:Math.max(g.pior,it.dias);}arr.sort(function(a,b){var pa=a.total?a.noPrazo/a.total:0,pb=b.total?b.noPrazo/b.total:0;return pb-pa||b.total-a.total||String(a.razaosocial).localeCompare(String(b.razaosocial));});return arr;}
        function updateMetrics(){var no=count('noprazo','S'),fora=items.length-no;document.getElementById('totalEnt').textContent=fmtNum(items.length);document.getElementById('pctOnTime').textContent=fmtPct(items.length?no/items.length*100:0);document.getElementById('foraPrazo').textContent=fmtNum(fora);document.getElementById('leadMedio').textContent=fmtDec(avg(items,'dias'));drawDonut('onTimeChart',[{label:'No Prazo',value:no,color:'#22c55e'},{label:'Fora',value:fora,color:'#ef4444'}]);drawDonut('tipoChart',[{label:'Uso/consumo',value:count('usoprod','C'),color:'#3b82f6'},{label:'Materia-prima',value:count('usoprod','M'),color:'#22c55e'}]);}
        function drawDonut(id,data){var c=document.getElementById(id);if(!c||!c.getContext)return;var ctx=c.getContext('2d'),total=0;ctx.clearRect(0,0,c.width,c.height);for(var i=0;i<data.length;i++)total+=data[i].value;var cx=74,cy=84,r=52,start=-Math.PI/2;ctx.lineWidth=18;if(!total){ctx.strokeStyle='#e2e8f0';ctx.beginPath();ctx.arc(cx,cy,r,0,Math.PI*2);ctx.stroke();}for(var j=0;j<data.length;j++){var sl=total?Math.PI*2*data[j].value/total:0;ctx.strokeStyle=data[j].color;ctx.beginPath();ctx.arc(cx,cy,r,start,start+sl);ctx.stroke();start+=sl;}ctx.fillStyle='#0f172a';ctx.font='800 22px Inter, Arial';ctx.textAlign='center';ctx.fillText(total?fmtPct(data[0].value/total*100).replace(',00',''):'0%',cx,cy+8);ctx.textAlign='left';for(var k=0;k<data.length;k++){var y=52+k*33;ctx.fillStyle=data[k].color;ctx.fillRect(158,y-9,11,11);ctx.fillStyle='#64748b';ctx.font='700 10px Inter, Arial';ctx.fillText(data[k].label,178,y);ctx.fillStyle='#0f172a';ctx.font='800 12px Inter, Arial';ctx.fillText(fmtNum(data[k].value),178,y+16);}}
        function fornecedorHtml(){var arr=groupFornecedor(),h='<div class=\"table-wrap\"><table class=\"monitor-table\"><thead><tr><th>Fornecedor</th><th>Total</th><th>No prazo</th><th>Fora</th><th>On-time</th><th>Lead medio</th><th>Melhor/Pior</th></tr></thead><tbody>';for(var i=0;i<arr.length;i++){var it=arr[i],pct=it.total?it.noPrazo/it.total*100:0;h+='<tr class=\"drill-row\" data-codparc=\"'+esc(it.codparc)+'\" data-nome=\"'+esc(it.razaosocial)+'\" data-search=\"'+esc(rowIndex(it))+'\"><td><strong>'+esc(it.razaosocial)+'</strong><small>CODPARC '+esc(it.codparc)+'</small></td><td><strong>'+fmtNum(it.total)+'</strong></td><td><span class=\"status-badge status-success\">'+fmtNum(it.noPrazo)+'</span></td><td><span class=\"status-badge status-danger\">'+fmtNum(it.fora)+'</span></td><td><strong class=\"'+(pct>=90?'value-positive':'value-danger')+'\">'+fmtPct(pct)+'</strong><small>Clique para detalhar</small></td><td><strong>'+fmtDec(it.somaDias/Math.max(it.total,1))+'</strong></td><td>'+fmtNum(it.melhor||0)+' / '+fmtNum(it.pior||0)+'</td></tr>';}return h+'</tbody></table></div>';}
        function detalheHtml(arr){var h='<div class=\"table-wrap\"><table class=\"monitor-table\"><thead><tr><th>Fornecedor</th><th>Pedido/Entrada</th><th>Produto</th><th>Uso</th><th>Datas</th><th>Dias</th><th>Prazo</th></tr></thead><tbody>';for(var i=0;i<arr.length;i++){var it=arr[i];h+='<tr data-codparc=\"'+esc(it.codparc)+'\" data-search=\"'+esc(rowIndex(it))+'\"><td><strong>'+esc(it.razaosocial)+'</strong><small>CODPARC '+esc(it.codparc)+'</small></td><td><strong>Ped. '+esc(it.nunotapedido)+'</strong><small>Ent. '+esc(it.nunotaentrada)+'</small></td><td><strong>'+esc(it.codprod)+'</strong><small>'+esc(it.descrprod)+'</small></td><td>'+esc(it.tipoproduto)+'<small>'+esc(it.usoprod)+'</small></td><td>Ped. '+esc(it.datapedido||'-')+'<small>Prev. '+esc(it.dataprevista||'-')+' | Ent. '+esc(it.dataentrada||'-')+'</small></td><td><strong>'+fmtNum(it.dias)+'</strong><small>Limite '+fmtNum(it.prazo)+'</small></td><td><span class=\"status-badge '+badgeClass(it.classificacao)+'\">'+esc(it.classificacao)+'</span></td></tr>';}return h+'</tbody></table></div>';}
        function renderTabs(){var tabs=[['fornecedor','Fornecedores',groupFornecedor().length],['detalhe','Detalhes',items.length]],h='';for(var i=0;i<tabs.length;i++)h+='<button class=\"tab-button'+(state.tab===tabs[i][0]?' is-active':'')+'\" data-tab=\"'+tabs[i][0]+'\"><span>'+tabs[i][1]+'</span><span class=\"tab-count\">'+tabs[i][2]+'</span></button>';document.getElementById('tabsNav').innerHTML=h;var btns=document.querySelectorAll('.tab-button');for(var b=0;b<btns.length;b++)btns[b].onclick=function(){state.tab=this.getAttribute('data-tab');render();};}
        function renderContext(){var el=document.getElementById('contextFilter');if(state.fornecedor&&state.tab==='detalhe'){el.style.display='flex';el.innerHTML='<span>Fornecedor selecionado: <strong>'+esc(state.fornecedor.nome)+'</strong> | CODPARC '+esc(state.fornecedor.codparc)+'</span><button type=\"button\" onclick=\"clearContext()\">Ver todos</button>';}else{el.style.display='none';el.innerHTML='';}}
        function clearContext(){state.fornecedor=null;render();}
        function bindDrill(){var rows=document.querySelectorAll('.drill-row');for(var i=0;i<rows.length;i++)rows[i].onclick=function(){state.fornecedor={codparc:this.getAttribute('data-codparc'),nome:this.getAttribute('data-nome')};state.tab='detalhe';render();};}
        function render(){renderTabs();document.getElementById('tablesArea').innerHTML=state.tab==='fornecedor'?fornecedorHtml():detalheHtml(items);bindDrill();renderContext();applySearch();}
        function applySearch(){var term=norm(document.getElementById('searchInput').value||''),rows=document.querySelectorAll('#tablesArea tbody tr'),vis=0;for(var i=0;i<rows.length;i++){var ok=!term||norm(rows[i].getAttribute('data-search')).indexOf(term)>=0;if(ok&&state.fornecedor&&state.tab==='detalhe')ok=rows[i].getAttribute('data-codparc')===state.fornecedor.codparc;rows[i].style.display=ok?'':'none';if(ok)vis++;}document.getElementById('searchInfo').textContent=rows.length?vis+' de '+rows.length+' registros exibidos':'';}
        document.getElementById('searchInput').oninput=applySearch;document.getElementById('clearSearch').onclick=function(){document.getElementById('searchInput').value='';clearContext();};
        updateMetrics();render();tick();setInterval(tick,1000);
    </script>
</body>
</html>
