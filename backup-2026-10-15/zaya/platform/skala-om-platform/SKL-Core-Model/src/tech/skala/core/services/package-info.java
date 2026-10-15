/**
 * Prove os servicos (<i>Service Providers</i>) que irao expor as interfaces de aplicacao 
 * utilizadas em requisicoes por outras aplicacoes incluindo aplicacoes moveis.
 * 
 * <p>
 * Neste pacote devem ser incluidas classes especiais que irao gerar servicoes que serao 
 * expostos para chamadas via <b>API</b>.<br>
 * Estas classes e respectivos metodos devem utilizar as anotacoes especificas para a correta 
 * geracao dos <b>EJB</b> (<i>Enterprise Java Bean</i>) que serao inicializados pelo servidor.
 * <pre>
 * <code>
 * http://192.0.4.112:8180/skl/service.sbr?serviceName=ExemploSP.metodoExemplo&mgeSession=b0ngvGP0cy-M5GS13hUK4lhbom3wPqCIE6hq1Kdl&outputType=json
 * </code>
 * </pre>
 * </p>
 * <p>
 * E necessario inserir a configuracao da classe no arquivo:<br>
 * <code>
 * 	SKL-Core-VC/build/WEB-INF/resources/service-providers.xml
 * </code> 
 * </p>
 * 
 * @author cassiomenezes
 * @since 2022-07-13
 *
 */
package tech.skala.core.services;