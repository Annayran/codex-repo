package tech.skala.action.modules.recebimento;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import tech.skala.core.beans.suprimento.recebimento.Recebimento;
import tech.skala.core.commons.extensions.Action;

public class EnviaRecebimentoAction implements AcaoRotinaJava {

	private static Gson gson = new GsonBuilder()
			.serializeNulls()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			.create();
	
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Action action = new Action(this.getClass().getCanonicalName());

		String msg;
		try {
			action.start();

			String recebimentoJO = "{\"nuRecebimento\":162,\"situacao\":\"1\",\"dtRecebimento\":\"2023-01-06T00:00:00.000Z\",\"nuNota\":2253648,\"numNota\":136765,\"codParc\":422,\"cnpj\":\"61434288000105\",\"conferencia\":{\"nuConferencia\":162,\"situacao\":\"A\",\"tipConf\":\"R\",\"codUsu\":594,\"dhAlter\":\"2023-01-06T14:21:20.000Z\",\"itens\":[{\"nuConferencia\":162,\"codProd\":2590,\"codBarra\":\"2590\",\"sequencia\":1,\"sequenciaOrig\":1,\"controle\":\" \",\"controleConf\":\"LOTE01\",\"qtdNeg\":18370.0,\"codVol\":\"KG\",\"qtdUnidPad\":18370.0,\"codVolPad\":\"KG\",\"fator\":0.0,\"qtdConf\":18000.0,\"qtdAvaria\":0.0,\"isRecontagem\":false,\"dtVal\":null,\"dtValConf\":\"2024-01-01T00:00:00.000Z\",\"dtFabricacao\":null,\"dtFabricacaoConf\":\"2022-01-01T00:00:00.000Z\",\"qtdVolume\":18000,\"qtdUnitVol\":1.0,\"produto\":{\"codProd\":2590,\"descricao\":\"QUATERNARIO QUATERCAP CTTN 29%\",\"descrProd\":\"\",\"codVol\":\"KG\",\"usaControle\":true,\"shelfLife\":0}},{\"nuConferencia\":162,\"codProd\":2590,\"codBarra\":\"2590\",\"sequencia\":1,\"sequenciaOrig\":1,\"controle\":\" \",\"controleConf\":\"LOTE02\",\"qtdNeg\":18370.0,\"codVol\":\"KG\",\"qtdUnidPad\":18370.0,\"codVolPad\":\"KG\",\"fator\":0.0,\"qtdConf\":370.0,\"qtdAvaria\":0.0,\"isRecontagem\":false,\"dtVal\":null,\"dtValConf\":\"2024-01-01T00:00:00.000Z\",\"dtFabricacao\":null,\"dtFabricacaoConf\":\"2023-01-01T00:00:00.000Z\",\"qtdVolume\":1,\"qtdUnitVol\":370.0,\"produto\":{\"codProd\":2590,\"descricao\":\"QUATERNARIO QUATERCAP CTTN 29%\",\"descrProd\":\"\",\"codVol\":\"KG\",\"usaControle\":true,\"shelfLife\":0}}]}}";
			
			Recebimento recebimento = gson.fromJson(recebimentoJO, Recebimento.class);
			
			action.appendMessageWithLineSeparator(recebimento.toString());
			
			RecebimentoMercadoriaProcessor processor = new RecebimentoMercadoriaProcessor();
			processor.processaConferenciaEntrada(recebimento);
			//processor.processarItemConferencia(recebimento);

		} catch (Exception e) {
			action.error(e);
		} finally {
			action.stop();

			contexto.setMensagemRetorno(action.getMensagemRetorno());
		}
	}

}