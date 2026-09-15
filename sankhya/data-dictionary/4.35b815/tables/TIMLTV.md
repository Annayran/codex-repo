# TIMLTV — Contrato de Venda de Lote

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 85
- Instâncias DWF: 1
- Relações DWF: 32
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimContratoVendaLote | Venda de Lote | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FILTROCAMPRADOR | Comprador | I |  |  | S |
| FILTROCPFPARCEIRO | CPF Comprador | S |  |  | S |
| FILTROLOTE | Lote | S |  |  | S |
| FILTROLOTEAMENTO | Loteamento | I |  |  | S |
| FILTROOLDCOMPRADOR | Antigo Comprador | I |  |  | S |
| FILTROQUADRA | Quadra | S |  |  | S |
| LTVADIANTAMENTO | Vlr. Total Arras | F |  |  | N |
| LTVAPLICARFRUICAO | Rescisão Conforme Lei do Distrato (nº 13.786/2018) | S |  |  | N |
| LTVASSINADOPROPRIETARIO | Dt. Ass. Proprietário | D |  |  | N |
| LTVCARENCIA | Dias de Carência p/ contrato | I |  |  | N |
| LTVCCA | Corte Concil. Arbitragem | S |  |  | N |
| LTVCODIGO | Código | I |  |  | N |
| LTVCODIGOSISLOTE | Código SISLOTE | I |  |  | N |
| LTVCOMADMRECA | Comissão Adminis. % | F |  |  | S |
| LTVCOMCORRECA | Comissão Corretor % | F |  |  | S |
| LTVCOMFRARECA | Comissão Franquia % | F |  |  | S |
| LTVCONTRATO | Contrato | I |  |  | N |
| LTVCOREXTERNO | Corretor externo | S |  |  | N |
| LTVCORRETOR | Corretor | I |  |  | N |
| LTVDATACADASTRO | Dt. Cadastro | D |  |  | N |
| LTVDATADAVENDA | Dt. Venda | D |  |  | N |
| LTVDATANOTIFICACAO | Dt. Notificação | D |  |  | N |
| LTVDESCONTO | Vlr. Desconto | F |  |  | N |
| LTVDESCRICAO | Descrição | S |  |  | S |
| LTVDHALTERACAO | Dh. Alteração | H |  |  | N |
| LTVDHINCLUSAO | Dh. Inclusão | H |  |  | N |
| LTVDTFINANCBANCO | Dt. Financ. Banco | D |  |  | N |
| LTVDTINICFINANC | Dt. Juros Carência | D |  |  | N |
| LTVDTOCUPADO | Dt. Ocupação | D |  |  | N |
| LTVDTULTREAJUSTE | Dt. Últ. Reajuste | D |  |  | N |
| LTVEMISSAOCONTRATO | Dt. Emissão escritura | D |  |  | N |
| LTVENTRADA | Vlr. Total Entrada/Sinal | F |  |  | N |
| LTVESTAGIO | Estágio | S |  |  | N |
| LTVGERARECA | Comissionar por | S |  |  | S |
| LTVGERENTECOR | Ger. Sup. | I |  |  | N |
| LTVGEROUPARCS | Gerou Parcelas | S |  |  | N |
| LTVIMOBILIARIA | Imobiliária | I |  |  | N |
| LTVIMVESTIMENTO | Comprou Para | S |  |  | N |
| LTVINDICECORRECAO | Índice Correção | I |  |  | N |
| LTVJUSTICACOMUM | Justiça Comum | S |  |  | N |
| LTVLEGADO | Legado | S |  |  | N |
| LTVLOTE | Lote | I |  |  | N |
| LTVMAISJUROS | Tx. Juros % | F |  |  | N |
| LTVNAOCOBRAR | Não cobrar | S |  |  | N |
| LTVNC | Notif. Cartorial | S |  |  | N |
| LTVOLDJUROS | Tx. Juros Original % | F |  |  | N |
| LTVPARCARRAS | Qtd. Parcela Arras | I |  |  | N |
| LTVPARCELAS | Qtd. Parcelas | I |  |  | N |
| LTVPE | Por. Edital | S |  |  | N |
| LTVPERCDESCONTO | Vlr. Desconto % | F |  |  | N |
| LTVPERIODICIDADE | Periodicidade | I |  |  | N |
| LTVPOUPANCA | Vlr. Poupança % | F |  |  | N |
| LTVPOUPPARCELAS | Parc. Poupança | I |  |  | N |
| LTVPOUPPARCELASVL | Vlr. Poupança Parcelas | F |  |  | N |
| LTVPOUPPRICE | Poup. Price | S |  |  | N |
| LTVPQDESCONTO | Justif. Desconto | S |  |  | N |
| LTVPRAZOESCRITURA | Prazo de Escrituração | S |  |  | N |
| LTVPRECODOLOTE | Vlr. Lote | F |  |  | N |
| LTVPRECOVENDIDO | Vlr. Venda | F |  |  | N |
| LTVPRIMEIRAPARCELA | Dt. Primeira Parcela | D |  |  | N |
| LTVPRIPARARRAS | Dt. Primeira Arras | D |  |  | N |
| LTVPROTOCOLO | Protocolo | S |  |  | N |
| LTVPROXREAJUSTE | Dt. Próx. Reajuste | D |  |  | N |
| LTVQTPPARDIF | Qtd. Parcela Diferenciada | I |  |  | N |
| LTVQTPPARENTRADA | Qtd. Parcela Entrada/Sinal | I |  |  | N |
| LTVRECAADM | Vlr. Comissão Adminis. | F |  |  | N |
| LTVRECACORRETOR | Vlr. Comissão Corretor | F |  |  | N |
| LTVRECAFRANQUIA | Vlr. Comissão Franquia | F |  |  | N |
| LTVSITUACAO | Sit. (Situação) | S |  |  | N |
| LTVSUPERVISORCOR | Supervisor | I |  |  | N |
| LTVTABELAPRICE | Price | S |  |  | N |
| LTVTIPORECA | Tipo Comissão | S |  |  | N |
| LTVTOTALDIFER | Vlr. Total Diferenciada | F |  |  | N |
| LTVTXCOMMANUAL | Tx/Comiss. manualmente | S |  |  | N |
| LTVURGENCIA | Urgência | S |  |  | N |
| LTVUSUALTERACAO | Usuário Alteração | I |  |  | N |
| LTVUSUINCLUSAO | Usuário Inclusão | I |  |  | N |
| LTVVALORFECHAMENTO | Vlr. Fechamento | F |  |  | N |
| LTVVALORPARCELA | Vlr. Parcela | F |  |  | N |
| LTVVENCPARENTRADA | Dt. Primeira Entrada/Sinal | D |  |  | N |
| LTVVISTORIA | Vistoria | S |  |  | N |
| LTVVLRFINANC | Vlr. Financiar | F |  |  | N |
| LTVVLRFINANCBANCO | Vlr. Financ. Banco | F |  |  | N |
| LTVVLRTOTALBALAO | Vlr. Total Balões | F |  |  | N |
| LTVVLRTOTALINTERMED | Vlr. Total Intermediárias | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TIMCONTRATOLTV->LTVCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TIMCONTRATOLTV->LTVCODIGO | I |
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | ACMCONTRATO->LTVCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | SACVENDALOTE->LTVCODIGO | I |
| TimBalao / [TIMBAL](TIMBAL.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | BALCONTRATO->LTVCODIGO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | CHKCONTRATOLTV->LTVCODIGO | I |
| TimContratoCancelado / [TIMAPO](TIMAPO.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | APOCONTRATO->LTVCODIGO | I |
| TimContratoRenegociado / [TIMAPR](TIMAPR.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | APRCONTRATO->LTVCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Moeda / [TSIMOE](TSIMOE.md) | LTVINDICECORRECAO->CODMOEDA | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Parceiro / [TGFPAR](TGFPAR.md) | FILTROCAMPRADOR->CODPARC | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | FILTROOLDCOMPRADOR->CODPARC | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimAcumuloParcelas / [TIMACM](TIMACM.md) | LTVCODIGO->ACMCONTRATO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimBenfeitoriasLote / [TIMLBF](TIMLBF.md) | LTVCODIGO->LBFCONTRATO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimCompradorLote / [TIMLVC](TIMLVC.md) | LTVCODIGO->LVCCONTRATO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimCorretor / [TIMCOR](TIMCOR.md) | LTVCORRETOR->CORCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimCorretorGerente2 / [TIMCOR](TIMCOR.md) | LTVSUPERVISORCOR->CORCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimGerenteCorretor / [TIMCOR](TIMCOR.md) | LTVGERENTECOR->CORCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | LTVIMOBILIARIA->IMBCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimLote / [TIMLTE](TIMLTE.md) | LTVLOTE->LTECODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | FILTROLOTEAMENTO->LOTCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimRenegociacao / [TIMLVR](TIMLVR.md) | LTVCODIGO->LVRCONTRATO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimRescisao / [TIMDLV](TIMDLV.md) | LTVCODIGO->DLVCONTRATO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimTransferencia / [TIMTLV](TIMTLV.md) | LTVCODIGO->TLVCONTRATO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Usuario / [TSIUSU](TSIUSU.md) | LTVUSUINCLUSAO->CODUSU | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | Usuario2 / [TSIUSU](TSIUSU.md) | LTVUSUALTERACAO->CODUSU | I |
| TimIntermediaria / [TIMINT](TIMINT.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | INTCONTRATO->LTVCODIGO | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | MFDCONTRATOLTV->LTVCODIGO | I |
| TimReajusteContrato / [TIMRCV](TIMRCV.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | RCVCONTRATO->LTVCODIGO | I |
| TimRenegociacao / [TIMLVR](TIMLVR.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | LVRCONTRATO->LTVCODIGO | I |
| TimRenegParcelas / [TIMREN](TIMREN.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | RENCONTRATO->LTVCODIGO | I |
| TimTransferencia / [TIMTLV](TIMTLV.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TLVCONTRATO->LTVCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | VDOCONTRATOLTV->LTVCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
