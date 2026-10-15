package tech.skala.modules.inventario;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProdutoContagemFinder {

	public static List<ProdutoInventario> buscaProdutosInventario() throws SkalaModelException {

		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		List<ProdutoInventario> produtos = new LinkedList<>();
		ProdutoInventario produtoInventario = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(ProdutoContagemFinder.class, "queProdutosContagem.sql");

			rs = sql.executeQuery();

			ProdutoLote lote = null;
			while (rs.next()) {
				produtoInventario = new ProdutoInventario();
				produtoInventario.setCodProd(rs.getBigDecimal(SklFieldName.CODPROD));

				if (produtos.contains(produtoInventario)) {
					lote = new ProdutoLote();
					lote.loadByResultSet(rs);

					produtos.get(produtos.indexOf(produtoInventario)).getProdutoLotes().add(lote);
				} else {
					produtoInventario.loadByResultSet(rs);
					produtos.add(produtoInventario);
				}
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}

		return produtos;

	}
	
	public static List<ProdutoContagem> buscaProdutosContagem(ContagemInventario contagem)
			throws SkalaModelException {

		List<ProdutoContagem> produtos = new LinkedList<>();

		ProdutoContagem produtoContagem = null;

		try {
			JapeWrapper dao = JapeFactory.dao(SklEntityName.PRODUTO_CONTAGEM);
			String where = "this.IDIVT = ? AND this.CODEMP = ? AND this.CODLOCAL = ? AND this.CODPARC = ? AND this.TIPO = ? AND this.SEQUENCIA = ?";

			Collection<DynamicVO> produtosVO = dao.find(where, contagem.getIdIvt(), contagem.getCodEmp(),
					contagem.getCodLocal(), contagem.getCodParc(), contagem.getTipo(), contagem.getSequencia());
			
			for(DynamicVO vo : produtosVO) {
				produtoContagem = new ProdutoContagem();
				produtoContagem.loadByVO(vo);
				produtos.add(produtoContagem);
				
			}
				
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}
		return produtos;

	}
}
