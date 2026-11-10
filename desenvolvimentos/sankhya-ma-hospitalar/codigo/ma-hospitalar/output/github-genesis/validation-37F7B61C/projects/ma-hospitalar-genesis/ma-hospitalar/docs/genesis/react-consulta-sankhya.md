# React no componente BI: fase 1, somente leitura

Entrega de 20/10/2026. Pacote separado do prototipo ficticio e do componente 160.
Homologar no 187 ou outro componente restrito de testes, preservando ZIP anterior.
Nenhuma implantacao ou consulta ao banco do cliente foi executada pelo agente.

## Arquitetura desta fase

JSP com snk:load, snk:query e JSTL (padrao do dashboard existente) executa SELECTs
fixos na abertura. Campos sao escapados com c:out e transportados em elementos
ocultos. React le textContent e remove esse bootstrap; renderiza strings escapadas.
Nao ha endpoint novo, SQL recebido do navegador, credencial Genesis, fetch remoto,
edicao, conciliacao com escrita, configuracao administrativa ou disparo manual.
JAR e agendamentos existentes nao mudam. Fase posterior exige servicos Java
autenticados, autorizacao por operacao e transacoes antes de habilitar gravacoes.

Fonte de dados:
- Indicadores: ultimo AD_GENMOVEST por CHAVE_MOVIMENTO, excluindo API|; total
  completo, enviados, erros/revisao/incerto e pendente/reprocessar separados.
  Cancelados e outros estados podem integrar o total sem integrar essas parcelas.
- Envios: ate 100 estados recentes de AD_GENMOVEST, mesma deduplicacao.
- Base instalada: ate 100 itens IDBASE=1, com TGFPRO e TGFPAR.
- Estoque: ate 100 controles AD_GENESTCTRL com TGFPRO. Nao calcula novo saldo ou
  preco: mostra somente o controle existente, sem alegar valor atual da tabela 101.
- Cancelamentos: ate 100 controles AD_GENEXCFILA.
- Historico: ate 100 evidencias AD_GENREQLOG. Refinamento da central inclui
  previas ja sanitizadas pelo integrador de requisicao/resposta (4.000 caracteres)
  e aplicacao local (2.000). Conteudo exibido como texto, nunca HTML executavel.
- Escopo: ate 100 codigos empresa/local no NUPAR=1. Sem URL/token/config sensivel.
- Cadastros: ate 100 registros IDBASE=1 por entidade: AD_GENCLIENTE, AD_GENSECTOR,
  AD_GENMATERIAL, AD_GENEMPRESA e AD_GENMODELO, com IDs/vinculos e estado conciliado.
- Grafico diario: sete dias de AD_GENREQLOG, HTTP 2xx versus HTTP>=400.
  Nao mistura sucesso HTTP com identidade/persistencia ou sucesso de envio.

Cada consulta isolada com c:catch. Erro de estrutura ou acesso aparece como fonte
indisponivel, sem substituir por demo/zero inventado. Controles vazios sao distintos
de erro. Filtros e paginacao atuam somente no recorte carregado, informado na tela.
Atualizar recarrega o JSP e todas as consultas; sem polling automatico.
Dados reais nao sao gravados em localStorage; apenas preferencia de tema.

## Acesso e riscos

Restringir o componente BI ao grupo de homologacao autorizado. snk:query nao e
garantia de aplicar automaticamente filtros/permissoes das telas nativas para
cada linha. Nesta fase, as consultas tem escopo fixo; revisar se o grupo pode ver
todos os movimentos e a base IDBASE=1. Nao liberar a todos os usuarios sem definir
e implementar autorizacao/escopo por usuario na camada de servico.
Cliente/serie e corpos HTTP sao dados operacionais: manter acesso interno e evitar
exportacoes. Antes da liberacao, revisar a sanitizacao dos registros historicos.
Mensagens do controle sao exibidas como texto, sem HTML executavel; registros
antigos devem ser revisados quanto a conteudo sensivel antes da disponibilizacao.

## Build e homologacao

Fontes em prototypes/genesis-workspace: src/readonly.jsx, sankhya-readonly.jsp,
build-readonly.cjs. React/Lucide/CSS compilados e embutidos no index.jsp do ZIP.
isELIgnored=false e necessario para JSTL; build desabilita template literals JS e
recusa bundle que contenha ${ para evitar colisao com JSP EL.

1. Backup do ZIP atual do componente de testes.
2. Importar MAHospitalar-Genesis-React-Somente-Leitura.zip no HTML5 do BI.
3. Revisar autorizacao do componente antes de abrir com usuarios de negocio.
4. Abrir preview e confirmar ausencia de erro JSP/Oracle e fontes indisponiveis.
5. Comparar totais com o dashboard 160 e consultas existentes no mesmo instante.
6. Conferir detalhes, caracteres especiais, filtros por serie, paginacao, temas
   e escopo NUPAR=1. Conferir tabela ausente em ambiente de teste, sem DDL real.
7. Confirmar ausencia de controles de escrita e de requisicoes diretas a Genesis.

Validacao local: compilacao React e testes Playwright com bootstrap sintetico,
treze consultas SELECT-only, XSS, falta de fontes, ausencia de fallback demo,
filtros/paginacao, mobile, temas e ausencia de armazenamento de registros.
Compilacao JSP, Oracle, sessao, privilegios e comportamento do snk:load exigem
homologacao no ambiente real. Nao afirmar validacao de banco por testes locais.
Rollback: restaurar ZIP anterior; nenhuma migracao, DML ou rollback de dados.

## Experiencia restaurada

Retomada da composicao aprovada do prototipo: metricas, grafico diario real,
indicador circular, ultimos equipamentos e identidade MA Hospitalar.
Cadastros com abas por entidade. Conciliacao e diagnostico mostra cards dos
movimentos pendentes/em revisao no recorte, sem acoes de escrita.
Detalhes em abas Resumo, Vinculos, Historico e JSON. Evidencias relacionadas
somente por chave exata de movimento ou ID com endpoint da mesma entidade.
Candidatos locais comparados por produto/serie, sem eleicao automatica.
Ausencia no recorte nao prova ausencia no banco ou na Genesis.
Pacote refinado: MAHospitalar-Genesis-React-Central-Operacional.zip.
Nao habilita formularios de cadastro, conciliacao gravada ou configuracoes
administrativas sem a futura camada de servicos autorizados/auditados.


---
Copia de consulta para entrega; fonte canonica local: `ma-hospitalar/docs/genesis/react-consulta-sankhya.md`. Sanitizacao de caminhos e enderecos aplicada; nao representa homologacao.
