# Ações do Financeiro — 28/10/2026

Correção V7.1: as três classes concretas declaram AcaoRotinaJava diretamente.
Antes, somente a base declarava a interface e as ações a herdavam; a captura
do cadastro mostrou apenas a base e a ação legada. A base pública abstrata
agora não implementa a interface, mantendo o método compartilhado acessível
para invocação reflexiva. Não atribuir essa falha à instalação de versão antiga.
Usar ZAYA-BLM-V7.1.jar para atualizar o módulo, preservando FQCNs e IDs.
O dashboard315 não precisa de alteração por esta correção. A ação legada pode
continuar aparecendo por compatibilidade; não deve ser usada em novo cadastro.

Atualização V7.0: o executor nativo e o dashboard do cliente foram integrados.
As referências abaixo a executor ainda ausente e ao JSP inicial são históricas;
usar [dashboard315](dashboard315.md) para a organização e publicação atuais.

Correção solicitada pelo cliente: cadastrar três rotinas Java na instância
Financeiro (TGFFIN). O dashboard/HTML abre o modal por JavaScript e chama essas
ações; o Java não abre a página. Esta decisão substitui a entrada anterior
AbrirModalRateioAction, cujo FQCN foi preservado apenas para informar a migração.

## Cadastro

| Descrição | Classe Java |
|---|---|
| Simular rateio | br.com.zaya.belmicro.rateio.acao.SimularRateioAction |
| Recalcular rateio | br.com.zaya.belmicro.rateio.acao.RecalcularRateioAction |
| Efetivar rateio | br.com.zaya.belmicro.rateio.acao.EfetivarRateioAction |

Tipo: Rotina Java. Instância: Financeiro. Habilitar controle de acesso e atribuir
permissões aprovadas aos usuários. Cada ação recebe uma linha com NUFIN e o
parâmetro texto RATEIO_JSON. Os três IDs são os gerados pelo cadastro real;
não há IDs fixos no pacote. A captura do cliente mostra o cadastro antigo 282,
que precisa ser substituído ou desativado pelo administrador. Nenhum cadastro
foi alterado por esta entrega.

## Contrato

Simular recebe modo SIMULAR, entrada e solicitacaoId. A entrada pode ser
`{"modalidade":"CRITERIO","criterioId":"...","criterioVersao":1}` ou
`{"modalidade":"MANUAL","percentuais":[{"codEmp":1,"codCencus":100,"percentual":"100.000000"}]}`.
Os valores são exemplos de formato, não cadastros aprovados do cliente.

Para consultar o contexto sem calcular, usar a mesma ação Simular com
`{"modo":"CONSULTAR"}`. Recalcular recebe simulacaoId, revisaoEsperada,
entrada, motivo e solicitacaoId. Efetivar recebe simulacaoId, revisaoEsperada,
hashEsperado e solicitacaoId. Revisões devem ser as retornadas pelo backend;
hashEsperado usa o hash de composição retornado. Preservar solicitacaoId no
reenvio da mesma operação; uma nova operação usa outra chave.

A origem vem do NUFIN selecionado e é resolvida no servidor. Usuário autenticado,
base, competência e valores finais não são aceitos do navegador. O recálculo
confere se a fotografia pertence à origem selecionada, inclusive no reenvio.

Sucesso em mensagemRetorno: objeto JSON com sucesso=true, operacao e dados.
Falhas são exceções com JSON contendo código e mensagem sanitizados; não são
sucessos com texto de erro. Isso preserva a propagação de falhas à transação.
Erro técnico ou de comunicação mantém a chave de reenvio no modal.

## JavaScript do dashboard

O cliente informou dashboard **315**, entrada **RateioDespesas.jsp**. O script
`web/abrir-rateio-financeiro.js`, adaptado do exemplo fornecido no chat, abre o
iframe por `/mge/html5component.mge`, mantendo a estrutura visual do exemplo.
Preencher CODIGO_SIMULAR, CODIGO_RECALCULAR e CODIGO_EFETIVAR com os IDs reais.
O código 315 identifica o dashboard e não uma ação Java. O launcher é uma ação
JavaScript de abertura separada das três rotinas Java de processamento.

O launcher exige seleção única de NUFIN. Usa contexto.getLinhas, ou getLinhas
global quando disponível; confirmar essa API no contexto JavaScript instalado.
Sem API compatível, bloqueia com mensagem em vez de abrir sem origem.

Publicar os arquivos de `web` juntos no componente 315. `RateioDespesas.jsp`
reutiliza a tela e referencia CSS, modal, bridge e `dashboard-rateio.js` por
caminhos relativos; conferir resolução dos assets no HTML5 instalado.
O adaptador nativo do dashboard deve chamar
`window.iniciarDashboardRateio(executarAcao)` depois de carregar os scripts.
Sem esse executor, a página informa integração pendente e mantém as operações
desabilitadas. O JSP não implementa chamadas nativas presumidas.

`web/acoes-financeiro-rateio.js` expõe criarBridgeAcoesFinanceiroRateio(executarAcao,
opcoes). As opções são nufin e os IDs reais simular, recalcular e efetivar.
O executor injetado pelo dashboard adapta a chamada ao SDK instalado e devolve
mensagemRetorno. Recebe idAcao, instancia Financeiro, linhas com NUFIN e
parametros com RATEIO_JSON. Não foi presumido endpoint nativo não documentado.
O bridge fornece abrirContexto, simular, recalcular e efetivar. A abertura e o
vínculo visual do modal continuam responsabilidade do dashboard HTML5.

## Configuração e limite da entrega

O pacote não exige um lançador HTML no Java. Ainda exige provider de
ConfiguracaoServicosRateio registrado em META-INF/services/
br.com.zaya.belmicro.rateio.sankhya.ConfiguracaoServicosRateio, com autorização e
mapeamento aprovado de base/competência. As onze instâncias AD_RAT* já confirmadas
permanecem no mapa padrão. O provider legado ConfiguracaoRateio é aceito para
compatibilidade. Sem provider, as operações são bloqueadas, sem assumir acesso.

EfetivarRateioAction é uma entrada e um contrato de integração. O serviço real
de geração financeira não está implementado neste pacote. O comportamento
padrão é EFETIVACAO_NAO_DISPONIVEL; nenhum título é criado. A implementação futura
precisa validar autorização, origem, revisão/hash, idempotência e executar
locks, títulos, rateios e vínculos na mesma transação, com rollback integral.

## Validação

Build V6.0 contra SDK local 4.35b99; testes das ações usam a interface real do SDK
com runtime simulado, sem banco. Bridge e modal testados localmente. Homologação
do executor nativo, permissões, provider, persistência e efeito financeiro
permanece necessária. Não houve deploy, DML ou aceite funcional no Sankhya.
