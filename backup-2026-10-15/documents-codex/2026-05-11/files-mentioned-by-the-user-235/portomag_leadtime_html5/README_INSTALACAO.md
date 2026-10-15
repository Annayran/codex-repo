# Dashboard HTML5/JSP - Lead Time

## Arquivos

- `dashboard_leadtime.jsp`
- `css/dashboardLeadTime.css`

## Parametros

```xml
<parameter id="PERIODO" metadata="datePeriod" required="true" />
<parameter id="CODPARC" metadata="entity:Parceiro@CODPARC" required="false" />
```

## Regra principal

- Periodo por `ITC.DTENTSAI`, data real da entrada.
- Fornecedor filtrado por `:CODPARC`.
- `USOPROD = C`: 15 dias.
- `USOPROD = M`: 30 dias.
- Percentual on-time = entregas no prazo / total de entregas * 100.
- Base auditavel em nivel fornecedor/pedido/entrada/produto/item.
