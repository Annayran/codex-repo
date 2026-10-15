const telecontrolConfig = [
      {
        id: 'produtos',
        table: 'AD_TCLPRO',
        title: 'Produtos Telecontrol',
        type: 'principal',
        icon: 'fa-box-open',
        key: 'CODPROD',
        descriptionField: 'DESCRICAO',
        displayFields: ['CODPROD', 'REFERENCIA', 'DESCRICAO', 'CODFAMILIA', 'CODLINHA', 'ATIVO', 'DHINTEG'],
        fields: [
          ['CODPROD', 'Produto', 'I', 'P'],
          ['REFERENCIA', 'Ref. Telecontrol', 'S', 'P'],
          ['DESCRICAO', 'DescriÃ§Ã£o', 'S', 'P'],
          ['CODFAMILIA', 'CÃ³digo FamÃ­lia', 'S', 'P'],
          ['CODLINHA', 'Linha', 'S', 'P'],
          ['GARANTIA', 'Garantia', 'I', 'P'],
          ['MAODEOBRA', 'MÃ£o de Obra', 'F', 'P'],
          ['MAODEOBRAADMIN', 'MÃ£o de Obra Adm', 'F', 'P'],
          ['NUMEROSERIEOBRIGATORIO', 'Nro de SÃ©rie ObrigatÃ³rio?', 'S', 'O S/N'],
          ['COD_TELECONTROL', 'CÃ³d. Telecontrol', 'I', 'P'],
          ['DHINTEG', 'Dh. IntegraÃ§Ã£o', 'H', 'P'],
          ['DTALTER', 'Dh. AlteraÃ§Ã£o', 'H', 'P'],
          ['VOLTAGEM', 'Voltagem', 'S', 'P'],
          ['ATIVO', 'Ativo no Telecontrol', 'S', 'O S/N'],
          ['UNID_NEGOCIO', 'Unid. NegÃ³cio', 'S', 'P'],
          ['MARCA', 'Marca', 'S', 'P']
        ],
        actions: [
          { code: 103, name: 'Envio Produto', type: 'RJ' },
          { code: 138, name: '[ZAYA.IT] - Sincronizar Produtos', type: 'RJ' }
        ],
        relations: [
          { table: 'AD_TCLLTB', title: 'Lista BÃ¡sica Telecontrol', kind: 'detalhe', parentField: 'CODPROD', childField: 'CODPROD' },
          { table: 'AD_TCLLIN', title: 'Linha Telecontrol', kind: 'apoio', parentField: 'CODLINHA', childField: 'CODLINHA' },
          { table: 'AD_TCLFAM', title: 'FamÃ­lia Telecontrol', kind: 'apoio', parentField: 'CODFAMILIA', childField: 'CODFAMILIA' }
        ]
      },
      {
        id: 'lista-basica',
        table: 'AD_TCLLTB',
        title: 'Lista BÃ¡sica Telecontrol',
        type: 'detalhe',
        icon: 'fa-list-check',
        key: 'LISTABASICA',
        parent: 'AD_TCLPRO',
        parentField: 'CODPROD',
        descriptionField: 'REFPRODUTO',
        displayFields: ['LISTABASICA', 'CODPROD', 'CODPECA', 'QTD', 'POSICAO', 'DHINTEG'],
        fields: [
          ['CODPECA', 'CÃ³d. PeÃ§a', 'I', 'P'],
          ['CODPROD', 'CÃ³digo', 'I', 'P'],
          ['QTD', 'Quantidade', 'F', 'P'],
          ['POSICAO', 'PosiÃ§Ã£o', 'S', 'P'],
          ['DHALTER', 'Data AlteraÃ§Ã£o', 'H', 'P'],
          ['DHINTEG', 'Data IntegraÃ§Ã£o', 'H', 'P'],
          ['REFPRODUTO', 'ReferÃªncia Produto', 'S', 'P'],
          ['REFPECA', 'ReferÃªncia PeÃ§a', 'S', 'P'],
          ['LISTABASICA', 'Lista BÃ¡sica', 'I', 'P']
        ],
        actions: [
          { code: 102, name: 'Envia Lista BÃ¡sica', type: 'RJ' },
          { code: 140, name: '[ZAYA.IT] - Sincroniza Lista BÃ¡sica', type: 'RJ' }
        ],
        relations: [
          { table: 'AD_TCLPRO', title: 'Produto pai', kind: 'pai', parentField: 'CODPROD', childField: 'CODPROD' },
          { table: 'AD_TCLPCA', title: 'PeÃ§a vinculada', kind: 'referÃªncia', parentField: 'CODPECA', childField: 'CODPROD' }
        ]
      },
      {
        id: 'pecas',
        table: 'AD_TCLPCA',
        title: 'PeÃ§as Telecontrol',
        type: 'principal',
        icon: 'fa-screwdriver-wrench',
        key: 'CODPROD',
        descriptionField: 'DESCRICAO',
        displayFields: ['CODPROD', 'REFERENCIA', 'DESCRICAO', 'ATIVO', 'ESTOQUE', 'COD_TELECONTROL'],
        fields: [
          ['CODPROD', 'PeÃ§a', 'I', 'P'],
          ['REFERENCIA', 'ReferÃªncia', 'S', 'P'],
          ['DESCRICAO', 'DescriÃ§Ã£o', 'S', 'P'],
          ['IPI', 'Ipi', 'I', 'P'],
          ['MULTIPLO', 'MÃºltiplo', 'I', 'P'],
          ['DEVOLUCAOOBRIGATORIA', 'DevoluÃ§Ã£o ObrigatÃ³ria?', 'S', 'O S/N'],
          ['GARANTIADIFERENCIADA', 'Garantia Diferenciada', 'I', 'P'],
          ['NUMEROSERIEPECA', 'Nro SÃ©rie da PeÃ§a?', 'S', 'O S/N'],
          ['ACESSORIO', 'AcessÃ³rio?', 'S', 'O S/N'],
          ['ITEMAPARENCIA', 'Item AparÃªncia?', 'S', 'O S/N'],
          ['ORIGEM', 'Origem', 'S', 'O S/N'],
          ['ATIVO', 'Ativo', 'S', 'O S/N'],
          ['BLOQUEADAVENDA', 'Bloquear Venda?', 'S', 'O S/N'],
          ['BLOQUEADAGARANTIA', 'Bloquear Garantia?', 'S', 'O S/N'],
          ['ESTOQUE', 'Estoque', 'F', 'P'],
          ['UNIDADE', 'Unid. Medida', 'S', 'P'],
          ['DHINTEG', 'Dh. IntegraÃ§Ã£o', 'H', 'P'],
          ['DTALTER', 'Dh. AlteraÃ§Ã£o', 'H', 'P'],
          ['CLASSIFICACAOFISCAL', 'Class. Fiscal', 'S', 'P'],
          ['COD_TELECONTROL', 'CÃ³digo Telecontrol', 'I', 'P'],
          ['PRECOVEN', 'PreÃ§o Venda', 'F', 'P'],
          ['PRECOGAR', 'PreÃ§o Garantia', 'F', 'P']
        ],
        actions: [
          { code: 105, name: 'Envio PeÃ§as', type: 'RJ' },
          { code: 137, name: '[ZAYA.IT] - Atualiza PeÃ§as', type: 'RJ' }
        ],
        relations: [
          { table: 'AD_TCLLTB', title: 'Uso em Lista BÃ¡sica', kind: 'referÃªncia', parentField: 'CODPROD', childField: 'CODPECA' }
        ]
      },
      {
        id: 'postos',
        table: 'AD_TCLPST',
        title: 'Postos Telecontrol',
        type: 'principal',
        icon: 'fa-store',
        key: 'CODPARC',
        descriptionField: 'CREDENCIAMENTO',
        displayFields: ['CODPARC', 'POSTOID', 'CREDENCIAMENTO', 'TIPOPOSTO', 'PEDFATURADO', 'PEDGARANTIA'],
        fields: [
          ['CODPARC', 'Posto', 'I', 'P'],
          ['PEDFATURADO', 'Pedido Faturado?', 'S', 'O S/N'],
          ['PEDGARANTIA', 'Pedido Garantia?', 'S', 'O S/N'],
          ['CREDENCIAMENTO', 'Credenciamento', 'S', 'P'],
          ['DESCONTO', 'Desconto', 'F', 'P'],
          ['VALORKM', 'Valor KM', 'F', 'P'],
          ['POSTOID', 'Posto Id Telecontrol', 'I', 'P'],
          ['DTALTER', 'Dh AlteraÃ§Ã£o', 'H', 'P'],
          ['DHINTEG', 'Dh. IntegraÃ§Ã£o', 'H', 'P'],
          ['TIPOPOSTO', 'Tipo Posto', 'S', 'O S/N']
        ],
        actions: [
          { code: 108, name: 'Envio Posto', type: 'RJ' }
        ],
        relations: []
      },
      {
        id: 'linha',
        table: 'AD_TCLLIN',
        title: 'Linha Telecontrol',
        type: 'apoio',
        icon: 'fa-layer-group',
        key: 'CODLINHA',
        descriptionField: 'NOME',
        displayFields: ['CODLINHA', 'NOME', 'ATIVO', 'FABRICA', 'DHINTEG'],
        fields: [
          ['CODLINHA', 'Linha', 'S', 'P'],
          ['NOME', 'Nome', 'S', 'P'],
          ['ATIVO', 'Ativo', 'S', 'O S/N'],
          ['FABRICA', 'FÃ¡brica', 'I', 'P'],
          ['DHINTEG', 'Data IntegraÃ§Ã£o', 'H', 'P'],
          ['DHALT', 'Data AlteraÃ§Ã£o', 'H', 'P'],
          ['LINHA', 'Linha', 'I', 'P']
        ],
        actions: [],
        relations: [
          { table: 'AD_TCLPRO', title: 'Produtos desta linha', kind: 'referÃªncia', parentField: 'CODLINHA', childField: 'CODLINHA' }
        ]
      },
      {
        id: 'familia',
        table: 'AD_TCLFAM',
        title: 'FamÃ­lia Telecontrol',
        type: 'apoio',
        icon: 'fa-sitemap',
        key: 'CODFAMILIA',
        descriptionField: 'DESCRICAO',
        displayFields: ['CODFAMILIA', 'DESCRICAO', 'FAMILIA', 'DHINTEG'],
        fields: [
          ['CODFAMILIA', 'CÃ³digo FamÃ­lia', 'S', 'P'],
          ['DESCRICAO', 'DescriÃ§Ã£o', 'S', 'P'],
          ['DHINTEG', 'Data IntegraÃ§Ã£o', 'H', 'P'],
          ['DHALT', 'Data AlteraÃ§Ã£o', 'H', 'P'],
          ['FAMILIA', 'FamÃ­lia (ID)', 'I', 'P']
        ],
        actions: [],
        relations: [
          { table: 'AD_TCLPRO', title: 'Produtos desta famÃ­lia', kind: 'referÃªncia', parentField: 'CODFAMILIA', childField: 'CODFAMILIA' }
        ]
      }
    ];

    const mockRecords = window.SANKHYA_DATA || {
      AD_TCLPRO: [
        { CODPROD: 1001, REFERENCIA: 'XB-2', DESCRICAO: 'Caixa de Som Boombox XB-2', CODFAMILIA: 'AUDIO', CODLINHA: 'BOOMBOX', ATIVO: 'S', DHINTEG: '12/06/2026 09:12', COD_TELECONTROL: 8801, MARCA: 'POLYVOX' },
        { CODPROD: 1002, REFERENCIA: 'XT-665T', DESCRICAO: 'Torre de Som XT-665T', CODFAMILIA: 'AUDIO', CODLINHA: 'TORRES', ATIVO: 'S', DHINTEG: '12/06/2026 09:18', COD_TELECONTROL: 8802, MARCA: 'POLYVOX' },
        { CODPROD: 1003, REFERENCIA: 'XTD-67', DESCRICAO: 'Toca Disco Belt Drive', CODFAMILIA: 'VINIL', CODLINHA: 'TOCADISCO', ATIVO: 'N', DHINTEG: '-', COD_TELECONTROL: '-', MARCA: 'POLYVOX' }
      ],
      AD_TCLLTB: [
        { LISTABASICA: 501, CODPROD: 1001, CODPECA: 7001, QTD: 2, POSICAO: 'Lateral', REFPRODUTO: 'XB-2', REFPECA: 'ALTO-04', DHINTEG: '12/06/2026 09:31' },
        { LISTABASICA: 502, CODPROD: 1002, CODPECA: 7002, QTD: 1, POSICAO: 'Painel', REFPRODUTO: 'XT-665T', REFPECA: 'LED-12', DHINTEG: '12/06/2026 09:35' }
      ],
      AD_TCLPCA: [
        { CODPROD: 7001, REFERENCIA: 'ALTO-04', DESCRICAO: 'Alto-falante 4 polegadas', ATIVO: 'S', ESTOQUE: 128, COD_TELECONTROL: 9901, UNIDADE: 'UN', PRECOVEN: 89.9 },
        { CODPROD: 7002, REFERENCIA: 'LED-12', DESCRICAO: 'Conjunto LED frontal', ATIVO: 'S', ESTOQUE: 44, COD_TELECONTROL: 9902, UNIDADE: 'UN', PRECOVEN: 59.9 },
        { CODPROD: 7003, REFERENCIA: 'CAB-P2', DESCRICAO: 'Cabo auxiliar P2', ATIVO: 'N', ESTOQUE: 0, COD_TELECONTROL: '-', UNIDADE: 'UN', PRECOVEN: 19.9 }
      ],
      AD_TCLPST: [
        { CODPARC: 30001, POSTOID: 4501, CREDENCIAMENTO: 'Autorizada SP Centro', TIPOPOSTO: 'A', PEDFATURADO: 'S', PEDGARANTIA: 'S', DHINTEG: '12/06/2026 08:20' },
        { CODPARC: 30002, POSTOID: 4502, CREDENCIAMENTO: 'Autorizada RJ Norte', TIPOPOSTO: 'B', PEDFATURADO: 'N', PEDGARANTIA: 'S', DHINTEG: '11/06/2026 18:11' }
      ],
      AD_TCLLIN: [
        { CODLINHA: 'BOOMBOX', NOME: 'Boombox', ATIVO: 'S', FABRICA: 1, DHINTEG: '10/06/2026 14:10' },
        { CODLINHA: 'TORRES', NOME: 'Torres', ATIVO: 'S', FABRICA: 1, DHINTEG: '10/06/2026 14:12' }
      ],
      AD_TCLFAM: [
        { CODFAMILIA: 'AUDIO', DESCRICAO: 'Ãudio', FAMILIA: 10, DHINTEG: '10/06/2026 13:44' },
        { CODFAMILIA: 'VINIL', DESCRICAO: 'Vinil', FAMILIA: 11, DHINTEG: '10/06/2026 13:45' }
      ]
    };

    let currentScreen = telecontrolConfig[0];
    let selectedRecord = null;
    let activeTab = 'campos';

    function init() {
      renderModules();
      renderStats();
      selectScreen('produtos');
    }

    function renderModules() {
      const list = document.getElementById('moduleList');
      list.innerHTML = telecontrolConfig.map(screen => `
        <button class="module-btn" data-id="${screen.id}" onclick="selectScreen('${screen.id}')">
          <span class="module-icon"><i class="fa-solid ${screen.icon}"></i></span>
          <span>
            <span class="module-name">${screen.title}</span>
            <span class="module-table">${screen.table}</span>
          </span>
          <span class="module-count">${screen.actions.length}</span>
        </button>
      `).join('');
    }

    function renderStats() {
      const main = telecontrolConfig.filter(s => s.type === 'principal').length;
      const detail = telecontrolConfig.filter(s => s.type === 'detalhe').length;
      const support = telecontrolConfig.filter(s => s.type === 'apoio').length;
      const actions = telecontrolConfig.reduce((sum, s) => sum + s.actions.length, 0);
      const relations = telecontrolConfig.reduce((sum, s) => sum + s.relations.length, 0);

      document.getElementById('statMain').textContent = main;
      document.getElementById('statDetail').textContent = detail;
      document.getElementById('statSupport').textContent = support;
      document.getElementById('statActions').textContent = actions;
      document.getElementById('heroTables').textContent = telecontrolConfig.length;
      document.getElementById('heroActions').textContent = actions;
      document.getElementById('heroRelations').textContent = relations;
    }

    function selectScreen(id) {
      currentScreen = telecontrolConfig.find(screen => screen.id === id) || telecontrolConfig[0];
      selectedRecord = null;
      activeTab = 'campos';

      document.querySelectorAll('.module-btn').forEach(btn => {
        btn.classList.toggle('active', btn.dataset.id === currentScreen.id);
      });

      document.getElementById('screenTitle').textContent = currentScreen.title;
      document.getElementById('screenTable').textContent = currentScreen.table;
      document.getElementById('recordSearch').value = '';
      document.getElementById('statusFilter').value = '';

      renderRecords();
      renderDetail();
    }

    function getVisibleRecords() {
      const search = document.getElementById('recordSearch').value.trim().toLowerCase();
      const status = document.getElementById('statusFilter').value;
      const records = mockRecords[currentScreen.table] || [];

      return records.filter(record => {
        const matchesSearch = !search || Object.values(record).some(value => String(value).toLowerCase().includes(search));
        const matchesStatus = !status || record.ATIVO === status;
        return matchesSearch && matchesStatus;
      });
    }

    function renderRecords() {
      const head = document.getElementById('recordsHead');
      const body = document.getElementById('recordsBody');
      const fields = currentScreen.displayFields;
      const records = getVisibleRecords();

      head.innerHTML = `<tr>${fields.map(field => `<th>${field}</th>`).join('')}<th>AÃ§Ãµes</th></tr>`;

      if (!records.length) {
        body.innerHTML = `<tr><td colspan="${fields.length + 1}">Nenhum registro encontrado para ${currentScreen.table}.</td></tr>`;
        return;
      }

      body.innerHTML = records.map((record, index) => `
        <tr class="${selectedRecord === record ? 'selected' : ''}" onclick="selectRecord(${index})">
          ${fields.map(field => `<td>${formatValue(field, record[field])}</td>`).join('')}
          <td>
            <button class="btn btn-ghost" onclick="event.stopPropagation(); selectRecord(${index}); activeTab='acoes'; renderDetail();">
              <i class="fa-solid fa-play"></i> AÃ§Ãµes
            </button>
          </td>
        </tr>
      `).join('');
    }

    function selectRecord(index) {
      selectedRecord = getVisibleRecords()[index];
      activeTab = 'campos';
      renderRecords();
      renderDetail();
    }

    function renderDetail() {
      const panel = document.getElementById('detailPanel');
      if (!selectedRecord) {
        panel.innerHTML = `
          <div class="detail-empty">
            <div>
              <i class="fa-solid fa-arrow-pointer" style="font-size: 28px; margin-bottom: 12px;"></i>
              <p>Selecione um registro para ver campos, detalhes e aÃ§Ãµes disponÃ­veis.</p>
            </div>
          </div>
        `;
        return;
      }

      const keyValue = selectedRecord[currentScreen.key] || '-';
      const description = selectedRecord[currentScreen.descriptionField] || currentScreen.title;

      panel.innerHTML = `
        <div class="record-head">
          <span>${currentScreen.table} / ${currentScreen.key}: ${keyValue}</span>
          <strong>${description}</strong>
        </div>
        <div class="tabs">
          <button class="tab ${activeTab === 'campos' ? 'active' : ''}" onclick="setTab('campos')">Campos</button>
          <button class="tab ${activeTab === 'relacoes' ? 'active' : ''}" onclick="setTab('relacoes')">RelaÃ§Ãµes</button>
          <button class="tab ${activeTab === 'acoes' ? 'active' : ''}" onclick="setTab('acoes')">AÃ§Ãµes</button>
        </div>
        <div class="detail-body">${renderTabContent()}</div>
      `;
    }

    function setTab(tab) {
      activeTab = tab;
      renderDetail();
    }

    function renderTabContent() {
      if (activeTab === 'relacoes') return renderRelations();
      if (activeTab === 'acoes') return renderActions();
      return renderFields();
    }

    function renderFields() {
      return `
        <div class="field-grid">
          ${currentScreen.fields.map(([field, label, type, mode]) => `
            <div class="field">
              <span>${label} <b class="code">${field}</b></span>
              <strong>${formatValue(field, selectedRecord[field] ?? '-')}</strong>
              <span>Tipo ${type} | ${mode}</span>
            </div>
          `).join('')}
        </div>
      `;
    }

    function renderRelations() {
      if (!currentScreen.relations.length) {
        return '<p style="color: var(--muted);">Nenhuma relaÃ§Ã£o pai/detalhe mapeada para esta tela ainda.</p>';
      }

      return `
        <div class="relation-list">
          ${currentScreen.relations.map(relation => `
            <div class="relation-card">
              <strong>${relation.title}</strong>
              <span>${relation.table} | ${relation.kind} | ${relation.parentField} -> ${relation.childField}</span>
              <div style="margin-top: 10px;">
                <button class="btn btn-ghost" onclick="openRelated('${relation.table}')"><i class="fa-solid fa-arrow-up-right-from-square"></i> Abrir relaÃ§Ã£o</button>
              </div>
            </div>
          `).join('')}
        </div>
      `;
    }

    function renderActions() {
      if (!currentScreen.actions.length) {
        return '<p style="color: var(--muted);">Nenhuma aÃ§Ã£o mapeada para esta tela ainda.</p>';
      }

      return `
        <div class="action-list">
          ${currentScreen.actions.map(action => `
            <div class="action-card">
              <div>
                <strong>${action.name}</strong>
                <span>AÃ§Ã£o ${action.code} | Tipo ${action.type} | Registro ${selectedRecord[currentScreen.key] || '-'}</span>
              </div>
              <button class="btn btn-primary" onclick="executeAction(${action.code}, '${action.name.replace(/'/g, "\\'")}')">
                <i class="fa-solid fa-play"></i> Executar
              </button>
            </div>
          `).join('')}
        </div>
      `;
    }

    function openRelated(table) {
      const target = telecontrolConfig.find(screen => screen.table === table);
      if (!target) {
        showToast(`RelaÃ§Ã£o ${table} ainda nÃ£o foi adicionada ao telecontrolConfig.`);
        return;
      }
      selectScreen(target.id);
      showToast(`Abrindo ${target.title}. Em produÃ§Ã£o, aplicar filtro pelo registro pai selecionado.`);
    }

    function executeAction(code, name) {
      const keyValue = selectedRecord ? selectedRecord[currentScreen.key] : '-';
      showToast(`Preparado para executar aÃ§Ã£o ${code} - ${name} em ${currentScreen.table}, chave ${keyValue}.`);
    }

    function applyGlobalSearch() {
      const term = document.getElementById('globalSearch').value.trim().toLowerCase();
      const buttons = document.querySelectorAll('.module-btn');

      telecontrolConfig.forEach((screen, index) => {
        const searchable = [
          screen.table,
          screen.title,
          screen.type,
          ...screen.actions.map(action => `${action.code} ${action.name}`)
        ].join(' ').toLowerCase();
        buttons[index].style.display = !term || searchable.includes(term) ? '' : 'none';
      });
    }

    function showMatrix() {
      const lines = telecontrolConfig.map(screen => {
        const parent = screen.parent || '-';
        const actions = screen.actions.map(action => action.code).join(', ') || '-';
        return `${screen.table}: ${screen.type} | Pai: ${parent} | AÃ§Ãµes: ${actions}`;
      });
      showToast(lines.join('<br>'));
    }

    function formatValue(field, value) {
      if (value === undefined || value === null || value === '') return '-';
      if (field === 'ATIVO' || field.startsWith('PED') || String(value) === 'S' || String(value) === 'N') {
        if (String(value) === 'S') return '<span class="status ok"><i class="fa-solid fa-check"></i> Sim</span>';
        if (String(value) === 'N') return '<span class="status off"><i class="fa-solid fa-minus"></i> NÃ£o</span>';
      }
      if (field.includes('DH') && value === '-') {
        return '<span class="status warn"><i class="fa-solid fa-clock"></i> Pendente</span>';
      }
      if (/^COD|ID$|LISTABASICA/.test(field)) {
        return `<span class="code">${value}</span>`;
      }
      return String(value);
    }

    function showToast(message) {
      const toast = document.getElementById('toast');
      toast.innerHTML = message;
      toast.classList.add('active');
      clearTimeout(window.toastTimer);
      window.toastTimer = setTimeout(() => toast.classList.remove('active'), 5200);
    }

    init();
