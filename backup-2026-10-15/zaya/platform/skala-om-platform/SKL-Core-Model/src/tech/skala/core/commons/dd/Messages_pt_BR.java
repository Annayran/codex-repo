package tech.skala.core.commons.dd;

import java.util.ListResourceBundle;

public class Messages_pt_BR extends ListResourceBundle  {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "insert-fail", "Nao foi possivel inserir o registro." },
			{ "update-fail", "Nao foi possivel atualizar o registro." },
			{ "invalid-field", "Campo nao existente." },
			{ "null-primary-key", "PK do registro nao pode ser nula." },
			{ "null-value-object", "VO do registro nao pode ser nulo" },
			{ "record-not-found", "Registro nao encontrado." }
			};
	}

}
