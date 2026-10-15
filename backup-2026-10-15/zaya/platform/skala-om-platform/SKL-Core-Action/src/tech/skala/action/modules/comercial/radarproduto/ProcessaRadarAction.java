package tech.skala.action.modules.comercial.radarproduto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import tech.skala.action.beans.meta.MetaPorFamilia;
import tech.skala.action.beans.radarproduto.RadarFamilia;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.extensions.Action;

public class ProcessaRadarAction implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			Registro[] linhas = contexto.getLinhas();
			action.validateRows(linhas);

			msg = "Atualizando Radar...\n";
			action.appendMessage(msg);

			List<RadarFamilia> familias = new LinkedList<>();
			for (Registro linha : linhas) {
				BigDecimal codEmp = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODEMP));
				Timestamp dtRef = (Timestamp) linha.getCampo(SklFieldName.DTREF);
				BigDecimal codFam = BigDecimalUtil.getBigDecimal(linha.getCampo(SklFieldName.CODFAM));

				MetaPorFamilia metaPorFamilia = new MetaPorFamilia(codEmp, dtRef, codFam, false, false);
				metaPorFamilia.loadByRecord(linha);

				msg = String.format("[CODFAM %s] %s produtos...%n", metaPorFamilia.getCodEmp(),
						metaPorFamilia.getProdutos().size());
				action.appendMessage(msg);

				RadarFamilia radarFamilia = new RadarFamilia(codEmp, dtRef, codFam);
				radarFamilia.loadByMeta(metaPorFamilia);

				if (!radarFamilia.getRadarProduto().isEmpty())
					familias.add(radarFamilia);
			}

			ProcessaRadar helper = new ProcessaRadar();
			helper.atualizarRadarWithTX(familias);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}