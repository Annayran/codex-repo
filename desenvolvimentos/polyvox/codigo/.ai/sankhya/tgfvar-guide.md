# Guia TGFVAR

## Proposito

`TGFVAR` representa vinculos entre documento de origem e documento de destino. Ela e critica para rastreabilidade, baixa de pendencias, faturamento, devolucao, transferencia, remessa, cancelamento e relatorios que localizam documentos relacionados.

## Quando Investigar

- Faturamento de pedido.
- Geracao de nota a partir de pedido.
- Transferencia.
- Devolucao.
- Remessa.
- Cancelamento com documento relacionado.
- Consulta de documentos pai/filho.
- Baixa, pendencia, entrega ou quantidade faturada.
- Dashboards, Jasper, JSPs ou integracoes que consultam origem/destino.

## Checklist

- Existe `NUNOTA` de origem?
- Existe `NUNOTA` de destino?
- Existe `SEQUENCIA` de origem?
- Existe `SEQUENCIA` de destino?
- A quantidade vinculada esta correta?
- O documento destino foi gerado a partir do documento origem?
- A consulta depende de `TGFVAR` para localizar documentos relacionados?
- Ha regra de cancelamento/faturamento usando quantidade pendente ou entregue?

## Riscos

- Documento destino sem rastreabilidade.
- Pedido permanecendo pendente indevidamente.
- Quantidade entregue/faturada incorreta.
- Cancelamento ou devolucao sem localizar documento relacionado.
- Dashboard ou relatorio exibindo resultado incompleto.
- Integracao externa enviando dados duplicados ou faltantes.

## No Repositorio Atual

Confirmado pela documentacao e SQL:

- Na compensacao financeira de devolucoes, `TGFVAR.NUNOTA` aponta para a nota de devolucao.
- `TGFVAR.NUNOTAORIG` aponta para a nota de origem.

A confirmar no projeto/banco:

- Campos exatos de sequencia e quantidade utilizados pelas rotinas atuais.
- Se ha joins adicionais por `SEQUENCIA`/`SEQUENCIAORIG` nas rotinas produtivas.
- Se outros fluxos usam `TGFVAR` para atualizar pendencia ou entrega.
