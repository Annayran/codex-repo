package tech.skala.core.beans.parceiro;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ParceiroFinder {
	private static final JapeWrapper DAO = JapeFactory.dao(DynamicEntityNames.PARCEIRO);

	private ParceiroFinder() {
	}

	public static DynamicVO buscaParceiroPorCgcCPF(String cgcCpf) throws RecordNotFoundException {
		DynamicVO vo = null;

		if (cgcCpf != null) {
			try {
				vo = DAO.findOne("this.CGC_CPF = ?", cgcCpf);
			} catch (Exception e) {
				throw new RecordNotFoundException(e);
			}
		}

		return vo;
	}

	public static DynamicVO buscaParceiroDaEmpresa(BigDecimal codEmp) throws RecordNotFoundException {
		DynamicVO vo = null;

		if (codEmp != null) {

			try {
				JapeWrapper empresaDAO = JapeFactory.dao(DynamicEntityNames.EMPRESA);
				DynamicVO empresaVO = empresaDAO.findByPK(codEmp);

				vo = DAO.findByPK(empresaVO.asBigDecimal(SklFieldName.CODPARC));
			} catch (Exception e) {
				throw new RecordNotFoundException(e);
			}
		}

		return vo;
	}
}
