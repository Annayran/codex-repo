package tech.skala.core.commons.i18n;

/**
 * Esta interface eh utilizada para mensagens de internacionalizacao.
 * <p>
 * 
 * Esta interface contem os <i>baseNames</i> dos pacotes de mensagem padrao 
 * e tambem impoe o metodo publico {@code getMessage(key)} para retornar uma 
 * mensagem existente em uma classe de mensagens.
 *  
 * @author cassiomenezes
 * @since 2022-05-06
 *
 */
public interface I18nMessage {
	static final String					MESSAGES_BASENAME = "tech.skala.core.commons.i18n.Messages";
    static final String					EXCEPTION_MESSAGES_BASENAME = "tech.skala.core.commons.exceptions.ExceptionMessages";
	
	String getMessage(String key);
	
}
