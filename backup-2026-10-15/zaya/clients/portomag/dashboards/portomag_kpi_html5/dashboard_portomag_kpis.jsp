<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Zaya.it Dashboard - KPIs Compras</title>
    <link rel="stylesheet" type="text/css" href="${BASE_FOLDER}/css/dashboardPortomagKpis.css">
    <link rel="stylesheet" type="text/css" href="css/dashboardPortomagKpis.css">
    <snk:load/>
</head>
<body>
    <snk:query var="reqDetalhe">
        <![CDATA[
            /* OS 7259 - detalhe por requisicao/item.
               Periodo: CAB.DTNEG.
               Corte historico: :PERIODO.FIN para itens sem atendimento final.
               Sem SYSDATE para nao alterar meses fechados. */
            WITH PARAMS AS (
                SELECT TRUNC(:PERIODO.INI) DTINI,
                       TRUNC(:PERIODO.FIN) DTFIN,
                       NVL(:DIASPRAZO, 7) DIASPRAZO
                  FROM DUAL
            ),
            REQ_ITEM AS (
                SELECT CAB.NUNOTA,
                       ITE.SEQUENCIA,
                       CAB.CODTIPOPER,
                       TOP.DESCROPER,
                       ITE.CODPROD,
                       PRO.DESCRPROD,
                       TRUNC(CAB.DTNEG) DT_REQUISICAO,
                       TRUNC(NVL(CAB.DTENTSAI, CAB.DTNEG)) DT_INICIO_CALCULO
                  FROM TGFCAB CAB
                  JOIN TGFITE ITE ON ITE.NUNOTA = CAB.NUNOTA
                  JOIN TGFTOP TOP ON TOP.CODTIPOPER = CAB.CODTIPOPER AND TOP.DHALTER = CAB.DHTIPOPER
                  JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD
                 CROSS JOIN PARAMS P
                 WHERE CAB.CODTIPOPER IN (400, 401)
                   AND TRUNC(CAB.DTNEG) BETWEEN P.DTINI AND P.DTFIN
            ),
            FLUXO_ITEM AS (
                SELECT R.NUNOTA,
                       R.SEQUENCIA,
                       MIN(TRUNC(NVL(ITC.DTNEG, ITC.DTENTSAI))) DT_INICIO_COTACAO,
                       MAX(TRUNC(CAP.DTENTSAI)) DT_ATENDIMENTO,
                       MAX(ITC.NUNOTA) NUNOTA_COTACAO,
                       MAX(CAP.NUNOTA) NUNOTA_ATENDIMENTO
                  FROM REQ_ITEM R
                  LEFT JOIN TGFVAR VAR1 ON VAR1.NUNOTAORIG = R.NUNOTA AND VAR1.SEQUENCIAORIG = R.SEQUENCIA
                  LEFT JOIN TGFCAB ITC ON ITC.NUNOTA = VAR1.NUNOTA
                  LEFT JOIN TGFITE ITV ON ITV.NUNOTA = VAR1.NUNOTA AND ITV.SEQUENCIA = VAR1.SEQUENCIA
                  LEFT JOIN TGFVAR VAR2 ON VAR2.NUNOTAORIG = ITC.NUNOTA AND VAR2.SEQUENCIAORIG = ITV.SEQUENCIA
                  LEFT JOIN TGFCAB CAP ON CAP.NUNOTA = VAR2.NUNOTA
                 GROUP BY R.NUNOTA, R.SEQUENCIA
            )
            SELECT R.NUNOTA,
                   R.SEQUENCIA,
                   R.CODTIPOPER,
                   R.DESCROPER,
                   R.CODPROD,
                   R.DESCRPROD,
                   R.DT_REQUISICAO,
                   F.DT_INICIO_COTACAO,
                   F.DT_ATENDIMENTO,
                   P.DTFIN DT_CORTE_USADA,
                   F.NUNOTA_COTACAO,
                   F.NUNOTA_ATENDIMENTO,
                   TRUNC(NVL(F.DT_ATENDIMENTO, P.DTFIN)) - R.DT_INICIO_CALCULO DIAS_CALCULADOS,
                   P.DIASPRAZO PRAZO_LIMITE,
                   CASE WHEN F.DT_ATENDIMENTO IS NOT NULL THEN 'Fechado'
                        WHEN F.DT_INICIO_COTACAO IS NOT NULL THEN 'Em andamento'
                        ELSE 'Aberto' END STATUS_REQUISICAO,
                   CASE WHEN TRUNC(NVL(F.DT_ATENDIMENTO, P.DTFIN)) - R.DT_INICIO_CALCULO <= P.DIASPRAZO
                        THEN 'No Prazo' ELSE 'Fora do Prazo' END CLASSIFICACAO_PRAZO
              FROM REQ_ITEM R
              JOIN FLUXO_ITEM F ON F.NUNOTA = R.NUNOTA AND F.SEQUENCIA = R.SEQUENCIA
             CROSS JOIN PARAMS P
             ORDER BY R.DT_REQUISICAO, R.NUNOTA, R.SEQUENCIA
        ]]>
    </snk:query>

    <snk:query var="entDetalhe">
        <![CDATA[
            /* OS 7260 - detalhe por fornecedor/pedido/entrada/item.
               Periodo: data real de entrada ITC.DTENTSAI.
               Somente entregas concluidas entram no on-time.
               Regra: USOPROD C = 15 dias, M = 30 dias. */
            WITH PARAMS AS (
                SELECT TRUNC(:PERIODO.INI) DTINI,
                       TRUNC(:PERIODO.FIN) DTFIN
                  FROM DUAL
            ),
            ENTREGA_ITEM AS (
                SELECT CAB.CODPARC,
                       PAR.RAZAOSOCIAL,
                       CAB.NUNOTA NUNOTA_PEDIDO,
                       ITE.SEQUENCIA SEQ_PEDIDO,
                       ITC.NUNOTA NUNOTA_ENTRADA,
                       ITV.SEQUENCIA SEQ_ENTRADA,
                       TOP.CODTIPOPER,
                       TOP.DESCROPER,
                       PRO.CODPROD,
                       PRO.DESCRPROD,
                       PRO.USOPROD,
                       CASE WHEN PRO.USOPROD = 'C' THEN 'Uso/consumo'
                            WHEN PRO.USOPROD = 'M' THEN 'Materia-prima'
                            ELSE 'Outros' END TIPO_PRODUTO,
                       TRUNC(NVL(ITE.DTINICIO, NVL(CAB.DTPREVENT, CAB.DTNEG))) DATA_PEDIDO,
                       TRUNC(CAB.DTPREVENT) DATA_PREVISTA,
                       TRUNC(ITC.DTENTSAI) DATA_ENTRADA,
                       CASE WHEN PRO.USOPROD = 'C' THEN 15 ELSE 30 END PRAZO_ESPERADO
                  FROM TGFCAB CAB
                  JOIN TGFITE ITE ON ITE.NUNOTA = CAB.NUNOTA
                  JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD
                  JOIN TGFPAR PAR ON PAR.CODPARC = CAB.CODPARC
                  JOIN TGFTOP TOP ON TOP.CODTIPOPER = CAB.CODTIPOPER AND TOP.DHALTER = CAB.DHTIPOPER
                  JOIN TGFVAR VAR ON VAR.NUNOTAORIG = CAB.NUNOTA AND VAR.SEQUENCIAORIG = ITE.SEQUENCIA
                  JOIN TGFCAB ITC ON ITC.NUNOTA = VAR.NUNOTA
                  JOIN TGFITE ITV ON ITV.NUNOTA = VAR.NUNOTA AND ITV.SEQUENCIA = VAR.SEQUENCIA
                 CROSS JOIN PARAMS P
                 WHERE CAB.TIPMOV = 'O'
                   AND TRUNC(ITC.DTENTSAI) BETWEEN P.DTINI AND P.DTFIN
                   AND PAR.CODPARC = NVL(:CODPARC, PAR.CODPARC)
                   AND PRO.USOPROD IN ('M', 'C')
                 GROUP BY CAB.CODPARC, PAR.RAZAOSOCIAL, CAB.NUNOTA, ITE.SEQUENCIA,
                          ITC.NUNOTA, ITV.SEQUENCIA, TOP.CODTIPOPER, TOP.DESCROPER,
                          PRO.CODPROD, PRO.DESCRPROD, PRO.USOPROD,
                          TRUNC(NVL(ITE.DTINICIO, NVL(CAB.DTPREVENT, CAB.DTNEG))),
                          TRUNC(CAB.DTPREVENT), TRUNC(ITC.DTENTSAI)
            )
            SELECT CODPARC,
                   RAZAOSOCIAL,
                   NUNOTA_PEDIDO,
                   SEQ_PEDIDO,
                   NUNOTA_ENTRADA,
                   SEQ_ENTRADA,
                   CODTIPOPER,
                   DESCROPER,
                   CODPROD,
                   DESCRPROD,
                   USOPROD,
                   TIPO_PRODUTO,
                   DATA_PEDIDO,
                   DATA_PREVISTA,
                   DATA_ENTRADA,
                   TRUNC(:PERIODO.FIN) DATA_CORTE_REFERENCIA,
                   DATA_ENTRADA - DATA_PEDIDO DIAS_CALCULADOS,
                   PRAZO_ESPERADO,
                   CASE WHEN DATA_ENTRADA - DATA_PEDIDO <= PRAZO_ESPERADO THEN 'No Prazo'
                        ELSE 'Fora do Prazo' END CLASSIFICACAO_PRAZO,
                   CASE WHEN DATA_ENTRADA - DATA_PEDIDO <= PRAZO_ESPERADO THEN 'S' ELSE 'N' END NOPRAZO
              FROM ENTREGA_ITEM
             ORDER BY RAZAOSOCIAL, DATA_ENTRADA, NUNOTA_PEDIDO, SEQ_PEDIDO
        ]]>
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
                    <h1>KPIs Auditaveis de Compras</h1>
                    <p>OS 7259 - Requisicoes | OS 7260 - Entregas On-time</p>
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
                <strong class="metric-value" id="reqTotal">0</strong>
                <small class="metric-foot">TOP 400/401 por item</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Req. no prazo</span>
                <strong class="metric-value" id="reqPctPrazo">0,00%</strong>
                <small class="metric-foot">Base detalhada auditavel</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Entregas</span>
                <strong class="metric-value" id="entTotal">0</strong>
                <small class="metric-foot">Notas de entrada no periodo</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">On-time fornecedor</span>
                <strong class="metric-value" id="entPctOnTime">0,00%</strong>
                <small class="metric-foot">No prazo / total de entregas</small>
            </article>
        </section>

        <section class="dashboard-layout">
            <aside class="side-column">
                <div class="chart-card">
                    <div class="card-title">Requisicoes por prazo</div>
                    <canvas id="reqPrazoChart" width="280" height="190"></canvas>
                </div>
                <div class="chart-card">
                    <div class="card-title">Status das requisicoes</div>
                    <canvas id="reqStatusChart" width="280" height="190"></canvas>
                </div>
                <div class="chart-card">
                    <div class="card-title">Entregas on-time</div>
                    <canvas id="entPrazoChart" width="280" height="190"></canvas>
                </div>
            </aside>

            <section class="dashboard-content">
                <div class="panel-card">
                    <div class="panel-head">
                        <div>
                            <h2>Indicadores e detalhamento</h2>
                            <p>Resultados historicos calculados com data de corte fixa e drill-down de conferencia.</p>
                        </div>
                    </div>
                    <div class="tabs-toolbar">
                        <div class="tabs-nav" id="tabsNav"></div>
                        <div class="table-search">
                            <input type="text" id="searchInput" placeholder="Buscar fornecedor, nota, produto, status..." autocomplete="off">
                            <button class="ghost-button" type="button" id="clearSearch">Limpar</button>
                        </div>
                    </div>
                    <div class="context-filter" id="contextFilter" style="display:none"></div>
                    <div class="search-info" id="searchInfo"></div>
                    <div id="tablesArea">
                        <div class="empty-state">Carregando dados...</div>
                    </div>
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
        var state = { tab: 'reqResumo', fornecedor: null, produto: null };

        function trimText(v) { return v == null ? '' : String(v).replace(/^\s+|\s+$/g, ''); }
        function toNumber(v) {
            var s = trimText(v);
            if (s.indexOf(',') >= 0 && s.indexOf('.') >= 0) s = s.replace(/\./g, '').replace(',', '.');
            else if (s.indexOf(',') >= 0) s = s.replace(',', '.');
            var n = Number(s);
            return isNaN(n) ? 0 : n;
        }
        function esc(s) {
            return String(s || '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
        }
        function norm(s) {
            s = trimText(s).toUpperCase();
            if (s.normalize) s = s.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
            return s;
        }
        function fmtNum(v) { return Number(v || 0).toLocaleString('pt-BR'); }
        function fmtDec(v) { return Number(v || 0).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 }); }
        function fmtPct(v) { return fmtDec(v) + '%'; }
        function pad(n) { return n < 10 ? '0' + n : String(n); }
        function tick() {
            var d = new Date();
            document.getElementById('clockDisplay').textContent = pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds());
        }

        function collectReq() {
            var rows = document.querySelectorAll('#reqSource .js-req'), out = [];
            for (var i = 0; i < rows.length; i++) {
                var r = rows[i];
                out.push({
                    nunota: r.getAttribute('data-nunota') || '',
                    sequencia: r.getAttribute('data-sequencia') || '',
                    codtipoper: r.getAttribute('data-codtipoper') || '',
                    descroper: r.getAttribute('data-descroper') || '',
                    codprod: r.getAttribute('data-codprod') || '',
                    descrprod: r.getAttribute('data-descrprod') || '',
                    dtrequisicao: r.getAttribute('data-dtrequisicao') || '',
                    dtiniciocotacao: r.getAttribute('data-dtiniciocotacao') || '',
                    dtatendimento: r.getAttribute('data-dtatendimento') || '',
                    dtcorte: r.getAttribute('data-dtcorte') || '',
                    nunotacotacao: r.getAttribute('data-nunotacotacao') || '',
                    nunotaatendimento: r.getAttribute('data-nunotaatendimento') || '',
                    dias: toNumber(r.getAttribute('data-dias')),
                    prazo: toNumber(r.getAttribute('data-prazo')),
                    status: r.getAttribute('data-status') || '',
                    classificacao: r.getAttribute('data-classificacao') || ''
                });
            }
            return out;
        }
        function collectEnt() {
            var rows = document.querySelectorAll('#entSource .js-ent'), out = [];
            for (var i = 0; i < rows.length; i++) {
                var r = rows[i];
                out.push({
                    codparc: r.getAttribute('data-codparc') || '',
                    razaosocial: r.getAttribute('data-razaosocial') || '',
                    nunotapedido: r.getAttribute('data-nunotapedido') || '',
                    seqpedido: r.getAttribute('data-seqpedido') || '',
                    nunotaentrada: r.getAttribute('data-nunotaentrada') || '',
                    seqentrada: r.getAttribute('data-seqentrada') || '',
                    codtipoper: r.getAttribute('data-codtipoper') || '',
                    descroper: r.getAttribute('data-descroper') || '',
                    codprod: r.getAttribute('data-codprod') || '',
                    descrprod: r.getAttribute('data-descrprod') || '',
                    usoprod: r.getAttribute('data-usoprod') || '',
                    tipoproduto: r.getAttribute('data-tipoproduto') || '',
                    datapedido: r.getAttribute('data-datapedido') || '',
                    dataprevista: r.getAttribute('data-dataprevista') || '',
                    dataentrada: r.getAttribute('data-dataentrada') || '',
                    dtcorte: r.getAttribute('data-dtcorte') || '',
                    dias: toNumber(r.getAttribute('data-dias')),
                    prazo: toNumber(r.getAttribute('data-prazo')),
                    classificacao: r.getAttribute('data-classificacao') || '',
                    noprazo: r.getAttribute('data-noprazo') || ''
                });
            }
            return out;
        }

        var reqItems = collectReq();
        var entItems = collectEnt();

        function groupEntByFornecedor() {
            var map = {}, arr = [];
            for (var i = 0; i < entItems.length; i++) {
                var it = entItems[i], k = it.codparc;
                if (!map[k]) {
                    map[k] = { codparc: it.codparc, razaosocial: it.razaosocial, total: 0, noPrazo: 0, fora: 0, somaDias: 0, melhor: null, pior: null };
                    arr.push(map[k]);
                }
                var g = map[k];
                g.total++;
                if (it.noprazo === 'S') g.noPrazo++; else g.fora++;
                g.somaDias += it.dias;
                g.melhor = g.melhor == null ? it.dias : Math.min(g.melhor, it.dias);
                g.pior = g.pior == null ? it.dias : Math.max(g.pior, it.dias);
            }
            arr.sort(function(a, b) {
                var pa = a.total ? a.noPrazo / a.total : 0, pb = b.total ? b.noPrazo / b.total : 0;
                return pb - pa || b.total - a.total || String(a.razaosocial).localeCompare(String(b.razaosocial));
            });
            return arr;
        }

        function countWhere(items, field, value) {
            var n = 0;
            for (var i = 0; i < items.length; i++) if (items[i][field] === value) n++;
            return n;
        }
        function avg(items, field) {
            if (!items.length) return 0;
            var s = 0;
            for (var i = 0; i < items.length; i++) s += Number(items[i][field] || 0);
            return s / items.length;
        }

        function updateMetrics() {
            var reqNoPrazo = countWhere(reqItems, 'classificacao', 'No Prazo');
            var entNoPrazo = countWhere(entItems, 'noprazo', 'S');
            document.getElementById('reqTotal').textContent = fmtNum(reqItems.length);
            document.getElementById('reqPctPrazo').textContent = fmtPct(reqItems.length ? reqNoPrazo / reqItems.length * 100 : 0);
            document.getElementById('entTotal').textContent = fmtNum(entItems.length);
            document.getElementById('entPctOnTime').textContent = fmtPct(entItems.length ? entNoPrazo / entItems.length * 100 : 0);
            drawDonut('reqPrazoChart', [
                { label: 'No Prazo', value: reqNoPrazo, color: '#22c55e' },
                { label: 'Fora do Prazo', value: reqItems.length - reqNoPrazo, color: '#ef4444' }
            ]);
            drawDonut('reqStatusChart', [
                { label: 'Aberto', value: countWhere(reqItems, 'status', 'Aberto'), color: '#3b82f6' },
                { label: 'Em andamento', value: countWhere(reqItems, 'status', 'Em andamento'), color: '#f97316' },
                { label: 'Fechado', value: countWhere(reqItems, 'status', 'Fechado'), color: '#22c55e' }
            ]);
            drawDonut('entPrazoChart', [
                { label: 'On-time', value: entNoPrazo, color: '#22c55e' },
                { label: 'Fora', value: entItems.length - entNoPrazo, color: '#ef4444' }
            ]);
        }

        function drawDonut(id, items) {
            var canvas = document.getElementById(id);
            if (!canvas || !canvas.getContext) return;
            var ctx = canvas.getContext('2d'), total = 0;
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            for (var i = 0; i < items.length; i++) total += items[i].value;
            var cx = 74, cy = 84, r = 52, start = -Math.PI / 2;
            ctx.lineWidth = 18;
            if (!total) {
                ctx.strokeStyle = '#e2e8f0';
                ctx.beginPath(); ctx.arc(cx, cy, r, 0, Math.PI * 2); ctx.stroke();
            }
            for (var j = 0; j < items.length; j++) {
                var slice = total ? Math.PI * 2 * items[j].value / total : 0;
                ctx.strokeStyle = items[j].color;
                ctx.beginPath(); ctx.arc(cx, cy, r, start, start + slice); ctx.stroke();
                start += slice;
            }
            ctx.fillStyle = '#0f172a';
            ctx.font = '800 22px Inter, Arial';
            ctx.textAlign = 'center';
            ctx.fillText(total ? fmtPct(items[0].value / total * 100).replace(',00', '') : '0%', cx, cy + 8);
            ctx.textAlign = 'left';
            for (var k = 0; k < items.length; k++) {
                var y = 52 + k * 33;
                ctx.fillStyle = items[k].color;
                ctx.fillRect(158, y - 9, 11, 11);
                ctx.fillStyle = '#64748b';
                ctx.font = '700 10px Inter, Arial';
                ctx.fillText(items[k].label, 178, y);
                ctx.fillStyle = '#0f172a';
                ctx.font = '800 12px Inter, Arial';
                ctx.fillText(fmtNum(items[k].value), 178, y + 16);
            }
        }

        function rowIndex(obj) {
            var s = '';
            for (var k in obj) if (obj.hasOwnProperty(k)) s += ' ' + obj[k];
            return norm(s);
        }
        function badgeClass(v) {
            return v === 'No Prazo' || v === 'S' || v === 'Fechado' ? 'status-success' : (v === 'Em andamento' ? 'status-warning' : 'status-danger');
        }

        function reqResumoHtml() {
            var status = [
                ['Aberto', countWhere(reqItems, 'status', 'Aberto')],
                ['Em andamento', countWhere(reqItems, 'status', 'Em andamento')],
                ['Fechado', countWhere(reqItems, 'status', 'Fechado')]
            ];
            var prazo = [
                ['No Prazo', countWhere(reqItems, 'classificacao', 'No Prazo')],
                ['Fora do Prazo', countWhere(reqItems, 'classificacao', 'Fora do Prazo')]
            ];
            var html = '<div class="quick-grid">';
            html += quickCard('Total requisicoes/item', reqItems.length, 'Periodo por DTNEG');
            html += quickCard('Abertas', status[0][1], 'Sem cotacao/atendimento');
            html += quickCard('Em andamento', status[1][1], 'Com cotacao/intermediario');
            html += quickCard('Fechadas', status[2][1], 'Com atendimento final');
            html += quickCard('Lead time medio', fmtDec(avg(reqItems, 'dias')) + ' dias', 'Base detalhada');
            html += quickCard('No prazo', reqItems.length ? fmtPct(prazo[0][1] / reqItems.length * 100) : '0,00%', 'Prazo parametrizado');
            html += '</div>';
            html += reqDetalheHtml(reqItems);
            return html;
        }
        function quickCard(label, value, foot) {
            return '<div class="mini-card"><span>' + esc(label) + '</span><strong>' + esc(value) + '</strong><small>' + esc(foot) + '</small></div>';
        }
        function reqDetalheHtml(items) {
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th>NUNOTA</th><th>TOP</th><th>Produto</th><th>Datas</th><th>Dias</th><th>Status</th><th>Prazo</th></tr></thead><tbody>';
            for (var i = 0; i < items.length; i++) {
                var it = items[i];
                html += '<tr data-search="' + esc(rowIndex(it)) + '">';
                html += '<td><strong>' + esc(it.nunota) + '</strong><small>Seq. ' + esc(it.sequencia) + '</small></td>';
                html += '<td><strong>' + esc(it.codtipoper) + '</strong><small>' + esc(it.descroper) + '</small></td>';
                html += '<td><strong>' + esc(it.codprod) + '</strong><small>' + esc(it.descrprod) + '</small></td>';
                html += '<td>Req. ' + esc(it.dtrequisicao || '-') + '<small>Atend. ' + esc(it.dtatendimento || '-') + ' | Corte ' + esc(it.dtcorte || '-') + '</small></td>';
                html += '<td><strong>' + esc(fmtNum(it.dias)) + '</strong><small>Limite ' + esc(fmtNum(it.prazo)) + '</small></td>';
                html += '<td><span class="status-badge ' + badgeClass(it.status) + '">' + esc(it.status) + '</span></td>';
                html += '<td><span class="status-badge ' + badgeClass(it.classificacao) + '">' + esc(it.classificacao) + '</span></td>';
                html += '</tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }
        function entFornecedorHtml() {
            var fornecedores = groupEntByFornecedor();
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th>Fornecedor</th><th>Total</th><th>No prazo</th><th>Fora</th><th>On-time</th><th>Lead medio</th><th>Melhor/Pior</th></tr></thead><tbody>';
            for (var i = 0; i < fornecedores.length; i++) {
                var it = fornecedores[i], pct = it.total ? it.noPrazo / it.total * 100 : 0;
                html += '<tr class="drill-row" data-codparc="' + esc(it.codparc) + '" data-nome="' + esc(it.razaosocial) + '" data-search="' + esc(rowIndex(it)) + '">';
                html += '<td><strong>' + esc(it.razaosocial) + '</strong><small>CODPARC ' + esc(it.codparc) + '</small></td>';
                html += '<td><strong>' + esc(fmtNum(it.total)) + '</strong></td>';
                html += '<td><span class="status-badge status-success">' + esc(fmtNum(it.noPrazo)) + '</span></td>';
                html += '<td><span class="status-badge status-danger">' + esc(fmtNum(it.fora)) + '</span></td>';
                html += '<td><strong class="' + (pct >= 90 ? 'value-positive' : 'value-danger') + '">' + esc(fmtPct(pct)) + '</strong><small>Clique para detalhar</small></td>';
                html += '<td><strong>' + esc(fmtDec(it.somaDias / Math.max(it.total, 1))) + '</strong></td>';
                html += '<td>' + esc(fmtNum(it.melhor || 0)) + ' / ' + esc(fmtNum(it.pior || 0)) + '</td>';
                html += '</tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }
        function entDetalheHtml(items) {
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th>Fornecedor</th><th>Pedido/Entrada</th><th>Produto</th><th>Uso</th><th>Datas</th><th>Dias</th><th>Prazo</th></tr></thead><tbody>';
            for (var i = 0; i < items.length; i++) {
                var it = items[i];
                html += '<tr data-codparc="' + esc(it.codparc) + '" data-codprod="' + esc(it.codprod) + '" data-search="' + esc(rowIndex(it)) + '">';
                html += '<td><strong>' + esc(it.razaosocial) + '</strong><small>CODPARC ' + esc(it.codparc) + '</small></td>';
                html += '<td><strong>Ped. ' + esc(it.nunotapedido) + '</strong><small>Ent. ' + esc(it.nunotaentrada) + '</small></td>';
                html += '<td><strong>' + esc(it.codprod) + '</strong><small>' + esc(it.descrprod) + '</small></td>';
                html += '<td>' + esc(it.tipoproduto) + '<small>' + esc(it.usoprod) + '</small></td>';
                html += '<td>Ped. ' + esc(it.datapedido || '-') + '<small>Prev. ' + esc(it.dataprevista || '-') + ' | Ent. ' + esc(it.dataentrada || '-') + '</small></td>';
                html += '<td><strong>' + esc(fmtNum(it.dias)) + '</strong><small>Limite ' + esc(fmtNum(it.prazo)) + '</small></td>';
                html += '<td><span class="status-badge ' + badgeClass(it.classificacao) + '">' + esc(it.classificacao) + '</span></td>';
                html += '</tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }

        function renderTabs() {
            var tabs = [
                ['reqResumo', 'Requisicoes', reqItems.length],
                ['reqDetalhe', 'Detalhe Req.', reqItems.length],
                ['entFornecedor', 'Fornecedor On-time', groupEntByFornecedor().length],
                ['entDetalhe', 'Detalhe Entrega', entItems.length]
            ];
            var h = '';
            for (var i = 0; i < tabs.length; i++) {
                h += '<button class="tab-button' + (state.tab === tabs[i][0] ? ' is-active' : '') + '" type="button" data-tab="' + tabs[i][0] + '"><span>' + tabs[i][1] + '</span><span class="tab-count">' + tabs[i][2] + '</span></button>';
            }
            document.getElementById('tabsNav').innerHTML = h;
            var buttons = document.querySelectorAll('.tab-button');
            for (var b = 0; b < buttons.length; b++) {
                buttons[b].onclick = function() { state.tab = this.getAttribute('data-tab'); render(); };
            }
        }
        function renderContext() {
            var el = document.getElementById('contextFilter');
            if (state.fornecedor && state.tab === 'entDetalhe') {
                el.style.display = 'flex';
                el.innerHTML = '<span>Fornecedor selecionado: <strong>' + esc(state.fornecedor.nome) + '</strong> | CODPARC ' + esc(state.fornecedor.codparc) + '</span><button type="button" onclick="clearContext()">Ver todos</button>';
            } else {
                el.style.display = 'none';
                el.innerHTML = '';
            }
        }
        function clearContext() {
            state.fornecedor = null;
            render();
        }
        function render() {
            renderTabs();
            var area = document.getElementById('tablesArea');
            if (state.tab === 'reqResumo') area.innerHTML = reqResumoHtml();
            if (state.tab === 'reqDetalhe') area.innerHTML = reqDetalheHtml(reqItems);
            if (state.tab === 'entFornecedor') area.innerHTML = entFornecedorHtml();
            if (state.tab === 'entDetalhe') area.innerHTML = entDetalheHtml(entItems);
            bindDrill();
            renderContext();
            applySearch();
        }
        function bindDrill() {
            var rows = document.querySelectorAll('.drill-row');
            for (var i = 0; i < rows.length; i++) {
                rows[i].onclick = function() {
                    state.fornecedor = { codparc: this.getAttribute('data-codparc'), nome: this.getAttribute('data-nome') };
                    state.tab = 'entDetalhe';
                    render();
                };
            }
        }
        function applySearch() {
            var term = norm(document.getElementById('searchInput').value || '');
            var rows = document.querySelectorAll('#tablesArea tbody tr'), visible = 0;
            for (var i = 0; i < rows.length; i++) {
                var ok = !term || norm(rows[i].getAttribute('data-search')).indexOf(term) >= 0;
                if (ok && state.fornecedor && state.tab === 'entDetalhe') ok = rows[i].getAttribute('data-codparc') === state.fornecedor.codparc;
                rows[i].style.display = ok ? '' : 'none';
                if (ok) visible++;
            }
            document.getElementById('searchInfo').textContent = rows.length ? visible + ' de ' + rows.length + ' registros exibidos' : '';
        }
        document.getElementById('searchInput').oninput = applySearch;
        document.getElementById('clearSearch').onclick = function() {
            document.getElementById('searchInput').value = '';
            clearContext();
        };

        updateMetrics();
        render();
        tick();
        setInterval(tick, 1000);
    </script>
</body>
</html>
