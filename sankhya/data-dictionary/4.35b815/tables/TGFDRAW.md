# TGFDRAW — Número do ato concessório de Drawback

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NumeroAtoConcDrawBack | Número do ato concessório de Drawback | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NROATOCONCDRAW | Número do ato concessório de Drawback | S |  |  | N |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| SEQUENCIA | Sequência do Item | I |  |  | N |
| SEQUENCIADRAW | Sequência do Draw | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NumeroAtoConcDrawBack / [TGFDRAW](TGFDRAW.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
