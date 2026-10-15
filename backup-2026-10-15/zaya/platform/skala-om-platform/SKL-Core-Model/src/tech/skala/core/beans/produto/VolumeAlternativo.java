package tech.skala.core.beans.produto;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.comercial.ConversorUnidadeProduto;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class VolumeAlternativo implements EntityBean, LoadableByValueObject {
	private BigDecimal				codProd;
	private String					codVol;
	private BigDecimal				fatorConversao;	
	
	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public String getCodVol() {
		return codVol;
	}

	public void setCodVol(String codVol) {
		this.codVol = codVol;
	}

	public BigDecimal getFatorConversao() {
		return fatorConversao;
	}

	public void setFatorConversao(BigDecimal fatorConversao) {
		this.fatorConversao = fatorConversao;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		
		try {
			this.codProd = vo.asBigDecimal(SklFieldName.CODPROD);
			this.codVol = vo.asString(SklFieldName.CODVOL);

			ConversorUnidadeProduto conversor = new ConversorUnidadeProduto();
			conversor.configurarPeloVolumeAlternativoVO(vo);
			this.fatorConversao = conversor.converte(BigDecimal.ONE, true);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}
		
		
	}
}
