package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;


public class MapaProcessamentoBeneficio implements EntityBean, LoadableByValueObject, Persistable, LoadableByResultSet {
	
	private BigDecimal 						nuOcor;
	private BigDecimal 						codEmp;
	private BigDecimal 						codFunc;
	private String 						    nomeFunc;
	private Timestamp 						dtRef;
	private String 						    diaRef;
	private String 						    mesRef;
	private String 						    anoRef;
	private BigDecimal 						codBen;
	private BigDecimal 						vlrBen;
	private BigDecimal 						vlrCre;
	private BigDecimal 						vlrDeb;
	private BigDecimal 						indCre;
	private BigDecimal 						indDeb;
	private BigDecimal 						diasTrab;
	private BigDecimal 						faltas;
	private BigDecimal 						atestados;
	private String 						    observacao;
	private BigDecimal 						indBen;
	private BigDecimal						codCargaHor;
	
	public BigDecimal getNuOcor() {
		return nuOcor;
	}

	public void setNuOcor(BigDecimal nuOcor) {
		this.nuOcor = nuOcor;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodFunc() {
		return codFunc;
	}

	public void setCodFunc(BigDecimal codFunc) {
		this.codFunc = codFunc;
	}

	public Timestamp getDtRef() {
		return dtRef;
	}

	public void setDtRef(Timestamp dtRef) {
		this.dtRef = dtRef;
	}

	public String getDiaRef() {
		return diaRef;
	}

	public void setDiaRef(String diaRef) {
		this.diaRef = diaRef;
	}
	
	public String getMesRef() {
		return mesRef;
	}

	public void setMesRef(String mesRef) {
		this.mesRef = mesRef;
	}

	public String getAnoRef() {
		return anoRef;
	}

	public void setAnoRef(String anoRef) {
		this.anoRef = anoRef;
	}

	public BigDecimal getCodBen() {
		return codBen;
	}

	public void setCodBen(BigDecimal codBen) {
		this.codBen = codBen;
	}

	public BigDecimal getVlrBen() {
		return vlrBen;
	}

	public void setVlrBen(BigDecimal vlrBen) {
		this.vlrBen = vlrBen;
	}

	public BigDecimal getVlrCre() {
		return vlrCre;
	}

	public void setVlrCre(BigDecimal vlrCre) {
		this.vlrCre = vlrCre;
	}

	public BigDecimal getVlrDeb() {
		return vlrDeb;
	}

	public void setVlrDeb(BigDecimal vlrDeb) {
		this.vlrDeb = vlrDeb;
	}

	public BigDecimal getIndCre() {
		return indCre;
	}

	public void setIndCre(BigDecimal indCre) {
		this.indCre = indCre;
	}

	public BigDecimal getIndDeb() {
		return indDeb;
	}

	public void setIndDeb(BigDecimal indDeb) {
		this.indDeb = indDeb;
	}

	public BigDecimal getDiasTrab() {
		return diasTrab;
	}

	public void setDiasTrab(BigDecimal diasTrab) {
		this.diasTrab = diasTrab;
	}

	public BigDecimal getFaltas() {
		return faltas;
	}

	public void setFaltas(BigDecimal faltas) {
		this.faltas = faltas;
	}

	public BigDecimal getAtestados() {
		return atestados;
	}

	public void setAtestados(BigDecimal atestados) {
		this.atestados = atestados;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getIndBen() {
		return indBen;
	}

	public void setIndBen(BigDecimal indBen) {
		this.indBen = indBen;
	}

	public BigDecimal getCodCargaHor() {
		return codCargaHor;
	}

	public void setCodCargaHor(BigDecimal codCargaHor) {
		this.codCargaHor = codCargaHor;
	}
	
	public void setNomeFunc(String nomeFunc) {
		this.nomeFunc = nomeFunc;
	}


	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuOcor = vo.asBigDecimal("NUOCOR");
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.codFunc = vo.asBigDecimal("CODFUNC");
		this.nomeFunc = vo.asString("NOMEFUNC");
		this.dtRef = vo.asTimestamp("DTREF");
		this.diaRef = vo.asString("DIAREF");
		this.mesRef = vo.asString("MESREF");
		this.anoRef = vo.asString("ANOREF");
		this.codBen = vo.asBigDecimal("CODBEN");
		this.vlrBen = vo.asBigDecimal("VLRBEN");
		this.vlrCre = vo.asBigDecimal("VLRCRE");
		this.vlrDeb = vo.asBigDecimal("VLRDEB");
		this.indCre = vo.asBigDecimal("INDCRE");
		this.indDeb = vo.asBigDecimal("INDDEB");
		this.diasTrab = vo.asBigDecimal("DIASTRAB");
		this.faltas = vo.asBigDecimal("FALTAS");
		this.atestados = vo.asBigDecimal("ATESTADOS");
		this.observacao = vo.asString("OBSERVACAO");
		this.indBen = vo.asBigDecimal("INDBEN");
		this.codCargaHor = vo.asBigDecimal("CODCARGAHOR");

		
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper mapaBeneficioDAO = JapeFactory.dao("AD_PLTMAPAPREBEN");

		DynamicVO vo;
		try {
			vo = mapaBeneficioDAO.findOne("this.CODEMP = ? AND this.CODFUNC = ? AND this.DTREF = ? and this.CODBEN = ?",
					codEmp, codFunc, dtRef, codBen);

			if (vo == null) {

				EntityInsert insert = new EntityInsert("AD_PLTMAPAPREBEN");

				insert.setField(SklFieldName.CODEMP, codEmp);
				insert.setField("CODFUNC", codFunc);
				insert.setField("NOMEFUNC", nomeFunc);
				insert.setField("DTREF", dtRef);
				insert.setField("DIAREF", diaRef);
				insert.setField("MESREF", mesRef);
				insert.setField("ANOREF", anoRef);
				insert.setField("CODBEN", codBen);
				insert.setField("VLRBEN", vlrBen);
				insert.setField("VLRCRE", vlrCre);
				insert.setField("VLRDEB", vlrDeb);
				insert.setField("INDCRE", indCre);
				insert.setField("INDDEB", indDeb);
				insert.setField("DIASTRAB", diasTrab);
				insert.setField("FALTAS", faltas);
				insert.setField("ATESTADOS", atestados);
				insert.setField("OBSERVACAO", observacao);
				insert.setField("INDBEN", indBen);
				insert.setField("CODCARGAHOR", codCargaHor);

				DynamicVO saveVO = insert.fluidInsert();
				this.nuOcor = saveVO.asBigDecimal("NUOCOR");

			} else {
				EntityUpdate update = new EntityUpdate("AD_PLTMAPAPREBEN", vo);

				update.setField("NOMEFUNC", nomeFunc);
				update.setField("VLRBEN", vlrBen);
				update.setField("VLRCRE", vlrCre);
				update.setField("VLRDEB", vlrDeb);
				update.setField("INDCRE", indCre);
				update.setField("INDDEB", indDeb);
				update.setField("FALTAS", faltas);
				update.setField("ATESTADOS", atestados);
				update.setField("OBSERVACAO", observacao);
				update.setField("INDBEN", indBen);
				update.setField("CODCARGAHOR", codCargaHor);

				update.fluidUpdate();

				this.nuOcor = vo.asBigDecimal("NUOCOR");
			}
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
			
		}

		return null;
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		
		try {
			this.codEmp = rs.getBigDecimal("CODEMP");
			this.codFunc = rs.getBigDecimal("CODFUNC");
			this.nomeFunc = rs.getString("NOMEFUNC");
			this.dtRef = rs.getTimestamp("DTREF");
			this.diaRef = rs.getString("DIAREF");
			this.mesRef = rs.getString("MESREF");
			this.anoRef = rs.getString("ANOREF");
			this.codBen = rs.getBigDecimal("CODBEN");
			this.vlrBen = rs.getBigDecimal("VLRBEN");
			this.diasTrab = rs.getBigDecimal("QTDIASTRAB");
			this.faltas = rs.getBigDecimal("DIASFALTAS");
			this.atestados = rs.getBigDecimal("ATESTDIAS");
			this.indBen = rs.getBigDecimal("INDBEN");
			this.observacao = rs.getString("OBSERVACAO");
			this.codCargaHor = rs.getBigDecimal("CODCARGAHOR");

		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
		
		
	}

}

