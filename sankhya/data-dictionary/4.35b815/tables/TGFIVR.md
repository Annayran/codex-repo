# TGFIVR — Itens de Entr. e Saída de Produto VR

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 46
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemPedidoVR | Item Pedido Venda Rápida | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQICMS | Alíq. ICMS | F |  |  | N |
| ALIQIPI | Alíq. IPI | F |  |  | N |
| ALIQISS | Alíq. ISS | F |  |  | N |
| BASEICMS | Base ICMS | F |  |  | N |
| BASEIPI | Base do IPI | F |  |  | N |
| BASEISS | Base do ISS | F |  |  | N |
| BASESUBSTIT | Base substituição | F |  |  | N |
| CODBENEFNAUF | Cód. de Benefício Fiscal na UF | S |  |  | N |
| CODCFO | CFOP | I |  |  | N |
| CODLOCALORIG | Local origem | I |  |  | N |
| CODLOCALTERC | Local terceiros | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODTRIB | Tributação | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CODVOLPAD | Unidade padrão | S |  |  | S |
| CONTROLE | Controle | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| IDALIQICMS | Cód. Alíq. ICMS | I |  |  | N |
| NUCVR | Nro. Único | I |  |  | N |
| NUFOP | Finalidade da Operação | I |  |  | N |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| NUPROMOCAO | Nro. Promoção | I |  |  | N |
| NUTAB | Tabela | I |  |  | N |
| PAINELPERSONALIZADO | Painel Personalizado | S |  |  | N |
| PERCDESC | % desconto | F |  |  | N |
| PERCDESCDIGITADO | % desc. digitado | F |  |  | N |
| PERCDESCTGFDES | % desc. promoção | F |  |  | N |
| PRECOBASE | Preço base | F |  |  | N |
| QTDNEG | Quantidade | F |  |  | N |
| SEQIVR | Seq. Item | I |  |  | N |
| TIPLANCNOTA | Digitação na nota | S |  |  | N |
| USOPROD | Uso do Produto | S |  |  | N |
| VLRACRESDESC | Vlr. acresc./desc. | F |  |  | N |
| VLRCUS | Vlr. custos | F |  |  | N |
| VLRDESC | Vlr. desconto | F |  |  | N |
| VLRDESCDIGITADO | Vlr. desc. digitado | F |  |  | N |
| VLRICMS | Vlr. ICMS | F |  |  | N |
| VLRIPI | Vlr. IPI | F |  |  | N |
| VLRISS | Vlr. ISS | F |  |  | N |
| VLRSUBST | Vlr. substituição | F |  |  | N |
| VLRTOT | Vlr. total | F |  |  | N |
| VLRTOTLIQ | Total líq. | F |  |  | S |
| VLRUNIT | Vlr. unitário | F |  |  | N |
| VLRUNITLIQ | Preço líq. | F |  |  | N |
| VLRVENDAPROMO | Vlr. Venda Promoção | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | ItemPedidoVR / [TGFIVR](TGFIVR.md) | NUCVR->NUCVR | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | NUCVR->NUCVR | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | FinalidadeOperacao / [TGFFOP](TGFFOP.md) | NUFOP->NUFOP | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | ImpostoItemVR / [TLFDINVR](TLFDINVR.md) | NUCVR->NUCVR; SEQIVR->SEQIVR | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
