(function () {
    'use strict';

    var ABAS = [
        'prorpiedade_equipamento',
        'situacao_equipamento',
        'Cliente',
        'Setor',
        'Material',
        'Empresa',
        'Modelo',
        'ItemMaterial'
    ];
    var TAMANHO_LOTE = 250;
    var MAX_ARQUIVO = 100 * 1024 * 1024;
    var idBase = '1';
    var codAcao = '123';
    var arquivoAtual = null;
    var dadosPorAba = null;
    var cancelado = false;
    var configuracaoValida = false;

    var arquivoInput = document.getElementById('arquivoExcel');
    var importarBtn = document.getElementById('importarBtn');
    var cancelarBtn = document.getElementById('cancelarBtn');
    var mensagem = document.getElementById('mensagem');
    var andamento = document.querySelector('.andamento');

    document.getElementById('baseBadge').textContent = 'Base ' + (idBase || '-');
    arquivoInput.addEventListener('change', lerArquivoSelecionado);
    importarBtn.addEventListener('click', importar);
    cancelarBtn.addEventListener('click', function () {
        cancelado = true;
        cancelarBtn.disabled = true;
        atualizarMensagem('Cancelamento solicitado. O lote atual sera concluido.');
    });

    configuracaoValida = validarConfiguracao();

    function validarConfiguracao() {
        var erros = [];
        if (!/^\d+$/.test(idBase)) erros.push('ID da Base Instalada');
        if (!/^\d+$/.test(codAcao)) erros.push('codigo da acao Java');
        if (typeof XLSX === 'undefined') erros.push('biblioteca XLSX');
        if (erros.length) {
            arquivoInput.disabled = true;
            falhar('Configuracao interna ausente: ' + erros.join(', ') + '.');
            return false;
        }
        return true;
    }

    function lerArquivoSelecionado(evento) {
        if (!configuracaoValida) {
            return falhar('Importador sem IDBASE ou codigo da acao Java configurado.');
        }
        var arquivo = evento.target.files && evento.target.files[0];
        importarBtn.disabled = true;
        dadosPorAba = null;
        arquivoAtual = null;
        if (!arquivo) return;
        if (!/\.(xlsx|xls)$/i.test(arquivo.name)) return falhar('Selecione um arquivo Excel .xlsx ou .xls.');
        if (arquivo.size > MAX_ARQUIVO) return falhar('O arquivo excede o limite de 100 MB.');

        document.getElementById('nomeArquivo').textContent = arquivo.name;
        atualizarMensagem('Lendo a planilha...');
        var leitor = new FileReader();
        leitor.onload = function (e) {
            try {
                var workbook = XLSX.read(e.target.result, { type: 'array', cellDates: true });
                var resultado = {};
                var faltantes = [];
                for (var i = 0; i < ABAS.length; i++) {
                    var nomeAba = ABAS[i];
                    var planilha = workbook.Sheets[nomeAba];
                    if (!planilha && nomeAba === 'Cliente') {
                        planilha = workbook.Sheets.Clientes;
                    }
                    if (!planilha) {
                        faltantes.push(nomeAba);
                        continue;
                    }
                    var linhas = XLSX.utils.sheet_to_json(planilha, { defval: null, raw: true });
                    validarCabecalho(nomeAba, linhas);
                    resultado[nomeAba] = normalizarDatas(linhas);
                }
                dadosPorAba = resultado;
                arquivoAtual = arquivo;
                renderizarAbas(resultado, faltantes);
                if (faltantes.length) return falhar('Abas obrigatorias ausentes: ' + faltantes.join(', ') + '.');
                importarBtn.disabled = !configuracaoValida;
                andamento.classList.remove('erro');
                atualizarProgresso(0);
                atualizarMensagem('Planilha validada. Clique em Importar planilha.');
            } catch (erro) {
                falhar(erro && erro.message ? erro.message : String(erro));
            }
        };
        leitor.onerror = function () { falhar('Nao foi possivel ler o arquivo selecionado.'); };
        leitor.readAsArrayBuffer(arquivo);
    }

    function validarCabecalho(nomeAba, linhas) {
        if (!linhas.length) throw new Error('A aba ' + nomeAba + ' nao possui registros.');
        var primeira = linhas[0];
        var possuiId = Object.keys(primeira).some(function (chave) {
            return normalizarChave(chave) === 'ID' || normalizarChave(chave) === 'IDGENESIS';
        });
        if (!possuiId) throw new Error('A aba ' + nomeAba + ' nao possui a coluna id/IDGENESIS na linha 1.');
    }

    function normalizarChave(valor) {
        return String(valor || '').normalize('NFD').replace(/[\u0300-\u036f]/g, '')
            .replace(/[^A-Za-z0-9]/g, '').toUpperCase();
    }

    function normalizarDatas(linhas) {
        return linhas.map(function (linha) {
            var copia = {};
            Object.keys(linha).forEach(function (chave) {
                var valor = linha[chave];
                copia[chave] = valor instanceof Date ? valor.toISOString() : valor;
            });
            return copia;
        });
    }

    function renderizarAbas(resultado, faltantes) {
        var tbody = document.getElementById('abasBody');
        while (tbody.firstChild) tbody.removeChild(tbody.firstChild);
        var total = 0;
        ABAS.forEach(function (nome) {
            var linhas = resultado[nome] || [];
            total += linhas.length;
            var tr = document.createElement('tr');
            adicionarCelula(tr, nome);
            adicionarCelula(tr, String(linhas.length));
            var status = faltantes.indexOf(nome) >= 0 ? 'Ausente' : 'Pronta';
            var tdStatus = adicionarCelula(tr, status);
            tdStatus.className = status === 'Pronta' ? 'status-ok' : 'status-erro';
            tbody.appendChild(tr);
        });
        document.getElementById('totalRegistros').textContent = total.toLocaleString('pt-BR') + ' registros';
    }

    function adicionarCelula(tr, texto) {
        var td = document.createElement('td');
        td.textContent = texto;
        tr.appendChild(td);
        return td;
    }

    async function importar() {
        if (!configuracaoValida) return falhar('Importacao bloqueada: parametros de abertura ausentes.');
        if (!dadosPorAba || !arquivoAtual) return falhar('Selecione e valide a planilha antes de importar.');
        importarBtn.disabled = true;
        arquivoInput.disabled = true;
        cancelarBtn.disabled = false;
        cancelado = false;
        andamento.classList.remove('erro');

        var total = ABAS.reduce(function (soma, aba) { return soma + dadosPorAba[aba].length; }, 0);
        var processados = 0;
        try {
            await executarAcao('INICIAR', null, null, arquivoAtual.name);
            for (var a = 0; a < ABAS.length; a++) {
                var aba = ABAS[a];
                var linhas = dadosPorAba[aba];
                for (var inicio = 0; inicio < linhas.length; inicio += TAMANHO_LOTE) {
                    if (cancelado) throw new Error('Importacao cancelada pelo usuario.');
                    var lote = linhas.slice(inicio, inicio + TAMANHO_LOTE);
                    atualizarMensagem('Importando ' + aba + ': ' + (inicio + 1) + ' a ' + (inicio + lote.length) + '.');
                    await executarAcao('IMPORTAR_LOTE', aba, JSON.stringify(lote), null, inicio + 2);
                    processados += lote.length;
                    atualizarProgresso(total ? Math.round(processados * 100 / total) : 100);
                }
            }
            await executarAcao('FINALIZAR', null, null, arquivoAtual.name);
            atualizarProgresso(100);
            atualizarMensagem('Importacao concluida. A base foi marcada como IMPORTADA.');
        } catch (erro) {
            falhar(erro && erro.message ? erro.message : String(erro));
        } finally {
            arquivoInput.disabled = false;
            cancelarBtn.disabled = true;
            importarBtn.disabled = !dadosPorAba || !configuracaoValida;
        }
    }

    async function executarAcao(operacao, aba, dadosJson, arquivo, linhaInicial) {
        if (!/^\d+$/.test(idBase) || !/^\d+$/.test(codAcao)) {
            throw new Error('IDBASE ou codigo da acao Java ausente.');
        }
        var params = [
            parametro('IDBASE', 'N', idBase),
            parametro('OPERACAO', 'S', operacao)
        ];
        if (aba) params.push(parametro('ABA', 'S', aba));
        if (dadosJson) params.push(parametro('DADOS_JSON', 'S', dadosJson));
        if (arquivo) params.push(parametro('ARQUIVO', 'S', arquivo));
        if (linhaInicial) params.push(parametro('LINHA_INICIAL', 'N', linhaInicial));

        var body = {
            serviceName: 'ActionButtonsSP.executeJava',
            requestBody: {
                javaCall: {
                    actionID: String(codAcao),
                    refreshType: 'SEL',
                    params: { param: params },
                    rows: {
                        row: [{ field: [{ fieldName: 'IDBASE', type: 'N', $: String(idBase) }] }]
                    }
                }
            }
        };
        var respostaHttp = await fetch('/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json', {
            method: 'POST',
            credentials: 'same-origin',
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            body: JSON.stringify(body)
        });
        if (!respostaHttp.ok) throw new Error('Falha HTTP ' + respostaHttp.status + ' ao executar a importacao.');
        var resposta = await respostaHttp.json();
        var ok = String(resposta && resposta.status) === '1' || String(resposta && resposta.status) === '2';
        if (!ok) {
            throw new Error((resposta && (resposta.statusMessage || resposta.message)) || 'A acao Java recusou o lote.');
        }
        return resposta;
    }

    function parametro(nome, tipo, valor) {
        return { paramName: nome, name: nome, type: tipo, $: String(valor) };
    }

    function atualizarProgresso(valor) {
        var percentual = Math.max(0, Math.min(100, valor));
        document.getElementById('percentual').textContent = percentual + '%';
        document.getElementById('barraProgresso').style.width = percentual + '%';
    }

    function atualizarMensagem(texto) { mensagem.textContent = texto; }

    function falhar(texto) {
        andamento.classList.add('erro');
        atualizarMensagem(texto);
        importarBtn.disabled = true;
    }
}());
