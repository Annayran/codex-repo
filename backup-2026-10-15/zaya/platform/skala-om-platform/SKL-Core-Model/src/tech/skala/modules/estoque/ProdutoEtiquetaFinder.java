package tech.skala.modules.estoque;

import java.math.BigDecimal;
import java.sql.ResultSet;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ProdutoEtiquetaFinder {

	private ProdutoEtiquetaFinder() {
	
	}
	
	public static ProdutoEtiquetaDTO getProdutoEtiqueta(BigDecimal codProd) throws RecordNotFoundException {
	
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		ProdutoEtiquetaDTO produtoEtiqueta = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(ProdutoEtiquetaDTO.class, "queProdutoEtiqueta.sql");
			sql.setNamedParameter(SklFieldName.CODPROD, codProd);

			rs = sql.executeQuery();

			if(rs.next()) {
				produtoEtiqueta = new ProdutoEtiquetaDTO();
				produtoEtiqueta.loadByResultSet(rs);
			}

		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}

		return produtoEtiqueta;
		
	}
	
}
