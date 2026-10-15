package tech.skala.core.beans.exceptions;

import java.util.ListResourceBundle;

public class Messages_pt_BR extends ListResourceBundle  {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "record-not-found", "Registro nao encontrado." },
			{ "person-not-client", "Não é possível atualizar o objeto. Esta pessoa não é um cliente." }
			};
	}

}
