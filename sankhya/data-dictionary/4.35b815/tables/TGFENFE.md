# TGFENFE — Eventos de Nota Fiscal Eletrônica

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventoNotaFiscalEletronica | Evento de Nota Fiscal Eletrônica | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | CODEVENTO | I |  |  | N |
| NROPROTAUT | Nro. Protocolo de Autorização do Evento | S |  |  | N |
| NULOTE | NULOTE | I |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| PROCESSADO | PROCESSADO | S |  |  | N |
| SEQEVENTO | SEQEVENTO | I |  |  | N |
| TEXTOCORRECAO | Texto Carta de Correção | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoEconf / [TGFECO](TGFECO.md) | EventoNotaFiscalEletronica / [TGFENFE](TGFENFE.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQUENCIA->SEQEVENTO | I |
| EventoNotaFiscalEletronica / [TGFENFE](TGFENFE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
