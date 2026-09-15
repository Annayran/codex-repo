# TGFCAS — Agenda de serviços

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoAgenda | Cabeçalho da agenda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODEXEC | Executante | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODUSU | Cód. usuário da alteração | I |  |  | N |
| DHALTER | Data alteração | H |  |  | N |
| DTAGENDA | Data do agendamento | D |  |  | N |
| FATURADO | Faturado | S |  |  | N |
| HRAGENDA | Hora do agendamento | T |  |  | N |
| HRFINALAGENDA | Hora final do agendamento | T |  |  | N |
| MOTIVOCANCELADO | Motivo de cancelamento | S |  |  | N |
| NUAGENDA | Nro agenda | I |  |  | N |
| NUAGENDAORIGEM | Agenda de origem | I |  |  | N |
| NUNOTAFATURA | Nro Nota fatura | I |  |  | N |
| OBSCANCELADO | Observação de cancelamento | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoAgenda / [TGFCAS](TGFCAS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoAgenda / [TGFCAS](TGFCAS.md) | ItemAgenda / [TGFIAS](TGFIAS.md) | NUAGENDA->NUAGENDA | I |
| CabecalhoAgenda / [TGFCAS](TGFCAS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoAgenda / [TGFCAS](TGFCAS.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODEXEC->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
