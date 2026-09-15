# TCSOOS — Ocorrências da OS

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OcorrenciaOS | Motivo da OS | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| COBRAR | Valor a Cobrar | S |  |  | N |
| CODOCOROS | Código | I |  |  | N |
| DESCROCOROS | Descrição | S |  |  | N |
| PREVISTO | Valor Previsto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemOrdemServico / [TCSITE](TCSITE.md) | OcorrenciaOS / [TCSOOS](TCSOOS.md) | CODOCOROS->CODOCOROS | I |
| OcorrenciaOS / [TCSOOS](TCSOOS.md) | RelacionamentoServicoMotivo / [TCSSEM](TCSSEM.md) | CODOCOROS->CODOCOROS | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | OcorrenciaOS / [TCSOOS](TCSOOS.md) | CODOCOROS->CODOCOROS | I |
| RelacionamentoServicoMotivo / [TCSSEM](TCSSEM.md) | OcorrenciaOS / [TCSOOS](TCSOOS.md) | CODOCOROS->CODOCOROS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
