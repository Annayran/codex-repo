# TGFRFD — TABLE TGFRFD

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RessuprimentoFilialDestino | Ressuprimento de Filiais de Destino | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMPDEST | Empresa Destino | I |  |  | N |
| CODLOCAL | Local Destino | I |  |  | N |
| CODMODDEST | Modelo Mov. Destino | I |  |  | N |
| CONSIDCOMPRAPEND | Considera Compras Pendentes? | S |  |  | N |
| CONSIDRESERVA | Considera Reservas? | S |  |  | N |
| GERAMOVENTDEST | Gera Mov. Entrada no Destino? | S |  |  | N |
| NUAGENDAMENTO | Nro Único Agend. | I |  |  | N |
| NURFD | Nro. único | I |  |  | N |
| PRIORIDADE | Prioridade | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPOAGE | Tipo Agendamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RessuprimentoFilialDestino / [TGFRFD](TGFRFD.md) | AgendamentoTarefa / [TGFTAG](TGFTAG.md) | NUAGENDAMENTO->NUAGENDAMENTO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| RessuprimentoFilialDestino / [TGFRFD](TGFRFD.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CODMODDEST->NUNOTA | I |
| RessuprimentoFilialDestino / [TGFRFD](TGFRFD.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPDEST->CODEMP | I |
| RessuprimentoFilialDestino / [TGFRFD](TGFRFD.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
