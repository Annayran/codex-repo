package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByRecord;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class AZShipRemessa implements EntityBean, LoadableByRecord {
	
	private BigDecimal  							nuNota;
	private BigDecimal  							numNota;
	private BigDecimal  							codEmp;
	private String  								cnpjEmp;
	private BigDecimal  							qtdItens;
	private BigDecimal  							peso;
	private Timestamp  								dtColeta;
	private BigDecimal  							codParc;
	private String  								cgcCpf;
	private String  								razaoSocial;
	private String  								logradouro;
	private String  								cep;
	private BigDecimal  							codCid;
	private String  								nomeCid;
	private BigDecimal  							codBai;
	private String  								nomeBai;
	private BigDecimal  							codUf;
	private String  								uf;
	private BigDecimal  							latitude;
	private BigDecimal  							longitude;
	private String									exigCliente;
	private String  								descrExigCliente;
	private String									tipoEntrega;
	private String  								descrTipoEntrega;
	private String									tipoCarga;
	private String  								descrTipoCarga;
	private Timestamp  								dtEntrega;
	private Timestamp  								dhAgendamento;
	private String									observacao;
	private BigDecimal  							vlrTot;

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public String getDescrExigCliente() {
		return descrExigCliente;
	}

	public void setDescrExigCliente(String descrExigCliente) {
		this.descrExigCliente = descrExigCliente;
	}

	public String getDescrTipoEntrega() {
		return descrTipoEntrega;
	}

	public void setDescrTipoEntrega(String descrTipoEntrega) {
		this.descrTipoEntrega = descrTipoEntrega;
	}

	public String getDescrTipoCarga() {
		return descrTipoCarga;
	}

	public void setDescrTipoCarga(String descrTipoCarga) {
		this.descrTipoCarga = descrTipoCarga;
	}

	public void setNuNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getNumNota() {
		return numNota;
	}

	public void setNumNota(BigDecimal numNota) {
		this.numNota = numNota;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public String getCnpjEmp() {
		return cnpjEmp;
	}

	public void setCnpjEmp(String cnpjEmp) {
		this.cnpjEmp = cnpjEmp;
	}

	public BigDecimal getQtdItens() {
		return qtdItens;
	}

	public void setQtdItens(BigDecimal qtdItens) {
		this.qtdItens = qtdItens;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public Timestamp getDtColeta() {
		return dtColeta;
	}

	public void setDtColeta(Timestamp dtColeta) {
		this.dtColeta = dtColeta;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
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

	public BigDecimal getCodCid() {
		return codCid;
	}

	public void setCodCid(BigDecimal codCid) {
		this.codCid = codCid;
	}

	public String getNomeCid() {
		return nomeCid;
	}

	public void setNomeCid(String nomeCid) {
		this.nomeCid = nomeCid;
	}

	public BigDecimal getCodBai() {
		return codBai;
	}

	public void setCodBai(BigDecimal codBai) {
		this.codBai = codBai;
	}

	public String getNomeBai() {
		return nomeBai;
	}

	public void setNomeBai(String nomeBai) {
		this.nomeBai = nomeBai;
	}

	public BigDecimal getCodUf() {
		return codUf;
	}

	public void setCodUf(BigDecimal codUf) {
		this.codUf = codUf;
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

	public String getExigCliente() {
		return exigCliente;
	}

	public void setExigCliente(String exigCliente) {
		this.exigCliente = exigCliente;
	}

	public String getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(String tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public String getTipoCarga() {
		return tipoCarga;
	}

	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	public Timestamp getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(Timestamp dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getVlrTot() {
		return vlrTot;
	}

	public void setVlrTot(BigDecimal vlrTot) {
		this.vlrTot = vlrTot;
	}

	public Timestamp getDhAgendamento() {
		return dhAgendamento;
	}

	public void setDhAgendamento(Timestamp dhAgendamento) {
		this.dhAgendamento = dhAgendamento;
	}

	@Override
	public void loadByRecord(Registro linha) throws RecordNotFoundException {
		this.nuNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUNOTA));
		this.numNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUMNOTA));
		this.codEmp = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODEMP));
		this.cnpjEmp = (String) linha.getCampo("CNPJEMP");
		this.qtdItens = BigDecimalUtil.getBigDecimal(linha.getCampo("QTDITENS"));
		this.peso = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.PESO));
		this.dtColeta = (Timestamp) linha.getCampo("DTCOLETA");
		this.codParc = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODPARC));
		this.cgcCpf = (String) linha.getCampo(SklFieldName.CGC_CPF);
		this.razaoSocial = ConversorUtils.semAcento((String) linha.getCampo(SklFieldName.RAZAOSOCIAL));
		this.logradouro = ConversorUtils.semAcento((String) linha.getCampo("LOGRADOURO"));
		this.cep = (String) linha.getCampo("CEP");
		this.codCid = BigDecimalUtil.getBigDecimal(linha.getCampo("CODCID"));
		this.nomeCid = ConversorUtils.semAcento((String) linha.getCampo("NOMECID"));
		this.codBai = BigDecimalUtil.getBigDecimal(linha.getCampo("CODBAI"));
		this.nomeBai = ConversorUtils.semAcento((String) linha.getCampo("NOMEBAI"));
		this.codUf = BigDecimalUtil.getBigDecimal(linha.getCampo("CODUF"));
		this.uf = (String) linha.getCampo("UF");
		this.latitude = BigDecimalUtil.getBigDecimal(linha.getCampo("LATITUDE"));
		this.longitude = BigDecimalUtil.getBigDecimal(linha.getCampo("LONGITUDE"));
		this.exigCliente = (String) linha.getCampo("AD_EXIGCLIENTE");
		this.descrExigCliente = (String) linha.getCampo("DESCREXIGCLIENTE");
		this.tipoEntrega = (String) linha.getCampo("AD_TIPOENTREGA");
		this.descrTipoEntrega = (String) linha.getCampo("DESCRTIPOENTREGA");
		this.tipoCarga = (String) linha.getCampo("AD_TIPOCARGA");
		this.descrTipoCarga = (String) linha.getCampo("DESCRTIPOCARGA");
		this.observacao = (String) linha.getCampo(SklFieldName.OBSERVACAO);
		this.dtEntrega = (Timestamp) linha.getCampo("DTENTREGA");
		this.dhAgendamento = (Timestamp) linha.getCampo("DHAGENDAMENTO");
		this.vlrTot = BigDecimalUtil.getBigDecimal(linha.getCampo("VLRTOT"));
	}

	@Override
	public String toString() {
		return String.format(
				"AZShipRemessa [nuNota=%s, numNota=%s, codEmp=%s, cnpjEmp=%s, qtdItens=%s, peso=%s, dtColeta=%s, codParc=%s, cgcCpf=%s, razaoSocial=%s, logradouro=%s, cep=%s, codCid=%s, nomeCid=%s, codBai=%s, nomeBai=%s, codUf=%s, uf=%s, latitude=%s, longitude=%s, exigCliente=%s, descrExigCliente=%s, tipoEntrega=%s, descrTipoEntrega=%s, tipoCarga=%s, descrTipoCarga=%s, dtEntrega=%s, vlrTot=%s]",
				nuNota, numNota, codEmp, cnpjEmp, qtdItens, peso, dtColeta, codParc, cgcCpf, razaoSocial, logradouro,
				cep, codCid, nomeCid, codBai, nomeBai, codUf, uf, latitude, longitude, exigCliente, descrExigCliente,
				tipoEntrega, descrTipoEntrega, tipoCarga, descrTipoCarga, dtEntrega, vlrTot);
	}
}
