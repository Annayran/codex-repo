<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8"/>
<title>CRM Bonus</title>
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

/* HEADER */
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

/* BODY */
.modal-body {
  flex: 1; overflow-y: auto; padding: 20px;
  scrollbar-width: thin; scrollbar-color: var(--surface2) transparent;
}
.modal-body::-webkit-scrollbar { width: 4px; }
.modal-body::-webkit-scrollbar-thumb { background: var(--surface2); border-radius: 2px; }

/* FOOTER */
.modal-footer {
  display: flex; gap: 8px; padding: 14px 20px;
  border-top: 1px solid var(--border); flex-shrink: 0; background: var(--surface);
}

/* STEPS */
.steps-bar { display: flex; gap: 4px; margin-bottom: 16px; }
.step-dot { height: 3px; flex: 1; border-radius: 2px; background: var(--surface2); transition: background var(--transition); }
.step-dot.done   { background: var(--accent-dim); }
.step-dot.active { background: var(--accent); }

/* PANELS */
.panel { display: none; flex-direction: column; gap: 16px; animation: fadeIn 0.25s ease; }
.panel.active { display: flex; }
@keyframes fadeIn { from { opacity: 0; transform: translateY(6px); } to { opacity: 1; transform: none; } }

/* CHIPS */
.info-row { display: flex; gap: 8px; flex-wrap: wrap; }
.info-chip {
  display: flex; align-items: center; gap: 6px;
  background: var(--surface2); border: 1px solid var(--border2);
  border-radius: var(--radius-sm); padding: 6px 10px; font-family: var(--mono); font-size: 12px;
}
.info-chip .lbl { color: var(--muted); font-size: 10px; text-transform: uppercase; letter-spacing: 0.6px; }
.info-chip .val { color: var(--text); font-weight: 500; }

/* ALERTS */
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

/* SPINNER */
.spinner-wrap { display: flex; align-items: center; gap: 10px; color: var(--muted); font-size: 13px; }
.spinner {
  width: 18px; height: 18px; border: 2px solid var(--surface2);
  border-top-color: var(--accent); border-radius: 50%;
  animation: spin 0.7s linear infinite; flex-shrink: 0;
}
@keyframes spin { to { transform: rotate(360deg); } }

/* INPUTS */
.field-group { display: flex; flex-direction: column; gap: 5px; }
.field-label { font-size: 11px; font-weight: 500; color: var(--muted); text-transform: uppercase; letter-spacing: 0.5px; }
.field-hint  { font-size: 11px; color: var(--muted); }
input[type="text"] {
  width: 100%; background: var(--surface2); border: 1px solid var(--border2);
  color: var(--text); border-radius: var(--radius-sm); padding: 9px 12px;
  font-family: var(--font); font-size: 14px; outline: none;
  transition: border-color var(--transition);
}
input[type="text"]:focus { border-color: var(--accent); }
input[type="text"]::placeholder { color: var(--muted); }
.pin-input { text-align: center; font-family: var(--mono); font-size: 18px; font-weight: 500; letter-spacing: 4px; }

/* BUTTONS */
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
.btn-sm { padding: 6px 12px; font-size: 12px; }

/* BONUS CARD */
.bonus-card {
  background: linear-gradient(135deg, #0d2e1c 0%, #0f2030 100%);
  border: 1px solid rgba(34,208,122,0.25); border-radius: var(--radius); padding: 16px;
  position: relative; overflow: hidden;
}
.bonus-card::before {
  content: ''; position: absolute; top: -30px; right: -30px; width: 100px; height: 100px;
  border-radius: 50%; background: radial-gradient(circle, rgba(34,208,122,0.15), transparent 70%);
}
.bc-label { font-size: 11px; color: rgba(34,208,122,0.7); text-transform: uppercase; letter-spacing: 0.7px; margin-bottom: 4px; }
.bc-value { font-size: 26px; font-weight: 600; color: var(--accent); font-family: var(--mono); line-height: 1.1; }
.bc-sub   { font-size: 12px; color: var(--muted); margin-top: 4px; }

/* CAMPAIGNS */
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

/* RESULT */
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
      <h1>CRM Bonus</h1>
      <p id="headerSub">Consulta de bonus</p>
    </div>
    <span id="headerBadge" class="badge badge-step">Passo 1/3</span>
  </div>

  <div class="modal-body">
    <div class="steps-bar">
      <div class="step-dot active" id="dot0"></div>
      <div class="step-dot" id="dot1"></div>
      <div class="step-dot" id="dot2"></div>
    </div>

    <!-- PANEL 0: IDENTIFICACAO -->
    <div class="panel active" id="panelVerify">
      <div class="info-row" id="infoRow"></div>
      <div class="field-group">
        <label class="field-label" id="docLabel">CPF / CNPJ do Cliente</label>
        <input type="text" id="inputCpf" placeholder="Carregando..." maxlength="18" autocomplete="off" readonly
               style="background:var(--surface);color:var(--muted);cursor:default;"/>
        <span class="field-hint" id="docHint">Documento obtido do cadastro do parceiro</span>
      </div>
      <div class="field-group">
        <label class="field-label">Telefone</label>
        <input type="text" id="inputFone" placeholder="Carregando..." maxlength="20" autocomplete="off" readonly
               style="background:var(--surface);color:var(--muted);cursor:default;"/>
      </div>
      <div id="verifyStatus"></div>
    </div>

    <!-- PANEL 1: PIN -->
    <div class="panel" id="panelPin">
      <div class="alert alert-info">
        <span class="alert-icon">[i]</span>
        <div class="alert-text">
          Um PIN foi enviado para o celular cadastrado do cliente.
          Solicite que ele informe o codigo recebido.
        </div>
      </div>
      <div class="field-group">
        <label class="field-label">Codigo PIN</label>
        <input type="text" id="inputPin" class="pin-input" placeholder="------" maxlength="8" autocomplete="off"/>
      </div>
      <div id="pinStatus"></div>
      <div>
        <button class="btn btn-ghost btn-sm" id="btnResendPin" onclick="resendPin()">Reenviar PIN</button>
      </div>
    </div>

    <!-- PANEL 2: BONUS -->
    <div class="panel" id="panelBonus">
      <div id="bonusAvailableArea"></div>
      <div id="bonusStatus"></div>
    </div>
  </div>

  <div class="modal-footer">
    <button class="btn btn-ghost"      id="btnCancel" onclick="closeModal()">Cancelar</button>
    <div style="flex:1"></div>
    <button class="btn btn-secondary"  id="btnBack"   style="display:none" onclick="goBack()">Voltar</button>
    <button class="btn btn-primary"    id="btnNext"   onclick="handleNext()">Verificar Cliente</button>
  </div>

</div>
<script>
/* ================================================================
   CONFIG
================================================================ */
var HUB_ENDPOINT = 'https://hub.zaya.it/webhook/crm-bonus';
var BUILD_TAG    = 'crm-bonus-pinfix-2026-03-02-1129';

/* ================================================================
   HELPERS - definidos antes de qualquer uso
================================================================ */
function esc(s) {
  return String(s||'')
    .replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;')
    .replace(/"/g,'&quot;').replace(/'/g,'&#39;');
}

/* Decodifica strings com encoding incorreto (latin1 interpretado como UTF-8)
   Resolve caracteres como &Atilde;§ -> &ccedil;, &Atilde;£ -> &atilde;, &Atilde;­ -> &iacute;, etc. */
function decodeStr(s) {
  if (!s) return '';
  /* Sankhya envia ISO-8859-1 — corrige os glyphs corrompidos mais comuns */
  var txt = String(s);
  try { txt = decodeURIComponent(escape(txt)); } catch (e) {}
  txt = txt.split('\uFFFD').join('?');

  var fixes = [
    ['B?nus', 'B\u00f4nus'],
    ['b?nus', 'b\u00f4nus'],
    ['B�nus', 'B\u00f4nus'],
    ['b�nus', 'b\u00f4nus'],
    ['BÃ´nus', 'B\u00f4nus'],
    ['bÃ´nus', 'b\u00f4nus'],
    ['?nico', '\u00fanico'],
    ['?ltimo', '\u00faltimo'],
    ['n?mero', 'n\u00famero'],
    ['j?', 'j\u00e1'],
    ['ap?s', 'ap\u00f3s'],
    ['jÃ¡', 'j\u00e1'],
    ['nÃºmero', 'n\u00famero'],
    ['Ãºnico', '\u00fanico'],
    ['Finaliza??o', 'Finaliza\u00e7\u00e3o'],
    ['finaliza??o', 'finaliza\u00e7\u00e3o'],
    ['conclu?da', 'conclu\u00edda'],
    ['conclu?do', 'conclu\u00eddo'],
    ['Consulta de b?nus', 'Consulta de b\u00f4nus'],
    ['atualiz?', 'atualiz\u00e1']
  ];
  for (var i = 0; i < fixes.length; i++) {
    txt = txt.split(fixes[i][0]).join(fixes[i][1]);
  }
  return txt;
}

function fmtNum(n) {
  return Number(n).toLocaleString('pt-BR',{minimumFractionDigits:2,maximumFractionDigits:2});
}
function formatarMoedaEmTexto(txt) {
  return String(txt || '').replace(/R\$\s*([0-9]+(?:[.,][0-9]{1,2})?)/gi, function(_, valorBruto) {
    var raw = String(valorBruto);
    if (raw.indexOf(',') >= 0) raw = raw.replace(/\./g,'').replace(',','.');
    var v = Number(raw);
    if (isNaN(v)) return 'R$ ' + valorBruto;
    return 'R$ ' + fmtNum(v);
  });
}
function generateHash() {
  var a = new Uint8Array(20);
  try { crypto.getRandomValues(a); } catch(e) {
    for (var i=0; i<20; i++) a[i] = Math.floor(Math.random()*256);
  }
  return Array.from(a).map(function(b){ return b.toString(16).padStart(2,'0'); }).join('');
}
function getCookie(name) {
  var m = document.cookie.match(new RegExp('(?:^|; )'+name+'=([^;]*)'));
  return m ? decodeURIComponent(m[1]) : '';
}
function chip(label, value) {
  return '<div class="info-chip"><span class="lbl">'+esc(label)+'</span><span class="val">'+esc(value)+'</span></div>';
}
function publicarContextoAtualNoStorage() {
  try {
    if (!window.sessionStorage && !window.localStorage) return;
    var payload = {
      nunota: String(STATE.nunota || ''),
      codparc: String(STATE.codparc || ''),
      requestHash: String(STATE.requestHash || ''),
      ts: Date.now(),
      origem: 'modalCrmBonus'
    };
    try { sessionStorage.setItem('crm_bonus_ctx', JSON.stringify(payload)); } catch (e1) {}
    try { localStorage.setItem('crm_bonus_ctx', JSON.stringify(payload)); } catch (e2) {}
    console.log('[CRM] contexto publicado no storage:', JSON.stringify(payload));
  } catch (e) {
    console.warn('[CRM] publicarContextoAtualNoStorage:', e && e.message ? e.message : e);
  }
}
function resultRow(k, v, cls) {
  return '<div class="result-row"><span class="rk">'+esc(k)+'</span><span class="rv'+(cls?' '+cls:'')+'">'+esc(String(v))+'</span></div>';
}
function showStatus(id, type, msg) {
  var el = document.getElementById(id);
  if (!el) return;
  if (!type) { el.innerHTML = msg; return; }
  var icons = {error:'(!)',success:'(ok)',warn:'(i)',info:'(i)'};
  var cls   = {error:'alert-error',success:'alert-success',warn:'alert-warn',info:'alert-info'};
  el.innerHTML = '<div class="alert '+(cls[type]||'alert-info')+'">'+
    '<span class="alert-icon">'+(icons[type]||'')+'</span>'+
    '<div class="alert-text">'+esc(msg)+'</div></div>';
}
function clearStatus(id) { var el=document.getElementById(id); if(el) el.innerHTML=''; }
function setLoading(id, msg) {
  var el = document.getElementById(id);
  if (el) el.innerHTML='<div class="spinner-wrap"><div class="spinner"></div>'+esc(msg)+'</div>';
}
function setBtn(enabled) {
  var b = document.getElementById('btnNext');
  if (b) b.disabled = !enabled;
}
function telefonePreenchido() {
  var el = document.getElementById('inputFone');
  var v  = (el && el.value ? el.value : (STATE.fone || '')).trim();
  var upper = v.toUpperCase();
  if (!v) return false;
  if (upper === 'INFORME O TELEFONE') return false;
  return /\d/.test(v);
}
function avisarTelefoneObrigatorio() {
  showStatus(
    'verifyStatus',
    'error',
    'Telefone do parceiro é obrigatório. Atualize o cadastro do parceiro antes de validar o cliente no CRM Bonus.'
  );
  setBtn(false);
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

/* Ajusta tamanho do popup hospedeiro quando o modal e aberto dentro
   de uma janela "Informacao" do Sankhya (cenario comum no PDV). */
function ajustarPopupHost() {
  try {
    var frame = window.frameElement;
    if (!frame || !window.parent || !window.parent.document) return;

    var modal = null;
    try { modal = frame.closest('.modal'); } catch(e) {}
    if (!modal) {
      var pdoc = window.parent.document;
      var iframes = pdoc.querySelectorAll('iframe');
      for (var i = 0; i < iframes.length; i++) {
        if (iframes[i] === frame) {
          try { modal = iframes[i].closest('.modal'); } catch(ex) {}
          break;
        }
      }
    }
    if (!modal) return;

    var dialog  = modal.querySelector('.modal-dialog') || modal.querySelector('.modal-content') && modal.querySelector('.modal-content').parentElement;
    var content = modal.querySelector('.modal-content');
    var body    = modal.querySelector('.modal-body');
    var footer  = modal.querySelector('.modal-footer');
    var header  = modal.querySelector('.modal-header');

    if (dialog) {
      dialog.style.setProperty('width', '940px', 'important');
      dialog.style.setProperty('max-width', '95vw', 'important');
      dialog.style.setProperty('height', '620px', 'important');
      dialog.style.setProperty('max-height', '90vh', 'important');
      dialog.style.setProperty('margin', '3vh auto', 'important');
    }
    if (content) {
      content.style.setProperty('height', '620px', 'important');
      content.style.setProperty('max-height', '90vh', 'important');
      content.style.setProperty('display', 'flex', 'important');
      content.style.setProperty('flex-direction', 'column', 'important');
      content.style.setProperty('overflow', 'hidden', 'important');
    }
    if (body) {
      var headerH = header ? header.offsetHeight : 44;
      body.style.setProperty('padding', '0', 'important');
      body.style.setProperty('overflow', 'hidden', 'important');
      body.style.setProperty('flex', '1 1 auto', 'important');
      body.style.setProperty('height', 'calc(620px - ' + headerH + 'px)', 'important');
      body.style.setProperty('max-height', 'calc(90vh - ' + headerH + 'px)', 'important');
    }
    if (footer && /ok/i.test((footer.textContent || '').trim())) {
      footer.style.setProperty('display', 'none', 'important');
    }

    /* Forca titulo da janela hospedeira (evita herdar "Informacao"). */
    var titulo = 'CRM Bonus';
    var tituloNodes = modal.querySelectorAll(
      '.modal-header h1, .modal-header h2, .modal-header h3, .modal-header h4, .modal-header h5, .modal-header .modal-title'
    );
    for (var t = 0; t < tituloNodes.length; t++) {
      tituloNodes[t].textContent = titulo;
    }

    /* Garante que todos os wrappers entre body e iframe ocupem 100% */
    var p = frame.parentElement;
    var j = 0;
    while (p && body && p !== body && j < 6) {
      p.style.setProperty('height', '100%', 'important');
      p.style.setProperty('min-height', '100%', 'important');
      p.style.setProperty('overflow', 'hidden', 'important');
      p = p.parentElement;
      j++;
    }

    frame.style.setProperty('width', '100%', 'important');
    frame.style.setProperty('height', '100%', 'important');
    frame.style.setProperty('min-height', '100%', 'important');
    frame.style.setProperty('display', 'block', 'important');
    frame.style.setProperty('border', 'none', 'important');
  } catch (e) {
    console.warn('[CRM] ajustarPopupHost:', e && e.message ? e.message : e);
  }
}



/* ================================================================
   ESTADO
================================================================ */
var STATE = {
  step:0, codparc:'', nunota:'', doc:'', docType:'',
  fone:'',
  userId:'', requestHash:'', needPin:false,
  pinObrigatorioParceiro:false,
  bonusHas:false, bonusValue:0, campaigns:[],
  selectedCampaignId:'', saleId:'', discountApplied:0
};

/* ================================================================
   NAVEGACAO
================================================================ */
var STEPS       = ['panelVerify','panelPin','panelBonus'];
var BTN_LABELS  = ['Verificar Cliente','Validar PIN','Fechar'];
var HDR_SUBS    = ['Identificacao do cliente','Validar PIN do cliente','Bonus consultado'];
var HDR_BADGES  = ['badge-step','badge-step','badge-ok'];

function goStep(n) {
  STEPS.forEach(function(id,i) {
    document.getElementById(id).classList.toggle('active', i===n);
    var dot = document.getElementById('dot'+i);
    if (!dot) return;
    dot.classList.remove('active','done');
    if (i<n) dot.classList.add('done');
    if (i===n) dot.classList.add('active');
  });
  STATE.step = n;
  var sub   = document.getElementById('headerSub');
  var badge = document.getElementById('headerBadge');
  if (sub)   sub.textContent  = HDR_SUBS[n]  || '';
  if (badge) { badge.textContent='Passo '+(n+1)+'/3'; badge.className='badge '+(HDR_BADGES[n]||'badge-step'); }
  var btnNext   = document.getElementById('btnNext');
  var btnBack   = document.getElementById('btnBack');
  var btnCancel = document.getElementById('btnCancel');
  if (btnNext)   { btnNext.textContent=BTN_LABELS[n]||'Avancar'; btnNext.disabled=false; }
  if (btnBack)   btnBack.style.display = (n>0&&n<2) ? '' : 'none';
  if (btnCancel && n===2) btnCancel.textContent='Fechar';
}

function goBack() {
  if (STATE.step===1) goStep(0);
  else if (STATE.step===2) goStep(STATE.needPin?1:0);
}

/* ================================================================
   ORQUESTRADOR
================================================================ */
function handleNext() {
  if      (STATE.step===0) doUserVerify();
  else if (STATE.step===1) doPinValidate();
  else if (STATE.step===2) doBonusCampaigns();
  else                     closeModal();
}

/* ================================================================
   INICIALIZACAO - chamada diretamente, sem depender de eventos
   Compativel com iframe Sankhya onde DOMContentLoaded pode nao disparar
================================================================ */
function init() {
  console.log('[CRM] build:', BUILD_TAG);
  ajustarPopupHost();
  setTimeout(ajustarPopupHost, 200);
  setTimeout(ajustarPopupHost, 600);
  var search = window.location.search || window.location.href.replace(/^[^?]*/,'');
  var p = new URLSearchParams(search);

  console.log('[CRM] init: location.href =', window.location.href);
  console.log('[CRM] init: location.search =', window.location.search);
  console.log('[CRM] init: params brutos: codparc='+p.get('codparc')+' nunota='+p.get('nunota')+' requesthash='+p.get('requesthash'));

  STATE.codparc     = (p.get('codparc')     || p.get('CODPARC')     || '').trim();
  STATE.nunota      = (p.get('nunota')       || p.get('NUNOTA')      || '').trim();
  /* Hash passado pelo CrmBonusController na URL — fonte primaria */
  STATE.requestHash = (p.get('requesthash') || p.get('REQUESTHASH') || '').trim();

  console.log('[CRM] init: STATE.nunota='+STATE.nunota+' STATE.codparc='+STATE.codparc+' (lengths: '+STATE.nunota.length+', '+STATE.codparc.length+')');
  publicarContextoAtualNoStorage();

  /* CPF/CNPJ e telefone sao readonly — carregados via lerDadosParceiro */

  /* Carrega CGC_CPF e telefone do parceiro antes de exibir o painel */
  if (STATE.codparc) {
    lerDadosParceiro(STATE.codparc, function(cpf, fone) {
      var elCpf  = document.getElementById('inputCpf');
      var elFone = document.getElementById('inputFone');
      if (elCpf)  elCpf.value  = cpf  || '';
      if (elFone) {
        var foneTxt = (fone || '').trim();
        elFone.value = foneTxt || 'Informe o telefone';
      }
      STATE.doc = (cpf||'').replace(/\D/g,'');
      STATE.fone = (fone||'').trim();
      if (!telefonePreenchido()) avisarTelefoneObrigatorio();
    });
  }

  /* Se hash nao veio na URL, busca da TGFCAB antes de prosseguir */
  if (STATE.requestHash) {
    console.log('[CRM] init: hash disponivel na URL:', STATE.requestHash.substring(0,16)+'...');
    publicarContextoAtualNoStorage();
    prosseguirAposHash();
  } else if (STATE.nunota) {
    lerHashDaTGFCAB(STATE.nunota, function(hash) {
      if (hash) {
        STATE.requestHash = hash;
        console.log('[CRM] init: AD_REQHASH lido da TGFCAB:', hash.substring(0,16)+'...');
      } else {
        console.warn('[CRM] init: AD_REQHASH nao encontrado na TGFCAB para NUNOTA='+STATE.nunota);
      }
      publicarContextoAtualNoStorage();
      prosseguirAposHash();
    });
  } else {
    console.warn('[CRM] init: NUNOTA vazio — nao e possivel buscar hash');
    publicarContextoAtualNoStorage();
    prosseguirAposHash();
  }
}

/* Busca CGC_CPF e telefone do parceiro na TSIPAR */
function lerDadosParceiro(codparc, cb) {
  var sid = getCookie('JSESSIONID').replace('.master','');
  var url = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession='+sid;
  var body = {
    serviceName:'CRUDServiceProvider.loadRecords',
    requestBody:{dataSet:{
      rootEntity:'Parceiro',
      includePresentationFields:'S',
      offsetPage:'0',
      criteria:{expression:{$:'this.CODPARC = '+Number(codparc)}},
      entity:{fieldset:{list:'CGC_CPF,TELEFONE,CODPARC'}}
    }}
  };
  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
    .then(function(r){return r.json();})
    .then(function(resp){
      console.log('[CRM] lerDadosParceiro status:', resp.status);
      var reg = extrairRegistroMaisRecente(resp);
      console.log('[CRM] lerDadosParceiro reg:', JSON.stringify(reg));
      var cpf  = reg ? (reg.CGC_CPF  || '') : '';
      var fone = reg ? (reg.TELEFONE || '') : '';
      cb(cpf, fone);
    })
    .catch(function(e){ console.error('[CRM] lerDadosParceiro erro:', e); cb('',''); });
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
      entity:{fieldset:{list:'AD_REQHASH,NUNOTA'}}
    }}
  };
  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
    .then(function(r){return r.json();})
    .then(function(resp){
      console.log('[CRM] lerHashDaTGFCAB status:', resp.status);
      var reg = extrairRegistroMaisRecente(resp);
      var hash = reg ? (reg.AD_REQHASH || null) : null;
      console.log('[CRM] lerHashDaTGFCAB hash:', hash ? hash.substring(0,16)+'...' : 'null');
      cb(hash);
    })
    .catch(function(e){ console.error('[CRM] lerHashDaTGFCAB erro:', e); cb(null); });
}

function prosseguirAposHash() {
  /* Atualiza chip HASH */
  var infoRow = document.getElementById('infoRow');
  if (infoRow) {
    var cpfEl  = document.getElementById('inputCpf');
    var foneEl = document.getElementById('inputFone');
    infoRow.innerHTML =
      chip('CODPARC', STATE.codparc||'-') +
      chip('NUNOTA',  STATE.nunota ||'-') +
      chip('HASH',    STATE.requestHash ? STATE.requestHash.substring(0,8)+'...' : '(sem hash)') +
      (cpfEl  && cpfEl.value  ? chip('CPF/CNPJ', cpfEl.value)  : '') +
      (foneEl && foneEl.value ? chip('TELEFONE', foneEl.value) : '');
  }

  console.log('[CRM] init: STATE.codparc=', STATE.codparc, '| STATE.nunota=', STATE.nunota, '| STATE.requestHash=', STATE.requestHash);

  if (!STATE.codparc) {
    console.warn('[CRM] init: STATE.codparc vazio — parametro nao foi passado na URL do modal');
    return;
  }

  /* Pre-carrega AD_USER_ID, mas NAO pula user-verify */
  lerUserIdParceiro(STATE.codparc, function(uid, pinObrigatorio) {
    if (!telefonePreenchido()) {
      console.warn('[CRM] init: telefone vazio para CODPARC', STATE.codparc, '— bloqueando validacao');
      avisarTelefoneObrigatorio();
      return;
    }
    console.log('[CRM] init: uid recebido de lerUserIdParceiro:', uid);
    STATE.pinObrigatorioParceiro = !!pinObrigatorio;
    console.log('[CRM] init: AD_PINVAL obriga PIN =', STATE.pinObrigatorioParceiro);
    if (uid) {
      STATE.userId = uid;
      if (STATE.pinObrigatorioParceiro) {
        showStatus('verifyStatus','warn','Parceiro com validação do PIN obtigatória. Execute a validação para seguir com o flluxo.');
      } else {
        showStatus('verifyStatus','info','Parceiro com user_id cadastrado. Clique em Verificar Cliente para validar no CRM Bonus.');
      }
    } else {
      if (STATE.pinObrigatorioParceiro) {
        showStatus('verifyStatus','warn','Parceiro com validação do PIN obtigatória. Execute a validação para seguir com o flluxo.');
      }
      console.warn('[CRM] init: AD_USER_ID nao encontrado para CODPARC', STATE.codparc, '— exibindo tela de CPF normalmente');
    }
  });
}

/* Garante execucao mesmo que o readyState ja seja "complete" no iframe */
if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', init);
} else {
  init();
}

function extrairRequestPin(data) {
  if (!data) return null;
  function paraBool(v) {
    if (v === true || v === 'true' || v === 1 || v === '1') return true;
    if (v === false || v === 'false' || v === 0 || v === '0') return false;
    return null;
  }
  var b = paraBool(data.request_pin);
  if (b !== null) return b;
  if (data.data && typeof data.data === 'object') {
    b = paraBool(data.data.request_pin);
    if (b !== null) return b;
  }
  var msg = String(data.statusMessage || '');
  var m = msg.match(/"request_pin"\s*:\s*(true|false|1|0)/i);
  if (m && m[1]) return paraBool(m[1].toLowerCase());
  return null;
}

function mensagemAmigavelPinValidate(err) {
  var bruto = String(err || '');
  var limpo = bruto.replace(/<[^>]+>/g,' ').replace(/\s+/g,' ').trim();
  if (/necess[aá]rio informar o pin/i.test(limpo)) {
    return 'Informe o PIN para continuar a validação.';
  }
  var jsonMatch = limpo.match(/\{[\s\S]*\}/);
  if (jsonMatch) {
    try {
      var j = JSON.parse(jsonMatch[0]);
      if (j && j.valid_pin === false) {
        return 'PIN inválido. Verifique o código informado e tente novamente.';
      }
    } catch (e) {}
  }
  return limpo || 'Não foi possível validar o PIN. Tente novamente.';
}

/* ================================================================
   PASSO 1 - user-verify (sempre executa no passo de verificacao)
================================================================ */
function doUserVerify() {
  if (!telefonePreenchido()) {
    avisarTelefoneObrigatorio();
    return;
  }
  /* Campo e readonly — usa STATE.doc que foi preenchido via lerDadosParceiro */
  var raw    = document.getElementById('inputCpf').value || STATE.doc || '';
  var digits = raw.replace(/\D/g,'');
  if (digits.length!==11 && digits.length!==14) {
    showStatus('verifyStatus','error','Informe um CPF valido (11 digitos) ou CNPJ valido (14 digitos).');
    return;
  }
  STATE.doc     = digits;
  STATE.docType = digits.length===11 ? 'CPF' : 'CNPJ';
  setLoading('verifyStatus','Consultando cliente na CRM Bonus...');
  setBtn(false);

  var payload = STATE.docType==='CPF' ? {cpf:digits} : {cnpj:digits};

  hubCall('inicializacao','user-verify', payload, function(err, data) {
    setBtn(true);
    if (err) { showStatus('verifyStatus','error',err); return; }

    /* Extrai user_id e request_pin (apenas informativo quando AD_PINVAL='N') */
    if (data.user_id) STATE.userId = data.user_id;
    var requestPin = extrairRequestPin(data);
    STATE.needPin = !!STATE.pinObrigatorioParceiro;

    if (STATE.userId) {
      salvarUserIdParceiro(STATE.codparc, STATE.userId, function(){});
    }

    var pinObrigatorio = !!STATE.pinObrigatorioParceiro;
    if (pinObrigatorio) {
      STATE.needPin = true;
      if (requestPin === false) {
        console.warn('[CRM] doUserVerify: request_pin=false, mas AD_PINVAL ativo no parceiro. Forcando fluxo de PIN.');
      }
      showStatus('verifyStatus','success','Cliente localizado. Enviando PIN...');
      doPinSend();
      return;
    }

    if (requestPin === true) {
      console.log('[CRM] doUserVerify: request_pin=true ignorado, pois AD_PINVAL do parceiro nao esta ativo.');
    }

    if (requestPin === false) {
      STATE.needPin = false;
      showStatus('verifyStatus','success','Cliente identificado. Consultando b&ocirc;nus...');
      setTimeout(function(){ doBonusAvailable(); }, 500);
      return;
    }

    if (data._htmlResponse) {
      /* Compatibilidade para respostas antigas sem request_pin explicito */
      if (STATE.userId) {
        showStatus('verifyStatus','success','Cliente identificado. Consultando b&ocirc;nus...');
        setTimeout(function(){ doBonusAvailable(); }, 500);
      } else {
        showStatus('verifyStatus','success','Cliente localizado. Registrando acesso...');
        doPinSendSilencioso();
      }
      return;
    }

    if (STATE.needPin) {
      showStatus('verifyStatus','success','Cliente localizado. Enviando PIN...');
      doPinSend();
    } else if (STATE.userId) {
      showStatus('verifyStatus','success','Cliente identificado. Consultando b&ocirc;nus...');
      setTimeout(function(){ doBonusAvailable(); }, 500);
    } else {
      showStatus('verifyStatus','success','Cliente localizado. Registrando acesso...');
      doPinSendSilencioso();
    }
  });
}

/* ================================================================
   PASSO 2 - pin-send
================================================================ */
function doPinSend() {
  var payload = {
    customer:{ country_code:55, name:'', phone:'', document_type:STATE.docType||'CPF', document_number:STATE.doc },
    gross_value:0, items:[]
  };
  setLoading('verifyStatus','Enviando PIN para o cliente...');
  hubCall('inicializacao','pin-send', payload, function(err, data) {
    if (err) { showStatus('verifyStatus','error',err); setBtn(true); return; }
    if (data.user_id) {
      STATE.userId = data.user_id;
      console.log('[CRM] pin-send: user_id obtido=', STATE.userId.substring(0,16)+'...');
      salvarUserIdParceiro(STATE.codparc, STATE.userId, function(){
        console.log('[CRM] pin-send: AD_USER_ID salvo na TGFPAR');
      });
    } else {
      console.warn('[CRM] pin-send: user_id NAO retornado. statusMessage completo:', data.statusMessage);
    }
    setBtn(true);
    clearStatus('pinStatus');
    goStep(1);
  });
}

/* ================================================================
   PIN-SEND SILENCIOSO
   Chamado quando user-verify retorna request_pin=false mas nao
   devolve user_id. Obtem o user_id via pin-send sem exibir tela
   de PIN — o operador nao precisa fazer nada.
================================================================ */
function doPinSendSilencioso() {
  var payload = {
    customer:{ country_code:55, name:'', phone:'', document_type:STATE.docType||'CPF', document_number:STATE.doc },
    gross_value:0, items:[]
  };
  setLoading('verifyStatus','Identificando cliente...');
  hubCall('inicializacao','pin-send', payload, function(err, data) {
    if (err) { showStatus('verifyStatus','error',err); setBtn(true); return; }
    if (data.user_id) STATE.userId = data.user_id;
    salvarUserIdParceiro(STATE.codparc, STATE.userId, function(){});
    showStatus('verifyStatus','success','Cliente identificado. Consultando b&ocirc;nus...');
    setTimeout(function(){ doBonusAvailable(); }, 500);
  });
}

/* ================================================================
   PASSO 3 - pin-validate
================================================================ */
function doPinValidate() {
  var pin = document.getElementById('inputPin').value.trim();
  if (!pin) { showStatus('pinStatus','error','Informe o PIN.'); return; }
  setLoading('pinStatus','Validando PIN...');
  setBtn(false);
  hubCall('inicializacao','pin-validate', {pin:pin,user_id:STATE.userId}, function(err, data) {
    setBtn(true);
    if (err) { showStatus('pinStatus','error',mensagemAmigavelPinValidate(err)); return; }
    /* user_id pode vir no _htmlResponse ou direto */
    if (data && data.user_id) STATE.userId = data.user_id;
    console.log('[CRM] doPinValidate: STATE.userId apos pin-validate=', STATE.userId);
    showStatus('pinStatus','success','PIN validado com sucesso.');

    function prosseguirComBonus() {
      if (STATE.userId) {
        salvarUserIdParceiro(STATE.codparc, STATE.userId, function(){
          console.log('[CRM] AD_USER_ID salvo na TGFPAR, chamando bonus-available');
          setTimeout(function(){ doBonusAvailable(); }, 300);
        });
      } else {
        console.error('[CRM] user_id VAZIO — bonus-available vai falhar!');
        showStatus('pinStatus','error',
          'PIN validado, mas nao foi possivel identificar o usuario CRM. ' +
          'Verifique se o parceiro possui AD_USER_ID cadastrado.');
      }
    }

    if (STATE.userId) {
      prosseguirComBonus();
    } else {
      /* userId nao veio no retorno da Action — tenta buscar direto da TGFPAR */
      console.warn('[CRM] user_id nao retornado pelo pin-validate, buscando da TGFPAR...');
      lerUserIdParceiro(STATE.codparc, function(uid) {
        if (uid) {
          STATE.userId = uid;
          console.log('[CRM] user_id recuperado da TGFPAR:', uid.substring(0,16)+'...');
          prosseguirComBonus();
        } else {
          /* Ultimo recurso: tenta AD_TGIBAV pelo requestHash desta sessao */
          console.warn('[CRM] user_id nao encontrado na TGFPAR, tentando AD_TGIBAV...');
          lerUserIdDaTGIBAV(function(uid2) {
            if (uid2) {
              STATE.userId = uid2;
              console.log('[CRM] user_id recuperado da AD_TGIBAV:', uid2.substring(0,16)+'...');
              salvarUserIdParceiro(STATE.codparc, STATE.userId, function(){});
            }
            prosseguirComBonus();
          });
        }
      });
    }
  });
}

/* ================================================================
   PASSO 4 - pin-resend
================================================================ */
function resendPin() {
  var btn = document.getElementById('btnResendPin');
  if (!btn) return;
  btn.disabled = true;
  setLoading('pinStatus','Reenviando PIN...');

  function executarReenvio(uid) {
    if (!uid) {
      btn.disabled = false;
      showStatus('pinStatus','error','Nao foi possivel reenviar o PIN: user_id nao identificado.');
      return;
    }
    hubCall('inicializacao','pin-resend', {user_id:uid}, function(err, data) {
      btn.disabled = false;
      if (err) { showStatus('pinStatus','error',err); return; }
      showStatus('pinStatus','info','PIN reenviado com sucesso.');
    });
  }

  if (STATE.userId) {
    executarReenvio(STATE.userId);
    return;
  }
  lerUserIdParceiro(STATE.codparc, function(uid) {
    if (uid) STATE.userId = uid;
    executarReenvio(STATE.userId);
  });
}

/* ================================================================
   PASSO 5 - bonus-available
   Fluxo: Action 113 grava em AD_TGIBAV → le registro → exibe
   BAV_MESSAGE como confirmacao → usuario decide aplicar ou nao
================================================================ */
function extrairRegistrosDaRespostaCRUD(resp) {
  try {
    var entities = resp && resp.responseBody && resp.responseBody.entities;
    if (!entities) return { total: 0, rows: [] };
    var metaFields = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
    var fieldNames = Array.isArray(metaFields) ? metaFields.map(function(f){ return f.name || ''; })
                  : (metaFields && metaFields.name ? [metaFields.name] : []);
    var ent = entities.entity;
    if (!ent) return { total: Number(entities.total || 0), rows: [] };
    var rowsRaw = Array.isArray(ent) ? ent : [ent];
    var rows = rowsRaw.map(function(row) {
      var obj = {};
      fieldNames.forEach(function(name, i) {
        var cell = row['f'+i];
        obj[name] = cell ? (cell.$ || '') : '';
      });
      return obj;
    });
    return { total: Number(entities.total || 0), rows: rows };
  } catch (e) {
    console.error('[CRM] extrairRegistrosDaRespostaCRUD:', e);
    return { total: 0, rows: [] };
  }
}

/* Busca paginada em AD_TGIBAV para evitar perda por limite de 50 registros/pagina.
   Tenta primeiro com criteria por hash; se a plataforma ignorar criteria, continua
   pagina a pagina filtrando no JS. */
function buscarRegistrosADTGIBAVPorHash(fieldList, hash, cb) {
  var sid = getCookie('JSESSIONID').replace('.master','');
  if (!sid) { cb('Sessao nao encontrada', []); return; }
  if (!hash) { cb(null, []); return; }

  var url = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession='+sid;
  var PAGE_GUARD = 40;

  function carregarPagina(page) {
    var body = {
      serviceName : 'CRUDServiceProvider.loadRecords',
      requestBody : {
        dataSet: {
          rootEntity : 'AD_TGIBAV',
          includePresentationFields: 'N',
          offsetPage : String(page),
          criteria   : { expression: { $: "this.BAV_REQUEST_HASH = '" + hash + "'" } },
          entity     : { fieldset: { list: fieldList } }
        }
      }
    };
    fetch(url, { method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(body) })
      .then(function(r){ return r.json(); })
      .then(function(resp){
        var st = Number(resp.status);
        if (st !== 0 && st !== 1) {
          cb('CRUD status '+st+': '+(resp.statusMessage||''), []);
          return;
        }
        var parsed = extrairRegistrosDaRespostaCRUD(resp);
        var rows = parsed.rows || [];
        var encontrados = rows.filter(function(r){ return String(r.BAV_REQUEST_HASH || '') === String(hash); });
        console.log('[CRM] AD_TGIBAV pagina '+page+': rows='+rows.length+' filtrados='+encontrados.length);
        if (encontrados.length) { cb(null, encontrados); return; }

        /* criteria pode ser ignorado em AD_; continua paginando ate esgotar */
        if (rows.length === 0 || page >= PAGE_GUARD) { cb(null, []); return; }
        carregarPagina(page + 1);
      })
      .catch(function(e){ cb(String(e), []); });
  }

  carregarPagina(0);
}

/* Le user_id da AD_TGIBAV pelo request hash da sessao atual */
function lerUserIdDaTGIBAV(cb) {
  if (!STATE.requestHash) { cb(null); return; }
  buscarRegistrosADTGIBAVPorHash('USER_ID,BAV_REQUEST_HASH,CODPARC', STATE.requestHash, function(err, regs) {
    if (err) { console.warn('[CRM] lerUserIdDaTGIBAV:', err); cb(null); return; }
    var uid = (regs && regs.length && regs[0].USER_ID) ? regs[0].USER_ID : null;
    cb(uid || null);
  });
}

function doBonusAvailable() {
  goStep(2);
  setBtn(false);
  var bonusArea = document.getElementById('bonusAvailableArea');
  bonusArea.innerHTML = '<div class="spinner-wrap"><div class="spinner"></div>Consultando b&ocirc;nus dispon&iacute;vel...</div>';

  /* A Action 113 le NUNOTA, CODPARC, AD_REQHASH, itens e valor diretamente do banco.
     O modal nao precisa passar payload — a Action ignora o campo PAYLOAD da row. */
  console.log('[CRM] bonus-available: chamando Action 113 para NUNOTA='+STATE.nunota);
  hubCall('bonus','bonus-available', {}, function(err, data) {
    if (err) {
      bonusArea.innerHTML = '<div class="alert alert-error"><span class="alert-icon">(!)</span><div class="alert-text"><strong>Erro:</strong> '+esc(err)+'</div></div>';
      setBtn(true); return;
    }
    console.log('[CRM] bonus-available resposta:', JSON.stringify(data));

    /* Action 113 gravou na AD_TGIBAV — busca o registro */
    if (data && data._gravouNaTabela) {
      lerRegistroTGIBAV(function(reg) {
        if (!reg) {
          bonusArea.innerHTML = '<div class="alert alert-error"><span class="alert-icon">(!)</span><div class="alert-text">N&atilde;o foi poss&iacute;vel ler o resultado da consulta de b&ocirc;nus (AD_TGIBAV).</div></div>';
          setBtn(true); return;
        }
        exibirConfirmacaoBonus(bonusArea, reg);
      });
      return;
    }

    /* Fallback: dados direto na resposta */
    STATE.bonusHas   = !!(data.bonus_has || data.BONUS_HAS);
    STATE.bonusValue = parseFloat(data.value || data.BAV_BONUS_AVAILABLE || 0);
    exibirResultadoBonus(bonusArea, STATE.bonusHas, STATE.bonusValue, '');
  });
}

/* Le o registro de AD_TGIBAV filtrando por BAV_REQUEST_HASH */
function lerRegistroTGIBAV(cb) {
  console.log('[CRM] AD_TGIBAV: busca paginada por REQUEST_HASH='+STATE.requestHash.substring(0,16)+'...');
  buscarRegistrosADTGIBAVPorHash(
    'USER_ID,BAV_BONUS_BALANCE,BAV_MESSAGE,BAV_REQUEST_HASH,BONUS_HAS,BAV_BONUS_AVAILABLE,BONUS_IDS',
    STATE.requestHash,
    function(err, regs) {
      if (err) {
        console.warn('[CRM] AD_TGIBAV erro na busca paginada:', err, '— tentando fallback SQL...');
        lerRegistroTGIBAVporSQL(cb);
        return;
      }
      var reg = (regs && regs.length) ? regs[0] : null;
      console.log('[CRM] AD_TGIBAV registro filtrado:', JSON.stringify(reg));
      if (!reg) {
        console.warn('[CRM] AD_TGIBAV: hash nao encontrado apos varredura paginada — tentando SQL...');
        lerRegistroTGIBAVporSQL(cb);
        return;
      }
      cb(reg);
    }
  );
}

/* Sem fallback SQL — DbExplorer.executeQuery nao disponivel nesta versao do Sankhya.
   Se o hash nao foi encontrado, a Action nao gravou o registro (erro no HUB).
   O erro ja foi tratado antes de chegar aqui. */
function lerRegistroTGIBAVporSQL(cb) {
  console.warn('[CRM] AD_TGIBAV: hash nao encontrado na tabela. A Action provavelmente falhou.');
  cb(null);
}

/* Filtra registros de AD_TGIBAV pelo BAV_REQUEST_HASH correto */
function filtrarPorHash(resp, hash) {
  try {
    var rb       = resp && resp.responseBody;
    var entities = rb && rb.entities;
    if (!entities) return null;

    var metaFields = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
    var fieldNames = [];
    if (Array.isArray(metaFields)) {
      fieldNames = metaFields.map(function(f){ return f.name || ''; });
    } else if (metaFields && metaFields.name) {
      fieldNames = [metaFields.name];
    }

    var ent  = entities.entity;
    if (!ent) return null;
    var rows = Array.isArray(ent) ? ent : [ent];

    /* Descobre qual indice corresponde a BAV_REQUEST_HASH */
    var hashIdx = fieldNames.indexOf('BAV_REQUEST_HASH');
    console.log('[CRM] filtrarPorHash: '+rows.length+' registros, hashIdx='+hashIdx+' buscando='+hash.substring(0,16)+'...');

    for (var r = 0; r < rows.length; r++) {
      var row     = rows[r];
      var rowHash = (hashIdx >= 0 && row['f'+hashIdx]) ? row['f'+hashIdx].$ : '';
      console.log('[CRM] filtrarPorHash: row '+r+' hash='+String(rowHash).substring(0,16)+'...');
      if (rowHash === hash) {
        /* Converte a linha para objeto nomeado */
        var obj = {};
        fieldNames.forEach(function(name, i) {
          var cell = row['f'+i];
          obj[name] = cell ? (cell.$ || '') : '';
        });
        return obj;
      }
    }
    return null;
  } catch(e){ console.error('[CRM] filtrarPorHash:', e); return null; }
}

/* Extrai o registro com maior ID (mais recente) de uma resposta CRUD */
function extrairRegistroMaisRecente(resp) {
  try {
    var rb       = resp && resp.responseBody;
    var entities = rb && rb.entities;
    if (!entities) return null;

    /* Nomes dos campos via metadata */
    var fieldNames = [];
    var metaFields = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
    if (Array.isArray(metaFields)) {
      fieldNames = metaFields.map(function(f){ return f.name || ''; });
    } else if (metaFields && metaFields.name) {
      fieldNames = [metaFields.name];
    }

    var ent  = entities.entity;
    if (!ent) return null;
    var rows = Array.isArray(ent) ? ent : [ent];
    if (!rows.length) return null;

    /* Converte todas as linhas em objetos e pega a de maior ID */
    var objetos = rows.map(function(row) {
      var obj = {};
      fieldNames.forEach(function(name, i) {
        var cell = row['f'+i];
        obj[name] = cell ? (cell.$ || '') : '';
      });
      return obj;
    });

    /* Ordena por ID decrescente e retorna o primeiro */
    objetos.sort(function(a,b){ return Number(b.ID||0) - Number(a.ID||0); });
    console.log('[CRM] extrairRegistroMaisRecente: '+objetos.length+' registros, usando ID='+objetos[0].ID);
    return objetos[0];
  } catch(e){ console.error('[CRM] extrairRegistroMaisRecente:', e); return null; }
}

/* Extrai primeiro registro de resposta CRUDServiceProvider.
   Sankhya W retorna campos como f0, f1... com nomes no metadata.
   Exemplo: metadata.fields.field = [{name:'AD_USER_ID'},{name:'CODPARC'}]
            entity = {f0:{$:'abc'}, f1:{$:'123'}} */
function extrairPrimeiroRegistro(resp) {
  try {
    var rb  = resp && resp.responseBody;
    if (!rb) return null;
    var entities = rb.entities;
    if (!entities) return null;

    /* Extrai nomes dos campos do metadata */
    var fieldNames = [];
    try {
      var metaFields = entities.metadata && entities.metadata.fields && entities.metadata.fields.field;
      if (Array.isArray(metaFields)) {
        fieldNames = metaFields.map(function(f){ return f.name || f.fieldName || ''; });
      } else if (metaFields && metaFields.name) {
        fieldNames = [metaFields.name];
      }
    } catch(e){}

    var ent  = entities.entity;
    if (!ent) return null;
    var rows = Array.isArray(ent) ? ent : [ent];
    if (!rows.length) return null;

    var row = rows[0];
    var obj = {};

    if (fieldNames.length > 0) {
      /* Formato Sankhya W: f0, f1, f2... mapeados pelo metadata */
      fieldNames.forEach(function(name, i) {
        var fKey = 'f' + i;
        var cell = row[fKey];
        obj[name] = cell ? (cell.$ || cell.value || '') : '';
      });
    } else {
      /* Fallback: itera todas as chaves do objeto */
      Object.keys(row).forEach(function(k) {
        var cell = row[k];
        if (cell && typeof cell === 'object') {
          obj[k] = cell.$ || cell.value || '';
        } else {
          obj[k] = cell || '';
        }
      });
    }

    console.log('[CRM] extrairPrimeiroRegistro: fieldNames=', JSON.stringify(fieldNames), 'obj=', JSON.stringify(obj));
    return obj;
  } catch(e){ console.error('[CRM] extrairPrimeiroRegistro:', e); return null; }
}

/* Exibe BAV_MESSAGE como confirmacao — usuario escolhe Sim ou Nao */
function exibirConfirmacaoBonus(bonusArea, reg) {
  STATE.bonusHas   = String(reg.BONUS_HAS||'').toUpperCase()==='S' || String(reg.BONUS_HAS||'')==='true';
  STATE.bonusValue = parseFloat(reg.BAV_BONUS_AVAILABLE || reg.BAV_BONUS_BALANCE || 0);
  STATE.bonusIds   = reg.BONUS_IDS || '';

  var msg = decodeStr((reg.BAV_MESSAGE || '').trim());

  if (!STATE.bonusHas) {
    bonusArea.innerHTML =
      '<div class="alert alert-warn"><span class="alert-icon">(i)</span>'+
      '<div class="alert-text">'+(msg||'O cliente <strong>nao possui bonus disponivel</strong> para esta venda.')+'</div></div>';
    var btn = document.getElementById('btnNext');
    btn.textContent = 'Fechar'; btn.onclick = closeModal;
    setBtn(true); return;
  }

  /* Esconde footer — botoes inline substituem */
  document.querySelector('.modal-footer').style.display = 'none';

  bonusArea.innerHTML =
    '<div class="bonus-card">'+
      '<div class="bc-label">B&ocirc;nus dispon&iacute;vel</div>'+
      '<div class="bc-value">R$ '+fmtNum(STATE.bonusValue)+'</div>'+
    '</div>'+
    '<div class="alert alert-warn" style="align-items:center">'+
      '<span class="alert-icon" style="font-size:18px">?</span>'+
      '<div class="alert-text" style="font-size:13px"><strong>'+esc(msg)+'</strong></div>'+
    '</div>'+
    '<div style="display:flex;gap:10px">'+
      '<button class="btn btn-primary" style="flex:1;padding:11px" onclick="confirmarAplicarBonus()">Sim, aplicar b&ocirc;nus</button>'+
      '<button class="btn btn-secondary" style="flex:1;padding:11px" onclick="closeModal()">N&atilde;o, fechar</button>'+
    '</div>';
}

/* Confirma: restaura footer e carrega campanhas */
function confirmarAplicarBonus() {
  /* Esconde footer e mostra spinner enquanto chama Action 115 */
  document.querySelector('.modal-footer').style.display = 'none';
  var bonusArea = document.getElementById('bonusAvailableArea');
  if (bonusArea) {
    bonusArea.innerHTML +=
      '<div id="submitStatus" style="margin-top:12px">'+
        '<div class="spinner-wrap"><div class="spinner"></div>Aplicando b&ocirc;nus (Action 115)...</div>'+
      '</div>';
  }

  hubCall('bonus', 'bonus-submit', {}, function(err, data) {
    var statusDiv = document.getElementById('submitStatus');
    if (statusDiv) statusDiv.innerHTML = '';

    document.querySelector('.modal-footer').style.display = '';
    var btnNext   = document.getElementById('btnNext');
    var btnBack   = document.getElementById('btnBack');
    var btnCancel = document.getElementById('btnCancel');
    if (btnBack)   btnBack.style.display   = 'none';
    if (btnCancel) btnCancel.style.display = 'none';
    if (btnNext)   { btnNext.textContent='Fechar'; btnNext.onclick=closeModal; btnNext.disabled=false; }

    if (err) {
      if (bonusArea) {
        bonusArea.innerHTML +=
          '<div class="alert alert-error" style="margin-top:10px">'+
            '<span class="alert-icon">(!)</span>'+
            '<div class="alert-text"><strong>Erro ao aplicar bonus:</strong> '+esc(err)+'</div>'+
          '</div>';
      }
      return;
    }

    /* Sucesso — Action 115 retorna status 2 com mensagem de confirmacao */
    var msg = decodeStr((data && data.statusMessage) ? data.statusMessage.replace(/<[^>]+>/g,' ').replace(/\s+/g,' ').trim() : 'B&ocirc;nus aplicado.');
    msg = formatarMoedaEmTexto(msg);
    if (bonusArea) {
      bonusArea.innerHTML =
        '<div class="alert alert-success" style="margin-bottom:10px">'+
          '<span class="alert-icon">&#10003;</span>'+
          '<div class="alert-text">'+
            '<strong>B&ocirc;nus aplicado com sucesso!</strong><br>'+
            esc(msg)+
          '</div>'+
        '</div>';
    }
  });
}

/* Exibe resultado quando dados veem direto (fallback) */
function exibirResultadoBonus(bonusArea, has, value, msg) {
  if (has) {
    bonusArea.innerHTML =
      '<div class="bonus-card">'+
        '<div class="bc-label">B&ocirc;nus dispon&iacute;vel</div>'+
        '<div class="bc-value">R$ '+fmtNum(value)+'</div>'+
        '<div class="bc-sub">Disponivel para aplicar nesta venda</div>'+
      '</div>';
    confirmarAplicarBonus(); /* Modal 1 encerra aqui */
  } else {
    bonusArea.innerHTML =
      '<div class="alert alert-warn"><span class="alert-icon">(i)</span>'+
      '<div class="alert-text">'+(msg||'O cliente <strong>nao possui bonus disponivel</strong> para esta venda.')+'</div></div>';
    var btn = document.getElementById('btnNext');
    btn.textContent = 'Fechar'; btn.onclick = closeModal;
    setBtn(true);
  }
}

/* ================================================================
   SANKHYA - salvar campo na TGFCAB
================================================================ */
function salvarCampoVenda(campo, valor, cb) {
  if (!STATE.nunota) { cb&&cb(); return; }
  try {
    var sid  = getCookie('JSESSIONID').replace('.master','');
    var url  = '/mge/service.sbr?serviceName=CabecalhoNota.salvarCabecalho&outputType=json&mgeSession='+sid;
    var body = {serviceName:'CabecalhoNota.salvarCabecalho',requestBody:{cabecalhoNota:{NUNOTA:{$:STATE.nunota}}}};
    body.requestBody.cabecalhoNota[campo] = {$:valor};
    fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
      .then(function(){ cb&&cb(); }).catch(function(e){ cb&&cb(String(e)); });
  } catch(e){ cb&&cb(String(e)); }
}

/* ================================================================
   SANKHYA - salvar AD_USER_ID na TGFPAR
================================================================ */
function salvarUserIdParceiro(codparc, userId, cb) {
  if (!codparc||!userId) { cb&&cb(); return; }
  try {
    var sid  = getCookie('JSESSIONID').replace('.master','');
    var url  = '/mge/service.sbr?serviceName=CadastroParc.salvar&outputType=json&mgeSession='+sid;
    var body = {serviceName:'CadastroParc.salvar',requestBody:{parceiro:{CODPARC:{$:codparc},AD_USER_ID:{$:userId}}}};
    fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
      .then(function(){ cb&&cb(); }).catch(function(e){ cb&&cb(String(e)); });
  } catch(e){ cb&&cb(String(e)); }
}

/* ================================================================
   SANKHYA - ler AD_USER_ID da TGFPAR
================================================================ */
function lerUserIdParceiro(codparc, cb) {
  if (!codparc) { cb&&cb(null); return; }
  var sid = getCookie('JSESSIONID').replace('.master','');
  var url  = '/mge/service.sbr?serviceName=CRUDServiceProvider.loadRecords&outputType=json&mgeSession='+sid;
  var body = {
    serviceName : 'CRUDServiceProvider.loadRecords',
    requestBody : {
      dataSet: {
        rootEntity  : 'Parceiro',
        includePresentationFields: 'N',
        offsetPage  : '0',
        criteria    : { expression: { $: 'this.CODPARC = '+Number(codparc) } },
        entity      : { fieldset: { list: 'AD_USER_ID,AD_PINVAL,CODPARC' } }
      }
    }
  };
  fetch(url, { method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(body) })
    .then(function(r){ return r.json(); })
    .then(function(resp){
      console.log('[CRM] lerUserIdParceiro status:', resp.status);
      /* Sankhya W retorna status 0 ou 1 para CRUD bem-sucedido */
      var st = Number(resp.status);
      if (st !== 0 && st !== 1) {
        console.warn('[CRM] lerUserIdParceiro CRUD erro:', resp.statusMessage);
        cb&&cb(null); return;
      }
      var reg = extrairRegistroMaisRecente(resp);
      console.log('[CRM] lerUserIdParceiro registro:', JSON.stringify(reg));
      var uid = reg ? (reg.AD_USER_ID || null) : null;
      var pinObrigatorio = reg ? flagParceiroObrigatoriaPin(reg.AD_PINVAL) : false;
      console.log('[CRM] lerUserIdParceiro: uid =', uid);
      console.log('[CRM] lerUserIdParceiro: AD_PINVAL =', reg ? reg.AD_PINVAL : null, '| pinObrigatorio =', pinObrigatorio);
      cb&&cb(uid||null, pinObrigatorio);
    })
    .catch(function(e){
      console.warn('[CRM] lerUserIdParceiro falhou:', e);
      cb&&cb(null);
    });
}

function flagParceiroObrigatoriaPin(v) {
  var s = String(v == null ? '' : v).trim().toUpperCase();
  if (!s) s = 'S'; /* NVL(AD_PINVAL,'S') */
  return s === 'S' || s === 'Y' || s === 'T' || s === 'TRUE' || s === '1';
}

/* ================================================================
   SANKHYA - aplicar desconto (VLRDESCONTO) na TGFCAB
================================================================ */
function aplicarDescontoSankhya(valorDesconto, cb) {
  if (!STATE.nunota) { cb&&cb(); return; }
  try {
    var sid  = getCookie('JSESSIONID').replace('.master','');
    var url  = '/mge/service.sbr?serviceName=CabecalhoNota.salvarCabecalho&outputType=json&mgeSession='+sid;
    var body = {
      serviceName:'CabecalhoNota.salvarCabecalho',
      requestBody:{cabecalhoNota:{NUNOTA:{$:STATE.nunota},VLRDESCONTO:{$:valorDesconto}}}
    };
    fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
      .then(function(r){ return r.json(); })
      .then(function(j){
        if(j.status&&Number(j.status)!==0) cb(j.statusMessage||'Erro Sankhya');
        else cb(null);
      }).catch(function(e){ cb(String(e)); });
  } catch(e){ cb(String(e)); }
}

/* ================================================================
   SANKHYA - persistir campanhas em AD_CRMBONUSCAMPAIGNS
================================================================ */
function persistCampaigns(camps) {
  try {
    var jx = (window.parent&&window.parent.JX) ? window.parent.JX : (window.JX||null);
    if (!jx) return;
    camps.forEach(function(c) {
      var id   = c.id||c.campaign_id||'';
      var name = (c.name||c.campaign_name||'').replace(/'/g,"''");
      var json = JSON.stringify(c).replace(/'/g,"''");
      jx.executar(
        "INSERT INTO AD_CRMBONUSCAMPAIGNS (ID_VENDA,REQUEST_HASH,USER_ID,CAMPAIGN_ID,CAMPAIGN_NAME,PAYLOAD_JSON) "+
        "VALUES ('"+STATE.nunota+"','"+STATE.requestHash+"','"+STATE.userId+"','"+id+"','"+name+"','"+json+"')"
      );
    });
  } catch(e){ console.warn('[CRM Bonus] persistCampaigns:',e); }
}

/* ================================================================
   HUB CALL
   Usa ActionButtonsSP.executeJava como proxy server-side.
   A Action Java roda no servidor Sankhya, chama o HUB e retorna
   o JSON — sem CORS, sem dependencia de servicos MGE opcionais.

   ACTION_ID: ID da action registrada no Sankhya (confirme no admin)
   Os campos da venda (NUNOTA) sao passados como rows para a Action.
================================================================ */
/* ================================================================
   ACTION IDs — mapeamento de cada endpoint HUB para o actionID
   correspondente registrado no Sankhya W.
   Ajuste os valores abaixo conforme seus IDs cadastrados.
================================================================ */
var ACTION_IDS = {
  /* entidade: inicializacao */
  'user-verify'  : '109',
  'pin-send'     : '110',
  'pin-validate' : '111',
  'pin-resend'   : '112',

  /* entidade: bonus */
  'bonus-available' : '113',
  'bonus-submit'    : '115'
};

/* Lista de client events padrao do Sankhya W — necessaria pelo executeJava */
var CLIENT_EVENTS = [
  'br.com.sankhya.comercial.recalcula.pis.cofins',
  'br.com.sankhya.financeiro.alert.mudanca.titulo.baixa',
  'br.com.sankhya.actionbutton.clientconfirm',
  'br.com.sankhya.mgecom.enviar.recebimento.wms.sncm',
  'comercial.status.nfe.situacao.diferente',
  'comercial.status.nfcom.situacao.diferente',
  'br.com.sankhya.mgecom.compra.SolicitacaoComprador',
  'br.com.sankhya.mgecom.expedicao.SolicitarUsuarioConferente',
  'br.com.sankhya.mgecom.nota.adicional.SolicitarUsuarioGerente',
  'br.com.sankhya.mgecom.cancelamento.nfeAcimaTolerancia',
  'br.com.sankhya.mgecom.cancelamento.nfComForaPrazo',
  'br.com.sankhya.mgecom.cancelamento.processo.wms.andamento',
  'br.com.sankhya.mgecom.msg.nao.possui.itens.pendentes',
  'br.com.sankhya.mgecomercial.event.baixaPortal',
  'br.com.sankhya.comercial.desfaz.renegociacoes.vendamais',
  'br.com.sankhya.comercial.desfaz.renegociacoes.vendamais.devolucao',
  'br.com.sankhya.mgecom.valida.ChaveNFeCompraTerceiros',
  'br.com.sankhya.mgewms.expedicao.validarPedidos',
  'br.com.sankhya.mgecom.gera.lote.xmlRejeitado',
  'br.com.sankhya.comercial.solicitaContingencia',
  'br.com.sankhya.mgecom.cancelamento.notas.remessa',
  'br.com.sankhya.mgecomercial.event.compensacao.credito.debito',
  'br.com.sankhya.modelcore.comercial.cancela.nota.devolucao.wms',
  'br.com.sankhya.mgewms.expedicao.selecaoDocas',
  'br.com.sankhya.mgewms.expedicao.cortePedidos',
  'br.com.sankhya.modelcore.comercial.cancela.nfce.baixa.caixa.fechado',
  'br.com.utiliza.dtneg.servidor',
  'comercial.status.nfe.aceita.naoSomarItem.SelecaoDocumento'
];

function hubCall(entidade, subEntidade, payload, cb) {
  payload.request_hash = STATE.requestHash;

  var sid = getCookie('JSESSIONID').replace('.master','');
  if (!sid) { cb('Sessao Sankhya nao encontrada. Abra o modal a partir do PDV Web.'); return; }

  console.log('[CRM] hubCall:', subEntidade, '| user_id:', STATE.userId, '| nunota:', STATE.nunota, '('+String(STATE.nunota).length+' chars)');

  chamarActionJava(sid, entidade, subEntidade, payload, function(err, data) {
    if (!err) { cb(null, data); return; }
    console.error('[CRM] Action Java falhou:', err);
    cb('Erro ao chamar Action Java (actionID '+ACTION_IDS[subEntidade]+'): ' + err);
  });
}

/* ----------------------------------------------------------------
   Chama ActionButtonsSP.executeJava passando:
   - actionID: ID da Action Java no Sankhya
   - NUNOTA:   numero da nota/venda atual
   - entidade / sub-entidade: identificam qual endpoint do HUB chamar
   A Action Java usa esses campos para montar o payload e chamar o HUB.
---------------------------------------------------------------- */
function chamarActionJava(sid, entidade, subEntidade, payload, cb) {
  var actionID = ACTION_IDS[subEntidade];
  if (!actionID) {
    cb('actionID nao configurado para sub-entidade "' + subEntidade + '". Verifique ACTION_IDS no topo do script.');
    return;
  }

  var url = '/mge/service.sbr?serviceName=ActionButtonsSP.executeJava&outputType=json&mgeSession=' + sid;

  /* Blindagem: garante nunota integro */
  var nunota = String(STATE.nunota || '').trim();
  console.log('[CRM] chamarActionJava: nunota="'+nunota+'" ('+nunota.length+' chars) subEntidade='+subEntidade);
  if (!nunota) { cb('NUNOTA invalido. Reabra o modal a partir da venda.'); return; }

  /* Campos passados como row para a Action Java */
  var fields = [
    {fieldName: 'NUNOTA',       $: nunota},
    {fieldName: 'ENTIDADE',     $: entidade},
    {fieldName: 'SUBENTIDADE',  $: subEntidade},
    {fieldName: 'REQUEST_HASH', $: STATE.requestHash},
    {fieldName: 'USER_ID',      $: STATE.userId || ''},
    {fieldName: 'PAYLOAD',      $: JSON.stringify(payload)}
  ];

  /* Modal 1 nao usa ACAO='F' */

  var javaCall = {
    actionID   : actionID,
    refreshType: 'SEL',
    rows: { row: [{ field: fields }] }
  };

  /* pin-validate (Action 111): segue contrato do botao nativo Sankhya
     - PIN em javaCall.params.param
     - refreshType = NONE
     - rows com apenas NUNOTA */
  if (subEntidade === 'pin-validate' && payload.pin) {
    javaCall.refreshType = 'NONE';
    javaCall.params = { param: [{ type: 'I', paramName: 'PIN', $: Number(String(payload.pin).trim()) }] };
    javaCall.rows = { row: [{ field: [{ fieldName: 'NUNOTA', $: nunota }] }] };
    console.log('[CRM] PIN adicionado em javaCall.params.param:', String(payload.pin).trim());
  }

  var body = {
    serviceName: 'ActionButtonsSP.executeJava',
    requestBody: {
      javaCall: javaCall,
      clientEventList: {
        clientEvent: CLIENT_EVENTS.map(function(e){ return {$: e}; })
      }
    }
  };

  fetch(url, {
    method  : 'POST',
    headers : {'Content-Type': 'application/json'},
    body    : JSON.stringify(body)
  })
  .then(function(r) {
    if (!r.ok) return r.text().then(function(t){ throw new Error('HTTP ' + r.status + ': ' + t.substring(0,150)); });
    return r.json();
  })
  .then(function(resp) {
    console.log('[CRM] ActionButtonsSP resposta bruta:', JSON.stringify(resp));

    /* -------------------------------------------------------
       Interpretacao dos status do Sankhya W:
       0 = sucesso com dados no responseBody
       1 = sucesso, resultado gravado em tabela AD_
       2 = retornou HTML informativo (dados no statusMessage)
       outros = erro
    ------------------------------------------------------- */
    var st = Number(resp.status);
    var htmlMsg = resp.statusMessage || '';
    console.log('[CRM] chamarActionJava: subEntidade='+subEntidade+' status='+st+' msg='+htmlMsg.substring(0,80));

    /* Sankhya W retorna status 2 para TODAS as actions bem-sucedidas.
       O statusMessage contem a mensagem de retorno (pode ser HTML ou texto simples).
       Tratamos status 2 como SUCESSO — nunca como erro. */

    /* Status 0 com "Regra Personalizada" ou "Erro" no statusMessage = Action executou
       mas retornou erro de negocio. Tratar como erro para mostrar ao usuario. */
    var msgSemHtml = htmlMsg.replace(/<[^>]+>/g,' ').replace(/\s+/g,' ').trim();
    var isErroNegocio = (st === 0) && (
      /regra personalizada/i.test(htmlMsg) ||
      /^erro/i.test(msgSemHtml)
    );
    var isErroPinValidate = (subEntidade === 'pin-validate') && (
      /necess[aá]rio informar o pin/i.test(msgSemHtml) ||
      /^erro/i.test(msgSemHtml)
    );

    if (isErroNegocio || isErroPinValidate) {
      /* Extrai mensagem limpa (sem HTML) para exibir */
      var msgLimpa = msgSemHtml;
      /* Simplifica: pega so a parte apos "Erro ao " se presente */
      var erroMatch = msgLimpa.match(/Erro[^:]*:\s*(.+)/i);
      cb(erroMatch ? erroMatch[1].substring(0,200) : msgLimpa.substring(0,200));
      return null;
    }

    if (st === 0 || st === 1 || st === 2) {
      /* Extrai user_id do HTML — tenta varios formatos possiveis */
      console.log('[CRM] chamarActionJava statusMessage completo ('+subEntidade+'):', htmlMsg);
      var uidFromHtml = null;
      var uidPatterns = [
        /user_id salvo[^<]*<b>([^<]+)<\/b>/i,          /* "user_id salvo ...<b>VALOR</b>" */
        /user[_\s]?id[^:=<>]*[:=]\s*([A-Za-z0-9+/=_-]{8,})/i, /* "user_id: VALOR" ou "user_id=VALOR" */
        /<b>([A-Za-z0-9+/=_-]{16,})<\/b>/,              /* qualquer <b>VALOR_LONGO</b> */
        /["']user_id["']\s*:\s*["']([^"']+)["']/i      /* JSON: "user_id":"VALOR" */
      ];
      for (var pi=0; pi<uidPatterns.length; pi++) {
        var m = htmlMsg.match(uidPatterns[pi]);
        if (m && m[1] && m[1].trim()) { uidFromHtml = m[1].trim(); break; }
      }
      if (uidFromHtml) console.log('[CRM] user_id extraido do statusMessage:', uidFromHtml.substring(0,16)+'...');
      else if (subEntidade === 'pin-validate' || subEntidade === 'user-verify') {
        console.warn('[CRM] user_id NAO encontrado no statusMessage de '+subEntidade+'. Revise os padroes.');
      }

      /* Monta resultado padrao */
      var result = {
        _sankhyaStatus  : st,
        _htmlResponse   : true,
        _gravouNaTabela : (st === 1),
        statusMessage   : htmlMsg
      };
      if (uidFromHtml) result.user_id = uidFromHtml;

      /* bonus-available e bonus-submit gravam em tabela quando retornam status 2 */
      if ((subEntidade === 'bonus-available' || subEntidade === 'bonus-submit') && st === 2) {
        result._gravouNaTabela = true;
      }

      /* bonus-submit: extrai valor aplicado do statusMessage se presente */
      if (subEntidade === 'bonus-submit') {
        var valMatch = htmlMsg.match(/R\$\s*([\d,.]+)/i);
        result._valorAplicado = valMatch ? valMatch[1].replace(',','.') : '';
        result._sucesso = (st === 2);
      }

      cb(null, result);
      return null;
    }

    /* status diferente de 0/1/2 = erro */
    cb(htmlMsg || 'status ' + st);
    return null;
  })
  .catch(function(e) {
    cb(String(e));
  });
}

/* Fallback caso precise chamar sem Action Java (nao deve ser necessario) */
function tentarHttpRequestDoPost(sid, bodyStr, hubHeaders, cb) {
  var url = '/mge/service.sbr?serviceName=HttpRequest.doPost&outputType=json&mgeSession='+sid;
  var req = {
    serviceName :'HttpRequest.doPost',
    requestBody :{
      url:HUB_ENDPOINT, body:bodyStr, contentType:'application/json',
      headers:{header:hubHeaders}
    }
  };
  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(req)})
    .then(function(r){ return r.json(); })
    .then(function(resp){
      if (resp.status && Number(resp.status)!==0) {
        cb('HttpRequest.doPost: '+(resp.statusMessage||'status '+resp.status)); return;
      }
      var raw = resp.responseBody ? (resp.responseBody.body||resp.responseBody) : null;
      if (!raw) { cb('HttpRequest.doPost: sem responseBody'); return; }
      if (typeof raw==='object') { cb(null,raw); return; }
      try { cb(null,JSON.parse(raw)); } catch(e){ cb('parse error: '+raw.substring(0,100)); }
    })
    .catch(function(e){ cb(String(e)); });
}

function tentarMGEServiceExecutor(sid, bodyStr, hubHeaders, cb) {
  var hdrsObj = {};
  hubHeaders.forEach(function(h){ hdrsObj[h.name]=h.value; });
  var url = '/mge/service.sbr?serviceName=MGEServiceExecutor.call&outputType=json&mgeSession='+sid;
  var req = {
    serviceName :'MGEServiceExecutor.call',
    requestBody :{ url:HUB_ENDPOINT, method:'POST', body:bodyStr, headers:hdrsObj }
  };
  fetch(url,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(req)})
    .then(function(r){ return r.json(); })
    .then(function(resp){
      if (resp.status && Number(resp.status)!==0) {
        cb('MGEServiceExecutor: '+(resp.statusMessage||'status '+resp.status)); return;
      }
      var raw = resp.responseBody ? (resp.responseBody.body||resp.responseBody) : null;
      if (!raw) { cb('MGEServiceExecutor: sem responseBody'); return; }
      if (typeof raw==='object') { cb(null,raw); return; }
      try { cb(null,JSON.parse(raw)); } catch(e){ cb('parse error: '+raw.substring(0,100)); }
    })
    .catch(function(e){ cb(String(e)); });
}
</script>
</body>
</html>
