package tech.skala.action.beans.cargahoraria;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class IdentificacaoCargaHoraria implements EntityBean, LoadableByValueObject {
	public static final String					ENTITY_NAME = "IdentificacaoCargaHoraria";
	
	private BigDecimal							codCargaHor;
	private String								descrCargaHor;
	private String								tipCargaHor;
	private BigDecimal							tpJornada;
	private boolean								escalonar;
	private boolean								ativo;
	private BigDecimal							diasTrab;
	private BigDecimal							diasFolga;
	private BigDecimal							qtdTurno;
	
	public BigDecimal getCodCargaHor() {
		return codCargaHor;
	}
	public void setCodCargaHor(BigDecimal codCargaHor) {
		this.codCargaHor = codCargaHor;
	}
	public String getDescrCargaHor() {
		return descrCargaHor;
	}
	public void setDescrCargaHor(String descrCargaHor) {
		this.descrCargaHor = descrCargaHor;
	}
	public String getTipCargaHor() {
		return tipCargaHor;
	}
	public void setTipCargaHor(String tipCargaHor) {
		this.tipCargaHor = tipCargaHor;
	}
	public BigDecimal getTpJornada() {
		return tpJornada;
	}
	public void setTpJornada(BigDecimal tpJornada) {
		this.tpJornada = tpJornada;
	}
	public boolean isEscalonar() {
		return escalonar;
	}
	public void setEscalonar(boolean escalonar) {
		this.escalonar = escalonar;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public BigDecimal getDiasTrab() {
		return diasTrab;
	}
	public void setDiasTrab(BigDecimal diasTrab) {
		this.diasTrab = diasTrab;
	}
	public BigDecimal getDiasFolga() {
		return diasFolga;
	}
	public void setDiasFolga(BigDecimal diasFolga) {
		this.diasFolga = diasFolga;
	}
	public BigDecimal getQtdTurno() {
		return qtdTurno;
	}
	public void setQtdTurno(BigDecimal qtdTurno) {
		this.qtdTurno = qtdTurno;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codCargaHor = vo.asBigDecimal("CODCARGAHOR");
		this.descrCargaHor = ConversorUtils.semAcento(vo.asString("DESCRCARGAHOR"));
		this.tipCargaHor = vo.asString("TIPCARGAHOR");
		this.tpJornada = vo.asBigDecimal("TPJORNADA");
		this.escalonar = vo.asBoolean("ESCALONAR");
		this.ativo = vo.asBoolean("ATIVO");
		this.diasTrab = vo.asBigDecimal("DIASTRAB");
		this.diasFolga = vo.asBigDecimal("DIASFOLGA");
		this.qtdTurno = vo.asBigDecimal("QTDTURNO");
	}
	
}









