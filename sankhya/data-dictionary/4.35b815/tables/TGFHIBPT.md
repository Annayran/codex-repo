# TGFHIBPT — Histórico IBPT

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoIBPT | Histórico IBPT | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHFIMEXEC | Dh. Fim execução | H |  |  | N |
| DHINIEXEC | Dh. Ini execução | H |  |  | N |
| MSGIBPT | Mensagem | S |  |  | N |
| NUAGENDAMENTO | Nro. agendamento | I |  |  | N |
| NUHISTORICO | Número do histórico | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPOAGE | Tipo de agendamento | S |  |  | N |
| TPEXEC | Tipo de Execução | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoIBPT / [TGFHIBPT](TGFHIBPT.md) | AgendamentoTarefa / [TGFTAG](TGFTAG.md) | NUAGENDAMENTO->NUAGENDAMENTO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
