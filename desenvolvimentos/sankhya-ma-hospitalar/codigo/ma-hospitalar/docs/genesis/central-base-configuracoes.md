# Central React: base instalada e configuracoes

## Organizacao

A navegacao Base instalada consolida Clientes, Dominios, Empresas, Itens
instalados, Materiais, Modelos e Setores. A aba Geral nao foi reproduzida.
O antigo menu Cadastros foi incorporado a essa area, evitando duas navegacoes
para as mesmas tabelas. Empresas exibe os indicadores de fabricante e fornecedor.
Dominios consulta AD_GENDOMINIO, com propriedade e situacao separadas pelo tipo.

Filtros especificos: produto, serie e cliente em itens; tipo e nome nos dominios;
codigo Sankhya, nome e codigo externo nos demais cadastros. Estado remoto e
conciliacao local sao filtros e informacoes distintos. As contagens das abas
representam apenas o recorte carregado, nao totais globais.

Configuracoes da integracao substitui Escopo configurado:

- Parametros: presenca da Central NUPAR=1 e preenchimento da URL/credencial.
  O SQL retorna apenas a situacao, nunca os valores sensiveis.
- Referencias da versao: valores lidos do codigo deste pacote, identificados
  como referencia, sem alegar que refletem a versao do JAR instalado.
- Empresas e locais: codigos e descricoes do escopo; inclui TOPs parametrizadas.
- Regras de processamento: condicoes verificadas nos repositorios/servicos.
- A aba Execucoes foi removida por solicitacao do usuario. Historico permanece
  na area Requisicoes, separado das configuracoes.

## Periodo do grafico e nomenclatura

O grafico diario consulta os ultimos 90 dias, incluindo hoje, pela data do banco.
Oferece filtros de 7, 30, 90 dias e datas personalizadas dentro desse intervalo.
Selecao inclusiva das duas datas, com validacao de ordem e limites. O filtro altera
somente o grafico, nao os indicadores de movimentos. Recarregar consulta novamente
os dados; alternar periodos usa os agregados ja carregados, sem nova chamada remota.

As barras animam ao trocar o intervalo. A preferencia de movimento reduzido do
sistema e respeitada. Layout validado com 90 dias no celular.

Menu padronizado como Configuracoes, com alinhamento igual aos demais itens.
Textos de configuracoes, regras e origens usam nomes de telas e campos do cliente,
nao nomes fisicos de tabelas, colunas ou classes Java. Exemplos: Central de
Parametros, Produtos, Codigo Produto Protheus, Tipos de Operacao e Itens instalados.
Evidencias originais de requisicao/resposta e logs nao sao reescritas no banco.

## Seguranca e limites

Componente somente leitura, com quinze consultas SELECT. Nenhum servico Java,
regra de envio, dependencia, campo ou permissao foi alterado. Nao chama a API,
nao grava dados, nao permite reprocessamento manual e nao disponibiliza token
ou URL interna ao navegador. Apenas o tema e armazenado no localStorage.

Os registros e filtros permanecem limitados a 100 registros por area. A pesquisa
completa e paginacao no banco ainda nao foram implementadas. O limite aparece na
interface; nao concluir ausencia na base a partir da ausencia no recorte.
Frequencia e proxima execucao do scheduler nao sao inventadas nem estimadas.

## Deploy, validacao e rollback

Pacote atual: prototypes/genesis-workspace/dist/MAHospitalar-Genesis-React-Periodos-Interface.zip.
Contem somente index.jsp com React e CSS compilados, sem CDN.

1. Exportar e guardar o ZIP anterior do componente React.
2. Importar o novo ZIP no mesmo componente de homologacao. Nao substituir o
   dashboard JSP original por engano. Esta entrega nao requer outro JAR.
3. Validar compilacao JSP e permissoes nas tabelas AD_GENDOMINIO, AD_GENEMPRESA,
   AD_CENTPARAM, AD_CENTPARAMEMP, AD_CENTPARAMLOC, AD_CENTPARAMTOP, TSIEMP e TGFLOC.
4. Conferir as sete abas e um cadastro real por tipo. Comparar o escopo com a
   Central NUPAR=1, sem divulgar valores de URL ou credencial.
5. Validar estados vazio/erro, filtros, detalhes e temas claro/escuro.

Rollback: reimportar o ZIP anterior. Nao ha DDL/DML de migracao para desfazer.

Validacao local: build esbuild e testes Playwright no Edge, com dados sinteticos,
desktop/mobile, estados de erro/ausencia, XSS, temas, filtros, navegacao e ausencia
de chamadas externas. Compilacao JSP/Oracle e permissoes reais exigem homologacao
no Sankhya; testes locais nao comprovam essas etapas.
