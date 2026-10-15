package br.com.zaya.like.Actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.zaya.like.Actions.services.ReprovarCQSemReposicaoService;

public class ReprovarCQSemReposicao implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.execWithTX(new JapeSession.TXBlock() {
				public void doWithTx() throws Exception {
				
					String param_P_APROVA = (String) contexto.getParam("P_APROVA");
					if ("N".equals(param_P_APROVA)) {
						return;
					}
					Registro[] linhas = contexto.getLinhas();
					
					String param_P_CODLOCAL = (String) contexto.getParam("P_CODLOCAL");
					String gerarDevolucaoCompra = (String) contexto.getParam("P_GERARDEVOLUCA");
					int nuNotaCompra = (int) contexto.getParam("P_NUNOTACOMPRA");
					
					BigDecimal nunota = BigDecimal.valueOf(nuNotaCompra);
					
					if("S".equals(gerarDevolucaoCompra) && ( nunota == null || BigDecimal.ZERO.compareTo(nunota) == 0)) {
						throw new Exception("Não foi informado o Nro único da Nota de compra, origem da devolução! " );
					}

					BigDecimal codlocalDestino = new BigDecimal(param_P_CODLOCAL);
					BigDecimal codUsuLogado = contexto.getUsuarioLogado();
					
					BigDecimal nunotaTransf = BigDecimal.ZERO;
					for (Registro linha : linhas) {
						nunotaTransf = ReprovarCQSemReposicaoService.reprovarCQSemReposicao(linha,codUsuLogado,codlocalDestino);
						
						if("S".equals(gerarDevolucaoCompra)) {
							ReprovarCQSemReposicaoService.gerarDevolucaoCompra(linha, nunota, codUsuLogado);
						}
					}
					contexto.setMensagemRetorno("Gerando Transferencia Nro único: "+nunotaTransf);
				}
			});

		} catch (Exception e) {
			contexto.mostraErro(
					"<p align=\"left\"><font size=\"12\" face=\"arial\" color=\"#000000\"><br></font><b>Atencão:  </b>Erro ao reprovar CQ sem reposição!<br><br>"
							+ "<b>Motivo: </b>" + e.getMessage());
		} finally {
			JapeSession.close(hnd);
		}
	}
}
