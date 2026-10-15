package tech.skala.action.modules.inventario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


import tech.skala.core.commons.exceptions.SkalaModelException;



public class InventarioItem {
	private BigDecimal						nuInventario;
	private Timestamp						dtContagem;
	private BigDecimal						codProd;
	private List<InventarioContagem>		contagens = new LinkedList<>();
	private List<InventarioAjuste>			ajustes = new LinkedList<>();
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
	public BigDecimal getCodProd() {
		return codProd;
	}
	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}
	public List<InventarioContagem> getContagens() {
		return contagens;
	}
	public void setContagens(List<InventarioContagem> contagens) {
		this.contagens = contagens;
	}
	public List<InventarioAjuste> getAjustes() {
		return ajustes;
	}
	public void setAjustes(List<InventarioAjuste> ajustes) {
		this.ajustes = ajustes;
	}
	
	public void setFromContagem(InventarioContagem contagem) {
		this.nuInventario = contagem.getNuInventario();
		this.dtContagem = contagem.getDtContagem();
		this.codProd = contagem.getCodProd();
	}
	
	public void addContagem(InventarioContagem contagem) throws SkalaModelException {
		
		if (this.contagens.contains(contagem))
			return;
		
		this.contagens.add(contagem);
			
		try {
			addAjuste(contagem);
		} catch (SkalaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addAjuste(InventarioContagem contagem) throws SkalaModelException {
		InventarioAjuste ajuste = new InventarioAjuste();
		ajuste.setFromContagem(contagem);
		
		if (this.getAjustes().contains(ajuste)) {
			this.getAjustes().get(this.getAjustes().indexOf(ajuste)).addContagem(contagem);
		} else {
			ajuste.addContagem(contagem);
			this.ajustes.add(ajuste);
		}
	}
	
	public List<InventarioAjuste> getAjustesEntrada() {
		List<InventarioAjuste> ajustesEntrada = new LinkedList<>();
		
		for(InventarioAjuste ajuste : ajustes) {
			if(InventarioAjusteTipoAjusteEnum.ENTRADA.getValue().equalsIgnoreCase(ajuste.getTipoAjuste())) 
				ajustesEntrada.add(ajuste);	
		}	
		return ajustesEntrada;
	}
	
	public List<InventarioAjuste> getAjustesSaida() {
		List<InventarioAjuste> ajustesSaida = new LinkedList<>();
		
		for(InventarioAjuste ajuste : ajustes) {
			if(InventarioAjusteTipoAjusteEnum.SAIDA.getValue().equalsIgnoreCase(ajuste.getTipoAjuste())) 
				ajustesSaida.add(ajuste);	
		}	
		return ajustesSaida;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(ajustes, codProd, contagens, dtContagem, nuInventario);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof InventarioItem))
			return false;
		InventarioItem other = (InventarioItem) obj;
		return Objects.equals(ajustes, other.ajustes) && Objects.equals(codProd, other.codProd)
				&& Objects.equals(contagens, other.contagens) && Objects.equals(dtContagem, other.dtContagem)
				&& Objects.equals(nuInventario, other.nuInventario);
	}
	@Override
	public String toString() {
		return String.format("InventarioItem [nuInventario=%s, dtContagem=%s, codProd=%s, contagens=%s, ajustes=%s]",
				nuInventario, dtContagem, codProd, contagens, ajustes);
	}
	
	
}
