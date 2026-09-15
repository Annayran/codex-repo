# TGFRPV — GF Relacionamento de Parceiros por Vendedores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VendedorParceiro | Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | N |
| CODREG | Região | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Regiao / [TSIREG](TSIREG.md) | VendedorParceiro / [TGFRPV](TGFRPV.md) | CODREG->CODREG | I |
| Vendedor / [TGFVEN](TGFVEN.md) | VendedorParceiro / [TGFRPV](TGFRPV.md) | CODVEND->CODVEND | I |
| VendedorParceiro / [TGFRPV](TGFRPV.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| VendedorParceiro / [TGFRPV](TGFRPV.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
