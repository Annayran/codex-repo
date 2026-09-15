# TGFCOI2 — Item conferência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalhesConferencia | Itens Conferência | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | Cód. Barras | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | UN | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| COPIA | Cópia | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| NUCONF | Nro conferência | I |  |  | N |
| QTDCONF | Qtd conferida | F |  |  | N |
| QTDCONFVOLPAD | Qtd UN padrão | F |  |  | N |
| SEQCONF | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoConferencia / [TGFCON2](TGFCON2.md) | DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | NUCONF->NUCONF | I |
| DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | CabecalhoConferencia / [TGFCON2](TGFCON2.md) | NUCONF->NUCONF | I |
| DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| PesoConferencia / [TGFPCON](TGFPCON.md) | DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | NUCONF->NUCONF; SEQCONF->SEQCONF | I |
| SequenciaLote / [TGFSLT](TGFSLT.md) | DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | NUCONF->NUCONF; SEQCONF->SEQCONF | I |
| SerieProdutoConferencia / [TGFCONSER](TGFCONSER.md) | DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | NUCONF->NUCONF; SEQCONF->SEQCONF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
