# TGFHEN — Histórico Envio Nota Sefaz

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoEnvioNotaSefaz | Histórico de Envio de Nota Sefaz | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHEXEC | Data de Execução | H |  |  | N |
| MSG | Mensagem | S |  |  | N |
| NUAGENDAMENTO | Nro. agendamento | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPOAGE | Tipo Agendamento | S |  |  | N |
| TIPOSERVEXEC | Tipo de Serviço Executado | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoTarefa / [TGFTAG](TGFTAG.md) | HistoricoEnvioNotaSefaz / [TGFHEN](TGFHEN.md) | NUAGENDAMENTO->NUAGENDAMENTO; SEQUENCIA->SEQUENCIA; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
