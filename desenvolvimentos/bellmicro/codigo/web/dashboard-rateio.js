(function (global) {
  'use strict';
  var iniciado = false;
  // O dashboard fornece o executor do SDK instalado; não inventar endpoint nativo.
  global.iniciarDashboardRateio = function (executarAcao) {
    if (iniciado) throw new Error('Dashboard de rateio já iniciado.');
    var query = new URLSearchParams(global.location.search);
    function unico(nome) {
      var valores = query.getAll(nome);
      if (valores.length !== 1) throw new Error('Parâmetro ausente ou duplicado: ' + nome);
      return valores[0];
    }
    var nufin = unico('nufin');
    var bridge = global.criarBridgeAcoesFinanceiroRateio(executarAcao, {
      nufin: nufin, simular: unico('codAcaoSimular'),
      recalcular: unico('codAcaoRecalcular'), efetivar: unico('codAcaoEfetivar')
    });
    var modal = global.RateioModal.iniciar(global.document, bridge, { tipo: 'TITULO', id: nufin });
    iniciado = true;
    return modal;
  };
  global.document.getElementById('mensagem').textContent =
    'Aguardando a integração do executor de ações do dashboard.';
})(window);
