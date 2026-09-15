# TCIEST — Estrutura

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EstruturaLote | Estrutura de lote | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Cód.Produto | I |  |  | N |
| LISTA | Lista | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| TAMANHO | Tamanho | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TITULO | Título | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | EstruturaLote / [TCIEST](TCIEST.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
