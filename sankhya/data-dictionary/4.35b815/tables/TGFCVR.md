# TGFCVR — Cabeçalho Venda Rápida

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 55
- Instâncias DWF: 1
- Relações DWF: 17
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoVendaRapida | Cabeçalho Venda Rápida | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQIRF | Alíquota IRF | F |  |  | N |
| BASEICMS | Base do ICMS | F |  |  | N |
| BASEINSS | Base do INSS | F |  |  | N |
| BASEIPI | Base do IPI | F |  |  | N |
| BASEIRF | Base do IRF | F |  |  | N |
| BASEISS | Base do ISS | F |  |  | N |
| BASESUBSTIT | Base da Substituição | F |  |  | N |
| CIF_FOB | CIF / FOB | S |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPARCDEST | Parceiro Destinatário | I |  |  | N |
| CODPARCTRANSP | Parc. Transportadora | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPVENDA | Tipo Negociação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEICULO | Veículo | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| DHTIPOPER | Dt./Hr. Operação | H |  |  | N |
| DHTIPVENDA | Dt./Hr. Tipo de Neg. | H |  |  | N |
| DTENTSAI | Dt. Entrada/Saída | D |  |  | N |
| DTFATUR | Dt. do Faturamento | H |  |  | N |
| DTFECHAMENTO | Dt. Fechamento | D |  |  | N |
| DTINSERCAO | Dt. Inserção | D |  |  | N |
| DTMOV | Dt. do Movimento | D |  |  | N |
| DTNEG | Dt. Neg. | D |  |  | N |
| IRFRETIDO | o INSS é retido? | S |  |  | N |
| ISSRETIDO | ISS Retido na fonte | S |  |  | N |
| NUCVR | Nro. Único | I |  |  | N |
| NUFOP | Finalidade da Operação | I |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | N |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PERCDESC | % de Desconto | F |  |  | N |
| PERCDESCFOB | Perc. desc. FOB | F |  |  | N |
| QTDITENS | Qtd. Itens | I |  |  | S |
| QTDVOL | Qtd. volumes | I |  |  | N |
| SERIENOTA | Série da Nota | S |  |  | N |
| STATUS | Status do Carrinho | S |  |  | N |
| TIPFRETE | Tip. Frete | S |  |  | N |
| TIPMOV | Tip. Mov. | S |  |  | N |
| VENCFRETE | Venc. Frete | D |  |  | N |
| VLRDESCTOT | Vlr. Desc. Total | F |  |  | N |
| VLRDESCTOTITEM | Desconto total por item | F |  |  | N |
| VLRFRETE | Vlr. Frete | F |  |  | N |
| VLRICMS | Vlr. do ICMS | F |  |  | N |
| VLRINSS | Vlr. INSS | F |  |  | N |
| VLRIPI | Vlr. do IPI | F |  |  | N |
| VLRIRF | Vlr. IRF | F |  |  | N |
| VLRISS | Vlr. ISS | F |  |  | N |
| VLRJURO | Vlr. Juros | F |  |  | N |
| VLRNOTA | Vlr. Nota | F |  |  | N |
| VLRSUBST | Vlr. da Substituição | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Contato / [TGFCTT](TGFCTT.md) | CODPARC->CODPARC; CODCONTATO->CODCONTATO | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | FinalidadeOperacao / [TGFFOP](TGFFOP.md) | NUFOP->NUFOP | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | NUCVR->NUCVR | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | ItemPedidoVR / [TGFIVR](TGFIVR.md) | NUCVR->NUCVR | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | ParceiroDestino / [TGFPAR](TGFPAR.md) | CODPARCDEST->CODPARC | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA; DHTIPVENDA->DHALTER | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Transportadora / [TGFPAR](TGFPAR.md) | CODPARCTRANSP->CODPARC | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | NUCVR->NUCVR | I |
| ItemPedidoVR / [TGFIVR](TGFIVR.md) | CabecalhoVendaRapida / [TGFCVR](TGFCVR.md) | NUCVR->NUCVR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
