package tech.skala.flow.modules.central;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.extensions.flow.ContextoTarefa;
import br.com.sankhya.extensions.flow.TarefaJava;
import tech.skala.core.commons.exceptions.SkalaModelException;


public class GeraPedidoSolicitacaoServico implements TarefaJava{
	
	@Override
	public void executar(ContextoTarefa ctx) throws SkalaModelException {

		try {
			Registro[] linhas = ctx.getLinhasFormulario("AD_PLTFLCABCOMPRAS");
			BigDecimal idSolicitacao = BigDecimalUtil.getBigDecimal(linhas[0].getCampo("IDINSTPRN"));
			
			SolicitacaoServicoProcessor processor = new SolicitacaoServicoProcessor();
			processor.gerarSolicitacaoServico(idSolicitacao);
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar Pedido de Solicitação de Serviço.", e);
		}
		
	

	}

}
