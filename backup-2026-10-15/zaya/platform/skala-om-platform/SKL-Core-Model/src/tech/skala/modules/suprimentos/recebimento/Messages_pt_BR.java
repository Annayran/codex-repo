package tech.skala.modules.suprimentos.recebimento;

import java.util.ListResourceBundle;

import tech.skala.core.commons.i18n.I18nMessageFactory;

/**
 * A classe {@code Messages_pt_BR} eh uma implementacao da classe abstrata 
 * {@code java.util.ListResourceBundle}.
 * <p>
 * 
 * Esta classe implementa o metodo @getContents() que retorna uma mensagem 
 * conforme a chave informada.
 * 
 * @author cassiomenezes
 * 
 * @see java.util.ListResourceBundle
 * @see I18nMessageFactory
 * 
 * @since 2022-07-14
 *
 */
public class Messages_pt_BR extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] { 
			{ "welcome", "Bem vindo ao ERP Grupo Skala" },
			{ "invoice-not-found", "[NUMNOTA %s] Nota Fiscal de Compra não localizada no sistema." },
			{ "invoice-not-found-cnpj", "[NUMNOTA %s CNPJ %s] Nota não localizada para parceiro." },
			{ "invoice-key-duplicated", "Chave NF-e %s duplicada." },
			{ "invoice-keys-duplicated", "[NUMNOTA %s CNPJ %s] duplicados."},
			{ "invoice-not-confirmed", "[NUNOTA %s] Nota não confirmada."},
			{ "receiving-duplicated", "[NUNOTA %s] Lançamento associado a mais de um Recebimento." },
			{ "receiving-not-found", "Não foi possível inicializar o Recebimento." },
			{ "receiving-not-completed", "Não foi possível finalizar o Recebimento." },
			{ "receiving-not-inserted", "Não foi possível gerar o Recebimento." },
			{ "itens-not-found", "[NUNOTA %s] Não foram localizados itens para este lançamento." },
			{ "update-date-failed", "Não foi possível atualizar a Data de Entrada e Saída."},
			{ "conference-not-processed", "[RECEBIMENTO %s] Não foi possível processar a Conferência de entrada."}
			};
	}

}
