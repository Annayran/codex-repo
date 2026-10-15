<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" isELIgnored ="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<html>
<head>
    <title>Painel de Monitoramento - Clientes</title>
    <link rel="stylesheet" type="text/css" href="${BASE_FOLDER}/css/dashboardClientes.css">
    <snk:load/>
</head>
<body>
    <snk:query var="logsCliente">
        <%
            String query =
                "SELECT DISTINCT " +
                "    LOG.IDLOG, LOG.CODPARC, PAR.NOMEPARC, PAR.CGC_CPF, LOG.DHINTEG, LOG.STATUS, LOG.MENSAGEM " +
                "FROM AD_LOGINTCLIENTE LOG " +
                "LEFT JOIN TGFPAR PAR ON PAR.CODPARC = LOG.CODPARC " +
                "WHERE LOG.IDLOG = ( " +
                "    SELECT MAX(L2.IDLOG) " +
                "    FROM AD_LOGINTCLIENTE L2 " +
                "    WHERE L2.CODPARC = LOG.CODPARC " +
                ") " +
                "AND ( " +
                "    ( " +
                "        ( " +
                "            NVL(UPPER(LOG.STATUS), '') LIKE '%SUCESS%' " +
                "            OR NVL(UPPER(LOG.STATUS), '') LIKE '%ENVIAD%' " +
                "            OR NVL(UPPER(LOG.STATUS), '') LIKE '%OK%' " +
                "            OR NVL(UPPER(LOG.STATUS), '') LIKE '%INTEGRAD%' " +
                "        ) " +
                "        AND TRUNC(LOG.DHINTEG) BETWEEN :P_PERIODO.INI AND :P_PERIODO.FIN " +
                "    ) " +
                "    OR ( " +
                "        NVL(UPPER(LOG.STATUS), '') NOT LIKE '%SUCESS%' " +
                "        AND NVL(UPPER(LOG.STATUS), '') NOT LIKE '%ENVIAD%' " +
                "        AND NVL(UPPER(LOG.STATUS), '') NOT LIKE '%OK%' " +
                "        AND NVL(UPPER(LOG.STATUS), '') NOT LIKE '%INTEGRAD%' " +
                "    ) " +
                ") " +
                "ORDER BY LOG.DHINTEG DESC, LOG.IDLOG DESC";
            out.println(query);
        %>
    </snk:query>

    <snk:query var="aptasCliente">
        <%
            String queryAptas =
                "SELECT " +
                "    CODPARC AS CODIGO, " +
                "    NOMEPARC AS NOME, " +
                "    CGC_CPF AS CPF_CNPJ, " +
                "    DTCAD AS DATA_CADASTRO, " +
                "    CASE " +
                "        WHEN TIPPESSOA = 'F' THEN 'FISICA' " +
                "        WHEN TIPPESSOA = 'J' THEN 'JURIDICA' " +
                "        ELSE 'DESCONHECIDO' " +
                "    END AS TIPO_PESSOA " +
                "FROM TGFPAR " +
                "WHERE AD_DATA_INTEGRACAO_SAP IS NULL " +
                "AND TRUNC(DTCAD) >= TO_DATE('08/02/2026', 'DD/MM/YYYY') " +
                "AND CLIENTE = 'S' " +
                "AND ATIVO = 'S' " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM AD_LOGINTCLIENTE LOG " +
                "    WHERE LOG.CODPARC = TGFPAR.CODPARC " +
                "      AND LOG.STATUS = 'SUCESSO' " +
                ")";
            out.println(queryAptas);
        %>
    </snk:query>

    <div class="dashboard-shell">

        <!-- ── TOPBAR ── -->
        <div class="topbar">
            <div class="topbar-left">
                <img src="https://www.skala.com.br/imagens/logo-skala-vegano.png" alt="Skala Cosméticos" class="topbar-logo">
                <div class="topbar-divider"></div>
                <span class="live-dot"></span>
                <div>
                    <div class="topbar-title">Monitoramento de Integracoes de Clientes</div>
                    <div class="topbar-sub">Base: <strong>AD_LOGINTCLIENTE</strong> &bull; Carregado em: <span id="lastRefresh">--:--:--</span></div>
                </div>
            </div>
            <div class="topbar-right">
                <span class="clock" id="clockDisplay">--:--:--</span>
                <button class="ghost-button" type="button" onclick="atualizarPagina(this)">Atualizar agora</button>
            </div>
        </div>

        <div class="dashboard-layout">
            <aside class="dashboard-sidebar">
                <div class="metric-card metric-card-strong">
                    <span class="metric-label">Clientes a integrar</span>
                    <strong class="metric-value" id="metricAptas">0</strong>
                    <small class="metric-foot">Prontos para envio ao SAP</small>
                </div>
                <div class="metric-card">
                    <span class="metric-label">Fila ativa</span>
                    <strong class="metric-value" id="metricFilaAtiva">0</strong>
                    <small class="metric-foot">Pendentes + erros + processando</small>
                </div>
                <div class="metric-card">
                    <span class="metric-label">Sucesso</span>
                    <strong class="metric-value" id="metricSucesso">0</strong>
                    <small class="metric-foot">Integracoes concluidas</small>
                </div>
                <div class="metric-card" id="metricErroCard">
                    <span class="metric-label">Erros</span>
                    <strong class="metric-value" id="metricErro">0</strong>
                    <small class="metric-foot">Ocorrencias para tratamento</small>
                </div>
                <div class="metric-card">
                    <span class="metric-label">Processando</span>
                    <strong class="metric-value" id="metricProcessando">0</strong>
                    <small class="metric-foot">Chamadas em andamento</small>
                </div>
            </aside>

            <section class="dashboard-content">
                <div class="charts-area">
                    <div class="chart-card chart-card-donut">
                        <div class="chart-card-title">Distribuicao de status</div>
                        <canvas id="statusChart" width="280" height="160"></canvas>
                    </div>
                    <div class="chart-card chart-card-timeline">
                        <div class="chart-card-title">Integracoes por hora &mdash; Hoje</div>
                        <canvas id="timelineChart" height="160"></canvas>
                    </div>
                </div>

                <div class="panel-card">
                    <div class="panel-head">
                        <div>
                            <h2>Fila de Integracoes de Clientes</h2>
                            <p>Visualizacao por abas para aproveitar melhor o espaco da tela.</p>
                        </div>
                    </div>

                    <div class="tabs-toolbar">
                        <div class="tabs-nav" id="statusTabs"></div>
                        <div class="table-search" id="tableSearchBox">
                            <input type="text" id="tabSearchInput" placeholder="Buscar na aba ativa (cod parc, parceiro, CPF/CNPJ...)" autocomplete="off">
                            <button class="ghost-button" type="button" id="tabSearchClear">Limpar</button>
                        </div>
                    </div>
                    <div class="search-info" id="tabSearchInfo"></div>
                    <div id="groupedAccordion">
                        <div class="empty-state">Carregando grupos...</div>
                    </div>
                </div>
            </section>
        </div>

        <!-- Fonte de dados: log AD_LOGINTCLIENTE (oculto) -->
        <table id="logDataSource" style="display:none">
            <tbody>
                <c:forEach items="${logsCliente.rows}" var="row">
                    <tr class="js-log-row"
                        data-idlog="<c:out value="${row.IDLOG}" />"
                        data-codparc="<c:out value="${row.CODPARC}" />"
                        data-nomeparc="<c:out value="${row.NOMEPARC}" />"
                        data-cgccpf="<c:out value="${row.CGC_CPF}" />"
                        data-dhinteg="<c:out value="${row.DHINTEG}" />"
                        data-status="<c:out value="${row.STATUS}" />"
                        data-mensagem="<c:out value="${row.MENSAGEM}" />"
                    ></tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Fonte de dados: clientes aptos (oculto) -->
        <table id="aptasDataSource" style="display:none">
            <tbody>
                <c:if test="${not empty aptasCliente.rows}">
                    <c:forEach items="${aptasCliente.rows}" var="row">
                        <tr class="js-aptas-row"
                            data-codigo="<c:out value="${row.CODIGO}" />"
                            data-nome="<c:out value="${row.NOME}" />"
                            data-cpfcnpj="<c:out value="${row.CPF_CNPJ}" />"
                            data-datacadastro="<c:out value="${row.DATA_CADASTRO}" />"
                            data-tipopessoa="<c:out value="${row.TIPO_PESSOA}" />"
                        ></tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
    </div>

    <!-- ══════════════════════════════════════════
         MODAL: DETALHE DA INTEGRACAO (inline)
    ══════════════════════════════════════════ -->
    <div class="modal-backdrop" id="backdropDetalhe" onclick="fecharModalDetalhe()">
        <div class="modal-card modal-card-large modal-inner" onclick="event.stopPropagation()">
            <div class="modal-head">
                <div class="modal-head-info">
                    <div class="modal-logo">LC</div>
                    <div>
                        <div class="modal-title">Detalhe da Integracao</div>
                        <div class="modal-sub" id="dtHeaderSub">Carregando dados do log</div>
                    </div>
                </div>
                <button class="icon-button" type="button" onclick="fecharModalDetalhe()" title="Fechar">&#10005;</button>
            </div>

            <div class="modal-body">
                <div id="dtStatusArea" class="loading">
                    <div class="spinner"></div>
                    <span>Buscando dados atuais da AD_LOGINTCLIENTE...</span>
                </div>

                <div class="detail-grid" id="dtDetailGrid" style="display:none;">
                    <div class="detail-item"><span>ID Log</span><strong id="dtDetailIdlog">-</strong></div>
                    <div class="detail-item"><span>Cod. Parceiro</span><strong id="dtDetailCodparc">-</strong></div>
                    <div class="detail-item"><span>Status</span><strong id="dtDetailStatus">-</strong></div>
                    <div class="detail-item"><span>Parceiro</span><strong id="dtDetailNomeparc">-</strong></div>
                    <div class="detail-item"><span>Data/Hora Integracao</span><strong id="dtDetailDhinteg">-</strong></div>
                </div>

                <div class="detail-observation" id="dtMessageBox" style="display:none;"></div>

                <div class="json-panels-row">
                    <div class="json-panel" id="dtJsonEnvioCard" style="display:none;">
                        <span>Json envio</span>
                        <pre id="dtJsonEnvio">-</pre>
                    </div>
                    <div class="json-panel" id="dtJsonRetornoCard" style="display:none;">
                        <span>Json retorno</span>
                        <pre id="dtJsonRetorno">-</pre>
                    </div>
                </div>
            </div>

            <div class="modal-actions">
                <button class="secondary-button" type="button" onclick="fecharModalDetalhe()">Fechar</button>
                <div style="flex:1;"></div>
                <button class="primary-button" type="button" onclick="abrirParceiro()">Abrir parceiro</button>
            </div>
        </div>
    </div>

    <!-- ══════════════════════════════════════════
         MODAL: PROCESSAMENTO DO CLIENTE (inline)
    ══════════════════════════════════════════ -->
    <div class="modal-backdrop" id="backdropReenvio" onclick="fecharModalReenvio()">
        <div class="modal-card modal-inner" onclick="event.stopPropagation()">
            <div class="modal-head">
                <div class="modal-head-info">
                    <div class="modal-logo" style="background:linear-gradient(135deg,#ff6b6b,#ffb54a);color:#1a0000;">RE</div>
                    <div>
                        <div class="modal-title">Processamento do Cliente</div>
                        <div class="modal-sub" id="rvSubTitle">Preparando execucao da action</div>
                    </div>
                </div>
                <button class="icon-button" type="button" id="rvCloseIconButton" onclick="fecharModalReenvio()" title="Fechar">&#10005;</button>
            </div>

            <div class="modal-body">
                <div class="chip-row" id="rvChipRow"></div>
                <div class="progress-track">
                    <div class="progress-bar" id="rvProgressBar" style="width:8%"></div>
                </div>
                <div class="detail-observation" id="rvResultBox">Aguardando inicio do processamento.</div>
            </div>

            <div class="modal-actions">
                <button class="secondary-button" type="button" id="rvCloseButton" onclick="fecharModalReenvio()">Fechar</button>
                <div style="flex:1;"></div>
                <button class="primary-button" type="button" id="rvRunButton" onclick="executarReenvio()">Tentar novamente</button>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var ACTION_REENVIO_ID   = '478';
        var ACTION_PROCESSAR_ID = '463';
        var DETALHE = {};
        var REENVIO = {};
        var rvTimer = null;
        var rvProgress = 8;
        var rvExecuting = false;

        /* ── Utilitarios ── */
        function normalizarTexto(v) {
            return v == null ? '' : String(v).replace(/^\s+|\s+$/g, '');
        }
        function normalizarBusca(v) {
            var s = normalizarTexto(v).toUpperCase();
            if (s && s.normalize) s = s.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
            return s;
        }
        function esc(s) {
            return String(s || '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
        }
        function escHtmlBasico(s) {
            return String(s || '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
        }
        function getCookie(n) {
            var m = document.cookie.match(new RegExp('(?:^|;)\\s*' + n + '=([^;]*)'));
            return m ? decodeURIComponent(m[1]) : '';
        }
        function pad(n) { return n < 10 ? '0' + n : String(n); }

        function formatarJsonLegivel(texto) {
            var raw = normalizarTexto(texto);
            if (!raw || raw === '-') return '-';
            var candidato = raw;
            for (var t = 0; t < 6; t++) {
                try {
                    var parsed = JSON.parse(candidato);
                    if (typeof parsed === 'string') { candidato = normalizarTexto(parsed); continue; }
                    return JSON.stringify(parsed, null, 2);
                } catch (e) {
                    var proximo = candidato
                        .replace(/&quot;/g, '"').replace(/&#34;/g, '"').replace(/&amp;/g, '&');
                    if (proximo.charAt(0) === '"' && proximo.charAt(proximo.length - 1) === '"') {
                        proximo = proximo.substring(1, proximo.length - 1);
                    }
                    proximo = proximo.replace(/\\"/g, '"').replace(/\\n/g, '\n').replace(/\\r/g, '\r').replace(/\\t/g, '\t').replace(/\\\\/g, '\\');
                    if (proximo === candidato) break;
                    candidato = proximo;
                }
            }
            return raw;
        }

        function statusClass(st) {
            var s = String(st || '').toUpperCase();
            if (s.indexOf('SUCESS') >= 0 || s.indexOf('ENVIAD') >= 0 || s.indexOf('OK') >= 0 || s.indexOf('INTEGRAD') >= 0) return 'status-success';
            if (s.indexOf('ERRO') >= 0 || s.indexOf('FALH') >= 0 || s.indexOf('REJEI') >= 0) return 'status-error';
            if (s.indexOf('PROC') >= 0 || s.indexOf('FILA') >= 0) return 'status-processing';
            return '';
        }

        /* ── Relogio ── */
        function atualizarRelogio() {
            var d = new Date();
            var el = document.getElementById('clockDisplay');
            if (el) el.textContent = pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds());
        }

        function atualizarPagina(btn) {
            if (btn) { btn.textContent = 'Carregando...'; btn.disabled = true; }
            window.location.reload();
        }

        /* ── Classificacao de status ── */
        function obterStatus(row) {
            var s = normalizarTexto(row.getAttribute('data-status'));
            return s || 'PENDENTE';
        }

        function categorizarStatus(status) {
            var s = normalizarTexto(status).toUpperCase();
            if (!s) return 'pending';
            if (s.indexOf('PROC') >= 0 || s.indexOf('FILA') >= 0) return 'processing';
            if (s.indexOf('ERRO') >= 0 || s.indexOf('FALH') >= 0 || s.indexOf('REJEI') >= 0) return 'error';
            if (s.indexOf('SUCESS') >= 0 || s.indexOf('ENVIAD') >= 0 || s.indexOf('OK') >= 0 || s.indexOf('INTEGRAD') >= 0) return 'success';
            return 'pending';
        }

        /* ── Metricas ── */
        function atualizarIndicadores() {
            var rows = document.getElementsByClassName('js-log-row');
            var sucesso = 0, erro = 0, proc = 0, pendente = 0;
            var aptas = document.querySelectorAll('#aptasDataSource .js-aptas-row').length;

            for (var i = 0; i < rows.length; i++) {
                var cat = categorizarStatus(obterStatus(rows[i]));
                if (cat === 'success') sucesso++;
                else if (cat === 'error') { erro++; rows[i].className = 'js-log-row row-has-error'; }
                else if (cat === 'processing') proc++;
                else pendente++;
            }

            document.getElementById('metricFilaAtiva').innerHTML  = erro + proc + pendente;
            document.getElementById('metricSucesso').innerHTML     = sucesso;
            document.getElementById('metricErro').innerHTML        = erro;
            document.getElementById('metricProcessando').innerHTML = proc;
            var aptasEl = document.getElementById('metricAptas');
            if (aptasEl) aptasEl.innerHTML = aptas;

            if (erro > 0) {
                var card = document.getElementById('metricErroCard');
                if (card) card.className = 'metric-card metric-card-danger metric-card-alert';
            }
            return { sucesso: sucesso, erro: erro, proc: proc, pendente: pendente, aptas: aptas };
        }

        /* ── Grafico donut (distribuicao) ── */
        function desenharDonut(counts) {
            var canvas = document.getElementById('statusChart');
            if (!canvas || !canvas.getContext) return;
            var ctx = canvas.getContext('2d');
            var W = canvas.width, H = canvas.height;
            ctx.clearRect(0, 0, W, H);

            var total = counts.sucesso + counts.erro + counts.proc + counts.pendente + (counts.aptas || 0);
            var cx = H / 2, cy = H / 2, r = H / 2 - 6, ir = r * 0.54;

            if (total === 0) {
                ctx.beginPath();
                ctx.arc(cx, cy, r, 0, Math.PI * 2);
                ctx.arc(cx, cy, ir, 0, Math.PI * 2, true);
                ctx.fillStyle = 'rgba(209,232,160,0.30)';
                ctx.fill();
                ctx.fillStyle = '#557033';
                ctx.font = '11px Poppins, sans-serif';
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                ctx.fillText('Sem dados', cx, cy);
                return;
            }

            var slices = [
                { v: counts.sucesso,    color: '#2ed07a', label: 'Sucesso'  },
                { v: counts.erro,       color: '#ff6b6b', label: 'Erro'     },
                { v: counts.proc,       color: '#62a800', label: 'Process.' },
                { v: counts.pendente,   color: '#ffb54a', label: 'Pendente' },
                { v: counts.aptas || 0, color: '#c07ffc', label: 'A integrar' }
            ];
            var angle = -Math.PI / 2;
            for (var i = 0; i < slices.length; i++) {
                if (!slices[i].v) continue;
                var arc = (slices[i].v / total) * Math.PI * 2;
                ctx.beginPath();
                ctx.arc(cx, cy, r, angle, angle + arc);
                ctx.arc(cx, cy, ir, angle + arc, angle, true);
                ctx.closePath();
                ctx.fillStyle = slices[i].color;
                ctx.fill();
                angle += arc;
            }
            ctx.fillStyle = '#1a2c08';
            ctx.font = 'bold 17px Poppins, sans-serif';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            ctx.fillText(String(total), cx, cy - 7);
            ctx.font = '10px Poppins, sans-serif';
            ctx.fillStyle = '#557033';
            ctx.fillText('total', cx, cy + 9);

            var ativos = [];
            for (var i = 0; i < slices.length; i++) if (slices[i].v) ativos.push(slices[i]);

            var lineH = 26;
            var legendH = ativos.length * lineH;
            var lx = H + 12;
            var ly = (H - legendH) / 2 + (lineH / 2);

            ctx.font = '13px Poppins, sans-serif';
            ctx.textBaseline = 'middle';
            ctx.textAlign = 'left';
            for (var i = 0; i < ativos.length; i++) {
                ctx.fillStyle = ativos[i].color;
                ctx.fillRect(lx, ly - 5, 10, 10);
                ctx.fillStyle = '#557033';
                ctx.fillText(ativos[i].label + ' (' + ativos[i].v + ')', lx + 16, ly);
                ly += lineH;
            }
        }

        /* ── Grafico de barras por hora ── */
        function desenharTimeline() {
            var canvas = document.getElementById('timelineChart');
            if (!canvas || !canvas.getContext) return;

            var containerW = 0;
            if (canvas.parentElement && canvas.parentElement.clientWidth) {
                containerW = canvas.parentElement.clientWidth - 32;
            }
            if (!containerW || containerW < 200) {
                var rect = canvas.getBoundingClientRect();
                if (rect && rect.width) containerW = rect.width;
            }
            if (containerW > 200) {
                var nextW = Math.floor(containerW);
                if (canvas.width !== nextW) canvas.width = nextW;
            }

            var ctx = canvas.getContext('2d');
            var W = canvas.width, H = canvas.height;
            ctx.clearRect(0, 0, W, H);

            var rows = document.querySelectorAll('#logDataSource .js-log-row');
            var bkts = {};
            for (var h = 0; h < 24; h++) bkts[h] = 0;

            for (var i = 0; i < rows.length; i++) {
                var dh = normalizarTexto(rows[i].getAttribute('data-dhinteg'));
                var sp = dh.indexOf(' ');
                if (sp < 0) continue;
                var hr = parseInt(dh.substring(sp + 1).split(':')[0], 10);
                if (!isNaN(hr) && hr >= 0 && hr < 24) bkts[hr]++;
            }

            var max = 1;
            for (var h = 0; h < 24; h++) if (bkts[h] > max) max = bkts[h];

            var totalPeriodo = 0;
            for (var h = 0; h < 24; h++) totalPeriodo += bkts[h];
            if (totalPeriodo === 0) {
                ctx.fillStyle = '#557033';
                ctx.font = '12px Poppins, sans-serif';
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                ctx.fillText('Nenhum registro encontrado no periodo selecionado', W / 2, H / 2);
                return;
            }

            var pl = 4, pr = 4, pt = 22, pb = 22;
            var cW = W - pl - pr, cH = H - pt - pb;
            var bw = cW / 24;
            var now = new Date().getHours();

            ctx.strokeStyle = 'rgba(80,148,20,0.15)';
            ctx.lineWidth = 1;
            ctx.beginPath();
            ctx.moveTo(pl, H - pb);
            ctx.lineTo(W - pr, H - pb);
            ctx.stroke();

            var pontos = [];
            for (var hr = 0; hr < 24; hr++) {
                var bh = (bkts[hr] / max) * cH;
                var x = pl + hr * bw + (bw / 2);
                var y = H - pb - bh;
                pontos.push({ h: hr, x: x, y: y, v: bkts[hr] });
            }

            var areaGrad = ctx.createLinearGradient(0, pt, 0, H - pb);
            areaGrad.addColorStop(0, 'rgba(98,168,0,0.40)');
            areaGrad.addColorStop(1, 'rgba(98,168,0,0.06)');

            ctx.beginPath();
            ctx.moveTo(pontos[0].x, H - pb);
            ctx.lineTo(pontos[0].x, pontos[0].y);
            for (var i = 0; i < pontos.length - 1; i++) {
                var xc = (pontos[i].x + pontos[i + 1].x) / 2;
                var yc = (pontos[i].y + pontos[i + 1].y) / 2;
                ctx.quadraticCurveTo(pontos[i].x, pontos[i].y, xc, yc);
            }
            ctx.quadraticCurveTo(pontos[pontos.length - 2].x, pontos[pontos.length - 2].y, pontos[pontos.length - 1].x, pontos[pontos.length - 1].y);
            ctx.lineTo(pontos[pontos.length - 1].x, H - pb);
            ctx.closePath();
            ctx.fillStyle = areaGrad;
            ctx.fill();

            ctx.beginPath();
            ctx.moveTo(pontos[0].x, pontos[0].y);
            for (var i = 0; i < pontos.length - 1; i++) {
                var xcl = (pontos[i].x + pontos[i + 1].x) / 2;
                var ycl = (pontos[i].y + pontos[i + 1].y) / 2;
                ctx.quadraticCurveTo(pontos[i].x, pontos[i].y, xcl, ycl);
            }
            ctx.quadraticCurveTo(pontos[pontos.length - 2].x, pontos[pontos.length - 2].y, pontos[pontos.length - 1].x, pontos[pontos.length - 1].y);
            ctx.strokeStyle = 'rgba(98,168,0,0.95)';
            ctx.lineWidth = 2;
            ctx.stroke();

            for (var i = 0; i < pontos.length; i++) {
                var p = pontos[i];
                var isNow = p.h === now;
                if (p.v > 0) {
                    ctx.beginPath();
                    ctx.arc(p.x, p.y, isNow ? 3.4 : 2.6, 0, Math.PI * 2);
                    ctx.fillStyle = isNow ? '#1a2c08' : '#62a800';
                    ctx.fill();
                    ctx.fillStyle = isNow ? '#1a2c08' : '#557033';
                    ctx.font = 'bold 10px Poppins, sans-serif';
                    ctx.textAlign = 'center';
                    ctx.textBaseline = 'bottom';
                    ctx.fillText(String(p.v), p.x, p.y - 3);
                }
            }

            ctx.fillStyle = '#557033';
            ctx.font = '10px Poppins, sans-serif';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'top';
            for (var hr = 0; hr < 24; hr += 2) {
                var lx = pl + hr * bw + (bw / 2);
                ctx.fillText(hr + 'h', lx, H - pb + 5);
            }
        }

        /* ── Coletar dados da linha de log ── */
        function coletarDadosLinha(row) {
            return {
                idlog:    normalizarTexto(row.getAttribute('data-idlog')),
                codparc:  normalizarTexto(row.getAttribute('data-codparc')),
                nomeparc: normalizarTexto(row.getAttribute('data-nomeparc')),
                cgccpf:   normalizarTexto(row.getAttribute('data-cgccpf')),
                dhinteg:  normalizarTexto(row.getAttribute('data-dhinteg')),
                status:   obterStatus(row),
                mensagem: normalizarTexto(row.getAttribute('data-mensagem'))
            };
        }

        /* ── Modal Detalhe ── */
        function abrirModalDetalhe(item) {
            DETALHE = item;

            document.getElementById('dtHeaderSub').innerHTML =
                'ID Log <strong>' + esc(item.idlog || '-') + '</strong>' +
                ' &bull; CODPARC <strong>' + esc(item.codparc || '-') + '</strong>' +
                ' &bull; <span class="' + statusClass(item.status) + '">' + esc(item.status || '-') + '</span>';

            document.getElementById('dtStatusArea').style.display = 'flex';
            document.getElementById('dtDetailGrid').style.display = 'none';
            document.getElementById('dtMessageBox').style.display = 'none';
            document.getElementById('dtJsonEnvioCard').style.display   = 'none';
            document.getElementById('dtJsonRetornoCard').style.display = 'none';

            document.getElementById('backdropDetalhe').classList.add('is-visible');
            carregarLogDetalhe(item.idlog);
        }

        function fecharModalDetalhe() {
            document.getElementById('backdropDetalhe').classList.remove('is-visible');
        }

        function extrairPrimeiroRegistro(resp) {
            try {
                var entities = resp && resp.responseBody && resp.responseBody.entities;
                if (!entities) return null;
                var meta  = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
                var names = Array.isArray(meta) ? meta.map(function(f) { return f.name || ''; }) : (meta && meta.name ? [meta.name] : []);
                var ent   = entities.entity;
                if (!ent) return null;
                var row   = Array.isArray(ent) ? ent[0] : ent;
                var obj   = {};
                names.forEach(function(n, i) { var c = row['f' + i]; obj[n] = c ? (c.$ || '') : ''; });
                return obj;
            } catch (e) { return null; }
        }

        function carregarLogDetalhe(idlog) {
            var sid  = getCookie('JSESSIONID').replace('.master', '');
            var url  = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession=' + sid;
            var body = {
                serviceName:  'CRUDServiceProvider.loadRecords',
                requestBody: { dataSet: {
                    rootEntity:                'AD_LOGINTCLIENTE',
                    includePresentationFields: 'N',
                    offsetPage:                '0',
                    criteria:    { expression: { $: 'this.IDLOG = ' + Number(idlog || 0) } },
                    entity:      { fieldset:    { list: 'IDLOG,CODPARC,DHINTEG,STATUS,MENSAGEM,JSON_ENVIO,JSON_RETORNO' } }
                }}
            };
            fetch(url, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) })
                .then(function(r) { return r.json(); })
                .then(function(resp) {
                    var row = extrairPrimeiroRegistro(resp);
                    document.getElementById('dtStatusArea').style.display = 'none';
                    document.getElementById('dtDetailGrid').style.display = 'grid';

                    if (!row) {
                        document.getElementById('dtMessageBox').style.display = 'block';
                        document.getElementById('dtMessageBox').innerHTML = 'Nao foi possivel localizar o registro na AD_LOGINTCLIENTE pelo ID informado.';
                        return;
                    }

                    document.getElementById('dtHeaderSub').textContent  = 'ID Log ' + (row.IDLOG || DETALHE.idlog || '-') + ' | CODPARC ' + (row.CODPARC || DETALHE.codparc || '-');
                    document.getElementById('dtDetailIdlog').innerHTML   = esc(row.IDLOG   || DETALHE.idlog   || '-');
                    document.getElementById('dtDetailCodparc').innerHTML = esc(row.CODPARC || DETALHE.codparc || '-');
                    document.getElementById('dtDetailStatus').innerHTML  = '<span class="' + statusClass(row.STATUS || DETALHE.status) + '">' + esc(row.STATUS || DETALHE.status || '-') + '</span>';
                    document.getElementById('dtDetailNomeparc').innerHTML = esc(DETALHE.nomeparc || '-');
                    document.getElementById('dtDetailDhinteg').innerHTML  = esc(row.DHINTEG || DETALHE.dhinteg || '-');

                    if (row.MENSAGEM) {
                        document.getElementById('dtMessageBox').style.display = 'block';
                        document.getElementById('dtMessageBox').textContent = row.MENSAGEM;
                    }
                    if (row.JSON_ENVIO && row.JSON_ENVIO !== '-') {
                        document.getElementById('dtJsonEnvioCard').style.display = 'block';
                        document.getElementById('dtJsonEnvio').textContent = formatarJsonLegivel(row.JSON_ENVIO);
                    }
                    if (row.JSON_RETORNO && row.JSON_RETORNO !== '-') {
                        document.getElementById('dtJsonRetornoCard').style.display = 'block';
                        var retornoTexto = (row.STATUS === 'SUCESSO') ? formatarJsonLegivel(row.JSON_RETORNO) : row.JSON_RETORNO;
                        document.getElementById('dtJsonRetorno').textContent = retornoTexto;
                    }
                })
                .catch(function(err) {
                    document.getElementById('dtStatusArea').innerHTML = '<span>Erro ao carregar: ' + esc(err && err.message ? err.message : String(err)) + '</span>';
                });
        }

        function abrirParceiro() {
            if (!DETALHE.codparc) { alert('CODPARC nao informado para abertura do cadastro de parceiro.'); return; }
            fecharModalDetalhe();
            openApp('br.com.sankhya.core.cad.parceiros', { CODPARC: parseInt(DETALHE.codparc, 10) });
        }

        /* ── Modal Processamento ── */
        function abrirModalReenvio(item) {
            REENVIO = { idlog: item.idlog, codparc: item.codparc, status: item.status, actionId: item.actionId || ACTION_REENVIO_ID };

            document.getElementById('rvChipRow').innerHTML =
                '<div class="chip">ID Log <strong>' + esc(item.idlog   || '-') + '</strong></div>' +
                '<div class="chip">CODPARC <strong>' + esc(item.codparc || '-') + '</strong></div>' +
                '<div class="chip">Status <strong>'  + esc(item.status  || '-') + '</strong></div>' +
                '<div class="chip">Action <strong>'  + esc(REENVIO.actionId || '-') + '</strong></div>';

            document.getElementById('rvSubTitle').textContent  = 'Preparando execucao da action';
            document.getElementById('rvResultBox').textContent = 'Aguardando inicio do processamento.';
            setEstadoExecucaoReenvio(false);

            var bar = document.getElementById('rvProgressBar');
            bar.className   = 'progress-bar';
            bar.style.width = '8%';

            document.getElementById('backdropReenvio').classList.add('is-visible');
            executarReenvio();
        }

        function setEstadoExecucaoReenvio(emExecucao) {
            rvExecuting = !!emExecucao;
            var runBtn       = document.getElementById('rvRunButton');
            var closeBtn     = document.getElementById('rvCloseButton');
            var closeIconBtn = document.getElementById('rvCloseIconButton');
            if (runBtn) {
                runBtn.disabled    = rvExecuting;
                runBtn.textContent = rvExecuting ? 'Processando...' : 'Tentar novamente';
            }
            if (closeBtn) closeBtn.disabled = rvExecuting;
            if (closeIconBtn) closeIconBtn.disabled = rvExecuting;
        }

        function fecharModalReenvio() {
            if (rvExecuting) return;
            if (rvTimer) { clearInterval(rvTimer); rvTimer = null; }
            document.getElementById('backdropReenvio').classList.remove('is-visible');
        }

        function startReenvioProgress() {
            var bar = document.getElementById('rvProgressBar');
            if (rvTimer) clearInterval(rvTimer);
            rvProgress = 8;
            bar.className   = 'progress-bar';
            bar.style.width = rvProgress + '%';
            rvTimer = setInterval(function() {
                if (rvProgress < 90) { rvProgress += 6; bar.style.width = rvProgress + '%'; }
            }, 220);
        }

        function finishReenvioProgress(cls, msg) {
            var bar = document.getElementById('rvProgressBar');
            if (rvTimer) { clearInterval(rvTimer); rvTimer = null; }
            bar.className   = 'progress-bar ' + (cls === 'ok' ? 'is-success' : 'is-error');
            bar.style.width = '100%';
            document.getElementById('rvResultBox').innerHTML = msg;
            if (cls === 'ok') {
                document.getElementById('rvResultBox').innerHTML += '<br><small style="opacity:.7">Concluido. Fechando modal...</small>';
                setTimeout(function() { rvExecuting = false; fecharModalReenvio(); }, 500);
                setTimeout(function() { atualizarPagina(null); }, 900);
                return;
            }
            setEstadoExecucaoReenvio(false);
        }

        function executarReenvio() {
            if (rvExecuting) return;
            setEstadoExecucaoReenvio(true);
            document.getElementById('rvSubTitle').textContent = 'Executando ActionButtonsSP.executeJava (Action ' + esc(REENVIO.actionId || '-') + ')';
            document.getElementById('rvResultBox').innerHTML  = 'Execucao iniciada para CODPARC ' + esc(REENVIO.codparc || '-') + '. Aguarde o retorno da action.';
            startReenvioProgress();

            if (!REENVIO.actionId) {
                finishReenvioProgress('err', 'Action nao configurada. Verifique ACTION_REENVIO_ID / ACTION_PROCESSAR_ID no dashboard.');
                return;
            }

            var sid  = getCookie('JSESSIONID').replace('.master', '');
            var url  = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession=' + sid;
            var body = {
                serviceName:  'ActionButtonsSP.executeJava',
                requestBody: { javaCall: {
                    actionID:    String(REENVIO.actionId),
                    refreshType: 'SEL',
                    rows: { row: [{ field: REENVIO.idlog
                        ? [
                            { fieldName: 'IDLOG',   $: String(REENVIO.idlog) },
                            { fieldName: 'CODPARC', $: String(REENVIO.codparc || '') }
                          ]
                        : [
                            { fieldName: 'CODPARC', $: String(REENVIO.codparc || '') }
                          ]
                    }]}
                }}
            };
            fetch(url, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) })
                .then(function(r) { return r.json(); })
                .then(function(resp) {
                    var status = resp && resp.status;
                    var msg    = (resp && (resp.statusMessage || resp.message)) || 'Action executada.';
                    if (String(status) === '1' || String(status) === '2' || msg) {
                        finishReenvioProgress('ok', 'Processamento concluido. Retorno: ' + esc(msg));
                    } else {
                        finishReenvioProgress('err', 'Resposta inesperada: ' + esc(JSON.stringify(resp)));
                    }
                })
                .catch(function(err) {
                    finishReenvioProgress('err', 'Falha no processamento: ' + esc(err && err.message ? err.message : String(err)));
                });
        }

        /* ── Abas: grupos por status ── */
        var _groupItems = {};

        function ativarAba(cat) {
            var btns   = document.querySelectorAll('.tab-button');
            var panels = document.querySelectorAll('.tab-panel');
            for (var i = 0; i < btns.length; i++)   btns[i].classList.toggle('is-active',   btns[i].getAttribute('data-tab') === cat);
            for (var p = 0; p < panels.length; p++) panels[p].classList.toggle('is-active', panels[p].getAttribute('data-tab') === cat);
            aplicarBuscaAbaAtiva();
        }

        function montarIndiceBusca(item) {
            var partes = [];
            for (var k in item) {
                if (!item.hasOwnProperty(k)) continue;
                if (k === 'key') continue;
                partes.push(normalizarTexto(item[k]));
            }
            return normalizarBusca(partes.join(' '));
        }

        function atualizarInfoBusca(visiveis, total) {
            var info = document.getElementById('tabSearchInfo');
            if (!info) return;
            if (total === 0) { info.textContent = ''; return; }
            info.textContent = visiveis + ' de ' + total + ' registros exibidos';
        }

        function aplicarBuscaAbaAtiva() {
            var input = document.getElementById('tabSearchInput');
            var panel = document.querySelector('.tab-panel.is-active');
            if (!panel) { atualizarInfoBusca(0, 0); return; }

            var termoDigitado = input ? input.value : '';
            var termo = normalizarBusca(termoDigitado);
            var rows = panel.querySelectorAll('tbody tr');
            var total = rows.length;
            var visiveis = 0;

            for (var i = 0; i < rows.length; i++) {
                var idx = normalizarBusca(rows[i].getAttribute('data-search'));
                var ok = !termo || idx.indexOf(termo) >= 0;
                rows[i].style.display = ok ? '' : 'none';
                if (ok) visiveis++;
            }

            var semResultado = panel.querySelector('.tab-search-empty');
            if (!semResultado) {
                semResultado = document.createElement('div');
                semResultado.className = 'empty-state tab-search-empty';
                semResultado.style.display = 'none';
                panel.appendChild(semResultado);
            }
            if (total > 0 && visiveis === 0 && termo) {
                semResultado.textContent = 'Nenhum registro encontrado para "' + termoDigitado + '" na aba ativa.';
                semResultado.style.display = 'block';
            } else {
                semResultado.style.display = 'none';
            }

            atualizarInfoBusca(visiveis, total);
        }

        function iniciarBuscaAbas() {
            var input = document.getElementById('tabSearchInput');
            var clear = document.getElementById('tabSearchClear');
            if (input) {
                input.addEventListener('input', aplicarBuscaAbaAtiva);
                input.addEventListener('keydown', function(e) {
                    if (e.key === 'Escape') {
                        input.value = '';
                        aplicarBuscaAbaAtiva();
                    }
                });
            }
            if (clear) {
                clear.addEventListener('click', function() {
                    if (input) input.value = '';
                    aplicarBuscaAbaAtiva();
                    if (input) input.focus();
                });
            }
        }

        function montarTabelaAptas(items) {
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th>Codigo</th><th>Nome</th><th>CPF/CNPJ</th><th>Data Cadastro</th><th>Tipo</th><th>Acoes</th>';
            html += '</tr></thead><tbody>';
            for (var a = 0; a < items.length; a++) {
                var ai = items[a];
                html += '<tr data-search="' + esc(montarIndiceBusca(ai)) + '">';
                html += '<td>' + esc(ai.codigo) + '</td>';
                html += '<td>' + esc(ai.nome) + '</td>';
                html += '<td>' + esc(ai.cpfcnpj) + '</td>';
                html += '<td>' + esc(ai.datacadastro) + '</td>';
                html += '<td>' + esc(ai.tipopessoa) + '</td>';
                html += '<td><div class="action-group">';
                html += '<button class="secondary-button" type="button" onclick="abrirParceiroApta(\'' + ai.key + '\')">Ver parceiro</button>';
                html += '<button class="primary-button" type="button" onclick="abrirProcessarAptaGrupo(\'' + ai.key + '\')">Processar</button>';
                html += '</div></td></tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }

        function montarTabelaLogs(items, categoriaErro, exibirCgcCpf) {
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th>ID Log</th><th>Cod. Parc.</th><th>Parceiro</th>';
            if (exibirCgcCpf) html += '<th>CGC_CPF</th>';
            html += '<th>Data/Hora</th><th>Mensagem</th><th>Acoes</th>';
            html += '</tr></thead><tbody>';
            for (var j = 0; j < items.length; j++) {
                var item = items[j];
                var rowCls = categoriaErro ? ' class="row-has-error"' : '';
                html += '<tr' + rowCls + ' data-search="' + esc(montarIndiceBusca(item)) + '">';
                html += '<td>' + esc(item.idlog) + '</td>';
                html += '<td>' + esc(item.codparc) + '</td>';
                html += '<td><button class="link-button" type="button" onclick="abrirDetalheGrupo(\'' + item.key + '\')">' + esc(item.nomeparc || item.codparc) + '</button></td>';
                if (exibirCgcCpf) html += '<td>' + esc(item.cgccpf) + '</td>';
                html += '<td>' + esc(item.dhinteg) + '</td>';
                html += '<td class="message-cell">' + esc(item.mensagem) + '</td>';
                html += '<td><div class="action-group">';
                html += '<button class="secondary-button" type="button" onclick="abrirDetalheGrupo(\'' + item.key + '\')">Detalhes</button>';
                html += '<button class="primary-button" type="button" onclick="abrirReenvioGrupo(\'' + item.key + '\')">Reenviar</button>';
                html += '</div></td></tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }

        function abrirDetalheGrupo(key)      { var item = _groupItems[key]; if (item) abrirModalDetalhe(item); }
        function abrirReenvioGrupo(key)      { var item = _groupItems[key]; if (item) abrirModalReenvio(item); }

        function abrirProcessarAptaGrupo(key) {
            var item = _groupItems[key];
            if (!item) return;
            if (!ACTION_PROCESSAR_ID) { alert('ACTION_PROCESSAR_ID nao configurada. Informe o ID da action de processamento no dashboard.'); return; }
            abrirModalReenvio({ idlog: '', codparc: item.codigo, status: 'APTA', actionId: ACTION_PROCESSAR_ID });
        }

        function abrirParceiroApta(key) {
            var item = _groupItems[key];
            if (item && item.codigo) openApp('br.com.sankhya.core.cad.parceiros', { CODPARC: parseInt(item.codigo, 10) });
        }

        function construirTabelaAgrupada() {
            var accordion = document.getElementById('groupedAccordion');
            if (!accordion) return;

            var ORDER_META = [
                { cat: 'aptas',      label: 'A integrar',            cls: 'status-processing', open: true,  isAptas: true  },
                { cat: 'error',      label: 'Erro / Rejeitado',       cls: 'status-error',      open: true,  isAptas: false },
                { cat: 'processing', label: 'Processando / Em fila',  cls: 'status-processing', open: true,  isAptas: false },
                { cat: 'pending',    label: 'Pendente',               cls: '',                  open: false, isAptas: false },
                { cat: 'success',    label: 'Sucesso',                cls: 'status-success',    open: false, isAptas: false }
            ];

            var logRows = document.querySelectorAll('#logDataSource .js-log-row');
            var groups  = { error: [], processing: [], pending: [], success: [] };
            for (var i = 0; i < logRows.length; i++) {
                var item = coletarDadosLinha(logRows[i]);
                var cat  = categorizarStatus(item.status);
                if (groups[cat]) groups[cat].push(item);
            }

            var aptasRows  = document.querySelectorAll('#aptasDataSource .js-aptas-row');
            var aptasItems = [];
            for (var a = 0; a < aptasRows.length; a++) {
                var ar   = aptasRows[a];
                var aKey = 'apta_' + a;
                _groupItems[aKey] = {
                    codigo:       ar.getAttribute('data-codigo')       || '',
                    nome:         ar.getAttribute('data-nome')         || '',
                    cpfcnpj:      ar.getAttribute('data-cpfcnpj')      || '',
                    datacadastro: ar.getAttribute('data-datacadastro') || '',
                    tipopessoa:   ar.getAttribute('data-tipopessoa')   || ''
                };
                _groupItems[aKey].key = aKey;
                aptasItems.push(_groupItems[aKey]);
            }

            var tabsHtml    = '';
            var contentHtml = '';
            var hasTab      = false;
            for (var m = 0; m < ORDER_META.length; m++) {
                var meta  = ORDER_META[m];
                var items = meta.isAptas ? aptasItems : groups[meta.cat];
                if (!items || !items.length) continue;

                hasTab = true;
                var activeCls = tabsHtml ? '' : ' is-active';
                tabsHtml += '<button class="tab-button' + activeCls + '" data-tab="' + meta.cat + '" type="button" onclick="ativarAba(\'' + meta.cat + '\')">';
                tabsHtml += '<span class="status-badge ' + meta.cls + '">' + meta.label + '</span>';
                tabsHtml += '<span class="tab-count">' + items.length + '</span>';
                tabsHtml += '</button>';

                if (!meta.isAptas) {
                    for (var j = 0; j < items.length; j++) {
                        var key = meta.cat + '_' + j;
                        _groupItems[key]     = items[j];
                        _groupItems[key].key = key;
                    }
                }

                contentHtml += '<div class="tab-panel' + activeCls + '" data-tab="' + meta.cat + '">';
                contentHtml += meta.isAptas ? montarTabelaAptas(items) : montarTabelaLogs(items, meta.cat === 'error', meta.cat === 'error' || meta.cat === 'success');
                contentHtml += '</div>';
            }

            document.getElementById('statusTabs').innerHTML = tabsHtml;
            accordion.innerHTML = hasTab ? contentHtml : '<div class="empty-state">Nenhum registro localizado em AD_LOGINTCLIENTE.</div>';
            aplicarBuscaAbaAtiva();
        }

        var AUTO_REFRESH_MINUTES = 60;
        function iniciarAutoRefresh() {
            var ms = AUTO_REFRESH_MINUTES * 60 * 1000;
            if (!ms || ms < 1000) return;
            setInterval(function() { window.location.reload(); }, ms);
        }

        /* ── Inicializacao ── */
        var _counts = atualizarIndicadores();
        desenharDonut(_counts);
        desenharTimeline();
        construirTabelaAgrupada();
        iniciarBuscaAbas();
        iniciarAutoRefresh();
        atualizarRelogio();
        setInterval(atualizarRelogio, 1000);

        setTimeout(desenharTimeline, 120);
        if (window.requestAnimationFrame) {
            window.requestAnimationFrame(function() { desenharTimeline(); });
        }
        window.addEventListener('resize', function() { desenharTimeline(); });

        (function() {
            var el = document.getElementById('lastRefresh');
            if (el) {
                var d = new Date();
                el.textContent = pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds());
            }
        })();
    </script>
</body>
</html>
