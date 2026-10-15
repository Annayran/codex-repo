package br.com.zaya.like.Actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.zaya.like.Actions.services.IncluirAcompanhamentoService;

public class IncluirAcompanhamentoAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {

		JapeSession.SessionHandle hnd = null;
		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);
			hnd.execWithTX(new JapeSession.TXBlock() {
				public void doWithTx() throws Exception {
					Registro[] linhas = contexto.getLinhas();
					
					String ocorrencia = (String) contexto.getParam("P_OCORRENCIAS");

					List<BigDecimal> notas = new ArrayList<>();
					for (Registro linha : linhas) {

						BigDecimal nunota = (BigDecimal) linha.getCampo("NUNOTA");
						BigDecimal codUsuLogado = contexto.getUsuarioLogado();
						BigDecimal sequencia = BigDecimalUtil.getValueOrZero((BigDecimal) linha.getCampo("SEQUENCIA"));
						
						IncluirAcompanhamentoService.incluir(nunota, sequencia, ocorrencia, codUsuLogado);
						
						notas.add(nunota);
					}
					contexto.setMensagemRetorno("Incluido com sucesso o acompanhamento para Nota(s)" );
				}
			});

		} catch (Exception e) {
			contexto.mostraErro(
					"<p align=\"left\"><font size=\"12\" face=\"arial\" color=\"#000000\"><br></font><b>Atencão:  </b>Erro ao Incluir acompanhamento!<br><br>"
							+ "<b>Motivo: </b>" + e.getMessage());
		} finally {
			JapeSession.close(hnd);
		}
	}
}
