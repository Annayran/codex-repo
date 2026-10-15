# Checkpoint Tecnico

## Data

2026-07-06

## Estado Atual

Projeto Java 8/Gradle para integracao Sankhya x Genesis em `C:\zy-tools\workspace\MA Hospitalar`.

A integracao esta estruturada para enviar movimentos de estoque/base instalada controlados por serie para a API Genesis (`POST /api/v1/estoques/`). A API ja foi validada via smoke test Java, incluindo GET de autenticacao e POST real com retorno HTTP 201 usando uma linha exportada da view.

A view `AD_VW_GENESIS_ESTOQUE` foi validada/subida no ambiente do cliente e retorna os campos esperados para empresa, nota, parceiro, produto, serie, garantia/status, tipo de movimento, poder e chave tecnica. A busca atual no Java considera registros com `SERIE IS NOT NULL`, cruza com `AD_GENMOVEST` e evita reenviar registros com `STATUS_INTEGRACAO = 'ENVIADO'`.

A tabela `AD_GENMOVEST` esta modelada para controle da fila/status da integracao. O repository ja possui operacoes para registrar pendencia, marcar enviado, marcar erro funcional e marcar reprocessamento.

A classe `IntegracaoGenesisScheduleAction` agora implementa a interface Sankhya real `org.cuckoo.core.ScheduledAction`, com metodo `onTime(ScheduledActionContext context)`, delegando para `runOnce(context)`.

Foi documentada e prototipada uma abordagem HTML5/JSP/JavaScript para dashboard analitico da integracao. A tela deve consolidar dados da `AD_VW_GENESIS_ESTOQUE` com o controle da `AD_GENMOVEST`, permitindo analisar entradas, saidas, series/estoque, status de estoque e resultado dos envios. O POST para Genesis permanece server-side no Java para proteger token e manter rastreabilidade.

## Arquivos/Pastas Relevantes

- `build.gradle`
- `settings.gradle`
- `sql/001_create_ad_genmovest.sql`
- `sql/002_create_ad_vw_genesis_estoque.sql`
- `sql/003_campos_adicionais.sql`
- `src/main/java/br/com/zaya/mahospitalar/genesis/schedule/IntegracaoGenesisScheduleAction.java`
- `src/main/java/br/com/zaya/mahospitalar/genesis/repository/MovimentoEstoqueRepository.java`
- `src/main/java/br/com/zaya/mahospitalar/genesis/client/GenesisHttpClient.java`
- `src/main/java/br/com/zaya/mahospitalar/genesis/dto/EstoqueGenesisDTO.java`
- `src/main/java/br/com/zaya/mahospitalar/genesis/service/PayloadBuilder.java`
- `src/main/java/br/com/zaya/mahospitalar/genesis/tools/GenesisPostLinhaViewMain.java`
- `docs/html5-jsp-integracao-genesis.md`
- `src/main/webapp/genesis/index.jsp`
- `src/main/webapp/genesis/assets/genesis-integracao.css`
- `src/main/webapp/genesis/assets/genesis-integracao.js`
- `src/test/java/br/com/zaya/mahospitalar/genesis/client/GenesisApiSmokeTest.java`
- `src/test/java/br/com/zaya/mahospitalar/genesis/client/GenesisApiViewRowPostSmokeTest.java`
- `build/libs/MA-HOSPITALAR-GENESIS-1.0-SNAPSHOT.jar`

## Fluxo Atual

1. `onTime(context)` inicia a acao agendada Sankhya.
2. `repository.lerParametros()` deve carregar URL/token e TOPs elegiveis.
3. `buscarPendentes()` consulta `AD_VW_GENESIS_ESTOQUE V` com `V.SERIE IS NOT NULL` e ignora registros ja `ENVIADO` na `AD_GENMOVEST`.
4. `PayloadBuilder` valida e monta `EstoqueGenesisDTO`.
5. `registrarPendente()` insere/atualiza `AD_GENMOVEST` como `PENDENTE` antes do POST.
6. `GenesisHttpClient.enviarEstoque()` faz `POST /api/v1/estoques/` com header `token`.
7. Retorno 201 marca `AD_GENMOVEST` como `ENVIADO`.
8. Erros funcionais marcam `ERRO`; timeout/500 marcam `REPROCESSAR` e incrementam `TENTATIVAS`.

## Validacoes Realizadas

- Build Gradle Java 8 executado com sucesso: `gradlew.bat clean test jar --stacktrace`.
- Prototipo HTML5/JSP/JS ajustado para dashboard analitico da view + controle de integracao, sem chamada direta a Genesis pelo navegador.
- Smoke test Java de API criado e compilado.
- GET Genesis validado com token correto.
- POST real Genesis validado com linha da view/exportacao: HTTP 201.
- JAR gerado: `MA-HOSPITALAR-GENESIS-1.0-SNAPSHOT.jar`.

## O que ja esta claro

- Build oficial: Gradle Java 8.
- Interface de agendamento Sankhya: `org.cuckoo.core.ScheduledAction`.
- View oficial de consulta: `AD_VW_GENESIS_ESTOQUE`.
- Tabela de controle/fila: `AD_GENMOVEST`.
- Campos adicionais de garantia/status devem ser tratados por serie em `TGFSER`.
- Status de estoque previstos: `O`, `A`, `I`, `V`, `D`, `R`.
- A API Genesis usa header `token`.
- O POST de estoque espera sucesso HTTP 201.

## O que precisa ser confirmado

- Como a Hiaria ira liberar/configurar a acao agendada no ambiente da MA Hospitalar.
- Onde o token/URL Genesis ficarao armazenados no Sankhya para implementar `lerParametros()` sem hardcode.
- Qual mecanismo interno sera usado para expor endpoints consumidos pelo JSP/JavaScript: servlet/action/service provider Sankhya ou padrao web ja usado pela equipe.
- Se `IDINTGENESIS` continuara com `MAX + 1` ou sera criada sequence Oracle.
- Se a Genesis exige IDs internos como `idCategoria`, `idFabricante`, `idFornecedor` e `idSubCategoria` para todos os cenarios.
- Volume de registros retornados pela view em producao e periodicidade ideal da acao.

## Proxima Melhor Acao

Aguardar a Hiaria liberar a acao agendada no sistema da MA Hospitalar. Depois, configurar parametros seguros de URL/token, executar a acao no ambiente do cliente, validar gravacao em `AD_GENMOVEST` e acompanhar os envios de POST em lote.
## Atualizacao 2026-07-06 - Dashboard HTML5/JSP

- Corrigido empacotamento do dashboard analitico Genesis para o Sankhya: o JSP agora referencia `css/genesis-integracao.css` e `js/genesis-integracao.js`.
- Gerado pacote ZIP corrigido com `dashboard_genesis_ma_hospitalar.jsp`, pasta `css/` e pasta `js/` na raiz.
- Caso o Sankhya ainda nao carregue CSS externo, proxima alternativa sera embutir o CSS diretamente no JSP.

## Atualizacao 2026-07-06 - Ajuste Dashboard Sankhya

- Dashboard HTML5/JSP ajustado conforme padrao dos dashboards existentes: links para `${BASE_FOLDER}/css/...` e `css/...`.
- CSS tambem foi embutido no JSP como fallback para evitar tela sem estilo no Sankhya.
- JavaScript foi embutido no JSP para reduzir dependencia de carregamento externo.
- JSP e pacote foram gravados em UTF-8 sem BOM e textos acentuados foram convertidos para entidades HTML.

## Atualizacao 2026-07-06 - Leitura de Parametros da Acao

- `MovimentoEstoqueRepository.lerParametros()` foi implementado para validar `AD_CENTPARAM.NUPAR = 1` e carregar TOPs elegiveis de `AD_CENTPARAMTOP`.
- `buscarPendentes()` passou a aplicar tambem no Java o filtro de `CODTIPOPER` conforme as TOPs carregadas, alem do filtro ja existente na view.
- URL base e token da Genesis ficaram fixos temporariamente no codigo, por solicitacao operacional, ate serem movidos para parametro seguro do Sankhya.
- Build Gradle executada com sucesso e JAR atualizado gerado em `build/libs/MA-HOSPITALAR-GENESIS-1.0-SNAPSHOT.jar`.

## Atualizacao 2026-07-07 - Dashboard Analitico sem Endpoint

- Dashboard HTML5/JSP Genesis ajustado para nao chamar endpoints internos REST (`/resumo` e `/movimentos`).
- A tela agora usa `snk:query` diretamente no JSP para consultar `AD_VW_GENESIS_ESTOQUE` cruzando com `AD_GENMOVEST`.
- O JavaScript passou a ler uma tabela HTML oculta com os dados retornados pela consulta Sankhya e renderizar cards, agrupamentos, filtros e grade analitica no navegador.
- Novo pacote gerado: `dashboard_genesis_ma_hospitalar_analitico_snkquery_20260707-130833.zip`.
