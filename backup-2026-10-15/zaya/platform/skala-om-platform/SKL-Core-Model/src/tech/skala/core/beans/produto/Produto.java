package tech.skala.core.beans.produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.produto.ProdutoExceptions.AlternativeVolumeNotFoundException;

/**
 * A classe {@code Produto} representa a tabela {@code TGFPRO}.
 *  
 * <p>
 * A classe {@code Produto} devera ser extendida como classes que representem 
 * os diversos tipos de produtos do sistema.
 * </p> 
 * 
 * @author marcos.damasceno
 * @author cassiomenezes
 * @since 2022-07-05
 */
public abstract class Produto implements EntityBean {
	public static final String ENTITY_NAME = DynamicEntityNames.PRODUTO;
			
	protected BigDecimal				codProd;
	protected String					descrProd;
	protected String					descricao;
	protected String					complDesc;
	protected String 					complementoDescricao;
	protected BigDecimal				codGrupoProd;
	protected boolean					usaControle;
	protected String 					marca;
	protected String					codVol;
	protected boolean					ativo;
	protected List<VolumeAlternativo>	volumesAlternativos;
	
	protected Produto() {
		
	}
	
	protected Produto(BigDecimal codProd) throws AlternativeVolumeNotFoundException {
		this.codProd = codProd;
		this.volumesAlternativos = VolumesAlternativosFinder.findVolumesAlternativos(codProd);
	}
	
	public BigDecimal getCodProd() {
		return codProd;
	}

	public String getDescrProd() {
		return descrProd;
	}

	public void setDescrProd(String descrProd) {
		this.descrProd = descrProd;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getComplDesc() {
		return complDesc;
	}

	public void setComplDesc(String complDesc) {
		this.complDesc = complDesc;
	}

	public String getComplementoDescricao() {
		return complementoDescricao;
	}

	public void setComplementoDescricao(String complementoDescricao) {
		this.complementoDescricao = complementoDescricao;
	}

	public BigDecimal getCodGrupoProd() {
		return codGrupoProd;
	}

	public void setCodGrupoProd(BigDecimal codGrupoProd) {
		this.codGrupoProd = codGrupoProd;
	}

	
	public boolean isUsaControle() {
		return usaControle;
	}

	public void setUsaControle(boolean usaControle) {
		this.usaControle = usaControle;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCodVol() {
		return codVol;
	}

	public void setCodVol(String codVol) {
		this.codVol = codVol;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<VolumeAlternativo> getVolumesAlternativos() {
		return volumesAlternativos;
	}

	public void setVolumesAlternativos(List<VolumeAlternativo> volumesAlternativos) {
		this.volumesAlternativos = volumesAlternativos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codProd, descrProd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Produto))
			return false;
		Produto other = (Produto) obj;
		return codProd == other.codProd && Objects.equals(descrProd, other.descrProd);
	}
}
