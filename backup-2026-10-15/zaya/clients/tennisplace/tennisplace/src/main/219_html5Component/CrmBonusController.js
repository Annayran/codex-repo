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

  /* ----------------------------------------------------------------
     Renderiza os dois botoes
  ---------------------------------------------------------------- */
  function renderDashboard(dados) {
    var nunota    = dados && dados.NUNOTA    ? String(dados.NUNOTA)  : '';
    var codparc   = dados && dados.CODPARC   ? String(dados.CODPARC) : '';
    var statusNFe = dados && dados.STATUSNFE ? String(dados.STATUSNFE).trim().toUpperCase() : '';
    var nfeOk     = (statusNFe === 'A');

    var container = document.getElementById('crmBonusButtons') || document.body;
    container.innerHTML = '';
    container.style.cssText = 'display:flex;gap:8px;flex-wrap:wrap;padding:8px 0;';

    /* Botao 1 — Consultar Bonus */
    container.appendChild(criarBotao('Consultar Bonus CRM', '#22d07a', '#000', function () {
      garantirRequestHash(nunota, function (hash) {
        abrirModal(MODAL_BONUS_JSP, nunota, codparc, hash);
      });
    }));

    /* Botao 2 — Aplicar Bonus (so quando NFe autorizada) */
    var btn2 = criarBotao(
      'Aplicar Bonus CRM',
      nfeOk ? '#3b82f6' : '#374151',
      nfeOk ? '#fff'    : '#9ca3af',
      function () {
        if (!nfeOk) {
          alert('NF-e ainda nao autorizada (STATUSNFE = "' + (statusNFe || '-') + '").\nAguarde a autorizacao para aplicar o bonus.');
          return;
        }
        garantirRequestHash(nunota, function (hash) {
          abrirModal(MODAL_CAMP_JSP, nunota, codparc, hash);
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
    var url = '/mge/html5component.mge?entryPoint=' + jspName
            + '&nuGdg='       + encodeURIComponent(NUGDG)
            + '&codparc='     + encodeURIComponent(codparc)
            + '&nunota='      + encodeURIComponent(nunota)
            + '&requesthash=' + encodeURIComponent(hash);
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
