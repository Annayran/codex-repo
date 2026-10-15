package tech.skala.action.modules.manufatura.sequenciamento;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.action.modules.manufatura.workcenter.WorkCenter;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class SequenciamentoDeOPInstancia implements EntityBean, Persistable {
	
	public static final String ENTITY_NAME = SklEntityName.SEQUENCIAMENTO_OP_INSTANCIA;
	
	private BigDecimal						seqOP;
	private BigDecimal						idiProc;
	private BigDecimal						codProdPA;
	private String							controlePA;
	private Timestamp						dtProg;
	private BigDecimal						qtdProduzir;
	private BigDecimal						peso;
	private String							nroLote;
	private WorkCenter						workCenter;
	private boolean							isConcluido;
	private BigDecimal						prioridade;
	
	public BigDecimal getSeqOP() {
		return seqOP;
	}
	
	public void setSeqOP(BigDecimal seqOP) {
		this.seqOP = seqOP;
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
	
	public String getControlePA() {
		return controlePA;
	}
	
	public void setControlePA(String controlePA) {
		this.controlePA = controlePA;
	}
	
	public BigDecimal getQtdProduzir() {
		return qtdProduzir;
	}
	
	public void setQtdProduzir(BigDecimal qtdProduzir) {
		this.qtdProduzir = qtdProduzir;
	}
	
	public BigDecimal getPeso() {
		return peso;
	}
	
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}
	
	public String getNroLote() {
		return nroLote;
	}
	
	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}
	
	public boolean isConcluido() {
		return isConcluido;
	}
	
	public void setConcluido(boolean isConcluido) {
		this.isConcluido = isConcluido;
	}
	
	public BigDecimal getPrioridade() {
		return prioridade;
	}
	
	public void setPrioridade(BigDecimal prioridade) {
		this.prioridade = prioridade;
	}
	
	public WorkCenter getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(WorkCenter workCenter) {
		this.workCenter = workCenter;
	}

	public Timestamp getDtProg() {
		return dtProg;
	}

	public void setDtProg(Timestamp dtProg) {
		this.dtProg = dtProg;
	}

	public void loadByProdutoAcabadoAProduzirVO(DynamicVO vo) {
		this.idiProc = vo.asBigDecimal(SklFieldName.IDIPROC);
		this.codProdPA = vo.asBigDecimal(SklFieldName.CODPRODPA);
		this.controlePA = vo.asString(SklFieldName.CONTROLEPA);
		this.qtdProduzir = vo.asBigDecimal(SklFieldName.QTDPRODUZIR);
		this.nroLote = vo.asString(SklFieldName.NROLOTE);
		this.isConcluido = false;
		
		BigDecimal pesoPadrao = workCenter.getPesoPad();
		BigDecimal pesoLiquido = vo.asBigDecimalOrZero("Produto.PESOLIQ");
		
		BigDecimal peso = (pesoPadrao == null) ? pesoLiquido : pesoPadrao;
		BigDecimal pesoCalculado = peso.multiply(qtdProduzir, BigDecimalUtil.MATH_CTX);
		this.peso = pesoCalculado;
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {

		EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
		entityNew.setField("SEQOP", seqOP);
		entityNew.setField(SklFieldName.IDIPROC, idiProc);
		entityNew.setField(SklFieldName.CODPRODPA, codProdPA);
		entityNew.setField(SklFieldName.CONTROLEPA, controlePA);
		entityNew.setField(SklFieldName.DTPROG, dtProg);
		entityNew.setField(SklFieldName.QTDPRODUZIR, qtdProduzir);
		entityNew.setField(SklFieldName.PESO, peso);
		entityNew.setField(SklFieldName.NROLOTE, nroLote);
		entityNew.setField(SklFieldName.CONCLUIDO, (isConcluido) ? "S" : "N");
		entityNew.setField(SklFieldName.PRIORIDADE, prioridade);
		entityNew.fluidInsert();
		
		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"SequenciamentoDeOPInstancia [seqOP=%s, idiProc=%s, codProdPA=%s, controlePA=%s, qtdProduzir=%s, peso=%s, nroLote=%s, isConcluido=%s, prioridade=%s]",
				seqOP, idiProc, codProdPA, controlePA, qtdProduzir, peso, nroLote, isConcluido, prioridade);
	}



	
}
