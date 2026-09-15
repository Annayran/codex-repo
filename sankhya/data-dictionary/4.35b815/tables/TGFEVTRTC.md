# TGFEVTRTC — Eventos da Reforma Tributária

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventoReformaTributaria | Evento da Reforma Tributária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | Cód. Evento | I |  |  | N |
| DESCEVENTO | Descrição do Evento | I |  |  | S |
| DHENV | Data/Hora de Envio do Evento | H |  |  | N |
| INDACEITACAO | Indicador de Aceitação | S |  |  | N |
| NROPROTAUT | Nro. Protocolo | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| PROCESSADO | Processado | S |  |  | N |
| SEQEVENTO | Sequência Evento | I |  |  | N |
| STATSEFAZ | Status da Sefaz | S |  |  | N |
| TPAUTOR | Tipo de Autor | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | AcompanhamentoEvento / [TGFAEV](TGFAEV.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQEVENTO->SEQEVENTO | I |
| EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | ItemEvtReformaTributaria / [TGFEVTITERTC](TGFEVTITERTC.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQEVENTO->SEQEVENTO | I |
| EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | XmlEventoReformaTributaria / [TGFXMLRTC](TGFXMLRTC.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQEVENTO->SEQEVENTO | I |
| ItemEvtReformaTributaria / [TGFEVTITERTC](TGFEVTITERTC.md) | EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQEVENTO->SEQEVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
