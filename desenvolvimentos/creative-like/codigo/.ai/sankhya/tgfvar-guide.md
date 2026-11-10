# Guia TGFVAR

## Proposito

`TGFVAR` registra o vinculo entre documento de origem e documento de destino. E essencial para rastreabilidade, faturamento, devolucao, remessa, transferencia e geracao de documento a partir de outro.

## Quando investigar

- Faturamento de pedido.
- Devolucao vinculada a venda/compra.
- Remessa ou retorno.
- Transferencia entre documentos.
- Baixa parcial ou total.
- Consulta que precisa localizar documentos relacionados.
- Cancelamento que depende de quantidade pendente, faturada ou entregue.

## Checklist

- Existe `NUNOTA` de origem?
- Existe `NUNOTA` de destino?
- Existe `SEQUENCIA` de origem?
- Existe `SEQUENCIA` de destino?
- A quantidade vinculada esta correta?
- O documento destino foi gerado a partir do origem?
- A consulta depende de `TGFVAR` para localizar documentos relacionados?
- Ha regra de cancelamento/faturamento usando quantidade pendente ou entregue?

## Riscos

- Documento destino nao aparecer em telas de rastreio.
- Quantidade pendente/faturada incorreta.
- Cancelamento, baixa ou faturamento inconsistentes.
- Dashboard ou relatorio localizar documento errado.

## Confirmado neste projeto

- O fluxo atual localiza Pedido(s) de Venda via `AD_VGLDOC`, nao via `TGFVAR`.
- A rotina atual nao cria documento destino.

## A confirmar no projeto/banco

- Campos exatos de `TGFVAR` usados por este cliente.
- Se algum relatorio de negociacao depende de `TGFVAR`.

