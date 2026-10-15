package tech.skala.action.modules.controladoria;

import java.math.BigDecimal;
import java.util.Collection;

import javax.swing.text.html.parser.Entity;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ImpostosProcessor {

	public void alteraCstPisCofins(BigDecimal nuNota, BigDecimal cst) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					alterarCstPisCofins(nuNota, cst);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível alterar CST do PIS e COFINS do lançamento.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}

	protected void alterarCstPisCofins(BigDecimal nuNota, BigDecimal cst) throws SkalaModelException {
		JapeWrapper impostoDAO = JapeFactory.dao(DynamicEntityNames.IMPOSTO_ITEM_NOTA);
		try {
			Collection<DynamicVO> impostosVO = impostoDAO.find("this.NUNOTA = ? AND this.CODIMP IN (6, 7)", nuNota);
		
			
			FluidUpdateVO impostoUpdate;
			for (DynamicVO vo : impostosVO) {
				impostoUpdate = impostoDAO.prepareToUpdate(vo);
				
				impostoUpdate.set("CST", cst);
				impostoUpdate.update();
			}
		
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível trocar os impostos do lançamento.", e);
		}
		
	}

	public void alteraCFOPPorLancamento(BigDecimal nuNota, BigDecimal codCfo) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);
					Collection<DynamicVO> vos = dao.find("this.NUNOTA = ?", nuNota);
					for (DynamicVO vo : vos) {
						EntityUpdate update = new EntityUpdate(DynamicEntityNames.ITEM_NOTA, vo);
						update.setField("CODCFO", codCfo);
						update.fluidUpdate();
					}
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possível alterar CFOP do lançamento.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

}
