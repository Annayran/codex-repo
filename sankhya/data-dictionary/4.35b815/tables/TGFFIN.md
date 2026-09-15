# TGFFIN — Financeiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 284
- Instâncias DWF: 4
- Relações DWF: 109
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Financeiro | Financeiro | S | S |
| TimFinanceiro | Financeiro | S | N |
| TimFinanceiroAluguel | Financeiro Aluguel | S | N |
| TimFinanceiroRepasse | Financeiro Repasse | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABATIMENTO | Abatimento (Cob. Registrada) | F |  |  | N |
| ABATIMENTOCAN | Abatimento Cancelado (Cob. Registrada) | F |  |  | N |
| AGENCIA_CMC7 | Agência CMC7 | S |  |  | N |
| ALIQICMS | % ICMS | F |  |  | N |
| ANTECIPADO | Antecipado | S |  |  | S |
| ATRASO | Atraso (dias) | I |  |  | S |
| ATRASOINICIAL | Atraso 1º Vencto (dias) | I |  |  | S |
| AUTORIZADO | Autorizado | S |  |  | N |
| BAIXAAPI | Baixa via API | S |  |  | N |
| BASEICMS | Base ICMS | F |  |  | N |
| BASEINSS | Base INSS | F |  |  | N |
| BASEIRF | Base IRF | F |  |  | N |
| BLOQVAR | Bloq. Calculo Variação | S |  |  | N |
| CARTA | Nro Carta | I |  |  | N |
| CARTAODESC | Taxa Administradora | F |  |  | N |
| CGC_CPF_CMC7 | CNPJ/CPF CMC7 | S |  |  | N |
| CGC_CPF_PARC | CNPJ / CPF | S |  |  | S |
| CHAVECTE | Chave CT-e | S |  |  | N |
| CHAVECTEREF | Chave CT-e de referência | S |  |  | N |
| CHAVENFEGNRE | Chave de acesso NFe | S |  |  | N |
| CHAVEPIX | Chave Pix | S |  |  | S |
| CHEQUERASTREADO_CMC7 | Cheque rastreado | S |  |  | N |
| CLASSIFCESSAOOBRA | Classificação Cessão M.d.Obra | I |  |  | N |
| CODBARRA | Código de Barras | S |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODBCO_CMC7 | Código Banco CMC7 | I |  |  | N |
| CODCBE | Acerto Benefício | I |  |  | N |
| CODCC | CODCC | I |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCFO | CFOP | I |  |  | N |
| CODCIDFIMCTE | Cód. Cid. Fim CT-e | I |  |  | N |
| CODCIDINICTE | Cód. Cid. Inicio CT-e | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEMPBAIXA | Emp. da Baixa | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODHISTAC | Histórico | I |  |  | S |
| CODIGOBARRA | Cód. Barras Receb. | S |  |  | N |
| CODIMOVELRURAL | Imóvel Rural | I |  |  | N |
| CODIPTU | Conta IPTU | I |  |  | N |
| CODLANC | Cód. Lançamento | I |  |  | S |
| CODLST | Tipo de Serviço | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODOBRA | Cód. da Obra | S |  |  | N |
| CODOBSPADRAO | Observação padrão | I |  |  | N |
| CODOPERACAOVENDAMAIS | Código da Operação | S |  |  | S |
| CODPARC | Parceiro | I |  |  | N |
| CODPARCMATRIZ | Cod. Parceiro Matriz | I |  |  | S |
| CODPROJ | Projeto | I |  |  | N |
| CODPROR | Cód. Prorrogação VM | S |  |  | S |
| CODRECEITA | Código receita Darf | S |  |  | N |
| CODREGUA | Régua | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPOPERBAIXA | Tipo Operação Baixa | I |  |  | N |
| CODTIPTIT | Tipo de Título | I |  |  | N |
| CODTRIB | Tributação CT-e | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUBAIXA | Usuário Baixa | I |  |  | N |
| CODUSUCOBR | CODUSUCOBR | I |  |  | N |
| CODUSUPROR | Prorrogado por | I |  |  | S |
| CODVEICULO | Veículo | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CONCILIADO | Conciliado | S |  |  | S |
| CONTA_CMC7 | Conta CMC7 | S |  |  | N |
| CONTABILIZADO | Contabilizado? | S |  |  | S |
| CONTABILIZADOPDD | Título contabilizado PDD | S |  |  | S |
| CTABCOBAIXA | Conta Baixa | S |  |  | S |
| CUSTASPROCESSUAIS | Custas Processuais? | S |  |  | N |
| DEPOSITOJUDICIAL | Depósito Judicial? | S |  |  | N |
| DESCALINEACHEQDEV | Alínea Dev. Cheque | S |  |  | S |
| DESCOPERFIN | Descrição da Operação Financeira | S |  |  | S |
| DESCRHISTAC | Descrição histórico | S |  |  | S |
| DESCRLANCBCO | Desc. Lanc. Bancário | S |  |  | S |
| DESCRTPAGNFCE | Descrição do Tipo de Pagto NFC-e/NF-e/CF-e (Outros) | S |  |  | N |
| DESDOBDUPL | Desdob. Duplicata | S |  |  | N |
| DESDOBRAMENTO | Desdob. | S |  |  | N |
| DESPADVOGADO | Desp. com Advogado(s)? | S |  |  | N |
| DESPCART | Despesas c/ Cartório | F |  |  | N |
| DH_IMPRESSAO | Data Impressão | D |  |  | N |
| DHAPROVACAOVENDAMAIS | Dt. Aprovação | H |  |  | S |
| DHBAIXA | Data Baixa | D |  |  | N |
| DHCONCIL | Dh. Conciliação | H |  |  | S |
| DHMOV | Dt/Hr Movimentação | H |  |  | N |
| DHTIPOPER | Dt/Hr Operação | H |  |  | N |
| DHTIPOPERBAIXA | Dt/Hr Tipo Operação | H |  |  | N |
| DIGSAFRA | Dígito Safra | S |  |  | N |
| DTALTER | Últ. Alteração | H |  |  | N |
| DTANTECIPACAO | Dt. antecipação | D |  |  | S |
| DTBAIXAPREV | Dt. Prevista p/ Baixa | H |  |  | N |
| DTCONTAB | Dt. Contabilização | H |  |  | N |
| DTCONTABBAIXA | Dt. Contabilização Baixa | H |  |  | N |
| DTENTSAI | Dt. Entrada e Saída | D |  |  | N |
| DTENTSAIINFO | Data Extemporânea | D |  |  | N |
| DTINITREFAPURACAO | Data apuração | H |  |  | N |
| DTINTEGRACAOIPI | Data integração | D |  |  | N |
| DTNEG | Dt. Negociação | D |  |  | N |
| DTNEGFILTER | Filtro p/ Dt Negociação | D |  |  | S |
| DTPRAZO | Dt. Prazo | D |  |  | N |
| DTPROR | Prorrogado em | H |  |  | S |
| DTREFERENCIA | Período de referência | D |  |  | N |
| DTVENC | Dt. Vencimento | D |  |  | N |
| DTVENCINIC | Dt. Venc. Inicial | H |  |  | N |
| EMVPIX | PIX Copia e Cola | S |  |  | N |
| EXIGEISSQN | Exigibilidade do ISSQN DES-BH | S |  |  | N |
| HISTORICO | Histórico | S |  |  | N |
| IDLOTEFDIC | Identificador do Lote FDIC | I |  |  | N |
| IDTRANSACAOPIX | TXID Pix | S |  |  | S |
| IDUNICO | Identificador boleto rápido | I |  |  | N |
| INDRECEFDCONT | Indicador de Receita (registro F525 - EFD Cont.) | S |  |  | N |
| INFCOMPLEFDCONT | Informação complementar do Indic. comp. receita recebida | S |  |  | N |
| INSSRETIDO | INSS Retido | S |  |  | N |
| IRFRETIDO | IRRF Retido | S |  |  | N |
| ISSRETIDO | ISS Retido | S |  |  | N |
| JUROSAVP | Juros Ajuste do Valor Presente | F |  |  | N |
| LINHADIGITAVEL | Linha Dig. Receb. | S |  |  | N |
| M2 | Metro Quadrado | F |  |  | N |
| METODOCALCIRRF | Método de Cálculo IRRF | S |  |  | N |
| MODELONFDES | Modelo NF DES-BH | S |  |  | N |
| MONIOCOREM | Monitora Ocorrência de Remessa | S |  |  | N |
| MOSTRARENEG | Mostrar Renegociados | S |  |  | S |
| MOTNAORETERISSQN | Motivo de não Retenção ISSQN DES-BH | S |  |  | N |
| NATUREZAOPERDES | Natureza Oper. DES-BH | S |  |  | N |
| NFCOMPLFIX | Nf. Complemento Fixação | I |  |  | N |
| NFENTSEQFIX | Nf. Entrada Fixação/Sequência | S |  |  | N |
| NOMEEMITENTE_CMC7 | Nome Emitente CMC7 | S |  |  | N |
| NOSSONUM | Nosso Número | S |  |  | N |
| NROCESSAOFDIC | Nro. Cessao FDIC | S |  |  | S |
| NROIMPORT | Nro da importação | I |  |  | N |
| NROLOTEGNRE | Número Lote GNRE | I |  |  | N |
| NUANTBANC | Nro. único antecipação | I |  |  | N |
| NUAPONTA | Nro Único Apontamento | I |  |  | N |
| NUBCO | Nro Único Bancário | I |  |  | N |
| NUCAIXA | Núm. Caixa | I |  |  | N |
| NUCCR | Nro do cartão de crédito | I |  |  | N |
| NUCHQ | Nro. cheque | I |  |  | N |
| NUCKC | Nro. Checkout | I |  |  | N |
| NUCOMPENS | Nro Compensação/Acerto | I |  |  | N |
| NUDEV | Nro Único Devolução | I |  |  | N |
| NUFIN | Nro Único | I |  |  | N |
| NUFTC | Nro único da fatura | I |  |  | N |
| NUMBOR | Nro Borderô | I |  |  | N |
| NUMCOMPLFIX | Nro Nota Complemento Fixação | I |  |  | S |
| NUMCONTRATO | Nro Contrato | I |  |  | N |
| NUMDOCARRECAD | Nro Documento de arrecadação | S |  |  | N |
| NUMDUPL | Nro Duplicata | I |  |  | N |
| NUMENTSAFFIX | Nro Nota Entrada Fixação | I |  |  | S |
| NUMNFSE | Nro. NFS-e | S |  |  | N |
| NUMNOTA | Nro Nota | I |  |  | N |
| NUMOCORRENCIAS | Nr. da Ocorrência Caixa | S |  |  | N |
| NUMOS | Nro da OS | I |  |  | N |
| NUMPROCADMJUDIC | Nº Processo Administrativo/Judicial | S |  |  | N |
| NUMREMESSA | Nro Remessa | I |  |  | N |
| NUMTRANSF | Nro da Transferência | I |  |  | S |
| NUNOTA | Nro Único Nota | I |  |  | N |
| NUPED | Número Único do Pedido | I |  |  | N |
| NURENEG | Nro Renegociação | I |  |  | N |
| NUVERBA | Nro. Verba | I |  |  | N |
| NVDTVENC | Nova data de vencimento | D |  |  | S |
| OBRACONSTCIVIL | Obra de Construção Civil | I |  |  | N |
| OBSERVACAOAC | Observação | S |  |  | S |
| ORDEMCARGA | Ordem de Carga | I |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| PARCRENEG | Parcelas da Renegociação | S |  |  | N |
| PDD | PDD | S |  |  | N |
| PENDENTECRIARDESP | Prorrogado VM | S |  |  | S |
| PERCDESC | Percentual de Desconto | F |  |  | S |
| PERTENCEAC | Pertence ao acerto de carga | S |  |  | S |
| PIXTEF | Pix TEF | I |  |  | N |
| PRAZO | Prazo | I |  |  | S |
| PRAZOINICIAL | Prazo 1º Vencto | I |  |  | S |
| PRORROGADO | Prorrogado VM | S |  |  | S |
| PROVISAO | Provisão | S |  |  | N |
| RATEADO | Rateado | S |  |  | N |
| RATEADOCAB | Proveniente de nota rateada | S |  |  | S |
| RECADIANTAMENTORURAL | Receita de Adiantamentos | S |  |  | N |
| RECDESP | Receita/Despesa | I |  |  | N |
| RECDESPFILTER | Rec Desp p/ o Filtro | S |  |  | S |
| RECEBCARTAO | Recebim. c/ cartão | S |  |  | N |
| RECEBIDO | Recebido | S |  |  | N |
| REFATCON | Ref. Faturamento do Contrato | D |  |  | N |
| REGESPTRIBUT | Regime Especial de Tributação DES-BH | S |  |  | N |
| REJEICAOGNRE | Motivo Rejeição GNRE | S |  |  | N |
| RETORNADOAC | Retornado | S |  |  | S |
| SANGDESPDV | Sangria de despesa PDV | S |  |  | N |
| SEQCAIXA | Seq. Caixa | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SERIEENTSAFFIX | Série Entrada Fixação | S |  |  | S |
| SERIENFDES | Série NF DES-BH | S |  |  | N |
| SERIENOTA | Série da Nota | S |  |  | N |
| SERIENOTACOMPL | Série Complemento Fixação | S |  |  | S |
| SITESPECIALRESP | Situação Especial de Responsabilidade DES-BH | S |  |  | N |
| STATUSGNRE | Status GNRE | S |  |  | N |
| STATUSLIB | Status liberação | I |  |  | S |
| SUBTIPOVENDA | Subtipo do Tipo de Título | F |  |  | S |
| TAXAVENDAMAIS | Taxa Venda Mais | F |  |  | S |
| TIMBLOQUEADA | Bloqueada | S |  |  | N |
| TIMCONTALANC | Conta Lançamento | I |  |  | N |
| TIMCONTAREP | Conta rep. prop. | I |  |  | N |
| TIMCONTRATOADM | Contrato Adm. | I |  |  | N |
| TIMCONTRATOLOC | Contrato de Locação | I |  |  | N |
| TIMCONTRATOLTV | Contrato de Venda Lotes | I |  |  | N |
| TIMDATADEJUR | Dt. Ida Jurídico | H |  |  | N |
| TIMDHBAIXA | Dt. Pagamento | H |  |  | N |
| TIMDHGERREPASSE | Dh. Ger. Repasse | H |  |  | N |
| TIMDHGERREPPARCIAL | Dh. Ger. Rep. Parcial | H |  |  | N |
| TIMDTIMPBOL | Dt. Impressão Boleto | H |  |  | N |
| TIMDTIMPBOLLOCAL | Dh. Imp. Local Boleto | H |  |  | N |
| TIMDTREPASSE | Dt. Repasse | D |  |  | N |
| TIMDTREPPARCIAL | Dt. Repasse Parcial | D |  |  | N |
| TIMESTAGIO | Estágio | S |  |  | S |
| TIMFECHAMENTO | Fechamento Cond. | I |  |  | N |
| TIMFECHAMENTOALU | Fechamento Aluguel Perc. | I |  |  | N |
| TIMFINGARANTORIG | Fin. Resp. Garantia | I |  |  | N |
| TIMIMOVEL | Imóvel | I |  |  | N |
| TIMNEGOCIACAO | Negociação | I |  |  | N |
| TIMNOMEADVOGADO | Advogado | S |  |  | N |
| TIMNUFINORIG | Financeiro Origem | I |  |  | N |
| TIMNUMREG | Sequência Banco | I |  |  | N |
| TIMORIGEM | Tipo Parcela | S |  |  | N |
| TIMORIGRENEG | Repactuação | S |  |  | N |
| TIMPARCELA | Parcela | I |  |  | N |
| TIMRENEGCANCLOTE | Renegociado Por | I |  |  | N |
| TIMRENEGIMV | Renegociação da venda do imóvel | I |  |  | N |
| TIMRENEGLOTE | Renegociação Contrato | I |  |  | N |
| TIMREPINTELIGENTE | Usuário Rep. Inteligente | I |  |  | N |
| TIMREPPARCIAL | Repasse Parcial | S |  |  | N |
| TIMRESCISAOLOC | Rescisão de Locação | I |  |  | N |
| TIMRESCISAOLTV | Rescisão de Contrato | I |  |  | N |
| TIMSAC | Atendimento ao cliente (OS) | I |  |  | N |
| TIMTIPOINTERMED | Tipo de Intermediária | I |  |  | N |
| TIMTXADMGERALU | Tx Adm pela Parcela | S |  |  | N |
| TIMVENDAIMV | Contrato de venda do imóvel | I |  |  | N |
| TIMVENDALOTE | Contrato de venda de lote | I |  |  | N |
| TIMVLRALUGUEL | Vlr. Aluguel | F |  |  | S |
| TIMVLRAMORTCONTRATO | Vlr. Amortização Contrato | F |  |  | N |
| TIMVLRCORRMONET | Vlr. Corr. Monetária | F |  |  | N |
| TIMVLRJUROCONTRATO | Vlr. Juro Contrato | F |  |  | N |
| TIPAPURACAO | Tipo Apuração | S |  |  | N |
| TIPJURO | Tipo Juro | S |  |  | N |
| TIPMARCCHEQ | Tipo Marcação Cheque | S |  |  | N |
| TIPMULTA | Tipo Multa | S |  |  | N |
| TPAGNFCE | Tipo de pgto para NFC-e | S |  |  | N |
| TROCOPDV | Vlr Troco | F |  |  | N |
| VALORPRESENTE | Valor Presente | F |  |  | N |
| VENDAMAIS | Venda Mais | S |  |  | N |
| VLRALIBERAR | Vlr. a liberar | F |  |  | N |
| VLRATUAL | Vlr. Atual | I |  |  | S |
| VLRBAIXA | Vlr Baixa | F |  |  | N |
| VLRCESSAO | Vlr. Cessão FDIC | F |  |  | N |
| VLRCHEQUE | Vlr Cheque | F |  |  | N |
| VLRDESC | Vlr Desconto | F |  |  | N |
| VLRDESCCALC | Vlr. Desconto Calc. | F |  |  | N |
| VLRDESCEMBUT | Vlr Desc. Embutido | F |  |  | N |
| VLRDESDOB | Vlr do Desdobramento | F |  |  | N |
| VLRDESDOBCALC | Vlr do Desdobramento Calc | F |  |  | N |
| VLRFATCARTAO | Vlr. Fatura Cartão | F |  |  | N |
| VLRGNREDOIS | Vlr GNRE dois | F |  |  | N |
| VLRICMS | Vlr ICMS | F |  |  | S |
| VLRICMSXMLCTE | Vlr ICMS_XML | F |  |  | N |
| VLRINSS | Vlr INSS | F |  |  | N |
| VLRIRF | Vlr IRF | F |  |  | N |
| VLRISS | Vlr ISS | F |  |  | N |
| VLRJURO | Vlr Juros | F |  |  | N |
| VLRJUROCALC | Juros Calculados | F |  |  | S |
| VLRJUROEMBUT | Vlr Juros Embutidos | F |  |  | N |
| VLRJUROLIB | Vlr Perdão de Juros | F |  |  | N |
| VLRJURONEGOC | Vlr Juros Negociados | F |  |  | N |
| VLRJUROSMAISMULTA | Juros + Multa | F |  |  | S |
| VLRLANCORIG | Valor lançamento | F |  |  | S |
| VLRLIQUIDO | Valor Líquido | F |  |  | S |
| VLRMOEDA | Vlr Moeda | F |  |  | N |
| VLRMOEDABAIXA | Vlr Moeda na Baixa | F |  |  | N |
| VLRMULTA | Vlr Multa | F |  |  | N |
| VLRMULTACALC | Multa Calculada | F |  |  | S |
| VLRMULTAEMBUT | Vlr Multa Embutida | F |  |  | N |
| VLRMULTALIB | Vlr Perdão de Multa | F |  |  | N |
| VLRMULTANEGOC | Vlr Multa Negociada | F |  |  | N |
| VLRPROV | Vlr Provisão Financeira | F |  |  | N |
| VLRTOTALCALC | Total Calculado | F |  |  | S |
| VLRTROCO | Vlr Troco | F |  |  | S |
| VLRVARCAMBIAL | Variação Cambial | F |  |  | N |
| VLRVENDOR | Vlr Vendor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AcertoFrete / [TGFFRE](TGFFRE.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| AcertoOrdemCarga / [TGFACF](TGFACF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| AdiantamentoBaixaFinanceiro / [TGFABF](TGFABF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| AjustaValorPresente / [TGFAVP](TGFAVP.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| AntecipacaoRecebiveis / [TGFANB](TGFANB.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFINTITORI->NUFIN | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUNOTA->NUNOTA | I |
| CarimboFinanceiro / [TC_TGFFIN](TC_TGFFIN.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUCHAVE->NUFIN | I |
| Cheque / [TGFCHQ](TGFCHQ.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| DescontoFinanceiroAplicado / [TGFDFA](TGFDFA.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| DestinacaoFinanceiros / [TGFDVF](TGFDVF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| EmailBoletos / [TGFEBOL](TGFEBOL.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | AcertoOrdemCargaHist / [TGFACH](TGFACH.md) | CODHISTAC->CODHIST | I |
| Financeiro / [TGFFIN](TGFFIN.md) | AntecipacaoRecebiveis / [TGFANB](TGFANB.md) | NUFIN->NUFINTITORI | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | BoletoRapido / [TGFPJB](TGFPJB.md) | IDUNICO->IDUNICO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CartaoCredito / [TGFCCR](TGFCCR.md) | NUCCR->NUCCR | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CidadeDestino / [TSICID](TSICID.md) | CODCIDFIMCTE->CODCID | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CidadeOrigem / [TSICID](TSICID.md) | CODCIDINICTE->CODCID | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CODCFO->CODCFO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | CompFinancReinf / [TGFRNF](TGFRNF.md) | NUFIN->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Financeiro / [TGFFIN](TGFFIN.md) | EmpresaBaixa / [TSIEMP](TSIEMP.md) | CODEMPBAIXA->CODEMP | I |
| Financeiro / [TGFFIN](TGFFIN.md) | FaturaCartao / [TGFFTC](TGFFTC.md) | NUFTC->NUFTC | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Funcionario /  | CODEMP->CODEMP; CODFUNC->CODFUNC | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ImovelRural / [TSIEMP](TSIEMP.md) | CODIMOVELRURAL->CODEMP | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ImpostoFinanceiro / [TGFIMF](TGFIMF.md) | NUFIN->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | LiberacaoLimite / [TSILIB](TSILIB.md) | NUFIN->NUCHAVE | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ListaServicoSintegra / [TGFLST](TGFLST.md) | CODLST->CODLST | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | MovimentoBancario / [TGFMBC](TGFMBC.md) | NUBCO->NUBCO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Financeiro / [TGFFIN](TGFFIN.md) | NotasDocumentoArrecadacao / [TGFNDA](TGFNDA.md) | NUFIN->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBSPADRAO->CODOBSPADRAO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | OrdemCarga / [TGFORD](TGFORD.md) | CODEMP->CODEMP; ORDEMCARGA->ORDEMCARGA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Financeiro / [TGFFIN](TGFFIN.md) | PixTEF / [TGFPIXTEF](TGFPIXTEF.md) | PIXTEF->SEQPIXTEF | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Financeiro / [TGFFIN](TGFFIN.md) | RateioRecDesp / [TGFRAT](TGFRAT.md) | NUFIN->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | RateioVeiculo / [TGFRAV](TGFRAV.md) | NUFIN->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ReceitaDarf / [TGFRDARF](TGFRDARF.md) | CODRECEITA->CODRECEITA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Renegociacao / [TGFREN](TGFREN.md) | NUFIN->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TEF / [TGFTEF](TGFTEF.md) | NUFIN->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimContaBancaria / [TSICTA](TSICTA.md) | TIMCONTALANC->CODCTABCOINT | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimContratoAdm / [TIMADM](TIMADM.md) | TIMCONTRATOADM->ADMNUCONTRATO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TIMCONTRATOLTV->LTVCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimDetalhamento / [TIMDTL](TIMDTL.md) | NUFIN->DTLPARCELA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimDetalhamentoLoteamento / [TIMLDT](TIMLDT.md) | NUFIN->LDTPARCELA | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimFechamentoItens / [TIMFEI](TIMFEI.md) | TIMCONTRATOLOC->FEICONTRATOLOC; TIMFECHAMENTO->FEIFECHAMENTO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimFinanceiroRepasse / [TGFFIN](TGFFIN.md) | NUFIN->TIMNUFINORIG | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimImovel / [TIMIMV](TIMIMV.md) | TIMIMOVEL->IMVCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimIptu / [TIMIPT](TIMIPT.md) | CODIPTU->IPTCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimLocacao / [TIMLOC](TIMLOC.md) | TIMCONTRATOLOC->LOCCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimNegociacao / [TIMLCR](TIMLCR.md) | TIMCONTRATOLOC->LCRCONTRATO; TIMNEGOCIACAO->LCRCONTADOR | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimRenegociacao / [TIMLVR](TIMLVR.md) | TIMCONTRATOLTV->LVRCONTRATO; TIMRENEGLOTE->LVRCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimRescisao / [TIMDLV](TIMDLV.md) | TIMCONTRATOLTV->DLVCONTRATO; TIMRESCISAOLTV->DLVCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimRescisaoLoc / [TIMREC](TIMREC.md) | TIMRESCISAOLOC->RECCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimTipoIntermed / [TIMTIN](TIMTIN.md) | TIMTIPOINTERMED->TINCODIGO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER; DHTIPOPER->DHALTER | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERBAIXA->CODTIPOPER; DHTIPOPERBAIXA->DHALTER | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTIT->CODTIPTIT | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | TIMREPINTELIGENTE->CODUSU | I |
| Financeiro / [TGFFIN](TGFFIN.md) | UsuarioBaixa / [TSIUSU](TSIUSU.md) | CODUSUBAIXA->CODUSU | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEICULO->CODVEICULO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| FinanceiroOcorrenciaVendaMais / [TGFFINVMOCOR](TGFFINVMOCOR.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| FinanceiroVendaMais / [TGFFINVM](TGFFINVM.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| FreteNotaFinanceiro / [TGFFNF](TGFFNF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ImpostosItemFinanceiro / [TGFIIF](TGFIIF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ItemFaturaCartao / [TGFIFC](TGFIFC.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| JuroMensal / [TGFJUM](TGFJUM.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| LoteGNRE / [TGFLGNRE](TGFLGNRE.md) | Financeiro / [TGFFIN](TGFFIN.md) | NROLOTEGNRE->NROLOTEGNRE | I |
| MovimentoApontamento / [TAPMAP](TAPMAP.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUAPONTA->NUAPONTA | I |
| NotasDocumentoArrecadacao / [TGFNDA](TGFNDA.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ProrrogacaoVendaMais / [TGFPRORVM](TGFPRORVM.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| Recibo / [TGFREC](TGFREC.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| RegistroAlteracaoFinanceiro / [TGFRAF](TGFRAF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| RegistroCobrancasPix / [TGFMPX](TGFMPX.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| Renegociacao / [TGFREN](TGFREN.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| Repasse / [TGFFRP](TGFFRP.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFINORIG->NUFIN | I |
| TimAcumuloParcelas / [TIMACM](TIMACM.md) | Financeiro / [TGFFIN](TGFFIN.md) | ACMNUFIN->NUFIN | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | Financeiro / [TGFFIN](TGFFIN.md) | DTLPARCELA->NUFIN | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimFinanceiro / [TGFFIN](TGFFIN.md) | DTLFINDESP->NUFIN | I |
| TimFinanceiroAluguel / [TGFFIN](TGFFIN.md) | TimDetalhamento / [TIMDTL](TIMDTL.md) | NUFIN->DTLPARCELA | I |
| TimFinanceiroAluguel / [TGFFIN](TGFFIN.md) | TimLocacao / [TIMLOC](TIMLOC.md) | TIMCONTRATOLOC->LOCCODIGO | I |
| TimFinanceiroAluguel / [TGFFIN](TGFFIN.md) | TimNegociacao / [TIMLCR](TIMLCR.md) | TIMNEGOCIACAO->LCRCODIGO | I |
| TimFinanceiroRepasse / [TGFFIN](TGFFIN.md) | Financeiro / [TGFFIN](TGFFIN.md) | TIMNUFINORIG->NUFIN | I |
| TimFinanceiroRepasse / [TGFFIN](TGFFIN.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| TimFinanceiroRepasse / [TGFFIN](TGFFIN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Financeiro / [TGFFIN](TGFFIN.md) | LOTMODFINPARC->NUFIN | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimFinanceiro / [TGFFIN](TGFFIN.md) | LOTMODFINRENEGPARC->NUFIN | I |
| TimNegociacao / [TIMLCR](TIMLCR.md) | Financeiro / [TGFFIN](TGFFIN.md) | LCRCONTADOR->TIMNEGOCIACAO; LCRCONTRATO->TIMCONTRATOLOC | I |
| TimParcelasOrigem / [TIMACO](TIMACO.md) | Financeiro / [TGFFIN](TGFFIN.md) | ACONUFIN->NUFIN | I |
| TimRenegNovaParcela / [TIMREP](TIMREP.md) | Financeiro / [TGFFIN](TGFFIN.md) | REPNUFIN->NUFIN | I |
| TimRenegParcelaOrigem / [TIMREO](TIMREO.md) | Financeiro / [TGFFIN](TGFFIN.md) | REONUFIN->NUFIN | I |
| TituloPorEventoRegua / [TGFTXE](TGFTXE.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| TituloPorEventoReguaStatus / [TGFTXECONTROLST](TGFTXECONTROLST.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ViewComissaoFechamento / [VGFCOM_FECH](VGFCOM_FECH.md) | Financeiro / [TGFFIN](TGFFIN.md) | FINNUFIN->NUFIN | L |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| ViewRegistroCobrancasPix / [VGFMPX](VGFMPX.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| VinculoVerbaFinanceiro / [TGFVVF](TGFVVF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
