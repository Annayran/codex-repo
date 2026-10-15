package tech.skala.action.modules.wms.extrato;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

/**
 * A classe {@code GeraExtratoWmsAction} gera historico de movimentacoes de um produto a partir 
 * do recebimento do mesmo no Armazem.
 * 
 * <p>
 * A classe {@code GeraExtratoWmsAction} procura localizar todas as atividades de um produto a 
 * partir da nota de entrada e do correspondente recebimento da mesma.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2021-07-22
 *
 */
public class GeraExtratoWmsAction implements AcaoRotinaJava {
	Action action = new Action(this.getClass().getCanonicalName());

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {

		try {
			action.start();
			
			BigDecimal codProd = BigDecimalUtil.getBigDecimal(contexto.getParam(SklFieldName.CODPROD));
			BigDecimal nuNotaEntrada = BigDecimalUtil.getBigDecimal(contexto.getParam("NUNOTAENTRADA"));
			
			GeraExtratoWmsProcessor processor = new GeraExtratoWmsProcessor(codProd, nuNotaEntrada);
			processor.gerarExtratoWms();
			
		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}
