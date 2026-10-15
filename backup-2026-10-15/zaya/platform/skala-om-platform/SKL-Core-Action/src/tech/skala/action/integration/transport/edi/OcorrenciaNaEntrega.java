package tech.skala.action.integration.transport.edi;

import java.math.BigDecimal;

import com.sankhya.util.StringUtils;

import tech.skala.core.beans.interfaces.EntityBean;

public class OcorrenciaNaEntrega implements EntityBean {

	private BigDecimal								identificador;
	private String									cgcEmpresaEmissora;
	private BigDecimal								serieNF;
	private BigDecimal								numeroNF;
	private BigDecimal								codigoOcorrencia;
	private String									dataOcorrencia;
	private String									horaOcorrencia;
	private String									codigoObservacao;
	private String									textoComplementar;
	
	public BigDecimal getIdentificador() {
		return identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
		this.identificador = identificador;
	}

	public String getCgcEmpresaEmissora() {
		return cgcEmpresaEmissora;
	}

	public void setCgcEmpresaEmissora(String cgcEmpresaEmissora) {
		this.cgcEmpresaEmissora = cgcEmpresaEmissora;
	}

	public BigDecimal getSerieNF() {
		return serieNF;
	}

	public void setSerieNF(BigDecimal serieNF) {
		this.serieNF = serieNF;
	}

	public BigDecimal getNumeroNF() {
		return numeroNF;
	}

	public void setNumeroNF(BigDecimal numeroNF) {
		this.numeroNF = numeroNF;
	}

	public BigDecimal getCodigoOcorrencia() {
		return codigoOcorrencia;
	}

	public void setCodigoOcorrencia(BigDecimal codigoOcorrencia) {
		this.codigoOcorrencia = codigoOcorrencia;
	}

	public String getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(String dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public String getHoraOcorrencia() {
		return horaOcorrencia;
	}

	public void setHoraOcorrencia(String horaOcorrencia) {
		this.horaOcorrencia = horaOcorrencia;
	}

	public String getCodigoObservacao() {
		return codigoObservacao;
	}

	public void setCodigoObservacao(String codigoObservacao) {
		this.codigoObservacao = codigoObservacao;
	}

	public String getTextoComplementar() {
		return textoComplementar;
	}

	public void setTextoComplementar(String textoComplementar) {
		this.textoComplementar = textoComplementar;
	}

	public void loadByEDI (String linha) {
		identificador = new BigDecimal(linha.substring(0, 3));
		cgcEmpresaEmissora = linha.substring(3, 17);
		serieNF = StringUtils.convertToBigDecimal(linha.substring(17, 20));
		numeroNF = new BigDecimal(linha.substring(20, 28));
		codigoOcorrencia = new BigDecimal(linha.substring(28, 30));
		dataOcorrencia = linha.substring(30, 38);
		horaOcorrencia = linha.substring(38, 42);
		codigoObservacao = linha.substring(42, 44);
		textoComplementar = linha.substring(44, 114);
	}
	
	@Override
	public String toString() {
		return String.format(
				"OcorrenciaNaEntrega [identificador=%s, cgcEmpresaEmissora=%s, serieNF=%s, numeroNF=%s, codigoOcorrencia=%s, dataOcorrencia=%s, horaOcorrencia=%s, codigoObservacao=%s, textoComplementar=%s]",
				identificador, cgcEmpresaEmissora, serieNF, numeroNF, codigoOcorrencia, dataOcorrencia, horaOcorrencia,
				codigoObservacao, textoComplementar);
	}
	
}
