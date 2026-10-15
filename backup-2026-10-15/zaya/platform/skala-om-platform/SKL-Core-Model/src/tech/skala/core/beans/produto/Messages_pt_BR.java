package tech.skala.core.beans.produto;

import java.util.ListResourceBundle;

public class Messages_pt_BR extends ListResourceBundle  {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "alternative-volume-not-found", "[CODPROD %s] Não foi possível localizar unidades alternativas para o Produto." }
			};
	}

}
