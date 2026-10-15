package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;

public class TesteRegistroAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		
		Registro[] linhas = contexto.getLinhas();
		
		contexto.setMensagemRetorno("Quantidade de linhas selecionadas: " + linhas.length);
		
	}

}