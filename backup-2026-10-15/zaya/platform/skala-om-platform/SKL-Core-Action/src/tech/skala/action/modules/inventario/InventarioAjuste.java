package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.logging.Logger;

import com.sankhya.util.BigDecimalUtil;

import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.commons.exceptions.SkalaModelException;



public class InventarioAjuste implements EntityBean {
	private BigDecimal						nuInventario;
	private Timestamp						dtContagem;
	private BigDecimal						codEmp;
	private BigDecimal						codLocal;
	private BigDecimal						codProd;
	private String							controle;
	private String							codVol;
	private BigDecimal						codParc;
	private String							tipo;
	private BigDecimal						qtdAjuste;
	private BigDecimal						qtdAjusteUnCad;
	private InventarioContagem				copia;
	private InventarioContagem				contagemFinal;
	private BigDecimal						sequenciaFinal;
	private boolean							geraAjuste;
	private String							tipoAjuste;
	public BigDecimal getNuInventario() {
		return nuInventario;
	}
	public void setNuInventario(BigDecimal nuInventario) {
		this.nuInventario = nuInventario;
	}
	public Timestamp getDtContagem() {
		return dtContagem;
	}
	public void setDtContagem(Timestamp dtContagem) {
		this.dtContagem = dtContagem;
	}
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getCodLocal() {
		return codLocal;
	}
	public void setCodLocal(BigDecimal codLocal) {
		this.codLocal = codLocal;
	}
	public BigDecimal getCodProd() {
		return codProd;
	}
	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}
	public String getControle() {
		return controle;
	}
	public void setControle(String controle) {
		this.controle = controle;
	}
	public String getCodVol() {
		return codVol;
	}
	public void setCodVol(String codVol) {
		this.codVol = codVol;
	}
	public BigDecimal getCodParc() {
		return codParc;
	}
	public void setCodParc(BigDecimal codParc) {
		this.codParc = codParc;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getQtdAjuste() {
		return qtdAjuste;
	}
	public void setQtdAjuste(BigDecimal qtdAjuste) {
		this.qtdAjuste = qtdAjuste;
	}
	public BigDecimal getQtdAjusteUnCad() {
		return qtdAjusteUnCad;
	}
	public void setQtdAjusteUnCad(BigDecimal qtdAjusteUnCad) {
		this.qtdAjusteUnCad = qtdAjusteUnCad;
	}
	public InventarioContagem getCopia() {
		return copia;
	}
	public void setCopia(InventarioContagem copia) {
		this.copia = copia;
	}
	public InventarioContagem getContagemFinal() {
		return contagemFinal;
	}
	public void setContagemFinal(InventarioContagem contagemFinal) {
		this.contagemFinal = contagemFinal;
	}
	public BigDecimal getSequenciaFinal() {
		return sequenciaFinal;
	}
	public void setSequenciaFinal(BigDecimal sequenciaFinal) {
		this.sequenciaFinal = sequenciaFinal;
	}
	public boolean isGeraAjuste() {
		return geraAjuste;
	}
	public void setGeraAjuste(boolean geraAjuste) {
		this.geraAjuste = geraAjuste;
	}
	public String getTipoAjuste() {
		return tipoAjuste;
	}
	public void setTipoAjuste(String tipoAjuste) {
		this.tipoAjuste = tipoAjuste;
	}
	public void setFromContagem(InventarioContagem contagem) {
		this.nuInventario = contagem.getNuInventario();
		this.dtContagem = contagem.getDtContagem();
		this.codEmp = contagem.getCodEmp();
		this.codLocal = contagem.getCodLocal();
		this.codProd = contagem.getCodProd();
		this.controle = contagem.getControle();
		this.codVol = contagem.getCodVol();
		this.codParc = contagem.getCodParc();
		this.tipo = contagem.getTipo();
		
		if (sequenciaFinal == null) {
			this.sequenciaFinal = BigDecimal.ZERO;
			this.geraAjuste = false;
		}
	}
	public void addContagem(InventarioContagem contagem) throws SkalaModelException {
		//final Logger								logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	//	String msg;

		if (contagem.getSequencia().longValue() == 1L) {
			this.copia = contagem;
			
			if (this.contagemFinal == null) {
				this.qtdAjuste = BigDecimal.ZERO;
				this.qtdAjusteUnCad = BigDecimal.ZERO;
				this.geraAjuste = false;
				this.tipoAjuste = InventarioAjusteTipoAjusteEnum.SEM_AJUSTE.getValue();
				this.sequenciaFinal = contagem.getSequencia();
			} else {
				calcularAjuste();
			}

		} else {
			if (contagem.getSequencia().compareTo(sequenciaFinal) > 0) {
				this.contagemFinal = contagem;
				this.sequenciaFinal = contagem.getSequencia();

				if (copia != null)
					calcularAjuste();
			}
			
		}
	}

	private void calcularAjuste() {
		this.qtdAjuste = contagemFinal.getQtdEst().subtract(copia.getQtdEst(), BigDecimalUtil.MATH_CTX);
		this.qtdAjusteUnCad = contagemFinal.getQtdEstUnCad().subtract(copia.getQtdEstUnCad(), BigDecimalUtil.MATH_CTX);
		
		this.geraAjuste = qtdAjusteUnCad.compareTo(BigDecimal.ZERO) != 0;
		if (geraAjuste)
			this.tipoAjuste = (qtdAjusteUnCad.compareTo(BigDecimal.ZERO) > 0)
					? InventarioAjusteTipoAjusteEnum.ENTRADA.getValue()
					: InventarioAjusteTipoAjusteEnum.SAIDA.getValue();
	}
	@Override
	public int hashCode() {
		return Objects.hash(codEmp, codLocal, codParc, codProd, codVol, controle, dtContagem, nuInventario, tipo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof InventarioAjuste))
			return false;
		InventarioAjuste other = (InventarioAjuste) obj;
		return Objects.equals(codEmp, other.codEmp) && Objects.equals(codLocal, other.codLocal)
				&& Objects.equals(codParc, other.codParc) && Objects.equals(codProd, other.codProd)
				&& Objects.equals(codVol, other.codVol) && Objects.equals(controle, other.controle)
				&& Objects.equals(dtContagem, other.dtContagem) && Objects.equals(nuInventario, other.nuInventario)
				&& Objects.equals(tipo, other.tipo);
	}
	@Override
	public String toString() {
		return String.format(
				"InventarioAjuste [nuInventario=%s, dtContagem=%s, codEmp=%s, codLocal=%s, codProd=%s, controle=%s, codVol=%s, codParc=%s, tipo=%s, qtdAjuste=%s, qtdAjusteUnCad=%s, copia=%s, contagemFinal=%s, sequenciaFinal=%s, geraAjuste=%s, tipoAjuste=%s]",
				nuInventario, dtContagem, codEmp, codLocal, codProd, controle, codVol, codParc, tipo, qtdAjuste,
				qtdAjusteUnCad, copia, contagemFinal, sequenciaFinal, geraAjuste, tipoAjuste);
	}
	
	
	
}
