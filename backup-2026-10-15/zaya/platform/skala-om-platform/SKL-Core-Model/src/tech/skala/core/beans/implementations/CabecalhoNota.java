package tech.skala.core.beans.implementations;

import java.math.BigDecimal;

import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.parceiro.Parceiro;

/**
 * A classe {@code CabecalhoNota} representa a tabela {@code TGFCAB}. 
 * 
 * <p>
 * Utilizar a classe sempre que for necessário utilizar informacoes de cabecalho 
 * de lancamento.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-06-29
 *
 */
public class CabecalhoNota implements EntityBean, LoadableByPrimaryKey {
	private BigDecimal						nuNota;
	private BigDecimal						numNota;
	private Parceiro						parceiro;
	private boolean							isConfirmada;
	
	public CabecalhoNota(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}

	public BigDecimal getNuNota() {
		return nuNota;
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

	public Parceiro getParceiro() {
		return parceiro;
	}

	public void setParceiro(Parceiro parceiro) {
		this.parceiro = parceiro;
	}

	public boolean isConfirmada() {
		return isConfirmada;
	}

	public void setConfirmada(boolean isConfirmada) {
		this.isConfirmada = isConfirmada;
	}

	@Override
	public void loadByPk(Object... keyValue) {
		// TODO Auto-generated method stub
		
	}

}
