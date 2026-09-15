# TGFTKCAVR — Campos Consulta Indexada Venda Rápida

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CamposTokenProdutoVR | Campos Consulta Indexada Venda Rápida | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPO | Campo | S |  |  | N |
| CODCFG | Código | I |  |  | N |
| ENTIDADE | Entidade | S |  |  | N |
| RELEVANCIA | Relevância | I |  |  | N |
| TABELA | Tabela | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoTokenProdutoVR / [TGFTKCFVR](TGFTKCFVR.md) | CamposTokenProdutoVR / [TGFTKCAVR](TGFTKCAVR.md) | CODCFG->CODCFG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
