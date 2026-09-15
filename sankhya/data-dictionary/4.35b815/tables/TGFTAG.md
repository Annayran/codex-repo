# TGFTAG — Tarefas Agendadas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 18
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AgendamentoTarefa | Agendamento Tarefa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CONFIG | Configuração | C |  |  | N |
| DESCRICAO | Descrição agendamento | S |  |  | N |
| DHEXECUCAO | Data da ultima execução | H |  |  | N |
| DHPROXEXEC | Próxima execução em | H |  |  | N |
| DHULTEXEC | Data Final de Execução | H |  |  | N |
| DIAMES | Agendar os Dias do Mês | S |  |  | N |
| DIASEMANA | Agendar os dias da Semana | S |  |  | N |
| GERALOTEPARA | Gerar Lote para | S |  |  | N |
| HORARIO | Agendar os Horários | S |  |  | N |
| INFO | Informações | C |  |  | N |
| MES | Agendar os Meses | S |  |  | N |
| NUAGENDAMENTO | Nro. agendamento | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| STATUSULTEXEC | Status Última Execução | S |  |  | N |
| TIPOAGE | Tipo Agendamento | S |  |  | N |
| TIPOEXEC | Tipo Execução | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoTarefa / [TGFTAG](TGFTAG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AgendamentoTarefa / [TGFTAG](TGFTAG.md) | ErroContabilizacao /  | NUAGENDAMENTO->NUAGENDCTZ; TIPOAGE->TIPO | I |
| AgendamentoTarefa / [TGFTAG](TGFTAG.md) | HistoricoContabilizacao /  | NUAGENDAMENTO->NUAGENDCTZ; TIPOAGE->TIPO | I |
| AgendamentoTarefa / [TGFTAG](TGFTAG.md) | HistoricoEnvioNotaSefaz / [TGFHEN](TGFHEN.md) | NUAGENDAMENTO->NUAGENDAMENTO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| AgendamentoTarefa / [TGFTAG](TGFTAG.md) | HistoricoProcessamentoXMLAG / [TGFHPROCXMLAG](TGFHPROCXMLAG.md) | NUAGENDAMENTO->NUAGENDPROC; TIPOAGE->TIPOAGE | I |
| HistoricoIBPT / [TGFHIBPT](TGFHIBPT.md) | AgendamentoTarefa / [TGFTAG](TGFTAG.md) | NUAGENDAMENTO->NUAGENDAMENTO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| HistoricoRessuprimentoFiliais / [TGFHRF](TGFHRF.md) | AgendamentoTarefa / [TGFTAG](TGFTAG.md) | NUAGENDAMENTO->NUAGENDAMENTO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |
| RessuprimentoFilialDestino / [TGFRFD](TGFRFD.md) | AgendamentoTarefa / [TGFTAG](TGFTAG.md) | NUAGENDAMENTO->NUAGENDAMENTO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
