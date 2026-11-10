# Decisões Técnicas

## 2026-08-24 - Consolidar em itens-materiais

**Decisão:** o destino da Base Instalada é `/itens-materiais/`; `/estoques/` não
faz parte deste fluxo.

## 2026-08-24 - Reutilizar a Central existente

**Decisão:** usar `AD_CENTPARAM` e `AD_CENTPARAMTOP`, `NUPAR=1`, apenas para validar
a Central e as TOPs já cadastradas.

## 2026-08-24 - Arquitetura mínima aprovada

**Decisão:** usar somente `AD_VW_GENESIS_ESTOQUE`, `AD_GENMOVEST`, Central e campos
da série. IDs de dependências são resolvidos pela API durante a execução.

## 2026-08-25 - Configuração temporária de homologação

**Decisão:** para reproduzir o fluxo de teste anterior sem depender da
configuração da JVM, manter temporariamente no Java a URL de homologação, epoch
`MS`, integração ativa, envio real e lote `1`. Ler o token exclusivamente do
campo restrito `AD_CENTPARAM.TOKENGENESIS`, registro `NUPAR=1`.

**Impacto:** a solução é própria para homologação. Antes do Go Live, revisar a
gestão da credencial e remover a configuração temporária fixa.

## 2026-08-24 - Simulação reversível

**Decisão:** movimentos `SIMULADO` não são repetidos enquanto o modo simulação
estiver ativo, mas voltam a ser elegíveis quando o modo real for habilitado.

## 2026-08-27 - Importar a Base Instalada sem tabela de estagio

**Decisao:** ler a planilha no componente HTML5 e enviar lotes de ate 250 linhas
para uma acao Java associada a `GENBASE`. O servidor mantem uma allowlist fixa e
faz upsert por `IDBASE + IDGENESIS` nas entidades adicionais.

**Motivo:** eliminar a execucao manual de milhares de INSERTs e evitar uma nova
tabela usada somente como transporte de JSON.

**Impacto:** a importacao pode ser retomada sem duplicar registros. Os IDs da
acao e do componente devem ser configurados apos o cadastro no Sankhya.

## 2026-08-27 - Empacotar a leitura XLSX localmente

**Decisao:** incluir SheetJS 0.18.5 e sua licenca no ZIP do componente, sem
carregar JavaScript de CDN durante a execucao.

**Motivo:** tornar a entrega reproduzivel e evitar indisponibilidade externa.

## 2026-08-27 - Abrir o importador diretamente pelo Gadget

**Decisao:** durante a homologacao, o JSP usa diretamente `IDBASE=1` e a acao
Java `123`. O fluxo nao depende mais de uma acao JavaScript para abrir o modal.

**Motivo:** o Gadget `171` ja esta publicado e abre corretamente no Sankhya.

**Impacto:** o ZIP fica especifico para a base e a acao atuais. Alteracoes nesses
identificadores exigem gerar e publicar uma nova versao do JSP.

## 2026-08-27 - Resolver dependencias na Base Instalada e enviar somente o item

**Decisao:** a acao agendada nao cria mais cliente, setor, material, empresa ou
modelo na API. Ela resolve cliente, setor e modelo nas tabelas importadas e
conciliadas, usando `TGFPRO.AD_IDEXTERNO` como vinculo do produto com
`AD_GENMODELO.CODIGOEXTERNO`, e sincroniza somente `/itens-materiais/`.

**Motivo:** a Genesis ja forneceu os IDs mestres na Base Instalada. Recriar a
arvore pela API gerava duplicidade, retrabalho e perda do vinculo com o legado.

**Impacto:** a carga e a conciliacao passam a ser pre-requisitos do agendador.
Item existente usa `PUT` pelo `IDGENESIS`; item novo usa GET/POST e o ID retornado
e registrado em `AD_GENITEMMAT`. A decisao de 2026-08-24 que resolvia IDs pela API
fica superada para o fluxo agendado.

## 2026-08-27 - Reprocessamento usa transacao gerenciada

**Contexto:** a ação manual apresentou `You cannot rollback during a managed transaction`.

**Decisão:** remover `setAutoCommit`, `commit` e `rollback` próprios da ação.
Ela mantém a sessão necessária para atualizar a fila e propaga falhas para o
contêiner decidir o rollback.

**Motivo:** evitar conflito entre o controle transacional da ação e o controle
do ambiente Sankhya.

**Impacto:** o cadastro da ação deve manter o controle manual desabilitado. O
novo JAR deve ser publicado antes do próximo teste de reprocessamento.

## 2026-08-28 - Separar movimento, tipo e poder do estoque

**Contexto:** `TIPO` já representa entrada ou saída, mas a análise também precisa
distinguir a propriedade e quem mantém fisicamente o estoque.

**Decisão:** preservar `TIPO` conforme a `TGFESE`, preservar `PODER` conforme
`TGFEST.CODPARC` e criar `TIPO_ESTOQUE` para a comparação de CNPJ. Quando o
CNPJ pertencer à `TSIEMP`, o tipo será `PROPRIO`; caso contrário, `TERCEIRO`.

**Motivo:** evitar aliases duplicados e representar separadamente movimento,
propriedade e posse.

**Impacto:** a view deve ser publicada antes do novo dashboard. Documento
ausente fica como `NAO_CLASSIFICADO`; a comparação não altera movimento ou
poder.

## 2026-08-27 - Consolidar equipamento por numero unico e serie

**Decisao:** dashboard e agendador consideram uma unica linha por
`NUNOTA + SERIE` e selecionam somente o registro mais recente da
`AD_GENMOVEST` para cada `CHAVE_MOVIMENTO`.

**Motivo:** a view e o historico de tentativas podem multiplicar visualmente e
operacionalmente o mesmo equipamento.

**Impacto:** repeticoes deixam de ser exibidas e reenviadas. A causa dos JOINs
duplicadores na view fisica continua pendente de ajuste no Oracle.
