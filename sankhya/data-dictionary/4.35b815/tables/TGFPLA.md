# TGFPLA — Planejamento de Garantia

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 2
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PlanejamentoGarantia | Planejamento de Garantia | S | N |
| PlanejamentoGarantia1 | PlanejamentoGarantia1 | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPLANO | Plano | I |  |  | N |
| NOMEPLANO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GarantiaProduto / [TGFGAR](TGFGAR.md) | PlanejamentoGarantia / [TGFPLA](TGFPLA.md) | CODPLANO->CODPLANO | I |
| GarantiaProdutoGestFinanc / [TGFGAR](TGFGAR.md) | PlanejamentoGarantia / [TGFPLA](TGFPLA.md) | CODPLANO->CODPLANO | I |
| PlanejamentoGarantia / [TGFPLA](TGFPLA.md) | GarantiaProdutoGestFinanc / [TGFGAR](TGFGAR.md) | CODPLANO->CODPLANO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
