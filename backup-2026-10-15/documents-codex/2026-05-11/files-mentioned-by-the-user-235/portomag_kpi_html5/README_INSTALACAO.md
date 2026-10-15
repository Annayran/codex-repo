# Dashboard HTML5/JSP - KPIs Compras Portomag

## Arquivo principal

- `dashboard_portomag_kpis.jsp`
- `css/dashboardPortomagKpis.css`

## Parametros esperados no Sankhya

Configurar os mesmos parametros usados nos gadgets atuais:

```xml
<parameter id="PERIODO" description="Periodo" metadata="datePeriod" required="true" />
<parameter id="DIASPRAZO" description="Qt Dias Prazo" metadata="integer" required="true" default="7" />
<parameter id="CODPARC" description="Fornecedor" metadata="entity:Parceiro@CODPARC" required="false" />
```

## Regras implementadas

### OS 7259 / Item 13

- Periodo filtrado por `TGFCAB.DTNEG`.
- TOPs consideradas: `400` e `401`.
- Prazo parametrizado por `:DIASPRAZO`.
- Data de corte historica: `:PERIODO.FIN`.
- Nao usa data atual para meses fechados.
- Detalhamento no nivel `NUNOTA + SEQUENCIA`.
- Status:
  - `Fechado`: possui atendimento final.
  - `Em andamento`: possui cotacao/intermediario.
  - `Aberto`: nao possui cotacao/intermediario.

### OS 7260 / Item 14

- Periodo filtrado por `ITC.DTENTSAI`, data real da entrada.
- Visao macro por fornecedor.
- Drill-down para entregas do fornecedor.
- Regras de prazo:
  - `USOPROD = C`: 15 dias.
  - `USOPROD = M`: 30 dias.
- Percentual on-time:

```text
entregas_no_prazo / total_entregas * 100
```

## Observacoes

- Produtos com `USOPROD` diferente de `C` ou `M` ficam fora do calculo ate confirmacao funcional.
- Entregas pendentes nao entram no KPI principal de on-time, para nao misturar realizado com pendente.
- O dashboard foi construido em HTML5/JSP mantendo o padrao visual Zaya.it usado nos dashboards anteriores.
