package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class DadosPedido implements EntityBean, LoadableByResultSet {

	private BigDecimal 								nuNota;
	private String 									cgc;
	private BigDecimal 								qtdNeg;
	private BigDecimal 								pesoBruto;
	private String 									dtPrevSaida;
	private String 									cgcCpf;
	private String 									razaoSocial;
	private String 									logradouro;
	private String 									cep;
	private String 									nomeBai;
	private String 									nomeCid;
	private String 									uf;
	private BigDecimal 								latitude;
	private BigDecimal 								longitude;
	private String 									dtPrevEnt;
	private BigDecimal 								vlrNota;
	private String 									observacao;

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public String getCgc() {
		return cgc;
	}

	public void setCgc(String cgc) {
		this.cgc = cgc;
	}

	public BigDecimal getQtdNeg() {
		return qtdNeg;
	}

	public void setQtdNeg(BigDecimal qtdNeg) {
		this.qtdNeg = qtdNeg;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public String getDtPrevSaida() {
		return dtPrevSaida;
	}

	public void setDtPrevSaida(String dtPrevSaida) {
		this.dtPrevSaida = dtPrevSaida;
	}

	public String getCgcCpf() {
		return cgcCpf;
	}

	public void setCgcCpf(String cgcCpf) {
		this.cgcCpf = cgcCpf;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNomeBai() {
		return nomeBai;
	}

	public void setNomeBai(String nomeBai) {
		this.nomeBai = nomeBai;
	}

	public String getNomeCid() {
		return nomeCid;
	}

	public void setNomeCid(String nomeCid) {
		this.nomeCid = nomeCid;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getDtPrevEnt() {
		return dtPrevEnt;
	}

	public void setDtPrevEnt(String dtPrevEnt) {
		this.dtPrevEnt = dtPrevEnt;
	}

	public BigDecimal getVlrNota() {
		return vlrNota;
	}

	public void setVlrNota(BigDecimal vlrNota) {
		this.vlrNota = vlrNota;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			this.nuNota = rs.getBigDecimal("NUNOTA");
			this.cgc = rs.getString("CGC");
			this.qtdNeg = rs.getBigDecimal("QTD");
			this.pesoBruto = rs.getBigDecimal("PESOBRUTO");
			this.dtPrevSaida = rs.getString("DTPREVSAIDA");
			this.cgcCpf = rs.getString("CGC_CPF");
			this.razaoSocial = rs.getString("RAZAOSOCIAL");
			this.logradouro = rs.getString("LOGRADOURO");
			this.cep = rs.getString("CEP");
			this.nomeBai = rs.getString("NOMEBAI");
			this.nomeCid = rs.getString("NOMECID");
			this.uf = rs.getString("UF");
			this.latitude = rs.getBigDecimal("LATITUDE");
			this.longitude = rs.getBigDecimal("LONGITUDE");
			this.dtPrevEnt = rs.getString("DTPREVENT");
			this.vlrNota = rs.getBigDecimal("VLRNOTA");
			this.observacao = rs.getString("OBSERVACAO");
		} catch (SQLException e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
	}

}
