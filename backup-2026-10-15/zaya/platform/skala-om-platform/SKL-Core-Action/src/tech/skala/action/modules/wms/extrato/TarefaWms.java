package tech.skala.action.modules.wms.extrato;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

/**
 * A classe {@code TarefaWms} representa a entidade {@code AD_PLTGWMOV}.
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2022-07-22
 *
 */
public class TarefaWms implements EntityBean, LoadableByResultSet, Persistable {
	public static final String				ENTITY_NAME = "AD_PLTGWMOV"; //SklEntityName.WMS_EXTRATO;	

	private BigDecimal						numNotaRec;
	private BigDecimal						sequenciaRec;
	private BigDecimal						nuTarefa;
	private BigDecimal						sequencia;
	private BigDecimal						codProd;
	private BigDecimal						codEndOrigem;
	private String							descrEndOrigem;
	private String							enderecoOrigem;
	private BigDecimal						codEndDestino;
	private String							descrEndDestino;
	private String							enderecoDestino;
	private BigDecimal						qtdOrig;
	private String							codVolOrig;
	private BigDecimal						qtdDest;
	private String							codVolDest;
	private BigDecimal						qtdVolPad;
	private String							codVolPad;
	private String							situacao;
	private BigDecimal						codUsuExec;
	private Timestamp						dhInicialExec;
	private Timestamp						dhFinalExec;
	private BigDecimal						codEmp;
	private BigDecimal						codLocal;
	private String							controle;
	private String							tipTarefa;
	private BigDecimal						codTarefa;
	private BigDecimal						nuRecebimento;
	private BigDecimal						nuNotaRec;
	private BigDecimal						nuSeparacao;
	private BigDecimal						nuNotaSep;
	private BigDecimal						ordemCarga;
	private NotaVenda						notaVenda;
	
	public TarefaWms(BigDecimal numNotaRec, BigDecimal sequenciaRec, BigDecimal nuTarefa, BigDecimal sequencia) {
		this.numNotaRec = numNotaRec;
		this.sequenciaRec = sequenciaRec;
		this.nuTarefa = nuTarefa;
		this.sequencia = sequencia;
	}
	
	public BigDecimal getNumNotaRec() {
		return numNotaRec;
	}

	public BigDecimal getSequenciaRec() {
		return sequenciaRec;
	}

	public BigDecimal getNuTarefa() {
		return nuTarefa;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public BigDecimal getCodEndOrigem() {
		return codEndOrigem;
	}

	public void setCodEndOrigem(BigDecimal codEndOrigem) {
		this.codEndOrigem = codEndOrigem;
	}

	public String getDescrEndOrigem() {
		return descrEndOrigem;
	}

	public void setDescrEndOrigem(String descrEndOrigem) {
		this.descrEndOrigem = descrEndOrigem;
	}

	public String getEnderecoOrigem() {
		return enderecoOrigem;
	}

	public void setEnderecoOrigem(String enderecoOrigem) {
		this.enderecoOrigem = enderecoOrigem;
	}

	public BigDecimal getCodEndDestino() {
		return codEndDestino;
	}

	public void setCodEndDestino(BigDecimal codEndDestino) {
		this.codEndDestino = codEndDestino;
	}

	public String getDescrEndDestino() {
		return descrEndDestino;
	}

	public void setDescrEndDestino(String descrEndDestino) {
		this.descrEndDestino = descrEndDestino;
	}

	public String getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public BigDecimal getQtdOrig() {
		return qtdOrig;
	}

	public void setQtdOrig(BigDecimal qtdOrig) {
		this.qtdOrig = qtdOrig;
	}

	public String getCodVolOrig() {
		return codVolOrig;
	}

	public void setCodVolOrig(String codVolOrig) {
		this.codVolOrig = codVolOrig;
	}

	public BigDecimal getQtdDest() {
		return qtdDest;
	}

	public void setQtdDest(BigDecimal qtdDest) {
		this.qtdDest = qtdDest;
	}

	public String getCodVolDest() {
		return codVolDest;
	}

	public void setCodVolDest(String codVolDest) {
		this.codVolDest = codVolDest;
	}

	public BigDecimal getQtdVolPad() {
		return qtdVolPad;
	}

	public void setQtdVolPad(BigDecimal qtdVolPad) {
		this.qtdVolPad = qtdVolPad;
	}

	public String getCodVolPad() {
		return codVolPad;
	}

	public void setCodVolPad(String codVolPad) {
		this.codVolPad = codVolPad;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public BigDecimal getCodUsuExec() {
		return codUsuExec;
	}

	public void setCodUsuExec(BigDecimal codUsuExec) {
		this.codUsuExec = codUsuExec;
	}

	public Timestamp getDhInicialExec() {
		return dhInicialExec;
	}

	public void setDhInicialExec(Timestamp dhInicialExec) {
		this.dhInicialExec = dhInicialExec;
	}

	public Timestamp getDhFinalExec() {
		return dhFinalExec;
	}

	public void setDhFinalExec(Timestamp dhFinalExec) {
		this.dhFinalExec = dhFinalExec;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(BigDecimal codLocal) {
		this.codLocal = codLocal;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public String getTipTarefa() {
		return tipTarefa;
	}

	public void setTipTarefa(String tipTarefa) {
		this.tipTarefa = tipTarefa;
	}

	public BigDecimal getCodTarefa() {
		return codTarefa;
	}

	public void setCodTarefa(BigDecimal codTarefa) {
		this.codTarefa = codTarefa;
	}

	public BigDecimal getNuRecebimento() {
		return nuRecebimento;
	}

	public void setNuRecebimento(BigDecimal nuRecebimento) {
		this.nuRecebimento = nuRecebimento;
	}

	public BigDecimal getNuNotaRec() {
		return nuNotaRec;
	}

	public void setNuNotaRec(BigDecimal nuNotaRec) {
		this.nuNotaRec = nuNotaRec;
	}

	public BigDecimal getNuSeparacao() {
		return nuSeparacao;
	}

	public void setNuSeparacao(BigDecimal nuSeparacao) {
		this.nuSeparacao = nuSeparacao;
	}

	public BigDecimal getNuNotaSep() {
		return nuNotaSep;
	}

	public void setNuNotaSep(BigDecimal nuNotaSep) {
		this.nuNotaSep = nuNotaSep;
	}

	public BigDecimal getOrdemCarga() {
		return ordemCarga;
	}

	public void setOrdemCarga(BigDecimal ordemCarga) {
		this.ordemCarga = ordemCarga;
	}

	public NotaVenda getNotaVenda() {
		return notaVenda;
	}

	public void setNotaVenda(NotaVenda notaVenda) {
		this.notaVenda = notaVenda;
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			this.nuTarefa = rs.getBigDecimal(SklFieldName.NUTAREFA);
			this.sequencia = rs.getBigDecimal(SklFieldName.SEQUENCIA);
			this.codProd = rs.getBigDecimal(SklFieldName.CODPROD);
			this.codEndOrigem = rs.getBigDecimal("CODENDORIGEM");
			this.descrEndOrigem = rs.getString("DESCRENDORIGEM");
			this.enderecoOrigem = rs.getString("ENDERECOORIGEM");
			this.codEndDestino = rs.getBigDecimal("CODENDDESTINO");
			this.descrEndDestino = rs.getString("DESCRENDDESTINO");
			this.enderecoDestino = rs.getString("ENDERECODESTINO");
			this.qtdOrig = rs.getBigDecimal("QTDORIG");
			this.codVolOrig = rs.getString("CODVOLORIG");
			this.qtdDest = rs.getBigDecimal("QTDDEST");
			this.codVolDest = rs.getString("CODVOLDEST");
			this.qtdVolPad = rs.getBigDecimal("QTDVOLPAD");
			this.codVolPad = rs.getString(SklFieldName.CODVOLPAD);
			this.situacao = rs.getString(SklFieldName.SITUACAO);
			this.codUsuExec = rs.getBigDecimal("CODUSUEXEC");
			this.dhInicialExec = rs.getTimestamp("DHINICIALEXEC");
			this.dhFinalExec = rs.getTimestamp("DHFINALEXEC");
			this.codEmp = rs.getBigDecimal(SklFieldName.CODEMP);
			this.codLocal = rs.getBigDecimal(SklFieldName.CODLOCAL);
			this.controle = rs.getString(SklFieldName.CONTROLE);
			this.tipTarefa = rs.getString("TIPTAREFA");
			this.codTarefa = rs.getBigDecimal("CODTAREFA");
			this.nuRecebimento = rs.getBigDecimal(SklFieldName.NURECEBIMENTO);
			this.nuNotaRec = rs.getBigDecimal("NUNOTAREC");
			this.nuSeparacao = rs.getBigDecimal("NUSEPARACAO");
			this.nuNotaSep = rs.getBigDecimal("NUNOTASEP");
			this.ordemCarga = rs.getBigDecimal(SklFieldName.ORDEMCARGA);
		} catch (SQLException e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {

		EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
		entityNew.setField("NUMNOTAREC", numNotaRec);
		entityNew.setField("SEQUENCIAREC", sequenciaRec);
		entityNew.setField(SklFieldName.NUTAREFA, nuTarefa);
		entityNew.setField(SklFieldName.SEQUENCIA, sequencia);
		entityNew.setField(SklFieldName.CODPROD, codProd);
		entityNew.setField("CODENDORIGEM", codEndOrigem);
		entityNew.setField("DESCRENDORIGEM", descrEndOrigem);
		entityNew.setField("ENDERECOORIGEM", enderecoOrigem);
		entityNew.setField("CODENDDESTINO", codEndDestino);
		entityNew.setField("DESCRENDDESTINO", descrEndDestino);
		entityNew.setField("ENDERECODESTINO", enderecoDestino);
		entityNew.setField("QTDORIG", qtdOrig);
		entityNew.setField("CODVOLORIG", codVolOrig);
		entityNew.setField("QTDDEST", qtdDest);
		entityNew.setField("CODVOLDEST", codVolDest);
		entityNew.setField("QTDVOLPAD", qtdVolPad);
		entityNew.setField(SklFieldName.CODVOLPAD, codVolPad);
		entityNew.setField(SklFieldName.SITUACAO, situacao);
		entityNew.setField("CODUSUEXEC", codUsuExec);
		entityNew.setField("DHINICIALEXEC", dhInicialExec);
		entityNew.setField("DHFINALEXEC", dhFinalExec);
		entityNew.setField(SklFieldName.CODEMP, codEmp);
		entityNew.setField(SklFieldName.CODLOCAL, codLocal);
		entityNew.setField(SklFieldName.CONTROLE, controle);
		entityNew.setField("TIPTAREFA", tipTarefa);
		entityNew.setField("CODTAREFA", codTarefa);
		entityNew.setField(SklFieldName.NURECEBIMENTO, nuRecebimento);
		entityNew.setField("NUNOTAREC", nuNotaRec);
		entityNew.setField("NUSEPARACAO", nuSeparacao);
		entityNew.setField("NUNOTASEP", nuNotaSep);
		entityNew.setField(SklFieldName.ORDEMCARGA, ordemCarga);

		if (notaVenda != null) {
			entityNew.setField("NUNOTAVENDA", notaVenda.getNuNota());
			entityNew.setField("CODTIPOPERVENDA", notaVenda.getCodTipOper());
			entityNew.setField("TIPMOVVENDA", notaVenda.getTipMov());
			entityNew.setField("NUMNOTAVENDA", notaVenda.getNumNota());
			entityNew.setField("DTFATURVENDA", notaVenda.getDtFatur());
			entityNew.setField(SklFieldName.CODPARC, notaVenda.getCodParc());
			entityNew.setField(SklFieldName.RAZAOSOCIAL, notaVenda.getRazaoSocial());
			entityNew.setField(SklFieldName.CGC_CPF, notaVenda.getCgcCnpj());
		}

		return entityNew.fluidInsert();
	}

	@Override
	public String toString() {
		return String.format(
				"TarefaWms [numNotaRec=%s, sequenciaRec=%s, nuTarefa=%s, sequencia=%s, codProd=%s, codEndOrigem=%s, descrEndOrigem=%s, enderecoOrigem=%s, codEndDestino=%s, descrEndDestino=%s, enderecoDestino=%s, qtdOrig=%s, codVolOrig=%s, qtdDest=%s, codVolDest=%s, qtdVolPad=%s, codVolPad=%s, situacao=%s, codUsuExec=%s, dhInicialExec=%s, dhFinalExec=%s, codEmp=%s, codLocal=%s, controle=%s, tipTarefa=%s, codTarefa=%s, nuRecebimento=%s, nuNotaRec=%s, nuSeparacao=%s, nuNotaSep=%s, ordemCarga=%s, notaVenda=%s]",
				numNotaRec, sequenciaRec, nuTarefa, sequencia, codProd, codEndOrigem, descrEndOrigem, enderecoOrigem,
				codEndDestino, descrEndDestino, enderecoDestino, qtdOrig, codVolOrig, qtdDest, codVolDest, qtdVolPad,
				codVolPad, situacao, codUsuExec, dhInicialExec, dhFinalExec, codEmp, codLocal, controle, tipTarefa,
				codTarefa, nuRecebimento, nuNotaRec, nuSeparacao, nuNotaSep, ordemCarga, notaVenda);
	}
	
}
