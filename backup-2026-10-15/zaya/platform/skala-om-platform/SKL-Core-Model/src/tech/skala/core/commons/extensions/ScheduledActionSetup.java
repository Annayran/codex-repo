package tech.skala.core.commons.extensions;

import java.math.BigDecimal;

import tech.skala.core.commons.exceptions.SkalaModelException;

/**
 * Metodos necessarios para implementacoes de <b>Acoes Agendadas</b>.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code setupContext(String uip)} que 
 * inicializa variaveis de contexto e de sessao a partir de um codigo de usuario.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-07-13
 * 
 *
 */
public interface ScheduledActionSetup {

	/**
	 * Inicializa o contexto da acao agendada com um usuario especifico.
	 * 
	 * <p>
	 * Na execucao de uma <b>Acao Agendada</b> nao existe um contexto ou um usuario logado 
	 * na secao.<br>
	 * Este metodo devera localizar um usuario conforme identificacao informada e devera 
	 * definir uma autenticacao e contexto apropriados.
	 * </p>
	 * 
	 * @param uid Codigo de usuario valido do sistema.
	 * @throws SkalaModelException quando nao for possivel autenticar ou atualizar contexto.
	 * 
	 */
	void setupContext(String uid) throws SkalaModelException;
	
	/**
	 * Inicializa o contexto da acao agendada com um usuario especifico.
	 * 
	 * <p>
	 * Na execucao de uma <b>Acao Agendada</b> nao existe um contexto ou um usuario logado 
	 * na secao.<br>
	 * Este metodo devera localizar um usuario conforme identificacao informada e devera 
	 * definir uma autenticacao e contexto apropriados.
	 * </p>
	 * 
	 * @param userId Codigo de usuario valido do sistema.
	 * @throws SkalaModelException quando nao for possivel autenticar ou atualizar contexto.
	 * 
	 */
	void setupContext(BigDecimal userId) throws SkalaModelException; 

}
