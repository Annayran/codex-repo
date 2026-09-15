# TGFEVTITERTC — Itens dos Eventos da Reforma Tributária

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemEvtReformaTributaria | Item do Evento da Reforma Tributária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASE | Base | F |  |  | N |
| CCREDPRESCBS | Cód. Crédito Presumido CBS | S |  |  | N |
| CCREDPRESIBS | Cód. Crédito Presumido IBS | S |  |  | N |
| CHAVEDFEREF | Chave do DFe Referenciado | S |  |  | N |
| CODEVENTO | Cód. Evento | S |  |  | N |
| CODVOL | Cód. Volume | S |  |  | N |
| CODVOLEVT | Unidade Evento | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| PCREDPRESCBS | Perc. Crédito Presumido CBS | F |  |  | N |
| PCREDPRESIBS | Perc. Crédito Presumido IBS | F |  |  | N |
| QTDNEG | Quantidade na Nota | F |  |  | N |
| QTDNEGEVT | Quantidade Evento | F |  |  | N |
| SEQEVENTO | Sequência Evento | I |  |  | N |
| SEQITEM | Sequência Item | I |  |  | N |
| VCBS | Valor CBS | F |  |  | N |
| VCREDCBS | Valor Crédito CBS | F |  |  | N |
| VCREDIBS | Valor Crédito IBS | F |  |  | N |
| VCREDPRESCBS | Valor Crédito Presumido CBS | F |  |  | N |
| VCREDPRESIBS | Valor Crédito Presumido IBS | F |  |  | N |
| VIBS | Valor IBS | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | ItemEvtReformaTributaria / [TGFEVTITERTC](TGFEVTITERTC.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQEVENTO->SEQEVENTO | I |
| ItemEvtReformaTributaria / [TGFEVTITERTC](TGFEVTITERTC.md) | EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQEVENTO->SEQEVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
