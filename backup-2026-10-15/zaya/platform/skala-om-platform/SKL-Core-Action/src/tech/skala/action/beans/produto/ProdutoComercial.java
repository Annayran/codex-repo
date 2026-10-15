package tech.skala.action.beans.produto;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.produto.ProdutoExceptions.AlternativeVolumeNotFoundException;
import tech.skala.core.beans.produto.VolumesAlternativosFinder;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ProdutoComercial extends ProdutoMeta implements LoadableByPrimaryKey, LoadableByValueObject {

	private BigDecimal						codGProd;
	private BigDecimal						codGProdSecao;
	private BigDecimal						codGProdDepartamento;
	
	public ProdutoComercial(BigDecimal codProd) throws AlternativeVolumeNotFoundException {
		super(codProd);
	}

	public BigDecimal getCodGProd() {
		return codGProd;
	}

	public void setCodGProd(BigDecimal codGProd) {
		this.codGProd = codGProd;
	}

	public BigDecimal getCodGProdSecao() {
		return codGProdSecao;
	}

	public void setCodGProdSecao(BigDecimal codGProdSecao) {
		this.codGProdSecao = codGProdSecao;
	}

	public BigDecimal getCodGProdDepartamento() {
		return codGProdDepartamento;
	}

	public void setCodGProdDepartamento(BigDecimal codGProdDepartamento) {
		this.codGProdDepartamento = codGProdDepartamento;
	}


	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.PRODUTO);
		try {
			DynamicVO vo = (codProd == null) ? dao.findByPK(keyValue) : dao.findByPK(codProd);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		if (codProd == null)
			this.codProd = vo.asBigDecimal(SklFieldName.CODPROD);

		this.descrProd = vo.asString(SklFieldName.DESCRPROD);
		this.descricao = descrProd;
		this.complDesc = vo.asString(SklFieldName.COMPLDESC);
		this.complementoDescricao = complDesc;
		this.codGrupoProd = vo.asBigDecimal(SklFieldName.CODGRUPOPROD);
		this.marca = vo.asString(SklFieldName.MARCA);
		this.codVol = vo.asString(SklFieldName.CODVOL);
		this.ativo = vo.asBoolean(SklFieldName.ATIVO);
		this.familia = FamiliaProdutoFinder.findByCodGrupoProd(codGrupoProd);
		this.codGProd = vo.asBigDecimal(SklFieldName.CODGPROD);
		this.codGProdSecao = GrupoProducaoFinder.findCodGrupoProducaoPai(codGProd);
		this.codGProdDepartamento = GrupoProducaoFinder.findCodGrupoProducaoPai(codGProdSecao);
		
		try {
			this.volumesAlternativos = VolumesAlternativosFinder.findVolumesAlternativos(codProd);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
	}

}
