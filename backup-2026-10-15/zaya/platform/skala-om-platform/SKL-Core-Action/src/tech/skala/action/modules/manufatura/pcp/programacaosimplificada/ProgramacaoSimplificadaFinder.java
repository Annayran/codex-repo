package tech.skala.action.modules.manufatura.pcp.programacaosimplificada;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ProgramacaoSimplificadaFinder {
	
	private ProgramacaoSimplificadaFinder() {
		throw new IllegalStateException("Utility class");
	}

	public static List<BigDecimal> findOrdensProducao(BigDecimal nuPrgP) throws RecordNotFoundException {
		
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;
		
		List<BigDecimal> ordensProducao = new LinkedList<>();
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.loadSql(ProgramacaoSimplificadaFinder.class, "queOrdensProducaoPorProgramacao.sql");
			sql.setNamedParameter("NUPRGP", nuPrgP);
			
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
	

	public static List<BigDecimal> findSequenciamentoProducaoDuplicado(BigDecimal nuPrgP) throws RecordNotFoundException {
		
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;
		
		List<BigDecimal> ordensProducao = new LinkedList<>();
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.loadSql(ProgramacaoSimplificadaFinder.class, "queValidaSequenciamentoProducao.sql");
			sql.setNamedParameter("NUPRGP", nuPrgP);
			
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
}
