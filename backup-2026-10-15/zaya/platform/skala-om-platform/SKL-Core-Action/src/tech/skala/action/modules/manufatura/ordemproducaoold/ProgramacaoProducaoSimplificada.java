package tech.skala.action.modules.manufatura.ordemproducaoold;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.cbmsoft.modelcore.CbmModelException;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.exceptions.EntityBeanExceptions;
import tech.skala.core.beans.exceptions.EntityBeanExceptions.SetFromRecordNotPossibleException;
import tech.skala.core.beans.interfaces.EntityBean;

public class ProgramacaoProducaoSimplificada implements EntityBean {

	public static final String ENTITY_NAME = "AD_PLTPRPRGP";
	
	private BigDecimal								nuProgP;
	private String									descricao;
	private BigDecimal								codPlp;
	private BigDecimal								codWcp;
	private BigDecimal								idProc;
	private BigDecimal								codProdPA;
	private BigDecimal								qtdLotes;
	private BigDecimal								prioridade;
	private Timestamp								dtProg;
	private String									status;
	private BigDecimal								qtdCapacidadePad;
	private List<BigDecimal>						ordensDeProducaoLancadas = new LinkedList<>();
	private List<ProgramacaoProducaoSimplificadaOrdemProducao>	ordensDeProducao = new LinkedList<>();

	public BigDecimal getNuProgP() {
		return nuProgP;
	}

	public void setNuProgP(BigDecimal nuProgP) {
		this.nuProgP = nuProgP;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getCodPlp() {
		return codPlp;
	}

	public void setCodPlp(BigDecimal codPlp) {
		this.codPlp = codPlp;
	}

	public BigDecimal getCodWcp() {
		return codWcp;
	}

	public void setCodWcp(BigDecimal codWcp) {
		this.codWcp = codWcp;
	}

	public BigDecimal getIdProc() {
		return idProc;
	}

	public void setIdProc(BigDecimal idProc) {
		this.idProc = idProc;
	}

	public BigDecimal getCodProdPA() {
		return codProdPA;
	}

	public void setCodProdPA(BigDecimal codProdPA) {
		this.codProdPA = codProdPA;
	}

	public BigDecimal getQtdLotes() {
		return qtdLotes;
	}

	public void setQtdLotes(BigDecimal qtdLotes) {
		this.qtdLotes = qtdLotes;
	}

	public BigDecimal getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(BigDecimal prioridade) {
		this.prioridade = prioridade;
	}

	public Timestamp getDtProg() {
		return dtProg;
	}

	public void setDtProg(Timestamp dtProg) {
		this.dtProg = dtProg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getQtdCapacidadePad() {
		return qtdCapacidadePad;
	}

	public void setQtdCapacidadePad(BigDecimal qtdCapacidadePad) {
		this.qtdCapacidadePad = qtdCapacidadePad;
	}

	public List<BigDecimal> getOrdensDeProducaoLancadas() {
		return ordensDeProducaoLancadas;
	}

	public void setOrdensDeProducaoLancadas(List<BigDecimal> ordensDeProducaoLancadas) {
		this.ordensDeProducaoLancadas = ordensDeProducaoLancadas;
	}

	public List<ProgramacaoProducaoSimplificadaOrdemProducao> getOrdensDeProducao() {
		return ordensDeProducao;
	}

	public void setOrdensDeProducao(List<ProgramacaoProducaoSimplificadaOrdemProducao> ordensDeProducao) {
		this.ordensDeProducao = ordensDeProducao;
	}

	public void setFromPK(Object... keyValue) throws CbmModelException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = dao.findByPK(keyValue);
			setFromVO(vo);
		} catch (Exception e) {
			String msg = String.format("Não foi possível inicializar o registro a partir da chave %s.", keyValue);
			msg = CbmModelException.buildErrorMessage(e, msg);
			
			throw new CbmModelException(msg);
		}
	}

	public void setFromVO(DynamicVO vo) throws Exception {
		this.nuProgP = vo.asBigDecimal("NUPRGP");
		this.descricao = vo.asString("DESCRICAO");
		this.codPlp = vo.asBigDecimal("CODPLP");
		this.codWcp = vo.asBigDecimal("CODWCP");
		this.idProc = vo.asBigDecimal("IDPROC");
		this.codProdPA = vo.asBigDecimal("CODPROD");
		this.qtdLotes = vo.asBigDecimal("QTDLOTES");
		this.prioridade = vo.asBigDecimal("PRIORIDADE");
		this.dtProg = TimeUtils.clearTime(vo.asTimestamp("DTPROG"));
		this.status = vo.asString("STATUS");
		
		if (idProc == null) {
			this.idProc = ProcessoProdutivoHelper.getProcessoProdutivoPadrao(codProdPA);
		}

		this.qtdCapacidadePad = ProcessoProdutivoHelper.getCapacidadePadraoWorkCenter(codWcp);
	}
	
	public void setFromRecord(Registro linha) throws SetFromRecordNotPossibleException {
		try {
			this.nuProgP = BigDecimalUtil.getValueOrZero((BigDecimal) linha.getCampo("NUPRGP"));
			this.descricao = (String) linha.getCampo("DESCRICAO");
			this.codPlp = BigDecimalUtil.getValueOrZero((BigDecimal) linha.getCampo("CODPLP"));
			this.codWcp = BigDecimalUtil.getValueOrZero((BigDecimal) linha.getCampo("CODWCP"));
			this.idProc = BigDecimalUtil.getValueOrZero((BigDecimal) linha.getCampo("IDPROC"));
			this.codProdPA = BigDecimalUtil.getValueOrZero((BigDecimal) linha.getCampo("CODPROD"));
			this.qtdLotes = BigDecimalUtil.getValueOrZero((BigDecimal) linha.getCampo("QTDLOTES"));
			this.prioridade = BigDecimalUtil.getValueOrZero((BigDecimal) linha.getCampo("PRIORIDADE"));
			this.dtProg = TimeUtils.clearTime((Timestamp) linha.getCampo("DTPROG"));
			this.status = (String) linha.getCampo("STATUS");
			this.qtdCapacidadePad = ProcessoProdutivoHelper.getCapacidadePadraoWorkCenter(codWcp);
			
			if (idProc == null)
				this.idProc = ProcessoProdutivoHelper.getProcessoProdutivoPadrao(codProdPA);
			
		} catch (Exception e) {
			throw new EntityBeanExceptions.SetFromRecordNotPossibleException(e);
		}

	}

	@Override
	public String toString() {
		return String.format(
				"ProgramacaoProducaoSimplificada [nuProgP=%s, descricao=%s, codPlp=%s, codWcp=%s, idProc=%s, codProdPA=%s, qtdLotes=%s, prioridade=%s, dtProg=%s, status=%s, qtdCapacidadePad=%s, ordensDeProducaoLancadas=%s, ordensDeProducao=%s]",
				nuProgP, descricao, codPlp, codWcp, idProc, codProdPA, qtdLotes, prioridade, dtProg, status,
				qtdCapacidadePad, ordensDeProducaoLancadas, ordensDeProducao);
	}

}
