package tech.skala.action.modules.controladoria;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class RateioProcessor {
	private final Logger					logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();
	private JapeWrapper 				rateioRecDespDAO = null;
	private BigDecimal						nuNota;
	
	public RateioProcessor(BigDecimal nuNota) {
		this.nuNota = nuNota;
		this.rateioRecDespDAO = JapeFactory.dao(DynamicEntityNames.RATEIO_REC_DESP);
	}
	
	public void excluirRateio() throws SkalaModelException {
		String msg;
		
		try {
			msg = String.format("[NUNOTA %s] Excluindo rateio...", nuNota);
			logger.info(msg);
			
			rateioRecDespDAO.deleteByCriteria("this.ORIGEM = 'E' AND this.NUFIN = ?", nuNota);
		} catch (Exception e) {
			msg = String.format("[NUNOTA %s] Não foi possível <b>excluir</b> o rateio.", nuNota);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			throw new SkalaModelException(msg);
		}
	}
	
	public void gerarRateio() throws SkalaModelException {
		String msg;
		JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		JapeWrapper itemNotaDAO = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);
		
		try {
			msg = String.format("[NUNOTA %s] Gerando rateio...", nuNota);
			logger.info(msg);

			DynamicVO cabecalhoNotaVO = cabecalhoNotaDAO.findByPK(nuNota);
			BigDecimal codParc = cabecalhoNotaVO.asBigDecimal("CODPARC");
			BigDecimal codCenCus = cabecalhoNotaVO.asBigDecimal("CODCENCUS");
			BigDecimal vlrNota = cabecalhoNotaVO.asBigDecimal("VLRNOTA");
			BigDecimal vlrTot;
			BigDecimal vlrDesc;
			BigDecimal vlrLiq;
			RateioRecDesp rateioItemNota;
			List<RateioRecDesp> rateios = new LinkedList<>();
			Collection<DynamicVO> itensNotaVO = itemNotaDAO.find("this.NUNOTA = ? AND this.SEQUENCIA > 0", nuNota);
			for (DynamicVO itemNotaVO:itensNotaVO) {
				
				vlrTot = itemNotaVO.asBigDecimal("VLRTOT");
				vlrDesc = itemNotaVO.asBigDecimal("VLRDESC");
				vlrLiq = vlrTot.subtract(vlrDesc, BigDecimalUtil.MATH_CTX);

				if (BigDecimalUtil.getValueOrZero(itemNotaVO.asBigDecimal("Produto.CODCENCUS")).longValue() != 0L)
					codCenCus = itemNotaVO.asBigDecimal("Produto.CODCENCUS");
			
				rateioItemNota = new RateioRecDesp();
				rateioItemNota.setOrigem("E");
				rateioItemNota.setNuFin(nuNota);
				rateioItemNota.setCodNat(BigDecimalUtil.getValueOrZero(itemNotaVO.asBigDecimal("Produto.CODNAT")));
				rateioItemNota.setCodCenCus(codCenCus);
				rateioItemNota.setCodProj(BigDecimalUtil.getValueOrZero(itemNotaVO.asBigDecimal("Produto.CODPROJ")));
				rateioItemNota.setCodSite(BigDecimal.ZERO);
				rateioItemNota.setCodParc(codParc);
				rateioItemNota.setVlrTotal(vlrNota);
				rateioItemNota.setVlrRateio(vlrLiq);
			
				if (rateios.contains(rateioItemNota)) {
					rateios.get(rateios.indexOf(rateioItemNota)).getVlrRateio().add(vlrLiq, BigDecimalUtil.MATH_CTX);
				} else {
					rateios.add(rateioItemNota);
				}
			}
			
			
			
		} catch (Exception e) {
			msg = String.format("[NUNOTA %s] Não foi possível <b>incluir</b> o rateio.", nuNota);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			throw new SkalaModelException(msg);
		}
	}
	public void atualizarOrigem() throws SkalaModelException {
		String msg;
		JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		FluidUpdateVO cabecalhoNotaUpdVO;
		
		try {
			msg = String.format("[NUNOTA %s] Atualizando a nota...", nuNota);
			logger.info(msg);

			DynamicVO cabecalhoNotaVO = cabecalhoNotaDAO.findByPK(nuNota);

			cabecalhoNotaUpdVO = cabecalhoNotaDAO.prepareToUpdate(cabecalhoNotaVO);
			cabecalhoNotaUpdVO.set("RATEADO", "S");
			cabecalhoNotaUpdVO.update();
		} catch (Exception e) {
			msg = String.format("[NUNOTA %s] Não foi possível <b>atualizar</b> o rateio da Nota.", nuNota);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			throw new SkalaModelException(msg);
		}

	}
	
	
}
