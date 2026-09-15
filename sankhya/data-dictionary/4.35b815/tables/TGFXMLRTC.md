# TGFXMLRTC — XML dos Eventos da Reforma Tributária

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| XmlEventoReformaTributaria | Xml do Evento da Reforma Tributária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | Cód. Evento | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| SEQEVENTO | Sequência do Evento | I |  |  | N |
| XMLENV | Xml de envio do Evento | C |  |  | N |
| XMLENVCLI | Xml de Distribuição | C |  |  | N |
| XMLPROTAUT | Xml de Autorização | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | XmlEventoReformaTributaria / [TGFXMLRTC](TGFXMLRTC.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQEVENTO->SEQEVENTO | I |
| XmlEventoReformaTributaria / [TGFXMLRTC](TGFXMLRTC.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
