# TCSSLT — Tempo de SLA

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TempoSLA | Tempo de SLA | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| HORASUTEIS | HORASUTEIS | S |  |  | N |
| NUMITEM | Sub-OS | I |  |  | N |
| NUMOS | OS | I |  |  | N |
| SLADECORRIDO | SLA Decorrido | I |  |  | N |
| SLARESTANTE | SLA Restante | I |  |  | N |
| TEMPOSLA | Tempo de SLA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemOrdemServico / [TCSITE](TCSITE.md) | TempoSLA / [TCSSLT](TCSSLT.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
