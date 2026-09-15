# TSIFOR — Tabela de Formulas Generica

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 4
- Relações DWF: 15
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Formula | Fórmula | S | S |
| Formula2 | Instancia filha de Formula (para efeito de relacionamento) | N | N |
| FormulaLancamentoContabil | Formula para Lançamento Contabil | S | N |
| FormulaRequisicao | Formula para Requisição | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORM | Código | I |  |  | N |
| CONSIDERARRATEIO | Considerar regras de rateio ao apropriar CIP | S |  |  | N |
| DESCRFORM | Descrição da Fórmula | S |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| FORMULAWEB | Fórmula Web | S |  |  | N |
| INDCUSTO | Perc. do valor a ser usado no custo | F |  |  | N |
| TIPDIST | Tipo distribuição | S |  |  | N |
| TIPFORM | Tipo Fórmula | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FiltroCalculoCusto / [TSIFOP](TSIFOP.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| FiltroCalculoCustoCtaContabil / [TSIFOP](TSIFOP.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| FiltroCalculoCustoNat / [TSIFOP](TSIFOP.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| FiltroCalculoCustoProjeto / [TSIFOP](TSIFOP.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| Formula / [TSIFOR](TSIFOR.md) | FiltroCalculoCusto / [TSIFOP](TSIFOP.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| Formula / [TSIFOR](TSIFOR.md) | FiltroCalculoCustoCtaContabil / [TSIFOP](TSIFOP.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| Formula / [TSIFOR](TSIFOR.md) | FiltroCalculoCustoNat / [TSIFOP](TSIFOP.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| Formula / [TSIFOR](TSIFOR.md) | FiltroCalculoCustoProjeto / [TSIFOP](TSIFOP.md) | CODFORM->CODFORM; TIPFORM->TIPFORM | I |
| MetadadoObjetoProjeto / [TAPMDO](TAPMDO.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORMTOP->CODFORM | I |
| MetadadoObjetoProjeto / [TAPMDO](TAPMDO.md) | Formula2 / [TSIFOR](TSIFOR.md) | CODFORMVAL->CODFORM | I |
| Produto / [TGFPRO](TGFPRO.md) | Formula / [TSIFOR](TSIFOR.md) | CODFILTRO->CODFORM | I |
| Produto / [TGFPRO](TGFPRO.md) | FormulaRequisicao / [TSIFOR](TSIFOR.md) | CODFILTROREQ->CODFORM | I |
| Servico / [TGFPRO](TGFPRO.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORMAPONTA->CODFORM | I |
| Servico / [TGFPRO](TGFPRO.md) | Formula2 / [TSIFOR](TSIFOR.md) | CODFILTRO->CODFORM | I |
| Veiculo / [TGFVEI](TGFVEI.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORMFRETE->CODFORM; TIPFORMFRETE->TIPFORM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
