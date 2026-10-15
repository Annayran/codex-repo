# Decisoes Tecnicas

## Decisoes Registradas

### 2026-06-23 - Contexto de IA no repositorio

**Decisao:**  
Centralizar contexto tecnico em `.ai/context`.

**Motivo:**  
Facilitar retomadas futuras e reduzir dependencia de historico de conversa.

**Impacto:**  
Novas interacoes devem ler primeiro `checkpoint.md`, `current-sprint.md`, `business-rules.md` e `known-issues.md`.

### 2026-06-23 - HTML5/JSP primeiro, backend depois

**Decisao:**  
Implementar o fluxo de suprimentos primeiro como simulacao em JavaScript dentro do `modalMomentoNegociacao.jsp`.

**Motivo:**  
Validar regra, visual e experiencia do usuario no HTML5 do Sankhya antes de gravar dados reais.

**Impacto:**  
O JSP demonstra bloqueios, popup de motivo, alteracao simulada de `PENDENTE` e inclusao simulada em `TGFACT`; a implementacao produtiva ainda deve ser feita em Java/DAO transacional.

### 2026-06-23 - Simulacao baseada na linha selecionada

**Decisao:**  
Remover seletor manual de cenario e derivar a simulacao do `NUNEGOCIACAO` recebido pela URL.

**Motivo:**  
Aproximar o teste da experiencia real do usuario no Sankhya.

**Impacto:**  
A tela passa a representar a negociacao selecionada, mesmo ainda usando dados mockados.

### 2026-06-23 - Preservar acompanhamento macro da negociacao

**Decisao:**  
Manter cards com status macro inspirados no dashboard de acompanhamento (`AD_TCRNEGCAB`): Suprimento, Layout, Producao, Faturamento e Entrega.

**Motivo:**  
O usuario precisa ver o contexto geral da negociacao antes da validacao especifica de suprimentos.

**Impacto:**  
O JSP combina dashboard executivo e detalhe operacional.

### 2026-06-23 - Documento relacionado via AD_VGLDOC

**Decisao:**  
Representar o documento relacionado como Pedido de Venda vindo da `AD_VGLDOC`, filtrando conceitualmente `TIPMOV = 'P'`.

**Motivo:**  
Alinhar o rastro visual com a view real usada pelo cliente.

**Impacto:**  
O rastro mostra Portal de Negociacao, Pedido de Venda, Nota Mov. Interna e Itens encontrados.

### 2026-06-23 - Zip HTML5 com JSP na raiz

**Decisao:**  
Sempre gerar `outputs/modal-momento-negociacao-html5.zip` contendo `modalMomentoNegociacao.jsp` diretamente na raiz.

**Motivo:**  
O componente HTML5 do Sankhya usa `entryPoint=modalMomentoNegociacao.jsp`.

**Impacto:**  
Toda alteracao no JSP exige regerar o zip antes de testar.

### 2026-06-23 - Backend real por Action Java

**Decisao:**  
Implementar a gravacao real como botao de acao Java Sankhya (`CancelarSuprimentoNegociacaoAction`), mantendo o JSP HTML5 como painel de apoio visual.

**Motivo:**  
O caminho de Action Java e mais seguro para transacao, parametro de motivo, usuario logado, update em `TGFITE` e inclusao em `TGFACT`.

**Impacto:**  
A primeira versao funcional deve ser cadastrada/testada no Sankhya como botao Java com parametro `P_MOTIVO`. A chamada direta do HTML5 para a Action fica para uma etapa posterior, quando o endpoint interno correto estiver confirmado.

### 2026-06-23 - JSP consulta Pedido de Venda real

**Decisao:**  
O JSP deve consultar o Pedido de Venda real pela `AD_VGLDOC` a partir do `NUNEGOCIACAO` da linha selecionada, exibindo apenas o `Nro. Unico` no card visual.

**Motivo:**  
Antes da gravacao real, o usuario precisa validar o comportamento visual com o documento real da negociacao selecionada.

**Impacto:**  
O card de Pedido de Venda nao usa mais NUNOTA mockado. Se a view nao retornar Pedido de Venda, a tela exibe `nao encontrado` e registra o erro tecnico no resumo.

### 2026-06-23 - Pedido de Venda sem limitar TOP 1001

**Decisao:**  
Consultar Pedido de Venda pela `AD_VGLDOC` usando a negociacao selecionada e `TGFCAB.TIPMOV = 'P'`, sem restringir `CODTIPOPER = 1001`.

**Motivo:**  
A view pode trazer diferentes TOPs de Pedido de Venda. A TOP deve ser exibida como informacao do documento, nao usada como criterio limitador.

**Impacto:**  
O Java e o JSP deixam de priorizar/filtrar `CODTIPOPER = 1001`. A selecao considera os documentos de venda retornados pela view.

### 2026-06-23 - Pedido de Venda plural e navegavel

**Decisao:**  
Tratar Pedido de Venda como lista de documentos vindos da `AD_VGLDOC`, pois a negociacao pode possuir mais de um pedido.

**Motivo:**  
A tela precisa refletir a realidade da linha selecionada: Pedido de Venda pode ser plural; Nota Mov. Interna permanece unica.

**Impacto:**  
O card de Pedido de Venda mostra todos os `Nro. Unico` retornados. Cada numero abre um modal com os itens reais da `TGFITE` daquela nota. No Java, `ResultadoSuprimentoDTO` passou a carregar lista de pedidos.

### 2026-06-24 - Regra de cancelamento tambem para Pedido de Venda

**Decisao:**  
Aplicar aos itens dos Pedidos de Venda a mesma regra usada nos itens da Nota Mov. Interna/Pedido de Compra.

**Motivo:**  
O fluxo produtivo precisa manter os documentos relacionados consistentes: se o item do Pedido de Venda possui saldo cancelavel, tambem deve permitir motivo, marcar `TGFITE.PENDENTE = 'N'` e registrar `TGFACT`.

**Impacto:**  
No JSP, o modal de itens do Pedido de Venda ganhou coluna de regra e botao de cancelamento item a item. No Java, `cancelarSuprimentos` passou a processar itens da Nota Mov. Interna e de todos os Pedidos de Venda retornados pela `AD_VGLDOC`.

### 2026-06-24 - Action pontual para item de Pedido de Venda

**Decisao:**  
Criar `CancelarItemPedidoVendaNegociacaoAction` para cancelar um item especifico de Pedido de Venda.

**Motivo:**  
O usuario pode acionar cancelamento item a item no Pedido de Venda. O backend precisa validar que o `NUNOTA` informado pertence a negociacao pela `AD_VGLDOC` antes de alterar `TGFITE` ou gravar `TGFACT`.

**Impacto:**  
A rotina exige `NUNEGOCIACAO` da linha selecionada, `P_NUNOTA_PEDVENDA`, `P_SEQUENCIA` e `P_MOTIVO`. A regra aplicada e a mesma do JSP: bloqueia item totalmente entregue, exige pendente `S`, altera `PENDENTE` para `N` e inclui acompanhamento.

### 2026-06-24 - UX orientada por decisao

**Decisao:**  
Manter a essencia tecnica da tela, mas colocar a decisao operacional em primeiro plano.

**Motivo:**  
O usuario precisa entender rapidamente se pode cancelar algo, quais itens tem saldo e por que os demais estao bloqueados.

**Impacto:**  
A tela ganhou bloco de decisao no topo, nomes mais humanos nos cards, coluna `SALDO` nas tabelas e hierarquia melhor de botoes.
