package tech.skala.action.modules.wms.extrato;

import java.math.BigDecimal;
import java.sql.ResultSet;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.wms.extrato.ExtratoWmsExceptions.InvoiceNotFoudException;
import tech.skala.core.commons.constants.SklFieldName;

public class NotaVendaFinder {
	private BigDecimal						codProd;
	private BigDecimal						codEmp;
	private BigDecimal						ordemCarga;
	
	public NotaVendaFinder(BigDecimal codProd, BigDecimal codEmp, BigDecimal ordemCarga) {
		this.codProd = codProd;
		this.codEmp = codEmp;
		this.ordemCarga = ordemCarga;
	}

	public NotaVenda getNotaVenda() throws InvoiceNotFoudException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		NotaVenda notaVenda = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.setReuseStatements(true);
			sql.cleanParameters();
			sql.loadSql(this.getClass(), "queNotaVenda.sql");
			sql.setNamedParameter(SklFieldName.CODEMP, codEmp);
			sql.setNamedParameter(SklFieldName.ORDEMCARGA, ordemCarga);
			sql.setNamedParameter(SklFieldName.CODPROD, codProd);
			
			rs = sql.executeQuery();
			while (rs.next()) {
				notaVenda = new NotaVenda();
				notaVenda.loadByResultSet(rs);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExtratoWmsExceptions.InvoiceNotFoudException(e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
		return notaVenda;
	}

}
