package tech.skala.action.beans.meta;

import java.math.BigDecimal;

/**
 * A classe {@code MetaParams} representa os parametros necessarios para os calculos. 
 * <p>
 * 
 * A classe {@code MetaParams} representa os parametros necessarios para os calculos 
 * devendo ser instanciada ao se instanciar a {@code MetaPorFamilia} e sera utilizada
 * para se instanciar os produtos da Familia. 
 *  
 * @author cassiomenezes
 * @since 2022-04-29
 *
 */
public class MetaParams {
	private boolean							buildProdutos = false;
	private boolean							usaCarteira;
	private String							usarReferencia;
	private BigDecimal						percCob;
	
	public MetaParams(boolean buildProdutos, boolean carteira) {
		this.buildProdutos = buildProdutos;
		this.usaCarteira = carteira;
		this.usarReferencia = UsaReferenciaEnum.ATUAL.getValue();
		this.percCob = BigDecimal.ZERO;
	}

	public boolean isBuildProdutos() {
		return buildProdutos;
	}

	public boolean isUsaCarteira() {
		return usaCarteira;
	}

	public String getUsarReferencia() {
		return usarReferencia;
	}

	public void setUsarReferencia(String usarReferencia) {
		this.usarReferencia = usarReferencia;
	}

	public BigDecimal getPercCob() {
		return percCob;
	}

	public void setPercCob(BigDecimal percCob) {
		this.percCob = percCob;
	}

	@Override
	public String toString() {
		return String.format("MetaParams [buildProdutos=%s, usaCarteira=%s, usarReferencia=%s, percCob=%s]", buildProdutos,
				usaCarteira, usarReferencia, percCob);
	}

}
