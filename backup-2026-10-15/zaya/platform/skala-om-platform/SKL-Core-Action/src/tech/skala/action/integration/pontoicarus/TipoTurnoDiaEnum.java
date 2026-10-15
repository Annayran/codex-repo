package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;

public enum TipoTurnoDiaEnum {
	DOMINGO,
	SEGUNDA,
	TERCA,
	QUARTA,
	QUINTA,
	SEXTA,
	SABADO;
	
	public static String toString(BigDecimal tipo) {
		int dia = tipo.intValue();
		String retorno = null;
		
		switch (dia) {
		case 1:
			retorno = DOMINGO.toString();
			break;
		case 2:
			retorno = SEGUNDA.toString();
			break;
		case 3:
			retorno = TERCA.toString();
			break;
		case 4:
			retorno = QUARTA.toString();
			break;
		case 5:
			retorno = QUINTA.toString();
			break;
		case 6:
			retorno = SEXTA.toString();
			break;
		case 7:
			retorno = SABADO.toString();
			break;

		default:
			break;
		}
		
		return retorno;
	}
}
