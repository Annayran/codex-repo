# Catalogo seletivo de documentacao Genesis

## Criterios e procedencia

Captura em 2026-10-22, limitada as sete fontes Markdown autorizadas. Caminhos
abaixo sao relativos a raiz Git autorizada. `hashSHA256` identifica os bytes
locais da fonte na captura, nao a versao de HEAD. Todas as fontes possuem
conteudo nao commitado: `commit_origem` nao atribuido e nenhum hash de HEAD
usado para representar esse conteudo. Estado dos registros: `local_nao_versionado`.
Cobertura: `resumo_identificado`, nao copia integral ou validacao do codigo.

As fontes tecnicas permanecem canonicas no Git do projeto. Estes resumos omitem
dados pessoais, terceiros, enderecos internos e segredos. Historicos de testes
nas fontes nao significam novos testes nesta catalogacao nem aceite do cliente.

## 1. Notas 120671 e 120872: produto fora da selecao

- Path: [ma-hospitalar/docs/genesis/analise-notas-120671-120872.md](../genesis/analise-notas-120671-120872.md).
- Estado Git: `untracked` (`??`).
- hashSHA256: `E0E04301828AD3AD44A8EA7D7E96944D459C5293C100831A7EA55699344D11B4`.
- Resumo: produto 11919 com tipo efetivo M3 nao atende o filtro M1 de
  equipamentos. Confirmar classificacao antes de corrigir cadastro ou solicitar
  ampliacao autorizada; nao inferir inexistencia remota ou falha de envio da API.

## 2. Central React: base instalada e configuracoes

- Path: [ma-hospitalar/docs/genesis/central-base-configuracoes.md](../genesis/central-base-configuracoes.md).
- Estado Git: `untracked` (`??`).
- hashSHA256: `026F5FA3C6EE95159403A1B3BBD07EE0B198852C9E6ACF066CE9A2C9614213E5`.
- Resumo: organiza consultas de base instalada e configuracoes, filtros e grafico
  por periodo. Componente read-only, sem API externa ou reprocessamento manual;
  recorte de ate 100 registros por area. Relata build e Playwright locais
  anteriores; compilacao JSP, permissoes e dados reais exigem homologacao.

## 3. Codigo externo de modelo: HSP_IDEXTERNO

- Path: [ma-hospitalar/docs/genesis/modelo-hsp-migracao.md](../genesis/modelo-hsp-migracao.md).
- Estado Git: `untracked` (`??`).
- hashSHA256: `17FF6BE4C8DBA34879C3E71976DEBFFF286057C7A01389E05D0BED0CE0D41836`.
- Resumo: codigo externo de modelos derivado de HSP_IDEXTERNO, sem fallback;
  migracao dedicada por identidade confirmada, GET/PUT/GET e controles locais,
  sem POST alternativo. Contrato remoto e concorrencia exigem homologacao.
  Restaurar binario nao reverte alteracoes remotas; rollback de dados e separado.

## 4. Controles e rastreabilidade Genesis

- Path: [ma-hospitalar/docs/genesis/rastreabilidade-controles.md](../genesis/rastreabilidade-controles.md).
- Estado Git: `untracked` (`??`).
- hashSHA256: `22E65F68C27E4A40B57C29A713723A71C131C5D7A30E1DC83D121F9C2614EBB9`.
- Resumo: separa movimentos, cadastros/reconsultas, estoque, cancelamentos e
  historico HTTP. Diferencia HTTP, identidade validada e persistencia confirmada;
  chamada sem resposta nao autoriza repetir POST. Descreve cadastro de controles,
  migracao, aceite e rollback, sem comprovar execucao no ambiente.

## 5. Catalogo de estoques Genesis - implementacao local

- Path: [ma-hospitalar/docs/genesis/catalogo-estoques.md](../genesis/catalogo-estoques.md).
- Estado Git: `untracked` (`??`).
- hashSHA256: `1D982A6E0319411AB8336BD440CD6F1CAD4B4089C4BBB252D85534940BDE028E`.
- Resumo: fluxo proprio M2/M3/SV com controle de estoque separado dos movimentos
  de equipamentos. A secao final atualiza a regra de valores: venda da tabela
  101 e IPI, sem custo no JSON. Identidade do fabricante e contratos remotos
  permanecem sujeitos a conciliacao/homologacao; implementacao local nao e deploy.

## 6. Diagnostico e tratamento dos bloqueios

- Path: [ma-hospitalar/docs/genesis/diagnostico-tratamento-20261021.md](../genesis/diagnostico-tratamento-20261021.md).
- Estado Git: `untracked` (`??`).
- hashSHA256: `BEFB60BD46DB975644C5CBFEFD52F9DAA7C6DD9F54AF4F66AD85CB38A05CAA1C`.
- Resumo: classificacao compartilhada dos bloqueios, POST sem ID sujeito a
  reconciliacao por GET e ambiguidades que impedem selecao automatica. Segundo
  caso de transporte permanece aberto. Nao reescrever historico nem liberar em
  massa; React consulta ate 100 registros. Validacoes anteriores nao substituem
  homologacao real e nao foram repetidas nesta entrega.

## 7. Arquitetura - Integracao Genesis

- Path: [ma-hospitalar/docs/genesis/arquitetura.md](../genesis/arquitetura.md).
- Estado Git: `tracked modified` (` M`).
- hashSHA256: `C5E3E4160F3DDC64443C8172C201F7E6A78106FE256685AC170B6617D4577594`.
- Resumo: apresenta dependencias, movimentos M1, controles, conciliacao,
  cancelamentos e catalogo separado M2/M3/SV. Possui camadas historicas e avisos
  de evolucao; nao tratar trechos antigos de PUT/custo/estoques como regra vigente
  sem confronto com documentos especificos posteriores. Codigo local nao prova
  JAR instalado, jobs ativos ou deploy.
- Cobertura seletiva: avisos iniciais e secoes Referencia atual, Cadastro e envio,
  Protecoes e lacunas comprovadas, Cancelamento e EXCFILA, Nova demanda: estoques
  e Caminho de evolucao e aceite. Descricao historica final nao usada como estado atual.

## Uso e atualizacao

Antes de gerar copia de consulta, conferir novamente hash e estado Git da fonte;
alteracao posterior invalida a correspondencia desta captura. Coordenador registra
as copias e vinculos no cerebro central conforme [mapeamento](cerebro-demandas.md).
Nao foi realizada verificacao remota, homologacao, teste Java ou aprovacao do cliente.


---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/docs/zaya/catalogo-documentacao.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
