package tech.skala.action.modules.manufatura.produtoacabado;

import java.math.BigDecimal;
import java.sql.ResultSet;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.manufatura.produtoacabado.ProdutoAcabadoExceptions.StandardProductiveProcessNotFoundException;
import tech.skala.core.commons.constants.SklFieldName;

/**
 * A classe {@code ProdutoAcabadoFinder} prove recursos para busca de informacoes relacionadas 
 * a um {@link ProdutoAcabado}.
 * 
 * @author hiagoalexandre
 * @author cassiomenezes
 * @since 2022-07-29
 *
 */
public class ProdutoAcabadoFinder {
	private BigDecimal						codProdPA;
	private BigDecimal						codPlp;

	public ProdutoAcabadoFinder(BigDecimal codProdPA, BigDecimal codPlp) {
		this.codProdPA = codProdPA;
		this.codPlp = codPlp;
	}

	/**
	 * Este metodo deve retornar um identificador unico de um <b>Processo Produtivo Padrao</b> para 
	 * um {@link ProdutoAcabado}.
	 * 
	 * <p>
	 * Um {@link ProdutoAcabado} pode estar associado a varios <b>Processos Produtivos</b> porem deve 
	 * existir somente um unico definido como <b>Padrão</b>.
	 * </p>
	 * 
	 * @return idProc O identificador do <b>Processo Produtivo Padrao</b> de um {@link ProdutoAcabado}.  
	 * @throws StandardProductiveProcessNotFoundException Caso ocorra um erro na consulta ou caso exista 
	 * mais de um <b>Processo Produtivo Padrao</b> para o sistema.
	 * 
	 */
	public BigDecimal findProcessoProdutivoPadrao() throws StandardProductiveProcessNotFoundException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		BigDecimal idProc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queProcessoProdutivoPadraoProduto.sql");
			sql.setNamedParameter(SklFieldName.CODPRODPA, codProdPA);
			
			if(codPlp != null)
				sql.setNamedParameter(SklFieldName.CODPLP, codPlp);
			
			rs = sql.executeQuery();
			
			int contador = 0;
			while (rs.next()) {
				idProc = rs.getBigDecimal(SklFieldName.IDPROC);
				contador++;
			}
			
			if (contador > 1)
				throw new ProdutoAcabadoExceptions.MultipleStandardProductiveProcessesFoundException(codProdPA);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProdutoAcabadoExceptions.StandardProductiveProcessNotFoundException(e, codProdPA);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}

		return idProc;
	}
	
	/**
	 * Este metodo deve retornar um identificador unico de um <b>Processo Produtivo Terceiro</b> para 
	 * um {@link ProdutoAcabado}.
	 * 
	 * <p>
	 * Um {@link ProdutoAcabado} pode estar associado a varios <b>Processos Produtivos</b> porem deve 
	 * existir somente um unico definido como <b>Terceiro</b>.
	 * </p>
	 * 
	 * @return idProc O identificador do <b>Processo Produtivo Terceiro</b> de um {@link ProdutoAcabado}.  
	 * @throws StandardProductiveProcessNotFoundException Caso ocorra um erro na consulta ou caso exista 
	 * mais de um <b>Processo Produtivo Terceiro</b> para o sistema.
	 * 
	 */
	public BigDecimal findProcessoProdutivoTerceiro() throws StandardProductiveProcessNotFoundException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		BigDecimal idProc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queProcessoProdutivoTerceiroProduto.sql");
			sql.setNamedParameter(SklFieldName.CODPRODPA, codProdPA);

			rs = sql.executeQuery();
			
			int contador = 0;
			while (rs.next()) {
				idProc = rs.getBigDecimal(SklFieldName.IDPROC);
				contador++;
			}
			
			if (contador > 1)
				throw new ProdutoAcabadoExceptions.MultipleStandardProductiveProcessesFoundException(codProdPA);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProdutoAcabadoExceptions.StandardProductiveProcessNotFoundException(e, codProdPA);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}

		return idProc;
	}
	
}
