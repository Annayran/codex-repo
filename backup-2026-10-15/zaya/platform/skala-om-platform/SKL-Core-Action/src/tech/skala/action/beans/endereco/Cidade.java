package tech.skala.action.beans.endereco;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class Cidade implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.CIDADE;

	private BigDecimal							codCid;
	private String								nomeCid;
	private String								descricaoCorreio;
	private UnidadeFederativa					unidadeFederativa;
	private BigDecimal							codMunFis;
	
	public Cidade() {
		super();
	}
	
	public BigDecimal getCodCid() {
		return codCid;
	}
	public void setCodCid(BigDecimal codCid) {
		this.codCid = codCid;
	}
	public String getNomeCid() {
		return nomeCid;
	}
	public void setNomeCid(String nomeCid) {
		this.nomeCid = nomeCid;
	}
	public String getDescricaoCorreio() {
		return descricaoCorreio;
	}
	public void setDescricaoCorreio(String descricaoCorreio) {
		this.descricaoCorreio = descricaoCorreio;
	}
	public UnidadeFederativa getUnidadeFederativa() {
		return unidadeFederativa;
	}
	public void setUnidadeFederativa(UnidadeFederativa unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}
	public BigDecimal getCodMunFis() {
		return codMunFis;
	}
	public void setCodMunFis(BigDecimal codMunFis) {
		this.codMunFis = codMunFis;
	}
	public String getSiglaUf() {
		return unidadeFederativa.getUf();
	}
	
	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		try {
			DynamicVO vo = dao.findByPK(keyValue);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}		
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codCid = vo.asBigDecimal("CODCID");
		this.nomeCid = vo.asString("NOMECID");
		this.descricaoCorreio = vo.asString("DESCRICAOCORREIO");
		this.unidadeFederativa = EnderecoFinder.findUnidadeFederativaByPk(vo.asBigDecimal("UF"));
		this.codMunFis = vo.asBigDecimal("CODMUNFIS");
	}
	
	@Override
	public String toString() {
		return String.format("Cidade [codCid=%s, nomeCid=%s, descricaoCorreio=%s, UnidadeFederativa=%s, codMunFis=%s]",
				codCid, nomeCid, descricaoCorreio, unidadeFederativa, codMunFis);
	}
	
}
