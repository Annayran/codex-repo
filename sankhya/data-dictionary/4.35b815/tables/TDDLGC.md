# TDDLGC — Campos de ligação

- Módulo técnico: [TDD](../modules/TDD.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CampoLigacao | Campo de Ligação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUCAMPODEST | Núm. campo destino | I |  |  | N |
| NUCAMPOORIG | Núm. campo origem | I |  |  | N |
| NUINSTDEST | Núm. Instância destino | I |  |  | N |
| NUINSTORIG | Núm. Instância origem | I |  |  | N |
| ORDEM | Ordem do campo na ligação | I |  |  | N |
| ORIG_OBRIGATORIA | Origem obrigatória | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CampoLigacao / [TDDLGC](TDDLGC.md) | Campo / [TDDCAM](TDDCAM.md) | NUCAMPOORIG->NUCAMPO | I |
| CampoLigacao / [TDDLGC](TDDLGC.md) | CampoDestino / [TDDCAM](TDDCAM.md) | NUCAMPODEST->NUCAMPO | I |
| Ligacao / [TDDLIG](TDDLIG.md) | CampoLigacao / [TDDLGC](TDDLGC.md) | NUINSTDEST->NUINSTDEST; NUINSTORIG->NUINSTORIG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
