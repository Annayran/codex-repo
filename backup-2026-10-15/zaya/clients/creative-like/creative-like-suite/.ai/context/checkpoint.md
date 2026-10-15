# Checkpoint Tecnico

## Data

2026-06-23

## Estado Atual

O projeto `CreativeLike` contem a acao Java de consulta do momento da negociacao, um componente HTML5/JSP de apoio visual e uma primeira Action Java real para cancelamento de suprimentos a partir da negociacao selecionada.

Fluxo simulado no JSP:

```text
NUNEGOCIACAO selecionado
  -> Pedido de Venda relacionado via AD_VGLDOC, filtrando TIPMOV = 'P'
  -> Nota Mov. Interna / TGFCAB.NUNOTA
  -> Itens TGFITE por Nro. Unico da Nota
  -> Validacao de QTDENTREGUE x QTDNEG
  -> Simulacao de PENDENTE = 'N' e inclusao em TGFACT
```

O JSP permanece como painel de apoio/diagnostico. A gravacao real foi iniciada no backend Java, por botao de acao Sankhya transacional.

## Implementado no JSP

- Leitura do `nunegociacao` recebido pela URL do componente HTML5.
- Simulacao derivada do `NUNEGOCIACAO`, sem seletor manual de cenario.
- Cards de decisao: status de suprimentos, acao permitida, Nro. Unico da Nota, itens bloqueados, itens cancelaveis e acompanhamentos.
- Faixa de acompanhamento macro da negociacao baseada nos status esperados de `AD_TCRNEGCAB`: Suprimento, Layout, Producao, Faturamento e Entrega.
- Rastro funcional: Portal de Negociacao, Pedido de Venda, Nota Mov. Interna e Itens encontrados.
- Popup de motivo para item cancelavel.
- Bloqueio visual quando `QTDENTREGUE = QTDNEG`.
- Simulacao de inclusao em `TGFACT` com `OCORRENCIAS`, `REFERENCIA`, `DIGITADO` e `AD_ITEMCANCELADO`.
- Reforco UTF-8 no JSP para uso de acentos.
- Layout responsivo com tabelas em rolagem propria e painel lateral compacto.

## Implementado no modulo `momento-negociacao`

Diretorio principal:

```text
C:\zy-tools\workspace\Creative Like\momento-negociacao
```

Implementacao Java 11:

- `CancelarSuprimentoNegociacaoAction`: nova Action Java para cadastrar como botao de acao Sankhya.
- `CancelarItemPedidoVendaNegociacaoAction`: Action Java para cancelar um item especifico de Pedido de Venda vinculado a negociacao.
- Parametro obrigatorio da Action: `P_MOTIVO`.
- `MomentoNegociacaoService.cancelarSuprimentos(...)`: regra transacional para diagnosticar e cancelar itens com saldo.
- `MomentoNegociacaoService.cancelarItemPedidoVenda(...)`: regra transacional pontual para validar e cancelar item de Pedido de Venda.
- `MomentoNegociacaoDAO.buscarPedidoVendaRelacionado(...)`: busca Pedido de Venda pela `AD_VGLDOC`, filtrando apenas `TIPMOV = 'P'`; `CODTIPOPER` e exibido como informacao, nao como limitador.
- `MomentoNegociacaoDAO.buscarPedidoVendaRelacionado(nunegociacao, nunota)`: valida se o Pedido de Venda pertence a negociacao selecionada.
- `MomentoNegociacaoDAO.buscarNotaInterna(...)`: localiza nota em `TGFCAB.AD_NUNEGOCIACAO`.
- `MomentoNegociacaoDAO.buscarItensSuprimento(...)`: consulta `TGFITE` por `NUNOTA`.
- `MomentoNegociacaoDAO.buscarItemSuprimento(...)`: consulta item exato por `NUNOTA` e `SEQUENCIA`.
- `MomentoNegociacaoDAO.marcarItemComoNaoPendente(...)`: atualiza `TGFITE.PENDENTE = 'N'`.
- `MomentoNegociacaoDAO.incluirAcompanhamentoCancelamento(...)`: cria `AcompanhamentoNota`/`TGFACT` com `REFERENCIA`, `OCORRENCIAS`, `DIGITADO = 'S'` e `AD_ITEMCANCELADO = 'S'`.
- DTOs novos: `DocumentoGeradoDTO`, `NotaInternaDTO`, `ItemSuprimentoDTO`, `ResultadoSuprimentoDTO`.
- `MomentoNegociacaoAction` continua como consulta/diagnostico e passou a incluir resumo do fluxo real de suprimentos no HTML de retorno.
- `modalMomentoNegociacao.jsp` do modulo foi limpo para UTF-8 e reposicionado como painel operacional de apoio, sem gravacao direta no HTML5.
- `momentoNegociacaoModalButton.js` agora envia `modo=OPERACIONAL` e placeholder para a Action de cancelamento.

## JSP hibrido: simulacao realista + dados reais

O JSP voltou a manter a bancada rica de validacao/simulacao antes da gravacao real.

Regra atual da tela:

- ao abrir pelo HTML5, o JSP tenta consultar o Pedido de Venda real diretamente na `AD_VGLDOC` usando o `NUNEGOCIACAO` da linha selecionada;
- a consulta do Pedido de Venda usa `AD_VGLDOC` + `TGFCAB`, filtrando `CAB.TIPMOV = 'P'`, sem limitar `CODTIPOPER`;
- o card de Pedido de Venda agora suporta multiplos documentos, mostrando apenas os `Nro. Unico`;
- cada `Nro. Unico` de Pedido de Venda e clicavel e abre os itens reais daquela nota pela `TGFITE`;
- os itens do Pedido de Venda agora seguem a mesma regra dos itens da Nota Mov. Interna/Pedido de Compra: bloqueia quando `QTDENTREGUE = QTDNEG`, permite cancelamento quando `QTDENTREGUE <> QTDNEG`, muda `PENDENTE` para `N` na simulacao e gera linha em `TGFACT`;
- a Nota Mov. Interna permanece unica no fluxo, mas a regra de cancelamento passa a ser aplicada tambem aos Pedidos de Venda;
- para cancelamento pontual de Pedido de Venda no Java, usar `CancelarItemPedidoVendaNegociacaoAction` com `P_NUNOTA_PEDVENDA`, `P_SEQUENCIA` e `P_MOTIVO`; a Action valida o vinculo pela `AD_VGLDOC` antes de gravar;
- quando o Java/Sankhya enviar parametros como `pedidoVendaNunota`, `pedidoVendaCodtipoper`, `pedidoVendaTipmov`, `notaInternaNunota` e `notaInternaNumNota`, a tela usa esses dados como fonte real;
- quando a consulta real nao retorna Pedido de Venda, a tela mostra `Nro. Unico = nao encontrado` e registra o retorno tecnico no resumo;
- a selecao do Pedido de Venda filtra `TIPMOV = 'P'`; `CODTIPOPER` nao limita a busca;
- a validacao/cancelamento no JSP segue simulada, mas espelha a regra produtiva que sera executada em Java.

## Ajuste UX/UI

Foi adicionada uma camada de experiencia para reduzir carga cognitiva:

- bloco de decisao no topo indicando se o cancelamento esta disponivel, parcial, bloqueado ou sem itens;
- faixa de cards redundantes removida do topo para reduzir poluicao visual; o status fica concentrado no bloco de decisao, no painel lateral e nas tabelas;
- topo do JSP simplificado para exibir somente `Nro. Negociacao #(numero selecionado)`;
- campo visual de usuario removido; `CODUSU` da simulacao passa a ser preenchido internamente por `getusulogado`/variacoes do ambiente Sankhya, com fallback por parametro e `203` apenas para teste fora do ambiente;
- rastro renomeado para caminho ate os documentos, com etapas mais simples;
- tabela principal e modal de Pedido de Venda passaram a exibir `SALDO`, facilitando entender por que um item pode ou nao ser cancelado;
- botoes laterais reorganizados, com acao principal `Cancelar itens com saldo` e opcoes secundarias `Revalidar`, `Ver resumo tecnico` e `Limpar simulacao`;
- itens bloqueados passaram a usar `Detalhes`, pois o estado de bloqueio ja aparece na regra da linha.
- o JSP funcional recebeu o design system do prototipo visual: fundo cinza claro, shell arredondado, cards brancos com sombra suave, painel lateral mais largo, bloco de decisao com icone, tabelas com linhas mais espacadas, tags arredondadas e botoes com estilo operacional. A mudanca foi aplicada preservando IDs e funcoes JS existentes.
- a antiga area "Caminho ate os documentos" foi evoluida para uma tabela seletora de documentos relacionados. O usuario pode selecionar o `NUNOTA` de Pedido de Venda ou Movimentacao Interna, e a tabela de itens passa a exibir os itens (`TGFITE`) daquele documento selecionado. A regra de cancelamento e os botoes continuam os mesmos, mas agora operam sobre o documento ativo.
- o painel lateral passou a priorizar acoes utilitarias do processo: `Cancelar todos com saldo`, `Notificar areas`, `Registrar pendencia`, `Solicitar aprovacao` e `Limpar simulacao`. O cancelamento em massa abre um unico motivo e aplica a simulacao a todos os itens cancelaveis do documento selecionado.

## Arquivos Relevantes

- `prototipos/modal-momento-negociacao/html5-component/modalMomentoNegociacao.jsp`
- `prototipos/modal-momento-negociacao/botao-acao/momentoNegociacaoModalButton.js`
- `outputs/modal-momento-negociacao-html5.zip`
- `src/main/java/br/com/zaya/like/Actions/MomentoNegociacaoAction.java`
- `src/main/java/br/com/zaya/like/Actions/dao/MomentoNegociacaoDAO.java`
- `.ai/context/*`

## Analise do Java em Actions

Diretorio analisado:

```text
C:\zy-tools\workspace\Creative Like\src\main\java\br\com\zaya\like\Actions
```

Arquivos encontrados:

- `MomentoNegociacaoAction.java`
- `services/MomentoNegociacaoService.java`
- `dao/MomentoNegociacaoDAO.java`
- `dto/MomentoNegociacaoDTO.java`
- `dto/ItemMomentoNegociacaoDTO.java`

Entendimento tecnico:

- `MomentoNegociacaoAction` e a entrada Sankhya via `AcaoRotinaJava`.
- A action valida se existe linha selecionada e se a linha possui `NUNEGOCIACAO`.
- Para cada linha selecionada, chama `MomentoNegociacaoService.gerarHtmlMomento(nunegociacao)`.
- `MomentoNegociacaoService` busca dados no DAO e monta HTML de retorno com cards de status e resumo de itens.
- `MomentoNegociacaoDAO` usa `NativeSql` e `EntityFacadeFactory.getDWFFacade()` para consultar dados reais.
- A consulta principal cruza `AD_TGLCAB` com `AD_TCRNEGCAB` para obter cliente, vendedor, status da proposta e status macro: suprimento, layout, producao, faturamento e entrega.
- A consulta de itens usa `AD_TCRNEGITE`, `TGFPRO` e `AD_TGLITEART` para trazer itens principais, arte, produto, status de suprimento/producao e quantidades.
- Os DTOs de suprimentos foram criados no modulo `momento-negociacao`.

Direcionamento para backend futuro:

- Cadastrar e testar a nova Action `br.com.zaya.like.Actions.CancelarSuprimentoNegociacaoAction` em homologacao.
- Manter a regra transacional fora do JSP. O JSP permanece como tela/visualizacao; a gravacao real fica em Java com transacao, validacao e auditoria.
- Reaproveitar o padrao atual de `NativeSql`, named parameters, DTOs e tratamento de erro amigavel para o usuario.

## Workspace Organizado

Foi criada/copiada uma area de trabalho em:

```text
C:\zy-tools\workspace\Creative Like
```

Tambem foi clonado o repositorio:

```text
C:\zy-tools\workspace\Creative Like\PersonalizacoesCompraLike
```

Repositorio clonado:

```text
https://gitlab.com/zaya9450252/PersonalizacoesCompraLike.git
```

Branch atual do clone: `main`.

## O que precisa ser confirmado

- `nuGdg` real do componente HTML5.
- Teste do zip dentro do Sankhya em homologacao.
- Como obter `CODUSU` real no backend.
- Formato definitivo de `DHOCOR` e `HRACT` em `TGFACT`.
- Se `REFERENCIA = 'R'`, `DIGITADO = 'S'` e `AD_ITEMCANCELADO = 'S'` serao sempre fixos.
- Confirmar em homologacao se a query de `AD_VGLDOC` deve exigir sempre `CODTIPOPER = 1001` ou se deve aceitar outros pedidos de venda `TIPMOV = 'P'` como fallback.
- Confirmar se `REFERENCIA = 'R'`, `DIGITADO = 'S'` e `AD_ITEMCANCELADO = 'S'` serao fixos em producao.

## Proxima Melhor Acao

Cadastrar a Action `CancelarSuprimentoNegociacaoAction` no Sankhya com parametro `P_MOTIVO`, testar em homologacao com negociacao real e comparar `TGFITE`/`TGFACT` antes e depois.
