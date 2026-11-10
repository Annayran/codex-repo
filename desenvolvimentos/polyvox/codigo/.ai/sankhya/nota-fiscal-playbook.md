# Playbook de Documentos Sankhya

Use este guia para demandas que envolvam pedido, nota, transferencia, devolucao, faturamento, remessa, compra, venda, requisicao ou movimentacao interna.

## Checklist Antes de Codar

1. Qual documento esta sendo criado ou alterado?
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
15. O projeto ja possui servico, action ou helper para gerar documento?

## Regras Fortes

- Nao criar nota apenas inserindo `TGFCAB`/`TGFITE` sem validar fluxo Sankhya.
- Nao ignorar `TGFVAR` quando existe relacao origem/destino.
- Nao duplicar item sem validar quantidade pendente, faturada ou entregue.
- Nao confirmar documento apenas alterando status/campo manualmente.
- Nao recalcular valores, impostos ou financeiro manualmente sem evidencia de padrao existente no projeto.
- Nao atualizar documento confirmado sem entender impacto fiscal, financeiro e de integracao.

## Aplicacao no Repositorio Atual

Confirmado:

- A frente de compensacao usa devolucoes com `CODTIPOPER IN (1200, 1201)`.
- A relacao entre devolucao e origem esta documentada como `TGFVAR.NUNOTA = devolucao` e `TGFVAR.NUNOTAORIG = origem`.
- A compensacao financeira e identificada por `TGFFIN.NUNOTA = NUNOTADEV` e `NUCOMPENS IS NOT NULL`.

A confirmar:

- Se a nota de origem tambem deve interferir no status de compensacao em algum cenario.
- Usuarios finais e periodicidade em producao.
