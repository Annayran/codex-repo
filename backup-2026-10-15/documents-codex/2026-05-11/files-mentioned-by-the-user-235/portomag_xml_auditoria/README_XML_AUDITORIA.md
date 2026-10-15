# XMLs nativos de auditoria - Portomag

Arquivos:

- `cotacao_auditoria.xml`
- `leadtime_auditoria.xml`

## Cotacao

Alteracoes aplicadas:

- Mantida estrutura nativa do gadget.
- TOPs `400` e `401`.
- Removido `TRUNC(SYSDATE)` dos calculos historicos.
- Data de corte para pendencias: `:PERIODO.FIN`.
- Detalhamento por `NUNOTA + SEQUENCIA`.
- Status corrigido para `Em andamento`.

## Lead Time

Alteracoes aplicadas:

- Mantida estrutura nativa do gadget.
- Periodo por data real de entrada `ITC.DTENTSAI`.
- Visao por fornecedor com total, no prazo, fora do prazo, percentual on-time e lead time medio.
- Detalhamento por pedido/entrada/produto/item.
- Prazo `USOPROD = C`: 15 dias.
- Prazo `USOPROD = M`: 30 dias.
