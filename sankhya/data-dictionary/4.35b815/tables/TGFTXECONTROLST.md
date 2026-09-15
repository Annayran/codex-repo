# TGFTXECONTROLST — Títulos por evento cobrança status

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TituloPorEventoReguaStatus | Títulos por evento cobrança status | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | Cód. Evento | I |  |  | N |
| CODREGUA | Código da Régua | I |  |  | N |
| DESTINATARIO | Destinatario | S |  |  | N |
| DHALTER | DATA DA ALTERACAO | H |  |  | N |
| DHINC | DATA DA CRIACAO | H |  |  | N |
| ID | ID | I |  |  | N |
| IDAPI | ID da API | I |  |  | N |
| MENSAGEM | Mensagem | S |  |  | N |
| NUFIN | Nro. Financeiro | I |  |  | N |
| SEQEXEC | Sequência de Execução | I |  |  | N |
| STATUSAPI | Status | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TituloPorEventoRegua / [TGFTXE](TGFTXE.md) | TituloPorEventoReguaStatus / [TGFTXECONTROLST](TGFTXECONTROLST.md) | CODREGUA->CODREGUA; NUFIN->NUFIN; SEQEXEC->SEQEXEC | I |
| TituloPorEventoReguaStatus / [TGFTXECONTROLST](TGFTXECONTROLST.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
