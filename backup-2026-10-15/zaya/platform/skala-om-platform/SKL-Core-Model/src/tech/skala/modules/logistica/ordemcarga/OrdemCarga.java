package tech.skala.modules.logistica.ordemcarga;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrdemCarga implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject, Persistable, Cloneable {

	private static final String						ENTITY_NAME = DynamicEntityNames.ORDEM_CARGA;
	
	private BigDecimal  							codEmp;
	private BigDecimal  							ordemCargaPai;
	private BigDecimal  							ordemCarga;
	private String									entSai;
	private Timestamp  								dtInic;
	private Timestamp  								dtPrevSaida;
	private Timestamp								horaSaida;
	private BigDecimal  							codReg;
	private BigDecimal  							codParcTransp;
	private String  								tipCarga;
	private BigDecimal								codVeiculo;
	private String									statusCarga;
	private String									situacao;
	private String									tipoEmbalagens;
	private String									roteiro;

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public Timestamp getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(Timestamp horaSaida) {
		this.horaSaida = horaSaida;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getOrdemCargaPai() {
		return ordemCargaPai;
	}

	public void setOrdemCargaPai(BigDecimal ordemCargaPai) {
		this.ordemCargaPai = ordemCargaPai;
	}

	public BigDecimal getOrdemCarga() {
		return ordemCarga;
	}

	public void setOrdemCarga(BigDecimal ordemCarga) {
		this.ordemCarga = ordemCarga;
	}

	public String getEntSai() {
		return entSai;
	}

	public void setEntSai(String entSai) {
		this.entSai = entSai;
	}

	public Timestamp getDtInic() {
		return dtInic;
	}

	public void setDtInic(Timestamp dtInic) {
		this.dtInic = dtInic;
	}

	public Timestamp getDtPrevSaida() {
		return dtPrevSaida;
	}

	public void setDtPrevSaida(Timestamp dtPrevSaida) {
		this.dtPrevSaida = dtPrevSaida;
	}

	public BigDecimal getCodReg() {
		return codReg;
	}

	public void setCodReg(BigDecimal codReg) {
		this.codReg = codReg;
	}

	public BigDecimal getCodParcTransp() {
		return codParcTransp;
	}

	public void setCodParcTransp(BigDecimal codParcTransp) {
		this.codParcTransp = codParcTransp;
	}

	public String getTipCarga() {
		return tipCarga;
	}

	public void setTipCarga(String tipCarga) {
		this.tipCarga = tipCarga;
	}

	public BigDecimal getCodVeiculo() {
		return codVeiculo;
	}

	public void setCodVeiculo(BigDecimal codVeiculo) {
		this.codVeiculo = codVeiculo;
	}

	public String getTipoEmbalagens() {
		return tipoEmbalagens;
	}

	public void setTipoEmbalagens(String tipoEmbalagens) {
		this.tipoEmbalagens = tipoEmbalagens;
	}

	public String getRoteiro() {
		return roteiro;
	}

	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}

	public String getStatusCarga() {
		return statusCarga;
	}

	public void setStatusCarga(String statusCarga) {
		this.statusCarga = statusCarga;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		EntityInsert entityNew = new EntityInsert(ENTITY_NAME);

		entityNew.setField(SklFieldName.CODEMP, codEmp);
		entityNew.setField(SklFieldName.DTINIC, dtInic);
		entityNew.setField(SklFieldName.DTPREVSAIDA, dtPrevSaida);
		entityNew.setField(SklFieldName.CODREG, codReg);
		entityNew.setField(SklFieldName.CODPARCTRANSP, codParcTransp);
		entityNew.setField(SklFieldName.TIPCARGA, tipCarga);
		entityNew.setField(SklFieldName.ENTSAI, entSai);
		entityNew.setField(SklFieldName.AD_ORDEMCARGA, ordemCargaPai);
		entityNew.setField(SklFieldName.CODVEICULO, codVeiculo);
		entityNew.setField(SklFieldName.ROTEIRO, roteiro);
		entityNew.setField(SklFieldName.AD_TIPEMBALAGEM, tipoEmbalagens);
		entityNew.setField(SklFieldName.AD_STATUSCARGA, statusCarga);
		entityNew.setField(SklFieldName.SITUACAO, situacao);
		entityNew.setField(SklFieldName.HORASAIDA, horaSaida);

		DynamicVO vo = entityNew.fluidInsert();
		this.ordemCarga = vo.asBigDecimal(SklFieldName.ORDEMCARGA);

		return null;
	}

	@Override
	public OrdemCarga clone() throws CloneNotSupportedException {
		return (OrdemCarga) super.clone();
	}

	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = dao.findByPK(keyValue);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.ordemCargaPai = vo.asBigDecimal(SklFieldName.AD_ORDEMCARGA);
		this.ordemCarga = vo.asBigDecimal(SklFieldName.ORDEMCARGA);
		this.entSai = vo.asString(SklFieldName.ENTSAI);
		this.dtInic = vo.asTimestamp(SklFieldName.DTINIC);
		this.dtPrevSaida = vo.asTimestamp(SklFieldName.DTPREVSAIDA);
		this.codReg = vo.asBigDecimal(SklFieldName.CODREG);
		this.codParcTransp = vo.asBigDecimal(SklFieldName.CODPARCTRANSP);
		this.tipCarga = vo.asString(SklFieldName.TIPCARGA);
		this.codVeiculo = vo.asBigDecimal(SklFieldName.CODVEICULO);
		this.statusCarga = vo.asString(SklFieldName.AD_STATUSCARGA);
		this.tipoEmbalagens = vo.asString(SklFieldName.AD_TIPEMBALAGEM);
		this.roteiro = vo.asString(SklFieldName.ROTEIRO);
		this.situacao = vo.asString(SklFieldName.SITUACAO);
		this.horaSaida = vo.asTimestamp(SklFieldName.HORASAIDA);
	}

	@Override
	public String toString() {
		return String.format(
				"OrdemCarga [codEmp=%s, ordemCargaPai=%s, ordemCarga=%s, entSai=%s, dtInic=%s, dtPrevSaida=%s, horaSaida=%s, codReg=%s, codParcTransp=%s, tipCarga=%s, codVeiculo=%s, statusCarga=%s, situacao=%s, tipoEmbalagens=%s, roteiro=%s]",
				codEmp, ordemCargaPai, ordemCarga, entSai, dtInic, dtPrevSaida, horaSaida, codReg, codParcTransp,
				tipCarga, codVeiculo, statusCarga, situacao, tipoEmbalagens, roteiro);
	}
}
