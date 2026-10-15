package tech.skala.core.commons.constants;

/**
 * A classe utilitaria {@code SklEntityName} contem a lista de nomes de entidade mais 
 * utilizados, a exemplo da <i>Interface</i> {@code DynamicEntityNames}.
 * <p>
 * 
 * Nomes de entidade sao largamente utilizados quando ha necessidade de interacao com 
 * o banco de dados.<br>

 * Deve-se utilizar a constante ao inves de utilizar diretamente a String com o nome 
 * da entidade, principalmente se houver repeticao dentro do codigo.<br>
 * 
 * <pre>
 * <code>
 *	JapeWrapper dao = JapeFactory.dao(SklEntityName.CABECALHO_NOTA);
 *	FluidUpdateVO updVO = dao.prepareToUpdate(vo);
 *	//updVO.set("DTNEG", TimeUtils.clearTime(dtComercial));
 *	updVO.set(SklFieldName.DTNEG, TimeUtils.clearTime(dtComercial));
 *	updVO.update();
 * </code>
 * </pre>
 * 
 * @since 2022-05-05
 * @author cassiomenezes
 *
 */
public class SklEntityName {

	private SklEntityName() {
		throw new IllegalStateException("Utility class");
	}

	/*
	 * Entidades de banco de dados
	 */
	public static final String CALL_CENTER						= "AD_TMSCAL";
	public static final String OCORRENCIA_CALL_CENTER			= "AD_TMSCALOCO";
	public static final String NOTA_CALL_CENTER					= "AD_TMSCALNOTA";
	public static final String OCORRENCIA_POR_NOTA				= "AD_TMSCALNOTAOCO";
	public static final String AZS_LOG							= "AD_PLTAZSLOG";
	public static final String AZS_OCP							= "AD_PLVAZSOCP";
	public static final String CABECALHO_NOTA					= "AD_PLTGFCAB";
	public static final String CAPACIDADE_POR_CAIXA				= "AD_PLTPRCAPCX";
	public static final String CONTAGEM_INVENTARIO				= "AD_PLTGFIVTCTE";
	public static final String ECOMM_CABECALHO					= "PLTGFCABEC";
	public static final String ECOMM_ITEM						= "PLTGFITEEC";
	public static final String ECOMM_LIGACAO_PARCEIRO			= "PLTGFLPPEC";
	public static final String ECOMM_PARCEIRO					= "PLTGFPAREC";
	public static final String FAMILIA_PRODUTO					= "AD_PLTGFFAM";
	public static final String INVENTARIO						= "AD_PLTGFIVT";
	public static final String MOBILE_CONFERENCIA				= "AD_PLTGFCON";
	public static final String MOBILE_CONFERENCIA_ITEM			= "AD_PLTGFCOI";
	public static final String MOBILE_RECEBIMENTO				= "AD_PLTGFREC";
	public static final String MOBILE_RECEBIMENTO_CONFERENCIA	= "AD_PLTGFRECXCON";
	public static final String PRODUTO_CONTAGEM					= "AD_PLTGFIVTCTEPRO";
	public static final String RADAR_PRODUTOS					= "AD_PLTGFRADAR";
	public static final String REGIONAL_VENDA					= "AD_PLTGFREGVEN";
	public static final String REGIONAL_VENDA_CR				= "AD_PLTGFREGVENCR";
	public static final String REGIONAL_VENDA_CR_VENDEDOR		= "AD_PLTGFREGVENCRV";
	public static final String RESERVA							= "TGFRPED";
	public static final String RESERVA_ITENS					= "AD_ITENS";
	public static final String RESERVA_LOG						= "AD_TSILOGDPED";
	public static final String SEQUENCIAMENTO_OP				= "AD_PLTPRSEQOP";
	public static final String SEQUENCIAMENTO_OP_INSTANCIA		= "AD_PLTPRSEQOPI";
	public static final String TOPCARTEIRA						= "AD_TPLTTOPCART";
	public static final String REQUISICOES						= "AD_PLTGFREQ";
	public static final String PRODUTO_REQUISICOES				= "AD_PLTGFREQITE";
	public static final String ATENDIMENTO_PRODUTO_REQUISICOES	= "AD_PLTGFREQITEATD";
	public static final String HUB_TRANSPORTADORA				= "AD_PLTHUBTRA";
	public static final String HUB_TRANSPORTADORA_EVENTOS		= "AD_PLTHUBTRAEVE";
	public static final String HUB_TRANSPORTADORA_OCORRENCIA	= "AD_PLTHUBTRAOCO";
	public static final String HUB_TRANSPORTADORA_REQRES		= "AD_PLTHUBTRAREQRES";
	public static final String HUB_TRANSPORTADORA_STATUS		= "AD_PLTHUBTRASTA";
	
}
