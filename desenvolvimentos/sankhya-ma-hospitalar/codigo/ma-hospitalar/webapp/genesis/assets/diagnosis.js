(function (root) {
  'use strict';
  function diagnose(record) {
    var r = record || {};
    var text = String(r.message || r.detail || '').toLowerCase();
    if (text.normalize) text = text.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
    var http = Number(r.http), method = String(r.method || '').toUpperCase();
    var response = String(r.response_body || '').trim();
    var uncertain = text.indexOf('envio_incerto') >= 0 || r.status === 'ENVIO_INCERTO';
    var result = null;
    function issue(code, title, summary, action, owner, stage) {
      return { code: code, title: title, summary: summary, action: action,
        owner: owner, stage: stage, classification: 'Aguardando tratamento',
        evidence: 'Consulte o historico da mesma chave de controle. Ausencia no recorte nao comprova ausencia na base.' };
    }
    if (r.in_selection === 'NAO' && ['PENDENTE','REPROCESSAR','ERRO','REVISAO','ENVIO_INCERTO'].indexOf(r.status) >= 0) {
      result = issue('FORA_SELECAO', 'Movimento fora da consulta de origem',
        'O controle foi preservado, mas este movimento nao aparece na consulta de origem. Sua presenca nessa consulta tambem nao garante elegibilidade: existem filtros adicionais no envio.',
        'Conferir nota, item, produto, serie e operacao. Verificar alteracoes na origem antes de encerrar ou vincular outro movimento; nao marcar enviado nem repetir inclusao.',
        'Responsavel pelo movimento e integracao', 'Elegibilidade atual');
    } else if (r.status === 'PENDENTE' && r.stage === 'AGUARDANDO_PROXIMA_ETAPA') {
      result = issue('PROXIMA_ETAPA', 'Movimento aguardando a proxima etapa',
        text ? 'Existe uma mensagem anterior preservada. Ela nao comprova bloqueio atual; o movimento avancou para aguardar outra rodada.' :
          'Uma dependencia foi processada e o equipamento aguarda outra rodada. Isso ainda nao confirma o envio completo.',
        'Conferir a ultima atualizacao e as proximas requisicoes. Se nao avancar, verificar selecao e execucao da rotina, sem apagar o historico.',
        'Equipe de integracao', 'Proxima etapa');
      result.classification = 'Aguardando processamento';
    } else if (text.indexOf('situacao_origem_ausente') >= 0 || text.indexOf('situacao_nao_conciliada') >= 0) {
      result = issue('SITUACAO_ORIGEM', 'Situacao do equipamento precisa de configuracao',
        'A serie nao possui situacao reconhecida ou nao ha um unico dominio ativo conciliado. A inclusao foi bloqueada antes do envio.',
        'Preencher a situacao da serie e conciliar seu dominio na Base instalada. Nao presumir Operacional nem escolher um ID arbitrario.',
        'Responsavel pelo cadastro e integracao', 'Validacao da situacao');
    } else if (text.indexOf('reativacao_vinculo_divergente') >= 0) {
      result = issue('REATIVACAO_VINCULO', 'Vinculo do equipamento diferente da origem',
        'O equipamento localizado na Genesis possui cliente, setor, modelo ou numero de serie diferente do movimento de origem. A reativacao foi bloqueada para evitar uma transferencia incorreta.',
        'Conferir o detalhe da mensagem e comparar o cadastro da Base instalada com a nota de origem. Solicitar conciliacao ao responsavel pelo cadastro; nao criar outro equipamento nem transferir o vinculo automaticamente.',
        'Responsavel pelo cadastro e integracao', 'Conferencia do vinculo');
    } else if (text.indexOf('equipamento_nao_ativo') >= 0 || text.indexOf('situacao_remota_') >= 0 || text.indexOf('reativacao_') >= 0) {
      result = issue('ESTADO_EQUIPAMENTO', 'Equipamento existente exige revisao de estado',
        'O cadastro foi localizado, mas atividade ou situacao nao foram confirmadas para concluir este movimento.',
        'Conferir cancelamentos, situacao e vinculos do mesmo ID. Cliente ou setor diferente exige conciliacao; nao criar outro equipamento nem transferir automaticamente.',
        'Equipe de integracao e responsavel pelo equipamento', 'Validacao remota');
    } else if (text.indexOf('mais de uma correspondencia') >= 0 || text.indexOf('mais de um item-material local') >= 0) {
      var material = text.indexOf('material ja vinculado ao modelo') >= 0;
      result = issue(material ? 'MATERIAL_AMBIGUO' : 'VINCULO_AMBIGUO',
        material ? 'Material com mais de um vinculo local' : 'Mais de um cadastro corresponde ao equipamento',
        material ? 'O material do modelo encontra varias linhas na base local. Nao foi possivel escolher um vinculo unico.' :
          'A busca encontrou varios candidatos. Isso nao significa que series diferentes na mesma nota sejam duplicadas.',
        material ? 'Comparar ID Genesis, registros locais e dependencias. Consolidar somente com backup e validacao; preservar o material do modelo.' :
          'Comparar produto, serie, modelo e contexto do cliente. Se nenhum cliente conferir, verificar transferencia ou inconsistencia cadastral. Nao escolher o maior ID.',
        'Responsavel pelos cadastros e integracao', 'Resolucao do vinculo local');
    } else if (text.indexOf('vinculo local pertence') >= 0) {
      result = issue('CONFLITO_LOCAL', 'Vinculo local diferente do esperado',
        'A gravacao local foi bloqueada porque o cadastro ja possui outro vinculo ou foi alterado desde a leitura. Isso nao comprova falha da API.',
        'Conferir entidade, registro, ID Genesis e valor esperado no detalhe tecnico. Material herdado do modelo nao deve receber automaticamente o grupo do produto.',
        'Equipe de integracao', 'Persistencia local');
    } else if (uncertain || (method === 'POST' && http >= 200 && http < 300 && !r.remote_id &&
        (response === '[]' || response === 'null' || !response))) {
      result = issue('ENVIO_SEM_CONFIRMACAO', 'Envio aguardando confirmacao',
        'Existe uma tentativa de inclusao sem ID remoto confirmado. O cadastro pode ter sido gravado; a reserva impede um novo POST que poderia duplicar o equipamento.',
        'Consultar a Genesis pelo codigo externo e validar serie e modelo. Se houver correspondencia unica, confirmar ID e gravacao local. Se nao houver, manter a reserva e analisar com o suporte Genesis.',
        'Equipe de integracao e suporte Genesis', 'Reconciliacao apos POST');
      if (method === 'POST' && http >= 200 && http < 300) result.summary =
        'O POST recebeu HTTP ' + http + ', mas nao confirmou um ID remoto. Sucesso HTTP nao confirma a criacao. Um novo POST permanece bloqueado.';
      result.evidence = response === '[]' || text.indexOf('corpo=[]') >= 0 ? 'Resposta registrada: lista vazia ([]), sem ID.' :
        response === 'null' ? 'Resposta registrada: null, sem ID.' :
        'Conferir resposta do POST e GET posterior no historico. A mensagem de reserva, sozinha, nao informa o resultado remoto.';
    } else if (text.indexOf('invalid http response') >= 0) {
      result = issue('TRANSPORTE', 'Resposta HTTP invalida',
        'O cliente da integracao nao conseguiu ler uma resposta HTTP valida. O codigo 503 pode ter sido gerado localmente; nao comprova rejeicao nem recebimento pela Genesis.',
        'Verificar transporte e logs do servidor. Para POST, manter a reserva e consultar a identidade remota antes de qualquer nova inclusao.',
        'Infraestrutura e suporte Genesis', 'Comunicacao HTTP');
    } else if (text.indexOf('hsp_idexterno') >= 0) {
      result = issue('CODIGO_MODELO', 'Codigo do modelo exige conciliacao',
        'O codigo de origem do modelo esta ausente, ambiguo ou diferente do vinculo local.',
        'Conferir o campo Codigo Produto Protheus na tela Produtos. Codigos antigos gerados pela integracao devem ser atualizados no cadastro confirmado, sem criar outro modelo.',
        'Responsavel pelos cadastros e integracao', 'Validacao do modelo');
    } else if (r.persistence === 'PENDENTE' && r.identity_state === 'VALIDADA') {
      result = issue('PERSISTENCIA_NAO_CONFIRMADA', 'Identidade validada; gravacao ainda nao confirmada',
        'A identidade foi validada nesta requisicao, mas nao ha confirmacao de persistencia local nesta evidencia. Outra etapa pode ter concluido depois.',
        'Consultar as proximas tentativas e o estado atual do movimento. Nao marcar como enviado apenas pelo HTTP ou por esta linha do historico.',
        'Equipe de integracao', 'Confirmacao local');
    }
    return result;
  }
  root.GenesisDiagnosis = { diagnose: diagnose };
})(typeof globalThis !== 'undefined' ? globalThis : window);
