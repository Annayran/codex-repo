# Auditoria dos KPIs de Compras - OS 7259 e OS 7260

## Contexto

Este pacote refina os indicadores ja existentes de compras para que os resultados sejam auditaveis, reprodutiveis historicamente e conferiveis pelo detalhamento.

Arquivos analisados:

- `Cotacao.txt`: base atual do KPI de requisicoes.
- `LeadTime.txt`: base atual do KPI de lead time/entregas.
- `OneDrive_1_25-05-2026.zip`: contem documentos de levantamento/validacao, mas nao contem o JSP/XML editavel do dashboard.

Por esse motivo, nao foi feita alteracao direta no dashboard nativo. Foram entregues as queries finais ajustadas para aplicar nos componentes atuais ou em uma versao HTML5/JSP.

## Mapeamento tecnico

### OS 7259 / Item 13

Indicador: Lead Time de Requisicoes Atendidas.

Query atual:

- Usa `TGFCAB`, `TGFITE`, `TGFTOP`, `TGFPRO`, `TGFVAR`.
- Considera apenas `CODTIPOPER IN (400)`.
- Usa `TRUNC(SYSDATE)` para requisicoes sem atendimento final.
- Classifica status como `Aberto`, `Em andamento` e `Fechado`.
- Classifica prazo como `No Prazo` ou `Fora do Prazo`.

Problemas corrigidos:

- Inclusao de TOP `401`, conforme levantamento que cita TOP 400/401.
- Remocao de `TRUNC(SYSDATE)` da visao historica.
- Uso de `:PERIODO.FIN` como data de corte para itens nao finalizados.
- Criacao de base detalhada por requisicao/item.
- Agrupamento no nivel `NUNOTA + SEQUENCIA`, evitando duplicidade por `TGFVAR`.

### OS 7260 / Item 14

Indicador: Numero de Entregas do Fornecedor On-time.

Query atual:

- Calcula lead time por tipo de produto e produto.
- Usa `TRUNC(SYSDATE)` quando nao ha nota de entrada.
- Filtra por `ITC.DTENTSAI` dentro do periodo.
- Usa regra de prazo por `USOPROD`.

Problemas corrigidos:

- Criacao de macro por fornecedor.
- Criacao de detalhamento por fornecedor/pedido/entrada/produto/item.
- Calculo de percentual on-time por fornecedor.
- Separacao clara de entregas concluidas.
- Remocao de pendencias da query principal de entrega, para nao misturar entregue com pendente.
- Agrupamento por nota/item, evitando duplicidade por variacao.

## Arquivos entregues

### SQL

- `sql/os7259_requisicoes_macro.sql`
- `sql/os7259_requisicoes_detalhe.sql`
- `sql/os7260_entregas_fornecedor_macro.sql`
- `sql/os7260_entregas_fornecedor_detalhe.sql`

## Regras - OS 7259 / Item 13

### Data usada no filtro

O periodo do KPI usa:

```sql
TRUNC(TGFCAB.DTNEG) BETWEEN :PERIODO.INI AND :PERIODO.FIN
```

Motivo: `DTNEG` representa a data de negociacao/geracao da requisicao e e a data ja usada pela query original.

### Inicio do calculo

```sql
TRUNC(NVL(CAB.DTENTSAI, CAB.DTNEG))
```

Motivo: a query original usava `CAB.DTENTSAI` para calcular dias. Foi mantida essa regra, com fallback em `DTNEG` caso `DTENTSAI` esteja nula.

### Fim do calculo

```sql
TRUNC(NVL(DT_ATENDIMENTO, :PERIODO.FIN))
```

Regras:

- Se houver atendimento final, usa a data de atendimento.
- Se nao houver atendimento final, usa `:PERIODO.FIN`.
- Nao usa `SYSDATE` na visao historica.

### Status da requisicao

```text
Fechado       = possui atendimento final
Em andamento = possui cotacao/intermediario, mas nao atendimento final
Aberto        = nao possui cotacao/intermediario nem atendimento final
```

### Prazo

```sql
:DIASPRAZO
```

Se o parametro vier nulo:

```sql
NVL(:DIASPRAZO, 7)
```

### Classificacao

```text
No Prazo      = DIAS_CALCULADOS <= PRAZO_LIMITE
Fora do Prazo = DIAS_CALCULADOS > PRAZO_LIMITE
```

### Percentual no prazo

```sql
TOTAL_NO_PRAZO / TOTAL_REQUISICOES_ITEM * 100
```

### Lead time medio

```sql
AVG(DIAS_CALCULADOS)
```

Calculado sobre a base detalhada de requisicao/item.

## Regras - OS 7260 / Item 14

### Data usada no filtro

O periodo do KPI usa a data real de entrada:

```sql
TRUNC(ITC.DTENTSAI) BETWEEN :PERIODO.INI AND :PERIODO.FIN
```

Motivo: o indicador mede entregas realizadas no periodo.

### Inicio do calculo

```sql
TRUNC(NVL(ITE.DTINICIO, NVL(CAB.DTPREVENT, CAB.DTNEG)))
```

Ordem usada:

1. Data de inicio do item.
2. Data prevista do pedido.
3. Data de negociacao do pedido.

### Fim do calculo

```sql
TRUNC(ITC.DTENTSAI)
```

Somente entregas concluidas entram no KPI principal.

### Regras de prazo

```text
USOPROD = C -> 15 dias
USOPROD = M -> 30 dias
```

Produtos com `USOPROD` diferente de `C` ou `M` foram mantidos fora do calculo ate confirmacao funcional.

### Classificacao

```text
No Prazo      = DIAS_CALCULADOS <= PRAZO_ESPERADO
Fora do Prazo = DIAS_CALCULADOS > PRAZO_ESPERADO
```

### Percentual on-time

```sql
TOTAL_NO_PRAZO / TOTAL_ENTREGAS * 100
```

### Lead time medio por fornecedor

```sql
AVG(DIAS_CALCULADOS)
```

Calculado sobre entregas concluidas do fornecedor no periodo.

## Como validar outubro de 2025

Usar filtros:

```text
:PERIODO.INI = 01/10/2025
:PERIODO.FIN = 31/10/2025
```

### Validacao OS 7259

1. Executar `os7259_requisicoes_detalhe.sql`.
2. Conferir se cada linha representa uma requisicao/item.
3. Somar manualmente:
   - total de linhas;
   - status `Aberto`;
   - status `Em andamento`;
   - status `Fechado`;
   - classificacao `No Prazo`;
   - classificacao `Fora do Prazo`.
4. Executar `os7259_requisicoes_macro.sql`.
5. Validar se os totais batem com a base detalhada.
6. Reexecutar em outro dia: o resultado deve ser identico, pois usa `:PERIODO.FIN` como corte.

### Validacao OS 7260

1. Executar `os7260_entregas_fornecedor_detalhe.sql`.
2. Conferir se cada linha representa uma entrega por fornecedor/pedido/nota/item.
3. Agrupar manualmente por fornecedor:
   - total de entregas;
   - total no prazo;
   - total fora do prazo;
   - percentual on-time;
   - lead time medio;
   - menor prazo;
   - maior prazo.
4. Executar `os7260_entregas_fornecedor_macro.sql`.
5. Validar se os numeros do fornecedor batem com o detalhamento.

## Pontos que precisam de confirmacao funcional

1. Confirmar definitivamente se a TOP `401` deve entrar junto com a `400` no KPI OS 7259.
2. Confirmar se `DTNEG` e a melhor data de periodo para requisicoes ou se deve ser usada `DTENTSAI`.
3. Confirmar se produtos com `USOPROD` diferente de `C` ou `M` devem entrar como prazo de 30 dias ou ficar fora.
4. Confirmar se entregas parciais devem contar cada nota/item como uma entrega ou consolidar por pedido/item.
5. Confirmar se deve existir uma visao operacional separada usando data atual para pendencias abertas.

## Observacao sobre HTML5/JSP

E possivel aplicar esse mesmo padrao no contexto HTML5/JSP usado nos dashboards deste chat. Para isso, e necessario o XML/JSP atual do componente KPI, pois o ZIP enviado contem documentos Word e nao o projeto editavel.

Recomendacao:

- Substituir as queries dos cards/graficos atuais pelas queries macro.
- Usar as queries detalhe como fonte de drill-down.
- Manter o layout atual e apenas adicionar/ajustar colunas de conferencia.
