# TGFCTTPRO — Produtos Contato

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoContato | Produtos Cotação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLASSIF | Classificação do Produto | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODGRUPOPROD | Grupo de Produto | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contato / [TGFCTT](TGFCTT.md) | ProdutoContato / [TGFCTTPRO](TGFCTTPRO.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| ProdutoContato / [TGFCTTPRO](TGFCTTPRO.md) | ClassificacaoProduto / [TGFCLA](TGFCLA.md) | CODCLASSIF->CODCLASSIF | I |
| ProdutoContato / [TGFCTTPRO](TGFCTTPRO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ProdutoContato / [TGFCTTPRO](TGFCTTPRO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
