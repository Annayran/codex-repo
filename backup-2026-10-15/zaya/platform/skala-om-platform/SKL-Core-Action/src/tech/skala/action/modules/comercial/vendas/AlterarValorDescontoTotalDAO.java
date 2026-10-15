package tech.skala.action.modules.comercial.vendas;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AlterarValorDescontoTotalDAO {
	
	private AlterarValorDescontoTotalDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static void alteraValorDescontoTotal(BigDecimal nuNota, BigDecimal vlrDescTot) throws Exception {
		SessionHandle hnd = null;
		
		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);
			
			hnd.execWithTX(new JapeSession.TXBlock() {
				
				@Override
				public void doWithTx() throws Exception {
					alterarValorDescontoTotal(nuNota, vlrDescTot);
				}
			});
			
		} finally {
			hnd = null;
		}
	}
	
	private static void alterarValorDescontoTotal(BigDecimal nuNota, BigDecimal vlrDescTot) throws Exception {
		try {
			JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
			DynamicVO cabVO = cabecalhoNotaDAO.findByPK(nuNota);
			FluidUpdateVO updVO = cabecalhoNotaDAO.prepareToUpdate(cabVO);
	
			updVO.set("VLRDESCTOT", vlrDescTot);
			updVO.set("VLRNOTA", cabVO.asBigDecimal("VLRNOTA").subtract(vlrDescTot));
			updVO.update();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível alterar o Valor do Desconto Total.", e);
		}
	}
}
