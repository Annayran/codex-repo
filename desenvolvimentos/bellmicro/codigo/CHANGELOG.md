# Changelog

## Java7.14 / dashboard7.13 - 2026-10-29

- Histórico completo dividido em fragmentos RAT1 de até99 caracteres, mantendo DESCRICAO100 por decisão do cliente.
- Leitura legada e SHA256 inteiro preservados; limite4000 validado antes dos eventos, com rollback por excesso.
- Sem alteração de tabela ou UI.146 cenários Java aprovados; atualizar somente JAR.

## Java7.13 / dashboard7.13 - 2026-10-29

- Corrigida suposição fixa de Oracle: bloqueios selecionados por metadata JDBC para Oracle/SQL Server; motor desconhecido falha fechado.
- SQL Server usa UPDLOCK/HOLDLOCK/TABLOCKX e LOCK_TIMEOUT0 com restauração; motor aparece no diagnóstico e painel.
- Códigos1003/155 isolados não confirmam Oracle.143 cenários Java e painel/JSP aprovados, sem banco real.

## Java7.12 / dashboard7.11 - 2026-10-29

- Conexão para bloqueios e trabalho adquirida dentro de execEnsuringTX, sem reutilizar o wrapper de leitura aberto antes da transação.
- Tratamento ABRIR_JAPE_TX e limpeza em finally. Sem mudança de locks ou controle manual de commit/rollback.
- Resolução do Oracle155 requer confirmação no ambiente; atualizar somente o JAR.

## Java7.11 / dashboard7.11 - 2026-10-29

- Percentual inválido identifica linha e motivo; modal valida antes de enviar e preserva decimal exato com vírgula/ponto.
- JSON usa escapes Unicode e JavaScript gerado charsetascii para proteger acentos no transporte legado.
- Validação/persistência não dispensadas; payload concreto do erro no cliente ainda desconhecido.

## Java7.10 / dashboard7.10 - 2026-10-29

- Diagnóstico correlacionado em todas as operações: UUID, horário, duração, etapas, versão e código seguro de banco.
- Mensagens específicas de composição por whitelist e painel recolhível com últimas20 operações em memória.
- Logs JUL estruturados sem SQL/payload/dados financeiros; persistência funcional inalterada. 136 cenários Java aprovados.

## Java7.9 / dashboard7.8 - 2026-10-29

- Bloqueio da origem executado em bloco Oracle parametrizado via CallableStatement, sem fetch de ResultSet no Java.
- Mantidos FOR UPDATE NOWAIT, conexão/transação e tratamentos de origem ausente e concorrência.
- Atualizar somente o JAR; confirmação da resolução do1003 no ambiente ainda pendente.

## Java7.8 / dashboard7.8 - 2026-10-29

- LOCK TABLE estático executado por Statement.executeUpdate; SELECT FOR UPDATE mantém binding e NOWAIT.
- Diagnóstico distingue os dois bloqueios e preserva exceções encapsuladas pelo Jape.
- Modal mostra mensagem técnica completa mantendo reenvio idêntico. Oracle1003 requer confirmação no ambiente.

## Java7.7 / dashboard7.7 - 2026-10-29

- Falha técnica informa etapa, código numérico do banco quando disponível e referência, sem expor SQL ou mensagens internas.
- Persistência preserva etapa específica; bridge reconhece prefixo nativo de erro sem perder a condição de reenvio.
- Causa operacional no servidor ainda pendente de diagnóstico; nenhuma regra funcional flexibilizada.

## Java7.6 / dashboard7.6 - 2026-10-29

- Competência do vencimento selecionado preservada quando a base está pendente.
- Diagnóstico específico da base, sem flexibilizar filtros; fotografia antiga não oculta o motivo atual.
- Validação: 129 cenários Java, dashboard no navegador e JSP Java 8.

## Java7.5 - 2026-10-29

- Retirado AUTORIZACAO_PENDENTE do provider padrão por solicitação explícita
  do cliente. Consulta/simulação/recálculo permitidos temporariamente para
  sessão/origem/operação válidas, sem política adicional por perfil/empresa.
- Sessão servidor, validações e bloqueio de efetivação mantidos.

## Java7.4 / dashboard7.5 - 2026-10-29

- Mapeamento aprovado: total nominal TGFFIN por documento e DTVENC da parcela
  selecionada. Referência preservada no hash, fotografia, releitura e reenvio.
- Ficha inclui total, quantidade de parcelas e DESDOBRAMENTO. Sem agrupamento
  por NUMNOTA; avulso tratado por NUFIN e renegociação ambígua vira pendência.
- Permissões continuam pendentes conforme adiamento do passo2.

## Java7.3 - 2026-10-29

- Provider concreto ConfiguracaoServicosBelmicro registrado via ServiceLoader
  e incluído somente no build com SDK. Configuração própria/legada tem prioridade.
- Passo1 entregue; autorização bloqueada com AUTORIZACAO_PENDENTE, e
  base/competência permanecem pendentes, conforme adiamento dos passos2/3.
- Build116 cenários; descoberta e contrato conferidos também no JAR final.

## Java7.2 / dashboard7.4 - 2026-10-28

- CONSULTAR inclui ficha do NUFIN escolhido e sugestões nativas TSIEMP/TSICUS,
  por consultas JDBC de leitura após autorização do contexto.
- Valor do título separado da base rateável; listas com código/nome e validação
  server-side. NUFIN visível durante carregamento ou erro.
- CONFIGURACAO_AUSENTE distingue provider não instalado de ausência de dados.
- Iframe com dimensões explícitas e teste do launcher/modal completo.
- 113 cenários Java aprovados, mantendo FQCNs e cadastro de instâncias.

## Dashboard 7.3 - 2026-10-28 (Java permanece 7.1)

- JSP simples no padrão TOP Nutri: removidos imports, scriptlets, taglibs,
  snk:load e SQL da entrada. Fontes/CSS/JS separados na fonte e incorporados
  ao JSP pelo build, sem dependência de BASE_FOLDER ou requisições de assets.
- Layout/CSS principal preservados. Sem catálogo do JSP, empresa/CR digitados
  por código e validados pelo Java; troca de título por consulta de NUFIN.
- Compilação local do JSP final com Jasper9/Java8 aprovada, além do teste de
  navegador com o arquivo integral e transporte simulado.

## Dashboard 7.2 - 2026-10-28 (Java permanece 7.1)

- Runtime React isolado de AMD/CommonJS globais do host. Reproduzida no pacote
  anterior a ausência de window.React quando define.amd está presente.
- Conteúdo inicial visível e mensagem para falha de assets; abertura direta sem
  parâmetros desenha o layout com orientação, sem executar ações.
- Mantido um único snk:load no início do head; ele já existia no JSP anterior.
- Teste usa head e estrutura final do próprio JSP, com AMD simulado, assets
  minificados e falha proposital do runtime. Não compila JSP no servidor.

## 7.1 - 2026-10-28

- Corrigida a descoberta das rotinas Java: as três classes concretas declaram
  AcaoRotinaJava diretamente; a base abstrata deixa de declarar a interface.
- Teste cobre interface direta, construtor público e método executável herdado.
- Base pública mantém acesso reflexivo ao método herdado; FQCNs preservados.

## 7.0 - 2026-10-28

- Dashboard315 incorporado a partir do ZIP do cliente; CSS preservado, JSP,
  CSS e JS separados, React no ciclo de vida da view e assets locais.
- Modal chama as ações Java pelo transporte usado na referência TOP Nutri.
- Contexto, valores, critérios, revisão e metadados passam a vir do servidor;
  removidos cálculo monetário local, fixtures e NUFINs fictícios do fluxo real.
- Recálculo com motivo e reenvio idempotente; efetivação e edição local de
  critérios bloqueadas enquanto seus serviços não estiverem disponíveis.
- Build Java/107 cenários e testes de integração simulada em Edge.

## 6.0 - 2026-10-28

- Três entradas Java no Financeiro: Simular, Recalcular e Efetivar. A abertura
  do modal passa ao JavaScript; entrada antiga preservada com aviso de migração.
- Payload RATEIO_JSON validado, sessão/NUFIN conferidos e retorno JSON.
- Bridge para os IDs reais das ações, com executor nativo a cargo do dashboard.
- Recálculo vinculado à origem selecionada; falha técnica mantém chave de reenvio.
- Efetivar possui contrato; serviço financeiro padrão bloqueado, sem criar títulos.
- Build SDK e 107 cenários Java aprovados; bridge e modal testados localmente.

## 5.1 - 2026-10-27

- Instâncias AD_RAT* confirmadas por captura do dicionário do cliente e
  vinculadas à configuração padrão Jape, sem inferir nomes de tabelas.

## 5.0 - 2026-10-27

- IntPDD confirmado como escopo final; demais documentos são complementares.
- Resolução JDBC da origem canônica e validação em lotes de empresas/CR.
- Pendências de base/competência e critérios exibidas na abertura do contexto.
- Build completo com SDK e 90 cenários aprovados; pacote Java 8 conferido.
- Script reproduzível contorna socket Unix local usando fallback TCP do JDK.

## 4.0 - 2026-10-26

- Persistência de revisões e solicitações em fotografias versionadas no histórico
  existente, com projeção corrente e rollback atômico.
- Ponte Jape compilada com SDK local, ação Java e comandos com usuário da sessão.
- Modal HTML5 com análise, recálculo e reenvio da mesma solicitação após timeout.
- DTO explícito sem dados internos; testes de persistência e navegador.
- Configuração de instalação documentada; sem deploy ou efetivação financeira.

## 3.0 - 2026-10-26

- Mapeamento físico AD_RAT* conforme manual e chaves compostas.
- Adaptadores JDBC somente leitura para critérios/matriz, marcador e execução.
- Limites de solicitação/motivo ajustados para 80/255 sem truncamento.
- TOP explícita na origem e no hash; testes JDBC com driver simulado.

## 2.0 - 2026-10-26

- Serviço de simulação e recálculo, DTOs e contratos de repositórios propostos.
- Revisões, solicitação idempotente, autorização e pendências com testes em memória.
- Contrato de integração para alinhamento de G1; adaptadores Sankhya pendentes.

## 1.0 - 2026-10-26

- Estrutura inicial A1-A3, cálculo decimal exato e hash canônico.
- Testes locais de domínio; sem implantação ou homologação Sankhya.
