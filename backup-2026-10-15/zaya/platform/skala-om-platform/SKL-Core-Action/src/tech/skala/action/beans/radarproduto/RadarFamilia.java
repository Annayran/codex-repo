package tech.skala.action.beans.radarproduto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.sankhya.util.BigDecimalUtil;

import tech.skala.action.beans.meta.MetaPorFamilia;
import tech.skala.action.beans.meta.MetaPorProduto;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class RadarFamilia implements EntityBean{
	private BigDecimal						codEmp;
	private Timestamp 						dtRef;
	private BigDecimal						codFam;
	private BigDecimal						qtdMedia;
	private BigDecimal						qtdPendente;
	private List<RadarProduto>				radarProdutos;

	public RadarFamilia(BigDecimal codEmp, Timestamp dtRef, BigDecimal codFam) {
		this.codEmp = codEmp;
		this.dtRef = dtRef;
		this.codFam = codFam;
		this.qtdMedia = BigDecimal.ZERO;
		this.qtdPendente = BigDecimal.ZERO;
		this.radarProdutos = new LinkedList<>();

	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public Timestamp getDtRef() {
		return dtRef;
	}

	public void setDtRef(Timestamp dtRef) {
		this.dtRef = dtRef;
	}

	public BigDecimal getCodFam() {
		return codFam;
	}

	public void setCodFam(BigDecimal codFam) {
		this.codFam = codFam;
	}

	public BigDecimal getQtdMedia() {
		return qtdMedia;
	}

	public void setQtdMedia(BigDecimal qtdMedia) {
		this.qtdMedia = qtdMedia;
	}

	public BigDecimal getQtdPendente() {
		return qtdPendente;
	}

	public void setQtdPendente(BigDecimal qtdPendente) {
		this.qtdPendente = qtdPendente;
	}

	public List<RadarProduto> getRadarProduto() {
		return radarProdutos;
	}

	public void setRadarProduto(List<RadarProduto> radarProduto) {
		this.radarProdutos = radarProduto;
	}

	public void loadByMeta(MetaPorFamilia metaPorFamilia) throws SkalaModelException {
		this.qtdMedia = metaPorFamilia.getQtdMedia();
		this.qtdPendente = metaPorFamilia.getQtdPendente();
		
		RadarProduto radarProduto;
		for (MetaPorProduto metaPorProduto : metaPorFamilia.getProdutos()) {
			radarProduto = new RadarProduto (metaPorFamilia.getDtRef(), BigDecimal.ZERO, metaPorProduto.getProduto());
			radarProduto.loadByMeta(metaPorProduto);
			
			this.radarProdutos.add(radarProduto);
		}
	}

	public void addRadarProduto(RadarProduto radarProduto) throws SkalaModelException {
		if (radarProdutos.isEmpty()) {
			this.codFam = radarProduto.getProduto().getFamilia().getCodFam();
			this.codEmp = BigDecimalUtil.valueOf(2);
		}
		this.qtdPendente = this.qtdPendente.add(BigDecimalUtil.getValueOrZero(radarProduto.getQtdPendAtu()));
		this.qtdMedia = this.qtdMedia.add(radarProduto.getQtdMedia());
		this.radarProdutos.add(radarProduto);
		

	}

	@Override
	public int hashCode() {
		return Objects.hash(codEmp, codFam, dtRef);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RadarFamilia))
			return false;
		RadarFamilia other = (RadarFamilia) obj;
		return Objects.equals(codEmp, other.codEmp) && Objects.equals(codFam, other.codFam)
				&& Objects.equals(dtRef, other.dtRef);
	}

	@Override
	public String toString() {
		return String.format(
				"RadarFamilia [codEmp=%s, dtRef=%s, codFam=%s, qtdMedia=%s, qtdPendente=%s, radarProduto=%s]", codEmp,
				dtRef, codFam, qtdMedia, qtdPendente, radarProdutos);
	}

}
