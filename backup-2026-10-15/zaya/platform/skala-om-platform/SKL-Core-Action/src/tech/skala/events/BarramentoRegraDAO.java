package tech.skala.events;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class BarramentoRegraDAO {

	public static final Logger LOGGER = Logger.getLogger(BarramentoRegraDAO.class.getCanonicalName());

	public List<Method> buscaRegrasExclusivas(int tipoEvento, BigDecimal codTipOper, String tipoCarteira)
			throws SkalaModelException {
		
		LOGGER.info("Entrou em buscaRegrasExclusivas");
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		ResultSet rs = null;
		NativeSql sql = null;

		List<Method> metodos = new LinkedList<>();
		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(BarramentoRegraDAO.class, "sql/BarramentoRegraDAO_queRegras.sql");
			sql.setNamedParameter(SklFieldName.CODTIPOPER, codTipOper);
			sql.setNamedParameter(SklFieldName.TIPO, tipoCarteira);
			sql.setNamedParameter("TIPOEVENTO", tipoEvento);

			rs = sql.executeQuery();

			Class<?> barramentoRegraClass = BarramentoRegra.class;
			String methodName;
			Method method;
			while (rs.next()) {
				methodName = rs.getString("METODO");
				method = barramentoRegraClass.getDeclaredMethod(methodName, DynamicVO.class);
				metodos.add(method);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu um erro ao buscar restrições.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return metodos;
	}

}
