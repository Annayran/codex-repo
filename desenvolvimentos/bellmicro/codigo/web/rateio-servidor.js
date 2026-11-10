(function (global) {
  'use strict';
  function descreverPendencia(codigo,motivo) {
    if(codigo!=='BASE_INCOMPLETA')return codigo;
    var mensagens={
      TIPO_NAO_DESPESA:'O título selecionado não é despesa (RECDESP diferente de -1).',
      TITULO_PROVISIONADO:'O título selecionado é provisão e não integra a base de despesas reais.',
      DOCUMENTO_RENEGOCIADO:'Há renegociação no documento. A composição precisa ser verificada para evitar somar títulos em duplicidade.',
      VENCIMENTO_SELECIONADO_AUSENTE:'A parcela selecionada não possui DTVENC para definir a competência.',
      IDENTIDADE_PARCELA_INVALIDA:'A apuração encontrou uma identificação de parcela inválida ou repetida.',
      VALOR_PARCELA_INVALIDO:'Uma parcela está sem valor nominal positivo ou possui precisão incompatível com centavos.',
      SEM_PARCELAS_ELEGIVEIS:'Não há parcelas de despesa não provisionadas elegíveis para esta origem.',
      REFERENCIA_FORA_DO_GRUPO:'A parcela selecionada não integra o grupo de despesas elegíveis da nota.',
      VENCIMENTO_ALTERADO_NA_CONSULTA:'O vencimento mudou durante a consulta. Atualize o título antes de simular.'
    };
    return mensagens[motivo]?'BASE_INCOMPLETA — '+mensagens[motivo]:codigo;
  }
  function inteiroDecimal(valor, escala) {
    var s = String(valor), m = /^(\d+)(?:\.(\d+))?$/.exec(s);
    if (!m) throw new Error('Decimal inválido na resposta do servidor.');
    var frac = (m[2] || '').padEnd(escala + 1, '0');
    var unidades = BigInt(m[1]) * (10n ** BigInt(escala)) + BigInt(frac.slice(0, escala) || '0');
    if (frac.charAt(escala) >= '5') unidades++;
    if (unidades > BigInt(Number.MAX_SAFE_INTEGER)) throw new Error('Valor excede a precisão de apresentação.');
    return Number(unidades);
  }
  function apresentar(s, contexto, nufin) {
    if (contexto.base != null) inteiroDecimal(contexto.base, 2);
    var f = contexto.financeiro || {};
    if (f.NUFIN && String(f.NUFIN) !== String(nufin)) throw new Error('Retorno de outro título financeiro.');
    var estado = {
      origem: { nufin: String(nufin), nunota: contexto.origem.tipo === 'NOTA' ? contexto.origem.id : '',
        tipo: contexto.origem.tipo, documento: contexto.origem.tipo + ' ' + contexto.origem.id,
        empresa: String(contexto.empresaOrigem), empresaNome: '', natureza: contexto.natureza,
        parceiro: contexto.parceiro || '', top: '', tipoTitulo: '', base: contexto.base,
        competencia: (contexto.competencia || '').slice(0, 7), nufinPesquisado: true, auxiliar: false, exemplo: false },
      modo: s && s.entrada.modalidade === 'CRITERIO' ? 'criterio' : 'manual',
      criterioId: s && s.entrada.criterioId || '', linhas: [],
      situacao: contexto.efetivada ? 'E' : (s ? (s.pendencia ? 'P' : 'S') : null),
      versao: s ? s.revisao : null, data: s ? s.data : null, usuario: s ? s.usuario : null,
      pendencias: (contexto.pendencias || []).map(function(c){return descreverPendencia(c,contexto.motivoBase);}), resultado: null, aviso: '', modoTela: 'servidor',
      podeSimular: contexto.podeSimular === true, podeEfetivar: contexto.podeEfetivar === true,
      wire: s || null
    };
    if (f.NUFIN) {
      estado.origem.documento = f.NUMNOTA ? 'NF ' + f.NUMNOTA : 'Título ' + f.NUFIN;
      estado.origem.empresaNome = String(f.CODEMP) === String(contexto.empresaOrigem) ? (f.NOMEFANTASIA || '') : '';
      estado.origem.parceiro = f.NOMEPARC || contexto.parceiro || '';
      estado.origem.top = f.CODTIPOPER || '';
      estado.origem.valorTitulo = f.VLRDESDOB;
      estado.origem.vencimento = f.DTVENC;
      estado.origem.desdobramento = f.DESDOBRAMENTO;
    }
    var resumo=contexto.resumoFinanceiro;
    if(resumo){estado.origem.valorDocumento=resumo.valorTotal;estado.origem.quantidadeParcelas=resumo.quantidadeParcelas;estado.origem.parcelas=resumo.parcelas || [];}
    if (!s) return estado;
    if (!s.id || !Number.isInteger(s.revisao) || !s.entrada) throw new Error('Fotografia do servidor inválida.');
    if(!contexto.efetivada&&s.nufinReferencia&&(String(s.nufinReferencia)!==String(nufin)||s.competencia!==contexto.competencia||s.base!==contexto.base)){
      estado.situacao='D';estado.aviso='A parcela, o vencimento ou o total diverge da revisão registrada. Recalcule e analise o resultado.';
    }
    if(s.pendencia){
      var descricao=descreverPendencia(s.pendencia,s.motivoBase);
      var atualDetalhada=descricao===s.pendencia&&estado.pendencias.some(function(p){return p.indexOf(s.pendencia+' — ')===0;});
      if(!atualDetalhada&&estado.pendencias.indexOf(descricao)<0)estado.pendencias.push(descricao);
    }
    estado.linhas = (s.entrada.percentuais || []).map(function (p) {
      return { empresa: String(p.codEmp), cr: String(p.codCencus), percentual: p.percentual };
    });
    if (s.resultado) {
      if (s.entrada.modalidade === 'CRITERIO') estado.linhas = s.resultado.parcelas.map(function (p) {
        var unidades = inteiroDecimal(p.percentualGlobal, 6);
        return { empresa: String(p.codEmp), cr: String(p.codCencus), percentual: (unidades / 1000000).toFixed(6) };
      });
      estado.resultado = {
        parcelas: s.resultado.parcelas.map(function (p) {
          return { empresa: String(p.codEmp), cr: String(p.codCencus), milhoes: inteiroDecimal(p.percentualGlobal, 6),
            cents: inteiroDecimal(p.valor, 2), destino: p.origem ? 'O' : 'A', semValor: inteiroDecimal(p.valor, 2) === 0 };
        }),
        auxiliares: s.resultado.grupos.map(function (g) {
          return { empresa: String(g.codEmp), cents: inteiroDecimal(g.valor, 2), nufin: null, recdesp: 0,
            internos: g.linhas.map(function (l) { return { cr: String(l.codCencus), cents: inteiroDecimal(l.valor, 2), milhoes: inteiroDecimal(l.percentual, 6) }; }) };
        })
      };
    }
    return estado;
  }
  function chave() {
    var bytes = new Uint8Array(16); global.crypto.getRandomValues(bytes);
    return Array.from(bytes).map(function (v) { return v.toString(16).padStart(2, '0'); }).join('');
  }
  function criar(executor, ids, atualizar) {
    var contexto = null, estado = null, bridge = null, nufin = null, pendente = null, ocupado = false, incerto = false, diagnosticos = [];
    function registrar(d) {
      if (!d || typeof d.referencia !== 'string') return;
      diagnosticos.push(d);if(diagnosticos.length>20)diagnosticos.shift();
    }
    function notificar() { atualizar({ estado: estado, contexto: contexto, ocupado: ocupado, incerto: incerto, pendente: pendente, diagnosticos: diagnosticos.slice() }); }
    function falha(e) {
      registrar(e && e.diagnostico);
      if (estado) estado.aviso = e && e.codigo ? 'Operação não concluída: ' + e.codigo + (e.mensagem ? '. ' + e.mensagem : '') : 'Resposta não confirmada. Reenvie a mesma operação.';
      if (e && e.codigo && e.codigo !== 'ERRO_TECNICO') { pendente = null; incerto = false; }
      else if (pendente) incerto = true;
      throw e;
    }
    return {
      selecionar: function (id) {
        if (ocupado || pendente) return Promise.reject(new Error('Conclua ou recupere a operação pendente antes de trocar o título.'));
        var nova = global.criarBridgeAcoesFinanceiroRateio(executor, Object.assign({}, ids, { nufin: id }));
        ocupado = true; contexto = null; estado = null; notificar();
        return nova.abrirContexto().then(function (c) {
          if (!c.origem || !Array.isArray(c.criterios) || !Array.isArray(c.pendencias)) throw new Error('Contexto inválido.');
          registrar(c.diagnostico);nufin = id; bridge = nova; contexto = c; estado = apresentar(c.simulacao, c, id); return estado;
        }).catch(function(e){registrar(e && e.diagnostico);throw e;}).finally(function () { ocupado = false; notificar(); });
      },
      executar: function (nome, entrada, motivo) {
        if (ocupado || !contexto || !bridge) return Promise.reject(new Error('Aguarde o contexto do título.'));
        if (!pendente) {
          if (nome !== 'efetivar' && !contexto.podeSimular) return Promise.reject({ codigo: 'SEM_PERMISSAO' });
          if (nome === 'efetivar' && !contexto.podeEfetivar) return Promise.reject({ codigo: 'EFETIVACAO_NAO_DISPONIVEL' });
          var dados = { solicitacaoId: chave() };
          if (nome === 'simular') dados.entrada = entrada;
          else {
            if (!estado.wire) return Promise.reject(new Error('Fotografia inexistente.'));
            dados.simulacaoId = estado.wire.id; dados.revisaoEsperada = estado.wire.revisao;
            if (nome === 'efetivar') dados.hashEsperado = estado.wire.hash;
            else { if (!motivo || !motivo.trim()) return Promise.reject({ codigo: 'COMPOSICAO_INVALIDA' }); dados.entrada = entrada; dados.motivo = motivo; }
          }
          pendente = { nome: nome, dados: JSON.parse(JSON.stringify(dados)) };
        }
        ocupado = true; notificar();
        var comando = pendente;
        return bridge[comando.nome](comando.dados).then(function (s) {
          registrar(s.diagnostico);
          if (comando.nome === 'efetivar') throw new Error('Contrato financeiro ainda não homologado. Consulte o backend.');
          var atual = Object.assign({},contexto);
          if(Object.prototype.hasOwnProperty.call(s,'base'))atual.base=s.base;
          if(Object.prototype.hasOwnProperty.call(s,'competencia'))atual.competencia=s.competencia;
          if(Object.prototype.hasOwnProperty.call(s,'motivoBase'))atual.motivoBase=s.motivoBase;
          var novo = apresentar(s, atual, nufin);
          contexto=atual;contexto.simulacao = s; estado = novo; pendente = null; incerto = false;
          estado.aviso = s.pendencia ? 'Revise as pendências retornadas pelo servidor.' : 'Simulação registrada no servidor. Confira os valores.';
          return estado;
        }).catch(falha).finally(function () { ocupado = false; notificar(); });
      }
    };
  }
  global.RateioServidor = { criar: criar, apresentar: apresentar, inteiroDecimal: inteiroDecimal };
  if (typeof module !== 'undefined') module.exports = global.RateioServidor;
})(typeof window !== 'undefined' ? window : globalThis);
