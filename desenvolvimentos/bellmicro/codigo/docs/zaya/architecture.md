# Rateio Belmicro - A1-A3 e aplicação de A5

## Origem e limites

Implementação autorizada nesta pasta em 26/10/2026. Repositório inicialmente
vazio, branch master, sem origin e sem commit. Nenhuma operação Git mutante
foi executada. Fontes completas e caminhos estão no README.

O layout src/test e as camadas/FQCNs seguem o IntPDD v1.1 especificamente
fornecido para esta entrega. O domínio é independente de Jape e de banco.

## Decisões de implementação

- Quociente e resto com BigInteger; nenhuma divisão aproximada decide centavos.
- Percentuais globais com 12 casas são demonstrativos; percentuais internos
  com seis casas são calculados sobre os valores finais dos grupos.
- Duplicatas compatíveis são consolidadas antes da distribuição; evidências
  preservadas em sequência ordenada com tamanhos prefixados.
- Compatibilidade é parte explícita do destino para permitir o agrupamento
  aprovado; o código não deduz compatibilidade de cadastros desconhecidos.
- Hash usa enquadramento binário de campos UTF-8 e registros aninhados,
  evitando também ambiguidade dentro das parcelas do exemplo do IntPDD.
- Validação do rateio nativo é um contrato separado e obrigatório antes da
  criação financeira futura. Nenhum arredondamento do Sankhya é presumido.
- Testes executáveis sem dependências externas: domainTest integra check.

## Validação em 26/10/2026

- Compilação direta por javac 21 com --release 8: aprovada.
- Suíte TesteDominio: 21 cenários aprovados, incluindo 1.000 composições
  geradas com semente fixa, conservação monetária e invariância de ordem.
- Gradle Wrapper 9.0.0: tentado offline; falhou antes da compilação com
  "Unable to establish loopback connection" no ambiente de execução.
- Build Gradle/JAR e compatibilidade com SDK/base Belmicro: não validados.
- O gerador do project-profile reconhece Java no layout src/main/java.
  Como o IntPDD determina src/ e test/, os contadores Java/testes do perfil
  gerado ficam zerados; isso não descreve a árvore de trabalho atual.
- Grafo não preexistente nesta pasta; instrução de atualização do grafo
  mencionada no documento não implica instalar ferramentas ou criar grafo.

## Próxima ação

Validar build Gradle em ambiente com loopback funcional e receber F1/F2,
G1, versão do SDK e contratos de integração para prosseguir com A4-A6.
Persistência, modal, efetivação e homologação funcional permanecem pendentes.

## Continuação autorizada - camada de aplicação de A5

Implementados SimulacaoService e DTOs imutáveis para origem canônica,
entrada manual/critério, fotografia, contexto do modal e critérios disponíveis.
As portas OrigemRepository, CriterioRepository, SimulacaoRepository,
ExecucaoRepository e AutorizacaoRateio isolam o ambiente real.

O serviço abre contexto somente para consulta; simula e recalcula sob contrato
transacional, com revisão esperada e chave idempotente. Confere autorização
antes do replay e da leitura da origem canônica. Rejeita auxiliares e novos
comandos em origem efetivada. Preserva fotografia anterior e registra uma nova
revisão com pendência sem reaproveitar cálculo/hash anterior. Falhas técnicas
propagam para rollback; mensagens privadas de adaptadores não são persistidas.

A identidade da solicitação é SHA-256 estruturado de ação, origem, usuário,
ID/revisão esperados, entrada e motivo. É separada do hash material do cálculo.
Reordenação e escala decimal equivalentes não alteram a identidade. Retornar
um replay não implica que sua revisão continue corrente ou apta a efetivar.

Transação, unicidade, histórico e geração de ID são obrigações explícitas do
adaptador futuro. O repositório em memória existe somente em test/, com
rollback e exclusão mútua para verificar o comportamento do serviço. Isso não
valida locks distribuídos nem persistência no Sankhya. Não existe gerador
financeiro conectado ao serviço nesta fase.

Contrato proposto, comportamentos e obrigações em contrato-simulacao.md,
aguardando alinhamento de G1 e os mapeamentos F1/F2.

### Validação desta continuação - 26/10/2026

- Compilação de todas as classes com javac --release 8: aprovada.
- Bytecode SimulacaoService: major version 52 (Java 8).
- TesteDominio: 21 cenários, incluindo 1.000 composições geradas: aprovado.
- TesteSimulacao: 27 cenários, incluindo rollback, permissão revogada antes
  do replay, origem canônica, recálculos concorrentes e reenvio simultâneo:
  aprovado.
- Revisão independente das adições em aplicacao/: sem achados concretos.
- Gradle offline: falha local anterior à compilação permanece. Stacktrace
  identifica PipeImpl/UnixDomainSockets com "Invalid argument: connect".
  Tentativas de ajustar parâmetros da JVM e seletor Windows não resolveram.
  Sem alteração persistente de ambiente ou versão de Java/Gradle.
- Versionamento do artefato previsto passou a 2.0 por nova funcionalidade,
  conforme padrão corporativo; o JAR não foi gerado/validado pelo Gradle.

Próxima ação: obter F1/F2, G1 e SDK/build da Belmicro para implementar os
adaptadores Jape e transporte autenticado; validar build em ambiente funcional.

## Adoção do manual AD_RAT* e leituras JDBC - 26/10/2026

Usuário definiu Criacao-Manual-Telas.html como especificação física. O modelo
ZYARAT do planejamento é referência funcional anterior. Mapeados os onze
nomes e suas chaves compostas em TabelaRateio.

Implementados CriterioRepositoryJdbc, MarcadorAuxiliarRepositoryJdbc e
ExecucaoRepositoryJdbc com SQL parametrizado nos recursos do classpath. São
somente leitura e recebem conexão já gerenciada pelo chamador; não abrem
conexões, não configuram credenciais e não fecham a conexão emprestada.
Statement e ResultSet são fechados mesmo em falha. A consulta direta é usada
porque o manual define nomes físicos, mas não as instâncias Jape instaladas;
não se presume um nome de entidade a partir da tabela.

Critérios respeitam ativo/aprovado, natureza, empresa, parceiro/TOP opcionais,
vigência do critério e versão na competência mapeada. Versões simultaneamente
aprovadas e vigentes do mesmo critério são rejeitadas. CODCRITERIO identifica
o critério e SEQUENCIA a versão física; NROVERSAO aparece na descrição.
Matrizes são validadas com percentuais positivos, até seis casas e soma 100;
sem fonte analítica mapeada, modalidade A retorna BASE_INCOMPLETA sem fallback.
Evidências usam a chave física do destino, sem dados individuais.

Execuções concluídas são consultadas por origem canônica na AD_RATSIM,
vinculada à AD_RATEXE de situação C. Título direto exige NUNOTA nulo.
Auxiliar é reconhecido em AD_RATFIN com MARCAUX=S, pelo NUFIN.

Limites CHAVESOLIC=80 e MOTIVORECALC=255 aplicados no serviço. DadosOrigem
passou a oferecer TOP tipada por construtor adicional, preservando o anterior;
a TOP é incluída explicitamente no hash material. Recursos em src/main/resources.

Validação: javac --release 8 e bytecode major 52 aprovados. Suítes aprovadas:
21 domínio (1.000 composições geradas), 29 aplicação e 14 JDBC, total 64.
Driver JDBC simulado verifica bindings, conversão de chaves, rejeições,
fechamento de recursos e erros sanitizados; não executa SQL em banco real.
Revisão independente das mudanças JDBC/SQL/TOP/limites: sem novos achados.

ORDEM orienta a ordenação da consulta, mas o desempate monetário continua o
do IntPDD (empresa/CR/componente). Essa divergência dos HTML permanece
explicitada no mapeamento; nenhuma mudança silenciosa de cálculo foi feita.

Não foram implementadas escritas incompletas em AD_RATSIM que perderiam
hash, histórico ou identidade da solicitação. Essas correspondências continuam
no mapeamento-tabelas.md. Adaptadores de origem/base, persistência de simulação,
autorização real, ligação Jape, modal e efetivação permanecem pendentes.
Nenhum acesso ao banco, deploy ou operação Git mutante foi realizado. O JAR
previsto passou a V3.0; build Gradle permanece com a limitação local já registrada.

## Persistência, ponte Jape e modal - 26/10/2026

Implementação 4.0 acrescentou SimulacaoRepositoryAdRat e gateway PersistenciaJape.
Cabeçalho e filhos refletem a revisão corrente. Fotografias imutáveis e
identidade de cada solicitação ficam em AD_RATEVT.DESCRICAO, com envelope
versionado, Base64/GZIP, fragmentos de até 3500 caracteres e validação de hash.
Revisões antigas sobrevivem à substituição da projeção e ao reinício do
repositório. Não foram criados campos, tabelas ou identificadores paralelos.

JapeSession.execEnsuringTX preserva a transação gerenciada; a ponte usa
autonumeração nativa e locks conservadores descritos em integracao-sankhya.md.
Código de integração em sankhya/src, compilado somente quando há SDK no build.
Nenhum JAR proprietário é empacotado.

ComandosModalRateio e ComandosSankhyaRateio obtêm usuário da sessão. A resposta
explícita DtoModalRateio preserva decimais como texto e omite campos materiais
e evidências internas. AbrirModalRateioAction valida seleção e sessão, consulta
o contexto autorizado e delega a abertura ao launcher efetivamente instalado.
Não foi criado um launcher fictício ou endpoint sem contrato do cliente.

Modal em web/: entrada manual/critério, resultados por empresa/CR, recálculo,
tratamento de pendências e reenvio idempotente após timeout. Sem bridge, fica
bloqueado. Efetivação permanece desabilitada. Recursos são incluídos no JAR
como META-INF/resources/rateio; publicação depende do componente do cliente.

Validação: 77 cenários Java aprovados (21 domínio, 29 aplicação, 14 JDBC,
13 persistência), incluindo 1000 composições geradas. Ponte e ação compiladas
com --release 8 contra SDK local 4.35b99; processadores de anotação externos
desativados porque não há geração de código necessária no módulo.
Teste headless no navegador instalado aprovado com bridge simulada e revisão
visual da captura. Não é teste da integração real com Sankhya.

Revisão independente encontrou um limite assimétrico do codec; corrigido antes
da entrega e coberto por rollback de 100001 entradas. Revisão final das
fronteiras e do modal sem novos achados. Não houve consulta ao banco real,
deploy, publicação ou Git mutante. Gradle/JAR seguem sem validação completa.

Próxima ação: fornecer ConfiguracaoRateio do ambiente (instâncias, origem/base,
competência, ACL e launcher), registrar o transporte e homologar a persistência
e o componente. Esse vínculo não está pronto por inferência do HTML de tabelas.

## Escopo final e origem nativa - 27/10/2026

Usuário confirmou o IntPDD v1.1 como escopo final. Os demais documentos são
complementos compatíveis. Registrar a hierarquia documental em decisions.md;
nenhum evento automático ou desempate por ORDEM substitui as regras do IntPDD.

OrigemRepositoryJdbc passou a resolver a referência nativa, rejeitando marcador
AD_RATFIN antes de converter um financeiro para nota. NUNOTA positivo leva ao
cabeçalho TGFCAB; NUNOTA nulo/zero mantém título direto. O valor de uma parcela
não é usado como base da nota. DocumentoOrigem fornece apenas identidade e
cabeçalho; MapeamentoOrigem aplica base/competência por regra aprovada e não
pode substituir empresa/natureza/parceiro/TOP nativos.

Destinos são validados por conjuntos deduplicados em TSIEMP e TSICUS, exigindo
CR ativo. Lotes de 500 evitam N+1 e ultrapassar a cardinalidade de IN no Oracle.
Campos consultados foram conferidos seletivamente no catálogo local b815;
isso não comprova versão ou dados do ambiente Belmicro.

Abertura do contexto passou a devolver pendências funcionais ao modal sem
calcular/gravar ou escolher critério implicitamente. Falhas técnicas propagam.
O default de ConfiguracaoRateio já fornece esse adaptador de origem; ausência
de mapeamento mantém base/competência pendentes, sem adotar data atual/valor bruto.

Validação: 90 cenários Java aprovados, SDK e modalidade de interface validados
localmente. Revisão independente sem novos achados. Build Gradle completo
offline passou com SDK 4_35 em prioridade no classpath. A falha de sockets Unix
do JDK foi contornada usando seu fallback TCP, sem atualizar ferramentas.
Script Build-Sankhya.ps1 torna o ajuste reproduzível e restaura o ambiente.

JAR V5.0 conferido: 62 classes Java 8, sete SQL e três recursos HTML5;
sem bibliotecas Sankhya ou testes empacotados. SHA-256 e instruções em
integracao-sankhya.md. Não houve deploy, publicação, Git mutante nem acesso
ao banco real. O pacote ainda depende da configuração do provider/HTML5 e da
homologação das regras e consultas no ambiente.

## Instâncias comprovadas e vinculadas - 27/10/2026

A captura do dicionário mostra Nome da Instância e Nome da Tabela iguais nos
onze objetos AD_RAT*. Essa evidência resolve a nomenclatura anteriormente
tratada como conferência pendente. Cópia preservada sem alteração em
docs/fontes/2026-10-27/codex-clipboard-6e96ea90-989a-47a5-9427-9d5af010be55.png.
InstanciasRateio registra os onze nomes e ConfiguracaoRateio.instanciasJape
retorna esse mapeamento como padrão, mantendo a possibilidade de override.

Build SDK/Gradle offline aprovado, 90 cenários aprovados. JAR V5.1 gerado,
com classes Java8 e sem SDK empacotado. Nenhuma conexão ao ambiente foi feita.
A confirmação de nomes não comprova chaves internas, privilégios, launcher ou
execução transacional; estes permanecem na integração/homologação.
