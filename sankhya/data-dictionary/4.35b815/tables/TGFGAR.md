# TGFGAR — Garantia por Produtos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 2
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GarantiaProduto | Garantia por Produtos | S | N |
| GarantiaProdutoGestFinanc | Garantia por Serviços | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGAR | Serviço | I |  |  | N |
| CODPLANO | Plano | I |  |  | N |
| PRECO | Preço | F |  |  | N |
| VLRMAX | Valor Máximo | F |  |  | N |
| VLRMIN | Valor Mínimo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GarantiaProduto / [TGFGAR](TGFGAR.md) | PlanejamentoGarantia / [TGFPLA](TGFPLA.md) | CODPLANO->CODPLANO | I |
| GarantiaProduto / [TGFGAR](TGFGAR.md) | Produto / [TGFPRO](TGFPRO.md) | CODGAR->CODGAR | I |
| GarantiaProdutoGestFinanc / [TGFGAR](TGFGAR.md) | PlanejamentoGarantia / [TGFPLA](TGFPLA.md) | CODPLANO->CODPLANO | I |
| GarantiaProdutoGestFinanc / [TGFGAR](TGFGAR.md) | Servico / [TGFPRO](TGFPRO.md) | CODGAR->CODPROD | I |
| PlanejamentoGarantia / [TGFPLA](TGFPLA.md) | GarantiaProdutoGestFinanc / [TGFGAR](TGFGAR.md) | CODPLANO->CODPLANO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
