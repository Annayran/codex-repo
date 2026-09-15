# TGFCKI — Itens do Checkout

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 29
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CheckoutItem | Itens do Checkout | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASEICMS | Vlr. Base do ICMS | F |  |  | N |
| BASEIPI | Vlr. Base do IPI | F |  |  | N |
| BASEISS | Base do ISS | F |  |  | N |
| BASESUBSTIT | Base de Cálc. da ST de oper. ant. | F |  |  | N |
| CODBARRA | Cód. barras | S |  |  | N |
| CODLOCALORIG | Local origem | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHALTER | Dh. alteração | H |  |  | N |
| MENSAGEM | Mensagem | S |  |  | N |
| NUCKC | Nro. único | I |  |  | N |
| NUPROMOCAO | Nro. Promoção | I |  |  | N |
| NUTAB | Tabela | I |  |  | N |
| PERCDESC | % desconto | F |  |  | N |
| PRECOBASE | Preço base | F |  |  | N |
| QTDNEG | Quantidade | F |  |  | N |
| SEQCKI | Sequência | I |  |  | N |
| STATUS | Status | S |  |  | N |
| USOPROD | Usado como | S |  |  | N |
| VLRDESC | Vlr. desc. | F |  |  | N |
| VLRICMS | Vlr. do ICMS | F |  |  | N |
| VLRIPI | Vlr. do IPI | F |  |  | N |
| VLRISS | Vlr. ISS | F |  |  | N |
| VLRSUBST | Vlr. substituição | F |  |  | N |
| VLRTOT | Vlr. tot. | F |  |  | N |
| VLRTOTLIQ | Total líq. | F |  |  | S |
| VLRUNIT | Vlr. unit | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CheckoutItem / [TGFCKI](TGFCKI.md) | CheckoutCabecalho / [TGFCKC](TGFCKC.md) | NUCKC->NUCKC | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
