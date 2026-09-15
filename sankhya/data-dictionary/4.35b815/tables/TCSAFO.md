# TCSAFO — Autorizações de Faturamento de OS

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AutorizacaoFaturamentoOS | Autorização de Faturamento de OS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AUTORIZADO | Autorizado | S |  |  | N |
| CODUSUAUT | Cód.Usuário Autorização | I |  |  | N |
| DHALTER | Data da autorização | H |  |  | N |
| NUMITEM | NUMITEM | I |  |  | N |
| NUMOS | NUMOS | I |  |  | N |
| NUMOTIVO | Motivo de Não Autorização | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AutorizacaoFaturamentoOS / [TCSAFO](TCSAFO.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |
| AutorizacaoFaturamentoOS / [TCSAFO](TCSAFO.md) | MotivoExcecaoFaturamento / [TCSMTE](TCSMTE.md) | NUMOTIVO->NUMOTIVO | I |
| AutorizacaoFaturamentoOS / [TCSAFO](TCSAFO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUAUT->CODUSU | I |
| ItemOrdemServico / [TCSITE](TCSITE.md) | AutorizacaoFaturamentoOS / [TCSAFO](TCSAFO.md) | NUMITEM->NUMITEM; NUMOS->NUMOS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
