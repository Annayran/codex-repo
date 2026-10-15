package tech.skala.core.commons.extensions;

import java.util.ListResourceBundle;

public class Messages_pt_BR extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "processing", "Processando" },
			{ "successful-processing", "Processamento efetuado com sucesso!" },
			{ "record-not-processed", "Registro não processado"},
			{ "jdbc-session-not-opened", "Não foi possível abrir a sessão <i>JDBC</i>." },
			{ "time-elapsed", "Tempo gasto" },
			{ "auth_not_possible", "Não foi possível autenticar o usuário." },
			{ "setup_context_not_possible", "Não foi possível alterar ambiente para usuário." }
			};
	}

}
