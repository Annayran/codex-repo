(function (global) {
  'use strict';
  function iniciar(documento, bridge, origem) {
    var $ = function (id) { return documento.getElementById(id); };
    var estado = { contexto: null, simulacao: null, ocupado: false, pendente: null, incerto: false, alterado: false };
    function mensagem(texto, erro) { $('mensagem').textContent = texto; $('mensagem').dataset.erro = erro ? 'true' : 'false'; }
    function controles() {
      var permite = estado.contexto && estado.contexto.podeSimular && !estado.contexto.efetivada;
      $('entrada').disabled = !permite || estado.ocupado || estado.incerto;
      $('simular').disabled = !permite || estado.ocupado;
      $('atualizar').disabled = estado.ocupado || estado.incerto;
      $('simular').textContent = estado.incerto ? 'Tentar novamente' : (estado.simulacao ? 'Recalcular' : 'Simular');
      $('painel-motivo').hidden = !estado.simulacao;
      $('painel-criterio').hidden = $('modalidade').value !== 'CRITERIO';
      $('painel-manual').hidden = $('modalidade').value !== 'MANUAL';
    }
    function alterar() { estado.alterado = true; if (estado.simulacao) $('situacao').textContent = 'Entradas alteradas. Recalcule e analise o novo resultado.'; controles(); }
    function destino(valor) {
      var tr = documento.createElement('tr');
      ['codEmp', 'codCencus', 'percentual'].forEach(function (nome) {
        var td = documento.createElement('td'), input = documento.createElement('input');
        input.type = 'text'; input.inputMode = nome === 'percentual' ? 'decimal' : 'numeric'; input.dataset.campo = nome;
        input.setAttribute('aria-label', nome === 'codEmp' ? 'Empresa beneficiária' : nome === 'codCencus' ? 'Centro de resultado' : 'Percentual global');
        input.value = valor && valor[nome] != null ? String(valor[nome]) : ''; input.addEventListener('input', alterar);
        td.appendChild(input); tr.appendChild(td);
      });
      var td = documento.createElement('td'), botao = documento.createElement('button');
      botao.type = 'button'; botao.textContent = 'Remover'; botao.addEventListener('click', function () { tr.remove(); alterar(); });
      td.appendChild(botao); tr.appendChild(td); $('destinos').appendChild(tr);
    }
    function aplicarEntrada(entrada) {
      $('destinos').textContent = ''; $('modalidade').value = entrada ? entrada.modalidade : 'MANUAL';
      if (entrada && entrada.modalidade === 'CRITERIO') $('criterio').value = JSON.stringify([entrada.criterioId, entrada.criterioVersao]);
      else (entrada && entrada.percentuais.length ? entrada.percentuais : [null]).forEach(destino);
      estado.alterado = false;
    }
    function tabela(titulo, colunas, linhas) {
      var h = documento.createElement('h3'), t = documento.createElement('table'), cab = documento.createElement('tr'); h.textContent = titulo;
      colunas.forEach(function (c) { var th = documento.createElement('th'); th.textContent = c; cab.appendChild(th); });
      var thead = documento.createElement('thead'); thead.appendChild(cab); t.appendChild(thead);
      var body = documento.createElement('tbody'); linhas.forEach(function (linha) { var tr = documento.createElement('tr'); linha.forEach(function (v) { var td = documento.createElement('td'); td.textContent = v == null ? '—' : String(v); tr.appendChild(td); }); body.appendChild(tr); });
      t.appendChild(body); $('resultado').appendChild(h); $('resultado').appendChild(t);
    }
    function mostrar(s) {
      estado.simulacao = s; $('resultado').textContent = '';
      if (!s) { $('situacao').textContent = 'Nenhuma simulação carregada.'; controles(); return; }
      $('situacao').textContent = 'Simulação ' + s.id + ' · revisão ' + s.revisao + ' · ' + s.situacao + (s.pendencia ? ' · ' + s.pendencia : '');
      if (s.resultado) {
        tabela('Distribuição global', ['Empresa', 'CR', '% global', 'Valor (R$)', 'Destino'], s.resultado.parcelas.map(function (p) { return [p.codEmp, p.codCencus, p.percentualGlobal, p.valor, p.origem ? 'Origem' : 'Auxiliar previsto']; }));
        s.resultado.grupos.forEach(function (g) { tabela('Empresa ' + g.codEmp + ' · auxiliar previsto de R$ ' + g.valor, ['CR', '% interno', 'Valor (R$)'], g.linhas.map(function (l) { return [l.codCencus, l.percentual, l.valor]; })); });
      }
      estado.alterado = false; controles();
    }
    function chamar(nome, entrada) { return Promise.resolve().then(function () { return bridge[nome](entrada); }); }
    function carregar() {
      estado.ocupado = true; controles(); mensagem('Carregando documento…', false);
      return chamar('abrirContexto', { origem: origem }).then(function (c) {
        estado.contexto = c; origem = c.origem;
        $('contexto').textContent = c.origem.tipo + ' ' + c.origem.id + ' · empresa ' + c.empresaOrigem + ' · competência ' + (c.competencia || 'não definida') + ' · base R$ ' + (c.base || 'não definida');
        $('criterio').textContent = ''; var vazio = documento.createElement('option'); vazio.value = ''; vazio.textContent = 'Selecione'; $('criterio').appendChild(vazio);
        c.criterios.forEach(function (cr) { var op = documento.createElement('option'); op.value = JSON.stringify([cr.id, cr.versao]); op.textContent = cr.descricao; $('criterio').appendChild(op); });
        aplicarEntrada(c.simulacao && c.simulacao.entrada); mostrar(c.simulacao);
        var pendencias = c.pendencias || [];
        mensagem(c.efetivada ? 'Rateio já efetivado. Disponível para consulta.' : (pendencias.length ? 'Pendências do documento: ' + pendencias.join(', ') + '.' : ''), !c.efetivada && pendencias.length > 0);
      }).catch(function () { estado.contexto = null; mensagem('Não foi possível carregar o documento. Atualize o contexto para tentar novamente.', true); })
        .then(function () { estado.ocupado = false; controles(); });
    }
    function lerEntrada() {
      if ($('modalidade').value === 'CRITERIO') {
        if (!$('criterio').value) throw new Error('Selecione um critério e sua versão.');
        var criterio = JSON.parse($('criterio').value); return { modalidade: 'CRITERIO', criterioId: criterio[0], criterioVersao: criterio[1] };
      }
      var percentuais = Array.prototype.map.call($('destinos').querySelectorAll('tr'), function (tr) {
        var campos = tr.querySelectorAll('input'), emp = campos[0].value.trim(), cr = campos[1].value.trim(), p = campos[2].value.trim().replace(',', '.');
        if (!/^[1-9]\d{0,8}$/.test(emp) || !/^[1-9]\d{0,8}$/.test(cr) || !/^\d+(\.\d{1,6})?$/.test(p)) throw new Error('Confira empresa, centro de resultado e percentual de cada destino.');
        return { codEmp: Number(emp), codCencus: Number(cr), percentual: p };
      });
      if (!percentuais.length) throw new Error('Informe ao menos um destino.'); return { modalidade: 'MANUAL', percentuais: percentuais };
    }
    function enviar() {
      if (estado.ocupado) return Promise.resolve();
      if (!estado.pendente) {
        try {
          if (!global.crypto || typeof global.crypto.randomUUID !== 'function') throw new Error('Não foi possível iniciar a solicitação neste navegador.');
          var entrada = lerEntrada(), dados = { entrada: entrada, solicitacaoId: global.crypto.randomUUID() }, nome = estado.simulacao ? 'recalcular' : 'simular';
          if (estado.simulacao) { dados.simulacaoId = estado.simulacao.id; dados.revisaoEsperada = estado.simulacao.revisao; dados.motivo = $('motivo').value.trim(); if (!dados.motivo) throw new Error('Informe o motivo do recálculo.'); }
          else dados.origem = origem;
          estado.pendente = { nome: nome, dados: dados };
        } catch (e) { mensagem(e.message, true); return Promise.resolve(); }
      }
      estado.ocupado = true; controles(); mensagem('Calculando…', false);
      return chamar(estado.pendente.nome, estado.pendente.dados).then(function (s) {
        estado.pendente = null; estado.incerto = false; aplicarEntrada(s.entrada); mostrar(s); $('motivo').value = '';
        mensagem(s.pendencia ? 'Revise as pendências antes de continuar.' : 'Simulação registrada. Confira os valores.', !!s.pendencia);
      }).catch(function (e) {
        if (e && e.codigo && e.codigo !== 'ERRO_TECNICO') { estado.pendente = null; estado.incerto = false; mensagem('Operação não concluída: ' + e.codigo + '. Atualize o contexto se necessário.', true); }
        else { estado.incerto = true; mensagem('Não foi possível confirmar a resposta. Tente novamente para recuperar o resultado.', true); }
      }).then(function () { estado.ocupado = false; controles(); });
    }
    $('adicionar').addEventListener('click', function () { destino(null); alterar(); });
    $('modalidade').addEventListener('change', alterar); $('criterio').addEventListener('change', alterar);
    $('simular').addEventListener('click', enviar); $('atualizar').addEventListener('click', carregar);
    if (!bridge || !origem || ['abrirContexto', 'simular', 'recalcular'].some(function (nome) { return typeof bridge[nome] !== 'function'; })) {
      mensagem('A conexão com o rateio ainda não está disponível.', true); return { pronto: Promise.resolve(), estado: estado };
    }
    return { pronto: carregar(), atualizar: carregar, simular: enviar, estado: estado };
  }
  global.RateioModal = { iniciar: iniciar };
  if (typeof module !== 'undefined' && module.exports) module.exports = global.RateioModal;
})(typeof window !== 'undefined' ? window : globalThis);
