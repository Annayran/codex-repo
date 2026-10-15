package tech.skala.action.integration.transport;

import java.util.ListResourceBundle;

/**
 * A classe {@code Messages_pt_BR} eh uma implementacao da classe abstrata 
 * {@code java.util.ListResourceBundle}.
 * <p>
 * 
 * Esta classe implementa o metodo @getContents() que retorna uma mensagem 
 * conforme a chave informada.<br>
 * 
 * @author hiagoalexandre
 * @since 2022-11-23
 * @see java.util.ListResourceBundle
 *
 */
public class ExceptionMessages_pt_BR extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "welcome", "Bem vindo ao ERP Grupo Skala" },
			{ "invalid-edi-file", "Arquivo EDI inválido."},
			{ "moved-file", "Falha ao mover arquivos."},
			{ "entry-event-not-saved", "Ocorrência de Entrada não cadastrada."},
			{ "unprocessed-occurrence", "Ocorrência transportadora não processada."},
			{ "callcenter-not-saved", "Call Center não cadastrado."},
			{ "unrelated-note", "Nota não relacionada ao Call Center."},
			{ "unable-fetch-callcenter", "Não foi possível buscar um Call Center para a nota."},
			{ "occurrence-callcenter-not-processed", "Não foi possível cadastrar ocorrência no Call Center."}
			}; 
	}

}
