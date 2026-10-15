# Ajustes KPI 300 e 301 - Auditoria

## Arquivos gerados

- `300_component_leadtime_auditoria.xml`
- `301_component_cotacao_auditoria.xml`

## Mapeamento

- `300_component.xml`: Lead Time / OS 7260 / Item 14.
- `301_component.xml`: Cotacao / OS 7259 / Item 13.

## Ajustes no KPI 300 - Lead Time

O layout original foi preservado.

Queries alteradas:

- `grd_021`: lead time por uso do produto.
- `grd_026`: lead time por produto.
- `grd_04T`: analise por fornecedor.
- `grd_059`: detalhamento por produto.

Regras aplicadas:

- Periodo continua usando `ITC.DTENTSAI BETWEEN :PERIODO.INI AND :PERIODO.FIN`.
- Filtro de fornecedor preservado com `:CODPARC`.
- `USOPROD = C`: prazo esperado de 15 dias.
- `USOPROD <> C`, limitado a `M` pelo filtro atual: prazo esperado de 30 dias.
- Removido uso de `TRUNC(SYSDATE)` das queries ajustadas.
- Visao por fornecedor agora apresenta:
  - total de entregas;
  - total no prazo;
  - total fora do prazo;
  - percentual on-time;
  - lead time medio;
  - melhor prazo;
  - pior prazo.

Formula on-time:

```sql
entregas_no_prazo / total_entregas * 100
```

## Ajustes no KPI 301 - Cotacao

O layout original foi preservado.

Queries alteradas:

- `cht_0KQ`: grafico por prazo.
- `cht_0IK`: grafico por status.
- `grd_0M5`: detalhamento principal.
- `grd_0MI`: detalhe por prazo.
- `grd_0NV`: detalhe por status.

Regras aplicadas:

- Periodo continua usando `CAB.DTNEG BETWEEN :PERIODO.INI AND :PERIODO.FIN`.
- TOPs ajustadas para `400, 401`.
- Removido uso de `TRUNC(SYSDATE)`.
- Para requisicoes sem atendimento final, a data de corte usada e `:PERIODO.FIN`.
- Status corrigido para:
  - `Aberto`;
  - `Em andamento`;
  - `Fechado`.
- Detalhamento agora considera `NUNOTA + SEQUENCIA`.
- Foram adicionadas colunas de auditoria:
  - sequencia;
  - data inicio cotacao;
  - data atendimento;
  - data corte;
  - prazo limite;
  - status;
  - classificacao de prazo.

## Validacao sugerida

Para validar outubro de 2025:

```text
:PERIODO.INI = 01/10/2025
:PERIODO.FIN = 31/10/2025
```

Conferencias:

1. Rodar o dashboard em dias diferentes e verificar se o resultado permanece igual.
2. No KPI 301, somar o detalhamento por `PRAZO` e comparar com o grafico de prazo.
3. No KPI 301, somar o detalhamento por `STATUS` e comparar com o grafico de status.
4. No KPI 300, somar o total por fornecedor e conferir os campos no prazo/fora prazo.
5. Validar se o percentual on-time segue:

```text
TOTAL_NO_PRAZO / TOTAL_ENTREGAS * 100
```

## Pontos funcionais ainda recomendados para confirmacao

1. Confirmar se TOP `401` deve sempre entrar junto com `400` no KPI 301.
2. Confirmar se produtos com `USOPROD` diferente de `C` e `M` devem ficar fora do KPI 300 ou entrar com prazo de 30 dias.
3. Confirmar se entrega parcial deve ser contada por nota/item ou consolidada por pedido/item.
