package tech.skala.action.modules.manufatura.workcenter;

import java.math.BigDecimal;
import java.util.Collection;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class WorkCenterProcessor {

	public void atualizaWorkCenterInstancia(BigDecimal idiProc, BigDecimal codWcpOrigem, BigDecimal codWcpDestino)
			throws UpdateNotPossibleException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.WORK_CENTER_INSTANCIA_PROCESSO);

					Collection<DynamicVO> vos;
					try {
						vos = dao.find("this.IDIPROC = ? AND this.CODWCP = ?", idiProc, codWcpOrigem);
						
						atualizarWorkCenterInstancia(vos, codWcpDestino);
					} catch (Exception e) {
						throw new DataDictionaryExceptions.RecordNotFoundException(e);
					}

				}

			});

		} catch (Exception e) {
			throw new UpdateNotPossibleException(e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void atualizarWorkCenterInstancia(Collection<DynamicVO> vos, BigDecimal codWcpDestino) throws UpdateNotPossibleException {
		try {
			for (DynamicVO vo : vos) {
				EntityUpdate entityUpd = new EntityUpdate(DynamicEntityNames.WORK_CENTER_INSTANCIA_PROCESSO, vo);
				entityUpd.setField(SklFieldName.CODWCP, codWcpDestino);
				entityUpd.fluidUpdate();
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
		}
	}
}

