# TGFTAXG — Taxas de juros ou desconto

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
| TaxasJurosDescontos | Taxas de juros ou desconto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPOPROD | Grupo do produto | I |  |  | N |
| CODTIPVENDA | Tipo da venda | I |  |  | N |
| TAXA | Taxa | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TaxasJurosDescontos / [TGFTAXG](TGFTAXG.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| TaxasJurosDescontos / [TGFTAXG](TGFTAXG.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | TaxasJurosDescontos / [TGFTAXG](TGFTAXG.md) | CODTIPVENDA->CODTIPVENDA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
