# Checklist Antes de Codar

## 1. Entendimento

- [ ] Entendi a demanda?
- [ ] Identifiquei o processo Sankhya envolvido?
- [ ] Sei se e pedido, nota, financeiro, estoque, parceiro, produto, TOP, Flow, dashboard ou integracao?
- [ ] Existem regras nao confirmadas?

## 2. Impacto

- [ ] Impacta `TGFCAB`?
- [ ] Impacta `TGFITE`?
- [ ] Impacta `TGFVAR`?
- [ ] Impacta `TGFFIN`?
- [ ] Impacta `TGFEST`?
- [ ] Impacta `TGFTOP`?
- [ ] Impacta tabelas `AD_`?
- [ ] Impacta integracao externa?

## 3. Seguranca

- [ ] Ha transacao?
- [ ] Ha risco de alteracao parcial?
- [ ] Ha rollback em caso de erro?
- [ ] Recursos sao fechados corretamente?
- [ ] Existe risco de duplicidade?

## 4. Dados

- [ ] Usei bind parameters?
- [ ] Preservei aliases?
- [ ] Tratei `BigDecimal`?
- [ ] Tratei `null`?
- [ ] Evitei SQL em loop?

## 5. Documento Sankhya

- [ ] Existe documento origem?
- [ ] Existe documento destino?
- [ ] Precisa `TGFVAR`?
- [ ] Quantidade pendente/faturada/entregue foi considerada?
- [ ] TOP foi validada?
- [ ] Estoque/financeiro/fiscal foram avaliados?

## 6. Entrega

- [ ] Listei arquivos alterados?
- [ ] Expliquei riscos?
- [ ] Sugeri testes manuais?
- [ ] Atualizei checkpoint?

