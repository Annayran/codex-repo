# TSIARF — TABLE TSIARF

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AgendamentoRelatorioFormatado | Agendamento de Relatório Formatado | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGENDAMENTO | AGENDAMENTO | S |  |  | N |
| ARQMODEMAIL | ARQMODEMAIL | S |  |  | N |
| CODSMTP | CODSMTP | I |  |  | N |
| CODUSURESP | Usuário responsável | I |  |  | N |
| DESCRICAO | DESCRICAO | S |  |  | N |
| DHFIM | DHFIM | H |  |  | N |
| DHINI | DHINI | H |  |  | N |
| EMAILMANUAL | EMAILMANUAL | C |  |  | N |
| EXECUNICA | EXECUNICA | S |  |  | N |
| NURFE | NURFE | I |  |  | N |
| PROXEXEC | PROXEXEC | H |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoRelatorioFormatado / [TSIARF](TSIARF.md) | DestinatarioRelatorioFormatado / [TSIDRF](TSIDRF.md) | NURFE->NURFE; SEQUENCIA->SEQUENCIA | I |
| AgendamentoRelatorioFormatado / [TSIARF](TSIARF.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
