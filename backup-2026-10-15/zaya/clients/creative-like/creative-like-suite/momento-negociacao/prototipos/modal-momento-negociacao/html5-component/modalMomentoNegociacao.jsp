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
            retorno.erro = "NUNEGOCIACAO não informado na URL.";
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
            sql.appendSql("       ITE.QTDNEG, NVL(ITE.QTDENTREGUE, 0) AS QTDENTREGUE, ITE.PENDENTE, ");
            sql.appendSql("       NVL(ACT.AD_ITEMCANCELADO, 'N') AS AD_ITEMCANCELADO, ");
            sql.appendSql("       ACT.OCORRENCIAS AS OCORRENCIA_CANCELAMENTO ");
            sql.appendSql("FROM TGFITE ITE ");
            sql.appendSql("LEFT JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD ");
            sql.appendSql("LEFT JOIN ( ");
            sql.appendSql("    SELECT NUNOTA, SEQUENCIA, AD_ITEMCANCELADO, OCORRENCIAS ");
            sql.appendSql("    FROM ( ");
            sql.appendSql("        SELECT NUNOTA, SEQUENCIA, AD_ITEMCANCELADO, OCORRENCIAS, ");
            sql.appendSql("               ROW_NUMBER() OVER (PARTITION BY NUNOTA, SEQUENCIA ORDER BY DHOCOR DESC, HRACT DESC) AS RN ");
            sql.appendSql("        FROM TGFACT ");
            sql.appendSql("        WHERE AD_ITEMCANCELADO = 'S' ");
            sql.appendSql("    ) ");
            sql.appendSql("    WHERE RN = 1 ");
            sql.appendSql(") ACT ON ACT.NUNOTA = ITE.NUNOTA AND ACT.SEQUENCIA = ITE.SEQUENCIA ");
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
                        .append("\"pendente\":\"").append(escapeJson(rs.getString("PENDENTE"))).append("\",")
                        .append("\"cancelado\":\"").append(escapeJson(rs.getString("AD_ITEMCANCELADO"))).append("\",")
                        .append("\"ocorrenciaCancelamento\":\"").append(escapeJson(rs.getString("OCORRENCIA_CANCELAMENTO"))).append("\"")
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
            min-height: 100vh;
            overflow: hidden;
            font-family: "Segoe UI", Arial, Helvetica, sans-serif;
            color: #172033;
            background:
                radial-gradient(circle at top left, rgba(22, 163, 74, 0.12), transparent 32%),
                linear-gradient(135deg, #eef3f8 0%, #f8fafc 52%, #eef4ef 100%);
            font-size: 13px;
        }

        button,
        input,
        select,
        textarea {
            font: inherit;
        }

        .app {
            width: 100%;
            max-width: none;
            height: 100vh;
            margin: 0;
            padding: 16px 18px 20px;
            background: #f4f7fa;
            border: none;
            border-radius: 0;
            box-shadow: none;
            overflow: auto;
        }

        .topbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 12px;
            margin: -16px -18px 16px;
            padding: 16px 20px;
            background: #fff;
            border-bottom: 1px solid #e7edf3;
            border-radius: 0;
        }

        .top-actions {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .icon-button {
            width: 38px;
            min-height: 38px;
            padding: 0;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-radius: 999px;
            background: #fff;
            border: 1px solid #d5dde7;
            color: #172033;
            font-size: 18px;
            line-height: 1;
        }

        .material-icon-svg {
            width: 22px;
            height: 22px;
            display: block;
            fill: currentColor;
            flex: 0 0 auto;
        }

        .icon-button:hover {
            background: #eefbf1;
            border-color: #a7dfb7;
        }

        .title {
            margin: 0;
            font-size: 21px;
            line-height: 1.2;
            font-weight: 800;
            color: #172033;
        }

        .subtitle {
            color: #667085;
            margin-top: 4px;
            line-height: 1.4;
            max-width: 840px;
        }

        .badge {
            display: inline-block;
            padding: 7px 11px;
            border-radius: 999px;
            background: #eefbf1;
            color: #137333;
            border: 1px solid #a7dfb7;
            font-weight: 800;
            white-space: nowrap;
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
            border: 1px solid #d5dde7;
            border-radius: 9px;
            background: #fff;
            color: #172033;
            padding: 8px 11px;
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
            font-weight: 800;
        }

        button.primary {
            border-color: #0f5132;
            background: #172033;
            color: #fff;
        }

        button.danger {
            border-color: #a7dfb7;
            background: #fff;
            color: #0f5132;
        }

        button.danger:hover {
            background: #eefbf1;
        }

        button:disabled {
            cursor: not-allowed;
            opacity: 0.55;
        }

        .macro-grid {
            display: grid;
            grid-template-columns: repeat(5, minmax(120px, 1fr));
            gap: 10px;
        }

        .kv {
            background: #fff;
            border: 1px solid #dfe7ef;
            border-left: 5px solid #f59e0b;
            border-radius: 10px;
            padding: 12px 13px;
            min-height: 116px;
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
            font-size: 18px;
            line-height: 1.08;
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
            grid-template-columns: minmax(0, 1fr) 318px;
            gap: 14px;
            align-items: start;
            width: 100%;
            min-width: 0;
        }

        main,
        aside {
            min-width: 0;
        }

        main {
            overflow: hidden;
        }

        .panel {
            background: #fff;
            border: 1px solid #dfe7ef;
            border-radius: 12px;
            padding: 14px 16px;
            margin-bottom: 14px;
            box-shadow: 0 8px 18px rgba(15, 23, 42, 0.035);
            min-width: 0;
        }

        .panel h2 {
            margin: 0 0 12px 0;
            font-size: 16px;
            color: #172033;
        }

        .flow {
            display: flex;
            align-items: stretch;
            flex-wrap: wrap;
            gap: 9px;
        }

        .flow-step {
            flex: 1 1 190px;
            border: 1px solid #e2e8f0;
            background: #f8fafc;
            border-radius: 12px;
            padding: 10px 11px;
            min-width: 0;
        }

        .flow-link {
            display: inline-block;
            border: 1px solid #d5dde7;
            background: #fff;
            color: #1d4ed8;
            padding: 6px 9px;
            border-radius: 999px;
            margin: 3px 4px 0 0;
            cursor: pointer;
            font-weight: 800;
        }

        .flow-link:hover {
            background: #eff6ff;
        }

        .doc-row {
            cursor: pointer;
        }

        .doc-row.selected td {
            background: #f1fbf4;
        }

        .doc-row:hover td {
            background: #f8fafc;
        }

        .doc-tag {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 34px;
            padding: 5px 8px;
            border-radius: 999px;
            background: #eaf2ff;
            color: #1d4ed8;
            font-size: 11px;
            font-weight: 900;
        }

        .doc-tag.mi {
            background: #eefbf1;
            color: #0f7a35;
        }

        .doc-type {
            display: flex;
            align-items: center;
            gap: 8px;
            min-width: 190px;
            white-space: nowrap;
        }

        .flow-step b {
            display: block;
            margin-bottom: 4px;
        }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            font-size: 12px;
            min-width: 940px;
        }

        th,
        td {
            border: none;
            border-bottom: 1px solid #e8eef4;
            padding: 12px 10px;
            vertical-align: middle;
            text-align: left;
        }

        th {
            background: #f8fafc;
            color: #475467;
            font-size: 11px;
            font-weight: 800;
            text-transform: uppercase;
            letter-spacing: 0.02em;
        }

        .table-wrap {
            width: 100%;
            max-width: 100%;
            min-width: 0;
            overflow-x: auto;
            border: 1px solid #dfe7ef;
            border-radius: 11px;
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

        .number-green {
            color: #087443;
            font-weight: 900;
        }

        .number-red {
            color: #b42318;
            font-weight: 900;
        }

        tr.blocked {
            background: #fff8f7;
        }

        tr.changed {
            background: #f2fbf5;
        }

        .tag {
            display: inline-block;
            padding: 5px 8px;
            border-radius: 999px;
            font-size: 11px;
            font-weight: 800;
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
            border: 1px solid #dfe7ef;
            border-radius: 11px;
            padding: 14px;
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
            border: 1px solid #cdeed7;
            background: linear-gradient(135deg, #ffffff, #f2fbf5);
            border-left: 0;
            border-radius: 14px;
            padding: 18px;
            margin-bottom: 14px;
            display: block;
            align-items: center;
            box-shadow: 0 8px 18px rgba(15, 23, 42, 0.035);
        }

        .decision-hero-content {
            display: flex;
            align-items: center;
            gap: 18px;
            min-width: 0;
        }

        .decision-icon {
            width: 68px;
            height: 68px;
            display: grid;
            place-items: center;
            flex: 0 0 auto;
            border-radius: 50%;
            background: linear-gradient(135deg, #15803d, #34a853);
            color: #fff;
            font-weight: 900;
            box-shadow: 0 0 0 10px #ddf7e5;
        }

        .decision-icon .material-icon-svg {
            width: 38px;
            height: 38px;
        }

        .decision-hero.warn .decision-icon {
            background: linear-gradient(135deg, #d97706, #f59e0b);
            box-shadow: 0 0 0 10px #fff2d6;
        }

        .decision-hero.block .decision-icon {
            background: linear-gradient(135deg, #b42318, #dc2626);
            box-shadow: 0 0 0 10px #fdecec;
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
            color: #087443;
            text-transform: uppercase;
            letter-spacing: 0.02em;
        }

        .decision-hero p {
            margin: 5px 0 0;
            color: #475467;
            line-height: 1.35;
        }

        .actions {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 8px;
            margin-bottom: 12px;
        }

        .log {
            background: #101828;
            color: #d0d5dd;
            height: 260px;
            overflow: auto;
            padding: 12px;
            border-radius: 11px;
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

        .compact-actions button.primary {
            grid-column: 1 / -1;
            min-height: 42px;
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
            border-radius: 14px;
            border: 1px solid #e1e7ef;
            box-shadow: 0 26px 70px rgba(15, 23, 42, 0.24);
            overflow: hidden;
        }

        .modal.large {
            width: min(760px, 100%);
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

        .modal-head p {
            margin: 5px 0 0;
            color: #667085;
            line-height: 1.35;
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

        .config-grid {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 10px;
        }

        .config-field {
            border: 1px solid #dfe7ef;
            border-radius: 12px;
            padding: 12px;
            background: #f8fafc;
        }

        .config-field span {
            display: block;
            color: #172033;
            font-weight: 800;
            margin-bottom: 4px;
        }

        .config-field small {
            display: block;
            color: #667085;
            line-height: 1.35;
            min-height: 34px;
            margin-bottom: 8px;
        }

        .config-field input {
            background: #fff;
        }

        .button-help-grid {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 10px;
            margin-top: 14px;
        }

        .button-help {
            border: 1px solid #dfe7ef;
            border-radius: 12px;
            padding: 12px;
            background: #fff;
        }

        .button-help b {
            display: block;
            margin-bottom: 4px;
        }

        .button-help p {
            margin: 0;
            color: #667085;
            line-height: 1.35;
        }

        .needs-config {
            border-color: #f59e0b !important;
            background: #fffbeb !important;
            color: #92400e !important;
        }

        @media (max-width: 900px) {
            .layout {
                grid-template-columns: 1fr;
            }

            aside .panel {
                position: static;
            }
        }

        @media (max-width: 980px) {
            .macro-grid,
            .flow,
            .decision-hero {
                grid-template-columns: 1fr;
            }
        }

        @media (max-width: 720px) {
            .app {
                padding: 10px;
            }

            .topbar {
                flex-direction: column;
            }

            .decision-hero-content {
                align-items: flex-start;
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
            <h1 class="title">Nro. Negociação <span id="nuNegociacao"></span></h1>
        </div>
        <div class="top-actions">
            <button class="icon-button" type="button" title="Configurar ações Java" onclick="abrirConfiguracaoAcoes()" id="configActionButton"></button>
        </div>
    </div>
    <input type="hidden" id="codUsu" value="">

    <section class="decision-hero" id="decisionHero">
        <div class="decision-hero-content">
            <div class="decision-icon" id="initialDecisionIcon"></div>
            <div>
            <h2>Avaliando a negociação...</h2>
            <p>Vamos identificar se há saldo para cancelamento e quais itens precisam de atenção.</p>
            </div>
        </div>
    </section>

    <div class="layout">
        <main>
            <section class="panel">
                <h2>Acompanhamento da negociação</h2>
                <div class="subtitle" style="margin-bottom:10px;">
                    Visão macro mantida do dashboard de acompanhamento da negociação. Estes cards explicam o momento geral
                    da proposta antes da validação específica dos itens de suprimentos.
                </div>
                <div class="macro-grid" id="macroGrid"></div>
            </section>

            <section class="panel">
                <h2>Documentos relacionados à negociação</h2>
                <div class="flow" id="flow"></div>
            </section>

            <section class="panel">
                <h2 id="itensTitulo">Itens da nota interna</h2>
                <div id="itensTable"></div>
            </section>

            <section class="panel">
                <h2>Acompanhamentos gerados na simulação</h2>
                <div id="tgfactTable"></div>
            </section>
        </main>

        <aside>
            <section class="panel">
                <h2>Resultado da regra</h2>
                <div class="decision" id="decision"></div>
                <div class="actions compact-actions">
                    <button class="primary" data-action-key="cancelarDocumento" onclick="cancelarItensPossiveis()">Cancelar todos com saldo</button>
                    <button data-action-key="notificarAreas" onclick="notificarAreas()">Notificar áreas</button>
                    <button data-action-key="registrarPendencia" onclick="registrarPendencia()">Registrar pendência</button>
                    <button data-action-key="solicitarAprovacao" onclick="solicitarAprovacao()">Solicitar aprovação</button>
                    <button onclick="limparSimulacao()">Limpar simulação</button>
                </div>
                <div class="log" id="log">Aguardando validação...</div>
            </section>
        </aside>
    </div>
</div>

<div class="modal-backdrop" id="configModal">
    <div class="modal large">
        <div class="modal-head">
            <h3>Configuração das ações</h3>
            <p>Informe manualmente os IDs das Actions Java deste ambiente. Os valores ficam salvos no navegador para este modal.</p>
        </div>
        <div class="modal-body">
            <div class="config-grid" id="configGrid"></div>
            <div class="button-help-grid" id="buttonHelpGrid"></div>
        </div>
        <div class="modal-footer">
            <button onclick="fecharConfiguracaoAcoes()">Cancelar</button>
            <button class="primary" onclick="salvarConfiguracaoAcoes()">Salvar</button>
        </div>
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
                <textarea id="motivoCancelamento" placeholder="Ex.: Pedido cancelado, indisponível no fornecedor, retirado da lojinha."></textarea>
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
    var modoOperacional = String(modo).toUpperCase() === 'OPERACIONAL';
    var actionCancelarDocumentoId = getParam('codAcaoJavaCancelarDocumento') || getParam('codAcaoJavaCancelarSuprimento');
    var ACTIONS = {
        cancelarDocumento: {
            id: actionCancelarDocumentoId,
            label: 'Cancelar todos com saldo',
            parametro: 'codAcaoJavaCancelarDocumento',
            descricao: 'Executa a Action Java que mantém TGFITE.PENDENTE como S e grava TGFACT para os itens com saldo do documento selecionado.'
        },
        notificarAreas: {
            id: getParam('codAcaoJavaNotificarAreas'),
            label: 'Notificar áreas',
            parametro: 'codAcaoJavaNotificarAreas',
            descricao: 'Botão utilitário para acionar uma rotina de aviso para áreas envolvidas, quando existir Action cadastrada.'
        },
        registrarPendencia: {
            id: getParam('codAcaoJavaRegistrarPendencia'),
            label: 'Registrar pendência',
            parametro: 'codAcaoJavaRegistrarPendencia',
            descricao: 'Botão utilitário para registrar pendência operacional da negociação, quando existir Action cadastrada.'
        },
        solicitarAprovacao: {
            id: getParam('codAcaoJavaSolicitarAprovacao'),
            label: 'Solicitar aprovação',
            parametro: 'codAcaoJavaSolicitarAprovacao',
            descricao: 'Botão utilitário para solicitar aprovação gerencial ou administrativa, quando existir Action cadastrada.'
        }
    };
    var parametrosReais = lerParametrosReais();
    var cenario = criarSimulacaoPorNegociacao(nunegociacao);
    var cenarioOriginal = clonar(cenario);
    var documentoSelecionado = chaveDocumentoInicial(cenario);
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

    function normalizarTexto(valor) {
        return valor == null ? '' : String(valor).replace(/^\s+|\s+$/g, '');
    }

    function somenteDigitos(valor) {
        return normalizarTexto(valor).replace(/\D/g, '');
    }

    function escaparHtml(valor) {
        return String(valor || '')
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }

    function limparHtml(valor) {
        return String(valor || '').replace(/<[^>]*>/g, ' ').replace(/\s+/g, ' ').trim();
    }

    function getCookie(nome) {
        var value = '; ' + document.cookie;
        var parts = value.split('; ' + nome + '=');
        if (parts.length === 2) {
            return parts.pop().split(';').shift();
        }
        return '';
    }

    function materialIconSvg(nome) {
        var paths = {
            settings: '<path d="M19.43 12.98c.04-.32.07-.65.07-.98s-.02-.66-.07-.98l2.11-1.65c.19-.15.24-.42.12-.64l-2-3.46c-.12-.22-.37-.31-.6-.22l-2.49 1c-.52-.4-1.08-.73-1.69-.98L14.5 2.42C14.47 2.18 14.25 2 14 2h-4c-.25 0-.46.18-.5.42L9.12 5.07c-.61.25-1.18.59-1.69.98l-2.49-1c-.23-.08-.48 0-.6.22l-2 3.46c-.13.22-.07.49.12.64l2.11 1.65c-.04.32-.08.65-.08.98s.03.66.08.98l-2.11 1.65c-.19.15-.24.42-.12.64l2 3.46c.12.22.37.31.6.22l2.49-1c.52.4 1.08.73 1.69.98l.38 2.65c.04.24.25.42.5.42h4c.25 0 .47-.18.5-.42l.38-2.65c.61-.25 1.18-.59 1.69-.98l2.49 1c.23.08.48 0 .6-.22l2-3.46c.12-.22.07-.49-.12-.64l-2.11-1.65z"/><path d="M12 15.5A3.5 3.5 0 1 1 12 8a3.5 3.5 0 0 1 0 7.5z" fill="#fff"/>',
            check_circle: '<path d="M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20zm-2 15-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>',
            warning: '<path d="M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z"/>',
            block: '<path d="M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20zM4 12a8 8 0 0 1 12.9-6.32L5.68 16.9A7.95 7.95 0 0 1 4 12zm8 8a7.95 7.95 0 0 1-4.9-1.68L18.32 7.1A8 8 0 0 1 12 20z"/>',
            task_alt: '<path d="M22 5.18 10.59 16.6l-4.24-4.24 1.41-1.41 2.83 2.83L20.59 3.77 22 5.18z"/><path d="M19.79 10.22c.13.57.21 1.16.21 1.78a8 8 0 1 1-8-8c1.58 0 3.04.46 4.28 1.25l1.44-1.44A9.96 9.96 0 0 0 12 2a10 10 0 1 0 10 10c0-1.19-.21-2.33-.6-3.39l-1.61 1.61z"/>',
            search_off: '<path d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5C16 5.91 13.09 3 9.5 3c-1.61 0-3.09.59-4.23 1.57L3.7 3 2.39 4.31 19.69 21.6 21 20.29 15.5 14zM9.5 5C11.99 5 14 7.01 14 9.5c0 .97-.31 1.86-.83 2.59L6.91 5.83A4.48 4.48 0 0 1 9.5 5z"/><path d="M9.5 14a4.5 4.5 0 0 1-4.5-4.5c0-.46.07-.9.2-1.31L3.67 6.66A6.47 6.47 0 0 0 3 9.5C3 13.09 5.91 16 9.5 16c1.03 0 2-.24 2.86-.67l-1.54-1.54c-.42.13-.86.21-1.32.21z"/>',
            hourglass_empty: '<path d="M6 2v6h.01L10 12l-3.99 4H6v6h12v-6h-.01L14 12l3.99-4H18V2H6zm10 14.5V20H8v-3.5l4-4 4 4zM12 11 8 7.5V4h8v3.5L12 11z"/>'
        };
        var path = paths[nome] || paths.check_circle;
        return '<svg class="material-icon-svg" viewBox="0 0 24 24" aria-hidden="true">' + path + '</svg>';
    }

    function storageKeyAcoes() {
        return 'momentoNegociacao.actionIds.v1';
    }

    function carregarConfiguracaoAcoes() {
        try {
            var saved = JSON.parse(localStorage.getItem(storageKeyAcoes()) || '{}');
            for (var key in saved) {
                if (saved.hasOwnProperty(key) && ACTIONS[key] && normalizarTexto(saved[key])) {
                    ACTIONS[key].id = normalizarTexto(saved[key]);
                }
            }
        } catch (e) {}

        actionCancelarDocumentoId = ACTIONS.cancelarDocumento.id;
        atualizarEstadoBotoesAcao();
    }

    function atualizarEstadoBotoesAcao() {
        var botoes = document.querySelectorAll('[data-action-key]');

        for (var i = 0; i < botoes.length; i++) {
            var key = botoes[i].getAttribute('data-action-key');
            if (ACTIONS[key] && !somenteDigitos(ACTIONS[key].id)) {
                botoes[i].classList.add('needs-config');
                botoes[i].title = 'Action Java ainda não configurada. Clique na engrenagem para informar o ID.';
            } else {
                botoes[i].classList.remove('needs-config');
                botoes[i].title = ACTIONS[key] ? ACTIONS[key].descricao : '';
            }
        }
    }

    function abrirConfiguracaoAcoes() {
        var html = '';
        var ajuda = '';

        for (var key in ACTIONS) {
            if (!ACTIONS.hasOwnProperty(key)) {
                continue;
            }

            html += '<label class="config-field">'
                + '<span>' + escaparHtml(ACTIONS[key].label) + '</span>'
                + '<small>Parâmetro URL: ' + escaparHtml(ACTIONS[key].parametro) + '</small>'
                + '<input type="text" data-config-key="' + escaparHtml(key) + '" value="' + escaparHtml(ACTIONS[key].id) + '" placeholder="ID da Action Java">'
                + '</label>';

            ajuda += '<div class="button-help">'
                + '<b>' + escaparHtml(ACTIONS[key].label) + '</b>'
                + '<p>' + escaparHtml(ACTIONS[key].descricao) + '</p>'
                + '</div>';
        }

        document.getElementById('configGrid').innerHTML = html;
        document.getElementById('buttonHelpGrid').innerHTML = ajuda;
        document.getElementById('configModal').style.display = 'flex';
    }

    function fecharConfiguracaoAcoes() {
        document.getElementById('configModal').style.display = 'none';
    }

    function salvarConfiguracaoAcoes() {
        var inputs = document.querySelectorAll('[data-config-key]');
        var saved = {};

        for (var i = 0; i < inputs.length; i++) {
            var key = inputs[i].getAttribute('data-config-key');
            if (!ACTIONS[key]) {
                continue;
            }

            ACTIONS[key].id = somenteDigitos(inputs[i].value);
            saved[key] = ACTIONS[key].id;
        }

        localStorage.setItem(storageKeyAcoes(), JSON.stringify(saved));
        actionCancelarDocumentoId = ACTIONS.cancelarDocumento.id;
        fecharConfiguracaoAcoes();
        atualizarEstadoBotoesAcao();
        escreverLog('Configuração das ações', [
            'Cancelar documento: ' + (ACTIONS.cancelarDocumento.id || 'não configurado'),
            'Notificar áreas: ' + (ACTIONS.notificarAreas.id || 'não configurado'),
            'Registrar pendência: ' + (ACTIONS.registrarPendencia.id || 'não configurado'),
            'Solicitar aprovação: ' + (ACTIONS.solicitarAprovacao.id || 'não configurado')
        ]);
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
    document.getElementById('codUsu').value = obterUsuarioLogado();

    function obterUsuarioLogado() {
        var valor = chamarFuncaoUsuario(window)
            || chamarFuncaoUsuario(window.parent)
            || chamarFuncaoUsuario(window.top)
            || getParam('codUsu')
            || getParam('codusu')
            || getParam('codUsuario')
            || getParam('usuarioLogado')
            || '203';

        return String(valor || '').replace(/[^0-9]/g, '') || '203';
    }

    function chamarFuncaoUsuario(escopo) {
        if (!escopo) {
            return '';
        }

        var nomes = ['getusulogado', 'getUsuLogado', 'getUsuarioLogado', 'getCodUsuLogado'];

        for (var i = 0; i < nomes.length; i++) {
            try {
                if (typeof escopo[nomes[i]] === 'function') {
                    return escopo[nomes[i]]();
                }
            } catch (e) {
            }
        }

        return '';
    }

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
            itens.push(item(nunota, 1, 'SUP-' + (numero % 1000), 'Item já entregue', qtdBase, qtdBase, 'S'));
            itens.push(item(nunota, 2, 'SUP-' + ((numero + 1) % 1000), 'Item com saldo pendente', qtdBase + 12, qtdBase, 'S'));
            itens.push(item(nunota, 3, 'SUP-' + ((numero + 2) % 1000), 'Item aguardando fornecedor', qtdBase + 5, 0, 'S'));
        }

        return {
            id: 'selecionada-' + numero,
            nome: 'Validação baseada na linha selecionada',
            cliente: 'Cliente da negociação ' + nuNeg,
            vendedor: 'Vendedor da negociação ' + nuNeg,
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
            fonte: pedidoVendaRealJsp.encontrado ? 'AD_VGLDOC real consultada no JSP' : 'Parâmetros reais recebidos do Java/Sankhya'
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

    function documentosOperacionais(cenario) {
        var docs = [];

        (cenario.documentosGerados || []).forEach(function(documento) {
            docs.push({
                chave: 'PV-' + documento.nunota,
                tipo: 'PV',
                tipoClasse: '',
                descricao: 'Pedido de Venda',
                nunota: documento.nunota,
                numNota: documento.numNota || documento.nunota,
                cliente: cenario.cliente,
                data: '-',
                valor: '-',
                itens: documento.itens || []
            });
        });

        docs.push({
            chave: 'MI-' + cenario.nunota,
            tipo: 'MI',
            tipoClasse: 'mi',
            descricao: 'Mov. Interna',
            nunota: cenario.nunota,
            numNota: cenario.numNota,
            cliente: cenario.cliente,
            data: '-',
            valor: '-',
            itens: cenario.itens || []
        });

        return docs;
    }

    function chaveDocumentoInicial(cenario) {
        var docs = documentosOperacionais(cenario);
        return docs.length ? docs[0].chave : '';
    }

    function documentoAtual() {
        var docs = documentosOperacionais(cenarioAtual());
        var selecionado = docs.filter(function(documento) {
            return documento.chave === documentoSelecionado;
        })[0];

        if (!selecionado && docs.length) {
            documentoSelecionado = docs[0].chave;
            selecionado = docs[0];
        }

        return selecionado || null;
    }

    function itensDocumentoSelecionado() {
        var doc = documentoAtual();
        return doc && doc.itens ? doc.itens : [];
    }

    function selecionarDocumento(chave) {
        documentoSelecionado = chave;
        renderizar();
        var doc = documentoAtual();
        escreverLog('Selecionar documento', [
            'Tipo=' + (doc ? doc.tipo : '-'),
            'NUNOTA=' + (doc ? doc.nunota : '-'),
            'Itens encontrados=' + (doc ? doc.itens.length : 0)
        ]);
    }

    function criarAcompanhamentoDashboard(perfil) {
        if (perfil === 0) {
            return {
                suprimento: 'Completo',
                layout: 'Concluido',
                producao: 'Finalizado',
                faturamento: 'Pendente validação',
                entrega: 'Entregue'
            };
        }

        if (perfil === 1) {
            return {
                suprimento: 'Pendente compra',
                layout: 'Concluido',
                producao: 'Aguardando suprimento',
                faturamento: 'Aguardando',
                entrega: 'Não iniciada'
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
        return itensDocumentoSelecionado().filter(function(item) {
            return Number(item.qtdentregue) === Number(item.qtdneg);
        });
    }

    function itensCancelaveis() {
        return itensDocumentoSelecionado().filter(function(item) {
            return Number(item.qtdentregue) !== Number(item.qtdneg) && !itemJaCancelado(item) && item.pendente === 'S';
        });
    }

    function itensCancelados() {
        return itensDocumentoSelecionado().filter(function(item) {
            return itemJaCancelado(item);
        });
    }

    function itemJaCancelado(item) {
        return item && String(item.cancelado || '').toUpperCase() === 'S';
    }

    function motivoCancelamentoItem(item) {
        return normalizarTexto(item && item.ocorrenciaCancelamento) || 'Motivo não encontrado na TGFACT.';
    }

    function avaliarDashboard() {
        var cenario = cenarioAtual();
        var doc = documentoAtual();
        var itens = itensDocumentoSelecionado();
        var entregues = [];
        var cancelaveis = [];
        var cancelados = [];
        var pendentes = [];
        var semItens = itens.length === 0;

        itens.forEach(function(item) {
            var entregueTotal = Number(item.qtdentregue) === Number(item.qtdneg);

            if (itemJaCancelado(item)) {
                cancelados.push(item);
            } else if (entregueTotal) {
                entregues.push(item);
            } else if (item.pendente === 'S') {
                cancelaveis.push(item);
            } else {
                entregues.push(item);
            }

            if (item.pendente === 'S') {
                pendentes.push(item);
            }
        });

        var status = 'Liberado';
        var severidade = 'ok';
        var acao = 'Pode cancelar';
        var mensagem = 'Itens com saldo permanecem com PENDENTE = S e recebem acompanhamento TGFACT mediante motivo.';

        if (semItens) {
            status = 'Sem itens';
            severidade = 'block';
            acao = 'Conferir pedido';
            mensagem = 'Nenhum item TGFITE foi localizado na simulação.';
        } else if (cancelados.length === itens.length) {
            status = 'Tratado';
            severidade = 'ok';
            acao = 'Já cancelado';
            mensagem = 'Todos os itens deste documento já possuem acompanhamento de cancelamento na TGFACT.';
        } else if (entregues.length === itens.length) {
            status = 'Bloqueado';
            severidade = 'block';
            acao = 'Não cancelar';
            mensagem = 'Todos os itens estão entregues. Não permitir alterar PENDENTE nem cancelar.';
        } else if (entregues.length > 0 && cancelaveis.length > 0) {
            status = 'Parcial';
            severidade = 'warn';
            acao = 'Cancelar saldo';
            mensagem = 'Itens entregues ficam bloqueados; itens com saldo podem ser cancelados.';
        } else if (cancelaveis.length === 0 && cancelados.length > 0) {
            status = 'Tratado';
            severidade = 'ok';
            acao = 'Sem pendência';
            mensagem = 'Não existem itens restantes para cancelamento. Os itens com saldo já foram tratados.';
        }

        return {
            cenario: cenario,
            documento: doc,
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

        renderizarDocumentosRelacionados(cenario);

        renderizarAcompanhamento(cenario);
        renderizarItens(cenario);
        renderizarTgfact(cenario);
        atualizarDecisao(diagnostico);
    }

    function renderizarHeroDecisao(diagnostico) {
        var el = document.getElementById('decisionHero');
        var titulo = 'Cancelamento disponível';
        var texto = 'Existem itens com saldo disponíveis para cancelamento. Informe o motivo e cancele somente os itens permitidos.';
        var icon = 'check_circle';

        if (diagnostico.semItens) {
            titulo = 'Nenhum item encontrado';
            texto = 'Não encontramos itens para validar nesta nota. Confira o rastro da negociação antes de seguir.';
            icon = 'search_off';
        } else if (diagnostico.cancelados.length === diagnostico.totalItens) {
            titulo = 'Cancelamento já tratado';
            texto = 'Todos os itens deste documento já possuem acompanhamento de cancelamento na TGFACT. Não há nova ação de cancelamento disponível.';
            icon = 'task_alt';
        } else if (!diagnostico.cancelaveis.length && diagnostico.cancelados.length > 0) {
            titulo = 'Cancelamento indisponível';
            texto = 'Os itens com saldo deste documento já foram cancelados. Consulte o motivo nos detalhes do item ou nos acompanhamentos TGFACT.';
            icon = 'block';
        } else if (diagnostico.severidade === 'block') {
            titulo = 'Cancelamento bloqueado';
            texto = 'Não existem itens com saldo disponível para cancelar. Itens totalmente entregues ou já tratados não permitem novo cancelamento.';
            icon = 'block';
        } else if (diagnostico.severidade === 'warn') {
            titulo = 'Cancelamento parcial';
            texto = 'Alguns itens não podem ser cancelados, mas ainda existem itens com saldo disponíveis para cancelamento mediante motivo.';
            icon = 'warning';
        }

        el.className = 'decision-hero ' + diagnostico.severidade;
        el.innerHTML = '<div class="decision-hero-content"><div class="decision-icon">' + materialIconSvg(icon) + '</div><div><h2>' + titulo + '</h2><p>' + texto + '</p></div></div>';
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

    function renderizarDocumentosRelacionados(cenario) {
        var docs = documentosOperacionais(cenario);
        var breadcrumb = '<div class="flow" style="margin-bottom:10px;">'
            + flowStep('Negociação', 'Nro. Negociação = ' + nunegociacao)
            + flowStep('Documentos encontrados', docs.length + ' documento(s)')
            + flowStep('Documento selecionado', rotuloDocumento(documentoAtual()))
            + flowStep('Itens (TGFITE)', 'Consulta por NUNOTA selecionado')
            + '</div>';

        var html = breadcrumb + '<div class="table-wrap"><table><thead><tr>'
            + '<th>Tipo de documento</th>'
            + '<th>Nro. único</th>'
            + '<th>Nro. nota</th>'
            + '<th>Origem</th>'
            + '<th>Itens</th>'
            + '<th>Itens com saldo</th>'
            + '<th>Itens bloqueados</th>'
            + '</tr></thead><tbody>';

        docs.forEach(function(doc) {
            var resumo = resumoItensDocumento(doc);
            var selecionado = doc.chave === documentoSelecionado ? ' selected' : '';
            html += '<tr class="doc-row' + selecionado + '" onclick="selecionarDocumento(\'' + doc.chave + '\')">'
                + '<td><span class="doc-type"><span class="doc-tag ' + doc.tipoClasse + '">' + doc.tipo + '</span><span>' + doc.descricao + '</span></span></td>'
                + '<td><b>' + doc.nunota + '</b></td>'
                + '<td>' + (doc.numNota || '-') + '</td>'
                + '<td>' + (doc.tipo === 'PV' ? 'AD_VGLDOC / TIPMOV=P' : 'TGFCAB.AD_NUNEGOCIACAO') + '</td>'
                + '<td>' + resumo.total + '</td>'
                + '<td><b class="number-green">' + resumo.saldo + '</b></td>'
                + '<td><b class="number-red">' + resumo.bloqueados + '</b></td>'
                + '</tr>';
        });

        html += '</tbody></table></div>';
        document.getElementById('flow').innerHTML = html;
    }

    function rotuloDocumento(doc) {
        if (!doc) {
            return '-';
        }

        return doc.descricao + ' ' + doc.nunota;
    }

    function resumoItensDocumento(doc) {
        var itens = doc && doc.itens ? doc.itens : [];
        var saldo = 0;
        var bloqueados = 0;

        itens.forEach(function(item) {
            if (Number(item.qtdentregue) === Number(item.qtdneg)) {
                bloqueados++;
            } else if (!itemJaCancelado(item) && item.pendente === 'S') {
                saldo++;
            }
        });

        return {
            total: itens.length,
            saldo: saldo,
            bloqueados: bloqueados
        };
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
        var doc = documentoAtual();
        var itens = itensDocumentoSelecionado();
        document.getElementById('itensTitulo').innerText = doc
            ? 'Itens do ' + doc.descricao + ': ' + doc.nunota
            : 'Itens do documento selecionado';

        var html = '<table><thead><tr>'
            + '<th class="col-num">NUNOTA</th>'
            + '<th class="col-seq">SEQ.</th>'
            + '<th class="col-ref">REFERÊNCIA</th>'
            + '<th>DESCRIÇÃO</th>'
            + '<th class="col-qtd">QTDNEG</th>'
            + '<th class="col-qtd">ENTREGUE</th>'
            + '<th class="col-qtd">SALDO</th>'
            + '<th class="col-status">PENDENTE</th>'
            + '<th class="col-rule">REGRA</th>'
            + '<th class="col-action">AÇÃO</th>'
            + '</tr></thead><tbody>';

        itens.forEach(function(item) {
            item.nunota = item.nunota || (doc ? doc.nunota : cenario.nunota);
            item.referencia = item.referencia || '-';
            item.descricao = item.descricao || '-';
            item.cancelado = item.cancelado || 'N';

            var entregueTotal = Number(item.qtdentregue) === Number(item.qtdneg);
            var saldo = Number(item.qtdneg) - Number(item.qtdentregue);
            var cancelado = itemJaCancelado(item);
            var linhaClasse = cancelado ? 'changed' : (entregueTotal ? 'blocked' : '');
            var regra = cancelado
                ? '<span class="tag ok">Cancelado anteriormente</span>'
                : (entregueTotal
                    ? '<span class="tag block">Bloqueado: já entregue</span>'
                    : '<span class="tag warn">Aguardando motivo</span>');
            var botao = cancelado
                ? '<button onclick="abrirCancelado(' + item.sequencia + ')">Ver motivo</button>'
                : (entregueTotal
                    ? '<button onclick="abrirBloqueio(' + item.sequencia + ')">Detalhes</button>'
                    : '<button class="danger" onclick="abrirMotivo(' + item.sequencia + ')">Cancelar item</button>');

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
        var linhas = tgfactDoDocumentoAtual(cenario);

        if (!linhas.length) {
            document.getElementById('tgfactTable').innerHTML = '<div class="decision">Nenhum acompanhamento TGFACT simulado ainda.</div>';
            return;
        }

        var html = '<table><thead><tr>'
            + '<th class="col-num">NUNOTA</th>'
            + '<th class="col-seq">SEQ.</th>'
            + '<th>DHOCOR</th>'
            + '<th>HRACT</th>'
            + '<th>CODUSU</th>'
            + '<th>REFERÊNCIA</th>'
            + '<th>OCORRÊNCIAS</th>'
            + '<th>DIGITADO</th>'
            + '<th>AD_ITEMCANCELADO</th>'
            + '</tr></thead><tbody>';

        linhas.forEach(function(row) {
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

    function tgfactDoDocumentoAtual(cenario) {
        var doc = documentoAtual();
        var linhas = [];

        itensDocumentoSelecionado().forEach(function(item) {
            if (!itemJaCancelado(item)) {
                return;
            }

            linhas.push({
                nunota: item.nunota || (doc ? doc.nunota : ''),
                sequencia: item.sequencia,
                dhocor: 'Já gravado',
                hract: '-',
                codusu: '-',
                referencia: 'R',
                ocorrencias: motivoCancelamentoItem(item),
                digitado: 'S',
                adItemCancelado: 'S'
            });
        });

        (cenario.tgfact || []).forEach(function(row) {
            if (!doc || Number(row.nunota) === Number(doc.nunota)) {
                linhas.push(row);
            }
        });

        return linhas;
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
            detalhes += '<li>' + diagnostico.cancelados.length + ' item(ns) já foram cancelados na simulação.</li>';
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
            '<p><b>Item ' + sequencia + ' já foi entregue.</b></p>'
            + '<p>Regra aplicada: quando <b>TGFITE.QTDENTREGUE = TGFITE.QTDNEG</b>, não permitir alterar <b>PENDENTE = S</b>, não permitir cancelar e exibir aviso ao usuário.</p>'
            + '<p>QTDNEG: <b>' + item.qtdneg + '</b><br>QTDENTREGUE: <b>' + item.qtdentregue + '</b></p>';
        document.getElementById('motivoBox').style.display = 'none';
        document.getElementById('confirmarCancelamento').style.display = 'none';
        document.getElementById('motivoModal').style.display = 'flex';
        escreverLog('Bloqueio de cancelamento', ['Item ' + sequencia + ' bloqueado: já foi entregue e não pode cancelar.']);
    }

    function abrirCancelado(sequencia) {
        var item = buscarItem(sequencia);
        itemSelecionado = null;

        document.getElementById('modalTitulo').innerText = 'Item já cancelado';
        document.getElementById('modalMensagem').innerHTML =
            '<p><b>Item ' + sequencia + ' já possui cancelamento registrado.</b></p>'
            + '<p>Foi encontrado acompanhamento em <b>TGFACT</b> com <b>AD_ITEMCANCELADO = S</b>.</p>'
            + '<p><b>Motivo/Ocorrência:</b><br>' + escaparHtml(motivoCancelamentoItem(item)) + '</p>';
        document.getElementById('motivoBox').style.display = 'none';
        document.getElementById('confirmarCancelamento').style.display = 'none';
        document.getElementById('motivoModal').style.display = 'flex';
        escreverLog('Item já cancelado', ['Item ' + sequencia + ': ' + motivoCancelamentoItem(item)]);
    }

    function abrirItensPedidoVenda(nunota) {
        var pedido = (cenarioAtual().documentosGerados || []).filter(function(documento) {
            return Number(documento.nunota) === Number(nunota);
        })[0];

        var itens = pedido && pedido.itens ? pedido.itens : [];
        var html = '';

        if (!pedido) {
            html = '<p>Pedido de Venda não encontrado na lista carregada da AD_VGLDOC.</p>';
        } else if (!itens.length) {
            html = '<p><b>Nro. Único: ' + pedido.nunota + '</b></p><p>Nenhum item encontrado na TGFITE para este Pedido de Venda.</p>';
        } else {
            html += '<p><b>Nro. Único: ' + pedido.nunota + '</b></p>';
            html += '<div class="table-wrap"><table><thead><tr>';
            html += '<th>NUNOTA</th><th>SEQUÊNCIA</th><th>CODPROD</th><th>REFERÊNCIA</th><th>DESCRIÇÃO</th><th>QTDNEG</th><th>QTDENTREGUE</th><th>SALDO</th><th>PENDENTE</th><th>REGRA</th><th>AÇÃO</th>';
            html += '</tr></thead><tbody>';

            itens.forEach(function(item) {
                item.nunota = item.nunota || pedido.nunota;
                item.referencia = item.referencia || '-';
                item.cancelado = item.cancelado || 'N';

                var entregueTotal = Number(item.qtdentregue) === Number(item.qtdneg);
                var saldo = Number(item.qtdneg) - Number(item.qtdentregue);
                var cancelado = itemJaCancelado(item);
                var regra = cancelado
                    ? '<span class="tag ok">Cancelado anteriormente</span>'
                    : (entregueTotal
                        ? '<span class="tag block">Bloqueado: já entregue</span>'
                        : '<span class="tag warn">Aguardando motivo</span>');
                var acao = cancelado
                    ? '<button onclick="abrirCanceladoPedidoVenda(' + pedido.nunota + ',' + item.sequencia + ')">Ver motivo</button>'
                    : (entregueTotal
                        ? '<button onclick="bloquearItemPedidoVenda(' + pedido.nunota + ',' + item.sequencia + ')">Ver bloqueio</button>'
                        : '<button class="danger" onclick="abrirMotivoPedidoVenda(' + pedido.nunota + ',' + item.sequencia + ')">Cancelar item</button>');

                html += '<tr>';
                html += '<td>' + item.nunota + '</td>';
                html += '<td>' + item.sequencia + '</td>';
                html += '<td>' + item.codprod + '</td>';
                html += '<td>' + (item.referencia || '-') + '</td>';
                html += '<td>' + (item.descricao || '-') + (cancelado ? '<br><small>' + escaparHtml(motivoCancelamentoItem(item)) + '</small>' : '') + '</td>';
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
            '<p><b>Pedido de Venda ' + nunota + ' / Item ' + sequencia + ' já foi entregue.</b></p>'
            + '<p>Regra aplicada: quando <b>TGFITE.QTDENTREGUE = TGFITE.QTDNEG</b>, não permitir alterar <b>PENDENTE</b> nem cancelar.</p>'
            + '<p>QTDNEG: <b>' + item.qtdneg + '</b><br>QTDENTREGUE: <b>' + item.qtdentregue + '</b></p>';
        document.getElementById('motivoBox').style.display = 'none';
        document.getElementById('confirmarCancelamento').style.display = 'none';
        document.getElementById('motivoModal').style.display = 'flex';
        escreverLog('Bloqueio Pedido de Venda', ['NUNOTA=' + nunota, 'SEQUÊNCIA=' + sequencia, 'Motivo=já entregue']);
    }

    function abrirCanceladoPedidoVenda(nunota, sequencia) {
        var item = buscarItemPedidoVenda(nunota, sequencia);

        if (!item) {
            return;
        }

        document.getElementById('modalTitulo').innerText = 'Item já cancelado';
        document.getElementById('modalMensagem').innerHTML =
            '<p><b>Pedido de Venda ' + nunota + ' / Item ' + sequencia + ' já possui cancelamento registrado.</b></p>'
            + '<p>Foi encontrado acompanhamento em <b>TGFACT</b> com <b>AD_ITEMCANCELADO = S</b>.</p>'
            + '<p><b>Motivo/Ocorrência:</b><br>' + escaparHtml(motivoCancelamentoItem(item)) + '</p>';
        document.getElementById('motivoBox').style.display = 'none';
        document.getElementById('confirmarCancelamento').style.display = 'none';
        document.getElementById('motivoModal').style.display = 'flex';
        escreverLog('Item Pedido de Venda já cancelado', [
            'NUNOTA=' + nunota,
            'SEQUÊNCIA=' + sequencia,
            'Motivo=' + motivoCancelamentoItem(item)
        ]);
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
            + '<p class="muted">Na rotina real, isso representa manter <b>TGFITE.PENDENTE</b> como <b>S</b> e gerar acompanhamento em <b>TGFACT</b> para o Pedido de Venda.</p>';
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
            + '<p class="muted">Na rotina real, isso representa manter <b>TGFITE.PENDENTE</b> como <b>S</b> e gerar acompanhamento em <b>TGFACT</b>.</p>';
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
            document.getElementById('modalMensagem').innerHTML += '<p><span class="tag block">Informe o motivo para gravar em OCORRÊNCIAS.</span></p>';
            return;
        }

        if (modoOperacional) {
            executarCancelamentoViaAction(itemSelecionado, motivo.trim());
            return;
        }

        if (Array.isArray(itemSelecionado)) {
            simularCancelamentoEmLote(itemSelecionado, motivo.trim());
        } else {
            simularCancelamento(itemSelecionado, motivo.trim());
        }
        fecharModal();
    }

    function executarCancelamentoViaAction(selecao, motivo) {
        actionCancelarDocumentoId = ACTIONS.cancelarDocumento.id;
        var actionId = somenteDigitos(actionCancelarDocumentoId);
        var doc = documentoAtual();

        if (!actionId) {
            document.getElementById('modalMensagem').innerHTML += '<p><span class="tag block">Configure o parâmetro codAcaoJavaCancelarDocumento com o ID da Action Java de cancelamento.</span></p>';
            return;
        }

        if (!doc || !doc.nunota || !doc.tipo) {
            document.getElementById('modalMensagem').innerHTML += '<p><span class="tag block">Documento selecionado não identificado para envio à Action Java.</span></p>';
            return;
        }

        var params = {
            param: [
                { paramName: 'P_NUNOTA_DOCUMENTO', name: 'P_NUNOTA_DOCUMENTO', type: 'N', $: String(doc.nunota) },
                { paramName: 'P_TIPO_DOCUMENTO', name: 'P_TIPO_DOCUMENTO', type: 'S', $: String(doc.tipo) },
                { paramName: 'P_MOTIVO', name: 'P_MOTIVO', type: 'S', $: motivo }
            ]
        };

        if (!Array.isArray(selecao)) {
            params.param.push({ paramName: 'P_SEQUENCIA', name: 'P_SEQUENCIA', type: 'N', $: String(selecao.sequencia) });
        }

        var rows = {
            row: [{
                field: [
                    { fieldName: 'NUNEGOCIACAO', type: 'N', $: String(nunegociacao) }
                ]
            }]
        };

        var sid = getCookie('JSESSIONID').replace('.master', '');
        var url = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession=' + sid;
        var body = {
            serviceName: 'ActionButtonsSP.executeJava',
            requestBody: {
                javaCall: {
                    actionID: String(actionId),
                    refreshType: 'SEL',
                    params: params,
                    rows: rows
                }
            }
        };

        document.getElementById('confirmarCancelamento').disabled = true;
        document.getElementById('confirmarCancelamento').innerText = 'Executando...';

        escreverLog('Chamando Action Java', [
            'Action ID: ' + actionId,
            'NUNEGOCIACAO: ' + nunegociacao,
            'Documento: ' + rotuloDocumento(doc),
            'Operação: ' + (Array.isArray(selecao) ? 'cancelar todos com saldo' : 'cancelar item ' + selecao.sequencia)
        ]);

        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        })
            .then(function(resp) { return resp.json(); })
            .then(function(resp) {
                var ok = String(resp && resp.status) === '1' || String(resp && resp.status) === '2';
                var msg = (resp && (resp.statusMessage || resp.message)) || JSON.stringify(resp);

                document.getElementById('confirmarCancelamento').disabled = false;
                document.getElementById('confirmarCancelamento').innerText = 'Confirmar';

                if (!ok) {
                    document.getElementById('modalMensagem').innerHTML += '<p><span class="tag block">' + escaparHtml(limparHtml(msg || 'Erro ao executar Action Java.')) + '</span></p>';
                    escreverLog('Erro Action Java', [
                        'Documento: ' + rotuloDocumento(doc),
                        'Retorno: ' + limparHtml(msg || 'Erro não identificado')
                    ]);
                    return;
                }

                if (Array.isArray(selecao)) {
                    simularCancelamentoEmLote(selecao, motivo);
                } else {
                    simularCancelamento(selecao, motivo);
                }

                escreverLog('Action Java executada', [
                    'Documento: ' + rotuloDocumento(doc),
                    'Retorno: ' + limparHtml(msg || 'Ação executada.')
                ]);
                fecharModal();
            })
            .catch(function(err) {
                document.getElementById('confirmarCancelamento').disabled = false;
                document.getElementById('confirmarCancelamento').innerText = 'Confirmar';
                document.getElementById('modalMensagem').innerHTML += '<p><span class="tag block">Erro ao chamar Action Java: ' + escaparHtml(err && err.message ? err.message : String(err)) + '</span></p>';
                escreverLog('Falha chamada Action Java', [
                    'Documento: ' + rotuloDocumento(doc),
                    'Erro: ' + (err && err.message ? err.message : String(err))
                ]);
            });
    }

    function simularCancelamento(item, motivo, silencioso) {
        var cenario = cenarioAtual();
        var agora = new Date();

        item.pendente = 'S';
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

        if (!silencioso) {
            ultimaAcao = 'cancelar';
            renderizar();
            escreverLog('Cancelar item ' + item.sequencia, [
                'TGFITE.NUNOTA: ' + item.nunota,
                'TGFITE.SEQUENCIA: ' + item.sequencia,
                'TGFITE.PENDENTE: S',
                'TGFACT.REFERENCIA: R',
                'TGFACT.DIGITADO: S',
                'TGFACT.AD_ITEMCANCELADO: S',
                'TGFACT.OCORRENCIAS: Pedido de compra possui itens cancelados! Motivo: ' + motivo
            ]);
        }
    }

    function cancelarItensPossiveis() {
        var cancelaveis = avaliarDashboard().cancelaveis;
        var doc = documentoAtual();

        if (!cancelaveis.length) {
            escreverLog('Cancelar possíveis', ['Nenhum item apto para cancelamento simulado nesta negociação selecionada.']);
            return;
        }

        itemSelecionado = cancelaveis;
        document.getElementById('modalTitulo').innerText = 'Motivo do cancelamento em lote';
        document.getElementById('modalMensagem').innerHTML =
            '<p>Documento selecionado: <b>' + rotuloDocumento(doc) + '</b></p>'
            + '<p>Quantidade de itens com saldo: <b>' + cancelaveis.length + '</b></p>'
            + '<p>Ao confirmar, todos os itens canceláveis deste documento permanecerão como <b>PENDENTE = S</b> na simulação e receberão acompanhamento em <b>TGFACT</b>.</p>';
        document.getElementById('motivoCancelamento').value = '';
        document.getElementById('motivoBox').style.display = 'block';
        document.getElementById('confirmarCancelamento').style.display = 'inline-block';
        document.getElementById('motivoModal').style.display = 'flex';
        setTimeout(function() {
            document.getElementById('motivoCancelamento').focus();
        }, 100);
    }

    function simularCancelamentoEmLote(itens, motivo) {
        var total = 0;

        itens.forEach(function(item) {
            if (item.cancelado !== 'S') {
                simularCancelamento(item, motivo, true);
                total++;
            }
        });

        ultimaAcao = 'cancelar_lote';
        renderizar();
        escreverLog('Cancelar todos com saldo', [
            'Documento selecionado: ' + rotuloDocumento(documentoAtual()),
            'Itens cancelados na simulação: ' + total,
            'Motivo aplicado em lote: ' + motivo
        ]);
    }

    function executarAcaoUtilitaria(key, linhasSimulacao) {
        var action = ACTIONS[key];
        var actionId = action ? somenteDigitos(action.id) : '';
        var doc = documentoAtual();

        if (!actionId) {
            escreverLog(action ? action.label : 'Ação utilitária', linhasSimulacao.concat([
                'Action Java não configurada. Clique na engrenagem para informar o ID.'
            ]));
            atualizarEstadoBotoesAcao();
            return;
        }

        if (!doc || !doc.nunota || !doc.tipo) {
            escreverLog(action.label, ['Documento selecionado não identificado para envio à Action Java.']);
            return;
        }

        var sid = getCookie('JSESSIONID').replace('.master', '');
        var url = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession=' + sid;
        var body = {
            serviceName: 'ActionButtonsSP.executeJava',
            requestBody: {
                javaCall: {
                    actionID: String(actionId),
                    refreshType: 'SEL',
                    params: {
                        param: [
                            { paramName: 'P_NUNOTA_DOCUMENTO', name: 'P_NUNOTA_DOCUMENTO', type: 'N', $: String(doc.nunota) },
                            { paramName: 'P_TIPO_DOCUMENTO', name: 'P_TIPO_DOCUMENTO', type: 'S', $: String(doc.tipo) },
                            { paramName: 'P_ACAO_UTILITARIA', name: 'P_ACAO_UTILITARIA', type: 'S', $: String(key) }
                        ]
                    },
                    rows: {
                        row: [{
                            field: [
                                { fieldName: 'NUNEGOCIACAO', type: 'N', $: String(nunegociacao) }
                            ]
                        }]
                    }
                }
            }
        };

        escreverLog('Chamando ' + action.label, [
            'Action ID: ' + actionId,
            'NUNEGOCIACAO: ' + nunegociacao,
            'Documento: ' + rotuloDocumento(doc)
        ]);

        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        })
            .then(function(resp) { return resp.json(); })
            .then(function(resp) {
                var ok = String(resp && resp.status) === '1' || String(resp && resp.status) === '2';
                var msg = (resp && (resp.statusMessage || resp.message)) || JSON.stringify(resp);
                escreverLog(ok ? action.label + ' executada' : 'Erro ' + action.label, [
                    'Documento: ' + rotuloDocumento(doc),
                    'Retorno: ' + limparHtml(msg || (ok ? 'Ação executada.' : 'Erro não identificado'))
                ]);
            })
            .catch(function(err) {
                escreverLog('Falha ' + action.label, [
                    'Documento: ' + rotuloDocumento(doc),
                    'Erro: ' + (err && err.message ? err.message : String(err))
                ]);
            });
    }

    function notificarAreas() {
        var diagnostico = avaliarDashboard();
        executarAcaoUtilitaria('notificarAreas', [
            'Áreas sugeridas: Suprimentos, Faturamento, Produção e Entrega',
            'Documento selecionado: ' + rotuloDocumento(diagnostico.documento),
            'Itens bloqueados: ' + diagnostico.entregues.length,
            'Itens com saldo: ' + diagnostico.cancelaveis.length,
            'Simulação: nenhuma notificação real enviada'
        ]);
    }

    function registrarPendencia() {
        var diagnostico = avaliarDashboard();
        executarAcaoUtilitaria('registrarPendencia', [
            'Pendência operacional simulada para acompanhamento da negociação',
            'Documento selecionado: ' + rotuloDocumento(diagnostico.documento),
            'Motivo sugerido: revisar itens bloqueados antes de concluir cancelamento',
            'Simulação: nenhum registro real gravado'
        ]);
    }

    function solicitarAprovacao() {
        var diagnostico = avaliarDashboard();
        executarAcaoUtilitaria('solicitarAprovacao', [
            'Aprovação gerencial simulada para cancelamento com pendências',
            'Documento selecionado: ' + rotuloDocumento(diagnostico.documento),
            'Bloqueios atuais: ' + diagnostico.entregues.length,
            'Simulação: nenhuma aprovação real enviada'
        ]);
    }

    function limparSimulacao() {
        cenario = clonar(cenarioOriginal);
        documentoSelecionado = chaveDocumentoInicial(cenario);
        ultimaAcao = '';
        itemSelecionado = null;
        renderizar();
        document.getElementById('log').innerText = 'Simulação restaurada para o estado inicial.';
    }

    function gerarResumoTecnico() {
        escreverLog('Resumo técnico', montarResultado());
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
        linhas.push('Documento selecionado = ' + rotuloDocumento(diagnostico.documento));
        linhas.push('Quantidade de itens encontrados na TGFITE do documento selecionado = ' + diagnostico.totalItens);
        linhas.push('Status dashboard = ' + diagnostico.status + ' / ' + diagnostico.acao);
        linhas.push('Acompanhamento AD_TCRNEGCAB: suprimento=' + cenario.acompanhamento.suprimento
            + ', layout=' + cenario.acompanhamento.layout
            + ', producao=' + cenario.acompanhamento.producao
            + ', faturamento=' + cenario.acompanhamento.faturamento
            + ', entrega=' + cenario.acompanhamento.entrega);
        linhas.push('Modo: ' + modo + ' / nenhuma gravação executada');
        linhas.push('');

        diagnostico.itens.forEach(function(item) {
            var regra = Number(item.qtdentregue) === Number(item.qtdneg)
                ? 'BLOQUEADO: já entregue'
                : (item.cancelado === 'S' ? 'CANCELADO_SIMULADO' : 'PERMITIDO: pendente permanece S com TGFACT');
            linhas.push('- Seq ' + item.sequencia + ' | QTDNEG=' + item.qtdneg + ' | QTDENTREGUE=' + item.qtdentregue + ' | PENDENTE=' + item.pendente + ' | ' + regra);
        });

        linhas.push('');
        linhas.push('Resumo: bloqueados=' + diagnostico.entregues.length
            + ', canceláveis=' + diagnostico.cancelaveis.length
            + ', cancelados=' + diagnostico.cancelados.length
            + ', TGFACT=' + cenario.tgfact.length);

        return linhas;
    }

    function buscarItem(sequencia) {
        return itensDocumentoSelecionado().filter(function(item) {
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

    document.getElementById('configActionButton').innerHTML = materialIconSvg('settings');
    document.getElementById('initialDecisionIcon').innerHTML = materialIconSvg('hourglass_empty');
    carregarConfiguracaoAcoes();
    renderizar();
</script>
</body>
</html>
