package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;

public class CabecalhoInstanciaProcessoStatus {
	private String					statusProc;
	private Timestamp				dhTermino;
	private BigDecimal				codUsuFinal;
	
	public CabecalhoInstanciaProcessoStatus() {
		
	}
	
	public CabecalhoInstanciaProcessoStatus(final boolean isReabrir) {
		statusProc = (isReabrir) ? CabecalhoInstanciaProcessoStatusEnum.CRIADO.getValue() : CabecalhoInstanciaProcessoStatusEnum.FINALIZADO.getValue();
		dhTermino = (isReabrir) ? null : TimeUtils.getNow();
		codUsuFinal = (isReabrir) ? null : AuthenticationInfo.getCurrent().getUserID();
	}

	public String getStatusProc() {
		return statusProc;
	}

	public void setStatusProc(String statusProc) {
		this.statusProc = statusProc;
	}

	public Timestamp getDhTermino() {
		return dhTermino;
	}

	public void setDhTermino(Timestamp dhTermino) {
		this.dhTermino = dhTermino;
	}

	public BigDecimal getCodUsuFinal() {
		return codUsuFinal;
	}

	public void setCodUsuFinal(BigDecimal codUsuFinal) {
		this.codUsuFinal = codUsuFinal;
	}

	@Override
	public String toString() {
		return String.format("CabecalhoInstanciaProcessoStatus [statusProc=%s, dhTermino=%s, codUsuFinal=%s]", statusProc,
				dhTermino, codUsuFinal);
	}
}
