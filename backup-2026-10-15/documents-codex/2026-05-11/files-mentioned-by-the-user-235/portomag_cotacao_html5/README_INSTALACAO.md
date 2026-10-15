# Dashboard HTML5/JSP - Cotacao

## Arquivos

- `dashboard_cotacao.jsp`
- `css/dashboardCotacao.css`

## Parametros

```xml
<parameter id="PERIODO" metadata="datePeriod" required="true" />
<parameter id="DIASPRAZO" metadata="integer" required="true" default="7" />
```

## Regra principal

- Periodo por `TGFCAB.DTNEG`.
- TOPs `400` e `401`.
- Prazo por `:DIASPRAZO`.
- Pendencias calculadas ate `:PERIODO.FIN`, sem `SYSDATE`.
- Base auditavel em nivel `NUNOTA + SEQUENCIA`.
