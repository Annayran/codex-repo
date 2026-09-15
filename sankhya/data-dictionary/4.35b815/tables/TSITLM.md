# TSITLM — TABLE TSITLM

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TelemetriaFramework | Telemetria | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODRGB | CodRgb | I |  |  | N |
| DTEXEC | Dt. Execução | H |  |  | N |
| NOMEREGRA | Nome Regra | S |  |  | N |
| QTDEXEC | Quant. Execuções | I |  |  | N |
| TABELA | Tabela | S |  |  | N |
| TEMPOMED | Tempo Médio (seg) | F |  |  | N |
| TEMPOTOT | Tempo Total (seg) | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegrasCustomizadas / [TSIRGB](TSIRGB.md) | TelemetriaFramework / [TSITLM](TSITLM.md) | CODRGB->CODRGB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
