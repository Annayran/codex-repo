# TCSRSI — Regra de SLA por sub-OS

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegraSLASubOs | Regra de SLA por sub-OS | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUMITEM | Número do Ítem | I |  |  | N |
| NUMOS | Número da OS | I |  |  | N |
| NUMREG | Num. Regra | I |  |  | N |
| NUSLA | Nro. Único SLA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegraSLASubOs / [TCSRSI](TCSRSI.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |
| RegraSLASubOs / [TCSRSI](TCSRSI.md) | RegraSLA / [TCSRSL](TCSRSL.md) | NUSLA->NUSLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
