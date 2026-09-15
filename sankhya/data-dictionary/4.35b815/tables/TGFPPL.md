# TGFPPL — Planejamento de produção

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 32
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PlanejamentoProducao | Planejamento de Produção | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPARCTRANSP | Cód. Parceiro Transportadora | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSULIBLOG | Cód. Usuário Liberador (Logística) | I |  |  | N |
| CODUSULIBPROD | Cód. Usuário Liberador (Produção) | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHLIBLOG | Dt/Hora Lib (Logística) | H |  |  | N |
| DHLIBPROD | Dt/Hora Lib. (Produção) | H |  |  | N |
| DTMOV | Dt Movimento | D |  |  | N |
| DTPLA | Dt Plan. | D |  |  | N |
| DTREF | Dt Refência | D |  |  | N |
| LIBLOGISTICA | Lib. logística | S |  |  | S |
| LIBPRODUCAO | Lib. Produção | S |  |  | S |
| NUNOTA | Nota Gerada na Produção | I |  |  | N |
| NUNOTAORIG | Nota Origem | I |  |  | N |
| NUPLAN | Nro Plan. | I |  |  | N |
| NUPLANORIG | Plan. Origem | I |  |  | N |
| OBS | Observação | S |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| PEDIDOORIGEM | Pedido | I |  |  | S |
| PLANDEMAND | Plan. por demanda | S |  |  | N |
| PROBLEMA | Problema | S |  |  | N |
| QTD | Quantidade | F |  |  | N |
| QTDEMPROD | Em produção | F |  |  | S |
| QTDGERADA | Qtd. já Gerada | F |  |  | S |
| QTDPRODUZIDO | Produzido | F |  |  | S |
| SALDOPEDIDO | Saldo Pedido | F |  |  | S |
| SEQPREV | Seq. Previsão | I |  |  | N |
| SEQUENCIAORIG | Item Origem | I |  |  | N |
| VARIACAO | Variação | I |  |  | N |
| VARIACAOMAPA | Variação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MateriaPrimaProduto / [TGFPPM](TGFPPM.md) | PlanejamentoProducao / [TGFPPL](TGFPPL.md) | NUPLAN->NUPLAN | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTAORIG->NUNOTA; SEQUENCIAORIG->SEQUENCIA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | MateriaPrimaProduto / [TGFPPM](TGFPPM.md) | NUPLAN->NUPLAN | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | NotaOrigem / [TGFCAB](TGFCAB.md) | NUNOTAORIG->NUNOTA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | PedidoOrigem / [TGFCAB](TGFCAB.md) | PEDIDOORIGEM->NUNOTA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | PrevisaoEntrega / [TGFDTP](TGFDTP.md) | NUNOTAORIG->NUNOTA; SEQPREV->SEQPREV; SEQUENCIAORIG->SEQUENCIA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| RomaneioCarga / [TGFROM](TGFROM.md) | PlanejamentoProducao / [TGFPPL](TGFPPL.md) | NUPLAN->NUPLAN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
