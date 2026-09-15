# TGFAEV — Acompanhamento de Eventos de NFe

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcompanhamentoEvento | Acompanhamento de Eventos de NFe | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | CODEVENTO | I |  |  | N |
| DHOCOR | DHOCOR | H |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| OCORRENCIA | Ocorrencia | C |  |  | N |
| SEQEVENTO | SEQEVENTO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoReformaTributaria / [TGFEVTRTC](TGFEVTRTC.md) | AcompanhamentoEvento / [TGFAEV](TGFAEV.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQEVENTO->SEQEVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
