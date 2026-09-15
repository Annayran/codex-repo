# TGFCON2 — Cabecalho conferência

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
| CabecalhoConferencia | Conferência | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSUCONF | Cód. Conferente | I |  |  | N |
| DHFINCONF | Data hora final | H |  |  | N |
| DHINICONF | Data hora início | H |  |  | N |
| NUCONF | Nro conferência | I |  |  | N |
| NUCONFORIG | Nro conferência origem | I |  |  | N |
| NUNOTADEV | Núm. nota de devolução | I |  |  | N |
| NUNOTAORIG | Nro nota conferência | I |  |  | N |
| NUPEDCOMP | Núm. pedido complementar | I |  |  | N |
| QTDVOL | Qtd. volume da conferência | I |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoConferencia / [TGFCON2](TGFCON2.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAORIG->NUNOTA | I |
| CabecalhoConferencia / [TGFCON2](TGFCON2.md) | DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | NUCONF->NUCONF | I |
| CabecalhoConferencia / [TGFCON2](TGFCON2.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUCONF->CODUSU | I |
| CabecalhoConferencia / [TGFCON2](TGFCON2.md) | VolumeConferencia / [TGFVCF](TGFVCF.md) | NUCONF->NUCONF | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | CabecalhoConferencia / [TGFCON2](TGFCON2.md) | NUCONFATUAL->NUCONF | I |
| DetalhesConferencia / [TGFCOI2](TGFCOI2.md) | CabecalhoConferencia / [TGFCON2](TGFCON2.md) | NUCONF->NUCONF | I |
| ProdutoDivergente / [TGFPDD](TGFPDD.md) | CabecalhoConferencia / [TGFCON2](TGFCON2.md) | NUCONF->NUCONF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
