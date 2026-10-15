package tech.skala.action.integration.tangerino;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.action.modules.empresas.Empresa;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class Company extends Empresa {
	private BigDecimal							tangerinoId;

	public BigDecimal getTangerinoId() {
		return tangerinoId;
	}
	public void setTangerinoId(BigDecimal tangerinoId) {
		this.tangerinoId = tangerinoId;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		super.loadByVO(vo);
		
		this.tangerinoId = vo.asBigDecimal("AD_ID_TANGERINO");
	}

	@Override
	public String toString() {
		return String.format(
				"Company [getCodEmp()=%s, getNomeFantasia()=%s, getRazaoSocial()=%s, getCgc()=%s, tangerinoId=%s]",
				getCodEmp(), getNomeFantasia(), getRazaoSocial(), getCgc(), tangerinoId);
	}

}
