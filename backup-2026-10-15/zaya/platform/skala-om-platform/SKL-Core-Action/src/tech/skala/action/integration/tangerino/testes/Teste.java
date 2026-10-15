package tech.skala.action.integration.tangerino.testes;

import java.math.BigDecimal;

import com.sankhya.util.TimeUtils;

import tech.skala.action.integration.tangerino.model.integracao.Autenticacao;
import tech.skala.action.integration.tangerino.model.integracao.ColaboradorDemitido;
import tech.skala.action.integration.tangerino.model.integracao.ColaboradorEscala;
import tech.skala.action.integration.tangerino.utils.ConversorUtils;
import tech.skala.action.integration.tangerino.utils.TangerinoHelper;

public class Teste {

	public static void main(String[] args) throws Exception {
		System.out.println("1");
		testarHorasAdicionais(new BigDecimal(1600), new BigDecimal(300));
		testarHorasAdicionais(new BigDecimal(2000), new BigDecimal(300));
		System.out.println("2");
		testarHorasAdicionais(new BigDecimal(2100), new BigDecimal(300));
		testarHorasAdicionais(new BigDecimal(400), new BigDecimal(300));
	}

	private static void testarHorasAdicionais(BigDecimal horas, BigDecimal horasAdicionais) throws Exception{
		System.out.println("Horas Sankhya: "+ horas);
		BigDecimal total = ConversorUtils.processarHorario(horas, horasAdicionais);
		System.out.println(ConversorUtils.converterHoraParaMillesegundos(total));
		System.out.println("====================================");
		
		TimeUtils.bigDecimal2Timestamp(horas);
	}

//	private static void testarDemissao(Autenticacao autenticacao) throws Exception {
//		TangerinoHelper.incluirDemissao(new ColaboradorDemitido("390001", Long.valueOf("1666045550590"), new BigDecimal(3003332)), autenticacao,  false);
//	}

//	private static void testarAlteracaoDeCargaHoraria(Autenticacao autenticacao) throws Exception {
//		ColaboradorEscala colaboradorEscala = new ColaboradorEscala(
//			"112195", new BigDecimal(3003366), TimeUtils.getNow().getTime(), new BigDecimal(21) , new BigDecimal(2197756)
//		);
//		TangerinoHelper.incluirNovaCargaHoraria(colaboradorEscala, autenticacao, false);
//	}
}
