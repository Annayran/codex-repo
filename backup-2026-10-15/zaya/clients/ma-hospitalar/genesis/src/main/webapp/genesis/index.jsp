<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Genesis - MA Hospitalar</title>
    <link rel="stylesheet" type="text/css" href="${BASE_FOLDER}/css/genesis-integracao.css">
    <link rel="stylesheet" type="text/css" href="css/genesis-integracao.css">
    <style data-inline-design-system="true">
:root {
    color-scheme: light;
    --bg: #f5f7fa;
    --surface: #ffffff;
    --line: #d9e0e8;
    --text: #1f2933;
    --muted: #607080;
    --accent: #0f766e;
    --accent-dark: #0b5f59;
    --danger: #b42318;
    --warning: #a15c07;
    --ok: #157f3b;
    --blue: #1d4ed8;
    --purple: #6d28d9;
}

* { box-sizing: border-box; }

body {
    margin: 0;
    background: var(--bg);
    color: var(--text);
    font-family: Arial, Helvetica, sans-serif;
    font-size: 14px;
}

.page-shell {
    width: min(1480px, calc(100% - 32px));
    margin: 0 auto;
    padding: 24px 0 40px;
}

.page-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 16px;
    margin-bottom: 18px;
}

h1, h2, p { margin: 0; }

h1 { font-size: 24px; font-weight: 700; }
h2 { font-size: 16px; font-weight: 700; }

.page-header p,
.summary-card span,
.table-header span,
label,
.breakdown-label { color: var(--muted); }

button, input, select { font: inherit; }

.primary-button,
.secondary-button,
.icon-button {
    min-height: 36px;
    border: 1px solid transparent;
    border-radius: 6px;
    cursor: pointer;
}

.primary-button { background: var(--accent); color: #fff; padding: 0 14px; }
.primary-button:hover { background: var(--accent-dark); }
.secondary-button,
.icon-button { background: #fff; border-color: var(--line); color: var(--text); padding: 0 12px; }
.secondary-button:hover,
.icon-button:hover { border-color: var(--accent); }

.summary-grid {
    display: grid;
    grid-template-columns: repeat(6, minmax(0, 1fr));
    gap: 12px;
    margin-bottom: 16px;
}

.summary-card,
.analysis-panel,
.filter-bar,
.table-section {
    background: var(--surface);
    border: 1px solid var(--line);
    border-radius: 8px;
}

.summary-card { padding: 16px; border-left-width: 4px; }
.summary-card strong { display: block; margin-top: 8px; font-size: 26px; }
.summary-card.entrada { border-left-color: var(--ok); }
.summary-card.saida { border-left-color: var(--danger); }
.summary-card.estoque { border-left-color: var(--blue); }
.summary-card.enviado { border-left-color: var(--accent); }
.summary-card.pendente { border-left-color: var(--warning); }
.summary-card.erro { border-left-color: var(--danger); }

.analysis-grid {
    display: grid;
    grid-template-columns: repeat(3, minmax(0, 1fr));
    gap: 12px;
    margin-bottom: 16px;
}

.analysis-panel { padding: 14px; }
.breakdown-list { display: grid; gap: 10px; margin-top: 12px; }
.breakdown-row { display: grid; grid-template-columns: 130px 1fr 48px; gap: 10px; align-items: center; }
.breakdown-bar { height: 8px; background: #eef2f7; border-radius: 999px; overflow: hidden; }
.breakdown-fill { height: 100%; width: 0; background: var(--accent); }
.breakdown-count { text-align: right; font-weight: 700; }

.filter-bar {
    display: grid;
    grid-template-columns: 150px 160px 130px 160px 1fr auto;
    gap: 12px;
    align-items: end;
    padding: 14px;
    margin-bottom: 16px;
}

label { display: grid; gap: 6px; font-size: 12px; font-weight: 700; }
input, select {
    width: 100%;
    min-height: 36px;
    border: 1px solid var(--line);
    border-radius: 6px;
    padding: 0 10px;
    color: var(--text);
    background: #fff;
}

.table-section { overflow: hidden; }
.table-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 12px;
    padding: 14px 16px;
    border-bottom: 1px solid var(--line);
}
.table-scroll { overflow: auto; }
table { width: 100%; min-width: 1320px; border-collapse: collapse; }
th, td { padding: 10px 12px; border-bottom: 1px solid var(--line); text-align: left; vertical-align: middle; }
th { background: #f8fafc; color: var(--muted); font-size: 12px; text-transform: uppercase; }

.status-pill,
.type-pill {
    display: inline-flex;
    align-items: center;
    min-height: 24px;
    border-radius: 999px;
    padding: 0 9px;
    font-size: 12px;
    font-weight: 700;
    background: #edf2f7;
}
.type-ENTRADA { color: var(--ok); background: #e8f7ee; }
.type-SAIDA { color: var(--danger); background: #fdecec; }
.type-A_CONFIRMAR { color: var(--warning); background: #fff3dc; }
.status-ENVIADO { color: var(--ok); background: #e8f7ee; }
.status-ERRO { color: var(--danger); background: #fdecec; }
.status-REPROCESSAR { color: var(--warning); background: #fff3dc; }
.status-PENDENTE { color: var(--blue); background: #eaf1ff; }

.empty-state { color: var(--muted); text-align: center; padding: 28px 12px; }
.nowrap { white-space: nowrap; }
.product-cell { max-width: 320px; white-space: normal; }

dialog { width: min(720px, calc(100% - 32px)); border: 1px solid var(--line); border-radius: 8px; padding: 0; }
dialog form { display: grid; gap: 14px; padding: 18px; }
pre { max-height: 360px; overflow: auto; margin: 0; padding: 12px; background: #f8fafc; border: 1px solid var(--line); border-radius: 6px; white-space: pre-wrap; }

@media (max-width: 1100px) {
    .summary-grid { grid-template-columns: repeat(3, minmax(0, 1fr)); }
    .analysis-grid { grid-template-columns: 1fr; }
    .filter-bar { grid-template-columns: 1fr 1fr; }
}

@media (max-width: 700px) {
    .summary-grid, .filter-bar { grid-template-columns: 1fr; }
    .page-header { align-items: flex-start; flex-direction: column; }
}
.data-source { display: none; }
.error-button { min-height: 28px; padding: 0 10px; border-radius: 6px; border: 1px solid var(--line); background: #fff; color: var(--danger); cursor: pointer; font-size: 12px; font-weight: 700; }
.error-button:hover { border-color: var(--danger); }
.table-row-muted { opacity: 0.72; }
    </style>
    <snk:load/>
</head>
<body>
    <snk:query var="movimentosGenesis">
        <%
            String queryMovimentos =
                "SELECT " +
                "    V.NUNOTA, " +
                "    V.NUMNOTA, " +
                "    V.CODTIPOPER, " +
                "    V.SEQUENCIA, " +
                "    V.CODPROD, " +
                "    V.PRODUTO, " +
                "    V.GRUPO_PRODUTO, " +
                "    V.SERIE, " +
                "    V.TIPO, " +
                "    V.PODER, " +
                "    V.DESCRLOCAL, " +
                "    V.STATUS_ESTOQUE, " +
                "    V.STATUS_ESTOQUE_DESC, " +
                "    V.VLRUNIT, " +
                "    V.CHAVE_MOVIMENTO, " +
                "    NVL(M.STATUS_INTEGRACAO, 'PENDENTE') AS STATUS_INTEGRACAO, " +
                "    M.HTTP_STATUS, " +
                "    M.MENSAGEM_ERRO, " +
                "    NVL(M.TENTATIVAS, 0) AS TENTATIVAS, " +
                "    M.DH_ENVIO, " +
                "    M.DH_ATUALIZACAO " +
                "FROM AD_VW_GENESIS_ESTOQUE V " +
                "LEFT JOIN AD_GENMOVEST M ON M.CHAVE_MOVIMENTO = V.CHAVE_MOVIMENTO " +
                "WHERE V.SERIE IS NOT NULL " +
                "ORDER BY V.NUNOTA DESC, V.SEQUENCIA, V.SERIE";
            out.println(queryMovimentos);
        %>
    </snk:query>

<main class="page-shell">
    <header class="page-header">
        <div>
            <h1>Gest&atilde;o Estoque por S&eacute;rie</h1>
            <p>Vis&atilde;o anal&iacute;tica da view Genesis cruzada com a tabela de integra&ccedil;&atilde;o.</p>
        </div>
        <button id="refreshButton" class="primary-button" type="button">Atualizar</button>
    </header>

    <section class="summary-grid" aria-label="Resumo analitico">
        <article class="summary-card entrada"><span>Entradas</span><strong id="countEntradas">0</strong></article>
        <article class="summary-card saida"><span>Sa&iacute;das</span><strong id="countSaidas">0</strong></article>
        <article class="summary-card estoque"><span>S&eacute;ries / Estoque</span><strong id="countSeries">0</strong></article>
        <article class="summary-card enviado"><span>Enviados Genesis</span><strong id="countEnviado">0</strong></article>
        <article class="summary-card pendente"><span>Pendentes</span><strong id="countPendente">0</strong></article>
        <article class="summary-card erro"><span>Erros</span><strong id="countErro">0</strong></article>
    </section>

    <section class="analysis-grid" aria-label="Distribuicoes analiticas">
        <article class="analysis-panel"><h2>Movimento</h2><div id="tipoBreakdown" class="breakdown-list"></div></article>
        <article class="analysis-panel"><h2>Status Estoque</h2><div id="estoqueBreakdown" class="breakdown-list"></div></article>
        <article class="analysis-panel"><h2>Status Integra&ccedil;&atilde;o</h2><div id="integracaoBreakdown" class="breakdown-list"></div></article>
    </section>

    <section class="filter-bar" aria-label="Filtros">
        <label>Movimento
            <select id="tipoFilter">
                <option value="">Todos</option>
                <option value="ENTRADA">Entrada</option>
                <option value="SAIDA">Sa&iacute;da</option>
                <option value="A_CONFIRMAR">A confirmar</option>
            </select>
        </label>
        <label>Integra&ccedil;&atilde;o
            <select id="statusFilter">
                <option value="">Todos</option>
                <option value="PENDENTE">Pendente</option>
                <option value="ENVIADO">Enviado</option>
                <option value="ERRO">Erro</option>
                <option value="REPROCESSAR">Reprocessar</option>
            </select>
        </label>
        <label>Nro. &Uacute;nico<input id="nunotaFilter" type="text" inputmode="numeric" autocomplete="off"></label>
        <label>S&eacute;rie<input id="serieFilter" type="text" autocomplete="off"></label>
        <label>Produto<input id="produtoFilter" type="text" autocomplete="off"></label>
        <button id="searchButton" class="secondary-button" type="button">Filtrar</button>
    </section>

    <section class="table-section" aria-label="Analise dos movimentos">
        <div class="table-header">
            <h2>Consolida&ccedil;&atilde;o View x Integra&ccedil;&atilde;o</h2>
            <span id="statusMessage" role="status">Carregando dados da consulta Sankhya.</span>
        </div>
        <div class="table-scroll">
            <table>
                <thead>
                <tr>
                    <th>Nro. &Uacute;nico</th><th>TOP</th><th>Produto</th><th>S&eacute;rie</th><th>Movimento</th><th>Poder</th><th>Local</th><th>Status Estoque</th><th>Valor</th><th>Status Integra&ccedil;&atilde;o</th><th>HTTP</th><th>Erro</th>
                </tr>
                </thead>
                <tbody id="movimentosBody"><tr><td colspan="12" class="empty-state">Nenhum dado carregado.</td></tr></tbody>
            </table>
        </div>
    </section>

    <table id="movimentosDataSource" class="data-source">
        <tbody>
        <c:forEach items="${movimentosGenesis.rows}" var="row">
            <tr class="js-movimento-row"
                data-nunota="<c:out value="${row.NUNOTA}" />"
                data-num-nota="<c:out value="${row.NUMNOTA}" />"
                data-codtipoper="<c:out value="${row.CODTIPOPER}" />"
                data-sequencia="<c:out value="${row.SEQUENCIA}" />"
                data-codprod="<c:out value="${row.CODPROD}" />"
                data-produto="<c:out value="${row.PRODUTO}" />"
                data-grupo-produto="<c:out value="${row.GRUPO_PRODUTO}" />"
                data-serie="<c:out value="${row.SERIE}" />"
                data-tipo="<c:out value="${row.TIPO}" />"
                data-poder="<c:out value="${row.PODER}" />"
                data-descrlocal="<c:out value="${row.DESCRLOCAL}" />"
                data-status-estoque="<c:out value="${row.STATUS_ESTOQUE}" />"
                data-status-estoque-desc="<c:out value="${row.STATUS_ESTOQUE_DESC}" />"
                data-vlrunit="<c:out value="${row.VLRUNIT}" />"
                data-chave-movimento="<c:out value="${row.CHAVE_MOVIMENTO}" />"
                data-status-integracao="<c:out value="${row.STATUS_INTEGRACAO}" />"
                data-http-status="<c:out value="${row.HTTP_STATUS}" />"
                data-mensagem-erro="<c:out value="${row.MENSAGEM_ERRO}" />"
                data-tentativas="<c:out value="${row.TENTATIVAS}" />"
                data-dh-envio="<c:out value="${row.DH_ENVIO}" />"
                data-dh-atualizacao="<c:out value="${row.DH_ATUALIZACAO}" />"></tr>
        </c:forEach>
        </tbody>
    </table>

    <dialog id="errorDialog">
        <form method="dialog">
            <h2>Detalhe da Integra&ccedil;&atilde;o</h2>
            <pre id="errorDialogText"></pre>
            <button class="primary-button" type="submit">Fechar</button>
        </form>
    </dialog>
</main>
<script type="text/javascript">
(function () {
    var movimentos = coletarMovimentos();
    var elements = {
        refreshButton: document.getElementById('refreshButton'), searchButton: document.getElementById('searchButton'), tipoFilter: document.getElementById('tipoFilter'), statusFilter: document.getElementById('statusFilter'), nunotaFilter: document.getElementById('nunotaFilter'), serieFilter: document.getElementById('serieFilter'), produtoFilter: document.getElementById('produtoFilter'), statusMessage: document.getElementById('statusMessage'), movimentosBody: document.getElementById('movimentosBody'), countEntradas: document.getElementById('countEntradas'), countSaidas: document.getElementById('countSaidas'), countSeries: document.getElementById('countSeries'), countEnviado: document.getElementById('countEnviado'), countPendente: document.getElementById('countPendente'), countErro: document.getElementById('countErro'), tipoBreakdown: document.getElementById('tipoBreakdown'), estoqueBreakdown: document.getElementById('estoqueBreakdown'), integracaoBreakdown: document.getElementById('integracaoBreakdown'), errorDialog: document.getElementById('errorDialog'), errorDialogText: document.getElementById('errorDialogText')
    };
    elements.refreshButton.addEventListener('click', function () { window.location.reload(); });
    elements.searchButton.addEventListener('click', aplicarFiltros);
    elements.tipoFilter.addEventListener('change', aplicarFiltros);
    elements.statusFilter.addEventListener('change', aplicarFiltros);
    elements.nunotaFilter.addEventListener('input', aplicarFiltros);
    elements.serieFilter.addEventListener('input', aplicarFiltros);
    elements.produtoFilter.addEventListener('input', aplicarFiltros);
    aplicarFiltros();

    function coletarMovimentos() {
        var rows = document.querySelectorAll('#movimentosDataSource .js-movimento-row');
        var items = [];
        for (var i = 0; i < rows.length; i++) {
            var r = rows[i];
            items.push({
                nunota: attr(r, 'data-nunota'), numNota: attr(r, 'data-num-nota'), codtipoper: attr(r, 'data-codtipoper'), sequencia: attr(r, 'data-sequencia'), codprod: attr(r, 'data-codprod'), produto: attr(r, 'data-produto'), grupoProduto: attr(r, 'data-grupo-produto'), serie: attr(r, 'data-serie'), tipo: attr(r, 'data-tipo') || 'A_CONFIRMAR', poder: attr(r, 'data-poder'), descrlocal: attr(r, 'data-descrlocal'), statusEstoque: attr(r, 'data-status-estoque'), statusEstoqueDesc: attr(r, 'data-status-estoque-desc'), vlrunit: attr(r, 'data-vlrunit'), chaveMovimento: attr(r, 'data-chave-movimento'), statusIntegracao: attr(r, 'data-status-integracao') || 'PENDENTE', httpStatus: attr(r, 'data-http-status'), mensagemErro: attr(r, 'data-mensagem-erro'), tentativas: attr(r, 'data-tentativas'), dhEnvio: attr(r, 'data-dh-envio'), dhAtualizacao: attr(r, 'data-dh-atualizacao')
            });
        }
        return items;
    }

    function aplicarFiltros() {
        var filtrados = [];
        var tipo = elements.tipoFilter.value;
        var status = elements.statusFilter.value;
        var nunota = normalizarBusca(elements.nunotaFilter.value);
        var serie = normalizarBusca(elements.serieFilter.value);
        var produto = normalizarBusca(elements.produtoFilter.value);
        for (var i = 0; i < movimentos.length; i++) {
            var m = movimentos[i];
            if (tipo && m.tipo !== tipo) continue;
            if (status && m.statusIntegracao !== status) continue;
            if (nunota && normalizarBusca(m.nunota).indexOf(nunota) < 0) continue;
            if (serie && normalizarBusca(m.serie).indexOf(serie) < 0) continue;
            if (produto && normalizarBusca(m.produto + ' ' + m.codprod).indexOf(produto) < 0) continue;
            filtrados.push(m);
        }
        renderResumo(filtrados);
        renderAnalises(filtrados);
        renderMovimentos(filtrados);
        elements.statusMessage.textContent = filtrados.length + ' de ' + movimentos.length + ' registros exibidos.';
    }

    function renderResumo(items) {
        elements.countEntradas.textContent = contar(items, 'tipo', 'ENTRADA');
        elements.countSaidas.textContent = contar(items, 'tipo', 'SAIDA');
        elements.countSeries.textContent = seriesUnicas(items);
        elements.countEnviado.textContent = contar(items, 'statusIntegracao', 'ENVIADO');
        elements.countPendente.textContent = contar(items, 'statusIntegracao', 'PENDENTE');
        elements.countErro.textContent = contar(items, 'statusIntegracao', 'ERRO') + contar(items, 'statusIntegracao', 'REPROCESSAR');
    }

    function renderAnalises(items) {
        renderBreakdown(elements.tipoBreakdown, agrupar(items, 'tipo'));
        renderBreakdown(elements.estoqueBreakdown, agrupar(items, 'statusEstoqueDesc'));
        renderBreakdown(elements.integracaoBreakdown, agrupar(items, 'statusIntegracao'));
    }

    function renderBreakdown(container, rows) {
        container.innerHTML = '';
        if (!rows.length) { container.innerHTML = '<div class="breakdown-label">Sem dados.</div>'; return; }
        var max = 1;
        for (var i = 0; i < rows.length; i++) if (rows[i].value > max) max = rows[i].value;
        for (var r = 0; r < rows.length; r++) {
            var item = document.createElement('div');
            item.className = 'breakdown-row';
            item.innerHTML = '<span class="breakdown-label">' + esc(rows[r].label) + '</span><div class="breakdown-bar"><div class="breakdown-fill" style="width:' + Math.round((rows[r].value / max) * 100) + '%"></div></div><span class="breakdown-count">' + esc(rows[r].value) + '</span>';
            container.appendChild(item);
        }
    }

    function renderMovimentos(items) {
        elements.movimentosBody.innerHTML = '';
        if (!items.length) {
            elements.movimentosBody.innerHTML = '<tr><td colspan="12" class="empty-state">Nenhum movimento encontrado.</td></tr>';
            return;
        }
        for (var i = 0; i < items.length; i++) {
            var m = items[i];
            var row = document.createElement('tr');
            if (m.statusIntegracao === 'ENVIADO') row.className = 'table-row-muted';
            appendCell(row, m.nunota, 'nowrap');
            appendCell(row, m.codtipoper, 'nowrap');
            appendCell(row, m.produto, 'product-cell');
            appendCell(row, m.serie, 'nowrap');
            appendType(row, m.tipo);
            appendCell(row, m.poder, 'nowrap');
            appendCell(row, m.descrlocal, 'nowrap');
            appendCell(row, m.statusEstoqueDesc || m.statusEstoque, 'nowrap');
            appendCell(row, formatMoney(m.vlrunit), 'nowrap');
            appendStatus(row, m.statusIntegracao);
            appendCell(row, m.httpStatus, 'nowrap');
            appendError(row, m);
            elements.movimentosBody.appendChild(row);
        }
    }

    function appendCell(row, value, className) { var cell = document.createElement('td'); if (className) cell.className = className; cell.textContent = value || '-'; row.appendChild(cell); }
    function appendType(row, value) { var cell = document.createElement('td'); var v = value || 'A_CONFIRMAR'; cell.innerHTML = '<span class="type-pill type-' + escAttr(v) + '">' + esc(v) + '</span>'; row.appendChild(cell); }
    function appendStatus(row, value) { var cell = document.createElement('td'); var v = value || 'PENDENTE'; cell.innerHTML = '<span class="status-pill status-' + escAttr(v) + '">' + esc(v) + '</span>'; row.appendChild(cell); }
    function appendError(row, m) { var cell = document.createElement('td'); if (!m.mensagemErro) { cell.textContent = '-'; } else { var b = document.createElement('button'); b.type = 'button'; b.className = 'error-button'; b.textContent = 'Ver erro'; b.onclick = function () { abrirMensagem(m.mensagemErro); }; cell.appendChild(b); } row.appendChild(cell); }
    function abrirMensagem(message) { elements.errorDialogText.textContent = message; if (elements.errorDialog && typeof elements.errorDialog.showModal === 'function') elements.errorDialog.showModal(); else alert(message); }
    function agrupar(items, field) { var map = {}; for (var i = 0; i < items.length; i++) { var key = items[i][field] || 'A_CONFIRMAR'; map[key] = (map[key] || 0) + 1; } var out = []; for (var k in map) if (map.hasOwnProperty(k)) out.push({ label: k, value: map[k] }); out.sort(function(a, b) { return b.value - a.value; }); return out; }
    function contar(items, field, value) { var total = 0; for (var i = 0; i < items.length; i++) if (items[i][field] === value) total++; return total; }
    function seriesUnicas(items) { var map = {}; for (var i = 0; i < items.length; i++) if (items[i].serie) map[items[i].serie] = true; var total = 0; for (var k in map) if (map.hasOwnProperty(k)) total++; return total; }
    function attr(el, name) { return el.getAttribute(name) || ''; }
    function normalizarBusca(value) { return String(value || '').toLowerCase().replace(/[\u0300-\u036f]/g, '').trim(); }
    function formatMoney(value) { if (!value) return '-'; var n = Number(String(value).replace(',', '.')); if (isNaN(n)) return value; return n.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 }); }
    function esc(value) { return String(value == null ? '' : value).replace(/[&<>"]/g, function (c) { return {'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c]; }); }
    function escAttr(value) { return String(value == null ? '' : value).replace(/[^a-zA-Z0-9_-]/g, '_'); }
})();
</script>
</body>
</html>