package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivo;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivoFinder;
import tech.skala.action.modules.manufatura.processoprodutivo.ProcessoProdutivoResumo;
import tech.skala.action.modules.manufatura.produtoacabado.ProdutoAcabadoExceptions.StandardProductiveProcessNotFoundException;
import tech.skala.action.modules.manufatura.produtoacabado.ProdutoAcabadoFinder;
import tech.skala.action.modules.manufatura.workcenter.WorkCenter;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByRecord;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.beans.produto.ProdutoExceptions.AlternativeVolumeNotFoundException;
import tech.skala.core.beans.produto.ProdutoVenda;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProgramacaoProducaoSimplificada implements EntityBean, LoadableByRecord, LoadableByPrimaryKey, LoadableByValueObject, LoadableByResultSet, Persistable {
	public static final String						ENTITY_NAME = "AD_PLTPRPRGP";
	
	private BigDecimal								nuProgP;
	private String									descricao;
	private BigDecimal								codPlp;
	private BigDecimal								codWcp;
	private BigDecimal								idProc;
	private BigDecimal								codPrc;
	private BigDecimal								codProdPA;
	private ProdutoVenda							produto;
	private BigDecimal								qtdLotes;
	private BigDecimal								prioridade;
	private Timestamp								dtProg;
	private String									status;
	private BigDecimal								qtdCapacidadePad;
	private WorkCenter								workCenter;
	private List<BigDecimal>						ordensDeProducaoLancadas = new LinkedList<>();
	private List<ProgramacaoProducaoSimplificadaOrdemProducao>	ordensDeProducao = new LinkedList<>();

	public ProgramacaoProducaoSimplificada(BigDecimal nuProgP) {
		this.nuProgP = nuProgP;
	}

	public BigDecimal getNuProgP() {
		return nuProgP;
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

	public ProdutoVenda getProduto() {
		return produto;
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

	public WorkCenter getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(WorkCenter workCenter) {
		this.workCenter = workCenter;
	}

	public void setNuProgP(BigDecimal nuProgP) {
		this.nuProgP = nuProgP;
	}

	@Override
	public void loadByRecord(Registro linha) throws RecordNotFoundException {
		
		if (nuProgP == null)
			this.nuProgP = BigDecimalUtil.getBigDecimal(linha.getCampo("NUPRGP"));
		
		this.descricao = (String) linha.getCampo("DESCRICAO");
		this.codPlp = BigDecimalUtil.getBigDecimal(linha.getCampo("CODPLP"));
		this.codWcp = BigDecimalUtil.getBigDecimal(linha.getCampo("CODWCP"));
		this.idProc = BigDecimalUtil.getBigDecimal(linha.getCampo("IDPROC"));
		this.codPrc = BigDecimalUtil.getBigDecimal(linha.getCampo("CODPRC"));
		this.codProdPA = BigDecimalUtil.getBigDecimal(linha.getCampo("CODPROD"));
		this.qtdLotes = BigDecimalUtil.getBigDecimal(linha.getCampo("QTDLOTES"));
		this.prioridade = BigDecimalUtil.getBigDecimal(linha.getCampo("PRIORIDADE"));
		this.dtProg = TimeUtils.clearTime((Timestamp) linha.getCampo("DTPROG"));
		this.status = (String) linha.getCampo("STATUS");
		this.qtdCapacidadePad = BigDecimalUtil.getBigDecimal(linha.getCampo("QTDCAPACIDADEPAD"));
		
		try {
			this.produto = new ProdutoVenda(codProdPA);
			this.produto.loadByPk(codProdPA);
		} catch (AlternativeVolumeNotFoundException e) {
			throw new RecordNotFoundException(e);
		}
		
		try {

			this.workCenter = new WorkCenter();
			this.workCenter.loadByPk(codWcp);

			//this.qtdCapacidadePad = workCenter.getQtdCapacidadePad();

			if (codPrc != null) {
				ProcessoProdutivoResumo processoProdutivo = ProcessoProdutivoFinder.buscaProcessoProdutivoPorCodPrc(codPrc);
				
				if (processoProdutivo != null) {
					this.idProc = processoProdutivo.getIdProc();
				}
								
			}
			
			if (idProc == null) {
				ProdutoAcabadoFinder finder = new ProdutoAcabadoFinder(codProdPA, codPlp);
				this.idProc = finder.findProcessoProdutivoPadrao();
			}
			
			
			

		} catch (SkalaModelException e) {
			throw new RecordNotFoundException(e);
		}

	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		if (nuProgP == null)
			this.nuProgP = vo.asBigDecimal("NUPRGP");
		
		this.descricao = vo.asString("DESCRICAO");
		this.codPlp = vo.asBigDecimal(SklFieldName.CODPLP);
		this.codWcp = vo.asBigDecimal(SklFieldName.CODWCP);
		this.idProc = vo.asBigDecimal("IDPROC");
		this.codProdPA = vo.asBigDecimal(SklFieldName.CODPROD);
		this.qtdLotes = vo.asBigDecimal("QTDLOTES");
		this.prioridade = vo.asBigDecimal(SklFieldName.PRIORIDADE);
		this.dtProg = TimeUtils.clearTime(vo.asTimestamp(SklFieldName.DTPROG));
		this.qtdCapacidadePad = vo.asBigDecimal(SklFieldName.QTDCAPACIDADEPAD);
		this.status =  vo.asString("STATUS");
	
		this.workCenter = new WorkCenter();
		this.workCenter.loadByPk(codWcp);
		
		
		this.ordensDeProducaoLancadas = ProgramacaoSimplificadaFinder.findOrdensProducao(nuProgP);
	}

	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = (nuProgP == null) ? dao.findByPK(keyValue) : dao.findByPK(nuProgP);
			loadByVO(vo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecordNotFoundException(e);
		}		
	}
	
	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			this.descricao = rs.getString("DESCRICAO");
			this.codPlp = rs.getBigDecimal(SklFieldName.CODPLP);
			this.codWcp = rs.getBigDecimal(SklFieldName.CODWCP);
			this.idProc = rs.getBigDecimal("IDPROC");
			this.codProdPA = rs.getBigDecimal(SklFieldName.CODPROD);
			this.qtdLotes = rs.getBigDecimal("QTDLOTES");
			this.prioridade = rs.getBigDecimal(SklFieldName.PRIORIDADE);
			this.dtProg = TimeUtils.clearTime(rs.getTimestamp(SklFieldName.DTPROG));
			this.status = rs.getString("STATUS");
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException();
		}
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
		entityNew.setField("DESCRICAO", descricao);
		entityNew.setField("CODPLP", codPlp);
		entityNew.setField("CODWCP", codWcp);
		entityNew.setField("IDPROC", idProc);
		entityNew.setField("CODPROD", codProdPA);
		entityNew.setField("QTDLOTES", qtdLotes);
		entityNew.setField("PRIORIDADE", prioridade);
		entityNew.setField("DTPROG", dtProg);
		entityNew.setField("STATUS", status);

		DynamicVO newVO = entityNew.fluidInsert();

		this.nuProgP = newVO.asBigDecimal("NUPRGP");
		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"ProgramacaoProducaoSimplificada [nuProgP=%s, descricao=%s, codPlp=%s, codWcp=%s, idProc=%s, codProdPA=%s, produto=%s, qtdLotes=%s, prioridade=%s, dtProg=%s, status=%s, qtdCapacidadePad=%s, workCenter=%s, ordensDeProducaoLancadas=%s, ordensDeProducao=%s]",
				nuProgP, descricao, codPlp, codWcp, idProc, codProdPA, produto, qtdLotes, prioridade, dtProg, status,
				qtdCapacidadePad, workCenter, ordensDeProducaoLancadas, ordensDeProducao);
	}


}
