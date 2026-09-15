# TCSCSL — Cores SLA

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CorSLA | Cor SLA | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CORRGB | Valor da Cor em RGB | I |  |  | N |
| DECORESTA | Decorrido/Restante | S |  |  | N |
| NUMREG | Núm. Regra | I |  |  | N |
| NUSLA | Nro. Único SLA | I |  |  | N |
| SEQCOR | Sequência de Cor | I |  |  | N |
| TIPOTEMPO | Tipo de Tempo | S |  |  | N |
| VALORTEMPO | Tempo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegraSLA / [TCSRSL](TCSRSL.md) | CorSLA / [TCSCSL](TCSCSL.md) | NUMREG->NUMREG; NUSLA->NUSLA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
