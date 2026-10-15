package br.com.zaya.like.Actions.repositories;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.DynamicVOPojo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public class CancelarPedidoCompraRepository {
	

	public Collection<DynamicVO> buscarItensPedidoVendaDaNegociacao(BigDecimal nuNegociacao, BigDecimal codProd) throws Exception {
		Collection<DynamicVO> itensNota = new ArrayList<DynamicVO>();
		JdbcWrapper jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();

		ResultSet resultSet = null;
		NativeSql sql = null;

		try {
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queBuscaItemPedidoVenda.sql");

			sql.setNamedParameter("AD_NUNEGOCIACAO", nuNegociacao);
			sql.setNamedParameter("CODPROD", codProd);

			resultSet = sql.executeQuery();

			DynamicVO itemNotaVO = null;
			while (resultSet.next()) {
				itemNotaVO = new DynamicVOPojo();
				itemNotaVO.setProperty("NUNOTA", resultSet.getBigDecimal("NUNOTA"));
				itemNotaVO.setProperty("TIPMOV", resultSet.getString("TIPMOV"));
				itemNotaVO.setProperty("CODPROD", resultSet.getBigDecimal("CODPROD"));
				itemNotaVO.setProperty("SEQUENCIA", resultSet.getBigDecimal("SEQUENCIA"));
				itemNotaVO.setProperty("AD_SEQUENCIAPNE", resultSet.getBigDecimal("AD_SEQUENCIAPNE"));
				
				itensNota.add(itemNotaVO);
			}
			
			return itensNota;

		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
	}
	
	public Collection<DynamicVO> buscarItensPedidoRequisicaoDaNegociacao(BigDecimal nuNegociacao, BigDecimal codProd) throws Exception {
		Collection<DynamicVO> itensNota = new ArrayList<DynamicVO>();
		JdbcWrapper jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();

		ResultSet resultSet = null;
		NativeSql sql = null;

		try {
			sql = new NativeSql(jdbc);
			sql.loadSql(this.getClass(), "queBuscaItemPedidoReqisicao.sql");

			sql.setNamedParameter("AD_NUNEGOCIACAO", nuNegociacao);
			sql.setNamedParameter("CODPROD", codProd);

			resultSet = sql.executeQuery();

			DynamicVO itemNotaVO = null;
			while (resultSet.next()) {
				itemNotaVO = new DynamicVOPojo();
				itemNotaVO.setProperty("NUNOTA", resultSet.getBigDecimal("NUNOTA"));
				itemNotaVO.setProperty("TIPMOV", resultSet.getString("TIPMOV"));
				itemNotaVO.setProperty("CODPROD", resultSet.getBigDecimal("CODPROD"));
				itemNotaVO.setProperty("SEQUENCIA", resultSet.getBigDecimal("SEQUENCIA"));
				
				itensNota.add(itemNotaVO);
			}
			
			return itensNota;

		} finally {
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
	}

}
