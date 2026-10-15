package tech.skala.action.modules.wms.extrato;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.wms.extrato.ExtratoWmsExceptions.InvoiceNotFoudException;
import tech.skala.action.modules.wms.extrato.ExtratoWmsExceptions.TaskNotFoundException;
import tech.skala.core.commons.constants.SklFieldName;

public class TarefasFinder {
	private BigDecimal						codProd;
	private BigDecimal						numNotaRec;
	private BigDecimal						sequenciaRec;
	private BigDecimal						nuTarefaInicial;
	private BigDecimal						codEndInicial;
	private BigDecimal						qtdVolPadRecebida;

	public TarefasFinder(BigDecimal codProd, BigDecimal numNotaRec, BigDecimal sequenciaRec, BigDecimal nuTarefaInicial, BigDecimal codEndInicial, BigDecimal qtdVolPadRecebida) {
		this.codProd = codProd;
		this.numNotaRec = numNotaRec;
		this.sequenciaRec = sequenciaRec;
		this.nuTarefaInicial = nuTarefaInicial;
		this.codEndInicial = codEndInicial;
		this.qtdVolPadRecebida = qtdVolPadRecebida;
	}

	public void getProximasTarefas(List<TarefaWms> tarefasWms) throws TaskNotFoundException {
		TarefaWms tarefa = getProximaTarefa(tarefasWms);
		
		if (tarefa != null) {			
			this.nuTarefaInicial = tarefa.getNuTarefa();
			this.codEndInicial = tarefa.getCodEndDestino();
			
			getProximasTarefas(tarefasWms);
		}
		
	}
	
	private TarefaWms getProximaTarefa(List<TarefaWms> tarefasWms) throws TaskNotFoundException {
		Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		TarefaWms tarefa = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.setReuseStatements(true);
			sql.cleanParameters();
			sql.loadSql(this.getClass(), "queProximaTarefa.sql");
			sql.setNamedParameter(SklFieldName.CODPROD, codProd);
			sql.setNamedParameter("NUTAREFAINICIAL", nuTarefaInicial);
			sql.setNamedParameter("CODENDINICIAL", codEndInicial);
			
			rs = sql.executeQuery();

			BigDecimal nuTarefa;
			BigDecimal sequencia;
			BigDecimal qtdVolPadAcumulada = BigDecimal.ZERO;
			while (rs.next() && qtdVolPadAcumulada.compareTo(qtdVolPadRecebida) < 1) {
				nuTarefa = BigDecimalUtil.getValueOrZero(rs.getBigDecimal(SklFieldName.NUTAREFA));
				sequencia = BigDecimalUtil.getValueOrZero(rs.getBigDecimal(SklFieldName.SEQUENCIA));
				
				tarefa = new TarefaWms(numNotaRec, sequenciaRec, nuTarefa, sequencia);
				tarefa.loadByResultSet(rs);
				
				qtdVolPadAcumulada = qtdVolPadAcumulada.add(tarefa.getQtdVolPad());
				
				String msg = String.format("getNotaVenda ordemCarga %s qtdVolPadAcumulada %s", tarefa.getOrdemCarga(), qtdVolPadAcumulada);
				logger.info(msg);

				if (tarefa.getOrdemCarga() != null)
					getNotaVenda(tarefa);

				msg = String.format("getNotaVenda notaVenda %s", tarefa.getNotaVenda());
				logger.info(msg);

				tarefasWms.add(tarefa);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExtratoWmsExceptions.TaskNotFoundException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
		return tarefa;
	}

	private void getNotaVenda(TarefaWms tarefa) throws InvoiceNotFoudException {
		Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
		
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;
		
		String msg = String.format("getNotaVenda codEmp %s ordemCarga %s codProd %s.", tarefa.getCodEmp(),
				tarefa.getOrdemCarga(), tarefa.getCodProd());
		logger.info(msg);

		NotaVenda notaVenda = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.setReuseStatements(true);
			sql.cleanParameters();
			sql.loadSql(this.getClass(), "queNotaVenda.sql");
			sql.setNamedParameter(SklFieldName.CODEMP, tarefa.getCodEmp());
			sql.setNamedParameter(SklFieldName.ORDEMCARGA, tarefa.getOrdemCarga());
			sql.setNamedParameter(SklFieldName.CODPROD, tarefa.getCodProd());
			
			rs = sql.executeQuery();

			while (rs.next()) {
				notaVenda = new NotaVenda();
				notaVenda.loadByResultSet(rs);
				tarefa.setNotaVenda(notaVenda);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExtratoWmsExceptions.InvoiceNotFoudException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
	}
}
