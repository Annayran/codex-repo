<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="snk" uri="/WEB-INF/tld/sankhyaUtil.tld" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Consolidado - Fornecedores</title>
    <link rel="stylesheet" type="text/css" href="${BASE_FOLDER}/css/dashboardReclForn.css">
    <link rel="stylesheet" type="text/css" href="css/dashboardReclForn.css">
    <style>
        :root {
            --color-zone-green: #22c55e;
            --color-zone-blue: #3b82f6;
            --color-zone-red: #ef4444;
            --bg-page: #f8fafc;
            --bg-card: #ffffff;
            --bg-soft: #f8fafc;
            --border: #e2e8f0;
            --border-soft: #f1f5f9;
            --text-main: #0f172a;
            --text-secondary: #64748b;
            --text-muted: #94a3b8;
            --shadow: 0 12px 30px rgba(15, 23, 42, 0.06);
        }
        * { box-sizing: border-box; }
        body {
            margin: 0;
            color: var(--text-main);
            background: var(--bg-page);
            font-family: Inter, "Segoe UI", Arial, sans-serif;
        }
        h1, h2, p { margin: 0; }
        button, input { font-family: inherit; }
        .dashboard-shell {
            min-height: 100vh;
            padding: 32px;
            display: flex;
            flex-direction: column;
            gap: 24px;
            overflow: hidden;
        }
        .topbar {
            min-height: 72px;
            padding: 14px 18px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 16px;
            border: 1px solid var(--border);
            border-radius: 24px;
            background: var(--bg-card);
            box-shadow: var(--shadow);
        }
        .topbar-left, .topbar-right {
            display: flex;
            align-items: center;
            gap: 14px;
            min-width: 0;
        }
        .brand-mark {
            width: 42px;
            height: 42px;
            border-radius: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-shrink: 0;
            color: #fff;
            background: var(--color-zone-green);
            box-shadow: 0 16px 30px rgba(34, 197, 94, 0.22);
            font-size: 20px;
            font-weight: 800;
        }
        .brand-text { font-size: 18px; font-weight: 800; line-height: 1; }
        .brand-sub {
            display: block;
            margin-top: 4px;
            color: var(--color-zone-green);
            font-size: 7px;
            font-weight: 800;
            line-height: 1;
            letter-spacing: 0.2em;
            text-transform: uppercase;
        }
        .topbar-divider { width: 1px; height: 36px; background: var(--border); }
        .topbar h1 {
            color: var(--text-main);
            font-size: 20px;
            font-weight: 800;
            line-height: 1.1;
            letter-spacing: -0.04em;
            text-transform: uppercase;
        }
        .topbar p {
            margin-top: 5px;
            color: var(--text-secondary);
            font-size: 11px;
            font-weight: 600;
            letter-spacing: 0.12em;
            text-transform: uppercase;
        }
        .clock {
            min-width: 78px;
            color: var(--text-main);
            font-size: 13px;
            font-weight: 700;
            text-align: center;
            font-variant-numeric: tabular-nums;
        }
        .ghost-button {
            min-height: 40px;
            padding: 10px 16px;
            border: 1px solid var(--border);
            border-radius: 14px;
            background: var(--bg-soft);
            color: var(--text-secondary);
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            box-shadow: 0 4px 14px rgba(15, 23, 42, 0.04);
            font-size: 11px;
            font-weight: 700;
            letter-spacing: 0.12em;
            text-transform: uppercase;
            white-space: nowrap;
        }
        .summary-grid {
            display: grid;
            grid-template-columns: repeat(4, minmax(0, 1fr));
            gap: 18px;
        }
        .metric-card, .panel-card, .chart-card, .info-panel {
            border: 1px solid var(--border-soft);
            border-radius: 24px;
            background: var(--bg-card);
            box-shadow: var(--shadow);
        }
        .metric-card {
            min-height: 134px;
            padding: 22px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            overflow: hidden;
        }
        .metric-card-strong {
            border-color: rgba(34, 197, 94, 0.28);
            box-shadow: 0 18px 36px rgba(34, 197, 94, 0.10);
        }
        .metric-label, .card-title {
            display: block;
            color: var(--text-muted);
            font-size: 10px;
            font-weight: 700;
            letter-spacing: 0.16em;
            text-transform: uppercase;
        }
        .metric-value {
            display: block;
            margin-top: 10px;
            color: var(--text-main);
            font-size: clamp(26px, 3vw, 40px);
            font-weight: 800;
            line-height: 1;
            letter-spacing: -0.04em;
        }
        .metric-danger { color: var(--color-zone-red); }
        .metric-foot, .panel-head p, .info-panel p, .search-info {
            color: var(--text-secondary);
            font-size: 11px;
            font-weight: 500;
            line-height: 1.55;
        }
        .metric-foot { margin-top: 12px; }
        .dashboard-layout {
            display: grid;
            grid-template-columns: 336px minmax(0, 1fr);
            gap: 24px;
            min-height: 0;
            flex: 1;
        }
        .side-column { display: grid; gap: 18px; align-content: start; }
        .chart-card, .info-panel { padding: 20px; }
        .card-title { margin-bottom: 14px; }
        canvas { display: block; max-width: 100%; }
        .info-panel { display: grid; gap: 12px; }
        .dashboard-content { min-width: 0; }
        .panel-card {
            min-height: calc(100vh - 248px);
            padding: 24px;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }
        .panel-head {
            margin-bottom: 18px;
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            gap: 16px;
            flex-shrink: 0;
        }
        .panel-head h2 {
            color: var(--text-main);
            font-size: 20px;
            font-weight: 800;
            letter-spacing: -0.04em;
            text-transform: uppercase;
        }
        .panel-head p { margin-top: 6px; }
        .tabs-toolbar {
            margin-bottom: 8px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 14px;
            flex-shrink: 0;
        }
        .filters-bar {
            min-width: min(760px, 100%);
            display: flex;
            align-items: center;
            justify-content: flex-end;
            gap: 8px;
        }
        .date-filter {
            height: 42px;
            padding: 0 10px;
            border: 1px solid var(--border-soft);
            border-radius: 16px;
            background: var(--bg-soft);
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .date-filter label {
            color: var(--text-muted);
            font-size: 10px;
            font-weight: 700;
            letter-spacing: 0.12em;
            text-transform: uppercase;
        }
        .date-filter input {
            width: 122px;
            border: 0;
            outline: none;
            background: transparent;
            color: var(--text-main);
            font-size: 11px;
            font-weight: 600;
        }
        .tabs-nav { min-width: 0; display: flex; flex-wrap: wrap; gap: 10px; flex: 1; }
        .tab-button {
            min-height: 40px;
            padding: 9px 12px;
            border: 1px solid var(--border);
            border-radius: 14px;
            background: var(--bg-soft);
            color: var(--text-secondary);
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            font-size: 11px;
            font-weight: 700;
            letter-spacing: 0.1em;
            text-transform: uppercase;
        }
        .tab-button.is-active {
            border-color: rgba(34, 197, 94, 0.35);
            background: rgba(34, 197, 94, 0.10);
            color: var(--color-zone-green);
        }
        .tab-count {
            min-width: 24px;
            padding: 2px 7px;
            border-radius: 999px;
            background: rgba(15, 23, 42, 0.08);
            color: var(--text-secondary);
            font-size: 10px;
            text-align: center;
        }
        .table-search {
            min-width: 300px;
            flex: 1 1 300px;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .table-search input {
            width: 100%;
            height: 42px;
            padding: 0 14px;
            border: 1px solid var(--border-soft);
            border-radius: 16px;
            outline: none;
            background: var(--bg-soft);
            color: var(--text-main);
            font-size: 13px;
            font-weight: 500;
        }
        .search-info { min-height: 18px; margin-bottom: 10px; }
        .supplier-filter {
            margin: 0 0 12px;
            padding: 10px 12px;
            border: 1px solid rgba(34, 197, 94, 0.18);
            border-radius: 16px;
            background: rgba(34, 197, 94, 0.08);
            color: var(--text-secondary);
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 12px;
            font-size: 11px;
            font-weight: 600;
        }
        .supplier-filter strong { color: var(--text-main); font-weight: 700; }
        .supplier-filter button {
            min-height: 30px;
            padding: 6px 10px;
            border: 1px solid rgba(34, 197, 94, 0.28);
            border-radius: 12px;
            background: #fff;
            color: var(--color-zone-green);
            cursor: pointer;
            font-size: 10px;
            font-weight: 700;
            letter-spacing: 0.08em;
            text-transform: uppercase;
        }
        #tablesArea { min-height: 0; flex: 1; overflow: auto; }
        .tab-panel { display: none; }
        .tab-panel.is-active { display: block; }
        .table-wrap {
            border: 1px solid var(--border-soft);
            border-radius: 20px;
            overflow: auto;
            background: var(--bg-card);
        }
        .monitor-table {
            width: 100%;
            min-width: 1080px;
            border-collapse: collapse;
            background: var(--bg-card);
        }
        .monitor-table th, .monitor-table td {
            padding: 13px 14px;
            border-bottom: 1px solid var(--border-soft);
            color: var(--text-secondary);
            text-align: left;
            vertical-align: middle;
        }
        .monitor-table th {
            position: sticky;
            top: 0;
            z-index: 1;
            background: rgba(248, 250, 252, 0.96);
            color: var(--text-muted);
            font-size: 10px;
            font-weight: 700;
            letter-spacing: 0.12em;
            text-transform: uppercase;
        }
        .monitor-table td {
            max-width: 280px;
            font-size: 11px;
            font-weight: 500;
        }
        .monitor-table tbody tr:hover { background: rgba(248, 250, 252, 0.72); }
        .monitor-table tbody tr.drill-row { cursor: pointer; }
        .monitor-table tbody tr.drill-row:hover { background: rgba(34, 197, 94, 0.08); }
        .monitor-table td strong, .monitor-table td small { display: block; }
        .monitor-table td strong { color: var(--text-main); font-size: 12px; font-weight: 700; }
        .monitor-table td small {
            margin-top: 4px;
            color: var(--text-muted);
            font-size: 10px;
            font-weight: 600;
            letter-spacing: 0.05em;
            text-transform: uppercase;
        }
        .status-badge, .score-pill {
            width: max-content;
            max-width: 100%;
            border-radius: 999px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            white-space: nowrap;
        }
        .status-badge {
            min-height: 26px;
            padding: 5px 9px;
            font-size: 10px;
            font-weight: 700;
            letter-spacing: 0.06em;
            text-transform: uppercase;
        }
        .score-pill {
            min-width: 88px;
            min-height: 34px;
            padding: 7px 12px;
            font-size: 13px;
            font-weight: 800;
        }
        .status-success { background: rgba(34, 197, 94, 0.10); color: var(--color-zone-green); }
        .status-warning { background: rgba(249, 115, 22, 0.10); color: #f97316; }
        .status-danger { background: rgba(239, 68, 68, 0.10); color: var(--color-zone-red); }
        .value-positive { color: var(--color-zone-green) !important; }
        .value-danger { color: var(--color-zone-red) !important; }
        .empty-state {
            padding: 34px;
            color: var(--text-secondary);
            text-align: center;
            font-size: 12px;
            font-weight: 500;
        }
        @media (max-width: 1180px) {
            .summary-grid { grid-template-columns: repeat(2, minmax(0, 1fr)); }
            .dashboard-layout { grid-template-columns: minmax(0, 1fr); }
            .side-column { grid-template-columns: repeat(2, minmax(0, 1fr)); }
            .info-panel { grid-column: 1 / -1; }
            .panel-card { min-height: auto; }
        }
        @media (max-width: 820px) {
            .dashboard-shell { padding: 16px; }
            .topbar, .topbar-left, .topbar-right, .tabs-toolbar, .filters-bar {
                align-items: stretch;
                flex-direction: column;
            }
            .topbar-divider { display: none; }
            .summary-grid, .side-column { grid-template-columns: minmax(0, 1fr); }
            .table-search { width: 100%; min-width: 0; flex: 1 1 auto; }
            .date-filter input { width: 100%; }
            .supplier-filter { align-items: stretch; flex-direction: column; }
            .tabs-nav { width: 100%; flex-wrap: nowrap; overflow-x: auto; padding-bottom: 4px; }
            .tab-button { white-space: nowrap; }
        }
    </style>
    <snk:load/>
</head>
<body>
    <snk:query var="resumoFornecedores">
        <%
            String fornecedorAberto =
                "NOT (UPPER(NVL(R.STATUS, '')) = 'E' " +
                " OR UPPER(NVL(R.STATUS, '')) LIKE '%ENCERR%' " +
                " OR UPPER(NVL(R.STATUS, '')) LIKE '%CONCL%' " +
                " OR UPPER(NVL(R.STATUS, '')) LIKE '%RESOLV%' " +
                " OR UPPER(NVL(R.STATUS, '')) LIKE '%FINAL%')";

            String queryResumo =
                "WITH BASE AS ( " +
                "    SELECT " +
                "        R.CODREGISTRO, " +
                "        R.CODPARC, " +
                "        PAR.NOMEPARC, " +
                "        PAR.CGC_CPF, " +
                "        R.TIPOCORR, " +
                "        R.DTCORR, " +
                "        R.STATUS, " +
                "        R.DTENCERRAMENTO, " +
                "        CASE WHEN " + fornecedorAberto + " THEN 1 ELSE 0 END AS OCORR_ABERTA, " +
                "        CASE " +
                "            WHEN R.DTCORR IS NULL THEN 0 " +
                "            WHEN R.DTENCERRAMENTO IS NOT NULL AND TRUNC(R.DTENCERRAMENTO) - TRUNC(R.DTCORR) > 7 THEN 1 " +
                "            ELSE 0 " +
                "        END AS OCORR_ATRASADA " +
                "    FROM AD_RECLFORN R " +
                "    LEFT JOIN TGFPAR PAR ON PAR.CODPARC = R.CODPARC " +
                "), AGRUPADO AS ( " +
                "    SELECT " +
                "        CODPARC, " +
                "        MAX(NOMEPARC) AS NOMEPARC, " +
                "        MAX(CGC_CPF) AS CGC_CPF, " +
                "        COUNT(*) AS QTD_OCORRENCIAS, " +
                "        SUM(OCORR_ABERTA) AS QTD_ABERTAS, " +
                "        SUM(OCORR_ATRASADA) AS QTD_ATRASADAS, " +
                "        ROUND(((COUNT(*) - SUM(OCORR_ATRASADA)) / COUNT(*)) * 100, 2) AS INDICE_QUALIDADE, " +
                "        ROUND((SUM(OCORR_ATRASADA) / COUNT(*)) * 100, 2) AS INDICE_ATRASO, " +
                "        MAX(DTCORR) AS ULTIMA_OCORRENCIA " +
                "    FROM BASE " +
                "    GROUP BY CODPARC " +
                ") " +
                "SELECT " +
                "    CODPARC, " +
                "    NOMEPARC, " +
                "    CGC_CPF, " +
                "    QTD_OCORRENCIAS, " +
                "    QTD_ABERTAS, " +
                "    QTD_ATRASADAS, " +
                "    INDICE_QUALIDADE, " +
                "    INDICE_ATRASO, " +
                "    ROUND(GREATEST(0, LEAST(10, INDICE_QUALIDADE / 10)), 2) AS NOTA_FORNECEDOR, " +
                "    ULTIMA_OCORRENCIA " +
                "FROM AGRUPADO " +
                "ORDER BY NOTA_FORNECEDOR DESC, QTD_OCORRENCIAS DESC, NOMEPARC";
            out.println(queryResumo);
        %>
    </snk:query>

    <snk:query var="historicoFornecedores">
        <%
            String queryHistorico =
                "SELECT " +
                "    R.IDINSTPRN, " +
                "    R.IDINSTTAR, " +
                "    R.CODREGISTRO, " +
                "    R.IDTAREFA, " +
                "    R.CODPARC, " +
                "    PAR.NOMEPARC, " +
                "    PAR.CGC_CPF, " +
                "    R.TIPOCORR, " +
                "    R.DTCORR, " +
                "    R.DESCRICAO, " +
                "    R.GRAVIDADE, " +
                "    R.RESPONSAVEL, " +
                "    R.STATUS, " +
                "    R.DTENCERRAMENTO, " +
                "    CASE " +
                "        WHEN R.DTCORR IS NULL THEN 'Sem data' " +
                "        WHEN R.DTENCERRAMENTO IS NOT NULL AND TRUNC(R.DTENCERRAMENTO) - TRUNC(R.DTCORR) > 7 THEN 'Atrasada' " +
                "        ELSE 'No prazo' " +
                "    END AS SITUACAO_PRAZO " +
                "FROM AD_RECLFORN R " +
                "LEFT JOIN TGFPAR PAR ON PAR.CODPARC = R.CODPARC " +
                "ORDER BY R.DTCORR DESC, R.CODREGISTRO DESC";
            out.println(queryHistorico);
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
                    <h1>Dashboard Consolidado de Fornecedores</h1>
                    <p>Consolidado de reclamacoes por fornecedor &bull; Atualizado em <span id="lastRefresh">--:--:--</span></p>
                </div>
            </div>
            <div class="topbar-right">
                <span class="clock" id="clockDisplay">--:--:--</span>
                <button class="ghost-button" type="button" onclick="atualizarPagina(this)">Atualizar</button>
            </div>
        </header>

        <section class="summary-grid">
            <article class="metric-card metric-card-strong">
                <span class="metric-label">Score de qualidade</span>
                <strong class="metric-value" id="metricNota">0,00%</strong>
                <small class="metric-foot">Calculado pelos itens avaliados no periodo filtrado</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">Itens avaliados</span>
                <strong class="metric-value" id="metricOcorrencias">0</strong>
                <small class="metric-foot"><span id="metricFornecedores">0</span> fornecedores consolidados</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">% Conforme</span>
                <strong class="metric-value" id="metricQualidade">0,00%</strong>
                <small class="metric-foot">Itens conformes sobre o total avaliado</small>
            </article>
            <article class="metric-card">
                <span class="metric-label">% Atraso</span>
                <strong class="metric-value metric-danger" id="metricAtraso">0,00%</strong>
                <small class="metric-foot">Itens entregues com atraso sobre o total</small>
            </article>
        </section>

        <section class="dashboard-layout">
            <aside class="side-column">
                <div class="chart-card">
                    <div class="card-title">Distribuicao consolidada</div>
                    <canvas id="qualityChart" width="280" height="190"></canvas>
                </div>
                <div class="chart-card">
                    <div class="card-title">Historico consolidado</div>
                    <canvas id="historyChart" width="280" height="180"></canvas>
                </div>
                <div class="info-panel">
                    <span class="metric-label">Regra aplicada</span>
                    <p>O score de qualidade usa apenas os itens avaliados no periodo filtrado: Conforme e Aceito por desvio contam como atendimento de qualidade.</p>
                    <p>Atraso, fora da especificacao e nao entregue aparecem separados para auditoria. A nota de homologacao permanece fora deste calculo.</p>
                </div>
            </aside>

            <section class="dashboard-content">
                <div class="panel-card">
                    <div class="panel-head">
                        <div>
                            <h2>Fornecedor e ocorrencias</h2>
                    <p>Resumo por fornecedor e historico consolidado dos itens avaliados no Flow.</p>
                        </div>
                    </div>

                    <div class="tabs-toolbar">
                        <div class="tabs-nav" id="statusTabs"></div>
                        <div class="filters-bar">
                            <div class="date-filter">
                                <label for="dateStart">De</label>
                                <input type="date" id="dateStart">
                            </div>
                            <div class="date-filter">
                                <label for="dateEnd">Ate</label>
                                <input type="date" id="dateEnd">
                            </div>
                            <div class="table-search">
                                <input type="text" id="tabSearchInput" placeholder="Buscar fornecedor, codigo, tipo, status..." autocomplete="off">
                                <button class="ghost-button" type="button" id="tabSearchClear">Limpar</button>
                            </div>
                        </div>
                    </div>
                    <div class="search-info" id="tabSearchInfo"></div>
                    <div class="supplier-filter" id="supplierFilterInfo" style="display:none"></div>
                    <div id="tablesArea">
                        <div class="empty-state">Carregando dados...</div>
                    </div>
                </div>
            </section>
        </section>

        <table id="resumoDataSource" style="display:none">
            <tbody>
                <c:forEach items="${resumoFornecedores.rows}" var="row">
                    <tr class="js-resumo-row"
                        data-codparc="<c:out value="${row.CODPARC}" />"
                        data-nomeparc="<c:out value="${row.NOMEPARC}" />"
                        data-cgccpf="<c:out value="${row.CGC_CPF}" />"
                        data-qtdocorrencias="<c:out value="${row.QTD_OCORRENCIAS}" />"
                        data-qtdabertas="<c:out value="${row.QTD_ABERTAS}" />"
                        data-qtdatrasadas="<c:out value="${row.QTD_ATRASADAS}" />"
                        data-indicequalidade="<c:out value="${row.INDICE_QUALIDADE}" />"
                        data-indiceatraso="<c:out value="${row.INDICE_ATRASO}" />"
                        data-notafornecedor="<c:out value="${row.NOTA_FORNECEDOR}" />"
                        data-ultimaocorrencia="<c:out value="${row.ULTIMA_OCORRENCIA}" />"
                    ></tr>
                </c:forEach>
            </tbody>
        </table>

        <table id="historicoDataSource" style="display:none">
            <tbody>
                <c:forEach items="${historicoFornecedores.rows}" var="row">
                    <tr class="js-historico-row"
                        data-idinstprn="<c:out value="${row.IDINSTPRN}" />"
                        data-idinsttar="<c:out value="${row.IDINSTTAR}" />"
                        data-codregistro="<c:out value="${row.CODREGISTRO}" />"
                        data-idtarefa="<c:out value="${row.IDTAREFA}" />"
                        data-codparc="<c:out value="${row.CODPARC}" />"
                        data-nomeparc="<c:out value="${row.NOMEPARC}" />"
                        data-cgccpf="<c:out value="${row.CGC_CPF}" />"
                        data-tipocorr="<c:out value="${row.TIPOCORR}" />"
                        data-dtcorr="<c:out value="${row.DTCORR}" />"
                        data-descricao="<c:out value="${row.DESCRICAO}" />"
                        data-gravidade="<c:out value="${row.GRAVIDADE}" />"
                        data-responsavel="<c:out value="${row.RESPONSAVEL}" />"
                        data-status="<c:out value="${row.STATUS}" />"
                        data-dtencerramento="<c:out value="${row.DTENCERRAMENTO}" />"
                        data-situacaoprazo="<c:out value="${row.SITUACAO_PRAZO}" />"
                    ></tr>
                </c:forEach>
            </tbody>
        </table>
    </main>

    <script type="text/javascript">
        var fornecedorSelecionado = null;

        function trimText(v) { return v == null ? '' : String(v).replace(/^\s+|\s+$/g, ''); }
        function normalizeSearch(v) {
            var s = trimText(v).toUpperCase();
            if (s && s.normalize) s = s.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
            return s.replace(/[^A-Z0-9 ]/g, '');
        }
        function esc(s) {
            return String(s || '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
        }
        function toNumber(v) {
            var s = trimText(v);
            if (s.indexOf(',') >= 0 && s.indexOf('.') >= 0) s = s.replace(/\./g, '').replace(',', '.');
            else if (s.indexOf(',') >= 0) s = s.replace(',', '.');
            var n = Number(s);
            return isNaN(n) ? 0 : n;
        }
        function formatNumber(v) { return Number(v || 0).toLocaleString('pt-BR'); }
        function formatPercent(v) { return Number(v || 0).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 }) + '%'; }
        function formatScore(v) { return Number(v || 0).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 }); }
        function pad(n) { return n < 10 ? '0' + n : String(n); }
        function atualizarRelogio() {
            var d = new Date();
            var value = pad(d.getHours()) + ':' + pad(d.getMinutes()) + ':' + pad(d.getSeconds());
            var clock = document.getElementById('clockDisplay');
            if (clock) clock.textContent = value;
        }
        function atualizarPagina(btn) {
            if (btn) {
                btn.textContent = 'Carregando...';
                btn.disabled = true;
            }
            window.location.reload();
        }
        function searchIndex(item) {
            var partes = [];
            for (var k in item) if (item.hasOwnProperty(k)) partes.push(trimText(item[k]));
            return normalizeSearch(partes.join(' '));
        }
        function cssClassByScore(score) {
            if (score >= 85) return 'status-success';
            if (score >= 65) return 'status-warning';
            return 'status-danger';
        }
        function cssClassByText(text) {
            var s = normalizeSearch(text);
            if (s.indexOf('ATRAS') >= 0 || s.indexOf('CRIT') >= 0 || s.indexOf('ALTA') >= 0 || s === 'A') return 'status-danger';
            if (s.indexOf('MEDIA') >= 0 || s.indexOf('ABERT') >= 0 || s === 'M' || s === 'T') return 'status-warning';
            return 'status-success';
        }
        function labelTipoOcorrencia(v) {
            var s = normalizeSearch(v);
            if (s === 'ATRASO') return 'Atraso de entrega';
            if (s === 'PARCIAL') return 'Entrega parcial';
            if (s === 'AVARIA') return 'Produto avariado';
            if (s === 'FISCAL') return 'Problema fiscal';
            if (s === 'QUALIDADE') return 'Divergencia de qualidade';
            if (s === 'OUTRAS') return 'Outras ocorrencias';
            if (s === 'ENTREGA') return 'Entrega normal sem ocorrencias';
            if (s === 'NAO ENTREGUE' || s === 'NAOENTREGUE' || s === 'N ENTREGUE') return 'Nao entregue';
            return trimText(v) || '-';
        }
        function situacaoItemPorTipo(v) {
            var s = normalizeSearch(v);
            if (s === 'ENTREGA' || s === 'CONFORME') return { key: 'conforme', label: 'Conforme' };
            if (s === 'ATRASO' || s === 'ENTREGUE COM ATRASO') return { key: 'atraso', label: 'Entregue com atraso' };
            if (s === 'DESVIO' || s === 'ACEITO POR DESVIO' || s === 'OUTRAS') return { key: 'desvio', label: 'Aceito por desvio' };
            if (s === 'NAO ENTREGUE' || s === 'NAOENTREGUE' || s === 'N ENTREGUE') return { key: 'naoentregue', label: 'Nao entregue' };
            if (s === 'QUALIDADE' || s === 'AVARIA' || s === 'FISCAL' || s === 'PARCIAL' || s === 'FORA DA ESPECIFICACAO') {
                return { key: 'foraesp', label: 'Fora da especificacao' };
            }
            return { key: 'foraesp', label: trimText(v) || 'Fora da especificacao' };
        }
        function cssClassBySituacao(key) {
            if (key === 'conforme') return 'status-success';
            if (key === 'desvio' || key === 'atraso') return 'status-warning';
            return 'status-danger';
        }
        function labelGravidade(v) {
            var s = normalizeSearch(v);
            if (s === 'B' || s === 'BAIXA') return 'Baixa';
            if (s === 'M' || s === 'MEDIA') return 'Media';
            if (s === 'A' || s === 'ALTA') return 'Alta';
            return trimText(v) || '-';
        }
        function labelStatus(v) {
            var s = normalizeSearch(v);
            if (s === 'A' || s === 'ABERTA') return 'Aberta';
            if (s === 'T' || s === 'EM TRATATIVA') return 'Em tratativa';
            if (s === 'E' || s === 'ENCERRADA') return 'Encerrada';
            return trimText(v) || '-';
        }
        function parseDateValue(v) {
            var s = trimText(v);
            if (!s) return null;
            var m = s.match(/^(\d{2})\/(\d{2})\/(\d{4})/);
            if (m) return m[3] + '-' + m[2] + '-' + m[1];
            m = s.match(/^(\d{4})-(\d{2})-(\d{2})/);
            if (m) return m[1] + '-' + m[2] + '-' + m[3];
            return null;
        }
        function coletarResumo() {
            var rows = document.querySelectorAll('#resumoDataSource .js-resumo-row');
            var items = [];
            for (var i = 0; i < rows.length; i++) {
                var r = rows[i];
                items.push({
                    codparc: r.getAttribute('data-codparc') || '',
                    nomeparc: r.getAttribute('data-nomeparc') || '',
                    cgccpf: r.getAttribute('data-cgccpf') || '',
                    qtdocorrencias: toNumber(r.getAttribute('data-qtdocorrencias')),
                    qtdabertas: toNumber(r.getAttribute('data-qtdabertas')),
                    qtdatrasadas: toNumber(r.getAttribute('data-qtdatrasadas')),
                    indicequalidade: toNumber(r.getAttribute('data-indicequalidade')),
                    indiceatraso: toNumber(r.getAttribute('data-indiceatraso')),
                    notafornecedor: toNumber(r.getAttribute('data-notafornecedor')),
                    ultimaocorrencia: r.getAttribute('data-ultimaocorrencia') || ''
                });
            }
            return items;
        }
        function coletarHistorico() {
            var rows = document.querySelectorAll('#historicoDataSource .js-historico-row');
            var items = [];
            for (var i = 0; i < rows.length; i++) {
                var r = rows[i];
                items.push({
                    idinstprn: r.getAttribute('data-idinstprn') || '',
                    idinsttar: r.getAttribute('data-idinsttar') || '',
                    codregistro: r.getAttribute('data-codregistro') || '',
                    idtarefa: r.getAttribute('data-idtarefa') || '',
                    codparc: r.getAttribute('data-codparc') || '',
                    nomeparc: r.getAttribute('data-nomeparc') || '',
                    cgccpf: r.getAttribute('data-cgccpf') || '',
                    tipocorr: r.getAttribute('data-tipocorr') || '',
                    dtcorr: r.getAttribute('data-dtcorr') || '',
                    descricao: r.getAttribute('data-descricao') || '',
                    gravidade: r.getAttribute('data-gravidade') || '',
                    responsavel: r.getAttribute('data-responsavel') || '',
                    status: r.getAttribute('data-status') || '',
                    dtencerramento: r.getAttribute('data-dtencerramento') || '',
                    situacaoprazo: r.getAttribute('data-situacaoprazo') || '',
                    dataref: parseDateValue(r.getAttribute('data-dtcorr') || '')
                });
                items[items.length - 1].situacaoitem = situacaoItemPorTipo(items[items.length - 1].tipocorr);
            }
            return items;
        }
        function dentroDoPeriodo(item) {
            var dateStart = document.getElementById('dateStart');
            var dateEnd = document.getElementById('dateEnd');
            var start = dateStart ? dateStart.value : '';
            var end = dateEnd ? dateEnd.value : '';
            var data = item.dataref || '';
            if (start && data && data < start) return false;
            if (end && data && data > end) return false;
            return true;
        }
        function leituraOperacional(grupo) {
            if (!grupo.qtdocorrencias) return '-';
            if (grupo.scorequalidade >= 85 && grupo.pctatraso <= 10) return 'Fornecedor consistente em qualidade e prazo';
            if (grupo.scorequalidade < 65 && grupo.pctatraso <= 10) return 'Atende prazo, mas exige acompanhamento de qualidade';
            if (grupo.scorequalidade >= 75 && grupo.pctatraso > 20) return 'Boa qualidade com ponto de atencao em prazo';
            if (grupo.pctdesvio >= 25) return 'Atendimento por necessidade especifica/desvio aprovado';
            if (grupo.pctforaesp >= 25 || grupo.pctnaoentregue > 0) return 'Risco operacional para auditoria e compras';
            return 'Monitorar evolucao no periodo';
        }
        function consolidarHistorico(historico) {
            var grupos = {};
            for (var i = 0; i < historico.length; i++) {
                var it = historico[i];
                if (!dentroDoPeriodo(it)) continue;
                var key = it.codparc || 'SEM_FORNECEDOR';
                if (!grupos[key]) {
                    grupos[key] = {
                        codparc: it.codparc,
                        nomeparc: it.nomeparc || '-',
                        cgccpf: it.cgccpf || '-',
                        qtdocorrencias: 0,
                        qtdabertas: 0,
                        conforme: 0,
                        foraesp: 0,
                        desvio: 0,
                        atraso: 0,
                        naoentregue: 0,
                        ultimaocorrencia: ''
                    };
                }
                var g = grupos[key];
                var sit = it.situacaoitem ? it.situacaoitem.key : 'foraesp';
                g.qtdocorrencias++;
                if (g.hasOwnProperty(sit)) g[sit]++;
                if (normalizeSearch(it.status) !== 'E' && normalizeSearch(it.status) !== 'ENCERRADA') g.qtdabertas++;
                if (!g.ultimaocorrencia || (it.dataref && it.dataref > parseDateValue(g.ultimaocorrencia))) g.ultimaocorrencia = it.dtcorr;
            }
            var lista = [];
            for (var k in grupos) if (grupos.hasOwnProperty(k)) {
                var g2 = grupos[k];
                var total = g2.qtdocorrencias || 0;
                g2.qtdatrasadas = g2.atraso;
                g2.pctconforme = total ? (g2.conforme / total) * 100 : 0;
                g2.pctforaesp = total ? (g2.foraesp / total) * 100 : 0;
                g2.pctdesvio = total ? (g2.desvio / total) * 100 : 0;
                g2.pctatraso = total ? (g2.atraso / total) * 100 : 0;
                g2.pctnaoentregue = total ? (g2.naoentregue / total) * 100 : 0;
                g2.scorequalidade = total ? ((g2.conforme + g2.desvio) / total) * 100 : 0;
                g2.indicequalidade = g2.scorequalidade;
                g2.indiceatraso = g2.pctatraso;
                g2.notafornecedor = g2.scorequalidade;
                g2.leitura = leituraOperacional(g2);
                lista.push(g2);
            }
            lista.sort(function(a, b) {
                if (b.scorequalidade !== a.scorequalidade) return b.scorequalidade - a.scorequalidade;
                return b.qtdocorrencias - a.qtdocorrencias;
            });
            return lista;
        }
        function atualizarMetricas(resumo) {
            var totalOcorr = 0, totalForn = resumo.length, totalConforme = 0, totalDesvio = 0, totalAtraso = 0;
            for (var i = 0; i < resumo.length; i++) {
                totalOcorr += resumo[i].qtdocorrencias;
                totalConforme += resumo[i].conforme || 0;
                totalDesvio += resumo[i].desvio || 0;
                totalAtraso += resumo[i].atraso || 0;
            }
            var pctConforme = totalOcorr ? (totalConforme / totalOcorr) * 100 : 0;
            var pctAtraso = totalOcorr ? (totalAtraso / totalOcorr) * 100 : 0;
            var scoreQualidade = totalOcorr ? ((totalConforme + totalDesvio) / totalOcorr) * 100 : 0;
            document.getElementById('metricNota').textContent = formatPercent(scoreQualidade);
            document.getElementById('metricOcorrencias').textContent = formatNumber(totalOcorr);
            document.getElementById('metricFornecedores').textContent = formatNumber(totalForn);
            document.getElementById('metricQualidade').textContent = formatPercent(pctConforme);
            document.getElementById('metricAtraso').textContent = formatPercent(pctAtraso);
            desenharGraficoQualidade(resumo);
        }
        function desenharGraficoQualidade(resumo) {
            var canvas = document.getElementById('qualityChart');
            if (!canvas || !canvas.getContext) return;
            var ctx = canvas.getContext('2d');
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            var cx = 78, cy = 88, r = 58;
            var total = 0, conformes = 0, fora = 0, desvio = 0, atraso = 0, nao = 0;
            for (var i = 0; i < resumo.length; i++) {
                total += resumo[i].qtdocorrencias || 0;
                conformes += resumo[i].conforme || 0;
                fora += resumo[i].foraesp || 0;
                desvio += resumo[i].desvio || 0;
                atraso += resumo[i].atraso || 0;
                nao += resumo[i].naoentregue || 0;
            }
            var items = [
                { label: 'Conforme', value: conformes, color: '#22c55e' },
                { label: 'Fora esp.', value: fora, color: '#ef4444' },
                { label: 'Desvio', value: desvio, color: '#f97316' },
                { label: 'Atraso', value: atraso, color: '#3b82f6' },
                { label: 'Nao entregue', value: nao, color: '#0f172a' }
            ];
            var start = -Math.PI / 2;
            ctx.lineWidth = 18;
            for (var d = 0; d < items.length; d++) {
                var pct = total ? items[d].value / total : 0;
                if (!pct) continue;
                ctx.strokeStyle = items[d].color;
                ctx.beginPath();
                ctx.arc(cx, cy, r, start, start + (Math.PI * 2 * pct));
                ctx.stroke();
                start += Math.PI * 2 * pct;
            }
            ctx.fillStyle = '#0f172a';
            ctx.font = '800 22px Inter, Arial';
            ctx.textAlign = 'center';
            var score = total ? ((conformes + desvio) / total) * 100 : 0;
            ctx.fillText(Math.round(score) + '%', cx, cy + 8);
            ctx.textAlign = 'left';
            for (var i = 0; i < items.length; i++) {
                var y = 35 + i * 28;
                ctx.fillStyle = items[i].color;
                ctx.fillRect(162, y - 10, 12, 12);
                ctx.fillStyle = '#64748b';
                ctx.font = '700 10px Inter, Arial';
                ctx.fillText(items[i].label, 184, y);
                ctx.fillStyle = '#0f172a';
                ctx.font = '800 12px Inter, Arial';
                ctx.fillText(formatPercent(total ? (items[i].value / total) * 100 : 0), 184, y + 15);
            }
        }
        function desenharGraficoHistorico(historico) {
            var canvas = document.getElementById('historyChart');
            if (!canvas || !canvas.getContext) return;
            var ctx = canvas.getContext('2d');
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            var counts = {};
            for (var i = 0; i < historico.length; i++) {
                var raw = trimText(historico[i].dtcorr);
                var key = raw.length >= 7 ? raw.substring(3, 10) : 'Sem data';
                counts[key] = (counts[key] || 0) + 1;
            }
            var labels = [];
            for (var k in counts) if (counts.hasOwnProperty(k)) labels.push(k);
            labels = labels.slice(0, 6).reverse();
            var max = 1;
            for (var l = 0; l < labels.length; l++) if (counts[labels[l]] > max) max = counts[labels[l]];
            ctx.font = '800 10px Inter, Arial';
            ctx.textAlign = 'center';
            for (var b = 0; b < labels.length; b++) {
                var x = 30 + b * 40;
                var h = Math.max(6, (counts[labels[b]] / max) * 105);
                ctx.fillStyle = '#22c55e';
                ctx.fillRect(x, 132 - h, 22, h);
                ctx.fillStyle = '#64748b';
                ctx.fillText(labels[b], x + 11, 152);
                ctx.fillStyle = '#0f172a';
                ctx.fillText(counts[labels[b]], x + 11, 132 - h - 8);
            }
            if (!labels.length) {
                ctx.fillStyle = '#64748b';
                ctx.font = '800 12px Inter, Arial';
                ctx.fillText('Sem historico', canvas.width / 2, 90);
            }
        }
        function montarTabelaResumo(items) {
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th>Fornecedor</th><th>Score qualidade</th><th>Itens</th><th>Situacoes</th><th>Atraso</th><th>Abertas</th><th>Leitura operacional</th>';
            html += '</tr></thead><tbody>';
            for (var i = 0; i < items.length; i++) {
                var it = items[i];
                html += '<tr class="drill-row" data-drill-fornecedor="S" data-codparc="' + esc(it.codparc || '') + '" data-nomeparc="' + esc(it.nomeparc || '-') + '" data-search="' + esc(searchIndex(it)) + '" data-date="' + esc(parseDateValue(it.ultimaocorrencia) || '') + '">';
                html += '<td><strong>' + esc(it.nomeparc || '-') + '</strong><small>CODPARC ' + esc(it.codparc || '-') + ' | ' + esc(it.cgccpf || '-') + '</small></td>';
                html += '<td><span class="score-pill ' + cssClassByScore(it.scorequalidade) + '">' + esc(formatPercent(it.scorequalidade)) + '</span><small>Homologacao separada</small></td>';
                html += '<td><strong>' + esc(formatNumber(it.qtdocorrencias)) + '</strong><small>Clique para abrir</small></td>';
                html += '<td><strong class="value-positive">Conforme ' + esc(formatPercent(it.pctconforme)) + '</strong><small>Fora esp. ' + esc(formatPercent(it.pctforaesp)) + ' | Desvio ' + esc(formatPercent(it.pctdesvio)) + '</small></td>';
                html += '<td><strong class="value-danger">' + esc(formatPercent(it.pctatraso)) + '</strong><small>' + esc(formatNumber(it.atraso)) + ' itens</small></td>';
                html += '<td><strong>' + esc(formatNumber(it.qtdabertas)) + '</strong><small>Ultima ' + esc(it.ultimaocorrencia || '-') + '</small></td>';
                html += '<td><strong>' + esc(it.leitura || '-') + '</strong></td>';
                html += '</tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }
        function montarTabelaHistorico(items) {
            var html = '<div class="table-wrap"><table class="monitor-table"><thead><tr>';
            html += '<th>Codigo</th><th>Fornecedor</th><th>Situacao Flow</th><th>Tipo</th><th>Data</th><th>Gravidade</th><th>Status</th><th>Responsavel</th>';
            html += '</tr></thead><tbody>';
            for (var i = 0; i < items.length; i++) {
                var it = items[i];
                html += '<tr data-codparc="' + esc(it.codparc || '') + '" data-search="' + esc(searchIndex(it) + ' ' + labelTipoOcorrencia(it.tipocorr) + ' ' + labelGravidade(it.gravidade) + ' ' + labelStatus(it.status)) + '" data-date="' + esc(parseDateValue(it.dtcorr) || '') + '">';
                html += '<td><strong>' + esc(it.codregistro || '-') + '</strong><small>Tarefa ' + esc(it.idtarefa || '-') + '</small></td>';
                html += '<td><strong>' + esc(it.nomeparc || '-') + '</strong><small>CODPARC ' + esc(it.codparc || '-') + '</small></td>';
                html += '<td><span class="status-badge ' + cssClassBySituacao(it.situacaoitem.key) + '">' + esc(it.situacaoitem.label) + '</span></td>';
                html += '<td>' + esc(labelTipoOcorrencia(it.tipocorr)) + '<small>' + esc(it.tipocorr || '-') + '</small></td>';
                html += '<td>' + esc(it.dtcorr || '-') + '<small>Enc. ' + esc(it.dtencerramento || '-') + '</small></td>';
                html += '<td><span class="status-badge ' + cssClassByText(it.gravidade) + '">' + esc(labelGravidade(it.gravidade)) + '</span></td>';
                html += '<td><span class="status-badge ' + cssClassByText(it.status) + '">' + esc(labelStatus(it.status)) + '</span></td>';
                html += '<td>' + esc(it.responsavel || '-') + '</td>';
                html += '</tr>';
            }
            html += '</tbody></table></div>';
            return html;
        }
        function ativarAba(cat) {
            var btns = document.querySelectorAll('.tab-button');
            var panels = document.querySelectorAll('.tab-panel');
            for (var i = 0; i < btns.length; i++) btns[i].classList.toggle('is-active', btns[i].getAttribute('data-tab') === cat);
            for (var p = 0; p < panels.length; p++) panels[p].classList.toggle('is-active', panels[p].getAttribute('data-tab') === cat);
            aplicarBuscaAbaAtiva();
        }
        function atualizarFiltroFornecedor() {
            var el = document.getElementById('supplierFilterInfo');
            if (!el) return;
            if (!fornecedorSelecionado) {
                el.style.display = 'none';
                el.innerHTML = '';
                return;
            }
            el.style.display = 'flex';
            el.innerHTML = '<span>Fornecedor selecionado: <strong>' + esc(fornecedorSelecionado.nomeparc) + '</strong> | CODPARC ' + esc(fornecedorSelecionado.codparc) + '</span>' +
                '<button type="button" onclick="limparFornecedorAtivo()">Ver todos</button>';
        }
        function abrirOcorrenciasFornecedor(codparc, nomeparc) {
            fornecedorSelecionado = { codparc: trimText(codparc), nomeparc: trimText(nomeparc || '-') };
            atualizarFiltroFornecedor();
            ativarAba('historico');
        }
        function limparFornecedorAtivo() {
            fornecedorSelecionado = null;
            atualizarFiltroFornecedor();
            aplicarBuscaAbaAtiva();
        }
        function vincularDrillDown() {
            var rows = document.querySelectorAll('[data-drill-fornecedor="S"]');
            for (var i = 0; i < rows.length; i++) {
                rows[i].onclick = function() {
                    abrirOcorrenciasFornecedor(this.getAttribute('data-codparc'), this.getAttribute('data-nomeparc'));
                };
            }
        }
        function atualizarInfoBusca(visiveis, total) {
            var info = document.getElementById('tabSearchInfo');
            if (info) info.textContent = total ? (visiveis + ' de ' + total + ' registros exibidos') : '';
        }
        function aplicarBuscaAbaAtiva() {
            var input = document.getElementById('tabSearchInput');
            var dateStart = document.getElementById('dateStart');
            var dateEnd = document.getElementById('dateEnd');
            var panel = document.querySelector('.tab-panel.is-active');
            if (!panel) { atualizarInfoBusca(0, 0); return; }
            var abaAtiva = panel.getAttribute('data-tab') || '';
            var termoDigitado = input ? input.value : '';
            var termo = normalizeSearch(termoDigitado);
            var start = dateStart ? dateStart.value : '';
            var end = dateEnd ? dateEnd.value : '';
            var rows = panel.querySelectorAll('tbody tr');
            var total = rows.length;
            var visiveis = 0;
            for (var i = 0; i < rows.length; i++) {
                var ok = !termo || normalizeSearch(rows[i].getAttribute('data-search')).indexOf(termo) >= 0;
                var dataLinha = rows[i].getAttribute('data-date') || '';
                var codLinha = rows[i].getAttribute('data-codparc') || '';
                if (ok && fornecedorSelecionado && abaAtiva === 'historico' && codLinha !== fornecedorSelecionado.codparc) ok = false;
                if (ok && start && dataLinha && dataLinha < start) ok = false;
                if (ok && end && dataLinha && dataLinha > end) ok = false;
                rows[i].style.display = ok ? '' : 'none';
                if (ok) visiveis++;
            }
            var empty = panel.querySelector('.tab-search-empty');
            if (!empty) {
                empty = document.createElement('div');
                empty.className = 'empty-state tab-search-empty';
                panel.appendChild(empty);
            }
            empty.style.display = total > 0 && visiveis === 0 && termo ? 'block' : 'none';
            empty.textContent = 'Nenhum registro encontrado para "' + termoDigitado + '".';
            atualizarInfoBusca(visiveis, total);
        }
        function historicoPorPeriodo() {
            var base = window.__historicoBase || [];
            var filtrado = [];
            for (var i = 0; i < base.length; i++) if (dentroDoPeriodo(base[i])) filtrado.push(base[i]);
            return filtrado;
        }
        function atualizarDashboardPorPeriodo() {
            var historicoPeriodo = historicoPorPeriodo();
            var resumoPeriodo = consolidarHistorico(window.__historicoBase || []);
            atualizarMetricas(resumoPeriodo);
            desenharGraficoHistorico(historicoPeriodo);
            construirAbas(resumoPeriodo, historicoPeriodo);
        }
        function iniciarBusca() {
            var input = document.getElementById('tabSearchInput');
            var clear = document.getElementById('tabSearchClear');
            var dateStart = document.getElementById('dateStart');
            var dateEnd = document.getElementById('dateEnd');
            if (input) input.addEventListener('input', aplicarBuscaAbaAtiva);
            if (dateStart) dateStart.addEventListener('change', atualizarDashboardPorPeriodo);
            if (dateEnd) dateEnd.addEventListener('change', atualizarDashboardPorPeriodo);
            if (clear) clear.addEventListener('click', function() {
                if (input) {
                    input.value = '';
                    input.focus();
                }
                if (dateStart) dateStart.value = '';
                if (dateEnd) dateEnd.value = '';
                limparFornecedorAtivo();
                atualizarDashboardPorPeriodo();
            });
        }
        function construirAbas(resumo, historico) {
            var tabs = '';
            var content = '';
            var metas = [
                { cat: 'resumo', label: 'Consolidado', count: resumo.length },
                { cat: 'historico', label: 'Historico', count: historico.length }
            ];
            for (var m = 0; m < metas.length; m++) {
                var active = m === 0 ? ' is-active' : '';
                tabs += '<button class="tab-button' + active + '" data-tab="' + metas[m].cat + '" type="button" onclick="ativarAba(\'' + metas[m].cat + '\')">';
                tabs += '<span>' + esc(metas[m].label) + '</span><span class="tab-count">' + esc(metas[m].count) + '</span></button>';
                content += '<div class="tab-panel' + active + '" data-tab="' + metas[m].cat + '">';
                if (metas[m].cat === 'resumo') content += resumo.length ? montarTabelaResumo(resumo) : '<div class="empty-state">Nenhum fornecedor localizado.</div>';
                if (metas[m].cat === 'historico') content += historico.length ? montarTabelaHistorico(historico) : '<div class="empty-state">Nenhuma ocorrencia localizada.</div>';
                content += '</div>';
            }
            document.getElementById('statusTabs').innerHTML = tabs;
            document.getElementById('tablesArea').innerHTML = content;
            vincularDrillDown();
            atualizarFiltroFornecedor();
            aplicarBuscaAbaAtiva();
        }
        var historico = coletarHistorico();
        window.__historicoBase = historico;
        var resumo = consolidarHistorico(historico);
        atualizarMetricas(resumo);
        desenharGraficoHistorico(historico);
        construirAbas(resumo, historico);
        iniciarBusca();
        atualizarRelogio();
        setInterval(atualizarRelogio, 1000);
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
