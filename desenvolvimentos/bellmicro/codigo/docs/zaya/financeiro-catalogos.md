# Financeiro selecionado e catálogos nativos — dashboard7.4 / Java7.2

Solicitação: manter os dados do título escolhido no Financeiro, recuperar as
listas de empresas e centros de resultado de tabelas existentes e investigar
o modal vazio. Fonte visual: captura enviada pelo cliente nesta etapa.

## Consultas e contrato

No dashboard original, as listas vinham de TSIEMP.CODEMP/NOMEFANTASIA e
TSICUS.CODCENCUS/DESCRCENCUS (fonte preservada em
docs/fontes/2026-10-28/RateioDespesas-original.jsp, consultas iniciais).
Foram restauradas por ConsultaModalRateioJdbc, somente leitura, sem reintroduzir
SQL no JSP. Centros exigem ATIVO='S'; códigos precisam ser positivos.
Sugestões limitadas a10000 registros por lista, com limiteAtingido explícito.
Inputs com datalist apresentam código/nome e permitem digitar outro código;
a validação definitiva de empresa/CR permanece no Java, antes de persistir.

CONSULTAR da ação Simular agora enriquece a resposta com:

- financeiro: NUFIN, NUNOTA, NUMNOTA, CODEMP, NOMEFANTASIA, CODNAT, CODPARC,
  NOMEPARC, CODTIPOPER, VLRDESDOB e DTVENC, lidos de TGFFIN com joins em TSIEMP
  e TGFPAR, por PreparedStatement com binding do NUFIN selecionado.
- catalogos: empresas e centros (id/nome), mais limiteAtingido.

Essas leituras ocorrem depois de abrir/autorizar o contexto da referência e da
origem canônica, na mesma conexão emprestada de PersistenciaJape. Não criam
conexão, não fecham a conexão do chamador e não fazem DML/commit. Resultados e
statements são fechados. Mensagens de driver não são expostas ao navegador.

A ficha mostra o NUFIN selecionado desde o início, ainda com ações bloqueadas
enquanto aguarda validação. Quando o servidor responde, exibe documento,
parceiro, empresa, TOP, vencimento e valor do título. O valor VLRDESDOB é
apresentação: não é adotado como base rateável. A base, competência e identidade
canônica continuam vindo das regras de backend existentes. Não aceitar valores
finais, usuário ou base do navegador. Retorno financeiro de outro NUFIN é rejeitado.

## Configuração pendente e diagnóstico

O projeto não contém provider instalado de ConfiguracaoServicosRateio. Pode
existir provider separado no ambiente, mas isso não foi verificado. Sem ele,
as ações agora devolvem CONFIGURACAO_AUSENTE, distinto de BASE_INCOMPLETA.
O modal informa que a configuração de acesso/regras está ausente, preservando
o NUFIN selecionado e mantendo operações indisponíveis. Não foi criado acesso
irrestrito para contornar essa dependência. Autorizações e regras aprovadas de
base/competência ainda precisam ser configuradas no servidor.

Quanto ao branco no print: o usuário confirmou que o componente passou a abrir,
mas mostrou modal vazio no fluxo do botão. O launcher recebeu dimensões explícitas
no iframe para não depender somente dos seletores do contêiner. Teste local
agora abre o modal com o próprio script de abertura, cria o iframe, carrega o
JSP integral, envia o NUFIN e verifica a ficha. O problema do ambiente ainda
não foi atribuído a causa específica: foi solicitado o status/URL sem domínio
da requisição html5component.mge, sem retorno até a preparação desta entrega.
Não afirmar que um teste local comprova resolução do branco no ambiente.

## Entrega e validação

Atualizar em conjunto build/libs/ZAYA-BLM-V7.2.jar,
build/315_html5Component.zip (dashboard7.4) e o trecho do launcher em
web/abrir-rateio-financeiro.js, preservando os três IDs reais já cadastrados.
Nenhum FQCN, ID, nome de instância AD_RAT* ou layout/CSS principal foi alterado.
A edição de critérios e efetivação financeira mantêm os limites anteriores.

113 cenários Java aprovados: inclui leituras nativas com binding, normalização
de identificador, fechamento dos recursos, falha sanitizada e configuração
ausente. Teste de navegador usa serviços simulados e verifica listas, dados,
iframe/launcher completo, motivo/revisão, reenvio e bloqueios. JSP compilado
localmente com Jasper para Java8. SQL não executado no cliente; sem deploy,
aceite funcional ou alteração de permissões/banco no ambiente.
