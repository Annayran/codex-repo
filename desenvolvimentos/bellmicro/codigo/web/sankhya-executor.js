(function (global) {
  'use strict';
  // Mesmo transporte usado no modal TOP Nutri. Confirmar compatibilidade na homologação.
  global.executarAcaoRateioSankhya = function (r) {
    var p = r.parametros[0];
    var body = { serviceName: 'ActionButtonsSP.executeJava', requestBody: { javaCall: {
      actionID: String(r.idAcao), refreshType: 'SEL',
      rows: { row: [{ field: [{ fieldName: 'NUFIN', $: String(r.linhas[0].NUFIN) }] }] },
      params: { param: [{ paramName: p.nome, name: p.nome, type: 'S', $: p.valor }] }
    } } };
    var controller = new AbortController();
    var timer = global.setTimeout(function () { controller.abort(); }, 30000);
    return global.fetch('/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json', {
      method: 'POST', credentials: 'same-origin', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body), signal: controller.signal
    }).then(function (http) {
      if (!http.ok) throw new Error('Falha HTTP ' + http.status + '. Confirme a operação pelo reenvio.');
      return http.json();
    }).then(function (resposta) {
      var mensagem = resposta.statusMessage || resposta.message;
      if (mensagem && typeof mensagem === 'object') mensagem = mensagem.$;
      if (!['1', '2'].includes(String(resposta.status))) {
        // Erro funcional JSON é normalizado pelo bridge; texto desconhecido permanece incerto.
        throw new Error(typeof mensagem === 'string' ? mensagem : 'Resposta nativa sem confirmação de sucesso.');
      }
      if (typeof mensagem !== 'string') throw new Error('Retorno nativo sem mensagem JSON.');
      return mensagem;
    }).finally(function () { global.clearTimeout(timer); });
  };
})(window);
