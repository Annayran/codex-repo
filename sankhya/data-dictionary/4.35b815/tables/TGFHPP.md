# TGFHPP — Histórico Utilização Produto Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UtilizacaoProdutoParceiro | Utilização do Produto no Cliente | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODHUP | Histórico Utilização | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | UtilizacaoProdutoParceiro / [TGFHPP](TGFHPP.md) | CODPARC->CODPARC; CODPROD->CODPROD | I |
| UtilizacaoProdutoParceiro / [TGFHPP](TGFHPP.md) | HistoricoUtilizacaoProduto / [TGFHUP](TGFHUP.md) | CODHUP->CODHUP | I |
| UtilizacaoProdutoParceiro / [TGFHPP](TGFHPP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| UtilizacaoProdutoParceiro / [TGFHPP](TGFHPP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
