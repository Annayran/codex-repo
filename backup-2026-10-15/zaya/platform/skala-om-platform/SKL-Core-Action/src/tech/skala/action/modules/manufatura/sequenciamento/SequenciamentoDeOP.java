package tech.skala.action.modules.manufatura.sequenciamento;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.modules.manufatura.pcp.programacaosimplificada.ProgramacaoProducaoSimplificada;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzir;
import tech.skala.action.modules.manufatura.workcenter.WorkCenter;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByRecord;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class SequenciamentoDeOP implements EntityBean, Persistable, LoadableByRecord {
	
	public static final String ENTITY_NAME = SklEntityName.SEQUENCIAMENTO_OP;
	
	private BigDecimal								seqOP;
	private BigDecimal								codPlp;
	private WorkCenter								workCenter;
	private Timestamp								dtProg;
	private String									observacao;
	private ProgramacaoProducaoSimplificada			programacao;
	private List<BigDecimal>						ordensDeProducaoLancadas = new LinkedList<>();
	private Collection<ProdutoAcabadoAProduzir> 	produtosAProduzir = new ArrayList<>();
	private List<SequenciamentoDeOPInstancia>		itens = new LinkedList<>();

	public SequenciamentoDeOP(ProgramacaoProducaoSimplificada programacao) {
		this.codPlp = programacao.getCodPlp();
		this.workCenter = programacao.getWorkCenter();
		this.dtProg = programacao.getDtProg();
		this.programacao = programacao;
	}

	public SequenciamentoDeOP() {
		super();
	}

	public BigDecimal getCodPlp() {
		return codPlp;
	}

	public BigDecimal getCodWcp() {
		return workCenter.getCodWcp();
	}
	
	public WorkCenter getWorkCenter() {
		return workCenter;
	}
	
	public Timestamp getDtProg() {
		return dtProg;
	}

	public List<BigDecimal> getOrdensDeProducaoLancadas() {
		return ordensDeProducaoLancadas;
	}

	public void setOrdensDeProducaoLancadas(List<BigDecimal> ordensDeProducaoLancadas) {
		this.ordensDeProducaoLancadas = ordensDeProducaoLancadas;
	}

	public List<SequenciamentoDeOPInstancia> getItens() {
		return itens;
	}

	public void setItens(List<SequenciamentoDeOPInstancia> itens) {
		this.itens = itens;
	}
	
	public void setSeqOP(BigDecimal seqOP) {
		this.seqOP = seqOP;
	}

	public BigDecimal getSeqOP() {
		return seqOP;
	}

	public Collection<ProdutoAcabadoAProduzir> getProdutosAProduzir() {
		return produtosAProduzir;
	}

	public void setProdutosAProduzir(Collection<ProdutoAcabadoAProduzir> produtosAProduzir) {
		this.produtosAProduzir = produtosAProduzir;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public ProgramacaoProducaoSimplificada getProgramacao() {
		return programacao;
	}

	public void setProgramacao(ProgramacaoProducaoSimplificada programacao) {
		this.programacao = programacao;
	}

	@Override
	public void loadByRecord(Registro linha) throws RecordNotFoundException {
		this.seqOP = BigDecimalUtil.getBigDecimal(linha.getCampo("SEQOP"));
		this.codPlp = BigDecimalUtil.getBigDecimal(linha.getCampo("CODPLP"));
		this.dtProg = (Timestamp) linha.getCampo("DTPROG");
		this.observacao = (String) linha.getCampo(SklFieldName.OBSERVACAO);

		BigDecimal codWcp = BigDecimalUtil.getBigDecimal(linha.getCampo("CODWCP"));
		this.workCenter = new WorkCenter();
		this.workCenter.loadByPk(codWcp);
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		
		DynamicVO vo = null;
		if(seqOP != null) {
			try {
				vo = dao.findByPK(seqOP);
			} catch (Exception e) {
				throw new UpdateNotPossibleException(e);
			}			
		}
		
		if(vo == null) {
			EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
			entityNew.setField(SklFieldName.CODPLP, codPlp);
			entityNew.setField(SklFieldName.CODWCP, workCenter.getCodWcp());
			entityNew.setField(SklFieldName.DTPROG, dtProg);
			entityNew.setField(SklFieldName.OBSERVACAO, observacao);
			entityNew.setField("NUPRGP", programacao.getNuProgP());
			DynamicVO newVO = entityNew.fluidInsert();
			
			this.seqOP = newVO.asBigDecimal("SEQOP");
			
		} else {
			EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, vo);
			entityUpd.setField(SklFieldName.CODWCP, workCenter.getCodWcp());
			entityUpd.fluidUpdate();
		}
		
		return null;
	}

}
