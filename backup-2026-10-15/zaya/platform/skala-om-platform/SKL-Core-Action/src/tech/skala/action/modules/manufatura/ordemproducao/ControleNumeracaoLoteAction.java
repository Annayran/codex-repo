package tech.skala.action.modules.manufatura.ordemproducao;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.core.commons.extensions.Action;

/**
 * A classe {@code ControleNumeracaoLoteAction} permite a geração e o controle de numeração 
 * de lotes de Ordens de Produção, conforme <b>Alteração de Linha de Produção</b>.
 * 
 * <p>
 * As OP's geradas serao associadas ao <b>Centro de Trabalho</b> informado.<br>
 * A <b>Quantidade de Lotes</b> informada na programação determina a quantidade de OP's a 
 * serem geradas e a quantidade a ser produzida em cada OP é determinada pelo tamanho do lote 
 * padrão do <b>Centro de Trabalho</b>.
 * <p>
 * 
 * @author marcosdamasceno
 * @since 2022-08-04
 *
 */
public class ControleNumeracaoLoteAction implements AcaoRotinaJava {
	
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		BigDecimal numLote = BigDecimalUtil.getBigDecimal(contexto.getParam("NUMLOTE"));
		
		String msg;
		try {
			action.start();
			
			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			OrdemProducaoProcessor loteProcessor = new OrdemProducaoProcessor();
			for (Registro linha : linhas) {
				String statusProc = (String) linha.getCampo("STATUSPROC");
				BigDecimal idiProc = (BigDecimal) linha.getCampo("IDIPROC");
				loteProcessor.validaStatusOP(statusProc, idiProc);
				
				//loteProcessor.setLote(numLote, idiProc);
				
				//loteProcessor.atualizaStatusDependencia(numLote, idiProc);
			}

		} catch (Exception e) {
			action.error(e);
			
		} finally {
			action.stop();
			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}
}