package tech.skala.core.beans.interfaces;

/**
 * A interface {@code EntityBean} marca classes que representam objetos de negocio 
 * utilizados pelas diversas aplicações.
 * 
 * <p>
 * Classes que representem objetos de negocio, principalmente, aqueles relacionados 
 * a entidades de banco de dados, devem implementar esta interface para identificacao.<br>
 * Você pode implementar uma classe simples sem esta interface quando nao houver 
 * necessidade de persistencia ou interacao com banco de dados.
 * </p>
 * <p>
 * Utilizar em conjunto com pelo menos uma interface do tipo {@code Loadable} para 
 * implementar os metodos de carregamento ou de persistencia.
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-06-29
 * 
 * @see LoadableByPrimaryKey
 * @see LoadableByRecord
 * @see LoadableByResultSet
 * @see LoadableBySql
 *
 */
public interface EntityBean {

}
