# AD_VGFPROVEST - fonte recebida e transformação implementada

O documento funcional determina que compras futuras confirmadas e pendentes
(`TIPMOV = 'C'`) componham o saldo projetado nas views `AD_VGFPROVEST` e
`AD_VGFRESEST`.

A fonte completa foi recebida em 18/08/2026 e registrada em
`database/baseline/2026-08-18/views/AD_VGFPROVEST.sql`.

Transformação já implementada e reutilizável:

- o estoque futuro será agregado diretamente em `AD_VGFRESEST` e
  `AD_VGFPROVEST`, sem nova view ou função auxiliar;
- `AD_VGFRESEST`: soma `QTDFUTURA` ao estoque comercial antes de descontar reservas.

O DDL candidato está em `deploy/10_AD_VGFPROVEST.sql` e preserva as 22 colunas,
os filtros e as ligações atuais. O saldo passa a considerar:

```sql
estoque_comercial
+ estoque_futuro
- total_da_fila_ativa
- regra_legada_do_item_provisionado
- acumulado_da_fila_provisionada
```

O contrato agregado de `AD_VGFFRPROD` também foi preservado e recompilado após
as duas views de fila.
