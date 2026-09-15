# TIMMTD — Motivos de devolução

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimMotivoDevolucao | Motivos de devolução | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| MTDCODIGO | Código | I |  |  | N |
| MTDDESISTENCIA | Desistiu | S |  |  | N |
| MTDMOTIVO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimMotivoDevolucao / [TIMMTD](TIMMTD.md) | IPRMOTIVO->MTDCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
