# Playbook de Documento Sankhya

Use este checklist para qualquer demanda que envolva pedido, nota, transferencia, devolucao, faturamento, remessa, compra, venda, requisicao ou movimentacao interna.

## Checklist Obrigatorio

1. Qual documento esta sendo criado/alterado?
2. Qual TOP esta envolvida?
3. O movimento e pedido, nota, transferencia, devolucao, compra, venda, requisicao ou movimentacao interna?
4. Existe documento de origem?
5. Existe documento de destino?
6. Precisa criar ou preservar vinculo em `TGFVAR`?
7. Quais itens serao envolvidos?
8. Quantidade negociada, entregue, pendente ou faturada importa?
9. Ha impacto em estoque?
10. Ha impacto financeiro?
11. Ha impacto fiscal?
12. Ha confirmacao/liberacao envolvida?
13. Ha integracao externa dependendo do documento?
14. Ha campos `AD_` usados por regra customizada?
15. O projeto ja possui servico, Action ou helper para gerar documento?

## Regras Fortes

- Nao criar nota apenas inserindo `TGFCAB`/`TGFITE` sem validar fluxo Sankhya.
- Nao ignorar `TGFVAR` quando existe relacao origem/destino.
- Nao duplicar item sem validar quantidade pendente/faturada/entregue.
- Nao confirmar documento apenas alterando status/campo manualmente.
- Nao recalcular valores, impostos ou financeiro manualmente sem evidencia de padrao existente no projeto.

## Confirmado neste projeto

- A rotina atual nao cria documento novo; ela consulta documentos existentes e grava acompanhamento de cancelamento.
- A busca de Pedido de Venda usa `AD_VGLDOC` e `TIPMOV = 'P'`.

## A confirmar

- Se algum cancelamento futuro deve gerar documento de retorno, devolucao ou ajuste.
- Se o fluxo deve criar/preservar `TGFVAR` em etapa posterior.

