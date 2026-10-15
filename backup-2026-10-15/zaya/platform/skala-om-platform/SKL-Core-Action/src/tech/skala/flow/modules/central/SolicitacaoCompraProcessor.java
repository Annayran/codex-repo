package tech.skala.flow.modules.central;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.sankhya.util.JdbcUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.AtributosRegras;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ListenerParameters;
import tech.skala.action.modules.centrais.CabecalhoNotaPlt;
import tech.skala.core.beans.cabecalhonota.CabecalhoNotaProcessor;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class SolicitacaoCompraProcessor {

	public void gerarSolicitacaoCompra(BigDecimal idSolicitacao) throws SkalaModelException {

		ResultSet rsCabecalho = null;
		BigDecimal nuNota;
		try {
			rsCabecalho = SolicitacaoCompraFinder.buscaInfoCabecalhoByIdSolicitacao(idSolicitacao);

			while (rsCabecalho.next()) {

				DynamicVO cabecalhoVO = setCabecalhoSolicitacao(rsCabecalho);

				if (cabecalhoVO != null) {
					nuNota = cabecalhoVO.asBigDecimal(SklFieldName.NUNOTA);
					relacionaSolicitacaoLancamento(nuNota, idSolicitacao);

					BigDecimal codParc = rsCabecalho.getBigDecimal(SklFieldName.CODPARC);

					setItensSolicitacao(nuNota, idSolicitacao, codParc);

//					CabecalhoNotaProcessor.confirmarNota(nuNota);
				}

			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar pedido de Solicitação de Compras.", e);
		} finally {
			JdbcUtils.closeResultSet(rsCabecalho);
		}

	}

	private DynamicVO setCabecalhoSolicitacao(ResultSet rsCabecalho) throws SkalaModelException {

		DynamicVO cabVO = null;
		DynamicVO cabecalhoVO = null;
		try {
			BigDecimal codEmp = rsCabecalho.getBigDecimal(SklFieldName.CODEMP);
			String observ = rsCabecalho.getString("OBSER");
			BigDecimal codUsu = rsCabecalho.getBigDecimal(SklFieldName.CODUSU);
			BigDecimal codNat = rsCabecalho.getBigDecimal("CODNAT");
			BigDecimal codTipVenda = rsCabecalho.getBigDecimal("CODTIPVENDA");
			BigDecimal codParc = rsCabecalho.getBigDecimal(SklFieldName.CODPARC);
			BigDecimal codTipOper = rsCabecalho.getBigDecimal(SklFieldName.CODTIPOPER);
			Timestamp dtPreEnt = rsCabecalho.getTimestamp("DTPREVENT");
			BigDecimal codCenCus = rsCabecalho.getBigDecimal(SklFieldName.CODCENCUSPAD);
			BigDecimal codComprador = rsCabecalho.getBigDecimal("COMPRADOR");

			cabVO = (DynamicVO) EntityFacadeFactory.getDWFFacade()
					.getDefaultValueObjectInstance(DynamicEntityNames.CABECALHO_NOTA);
			cabVO.setProperty(SklFieldName.CODEMP, codEmp);
			cabVO.setProperty(SklFieldName.OBSERVACAO, observ);
			cabVO.setProperty(SklFieldName.TIPMOV, "O");
			cabVO.setProperty("CODUSUINC", codUsu);
			cabVO.setProperty("CODNAT", codNat);
			cabVO.setProperty("CODTIPVENDA", codTipVenda);
			cabVO.setProperty(SklFieldName.DTNEG, TimeUtils.getNow());
			cabVO.setProperty(SklFieldName.CODTIPOPER, codTipOper);
			cabVO.setProperty(SklFieldName.CODPARC, codParc);
			cabVO.setProperty("DTPREVENT", dtPreEnt);
			cabVO.setProperty(SklFieldName.CODCENCUS, codCenCus);
			cabVO.setProperty(SklFieldName.NUMNOTA, BigDecimal.ZERO);
			cabVO.setProperty(SklFieldName.STATUSNOTA, "A");
			cabVO.setProperty(SklFieldName.CODVEND, codComprador);

			cabecalhoVO = incluirCabecalhoSolicitacao(cabVO);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar informações para criar a Solicitação de Compra.", e);
		}

		return cabecalhoVO;
	}

	private DynamicVO incluirCabecalhoSolicitacao(DynamicVO cabVO) throws SkalaModelException {

		JapeSessionContext.putProperty(ListenerParameters.CENTRAIS, Boolean.TRUE);
		JapeSessionContext.putProperty(AtributosRegras.INC_UPD_ITEM_CENTRAL, Boolean.TRUE);

		DynamicVO cabecalhoVO = null;
		try {
			PrePersistEntityState cabPreState = PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(),
					DynamicEntityNames.CABECALHO_NOTA, cabVO);

			CACHelper cacHelper = new CACHelper();
			BarramentoRegra bRegrasCab = cacHelper.incluirAlterarCabecalho(AuthenticationInfo.getCurrent(),
					cabPreState);
			cabecalhoVO = bRegrasCab.getState().getNewVO();

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir o Cabeçalho do Lançamento.", e);
		}

		return cabecalhoVO;

	}

	private void relacionaSolicitacaoLancamento(BigDecimal nuNota, BigDecimal idSolicitacao)
			throws SkalaModelException {
		try {
			CabecalhoNotaPlt cabecalhoNotaPlt = new CabecalhoNotaPlt();
			cabecalhoNotaPlt.setNuNota(nuNota);
			cabecalhoNotaPlt.setIdInstPrn(idSolicitacao);

			cabecalhoNotaPlt.persists();
		} catch (InsertNotPossibleException | UpdateNotPossibleException e) {
			SkalaModelException.throwMe("Não foi possível relacionar ID da Solicitação ao Lançamento.", e);
		}
	}

	private void setItensSolicitacao(BigDecimal nuNota, BigDecimal idSolicitacao, BigDecimal codParc)
			throws SkalaModelException {
		ResultSet rsItens = null;
		try {
			rsItens = SolicitacaoCompraFinder.buscaInfoItensByIdSolicitacao(idSolicitacao, codParc);

			CACHelper cacHelper = new CACHelper();
			Collection<PrePersistEntityState> itensNota = new ArrayList<>();

			while (rsItens.next())
				loadItensSolicitacao(nuNota, rsItens, itensNota);

			cacHelper.incluirAlterarItem(nuNota, AuthenticationInfo.getCurrent(), itensNota, true);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar pedido de Solicitação de Compras.", e);

		} finally {
			JdbcUtils.closeResultSet(rsItens);
		}

	}

	private void loadItensSolicitacao(BigDecimal nuNota, ResultSet rsItens, Collection<PrePersistEntityState> itensNota)
			throws SkalaModelException {

		try {
			BigDecimal codProd = rsItens.getBigDecimal(SklFieldName.CODPROD);
			BigDecimal quantidade = rsItens.getBigDecimal("QTDSOLIC");
			BigDecimal vlrUnit = rsItens.getBigDecimal(SklFieldName.VLRUNIT);
			BigDecimal vlrTot = rsItens.getBigDecimal("VLRTOT");
			String codVol = rsItens.getString(SklFieldName.CODVOL);
			String usoProd = rsItens.getString(SklFieldName.USOPROD);
			BigDecimal localPadrao = rsItens.getBigDecimal("LOCALPAD");
			Timestamp dtPrevEnt = rsItens.getTimestamp("DTPREVENT");

			DynamicVO itemVO = (DynamicVO) EntityFacadeFactory.getDWFFacade()
					.getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);
			itemVO.setProperty(SklFieldName.NUNOTA, nuNota);
			itemVO.setProperty(SklFieldName.CODPROD, codProd);
			itemVO.setProperty(SklFieldName.QTDNEG, quantidade);
			itemVO.setProperty(SklFieldName.VLRUNIT, vlrUnit);
			itemVO.setProperty("VLRTOT", vlrTot);
			itemVO.setProperty(SklFieldName.CODVOL, codVol);
			itemVO.setProperty(SklFieldName.USOPROD, usoProd);
			itemVO.setProperty("ATUALESTOQUE", BigDecimal.ZERO);
			itemVO.setProperty(SklFieldName.CODLOCALORIG, localPadrao);
			itemVO.setProperty(SklFieldName.DTINICIO, dtPrevEnt);
			

			PrePersistEntityState itePreState = PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(),
					DynamicEntityNames.ITEM_NOTA, itemVO);
			itensNota.add(itePreState);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível buscar informações para criar a Solicitação de Compra.", e);
		}

	}

}
