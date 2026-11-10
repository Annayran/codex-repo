(function (global) {
  'use strict';
  function criar(executarAcao, opcoes) {
    if (typeof executarAcao !== 'function' || !opcoes) throw new Error('Informe o executor nativo e os IDs das ações.');
    var nufin = String(opcoes.nufin), ids = {};
    if (!/^[1-9]\d{0,9}$/.test(nufin)) throw new Error('NUFIN inválido.');
    ['simular', 'recalcular', 'efetivar'].forEach(function (nome) {
      var id = String(opcoes[nome]);
      if (!/^[1-9]\d{0,9}$/.test(id)) throw new Error('Informe o ID cadastrado de ' + nome + '.');
      ids[nome] = id;
    });
    if (ids.simular === ids.recalcular || ids.simular === ids.efetivar || ids.recalcular === ids.efetivar) throw new Error('As três ações precisam de IDs distintos.');
    function erroRecebido(erro) {
      var e = erro;
      var texto = typeof e === 'string' ? e : (e && typeof e.message === 'string' ? e.message : null);
      if (texto !== null) {
        // Native action errors wrap the JSON envelope in this known prefix.
        texto = texto.replace(/^\s*<b>Regra Personalizada:<\/b>\s*<br\s*\/?>\s*/i, '');
        try { e = JSON.parse(texto); } catch (_) { return erro; }
      }
      if (e && e.codigo) return { codigo: e.codigo, mensagem: e.mensagem || 'Operação não concluída.', orientacao: e.orientacao, diagnostico: e.diagnostico };
      if (e && typeof e.message === 'string') { try { var json = JSON.parse(e.message); if (json.codigo) return { codigo: json.codigo, mensagem: json.mensagem }; } catch (_) {} }
      return erro;
    }
    function chamar(nome, payload, operacaoEsperada) {
      // The dashboard's installed SDK adapter performs the native call and extracts mensagemRetorno.
      // No URL, undocumented service, credentials, guessed action ID or generic SQL is embedded here.
      var requisicao = { idAcao: ids[nome], instancia: 'Financeiro', linhas: [{ NUFIN: nufin }],
        parametros: [{ nome: 'RATEIO_JSON', tipo: 'S', valor: JSON.stringify(payload) }] };
      return Promise.resolve().then(function () { return executarAcao(requisicao); }).then(function (resposta) {
        var envelope = typeof resposta === 'string' ? JSON.parse(resposta) : resposta;
        if (!envelope || typeof envelope.sucesso !== 'boolean') throw new Error('Resposta da ação de rateio inválida.');
        if (!envelope.sucesso) throw erroRecebido(envelope);
        if (envelope.operacao !== operacaoEsperada || !envelope.dados || typeof envelope.dados !== 'object' || Array.isArray(envelope.dados)) throw new Error('Resposta incompatível com a ação solicitada.');
        return Object.assign({}, envelope.dados, envelope.diagnostico ? {diagnostico:envelope.diagnostico} : {});
      }).catch(function (erro) { throw erroRecebido(erro); });
    }
    return {
      abrirContexto: function () { return chamar('simular', { modo: 'CONSULTAR' }, 'CONSULTAR'); },
      simular: function (d) { return chamar('simular', { modo: 'SIMULAR', entrada: d.entrada, solicitacaoId: d.solicitacaoId }, 'SIMULAR'); },
      recalcular: function (d) { return chamar('recalcular', { simulacaoId: d.simulacaoId, revisaoEsperada: d.revisaoEsperada, entrada: d.entrada, motivo: d.motivo, solicitacaoId: d.solicitacaoId }, 'RECALCULAR'); },
      efetivar: function (d) { return chamar('efetivar', { simulacaoId: d.simulacaoId, revisaoEsperada: d.revisaoEsperada, hashEsperado: d.hashEsperado, solicitacaoId: d.solicitacaoId }, 'EFETIVAR'); }
    };
  }
  global.criarBridgeAcoesFinanceiroRateio = criar;
  if (typeof module !== 'undefined' && module.exports) module.exports = criar;
})(typeof window !== 'undefined' ? window : globalThis);
