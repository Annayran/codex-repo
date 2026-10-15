package tech.skala.core.beans.usuario;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class UsuarioFinder {
	
	private UsuarioFinder() {
		
	}
	
	public static BigDecimal getCodUsuByNomeUsu(String nomeUsu) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.USUARIO);
		
		DynamicVO vo = null;
		try {
			vo = dao.findOne("this.NOMEUSU = ?", nomeUsu);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar o usuário.", e);
		}
		
		return vo == null ? null : vo.asBigDecimal(SklFieldName.CODUSU);
	}
	
}
