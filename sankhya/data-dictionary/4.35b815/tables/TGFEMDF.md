# TGFEMDF — Evento MDF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventoMDF | Evento do MDF-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCIDEVE | Cidade de encerramento | I |  |  | N |
| CODEVE | Cód. Evento | S |  |  | N |
| CODMOTORISTA | Motorista | I |  |  | N |
| CODSTATUS | Cód. Status | S |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DESCREVE | Descrição do Evento | S |  |  | N |
| DESCRSTATUS | Descrição do Status | S |  |  | N |
| DHRECIBO | Dt. e Hora do Evento | H |  |  | N |
| JUSTEVE | Justificativa do Evento | S |  |  | N |
| NRORECIBO | Nro. Recibo | S |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SEQEVE | Sequência Evento | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |
| UFEVE | UF de encerramento | I |  |  | N |
| XML | XML | C |  |  | N |
| XMLPROT | XML Autorização | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoMDF / [TGFEMDF](TGFEMDF.md) | Cidade / [TSICID](TSICID.md) | CODCIDEVE->CODCID | I |
| EventoMDF / [TGFEMDF](TGFEMDF.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| EventoMDF / [TGFEMDF](TGFEMDF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODMOTORISTA->CODPARC | I |
| EventoMDF / [TGFEMDF](TGFEMDF.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UFEVE->CODUF | I |
| EventoMDF / [TGFEMDF](TGFEMDF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | EventoMDF / [TGFEMDF](TGFEMDF.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
