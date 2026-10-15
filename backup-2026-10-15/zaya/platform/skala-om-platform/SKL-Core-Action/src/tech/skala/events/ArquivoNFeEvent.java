package tech.skala.events;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.logistica.ordemcarga.StatusCargaEnum;

public class ArquivoNFeEvent implements EventoProgramavelJava {
	final private Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private String msg;

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		logger.info("afterUpdate");
		encerraOrdemCarga(vo);

	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	private void encerraOrdemCarga(DynamicVO vo) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();

			BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
			char[] protAutorizacao = vo.asClob("XMLPROTAUTNOT");

			JapeWrapper cabecalhoNotaDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
			JapeWrapper ordemCargaDAO = JapeFactory.dao(DynamicEntityNames.ORDEM_CARGA);

			DynamicVO cabecalhoNotaVO = cabecalhoNotaDAO.findByPK(nuNota);
			BigDecimal codEmp = cabecalhoNotaVO.asBigDecimal(SklFieldName.CODEMP);
			BigDecimal codTipOper = cabecalhoNotaVO.asBigDecimal(SklFieldName.CODTIPOPER);
			BigDecimal ordemCarga = cabecalhoNotaVO.asBigDecimal(SklFieldName.ORDEMCARGA);
			
			JapeWrapper carteiraDAO = JapeFactory.dao(SklEntityName.TOPCARTEIRA);
			DynamicVO carteiraAntecipadoVO = carteiraDAO.findOne("this.CODTIPOPER = ? AND this.TIPCART = 'V' AND this.ANTECIPADO = 'S'", codTipOper);

			//valida se eh lancamento de faturamento antecipado
			if(carteiraAntecipadoVO != null)
				return;
			
			if (ordemCarga == null)
				return;

			if (ordemCarga.doubleValue() == 0D)
				return;

			DynamicVO ordemCargaVO = ordemCargaDAO.findByPK(codEmp, ordemCarga);
			if (ordemCargaVO != null && protAutorizacao != null) {

				String situacao = ordemCargaVO.asString(SklFieldName.SITUACAO);
				String statusCarga = ordemCargaVO.asString(SklFieldName.AD_STATUSCARGA);

				msg = String.format("nuNota %s ordemCarga %s situacao %s statusCarga %s", nuNota, ordemCarga, situacao,
						statusCarga);
				msg += String.format(" protocolo %s", String.valueOf(protAutorizacao));

				if (statusCarga != null && !"EX".equals(statusCarga)) {
					msg += " - atualizando Status da Carga";

					EntityUpdate update = new EntityUpdate(DynamicEntityNames.ORDEM_CARGA, ordemCargaVO);
					update.setField(SklFieldName.AD_STATUSCARGA, StatusCargaEnum.EXPEDIDO.getValue());
					update.fluidUpdate();
				}

			} else {
				msg = String.format("nuNota %s ordemCarga %s", nuNota, ordemCarga);

				if (protAutorizacao != null)
					msg += String.format(" protocolo %s", String.valueOf(protAutorizacao));

			}

			logger.info(msg);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível encerrar Ordem de Carga", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

}
