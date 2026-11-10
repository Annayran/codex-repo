(function (global) {
  'use strict';
  if (!global.React || !global.ReactDOM || !global.RateioView || !global.RateioServidor || !global.criarBridgeAcoesFinanceiroRateio) {
    if (global.rateioFalhaCarga) global.rateioFalhaCarga();
    return;
  }
  var h = global.React.createElement;
  function DashboardRateio() {
    var host = global.React.useRef(null);
    global.React.useLayoutEffect(function () {
      var view;
      try {
        var q = new URLSearchParams(global.location.search);
        function parametro(nome) {
          var itens = q.getAll(nome);
          if (itens.length === 0) return '';
          if (itens.length !== 1 || !/^[1-9]\d{0,9}$/.test(itens[0])) throw new Error('Confira o parâmetro ' + nome + ' na ação de abertura.');
          return itens[0];
        }
        var ids = { simular: parametro('codAcaoSimular'), recalcular: parametro('codAcaoRecalcular'), efetivar: parametro('codAcaoEfetivar') };
        var nufin = parametro('nufin');
        var configurado = ids.simular && ids.recalcular && ids.efetivar;
        if (configurado && nufin) global.criarBridgeAcoesFinanceiroRateio(global.executarAcaoRateioSankhya, Object.assign({ nufin: nufin }, ids));
        view = global.RateioView.mount(host.current, { ids: ids, nufin: configurado ? nufin : '', executor: global.executarAcaoRateioSankhya,
          aviso: configurado ? '' : 'Abra o painel pelo botão de rateio do Financeiro para receber os códigos das ações.' });
      } catch (e) {
        host.current.textContent = e.message || 'Não foi possível iniciar o dashboard.';
        host.current.setAttribute('role', 'alert');
      }
      return function () { if (view) view.destruir(); };
    }, []);
    // React controla o ciclo de vida; o componente visual preserva o DOM/CSS do cliente.
    return h('div', { ref: host });
  }
  var root = global.ReactDOM.createRoot(global.document.getElementById('rateio-despesas-root'));
  root.render(h(DashboardRateio));
})(window);
