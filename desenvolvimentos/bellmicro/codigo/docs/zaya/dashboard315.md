# Dashboard 315 integrado — V7.0, 28/10/2026

**Vigente: [dashboard7.3, base TOP Nutri](dashboard315-topnutri.md).** O histórico
abaixo descreve versões anteriores com consultas/tags/arquivos externos no JSP.

## Correção de carregamento — dashboard7.2 / Java7.1

Após captura de tela branca, a revisão encontrou inicialização que acessava
window.React antes de qualquer proteção e uma raiz HTML vazia. O runtime UMD
anterior foi reproduzido sob define.amd: registrou no AMD e deixou o global
React ausente. Isso impede o bootstrap anterior e produz página sem conteúdo.
Não houve acesso ao Console/Rede do ambiente do cliente; essa reprodução
confirma o defeito e sua condição de ocorrência, não o diagnóstico exclusivo
da instância instalada. Falha de URL/assets também é possível.

O snk:load já existia no JSP anterior, com espaço antes de />; o diagnóstico
inicial de ausência foi corrigido. Agora há um único snk:load no início do head,
antes dos recursos. Runtime gerado por Build-ReactRuntime.cjs isola os módulos
React/ReactDOM de define/module/exports do host, mantendo as licenças.
O JSP carrega vendor/rateio-react-runtime.js e apresenta conteúdo inicial e
mensagem de erro se o runtime não carregar. Abertura direta sem IDs/NUFIN monta
o layout e orienta a abrir pelo botão do Financeiro; não presume IDs nem faz
operações sem contexto. CSS original permanece idêntico.

Test-Dashboard usa o head e a estrutura final do JSP entregue, substituindo
somente as partes que exigem processamento servidor por fixtures. Cobre AMD
simulado, abertura direta, falha de asset e os fluxos anteriores. Ainda não é
compilação JSP/JSTL ou homologação no Sankhya. O ZIP tem dashboardVersion7.2 e
moduleVersion7.1; não é necessário alterar o Java por esta correção de frontend.

## Fonte e referências

Layout recebido em `315_html5Component (1).zip`, entrada RateioDespesas.jsp.
Cópia integral original: `docs/fontes/2026-10-28/RateioDespesas-original.jsp`.
O projeto TOP Nutri foi consultado apenas para referência do fluxo de abertura
e transporte ActionButtonsSP.executeJava, em
`src/main/webapp/html5/importacaoFormulas.jsp` (executeAction).
MA Hospitalar/Genesis foi consultado para separação JSP/CSS/JS e BASE_FOLDER.
Não foi encontrado React nessa cópia local; React foi incorporado aqui por
solicitação do cliente. Nenhum repositório de referência foi alterado.

## Organização e contratos

- `web/abrir-rateio-financeiro.js`: ação JavaScript de abertura, dashboard315,
  RateioDespesas.jsp, NUFIN e IDs reais das três ações Java.
- `web/RateioDespesas.jsp`: entrada e consultas auxiliares de leitura.
- `web/css/rateio-despesas.css`: CSS extraído integralmente, sem mudança de regras.
- `web/css/fonts.css` e `web/fonts`: mesmas famílias e ícones, agora locais;
  licenças e manifesto de origem/hash acompanham os arquivos.
- `web/js/rateio-react.js`: componente React que administra montagem e limpeza
  da view existente. Integração híbrida deliberada para preservar DOM/layout;
  não é uma reescrita de toda a interface em JSX.
- `web/js/rateio-view.js`: componentes visuais/classes originais e eventos.
- `web/rateio-servidor.js`: estado, entrada, fotografia, revisão e reenvio.
- `web/acoes-financeiro-rateio.js`: contrato das três ações, RATEIO_JSON.
- `web/sankhya-executor.js`: transporte nativo conforme referência TOP Nutri,
  com NUFIN na linha selecionada, sessão same-origin, erro funcional e timeout.
- React/ReactDOM18.3.1 locais, com licença e manifesto dos pacotes de origem.

JavaScript abre o iframe; dentro dele React monta a view e consulta contexto
pela ação Simular/modo CONSULTAR. As operações usam os IDs cadastrados e as
classes SimularRateioAction, RecalcularRateioAction e EfetivarRateioAction.
Não cadastrar AcaoFinanceiroRateio (base abstrata).

## Correções em relação ao ZIP recebido

Os valores da simulação são apresentados a partir da fotografia Java. Não há
cálculo monetário alternativo no dashboard nem correção de saldo na última
parcela. A conversão decimal serve apenas à apresentação e valida precisão.
Base, competência e origem canônica vêm do Java; a competência fica somente
leitura. VLRDESDOB da pesquisa identifica o valor do título, não determina a
base usada no cálculo.

Critérios aplicáveis e sua SEQUENCIA vêm do backend, que aplica os filtros
materiais e a vigência pela competência, e rejeita sobreposições. O navegador
não escolhe a última versão pela data atual. Listas vazias/erros não carregam
fixtures. Na modalidade critério, a composição da fotografia aparece após
simular; a descrição de versão é a fornecida pelo backend.

ID, revisão, usuário, data e hash vêm do servidor. Recálculo requer motivo e
revisão corrente. Falha técnica/comunicação mantém a mesma operação e chave;
o usuário deve recuperá-la antes de alterar campos, título ou recomeçar.
O timeout do navegador não garante cancelamento da execução no servidor.

A seleção do NUFIN recebido na abertura é consultada automaticamente. A
pesquisa interna pode selecionar outro título somente fora de operação
pendente; o Java resolve e autoriza a nova origem. O marcador de auxiliar é
conferido no backend; a pesquisa não afirma mais que todos são origens.
Alternativas de consulta mantêm os filtros de provisão/parceiro ativo;
falhas de consulta não exibem detalhes técnicos do servidor.

Treino e edição/salvamento local de critérios não executam mutações no fluxo
real. Os botões permanecem no layout, desabilitados e com indicação de uso das
telas AD_RAT* para manutenção. Não foi criado CRUD adicional sob os três
comandos de simulação/recálculo/efetivação.

Efetivar não inventa NUFIN nem altera situação em memória. O contexto Java
expõe podeEfetivar=false e o botão permanece bloqueado. A implementação real
do serviço financeiro continua pendente, conforme escopo anterior; possuir a
classe de ação não significa possuir geração financeira homologada.

## Build e implantação

`node scripts/Build-Dashboard.cjs` usa esbuild0.25.12 local (ou RATEIO_ESBUILD)
para minificar os JS autorais e gera ZIP determinístico com manifesto SHA256.
Pode-se instalar as versões declaradas no package.json no ambiente de
desenvolvimento; aqui o binário foi obtido do pacote oficial e rastreado em
scripts/esbuild-source.json. React/fontes de produção são locais, sem CDN.
O script aceita RATEIO_PYTHON para o empacotador ZIP. O processo Gradle Java
mantém Java8 e SDK local existente. Não há atualização de SDK/JDK.

Entregas: `build/315_html5Component.zip` e `build/libs/ZAYA-BLM-V7.0.jar`.
Publicar o conteúdo do ZIP no componente315, mantendo diretórios relativos e
entryPoint RateioDespesas.jsp. Preencher os três IDs reais na ação de abertura.
Instalar o módulo e configurar o provider de backend/autorização e mapeamento
aprovado de base/competência. As onze instâncias já confirmadas são preservadas.
Essas ações de implantação não foram executadas nesta entrega.

## Validação e limites

107 cenários Java no build com SDK. Testes do bridge e do dashboard em Edge com
transporte simulado: NUFIN selecionado, origem canônica, simulação, sequência
de critério, motivo obrigatório, revisão, reenvio idêntico após falha, origem
auxiliar rejeitada e bloqueio de efetivação. Identidade do CSS original é
verificada pelo teste. React monta sem erros JavaScript. Screenshot em
build/ui/dashboard315.png contém exclusivamente fixtures de teste.

O teste de navegador não compila JSP/JSTL nem executa o serviço nativo no
Sankhya. Ainda é necessário homologar BASE_FOLDER, consultas auxiliares, ACL,
estrutura da linha NUFIN no transporte e persistência real no ambiente.
Não houve deploy, DML, publicação Git ou aceite funcional.

Rollback de implantação: restaurar ZIP/JAR anteriores e cadastros de ações,
preservando fotografias/eventos existentes; não excluir dados de simulação.
