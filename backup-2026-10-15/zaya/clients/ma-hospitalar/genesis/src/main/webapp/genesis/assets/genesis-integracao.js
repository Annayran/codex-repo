(function () {
    var shell = document.querySelector('.page-shell');
    var apiBase = shell ? shell.getAttribute('data-api-base') : '';

    var elements = {
        refreshButton: document.getElementById('refreshButton'),
        searchButton: document.getElementById('searchButton'),
        tipoFilter: document.getElementById('tipoFilter'),
        statusFilter: document.getElementById('statusFilter'),
        nunotaFilter: document.getElementById('nunotaFilter'),
        serieFilter: document.getElementById('serieFilter'),
        produtoFilter: document.getElementById('produtoFilter'),
        statusMessage: document.getElementById('statusMessage'),
        movimentosBody: document.getElementById('movimentosBody'),
        countEntradas: document.getElementById('countEntradas'),
        countSaidas: document.getElementById('countSaidas'),
        countSeries: document.getElementById('countSeries'),
        countEnviado: document.getElementById('countEnviado'),
        countPendente: document.getElementById('countPendente'),
        countErro: document.getElementById('countErro'),
        tipoBreakdown: document.getElementById('tipoBreakdown'),
        estoqueBreakdown: document.getElementById('estoqueBreakdown'),
        integracaoBreakdown: document.getElementById('integracaoBreakdown'),
        errorDialog: document.getElementById('errorDialog'),
        errorDialogText: document.getElementById('errorDialogText')
    };

    elements.refreshButton.addEventListener('click', carregarDados);
    elements.searchButton.addEventListener('click', carregarDados);

    carregarDados();

    function carregarDados() {
        setStatus('Consultando dashboard...');
        Promise.all([buscarResumo(), buscarMovimentos()])
            .then(function (results) {
                renderResumo(results[0]);
                renderAnalises(results[0]);
                renderMovimentos(results[1]);
                setStatus('Consulta concluida.');
            })
            .catch(function (error) {
                setStatus('Nao foi possivel consultar os dados.');
                renderResumo({});
                renderAnalises({});
                renderMovimentos([]);
                abrirMensagem(error.message || String(error));
            });
    }

    function buscarResumo() {
        return fetchJson(apiBase + '/resumo');
    }

    function buscarMovimentos() {
        var params = new URLSearchParams();
        addParam(params, 'tipo', elements.tipoFilter.value);
        addParam(params, 'status', elements.statusFilter.value);
        addParam(params, 'nunota', elements.nunotaFilter.value);
        addParam(params, 'serie', elements.serieFilter.value);
        addParam(params, 'produto', elements.produtoFilter.value);
        var query = params.toString();
        return fetchJson(apiBase + '/movimentos' + (query ? '?' + query : ''));
    }

    function fetchJson(url, options) {
        return fetch(url, options || {})
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('HTTP ' + response.status + ' ao chamar endpoint interno.');
                }
                return response.json();
            });
    }

    function renderResumo(resumo) {
        resumo = resumo || {};
        elements.countEntradas.textContent = valueOrZero(resumo.entradas);
        elements.countSaidas.textContent = valueOrZero(resumo.saidas);
        elements.countSeries.textContent = valueOrZero(resumo.series);
        elements.countEnviado.textContent = valueOrZero(resumo.enviados);
        elements.countPendente.textContent = valueOrZero(resumo.pendentes);
        elements.countErro.textContent = valueOrZero(resumo.erros);
    }

    function renderAnalises(resumo) {
        resumo = resumo || {};
        renderBreakdown(elements.tipoBreakdown, resumo.porTipo || [
            { label: 'Entrada', value: resumo.entradas || 0 },
            { label: 'Saida', value: resumo.saidas || 0 },
            { label: 'A confirmar', value: resumo.aConfirmar || 0 }
        ]);
        renderBreakdown(elements.estoqueBreakdown, resumo.porStatusEstoque || []);
        renderBreakdown(elements.integracaoBreakdown, resumo.porStatusIntegracao || [
            { label: 'Enviado', value: resumo.enviados || 0 },
            { label: 'Pendente', value: resumo.pendentes || 0 },
            { label: 'Erro', value: resumo.erros || 0 },
            { label: 'Reprocessar', value: resumo.reprocessar || 0 }
        ]);
    }

    function renderBreakdown(container, rows) {
        container.innerHTML = '';
        rows = rows || [];
        if (!rows.length) {
            var empty = document.createElement('div');
            empty.className = 'breakdown-label';
            empty.textContent = 'Sem dados.';
            container.appendChild(empty);
            return;
        }
        var max = rows.reduce(function (acc, row) { return Math.max(acc, Number(row.value || 0)); }, 0) || 1;
        rows.forEach(function (row) {
            var item = document.createElement('div');
            item.className = 'breakdown-row';
            var label = document.createElement('span');
            label.className = 'breakdown-label';
            label.textContent = row.label || row.status || '-';
            var bar = document.createElement('div');
            bar.className = 'breakdown-bar';
            var fill = document.createElement('div');
            fill.className = 'breakdown-fill';
            fill.style.width = Math.round((Number(row.value || 0) / max) * 100) + '%';
            bar.appendChild(fill);
            var count = document.createElement('span');
            count.className = 'breakdown-count';
            count.textContent = valueOrZero(row.value);
            item.appendChild(label);
            item.appendChild(bar);
            item.appendChild(count);
            container.appendChild(item);
        });
    }

    function renderMovimentos(movimentos) {
        elements.movimentosBody.innerHTML = '';
        if (!movimentos || !movimentos.length) {
            var emptyRow = document.createElement('tr');
            var emptyCell = document.createElement('td');
            emptyCell.colSpan = 12;
            emptyCell.className = 'empty-state';
            emptyCell.textContent = 'Nenhum movimento encontrado.';
            emptyRow.appendChild(emptyCell);
            elements.movimentosBody.appendChild(emptyRow);
            return;
        }

        movimentos.forEach(function (movimento) {
            var row = document.createElement('tr');
            appendCell(row, movimento.nunota, 'nowrap');
            appendCell(row, movimento.codtipoper || movimento.codTipoOperacao, 'nowrap');
            appendCell(row, movimento.produto || movimento.descrprod || movimento.descrProd, 'product-cell');
            appendCell(row, movimento.serie, 'nowrap');
            appendType(row, movimento.tipo);
            appendCell(row, movimento.poder, 'nowrap');
            appendCell(row, movimento.descrlocal || movimento.localizacao, 'nowrap');
            appendCell(row, movimento.statusEstoqueDesc || movimento.status_estoque_desc || movimento.statusEstoque, 'nowrap');
            appendCell(row, formatMoney(movimento.vlrunit || movimento.valorUnitario), 'nowrap');
            appendStatus(row, movimento.statusIntegracao || movimento.status_integracao || 'PENDENTE');
            appendCell(row, movimento.httpStatus || movimento.http_status, 'nowrap');
            appendError(row, movimento);
            elements.movimentosBody.appendChild(row);
        });
    }

    function appendCell(row, value, className) {
        var cell = document.createElement('td');
        if (className) cell.className = className;
        cell.textContent = value == null || value === '' ? '-' : value;
        row.appendChild(cell);
    }

    function appendType(row, tipo) {
        var cell = document.createElement('td');
        var pill = document.createElement('span');
        var value = tipo || 'A_CONFIRMAR';
        pill.className = 'type-pill type-' + value;
        pill.textContent = value;
        cell.appendChild(pill);
        row.appendChild(cell);
    }

    function appendStatus(row, status) {
        var cell = document.createElement('td');
        var pill = document.createElement('span');
        var value = status || 'PENDENTE';
        pill.className = 'status-pill status-' + value;
        pill.textContent = value;
        cell.appendChild(pill);
        row.appendChild(cell);
    }

    function appendError(row, movimento) {
        var cell = document.createElement('td');
        var message = movimento.mensagemErro || movimento.mensagem_erro;
        if (!message) {
            cell.textContent = '-';
        } else {
            var button = document.createElement('button');
            button.type = 'button';
            button.className = 'icon-button';
            button.textContent = 'Ver erro';
            button.addEventListener('click', function () { abrirMensagem(message); });
            cell.appendChild(button);
        }
        row.appendChild(cell);
    }

    function abrirMensagem(message) {
        elements.errorDialogText.textContent = message;
        if (typeof elements.errorDialog.showModal === 'function') {
            elements.errorDialog.showModal();
        } else {
            alert(message);
        }
    }

    function addParam(params, name, value) {
        if (value && value.trim()) params.set(name, value.trim());
    }

    function setStatus(message) {
        elements.statusMessage.textContent = message;
    }

    function valueOrZero(value) {
        return value == null ? '0' : String(value);
    }

    function formatMoney(value) {
        if (value == null || value === '') return '-';
        var number = Number(value);
        if (isNaN(number)) return value;
        return number.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    }
})();