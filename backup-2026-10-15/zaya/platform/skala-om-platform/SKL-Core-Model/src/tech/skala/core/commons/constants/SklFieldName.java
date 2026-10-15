package tech.skala.core.commons.constants;

/**
 * A classe utilitaria {@code SklFieldName} contem a lista de nomes de campos mais 
 * utilizados.
 * <p>
 * 
 * Nomes de campos sao largamente utilizados quando ha necessidade de interacao com 
 * o banco de dados, seja para buscar o valor do campo seja para inserir ou alterar 
 * o mesmo.<br>
 * Exemplo simples disto eh a interacao com objetos do tipo {@code DynamicVO} ou 
 * {@code FluidUpdateVO}.<br>
 * Deve-se utilizar a constante ao inves de utilizar diretamente a String com o nome 
 * do campo, principalmente se houver repeti��o dentro do codigo.<br>
 * 
 * <pre>
 * <code>
 *	JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
 *	FluidUpdateVO updVO = dao.prepareToUpdate(vo);
 *	//updVO.set("DTNEG", TimeUtils.clearTime(dtComercial));
 *	updVO.set(SklFieldName.DTNEG, TimeUtils.clearTime(dtComercial));
 *	updVO.update();
 * </code>
 * </pre>
 * 
 * @since 2022-05-05
 * @author cassiomenezes
 * @author hiagoalexandre
 *
 */
public class SklFieldName {

	private SklFieldName() {
		throw new IllegalStateException("Utility class");
	}

	/*
	 * Campos e constantes
	 */
	public static final String AD_ORDEMCARGA					= "AD_ORDEMCARGA";
	public static final String AD_PESOPAD						= "AD_PESOPAD";
	public static final String AD_STATUSCARGA					= "AD_STATUSCARGA";
	public static final String AD_TIPEMBALAGEM					= "AD_TIPEMBALAGEM";
	public static final String ATIVO							= "ATIVO";
	public static final String CGC_CPF							= "CGC_CPF";
	public static final String CHAVECTE							= "CHAVECTE";
	public static final String CHAVENFE							= "CHAVENFE";
	public static final String CLIENTE							= "CLIENTE";
	public static final String CAPACIDADE						= "CAPACIDADE";
	public static final String CODBARRA							= "CODBARRA";
	public static final String CODBARRAPARC						= "CODBARRAPARC";
	public static final String CODCAL 							= "CODCAL";
	public static final String CODCENCUS						= "CODCENCUS";
	public static final String CODCENCUSPAD						= "CODCENCUSPAD";
	public static final String CODEMP							= "CODEMP";
	public static final String CODEXECUTANTE					= "CODEXECUTANTE";
	public static final String CODFAM							= "CODFAM";
	public static final String CODFUNC							= "CODFUNC";
	public static final String CODGPROD							= "CODGPROD";
	public static final String CODGRUPOPROD						= "CODGRUPOPROD";
	public static final String CODLOCAL							= "CODLOCAL";
	public static final String CODLOCALORIG						= "CODLOCALORIG";
	public static final String CODOCO 							= "CODOCO";
	public static final String CODOCOENT 						= "CODOCOENT";
	public static final String CODPARC							= "CODPARC";
	public static final String CODPARCCAUSADOR 					= "CODPARCCAUSADOR";
	public static final String CODPARCMATRIZ					= "CODPARCMATRIZ";
	public static final String CODPARCMOTORISTA 				= "CODPARCMOTORISTA";
	public static final String CODPARCTRANSP					= "CODPARCTRANSP";
	public static final String CODPLP							= "CODPLP";
	public static final String CODPROD							= "CODPROD";
	public static final String CODPRODPA						= "CODPRODPA";
	public static final String CODPROPARC						= "CODPROPARC";
	public static final String CODREG							= "CODREG";
	public static final String CODTIPOPER						= "CODTIPOPER";
	public static final String CODUSU							= "CODUSU";
	public static final String CODUSUATD						= "CODUSUATD";
	public static final String CODVEND							= "CODVEND";
	public static final String CODVEICULO						= "CODVEICULO";
	public static final String CODVOL							= "CODVOL";
	public static final String CODVOLCONF						= "CODVOLCONF";
	public static final String CODVOLPAD						= "CODVOLPAD";
	public static final String CODWCP							= "CODWCP";
	public static final String COMPLDESC						= "COMPLDESC";
	public static final String CONCLUIDO						= "CONCLUIDO";
	public static final String CONTROLE							= "CONTROLE";
	public static final String CONTROLECONF						= "CONTROLECONF";
	public static final String CONTROLEPA						= "CONTROLEPA";
	public static final String CPF								= "CPF";
	public static final String DESCRFAM							= "DESCRFAM";
	public static final String DESCRICAO						= "DESCRICAO";
	public static final String DESCRPROD						= "DESCRPROD";
	public static final String DESCRPRODPA						= "DESCRPRODPA";
	public static final String DHALTER							= "DHALTER";
	public static final String DHENVTRANSP 						= "DHENVTRANSP";
	public static final String DHINI							= "DHINI";
	public static final String DHINICIO							= "DHINICIO"; 
	public static final String DHINICIOCONF						= "DHINICIOCONF";
	public static final String DHFINAL							= "DHFINAL";
	public static final String DHFINALCONF						= "DHFINALCONF";
	public static final String DHOCORRENCIA 					= "DHOCORRENCIA";
	public static final String DHTERMINO						= "DHTERMINO";
	public static final String DTADM							= "DTADM";
	public static final String DTINIC 							= "DTINIC";
	public static final String DTINICIO							= "DTINICIO";
	public static final String DTIVT							= "DTIVT";
	public static final String DTFABRICACAO						= "DTFABRICACAO";
	public static final String DTFABRICACAOCONF					= "DTFABRICACAOCONF";
	public static final String DTFATUR							= "DTFATUR";
	public static final String DTNEG							= "DTNEG";
	public static final String DTPREVSAIDA						= "DTPREVSAIDA";
	public static final String DTPROG							= "DTPROG";
	public static final String DTRECEBIMENTO					= "DTRECEBIMENTO";
	public static final String DTREF							= "DTREF";
	public static final String DTVALCONF						= "DTVALCONF";
	public static final String DTVAL							= "DTVAL";
	public static final String ENTSAI							= "ENTSAI";
	public static final String ESTOQUE							= "ESTOQUE";
	public static final String ESTOQUEDISP						= "ESTOQUEDISP";
	public static final String ESTOQUE_INI						= "ESTOQUE_INI";
	public static final String ESTOQUE_FINAL					= "ESTOQUE_FINAL";
	public static final String FATOR							= "FATOR";
	public static final String IDENTIDADE						= "IDENTIDADE";
	public static final String IDIPROC							= "IDIPROC";
	public static final String IDIVT							= "IDIVT";
	public static final String IDPROC							= "IDPROC";
	public static final String LINHA							= "LINHA";
	public static final String MARCA							= "MARCA";
	public static final String MATRICULA						= "MATRICULA";
	public static final String NOME								= "NOME";
	public static final String NOMEFUNC							= "NOMEFUNC";
	public static final String NOMEPARC							= "NOMEPARC";
	public static final String NROLOTE							= "NROLOTE";
	public static final String NUAPO							= "NUAPO";
	public static final String NUMNOTA							= "NUMNOTA";
	public static final String NUNOTA							= "NUNOTA";
	public static final String NUNOTAENTRADA					= "NUNOTAENTRADA";
	public static final String NUNOTAORIG						= "NUNOTAORIG";
	public static final String NUCONFERENCIA					= "NUCONFERENCIA";
	public static final String NUREQ 							= "NUREQ";
	public static final String NURECEBIMENTO					= "NURECEBIMENTO";
	public static final String NUTAREFA							= "NUTAREFA";
	public static final String OBSERVACAO						= "OBSERVACAO";
	public static final String ORDEM							= "ORDEM";
	public static final String ORDEMCARGA						= "ORDEMCARGA";
	public static final String PENDENTE							= "PENDENTE";
	public static final String PERCEXPED						= "PERCEXPED";
	public static final String PERCFAT							= "PERCFAT";
	public static final String PESO								= "PESO";
	public static final String PESOPAD							= "PESOPAD";
	public static final String PRIORIDADE						= "PRIORIDADE";
	public static final String PROGRAMADO						= "PROGRAMADO";
	public static final String QUANTIDADE						= "QUANTIDADE";
	public static final String QTDAPONTADA						= "QTDAPONTADA";
	public static final String QTDAVARIA						= "QTDAVARIA";
	public static final String QTDATENDIDA						= "QTDATENDIDA";
	public static final String QTDCAPACIDADEPAD					= "QTDCAPACIDADEPAD";
	public static final String QTDDEM							= "QTDDEM";
	public static final String QTDDEMPAD						= "QTDDEMPAD";
	public static final String QTDDEMSLD						= "QTDDEMSLD";
	public static final String QTDEXPED							= "QTDEXPED";
	public static final String QTDFAT							= "QTDFAT";
	public static final String QTDECONF							= "QTDECONF";
	public static final String QTDCONTAGEM						= "QTDCONTAGEM";
	public static final String QTDNEG							= "QTDNEG";
	public static final String QTDPERDA							= "QTDPERDA";
	public static final String QTDPRODUZIR						= "QTDPRODUZIR";
	public static final String QTDMEDIA							= "QTDMEDIA";
	public static final String QTDPEDIDOS						= "QTDPEDIDOS";
	public static final String QTDPENDATU						= "QTDPENDATU";
	public static final String QTDPENDENTE						= "QTDPENDENTE";
	public static final String QTDPENDENTEFAM					= "QTDPENDENTEFAM";
	public static final String QTDPREV							= "QTDPREV";
	public static final String QTDPREVPROX						= "QTDPREVPROX";
	public static final String QTDRESERVADA						= "QTDRESERVADA";
	public static final String QTDRESERVADACX					= "QTDRESERVADACX";
	public static final String QTDSOLICITADO					= "QTDSOLICITADO";
	public static final String QTDUNIDPAD						= "QTDUNIDPAD";
	public static final String QTDVOL 							= "QTDVOL";
	public static final String QTDUNIT							= "QTDUNIT";
	public static final String QTDVOLUME						= "QTDVOLUME";
	public static final String QTDUNITVOL						= "QTDUNITVOL";
	public static final String RAZAOSOCIAL						= "RAZAOSOCIAL";
	public static final String RECONTAGEM						= "RECONTAGEM";
	public static final String REFERENCIA						= "REFERENCIA";
	public static final String RESERVADO						= "RESERVADO";
	public static final String ROTEIRO							= "ROTEIRO";
	public static final String SEMANA							= "SEMANA";
	public static final String SEQAPA							= "SEQAPA";
	public static final String SEQUENCIAORIG					= "SEQUENCIAORIG";
	public static final String SEQUENCIA						= "SEQUENCIA";
	public static final String SEQUENCIAATD						= "SEQUENCIAATD";
	public static final String SITUACAO							= "SITUACAO";
	public static final String STATUS							= "STATUS";
	public static final String STATUSNOTA						= "STATUSNOTA";
	public static final String STATUSPROC						= "STATUSPROC";
	public static final String TAMLOTEPAD						= "TAMLOTEPAD";
	public static final String TEMREAJUSTE						= "TEMREAJUSTE";
	public static final String TIPCARGA							= "TIPCARGA";
	public static final String TIPCONF							= "TIPCONF";
	public static final String TIPCONTEST						= "TIPCONTEST";
	public static final String TIPMOV							= "TIPMOV";
	public static final String TIPO								= "TIPO";
	public static final String TIPOTRANSF						= "TIPOTRANSF";
	public static final String USALOCAL							= "USALOCAL";
	public static final String USALOTE							= "USALOTE";
	public static final String USOPROD							= "USOPROD";
	public static final String VLRDESC							= "VLRDESC";
	public static final String VLRUNIT							= "VLRUNIT";
	public static final String VLRLIQ							= "VLRLIQ";
	public static final String VLRPENDATU						= "VLRPENDATU";
	public static final String VLRPENDENTE						= "VLRPENDENTE";
	public static final String VLRTOT							= "VLRTOT";
	public static final String WMSBLOQUEADO						= "WMSBLOQUEADO";
	public static final String HORASAIDA						= "HORASAIDA";	
	public static final String TIPPESSOA 						= "TIPPESSOA";
	public static final String CODEND 							= "CODEND";
	public static final String NUMEND 							= "NUMEND";
	public static final String COMPLEMENTO 						= "COMPLEMENTO";
	public static final String CODBAI 							= "CODBAI";
	public static final String CODCID 							= "CODCID";
	public static final String CEP 								=  "CEP";
	public static final String TELEFONE 						= "TELEFONE";
	public static final String FAX 								=  "FAX";
	public static final String EMAIL 					 		= "EMAIL";
	public static final String EMAILNFE 					 	= "EMAILNFE";
	public static final String DTNASC 					 		= "DTNASC";
	public static final String CLASSIFICMS 					 	= "CLASSIFICMS";
	public static final String GRUPOAUTOR 					 	= "GRUPOAUTOR";
	public static final String CODEMPPREF 					 	= "CODEMPPREF";
	public static final String CODTAB 					 		= "CODTAB";
	public static final String CODTIPPARC 					 	= "CODTIPPARC";
}
