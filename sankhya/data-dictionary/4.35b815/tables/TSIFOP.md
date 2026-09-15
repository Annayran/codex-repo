# TSIFOP — Filtro para Cálculo de Custo

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 4
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FiltroCalculoCusto | Filtro para Cálculo de Custo | S | N |
| FiltroCalculoCustoCtaContabil | Filtro p/ Calculo de Custo Conta Contabil | S | N |
| FiltroCalculoCustoNat | Filtro p/Cálculo de Custo Natureza | S | N |
| FiltroCalculoCustoProjeto | Filtro p/ Calculo de Custo Projeto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCHAVE | Código | I |  |  | N |
| CODEMPPLA | Empresa | I |  |  | N |
| CODFORM | Cód. Fórmula | I |  |  | N |
| TABELA | Tabela | S |  |  | N |
| TIPFORM | Tipo Fórmula | S |  |  | N |
| TIPLANCPLA | Tip. Lançamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FiltroCalculoCusto / [TSIFOP](TSIFOP.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCHAVE->CODCENCUS | I |
| FiltroCalculoCusto / [TSIFOP](TSIFOP.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| FiltroCalculoCustoCtaContabil / [TSIFOP](TSIFOP.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| FiltroCalculoCustoCtaContabil / [TSIFOP](TSIFOP.md) | PlanoConta /  | CODCHAVE->CODCTACTB; CODEMPPLA->CODEMP | I |
| FiltroCalculoCustoCtaContabil / [TSIFOP](TSIFOP.md) | ViewEmpresaContabilidade /  | CODEMPPLA->CODEMP | I |
| FiltroCalculoCustoNat / [TSIFOP](TSIFOP.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| FiltroCalculoCustoNat / [TSIFOP](TSIFOP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODCHAVE->CODNAT | I |
| FiltroCalculoCustoProjeto / [TSIFOP](TSIFOP.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| FiltroCalculoCustoProjeto / [TSIFOP](TSIFOP.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODCHAVE->CODPROJ | I |
| Formula / [TSIFOR](TSIFOR.md) | FiltroCalculoCusto / [TSIFOP](TSIFOP.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| Formula / [TSIFOR](TSIFOR.md) | FiltroCalculoCustoCtaContabil / [TSIFOP](TSIFOP.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| Formula / [TSIFOR](TSIFOR.md) | FiltroCalculoCustoNat / [TSIFOP](TSIFOP.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| Formula / [TSIFOR](TSIFOR.md) | FiltroCalculoCustoProjeto / [TSIFOP](TSIFOP.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
