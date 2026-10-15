package tech.skala.flow.modules.central;

import java.math.BigDecimal;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.extensions.flow.ContextoTarefa;
import br.com.sankhya.extensions.flow.TarefaJava;

public class GeraPedidoSolicitacaoCompraTarefa implements TarefaJava {

	@Override
	public void executar(ContextoTarefa ctx) throws Exception {

		Registro[] linhas = ctx.getLinhasFormulario("AD_PLTFLCABCOMPRAS");
		BigDecimal idSolicitacao = BigDecimalUtil.getBigDecimal(linhas[0].getCampo("IDINSTPRN"));

		SolicitacaoCompraProcessor processor = new SolicitacaoCompraProcessor();
		processor.gerarSolicitacaoCompra(idSolicitacao);

	}

}
