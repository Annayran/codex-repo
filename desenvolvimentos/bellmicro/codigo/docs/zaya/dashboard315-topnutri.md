# Dashboard315 — base TOP Nutri, versão7.3

Atualização vigente: [dashboard7.4 / Java7.2](financeiro-catalogos.md) restaura
catálogos nativos e enriquece a ficha pelo Java. A orientação abaixo de manter
Java7.1 e digitar códigos sem sugestões descreve somente a versão7.3.

Solicitação: após erro HTTP500 no cliente, adotar a base do modal TOP Nutri,
mantendo a identidade visual do rateio e os comandos Java existentes.

## Diferença técnica e limite do diagnóstico

O JSP da TOP Nutri usa apenas a diretiva de página UTF8 e HTML/CSS/JS. Não
depende de JSTL, TLD Sankhya, snk:load ou SQL para desenhar o modal. O JSP do
rateio tinha essas dependências. Sem o corpo/log da resposta HTTP500 não foi
possível determinar qual delas falhou no ambiente. O diagnóstico anterior
sobre React/AMD explica um cenário de tela branca no navegador, não um erro
HTTP500 de processamento servidor.

## Implementação vigente

RateioDespesas.template.jsp é a fonte da página simples. Build-Standalone.cjs
incorpora o CSS original, fontes locais em data URI e JS minificado no JSP
final. As fontes continuam separadas em web/css, web/js, vendor e módulos de
transporte/estado. Não editar manualmente o JSP gerado. React isolado permanece
no ciclo de vida da view, sem requisitar runtime externo nem carregar o SDK
HTML5 inteiro para abrir a página.

O build também preserva os arquivos separados no ZIP para rastreabilidade e
manutenção. A página final não usa esses caminhos: não possui BASE_FOLDER,
links de CSS, scripts externos ou URLs de fontes. Não contém SQL, taglibs ou
scriptlets Java. O CSS principal foi preservado integralmente do ZIP original.

Abertura permanece pela ação JavaScript do Financeiro: nuGdg315, entryPoint
RateioDespesas.jsp, IDs reais das ações e NUFIN selecionado. O transporte de
ações segue o padrão TOP Nutri; os dados e validações são do Java do rateio.
JAR7.1, FQCNs e IDs existentes permanecem inalterados.

Sem as consultas do JSP, empresa e CR são informados por código, com validação
no Java. Critérios aplicáveis vêm do backend. A consulta interna de outro título
recebe NUFIN e usa CONSULTAR da ação Simular, respeitando origem/autorização.
Os filtros avançados de fornecedor/nota/vencimento do protótipo não executam
mais SQL no JSP; ampliar essa pesquisa exige serviço Java específico. Nenhum
cadastro ou dado de exemplo substitui consultas reais. O desenho principal,
cores, tipografia, passos e painéis permanecem; o formulário da pesquisa passa
a ser por NUFIN. Isso é uma alteração de interação explicitada, não identidade
visual nova.

## Validação e artefato

`node scripts/Build-Dashboard.cjs` gera build/315_html5Component.zip com
dashboardVersion7.3 / moduleVersion7.1. Para conferir compilação JSP:
preparar ferramentas locais com scripts/Prepare-JspCompiler.py e executar
scripts/Test-Jsp.ps1. Jasper9.0.98/ECJ3.38 compilaram a página final para Java8,
sem erros. As ferramentas ficam em build e não são empacotadas em produção.
Esse teste verifica tradução, sintaxe e limites de tamanho do JSP, incluindo
fontes e scripts incorporados; não representa o servidor Sankhya do cliente.

Test-Dashboard.cjs serve o JSP final integral, removendo somente a diretiva de
página para o navegador local. Valida AMD, abertura direta, parâmetro inválido,
contingência sem JavaScript, contexto canônico, consulta por NUFIN, critério,
simulação/recálculo, revisão/motivo, reenvio e bloqueio de efetivação. Transporte
Java simulado. O teste não monta outra página para substituir a estrutura real.

Publicar o novo ZIP no componente315 e manter o JAR7.1. Não houve deploy remoto
nesta entrega. Se HTTP500 persistir antes de mostrar conteúdo, coletar o corpo
da resposta/log servidor e conferir componente/entryPoint: não atribuir nova
causa sem evidência. Provider de autorização/base/competência e efetivação real
continuam com as pendências já documentadas; um erro funcional deve aparecer
após a página abrir, sem ser apresentado como sucesso financeiro.
