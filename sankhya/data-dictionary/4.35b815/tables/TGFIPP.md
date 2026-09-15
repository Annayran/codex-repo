# TGFIPP — IPI Produto Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| IpiProdutoParceiro | IPI por Produto e Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIPI | Código do IPI | I |  |  | N |
| CODPARC | Código do Parceiro | I |  |  | N |
| CODPROD | Código do Produto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaIPI / [TGFIPI](TGFIPI.md) | IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | CODIPI->CODIPI | I |
| HistoricoAliquotaIPI / [TGFHIPI](TGFHIPI.md) | IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | CODIPI->CODIPI | I |
| IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | AliquotaIPI / [TGFIPI](TGFIPI.md) | CODIPI->CODIPI | I |
| IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | IpiProdutoParceiro / [TGFIPP](TGFIPP.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
