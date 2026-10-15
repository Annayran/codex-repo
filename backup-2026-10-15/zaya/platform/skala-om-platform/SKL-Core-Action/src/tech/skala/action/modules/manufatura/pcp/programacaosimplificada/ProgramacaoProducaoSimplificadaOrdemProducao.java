package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import tech.skala.action.modules.manufatura.ordemproducao.OrdemProducaoStatusEnum;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;

/**
 * Ordem de Producao gerada a partir da Programacao Simplificada.
 * <br>
 * 
 * @author cassiomenezes
 * @since 2022-07-26
 * 
 */
public class ProgramacaoProducaoSimplificadaOrdemProducao implements EntityBean, Persistable {
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
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo = null;
		
		FluidCreateVO newVO = dao.create();
		newVO.set("NUPRGP", nuProgP);
		newVO.set("NULOP", BigDecimal.ZERO);
		newVO.set("IDIPROC", idiProc);
		newVO.set("CODPRODPA", codProdPA);
		newVO.set("NROLOTE", nroLote);
		newVO.set("STATUSPROC", OrdemProducaoStatusEnum.CRIADO.getValue());
		try {
			vo = newVO.save();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}
		
		return vo;

	}

	@Override
	public String toString() {
		return String.format(
				"ProgramacaoProducaoSimplificadaOrdemProducao [nuProgP=%s, idiProc=%s, codProdPA=%s, nroLote=%s, statusProc=%s]",
				nuProgP, idiProc, codProdPA, nroLote, statusProc);
	}

}
