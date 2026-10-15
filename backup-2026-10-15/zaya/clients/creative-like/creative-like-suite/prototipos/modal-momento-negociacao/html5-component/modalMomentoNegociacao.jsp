<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="br.com.sankhya.jape.dao.JdbcWrapper" %>
<%@ page import="br.com.sankhya.jape.sql.NativeSql" %>
<%@ page import="br.com.sankhya.modelcore.util.EntityFacadeFactory" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
%>
<%!
    private static class PedidoVendaView {
        String nunota = "";
        String pedidosJson = "[]";
        String erro = "";
        boolean encontrado = false;
    }

    private PedidoVendaView buscarPedidoVendaView(HttpServletRequest request) {
        PedidoVendaView retorno = new PedidoVendaView();
        String nuNegociacaoParam = request.getParameter("nunegociacao");

        if (nuNegociacaoParam == null || nuNegociacaoParam.trim().isEmpty()) {
            retorno.erro = "NUNEGOCIACAO nao informado na URL.";
            return retorno;
        }

        NativeSql sql = null;
        ResultSet rs = null;
        JdbcWrapper jdbc = null;

        try {
            BigDecimal nuNegociacao = new BigDecimal(nuNegociacaoParam.replaceAll("[^0-9]", ""));
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();

            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT CAB.NUNOTA, CAB.NUMNOTA, CAB.CODTIPOPER ");
            sql.appendSql("FROM AD_VGLDOC DOC ");
            sql.appendSql("JOIN TGFCAB CAB ON CAB.NUNOTA = DOC.NUNOTA ");
            sql.appendSql("WHERE DOC.NUNEGOCIACAO = :NUNEGOCIACAO ");
            sql.appendSql("AND CAB.TIPMOV = 'P' ");
            sql.appendSql("ORDER BY CAB.NUNOTA DESC");
            sql.setNamedParameter("NUNEGOCIACAO", nuNegociacao);

            rs = sql.executeQuery();

            StringBuilder pedidos = new StringBuilder("[");
            int total = 0;

            while (rs.next()) {
                BigDecimal nunota = rs.getBigDecimal("NUNOTA");

                if (total > 0) {
                    pedidos.append(",");
                }

                pedidos.append("{")
                        .append("\"nunota\":\"").append(escapeJson(String.valueOf(nunota))).append("\",")
                        .append("\"numNota\":\"").append(escapeJson(String.valueOf(rs.getBigDecimal("NUMNOTA")))).append("\",")
                        .append("\"codtipoper\":\"").append(escapeJson(String.valueOf(rs.getBigDecimal("CODTIPOPER")))).append("\",")
                        .append("\"tipmov\":\"P\",")
                        .append("\"itens\":").append(buscarItensNotaJson(jdbc, nunota))
                        .append("}");

                if (retorno.nunota == null || retorno.nunota.isEmpty()) {
                    retorno.nunota = String.valueOf(nunota);
                }

                total++;
            }

            pedidos.append("]");
            retorno.pedidosJson = pedidos.toString();

            if (total > 0) {
                retorno.encontrado = true;
            } else {
                retorno.erro = "Nenhum Pedido de Venda encontrado na AD_VGLDOC com TIPMOV = P.";
            }
        } catch (Exception e) {
            retorno.erro = e.getMessage();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (sql != null) {
                    NativeSql.releaseResources(sql);
                }
            } catch (Exception ignored) {
            }

            try {
                if (jdbc != null) {
                    jdbc.closeSession();
                }
            } catch (Exception ignored) {
            }
        }

        return retorno;
    }

    private String buscarItensNotaJson(JdbcWrapper jdbc, BigDecimal nunota) {
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT ITE.NUNOTA, ITE.SEQUENCIA, ITE.CODPROD, PRO.REFERENCIA, PRO.DESCRPROD, ");
            sql.appendSql("       ITE.QTDNEG, NVL(ITE.QTDENTREGUE, 0) AS QTDENTREGUE, ITE.PENDENTE ");
            sql.appendSql("FROM TGFITE ITE ");
            sql.appendSql("LEFT JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD ");
            sql.appendSql("WHERE ITE.NUNOTA = :NUNOTA ");
            sql.appendSql("ORDER BY ITE.SEQUENCIA");
            sql.setNamedParameter("NUNOTA", nunota);

            rs = sql.executeQuery();

            StringBuilder itens = new StringBuilder("[");
            int total = 0;

            while (rs.next()) {
                if (total > 0) {
                    itens.append(",");
                }

                itens.append("{")
                        .append("\"nunota\":\"").append(escapeJson(String.valueOf(rs.getBigDecimal("NUNOTA")))).append("\",")
                        .append("\"sequencia\":\"").append(escapeJson(String.valueOf(rs.getBigDecimal("SEQUENCIA")))).append("\",")
                        .append("\"codprod\":\"").append(escapeJson(String.valueOf(rs.getBigDecimal("CODPROD")))).append("\",")
                        .append("\"referencia\":\"").append(escapeJson(rs.getString("REFERENCIA"))).append("\",")
                        .append("\"descricao\":\"").append(escapeJson(rs.getString("DESCRPROD"))).append("\",")
                        .append("\"qtdneg\":\"").append(escapeJson(String.valueOf(rs.getBigDecimal("QTDNEG")))).append("\",")
                        .append("\"qtdentregue\":\"").append(escapeJson(String.valueOf(rs.getBigDecimal("QTDENTREGUE")))).append("\",")
                        .append("\"pendente\":\"").append(escapeJson(rs.getString("PENDENTE"))).append("\"")
                        .append("}");
                total++;
            }

            itens.append("]");
            return itens.toString();
        } catch (Exception e) {
            return "[]";
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (sql != null) {
                    NativeSql.releaseResources(sql);
                }
            } catch (Exception ignored) {
            }
        }
    }

    private String escapeJs(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\r", " ")
                .replace("\n", " ");
    }

    private String escapeJson(String value) {
        if (value == null || "null".equals(value)) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", " ")
                .replace("\n", " ");
    }
%>
<%
    PedidoVendaView pedidoVendaView = buscarPedidoVendaView(request);
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simulação Suprimentos</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            color: #1f2933;
            background: #f5f7fa;
            font-size: 13px;
        }

        button,
        input,
        select,
        textarea {
            font: inherit;
        }

        .app {
            max-width: 1180px;
            margin: 0 auto;
            padding: 14px 16px 18px;
        }

        .topbar {
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            gap: 12px;
            margin-bottom: 12px;
        }

        .title {
            margin: 0;
            font-size: 19px;
            font-weight: 700;
        }

        .subtitle {
            color: #667085;
            margin-top: 4px;
            line-height: 1.4;
            max-width: 840px;
        }

        .badge {
            display: inline-block;
            padding: 6px 10px;
            border-radius: 4px;
            background: #fff4d6;
            color: #8a5a00;
            border: 1px solid #ffd580;
            font-weight: 700;
            white-space: nowrap;
        }

        .toolbar {
            display: grid;
            grid-template-columns: minmax(260px, 1fr) 140px 150px;
            gap: 10px;
            margin-bottom: 12px;
            align-items: end;
        }

        label {
            display: block;
            color: #667085;
            font-size: 12px;
            font-weight: 700;
            margin-bottom: 5px;
        }

        select,
        input,
        textarea,
        button {
            width: 100%;
            border: 1px solid #cbd5e1;
            border-radius: 4px;
            background: #fff;
            color: #344054;
            padding: 7px 10px;
        }

        select,
        input,
        button {
            min-height: 34px;
        }

        textarea {
            min-height: 90px;
            resize: vertical;
        }

        button {
            cursor: pointer;
            font-weight: 700;
        }

        button.primary {
            border-color: #344054;
            background: #344054;
            color: #fff;
        }

        button.danger {
            border-color: #b42318;
            background: #b42318;
            color: #fff;
        }

        button:disabled {
            cursor: not-allowed;
            opacity: 0.55;
        }

        .summary-grid {
            display: grid;
            grid-template-columns: repeat(6, minmax(120px, 1fr));
            gap: 8px;
            margin-bottom: 12px;
        }

        .macro-grid {
            display: grid;
            grid-template-columns: repeat(5, minmax(120px, 1fr));
            gap: 8px;
        }

        .kv {
            background: #fff;
            border: 1px solid #e1e7ef;
            border-left: 6px solid #344054;
            padding: 9px 10px;
            min-height: 58px;
            min-width: 0;
        }

        .kv.ok {
            border-left-color: #00a65a;
        }

        .kv.warn {
            border-left-color: #f0ad4e;
        }

        .kv.block {
            border-left-color: #dd4b39;
        }

        .kv.info {
            border-left-color: #175cd3;
        }

        .kv strong {
            display: block;
            font-size: 16px;
        }

        .kv span {
            display: block;
            line-height: 1.25;
        }

        .kv .card-note {
            margin-top: 4px;
            color: #667085;
            font-size: 11px;
        }

        .label {
            color: #667085;
            font-size: 12px;
            margin-bottom: 6px;
        }

        .layout {
            display: grid;
            grid-template-columns: minmax(0, 1fr) 270px;
            gap: 12px;
            align-items: start;
        }

        main,
        aside {
            min-width: 0;
        }

        .panel {
            background: #fff;
            border: 1px solid #e1e7ef;
            padding: 12px;
            margin-bottom: 12px;
        }

        .panel h2 {
            margin: 0 0 10px 0;
            font-size: 14px;
        }

        .flow {
            display: grid;
            grid-template-columns: repeat(4, minmax(110px, 1fr));
            gap: 8px;
        }

        .flow-step {
            border: 1px solid #e4e7ec;
            background: #fcfcfd;
            padding: 8px;
            min-width: 0;
        }

        .flow-link {
            display: inline-block;
            border: 1px solid #cbd5e1;
            background: #fff;
            color: #1d4ed8;
            padding: 5px 7px;
            border-radius: 4px;
            margin: 3px 4px 0 0;
            cursor: pointer;
            font-weight: 700;
        }

        .flow-link:hover {
            background: #eff6ff;
        }

        .flow-step b {
            display: block;
            margin-bottom: 4px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 12px;
            min-width: 780px;
        }

        th,
        td {
            border: 1px solid #e1e4e8;
            padding: 7px 8px;
            vertical-align: middle;
            text-align: left;
        }

        th {
            background: #f2f4f7;
            color: #344054;
            font-weight: 700;
        }

        .table-wrap {
            width: 100%;
            overflow-x: auto;
            border: 1px solid #e1e7ef;
            background: #fff;
        }

        .table-wrap table {
            border: none;
        }

        .table-wrap th:first-child,
        .table-wrap td:first-child {
            border-left: none;
        }

        .table-wrap th:last-child,
        .table-wrap td:last-child {
            border-right: none;
        }

        .col-num {
            width: 76px;
        }

        .col-seq {
            width: 84px;
        }

        .col-ref {
            width: 105px;
        }

        .col-qtd {
            width: 92px;
        }

        .col-status {
            width: 90px;
        }

        .col-action {
            width: 112px;
        }

        .col-rule {
            width: 170px;
        }

        .muted {
            color: #667085;
        }

        tr.blocked {
            background: #fff1f0;
        }

        tr.changed {
            background: #ecfdf3;
        }

        .tag {
            display: inline-block;
            padding: 3px 7px;
            border-radius: 999px;
            font-size: 11px;
            font-weight: 700;
            white-space: nowrap;
        }

        .tag.ok {
            background: #e8f7ef;
            color: #087443;
        }

        .tag.warn {
            background: #fff4d6;
            color: #8a5a00;
        }

        .tag.block {
            background: #fdecec;
            color: #b42318;
        }

        .tag.info {
            background: #eff8ff;
            color: #175cd3;
        }

        .decision {
            border: 1px solid #e1e7ef;
            padding: 12px;
            margin-bottom: 12px;
            background: #f8fafc;
            line-height: 1.4;
        }

        .decision.block {
            border-color: #fecdca;
            background: #fffbfa;
        }

        .decision.ok {
            border-color: #abefc6;
            background: #f6fef9;
        }

        .decision.warn {
            border-color: #fedf89;
            background: #fffcf5;
        }

        .decision-hero {
            border: 1px solid #e1e7ef;
            background: #fff;
            border-left: 8px solid #344054;
            padding: 14px 16px;
            margin-bottom: 12px;
            display: grid;
            grid-template-columns: minmax(0, 1fr) auto;
            gap: 12px;
            align-items: center;
        }

        .decision-hero.ok {
            border-left-color: #00a65a;
            background: #f6fef9;
        }

        .decision-hero.warn {
            border-left-color: #f0ad4e;
            background: #fffcf5;
        }

        .decision-hero.block {
            border-left-color: #dd4b39;
            background: #fffbfa;
        }

        .decision-hero h2 {
            margin: 0;
            font-size: 18px;
        }

        .decision-hero p {
            margin: 5px 0 0;
            color: #475467;
            line-height: 1.35;
        }

        .hero-metrics {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
            justify-content: flex-end;
        }

        .hero-pill {
            border: 1px solid #d0d5dd;
            background: #fff;
            border-radius: 999px;
            padding: 6px 9px;
            white-space: nowrap;
            font-size: 12px;
        }

        .actions {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 7px;
            margin-bottom: 10px;
        }

        .log {
            background: #101828;
            color: #d0d5dd;
            height: 260px;
            overflow: auto;
            padding: 10px;
            font-family: Consolas, monospace;
            white-space: pre-wrap;
            line-height: 1.4;
            font-size: 12px;
        }

        aside .panel {
            position: sticky;
            top: 10px;
        }

        .compact-actions button {
            min-height: 32px;
            padding: 6px 8px;
            line-height: 1.15;
        }

        .modal-backdrop {
            display: none;
            position: fixed;
            z-index: 99;
            inset: 0;
            background: rgba(16, 24, 40, 0.52);
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .modal {
            width: min(560px, 100%);
            background: #fff;
            border-radius: 6px;
            border: 1px solid #e1e7ef;
            box-shadow: 0 18px 44px rgba(16, 24, 40, 0.24);
        }

        .modal-head,
        .modal-body,
        .modal-footer {
            padding: 14px;
        }

        .modal-head {
            border-bottom: 1px solid #eef2f6;
        }

        .modal-head h3 {
            margin: 0;
            font-size: 16px;
        }

        .modal-footer {
            display: flex;
            justify-content: flex-end;
            gap: 8px;
            border-top: 1px solid #eef2f6;
        }

        .modal-footer button {
            width: auto;
            min-width: 110px;
        }

        @media (max-width: 980px) {
            .toolbar,
            .layout,
            .summary-grid,
            .macro-grid,
            .flow,
            .decision-hero {
                grid-template-columns: 1fr;
            }

            .hero-metrics {
                justify-content: flex-start;
            }

            aside .panel {
                position: static;
            }
        }

        @media (max-width: 720px) {
            .app {
                padding: 10px;
            }

            .topbar {
                flex-direction: column;
            }

            .badge {
                align-self: flex-start;
            }
        }
    </style>
</head>
<body>
<div class="app">
    <div class="topbar">
        <div>
            <h1 class="title">Simulação de Suprimentos <span id="nuNegociacao"></span></h1>
            <div class="subtitle">
                Fluxo da linha selecionada: busca o Pedido de Venda real pela AD_VGLDOC, mostra seu Nro. Único
                e mantém a simulação de cancelamento para validar a regra antes da gravação em homologação.
            </div>
        </div>
        <span class="badge" id="modo">SIMULAÇÃO</span>
    </div>

    <div class="toolbar">
        <div>
            <label>Negociação selecionada</label>
            <div class="kv">
                <div class="label">Origem da simulação</div>
                <b id="origemSelecionada">Linha selecionada no Sankhya</b>
            </div>
        </div>
        <div>
            <label for="codUsu">Usuário logado</label>
            <input id="codUsu" value="203" maxlength="10">
        </div>
        <button class="primary" onclick="validarSuprimentos()">Validar itens</button>
    </div>

    <section class="decision-hero" id="decisionHero">
        <div>
            <h2>Avaliando a negociacao...</h2>
            <p>Vamos identificar se ha saldo para cancelamento e quais itens precisam de atencao.</p>
        </div>
        <div class="hero-metrics">
            <span class="hero-pill">Pedidos: -</span>
            <span class="hero-pill">Com saldo: -</span>
            <span class="hero-pill">Bloqueados: -</span>
        </div>
    </section>

    <div class="summary-grid" id="summaryGrid"></div>

    <div class="layout">
        <main>
            <section class="panel">
                <h2>Acompanhamento da negociacao</h2>
                <div class="subtitle" style="margin-bottom:10px;">
                    Visão macro mantida do dashboard de acompanhamento da negociação. Estes cards explicam o momento geral
                    da proposta antes da validação específica dos itens de suprimentos.
                </div>
                <div class="macro-grid" id="macroGrid"></div>
            </section>

            <section class="panel">
                <h2>Caminho ate os documentos</h2>
                <div class="flow" id="flow"></div>
            </section>

            <section class="panel">
                <h2>Itens da nota interna</h2>
                <div id="itensTable"></div>
            </section>

            <section class="panel">
                <h2>Acompanhamentos gerados na simulacao</h2>
                <div id="tgfactTable"></div>
            </section>
        </main>

        <aside>
            <section class="panel">
                <h2>Resultado da regra</h2>
                <div class="decision" id="decision"></div>
                <div class="actions compact-actions">
                    <button class="primary" onclick="cancelarItensPossiveis()">Cancelar itens com saldo</button>
                    <button onclick="validarSuprimentos()">Revalidar</button>
                    <button onclick="gerarResumoTecnico()">Ver resumo tecnico</button>
                    <button onclick="limparSimulacao()">Limpar simulacao</button>
                </div>
                <div class="log" id="log">Aguardando validacao...</div>
            </section>
        </aside>
    </div>
</div>

<div class="modal-backdrop" id="motivoModal">
    <div class="modal">
        <div class="modal-head">
            <h3 id="modalTitulo">Motivo do cancelamento</h3>
        </div>
        <div class="modal-body">
            <div id="modalMensagem"></div>
            <div id="motivoBox">
                <label for="motivoCancelamento">Motivo do cancelamento do item</label>
                <textarea id="motivoCancelamento" placeholder="Ex.: Pedido cancelado, indisponivel no fornecedor, retirado da lojinha."></textarea>
                <div class="card-note">Este texto será usado na ocorrência simulada da TGFACT.</div>
            </div>
        </div>
        <div class="modal-footer">
            <button onclick="fecharModal()">Fechar</button>
            <button class="danger" id="confirmarCancelamento" onclick="confirmarCancelamentoItem()">Confirmar</button>
        </div>
    </div>
</div>

<script>
    var pedidoVendaRealJsp = {
        encontrado: <%= pedidoVendaView.encontrado ? "true" : "false" %>,
        nunota: '<%= escapeJs(pedidoVendaView.nunota) %>',
        pedidos: <%= pedidoVendaView.pedidosJson %>,
        erro: '<%= escapeJs(pedidoVendaView.erro) %>'
    };

    var nunegociacao = getParam('nunegociacao') || '26918';
    var modo = getParam('modo') || 'SIMULACAO';
    var parametrosReais = lerParametrosReais();
    var cenario = criarSimulacaoPorNegociacao(nunegociacao);
    var cenarioOriginal = clonar(cenario);
    var itemSelecionado = null;
    var ultimaAcao = '';

    function item(nunota, sequencia, referencia, descricao, qtdneg, qtdentregue, pendente) {
        return {
            nunota: nunota,
            sequencia: sequencia,
            referencia: referencia,
            descricao: descricao,
            qtdneg: qtdneg,
            qtdentregue: qtdentregue,
            pendente: pendente,
            cancelado: 'N'
        };
    }

    function clonar(obj) {
        return JSON.parse(JSON.stringify(obj));
    }

    function getParam(nome) {
        var params = new URLSearchParams(window.location.search);
        return params.get(nome) || '';
    }

    function lerParametrosReais() {
        return {
            pedidoVendaNunota: pedidoVendaRealJsp.nunota || getParam('pedidoVendaNunota'),
            pedidoVendaCodtipoper: getParam('pedidoVendaCodtipoper') || getParam('codtipoperDocumentoGerado'),
            pedidoVendaTipmov: getParam('pedidoVendaTipmov') || getParam('tipmovDocumentoGerado'),
            pedidoVendaDescricao: getParam('pedidoVendaDescricao'),
            notaInternaNunota: getParam('notaInternaNunota') || getParam('nunotaInterna'),
            notaInternaNumNota: getParam('notaInternaNumNota') || getParam('numNotaInterna')
        };
    }

    document.getElementById('nuNegociacao').innerText = '#' + nunegociacao;
    document.getElementById('modo').innerText = modo;
    document.getElementById('origemSelecionada').innerText = 'Nro. Negociação = ' + nunegociacao + ' | Fonte: ' + rotuloFonteDados(cenario);

    function cenarioAtual() {
        return cenario;
    }

    function criarSimulacaoPorNegociacao(nuNeg) {
        var numero = parseInt(String(nuNeg).replace(/\D/g, ''), 10);

        if (isNaN(numero)) {
            numero = 26918;
        }

        var nunota = parametrosReais.notaInternaNunota ? Number(parametrosReais.notaInternaNunota) : 100000 + (numero % 90000);
        var documentosGerados = criarDocumentosGerados(numero, nuNeg);
        var pedidoVenda = selecionarPedidoVenda(documentosGerados);
        var numNota = parametrosReais.notaInternaNumNota ? Number(parametrosReais.notaInternaNumNota) : 5000 + (numero % 4000);
        var perfil = numero % 3;
        var qtdBase = 5 + (numero % 8);
        var itens = [];

        if (perfil === 0) {
            itens.push(item(nunota, 1, 'SUP-' + (numero % 1000), 'Item suprimentos selecionado', qtdBase, qtdBase, 'S'));
            itens.push(item(nunota, 2, 'SUP-' + ((numero + 1) % 1000), 'Item complementar entregue', qtdBase + 4, qtdBase + 4, 'S'));
        } else if (perfil === 1) {
            itens.push(item(nunota, 1, 'SUP-' + (numero % 1000), 'Item suprimentos selecionado', qtdBase + 10, qtdBase - 1, 'S'));
            itens.push(item(nunota, 2, 'SUP-' + ((numero + 1) % 1000), 'Item sem entrega registrada', qtdBase + 6, 0, 'S'));
        } else {
            itens.push(item(nunota, 1, 'SUP-' + (numero % 1000), 'Item ja entregue', qtdBase, qtdBase, 'S'));
            itens.push(item(nunota, 2, 'SUP-' + ((numero + 1) % 1000), 'Item com saldo pendente', qtdBase + 12, qtdBase, 'S'));
            itens.push(item(nunota, 3, 'SUP-' + ((numero + 2) % 1000), 'Item aguardando fornecedor', qtdBase + 5, 0, 'S'));
        }

        return {
            id: 'selecionada-' + numero,
            nome: 'Validacao baseada na linha selecionada',
            cliente: 'Cliente da negociacao ' + nuNeg,
            vendedor: 'Vendedor da negociacao ' + nuNeg,
            statusNegociacao: perfil === 0 ? 'Aprovada' : (perfil === 1 ? 'Em andamento' : 'Parcial'),
            acompanhamento: criarAcompanhamentoDashboard(perfil),
            fonteDados: definirFonteDados(),
            documentosGerados: documentosGerados,
            pedidoVenda: pedidoVenda,
            nunotaDocumentoGerado: pedidoVenda ? pedidoVenda.nunota : '',
            codtipoperDocumentoGerado: pedidoVenda ? pedidoVenda.codtipoper : '',
            tipmovDocumentoGerado: pedidoVenda ? pedidoVenda.tipmov : '',
            nunota: nunota,
            numNota: numNota,
            itens: itens,
            tgfact: []
        };
    }

    function criarDocumentosGerados(numero, nuNeg) {
        if (pedidoVendaRealJsp.encontrado && pedidoVendaRealJsp.pedidos && pedidoVendaRealJsp.pedidos.length) {
            return pedidoVendaRealJsp.pedidos.map(function(pedido) {
                return {
                    nunegociacao: Number(nuNeg),
                    nunota: Number(pedido.nunota),
                    numNota: pedido.numNota,
                    codtipoper: pedido.codtipoper,
                    tipmov: pedido.tipmov || 'P',
                    descricao: 'PEDIDO DE VENDA',
                    fonte: 'AD_VGLDOC real consultada no JSP',
                    itens: pedido.itens || []
                };
            });
        }

        var documentoReal = documentoPedidoVendaPorParametro(nuNeg);

        if (documentoReal) {
            return [documentoReal];
        }

        return [];
    }

    function documentoPedidoVendaPorParametro(nuNeg) {
        if (!parametrosReais.pedidoVendaNunota) {
            return null;
        }

        return {
            nunegociacao: Number(nuNeg),
            nunota: Number(parametrosReais.pedidoVendaNunota),
            codtipoper: parametrosReais.pedidoVendaCodtipoper ? Number(parametrosReais.pedidoVendaCodtipoper) : '',
            tipmov: parametrosReais.pedidoVendaTipmov || 'P',
            descricao: parametrosReais.pedidoVendaDescricao || 'PEDIDO DE VENDA',
            fonte: pedidoVendaRealJsp.encontrado ? 'AD_VGLDOC real consultada no JSP' : 'Parametros reais recebidos do Java/Sankhya'
        };
    }

    function definirFonteDados() {
        if (pedidoVendaRealJsp.encontrado) {
            return 'AD_VGLDOC_REAL';
        }

        if (pedidoVendaRealJsp.erro) {
            return 'AD_VGLDOC_SEM_RETORNO';
        }

        if (parametrosReais.pedidoVendaNunota || parametrosReais.notaInternaNunota) {
            return 'PARAMETROS_REAIS';
        }

        return 'SIMULACAO_AD_VGLDOC';
    }

    function selecionarPedidoVenda(documentos) {
        var pedidos = (documentos || []).filter(function(documento) {
            return documento.tipmov === 'P';
        });

        return pedidos[0] || null;
    }

    function criarAcompanhamentoDashboard(perfil) {
        if (perfil === 0) {
            return {
                suprimento: 'Completo',
                layout: 'Concluido',
                producao: 'Finalizado',
                faturamento: 'Pendente validacao',
                entrega: 'Entregue'
            };
        }

        if (perfil === 1) {
            return {
                suprimento: 'Pendente compra',
                layout: 'Concluido',
                producao: 'Aguardando suprimento',
                faturamento: 'Aguardando',
                entrega: 'Nao iniciada'
            };
        }

        return {
            suprimento: 'Parcial',
            layout: 'Concluido',
            producao: 'Parcial',
            faturamento: 'Pendente',
            entrega: 'Aguardando'
        };
    }

    function itensEntregues() {
        return cenarioAtual().itens.filter(function(item) {
            return Number(item.qtdentregue) === Number(item.qtdneg);
        });
    }

    function itensCancelaveis() {
        return cenarioAtual().itens.filter(function(item) {
            return Number(item.qtdentregue) !== Number(item.qtdneg) && item.cancelado !== 'S';
        });
    }

    function itensCancelados() {
        return cenarioAtual().itens.filter(function(item) {
            return item.cancelado === 'S';
        });
    }

    function avaliarDashboard() {
        var cenario = cenarioAtual();
        var itens = cenario.itens || [];
        var entregues = [];
        var cancelaveis = [];
        var cancelados = [];
        var pendentes = [];
        var semItens = itens.length === 0;

        itens.forEach(function(item) {
            var entregueTotal = Number(item.qtdentregue) === Number(item.qtdneg);

            if (item.cancelado === 'S' || item.pendente === 'N') {
                cancelados.push(item);
            } else if (entregueTotal) {
                entregues.push(item);
            } else {
                cancelaveis.push(item);
            }

            if (item.pendente === 'S') {
                pendentes.push(item);
            }
        });

        var status = 'Liberado';
        var severidade = 'ok';
        var acao = 'Pode cancelar';
        var mensagem = 'Itens com saldo podem receber PENDENTE = N mediante motivo.';

        if (semItens) {
            status = 'Sem itens';
            severidade = 'block';
            acao = 'Conferir pedido';
            mensagem = 'Nenhum item TGFITE foi localizado na simulacao.';
        } else if (entregues.length === itens.length) {
            status = 'Bloqueado';
            severidade = 'block';
            acao = 'Nao cancelar';
            mensagem = 'Todos os itens estao entregues. Nao permitir alterar PENDENTE nem cancelar.';
        } else if (entregues.length > 0 && cancelaveis.length > 0) {
            status = 'Parcial';
            severidade = 'warn';
            acao = 'Cancelar saldo';
            mensagem = 'Itens entregues ficam bloqueados; itens com saldo podem ser cancelados.';
        } else if (cancelaveis.length === 0 && cancelados.length > 0) {
            status = 'Tratado';
            severidade = 'ok';
            acao = 'Sem pendencia';
            mensagem = 'Nao existem itens restantes para cancelamento simulado.';
        }

        return {
            cenario: cenario,
            itens: itens,
            totalItens: itens.length,
            entregues: entregues,
            cancelaveis: cancelaveis,
            cancelados: cancelados,
            pendentes: pendentes,
            semItens: semItens,
            status: status,
            severidade: severidade,
            acao: acao,
            mensagem: mensagem,
            tgfact: cenario.tgfact || []
        };
    }

    function renderizar() {
        var diagnostico = avaliarDashboard();
        var cenario = diagnostico.cenario;

        renderizarHeroDecisao(diagnostico);

        document.getElementById('summaryGrid').innerHTML =
            cardResumo('Situacao', diagnostico.status, textoStatusSuprimentos(diagnostico), diagnostico.severidade)
            + cardResumo('Proxima acao', diagnostico.acao, textoAcaoPermitida(diagnostico), diagnostico.severidade)
            + cardResumo('Nota interna', cenario.nunota, 'Nro. Unico vinculado a negociacao', 'info')
            + cardResumo('Ja entregues', diagnostico.entregues.length, 'Sem saldo para cancelar', diagnostico.entregues.length ? 'block' : 'ok')
            + cardResumo('Com saldo', diagnostico.cancelaveis.length, 'Podem receber motivo', diagnostico.cancelaveis.length ? 'warn' : 'ok')
            + cardResumo('TGFACT', diagnostico.tgfact.length, 'Acompanhamentos gerados na simulacao', diagnostico.tgfact.length ? 'ok' : 'info');

        document.getElementById('flow').innerHTML =
            flowStep('Negociacao', 'Nro. Negociacao = ' + nunegociacao)
            + flowStep('Pedido(s) de Venda', textoPedidoVenda(cenario))
            + flowStep('Nota interna', 'Nro. Unico = ' + cenario.nunota + '<br>Nro. Nota = ' + cenario.numNota)
            + flowStep('Itens da nota', 'Qtd. itens = ' + diagnostico.totalItens);

        renderizarAcompanhamento(cenario);
        renderizarItens(cenario);
        renderizarTgfact(cenario);
        atualizarDecisao(diagnostico);
    }

    function renderizarHeroDecisao(diagnostico) {
        var el = document.getElementById('decisionHero');
        var titulo = 'Cancelamento disponivel';
        var texto = 'Ha itens com saldo. Revise os itens, informe o motivo e cancele somente o que ainda nao foi totalmente entregue.';

        if (diagnostico.semItens) {
            titulo = 'Nenhum item encontrado';
            texto = 'Nao encontramos itens para validar nesta nota. Confira o rastro da negociacao antes de seguir.';
        } else if (diagnostico.severidade === 'block') {
            titulo = 'Cancelamento bloqueado';
            texto = 'Todos os itens avaliados ja foram entregues. Nao ha saldo para alterar PENDENTE nem gerar cancelamento.';
        } else if (diagnostico.severidade === 'warn') {
            titulo = 'Cancelamento parcial';
            texto = 'Alguns itens ja foram entregues e ficam bloqueados. Os itens com saldo podem ser cancelados mediante motivo.';
        }

        el.className = 'decision-hero ' + diagnostico.severidade;
        el.innerHTML = '<div><h2>' + titulo + '</h2><p>' + texto + '</p></div>'
            + '<div class="hero-metrics">'
            + '<span class="hero-pill">Pedidos: ' + (cenarioAtual().documentosGerados || []).length + '</span>'
            + '<span class="hero-pill">Com saldo: ' + diagnostico.cancelaveis.length + '</span>'
            + '<span class="hero-pill">Ja entregues: ' + diagnostico.entregues.length + '</span>'
            + '</div>';
    }

    function cardResumo(label, value, detail, type) {
        return '<div class="kv ' + (type || '') + '"><div class="label">' + label + '</div><strong>' + value + '</strong><span class="card-note">' + detail + '</span></div>';
    }

    function textoPedidoVenda(cenario) {
        if (!cenario.documentosGerados || !cenario.documentosGerados.length) {
            return 'Nro. Único = não encontrado';
        }

        return cenario.documentosGerados.map(function(pedido) {
            return '<span class="flow-link" onclick="abrirItensPedidoVenda(' + pedido.nunota + ')">Nro. Único = ' + pedido.nunota + '</span>';
        }).join('');
    }

    function rotuloFonteDados(cenario) {
        if (cenario.fonteDados === 'AD_VGLDOC_REAL') {
            return 'AD_VGLDOC real';
        }

        if (cenario.fonteDados === 'AD_VGLDOC_SEM_RETORNO') {
            return 'AD_VGLDOC real sem Pedido de Venda';
        }

        if (cenario.fonteDados === 'PARAMETROS_REAIS') {
            return 'Java/Sankhya';
        }

        return cenario.pedidoVenda && cenario.pedidoVenda.fonte
            ? cenario.pedidoVenda.fonte
            : 'AD_VGLDOC simulada';
    }

    function textoStatusSuprimentos(diagnostico) {
        if (diagnostico.semItens) {
            return 'Nenhum item foi encontrado para validar.';
        }

        if (diagnostico.entregues.length === diagnostico.totalItens) {
            return 'Todos os itens já foram entregues.';
        }

        if (diagnostico.entregues.length > 0 && diagnostico.cancelaveis.length > 0) {
            return 'Há itens entregues e itens com saldo.';
        }

        if (diagnostico.cancelaveis.length > 0) {
            return 'Há itens com saldo disponível para simular cancelamento.';
        }

        return 'Todos os itens possíveis já foram tratados.';
    }

    function textoAcaoPermitida(diagnostico) {
        if (diagnostico.severidade === 'block') {
            return 'Não permitir cancelamento.';
        }

        if (diagnostico.severidade === 'warn') {
            return 'Permitir apenas para itens não entregues.';
        }

        return 'Permitir mediante motivo informado.';
    }

    function flowStep(title, detail) {
        return '<div class="flow-step"><b>' + title + '</b><span>' + detail + '</span></div>';
    }

    function renderizarAcompanhamento(cenario) {
        var dash = cenario.acompanhamento;
        var diagnostico = avaliarDashboard();
        document.getElementById('macroGrid').innerHTML =
            cardResumo('Suprimento', dash.suprimento, motivoAcompanhamento('suprimento', dash.suprimento, diagnostico), tipoStatus(dash.suprimento))
            + cardResumo('Layout', dash.layout, motivoAcompanhamento('layout', dash.layout, diagnostico), tipoStatus(dash.layout))
            + cardResumo('Produção', dash.producao, motivoAcompanhamento('producao', dash.producao, diagnostico), tipoStatus(dash.producao))
            + cardResumo('Faturamento', dash.faturamento, motivoAcompanhamento('faturamento', dash.faturamento, diagnostico), tipoStatus(dash.faturamento))
            + cardResumo('Entrega', dash.entrega, motivoAcompanhamento('entrega', dash.entrega, diagnostico), tipoStatus(dash.entrega));
    }

    function motivoAcompanhamento(area, status, diagnostico) {
        if (area === 'suprimento') {
            if (diagnostico.entregues.length && diagnostico.cancelaveis.length) {
                return 'Parcial porque há itens entregues e itens com saldo.';
            }
            if (diagnostico.entregues.length === diagnostico.totalItens) {
                return 'Bloqueado porque todos os itens já foram entregues.';
            }
            return 'Aberto porque há itens com quantidade divergente.';
        }

        if (area === 'layout') {
            return 'Informativo do acompanhamento geral da negociação.';
        }

        if (area === 'producao') {
            return 'Mostra o reflexo operacional antes do cancelamento.';
        }

        if (area === 'faturamento') {
            return 'Acompanha se existe etapa fiscal/financeira pendente.';
        }

        if (area === 'entrega') {
            return 'Indica se a negociação já avançou para entrega.';
        }

        return status;
    }

    function tipoStatus(status) {
        var s = String(status || '').toUpperCase();

        if (s.indexOf('COMPLETO') >= 0 || s.indexOf('CONCL') >= 0 || s.indexOf('FINALIZ') >= 0 || s.indexOf('ENTREG') >= 0) {
            return 'ok';
        }

        if (s.indexOf('PEND') >= 0 || s.indexOf('PARCIAL') >= 0 || s.indexOf('AGUARD') >= 0 || s.indexOf('ANDAMENTO') >= 0 || s.indexOf('NAO INICI') >= 0) {
            return 'warn';
        }

        if (s.indexOf('BLOQUE') >= 0 || s.indexOf('CANCEL') >= 0 || s.indexOf('ERRO') >= 0 || s.indexOf('ATRAS') >= 0) {
            return 'block';
        }

        return 'info';
    }

    function renderizarItens(cenario) {
        var html = '<table><thead><tr>'
            + '<th class="col-num">NUNOTA</th>'
            + '<th class="col-seq">SEQ.</th>'
            + '<th class="col-ref">REFERENCIA</th>'
            + '<th>DESCRICAO</th>'
            + '<th class="col-qtd">QTDNEG</th>'
            + '<th class="col-qtd">ENTREGUE</th>'
            + '<th class="col-qtd">SALDO</th>'
            + '<th class="col-status">PENDENTE</th>'
            + '<th class="col-rule">REGRA</th>'
            + '<th class="col-action">ACAO</th>'
            + '</tr></thead><tbody>';

        cenario.itens.forEach(function(item) {
            var entregueTotal = Number(item.qtdentregue) === Number(item.qtdneg);
            var saldo = Number(item.qtdneg) - Number(item.qtdentregue);
            var linhaClasse = entregueTotal ? 'blocked' : (item.cancelado === 'S' ? 'changed' : '');
            var regra = entregueTotal
                ? '<span class="tag block">Bloqueado: ja entregue</span>'
                : (item.cancelado === 'S'
                    ? '<span class="tag ok">Cancelado simulado</span>'
                    : '<span class="tag warn">Aguardando motivo</span>');
            var botao = entregueTotal
                ? '<button onclick="abrirBloqueio(' + item.sequencia + ')">Detalhes</button>'
                : '<button class="danger" onclick="abrirMotivo(' + item.sequencia + ')" ' + (item.cancelado === 'S' ? 'disabled' : '') + '>Cancelar item</button>';

            html += '<tr class="' + linhaClasse + '">'
                + '<td class="col-num">' + item.nunota + '</td>'
                + '<td class="col-seq">' + item.sequencia + '</td>'
                + '<td class="col-ref">' + item.referencia + '</td>'
                + '<td>' + item.descricao + '</td>'
                + '<td class="col-qtd">' + item.qtdneg + '</td>'
                + '<td class="col-qtd">' + item.qtdentregue + '</td>'
                + '<td class="col-qtd"><b>' + saldo + '</b></td>'
                + '<td class="col-status"><b>' + item.pendente + '</b></td>'
                + '<td>' + regra + '</td>'
                + '<td>' + botao + '</td>'
                + '</tr>';
        });

        html += '</tbody></table>';
        html = '<div class="table-wrap">' + html + '</div>';
        document.getElementById('itensTable').innerHTML = html;
    }

    function renderizarTgfact(cenario) {
        if (!cenario.tgfact.length) {
            document.getElementById('tgfactTable').innerHTML = '<div class="decision">Nenhum acompanhamento TGFACT simulado ainda.</div>';
            return;
        }

        var html = '<table><thead><tr>'
            + '<th class="col-num">NUNOTA</th>'
            + '<th class="col-seq">SEQ.</th>'
            + '<th>DHOCOR</th>'
            + '<th>HRACT</th>'
            + '<th>CODUSU</th>'
            + '<th>REFERENCIA</th>'
            + '<th>OCORRENCIAS</th>'
            + '<th>DIGITADO</th>'
            + '<th>AD_ITEMCANCELADO</th>'
            + '</tr></thead><tbody>';

        cenario.tgfact.forEach(function(row) {
            html += '<tr class="changed">'
                + '<td>' + row.nunota + '</td>'
                + '<td>' + row.sequencia + '</td>'
                + '<td>' + row.dhocor + '</td>'
                + '<td>' + row.hract + '</td>'
                + '<td>' + row.codusu + '</td>'
                + '<td>' + row.referencia + '</td>'
                + '<td>' + row.ocorrencias + '</td>'
                + '<td>' + row.digitado + '</td>'
                + '<td>' + row.adItemCancelado + '</td>'
                + '</tr>';
        });

        html += '</tbody></table>';
        html = '<div class="table-wrap">' + html + '</div>';
        document.getElementById('tgfactTable').innerHTML = html;
    }

    function atualizarDecisao(diagnostico) {
        diagnostico = diagnostico || avaliarDashboard();
        var el = document.getElementById('decision');
        var detalhes = '';

        if (diagnostico.entregues.length) {
            detalhes += '<li>' + diagnostico.entregues.length + ' item(ns) bloqueado(s) por entrega total.</li>';
        }

        if (diagnostico.cancelaveis.length) {
            detalhes += '<li>' + diagnostico.cancelaveis.length + ' item(ns) podem abrir popup de motivo.</li>';
        }

        if (diagnostico.cancelados.length) {
            detalhes += '<li>' + diagnostico.cancelados.length + ' item(ns) ja foram cancelados na simulacao.</li>';
        }

        if (!detalhes) {
            detalhes = '<li>Nenhum detalhe operacional para exibir.</li>';
        }

        el.className = 'decision ' + diagnostico.severidade;
        el.innerHTML = '<b>' + diagnostico.acao + '</b><br>'
            + diagnostico.mensagem
            + '<ul style="margin:8px 0 0 18px;padding:0;">' + detalhes + '</ul>';
    }

    function validarSuprimentos() {
        ultimaAcao = 'validar';
        renderizar();
        escreverLog('Validar suprimentos', montarResultado());
    }

    function abrirBloqueio(sequencia) {
        var item = buscarItem(sequencia);
        itemSelecionado = null;

        document.getElementById('modalTitulo').innerText = 'Cancelamento bloqueado';
        document.getElementById('modalMensagem').innerHTML =
            '<p><b>Item ' + sequencia + ' ja foi entregue.</b></p>'
            + '<p>Regra aplicada: quando <b>TGFITE.QTDENTREGUE = TGFITE.QTDNEG</b>, nao permitir alterar <b>PENDENTE = S</b>, nao permitir cancelar e exibir aviso ao usuario.</p>'
            + '<p>QTDNEG: <b>' + item.qtdneg + '</b><br>QTDENTREGUE: <b>' + item.qtdentregue + '</b></p>';
        document.getElementById('motivoBox').style.display = 'none';
        document.getElementById('confirmarCancelamento').style.display = 'none';
        document.getElementById('motivoModal').style.display = 'flex';
        escreverLog('Bloqueio de cancelamento', ['Item ' + sequencia + ' bloqueado: ja foi entregue e nao pode cancelar.']);
    }

    function abrirItensPedidoVenda(nunota) {
        var pedido = (cenarioAtual().documentosGerados || []).filter(function(documento) {
            return Number(documento.nunota) === Number(nunota);
        })[0];

        var itens = pedido && pedido.itens ? pedido.itens : [];
        var html = '';

        if (!pedido) {
            html = '<p>Pedido de Venda nao encontrado na lista carregada da AD_VGLDOC.</p>';
        } else if (!itens.length) {
            html = '<p><b>Nro. Único: ' + pedido.nunota + '</b></p><p>Nenhum item encontrado na TGFITE para este Pedido de Venda.</p>';
        } else {
            html += '<p><b>Nro. Único: ' + pedido.nunota + '</b></p>';
            html += '<div class="table-wrap"><table><thead><tr>';
            html += '<th>NUNOTA</th><th>SEQUENCIA</th><th>CODPROD</th><th>REFERENCIA</th><th>DESCRICAO</th><th>QTDNEG</th><th>QTDENTREGUE</th><th>SALDO</th><th>PENDENTE</th><th>REGRA</th><th>ACAO</th>';
            html += '</tr></thead><tbody>';

            itens.forEach(function(item) {
                item.nunota = item.nunota || pedido.nunota;
                item.referencia = item.referencia || '-';
                item.cancelado = item.cancelado || 'N';

                var entregueTotal = Number(item.qtdentregue) === Number(item.qtdneg);
                var saldo = Number(item.qtdneg) - Number(item.qtdentregue);
                var regra = entregueTotal
                    ? '<span class="tag block">Bloqueado: ja entregue</span>'
                    : (item.cancelado === 'S' || item.pendente === 'N'
                        ? '<span class="tag ok">Cancelado simulado</span>'
                        : '<span class="tag warn">Aguardando motivo</span>');
                var acao = entregueTotal
                    ? '<button onclick="bloquearItemPedidoVenda(' + pedido.nunota + ',' + item.sequencia + ')">Ver bloqueio</button>'
                    : (item.cancelado === 'S' || item.pendente === 'N'
                        ? '<button disabled>Cancelado</button>'
                        : '<button class="danger" onclick="abrirMotivoPedidoVenda(' + pedido.nunota + ',' + item.sequencia + ')">Cancelar item</button>');

                html += '<tr>';
                html += '<td>' + item.nunota + '</td>';
                html += '<td>' + item.sequencia + '</td>';
                html += '<td>' + item.codprod + '</td>';
                html += '<td>' + (item.referencia || '-') + '</td>';
                html += '<td>' + (item.descricao || '-') + '</td>';
                html += '<td>' + item.qtdneg + '</td>';
                html += '<td>' + item.qtdentregue + '</td>';
                html += '<td><b>' + saldo + '</b></td>';
                html += '<td><b>' + item.pendente + '</b></td>';
                html += '<td>' + regra + '</td>';
                html += '<td>' + acao + '</td>';
                html += '</tr>';
            });

            html += '</tbody></table></div>';
        }

        document.getElementById('modalTitulo').innerText = 'Itens do Pedido de Venda';
        document.getElementById('modalMensagem').innerHTML = html;
        document.getElementById('motivoBox').style.display = 'none';
        document.getElementById('confirmarCancelamento').style.display = 'none';
        document.getElementById('motivoModal').style.display = 'flex';
        escreverLog('Consulta itens Pedido de Venda', ['NUNOTA=' + nunota, 'Itens encontrados=' + itens.length]);
    }

    function buscarItemPedidoVenda(nunota, sequencia) {
        var pedido = (cenarioAtual().documentosGerados || []).filter(function(documento) {
            return Number(documento.nunota) === Number(nunota);
        })[0];

        if (!pedido || !pedido.itens) {
            return null;
        }

        return pedido.itens.filter(function(item) {
            return Number(item.sequencia) === Number(sequencia);
        })[0] || null;
    }

    function bloquearItemPedidoVenda(nunota, sequencia) {
        var item = buscarItemPedidoVenda(nunota, sequencia);

        if (!item) {
            return;
        }

        item.nunota = item.nunota || nunota;
        item.referencia = item.referencia || '-';
        document.getElementById('modalTitulo').innerText = 'Cancelamento bloqueado';
        document.getElementById('modalMensagem').innerHTML =
            '<p><b>Pedido de Venda ' + nunota + ' / Item ' + sequencia + ' ja foi entregue.</b></p>'
            + '<p>Regra aplicada: quando <b>TGFITE.QTDENTREGUE = TGFITE.QTDNEG</b>, nao permitir alterar <b>PENDENTE</b> nem cancelar.</p>'
            + '<p>QTDNEG: <b>' + item.qtdneg + '</b><br>QTDENTREGUE: <b>' + item.qtdentregue + '</b></p>';
        document.getElementById('motivoBox').style.display = 'none';
        document.getElementById('confirmarCancelamento').style.display = 'none';
        document.getElementById('motivoModal').style.display = 'flex';
        escreverLog('Bloqueio Pedido de Venda', ['NUNOTA=' + nunota, 'SEQUENCIA=' + sequencia, 'Motivo=ja entregue']);
    }

    function abrirMotivoPedidoVenda(nunota, sequencia) {
        var item = buscarItemPedidoVenda(nunota, sequencia);

        if (!item) {
            return;
        }

        item.nunota = item.nunota || nunota;
        item.referencia = item.referencia || '-';
        item.descricao = item.descricao || '-';
        itemSelecionado = item;
        document.getElementById('modalTitulo').innerText = 'Motivo do cancelamento';
        document.getElementById('modalMensagem').innerHTML =
            '<p>Pedido de Venda: <b>' + nunota + '</b></p>'
            + '<p>Item selecionado: <b>' + item.sequencia + ' - ' + item.referencia + '</b></p>'
            + '<p>Como a quantidade entregue (<b>' + item.qtdentregue + '</b>) é diferente da quantidade negociada (<b>' + item.qtdneg + '</b>), a simulação permite cancelar o saldo do item.</p>'
            + '<p class="muted">Na rotina real, isso representa alterar <b>TGFITE.PENDENTE</b> para <b>N</b> e gerar acompanhamento em <b>TGFACT</b> para o Pedido de Venda.</p>';
        document.getElementById('motivoCancelamento').value = '';
        document.getElementById('motivoBox').style.display = 'block';
        document.getElementById('confirmarCancelamento').style.display = 'inline-block';
        document.getElementById('motivoModal').style.display = 'flex';
        setTimeout(function() {
            document.getElementById('motivoCancelamento').focus();
        }, 100);
    }

    function abrirMotivo(sequencia) {
        var item = buscarItem(sequencia);
        itemSelecionado = item;

        document.getElementById('modalTitulo').innerText = 'Motivo do cancelamento';
        document.getElementById('modalMensagem').innerHTML =
            '<p>Item selecionado: <b>' + item.sequencia + ' - ' + item.referencia + '</b></p>'
            + '<p>Como a quantidade entregue (<b>' + item.qtdentregue + '</b>) é diferente da quantidade negociada (<b>' + item.qtdneg + '</b>), a simulação permite cancelar o saldo do item.</p>'
            + '<p class="muted">Na rotina real, isso representa alterar <b>TGFITE.PENDENTE</b> para <b>N</b> e gerar acompanhamento em <b>TGFACT</b>.</p>';
        document.getElementById('motivoCancelamento').value = '';
        document.getElementById('motivoBox').style.display = 'block';
        document.getElementById('confirmarCancelamento').style.display = 'inline-block';
        document.getElementById('motivoModal').style.display = 'flex';
        setTimeout(function() {
            document.getElementById('motivoCancelamento').focus();
        }, 100);
    }

    function confirmarCancelamentoItem() {
        var motivo = document.getElementById('motivoCancelamento').value;

        if (!itemSelecionado) {
            fecharModal();
            return;
        }

        if (!motivo || !motivo.trim()) {
            document.getElementById('modalMensagem').innerHTML += '<p><span class="tag block">Informe o motivo para gravar em OCORRENCIAS.</span></p>';
            return;
        }

        simularCancelamento(itemSelecionado, motivo.trim());
        fecharModal();
    }

    function simularCancelamento(item, motivo) {
        var cenario = cenarioAtual();
        var agora = new Date();

        item.pendente = 'N';
        item.cancelado = 'S';

        cenario.tgfact.push({
            nunota: item.nunota,
            sequencia: item.sequencia,
            dhocor: formatarData(agora),
            hract: formatarHoraAct(agora),
            codusu: document.getElementById('codUsu').value || '0',
            referencia: 'R',
            ocorrencias: 'Pedido de compra possui itens cancelados! Motivo: ' + motivo,
            digitado: 'S',
            adItemCancelado: 'S'
        });

        ultimaAcao = 'cancelar';
        renderizar();
        escreverLog('Cancelar item ' + item.sequencia, [
            'TGFITE.NUNOTA: ' + item.nunota,
            'TGFITE.SEQUENCIA: ' + item.sequencia,
            'TGFITE.PENDENTE: S -> N',
            'TGFACT.REFERENCIA: R',
            'TGFACT.DIGITADO: S',
            'TGFACT.AD_ITEMCANCELADO: S',
            'TGFACT.OCORRENCIAS: Pedido de compra possui itens cancelados! Motivo: ' + motivo
        ]);
    }

    function cancelarItensPossiveis() {
        var cancelaveis = avaliarDashboard().cancelaveis;

        if (!cancelaveis.length) {
            escreverLog('Cancelar possíveis', ['Nenhum item apto para cancelamento simulado nesta negociação selecionada.']);
            return;
        }

        abrirMotivo(cancelaveis[0].sequencia);
    }

    function limparSimulacao() {
        cenario = clonar(cenarioOriginal);
        ultimaAcao = '';
        itemSelecionado = null;
        renderizar();
        document.getElementById('log').innerText = 'Simulação restaurada para o estado inicial.';
    }

    function gerarResumoTecnico() {
        escreverLog('Resumo tecnico', montarResultado());
    }

    function montarResultado() {
        var diagnostico = avaliarDashboard();
        var cenario = diagnostico.cenario;
        var linhas = [];

        linhas.push('Nro. Negociação selecionada = ' + nunegociacao);
        linhas.push('Pedido de Venda (AD_VGLDOC / TIPMOV=P) - NUNOTA encontrado = ' + cenario.nunotaDocumentoGerado + ' / CODTIPOPER informativa = ' + cenario.codtipoperDocumentoGerado);
        linhas.push('Fonte do Pedido de Venda = ' + rotuloFonteDados(cenario));
        if (pedidoVendaRealJsp.erro) {
            linhas.push('Retorno consulta AD_VGLDOC = ' + pedidoVendaRealJsp.erro);
        }
        linhas.push('Nota Mov. Interna - Nro. Único = ' + cenario.nunota);
        linhas.push('Quantidade de itens encontrados na TGFITE = ' + diagnostico.totalItens);
        linhas.push('Status dashboard = ' + diagnostico.status + ' / ' + diagnostico.acao);
        linhas.push('Acompanhamento AD_TCRNEGCAB: suprimento=' + cenario.acompanhamento.suprimento
            + ', layout=' + cenario.acompanhamento.layout
            + ', producao=' + cenario.acompanhamento.producao
            + ', faturamento=' + cenario.acompanhamento.faturamento
            + ', entrega=' + cenario.acompanhamento.entrega);
        linhas.push('Modo: ' + modo + ' / nenhuma gravacao executada');
        linhas.push('');

        cenario.itens.forEach(function(item) {
            var regra = Number(item.qtdentregue) === Number(item.qtdneg)
                ? 'BLOQUEADO: ja entregue'
                : (item.cancelado === 'S' ? 'CANCELADO_SIMULADO' : 'PERMITIDO: pendente pode virar N');
            linhas.push('- Seq ' + item.sequencia + ' | QTDNEG=' + item.qtdneg + ' | QTDENTREGUE=' + item.qtdentregue + ' | PENDENTE=' + item.pendente + ' | ' + regra);
        });

        linhas.push('');
        linhas.push('Resumo: bloqueados=' + diagnostico.entregues.length
            + ', cancelaveis=' + diagnostico.cancelaveis.length
            + ', cancelados=' + diagnostico.cancelados.length
            + ', TGFACT=' + cenario.tgfact.length);

        return linhas;
    }

    function buscarItem(sequencia) {
        return cenarioAtual().itens.filter(function(item) {
            return Number(item.sequencia) === Number(sequencia);
        })[0];
    }

    function formatarData(data) {
        var dia = pad(data.getDate());
        var mes = pad(data.getMonth() + 1);
        var ano = data.getFullYear();
        return dia + '/' + mes + '/' + ano + ' 00:00';
    }

    function formatarHoraAct(data) {
        return pad(data.getHours()) + pad(data.getMinutes()) + pad(data.getSeconds());
    }

    function pad(value) {
        value = String(value);
        return value.length < 2 ? '0' + value : value;
    }

    function fecharModal() {
        document.getElementById('motivoModal').style.display = 'none';
        itemSelecionado = null;
    }

    function escreverLog(acao, linhas) {
        var agora = new Date().toLocaleString();
        document.getElementById('log').innerText = '[' + agora + '] ' + acao + '\n' + linhas.join('\n');
    }

    renderizar();
</script>
</body>
</html>
