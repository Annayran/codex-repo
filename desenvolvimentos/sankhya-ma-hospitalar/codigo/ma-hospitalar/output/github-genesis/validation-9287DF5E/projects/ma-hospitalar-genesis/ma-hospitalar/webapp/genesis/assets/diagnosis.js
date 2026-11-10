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
    if (text.indexOf('mais de uma correspondencia') >= 0 || text.indexOf('mais de um item-material local') >= 0) {
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
