"""Importação rastreável do layout fornecido pelo cliente; sem cálculo no navegador."""
from pathlib import Path
import zipfile,re,sys
root=Path(__file__).resolve().parent.parent
with zipfile.ZipFile(sys.argv[1]) as z:
    original=z.read('RateioDespesas.jsp')
s=original.decode('utf-8-sig')
backup=root/'docs/fontes/2026-10-28/RateioDespesas-original.jsp'
backup.parent.mkdir(parents=True,exist_ok=True)
if backup.exists(): assert backup.read_bytes()==original
else: backup.write_bytes(original)
css=re.search(r'<style[^>]*>([\s\S]*?)</style>',s).group(1)
(root/'web/css').mkdir(exist_ok=True)
(root/'web/js').mkdir(exist_ok=True)
(root/'web/css/rateio-despesas.css').write_bytes(css.encode('utf-8'))
start=s.index('(function (root, factory)')
end=s.index('\n    RateioModal.mount',start)
js=s[start:end].replace('root.RateioModal = api','root.RateioView = api')
def between(a,b,replacement):
    global js
    x=js.index(a); y=js.index(b,x)
    js=js[:x]+replacement+'\n'+js[y:]
between('    var CENARIOS =','    function clonarLinhas', '''    var catalogo = { empresas: [], centros: [], naturezas: [], parceiros: [], tops: [], titulos: [], criterios: [], origemConsulta: true };
    function origemBase() { return { nufin:'', nunota:'', documento:'Nenhum título selecionado', empresa:'', competencia:'', base:null, exemplo:false, auxiliar:false }; }
''')
between('    function previa(', '    function formatCents(', '')
between('    function criarEstado(', '    function percentualInterno(', '''    function estadoInicial() { return { origem:origemBase(), modo:'manual', criterioId:'', linhas:[], situacao:null,
      versao:null, data:null, usuario:null, pendencias:[], resultado:null, aviso:'', podeSimular:false, podeEfetivar:false }; }
''')
between('    function hojeIso(', '    function nomeDe(', '''    function versaoAprovada(criterio) { return criterio.versoes[0] || null; }
    function criterioSelecionavel() { return true; }
''')
between('    function aplicarSimulacao(', '    function tituloEmAberto(', '')
js=js.replace('    function mount(elemento) {','    function mount(elemento, opcoes) {')
between('        var criteriosConsulta =','        var estado = estadoInicial();','''        if (empresas.length) catalogo.empresas = empresas;
        if (centros.length) catalogo.centros = centros;
        if (naturezas.length) catalogo.naturezas = naturezas;
        catalogo.parceiros = parceiros.filter(ehFornecedorAtivo);
        if (tops.length) catalogo.tops = tops;
        var boxTitulos = document.getElementById('db-titulos');
        catalogo.titulos = lerTitulos(); catalogo.origemTitulos = true;
''')
js=js.replace('        var rascunho = null;', '''        var rascunho = null, ocupado = false, incerto = false, desativado = false, renderizando = false;
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
''')
js=js.replace('            if (competencia) estado.origem.competencia = competencia.value;','            // Competência é somente leitura e vem do backend.')
between('        function aplicarTitulo(nufin)', '        function passoInicial(', '''        function aplicarTitulo(nufin) {
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
''')
# Nunca construir simulação/critério/NUFIN em memória como se persistidos.
between('            var novoCriterio =', '            var fecharCriterio =', '''            elemento.querySelectorAll('[data-acao=novo-criterio],[data-acao=abrir-criterio]').forEach(function(b) {
              b.disabled=true; b.title='Cadastre e aprove os critérios nas telas AD_RAT* do Sankhya.';
            });
''')
between('            var salvarCriterio =', '            elemento.querySelectorAll("[data-acao=passo]")', '''            var salvarCriterio = elemento.querySelector('[data-acao=salvar-criterio]');
            if (salvarCriterio) salvarCriterio.disabled=true;
''')
between('            var exemploCompleto =', '            var seletorTitulo =', '''            var exemploCompleto = elemento.querySelector('[data-acao=exemplo-completo]');
            if (exemploCompleto) { exemploCompleto.disabled=true; exemploCompleto.title='O fluxo real não utiliza exemplos.'; }
''')
between('            var exemplo = elemento.querySelector', '            elemento.querySelectorAll("[data-acao=remover]")', '')
js=js.replace('        function render() {', '        function render() {\n            if (renderizando || desativado) return;\n            renderizando=true;\n            try {')
js=js.replace('        }\n\n        render();\n        return {', '            } finally { renderizando=false; }\n        }\n\n        render();\n        return {')
js=js.replace('var podeEfetivar = estado.situacao === "S";', 'var podeEfetivar = estado.situacao === "S" && estado.podeEfetivar;')
js=js.replace('? linhasDoCriterio(estado.criterioId)', "? (estado.wire && String(estado.wire.entrada.criterioId) === String(estado.criterioId) ? estado.linhas : [])")
js=js.replace('html += \'<div class="rd-comandos">\';', '''if (passo === 3 && estado.wire) html += '<label>Motivo do recálculo<textarea data-campo="motivo" maxlength="255" rows="2"></textarea></label>';
            html += '<div class="rd-comandos">';''')
js=js.replace('if (passo === 3 && !estado.resultado)', 'if (passo === 3 && !estado.resultado)')
js=js.replace('elemento.innerHTML = html;', '''elemento.innerHTML = html;
            elemento.querySelectorAll('[data-acao=exemplo]').forEach(function(b) { b.disabled=true; b.title='O fluxo real não utiliza exemplos.'; });
            elemento.querySelectorAll('[data-campo=competencia]').forEach(function(c) { c.disabled=true; });
            elemento.querySelectorAll('[data-acao=simular],[data-acao=recalcular]').forEach(function(b) { b.disabled = ocupado || !estado.podeSimular; if (incerto) b.textContent='Tentar novamente'; });
            elemento.querySelectorAll('[data-acao=efetivar]').forEach(function(b) { b.disabled=ocupado || incerto || !podeEfetivar; b.title='Efetivação depende do serviço financeiro homologado.'; });
            if (ocupado || incerto) elemento.querySelectorAll('input,select,textarea,button').forEach(function(c) {
              if (!c.matches('[data-acao=simular],[data-acao=recalcular]') || ocupado) c.disabled=true;
            });
''')
# Texto operacional, mantendo os mesmos elementos/classes do layout.
js=js.replace('Dois caminhos','Rateio integrado')
js=js.replace('Teste real: pesquise o NUFIN da TGFFIN no passo Título. Treino: carregue o exemplo de R$ 10.000. Não há outros cenários.', 'Selecione o título. Base, competência, critérios e resultados são confirmados pelo Java.')
js=js.replace('Carregar exemplo de R$ 10.000','Exemplo indisponível no fluxo real')
js=js.replace('formatCents(Math.round(Number(origem.base) * 100))','formatCents(window.RateioServidor.inteiroDecimal(origem.base, 2))')
js=js.replace('Preencher exemplo de R$ 10.000','Exemplo indisponível no fluxo real')
js=js.replace('"Informada para a efetivação." : "Obrigatória para efetivar. A data de hoje não entra no lugar."', '"Confirmada pelo servidor." : "Pendente de mapeamento no servidor."')
js=js.replace('"O critério entra somente leitura. Para ajustar uma linha, volte para a digitação manual."', '"O critério e sua sequência são validados pelo Java. A composição calculada aparece após simular."')
js=js.replace('"A simulação está pronta. Seguir para efetivar é o próximo passo. Recalcular só se quiser outra versão."', '"Simulação registrada. A efetivação permanece bloqueada até a homologação do serviço financeiro."')
js=js.replace('"Efetivar usa o NUFIN pesquisado na TGFFIN. O título original permanece. Esta prévia não grava o financeiro."', '"Efetivação financeira ainda não disponível neste pacote."')
js=js.replace('Um auxiliar por empresa. Analisar, ou o duplo clique, abre o rateio interno.', 'Auxiliares previstos pelo servidor. Analisar, ou o duplo clique, abre o rateio interno.')
js=js.replace(' - versão ', ' - sequência ')
js=js.replace('Selecionável - versão ', 'Selecionável - sequência ')
js=js.replace('if (!estado.origem.nufin && !estado.origem.exemplo) return', "if (incerto) return 'Resposta não confirmada. Use Tentar novamente antes de alterar dados.';\n                if (!estado.origem.nufin && !estado.origem.exemplo) return")
js=js.replace('        render();\n        return {\n            lerEstado:', '''        render();
        if (opcoes.aviso) { estado.aviso=opcoes.aviso; render(); }
        if (opcoes.nufin) aplicarTitulo(opcoes.nufin);
        return {
            destruir: function() { desativado=true; elemento.textContent=''; },
            lerEstado:''')
for line in ['        previa: previa,\n','        criarEstado: criarEstado,\n','        aplicarSimulacao: aplicarSimulacao,\n','        aplicarEfetivacao: aplicarEfetivacao,\n']:
    js=js.replace(line,'')
js=re.sub(r'        cenarios: function \(\) \{[^\n]+\n','',js)
# Sugestões de tabelas nativas; código livre é sempre validado pelo Java.
a=js.index('    function selecaoPesquisa(');b=js.index('    function selecaoCriterio(',a)
js=js[:a]+'''    var numeroLista=0;
    function selecaoPesquisa(campo, valor, itens, rotulo) {
        var id='rateio-lista-'+(++numeroLista);
        return '<input data-campo="'+campo+'" value="'+esc(valor || '')+'" inputmode="numeric" aria-label="'+esc(rotulo)+'" list="'+id+'"><datalist id="'+id+'">'+itens.map(function(item){return '<option value="'+esc(item.id)+'" label="'+esc(item.nome || '')+'"></option>';}).join('')+'</datalist>';
    }
'''+js[b:]
js=js.replace('["Base", cartoes.base', '["Valor do título", estado.origem.valorTitulo == null ? "" : "R$ " + formatCents(window.RateioServidor.inteiroDecimal(estado.origem.valorTitulo,2))],\n                        ["Vencimento", estado.origem.vencimento || ""],\n                        ["Base", cartoes.base')
# Pesquisa simples por NUFIN, autorizada pelo mesmo contexto Java.
a=js.index('            function htmlPesquisa() {');b=js.index('            function legendaBotoes()',a)
js=js[:a]+'''            function htmlPesquisa() {
                return '<div class="rd-modal" role="dialog" aria-modal="true" aria-label="Consultar título"><div class="rd-modal-fundo" data-acao="fechar-pesquisa"></div><div class="rd-modal-caixa"><header class="rd-modal-topo"><h2>Consultar título financeiro</h2><button type="button" data-acao="fechar-pesquisa">Fechar</button></header><p class="rd-nota">Informe o NUFIN. A origem e as permissões são verificadas pelo Java.</p><div class="rd-filtros-modal"><div class="filter-group"><label>NUFIN</label><input class="filter-input" data-nufin-consulta inputmode="numeric"></div><div class="filter-group rd-filtro-acao"><button type="button" class="rd-primario" data-acao="consultar-nufin">Consultar</button></div></div></div></div>';
            }
'''+js[b:]
needle='            var abrirPesquisa = elemento.querySelector'
js=js.replace(needle,'''            var consultarNufin=elemento.querySelector('[data-acao=consultar-nufin]');
            if (consultarNufin) consultarNufin.addEventListener('click',function(){
                var valor=elemento.querySelector('[data-nufin-consulta]').value.trim();
                if(!/^[1-9][0-9]{0,9}$/.test(valor)){mostrarErro(new Error('Informe um NUFIN válido.'));return;}
                pesquisaAberta=false;aplicarTitulo(valor);
            });
'''+needle)
# Nenhuma consulta por HTML/SQL do JSP permanece alcançável.
js=js.replace("            if (estado.aviso) html += '<p class=\"rd-aviso\">' + esc(estado.aviso) + '</p>';", """            if (estado.aviso) html += '<p class="rd-aviso" role="status">' + esc(estado.aviso) + '</p>';
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
            }""")
a=js.index('        function montarUrlConsulta()');b=js.index('        function render()',a)
js=js[:a]+js[b:]
js=re.sub(r'                    if \(catalogo.origemTitulos\) \{\s*consultarTitulosServidor\(\);\s*return;\s*\}', '', js)
a=js.index('            var buscarTitulos =');b=js.index('            function desenharFornecedores()',a)
js=js[:a]+js[b:]
js=js.replace('["Vencimento", estado.origem.vencimento || ""]', '["Valor total do documento", estado.origem.valorDocumento == null ? "" : "R$ " + formatCents(window.RateioServidor.inteiroDecimal(estado.origem.valorDocumento,2))],\n                        ["Quantidade de parcelas", estado.origem.quantidadeParcelas == null ? "" : String(estado.origem.quantidadeParcelas)],\n                        ["Parcela selecionada", estado.origem.desdobramento || ""],\n                        ["Vencimento da parcela", estado.origem.vencimento || ""]')
(root/'web/js/rateio-view.js').write_text(js,encoding='utf-8')
print('Layout preservado. Execute Build-Dashboard.cjs para gerar o JSP simples.')
