# TSICTA — Contas Bancárias

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 116
- Instâncias DWF: 8
- Relações DWF: 64
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContaBancaria | Conta Bancária | S | S |
| ContaBancariaDespesa | Contas Bancárias para Despesas | S | N |
| ContaBancariaIpi | Conta Bancária | S | N |
| ContaBancariaReceita | Contas Bancárias para Receitas | S | N |
| ContaBancarioFgts | ContaBancarioFgts | S | N |
| ContaDestino | Conta de destino | S | N |
| ImplantacaoSaldoConta | Implantação dos saldos de conta | S | N |
| TimContaBancaria | Conta Bancária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACEITATITULOVENCIDO | Aceita Titulo Vencido | S |  |  | N |
| APIBAIXAAUTOMATICA | Selecione o tipo | S |  |  | N |
| APICONCILIACAOAUTOMATICA | Selecione o tipo | S |  |  | N |
| ATIVA | Ativa | S |  |  | N |
| BJBBAIBOLPAG | Baixar o tílulo quando o boleto for pago | S |  |  | N |
| CAMPOLIVRE | Campo Livre | S |  |  | N |
| CARTEIRA | Carteira | I |  |  | N |
| CATEGLANCHQ | Categoria p/ lançamento de cheque | I |  |  | N |
| CHAVEAPIPIX | Chave da API Pix | S |  |  | N |
| CHAVEPIX | Chave pix | S |  |  | N |
| CLASSE | Tipo de conta | S |  |  | N |
| CODAGE | Agência bancária | S |  |  | N |
| CODAGEBENEF | Agência Beneficiário | I |  |  | N |
| CODBCO | Banco | I |  |  | N |
| CODCONTARURAL | Classificação da conta Produtor Rural | S |  |  | N |
| CODCORRBCO | Correspondente bancário | I |  |  | N |
| CODCTABAIXA | Conta p/ baixa (Processamento de Retorno) | I |  |  | N |
| CODCTABCO | Conta | S |  |  | N |
| CODCTABCOINT | Código da conta bancária | I |  |  | N |
| CODCTABCOINTREM | Conta p/controlar a seq.de remessa | I |  |  | N |
| CODCTABENEF | Conta Beneficiário | I |  |  | N |
| CODCTACTB | Conta contábil | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLANCBAIXABOLRAP | Lançamento baixa boleto | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODOPEREXCL | Operador exclusivo | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODTIPOPERBAIXABOLRAP | TOP Baixa boleto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONCAUTRECEBPIX | Conciliar automaticamente os recebimentos via API | S |  |  | N |
| CONTABILIZARDIAS | Contabilizar dia útil ou dias corridos | S |  |  | N |
| CONVENIO | Convênio | I |  |  | N |
| CTADEFEMIBOL | Conta padrão para emissão | S |  |  | N |
| CTAMINBOLETA | Alterar p/outra conta | I |  |  | N |
| DATAJURO | Data dos juros | S |  |  | N |
| DATAMULTA | Selecione o tipo | S |  |  | N |
| DESCONSLCDPR | Desconsiderar Conta na Geração do Livro Caixa Digital do Produtor Rural | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DIASJURO | Dias Juros | I |  |  | N |
| DIASMULTA | Dias Multa | I |  |  | N |
| DIASPARANEGATIVACAO | Dias para Negativação | I |  |  | N |
| DIASPROT | Dias para protesto | I |  |  | N |
| DIASPROTESTO | Dias para protesto | I |  |  | N |
| DIGITOSUBST1 | substitui por: | S |  |  | N |
| DIGITOSUBST2 | substitui por: | S |  |  | N |
| DIGITOSUBST3 | substitui por: | S |  |  | N |
| DTALTER | Data alteração | D |  |  | N |
| DTDESCREDCONTA | Data de descredenciamento da conta | D |  |  | N |
| DTENVIOAPIBANCO | Data da integração com a API de boletos | D |  |  | N |
| DTIMPLANT | Referência p/ aceitar lançamentos | D |  |  | N |
| DTREGCONTA | Data de registro da conta | D |  |  | N |
| EMITEBOLETA | Emite | S |  |  | N |
| EXCLUSIVA | Exclusiva da empresa | S |  |  | N |
| IDAPIBANCO | Sequência remessa alternativa | I |  |  | N |
| IDCLIENTE | ID do cliente | S |  |  | N |
| IDCLIPIX | Client ID Pix | S |  |  | N |
| IDSEQBOL | Id Sequencial do boleto Liquidado | I |  |  | N |
| IMPBOLETA | Impressora | S |  |  | N |
| INDICADORPIX | Indicador pix | S |  |  | N |
| INDNOSSONUM | Indicador do Nosso Número | S |  |  | N |
| INSTRUCAOI | Instrução I | I |  |  | N |
| INSTRUCAOII | Instrução II | I |  |  | N |
| INSTRUCAONEGATIVACAO | Instrução de negativação | I |  |  | N |
| INSTRUCAOPROTESTO | Instrução de protesto | I |  |  | N |
| LINHADIGATIVO | Usar geração da Linha Digitável | S |  |  | N |
| LOGOURL | Guarda logomarca | S |  |  | N |
| MENADICPIX | Mensagem Inf. Adicional Pix API | S |  |  | N |
| MODALIDADE | Modalidade | I |  |  | N |
| MODBOLETA | Modelo | I |  |  | N |
| MULTIPNOSSONUM | Multiplicadores | S |  |  | N |
| NOSSONUMATIVO | Usar geração de Nosso Número | S |  |  | N |
| NOSSONUMERO | Configuração do Nosso Número | S |  |  | N |
| NUCONTRATO | Nro. Contrato | I |  |  | N |
| NUMBENEFICIARIO | Número Beneficiário | S |  |  | N |
| NUMCHEQ | Último nro. cheque | I |  |  | N |
| NUMCLIENTE | Número do cliente | I |  |  | N |
| NUMCONTARURAL | Número da conta Produtor Rural | S |  |  | N |
| NURFEMODBOLETO | Modelo de Boleto | I |  |  | N |
| NURFEMODCHEQG | Modelo de cheque | I |  |  | N |
| ORGAONEGATIVADOR | Orgão Negativador | I |  |  | N |
| PJBCHAVE | Chave | S |  |  | N |
| PJBCONBAIXCRED | Conciliar a baixa do titulo ao receber o crédito | S |  |  | N |
| PJBCRED | Credencial | S |  |  | N |
| QTDDIASVALPIX | Qtd. Dias Val. Pix após Venc | I |  |  | N |
| RECEBIMENTODIAS | Dias de recebimento | I |  |  | N |
| RECEBIMENTOPARCIAL | Recebimento parcial | S |  |  | N |
| REMBCO | Último boleto | I |  |  | N |
| REMBCOMAX | Número máximo | I |  |  | N |
| REMFINAL | Nro máx.p/seq.remessa | I |  |  | N |
| REMFINAL2 | Nro máx.p/seq.remessa alternativa | I |  |  | N |
| RESTOSUBST1 | Se resto igual: | I |  |  | N |
| RESTOSUBST2 | Se resto igual: | I |  |  | N |
| RESTOSUBST3 | Se resto igual: | I |  |  | N |
| SALDOBCO | Saldo do banco na referência | F |  |  | N |
| SALDOREAL | Saldo real na referência | F |  |  | N |
| SENCLIPIX | Client Secret Pix | S |  |  | N |
| SEQREM | Sequência remessa | I |  |  | N |
| SEQREM2 | Sequência remessa alternativa | I |  |  | N |
| STATUSAPI | Status Api | S |  |  | N |
| SUBRESTMODULO | Substitui resto: | S |  |  | N |
| TAXA | Ou cobrar taxa | F |  |  | N |
| TIPMODNOSSNUM | Tipo do Módulo | S |  |  | N |
| TIPMULTIPSOMA | Ao somar as multiplicações | S |  |  | N |
| TIPOAPIBOLETO | Selecione o serviço | S |  |  | N |
| TIPOBOLETO | Tipo Boleto | S |  |  | N |
| TIPOIMPRESSORA | Tipo impressora | S |  |  | N |
| TIPOJUROS | Selecione o tipo | S |  |  | N |
| TIPOMULTA | Selecione o tipo | S |  |  | N |
| TITINFADICPIX | Titulo Inf. Adicional Pix API | S |  |  | N |
| URLPIX | URL pix | S |  |  | N |
| UTILIZAPIXPDV | Utiliza PIX PDV | S |  |  | N |
| VALORJUROS | Valor juros | F |  |  | N |
| VALORMULTA | Valor multa | F |  |  | N |
| VARIACAO | Variação | I |  |  | N |
| VLRMINBOLETA | Valor mínimo | F |  |  | N |
| ZERARAUT | Zerar automaticamente | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendadorRemessaBancaria / [TGFREM](TGFREM.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| BoletoRapido / [TGFPJB](TGFPJB.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| CertificadoPix / [TGFCERT](TGFCERT.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ConfRegAlteracaoFinanceiro / [TGFCRAF](TGFCRAF.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ContaBancaria / [TSICTA](TSICTA.md) | AgenciaBancaria / [TSIAGE](TSIAGE.md) | CODAGE->CODAGE; CODBCO->CODBCO | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ContaBancaria / [TSICTA](TSICTA.md) | CategoriaLancamentoExtrato / [TGFCLE](TGFCLE.md) | CODCTABCOINT->CODCTABCOINT | I |
| ContaBancaria / [TSICTA](TSICTA.md) | CEPsBancoSafra / [TGFCBS](TGFCBS.md) | CODCTABCOINT->CODCTABCOINT | I |
| ContaBancaria / [TSICTA](TSICTA.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABAIXA->CODCTABCOINT | I |
| ContaBancaria / [TSICTA](TSICTA.md) | ContaBancariaDespesa / [TSICTA](TSICTA.md) | CTAMINBOLETA->CODCTABCOINT | I |
| ContaBancaria / [TSICTA](TSICTA.md) | ContaDestino / [TSICTA](TSICTA.md) | CODCTABCOINTREM->CODCTABCOINT | I |
| ContaBancaria / [TSICTA](TSICTA.md) | ConvenioConta / [TGFCOC](TGFCOC.md) | CODCTABCOINT->CODCTABCOINT | I |
| ContaBancaria / [TSICTA](TSICTA.md) | CorrespondenteBancario / [TSIBCO](TSIBCO.md) | CODCORRBCO->CODBCO | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContaBancaria / [TSICTA](TSICTA.md) | HistoricoBancario / [TGFHBC](TGFHBC.md) | CODLANCBAIXABOLRAP->CODLANC | I |
| ContaBancaria / [TSICTA](TSICTA.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | MODBOLETA->CODMODNF | I |
| ContaBancaria / [TSICTA](TSICTA.md) | ModeloPixCobranca / [TSIRFE](TSIRFE.md) | NURFEMODBOLETO->NURFE | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContaBancaria / [TSICTA](TSICTA.md) | PlanoContaEmpresa /  | CODCTACTB->CODCTACTB | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFEMODCHEQG->NURFE | I |
| ContaBancaria / [TSICTA](TSICTA.md) | TipoOperacaoBaixa / [TGFTOP](TGFTOP.md) | CODTIPOPERBAIXABOLRAP->CODTIPOPER | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ContaBancaria / [TSICTA](TSICTA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODOPEREXCL->CODUSU | I |
| ContaDestino / [TSICTA](TSICTA.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |
| ContaUsuario / [TSIUCT](TSIUCT.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ControleCaixa / [TGFCAI](TGFCAI.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ConvenioConta / [TGFCOC](TGFCOC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ConvenioContaParceiro / [TGFCPC](TGFCPC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTAPIXTEF->CODCTABCOINT | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ContaBancariaIpi / [TSICTA](TSICTA.md) | CODCTABCOIPI->CODCTABCOINT | I |
| ExtratoBancario / [TGFEXB](TGFEXB.md) | ContaBancaria / [TSICTA](TSICTA.md) | NROCTA->CODCTABCO | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | ContaBancariaDespesa / [TSICTA](TSICTA.md) | CODCTABCOINTDESP->CODCTABCOINT | I |
| FaturamentoCruzado / [TSIFAT](TSIFAT.md) | ContaBancariaReceita / [TSICTA](TSICTA.md) | CODCTABCOINTREC->CODCTABCOINT | I |
| FilaImpressaoBoleta / [TSIFIL](TSIFIL.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| Financeiro / [TGFFIN](TGFFIN.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| Financeiro / [TGFFIN](TGFFIN.md) | TimContaBancaria / [TSICTA](TSICTA.md) | TIMCONTALANC->CODCTABCOINT | I |
| FinanceiroVendaRapida / [TGFFVR](TGFFVR.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| FormatadorRemessaPorConta / [TGFFCT](TGFFCT.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ItemRegras / [TGFITR](TGFITR.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODINSTPRINC->CODCTABCOINT | I |
| ItemRegras / [TGFITR](TGFITR.md) | ContaBancariaDespesa / [TSICTA](TSICTA.md) | CODINSTSECFIN->CODCTABCOINT | I |
| ItemRegras / [TGFITR](TGFITR.md) | ContaBancariaReceita / [TSICTA](TSICTA.md) | CODINSTSECINI->CODCTABCOINT | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOCONTRA->CODCTABCOINT | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | ContaDestino / [TSICTA](TSICTA.md) | CODCTABCOINTDEST->CODCTABCOINT | I |
| MovimentoBancario / [TGFMBC](TGFMBC.md) | ImplantacaoSaldoConta / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| MovimentoBancarioDevCheque / [TGFMBC](TGFMBC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| NaturezaContaBancaria / [TGFNCB](TGFNCB.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| OcorrenciaBancariaRetorno / [TSIOBC](TSIOBC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ParcelaPagamento / [TGFPPG](TGFPPG.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| Projeto / [TCSPRJ](TCSPRJ.md) | ContaBancaria / [TSICTA](TSICTA.md) | TIMNMCTACOMP->CODCTABCOINT | I |
| RegistroCobrancasPix / [TGFMPX](TGFMPX.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | ContaBancaria / [TSICTA](TSICTA.md) | EPRCONTACORRENTE->CODCTABCOINT | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | ContaBancaria / [TSICTA](TSICTA.md) | LOCCONTACORRENTE->CODCTABCOINT | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | ContaBancaria / [TSICTA](TSICTA.md) | LOTCONTACORRENTE->CODCTABCOINT | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | ContaDestino / [TSICTA](TSICTA.md) | LOTCCENTRADA->CODCTABCOINT | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | ContaBancaria / [TSICTA](TSICTA.md) | CNFCODCTABCOINT->CODCTABCOINT | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | ContaBancaria / [TSICTA](TSICTA.md) | TXCCODCTABCOINT->CODCTABCOINT | I |
| Transferencia / [TGMTRA](TGMTRA.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTACTB->CODCTACTB | I |
| TransferenciaCheque / [TGFTRC](TGFTRC.md) | ContaDestino / [TSICTA](TSICTA.md) | CODCTABCOINTDEST->CODCTABCOINT | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ViewRegistroCobrancasPix / [VGFMPX](VGFMPX.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
