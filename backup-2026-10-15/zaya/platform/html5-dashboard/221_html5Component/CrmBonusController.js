/**
 * CrmBonusController.js
 * Dashboard HTML5 — dois bot&otilde;es no PDV Web (TGFCAB):
 *
 *  [Consultar Bonus CRM]  → modalCrmBonus.jsp   (identificacao + consulta bonus)
 *  [Aplicar Bonus CRM]    → modalCrmCampanhas.jsp (campanhas + finalizacao)
 *                           bloqueado se STATUSNFE != 'A'
 */
(function () {
  'use strict';

  var MODAL_BONUS_JSP = 'modalCrmBonus.jsp';
  var MODAL_CAMP_JSP  = 'modalCrmCampanhas.jsp';
  var MODAL_WIDTH     = 540;
  var MODAL_HEIGHT    = 520;
  var NUGDG           = 218;   /* nuGdg do componente — ajustar se necessario */
  var LAST_DADOS      = {};

  /* ----------------------------------------------------------------
     Renderiza os dois botoes
  ---------------------------------------------------------------- */
  function renderDashboard(dados) {
    LAST_DADOS = dados || {};
    var nunota    = LAST_DADOS && LAST_DADOS.NUNOTA    ? String(LAST_DADOS.NUNOTA)  : '';
    var codparc   = LAST_DADOS && LAST_DADOS.CODPARC   ? String(LAST_DADOS.CODPARC) : '';
    var statusNFe = LAST_DADOS && LAST_DADOS.STATUSNFE ? String(LAST_DADOS.STATUSNFE).trim().toUpperCase() : '';
    var nfeOk     = (statusNFe === 'A');

    var container = document.getElementById('crmBonusButtons');
    if (!container) {
      container = document.createElement('div');
      container.id = 'crmBonusButtons';
      document.body.appendChild(container);
    }
    container.innerHTML = '';
    container.style.cssText = 'display:flex;gap:8px;flex-wrap:wrap;padding:8px 0;';

    /* Botao 1 — Consultar Bonus */
    container.appendChild(criarBotao('Consultar Bonus CRM', '#22d07a', '#000', function () {
      var ctx = getContextoAtual();
      garantirRequestHash(ctx.nunota, function (hash) {
        abrirModal(MODAL_BONUS_JSP, ctx.nunota, ctx.codparc, hash);
      });
    }));

    /* Botao 2 — Aplicar Bonus (so quando NFe autorizada) */
    var btn2 = criarBotao(
      'Aplicar Bonus CRM',
      nfeOk ? '#3b82f6' : '#374151',
      nfeOk ? '#fff'    : '#9ca3af',
      function () {
        var ctx = getContextoAtual();
        if (!ctx.nfeOk) {
          alert('NF-e ainda nao autorizada (STATUSNFE = "' + (ctx.statusNFe || '-') + '").\nAguarde a autorizacao para aplicar o bonus.');
          return;
        }
        garantirRequestHash(ctx.nunota, function (hash) {
          abrirModal(MODAL_CAMP_JSP, ctx.nunota, ctx.codparc, hash);
        });
      }
    );
    if (!nfeOk) btn2.title = 'Disponivel apos autorizacao da NF-e (STATUSNFE = A)';
    container.appendChild(btn2);
  }

  /* ----------------------------------------------------------------
     Garante AD_REQHASH na TGFCAB — le o existente ou cria novo
  ---------------------------------------------------------------- */
  function garantirRequestHash(nunota, cb) {
    if (!nunota) { cb(''); return; }
    var sid = getCookie('JSESSIONID').replace('.master', '');
    var url = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession=' + sid;
    fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        serviceName: 'CRUDServiceProvider.loadRecords',
        requestBody: { dataSet: {
          rootEntity: 'CabecalhoNota', includePresentationFields: 'N', offsetPage: '0',
          criteria: { expression: { $: 'this.NUNOTA = ' + Number(nunota) } },
          entity: { fieldset: { list: 'AD_REQHASH,NUNOTA' } }
        }}
      })
    })
    .then(function (r) { return r.json(); })
    .then(function (resp) {
      var reg       = extrairPrimeiroRegistro(resp);
      var existente = reg && reg.AD_REQHASH ? reg.AD_REQHASH.trim() : '';
      if (existente) {
        console.log('[CRM-CTRL] hash existente:', existente.substring(0, 16) + '...');
        cb(existente);
      } else {
        var novo = gerarHash();
        console.log('[CRM-CTRL] novo hash:', novo.substring(0, 16) + '...');
        salvarHash(nunota, novo, function () { cb(novo); });
      }
    })
    .catch(function (e) { console.error('[CRM-CTRL] garantirRequestHash:', e); cb(gerarHash()); });
  }

  function salvarHash(nunota, hash, cb) {
    var sid = getCookie('JSESSIONID').replace('.master', '');
    fetch('/mge/service.sbr?serviceName=CabecalhoNota.salvarCabecalho&outputType=json&mgeSession=' + sid, {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        serviceName: 'CabecalhoNota.salvarCabecalho',
        requestBody: { cabecalhoNota: { NUNOTA: { $: nunota }, AD_REQHASH: { $: hash } } }
      })
    })
    .then(function () { cb(); })
    .catch(function (e) { console.error('[CRM-CTRL] salvarHash:', e); cb(); });
  }

  /* ----------------------------------------------------------------
     Abre o modal via SanPopup
  ---------------------------------------------------------------- */
  function abrirModal(jspName, nunota, codparc, hash) {
    var cb = Date.now();
    var url = '/mge/html5component.mge?entryPoint=' + jspName
            + '&nuGdg='       + encodeURIComponent(NUGDG)
            + '&codparc='     + encodeURIComponent(codparc)
            + '&nunota='      + encodeURIComponent(nunota)
            + '&requesthash=' + encodeURIComponent(hash)
            + '&_cb='         + encodeURIComponent(cb);
    console.log('[CRM-CTRL] abrirModal:', url);
    var popup = window.SanPopup || (window.parent && window.parent.SanPopup);
    if (popup) {
      popup.open(url, { width: MODAL_WIDTH, height: MODAL_HEIGHT, title: '' });
    } else {
      window.open(url, '_blank', 'width=' + MODAL_WIDTH + ',height=' + MODAL_HEIGHT);
    }
  }

  /* ----------------------------------------------------------------
     Helpers
  ---------------------------------------------------------------- */
  function criarBotao(texto, bg, color, onClick) {
    var btn = document.createElement('button');
    btn.textContent = texto;
    btn.style.cssText = 'padding:8px 16px;border:none;border-radius:8px;cursor:pointer;' +
      'font-size:13px;font-weight:500;background:' + bg + ';color:' + color + ';transition:opacity 0.2s;';
    btn.onmouseover = function () { btn.style.opacity = '0.8'; };
    btn.onmouseout  = function () { btn.style.opacity = '1'; };
    btn.onclick = onClick;
    return btn;
  }

  function gerarHash() {
    var a = new Uint8Array(32);
    if (window.crypto && window.crypto.getRandomValues) window.crypto.getRandomValues(a);
    else for (var i = 0; i < 32; i++) a[i] = Math.floor(Math.random() * 256);
    return Array.from(a).map(function (b) { return ('0' + b.toString(16)).slice(-2); }).join('');
  }

  function getCookie(name) {
    var m = document.cookie.match(new RegExp('(?:^|;)\\s*' + name + '=([^;]*)'));
    return m ? decodeURIComponent(m[1]) : '';
  }

  function extrairPrimeiroRegistro(resp) {
    try {
      var entities = resp && resp.responseBody && resp.responseBody.entities;
      if (!entities) return null;
      var meta  = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
      var names = Array.isArray(meta) ? meta.map(function (f) { return f.name || ''; })
                : (meta && meta.name ? [meta.name] : []);
      var ent = entities.entity;
      if (!ent) return null;
      var row = Array.isArray(ent) ? ent[0] : ent;
      var obj = {};
      names.forEach(function (n, i) { var c = row['f' + i]; obj[n] = c ? (c.$ || '') : ''; });
      return obj;
    } catch (e) { return null; }
  }

  function getContextoAtual() {
    function lerCampo(obj, nome) {
      try {
        if (!obj) return '';
        if (obj[nome] != null && obj[nome] !== '') return String(obj[nome]);
        if (obj[nome.toLowerCase()] != null && obj[nome.toLowerCase()] !== '') return String(obj[nome.toLowerCase()]);
        if (obj.getCampo && typeof obj.getCampo === 'function') {
          var v = obj.getCampo(nome);
          if (v != null && v !== '') return String(v);
        }
      } catch (e) {}
      return '';
    }

    function extrairDeFonte(src) {
      if (!src) return null;
      var nunota = lerCampo(src, 'NUNOTA');
      var codparc = lerCampo(src, 'CODPARC');
      var status = lerCampo(src, 'STATUSNFE');

      if ((!nunota || !codparc) && src.linhas && src.linhas.length) {
        var l0 = src.linhas[0];
        nunota = nunota || lerCampo(l0, 'NUNOTA');
        codparc = codparc || lerCampo(l0, 'CODPARC');
        status = status || lerCampo(l0, 'STATUSNFE');
      }
      if ((!nunota || !codparc) && src.linhaAtual) {
        nunota = nunota || lerCampo(src.linhaAtual, 'NUNOTA');
        codparc = codparc || lerCampo(src.linhaAtual, 'CODPARC');
        status = status || lerCampo(src.linhaAtual, 'STATUSNFE');
      }

      nunota = String(nunota || '').trim();
      codparc = String(codparc || '').trim();
      status = String(status || '').trim().toUpperCase();
      if (!nunota && !codparc && !status) return null;
      return { nunota: nunota, codparc: codparc, statusNFe: status };
    }

    /* Prioridade das fontes de contexto:
       1. LAST_DADOS — dado enviado explicitamente pelo Sankhya W ao renderizar
          este componente dashboard (via init ou postMessage). E a fonte mais
          confiavel porque reflete a nota para a qual o dashboard foi ativado.
       2. window.sankhyaContext — contexto do proprio iframe do controller.
       3. parent/top.sankhyaContext — usado apenas como fallback de ultimo recurso.
          Na tela de recebimento, parent.sankhyaContext aponta para a ultima nota
          *processada* naquela tela (nota anterior), nao para a nota atual, o que
          causava o bug de abertura do modal na nota errada. */
    var fontes = [];
    fontes.push(LAST_DADOS);
    try { fontes.push(window.sankhyaContext); } catch (e) {}
    try { fontes.push(window.parent && window.parent.sankhyaContext); } catch (e) {}
    try { fontes.push(window.top && window.top.sankhyaContext); } catch (e) {}

    var ctxBase = null;
    for (var i = 0; i < fontes.length; i++) {
      ctxBase = extrairDeFonte(fontes[i]);
      if (ctxBase && ctxBase.nunota) break;
    }
    if (!ctxBase) ctxBase = { nunota: '', codparc: '', statusNFe: '' };

    var nunota    = ctxBase.nunota;
    var codparc   = ctxBase.codparc;
    var statusNFe = ctxBase.statusNFe;
    var nfeOk     = (statusNFe === 'A');
    console.log('[CRM-CTRL] contexto atual: NUNOTA=' + nunota + ' CODPARC=' + codparc + ' STATUSNFE=' + statusNFe);
    return { nunota: nunota, codparc: codparc, statusNFe: statusNFe, nfeOk: nfeOk };
  }

  /* ----------------------------------------------------------------
     Init — tenta ler dados do contexto Sankhya W
  ---------------------------------------------------------------- */
  function init() {
    var dados = {};
    if (window.sankhyaContext) {
      dados = window.sankhyaContext;
    } else {
      var p = new URLSearchParams(window.location.search || '');
      dados = {
        NUNOTA:    p.get('nunota')    || p.get('NUNOTA')    || '',
        CODPARC:   p.get('codparc')   || p.get('CODPARC')   || '',
        STATUSNFE: p.get('statusnfe') || p.get('STATUSNFE') || ''
      };
    }
    renderDashboard(dados);
  }

  window.addEventListener('message', function (e) {
    if (e.data && (e.data.NUNOTA || e.data.nunota)) renderDashboard(e.data);
  });

  if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', init);
  else init();

}());
