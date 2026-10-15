package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import tech.skala.core.beans.interfaces.EntityBean;

/**
 * Ordem de Producao gerada a partir da Programacao Simplificada.
 * <br>
 * 
 * @since 2021-12-28
 * @author cassiomenezes
 * 
 */
public class ProgramacaoProducaoSimplificadaOrdemProducao implements EntityBean {

	public static final String ENTITY_NAME = "AD_PLTPRPRGPOP";

	private BigDecimal								nuProgP;
	private BigDecimal								idiProc;
	private BigDecimal								codProdPA;
	private String									nroLote;
	private String									statusProc;

	public BigDecimal getNuProgP() {
		return nuProgP;
	}

	public void setNuProgP(BigDecimal nuProgP) {
		this.nuProgP = nuProgP;
	}

	public BigDecimal getIdiProc() {
		return idiProc;
	}

	public void setIdiProc(BigDecimal idiProc) {
		this.idiProc = idiProc;
	}

	public BigDecimal getCodProdPA() {
		return codProdPA;
	}

	public void setCodProdPA(BigDecimal codProdPA) {
		this.codProdPA = codProdPA;
	}

	public String getNroLote() {
		return nroLote;
	}

	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}

	public String getStatusProc() {
		return statusProc;
	}

	public void setStatusProc(String statusProc) {
		this.statusProc = statusProc;
	}

	public void setFromProdutoAcabadoAProduzirVO(DynamicVO vo) {
		this.idiProc = vo.asBigDecimal("IDIPROC");
		this.codProdPA = vo.asBigDecimal("CODPRODPA");
		this.nroLote = vo.asString("NROLOTE");
	}

	public void persist() throws Exception {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		
		FluidCreateVO newVO = dao.create();
		newVO.set("NUPRGP", nuProgP);
		newVO.set("NULOP", BigDecimal.ZERO);
		newVO.set("IDIPROC", idiProc);
		newVO.set("CODPRODPA", codProdPA);
		newVO.set("NROLOTE", nroLote);
		newVO.set("STATUSPROC", OrdemProducaoStatusEnum.CRIADO.getValue());
		newVO.save();
	}

	@Override
	public String toString() {
		return String.format(
				"ProgramacaoProducaoSimplificadaOrdemProducao [nuProgP=%s, idiProc=%s, codProdPA=%s, nroLote=%s, statusProc=%s]",
				nuProgP, idiProc, codProdPA, nroLote, statusProc);
	}

}
