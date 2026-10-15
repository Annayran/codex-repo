package tech.skala.core.commons.extensions;

import br.com.sankhya.extensions.actionbutton.Registro;

/**
 * Metodos necessarios para implementacoes de <b>Botoes de Acao</b>.
 * 
 * <p>
 * Esta interface exige a implementacao do metodo {@code validateRows(Registro[] linhas)} 
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-05-07
 * 
 * @see Registro
 *
 */
public interface ActionButton {

	/**
	 * Verifica se usuario selecionou pelo menos uma linha na tela para execucao de uma 
	 * determinada acao.
	 * 
	 * <p>
	 * Em determinados tipos de acao eh necessario utilizar um ou mais registros 
	 * selecionados pelo usuario na tela.
	 * </p>
	 * 
	 * @param linhas Conjunto de linhas selecionadas em uma tela.
	 * @throws IllegalArgumentException se nao houver nenhum registro selecionado pelo usuario.
	 */
	void validateRows(Registro[] linhas);

	/**
	 * Verifica se usuario selecionou somente uma linha na tela para execucao de uma 
	 * determinada acao.
	 * 
	 * <p>
	 * Em determinados tipos de acao eh necessario utilizar somente um registro 
	 * selecionado pelo usuario na tela.
	 * </p>
	 * 
	 * @param linhas Conjunto de linhas selecionadas em uma tela.
	 * @throws IllegalArgumentException se houver mais de um registro selecionado pelo usuario.
	 */
	void validateUniqueRowSelected(Registro[] linhas);

}
