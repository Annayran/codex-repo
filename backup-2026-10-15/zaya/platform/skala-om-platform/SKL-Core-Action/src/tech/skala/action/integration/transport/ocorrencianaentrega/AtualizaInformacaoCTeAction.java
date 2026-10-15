package tech.skala.action.integration.transport.ocorrencianaentrega;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;
import tech.skala.core.main.Skl;
import tech.skala.events.BarramentoRegra;

public class AtualizaInformacaoCTeAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			BigDecimal nuNota;
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
			for (Registro linha : linhas) {
				
				nuNota = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.NUNOTA));
				DynamicVO vo = dao.findByPK(nuNota);
				
				BarramentoRegra.incluirCTE(vo);
			}
			
			/* adiciona uma mensagem na mensagem de retorno ao usuario */
			msg = "";
			action.appendMessage(msg);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}