# TCSMTE — Motivos Exceção Faturamento

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MotivoExcecaoFaturamento | Motivo de Exceção de Faturamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRMOTIVO | Descrição | S |  |  | N |
| NUMOTIVO | Nro. Motivo | I |  |  | N |
| TIPOMOTIVO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorizacaoFaturamentoOS / [TCSAFO](TCSAFO.md) | MotivoExcecaoFaturamento / [TCSMTE](TCSMTE.md) | NUMOTIVO->NUMOTIVO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
