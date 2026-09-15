# THGMTV — Hidrogeologia Motivo de Parada

- Módulo técnico: [THG](../modules/THG.md)
- Campos: 2
- Instâncias DWF: 2
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MotivoParada | Hidrogeologia Motivo de Parada | N | N |
| MotivoParada2 | Hidrogeologia Motivo de Parada | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOTIVO | CODMOTIVO | I |  |  | N |
| DESCRICAO | DESCRICAO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemRelatorioProducao / [THGITE](THGITE.md) | MotivoParada / [THGMTV](THGMTV.md) | CODMOTIVOPARADA->CODMOTIVO | I |
| ItemRelatorioProducao / [THGITE](THGITE.md) | MotivoParada2 / [THGMTV](THGMTV.md) | SLUG_CODMOTIVOPARADA->CODMOTIVO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
