# TGFNPV — Vendedores por Venda

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 2
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VendedoresTabPreco | Vendedores vinc. Tabela de Preço | N | N |
| VendedorVenda | Vendedor/Tabela | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTAB | Tabela | I |  |  | N |
| CODTIPVENDA | Tipo de Negociação | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TipoNegociacao / [TGFTPV](TGFTPV.md) | VendedorVenda / [TGFNPV](TGFNPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| VendedoresTabPreco / [TGFNPV](TGFNPV.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| VendedoresTabPreco / [TGFNPV](TGFNPV.md) | TipNegTabPreco / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| VendedoresTabPreco / [TGFNPV](TGFNPV.md) | VendedorRepTabPreco / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| VendedorVenda / [TGFNPV](TGFNPV.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| VendedorVenda / [TGFNPV](TGFNPV.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| VendedorVenda / [TGFNPV](TGFNPV.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
