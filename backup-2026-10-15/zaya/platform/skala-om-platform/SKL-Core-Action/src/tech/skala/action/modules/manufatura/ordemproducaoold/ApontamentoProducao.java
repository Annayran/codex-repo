package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;

public class ApontamentoProducao implements EntityBean {

	private BigDecimal						nuApo;
	private BigDecimal						idiAtv;
	private String							observacao;
	private String							situacao;

	public BigDecimal getNuApo() {
		return nuApo;
	}

	public void setNuApo(BigDecimal nuApo) {
		this.nuApo = nuApo;
	}

	public BigDecimal getIdiAtv() {
		return idiAtv;
	}

	public void setIdiAtv(BigDecimal idiAtv) {
		this.idiAtv = idiAtv;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public void setFromVO(DynamicVO vo) throws Exception {
		this.nuApo = vo.asBigDecimal("NUAPO");
		this.idiAtv = vo.asBigDecimal("IDIATV");
		this.observacao = vo.asString("OBSERVACAO");
		this.situacao = vo.asString("SITUACAO");
	}
	
	@Override
	public String toString() {
		return String.format("ApontamentoProducao [nuApo=%s, idiAtv=%s, observacao=%s, situacao=%s]", nuApo, idiAtv,
				observacao, situacao);
	}

}
