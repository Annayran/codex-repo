(function (root, factory) {
    var api = factory();
    if (typeof module === "object" && module.exports) {
        module.exports = api;
    }
    root.RateioView = api;
})(typeof globalThis !== "undefined" ? globalThis : this, function () {
    var catalogo = { empresas: [], centros: [], naturezas: [], parceiros: [], tops: [], titulos: [], criterios: [], origemConsulta: true };
    function origemBase() { return { nufin:'', nunota:'', documento:'Nenhum título selecionado', empresa:'', competencia:'', base:null, exemplo:false, auxiliar:false }; }

    function clonarLinhas(linhas) {
        return linhas.map(function (linha) {
            return { empresa: linha.empresa, cr: linha.cr, percentual: linha.percentual };
        });
    }

    function parsePercent(texto) {
        if (texto == null || String(texto).trim() === "") {
            return { ok: false, erro: "Informe o percentual." };
        }
        var bruto = String(texto).trim().replace(",", ".");
        if (!/^\d+(\.\d{1,6})?$/.test(bruto)) {
            return { ok: false, erro: "Percentual com até seis casas decimais." };
        }
        var numero = Number(bruto);
        if (!(numero > 0)) {
            return { ok: false, erro: "Percentual zero não compõe a entrada." };
        }
        if (numero > 100) {
            return { ok: false, erro: "Percentual acima de 100." };
        }
        return { ok: true, milhoes: Math.round(numero * 1e6) };
    }

    function validar(origem, linhas) {
        var erros = [];
        if (!origem.competencia) {
            erros.push("Competência não definida. A data atual não é adotada.");
        }
        if (origem.base == null || !(Number(origem.base) > 0)) {
            erros.push("Base rateável ausente ou não positiva.");
        }
        if (!linhas.length) {
            erros.push("Informe a composição por empresa e centro de resultado.");
        }
        var soma = 0;
        var vistos = {};
        var normalizadas = [];
        linhas.forEach(function (linha, indice) {
            var prefixo = "Linha " + (indice + 1) + ": ";
            if (!linha.empresa || !linha.cr) {
                erros.push(prefixo + "empresa e centro de resultado são obrigatórios.");
                return;
            }
            var chave = linha.empresa + "|" + linha.cr;
            if (vistos[chave]) {
                erros.push(prefixo + "empresa e centro de resultado repetidos.");
            }
            vistos[chave] = true;
            var percentual = parsePercent(linha.percentual);
            if (!percentual.ok) {
                erros.push(prefixo + percentual.erro);
                return;
            }
            soma += percentual.milhoes;
            normalizadas.push({
                empresa: String(linha.empresa),
                cr: String(linha.cr),
                milhoes: percentual.milhoes
            });
        });
        if (linhas.length && soma !== 100000000) {
            erros.push("A soma dos percentuais, com a parcela da origem, tem de ser 100%.");
        }
        return { ok: erros.length === 0, erros: erros, linhas: normalizadas };
    }


    function formatCents(cents) {
        var negativo = cents < 0;
        var absoluto = Math.abs(cents);
        var reais = Math.floor(absoluto / 100);
        var centavos = absoluto % 100;
        var textoReais = String(reais).replace(/\B(?=(\d{3})+(?!\d))/g, ".");
        return (negativo ? "-" : "") + textoReais + "," + (centavos < 10 ? "0" : "") + centavos;
    }

    function formatMilhoes(milhoes) {
        var negativo = milhoes < 0;
        var absoluto = Math.abs(milhoes);
        var inteiro = Math.floor(absoluto / 1e6);
        var frac = String(absoluto % 1e6);
        while (frac.length < 6) {
            frac = "0" + frac;
        }
        return (negativo ? "-" : "") + inteiro + "," + frac;
    }

    function esc(valor) {
        return String(valor == null ? "" : valor)
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;");
    }

    function situacaoDe(id) {
        if (id === "simulada") return "S";
        if (id === "desatualizada") return "D";
        if (id === "pendencia") return "P";
        if (id === "efetivada") return "E";
        return null;
    }

    function rotuloSituacao(codigo) {
        if (codigo === "S") return "Simulada";
        if (codigo === "D") return "Desatualizada";
        if (codigo === "P") return "Pendência";
        if (codigo === "E") return "Efetivada";
        return "Sem simulação";
    }

    function estadoInicial() { return { origem:origemBase(), modo:'manual', criterioId:'', linhas:[], situacao:null,
      versao:null, data:null, usuario:null, pendencias:[], resultado:null, aviso:'', podeSimular:false, podeEfetivar:false }; }

    function percentualInterno(resultado, empresa, cr) {
        if (!resultado) return "";
        var auxiliar = null;
        resultado.auxiliares.forEach(function (item) {
            if (String(item.empresa) === String(empresa)) auxiliar = item;
        });
        if (!auxiliar) return "";
        var achado = "";
        auxiliar.internos.forEach(function (interno) {
            if (String(interno.cr) === String(cr)) achado = formatMilhoes(interno.milhoes);
        });
        return achado;
    }

    function versaoAprovada(criterio) { return criterio.versoes[0] || null; }
    function criterioSelecionavel() { return true; }

    function nomeDe(lista, id) {
        var nome = "";
        lista.forEach(function (item) {
            if (String(item.id) === String(id)) nome = item.nome;
        });
        return nome || id || "";
    }

    function criteriosAplicaveis() {
        return catalogo.criterios.filter(criterioSelecionavel);
    }

    var numeroLista=0;
    function selecaoPesquisa(campo, valor, itens, rotulo) {
        var id='rateio-lista-'+(++numeroLista);
        return '<input data-campo="'+campo+'" value="'+esc(valor || '')+'" inputmode="numeric" aria-label="'+esc(rotulo)+'" list="'+id+'"><datalist id="'+id+'">'+itens.map(function(item){return '<option value="'+esc(item.id)+'" label="'+esc(item.nome || '')+'"></option>';}).join('')+'</datalist>';
    }
    function selecaoCriterio(campo, valor, itens) {
        var html = '<select class="filter-input" data-criterio="' + campo + '">';
        html += '<option value="">Selecione</option>';
        itens.forEach(function (item) {
            html += '<option value="' + esc(item.id) + '"' + (String(item.id) === String(valor) ? " selected" : "") + '>';
            html += esc(item.id + " - " + item.nome) + '</option>';
        });
        html += '</select>';
        return html;
    }

    function rotuloItem(lista, id) {
        if (!id) return "-";
        var nome = nomeDe(lista, id);
        return nome && nome !== id ? id + " - " + nome : String(id);
    }

    function linhasDoCriterio(criterioId) {
        var criterio = null;
        catalogo.criterios.forEach(function (item) {
            if (String(item.id) === String(criterioId)) criterio = item;
        });
        if (!criterio) return [];
        var versao = versaoAprovada(criterio);
        if (!versao) return [];
        return versao.destinos.map(function (destino) {
            return { empresa: String(destino.empresa), cr: String(destino.cr), percentual: String(destino.percentual) };
        });
    }

    function linhasNota(estado, destino) {
        if (!estado || !estado.resultado) return [];
        return estado.resultado.parcelas.filter(function (parcela) {
            if (destino === "A") return parcela.destino === "A" && !parcela.semValor;
            return parcela.destino === "O" || parcela.semValor;
        }).map(function (parcela) {
            return {
                CODEMP: parcela.empresa,
                CODCENCUS: parcela.cr,
                PERCGLOBAL: formatMilhoes(parcela.milhoes),
                MILHOES: parcela.milhoes,
                VALOR: formatCents(parcela.cents),
                CENTS: parcela.cents,
                DESTINO: parcela.semValor ? "Sem valor" : (parcela.destino === "O" ? "Origem" : "Auxiliar"),
                PERCINTERNO: parcela.destino === "A" && !parcela.semValor
                    ? percentualInterno(estado.resultado, parcela.empresa, parcela.cr)
                    : "",
                NUFIN: (function () {
                    var nufin = "";
                    estado.resultado.auxiliares.forEach(function (auxiliar) {
                        if (String(auxiliar.empresa) === String(parcela.empresa) && auxiliar.nufin) nufin = auxiliar.nufin;
                    });
                    return nufin;
                })()
            };
        });
    }

    function linhasInternas(estado, empresa) {
        if (!estado || !estado.resultado || empresa == null || empresa === "") return [];
        var auxiliar = null;
        estado.resultado.auxiliares.forEach(function (item) {
            if (String(item.empresa) === String(empresa)) auxiliar = item;
        });
        if (!auxiliar) return [];
        return auxiliar.internos.map(function (interno) {
            return {
                CODEMP: auxiliar.empresa,
                CODCENCUS: interno.cr,
                PERCINTERNO: formatMilhoes(interno.milhoes),
                MILHOES: interno.milhoes,
                VALOR: formatCents(interno.cents),
                CENTS: interno.cents,
                RECDESP: "0",
                NUFIN: auxiliar.nufin || "",
                VLRAUX: formatCents(auxiliar.cents)
            };
        });
    }

    function resumo(estado) {
        var origemValor = "-";
        var auxiliaresValor = "-";
        var quantidade = 0;
        if (estado && estado.resultado) {
            var centsOrigem = 0;
            var centsAux = 0;
            estado.resultado.parcelas.forEach(function (parcela) {
                if (parcela.destino === "O") centsOrigem += parcela.cents;
            });
            estado.resultado.auxiliares.forEach(function (auxiliar) {
                centsAux += auxiliar.cents;
                quantidade += 1;
            });
            origemValor = formatCents(centsOrigem);
            auxiliaresValor = formatCents(centsAux);
        }
        var origem = estado && estado.origem ? estado.origem : {};
        return {
            documento: origem.documento || "-",
            empresa: origem.empresa ? origem.empresa + " - " + (origem.empresaNome || "") : "-",
            natureza: origem.natureza || "-",
            parceiro: origem.parceiro || "-",
            base: origem.base == null ? "-" : formatCents(window.RateioServidor.inteiroDecimal(origem.base, 2)),
            origem: origemValor,
            auxiliares: auxiliaresValor,
            quantidade: quantidade,
            situacao: rotuloSituacao(estado && estado.situacao),
            competencia: (origem.competencia || "").trim()
        };
    }

    function marcarDesatualizada(estado) {
        if (!estado || estado.situacao !== "S") return estado;
        estado.situacao = "D";
        estado.cenario = "desatualizada";
        estado.pendencias = ["SIMULACAO_DESATUALIZADA. Altere a entrada e simule de novo antes de efetivar."];
        estado.aviso = "A entrada mudou depois da análise. Efetivar fica desabilitado até uma nova simulação.";
        return estado;
    }


    function tituloEmAberto(titulo) {
        if (!titulo || titulo.baixado || titulo.provisao || titulo.auxiliar) return false;
        if (!(Number(titulo.base) > 0)) return false;
        return Number(titulo.recdesp) === -1;
    }

    function dataBr(iso) {
        if (!iso || String(iso).length < 10) return "-";
        var texto = String(iso);
        return texto.slice(8, 10) + "/" + texto.slice(5, 7) + "/" + texto.slice(0, 4);
    }

    function parseValor(texto) {
        var s = String(texto == null ? "" : texto).trim();
        if (!s) return 0;
        if (s.indexOf(",") >= 0) s = s.replace(/\./g, "").replace(",", ".");
        var n = Number(s);
        return isNaN(n) ? 0 : n;
    }

    function lerTitulos() {
        if (typeof document === "undefined") return [];
        var box = document.getElementById("db-titulos");
        if (!box) return [];
        return Array.prototype.map.call(box.querySelectorAll("[data-nufin]"), function (el) {
            return {
                nufin: el.getAttribute("data-nufin"),
                nunota: el.getAttribute("data-nunota") || "",
                numnota: el.getAttribute("data-numnota") || "",
                documento: el.getAttribute("data-documento") || ("NUFIN " + el.getAttribute("data-nufin")),
                empresa: el.getAttribute("data-empresa") || "",
                empresaNome: el.getAttribute("data-empresa-nome") || "",
                natureza: el.getAttribute("data-natureza") || "",
                codnat: el.getAttribute("data-codnat") || "",
                parceiro: el.getAttribute("data-nomeparc") || "",
                codparc: el.getAttribute("data-codparc") || "",
                top: el.getAttribute("data-top") || "",
                codtipoper: el.getAttribute("data-codtipoper") || "",
                tipoTitulo: el.getAttribute("data-tiptit") || "",
                base: parseValor(el.getAttribute("data-base")),
                dtvenc: el.getAttribute("data-dtvenc") || "",
                recdesp: parseValor(el.getAttribute("data-recdesp") || "-1"),
                baixado: el.getAttribute("data-baixado") === "S",
                provisao: false,
                auxiliar: el.getAttribute("data-auxiliar") === "S"
            };
        }).filter(function (item) { return item.nufin; });
    }

    function lerLista(id) {
        if (typeof document === "undefined") return [];
        var box = document.getElementById(id);
        if (!box) return [];
        return Array.prototype.map.call(box.querySelectorAll("[data-id]"), function (el) {
            return {
                id: el.getAttribute("data-id"),
                nome: el.getAttribute("data-nome") || "",
                fornecedor: el.getAttribute("data-fornecedor") !== "N",
                ativo: el.getAttribute("data-ativo") !== "N"
            };
        }).filter(function (item) { return item.id; });
    }

    function ehFornecedorAtivo(item) {
        return !!(item && item.fornecedor !== false && item.ativo !== false);
    }

    function lerCriteriosConsulta() {
        if (typeof document === "undefined") return [];
        var box = document.getElementById("db-criterios");
        if (!box) return [];
        var porId = {};
        Array.prototype.forEach.call(box.querySelectorAll("[data-criterio-id]"), function (el) {
            var id = el.getAttribute("data-criterio-id");
            if (!porId[id]) {
                porId[id] = {
                    id: id,
                    descricao: el.getAttribute("data-descricao") || "",
                    modalidade: el.getAttribute("data-modalidade") || "M",
                    ativo: el.getAttribute("data-ativo") === "S",
                    aprovado: el.getAttribute("data-aprovado") === "S",
                    codnat: el.getAttribute("data-codnat") || "",
                    codparc: el.getAttribute("data-codparc") || "",
                    codtipoper: el.getAttribute("data-codtipoper") || "",
                    codemporig: el.getAttribute("data-codemporig") || "",
                    vigenciaIni: el.getAttribute("data-vigencia-ini") || "",
                    vigenciaFim: el.getAttribute("data-vigencia-fim") || "",
                    versoes: []
                };
            }
            var seq = el.getAttribute("data-sequencia");
            if (!seq) return;
            var criterio = porId[id];
            var versao = null;
            criterio.versoes.forEach(function (item) {
                if (String(item.sequencia) === String(seq)) versao = item;
            });
            if (!versao) {
                versao = {
                    sequencia: Number(seq),
                    nro: el.getAttribute("data-nro") || seq,
                    situacao: el.getAttribute("data-situacao") || "R",
                    vigenciaIni: el.getAttribute("data-versao-ini") || "",
                    vigenciaFim: el.getAttribute("data-versao-fim") || "",
                    destinos: []
                };
                criterio.versoes.push(versao);
            }
            if (el.getAttribute("data-emp")) {
                versao.destinos.push({
                    empresa: el.getAttribute("data-emp"),
                    cr: el.getAttribute("data-cr") || "",
                    percentual: el.getAttribute("data-perc") || "",
                    ordem: el.getAttribute("data-ordem") || ""
                });
            }
        });
        var lista = [];
        for (var chave in porId) {
            if (Object.prototype.hasOwnProperty.call(porId, chave)) lista.push(porId[chave]);
        }
        return lista;
    }

    function mount(elemento, opcoes) {
        var empresas = lerLista("db-empresas");
        var centros = lerLista("db-centros");
        var naturezas = lerLista("db-naturezas");
        var parceiros = lerLista("db-parceiros");
        var tops = lerLista("db-tops");
        if (empresas.length) catalogo.empresas = empresas;
        if (centros.length) catalogo.centros = centros;
        if (naturezas.length) catalogo.naturezas = naturezas;
        catalogo.parceiros = parceiros.filter(ehFornecedorAtivo);
        if (tops.length) catalogo.tops = tops;
        var boxTitulos = document.getElementById('db-titulos');
        catalogo.titulos = lerTitulos(); catalogo.origemTitulos = true;

        var estado = estadoInicial();
        var aba = "composicao";
        var empresaAberta = null;
        var passo = 1;
        var menu = "rateio";
        var filtroTitulo = { codparc: "", numnota: "", dtvenc: "" };
        var gradeTitulos = null;
        var pesquisaAberta = false;
        var avisoGrade = "";
        var carregandoTitulos = false;
        var nufinMarcado = "";
        if (boxTitulos && boxTitulos.getAttribute("data-busca") === "S") {
            filtroTitulo.codparc = boxTitulos.getAttribute("data-parc") || "";
            filtroTitulo.numnota = boxTitulos.getAttribute("data-nota") || "";
            filtroTitulo.dtvenc = boxTitulos.getAttribute("data-venc") || "";
            gradeTitulos = catalogo.titulos.filter(tituloEmAberto);
            var erroConsulta = boxTitulos.getAttribute("data-erro") || "";
            avisoGrade = erroConsulta
                ? "A consulta do financeiro não carregou os títulos. " + erroConsulta
                : (gradeTitulos.length ? "Marque um título na grade para usar o registro." : "Nenhum título de despesa em aberto com esses filtros.");
            pesquisaAberta = true;
        }
        var rascunho = null, ocupado = false, incerto = false, desativado = false, renderizando = false;
        var servidor = window.RateioServidor.criar(opcoes.executor, opcoes.ids, function (novo) {
            if (desativado) return;
            ocupado = novo.ocupado; incerto = novo.incerto;
            if (novo.estado) estado = novo.estado;
            estado.diagnosticos=novo.diagnosticos || [];
            if (novo.contexto && novo.contexto.catalogos) {
                catalogo.empresas=novo.contexto.catalogos.empresas || [];
                catalogo.centros=novo.contexto.catalogos.centros || [];
                if (novo.contexto.catalogos.limiteAtingido) estado.aviso='Lista de sugestões limitada. Informe o código para validação pelo servidor.';
            }
            if (novo.contexto) catalogo.criterios = novo.contexto.criterios.map(function (c) {
                return { id:c.id, descricao:c.descricao, ativo:true, aprovado:true,
                  versoes:[{ sequencia:c.versao, nro:c.versao, situacao:'A', destinos:[] }] };
            });
            render();
        });
        function mostrarErro(e) {
            estado.aviso = e && e.codigo === 'CONFIGURACAO_AUSENTE' ? 'Configuração de acesso e regras do rateio não instalada no servidor. Solicite a configuração antes de consultar.' : (e && e.codigo ? 'Operação não concluída: ' + e.codigo + (e.mensagem ? '. ' + e.mensagem : '') : (e.message || 'Falha ao consultar o servidor.'));
            render();
        }


        function lerFormulario() {
            var competencia = elemento.querySelector("[data-campo=competencia]");
            // Competência é somente leitura e vem do backend.
            if (estado.modo === "manual" && estado.situacao !== "E") {
                var linhasTela = elemento.querySelectorAll("[data-linha]");
                if (linhasTela.length) {
                    var linhas = [];
                    linhasTela.forEach(function (linha) {
                        linhas.push({
                            empresa: linha.querySelector("[data-campo=empresa]").value.trim(),
                            cr: linha.querySelector("[data-campo=cr]").value.trim(),
                            percentual: linha.querySelector("[data-campo=percentual]").value.trim()
                        });
                    });
                    estado.linhas = linhas;
                }
            }
            var escolhido = elemento.querySelector("input[name=criterio]:checked");
            if (escolhido) estado.criterioId = escolhido.value;
        }

        function aoEditar() {
            lerFormulario();
            if (estado.situacao === "S") {
                empresaAberta = null;
                marcarDesatualizada(estado);
            }
        }

        function aplicarTitulo(nufin) {
            if (ocupado || incerto) return;
            estado = estadoInicial(); estado.origem.nufin=String(nufin); estado.origem.documento='Título selecionado'; estado.aviso='Consultando dados do financeiro no servidor…'; catalogo.criterios = [];
            Promise.resolve().then(function() { return servidor.selecionar(nufin); }).catch(mostrarErro);
        }
        function entradaServidor() {
            if (estado.modo === 'criterio') {
                var c = catalogo.criterios.filter(function(c) { return String(c.id) === String(estado.criterioId); })[0];
                if (!c) throw new Error('Selecione um critério retornado pelo servidor.');
                return { modalidade:'CRITERIO', criterioId:String(c.id), criterioVersao:c.versoes[0].sequencia };
            }
            return { modalidade:'MANUAL', percentuais:estado.linhas.map(function(l,indice) {
                var percentual=String(l.percentual).trim().replace(',', '.');
                var validacao=parsePercent(percentual);
                if(!validacao.ok)throw {codigo:'COMPOSICAO_INVALIDA',mensagem:'Linha ' + (indice+1) + ': ' + validacao.erro};
                return {codEmp:l.empresa, codCencus:l.cr, percentual:percentual};
            }) };
        }
        function simular() {
            if (ocupado) return;
            lerFormulario();
            try {
                var motivo = elemento.querySelector('[data-campo=motivo]');
                servidor.executar(estado.wire ? 'recalcular' : 'simular', entradaServidor(), motivo ? motivo.value : '')
                  .then(function() { empresaAberta=null; aba='auxiliar'; passo=3; render(); }).catch(mostrarErro);
            } catch(e) { mostrarErro(e); }
        }
        function efetivar() {
            if (!estado.podeEfetivar) { mostrarErro({codigo:'EFETIVACAO_NAO_DISPONIVEL'}); return; }
            servidor.executar('efetivar').catch(mostrarErro);
        }

        function passoInicial(atual) {
            if (atual.situacao === "E") return 4;
            if (atual.resultado || atual.situacao === "P" || atual.situacao === "D") return 3;
            if (atual.linhas.length) return 2;
            return 1;
        }

        function somaDigitada(linhas) {
            var soma = 0;
            var algumaInvalida = false;
            linhas.forEach(function (linha) {
                var percentual = parsePercent(linha.percentual);
                if (!percentual.ok) algumaInvalida = true;
                else soma += percentual.milhoes;
            });
            return {
                soma: soma,
                texto: linhas.length ? formatMilhoes(soma) + "%" : "0%",
                fecha: !algumaInvalida && linhas.length > 0 && soma === 100000000
            };
        }

        function tabelaParcelas(linhas, comAnalise) {
            var html = '<div class="rd-tabela-wrap"><table class="rd-tabela"><thead><tr>';
            html += '<th>Empresa</th><th>Centro de resultado</th><th class="rd-num">Percentual global</th>';
            html += '<th class="rd-num">Valor</th><th>Destino</th><th class="rd-num">Percentual interno</th><th>NUFIN</th>';
            if (comAnalise) html += '<th></th>';
            html += '</tr></thead><tbody>';
            if (!linhas.length) {
                html += '<tr><td colspan="8">Nenhuma parcela neste grupo.</td></tr>';
            }
            var totalMilhoes = 0;
            var totalCents = 0;
            linhas.forEach(function (linha) {
                totalMilhoes += linha.MILHOES || 0;
                totalCents += linha.CENTS || 0;
                html += '<tr data-empresa="' + esc(linha.CODEMP) + '"' + (linha.DESTINO === "Sem valor" ? ' class="rd-zero"' : '') + '>';
                html += '<td>' + esc(rotuloItem(catalogo.empresas, linha.CODEMP)) + '</td>';
                html += '<td>' + esc(rotuloItem(catalogo.centros, linha.CODCENCUS)) + '</td>';
                html += '<td class="rd-num">' + esc(linha.PERCGLOBAL) + '%</td>';
                html += '<td class="rd-num">R$ ' + esc(linha.VALOR) + '</td>';
                html += '<td>' + esc(linha.DESTINO) + '</td>';
                html += '<td class="rd-num">' + (linha.PERCINTERNO ? esc(linha.PERCINTERNO) + '%' : '-') + '</td>';
                html += '<td>' + esc(linha.NUFIN || "") + '</td>';
                if (comAnalise) {
                    html += '<td><button type="button" data-acao="abrir" data-empresa="' + esc(linha.CODEMP) + '">Analisar</button></td>';
                }
                html += '</tr>';
            });
            if (linhas.length) {
                html += '</tbody><tfoot><tr>';
                html += '<td colspan="2">Total</td>';
                html += '<td class="rd-num">' + esc(formatMilhoes(totalMilhoes)) + '%</td>';
                html += '<td class="rd-num">R$ ' + esc(formatCents(totalCents)) + '</td>';
                html += '<td colspan="' + (comAnalise ? 4 : 3) + '">Percentual interno fecha 100% dentro de cada auxiliar.</td>';
                html += '</tr></tfoot>';
            } else {
                html += '</tbody>';
            }
            html += '</table></div>';
            return html;
        }

        function htmlCriterios() {
            var html = '<section class="card-section"><h2><i class="material-icons">rule</i> Critérios de rateio</h2>';
            html += '<p class="rd-nota">O percentual global tem seis casas decimais. A soma da versão, incluindo a parcela da empresa de origem, é 100%. Percentual zero não entra. Só critério ativo, aprovado e com versão A vigente aparece no rateio.</p>';
            if (!catalogo.origemConsulta) {
                html += '<p class="rd-nota">A lista inicial é a do escopo. Se AD_RATCRI, AD_RATVER e AD_RATDES existirem, a abertura usa a consulta. O que for salvo aqui fica nesta sessão da tela.</p>';
            }
            if (!rascunho) {
                html += '<div class="rd-comandos"><button type="button" class="rd-primario" data-acao="novo-criterio">Novo critério</button></div>';
                html += '<div class="table-responsive"><table><thead><tr><th>Código</th><th>Descrição</th><th>Natureza</th><th>Empresa origem</th><th>Situação</th><th></th></tr></thead><tbody>';
                catalogo.criterios.forEach(function (criterio) {
                    var versao = versaoAprovada(criterio);
                    html += '<tr><td>' + esc(criterio.id) + '</td><td>' + esc(criterio.descricao) + '</td>';
                    html += '<td>' + esc(rotuloItem(catalogo.naturezas, criterio.codnat)) + '</td>';
                    html += '<td>' + esc(rotuloItem(catalogo.empresas, criterio.codemporig)) + '</td>';
                    html += '<td>' + (criterioSelecionavel(criterio) ? "Selecionável - sequência " + esc(versao.nro) : "Fora da seleção") + '</td>';
                    html += '<td><button type="button" data-acao="abrir-criterio" data-id="' + esc(criterio.id) + '">Abrir</button></td></tr>';
                });
                if (!catalogo.criterios.length) html += '<tr><td colspan="6">Nenhum critério cadastrado.</td></tr>';
                html += '</tbody></table></div></section>';
                return html;
            }
            if (!rascunho.versoes || !rascunho.versoes.length) {
                rascunho.versoes = [{ nro: 1, situacao: "R", vigenciaIni: "", vigenciaFim: "", destinos: [] }];
            }
            var versao = rascunho.versoes[0];
            var destinos = versao.destinos || [];
            var soma = somaDigitada(destinos);
            html += '<div class="filter-panel">';
            html += '<div class="filter-group"><label>Descrição</label><input class="filter-input" data-criterio="descricao" value="' + esc(rascunho.descricao) + '"></div>';
            html += '<div class="filter-group"><label>Modalidade</label><select class="filter-input" data-criterio="modalidade"><option value="M"' + (rascunho.modalidade === "M" ? " selected" : "") + '>M - Matriz</option><option value="A"' + (rascunho.modalidade === "A" ? " selected" : "") + '>A - Analítica</option></select></div>';
            html += '<div class="filter-group"><label>Natureza</label>' + selecaoCriterio("codnat", rascunho.codnat, catalogo.naturezas) + '</div>';
            html += '<div class="filter-group"><label>Parceiro</label>' + selecaoCriterio("codparc", rascunho.codparc, catalogo.parceiros) + '</div>';
            html += '<div class="filter-group"><label>TOP</label>' + selecaoCriterio("codtipoper", rascunho.codtipoper, catalogo.tops) + '</div>';
            html += '<div class="filter-group"><label>Empresa de origem</label>' + selecaoCriterio("codemporig", rascunho.codemporig, catalogo.empresas) + '</div>';
            html += '<div class="filter-group"><label>Vigência inicial</label><input class="filter-input" type="date" data-criterio="vigenciaIni" value="' + esc(rascunho.vigenciaIni) + '"></div>';
            html += '<div class="filter-group"><label>Vigência final</label><input class="filter-input" type="date" data-criterio="vigenciaFim" value="' + esc(rascunho.vigenciaFim) + '"></div>';
            html += '<div class="filter-group"><label>Versão</label><input class="filter-input" data-criterio="nro" inputmode="numeric" value="' + esc(versao.nro) + '"></div>';
            html += '<div class="filter-group"><label>Situação da versão</label><select class="filter-input" data-criterio="situacao"><option value="R"' + (versao.situacao === "R" ? " selected" : "") + '>R - Rascunho</option><option value="A"' + (versao.situacao === "A" ? " selected" : "") + '>A - Aprovada</option><option value="I"' + (versao.situacao === "I" ? " selected" : "") + '>I - Inativa</option></select></div>';
            html += '</div>';
            html += '<p class="rd-soma' + (soma.fecha ? " rd-soma-ok" : "") + '">Soma do percentual global: <strong>' + esc(soma.texto) + '</strong>. O fechamento exigido é 100,000000%.</p>';
            html += '<label class="check-linha"><input type="checkbox" data-criterio="ativo"' + (rascunho.ativo ? " checked" : "") + '> Ativo</label>';
            html += '<label class="check-linha"><input type="checkbox" data-criterio="aprovado"' + (rascunho.aprovado ? " checked" : "") + '> Aprovado</label>';
            html += '<div class="table-responsive"><table><thead><tr><th>Empresa</th><th>Centro de resultado</th><th class="num">Percentual global</th><th class="num">Ordem</th><th></th></tr></thead><tbody>';
            destinos.forEach(function (destino, indice) {
                html += '<tr data-destino="' + indice + '">';
                html += '<td>' + selecaoPesquisa("empresa", destino.empresa, catalogo.empresas, "Empresa do destino " + (indice + 1)) + '</td>';
                html += '<td>' + selecaoPesquisa("cr", destino.cr, catalogo.centros, "Centro de resultado do destino " + (indice + 1)) + '</td>';
                html += '<td><input data-campo="percentual" inputmode="decimal" placeholder="0,000000" value="' + esc(destino.percentual) + '" aria-label="Percentual global do destino ' + (indice + 1) + '"></td>';
                html += '<td><input data-campo="ordem" inputmode="numeric" value="' + esc(destino.ordem || indice + 1) + '" aria-label="Ordem do destino ' + (indice + 1) + '"></td>';
                html += '<td><button type="button" data-acao="remover-destino" data-indice="' + indice + '">Remover</button></td></tr>';
            });
            if (!destinos.length) html += '<tr><td colspan="5">Inclua a origem e cada empresa beneficiária.</td></tr>';
            html += '</tbody></table></div>';
            if (rascunho.erro) html += '<p class="date-alert">' + esc(rascunho.erro) + '</p>';
            html += '<div class="rd-comandos"><button type="button" data-acao="incluir-destino">Incluir destino</button>';
            html += '<button type="button" class="rd-primario" data-acao="salvar-criterio">Salvar critério</button>';
            html += '<button type="button" data-acao="fechar-criterio">Voltar à lista</button></div></section>';
            return html;
        }

        function render() {
            if (renderizando || desativado) return;
            renderizando=true;
            try {
            var somenteLeitura = estado.situacao === "E" || estado.modo === "criterio";
            var podeEfetivar = estado.situacao === "S" && estado.podeEfetivar;
            var linhasEntrada = estado.modo === "criterio" && estado.criterioId
                ? (estado.wire && String(estado.wire.entrada.criterioId) === String(estado.criterioId) ? estado.linhas : [])
                : estado.linhas;
            var cartoes = resumo(estado);
            var soma = somaDigitada(linhasEntrada);
            var guias = [
                "Pesquise o NUFIN do título original. O valor dele não muda. A competência é obrigatória para efetivar e a data de hoje não entra no lugar.",
                "Indique empresa e centro de resultado. O percentual global tem seis casas e a soma, com a parcela da origem, fecha em 100,000000%.",
                "Confira o fechamento. A origem fica no demonstrativo. Cada outra empresa vira um auxiliar. Analisar abre o percentual interno desse auxiliar.",
                "A efetivação definitiva usa o NUFIN pesquisado. O título original permanece. Cada auxiliar nasce com RECDESP 0. Esta prévia não grava TGFFIN."
            ];
            var nomesPasso = ["Título", "Indicar", "Simular", "Efetivar"];
            var html = '';
            html += '<section class="rd">';
            html += '<header class="rd-topo"><div><p class="rd-kicker">Rateio de despesas</p><h1>Indicar o rateio da nota</h1></div>';
            html += '<span class="rd-status">' + esc(rotuloSituacao(estado.situacao)) + '</span></header>';
            html += '<div class="tab-bar" role="tablist">';
            html += '<button type="button" class="tab-button' + (menu === "rateio" ? " active" : "") + '" data-acao="menu" data-menu="rateio"><i class="material-icons">account_tree</i> Rateio da nota</button>';
            html += '<button type="button" class="tab-button' + (menu === "criterios" ? " active" : "") + '" data-acao="menu" data-menu="criterios"><i class="material-icons">rule</i> Cadastro de critérios</button>';
            html += '</div>';

            if (menu === "criterios") {
                html += htmlCriterios();
            } else {
            html += '<aside class="rd-demo-caixa"><div><strong>Rateio integrado</strong>';
            html += '<p>Selecione o título. Base, competência, critérios e resultados são confirmados pelo Java.</p></div>';
            html += '<button type="button" data-acao="exemplo-completo">Exemplo indisponível no fluxo real</button></aside>';
            html += '<ol class="rd-passos">';
            nomesPasso.forEach(function (nome, indice) {
                var numero = indice + 1;
                var classe = numero === passo ? " ativo" : (numero < passo ? " feito" : "");
                html += '<li class="rd-passo' + classe + '"><button type="button" data-acao="passo" data-passo="' + numero + '">';
                html += '<span class="rd-bola">' + (numero < passo ? "&#10003;" : numero) + '</span>';
                html += '<span><b>' + nome + '</b><small>Passo ' + numero + ' de 4</small></span></button></li>';
            });
            html += '</ol>';
            html += '<p class="rd-orientacao"><strong>Orientação. </strong>' + esc(guias[passo - 1]) + '</p>';

            html += '<section class="kpi-grid" aria-label="Resumo da nota">';
            [
                ["payments", "Base rateável", cartoes.base === "-" ? "-" : "R$ " + cartoes.base, "forest"],
                ["account_balance", "Parcela da origem", cartoes.origem === "-" ? "-" : "R$ " + cartoes.origem, "sage"],
                ["call_split", "Auxiliares", cartoes.quantidade ? cartoes.quantidade + " - R$ " + cartoes.auxiliares : "-", "gold"]
            ].forEach(function (par) {
                html += '<article class="card-kpi"><div class="kpi-icon-container ' + par[3] + '"><i class="material-icons">' + par[0] + '</i></div>';
                html += '<div class="kpi-info"><h4>' + esc(par[1]) + '</h4><p>' + esc(par[2]) + '</p></div></article>';
            });
            html += '</section>';

            if (estado.aviso) html += '<p class="rd-aviso" role="status">' + esc(estado.aviso) + '</p>';
            if (estado.diagnosticos && estado.diagnosticos.length) {
                html += '<details class="rd-aviso rd-diagnostico"><summary>Histórico e detalhes técnicos (' + estado.diagnosticos.length + ' operações)</summary>';
                estado.diagnosticos.slice().reverse().forEach(function(d) {
                    html += '<section><strong>' + esc(d.operacao || '') + ' · ' + esc(d.codigo || '') + '</strong><dl>';
                    [['Versão',d.versao],['Horário (UTC)',d.inicio],['Duração',String(d.duracaoMs == null ? '' : d.duracaoMs) + ' ms'],['Etapa',d.etapa],['Banco',(d.bancoTipo || 'Não identificado') + (d.banco == null ? '' : ' / código ' + d.banco)],['Referência',d.referencia]].forEach(function(par) {
                        html += '<dt>' + esc(par[0]) + '</dt><dd>' + esc(par[1] == null ? '' : String(par[1])) + '</dd>';
                    });
                    html += '</dl><p>' + esc((Array.isArray(d.etapas) ? d.etapas.slice(0,20) : []).map(function(e){return String(e.etapa) + ' (' + String(e.decorridoMs) + ' ms)';}).join(' → ')) + '</p></section>';
                });
                html += '<p>Últimas 20 operações desta janela. O histórico técnico não substitui o registro da simulação no servidor.</p></details>';
            }
            if (estado.pendencias.length) {
                html += '<section class="rd-pendencias"><h2>O que impede de seguir</h2><ul>';
                estado.pendencias.forEach(function (texto) { html += '<li>' + esc(texto) + '</li>'; });
                html += '</ul></section>';
            }

            if (passo === 1) {
                html += '<section class="rd-painel"><h2>1. Título original</h2>';
                var temTitulo = !!(estado.origem.nufin || estado.origem.exemplo);
                if (!temTitulo) {
                    html += '<div class="rd-vazio"><div><strong>Nenhum título selecionado</strong>';
                    html += '<p>Pesquise um NUFIN da TGFFIN. A ficha só aparece depois da escolha.</p></div>';
                    html += '<button type="button" class="rd-primario" data-acao="abrir-pesquisa">Pesquisar título</button></div>';
                } else {
                    html += '<div class="rd-ficha-topo"><div><p class="rd-kicker">' + (estado.origem.exemplo ? "Exemplo de treino" : "Título da TGFFIN") + '</p>';
                    html += '<strong>NUFIN ' + esc(estado.origem.nufin || "-") + '</strong>';
                    html += '<p>' + esc(estado.origem.documento || "Sem documento") + (estado.origem.nunota ? " · nota " + esc(estado.origem.nunota) : "") + '</p></div>';
                    html += '<button type="button" data-acao="abrir-pesquisa"' + (estado.situacao === "E" ? " disabled" : "") + '>Trocar título</button></div>';
                    if (estado.origem.auxiliar) html += '<p class="rd-aviso">Este NUFIN é auxiliar. Pesquise o título original.</p>';
                    html += '<dl class="rd-origem">';
                    [
                        ["Empresa", [estado.origem.empresa, estado.origem.empresaNome].filter(Boolean).join(" - ")],
                        ["Natureza", estado.origem.natureza],
                        ["Parceiro", estado.origem.parceiro],
                        ["TOP", estado.origem.top],
                        ["Tipo de título", estado.origem.tipoTitulo],
                        ["Valor do título", estado.origem.valorTitulo == null ? "" : "R$ " + formatCents(window.RateioServidor.inteiroDecimal(estado.origem.valorTitulo,2))],
                        ["Valor total do documento", estado.origem.valorDocumento == null ? "" : "R$ " + formatCents(window.RateioServidor.inteiroDecimal(estado.origem.valorDocumento,2))],
                        ["Quantidade de parcelas", estado.origem.quantidadeParcelas == null ? "" : String(estado.origem.quantidadeParcelas)],
                        ["Parcela selecionada", estado.origem.desdobramento || ""],
                        ["Vencimento da parcela", estado.origem.vencimento || ""],
                        ["Base", cartoes.base === "-" ? "" : "R$ " + cartoes.base]
                    ].forEach(function (par) {
                        if (!par[1]) return;
                        html += '<div><dt>' + esc(par[0]) + '</dt><dd>' + esc(par[1]) + '</dd></div>';
                    });
                    html += '<div class="rd-competencia"><dt><label for="rd-competencia">Competência</label></dt><dd>';
                    html += '<input id="rd-competencia" data-campo="competencia" type="month" value="' + esc(estado.origem.competencia) + '"' + (estado.situacao === "E" ? " disabled" : "") + '>';
                    html += '<small>' + (estado.origem.competencia ? "Confirmada pelo servidor." : "Pendente de mapeamento no servidor.") + '</small>';
                    html += '</dd></div></dl>';
                }
                html += '</section>';
            } else if (passo === 2) {
                html += '<section class="rd-painel"><h2>2. Indicar destinos</h2>';
                html += '<p class="rd-soma' + (soma.fecha ? " rd-soma-ok" : "") + '">Soma dos percentuais globais: <strong>' + esc(soma.texto) + '</strong>';
                html += soma.fecha ? " · fecha 100%." : " · o fechamento é 100%, com a parcela da origem.";
                html += '</p>';
                html += '<div class="rd-modos">';
                html += '<label><input type="radio" name="modo" value="manual"' + (estado.modo === "manual" ? " checked" : "") + (estado.situacao === "E" ? " disabled" : "") + '> Digitar o rateio</label>';
                html += '<label><input type="radio" name="modo" value="criterio"' + (estado.modo === "criterio" ? " checked" : "") + (estado.situacao === "E" ? " disabled" : "") + '> Usar critério cadastrado</label>';
                html += '</div>';
                if (estado.modo === "criterio") {
                    html += '<fieldset class="rd-criterios"><legend>Escolha um critério. A tela não escolhe sozinha.</legend>';
                    criteriosAplicaveis().forEach(function (criterio) {
                        var versao = versaoAprovada(criterio);
                        html += '<label><input type="radio" name="criterio" value="' + esc(criterio.id) + '"' + (String(estado.criterioId) === String(criterio.id) ? " checked" : "") + (estado.situacao === "E" ? " disabled" : "") + '>';
                        html += esc(criterio.descricao) + ' - sequência ' + esc(versao ? versao.nro : "") + '</label>';
                    });
                    html += '</fieldset>';
                    html += '<p class="rd-nota">O critério entra somente leitura. Para ajustar uma linha, volte para a digitação manual.</p>';
                }
                html += '<div class="rd-tabela-wrap"><table class="rd-tabela"><thead><tr>';
                html += '<th>Empresa</th><th>Centro de resultado</th><th class="rd-num">Percentual global</th>';
                if (estado.modo === "manual" && estado.situacao !== "E") html += '<th></th>';
                html += '</tr></thead><tbody>';
                linhasEntrada.forEach(function (linha, indice) {
                    html += '<tr data-linha="' + indice + '">';
                    if (somenteLeitura) {
                        html += '<td>' + esc(rotuloItem(catalogo.empresas, linha.empresa)) + '</td><td>' + esc(rotuloItem(catalogo.centros, linha.cr)) + '</td><td class="rd-num">' + esc(linha.percentual) + '</td>';
                    } else {
                        html += '<td>' + selecaoPesquisa("empresa", linha.empresa, catalogo.empresas, "Empresa da linha " + (indice + 1)) + '</td>';
                        html += '<td>' + selecaoPesquisa("cr", linha.cr, catalogo.centros, "Centro de resultado da linha " + (indice + 1)) + '</td>';
                        html += '<td class="rd-num"><input data-campo="percentual" inputmode="decimal" placeholder="0,000000" value="' + esc(linha.percentual) + '" aria-label="Percentual global da linha ' + (indice + 1) + ' com ate seis casas"></td>';
                        html += '<td><button type="button" data-acao="remover" data-indice="' + indice + '">Remover</button></td>';
                    }
                    html += '</tr>';
                });
                if (!linhasEntrada.length) html += '<tr><td colspan="4">Nenhuma linha. Inclua a empresa de origem e cada empresa beneficiária.</td></tr>';
                html += '</tbody></table></div>';
                if (estado.modo === "manual" && estado.situacao !== "E") {
                    html += '<div class="rd-linha-acoes">';
                    html += '<button type="button" data-acao="incluir">Incluir linha</button>';
                    html += '<button type="button" data-acao="exemplo">Exemplo indisponível no fluxo real</button>';
                    html += '</div>';
                }
                html += '</section>';
            } else if (empresaAberta) {
                var detalhe = linhasInternas(estado, empresaAberta);
                var valorAux = detalhe.length ? detalhe[0].VLRAUX : "-";
                var nufin = detalhe.length && detalhe[0].NUFIN ? detalhe[0].NUFIN : "previsto, sem gravação";
                html += '<div class="rd-volta">';
                html += '<button type="button" class="rd-primario" data-acao="mostrar-nota">Mostrar rateio da nota</button>';
                html += '<p>Empresa ' + esc(empresaAberta) + ' · R$ ' + esc(valorAux) + ' · RECDESP 0 · NUFIN ' + esc(nufin) + '</p>';
                html += '</div>';
                html += '<section class="rd-painel"><h2>Rateio interno do auxiliar</h2>';
                html += '<p class="rd-nota">O percentual interno reparte só este auxiliar, de R$ ' + esc(valorAux) + '. A soma das linhas é 100% e o valor fecha o auxiliar.</p>';
                html += '<div class="rd-tabela-wrap"><table class="rd-tabela"><thead><tr>';
                html += '<th>Centro de resultado</th><th class="rd-num">Percentual interno</th><th class="rd-num">Valor</th>';
                html += '</tr></thead><tbody>';
                var somaMilhoes = 0;
                var somaCents = 0;
                detalhe.forEach(function (linha) {
                    somaMilhoes += linha.MILHOES || 0;
                    somaCents += linha.CENTS || 0;
                    html += '<tr><td>' + esc(rotuloItem(catalogo.centros, linha.CODCENCUS)) + '</td>';
                    html += '<td class="rd-num">' + esc(linha.PERCINTERNO) + '%</td>';
                    html += '<td class="rd-num">R$ ' + esc(linha.VALOR) + '</td></tr>';
                });
                if (!detalhe.length) html += '<tr><td colspan="3">Este auxiliar não está na simulação atual.</td></tr>';
                if (detalhe.length) {
                    html += '</tbody><tfoot><tr><td>Total do auxiliar</td>';
                    html += '<td class="rd-num">' + esc(formatMilhoes(somaMilhoes)) + '%</td>';
                    html += '<td class="rd-num">R$ ' + esc(formatCents(somaCents)) + '</td></tr></tfoot>';
                } else {
                    html += '</tbody>';
                }
                html += '</table></div></section>';
            } else if (passo === 3) {
                html += '<section class="rd-painel"><h2>3. Resultado da simulação</h2>';
                if (!estado.resultado) {
                    html += '<p class="rd-nota">Ainda não há simulação. O botão Simular calcula a origem e os auxiliares sem gravar título.</p>';
                } else {
                    html += '<h3 class="rd-sub">Permanece na origem</h3>';
                    html += '<p class="rd-nota">Esta parcela fica no demonstrativo e não gera título.</p>';
                    html += tabelaParcelas(linhasNota(estado, "O"), false);
                    html += '<h3 class="rd-sub">Vira auxiliar</h3>';
                    html += '<p class="rd-nota">Auxiliares previstos pelo servidor. Analisar, ou o duplo clique, abre o rateio interno.</p>';
                    html += tabelaParcelas(linhasNota(estado, "A"), true);
                }
                html += '</section>';
            } else {
                html += '<section class="rd-painel"><h2>4. Efetivar</h2>';
                html += '<ul class="rd-checklist">';
                html += '<li>' + (estado.origem.auxiliar ? "O NUFIN pesquisado é auxiliar e não pode ser origem." : (estado.origem.nufinPesquisado ? "Título original da TGFFIN: NUFIN " + estado.origem.nufin + "." : (estado.origem.exemplo ? "Exemplo de treino, sem título da TGFFIN." : "Nenhum título selecionado."))) + '</li>';
                html += '<li>' + (estado.origem.competencia ? "Competência informada." : "Falta a competência.") + '</li>';
                html += '<li>' + (soma.fecha ? "Percentuais fecham 100%." : "Os percentuais ainda não fecham 100%.") + '</li>';
                html += '<li>' + (estado.situacao === "S" ? "Simulação pronta para efetivar." : (estado.situacao === "E" ? "Efetivação já consultada." : "Simule de novo antes de efetivar.")) + '</li>';
                html += '<li>O título original permanece em R$ ' + esc(cartoes.base) + '. Cada auxiliar sai com RECDESP 0.</li>';
                html += '</ul>';
                if (estado.situacao === "E" && estado.resultado) {
                    html += '<p class="rd-nota">NUFINs previstos nesta consulta, sem gravação em TGFFIN:</p>';
                    html += '<ul class="rd-checklist">';
                    estado.resultado.auxiliares.forEach(function (auxiliar) {
                        html += '<li>Empresa ' + esc(auxiliar.empresa) + ' - R$ ' + esc(formatCents(auxiliar.cents)) + ' - NUFIN ' + esc(auxiliar.nufin || "-") + '</li>';
                    });
                    html += '</ul>';
                }
                html += '</section>';
            }

            function textoParceiro() {
                if (!filtroTitulo.codparc) return "Todos os fornecedores ativos";
                var nome = nomeDe(catalogo.parceiros, filtroTitulo.codparc);
                return nome && nome !== filtroTitulo.codparc ? filtroTitulo.codparc + " - " + nome : String(filtroTitulo.codparc);
            }

            function htmlPesquisa() {
                return '<div class="rd-modal" role="dialog" aria-modal="true" aria-label="Consultar título"><div class="rd-modal-fundo" data-acao="fechar-pesquisa"></div><div class="rd-modal-caixa"><header class="rd-modal-topo"><h2>Consultar título financeiro</h2><button type="button" data-acao="fechar-pesquisa">Fechar</button></header><p class="rd-nota">Informe o NUFIN. A origem e as permissões são verificadas pelo Java.</p><div class="rd-filtros-modal"><div class="filter-group"><label>NUFIN</label><input class="filter-input" data-nufin-consulta inputmode="numeric"></div><div class="filter-group rd-filtro-acao"><button type="button" class="rd-primario" data-acao="consultar-nufin">Consultar</button></div></div></div></div>';
            }
            function legendaBotoes() {
                if (incerto) return 'Resposta não confirmada. Use Tentar novamente antes de alterar dados.';
                if (!estado.origem.nufin && !estado.origem.exemplo) return "Pesquise um NUFIN da TGFFIN.";
                if (estado.origem.auxiliar) return "Este NUFIN é auxiliar. Pesquise o título original na TGFFIN.";
                if (passo === 3 && !estado.resultado) return "Simular calcula a origem e os auxiliares. Ainda não grava título.";
                if (passo === 3 && estado.situacao === "P") return "A pendência impede a efetivação. Ajuste o que a tela apontou e simule de novo.";
                if (passo === 3 && estado.situacao === "D") return "A entrada mudou. Recalcular gera outra versão antes de efetivar.";
                if (passo === 3 && estado.situacao === "S") return "Simulação registrada. A efetivação permanece bloqueada até a homologação do serviço financeiro.";
                if (passo === 4 && estado.situacao === "S" && estado.origem.nufinPesquisado) return "Efetivação financeira ainda não disponível neste pacote.";
                if (passo === 4 && estado.situacao === "S") return "Isto consulta o exemplo de R$ 10.000. O teste real é a pesquisa do NUFIN no passo Título.";
                if (estado.situacao === "E") return "Efetivação consultada. Recomeçar volta ao início. Nenhum título foi gravado em TGFFIN.";
                return "Continuar avança o passo.";
            }

            if (passo === 3 && estado.wire) html += '<label>Motivo do recálculo<textarea data-campo="motivo" maxlength="255" rows="2"></textarea></label>';
            html += '<div class="rd-comandos">';
            if (passo > 1 && estado.situacao !== "E") html += '<button type="button" data-acao="passo" data-passo="' + (passo - 1) + '">Voltar</button>';
            if (passo < 3) {
                var faltaTitulo = passo === 1 && ((!estado.origem.nufinPesquisado && !estado.origem.exemplo) || estado.origem.auxiliar);
                html += '<button type="button" class="rd-primario" data-acao="passo" data-passo="' + (passo + 1) + '"' + (faltaTitulo ? " disabled" : "") + '>Continuar</button>';
            }
            if (passo === 3 && !estado.resultado) {
                html += '<button type="button" class="rd-primario" data-acao="simular">Simular</button>';
            } else if (passo === 3 && estado.situacao === "P") {
                html += '<button type="button" class="rd-primario" data-acao="simular">Simular de novo</button>';
            } else if (passo === 3 && estado.situacao === "D") {
                html += '<button type="button" class="rd-primario" data-acao="recalcular">Recalcular</button>';
            } else if (passo === 3 && estado.situacao === "S") {
                html += '<button type="button" class="rd-primario" data-acao="passo" data-passo="4">Seguir para efetivar</button>';
                html += '<button type="button" data-acao="recalcular">Recalcular</button>';
            }
            if (passo === 4 && estado.situacao === "S" && !estado.origem.auxiliar) {
                if (estado.origem.nufinPesquisado) {
                    html += '<button type="button" class="rd-primario" data-acao="efetivar">Efetivar título NUFIN ' + esc(estado.origem.nufin) + '</button>';
                } else if (estado.origem.exemplo) {
                    html += '<button type="button" class="rd-primario" data-acao="efetivar">Consultar efetivação do exemplo</button>';
                }
            }
            html += '<button type="button" data-acao="fechar">Recomeçar</button>';
            html += '</div>';
            if (pesquisaAberta && passo === 1) html += htmlPesquisa();
            }
            html += '<footer class="rd-rodape">';
            html += '<p class="rd-comandos-nota">' + esc(legendaBotoes()) + '</p>';
            html += '<dl class="rd-meta">';
            [
                ["Versão", estado.versao == null ? "-" : estado.versao],
                ["Registro", estado.data || "-"],
                ["Usuário", estado.usuario || "-"],
                ["Situação", rotuloSituacao(estado.situacao)]
            ].forEach(function (par) {
                html += '<div><dt>' + esc(par[0]) + '</dt><dd>' + esc(par[1]) + '</dd></div>';
            });
            html += '</dl></footer></section>';
            elemento.innerHTML = html;
            elemento.querySelectorAll('[data-acao=exemplo]').forEach(function(b) { b.disabled=true; b.title='O fluxo real não utiliza exemplos.'; });
            elemento.querySelectorAll('[data-campo=competencia]').forEach(function(c) { c.disabled=true; });
            elemento.querySelectorAll('[data-acao=simular],[data-acao=recalcular]').forEach(function(b) { b.disabled = ocupado || !estado.podeSimular; if (incerto) b.textContent='Tentar novamente'; });
            elemento.querySelectorAll('[data-acao=efetivar]').forEach(function(b) { b.disabled=ocupado || incerto || !podeEfetivar; b.title='Efetivação depende do serviço financeiro homologado.'; });
            if (ocupado || incerto) elemento.querySelectorAll('input,select,textarea,button').forEach(function(c) {
              if (!c.matches('[data-acao=simular],[data-acao=recalcular]') || ocupado) c.disabled=true;
            });


            function desenharFornecedores() {
                var opcoes = elemento.querySelector("[data-opcoes-parceiro]");
                var campo = elemento.querySelector("[data-filtro=busca-parceiro]");
                if (!opcoes) return;
                var texto = campo ? campo.value.trim().toLowerCase() : "";
                var selecionado = "";
                var oculto = elemento.querySelector("[data-filtro=codparc]");
                if (oculto) selecionado = oculto.value || "";
                var html = "";
                var todosMarcado = !selecionado;
                html += '<button type="button" role="option" aria-selected="' + (todosMarcado ? "true" : "false") + '" data-acao="escolher-parceiro" data-id=""' + (todosMarcado ? ' class="rd-marcado"' : "") + '><span class="rd-marca" aria-hidden="true"></span>Todos os fornecedores ativos</button>';
                var quantidade = 0;
                catalogo.parceiros.forEach(function (item) {
                    if (!ehFornecedorAtivo(item)) return;
                    var rotulo = item.id + " - " + item.nome;
                    if (texto && rotulo.toLowerCase().indexOf(texto) < 0) return;
                    quantidade += 1;
                    var marcado = String(selecionado) === String(item.id);
                    html += '<button type="button" role="option" aria-selected="' + (marcado ? "true" : "false") + '" data-acao="escolher-parceiro" data-id="' + esc(item.id) + '" data-rotulo="' + esc(rotulo) + '"' + (marcado ? ' class="rd-marcado"' : "") + '><span class="rd-marca" aria-hidden="true"></span>' + esc(rotulo) + '</button>';
                });
                if (!quantidade) {
                    var boxParceiros = document.getElementById("db-parceiros");
                    var erroParceiros = boxParceiros ? (boxParceiros.getAttribute("data-erro") || "") : "";
                    html += '<p class="rd-combo-vazio">' + (erroParceiros ? esc(erroParceiros) : "Nenhum fornecedor ativo com esse texto.") + '</p>';
                }
                opcoes.innerHTML = html;
                opcoes.querySelectorAll("[data-acao=escolher-parceiro]").forEach(function (botao) {
                    botao.addEventListener("click", function (evento) {
                        evento.preventDefault();
                        evento.stopPropagation();
                        var id = botao.getAttribute("data-id") || "";
                        if (oculto) oculto.value = id;
                        filtroTitulo.codparc = id;
                        var rotulo = elemento.querySelector("[data-rotulo-parceiro]");
                        if (rotulo) rotulo.textContent = id ? (botao.getAttribute("data-rotulo") || "") : "Todos os fornecedores ativos";
                        fecharListaParceiro();
                    });
                });
            }
            function fecharListaParceiro() {
                var lista = elemento.querySelector("[data-lista-parceiro]");
                var gatilho = elemento.querySelector("[data-acao=abrir-parceiros]");
                if (lista) lista.hidden = true;
                if (gatilho) gatilho.setAttribute("aria-expanded", "false");
            }
            function abrirListaParceiro() {
                var lista = elemento.querySelector("[data-lista-parceiro]");
                var gatilho = elemento.querySelector("[data-acao=abrir-parceiros]");
                var campo = elemento.querySelector("[data-filtro=busca-parceiro]");
                if (!lista) return;
                desenharFornecedores();
                lista.hidden = false;
                if (gatilho) gatilho.setAttribute("aria-expanded", "true");
                if (campo) campo.focus();
            }
            var gatilhoParceiro = elemento.querySelector("[data-acao=abrir-parceiros]");
            if (gatilhoParceiro) {
                gatilhoParceiro.addEventListener("click", function (evento) {
                    evento.preventDefault();
                    evento.stopPropagation();
                    var lista = elemento.querySelector("[data-lista-parceiro]");
                    if (lista && !lista.hidden) fecharListaParceiro();
                    else abrirListaParceiro();
                });
            }
            var buscaParceiro = elemento.querySelector("[data-filtro=busca-parceiro]");
            if (buscaParceiro) {
                buscaParceiro.addEventListener("input", desenharFornecedores);
                buscaParceiro.addEventListener("keydown", function (evento) {
                    if (evento.key === "Escape") fecharListaParceiro();
                });
                buscaParceiro.addEventListener("click", function (evento) {
                    evento.stopPropagation();
                });
            }
            var caixaModal = elemento.querySelector(".rd-modal-caixa");
            if (caixaModal) {
                caixaModal.addEventListener("click", function (evento) {
                    var combo = caixaModal.querySelector(".rd-combo");
                    if (combo && combo.contains(evento.target)) return;
                    fecharListaParceiro();
                });
            }
            var consultarNufin=elemento.querySelector('[data-acao=consultar-nufin]');
            if (consultarNufin) consultarNufin.addEventListener('click',function(){
                var valor=elemento.querySelector('[data-nufin-consulta]').value.trim();
                if(!/^[1-9][0-9]{0,9}$/.test(valor)){mostrarErro(new Error('Informe um NUFIN válido.'));return;}
                pesquisaAberta=false;aplicarTitulo(valor);
            });
            var abrirPesquisa = elemento.querySelector("[data-acao=abrir-pesquisa]");
            if (abrirPesquisa) {
                abrirPesquisa.addEventListener("click", function () {
                    pesquisaAberta = true;
                    filtroTitulo = { codparc: "", numnota: "", dtvenc: "" };

                    gradeTitulos = catalogo.titulos.filter(tituloEmAberto);
                    nufinMarcado = "";
                    avisoGrade = gradeTitulos.length ? "Marque um título na grade para usar o registro." : "Nenhum título de despesa em aberto com esses filtros.";
                    render();
                });
            }
            elemento.querySelectorAll("[data-acao=fechar-pesquisa]").forEach(function (botao) {
                botao.addEventListener("click", function () {
                    pesquisaAberta = false;
                    render();
                });
            });
            elemento.querySelectorAll("[data-acao=marcar-titulo]").forEach(function (linha) {
                linha.addEventListener("click", function () {
                    nufinMarcado = linha.getAttribute("data-nufin");
                    var radio = linha.querySelector("input[name=titulo-escolha]");
                    if (radio) radio.checked = true;
                    var usar = elemento.querySelector("[data-acao=usar-titulo]");
                    if (usar) usar.disabled = false;
                });
            });
            var usarTitulo = elemento.querySelector("[data-acao=usar-titulo]");
            if (usarTitulo) {
                usarTitulo.addEventListener("click", function () {
                    var radio = elemento.querySelector("input[name=titulo-escolha]:checked");
                    var nufin = radio ? radio.value : nufinMarcado;
                    if (!nufin) return;
                    aplicarTitulo(nufin);
                    pesquisaAberta = false;
                    render();
                });
            }
            var exemploCompleto = elemento.querySelector('[data-acao=exemplo-completo]');
            if (exemploCompleto) { exemploCompleto.disabled=true; exemploCompleto.title='O fluxo real não utiliza exemplos.'; }

            var seletorTitulo = elemento.querySelector("[data-acao=titulo]");
            if (seletorTitulo) {
                seletorTitulo.addEventListener("change", function () {
                    if (!seletorTitulo.value) return;
                    aplicarTitulo(seletorTitulo.value);
                    render();
                });
            }
            elemento.querySelectorAll("[data-acao=menu]").forEach(function (botao) {
                botao.addEventListener("click", function () {
                    menu = botao.getAttribute("data-menu");
                    render();
                });
            });
            elemento.querySelectorAll('[data-acao=novo-criterio],[data-acao=abrir-criterio]').forEach(function(b) {
              b.disabled=true; b.title='Cadastre e aprove os critérios nas telas AD_RAT* do Sankhya.';
            });

            var fecharCriterio = elemento.querySelector("[data-acao=fechar-criterio]");
            if (fecharCriterio) {
                fecharCriterio.addEventListener("click", function () {
                    rascunho = null;
                    render();
                });
            }
            function lerRascunho() {
                if (!rascunho) return;
                function valor(nome) {
                    var campo = elemento.querySelector("[data-criterio=" + nome + "]");
                    if (!campo) return "";
                    if (campo.type === "checkbox") return campo.checked;
                    return campo.value.trim();
                }
                rascunho.descricao = valor("descricao");
                rascunho.modalidade = valor("modalidade") || "M";
                rascunho.codnat = valor("codnat");
                rascunho.codparc = valor("codparc");
                rascunho.codtipoper = valor("codtipoper");
                rascunho.codemporig = valor("codemporig");
                rascunho.vigenciaIni = valor("vigenciaIni");
                rascunho.vigenciaFim = valor("vigenciaFim");
                rascunho.ativo = !!valor("ativo");
                rascunho.aprovado = !!valor("aprovado");
                var versao = rascunho.versoes[0];
                versao.nro = valor("nro") || "1";
                versao.situacao = valor("situacao") || "R";
                versao.vigenciaIni = rascunho.vigenciaIni;
                versao.vigenciaFim = rascunho.vigenciaFim;
                var destinos = [];
                elemento.querySelectorAll("[data-destino]").forEach(function (linha) {
                    destinos.push({
                        empresa: linha.querySelector("[data-campo=empresa]").value,
                        cr: linha.querySelector("[data-campo=cr]").value,
                        percentual: linha.querySelector("[data-campo=percentual]").value.trim(),
                        ordem: linha.querySelector("[data-campo=ordem]").value.trim()
                    });
                });
                versao.destinos = destinos;
            }
            var incluirDestino = elemento.querySelector("[data-acao=incluir-destino]");
            if (incluirDestino) {
                incluirDestino.addEventListener("click", function () {
                    lerRascunho();
                    rascunho.versoes[0].destinos.push({ empresa: "", cr: "", percentual: "", ordem: String(rascunho.versoes[0].destinos.length + 1) });
                    render();
                });
            }
            elemento.querySelectorAll("[data-acao=remover-destino]").forEach(function (botao) {
                botao.addEventListener("click", function () {
                    lerRascunho();
                    rascunho.versoes[0].destinos.splice(Number(botao.getAttribute("data-indice")), 1);
                    render();
                });
            });
            var salvarCriterio = elemento.querySelector('[data-acao=salvar-criterio]');
            if (salvarCriterio) salvarCriterio.disabled=true;

            elemento.querySelectorAll("[data-acao=passo]").forEach(function (botao) {
                botao.addEventListener("click", function () {
                    lerFormulario();
                    passo = Number(botao.getAttribute("data-passo"));
                    if (passo !== 3) empresaAberta = null;
                    render();
                });
            });
            elemento.querySelectorAll("[data-acao=aba]").forEach(function (botao) {
                botao.addEventListener("click", function () {
                    aba = botao.getAttribute("data-aba");
                    render();
                });
            });
            elemento.querySelectorAll("[data-acao=abrir]").forEach(function (botao) {
                botao.addEventListener("click", function () {
                    empresaAberta = botao.getAttribute("data-empresa");
                    render();
                });
            });
            elemento.querySelectorAll("tr[data-empresa]").forEach(function (linha) {
                linha.addEventListener("dblclick", function () {
                    if (!linha.querySelector("[data-acao=abrir]")) return;
                    empresaAberta = linha.getAttribute("data-empresa");
                    render();
                });
            });
            var voltar = elemento.querySelector("[data-acao=mostrar-nota]");
            if (voltar) {
                voltar.addEventListener("click", function () {
                    empresaAberta = null;
                    aba = "auxiliar";
                    render();
                });
            }
            elemento.querySelectorAll("input[name=modo]").forEach(function (radio) {
                radio.addEventListener("change", function () {
                    aoEditar();
                    estado.modo = radio.value;
                    if (estado.modo === "manual") estado.criterioId = "";
                    render();
                });
            });
            elemento.querySelectorAll("input[name=criterio]").forEach(function (radio) {
                radio.addEventListener("change", function () {
                    aoEditar();
                    render();
                });
            });
            var competencia = elemento.querySelector("[data-campo=competencia]");
            if (competencia) {
                competencia.addEventListener("change", function () {
                    aoEditar();
                    render();
                });
            }
            elemento.querySelectorAll("[data-linha] input").forEach(function (campo) {
                campo.addEventListener("change", function () {
                    aoEditar();
                    render();
                });
            });
            var incluir = elemento.querySelector("[data-acao=incluir]");
            if (incluir) {
                incluir.addEventListener("click", function () {
                    aoEditar();
                    estado.linhas.push({ empresa: "", cr: "", percentual: "" });
                    render();
                });
            }

            elemento.querySelectorAll("[data-acao=remover]").forEach(function (botao) {
                botao.addEventListener("click", function () {
                    aoEditar();
                    estado.linhas.splice(Number(botao.getAttribute("data-indice")), 1);
                    render();
                });
            });
            var botaoSimular = elemento.querySelector("[data-acao=simular]");
            if (botaoSimular) botaoSimular.addEventListener("click", function () { simular(false); });
            var botaoRecalcular = elemento.querySelector("[data-acao=recalcular]");
            if (botaoRecalcular) botaoRecalcular.addEventListener("click", function () { simular(true); });
            var botaoEfetivar = elemento.querySelector("[data-acao=efetivar]");
            if (botaoEfetivar) botaoEfetivar.addEventListener("click", efetivar);
            var botaoFechar = elemento.querySelector("[data-acao=fechar]");
            if (botaoFechar) {
                botaoFechar.addEventListener("click", function () {
                    estado = estadoInicial();
                    empresaAberta = null;
                    aba = "composicao";
                    passo = 1;
                    filtroTitulo = { codparc: "", numnota: "", dtvenc: "" };
                    gradeTitulos = null;
                    render();
                });
            }
            } finally { renderizando=false; }
        }

        render();
        if (opcoes.aviso) { estado.aviso=opcoes.aviso; render(); }
        if (opcoes.nufin) aplicarTitulo(opcoes.nufin);
        return {
            destruir: function() { desativado=true; elemento.textContent=''; },
            lerEstado: function () { return estado; }
        };
    }

    return {
        parsePercent: parsePercent,
        validar: validar,
        formatCents: formatCents,
        formatMilhoes: formatMilhoes,
        linhasNota: linhasNota,
        linhasInternas: linhasInternas,
        resumo: resumo,
        marcarDesatualizada: marcarDesatualizada,
        criterios: function () {
            return catalogo.criterios.map(function (item) {
                var versao = versaoAprovada(item);
                return { id: item.id, versao: versao ? String(versao.nro) : "", descricao: item.descricao };
            });
        },
        mount: mount,
        ehFornecedorAtivo: ehFornecedorAtivo
    };
});

