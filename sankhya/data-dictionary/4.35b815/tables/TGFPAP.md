# TGFPAP — Interação Parceiro Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RelacionamentoParceiroProduto | Produtos Equivalentes | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | Cód.Barra | S |  |  | N |
| CODBARRAPARC | Cód. Barras Parceiro | S |  |  | N |
| CODEMP | Código Empresa | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODPROPARC | Cod. Produto Equivalente | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DESCRPROPARC | Descrição do Produto Equivalente | S |  |  | N |
| DUM14 | Cód. Barra DUN-14 | S |  |  | N |
| MULTIPCPA | Multipl. Compra | F |  |  | N |
| PRAZOENT | Prazo para entrega | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| UNIDADE | Unidade do Parceiro | S |  |  | N |
| UNIDADELOTE | Unidade do Lote | S |  |  | N |
| UNIDADEPARC | Unidade | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | CODPARC->CODPARC | I |
| Produto / [TGFPRO](TGFPRO.md) | RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | CODPROD->CODPROD | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | UtilizacaoProdutoParceiro / [TGFHPP](TGFHPP.md) | CODPARC->CODPARC; CODPROD->CODPROD | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | Volume / [TGFVOL](TGFVOL.md) | UNIDADEPARC->CODVOL | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | VolumeAlternativo / [TGFVOA](TGFVOA.md) | CODPROD->CODPROD; CONTROLE->CONTROLE; UNIDADELOTE->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
