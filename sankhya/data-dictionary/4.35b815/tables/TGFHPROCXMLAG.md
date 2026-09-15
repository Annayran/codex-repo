# TGFHPROCXMLAG — Histórico de Processamento XML

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoProcessamentoXMLAG | Histórico de Processamento XML | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHEXEC | Data de Execução | H |  |  | N |
| MSG | Mensagem | C |  |  | N |
| NUAGENDPROC | Número Único Agend. | I |  |  | N |
| QTDERRO | Qtde Erro | I |  |  | N |
| QTDSUCESSO | Qtde Sucesso | I |  |  | N |
| QTDTOT | Qtde de Arquivos | I |  |  | N |
| TIPOAGE | Tipo Agendamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoTarefa / [TGFTAG](TGFTAG.md) | HistoricoProcessamentoXMLAG / [TGFHPROCXMLAG](TGFHPROCXMLAG.md) | NUAGENDAMENTO->NUAGENDPROC; TIPOAGE->TIPOAGE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
