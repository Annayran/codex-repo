package tech.skala.action.modules.manufatura.produtoacabadoaproduzir;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.action.modules.manufatura.produtoacabadoaproduzir.ProdutoAcabadoAProduzirExceptions.ProductNotFoundException;

/**
 * A classe {@code ProdutoAcabadoAProduzirFinder} prove recursos para busca de informacoes relacionadas 
 * a um {@link ProdutoAcabadoAProduzir}.
 * 
 * @author hiagoalexandre
 * @since 2022-08-01
 *
 */
public class ProdutoAcabadoAProduzirFinder {
	
	private static final String ENTITY_NAME = DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR;
	
	/**
	 * Este metodo deve retornar uma coleção contendo um ou mais registros de <b>Produtos Acabados a Produzir</b>.
	 * 
	 * </p>
	 * O método findProdutosAcabadosAProduzir retorna uma coleção de Produtos Acabados a Produzir, a partir de um
	 * número de OP.
	 * 
	 * @param idiProc O identificador de uma <b>Ordem Produção</b>.
	 * @throws ProductNotFoundException Caso ocorra um erro na consulta ou caso não exista
	 * <b>Produtos Acabados A Produzir</b> para este Número de Ordem de Produção Processo Produtivo Padrao.
	 */
	public static Collection<DynamicVO> findProdutosAcabadosAProduzir(BigDecimal idiProc) throws ProductNotFoundException {
		JapeWrapper produtoAcabadoAProduzirDAO = JapeFactory.dao(ENTITY_NAME);
		
		Collection<DynamicVO> produtosAcabadosVO = new ArrayList<>();
		try {
			produtosAcabadosVO = produtoAcabadoAProduzirDAO.find("this.IDIPROC = ?", idiProc);
		} catch (Exception e) {
			throw new ProdutoAcabadoAProduzirExceptions.ProductNotFoundException(e);
		}
		
		return produtosAcabadosVO;
	}
	
	public static Collection<DynamicVO> findProdutosAcabadosAProduzir(BigDecimal codProdPA, String prefixoLote)
			throws ProductNotFoundException {
		JapeWrapper produtoAcabadoAProduzirDAO = JapeFactory.dao(ENTITY_NAME);

		Collection<DynamicVO> produtosAcabadosVO = new ArrayList<>();
		try {
			produtosAcabadosVO = produtoAcabadoAProduzirDAO.find(
					"this.CODPRODPA = ? AND this.NROLOTE = (select MAX(T.NROLOTE) from TPRIPA T where T.NROLOTE LIKE '?%' AND T.CODPRODPA = this.CODPRODPA)",
					codProdPA, prefixoLote);
		} catch (Exception e) {
			throw new ProdutoAcabadoAProduzirExceptions.ProductNotFoundException(e);
		}

		return produtosAcabadosVO;
	}
	
}
