package tech.skala.modules.logistica.callcenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class NotasCallCenterDAO {

	public static Collection<DynamicVO> buscarNotasCallCenterPorNuNotaParcTransp(BigDecimal nuNota, BigDecimal codParcTransp) throws RecordNotFoundException {
		JapeWrapper callNotaDAO = JapeFactory.dao(SklEntityName.NOTA_CALL_CENTER);

		Collection<DynamicVO> notasVO = new ArrayList<>();
		try {
			Collection<DynamicVO> vos = callNotaDAO.find("this.NUNOTA = ?", nuNota);
			
			for (DynamicVO vo : vos) {
				if(vo.asBigDecimal("AD_TMSCAL.CODPARCTRANSP").longValue() == codParcTransp.longValue()) {
					notasVO.add(vo);
				}
			}
				
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

		return notasVO;
	}
	
	public static Collection<NotaCallCenter> buscarNotasPorNuNotaEChaveCTeVazia(BigDecimal nuNota) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.NOTA_CALL_CENTER);
		
		StringBuilder where = new StringBuilder();
		where.append("this.NUNOTA = ? ");
		where.append("AND EXISTS(SELECT 1 FROM AD_TMSCAL T WHERE T.CODCAL = this.CODCAL AND T.CHAVECTE IS NULL) ");
		
		Collection<NotaCallCenter> notasCallCenter = new ArrayList<>();
		try {
			Collection<DynamicVO> vos = dao.find(where.toString(), nuNota);
			
			for(DynamicVO vo : vos) {
				NotaCallCenter nota = new NotaCallCenter();
				nota.loadByVO(vo);
				
				notasCallCenter.add(nota);
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar os Call Centers.", e);
		}
		
		return notasCallCenter;
	}

}
