# Sprint / Ciclo Atual

## Objetivo Atual

Transformar o fluxo de suprimentos validado no JSP em botao de acao Java funcional no Sankhya.

## Escopo Atual

- JSP HTML5 como painel de apoio operacional.
- Action Java real para gravar `TGFITE` e `TGFACT`.
- Fluxo baseado no `NUNEGOCIACAO` da linha selecionada.
- Documento relacionado vindo da `AD_VGLDOC`, filtrando Pedido de Venda apenas por `TIPMOV = 'P'`.
- Validacao de itens pela regra `QTDENTREGUE` x `QTDNEG`.

## Proximas Acoes

- Cadastrar `br.com.zaya.like.Actions.CancelarSuprimentoNegociacaoAction` como botao de acao.
- Cadastrar `br.com.zaya.like.Actions.CancelarItemPedidoVendaNegociacaoAction` como botao de acao para cancelamento pontual de item do Pedido de Venda.
- Criar parametro `P_MOTIVO` no botao.
- Criar parametros `P_NUNOTA_PEDVENDA` e `P_SEQUENCIA` no botao pontual do Pedido de Venda.
- Preencher o `nuGdg` real no botao JavaScript.
- Testar com negociacoes reais.
- Validar gravacao em `TGFITE.PENDENTE` e inclusao em `TGFACT`.
- Ajustar query se a nota interna exigir filtro adicional de TOP/TIPMOV.
- Manter no JSP a leitura real do Pedido de Venda pela `AD_VGLDOC`; a simulacao fica apenas para a regra de cancelamento/validacao antes da gravacao.
- Validar no JSP negociacoes com mais de um Pedido de Venda, clicando em cada `Nro. Unico` e conferindo os itens da `TGFITE`.
- Validar cancelamento item a item tambem nos Pedidos de Venda, conferindo atualizacao de `PENDENTE` e inclusao de `TGFACT`.
- Validar a nova UX em homologacao, principalmente se o bloco de decisao e a coluna `SALDO` deixam claro o que o usuario pode fazer.

## Bloqueios

- Falta `nuGdg` real.
- Falta teste integrado no Sankhya.
- Endpoint/chamada direta do HTML5 para Action Java ainda nao foi confirmado; por enquanto a execucao real fica no botao Java Sankhya.

## Riscos

- Rodar a Action real em producao sem homologacao previa.
- Divergencia na regra de identificacao da Nota Mov. Interna em `TGFCAB.AD_NUNEGOCIACAO`.
- Encoding do ambiente Sankhya pode exigir ajuste adicional mesmo com UTF-8 reforcado no JSP.

## Observacoes

Atualizar este arquivo ao final de cada sessao relevante.
