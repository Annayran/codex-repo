<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Particionamento de Pedidos</title>
    <style>
        :root {
            --zaya-green: #004b32;
            --zaya-accent: #00a85a;
            --zaya-mint: #edf8ee;
            --zaya-yellow: #ffdf2f;
            --ink: #14382b;
            --muted: #66756f;
            --line: #dce8dd;
            --page: #f6faf4;
            --surface: #ffffff;
            --danger: #a33a2d;
            --danger-bg: #fff4f1;
        }
        * { box-sizing: border-box; }
        html { height: 100%; overflow: hidden; }
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            color: var(--ink);
            background: var(--page);
            font-size: 13px;
            height: 100%;
            overflow: hidden;
        }
        .app {
            height: 100vh;
            min-height: 0;
            display: grid;
            grid-template-rows: 68px minmax(0, 1fr) 56px;
            background:
                linear-gradient(90deg, rgba(255,223,47,0.08), transparent 42%),
                var(--page);
        }
        .header {
            position: relative;
            overflow: hidden;
            background: #ffffff;
            border-bottom: 1px solid var(--line);
            padding: 10px 18px;
            display: flex;
            justify-content: space-between;
            gap: 18px;
            align-items: center;
            color: var(--ink);
        }
        .header:after {
            content: "";
            position: absolute;
            inset: 0 0 0 auto;
            width: 44%;
            background: url("https://www.skala.com.br/imagens/banner-institucional.png") center right / cover no-repeat;
            opacity: 0.12;
            pointer-events: none;
        }
        .header > * { position: relative; z-index: 1; }
        .brand-line { display: flex; align-items: center; gap: 12px; min-width: 0; }
        .brand-logo {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 102px;
            min-width: 102px;
            height: 42px;
            border-radius: 6px;
            background: var(--zaya-yellow);
            border: 1px solid rgba(0,0,0,0.05);
            overflow: hidden;
        }
        .brand-logo img { max-width: 88px; max-height: 34px; display: block; }
        .brand-logo span { color: var(--zaya-green); font-weight: 900; font-size: 18px; }
        .title { margin: 0; font-size: 18px; font-weight: 800; line-height: 1.1; }
        .subtitle { margin-top: 3px; color: var(--muted); font-size: 12px; }
        .badge {
            display: inline-flex;
            align-items: center;
            min-height: 28px;
            padding: 5px 11px;
            border: 1px solid #c9ead1;
            border-radius: 999px;
            background: #f3fff6;
            color: var(--zaya-green);
            font-weight: 700;
            white-space: nowrap;
        }
        .content {
            padding: 12px 16px;
            display: grid;
            grid-template-columns: 320px minmax(620px, 1fr) 330px;
            gap: 12px;
            min-height: 0;
            overflow: hidden;
        }
        .panel {
            background: var(--surface);
            border: 1px solid var(--line);
            border-radius: 6px;
            box-shadow: none;
            overflow: hidden;
            min-width: 0;
            min-height: 0;
            display: flex;
            flex-direction: column;
        }
        .panel-header {
            padding: 10px 12px;
            border-bottom: 1px solid var(--line);
            font-weight: 800;
            background: #fbfdfb;
        }
        .panel-header:before {
            content: "";
            display: inline-block;
            width: 6px;
            height: 18px;
            border-radius: 6px;
            background: var(--zaya-accent);
            vertical-align: -4px;
            margin-right: 8px;
        }
        .panel-body { padding: 12px; }
        .panel > .panel-body { overflow: auto; min-height: 0; }
        .field { margin-bottom: 12px; }
        .field label {
            display: block;
            margin-bottom: 5px;
            color: var(--muted);
            font-weight: 700;
        }
        input, select, textarea {
            width: 100%;
            min-height: 34px;
            border: 1px solid #b8cfc0;
            border-radius: 8px;
            background: #fff;
            padding: 7px 8px;
            color: var(--ink);
            outline: none;
        }
        input[type=checkbox] { width: 16px; min-height: 16px; }
        input.inline-num { width: 72px; min-height: 30px; padding: 5px 6px; text-align: right; }
        input.inline-text { width: 72px; min-height: 30px; padding: 5px 6px; }
        input:focus, select:focus, textarea:focus {
            border-color: var(--zaya-accent);
            box-shadow: 0 0 0 3px rgba(0,168,89,0.14);
        }
        textarea { min-height: 68px; resize: vertical; }
        .tabs {
            display: flex;
            gap: 4px;
            padding: 9px 10px 0;
            border-bottom: 1px solid var(--line);
            background: #fbfdfb;
            overflow-x: auto;
            flex: 0 0 auto;
        }
        .tab {
            border: 1px solid var(--line);
            border-bottom: none;
            border-radius: 6px 6px 0 0;
            background: #f4faf5;
            padding: 8px 11px;
            cursor: pointer;
            font-weight: 700;
            white-space: nowrap;
        }
        .tab.active {
            background: #fff;
            color: var(--zaya-green);
            box-shadow: inset 0 3px 0 var(--zaya-yellow);
        }
        .tab-content { display: none; padding: 12px; min-height: 0; overflow: auto; }
        .tab-content.active { display: block; flex: 1 1 auto; }
        .toolbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 10px;
            margin-bottom: 10px;
            flex-wrap: wrap;
        }
        .toolbar input { max-width: 280px; }
        .table-wrap { max-height: 360px; overflow: auto; border: 1px solid var(--line); border-radius: 6px; }
        table { width: 100%; border-collapse: collapse; background: #fff; }
        th, td {
            border-bottom: 1px solid var(--line);
            border-right: 1px solid var(--line);
            padding: 7px 8px;
            text-align: left;
            vertical-align: middle;
        }
        th {
            background: var(--zaya-mint);
            color: var(--zaya-green);
            position: sticky;
            top: 0;
            z-index: 1;
        }
        tbody tr:nth-child(even) { background: #fbfdfb; }
        tbody tr:hover { background: #fff9dc; }
        tbody tr.row-selected { background: #eefaf2; }
        tbody tr.row-priority td:first-child { box-shadow: inset 4px 0 0 var(--zaya-yellow); }
        td.num, th.num { text-align: right; }
        .check-cell { width: 34px; text-align: center; }
        .mini {
            display: block;
            margin-top: 2px;
            color: var(--muted);
            font-size: 11px;
        }
        .calc-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 8px;
            margin-bottom: 10px;
        }
        .summary-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 8px; }
        .capacity-box {
            border: 1px solid #bde5c9;
            border-radius: 8px;
            background: linear-gradient(180deg, #ffffff, #f5fff7);
            padding: 10px;
            margin-bottom: 12px;
        }
        .capacity-head {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
            margin-bottom: 7px;
        }
        .capacity-head span { color: var(--muted); font-weight: 700; }
        .capacity-head b { color: var(--zaya-green); font-size: 18px; }
        .capacity-box input { font-size: 18px; font-weight: 800; color: var(--zaya-green); }
        .capacity-note {
            margin-top: 7px;
            color: var(--muted);
            font-size: 12px;
            line-height: 1.35;
        }
        .summary-line {
            display: flex;
            gap: 6px;
            flex-wrap: wrap;
            margin: 10px 0 2px;
        }
        .metric {
            border: 1px solid var(--line);
            border-radius: 6px;
            background: #ffffff;
            padding: 10px;
        }
        .metric span { display: block; color: var(--muted); margin-bottom: 5px; }
        .metric b { font-size: 16px; color: var(--zaya-green); }
        .pill {
            display: inline-block;
            padding: 3px 7px;
            border: 1px solid #b8cfc0;
            border-radius: 999px;
            background: #f7fbf8;
            font-weight: 700;
            white-space: nowrap;
        }
        .pill.ok { background: var(--zaya-mint); color: #106236; border-color: #9edbb7; }
        .pill.warn { background: #fff7d6; color: #765600; border-color: var(--zaya-yellow); }
        .pill.mix { background: #eef6ff; color: #1b5da8; border-color: #bbd9ff; }
        .hint {
            border-left: 4px solid var(--zaya-yellow);
            background: #fffbe8;
            border-radius: 6px;
            padding: 10px;
            margin-top: 12px;
            color: #4e4612;
        }
        .log {
            margin-top: 12px;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid var(--line);
            background: #f8fbf8;
            color: var(--ink);
            min-height: 96px;
            max-height: 180px;
            font-family: Consolas, monospace;
            white-space: pre-wrap;
            overflow: auto;
        }
        .log.error { border-color: #f1c7bd; background: var(--danger-bg); color: var(--danger); }
        #payloadPreview { max-height: 430px; background: #fbfdfb; color: #244336; }
        .sim-card {
            border: 1px solid var(--line);
            border-radius: 8px;
            margin-bottom: 10px;
            overflow: hidden;
        }
        .sim-card h4 {
            margin: 0;
            padding: 9px 10px;
            background: #eef7f1;
        }
        .sim-card .sim-body { padding: 10px; }
        .oe-list { display: grid; gap: 10px; }
        .oe-card {
            border: 1px solid var(--line);
            border-radius: 8px;
            background: #fff;
            overflow: hidden;
        }
        .oe-card summary {
            display: grid;
            grid-template-columns: 1.25fr 1fr 0.7fr 0.7fr 0.7fr;
            gap: 10px;
            align-items: center;
            padding: 10px 12px;
            cursor: pointer;
            background: #fbfdfb;
            list-style: none;
        }
        .oe-card summary::-webkit-details-marker { display: none; }
        .oe-card summary:before {
            content: "+";
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 20px;
            height: 20px;
            border-radius: 999px;
            background: var(--zaya-mint);
            color: var(--zaya-green);
            font-weight: 900;
            margin-right: 4px;
        }
        .oe-card[open] summary:before { content: "-"; }
        .oe-title { display: flex; align-items: center; font-weight: 800; color: var(--zaya-green); }
        .oe-stat span { display: block; color: var(--muted); font-size: 11px; }
        .oe-stat b { color: var(--ink); }
        .oe-items { padding: 10px 12px 12px; border-top: 1px solid var(--line); }
        .oe-actions { margin-bottom: 8px; display: flex; gap: 8px; flex-wrap: wrap; }
        .footer {
            border-top: 1px solid var(--line);
            background: #fff;
            padding: 10px 16px;
            display: flex;
            justify-content: space-between;
            gap: 12px;
            align-items: center;
            color: var(--muted);
            min-height: 56px;
        }
        .footer div { display: flex; gap: 8px; flex-wrap: wrap; justify-content: flex-end; }
        button {
            border: 1px solid #9fcbb3;
            border-radius: 6px;
            background: #fff;
            color: var(--zaya-green);
            min-height: 32px;
            padding: 7px 12px;
            cursor: pointer;
            font-weight: 700;
        }
        button.primary { background: var(--zaya-green); color: #fff; border-color: var(--zaya-green); }
        button:hover { filter: brightness(0.97); }
        .scope-item { display: flex; align-items: flex-start; gap: 8px; margin: 0 0 10px; line-height: 1.35; }
        @media (max-width: 1160px) {
            .content { grid-template-columns: 1fr; }
            .table-wrap { max-height: 360px; }
        }
    </style>
</head>
<body>
<table id="itensSource" style="display:none">
    <tbody></tbody>
</table>

<div class="app">
    <header class="header">
        <div class="brand-line">
            <span class="brand-logo">
                <img src="https://www.skala.com.br/imagens/logo-skala.png" alt="Skala" onerror="this.style.display='none'; this.nextElementSibling.style.display='inline';">
                <span style="display:none">SKALA</span>
            </span>
            <div>
                <h1 class="title">Particionamento de Pedidos <span id="nuNotaLabel"></span></h1>
                <div class="subtitle">Configuração de OEs, mix de SKUs, prioridade, grupos e capacidade de paletes.</div>
            </div>
        </div>
        <span class="badge" id="modoExecucao">CONFIGURACAO</span>
    </header>

    <main class="content">
        <section class="panel">
            <div class="panel-header">Configuração</div>
            <div class="panel-body">
                <div class="capacity-box">
                    <div class="capacity-head">
                        <span>Paletes por OE particionada</span>
                        <b><span id="oesPrevistasConfig">0</span> OEP</b>
                    </div>
                    <input id="capacidadePaletes" type="number" min="1" step="1" value="28" oninput="configuracaoAlterada()">
                    <div class="capacity-note">Este valor limita cada OEP na simulacao e tambem e enviado para a action Java.</div>
                </div>
                <div class="field">
                    <label for="modoParticionamento">Regra de particionamento</label>
                    <select id="modoParticionamento" onchange="configuracaoAlterada()">
                        <option value="PLANILHA_ATUAL">Regra original: paletes inteiros + quebras</option>
                        <option value="PRIORIDADE">Mix com prioridade definida</option>
                        <option value="GRUPO_PERCENTUAL">Mix por percentual do grupo</option>
                    </select>
                </div>
                <div class="field">
                    <label for="observacao">Observacao</label>
                    <textarea id="observacao" placeholder="Opcional: observacao do teste ou criterio usado na simulacao."></textarea>
                </div>
                <div class="summary-grid">
                    <div class="metric"><span>OEs selecionadas</span><b id="nunotaResumo">-</b></div>
                    <div class="metric"><span>Itens da OE</span><b id="qtdLinhas">0</b></div>
                    <div class="metric"><span>Itens no mix</span><b id="qtdSelecionadas">0</b></div>
                    <div class="metric"><span>Paletes no mix</span><b id="paletesSelecionados">0</b></div>
                </div>
                <div class="summary-line">
                    <span class="pill warn"><span id="qtdPriorizadas">0</span> priorizados</span>
                    <span class="pill mix"><span id="qtdGrupos">0</span> grupos</span>
                    <span class="pill ok"><span id="modoResumo">Regra original</span></span>
                </div>
                <div class="hint">
                    Esta tela aplica o escopo aprovado para mix, prioridade e grupos. Regras especiais por cliente, saldo, altura, bitrem e paletização dedicada permanecem fora desta etapa.
                </div>
            </div>
        </section>

        <section class="panel">
            <div class="tabs">
                <button class="tab active" onclick="abrirAba('oes')">OEs</button>
                <button class="tab" onclick="abrirAba('sku')">Itens das OEs</button>
                <button class="tab" onclick="abrirAba('calculo')">Calculo de Palete</button>
                <button class="tab" onclick="abrirAba('grupos')">Grupos</button>
                <button class="tab" onclick="abrirAba('simulacao')">Simulacao</button>
                <button class="tab" onclick="abrirAba('preview')">Técnico</button>
            </div>

            <div id="aba-oes" class="tab-content active">
                <div class="toolbar">
                    <b>OEs selecionadas no Sankhya</b>
                    <span class="pill mix">Abra uma OE para conferir seus itens</span>
                </div>
                <div id="listaOes" class="oe-list"></div>
            </div>

            <div id="aba-sku" class="tab-content">
                <div class="toolbar">
                    <b>Itens carregados da TGFITE</b>
                    <input id="filtroSku" placeholder="Filtrar SKU, grupo ou descricao" oninput="filtrarTabela()">
                    <button onclick="marcarItens(true)">Marcar todos</button>
                    <button onclick="marcarItens(false)">Limpar mix</button>
                </div>
                <div class="table-wrap">
                    <table id="tabelaSku">
                        <thead>
                        <tr>
                            <th class="check-cell">Usar</th>
                            <th class="check-cell">Prior.</th>
                            <th>OE</th>
                            <th>SEQ</th>
                            <th>SKU</th>
                            <th>Descricao</th>
                            <th class="num">QTDNEG</th>
                            <th class="num">Unid/palete</th>
                            <th class="num">Paletes</th>
                            <th>Grupo</th>
                            <th class="num">Ordem prioridade</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>

            <div id="aba-calculo" class="tab-content">
                <div class="toolbar">
                    <b>Validacao do calculo de paletes</b>
                    <span class="pill warn">Formula: QTDNEG / (LASTRO * CAMADAS)</span>
                </div>
                <div class="calc-grid">
                    <div class="metric"><span>Paletes inteiros</span><b id="paletesInteiros">0</b></div>
                    <div class="metric"><span>Quebras</span><b id="paletesQuebra">0</b></div>
                    <div class="metric"><span>Total calculado</span><b id="paletesCalculados">0</b></div>
                    <div class="metric"><span>OEP previstas</span><b id="oesPrevistas">0</b></div>
                </div>
                <div class="table-wrap">
                    <table id="tabelaCalculo">
                        <thead>
                        <tr>
                            <th>Usar</th>
                            <th>OE</th>
                            <th>SKU</th>
                            <th>Descricao</th>
                            <th class="num">QTDNEG</th>
                            <th class="num">Unid/palete</th>
                            <th class="num">Paletes</th>
                            <th class="num">Parte inteira</th>
                            <th class="num">Quebra</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>

            <div id="aba-grupos" class="tab-content">
                <div class="toolbar">
                    <b>Percentual por grupo no total do pedido</b>
                    <span class="pill mix">Usado quando nao houver prioridade</span>
                </div>
                <div class="table-wrap">
                    <table id="tabelaGrupos">
                        <thead>
                        <tr>
                            <th>Grupo</th>
                            <th class="num">Linhas</th>
                            <th class="num">Paletes</th>
                            <th class="num">% do pedido</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>

            <div id="aba-simulacao" class="tab-content">
                <div class="toolbar">
                    <b>Resultado da simulacao</b>
                    <button onclick="simularParticionamento()">Simular regra</button>
                </div>
                <div id="resultadoSimulacao"></div>
            </div>

            <div id="aba-preview" class="tab-content">
                <div class="toolbar">
                    <b>Auditoria técnica da execução</b>
                    <button onclick="montarPayload()">Atualizar dados técnicos</button>
                </div>
                <div class="log" id="payloadPreview">Aguardando montagem dos dados técnicos...</div>
            </div>
        </section>

        <aside class="panel">
            <div class="panel-header">Resumo</div>
            <div class="panel-body">
                <p class="scope-item"><span class="pill ok">OE</span><span>Visualização das OEs selecionadas no Sankhya.</span></p>
                <p class="scope-item"><span class="pill ok">28</span><span>Capacidade padrão por pedido particionado.</span></p>
                <p class="scope-item"><span class="pill mix">Mix</span><span>Composição da OEP com produtos do pedido origem.</span></p>
                <p class="scope-item"><span class="pill warn">Prioridade</span><span>Itens priorizados entram primeiro, respeitando a ordem definida.</span></p>
                <p class="scope-item"><span class="pill warn">Grupo</span><span>Sem prioridade, a distribuição considera o percentual do grupo.</span></p>
                <p class="scope-item"><span class="pill ok">Cálculo</span><span>Paletes = QTDNEG / (LASTRO * CAMADAS); caixas = paletes * (LASTRO * CAMADAS).</span></p>
                <div class="log" id="logTela">Tela carregada somente com o escopo aprovado.</div>
            </div>
        </aside>
    </main>

    <footer class="footer">
        <span>Particionamento Skala: configuração de mix, prioridade e grupos.</span>
        <div>
            <button onclick="simularParticionamento()">Simular</button>
            <button onclick="montarPayload()">Dados técnicos</button>
            <button onclick="desfazerParticionamento()">Desfazer particionamento</button>
            <button class="primary" onclick="enviarParaAction()">Enviar para particionar</button>
        </div>
    </footer>
</div>

<script>
    function getParam(nome) {
        var params = new URLSearchParams(window.location.search);
        return params.get(nome) || '';
    }

    function splitParam(valor) {
        if (!valor) return [];
        return valor.split(',').map(function(v) { return v.trim(); }).filter(function(v) { return v; });
    }

    function getCookie(nome) {
        var cookies = document.cookie ? document.cookie.split(';') : [];
        for (var i = 0; i < cookies.length; i++) {
            var parte = cookies[i].split('=');
            if (parte[0] && parte[0].trim() === nome) {
                return decodeURIComponent(parte.slice(1).join('='));
            }
        }
        return '';
    }

    function esc(valor) {
        return String(valor == null ? '' : valor)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }

    function limparMensagemSankhya(valor) {
        return String(valor == null ? '' : valor)
            .replace(/<br\s*\/?>/gi, '\n')
            .replace(/<\/?b>/gi, '')
            .replace(/&nbsp;/gi, ' ')
            .replace(/&lt;/gi, '<')
            .replace(/&gt;/gi, '>')
            .replace(/&amp;/gi, '&')
            .replace(/\n{3,}/g, '\n\n')
            .trim();
    }

    function registrarLog(mensagem, erro) {
        var log = document.getElementById('logTela');
        log.className = erro ? 'log error' : 'log';
        log.innerText = mensagem;
    }

    var nuNotas = splitParam(getParam('nunotas'));
    if (nuNotas.length === 0 && getParam('nunota')) nuNotas = [getParam('nunota')];
    if (nuNotas.length === 0) nuNotas = ['5085196'];
    var codParcs = splitParam(getParam('codparcs'));
    if (codParcs.length === 0 && getParam('codparc')) codParcs = [getParam('codparc')];
    var nuNota = nuNotas[0];
    var codParc = codParcs[0] || '';
    var modo = getParam('modo') || 'SIMULACAO';
    var codAcaoParticionar = getParam('codAcaoParticionar') || '484';
    var codAcaoDesfazer = getParam('codAcaoDesfazer') || '487';
    var ultimaSimulacao = [];
    var oes = [];

    var itens = [];

    function numero(valor) {
        var n = Number(String(valor == null ? '' : valor).replace(',', '.'));
        return isNaN(n) ? 0 : n;
    }

    function nuNotasSqlSeguro() {
        var nums = [];
        for (var i = 0; i < nuNotas.length; i++) {
            var limpo = String(nuNotas[i] || '').replace(/[^0-9]/g, '');
            if (limpo) nums.push(limpo);
        }
        return nums.length ? nums.join(',') : '0';
    }

    function executarQuery(sql, sucesso, falha) {
        var sid = getCookie('JSESSIONID').replace('.master', '');
        var url = '/mge/service.sbr?serviceName=DbExplorerSP.executeQuery&outputType=json&mgeSession=' + encodeURIComponent(sid);
        var body = {
            serviceName: 'DbExplorerSP.executeQuery',
            requestBody: { sql: sql }
        };
        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json; charset=UTF-8' },
            body: JSON.stringify(body)
        })
            .then(function(resp) { return resp.json(); })
            .then(function(resp) {
                if (resp && resp.responseBody && resp.responseBody.rows) {
                    sucesso(resp.responseBody.rows);
                    return;
                }
                falha((resp && (resp.statusMessage || resp.message)) || JSON.stringify(resp));
            })
            .catch(function(err) {
                falha(err && err.message ? err.message : String(err));
            });
    }

    function carregarItensReaisDaBase() {
        registrarLog('Carregando itens reais da TGFITE/TGFPRO...', false);
        var sqlColunas =
            "SELECT DISTINCT COLUMN_NAME FROM ALL_TAB_COLUMNS " +
            "WHERE TABLE_NAME = 'TGFPRO' " +
            "AND COLUMN_NAME IN ('LASTRO','CAMADAS','CODGRUPOPROD')";
        executarQuery(sqlColunas, function(rows) {
            var colunas = {};
            for (var i = 0; i < rows.length; i++) {
                colunas[String(rows[i][0] || '').toUpperCase()] = true;
            }
            var campoLastro = colunas.LASTRO ? 'LASTRO' : '';
            var campoCamada = colunas.CAMADAS ? 'CAMADAS' : '';
            var temLastroCamada = !!campoLastro && !!campoCamada;
            var exprLastro = campoLastro ? 'NVL(PRO.' + campoLastro + ',0)' : '0';
            var exprCamada = campoCamada ? 'NVL(PRO.' + campoCamada + ',0)' : '0';
            var exprFatorNovo = temLastroCamada
                ? '(CASE WHEN ' + exprLastro + ' > 0 AND ' + exprCamada + ' > 0 THEN ' + exprLastro + ' * ' + exprCamada + ' ELSE 0 END)'
                : '0';
            var exprGrupo = colunas.CODGRUPOPROD ? "NVL(TO_CHAR(PRO.CODGRUPOPROD), 'SEM_GRUPO')" : "'SEM_GRUPO'";
            var exprUnidPalete = exprFatorNovo;
            var exprCxPalete = exprFatorNovo;
            if (!temLastroCamada) {
                registrarLog('Campos obrigatorios TGFPRO.LASTRO e TGFPRO.CAMADAS nao encontrados.', true);
                return;
            }
            var sqlItens =
                'SELECT CAB.NUNOTA, CAB.CODPARC, ITE.SEQUENCIA, ITE.CODPROD, PRO.DESCRPROD, ' +
                'ITE.QTDNEG, ITE.CODVOL, ' +
                exprLastro + ' AS LASTRO, ' +
                exprCamada + ' AS CAMADA, ' +
                exprUnidPalete + ' AS UNID_PALETE, ' +
                exprCxPalete + ' AS CX_PALETE, ' +
                'CASE WHEN ' + exprUnidPalete + ' > 0 THEN ITE.QTDNEG / ' + exprUnidPalete + ' ELSE 0 END AS PALETES, ' +
                exprGrupo + ' AS GRUPO ' +
                'FROM TGFCAB CAB ' +
                'INNER JOIN TGFITE ITE ON ITE.NUNOTA = CAB.NUNOTA ' +
                'INNER JOIN TGFPRO PRO ON PRO.CODPROD = ITE.CODPROD ' +
                'WHERE CAB.NUNOTA IN (' + nuNotasSqlSeguro() + ') ' +
                'ORDER BY CAB.NUNOTA, ITE.SEQUENCIA';
            executarQuery(sqlItens, function(itensRows) {
                popularItensSource(itensRows);
                carregarItensReais();
                renderOes();
                renderSkus();
                renderGrupos();
                renderCalculo();
                atualizarResumo();
                registrarLog(
                    'Itens reais carregados: ' + itens.length + '. ' +
                    'Calculo por QTDNEG / (' + campoLastro + ' * ' + campoCamada + '); caixas por paletes * (' + campoLastro + ' * ' + campoCamada + ').',
                    false
                );
            }, function(erro) {
                registrarLog('Erro ao carregar itens reais:\n' + limparMensagemSankhya(erro), true);
            });
        }, function(erro) {
            registrarLog('Erro ao verificar colunas da TGFPRO:\n' + limparMensagemSankhya(erro), true);
        });
    }

    function popularItensSource(rows) {
        var tbody = document.querySelector('#itensSource tbody');
        tbody.innerHTML = '';
        for (var i = 0; i < rows.length; i++) {
            var r = rows[i];
            var tr = document.createElement('tr');
            tr.setAttribute('data-nunota', r[0] || '');
            tr.setAttribute('data-codparc', r[1] || '');
            tr.setAttribute('data-sequencia', r[2] || '');
            tr.setAttribute('data-codprod', r[3] || '');
            tr.setAttribute('data-descrprod', r[4] || '');
            tr.setAttribute('data-qtdneg', r[5] || '');
            tr.setAttribute('data-codvol', r[6] || '');
            tr.setAttribute('data-lastro', r[7] || '');
            tr.setAttribute('data-camada', r[8] || '');
            tr.setAttribute('data-unidpalete', r[9] || '');
            tr.setAttribute('data-cxpalete', r[10] || '');
            tr.setAttribute('data-paletes', r[11] || '');
            tr.setAttribute('data-grupo', r[12] || 'SEM_GRUPO');
            tbody.appendChild(tr);
        }
    }

    function carregarItensReais() {
        itens = [];
        oes = [];
        var mapaOes = {};
        var linhas = document.querySelectorAll('#itensSource tbody tr');
        for (var i = 0; i < linhas.length; i++) {
            var row = linhas[i];
            var nunotaAtual = row.getAttribute('data-nunota') || '';
            var codParcAtual = row.getAttribute('data-codparc') || '';
            var qtdNeg = numero(row.getAttribute('data-qtdneg'));
            var lastro = numero(row.getAttribute('data-lastro'));
            var camada = numero(row.getAttribute('data-camada'));
            var unidPalete = numero(row.getAttribute('data-unidpalete'));
            var cxPalete = numero(row.getAttribute('data-cxpalete'));
            var paletes = numero(row.getAttribute('data-paletes'));
            var grupo = row.getAttribute('data-grupo') || 'SEM_GRUPO';
            itens.push({
                selecionado: unidPalete > 0,
                nunota: nunotaAtual,
                codParc: codParcAtual,
                seq: numero(row.getAttribute('data-sequencia')),
                codProd: row.getAttribute('data-codprod') || '',
                desc: row.getAttribute('data-descrprod') || '',
                qtdNeg: qtdNeg,
                codVol: row.getAttribute('data-codvol') || '',
                lastro: lastro,
                camada: camada,
                unidPalete: unidPalete,
                cxPalete: cxPalete,
                grupo: grupo,
                priorizado: false,
                ordem: 0,
                paletes: paletes
            });
            if (!mapaOes[nunotaAtual]) {
                mapaOes[nunotaAtual] = {
                    nunota: nunotaAtual,
                    codParc: codParcAtual,
                    linhas: 0,
                    paletes: 0
                };
            }
            mapaOes[nunotaAtual].linhas++;
            mapaOes[nunotaAtual].paletes += paletes;
        }
        for (var n = 0; n < nuNotas.length; n++) {
            if (mapaOes[nuNotas[n]]) {
                oes.push(mapaOes[nuNotas[n]]);
            }
        }
        for (var k in mapaOes) {
            if (mapaOes.hasOwnProperty(k) && nuNotas.indexOf(k) < 0) {
                oes.push(mapaOes[k]);
            }
        }
    }

    document.getElementById('nuNotaLabel').innerText = nuNotas.length === 1 ? '#' + nuNota : '(' + nuNotas.length + ' OEs)';
    document.getElementById('modoExecucao').innerText = modo;
    document.getElementById('nunotaResumo').innerText = nuNotas.length;

    function renderOes() {
        var alvo = document.getElementById('listaOes');
        alvo.innerHTML = '';
        for (var i = 0; i < oes.length; i++) {
            var oe = oes[i];
            var itensOe = itens.filter(function(item) { return item.nunota === oe.nunota; });
            var selecionados = itensOe.filter(function(item) { return item.selecionado; });
            var paletesSelecionados = selecionados.reduce(function(total, item) { return total + item.paletes; }, 0);
            var details = document.createElement('details');
            details.className = 'oe-card';
            if (i === 0) details.setAttribute('open', 'open');
            var html = ''
                + '<summary>'
                + '<div class="oe-title">OE ' + oe.nunota + '</div>'
                + '<div class="oe-stat"><span>Parceiro</span><b>' + (oe.codParc || '-') + '</b></div>'
                + '<div class="oe-stat"><span>Itens</span><b>' + oe.linhas + '</b></div>'
                + '<div class="oe-stat"><span>No mix</span><b>' + selecionados.length + '</b></div>'
                + '<div class="oe-stat"><span>Paletes</span><b>' + paletesSelecionados.toFixed(2) + '</b></div>'
                + '</summary>'
                + '<div class="oe-items">'
                + '<div class="oe-actions">'
                + '<button data-nunota="' + oe.nunota + '" data-usar="S" onclick="acionarOe(this)">Usar todos desta OE</button>'
                + '<button data-nunota="' + oe.nunota + '" data-usar="N" onclick="acionarOe(this)">Remover esta OE do mix</button>'
                + '</div>'
                + '<div class="table-wrap"><table>'
                + '<thead><tr>'
                + '<th>Usar</th><th>Prior.</th><th>SEQ</th><th>SKU</th><th>Descricao</th><th class="num">QTDNEG</th><th class="num">Unid/palete</th><th class="num">Paletes</th><th>Grupo</th><th class="num">Ordem</th>'
                + '</tr></thead><tbody>';
            for (var j = 0; j < itensOe.length; j++) {
                var item = itensOe[j];
                var idx = itens.indexOf(item);
                html += '<tr class="' + (item.selecionado ? 'row-selected ' : '') + (item.priorizado ? 'row-priority' : '') + '">'
                    + '<td class="check-cell"><input type="checkbox" ' + (item.selecionado ? 'checked' : '') + ' onchange="alterarSelecionado(' + idx + ', this.checked)"></td>'
                    + '<td class="check-cell"><input type="checkbox" ' + (item.priorizado ? 'checked' : '') + ' onchange="alterarPriorizado(' + idx + ', this.checked)"></td>'
                    + '<td>' + item.seq + '</td>'
                    + '<td>' + item.codProd + '</td>'
                    + '<td>' + item.desc + '</td>'
                    + '<td class="num">' + item.qtdNeg.toFixed(0) + '</td>'
                    + '<td class="num">' + item.unidPalete.toFixed(0) + '</td>'
                    + '<td class="num">' + item.paletes.toFixed(2) + '</td>'
                    + '<td><input class="inline-text" value="' + item.grupo + '" oninput="alterarGrupo(' + idx + ', this.value)"></td>'
                    + '<td class="num"><input class="inline-num" type="number" min="0" step="1" value="' + item.ordem + '" oninput="alterarOrdem(' + idx + ', this.value)"></td>'
                    + '</tr>';
            }
            html += '</tbody></table></div></div>';
            details.innerHTML = html;
            alvo.appendChild(details);
        }
    }

    function renderSkus() {
        var tbody = document.querySelector('#tabelaSku tbody');
        tbody.innerHTML = '';
        for (var i = 0; i < itens.length; i++) {
            var item = itens[i];
            var tr = document.createElement('tr');
            if (item.selecionado) tr.className += ' row-selected';
            if (item.priorizado) tr.className += ' row-priority';
            tr.setAttribute('data-filtro', (item.nunota + ' ' + item.codProd + ' ' + item.desc + ' ' + item.grupo).toLowerCase());
            tr.innerHTML = ''
                + '<td class="check-cell"><input type="checkbox" ' + (item.selecionado ? 'checked' : '') + ' onchange="alterarSelecionado(' + i + ', this.checked)"></td>'
                + '<td class="check-cell"><input type="checkbox" ' + (item.priorizado ? 'checked' : '') + ' onchange="alterarPriorizado(' + i + ', this.checked)"></td>'
                + '<td>' + item.nunota + '</td>'
                + '<td>' + item.seq + '</td>'
                + '<td>' + item.codProd + '</td>'
                + '<td>' + item.desc + '</td>'
                + '<td class="num">' + item.qtdNeg.toFixed(0) + '</td>'
                + '<td class="num">' + item.unidPalete.toFixed(0) + '</td>'
                + '<td class="num">' + item.paletes.toFixed(2) + '</td>'
                + '<td><input class="inline-text" value="' + item.grupo + '" oninput="alterarGrupo(' + i + ', this.value)"></td>'
                + '<td class="num"><input class="inline-num" type="number" min="0" step="1" value="' + item.ordem + '" oninput="alterarOrdem(' + i + ', this.value)"></td>';
            tbody.appendChild(tr);
        }
    }

    function renderCalculo() {
        var tbody = document.querySelector('#tabelaCalculo tbody');
        tbody.innerHTML = '';
        var totalInteiro = 0;
        var totalQuebra = 0;
        var totalCalc = 0;
        for (var i = 0; i < itens.length; i++) {
            var item = itens[i];
            var inteiro = Math.floor(item.paletes);
            var quebra = Number((item.paletes - inteiro).toFixed(6));
            if (item.selecionado) {
                totalInteiro += inteiro;
                totalQuebra += quebra;
                totalCalc += item.paletes;
            }
            var tr = document.createElement('tr');
            if (item.selecionado) tr.className = 'row-selected';
            tr.innerHTML = ''
                + '<td>' + (item.selecionado ? 'Sim' : 'Nao') + '</td>'
                + '<td>' + item.nunota + '</td>'
                + '<td>' + item.codProd + '</td>'
                + '<td>' + item.desc + '<span class="mini">Formula: QTDNEG ' + item.qtdNeg.toFixed(0) + ' / (' + item.lastro.toFixed(0) + ' * ' + item.camada.toFixed(0) + ') = ' + item.unidPalete.toFixed(0) + '; caixas por palete: ' + item.cxPalete.toFixed(0) + '</span></td>'
                + '<td class="num">' + item.qtdNeg.toFixed(0) + '</td>'
                + '<td class="num">' + item.unidPalete.toFixed(0) + '</td>'
                + '<td class="num">' + item.paletes.toFixed(4) + '</td>'
                + '<td class="num">' + inteiro.toFixed(0) + '</td>'
                + '<td class="num">' + quebra.toFixed(4) + '</td>';
            tbody.appendChild(tr);
        }
        var capacidade = Number(document.getElementById('capacidadePaletes').value || 28);
        document.getElementById('paletesInteiros').innerText = totalInteiro.toFixed(0);
        document.getElementById('paletesQuebra').innerText = totalQuebra.toFixed(4);
        document.getElementById('paletesCalculados').innerText = totalCalc.toFixed(2);
        document.getElementById('oesPrevistas').innerText = capacidade > 0 ? Math.ceil(totalCalc / capacidade) : 0;
    }

    function renderGrupos() {
        var tbody = document.querySelector('#tabelaGrupos tbody');
        tbody.innerHTML = '';
        var grupos = calcularGrupos();
        var total = totalPaletes();
        var nomes = Object.keys(grupos).sort();
        for (var i = 0; i < nomes.length; i++) {
            var g = grupos[nomes[i]];
            var pct = total > 0 ? (g.paletes / total) * 100 : 0;
            var tr = document.createElement('tr');
            tr.innerHTML = ''
                + '<td>' + nomes[i] + '</td>'
                + '<td class="num">' + g.linhas + '</td>'
                + '<td class="num">' + g.paletes.toFixed(2) + '</td>'
                + '<td class="num">' + pct.toFixed(2) + '%</td>';
            tbody.appendChild(tr);
        }
    }

    function abrirAba(nome) {
        var tabs = document.querySelectorAll('.tab');
        var contents = document.querySelectorAll('.tab-content');
        for (var i = 0; i < tabs.length; i++) tabs[i].classList.remove('active');
        for (var j = 0; j < contents.length; j++) contents[j].classList.remove('active');
        for (var k = 0; k < tabs.length; k++) {
            if (tabs[k].getAttribute('onclick') === "abrirAba('" + nome + "')") tabs[k].classList.add('active');
        }
        document.getElementById('aba-' + nome).classList.add('active');
    }

    function filtrarTabela() {
        var filtro = document.getElementById('filtroSku').value.toLowerCase();
        var linhas = document.querySelectorAll('#tabelaSku tbody tr');
        for (var i = 0; i < linhas.length; i++) {
            linhas[i].style.display = linhas[i].getAttribute('data-filtro').indexOf(filtro) >= 0 ? '' : 'none';
        }
    }

    function invalidarSimulacao() {
        ultimaSimulacao = [];
        var alvo = document.getElementById('resultadoSimulacao');
        if (alvo) alvo.innerHTML = '<div class="hint">Simulacao pendente. Clique em Simular para recalcular com a configuracao atual.</div>';
    }

    function configuracaoAlterada() {
        invalidarSimulacao();
        atualizarResumo();
    }

    function rotuloModoParticionamento() {
        var modo = document.getElementById('modoParticionamento').value;
        if (modo === 'PLANILHA_ATUAL') return 'Regra original';
        if (modo === 'PRIORIDADE') return 'Prioridade';
        return 'Grupo percentual';
    }

    function alterarPriorizado(idx, valor) {
        itens[idx].priorizado = valor;
        if (valor) itens[idx].selecionado = true;
        if (valor && !itens[idx].ordem) itens[idx].ordem = proximaOrdemPrioridade();
        if (!valor) itens[idx].ordem = 0;
        invalidarSimulacao();
        renderSkus();
        atualizarResumo();
    }

    function alterarSelecionado(idx, valor) {
        itens[idx].selecionado = valor;
        if (!valor) {
            itens[idx].priorizado = false;
            itens[idx].ordem = 0;
        }
        invalidarSimulacao();
        renderSkus();
        atualizarResumo();
    }

    function marcarItens(valor) {
        for (var i = 0; i < itens.length; i++) {
            itens[i].selecionado = valor;
            if (!valor) {
                itens[i].priorizado = false;
                itens[i].ordem = 0;
            }
        }
        invalidarSimulacao();
        renderSkus();
        atualizarResumo();
    }

    function marcarItensDaOe(nunota, valor) {
        for (var i = 0; i < itens.length; i++) {
            if (itens[i].nunota === nunota) {
                itens[i].selecionado = valor;
                if (!valor) {
                    itens[i].priorizado = false;
                    itens[i].ordem = 0;
                }
            }
        }
        invalidarSimulacao();
        renderSkus();
        atualizarResumo();
    }

    function acionarOe(botao) {
        marcarItensDaOe(botao.getAttribute('data-nunota'), botao.getAttribute('data-usar') === 'S');
    }

    function alterarGrupo(idx, valor) {
        itens[idx].grupo = valor || 'A';
        invalidarSimulacao();
        renderGrupos();
        atualizarResumo();
    }

    function alterarOrdem(idx, valor) {
        itens[idx].ordem = Number(valor || 0);
        itens[idx].priorizado = itens[idx].ordem > 0;
        if (itens[idx].priorizado) itens[idx].selecionado = true;
        invalidarSimulacao();
        atualizarResumo();
    }

    function proximaOrdemPrioridade() {
        var maior = 0;
        for (var i = 0; i < itens.length; i++) if (itens[i].ordem > maior) maior = itens[i].ordem;
        return maior + 1;
    }

    function totalPaletes() {
        var total = 0;
        for (var i = 0; i < itens.length; i++) {
            if (itens[i].selecionado) total += itens[i].paletes;
        }
        return total;
    }

    function calcularGrupos() {
        var grupos = {};
        for (var i = 0; i < itens.length; i++) {
            if (!itens[i].selecionado) continue;
            var nome = itens[i].grupo || 'A';
            if (!grupos[nome]) grupos[nome] = {linhas: 0, paletes: 0};
            grupos[nome].linhas++;
            grupos[nome].paletes += itens[i].paletes;
        }
        return grupos;
    }

    function atualizarResumo() {
        var grupos = calcularGrupos();
        var selecionadas = itens.filter(function(item) { return item.selecionado; }).length;
        var priorizadas = itens.filter(function(item) { return item.selecionado && item.priorizado; }).length;
        var capacidade = Number(document.getElementById('capacidadePaletes').value || 28);
        var total = totalPaletes();
        document.getElementById('qtdLinhas').innerText = itens.length;
        document.getElementById('qtdSelecionadas').innerText = selecionadas;
        document.getElementById('qtdPriorizadas').innerText = priorizadas;
        document.getElementById('qtdGrupos').innerText = Object.keys(grupos).length;
        document.getElementById('paletesSelecionados').innerText = total.toFixed(2);
        var previstas = capacidade > 0 ? Math.ceil(total / capacidade) : 0;
        document.getElementById('oesPrevistas').innerText = previstas;
        document.getElementById('oesPrevistasConfig').innerText = previstas;
        document.getElementById('modoResumo').innerText = rotuloModoParticionamento();
        renderOes();
        renderGrupos();
        renderCalculo();
    }

    function ordenarItensParaRegra() {
        var base = itens.filter(function(item) { return item.selecionado; });
        var priorizados = base.filter(function(item) { return item.priorizado; }).sort(function(a, b) {
            return (a.ordem || 9999) - (b.ordem || 9999);
        });
        var restantes = base.filter(function(item) { return !item.priorizado; }).sort(function(a, b) {
            if (a.grupo !== b.grupo) return a.grupo.localeCompare(b.grupo);
            return b.paletes - a.paletes;
        });
        if (priorizados.length > 0) return priorizados.concat(restantes);
        return ordenarPorPercentualGrupo();
    }

    function ordenarPorPercentualGrupo() {
        var grupos = calcularGrupos();
        return itens.filter(function(item) { return item.selecionado; }).sort(function(a, b) {
            var diff = grupos[b.grupo].paletes - grupos[a.grupo].paletes;
            if (Math.abs(diff) > 0.0001) return diff;
            return b.paletes - a.paletes;
        });
    }

    function temItensPriorizados() {
        for (var i = 0; i < itens.length; i++) {
            if (itens[i].selecionado && (itens[i].priorizado || itens[i].ordem > 0)) return true;
        }
        return false;
    }

    function simularParticionamento() {
        var capacidade = Number(document.getElementById('capacidadePaletes').value || 28);
        var modo = document.getElementById('modoParticionamento').value;
        if (totalPaletes() <= 0) {
            document.getElementById('resultadoSimulacao').innerHTML = '<div class="hint">Nenhum item selecionado para distribuir no mix.</div>';
            registrarLog('Simulação bloqueada: selecione ao menos um item para o mix.', true);
            abrirAba('simulacao');
            return [];
        }
        if (capacidade <= 0) {
            document.getElementById('resultadoSimulacao').innerHTML = '<div class="hint">Informe uma capacidade de paletes maior que zero.</div>';
            registrarLog('Simulacao bloqueada: capacidade invalida.', true);
            abrirAba('simulacao');
            return [];
        }
        if (modo === 'PLANILHA_ATUAL') {
            simularRegraOriginal(capacidade);
            renderSimulacao();
            registrarLog('Simulacao concluida pela regra original para NUNOTA=' + nuNotas.join(',') + ', OEP previstas=' + ultimaSimulacao.length + '.', false);
            abrirAba('simulacao');
            return ultimaSimulacao;
        }
        if (modo === 'GRUPO_PERCENTUAL' && !temItensPriorizados()) {
            simularGrupoPercentual(capacidade);
            renderSimulacao();
            registrarLog('Simulacao concluida por percentual de grupo para NUNOTA=' + nuNotas.join(',') + ', OEP previstas=' + ultimaSimulacao.length + '.', false);
            abrirAba('simulacao');
            return ultimaSimulacao;
        }
        var filas = ordenarItensParaRegra().map(function(item) {
            return {
                seq: item.seq,
                nunota: item.nunota,
                codProd: item.codProd,
                desc: item.desc,
                grupo: item.grupo,
                priorizado: item.priorizado,
                ordem: item.ordem,
                restante: item.paletes
            };
        });
        ultimaSimulacao = [];
        var numero = 1;
        while (filas.length > 0 && numero <= 200) {
            var restanteOe = capacidade;
            var particao = {numero: numero, paletes: 0, itens: []};
            var gruposUsados = {};
            var guard = 0;
            while (restanteOe > 0.0001 && filas.length > 0 && guard < 200) {
                guard++;
                var idx = escolherIndiceMix(filas, gruposUsados);
                var item = filas[idx];
                var usar = Math.min(restanteOe, item.restante);
                particao.itens.push({
                    seq: item.seq,
                    nunota: item.nunota,
                    codProd: item.codProd,
                    grupo: item.grupo,
                    priorizado: item.priorizado,
                    ordem: item.ordem,
                    paletes: Number(usar.toFixed(4))
                });
                gruposUsados[item.grupo] = true;
                item.restante = Number((item.restante - usar).toFixed(4));
                restanteOe = Number((restanteOe - usar).toFixed(4));
                particao.paletes = Number((particao.paletes + usar).toFixed(4));
                if (item.restante <= 0.0001) filas.splice(idx, 1);
            }
            ultimaSimulacao.push(particao);
            numero++;
        }
        renderSimulacao();
        registrarLog('Simulação concluída para NUNOTA=' + nuNota + ', OEP previstas=' + ultimaSimulacao.length + '.', false);
        abrirAba('simulacao');
        return ultimaSimulacao;
    }

    function simularGrupoPercentual(capacidade) {
        var base = ordenarPorPercentualGrupo();
        var filasPorGrupo = {};
        var totalPorGrupo = {};
        for (var i = 0; i < base.length; i++) {
            var grupo = base[i].grupo || 'SEM_GRUPO';
            if (!filasPorGrupo[grupo]) {
                filasPorGrupo[grupo] = [];
                totalPorGrupo[grupo] = 0;
            }
            filasPorGrupo[grupo].push({
                seq: base[i].seq,
                nunota: base[i].nunota,
                codProd: base[i].codProd,
                desc: base[i].desc,
                grupo: grupo,
                priorizado: false,
                ordem: 0,
                restante: base[i].paletes
            });
            totalPorGrupo[grupo] += base[i].paletes;
        }

        ultimaSimulacao = [];
        var numero = 1;
        while (totalRestanteGrupos(filasPorGrupo) > 0.0001 && numero <= 200) {
            var restanteOe = capacidade;
            var totalInicioOe = totalRestanteGrupos(filasPorGrupo);
            var alocadoGrupo = {};
            var particao = {numero: numero, paletes: 0, itens: []};
            var guard = 0;

            while (restanteOe > 0.0001 && totalRestanteGrupos(filasPorGrupo) > 0.0001 && guard < 10000) {
                guard++;
                var grupoEscolhido = escolherGrupoPorDeficit(filasPorGrupo, totalPorGrupo, alocadoGrupo, capacidade, totalInicioOe);
                var fila = primeiraFilaGrupo(filasPorGrupo[grupoEscolhido]);
                if (!fila) {
                    delete filasPorGrupo[grupoEscolhido];
                    continue;
                }

                var meta = metaGrupoNaOe(grupoEscolhido, totalPorGrupo, capacidade, totalInicioOe);
                var jaAlocado = alocadoGrupo[grupoEscolhido] || 0;
                var deficit = Number((meta - jaAlocado).toFixed(6));
                var limitePercentual = deficit > 0.0001 ? deficit : restanteOe;
                var usar = Math.min(fila.restante, restanteOe, limitePercentual);
                if (usar <= 0.0001) usar = Math.min(fila.restante, restanteOe);

                particao.itens.push({
                    seq: fila.seq,
                    nunota: fila.nunota,
                    codProd: fila.codProd,
                    grupo: fila.grupo,
                    priorizado: false,
                    ordem: 0,
                    paletes: Number(usar.toFixed(4))
                });

                alocadoGrupo[grupoEscolhido] = Number((jaAlocado + usar).toFixed(6));
                fila.restante = Number((fila.restante - usar).toFixed(6));
                restanteOe = Number((restanteOe - usar).toFixed(6));
                particao.paletes = Number((particao.paletes + usar).toFixed(4));
                limparFilasVazias(filasPorGrupo[grupoEscolhido]);
            }

            ultimaSimulacao.push(particao);
            numero++;
        }
    }

    function escolherGrupoPorDeficit(filasPorGrupo, totalPorGrupo, alocadoGrupo, capacidade, totalInicioOe) {
        var melhor = null;
        var maiorDeficit = -999999999;
        for (var grupo in filasPorGrupo) {
            if (!primeiraFilaGrupo(filasPorGrupo[grupo])) continue;
            var deficit = metaGrupoNaOe(grupo, totalPorGrupo, capacidade, totalInicioOe) - (alocadoGrupo[grupo] || 0);
            if (melhor === null || deficit > maiorDeficit) {
                melhor = grupo;
                maiorDeficit = deficit;
            }
        }
        return melhor;
    }

    function metaGrupoNaOe(grupo, totalPorGrupo, capacidade, totalInicioOe) {
        if (totalInicioOe <= 0.0001) return 0;
        return Math.min(capacidade, totalInicioOe) * ((totalPorGrupo[grupo] || 0) / totalInicioOe);
    }

    function totalRestanteGrupos(filasPorGrupo) {
        var total = 0;
        for (var grupo in filasPorGrupo) {
            for (var i = 0; i < filasPorGrupo[grupo].length; i++) {
                total += filasPorGrupo[grupo][i].restante;
            }
        }
        return Number(total.toFixed(6));
    }

    function primeiraFilaGrupo(filas) {
        if (!filas) return null;
        for (var i = 0; i < filas.length; i++) {
            if (filas[i].restante > 0.0001) return filas[i];
        }
        return null;
    }

    function limparFilasVazias(filas) {
        if (!filas) return;
        for (var i = filas.length - 1; i >= 0; i--) {
            if (filas[i].restante <= 0.0001) filas.splice(i, 1);
        }
    }

    function escolherIndiceMix(filas, gruposUsados) {
        for (var i = 0; i < filas.length; i++) {
            if (!gruposUsados[filas[i].grupo]) return i;
        }
        return 0;
    }

    function simularRegraOriginal(capacidade) {
        var base = itens.filter(function(item) { return item.selecionado; });
        ultimaSimulacao = [];
        var estado = {numero: 0, livre: 0, atual: null};

        for (var i = 0; i < base.length; i++) {
            var inteiro = Math.floor(base[i].paletes);
            if (inteiro > 0) alocarRegraOriginal(base[i], inteiro, capacidade, estado);
        }

        for (var j = 0; j < base.length; j++) {
            var quebra = Number((base[j].paletes - Math.floor(base[j].paletes)).toFixed(6));
            if (quebra > 0.0001) alocarRegraOriginal(base[j], quebra, capacidade, estado);
        }
    }

    function alocarRegraOriginal(item, quantidade, capacidade, estado) {
        var restante = Number(quantidade.toFixed(6));
        while (restante > 0.0001) {
            if (!estado.atual || estado.livre <= 0.0001) {
                estado.numero++;
                estado.livre = capacidade;
                estado.atual = {numero: estado.numero, paletes: 0, itens: []};
                ultimaSimulacao.push(estado.atual);
            }

            var usar = Math.min(estado.livre, restante);
            estado.atual.itens.push({
                seq: item.seq,
                nunota: item.nunota,
                codProd: item.codProd,
                grupo: item.grupo,
                priorizado: item.priorizado,
                ordem: item.ordem,
                paletes: Number(usar.toFixed(4))
            });
            estado.atual.paletes = Number((estado.atual.paletes + usar).toFixed(4));
            estado.livre = Number((estado.livre - usar).toFixed(4));
            restante = Number((restante - usar).toFixed(4));
        }
    }

    function textoRegraSimulacao() {
        var modo = document.getElementById('modoParticionamento').value;
        if (modo === 'PLANILHA_ATUAL') {
            return 'Regra original: aloca primeiro os paletes inteiros e depois distribui as quebras, sempre respeitando a capacidade por OEP.';
        }
        if (modo === 'PRIORIDADE') {
            return 'Mix com prioridade: itens priorizados entram primeiro e os demais sao distribuidos por grupo.';
        }
        return 'Mix por grupo: a distribuicao considera o percentual de paletes de cada grupo no total selecionado.';
    }

    function renderSimulacao() {
        var alvo = document.getElementById('resultadoSimulacao');
        alvo.innerHTML = '';
        var html = '<div class="sim-card"><h4>OEs selecionadas: ' + nuNotas.join(', ') + ' - OEP simuladas: ' + ultimaSimulacao.length + '</h4><div class="sim-body">';
        html += '<div class="hint">' + textoRegraSimulacao() + '</div>';
        html += '<div class="table-wrap"><table><thead><tr><th>OEP</th><th class="num">Paletes</th><th class="num">Itens</th><th>Distribuicao do mix</th></tr></thead><tbody>';
        for (var i = 0; i < ultimaSimulacao.length; i++) {
            var part = ultimaSimulacao[i];
            var mix = part.itens.map(function(item) {
                return item.nunota + ':' + item.codProd + '/' + item.grupo + (item.priorizado ? '/P' + item.ordem : '') + ' (' + item.paletes.toFixed(2) + ')';
            }).join(', ');
            html += '<tr><td>' + part.numero + '</td><td class="num">' + part.paletes.toFixed(2) + '</td><td class="num">' + part.itens.length + '</td><td>' + mix + '</td></tr>';
        }
        html += '</tbody></table></div></div></div>';
        alvo.innerHTML = html;
    }

    function montarPayload() {
        simularParticionamento();
        var payload = {
            escopo: 'ESCOPO_APROVADO_PARTICIONAMENTO_V2',
            origem: {
                tabelaCabecalho: 'TGFCAB',
                campoCabecalho: 'NUNOTA',
                tabelaItens: 'TGFITE',
                campoItens: 'NUNOTA'
            },
            nuNota: nuNota,
            nuNotas: nuNotas,
            codParc: codParc,
            codParcs: codParcs,
            capacidadePaletes: Number(document.getElementById('capacidadePaletes').value || 28),
            modoParticionamento: document.getElementById('modoParticionamento').value,
            observacao: document.getElementById('observacao').value,
            itens: itens.map(function(item) {
                return {
                    selecionado: item.selecionado,
                    sequencia: item.seq,
                    nunota: item.nunota,
                    codProd: item.codProd,
                    qtdNeg: item.qtdNeg,
                    codVol: item.codVol,
                    lastro: item.lastro,
                    camada: item.camada,
                    unidPorPalete: item.unidPalete,
                    cxPorPalete: item.cxPalete,
                    paletes: Number(item.paletes.toFixed(4)),
                    grupo: item.grupo,
                    priorizado: item.priorizado,
                    ordemPrioridade: item.ordem
                };
            }),
            simulacao: ultimaSimulacao
        };
        document.getElementById('payloadPreview').innerText = JSON.stringify(payload, null, 2);
        registrarLog('Dados técnicos montados para a action Java ' + (codAcaoParticionar || '-') + '.', false);
        abrirAba('preview');
        return payload;
    }

    function enviarParaAction() {
        var payload = montarPayload();
        if (!codAcaoParticionar) {
            registrarLog('Action Java não configurada. Informe codAcaoParticionar na URL do modal.', true);
            return;
        }
        if (!payload.nuNotas || payload.nuNotas.length === 0) {
            registrarLog('Nenhuma OE selecionada para enviar ao particionamento.', true);
            return;
        }
        var selecionados = payload.itens.filter(function(item) { return item.selecionado; });
        if (selecionados.length === 0) {
            registrarLog('Nenhum item marcado para compor o mix.', true);
            return;
        }
        if (!confirm('Executar particionamento Java para ' + payload.nuNotas.length + ' OE(s) e ' + selecionados.length + ' item(ns) selecionado(s)?')) {
            registrarLog('Envio cancelado pelo usuário.', false);
            return;
        }
        executarActionParticionamento(payload);
    }

    function montarLinhasAction(payload) {
        var rows = [];
        for (var i = 0; i < payload.nuNotas.length; i++) {
            rows.push({
                field: [
                    { fieldName: 'NUNOTA', type: 'N', $: String(payload.nuNotas[i]) },
                    { fieldName: 'CODPARC', type: 'N', $: String(payload.codParcs[i] || payload.codParc || '') }
                ]
            });
        }
        return rows;
    }

    function executarActionParticionamento(payload) {
        var sid = getCookie('JSESSIONID').replace('.master', '');
        var url = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession=' + encodeURIComponent(sid);
        var payloadJson = JSON.stringify(payload);
        var body = {
            serviceName: 'ActionButtonsSP.executeJava',
            requestBody: {
                javaCall: {
                    actionID: String(codAcaoParticionar),
                    refreshType: 'SEL',
                    params: {
                        param: [
                            { paramName: 'PART_PAYLOAD', name: 'PART_PAYLOAD', type: 'S', $: payloadJson },
                            { paramName: 'PAYLOAD_PARTICIONAMENTO', name: 'PAYLOAD_PARTICIONAMENTO', type: 'S', $: payloadJson }
                        ]
                    },
                    rows: { row: montarLinhasAction(payload) }
                }
            }
        };
        registrarLog(
            'Executando ActionButtonsSP.executeJava...\\n'
            + 'Action=' + codAcaoParticionar + '\\n'
            + 'NUNOTAS=' + payload.nuNotas.join(',') + '\\n'
            + 'Itens selecionados=' + payload.itens.filter(function(item) { return item.selecionado; }).length,
            false
        );
        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        })
            .then(function(resp) { return resp.json(); })
            .then(function(resp) {
                var ok = String(resp && resp.status) === '1' || String(resp && resp.status) === '2';
                var msg = limparMensagemSankhya((resp && (resp.statusMessage || resp.message)) || JSON.stringify(resp));
                registrarLog((ok ? 'Particionamento enviado com sucesso.' : 'Falha ao executar particionamento.')
                    + '\nAction: ' + codAcaoParticionar
                    + '\nRetorno: ' + (msg || '-'), !ok);
            })
            .catch(function(err) {
                registrarLog(
                    'Erro ao chamar ActionButtonsSP.executeJava.\\n'
                    + 'Action=' + codAcaoParticionar + '\\n'
                    + 'Detalhe=' + limparMensagemSankhya(err && err.message ? err.message : String(err)),
                    true
                );
            });
    }

    function desfazerParticionamento() {
        if (!codAcaoDesfazer) {
            registrarLog('Action Java de desfazer não configurada. Informe codAcaoDesfazer na URL do modal.', true);
            return;
        }
        if (!nuNotas || nuNotas.length === 0) {
            registrarLog('Nenhuma OE selecionada para desfazer particionamento.', true);
            return;
        }
        if (!confirm('Desfazer o particionamento de ' + nuNotas.length + ' OE(s) selecionada(s)?')) {
            registrarLog('Desfazer cancelado pelo usuário.', false);
            return;
        }
        executarActionDesfazer();
    }

    function executarActionDesfazer() {
        var sid = getCookie('JSESSIONID').replace('.master', '');
        var url = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession=' + encodeURIComponent(sid);
        var payload = {
            serviceName: 'ActionButtonsSP.executeJava',
            requestBody: {
                javaCall: {
                    actionID: String(codAcaoDesfazer),
                    refreshType: 'SEL',
                    rows: { row: montarLinhasAction({
                        nuNotas: nuNotas,
                        codParc: codParc,
                        codParcs: codParcs
                    }) }
                }
            }
        };
        registrarLog(
            'Executando desfazer particionamento...\\n'
            + 'Action=' + codAcaoDesfazer + '\\n'
            + 'NUNOTAS=' + nuNotas.join(','),
            false
        );
        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
            .then(function(resp) { return resp.json(); })
            .then(function(resp) {
                var ok = String(resp && resp.status) === '1' || String(resp && resp.status) === '2';
                var msg = limparMensagemSankhya((resp && (resp.statusMessage || resp.message)) || JSON.stringify(resp));
                registrarLog((ok ? 'Desfazer particionamento enviado com sucesso.' : 'Falha ao desfazer particionamento.')
                    + '\nAction: ' + codAcaoDesfazer
                    + '\nRetorno: ' + (msg || '-'), !ok);
            })
            .catch(function(err) {
                registrarLog(
                    'Erro ao chamar ActionButtonsSP.executeJava para desfazer.\\n'
                    + 'Action=' + codAcaoDesfazer + '\\n'
                    + 'Detalhe=' + limparMensagemSankhya(err && err.message ? err.message : String(err)),
                    true
                );
            });
    }

    carregarItensReaisDaBase();
</script>
</body>
</html>
