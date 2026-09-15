# TCSEAG — EventoAgendado

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 33
- Instâncias DWF: 2
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventoAgendado | Evento Agendado | S | N |
| EventoAgendadoPai | Evento Agendado Pai | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CANCELADO | Cancelado | S |  |  | N |
| CHANGEKEY | Change Key | S |  |  | N |
| CODETAPA | Cód. Etapa | I |  |  | N |
| CODPAP | Cód. Prospect | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário Executante | I |  |  | N |
| CODUSULANCADOR | Cód. Usuário Lançador | I |  |  | N |
| CONFIRMADO | Confirmado | S |  |  | N |
| DESCRABREV | Descrição abreviada | S |  |  | N |
| DESCRLONGA | Descrição completa | S |  |  | N |
| DHALTERSINC | Data sincronização | H |  |  | N |
| DHCANCELAMENTO | Data de Cancelamento | H |  |  | N |
| DHFINAL | Data e hora fim | H |  |  | N |
| DHINICIO | Data e hora início | H |  |  | N |
| DHINICIOPAI | Dh. Início pai | H |  |  | N |
| DHLCTO | Dh. Lançamento | H |  |  | N |
| DIATODO | Dia todo | S |  |  | N |
| FALHASINC | FALHASINC | I |  |  | N |
| ICALEXP | Expressão de recorrência | S |  |  | N |
| IDOFFICE | ID Sinc. Office | S |  |  | N |
| IDSINC | ID Sincronização | S |  |  | N |
| LOCALEVENTO | Local do evento | S |  |  | N |
| MOTIVO | Motivo do Cancelamento | S |  |  | N |
| NUCURRICULO | Nro. Currículo | I |  |  | N |
| NUEVENTO | Num. Evento | I |  |  | N |
| NUEVENTOPAI | Evento Pai | I |  |  | N |
| NUFAP | Projeto | I |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| NUMETAPA | Etapa do projeto | I |  |  | N |
| NUMOS | Número da OS | I |  |  | N |
| NUSELECAO | Nro. Seleção | I |  |  | N |
| SINCRONIZAR | Sincronizar | S |  |  | N |
| SUBOS | Sub OS | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoAgendado / [TCSEAG](TCSEAG.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | EventoAgendadoPai / [TCSEAG](TCSEAG.md) | NUEVENTOPAI->NUEVENTO | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | ItemOrdemServico / [TCSITE](TCSITE.md) | NUMOS->NUMOS; SUBOS->NUMITEM | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | OrdemServico / [TCSOSE](TCSOSE.md) | NUMOS->NUMOS | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | ParceiroProspect / [TCSPAP](TCSPAP.md) | CODPAP->CODPAP | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
