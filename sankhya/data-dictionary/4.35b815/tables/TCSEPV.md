# TCSEPV — Expectativas do Esforço Pré-Venda

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ExpectativaEsforcoPreVenda | Expectativa Fechamento | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEPV | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| PERCPRO | Percentual de Projeção | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemOrdemServico / [TCSITE](TCSITE.md) | ExpectativaEsforcoPreVenda / [TCSEPV](TCSEPV.md) | CODEPV->CODEPV | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
