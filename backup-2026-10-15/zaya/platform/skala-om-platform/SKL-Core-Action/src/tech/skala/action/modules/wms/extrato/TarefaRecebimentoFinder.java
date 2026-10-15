package tech.skala.action.modules.wms.extrato;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class TarefaRecebimentoFinder {
	private BigDecimal						codProd;
	private BigDecimal						nuNotaEntrada;
	
	public TarefaRecebimentoFinder(BigDecimal codProd, BigDecimal nuNotaEntrada) {
		this.codProd = codProd;
		this.nuNotaEntrada = nuNotaEntrada;
	}

	public List<TarefaWms> getTarefasRecebimento() throws SkalaModelException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		List<TarefaWms> tarefasRecebimento = new LinkedList<>();
		TarefaWms tarefa = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queTarefaRecebimento.sql");
			sql.setNamedParameter(SklFieldName.CODPROD, codProd);
			sql.setNamedParameter(SklFieldName.NUNOTA, nuNotaEntrada);
			
			rs = sql.executeQuery();

			BigDecimal numNotaRec;
			BigDecimal sequenciaRec;
			BigDecimal nuTarefa;
			BigDecimal sequencia;
			while (rs.next()) {
				numNotaRec = BigDecimalUtil.getValueOrZero(rs.getBigDecimal("NUMNOTAREC"));
				sequenciaRec = BigDecimalUtil.getValueOrZero(rs.getBigDecimal("CODENDDESTINO"));
				nuTarefa = BigDecimalUtil.getValueOrZero(rs.getBigDecimal(SklFieldName.NUTAREFA));
				sequencia = BigDecimalUtil.getValueOrZero(rs.getBigDecimal(SklFieldName.SEQUENCIA));
				
				tarefa = new TarefaWms(numNotaRec, sequenciaRec, nuTarefa, sequencia);
				tarefa.loadByResultSet(rs);
				
				tarefasRecebimento.add(tarefa);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExtratoWmsExceptions.ReceivingNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
		return tarefasRecebimento;
	}

}
