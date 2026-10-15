<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8"/>
<title>CRM Bonus - Aplicar Campanha</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@300;400;500;600&family=DM+Mono:wght@400;500&display=swap" rel="stylesheet">
<style>
*, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
:root {
  --bg: #0f1117; --surface: #181c27; --surface2: #1f2436;
  --border: rgba(255,255,255,0.07); --border2: rgba(255,255,255,0.12);
  --text: #e8eaf0; --muted: #6b7280; --accent: #22d07a; --accent-dim: #16a358;
  --warn: #f59e0b; --danger: #ef4444; --info: #60a5fa;
  --radius: 12px; --radius-sm: 8px;
  --font: 'DM Sans', sans-serif; --mono: 'DM Mono', monospace;
  --transition: 0.2s ease;
}
html, body {
  height: 100%; font-family: var(--font); background: var(--bg);
  color: var(--text); font-size: 14px; line-height: 1.6;
  -webkit-font-smoothing: antialiased;
}
.modal-wrap { display: flex; flex-direction: column; height: 100%; overflow: hidden; }
.modal-header {
  display: flex; align-items: center; gap: 10px; padding: 16px 20px;
  border-bottom: 1px solid var(--border); flex-shrink: 0; background: var(--surface);
}
.logo {
  width: 28px; height: 28px; background: var(--accent); border-radius: 7px;
  display: flex; align-items: center; justify-content: center;
  font-size: 13px; font-weight: 700; color: #000; flex-shrink: 0;
}
.title-block { flex: 1; min-width: 0; }
.title-block h1 { font-size: 14px; font-weight: 600; line-height: 1.2; }
.title-block p  { font-size: 11px; color: var(--muted); font-family: var(--mono); }
.badge { display: inline-flex; align-items: center; padding: 2px 8px; border-radius: 20px; font-size: 11px; font-weight: 500; white-space: nowrap; }
.badge-step { background: rgba(96,165,250,0.15); color: var(--info); }
.badge-ok   { background: rgba(34,208,122,0.15);  color: var(--accent); }
.badge-warn { background: rgba(245,158,11,0.12);  color: var(--warn); }
.modal-body {
  flex: 1; overflow-y: auto; padding: 20px;
  scrollbar-width: thin; scrollbar-color: var(--surface2) transparent;
}
.modal-body::-webkit-scrollbar { width: 4px; }
.modal-body::-webkit-scrollbar-thumb { background: var(--surface2); border-radius: 2px; }
.modal-footer {
  display: flex; gap: 8px; padding: 14px 20px;
  border-top: 1px solid var(--border); flex-shrink: 0; background: var(--surface);
}
.steps-bar { display: flex; gap: 4px; margin-bottom: 16px; }
.step-dot { height: 3px; flex: 1; border-radius: 2px; background: var(--surface2); transition: background var(--transition); }
.step-dot.done   { background: var(--accent-dim); }
.step-dot.active { background: var(--accent); }
.panel { display: none; flex-direction: column; gap: 16px; animation: fadeIn 0.25s ease; }
.panel.active { display: flex; }
@keyframes fadeIn { from { opacity: 0; transform: translateY(6px); } to { opacity: 1; transform: none; } }
.info-row { display: flex; gap: 8px; flex-wrap: wrap; }
.info-chip {
  display: flex; align-items: center; gap: 6px;
  background: var(--surface2); border: 1px solid var(--border2);
  border-radius: var(--radius-sm); padding: 6px 10px; font-family: var(--mono); font-size: 12px;
}
.info-chip .lbl { color: var(--muted); font-size: 10px; text-transform: uppercase; letter-spacing: 0.6px; }
.info-chip .val { color: var(--text); font-weight: 500; }
.alert {
  display: flex; align-items: flex-start; gap: 10px; padding: 12px 14px;
  border-radius: var(--radius-sm); border: 1px solid; font-size: 13px; line-height: 1.5;
}
.alert-icon { font-size: 13px; flex-shrink: 0; margin-top: 1px; font-family: var(--mono); }
.alert-info    { background: rgba(96,165,250,0.08);  border-color: rgba(96,165,250,0.25);  color: var(--info); }
.alert-success { background: rgba(34,208,122,0.08);  border-color: rgba(34,208,122,0.25);  color: var(--accent); }
.alert-warn    { background: rgba(245,158,11,0.08);  border-color: rgba(245,158,11,0.25);  color: var(--warn); }
.alert-error   { background: rgba(239,68,68,0.08);   border-color: rgba(239,68,68,0.25);   color: var(--danger); }
.alert .alert-text { color: var(--text); }
.alert .alert-text strong { color: inherit; }
.spinner-wrap { display: flex; align-items: center; gap: 10px; color: var(--muted); font-size: 13px; }
.spinner {
  width: 18px; height: 18px; border: 2px solid var(--surface2);
  border-top-color: var(--accent); border-radius: 50%;
  animation: spin 0.7s linear infinite; flex-shrink: 0;
}
@keyframes spin { to { transform: rotate(360deg); } }
.btn {
  display: inline-flex; align-items: center; justify-content: center; gap: 6px;
  padding: 9px 16px; border-radius: var(--radius-sm); font-family: var(--font);
  font-size: 13px; font-weight: 500; cursor: pointer; border: 1px solid transparent;
  transition: all var(--transition); white-space: nowrap;
}
.btn:disabled { opacity: 0.4; cursor: not-allowed; }
.btn-primary   { background: var(--accent); color: #000; border-color: var(--accent); }
.btn-primary:not(:disabled):hover { background: #1ebf6e; }
.btn-secondary { background: var(--surface2); color: var(--text); border-color: var(--border2); }
.btn-secondary:not(:disabled):hover { border-color: var(--accent); color: var(--accent); }
.btn-ghost { background: transparent; color: var(--muted); border-color: transparent; }
.btn-ghost:not(:disabled):hover { color: var(--text); }
.section-title { font-size: 12px; font-weight: 600; color: var(--muted); text-transform: uppercase; letter-spacing: 0.7px; }
.campaign-list { display: flex; flex-direction: column; gap: 8px; }
.campaign-item {
  display: flex; align-items: center; gap: 12px; background: var(--surface2);
  border: 1px solid var(--border); border-radius: var(--radius-sm);
  padding: 12px 14px; cursor: pointer; transition: all var(--transition);
}
.campaign-item:hover    { border-color: var(--border2); background: #232840; }
.campaign-item.selected { border-color: var(--accent); background: rgba(34,208,122,0.06); }
.campaign-radio {
  width: 18px; height: 18px; border-radius: 50%; border: 2px solid var(--border2);
  flex-shrink: 0; transition: all var(--transition);
  display: flex; align-items: center; justify-content: center;
}
.campaign-item.selected .campaign-radio { border-color: var(--accent); background: var(--accent); }
.campaign-radio::after { content: ''; width: 6px; height: 6px; border-radius: 50%; background: #000; opacity: 0; transition: opacity var(--transition); }
.campaign-item.selected .campaign-radio::after { opacity: 1; }
.campaign-info { flex: 1; min-width: 0; }
.campaign-name { font-size: 13px; font-weight: 500; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.campaign-desc { font-size: 11px; color: var(--muted); margin-top: 2px; }
.campaign-tag  { font-size: 10px; font-family: var(--mono); color: var(--muted); flex-shrink: 0; }
.result-box {
  background: rgba(34,208,122,0.06); border: 1px solid rgba(34,208,122,0.2);
  border-radius: var(--radius); padding: 16px;
}
.result-row { display: flex; justify-content: space-between; align-items: center; padding: 6px 0; border-bottom: 1px solid var(--border); }
.result-row:last-child { border: none; padding-bottom: 0; }
.result-row .rk { font-size: 12px; color: var(--muted); }
.result-row .rv { font-size: 13px; font-weight: 500; font-family: var(--mono); }
.result-row .rv.green { color: var(--accent); }
</style>
</head>
<body>
<div class="modal-wrap">

  <div class="modal-header">
    <div class="logo">CB</div>
    <div class="title-block">
      <h1>CRM Bonus - Aplicar</h1>
      <p id="headerSub">Carregando campanhas</p>
    </div>
    <span id="headerBadge" class="badge badge-step">Passo 1/2</span>
  </div>

  <div class="modal-body">
    <div class="steps-bar">
      <div class="step-dot active" id="dot0"></div>
      <div class="step-dot" id="dot1"></div>
    </div>

    <!-- PANEL 0: SELECAO DE CAMPANHA -->
    <div class="panel active" id="panelCampanhas">
      <div class="info-row" id="infoRow"></div>
      <div id="campStatus"></div>
      <div class="section-title" style="margin-bottom:10px">Campanhas disponiveis</div>
      <div class="campaign-list" id="campaignList"></div>
    </div>

    <!-- PANEL 1: RESULTADO -->
    <div class="panel" id="panelResult">
      <div class="alert alert-success">
        <span class="alert-icon">&#10003;</span>
        <div class="alert-text"><strong>Bonus aplicado com sucesso!</strong><br>A venda foi finalizada no CRM.</div>
      </div>
      <div class="result-box" id="resultBox"></div>
    </div>
  </div>

  <div class="modal-footer">
    <button class="btn btn-ghost" id="btnCancel" onclick="closeModal()">Cancelar</button>
    <div style="flex:1"></div>
    <button class="btn btn-primary" id="btnNext" onclick="handleNext()" disabled>Finalizar Venda</button>
  </div>

</div>
<script>
/* ================================================================
   CONFIG
================================================================ */
var HUB_ENDPOINT = 'https://hub.zaya.it/webhook/crm-bonus';
var HUB_TOKEN    = '9c7f3a1e5b8d4f2c6a0e9d7b3c1f8a2d4e6b9c0f1a3d5e7c8b2a4f6d9e1c3b7';

var ACTION_IDS = {
  'bonus-campaigns' : '116',
  'bonus-submit'    : '114'   /* recebe ACAO='F' */
};

var CLIENT_EVENTS = [
  'beforeSave','afterSave','beforeInsert','afterInsert',
  'beforeUpdate','afterUpdate','beforeDelete','afterDelete',
  'onLoad','onChange','onBlur','onFocus'
];

/* ================================================================
   STATE
================================================================ */
var STATE = {
  step: 0,
  nunota: '', codparc: '', requestHash: '', userId: '',
  campaigns: [], selectedCampaignId: '', idVenda: ''
};

/* ================================================================
   UTILITARIOS
================================================================ */
function esc(s) {
  return String(s||'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
}
/* Decodifica strings com encoding incorreto (latin1 interpretado como UTF-8)
   Resolve caracteres como &Atilde;§ -> &ccedil;, &Atilde;£ -> &atilde;, &Atilde;­ -> &iacute;, etc. */
function decodeStr(s) {
  if (!s) return '';
  try {
    /* Tenta corrigir double-encoding latin1/UTF-8 */
    return decodeURIComponent(escape(s));
  } catch(e) {
    return s; /* retorna original se nao for possivel decodificar */
  }
}
function fmtNum(n) {
  var v = parseFloat(String(n||0).replace(',','.'));
  return isNaN(v) ? '0,00' : v.toFixed(2).replace('.',',');
}
function getCookie(name) {
  var m = document.cookie.match(new RegExp('(?:^|;)\\s*'+name+'=([^;]*)'));
  return m ? decodeURIComponent(m[1]) : '';
}
function chip(label, value) {
  return '<div class="info-chip"><span class="lbl">'+esc(label)+'</span><span class="val">'+esc(value)+'</span></div>';
}
function resultRow(k, v, cls) {
  return '<div class="result-row"><span class="rk">'+esc(k)+'</span><span class="rv'+(cls?' '+cls:'')+'">'+esc(v)+'</span></div>';
}
function showStatus(id, type, msg) {
  var el = document.getElementById(id);
  if (!el) return;
  if (!type && !msg) { el.innerHTML=''; return; }
  var cls = type==='error'?'alert-error':type==='warn'?'alert-warn':type==='success'?'alert-success':'alert-info';
  var icon = type==='error'?'(!)':type==='warn'?'(i)':type==='success'?'&#10003;':'[i]';
  el.innerHTML='<div class="alert '+cls+'" style="margin-top:10px"><span class="alert-icon">'+icon+'</span><div class="alert-text">'+msg+'</div></div>';
}
function clearStatus(id) { var el=document.getElementById(id); if(el) el.innerHTML=''; }
function setBtn(enabled) {
  var b = document.getElementById('btnNext');
  if (b) b.disabled = !enabled;
}
function closeModal() {
  /* O modal e um iframe dentro de um sk-popup Angular do Sankhya W.
     Para fechar: clica no botao X nativo do popup no documento pai. */
  var SEL_POPUP  = 'div.modal-type-warning.modal.sk-popup.ng-isolate-scope.in';
  var SEL_CLOSE  = '[ng-click*="close"], [ng-click*="cancel"], .close, button.sk-close, [data-dismiss="modal"]';

  /* Tenta fechar via documento pai (o iframe esta dentro do sk-popup) */
  var parents = [];
  try { parents.push(window.parent.document); } catch(e) {}
  try { parents.push(window.top.document); } catch(e) {}

  for (var i = 0; i < parents.length; i++) {
    try {
      var doc    = parents[i];
      var popup  = doc.querySelector(SEL_POPUP);
      if (!popup) continue;

      /* 1. Tenta clicar no botao de fechar do popup */
      var btnClose = popup.querySelector(SEL_CLOSE);
      if (btnClose) {
        console.log("[CRM] closeModal: clicando no botao X do sk-popup");
        btnClose.click();
        return;
      }

      /* 2. Remove a classe 'in' para esconder (Angular detecta e destroi) */
      console.log("[CRM] closeModal: removendo classe 'in' do sk-popup");
      popup.classList.remove('in');

      /* 3. Tenta via Angular scope do popup */
      try {
        var scope = angular.element(popup).scope();
        if (scope && typeof scope.close  === 'function') { scope.close();  return; }
        if (scope && typeof scope.cancel === 'function') { scope.cancel(); return; }
        if (scope && scope.$parent) {
          var ps = scope.$parent;
          if (typeof ps.close  === 'function') { ps.close();  return; }
          if (typeof ps.cancel === 'function') { ps.cancel(); return; }
        }
      } catch(e) {}
      return;
    } catch(e) { console.log('[CRM] closeModal erro contexto '+i+':', e.message); }
  }

  /* Fallback: workspace.closeApp */
  var nuGdg = (new URLSearchParams(window.location.search)).get('nuGdg') || '';
  try { window.parent.workspace && window.parent.workspace.closeApp(Number(nuGdg)); } catch(e) {}
  try { window.top.workspace    && window.top.workspace.closeApp(Number(nuGdg));    } catch(e) {}
}



/* ================================================================
   STEPS
================================================================ */
function goStep(n) {
  var panels  = ['panelCampanhas','panelResult'];
  var subs    = ['Selecione uma campanha','Venda finalizada'];
  var badges  = ['badge-step','badge-ok'];
  var total   = panels.length;

  panels.forEach(function(id,i) {
    var el = document.getElementById(id);
    if (el) el.classList.toggle('active', i===n);
    var dot = document.getElementById('dot'+i);
    if (!dot) return;
    dot.classList.remove('active','done');
    if (i<n) dot.classList.add('done');
    if (i===n) dot.classList.add('active');
  });

  STATE.step = n;
  var sub   = document.getElementById('headerSub');
  var badge = document.getElementById('headerBadge');
  if (sub)   sub.textContent   = subs[n]   || '';
  if (badge) { badge.textContent='Passo '+(n+1)+'/'+total; badge.className='badge '+(badges[n]||'badge-step'); }

  var btnNext   = document.getElementById('btnNext');
  var btnCancel = document.getElementById('btnCancel');
  if (n === 1) {
    /* Resultado: so Fechar */
    if (btnNext)   { btnNext.textContent='Fechar'; btnNext.onclick=closeModal; btnNext.disabled=false; }
    if (btnCancel) btnCancel.style.display='none';
  }
}

function handleNext() {
  if (STATE.step === 0) doFinalizar();
}

/* ================================================================
   INIT — le parametros da URL e AD_REQHASH da TGFCAB
================================================================ */
function init() {
  var search = window.location.search || window.location.href.replace(/^[^?]*/,'');
  var p = new URLSearchParams(search);

  console.log('[CRM-CAMP] init: href=', window.location.href);

  STATE.codparc     = (p.get('codparc')     || p.get('CODPARC')     || '').trim();
  STATE.nunota      = (p.get('nunota')       || p.get('NUNOTA')      || '').trim();
  STATE.requestHash = (p.get('requesthash') || p.get('REQUESTHASH') || '').trim();

  console.log('[CRM-CAMP] init: nunota='+STATE.nunota+' codparc='+STATE.codparc+' hash='+STATE.requestHash.substring(0,16));

  /* Atualiza chips */
  var infoRow = document.getElementById('infoRow');
  if (infoRow) {
    infoRow.innerHTML = chip('CODPARC', STATE.codparc||'-') + chip('NUNOTA', STATE.nunota||'-');
  }

  if (STATE.requestHash) {
    prosseguirAposHash();
  } else if (STATE.nunota) {
    lerHashDaTGFCAB(STATE.nunota, function(hash) {
      if (hash) {
        STATE.requestHash = hash;
        console.log('[CRM-CAMP] hash lido da TGFCAB:', hash.substring(0,16)+'...');
      } else {
        showStatus('campStatus','warn','AD_REQHASH nao encontrado para esta venda.');
      }
      prosseguirAposHash();
    });
  } else {
    showStatus('campStatus','error','NUNOTA nao informado. Reabra o modal a partir da venda.');
  }
}

function lerHashDaTGFCAB(nunota, cb) {
  var sid = getCookie('JSESSIONID').replace('.master','');
  var url = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession='+sid;
  var body = {
    serviceName:'CRUDServiceProvider.loadRecords',
    requestBody:{dataSet:{
      rootEntity:'CabecalhoNota',
      includePresentationFields:'N',
      offsetPage:'0',
      criteria:{expression:{$:'this.NUNOTA = '+Number(nunota)}},
      entity:{fieldset:{list:'AD_REQHASH,NUNOTA,AD_USER_ID'}}
    }}
  };
  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
    .then(function(r){return r.json();})
    .then(function(resp){
      console.log('[CRM-CAMP] lerHashDaTGFCAB status:', resp.status);
      var reg = extrairRegistroMaisRecente(resp);
      console.log('[CRM-CAMP] lerHashDaTGFCAB reg:', JSON.stringify(reg));
      /* Se extrairRegistroMaisRecente falhou, tenta parser direto */
      if (!reg) {
        try {
          var ents = resp.responseBody && resp.responseBody.entities;
          var ent  = ents && ents.entity;
          var row  = Array.isArray(ent) ? ent[0] : ent;
          if (row) {
            /* Tenta pegar f0 direto (AD_REQHASH e o primeiro campo) */
            var hash0 = row.f0 && row.f0.$;
            var hash1 = row.f1 && row.f1.$;
            /* AD_REQHASH tem 64 chars hex */
            if (hash0 && hash0.length === 64) { console.log('[CRM-CAMP] hash via f0:', hash0.substring(0,16)); cb(hash0); return; }
            if (hash1 && hash1.length === 64) { console.log('[CRM-CAMP] hash via f1:', hash1.substring(0,16)); cb(hash1); return; }
            /* Procura em todos os campos */
            ['f0','f1','f2','f3','f4'].forEach(function(k){
              var v = row[k] && row[k].$;
              if (v && v.length === 64 && /^[0-9a-f]+$/i.test(v)) {
                console.log('[CRM-CAMP] hash via '+k+':', v.substring(0,16));
                cb(v); return;
              }
            });
            console.warn('[CRM-CAMP] hash nao encontrado nos campos:', JSON.stringify(row));
          }
        } catch(ex){ console.error('[CRM-CAMP] parse direto erro:', ex); }
        cb(null); return;
      }
      var hash = reg.AD_REQHASH || null;
      console.log('[CRM-CAMP] lerHashDaTGFCAB hash:', hash ? hash.substring(0,16)+'...' : 'null');
      cb(hash);
    })
    .catch(function(e){ console.error('[CRM-CAMP] lerHashDaTGFCAB erro:', e); cb(null); });
}

function prosseguirAposHash() {
  /* Le userId do parceiro para passar a Action */
  lerUserIdParceiro(STATE.codparc, function(uid) {
    STATE.userId = uid || '';
    console.log('[CRM-CAMP] userId:', STATE.userId.substring(0,16));
    /* Chama Action 116 para carregar/atualizar campanhas */
    carregarCampanhas();
  });
}

/* ================================================================
   STEP 0 — CAMPANHAS (Action 116)
================================================================ */
function carregarCampanhas() {
  showStatus('campStatus','','<div class="spinner-wrap"><div class="spinner"></div>Carregando campanhas...</div>');
  setBtn(false);

  console.log('[CRM-CAMP] carregarCampanhas: NUNOTA='+STATE.nunota+' hash='+STATE.requestHash.substring(0,16)+'...');

  chamarActionJava(getCookie('JSESSIONID').replace('.master',''), 'bonus', 'bonus-campaigns', {}, function(err, data) {
    console.log('[CRM-CAMP] Action 116 retorno: err='+err+' data='+JSON.stringify(data));

    if (err) {
      showStatus('campStatus','error', esc(err));
      return;
    }

    /* Action 116 grava em AD_CRMBONUSCAMPAIGNS.
       Aguarda 800ms para garantir que a gravacao foi commitada antes de ler. */
    setTimeout(function() {
      lerCampanhasDaTabela(function(camps) {
        clearStatus('campStatus');
        STATE.campaigns = camps;
        renderCampaigns(camps);
      });
    }, 800);
  });
}

function lerCampanhasDaTabela(cb) {
  var sid = getCookie('JSESSIONID').replace('.master','');
  var url = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession='+sid;
  var body = {
    serviceName:'CRUDServiceProvider.loadRecords',
    requestBody:{dataSet:{
      rootEntity:'AD_CRMBONUSCAMPAIGNS',
      includePresentationFields:'N',
      offsetPage:'0',
      entity:{fieldset:{list:'ID_VENDA,REQUEST_HASH,JSON_RETORNO,USER_ID,NUNOTA,CAMP_SELEC,CAMPID'}}
    }}
  };
  console.log('[CRM-CAMP] AD_CRMBONUSCAMPAIGNS: buscando por REQUEST_HASH='+STATE.requestHash.substring(0,16)+'...');
  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
    .then(function(r){return r.json();})
    .then(function(resp){
      console.log('[CRM-CAMP] AD_CRMBONUSCAMPAIGNS status:', resp.status, '| total:', resp.responseBody&&resp.responseBody.entities&&resp.responseBody.entities.total);
      var st = Number(resp.status);
      if (st !== 0 && st !== 1) {
        showStatus('campStatus','error','Erro ao ler campanhas ('+st+'): '+(resp.statusMessage||''));
        cb([]); return;
      }
      var reg = filtrarCampanhaPorHash(resp, STATE.requestHash);
      /* Fallback: se hash vazio ou nao encontrado, filtra por NUNOTA */
      if (!reg && STATE.nunota) {
        console.warn('[CRM-CAMP] hash nao encontrado, tentando filtrar por NUNOTA='+STATE.nunota);
        reg = filtrarCampanhaPorNunota(resp, STATE.nunota);
        if (reg) {
          STATE.requestHash = reg.REQUEST_HASH || STATE.requestHash;
          console.log('[CRM-CAMP] encontrado por NUNOTA, hash='+STATE.requestHash.substring(0,16)+'...');
        }
      }
      /* Guarda ID_VENDA (PK) para usar no update do CAMP_SELEC */
      if (reg && reg.ID_VENDA) {
        STATE.idVenda = reg.ID_VENDA;
        console.log('[CRM-CAMP] ID_VENDA (PK):', STATE.idVenda);
      }
      console.log('[CRM-CAMP] registro campanha:', JSON.stringify(reg));
      if (!reg) {
        showStatus('campStatus','warn','Nenhuma campanha encontrada para esta venda.');
        cb([]); return;
      }
      parsearERetornarCampanhas(reg, cb);
    })
    .catch(function(e){ console.error('[CRM-CAMP] AD_CRMBONUSCAMPAIGNS erro:', e); cb([]); });
}

function filtrarCampanhaPorHash(resp, hash) {
  try {
    var entities = resp.responseBody && resp.responseBody.entities;
    if (!entities) return null;
    var metaFields = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
    var fieldNames = Array.isArray(metaFields) ? metaFields.map(function(f){return f.name||'';}) : [];
    var ent  = entities.entity;
    if (!ent) return null;
    var rows = Array.isArray(ent) ? ent : [ent];
    var hashIdx = fieldNames.indexOf('REQUEST_HASH');
    console.log('[CRM-CAMP] filtrarCampanhaPorHash: '+rows.length+' rows, hashIdx='+hashIdx);
    for (var r=0; r<rows.length; r++) {
      var row = rows[r];
      var rowHash = (hashIdx>=0 && row['f'+hashIdx]) ? row['f'+hashIdx].$ : '';
      console.log('[CRM-CAMP] row '+r+' hash='+String(rowHash).substring(0,16)+'...');
      if (rowHash === hash) {
        var obj = {};
        fieldNames.forEach(function(name,i){ var cell=row['f'+i]; obj[name]=cell?(cell.$||''):''; });
        return obj;
      }
    }
    return null;
  } catch(e){ console.error('[CRM-CAMP] filtrarCampanhaPorHash:', e); return null; }
}

function filtrarCampanhaPorNunota(resp, nunota) {
  try {
    var entities = resp.responseBody && resp.responseBody.entities;
    if (!entities) return null;
    var metaFields = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
    var fieldNames = Array.isArray(metaFields) ? metaFields.map(function(f){return f.name||'';}) : [];
    var ent  = entities.entity;
    if (!ent) return null;
    var rows = Array.isArray(ent) ? ent : [ent];
    var nunotaIdx = fieldNames.indexOf('NUNOTA');
    console.log('[CRM-CAMP] filtrarCampanhaPorNunota: '+rows.length+' rows, nunotaIdx='+nunotaIdx+' buscando NUNOTA='+nunota);
    for (var r=0; r<rows.length; r++) {
      var row = rows[r];
      var rowNunota = (nunotaIdx>=0 && row['f'+nunotaIdx]) ? String(row['f'+nunotaIdx].$||'').trim() : '';
      console.log('[CRM-CAMP] row '+r+' NUNOTA='+rowNunota);
      if (rowNunota === String(nunota).trim()) {
        var obj = {};
        fieldNames.forEach(function(name,i){ var cell=row['f'+i]; obj[name]=cell?(cell.$||''):''; });
        return obj;
      }
    }
    return null;
  } catch(e){ console.error('[CRM-CAMP] filtrarCampanhaPorNunota:', e); return null; }
}

function parsearERetornarCampanhas(reg, cb) {
  var jsonStr = reg.JSON_RETORNO || '';
  console.log('[CRM-CAMP] JSON_RETORNO:', jsonStr.substring(0,300));
  if (!jsonStr) { cb([]); return; }
  var parsed;
  try { parsed = JSON.parse(jsonStr); } catch(e) { console.error('[CRM-CAMP] JSON parse:', e); cb([]); return; }
  var camps;
  if (Array.isArray(parsed))          camps = parsed;
  else if (parsed && parsed.campaigns) camps = parsed.campaigns;
  else if (parsed && parsed.data)      camps = parsed.data;
  else if (parsed && parsed.id)        camps = [parsed];  /* objeto unico */
  else                                  camps = [];
  console.log('[CRM-CAMP] campanhas:', camps.length);
  cb(camps);
}

function renderCampaigns(camps) {
  var list = document.getElementById('campaignList');
  if (!list) return;
  if (!camps || camps.length === 0) {
    list.innerHTML = '<div class="alert alert-warn"><span class="alert-icon">(i)</span><div class="alert-text">Nenhuma campanha disponivel para esta venda.</div></div>';
    return;
  }
  list.innerHTML = camps.map(function(c) {
    var id   = String(c.id || c.campaign_id || c.campaignId || '');
    var name = esc(decodeStr(c.name || c.campaign_name || id));
    var desc = esc(decodeStr(c.text || c.subtitle || c.description || ''));
    var type = esc(decodeStr(c.type || ''));
    var val  = c.value ? 'R$ '+fmtNum(c.value) : '';
    return '<div class="campaign-item" data-id="'+esc(id)+'" onclick="selectCampaign(this,\''+esc(id)+'\')">'+
      '<div class="campaign-radio"></div>'+
      '<div class="campaign-info">'+
        '<div class="campaign-name">'+name+'</div>'+
        (desc ? '<div class="campaign-desc">'+desc+'</div>' : '')+
      '</div>'+
      (val ? '<div class="campaign-tag">'+val+'</div>' : '')+
      (type ? '<div class="campaign-tag" style="margin-left:4px">'+type+'</div>' : '')+
    '</div>';
  }).join('');
}

function selectCampaign(el, id) {
  document.querySelectorAll('.campaign-item').forEach(function(item) {
    item.classList.remove('selected');
  });
  el.classList.add('selected');
  STATE.selectedCampaignId = id;
  console.log('[CRM-CAMP] campanha selecionada:', id);
  setBtn(true);

  /* Update imediato: CAMP_SELEC = 'S' ao selecionar */
  atualizarCampSelec(id);
}

function atualizarCampSelec(campaignId) {
  /* Usa CRUDServiceProvider.loadRecords para buscar todos os registros da nota,
     depois salva cada um via dataSetSP.save passando NUNOTA+CAMPID como chave
     (campos marcados com k__=true forcam UPDATE em vez de INSERT).
     - Campanha selecionada  → CAMP_SELEC = 'S'
     - Demais campanhas      → CAMP_SELEC = 'N'                          */
  if (!STATE.nunota || !campaignId) return;
  var sid    = getCookie('JSESSIONID').replace('.master','');
  var nunota = Number(STATE.nunota);
  var urlLoad = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession=' + sid;
  var urlSave = '/mge/service.sbr?serviceName=DatasetSP.save&outputType=json&mgeSession=' + sid;

  /* 1. Busca todos os registros da nota */
  var bodyLoad = {
    serviceName: 'CRUDServiceProvider.loadRecords',
    requestBody: { dataSet: {
      rootEntity: 'AD_CRMBONUSCAMPAIGNS',
      includePresentationFields: 'N',
      offsetPage: '0',
      criteria: { expression: { $: 'this.NUNOTA = ' + nunota } },
      entity: { fieldset: { list: 'ID_VENDA,NUNOTA,CAMPID,CAMP_SELEC,REQUEST_HASH' } }
    }}
  };

  fetch(urlLoad, {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(bodyLoad)})
    .then(function(r){ return r.json(); })
    .then(function(resp){
      var registros = extrairTodosRegistros(resp);
      console.log('[CRM-CAMP] atualizarCampSelec: ' + registros.length + ' registros encontrados para NUNOTA=' + nunota);
      if (!registros.length) return;

      /* 2. Salva cada registro via DatasetSP.save usando ID_VENDA como PK.
            Formato: pk={ID_VENDA:valor}, fields=[CAMP_SELEC], values={0:valor}
            O ID_VENDA e buscado fresco no loadRecords acima — sempre atual
            mesmo apos DELETE+INSERT da Action 116.                          */
      registros.forEach(function(reg) {
        var selecionado = String(reg.CAMPID) === String(campaignId) ? 'S' : 'N';
        var bodySave = {
          serviceName: 'DatasetSP.save',
          requestBody: {
            entityName: 'AD_CRMBONUSCAMPAIGNS',
            standAlone: false,
            fields: ['CAMP_SELEC'],
            records: [{
              pk:     { ID_VENDA: String(reg.ID_VENDA) },
              values: { '0': selecionado }
            }]
          }
        };
        console.log('[CRM-CAMP] DatasetSP.save ID_VENDA=' + reg.ID_VENDA + ' CAMPID=' + reg.CAMPID + ' CAMP_SELEC=' + selecionado);
        fetch(urlSave, {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(bodySave)})
          .then(function(r){ return r.json(); })
          .then(function(r){
            console.log('[CRM-CAMP] save status:', r.status, r.status==1?'(OK)':r.status==0?'(ERRO)':'', r.statusMessage||'');
          })
          .catch(function(e){ console.error('[CRM-CAMP] save erro:', e); });
      });
    })
    .catch(function(e){ console.error('[CRM-CAMP] atualizarCampSelec load erro:', e); });
}

/* Retorna todos os registros de uma resposta CRUDServiceProvider */
function extrairTodosRegistros(resp) {
  try {
    var entities = resp.responseBody && resp.responseBody.entities;
    if (!entities) return [];
    var meta  = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
    var names = Array.isArray(meta) ? meta.map(function(f){ return f.name||''; }) : (meta ? [meta.name] : []);
    var ent   = entities.entity;
    if (!ent) return [];
    var rows  = Array.isArray(ent) ? ent : [ent];
    return rows.map(function(row) {
      var obj = {};
      names.forEach(function(n, i){ var c = row['f'+i]; obj[n] = c ? (c.$||'') : ''; });
      return obj;
    });
  } catch(e){ console.error('[CRM-CAMP] extrairTodosRegistros:', e); return []; }
}

/* ================================================================
   STEP 1 — FINALIZAR (Action 114 com ACAO='F')
================================================================ */
function doFinalizar() {
  if (!STATE.selectedCampaignId) {
    showStatus('campStatus','warn','Selecione uma campanha para continuar.');
    return;
  }
  setBtn(false);
  showStatus('campStatus','','<div class="spinner-wrap"><div class="spinner"></div>Aplicando bonus e finalizando venda...</div>');

  /* Grava campanha selecionada na AD_CRMBONUSCAMPAIGNS para a Action 114 ler */
  salvarCampanhaSelecionada(STATE.nunota, STATE.selectedCampaignId, function() {
    /* Action 114 com ACAO='F' */
    var sid = getCookie('JSESSIONID').replace('.master','');
    console.log('[CRM-CAMP] chamando Action 114 ACAO=F para NUNOTA='+STATE.nunota+' campanha='+STATE.selectedCampaignId);

    var actionID = ACTION_IDS['bonus-submit'];
    var nunota   = String(STATE.nunota).trim();
    var url = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession='+sid;
    var fields = [
      {fieldName:'NUNOTA',       $: nunota},
      {fieldName:'ENTIDADE',     $: 'bonus'},
      {fieldName:'SUBENTIDADE',  $: 'bonus-submit'},
      {fieldName:'REQUEST_HASH', $: STATE.requestHash},
      {fieldName:'USER_ID',      $: STATE.userId},
      {fieldName:'CAMPAIGN_ID',  $: STATE.selectedCampaignId},
      {fieldName:'ACAO',         $: 'F'},            /* obrigatorio para Action 114 */
      {fieldName:'PAYLOAD',      $: JSON.stringify({campaign_id: STATE.selectedCampaignId})}
    ];
    var body = {
      serviceName:'ActionButtonsSP.executeJava',
      requestBody:{
        javaCall:{
          actionID:actionID, refreshType:'SEL',
          rows:{ row:[{ field:fields }] }
        },
        clientEventList:{ clientEvent: CLIENT_EVENTS.map(function(e){return{$:e};}) }
      }
    };
    fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
      .then(function(r){return r.json();})
      .then(function(resp){
        console.log('[CRM-CAMP] Action 114 resposta:', JSON.stringify(resp));
        var st  = Number(resp.status);
        var msg = resp.statusMessage || '';

        var resultado = interpretarRespostaAction114(st, msg);
        console.log('[CRM-CAMP] Action 114 interpretado:', JSON.stringify(resultado));

        if (resultado.sucesso) {
          clearStatus('campStatus');
          showResultado(resultado);
        } else {
          showStatus('campStatus','error',
            '<strong>Erro ao finalizar venda:</strong> ' + esc(resultado.mensagem));
          setBtn(true);
        }
      })
      .catch(function(e){
        showStatus('campStatus','error', esc(String(e)));
        setBtn(true);
      });
  });
}

function salvarCampanhaSelecionada(nunota, campaignId, cb) {
  if (!campaignId) { cb(); return; }
  var sid = getCookie('JSESSIONID').replace('.master','');
  var url = '/mge/service.sbr?serviceName=CRUDServiceProvider.save&outputType=json&mgeSession='+sid;
  var body = {
    serviceName:'CRUDServiceProvider.save',
    requestBody:{dataSet:{
      rootEntity:'AD_CRMBONUSCAMPAIGNS',
      entity:{
        fieldset:{list:'REQUEST_HASH,CAMP_SELEC'},
        record:[{ REQUEST_HASH:{$:STATE.requestHash}, CAMP_SELEC:{$:String(campaignId)} }]
      }
    }}
  };
  console.log('[CRM-CAMP] salvarCampanhaSelecionada: CAMP_SELEC='+campaignId);
  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
    .then(function(r){return r.json();})
    .then(function(resp){ console.log('[CRM-CAMP] salvarCampanha status:', resp.status); cb(); })
    .catch(function(e){ console.error('[CRM-CAMP] salvarCampanha erro:', e); cb(); });
}

/* ----------------------------------------------------------------
   Interpreta o statusMessage da Action 114.
   Formatos conhecidos:
     SUCESSO: "Finalizacao concluida para ..."
     ERRO:    "Erro ao testar finalizeSale nota X: Erro na chamada ao HUB (400):
               400 - "{"status":false,"message":"This sale already exist","data":""}""
---------------------------------------------------------------- */
function interpretarRespostaAction114(st, msg) {
  var msgLimpa = msg.replace(/<[^>]+>/g,' ').replace(/\s+/g,' ').trim();

  /* 1. Sucesso explicito no texto */
  if (/finaliza[&ccedil;c][a&atilde;]o conclu[i&iacute;]da/i.test(msgLimpa)) {
    return { sucesso: true, mensagem: msgLimpa };
  }

  /* 2. Tenta extrair JSON da API embutido no texto
        ex: 400 - "{"status":false,"message":"This sale already exist",...}" */
  var jsonMatch = msgLimpa.match(/\{[^{}]*"status"\s*:\s*(true|false)[^{}]*\}/i);
  if (jsonMatch) {
    try {
      var apiResp = JSON.parse(jsonMatch[0]);
      if (apiResp.status === true  || apiResp.status === 'true')  return { sucesso: true,  mensagem: apiResp.message || msgLimpa };
      if (apiResp.status === false || apiResp.status === 'false') return { sucesso: false, mensagem: apiResp.message || msgLimpa };
    } catch(e) { /* continua */ }
  }

  /* 3. Erro generico no texto */
  if (/^erro/i.test(msgLimpa) || /regra personalizada/i.test(msgLimpa)) {
    /* Pega a parte mais legivel: apos o ultimo ":" se houver JSON feio */
    var erroMatch = msgLimpa.match(/message["']?\s*:\s*["']([^"']+)/i);
    var erroTexto = erroMatch ? erroMatch[1] : msgLimpa.replace(/Erro[^:]*:\s*/gi,'').substring(0,200);
    return { sucesso: false, mensagem: erroTexto };
  }

  /* 4. Status Sankhya 0/1/2 sem indicacao clara = assume sucesso */
  if (st === 0 || st === 1 || st === 2) {
    return { sucesso: true, mensagem: msgLimpa || 'Venda finalizada.' };
  }

  return { sucesso: false, mensagem: msgLimpa || ('Status inesperado: ' + st) };
}

function showResultado(resultado) {
  var camp = STATE.campaigns.find(function(c){
    return String(c.id||c.campaign_id||c.campaignId) === STATE.selectedCampaignId;
  });
  var campName = camp ? (camp.name||camp.campaign_name||STATE.selectedCampaignId) : STATE.selectedCampaignId;

  /* Atualiza header do panel de resultado */
  var alertEl = document.querySelector('#panelResult .alert-success');
  if (alertEl && resultado.mensagem) {
    alertEl.querySelector('.alert-text').innerHTML =
      '<strong>Bonus aplicado com sucesso!</strong><br>' + esc(resultado.mensagem);
  }

  var box = document.getElementById('resultBox');
  if (box) {
    box.innerHTML =
      resultRow('Campanha', campName) +
      resultRow('NUNOTA',   STATE.nunota) +
      resultRow('Retorno',  resultado.mensagem.substring(0,120), 'green');
  }
  goStep(1);
}

/* ================================================================
   CRUD HELPERS
================================================================ */
function extrairRegistroMaisRecente(resp) {
  try {
    var entities = resp && resp.responseBody && resp.responseBody.entities;
    if (!entities) return null;
    var metaFields = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
    var fieldNames = Array.isArray(metaFields) ? metaFields.map(function(f){return f.name||'';})
                   : (metaFields && metaFields.name ? [metaFields.name] : []);
    var ent  = entities.entity;
    if (!ent) return null;
    var rows = Array.isArray(ent) ? ent : [ent];
    var objetos = rows.map(function(row) {
      var obj={};
      fieldNames.forEach(function(name,i){ var c=row['f'+i]; obj[name]=c?(c.$||''):''; });
      return obj;
    });
    objetos.sort(function(a,b){ return Number(b.ID||0)-Number(a.ID||0); });
    return objetos[0] || null;
  } catch(e){ console.error('[CRM-CAMP] extrairRegistroMaisRecente:', e); return null; }
}

function lerUserIdParceiro(codparc, cb) {
  if (!codparc) { cb&&cb(null); return; }
  var sid = getCookie('JSESSIONID').replace('.master','');
  var url = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession='+sid;
  var body = {
    serviceName:'CRUDServiceProvider.loadRecords',
    requestBody:{dataSet:{
      rootEntity:'Parceiro', includePresentationFields:'N', offsetPage:'0',
      criteria:{expression:{$:'this.CODPARC = '+Number(codparc)}},
      entity:{fieldset:{list:'AD_USER_ID,CODPARC'}}
    }}
  };
  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
    .then(function(r){return r.json();})
    .then(function(resp){
      var reg = extrairRegistroMaisRecente(resp);
      cb&&cb(reg ? (reg.AD_USER_ID||null) : null);
    })
    .catch(function(e){ cb&&cb(null); });
}

/* ================================================================
   ACTION JAVA - chamarActionJava (apenas bonus-campaigns e bonus-submit/114)
================================================================ */
function chamarActionJava(sid, entidade, subEntidade, payload, cb) {
  var actionID = ACTION_IDS[subEntidade];
  if (!actionID) { cb('actionID nao configurado para: '+subEntidade); return; }

  var nunota = String(STATE.nunota).trim();
  if (!nunota) { cb('NUNOTA invalido.'); return; }

  var url = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession='+sid;
  var fields = [
    {fieldName:'NUNOTA',       $: nunota},
    {fieldName:'ENTIDADE',     $: entidade},
    {fieldName:'SUBENTIDADE',  $: subEntidade},
    {fieldName:'REQUEST_HASH', $: STATE.requestHash},
    {fieldName:'USER_ID',      $: STATE.userId||''},
    {fieldName:'PAYLOAD',      $: JSON.stringify(payload)}
  ];
  /* ACAO='F' apenas para bonus-submit (Action 114) */
  if (subEntidade === 'bonus-submit') {
    fields.push({fieldName:'ACAO', $:'F'});
  }

  var body = {
    serviceName:'ActionButtonsSP.executeJava',
    requestBody:{
      javaCall:{ actionID:actionID, refreshType:'SEL', rows:{row:[{field:fields}]} },
      clientEventList:{ clientEvent: CLIENT_EVENTS.map(function(e){return{$:e};}) }
    }
  };

  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
    .then(function(r){return r.json();})
    .then(function(resp){
      console.log('[CRM-CAMP] ActionButtonsSP status='+resp.status+' msg='+(resp.statusMessage||'').substring(0,120));
      var st  = Number(resp.status);
      var msg = resp.statusMessage || '';

      var isErro = (st===0) && (/regra personalizada/i.test(msg)||/^erro/i.test(msg.replace(/<[^>]+>/g,'').trim()));
      if (isErro) {
        var msgLimpa = msg.replace(/<[^>]+>/g,' ').replace(/\s+/g,' ').trim();
        var m = msgLimpa.match(/Erro[^:]*:\s*(.+)/i);
        cb(m ? m[1].substring(0,200) : msgLimpa.substring(0,200));
        return;
      }
      if (st===0||st===1||st===2) {
        var result = { _sankhyaStatus:st, statusMessage:msg, _gravouNaTabela:(st===2) };
        cb(null, result);
      } else {
        cb(msg||'status '+st);
      }
    })
    .catch(function(e){ cb(String(e)); });
}

/* ================================================================
   BOOT
================================================================ */
if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', init);
} else {
  init();
}
</script>
</body>
</html>
