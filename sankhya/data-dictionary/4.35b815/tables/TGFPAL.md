# TGFPAL — Produto Alternativo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoAlternativo | Produtos Alternativos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| CODPRODALT | Produto Alternativo | I |  |  | N |
| DTVIGENCIA | Data de vigência | H |  |  | N |
| PRIORIDADE | Prioridade dos produtos alternativos | I |  |  | N |
| QUANTIDADE | Quantidade de produto substituído | I |  |  | N |
| REFERENCIAPA | Referência (PA) | S |  |  | S |
| SUBSTITUTO | Produto Substituto ? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | ProdutoAlternativo / [TGFPAL](TGFPAL.md) | CODPROD->CODPROD | I |
| ProdutoAlternativo / [TGFPAL](TGFPAL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPRODALT->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
