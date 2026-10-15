package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import tech.skala.core.commons.dd.DTO;

public class AbonoPontoSaveDTO implements DTO {
	private String								anexo;
	private String								contentTypeAnexo;
	private Timestamp							dataSolicitacao;
	private String								diaTodo;
	private String								fileName;
	private Timestamp							horaFim;
	private Timestamp							horaFimDateTime;
	private Timestamp							horaInicio;
	private Timestamp							horaInicioDateTime;
	private BigDecimal							idAbonoPonto;
	private String								justificativa;
	private MotivoAbonoPontoIdDTO				motivoAbono;
	private MutuarioIdDTO						mutuario;
	private String								tempoAbono;
	private String								tipoPeriodoAbono;
	
	public AbonoPontoSaveDTO() {
		this.tipoPeriodoAbono = "HORAS";
	}
	public String getAnexo() {
		return anexo;
	}
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
	public String getContentTypeAnexo() {
		return contentTypeAnexo;
	}
	public void setContentTypeAnexo(String contentTypeAnexo) {
		this.contentTypeAnexo = contentTypeAnexo;
	}
	public Timestamp getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Timestamp dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	public String getDiaTodo() {
		return diaTodo;
	}
	public void setDiaTodo(String diaTodo) {
		this.diaTodo = diaTodo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Timestamp getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(Timestamp horaFim) {
		this.horaFim = horaFim;
	}
	public Timestamp getHoraFimDateTime() {
		return horaFimDateTime;
	}
	public void setHoraFimDateTime(Timestamp horaFimDateTime) {
		this.horaFimDateTime = horaFimDateTime;
	}
	public Timestamp getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Timestamp horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Timestamp getHoraInicioDateTime() {
		return horaInicioDateTime;
	}
	public void setHoraInicioDateTime(Timestamp horaInicioDateTime) {
		this.horaInicioDateTime = horaInicioDateTime;
	}
	public BigDecimal getIdAbonoPonto() {
		return idAbonoPonto;
	}
	public void setIdAbonoPonto(BigDecimal idAbonoPonto) {
		this.idAbonoPonto = idAbonoPonto;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public MotivoAbonoPontoIdDTO getMotivoAbono() {
		return motivoAbono;
	}
	public void setMotivoAbono(MotivoAbonoPontoIdDTO motivoAbono) {
		this.motivoAbono = motivoAbono;
	}
	public MutuarioIdDTO getMutuario() {
		return mutuario;
	}
	public void setMutuario(MutuarioIdDTO mutuario) {
		this.mutuario = mutuario;
	}
	public String getTempoAbono() {
		return tempoAbono;
	}
	public void setTempoAbono(String tempoAbono) {
		this.tempoAbono = tempoAbono;
	}
	public String getTipoPeriodoAbono() {
		return tipoPeriodoAbono;
	}
	public void setTipoPeriodoAbono(String tipoPeriodoAbono) {
		this.tipoPeriodoAbono = tipoPeriodoAbono;
	}
	
	@Override
	public String toString() {
		return String.format(
				"AbonoPontoSaveDTO [anexo=%s, contentTypeAnexo=%s, dataSolicitacao=%s, diaTodo=%s, fileName=%s, horaFim=%s, horaFimDateTime=%s, horaInicio=%s, horaInicioDateTime=%s, idAbonoPonto=%s, justificativa=%s, motivoAbono=%s, mutuario=%s, tempoAbono=%s, tipoPeriodoAbono=%s]",
				anexo, contentTypeAnexo, dataSolicitacao, diaTodo, fileName, horaFim, horaFimDateTime, horaInicio,
				horaInicioDateTime, idAbonoPonto, justificativa, motivoAbono, mutuario, tempoAbono, tipoPeriodoAbono);
	}

}
