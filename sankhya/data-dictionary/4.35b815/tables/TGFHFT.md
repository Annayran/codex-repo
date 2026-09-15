# TGFHFT — Histórico de Faturamento de Contratos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoFaturamentoContrato | Histórico de Faturamento de Contratos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CONFIG | Configuração | C |  |  | N |
| DHFIMEXEC | Dh. Fim Execução | H |  |  | N |
| DHINIEXEC | Dh. Ini. Execução | H |  |  | N |
| DHREFERENCIA | Mês de Referência | H |  |  | N |
| NUAGENDAMENTO | NUAGENDAMENTO | I |  |  | N |
| NUHISTORICO | Nro. Único Agend. | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| QTDCOMIMPEDIMENTO | Contratos com Impedimento | I |  |  | N |
| QTDCONTRATOS | Contratos Faturados | I |  |  | N |
| QTDFATURASGERADAS | Faturas Geradas | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |
| TIPOAGE | TIPOAGE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | HistoricoFaturamentoContrato / [TGFHFT](TGFHFT.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| HistoricoFaturamentoContrato / [TGFHFT](TGFHFT.md) | DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | NUAGENDAMENTO->NUAGENDAMENTO; NUHISTORICO->NUHISTORICO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
