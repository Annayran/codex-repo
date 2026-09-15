# TGFVCS — Venda Casada

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VendaCasada | Produtos Sugeridos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODORIG | Produto/Grupo origem | I |  |  | N |
| CODPRODSUG | Produto/Serviço sugerido | I |  |  | N |
| MULTQTD | Multiplica pela Qtd. Vendida | S |  |  | N |
| QTDSUGERIDA | Qtd. Sugerida | F |  |  | N |
| TIPORIG | Tipo da origem | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoProduto / [TGFGRU](TGFGRU.md) | VendaCasada / [TGFVCS](TGFVCS.md) | CODGRUPOPROD->CODORIG | I |
| Produto / [TGFPRO](TGFPRO.md) | VendaCasada / [TGFVCS](TGFVCS.md) | CODPROD->CODORIG | I |
| VendaCasada / [TGFVCS](TGFVCS.md) | Produto / [TGFPRO](TGFPRO.md) | CODPRODSUG->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
