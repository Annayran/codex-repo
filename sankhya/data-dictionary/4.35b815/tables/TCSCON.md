# TCSCON — Contratos

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 162
- Instâncias DWF: 3
- Relações DWF: 85
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContradosOrigem | Contrato de Origem | S | N |
| Contrato | Contrato | S | S |
| ContratoArmazenagemGeral | Contratos de Armazenagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ACESSAHISTSUBOS | Visualizar histórico na consulta Web | S |  |  | N |
| AMBIENTE | Ambiente | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CIF_FOB | CIF / FOB | S |  |  | N |
| CLAUSCONTRATO | Cláusulas do Contrato | S |  |  | N |
| COBPROPORCAR | Tipo de Cobrança | S |  |  | N |
| COBPROQUE | Cobrar Proporcional | S |  |  | N |
| CODBENEFFIS | Número de identificação do Benefício Municipal | S |  |  | N |
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODCENCUSAR | Centro Resultado | I |  |  | N |
| CODCENCUSEX | Centro Resultado | I |  |  | N |
| CODCID | Cidade de Execução do Serviço | I |  |  | N |
| CODCLC | Característica Analisável | I |  |  | N |
| CODCONTATO | Contato | I |  |  | N |
| CODCRITERIO | Critério de Rateio | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODIMPLANT | Responsável Técnico da Contratante | I |  |  | N |
| CODMOEALTREAJ | Moeda alternativa para reajuste | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODMONSANKHYA | Gerente do Contrato | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODNATAR | Natureza | I |  |  | N |
| CODNATEX | Natureza | I |  |  | N |
| CODNATOPERISS | Cód. Natureza Oper. ISS (NFS-e) | S |  |  | N |
| CODOBS | Observação padrão | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPARCPREST | Parceiro prest. serviço | I |  |  | N |
| CODPARCSEC | Parceiro atendido | I |  |  | N |
| CODPROD | Produto | I |  |  | S |
| CODPROJ | Projeto | I |  |  | N |
| CODPROJSINT | Projeto sintético | I |  |  | N |
| CODSAF | Safra | I |  |  | N |
| CODSERVEX | Serviço | I |  |  | N |
| CODTIPVENDA | Tipo negociação | I |  |  | N |
| CODTIPVENDAAR | Tipo negociação | I |  |  | N |
| CODTIPVENDAEX | Tipo negociação | I |  |  | N |
| CODUSU | Usuário Inclusão | I |  |  | N |
| CODUSUALTER | Usuário Última Alteração | I |  |  | N |
| COLORFATFORASEQ | Colorir Faturamento Fora da Sequencia | S |  |  | S |
| CONTCOMPLETO | Instalações até o Período | I |  |  | S |
| CONTDESINSTAL | Desinstalações no Período | I |  |  | S |
| CONTINSTAL | Instalações no Período | I |  |  | S |
| CONTROLOCBENS | Contrato de locação de bens | S |  |  | N |
| CONTRORGPUBLICO | Contrato de órgão público | S |  |  | N |
| DEFTIPA | Tipo de Armazenador | S |  |  | N |
| DIACARECAR | Períodos de Carência | I |  |  | N |
| DIACARENC | Prazo de Carência | I |  |  | N |
| DIACARENCEX | Tipo de Apuração por Percentual | I |  |  | N |
| DIAFIMMED | Dia final p/ medição | I |  |  | N |
| DIAFIXO | Dia Fixo para vencimento | S |  |  | N |
| DIAPAG | Dia do pagamento | I |  |  | N |
| DIAUTIL | Considera dia do pagamento como dia útil | S |  |  | N |
| DTBASEREAJ | Data base reajuste | D |  |  | N |
| DTCONTRATO | Data do contrato | D |  |  | N |
| DTENVIOEMAIL | Data envio email | D |  |  | N |
| DTFINENTREGA | Data Final da Entrega | D |  |  | N |
| DTINIENTREGA | Data Início da Entrega | D |  |  | N |
| DTREFARMAZE | Data Referência Faturamento | D |  |  | N |
| DTREFEXPREC | Data Referência Faturamento | D |  |  | N |
| DTREFPROXFAT | Referência próximo faturamento | D |  |  | N |
| DTREFULTFAT | Referência último faturamento | D |  |  | S |
| DTTERMINO | Data de término | D |  |  | N |
| DTVENCTO | Data Vencimento | D |  |  | S |
| DURACAO | Duração da visita (horas e minutos) | T |  |  | N |
| ENDERECO | Endereço | S |  |  | S |
| EQUIPAMENTO | Equipamento | S |  |  | N |
| EXIGEPEDIDOPES | Exige Pedido na Pesagem | S |  |  | N |
| FATURPRORATA | Faturamento pro rata? | S |  |  | N |
| FERIADOEST | Feriado estadual | S |  |  | N |
| FERIADOMUN | Feriado municipal | S |  |  | N |
| FERIADONAC | Feriado nacional | S |  |  | N |
| FORMFATARMAZE | Apurar por peso líquido | S |  |  | N |
| FORMFATEXPREC | Apurar por peso líquido | S |  |  | N |
| FREQREAJ | Frequência reajuste | I |  |  | N |
| FREQVISITAS | Intervalo entre visitas | I |  |  | N |
| GATILHO | Gatilho | I |  |  | N |
| GERARFINNOTA | Gerar Financeiro da nota como | S |  |  | N |
| GERARNF | Gerar NF | S |  |  | N |
| GRUPO | Grupo | S |  |  | N |
| GRUPOFATURPRORATA | Grupo de Faturamento | S |  |  | N |
| IMPRIME | Imprime | S |  |  | N |
| IMPRPRECINDIV | Imprime preço individualizado | S |  |  | N |
| INSCEST | Inscrição Estadual | S |  |  | S |
| LOCACAOBEM | Contrato de locação de bens | S |  |  | N |
| MODALIDADE | Modalidade do Contrato | S |  |  | N |
| NOMEPARC | Nome Parceiro | S |  |  | S |
| NUMCONTIN | Contrato Interno | S |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| NUMCONTRATOORIGEM | Aditivo de | I |  |  | N |
| NUMCSTC | Característica de Serviço de Tele/Comunicação | I |  |  | N |
| NUNOTA | Pedido de Captação | I |  |  | N |
| NUNOTAPEDLOC | Número Pedido | I |  |  | N |
| NUNOTAREFARMAZE | % Ultima nota Armazenagem | I |  |  | N |
| NUNOTAREFEXPREC | % Ultima nota Expedição | I |  |  | N |
| NUSLA | SLA | I |  |  | N |
| OBSERVACOES | Observações | S |  |  | N |
| PADCLASS | Padrão de Classificação | I |  |  | N |
| PARCELAATUAL | Parcela atual | I |  |  | N |
| PARCELAFAT | Parcela Fat. | I |  |  | S |
| PARCELAQTD | Qtd. parcelas | I |  |  | N |
| PERCDEDUCAO | Perc. de dedução na base do ISS | F |  |  | N |
| PERCIRF | % IRF | F |  |  | N |
| PERCISS | % ISS | F |  |  | N |
| PERCLOC | Perc. Locação | F |  |  | N |
| PERCOBRA | Periodicidade de Cobrança | S |  |  | N |
| PERCOBRAAR | Periodicidade de Cobrança | S |  |  | N |
| PERCTOLEXCED | % Tolerância Excedente | F |  |  | N |
| PERDESC | Periodicidade de Desconto | S |  |  | N |
| PERDESCON | Periodicidade de Desconto | S |  |  | N |
| PPAUTASC | Preço de Pauta/SC | I |  |  | N |
| PRAZOMENSAL | Prazo Mensal para Pagamento | I |  |  | N |
| PRAZOVENCTO | Prazo de vencimento | I |  |  | N |
| QTDEPREVISTA | Qtd. Prevista | F |  |  | S |
| QTDFINPEND | Qtd. Fin. Pendentes | I |  |  | S |
| QTDGERARPREV | Qtd. Provisões a Gerar | I |  |  | S |
| QTDNEG | Quantidade Negociada | F |  |  | N |
| QTDPARCPGCOM | Qtd parcelas p/ pagto de comissão | I |  |  | N |
| QTDPREV | Qtd. Provisões | I |  |  | S |
| QTDPROVISAO | Qtd. Provisão | I |  |  | N |
| QUEBRATEC | % Quebra Técnica | F |  |  | N |
| REAJUSTENEGATIVO | Aceita reajuste com índice negativo | S |  |  | N |
| RECDESP | Tipo de Financeiro | I |  |  | N |
| REFERENCIA | Referência | S |  |  | S |
| REGLAUDSAIDA | Regra p/ Laudo nas Saídas | S |  |  | N |
| RESPPAGAR | Responsável pelo Pagamento | I |  |  | N |
| RETEMISS | Retém ISS | S |  |  | N |
| SERFATURCON | Série para faturamento em contratos | S |  |  | N |
| SITCONT | Situação do contrato | S |  |  | N |
| TABPRECUMI | Tabela de Preços por Umidade | I |  |  | N |
| TABPRECUMIAR | Tabela de Preços por Umidade | I |  |  | N |
| TEMCRIRATESP | Possui critério de rateio específico | S |  |  | S |
| TEMIRF | Calcular IRF | S |  |  | N |
| TEMISS | Calcular ISS | S |  |  | N |
| TEMMED | Tem medição | S |  |  | N |
| TIPCOBR | Tipo de Cobrança | S |  |  | N |
| TIPCON | Tipo de Contrato | S |  |  | N |
| TIPISENCAO | Tipo de Isenção | S |  |  | N |
| TIPO | Periodicidade do Faturamento | S |  |  | N |
| TIPOARM | TIPOARM | S |  |  | N |
| TIPOCONTRATO | Tipo contrato | S |  |  | N |
| TIPODEDUCAO | Tipo de dedução de base do ISS | S |  |  | N |
| TIPOTITULO | Tipo de título | I |  |  | N |
| TIPOTITULOAR | Tipo de título | I |  |  | N |
| TIPOTITULOEX | Tipo de título | I |  |  | N |
| TIPPAG | Tipo de Pagamento | S |  |  | N |
| TIPQUEBRA | Tipo de Quebra | S |  |  | N |
| TOPFATURCON | TOP para faturamento em contratos | I |  |  | N |
| TOTALCONTRATO | Total do contrato | I |  |  | S |
| TOTALFATURADO | Total faturado | I |  |  | S |
| TOTALPROVPENDENTE | Total prov. pendente | I |  |  | S |
| TOTNEG | Valor Total Negociado | F |  |  | S |
| ULTTABUMI | Utilizar tabela de preços por umidade? | S |  |  | N |
| UMIDPADRA | % Umidade Padrão Quebra de Massa | F |  |  | N |
| UNICONVSC | Unidade de Conversão p/ SC | F |  |  | N |
| VALNEGSC | Valor Negociado/SC | F |  |  | N |
| VALOR | Valor | F |  |  | S |
| VALPEDFIN | Valida pendência financeira x estoque disponível | S |  |  | N |
| VALQUEBTRANS | % Quebra Transbordo | F |  |  | N |
| VLRFINPEND | Valor Pend. Fin. | F |  |  | S |
| VLRORIGINAL | Valor original | F |  |  | S |
| VLRSERVICOS | Valor dos serviços | F |  |  | S |
| VLRTOTAL | Valor Total | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoArmazenagemTrans / [TGATRA](TGATRA.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CODDEST->NUMCONTRATO | I |
| BloqDesbloqEstoque / [TGABD](TGABD.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ComercialGrupo / [TCSCGR](TCSCGR.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTAPEDLOC->NUNOTA | I |
| Contrato / [TCSCON](TCSCON.md) | CabecalhoSLA / [TCSSLA](TCSSLA.md) | NUSLA->NUSLA | I |
| Contrato / [TCSCON](TCSCON.md) | CaracteristicasSvTeleCom / [TCSCSTC](TCSCSTC.md) | NUMCSTC->NUMCSTC | I |
| Contrato / [TCSCON](TCSCON.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| Contrato / [TCSCON](TCSCON.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Contrato / [TCSCON](TCSCON.md) | Comissao / [TCSCOM](TCSCOM.md) | NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | CriterioRateio / [TGFCRI](TGFCRI.md) | CODCRITERIO->CODCRITERIO | I |
| Contrato / [TCSCON](TCSCON.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Contrato / [TCSCON](TCSCON.md) | EmpresaLicenciada / [TCSELC](TCSELC.md) | NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | GrupoEconomicoContrato / [TCSGEC](TCSGEC.md) | NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | GrupoInstalacao / [TCSCGR](TCSCGR.md) | NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | Implantador / [TGFCTT](TGFCTT.md) | CODIMPLANT->CODCONTATO; CODPARC->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Contrato / [TCSCON](TCSCON.md) | Moeda2 / [TSIMOE](TSIMOE.md) | CODMOEALTREAJ->CODMOEDA | I |
| Contrato / [TCSCON](TCSCON.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Contrato / [TCSCON](TCSCON.md) | NaturezaOperacao / [TGFNTP](TGFNTP.md) | CODNATOPERISS->CODNATOPERISS | I |
| Contrato / [TCSCON](TCSCON.md) | NumeroTerminaisTelefonicos / [TCSNTE](TCSNTE.md) | NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | ObservacaoNotasFiscais / [TGFOBS](TGFOBS.md) | CODOBS->CODOBSPADRAO | I |
| Contrato / [TCSCON](TCSCON.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | ParceiroAtendido / [TGFPAR](TGFPAR.md) | CODPARCSEC->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | ParceiroPrestador / [TGFPAR](TGFPAR.md) | CODPARCPREST->CODPARC | I |
| Contrato / [TCSCON](TCSCON.md) | ParcelaAdicional / [TCSPAD](TCSPAD.md) | NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| Contrato / [TCSCON](TCSCON.md) | Projeto2 / [TCSPRJ](TCSPRJ.md) | CODPROJSINT->CODPROJ | I |
| Contrato / [TCSCON](TCSCON.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| Contrato / [TCSCON](TCSCON.md) | TipoOperacaoGrupoFaturamento / [TGFTOP](TGFTOP.md) | TOPFATURCON->CODTIPOPER | I |
| Contrato / [TCSCON](TCSCON.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | TIPOTITULO->CODTIPTIT | I |
| Contrato / [TCSCON](TCSCON.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Contrato / [TCSCON](TCSCON.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODMONSANKHYA->CODUSU | I |
| Contrato / [TCSCON](TCSCON.md) | Usuario3 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CaracteristicaAnalisavel / [TGACLC](TGACLC.md) | CODCLC->CODCLC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CentroResultadoArm / [TSICUS](TSICUS.md) | CODCENCUSAR->CODCENCUS | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | CentroResultadoExp / [TSICUS](TSICUS.md) | CODCENCUSEX->CODCENCUS | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Moeda2 / [TSIMOE](TSIMOE.md) | PPAUTASC->CODMOEDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NaturezaArm / [TGFNAT](TGFNAT.md) | CODNATAR->CODNAT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NaturezaExp / [TGFNAT](TGFNAT.md) | CODNATEX->CODNAT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | PADCLASS->CODCLT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | PrecoUmidade / [TGATDC](TGATDC.md) | TABPRECUMI->CODTDC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | PrecoUmidadeArm / [TGATDC](TGATDC.md) | TABPRECUMIAR->CODTDC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | ProdutoServicoContrato2 / [TCSPSC](TCSPSC.md) | NUMCONTRATO->NUMCONTRATO | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | ResponsavelPagador / [TGFPAR](TGFPAR.md) | RESPPAGAR->CODPARC | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Safra / [TGASAF](TGASAF.md) | CODSAF->CODSAF | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERVEX->CODPROD | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoNegociacaoArm / [TGFTPV](TGFTPV.md) | CODTIPVENDAAR->CODTIPVENDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoNegociacaoExp / [TGFTPV](TGFTPV.md) | CODTIPVENDAEX->CODTIPVENDA | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | TIPOTITULO->CODTIPTIT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoTituloArm / [TGFTIT](TGFTIT.md) | TIPOTITULOAR->CODTIPTIT | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | TipoTituloExp / [TGFTIT](TGFTIT.md) | TIPOTITULOEX->CODTIPTIT | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ContratoVeiculo / [TCSCVE](TCSCVE.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| DetalheFaturamentoContrato / [TGFDFT](TGFDFT.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| EventoAgendado / [TCSEAG](TCSEAG.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| Financeiro / [TGFFIN](TGFFIN.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| GrupoEconomicoContrato / [TCSGEC](TCSGEC.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| Imobilizado / [TCIBEM](TCIBEM.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ItemParcelaAdicional / [TCSPIT](TCSPIT.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| MovimentoRM / [TAPMRM](TAPMRM.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| OrdemServico / [TCSOSE](TCSOSE.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ParcelaAdicional / [TCSPAD](TCSPAD.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato2 / [TCSPSC](TCSPSC.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| RastreabilidadeContratoAgrupa / [TGFRCA](TGFRCA.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| RateioRecDesp / [TGFRAT](TGFRAT.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| RateioVeiculo / [TGFRAV](TGFRAV.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ResumoFaturamento / [TAPFAT](TAPFAT.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ViewFinanceiro / [VGFFIN](VGFFIN.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ViewFinanceiroEDI / [VGFFREM](VGFFREM.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
