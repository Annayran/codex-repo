# Configuração Java — passo1, 29/10/2026

Atualização posterior: o cliente confirmou a [base TGFFIN e DTVENC selecionado](base-tgffin-2026-10-29.md),
implementados no Java7.4. A descrição de base/competência pendentes abaixo
refere-se ao momento da entrega7.3. O passo2 segue adiado.

Escopo autorizado: implementar somente a configuração concreta e seu registro.
Permissões ficaram para depois; base/competência serão verificadas pelo cliente.

Implementada a classe pública com construtor sem argumentos
`br.com.zaya.belmicro.rateio.sankhya.ConfiguracaoServicosBelmicro`, registrada em
`sankhya/resources/META-INF/services/br.com.zaya.belmicro.rateio.sankhya.ConfiguracaoServicosRateio`.
O build com SDK inclui esses recursos no JAR. Builds sem SDK não recebem um
registro apontando para classe não compilada.

O provider herda o mapa das onze instâncias confirmadas, repositório de origem,
mapeamento pendente e bloqueio de efetivação. Como a interface exige um adaptador
de autorização, o passo1 contém apenas um bloqueio explícito: permite=false e
exigir lança AUTORIZACAO_PENDENTE. Isso não implementa a política do passo2 e não
libera consultas, simulação/recálculo ou geração financeira. Base e competência
continuam null/NAO_HOMOLOGADO; nenhuma data ou valor foi inventado.

O carregador mantém prioridade de configuração específica já instalada e depois
da configuração legada; o provider fechado do projeto é usado como fallback.
Vários providers específicos continuam sendo conflito, sem escolha silenciosa.
Se o carregador do módulo não expuser recursos META-INF/services, o runtime
instancia explicitamente o mesmo provider fechado empacotado; esse caminho
também é testado. Não depende só da descoberta de recursos para inicializar.
O método legado de abertura não é invocado. Não foram alterados FQCNs das ações,
IDs de botões, instâncias, dashboard, regras de cálculo ou dados do cliente.

Entregável: build/libs/ZAYA-BLM-V7.3.jar. Atualizar apenas o módulo Java;
dashboard7.4 existente permanece compatível. Não houve instalação remota.
Com este JAR, CONFIGURACAO_AUSENTE deixa de ser o resultado esperado do pacote
completo. AUTORIZACAO_PENDENTE é o próximo bloqueio deliberado até o passo2.
Mesmo após o passo2, o cálculo precisará do mapeamento aprovado do passo3.

Validação: build com SDK local4.35b99/Java8 aprovado em116 cenários. Os18 cenários
das ações foram executados também usando o JAR final no classpath, confirmando
descoberta pelo ServiceLoader, provider concreto, bloqueio sem leitura de banco,
base/competência pendentes, instâncias, prioridade e conflitos de configuração.
ZIP interno do JAR conferido: descriptor e classe presentes, bytecode52, sem
SDK, JARs dependentes ou testes empacotados. Nenhum teste acessou a base cliente.

Pendências preservadas: passo2 (permissões), passo3 (base/competência), fontes
analíticas, efetivação nativa, normalização do erro HTML/JSON e homologação das
demais regras apontadas na auditoria. Esta entrega não declara o fluxo completo
como operacional.
