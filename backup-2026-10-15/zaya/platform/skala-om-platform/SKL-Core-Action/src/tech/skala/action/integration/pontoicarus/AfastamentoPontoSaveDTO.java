package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import tech.skala.core.commons.dd.DTO;

public class AfastamentoPontoSaveDTO implements DTO {
	private String								anexo;
	private String								antecipar13;
	private String								contentTypeAnexo;
	private Timestamp							diaFim;
	private Timestamp							diaInicio;
	private BigDecimal							dias;
	private BigDecimal							diasAbono;
	private String								fileName;
	private BigDecimal							idAfastamentoPonto;
	private String								justificativa;
	private MotivoAbonoPontoIdDTO				motivoAfastamento;
	private MutuarioIdDTO						mutuario;
	private String								tipoAfastamento;
	
	public String getAnexo() {
		return anexo;
	}
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
	public String getAntecipar13() {
		return antecipar13;
	}
	public void setAntecipar13(String antecipar13) {
		this.antecipar13 = antecipar13;
	}
	public String getContentTypeAnexo() {
		return contentTypeAnexo;
	}
	public void setContentTypeAnexo(String contentTypeAnexo) {
		this.contentTypeAnexo = contentTypeAnexo;
	}
	public Timestamp getDiaFim() {
		return diaFim;
	}
	public void setDiaFim(Timestamp diaFim) {
		this.diaFim = diaFim;
	}
	public Timestamp getDiaInicio() {
		return diaInicio;
	}
	public void setDiaInicio(Timestamp diaInicio) {
		this.diaInicio = diaInicio;
	}
	public BigDecimal getDias() {
		return dias;
	}
	public void setDias(BigDecimal dias) {
		this.dias = dias;
	}
	public BigDecimal getDiasAbono() {
		return diasAbono;
	}
	public void setDiasAbono(BigDecimal diasAbono) {
		this.diasAbono = diasAbono;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public BigDecimal getIdAfastamentoPonto() {
		return idAfastamentoPonto;
	}
	public void setIdAfastamentoPonto(BigDecimal idAfastamentoPonto) {
		this.idAfastamentoPonto = idAfastamentoPonto;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public MotivoAbonoPontoIdDTO getMotivoAfastamento() {
		return motivoAfastamento;
	}
	public void setMotivoAfastamento(MotivoAbonoPontoIdDTO motivoAfastamento) {
		this.motivoAfastamento = motivoAfastamento;
	}
	public MutuarioIdDTO getMutuario() {
		return mutuario;
	}
	public void setMutuario(MutuarioIdDTO mutuario) {
		this.mutuario = mutuario;
	}
	public String getTipoAfastamento() {
		return tipoAfastamento;
	}
	public void setTipoAfastamento(String tipoAfastamento) {
		this.tipoAfastamento = tipoAfastamento;
	}
	
	@Override
	public String toString() {
		return String.format(
				"AfastamentoPontoSaveDTO [anexo=%s, antecipar13=%s, contentTypeAnexo=%s, diaFim=%s, diaInicio=%s, dias=%s, diasAbono=%s, fileName=%s, idAfastamentoPonto=%s, justificativa=%s, motivoAfastamento=%s, mutuario=%s, tipoAfastamento=%s]",
				anexo, antecipar13, contentTypeAnexo, diaFim, diaInicio, dias, diasAbono, fileName, idAfastamentoPonto,
				justificativa, motivoAfastamento, mutuario, tipoAfastamento);
	}
}
