# TGFTAX — Taxas Especiais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TaxasEspeciais | Taxas Especiais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODTIPVENDA | Tipo de Negociação | I |  |  | N |
| TAXAJURO | Taxa | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TaxasEspeciais / [TGFTAX](TGFTAX.md) | ParcelaPagamento / [TGFPPG](TGFPPG.md) | CODTIPVENDA->CODTIPVENDA | I |
| TaxasEspeciais / [TGFTAX](TGFTAX.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | TaxasEspeciais / [TGFTAX](TGFTAX.md) | CODTIPVENDA->CODTIPVENDA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
