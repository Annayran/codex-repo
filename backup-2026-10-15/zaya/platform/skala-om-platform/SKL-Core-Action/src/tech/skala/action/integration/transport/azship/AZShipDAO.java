package tech.skala.action.integration.transport.azship;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AZShipDAO {

	private AZShipDAO() {
		throw new IllegalStateException("Utility class");
	}

	public static List<OrderDTO> buscaInformacoesPedido(BigDecimal codEmp, BigDecimal ordemCarga)
			throws SkalaModelException {

		List<OrderDTO> orders = new ArrayList<>();

		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);

			sql.loadSql(AZShipDAO.class.getClass(), "CriaCarga_queDadosPedidos.sql");
			sql.setNamedParameter(SklFieldName.CODEMP, codEmp);
			sql.setNamedParameter(SklFieldName.ORDEMCARGA, ordemCarga);

			rs = sql.executeQuery();

			DadosPedido dadoPedido = null;
			while (rs.next()) {
				dadoPedido = new DadosPedido();
				dadoPedido.loadByResultSet(rs);

				OrderDTO order = (OrderDTO) AZShipMapper.dadosPedido2OrdersDTOMapper(dadoPedido);
				orders.add(order);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar informações para o Pedido", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

		return orders;
	}

	public static BigDecimal buscaCodOcoPeloNomeEvento(String nameEvent) throws SkalaModelException {

		SessionHandle hnd = null;

		BigDecimal codOco = null;
		try {
			hnd = JapeSession.open();

			DynamicVO vo = JapeFactory.dao("AD_PLTAZSCFG").findOne("UPPER(this.DESCROCO) = ?", nameEvent.toUpperCase());
			if (vo != null)
				codOco = vo.asBigDecimal("CODOCOAZS");

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar evento.", e);
		} finally {
			JapeSession.close(hnd);
		}

		return codOco;
	}

	public static ShippingSaveDTO buscaShippingSave(BigDecimal codEmp, BigDecimal ordemCarga)
			throws SkalaModelException {

		String[] targets = getListTransporters(codEmp, ordemCarga);

		ShippingSaveDTO shippingSaveDTO = new ShippingSaveDTO();

		shippingSaveDTO.setCode(ordemCarga.toString());
		shippingSaveDTO.setTargets(targets);
		shippingSaveDTO.setFreightPrice(BigDecimal.ZERO);
		shippingSaveDTO.setTollPrice(BigDecimal.ZERO);
		shippingSaveDTO.setFeeDeliveryPrice(BigDecimal.ZERO);
		shippingSaveDTO.setObservation("");

		return shippingSaveDTO;

	}

	private static String[] getListTransporters(BigDecimal codEmp, BigDecimal ordemCarga) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ORDEM_CARGA);

		String[] targets = new String[1];
		try {
			DynamicVO ordemCargaVO = dao.findByPK(codEmp, ordemCarga);
			String cnpj = ordemCargaVO.asString("Transportadora.CGC_CPF");

			targets[0] = cnpj;
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar transportador para carga", e);
		}

		return targets;
	}
}
