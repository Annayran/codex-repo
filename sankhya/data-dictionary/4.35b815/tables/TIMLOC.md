# TIMLOC — Locações

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 78
- Instâncias DWF: 1
- Relações DWF: 39
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLocacao | Contrato de Locação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LOCAPRESENTACAO | Descr. Contrato | S |  |  | S |
| LOCATENDENTE | Atendente | I |  |  | N |
| LOCATIVIDADE | Atividade | S |  |  | N |
| LOCAVENCER | Pagamento Antecipado | S |  |  | N |
| LOCCARCORMONE | Carência p/ correção monetária | I |  |  | N |
| LOCCARENCIA | Dias de Carência p/ contrato | I |  |  | N |
| LOCCODIGO | Número | I |  |  | N |
| LOCCODMOEDACDU | Índice Corr. CDU | I |  |  | N |
| LOCCODUSUINC | Usuário Inclusão | I |  |  | N |
| LOCCONSDESCSIMPIRPF | Considerar desconto simplificado para cálculo do IRPF? | S |  |  | N |
| LOCCONTACORRENTE | Conta Corrente | I |  |  | N |
| LOCCONTRATOADM | Nro. Contrato Adm. | I |  |  | N |
| LOCCORRETOR | Ger. de Carteira | I |  |  | N |
| LOCCRD | CRD | F |  |  | S |
| LOCCRDAREA | Área | F |  |  | N |
| LOCCRDATIVIDADE | Atividade | F |  |  | N |
| LOCCRDFORMATO | Formato | F |  |  | N |
| LOCCRDLOCAL | Localização | F |  |  | N |
| LOCCRDVITRINE | Tamanho Vitrine | F |  |  | N |
| LOCDATABASE | Dt. Próximo Reajuste | D |  |  | N |
| LOCDATAENCERRAMENTO | Dt. Encerr. Contrato | D |  |  | N |
| LOCDATAINICIO | Dt. início última neg. | D |  |  | S |
| LOCDATALOCACAO | Dt. início primeira neg. | D |  |  | S |
| LOCDATATERMINO | Dt. fim última neg. | D |  |  | S |
| LOCDESCPONTUAL | % Desconto Pontualidade | F |  |  | N |
| LOCDESTINACAO | Destinação | S |  |  | N |
| LOCDHALTER | Dh. Alteração | H |  |  | N |
| LOCDHINC | Dh. Inclusão | H |  |  | N |
| LOCDIADEPAGAMENTO | Dia de Vcto. | I |  |  | S |
| LOCDIAS | Qtd. dias até o repasse | I |  |  | N |
| LOCDIAVENCAP | Dia Venc. AP | I |  |  | N |
| LOCDIAVENCCC | Dia Venc. Condomínio Comum | I |  |  | N |
| LOCDIAVENCCE | Dia Venc. Condomínio Específico | I |  |  | N |
| LOCDIAVENCFPP | Dia Venc. FPP | I |  |  | N |
| LOCDTINICPRIREAJUSTE | Dt. Início. Primeiro Reajuste | D |  |  | N |
| LOCDTREAJUSTECDU | Dt. Prox. Reajuste CDU | D |  |  | N |
| LOCDTVENCINICDU | Dt. Ini. Venc. CDU | D |  |  | N |
| LOCENVIAJUR | Envia Jurídico Fech. aberto | S |  |  | N |
| LOCESTAGIO | Estágio | S |  |  | N |
| LOCGARANTIA | Tipo Garantia | S |  |  | N |
| LOCGARANTIDO | Garantido | S |  |  | N |
| LOCGARANTIDOPOR | Garantido por (meses) | I |  |  | N |
| LOCGARJUSTIFICATIVA | Lembrete de Problema | S |  |  | N |
| LOCGEROUCDU | CDU Gerada | S |  |  | N |
| LOCIMOBILIARIA | Imobiliária | I |  |  | N |
| LOCIMOVEL | Nro. Imóvel | I |  |  | N |
| LOCINDEXADOR | Índice de Correção | I |  |  | N |
| LOCINDICETXADM | Índice para Tx Adm | I |  |  | N |
| LOCMESESISENTA | Meses Para Isenção | I |  |  | N |
| LOCMESESMULTA | Meses de Multa | I |  |  | N |
| LOCMOTIVOBLOQUEIO | Motivo Bloqueio | S |  |  | N |
| LOCNUCHECKLIST | CheckList do contrato | I |  |  | N |
| LOCPAPERCENTUAL | Tx. Interm. (%) | F |  |  | N |
| LOCPAPRIPARC | Cobrar Tx. Interm. a partir da parcela | I |  |  | N |
| LOCPAQTDPARC | Cobrar Tx. Interm. até a parcela: | I |  |  | N |
| LOCPARCINIADM | Cobrar Tx. Adm. a partir da parcela | I |  |  | N |
| LOCPASTA | Pasta Física | I |  |  | N |
| LOCPERCENTUALADM | Tx. Adm. (%) | F |  |  | N |
| LOCPERCFPP | % FPP | F |  |  | N |
| LOCPRAZODELOCACAO | Qtd. meses de locação | I |  |  | N |
| LOCPRIMEIRO | Cobrar Tx. Interm. | S |  |  | N |
| LOCQTDEGARANTIDA | Garantia Total | S |  |  | N |
| LOCQTDPARCCDU | Qtd. Parc. CDU | I |  |  | N |
| LOCREAJUSTE | Periodicidade Reajuste | S |  |  | N |
| LOCRENEGOCIACAO | Renegociação Ativa | I |  |  | N |
| LOCSEMBOLETO | Bloquear Boleto | S |  |  | N |
| LOCTIPOFPP | Tipo FPP | S |  |  | N |
| LOCTIPOIRRF | Tipo do IRRF | S |  |  | N |
| LOCTIPOTXADM | Tipo de Tx Adm | S |  |  | N |
| LOCTRANSFERENCIA | É Transferência | S |  |  | N |
| LOCTXADMVLR | Tx Adm (Valor) | F |  |  | N |
| LOCULTIMAAPRES | Dt. última apres. de contas | D |  |  | N |
| LOCULTIMOREAJUSTE | Dt. Último Reajuste | D |  |  | N |
| LOCUSUARIO | Usuário Alteração | I |  |  | N |
| LOCVALORALUGUEL | Vlr. Aluguel Negociado | F |  |  | S |
| LOCVALORMERCADO | Vlr. Aluguel | F |  |  | N |
| LOCVLRCDU | Vlr. Parcela CDU | F |  |  | N |
| LOCVLRFPP | Vlr. FPP | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimLocacao / [TIMLOC](TIMLOC.md) | TIMCONTRATOLOC->LOCCODIGO | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimLocacao / [TIMLOC](TIMLOC.md) | SACLOCACAO->LOCCODIGO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimLocacao / [TIMLOC](TIMLOC.md) | CHKCONTRATOLOC->LOCCODIGO | I |
| TimDetalhamento / [TIMDTL](TIMDTL.md) | TimLocacao / [TIMLOC](TIMLOC.md) | DTLCONTRATOLOC->LOCCODIGO | I |
| TimFechamentoAluItens / [TIMFAI](TIMFAI.md) | TimLocacao / [TIMLOC](TIMLOC.md) | FAICONTRATOLOC->LOCCODIGO | I |
| TimFechamentoItens / [TIMFEI](TIMFEI.md) | TimLocacao / [TIMLOC](TIMLOC.md) | FEICONTRATOLOC->LOCCODIGO | I |
| TimFinanceiroAluguel / [TGFFIN](TGFFIN.md) | TimLocacao / [TIMLOC](TIMLOC.md) | TIMCONTRATOLOC->LOCCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | ContaBancaria / [TSICTA](TSICTA.md) | LOCCONTACORRENTE->CODCTABCOINT | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | Moeda / [TSIMOE](TSIMOE.md) | LOCINDEXADOR->CODMOEDA | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | Moeda2 / [TSIMOE](TSIMOE.md) | LOCCODMOEDACDU->CODMOEDA | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimCheckList / [TIMCHK](TIMCHK.md) | LOCNUCHECKLIST->CHKNUCHECK | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimContratoAdm / [TIMADM](TIMADM.md) | LOCCONTRATOADM->ADMNUCONTRATO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimCorretor / [TIMCOR](TIMCOR.md) | LOCCORRETOR->CORCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimCorretorGerente / [TIMCOR](TIMCOR.md) | LOCATENDENTE->CORCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimFormaPagCond / [TIMFPC](TIMFPC.md) | LOCCODIGO->FPCCONTRATOLOC | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | LOCIMOBILIARIA->IMBCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimImovel / [TIMIMV](TIMIMV.md) | LOCIMOVEL->IMVCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoFiador / [TIMLCF](TIMLCF.md) | LOCCODIGO->LCFLOCACAO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoGarantiaCaucao / [TIMLGP](TIMLGP.md) | LOCCODIGO->LGPCONTRATO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoGarantiaSeguro / [TIMLGS](TIMLGS.md) | LOCCODIGO->LGSCONTRATO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | LOCCODIGO->LTCCONTRATO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoImovelCaucao / [TIMLIC](TIMLIC.md) | LOCCODIGO->LICLOCACAO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoSeguroIncendio / [TIMSGI](TIMSGI.md) | LOCCODIGO->SGICONTRATOLOC | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocatario / [TIMLCL](TIMLCL.md) | LOCCODIGO->LCLLOCACAO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimMoeda / [TSIMOE](TSIMOE.md) | LOCINDICETXADM->CODMOEDA | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimNegociacao / [TIMLCR](TIMLCR.md) | LOCCODIGO->LCRCONTRATO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimProprietario / [TIMIMP](TIMIMP.md) | LOCCONTRATOADM->IMPCONTRATO; LOCIMOVEL->IMPIMOVEL | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | LOCUSUARIO->CODUSU | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | LOCCODUSUINC->CODUSU | I |
| TimLocacaoGarantiaCaucao / [TIMLGP](TIMLGP.md) | TimLocacao / [TIMLOC](TIMLOC.md) | LGPCONTRATO->LOCCODIGO | I |
| TimLocacaoGarantiaSeguro / [TIMLGS](TIMLGS.md) | TimLocacao / [TIMLOC](TIMLOC.md) | LGSCONTRATO->LOCCODIGO | I |
| TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | TimLocacao / [TIMLOC](TIMLOC.md) | LTCCONTRATO->LOCCODIGO | I |
| TimLocacaoSeguroIncendio / [TIMSGI](TIMSGI.md) | TimLocacao / [TIMLOC](TIMLOC.md) | SGICONTRATOLOC->LOCCODIGO | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimLocacao / [TIMLOC](TIMLOC.md) | MFDCONTRATOLOC->LOCCODIGO | I |
| TimNegociacao / [TIMLCR](TIMLCR.md) | TimLocacao / [TIMLOC](TIMLOC.md) | LCRCONTRATO->LOCCODIGO | I |
| TimReajusteLocContrato / [TIMRLC](TIMRLC.md) | TimLocacao / [TIMLOC](TIMLOC.md) | RLCCONTRATO->LOCCODIGO | I |
| TimRenovacaoContrato / [TIMCTR](TIMCTR.md) | TimLocacao / [TIMLOC](TIMLOC.md) | CTRCONTRATO->LOCCODIGO | I |
| TimRescisaoLoc / [TIMREC](TIMREC.md) | TimLocacao / [TIMLOC](TIMLOC.md) | RECCONTRATOLOC->LOCCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimLocacao / [TIMLOC](TIMLOC.md) | VDOCONTRATOLOC->LOCCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
