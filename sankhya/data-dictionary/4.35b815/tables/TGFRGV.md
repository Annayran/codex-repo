# TGFRGV — GF Relacionamento de grupos por vendedores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VendedorGrupoProduto | Grupo de Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPOPROD | Grupo de Produto | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoProduto / [TGFGRU](TGFGRU.md) | VendedorGrupoProduto / [TGFRGV](TGFRGV.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| Vendedor / [TGFVEN](TGFVEN.md) | VendedorGrupoProduto / [TGFRGV](TGFRGV.md) | CODVEND->CODVEND | I |
| VendedorGrupoProduto / [TGFRGV](TGFRGV.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
