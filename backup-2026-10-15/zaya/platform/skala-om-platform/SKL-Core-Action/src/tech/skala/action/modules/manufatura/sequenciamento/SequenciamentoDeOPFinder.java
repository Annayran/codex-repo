package tech.skala.action.modules.manufatura.sequenciamento;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * A classe {@code SequenciamentoDeOPFinder} prove recursos para busca de
 * informacoes relacionadas a um {@link SequenciamentoDeOP}.
 * 
 * @author hiagoalexandre
 * @since 2022-08-08
 *
 */
public class SequenciamentoDeOPFinder {

	public Collection<BigDecimal> findOrdensProducao(BigDecimal seqOP) throws RecordNotFoundException {
		
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;
		
		Collection<BigDecimal> ordensProducao = new ArrayList<>();
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queOrdensProducaoPorSequenciamento.sql");
			sql.setNamedParameter("SEQOP", seqOP);
			
			rs = sql.executeQuery();

			while (rs.next()) 
				ordensProducao.add(rs.getBigDecimal(SklFieldName.IDIPROC));
		
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
		return ordensProducao;
	}
	
	public BigDecimal findQtdProduzir(BigDecimal seqOP) throws RecordNotFoundException {
		
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;
		
		BigDecimal qtdTotalAProduzir = BigDecimal.ZERO;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queOrdensProducaoPorSequenciamento.sql");
			sql.setNamedParameter("SEQOP", seqOP);
			
			rs = sql.executeQuery();

			while (rs.next()) 
				qtdTotalAProduzir = qtdTotalAProduzir.add(rs.getBigDecimal(SklFieldName.QTDPRODUZIR));
		
		} catch (Exception e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
		return qtdTotalAProduzir;
	}

}
