package tech.skala.modules.suprimentos.recebimento;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.StringUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityDAO;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.CentralItemNota;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ListenerParameters;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.beans.suprimento.recebimento.Conferencia;
import tech.skala.core.beans.suprimento.recebimento.ConferenciaItem;
import tech.skala.core.beans.suprimento.recebimento.ConferenciaSituacaoEnum;
import tech.skala.core.beans.suprimento.recebimento.Recebimento;
import tech.skala.core.beans.suprimento.recebimento.RecebimentoConferencia;
import tech.skala.core.beans.suprimento.recebimento.RecebimentoSituacaoEnum;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InvalidFieldException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaExceptions.DataEntradaSaidaNaoAtualizadaException;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaExceptions.LancamentoSemItensException;

public class RecebimentoMercadoriaProcessor {
	
	/**
	 * O método {@code buildRecebimento} é responsável por construir um recebimento
	 * através do numero único da nota e o código do parceiro.
	 * 
	 * @param codParc Paramêtro que guarda a informação do parceiro.
	 * @param nuNota Paramêtro que guarda a informação da nota fiscal.
	 * @param numNota 
	 * @param cnpj 
	 * @param cnpj 
	 * @return recebimento Retorno que quarda as informações do recebimento.
	 * @throws SkalaModelException 
	 */
	public Recebimento buildRecebimento(final BigDecimal nuNota, final BigDecimal codParc, BigDecimal numNota, String cnpj)
			throws SkalaModelException {
		SessionHandle hnd = null;
		final Recebimento recebimento = new Recebimento();
		
		containsItens(nuNota);

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					cadastrarRecebimento(nuNota, codParc, numNota, cnpj, recebimento);
					
					ConferenciaProcessor conferenciaProcessor = new ConferenciaProcessor();
					Conferencia conferencia = conferenciaProcessor.cadastrarConferencia(nuNota, codParc, recebimento);

					relacionarConferenciaRecebimento(recebimento.getNuRecebimento(), conferencia.getNuConferencia());
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
			throw new RecebimentoMercadoriaExceptions.RecebimentoNaoCadastradoException(e);
		} finally {
			JapeSession.close(hnd);
		}

		return recebimento;
	}
	
	private void containsItens(BigDecimal nuNota) throws LancamentoSemItensException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;
		
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			sql = new NativeSql(jdbc);
			sql.appendSql("SELECT this.NUNOTA, this.SEQUENCIA FROM TGFITE this WHERE this.NUNOTA = :NUNOTA");
			sql.setNamedParameter("NUNOTA", nuNota);
			
			BigDecimal sequencia = BigDecimal.ZERO;
			rs = sql.executeQuery();
			while (rs.next()) {
				sequencia = BigDecimalUtil.getValueOrZero(rs.getBigDecimal(SklFieldName.SEQUENCIA));
			}
			
			if (sequencia.compareTo(BigDecimal.ZERO) == 0)
				throw new RecebimentoMercadoriaExceptions.LancamentoSemItensException(nuNota);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecebimentoMercadoriaExceptions.LancamentoSemItensException(e, nuNota);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
	}
	
	private void cadastrarRecebimento(final BigDecimal nuNota, final BigDecimal codParc, BigDecimal numNota, String cnpj, final Recebimento recebimento)
			throws InsertNotPossibleException, UpdateNotPossibleException {
		recebimento.setNuNota(nuNota);
		recebimento.setCodParc(codParc);
		recebimento.setNumNota(numNota);
		recebimento.setCnpj(cnpj);
		recebimento.setSituacao(RecebimentoSituacaoEnum.EM_PROCESSO_CONFERENCIA.getValue());
		recebimento.persists();
	}
	
	private void relacionarConferenciaRecebimento(BigDecimal nuRecebimento, BigDecimal nuConferencia)
			throws InsertNotPossibleException, InvalidFieldException, UpdateNotPossibleException {
		RecebimentoConferencia relacionamento = new RecebimentoConferencia();
		relacionamento.setNuRecebimento(nuRecebimento);
		relacionamento.setNuConferencia(nuConferencia);
		relacionamento.persists();
	}
	
	/**
	 * O método {@code processaConferenciaEntrada} é responsável por processar um recebimento.
	 * 
	 * @param recebimento
	 * @throws SkalaModelException
	 */
	public void processaConferenciaEntrada(Recebimento recebimento) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					finalizarRecebimento(recebimento);
					
					recebimento.getConferencia().persists();
					
					validaLotes(recebimento);
					if (RecebimentoSituacaoEnum.CONCLUIDO.getValue().equals(recebimento.getSituacao()))
						atualizarDataEntradaSaida(recebimento);
				}
			});
			

		} catch (Exception e) {
			throw new RecebimentoMercadoriaExceptions.ConferenciaNaoProcessadaException(e, recebimento.getNuRecebimento());
		} finally {
			JapeSession.close(hnd);
		}

	}

	/**
	 * O método {@code finalizarRecebimento} é responsável por finalizar um recebimento. 
	 * 
	 * @param recebimento Objeto que guarda as informações do recebimento.
	 * @throws SkalaModelException 
	 */
	public void finalizarRecebimento(Recebimento recebimento) throws SkalaModelException {

		ConferenciaProcessor conferenciaProcessor = new ConferenciaProcessor();
		boolean temDivergencia = conferenciaProcessor.verificarDivergencia(recebimento.getConferencia());
		

		try {
			if (temDivergencia) {
				recebimento.setSituacao(RecebimentoSituacaoEnum.CONFERENCIA_DIVERGENCIA.getValue());
				recebimento.getConferencia().setSituacao(ConferenciaSituacaoEnum.FINALIZADA_DIVERGENTE.getValue());
			} else {
				recebimento.setSituacao(RecebimentoSituacaoEnum.CONCLUIDO.getValue());
				recebimento.getConferencia().setSituacao(ConferenciaSituacaoEnum.FINALIZADA_OK.getValue());
			}
			
			processaItensAdicionais(recebimento);

			recebimento.getConferencia().setDhFinalConf(TimeUtils.getNow());
			recebimento.getConferencia().persists();

			recebimento.persists();

		} catch (Exception e) {
			throw new RecebimentoMercadoriaExceptions.RecebimentoNaoFinalizadoException(e);
		}
		
	}
	
	private void processaItensAdicionais(Recebimento recebimento) throws SkalaModelException {

		JapeSessionContext.putProperty(ListenerParameters.CENTRAIS, Boolean.TRUE);
		JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.TRUE);

		Map<ConferenciaItem, LinkedList<ConferenciaItem>> itensMap = new HashMap<>();
		for (ConferenciaItem conferenciaItem : recebimento.getConferencia().getItens()) {

			if (itensMap.containsKey(conferenciaItem)) {
				itensMap.get(conferenciaItem).add(conferenciaItem);
			} else {
				LinkedList<ConferenciaItem> itens = new LinkedList<>();
				itens.add(conferenciaItem);

				itensMap.put(conferenciaItem, itens);
			}
		}

		try {
			Collection<PrePersistEntityState> itens = new ArrayList<>();

			CACHelper cacHelper = new CACHelper();
			ConferenciaItem conferencia;

			BigDecimal qtdNeg;
			BigDecimal vlrUnit;
			BigDecimal vlrTot;

			EntityDAO dao = EntityFacadeFactory.getDWFFacade().getDAOInstance(DynamicEntityNames.ITEM_NOTA);
			EntityPrimaryKey key;
			DynamicVO itemVO = null;

			PersistentLocalEntity entity = null;
			DynamicVO oldItemNotaVO = null;

			for (Map.Entry<ConferenciaItem, LinkedList<ConferenciaItem>> entry : itensMap.entrySet()) {
				List<ConferenciaItem> value = entry.getValue();

				for (int i = 0; i < value.size(); i++) {
					conferencia = value.get(i);

					key = new EntityPrimaryKey(
							new Object[] { recebimento.getNuNota(), conferencia.getSequenciaOrig() });
					entity = EntityFacadeFactory.getDWFFacade().findEntityByPrimaryKey(dao.getEntityName(), key);

					oldItemNotaVO = (DynamicVO) entity.getValueObject();

					if (i == 0) {

						itemVO = oldItemNotaVO.buildClone();
						itemVO.setAceptTransientProperties(true);

						itemVO.setProperty(SklFieldName.CONTROLE, conferencia.getControleConf());

						qtdNeg = BigDecimalUtil.getValueOrZero(conferencia.getQtdConf());
						vlrUnit = itemVO.asBigDecimal(SklFieldName.VLRUNIT);
						vlrTot = qtdNeg.multiply(vlrUnit, BigDecimalUtil.MATH_CTX);
						vlrTot = BigDecimalUtil.getRounded(vlrTot, 2);

						itemVO.setProperty(SklFieldName.QTDNEG, qtdNeg);
						itemVO.setProperty(SklFieldName.VLRUNIT, vlrUnit);
						itemVO.setProperty("VLRTOT", vlrTot);

						itens.add(PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(),
								DynamicEntityNames.ITEM_NOTA, itemVO, oldItemNotaVO, entity));
					} else {
						conferencia.setSequencia(null);
						conferencia.setSequenciaOrig(null);
						DynamicVO itemCloneVO = oldItemNotaVO.buildClone();
						itemCloneVO.setPrimaryKey(null);

						itemCloneVO.setProperty(SklFieldName.SEQUENCIA, null);

						itemCloneVO.setProperty(SklFieldName.CONTROLE, conferencia.getControleConf());

						qtdNeg = BigDecimalUtil.getValueOrZero(conferencia.getQtdConf());
						vlrUnit = itemCloneVO.asBigDecimal(SklFieldName.VLRUNIT);
						vlrTot = qtdNeg.multiply(vlrUnit, BigDecimalUtil.MATH_CTX);
						vlrTot = BigDecimalUtil.getRounded(vlrTot, 2);

						itemCloneVO.setProperty(SklFieldName.QTDNEG,
								BigDecimalUtil.getValueOrZero(conferencia.getQtdConf()));
						itemCloneVO.setProperty(SklFieldName.VLRUNIT, vlrUnit);
						itemCloneVO.setProperty("VLRTOT", vlrTot);

						itens.add(PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(),
								DynamicEntityNames.ITEM_NOTA, itemCloneVO, null, null));
						conferencia.setControle(conferencia.getControleConf());
					}

					conferencia.persists();
					conferencia.persists();
				}

				cacHelper.incluirAlterarItem(recebimento.getNuNota(), ServiceContext.getCurrent(), itens, false);
			}

//			Iterator<DynamicVO> iterator;
//			DynamicVO lastVO = null;
//			for (Map.Entry<ConferenciaItem, LinkedList<ConferenciaItem>> entry : itensMap.entrySet()) {
//				List<ConferenciaItem> values = entry.getValue();
//
//				for (ConferenciaItem itemConferencia : values) {
//					Collection<DynamicVO> vos = itemNotaDAO.find(
//							"this.CODPROD = ? AND this.CONTROLE = ? AND this.NUNOTA = ?", itemConferencia.getCodProd(),
//							itemConferencia.getControleConf(), recebimento.getNuNota());
//
//					iterator = vos.iterator();
//					lastVO = iterator.next();
//					while (iterator.hasNext()) 
//						lastVO = iterator.next();
//					
//					itemConferencia.setSequenciaOrig(lastVO.asBigDecimal(SklFieldName.SEQUENCIA));
//					itemConferencia.persists();
//				}
//
//			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar itens adicionais.", e);
		}

	}

	private void validaLotes(Recebimento recebimento) throws SkalaModelException {

		JapeWrapper itemDAO = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);

		DynamicVO itemVO = null;
		EntityUpdate entityUpdate;

		for (ConferenciaItem item : recebimento.getConferencia().getItens()) {
			if (item.getProduto().isUsaControle()) {
				
				if (StringUtils.getEmptyAsNull(item.getControleConf()) == null) {
					String msg = String.format("[%s - %s] Necesário informar Lote para produto.",
							item.getProduto().getCodProd(), item.getProduto().getDescrProd());
					throw new SkalaModelException(msg);
				}

				if (!item.getControle().equals(item.getControleConf())) {

					try {
						itemVO = itemDAO.findByPK(recebimento.getNuNota(), item.getSequenciaOrig());

						if (itemVO != null) {
							Estoque estoque = new Estoque();
							estoque.setCodEmp(itemVO.asBigDecimal(SklFieldName.CODEMP));
							estoque.setCodProd(itemVO.asBigDecimal(SklFieldName.CODPROD));
							estoque.setCodLocal(itemVO.asBigDecimal(SklFieldName.CODLOCALORIG));
							estoque.setControle(item.getControleConf());
							estoque.setCodParc(BigDecimal.ZERO);
							estoque.setTipo(TipoEstoqueEnum.PROPRIO.getValue());
							estoque.setDtVal(item.getDtValConf());
							estoque.setDtFabricacao(item.getDtFabricacaoConf());

							estoque.persists();

							entityUpdate = new EntityUpdate(DynamicEntityNames.ITEM_NOTA, itemVO);
							entityUpdate.setField(SklFieldName.CONTROLE, item.getControleConf());
							entityUpdate.fluidUpdate();
						}

					} catch (Exception e) {
						String msg = String.format("[NUNOTA %s SEQUENCIA %s] Não foi possível atualizar lote.",
								item.getNuNotaOrig(), item.getSequenciaOrig());
						SkalaModelException.throwMe(msg, e);
					}
				}

			}

		}
	}

	private void atualizarDataEntradaSaida(Recebimento recebimento) throws DataEntradaSaidaNaoAtualizadaException {
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
			DynamicVO vo = dao.findByPK(recebimento.getNuNota());

			EntityUpdate entityUpd = new EntityUpdate(DynamicEntityNames.CABECALHO_NOTA, vo);
			entityUpd.setField("DTENTSAI", TimeUtils.clearTime(TimeUtils.getNow()));
			
			entityUpd.fluidUpdate();
		} catch (Exception e) {
			throw new RecebimentoMercadoriaExceptions.DataEntradaSaidaNaoAtualizadaException(e);
		}
	}


}
