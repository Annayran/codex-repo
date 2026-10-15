package tech.skala.core.commons.exceptions;

import java.util.ListResourceBundle;

public class ExceptionMessages_pt_BR extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "welcome", "Exceções ERP Grupo Skala." },
			{ "record-not-found", "Registro não localizado." },
			{ "npe", "Erro interno (NPE)." },
			{ "no-error-message", "Sem mensagem de erro." },
			{ "unidentified-error", "Erro nao identificado." },
			{ "cause", "Causa" }
			};
	}

}
