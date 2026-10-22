(function () {
  // Lucide icon nodes, bundled locally so the JSP does not depend on a CDN.
  var iconNodes = {
    sun: [['circle',{cx:12,cy:12,r:4}],['path',{d:'M12 2v2'}],['path',{d:'M12 20v2'}],['path',{d:'m4.93 4.93 1.41 1.41'}],['path',{d:'m17.66 17.66 1.41 1.41'}],['path',{d:'M2 12h2'}],['path',{d:'M20 12h2'}],['path',{d:'m6.34 17.66-1.41 1.41'}],['path',{d:'m19.07 4.93-1.41 1.41'}]],
    moon: [['path',{d:'M20.985 12.486a9 9 0 1 1-9.473-9.472c.405-.022.617.46.402.803a6 6 0 0 0 8.268 8.268c.344-.215.825-.004.803.401'}]],
    eye: [['path',{d:'M2.062 12.348a1 1 0 0 1 0-.696 10.75 10.75 0 0 1 19.876 0 1 1 0 0 1 0 .696 10.75 10.75 0 0 1-19.876 0'}],['circle',{cx:12,cy:12,r:3}]],
    history: [['path',{d:'M3 12a9 9 0 1 0 9-9 9.75 9.75 0 0 0-6.74 2.74L3 8'}],['path',{d:'M3 3v5h5'}],['path',{d:'M12 7v5l4 2'}]]
  };
  function buttonIcon(button, name, label) {
    button.textContent = '';
    button.setAttribute('aria-label', label); button.setAttribute('title', label);
    var svg = document.createElementNS('http://www.w3.org/2000/svg','svg');
    svg.setAttribute('viewBox','0 0 24 24'); svg.setAttribute('width','16'); svg.setAttribute('height','16');
    svg.setAttribute('fill','none'); svg.setAttribute('stroke','currentColor'); svg.setAttribute('stroke-width','1.8');
    svg.setAttribute('stroke-linecap','round'); svg.setAttribute('stroke-linejoin','round'); svg.setAttribute('aria-hidden','true');
    var nodes=iconNodes[name];
    for(var n=0;n<nodes.length;n++){
      var node=document.createElementNS('http://www.w3.org/2000/svg',nodes[n][0]);
      for(var attr in nodes[n][1])if(nodes[n][1].hasOwnProperty(attr))node.setAttribute(attr,nodes[n][1][attr]);
      svg.appendChild(node);
    }
    button.appendChild(svg);
  }
  function groupAction(button) {
    var parent=button.parentNode;
    var group=parent.querySelector('.row-actions');
    if(!group){group=document.createElement('div');group.className='row-actions';parent.appendChild(group);}
    group.appendChild(button);
  }
  var themeToggle = document.getElementById('themeToggle');
  var savedTheme = 'light';
  try { savedTheme = localStorage.getItem('ma-genesis-theme') || 'light'; } catch (storageUnavailable) { }
  function applyTheme(value) {
    var theme = value === 'dark' ? 'dark' : 'light';
    document.documentElement.setAttribute('data-theme', theme);
    if (themeToggle) {
      buttonIcon(themeToggle,theme === 'dark' ? 'sun' : 'moon',theme === 'dark' ? 'Ativar tema claro' : 'Ativar tema escuro');
      themeToggle.setAttribute('aria-pressed',String(theme === 'dark'));
    }
    try { localStorage.setItem('ma-genesis-theme', theme); } catch (storageUnavailable) { }
  }
  applyTheme(savedTheme);
  var statusLines=document.querySelectorAll('.status-line');
  var totalLine=document.querySelector('.status-line.total b');
  var statusTotal=totalLine ? parseFloat(totalLine.textContent) : 0;
  for(var si=0;si<statusLines.length;si++){
    var count=statusLines[si].querySelector('b');
    var value=count ? parseFloat(count.textContent) : 0;
    statusLines[si].style.setProperty('--status-width',(statusTotal>0 ? Math.min(100,Math.max(0,value/statusTotal*100)) : 0)+'%');
  }
  if (themeToggle) themeToggle.addEventListener('click', function () {
    applyTheme(document.documentElement.getAttribute('data-theme') === 'dark' ? 'light' : 'dark');
  });
  var commonForm = document.querySelector('.unified-filter');
  if (commonForm) {
    commonForm.addEventListener('submit', function (event) {
      event.preventDefault();
      navigatePreservingContext({search:this.elements.search.value,catalogPage:null});
    });
    commonForm.querySelector('[data-clear-search]').addEventListener('click', function () {
      navigatePreservingContext({search:null,catalogPage:null});
    });
  }
  var evidenceDialog = document.getElementById('evidenceDialog');
  var evidenceContent = document.getElementById('evidenceContent');
  var details = document.querySelectorAll('.table-wrap details');
  for (var di = 0; di < details.length; di++) {
    (function (detail) {
      var summary = detail.querySelector('summary');
      if (!summary || !evidenceDialog) return;
      var trigger = document.createElement('button');
      trigger.type = 'button'; trigger.className = 'icon-button evidence-trigger';
      buttonIcon(trigger,'eye',summary.textContent.trim());
      detail.hidden = true; detail.parentNode.insertBefore(trigger, detail);
      groupAction(trigger);
      trigger.addEventListener('click', function () {
        evidenceContent.textContent = '';
        var clone = detail.cloneNode(true); clone.removeChild(clone.querySelector('summary'));
        var content = document.createElement('div');
        while (clone.firstChild) content.appendChild(clone.firstChild);
        evidenceContent.appendChild(content);
        evidenceDialog.showModal();
      });
    })(details[di]);
  }
  if (evidenceDialog) {
    document.getElementById('closeEvidence').addEventListener('click', function () { evidenceDialog.close(); });
    evidenceDialog.addEventListener('click', function (event) { if(event.target === evidenceDialog) evidenceDialog.close(); });
  }
  var bodies = document.querySelectorAll('.trace-detail pre');
  for (var bi=0; bi<bodies.length; bi++) {
    try { bodies[bi].textContent=JSON.stringify(JSON.parse(bodies[bi].textContent),null,2); }
    catch (notJson) { }
  }
  var traceForm = document.querySelector('.trace-filter');
  if (traceForm) traceForm.addEventListener('submit', function (event) {
    event.preventDefault();
    navigatePreservingContext({area:'history',historyContext:this.elements.historyContext.value,catalogPage:null});
  });
  var table = document.getElementById('queue');
  var areaLinks = document.querySelectorAll('[data-area-target]');
  for (var a = 0; a < areaLinks.length; a++) {
    areaLinks[a].addEventListener('click', function () {
      navigatePreservingContext({ area: this.getAttribute('data-area-target'), view: null,
        page: null, pageSize: null, catalogPage: null, equipment: null, code: null, document: null, customer: null, serial:null, search:null, historyContext:null });
    });
  }
  var catalogPages = document.querySelectorAll('[data-catalog-page]');
  var historyLinks = document.querySelectorAll('[data-history-context]');
  for (var hl = 0; hl < historyLinks.length; hl++) {
    historyLinks[hl].classList.add('icon-button','history-trigger');
    buttonIcon(historyLinks[hl],'history','Ver historico de requisicoes');
    groupAction(historyLinks[hl]);
    historyLinks[hl].addEventListener('click', function () {
      navigatePreservingContext({area:'history',historyContext:this.getAttribute('data-history-context'),catalogPage:null});
    });
  }
  for (var cp = 0; cp < catalogPages.length; cp++) {
    catalogPages[cp].addEventListener('click', function () {
      navigatePreservingContext({ catalogPage: this.getAttribute('data-catalog-page') });
    });
  }
  if (!table || !table.tBodies.length) return;

  var filters = {
    equipment: { element: document.getElementById('equipmentFilter'), column: 5 },
    code: { element: document.getElementById('codeFilter'), column: 4 },
    document: { element: document.getElementById('documentFilter'), column: 1 },
    customer: { element: document.getElementById('customerFilter'), column: 0 },
    serial: { element: document.getElementById('serialFilter'), column: 6 }
  };
  var clearFilters = document.getElementById('clearFilters');
  var filterForm = document.getElementById('equipmentFilters');
  var filterPageSize = document.getElementById('filterPageSize');
  var tabs = document.querySelectorAll('.subtab');
  var activeSource = document.body.getAttribute('data-view') || 'sent';
  var serverMode = activeSource === 'installed';
  var serverTotal = parseInt(table.getAttribute('data-total'), 10) || 0;

  var body = table.tBodies[0];
  var tableWrap = table.parentNode;
  var rows = [];
  for (var i = 0; i < body.rows.length; i++) {
    rows.push({ element: body.rows[i], index: i });
  }

  var state = {
    page: parseInt(document.body.getAttribute('data-page'), 10) || 1,
    pageSize: parseInt(document.body.getAttribute('data-page-size'), 10) || 25,
    sortColumn: 7,
    sortDirection: 'desc',
    filtered: rows.slice(0)
  };
  var numericColumns = { 4: true, 8: true };
  var pageSize = document.getElementById('pageSize');
  var summary = document.getElementById('paginationSummary');
  var pageIndicator = document.getElementById('pageIndicator');
  var firstPage = document.getElementById('firstPage');
  var previousPage = document.getElementById('previousPage');
  var nextPage = document.getElementById('nextPage');
  var lastPage = document.getElementById('lastPage');
  var sortButtons = table.querySelectorAll('.sort-button');
  pageSize.value = String(state.pageSize);
  enableColumnResize();
  var errorDialog = document.getElementById('errorDialog');
  var errorElements = {
    title: document.getElementById('errorDialogTitle'),
    summary: document.getElementById('errorSummary'),
    entity: document.getElementById('errorEntity'),
    stage: document.getElementById('errorStage'),
    http: document.getElementById('errorHttp'),
    classification: document.getElementById('errorClassification'),
    apiMessage: document.getElementById('errorApiMessage'),
    action: document.getElementById('errorAction'),
    technical: document.getElementById('errorTechnical')
  };

  for (var filterName in filters) {
    if (!filters.hasOwnProperty(filterName) || !filters[filterName].element) continue;
    filters[filterName].element.addEventListener('input', function () {
      if (serverMode) return;
      state.page = 1;
      applyFilter();
    });
  }
  if (clearFilters) clearFilters.addEventListener('click', function () {
    for (var name in filters) {
      if (filters.hasOwnProperty(name) && filters[name].element) filters[name].element.value = '';
    }
    if (serverMode && filterForm) {
      navigateInstalled(1);
    } else {
      state.page = 1;
      applyFilter();
    }
  });
  if (filterForm) filterForm.addEventListener('submit', function (event) {
    event.preventDefault();
    if (!serverMode) {
      state.page = 1;
      applyFilter();
      return;
    }
    if (filterPageSize) filterPageSize.value = String(state.pageSize);
    navigateInstalled(1);
  });
  for (var t = 0; t < tabs.length; t++) {
    tabs[t].addEventListener('click', function () {
      var source = this.getAttribute('data-source') || 'sent';
      if (source === activeSource) return;
      navigateView(source);
    });
  }
  pageSize.addEventListener('change', function () {
    state.pageSize = parseInt(this.value, 10) || 25;
    state.page = 1;
    if (serverMode) navigateInstalled(1);
    else render();
  });
  firstPage.addEventListener('click', function () { changePage(1); });
  previousPage.addEventListener('click', function () { changePage(state.page - 1); });
  nextPage.addEventListener('click', function () { changePage(state.page + 1); });
  lastPage.addEventListener('click', function () { changePage(totalPages()); });
  body.addEventListener('click', function (event) {
    var target = event.target || event.srcElement;
    if (!target || !hasClass(target, 'error-detail-button')) return;
    var cell = target.parentNode;
    openErrorDialog(
      childText(cell, '.error-raw'),
      childText(cell, '.error-http'),
      childText(cell, '.error-status')
    );
  });

  for (var b = 0; b < sortButtons.length; b++) {
    sortButtons[b].addEventListener('click', function () {
      var column = parseInt(this.getAttribute('data-column'), 10);
      if (state.sortColumn === column) {
        state.sortDirection = state.sortDirection === 'asc' ? 'desc' : 'asc';
      } else {
        state.sortColumn = column;
        state.sortDirection = 'asc';
      }
      state.page = 1;
      render();
    });
  }

  applyFilter();

  function applyFilter() {
    state.filtered = [];
    for (var r = 0; r < rows.length; r++) {
      if (serverMode || matchesFilters(rows[r].element)) state.filtered.push(rows[r]);
    }
    render();
  }

  function matchesFilters(row) {
    if ((row.getAttribute('data-source') || 'sent') !== activeSource) return false;
    for (var name in filters) {
      if (!filters.hasOwnProperty(name)) continue;
      var filter = filters[name];
      var term = normalize(filter.element ? filter.element.value : '');
      if (term && normalize(cellValue(row, filter.column)).indexOf(term) < 0) return false;
    }
    return true;
  }

  function render() {
    clearEmptyRow();
    var ordered = state.filtered.slice(0);
    ordered.sort(compareRows);
    var pages = totalPages();
    if (state.page > pages) state.page = pages;
    if (state.page < 1) state.page = 1;
    var start = serverMode ? (state.page - 1) * state.pageSize : (state.page - 1) * state.pageSize;
    var end = serverMode ? start + ordered.length : Math.min(start + state.pageSize, ordered.length);

    for (var i = 0; i < rows.length; i++) rows[i].element.hidden = true;
    var firstVisible = serverMode ? 0 : start;
    var lastVisible = serverMode ? ordered.length : end;
    for (var visible = firstVisible; visible < lastVisible; visible++) {
      ordered[visible].element.hidden = false;
      body.appendChild(ordered[visible].element);
    }
    if (!ordered.length) appendEmptyRow();

    var total = serverMode ? serverTotal : ordered.length;
    summary.textContent = ordered.length
      ? 'Exibindo ' + (start + 1) + '-' + end + ' de ' + total + ' registros únicos'
      : 'Nenhum registro encontrado';
    pageIndicator.textContent = 'Página ' + state.page + ' de ' + pages;
    firstPage.disabled = state.page <= 1;
    previousPage.disabled = state.page <= 1;
    nextPage.disabled = state.page >= pages;
    lastPage.disabled = state.page >= pages;
    updateSortHeaders();
  }

  function compareRows(a, b) {
    var left = cellValue(a.element, state.sortColumn);
    var right = cellValue(b.element, state.sortColumn);
    var result;
    if (numericColumns[state.sortColumn]) {
      result = numberValue(left) - numberValue(right);
    } else {
      result = normalize(left).localeCompare(normalize(right));
    }
    if (result === 0) return a.index - b.index;
    return state.sortDirection === 'asc' ? result : -result;
  }

  function cellValue(row, column) {
    if (!row.cells[column]) return '';
    return row.cells[column].getAttribute('data-sort') || row.cells[column].textContent;
  }

  function numberValue(value) {
    var cleaned = String(value || '').replace(/\s/g, '').replace(/[^0-9,.-]/g, '');
    if (cleaned.indexOf(',') >= 0 && cleaned.indexOf('.') >= 0) {
      cleaned = cleaned.lastIndexOf(',') > cleaned.lastIndexOf('.')
        ? cleaned.replace(/\./g, '').replace(',', '.')
        : cleaned.replace(/,/g, '');
    } else if (cleaned.indexOf(',') >= 0) {
      cleaned = cleaned.replace(',', '.');
    }
    var number = parseFloat(cleaned);
    return isNaN(number) ? Number.NEGATIVE_INFINITY : number;
  }

  function updateSortHeaders() {
    for (var i = 0; i < sortButtons.length; i++) {
      var button = sortButtons[i];
      var column = parseInt(button.getAttribute('data-column'), 10);
      var active = column === state.sortColumn;
      button.className = 'sort-button' + (active ? ' is-sorted ' + state.sortDirection : '');
      button.parentNode.setAttribute('aria-sort', active
        ? (state.sortDirection === 'asc' ? 'ascending' : 'descending')
        : 'none');
    }
  }

  function changePage(page) {
    var max = totalPages();
    state.page = Math.max(1, Math.min(page, max));
    if (serverMode) {
      navigateInstalled(state.page);
      return;
    }
    render();
    if (tableWrap && typeof tableWrap.scrollTo === 'function') {
      tableWrap.scrollTo(0, 0);
    } else if (tableWrap) {
      tableWrap.scrollTop = 0;
      tableWrap.scrollLeft = 0;
    }
  }

  function totalPages() {
    var total = serverMode ? serverTotal : state.filtered.length;
    return Math.max(1, Math.ceil(total / state.pageSize));
  }

  function navigateInstalled(page) {
    var parameters = {
      view: 'installed',
      page: String(page),
      pageSize: String(state.pageSize)
    };
    for (var name in filters) {
      if (!filters.hasOwnProperty(name) || !filters[name].element) continue;
      var value = filters[name].element.value;
      parameters[name] = value || null;
    }
    navigatePreservingContext(parameters);
  }

  function navigateView(source) {
    navigatePreservingContext({
      view: source,
      page: source === 'installed' ? '1' : null,
      pageSize: source === 'installed' ? '25' : null,
      equipment: null,
      code: null,
      document: null,
      customer: null,
      serial: null
    });
  }

  function navigatePreservingContext(changes) {
    var url = new URL(window.location.href);
    for (var name in changes) {
      if (!changes.hasOwnProperty(name)) continue;
      if (changes[name] === null || changes[name] === '') url.searchParams.delete(name);
      else url.searchParams.set(name, changes[name]);
    }
    window.location.href = url.toString();
  }

  function appendEmptyRow() {
    var row = document.createElement('tr');
    row.id = 'emptyQueueRow';
    var cell = document.createElement('td');
    cell.colSpan = 12;
    cell.className = 'empty-state';
    cell.textContent = 'Nenhum movimento encontrado para os filtros informados.';
    row.appendChild(cell);
    body.appendChild(row);
  }

  function clearEmptyRow() {
    var row = document.getElementById('emptyQueueRow');
    if (row && row.parentNode) row.parentNode.removeChild(row);
  }

  function enableColumnResize() {
    var headers = table.tHead ? table.tHead.rows[0].cells : [];
    if (!headers.length) return;
    var group = document.createElement('colgroup');
    var columns = [];
    var total = 0;
    for (var i = 0; i < headers.length; i++) {
      var column = document.createElement('col');
      var width = Math.max(72, Math.round(headers[i].getBoundingClientRect().width));
      column.style.width = width + 'px';
      columns.push(column);
      group.appendChild(column);
      total += width;
    }
    table.insertBefore(group, table.firstChild);
    table.style.width = Math.max(total, tableWrap.clientWidth) + 'px';

    for (var index = 0; index < headers.length; index++) addResizeHandle(headers[index], columns[index]);
  }

  function addResizeHandle(header, column) {
    var handle = document.createElement('span');
    handle.className = 'column-resizer';
    handle.setAttribute('aria-hidden', 'true');
    header.appendChild(handle);
    handle.addEventListener('mousedown', function (event) {
      event.preventDefault();
      event.stopPropagation();
      var startX = event.clientX;
      var startWidth = parseInt(column.style.width, 10) || header.offsetWidth;
      document.body.className += ' is-resizing-column';

      function move(moveEvent) {
        column.style.width = Math.max(72, startWidth + moveEvent.clientX - startX) + 'px';
        updateTableWidth();
      }
      function stop() {
        document.removeEventListener('mousemove', move);
        document.removeEventListener('mouseup', stop);
        document.body.className = document.body.className.replace(/\s*is-resizing-column/g, '');
      }
      document.addEventListener('mousemove', move);
      document.addEventListener('mouseup', stop);
    });
  }

  function updateTableWidth() {
    var columns = table.getElementsByTagName('col');
    var total = 0;
    for (var i = 0; i < columns.length; i++) total += parseInt(columns[i].style.width, 10) || 0;
    table.style.width = Math.max(total, tableWrap.clientWidth) + 'px';
  }

  function normalize(value) {
    var text = String(value || '').toLowerCase();
    if (typeof text.normalize === 'function') {
      text = text.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
    }
    return text.trim();
  }

  function openErrorDialog(raw, httpValue, statusValue) {
    var diagnosis = diagnoseError(raw, httpValue, statusValue);
    errorElements.title.textContent = diagnosis.title;
    errorElements.summary.textContent = diagnosis.summary;
    errorElements.entity.textContent = diagnosis.entity;
    errorElements.stage.textContent = diagnosis.stage;
    errorElements.http.textContent = diagnosis.http;
    errorElements.classification.textContent = diagnosis.classification;
    errorElements.apiMessage.textContent = diagnosis.apiMessage;
    errorElements.action.textContent = diagnosis.action;
    errorElements.technical.textContent = diagnosis.technical;
    if (errorDialog && typeof errorDialog.showModal === 'function') {
      errorDialog.showModal();
    } else {
      window.alert(diagnosis.title + '\n\n' + diagnosis.summary + '\n\nO que verificar: ' + diagnosis.action);
    }
  }

  function diagnoseError(rawValue, httpValue, statusValue) {
    var raw = String(rawValue || '').trim();
    var specific = window.GenesisDiagnosis && window.GenesisDiagnosis.diagnose({message: raw, http: httpValue, status: statusValue});
    if (specific) return {
      title: specific.title, summary: specific.summary, entity: 'Conferir detalhe e vinculos',
      stage: specific.stage, http: httpValue || 'Nao informado nesta ocorrencia',
      classification: specific.classification, apiMessage: specific.evidence,
      action: specific.action + ' Responsavel: ' + specific.owner, technical: raw
    };
    var httpMatch = raw.match(/HTTP\s+(\d{3})/i);
    var http = parseInt(httpValue || (httpMatch ? httpMatch[1] : ''), 10);
    var entityMatch = raw.match(/Falha\s+em\s+([A-Z_]+)/i);
    var stageMatch = raw.match(/(?:na\s+etapa\s+|durante\s+)(GET|POST|PUT|DELETE|PATCH)/i);
    var classificationMatch = raw.match(/HTTP\s+\d{3}\s*-\s*([A-Z_]+)/i);
    var entityCode = entityMatch ? entityMatch[1].toUpperCase() : '';
    var stageCode = stageMatch ? stageMatch[1].toUpperCase() : '';
    var classificationCode = classificationMatch ? classificationMatch[1].toUpperCase() : String(statusValue || '').toUpperCase();
    var apiMessage = extractApiMessages(raw);
    var searchable = normalize(raw + ' ' + apiMessage);
    var httpInfo = httpDiagnosis(http);
    var action = httpInfo.action;

    if (searchable.indexOf('oracle.sql.clob@') >= 0) {
      apiMessage = 'O detalhe do banco não foi convertido para texto.';
      action = 'Atualize o dashboard e o JAR e execute novamente. A mensagem foi armazenada como CLOB, mas não foi lida corretamente.';
    } else if (containsAll(searchable, ['cnpj', 'obrigatorio'])) {
      action = entityCode === 'FABRICANTE'
        ? 'Informe o CNPJ da empresa fabricante na origem ou utilize o ID Genesis já conciliado, evitando tentar criar um fabricante incompleto.'
        : 'Preencha e valide o CNPJ/CPF do cadastro relacionado antes de reenviar.';
    } else if (searchable.indexOf('ja existe') >= 0 || searchable.indexOf('duplic') >= 0) {
      action = 'O cadastro provavelmente já existe na Genesis. Concilie o ID existente e faça atualização por PUT, em vez de uma nova inclusão por POST.';
    } else if (searchable.indexOf('codigoexterno') >= 0 || searchable.indexOf('codigo externo') >= 0) {
      action = 'Revise o código externo e a conciliação da Base Instalada. Ele deve ser único e apontar para o mesmo cadastro nos dois sistemas.';
    } else if (searchable.indexOf('serie') >= 0 && searchable.indexOf('obrigatorio') >= 0) {
      action = 'Informe uma série válida no item. Movimentos sem série não devem seguir para a integração de itens-materiais.';
    } else if (searchable.indexOf('idcliente') >= 0 || searchable.indexOf('cliente nao') >= 0) {
      action = 'Confira a conciliação do cliente na AD_GENCLIENTE e o vínculo com o CODPARC do Sankhya.';
    } else if (searchable.indexOf('idsetor') >= 0 || searchable.indexOf('setor nao') >= 0) {
      action = 'Confira o setor conciliado. Quando houver mais de um setor para o cliente, mantenha somente um como PADRAO=S.';
    } else if (searchable.indexOf('idmodelo') >= 0 || searchable.indexOf('modelo nao') >= 0) {
      action = 'Confira TGFPRO.HSP_IDEXTERNO e a correspondência conciliada em AD_GENMODELO.CODIGOEXTERNO.';
    } else if (searchable.indexOf('sem id genesis') >= 0 || searchable.indexOf('sucesso sem id') >= 0) {
      action = 'A API respondeu sem o identificador necessário. Confirme o contrato da resposta e não marque o item como enviado até recuperar o ID Genesis.';
    }

    return {
      title: httpInfo.title,
      summary: httpInfo.summary,
      entity: entityLabel(entityCode),
      stage: stageLabel(stageCode),
      http: isNaN(http) ? 'Não informado' : String(http),
      classification: classificationLabel(classificationCode, http),
      apiMessage: apiMessage || 'A API não devolveu uma mensagem funcional legível.',
      action: action,
      technical: raw || 'Nenhum detalhe técnico foi armazenado para esta ocorrência.'
    };
  }

  function httpDiagnosis(http) {
    if (http === 400) return { title: 'Dados recusados pela Genesis', summary: 'A requisição chegou à Genesis, mas algum campo ou regra de negócio foi rejeitado.', action: 'Revise a mensagem da Genesis e os campos obrigatórios do cadastro antes de reenviar.' };
    if (http === 401) return { title: 'Credencial não autorizada', summary: 'A Genesis não reconheceu ou não aceitou a credencial enviada.', action: 'Confirme o token da Central de Parâmetros, o ambiente e o cabeçalho token. Não exponha a credencial em logs.' };
    if (http === 403) return { title: 'Acesso não permitido', summary: 'A credencial foi reconhecida, mas não possui permissão para esta operação.', action: 'Solicite à Genesis a liberação do endpoint e da operação para a credencial de homologação ou produção.' };
    if (http === 404) return { title: 'Recurso não localizado', summary: 'O endpoint ou o registro informado não foi encontrado na Genesis.', action: 'Revise a URL, o ID Genesis e o código externo. Para item novo, confirme se o fluxo deve seguir para POST.' };
    if (http === 408 || http === 504) return { title: 'Tempo de resposta excedido', summary: 'A Genesis não respondeu dentro do tempo configurado.', action: 'Aguarde e reprocesse. Antes, consulte pelo código externo para evitar inclusão duplicada.' };
    if (http === 409) return { title: 'Conflito com cadastro existente', summary: 'A operação conflita com um registro que já existe ou foi alterado.', action: 'Concilie o ID existente e repita como atualização, preservando o mesmo código externo.' };
    if (http === 422) return { title: 'Dados não puderam ser processados', summary: 'O formato foi aceito, mas os dados não atendem às validações da Genesis.', action: 'Revise campos obrigatórios, IDs relacionados, formatos de data, série e valores.' };
    if (http === 429) return { title: 'Limite de requisições atingido', summary: 'A Genesis bloqueou temporariamente o volume de chamadas.', action: 'Reduza o lote ou a frequência do agendador e reprocesse após o intervalo indicado pela API.' };
    if (http >= 500 && http <= 599) return { title: 'Falha de servico ou comunicacao', summary: 'Foi registrada uma falha temporaria. O codigo pode representar resposta do servidor ou falha de transporte; nao comprova recebimento pela Genesis.', action: 'Confira a evidencia de transporte. Para POST sem confirmacao, consulte pelo codigo externo antes de autorizar outra inclusao.' };
    if (isNaN(http)) return { title: 'Falha antes de receber resposta da API', summary: 'Não foi registrado um código HTTP para esta ocorrência.', action: 'Verifique conexão, DNS, certificado, timeout, conciliação da Base Instalada e logs da ação agendada.' };
    return { title: 'Não foi possível concluir o envio', summary: 'A integração recebeu uma resposta que precisa de análise.', action: 'Consulte a mensagem funcional e o detalhe técnico antes de reprocessar.' };
  }

  function extractApiMessages(raw) {
    var messages = [];
    var pattern = /"userMessage"\s*:\s*"((?:\\.|[^"\\])*)"/gi;
    var match;
    while ((match = pattern.exec(raw)) !== null) addUnique(messages, decodeJsonText(match[1]));
    if (!messages.length) {
      pattern = /"message"\s*:\s*"((?:\\.|[^"\\])*)"/gi;
      while ((match = pattern.exec(raw)) !== null) addUnique(messages, decodeJsonText(match[1]));
    }
    return messages.join(' | ');
  }

  function decodeJsonText(value) {
    try { return JSON.parse('"' + value + '"').trim(); }
    catch (ignore) { return String(value || '').replace(/\\"/g, '"').replace(/\\n/g, ' ').trim(); }
  }

  function addUnique(values, value) {
    if (value && values.indexOf(value) < 0) values.push(value);
  }

  function entityLabel(code) {
    var labels = { CLIENTE: 'Cliente', SETOR: 'Setor', MATERIAL: 'Material', FABRICANTE: 'Fabricante', MODELO: 'Modelo', ITEM_MATERIAL: 'Item-material' };
    return labels[code] || (code ? code.replace(/_/g, ' ') : 'Não identificada');
  }

  function stageLabel(code) {
    var labels = { GET: 'Consulta (GET)', POST: 'Inclusão (POST)', PUT: 'Atualização (PUT)', DELETE: 'Exclusão (DELETE)', PATCH: 'Atualização parcial (PATCH)' };
    return labels[code] || 'Não identificada';
  }

  function classificationLabel(code, http) {
    var labels = { ERRO_FUNCIONAL: 'Validação funcional', AUTENTICACAO: 'Autenticação', TRANSITORIO: 'Falha temporária', NAO_ENCONTRADO: 'Não localizado', SUCESSO: 'Sucesso' };
    if (labels[code]) return labels[code];
    if (http >= 500 || http === 408 || http === 429) return 'Falha temporária';
    if (http >= 400 && http < 500) return 'Validação da requisição';
    return code ? code.replace(/_/g, ' ') : 'Não classificada';
  }

  function childText(parent, selector) {
    var child = parent ? parent.querySelector(selector) : null;
    return child ? child.textContent : '';
  }

  function hasClass(element, className) {
    return (' ' + element.className + ' ').indexOf(' ' + className + ' ') >= 0;
  }

  function containsAll(value, terms) {
    for (var i = 0; i < terms.length; i++) if (value.indexOf(terms[i]) < 0) return false;
    return true;
  }
})();
