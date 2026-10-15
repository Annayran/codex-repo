package tech.skala.action.modules.manufatura.processoprodutivo;

import java.math.BigDecimal;
import java.util.Objects;

import tech.skala.action.modules.manufatura.planta.PlantaManufatura;
import tech.skala.core.beans.interfaces.EntityBean;

/**
 * A classe {@code ProcessoProdutivo} representa a tabela {@code TPRPRC}.
 *  
 * <p>
 * A classe {@code ProcessoProdutivo} representa a entidade <i>ProcessoProdutivo</i> {@code TPRPRC} 
 * que representa os processos produtivos utilizadas na producao.
 * </p> 
 * 
 * @author cassiomenezes
 * @since 2022-09-28
 *
 */
public class ProcessoProdutivo implements EntityBean {

	private BigDecimal						codPrc;
	private String							descrAbrev;
	private BigDecimal						idProc;
	private BigDecimal						versao;
	private String							tipoProc;
	private boolean							ativo;
	private boolean							padrao;
	private boolean							usaTerceiro;
	private PlantaManufatura				planta;
	
	public BigDecimal getCodPrc() {
		return codPrc;
	}
	public void setCodPrc(BigDecimal codPrc) {
		this.codPrc = codPrc;
	}
	public String getDescrAbrev() {
		return descrAbrev;
	}
	public void setDescrAbrev(String descrAbrev) {
		this.descrAbrev = descrAbrev;
	}
	public BigDecimal getIdProc() {
		return idProc;
	}
	public void setIdProc(BigDecimal idProc) {
		this.idProc = idProc;
	}
	public BigDecimal getVersao() {
		return versao;
	}
	public void setVersao(BigDecimal versao) {
		this.versao = versao;
	}
	public String getTipoProc() {
		return tipoProc;
	}
	public void setTipoProc(String tipoProc) {
		this.tipoProc = tipoProc;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public boolean isPadrao() {
		return padrao;
	}
	public void setPadrao(boolean padrao) {
		this.padrao = padrao;
	}
	public boolean isUsaTerceiro() {
		return usaTerceiro;
	}
	public void setUsaTerceiro(boolean usaTerceiro) {
		this.usaTerceiro = usaTerceiro;
	}
	public PlantaManufatura getPlanta() {
		return planta;
	}
	public void setPlanta(PlantaManufatura planta) {
		this.planta = planta;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codPrc, descrAbrev, idProc, versao);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProcessoProdutivo))
			return false;
		ProcessoProdutivo other = (ProcessoProdutivo) obj;
		return Objects.equals(codPrc, other.codPrc) && Objects.equals(descrAbrev, other.descrAbrev)
				&& Objects.equals(idProc, other.idProc) && Objects.equals(versao, other.versao);
	}
	@Override
	public String toString() {
		return String.format(
				"ProcessoProdutivo [codPrc=%s, descrAbrev=%s, idProc=%s, versao=%s, tipoProc=%s, ativo=%s, padrao=%s, usaTerceiro=%s, planta=%s]",
				codPrc, descrAbrev, idProc, versao, tipoProc, ativo, padrao, usaTerceiro, planta);
	}
	
}
