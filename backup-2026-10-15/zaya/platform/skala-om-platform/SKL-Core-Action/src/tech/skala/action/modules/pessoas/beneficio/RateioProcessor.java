package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;
import java.sql.ResultSet;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class RateioProcessor {
	
	private BigDecimal nuNota;
	private DynamicVO vo;
	
	RateioProcessor(BigDecimal nuNota) {
		this.nuNota = nuNota;
	}
	
	public void geraRateio(BigDecimal codCbe) throws SkalaModelException {
		carregaNotaVO();
		gerarRateio(codCbe);
	}

	private void gerarRateio(BigDecimal codCbe) throws SkalaModelException {
		BigDecimal codNat = vo.asBigDecimal("CODNAT");
		BigDecimal codParc = vo.asBigDecimal("CODPARC");
		BigDecimal vlrNota = vo.asBigDecimal("VLRNOTA");
		
		NativeSql queValores = null;
		ResultSet rsValores = null;
		
		Rateio rateio;
		JdbcWrapper jdbc = null;
		
		
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			queValores = new NativeSql(jdbc);
			
			queValores.loadSql(getClass(), "RateioProcessor_queRateio.sql");
			queValores.setNamedParameter("VLRNOTA", vlrNota);
			queValores.setNamedParameter("CODCBE", codCbe);
			
			rsValores = queValores.executeQuery();
			
			while (rsValores.next()) {
				rateio = new Rateio();
				rateio.loadByResultSet(rsValores);
				rateio.setNuFin(nuNota);
				rateio.setCodNat(codNat);
				rateio.setCodParc(codParc);
				rateio.setCodUsu(AuthenticationInfo.getCurrent().getUserID());
				rateio.persists();
			}
		} catch (Exception e) {
			String msg = "Não foi possível gerar rateio.";
			SkalaModelException.throwMe( msg, e);
		} finally {
			JdbcUtils.closeResultSet(rsValores);
			NativeSql.releaseResources(queValores);
			JdbcWrapper.closeSession(jdbc);
			
		}
	}

	private void carregaNotaVO() throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao (DynamicEntityNames.CABECALHO_NOTA);
		
		try {
			vo = dao.findByPK(nuNota);
		} catch (Exception e) {
			throw new SkalaModelException("Não foi possivel encontrar nota.", e);
		}
		
	}	
}
