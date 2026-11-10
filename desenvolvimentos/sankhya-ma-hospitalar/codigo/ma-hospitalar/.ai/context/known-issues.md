# Problemas Conhecidos

- A view e a `AD_GENMOVEST` precisam ser validadas na base de homologação.
- Contratos reais dos seis endpoints e unidade epoch precisam de confirmação.
- IDs Genesis são tratados como `int64` conforme schema fornecido.
- Token anteriormente exposto no projeto legado deve ser rotacionado.
- O primeiro envio real alcançou a API, mas o cadastro/consulta de cliente
  respondeu HTTP 400. A mensagem antiga não preservava a etapa nem o corpo da
  resposta; o diagnóstico foi ampliado e depende de nova execução para revelar
  a validação funcional específica da Genesis.
- A publicação do JSP e das classes Java depende do procedimento e das permissões
  do ambiente Sankhya da MA Hospitalar.
- A `AD_VW_GENESIS_ESTOQUE` publicada pode apresentar linhas duplicadas. O
  dashboard e o agendador consolidam `NUNOTA + SERIE` e usam o historico mais
  recente da `AD_GENMOVEST`, mas os `JOINs` da view fisica ainda precisam ser
  revisados na definicao efetivamente instalada para eliminar a causa na origem.
- Se nenhum registro `[MAH-GENESIS]` aparecer após publicar o JAR atualizado,
  a classe não foi carregada ou a ação não está sendo disparada pelo agendador;
  revisar cadastro, classloader/reinício e liberação da customização.
- A API Genesis é externa à transação Oracle. Manter lote 1 na homologação e a
  idempotência por `codigoExterno`, pois eventual falha de commit no Sankhya não
  desfaz uma requisição já aceita pela API.
- O importador ainda precisa ser homologado com o limite real de parametros do
  `ActionButtonsSP.executeJava` na versao `4.36b126`. O lote atual e de 250 linhas.
- O JSP de homologacao esta fixo em `IDBASE=1` e acao Java `123`. Antes de mudar
  de base ou ambiente, esses valores devem ser externalizados ou recompilados.
- A leitura dos 120.241 registros foi validada, mas a persistencia integral ainda
  nao foi executada; validar tempo total, limite dos lotes e retomada em HML.
- Quatro testes antigos do cliente HTTP nao executam neste host porque a criacao
  de servidor loopback esta bloqueada. Os testes do importador nao dependem de rede.
- A acao agendada usa temporariamente `IDBASE=1`; a selecao de base ativa ainda
  nao esta externalizada.
- Um cliente com varios setores conciliados precisa ter exatamente um registro
  `AD_GENSECTOR.PADRAO='S'`; sem isso o item e bloqueado antes do envio.
- O produto so e elegivel quando `TGFPRO.AD_IDEXTERNO` possui correspondencia
  unica e conciliada em `AD_GENMODELO.CODIGOEXTERNO`.
- A chamada HTTP ocorre durante o processamento transacional do agendador. Uma
  resposta aceita pela API nao pode ser desfeita por rollback Oracle; manter
  idempotencia, lote pequeno e conferencia de `AD_GENITEMMAT`/`AD_GENMOVEST`.
