package tech.skala.action.modules.recebimento;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityDAO;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.dao.JdbcWrapper;
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
import tech.skala.core.beans.suprimento.recebimento.ConferenciaItem;
import tech.skala.core.beans.suprimento.recebimento.Recebimento;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaExceptions;
import tech.skala.modules.suprimentos.recebimento.RecebimentoMercadoriaExceptions.ConferenciaNaoProcessadaException;

public class RecebimentoMercadoriaProcessor {
	private static final Logger				LOGGER = new LoggerFactory(RecebimentoMercadoriaProcessor.class.getCanonicalName()).getLogger();
	
	public void processaConferenciaEntrada(Recebimento recebimento) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					finalizarRecebimento(recebimento);
				}
			});

		} catch (Exception e) {
			throw new RecebimentoMercadoriaExceptions.ConferenciaNaoProcessadaException(e,
					recebimento.getNuRecebimento());
		} finally {
			JapeSession.close(hnd);
		}

	}

	protected void finalizarRecebimento(Recebimento recebimento) throws SkalaModelException {
		
		JapeSessionContext.putProperty(ListenerParameters.CENTRAIS, Boolean.TRUE);
        JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.TRUE);

        //carrega mapa de itens
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
			Collection<PrePersistEntityState> itens = new ArrayList<PrePersistEntityState>();
			CentralItemNota central = new CentralItemNota();

			Collection<PrePersistEntityState> itensNota = new ArrayList<>();
			ConferenciaItem conferencia;
			PrePersistEntityState itePreState;
			CACHelper cacHelper = new CACHelper();

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
				
					key = new EntityPrimaryKey(new Object[]{ recebimento.getNuNota(), conferencia.getSequenciaOrig() });
					entity = EntityFacadeFactory.getDWFFacade().findEntityByPrimaryKey(dao.getEntityName(), key);
					
					oldItemNotaVO = (DynamicVO) entity.getValueObject();
					
					//isso aqui é para alterar
					if(i == 0) {
						
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
						
						itens.add(PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(), DynamicEntityNames.ITEM_NOTA, itemVO, oldItemNotaVO, entity));
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
						
						itemCloneVO.setProperty(SklFieldName.QTDNEG, BigDecimalUtil.getValueOrZero(conferencia.getQtdConf()));
						itemCloneVO.setProperty(SklFieldName.VLRUNIT, vlrUnit);
						
						itens.add(PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(), DynamicEntityNames.ITEM_NOTA, itemCloneVO, null, null));
						conferencia.setControle(conferencia.getControleConf());
					}
					
					conferencia.persists();
					conferencia.persists();
				}
				
				cacHelper.incluirAlterarItem(recebimento.getNuNota(), ServiceContext.getCurrent(), itens, false);
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar itens adicionais.", e);
		}

	}

	public void processarItemConferencia(Recebimento recebimento) throws ConferenciaNaoProcessadaException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					processaItem(recebimento);
				}

			});

		} catch (Exception e) {
			throw new RecebimentoMercadoriaExceptions.ConferenciaNaoProcessadaException(e,
					recebimento.getNuRecebimento());
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void processaItem(Recebimento recebimento) throws Exception {
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
		
		LOGGER.info("Terminou de incluir os itens na ITE");
		JapeWrapper itemNotaDAO = JapeFactory.dao("ItemNota");
		
		Iterator<DynamicVO> iterator;
		DynamicVO lastVO = null;
		StringBuilder sb = new StringBuilder();
		Collection<DynamicVO> vos = null;
		for (Map.Entry<ConferenciaItem, LinkedList<ConferenciaItem>> entry : itensMap.entrySet()) {
			List<ConferenciaItem> values = entry.getValue();

			LOGGER.info("\nQuantidade de conferencia na lista: " + values.size());

			for (ConferenciaItem itemConferencia : values) {
				vos = itemNotaDAO.find(
						"this.CODPROD = ? AND this.CONTROLE = ? AND this.NUNOTA = ?", itemConferencia.getCodProd(),
						itemConferencia.getControleConf(), recebimento.getNuNota());

				if(!vos.isEmpty()) {
					
					iterator = vos.iterator();
					lastVO = iterator.next();
					while (iterator.hasNext()) 
						lastVO = iterator.next();
				}
				
				sb.append("\n\nConferencia atual: \n");
				sb.append("CODPROD: " + itemConferencia.getCodProd());
				sb.append("\nCONTROLE: " + itemConferencia.getControleConf());
				sb.append("\nSEQUENCIA: " + itemConferencia.getSequencia());
				sb.append("\nNUNOTA DO RECEBIMENTO: " + recebimento.getNuNota());
				sb.append("\nNUCONFERENCIA: " + itemConferencia.getNuConferencia());
				
				LOGGER.info(sb.toString());
				
				LOGGER.info("LastVO eh nulo? " + (lastVO == null));
				
				if(lastVO != null) {
					LOGGER.info("Sequencia do LastVO da ITE: " + lastVO.asBigDecimal(SklFieldName.SEQUENCIA));
					
					itemConferencia.setSequenciaOrig(lastVO.asBigDecimal(SklFieldName.SEQUENCIA));
					itemConferencia.persists();
				}
				
			}

		}
		
	}
	
	public void excluiRecebimento(BigDecimal nuNota) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					excluirRecebimento(nuNota);

				}

			});

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível excluir os recebimentos", nuNota);
			SkalaModelException.throwMe(msg, e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	protected void excluirRecebimento(BigDecimal nuNota) throws SkalaModelException {


		CallableStatement rsv = null;
		JdbcWrapper jdbc = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			rsv = jdbc.getConnection().prepareCall("{ call STP_EXCLUI_RECEBIMENTO( ? ) }");

			rsv.setBigDecimal(1, nuNota);

			rsv.execute();
			
		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível excluir os recebimentos", nuNota);
			SkalaModelException.throwMe(msg, e);

		} finally {
			JdbcUtils.closeStatement(rsv);
			JdbcWrapper.closeSession(jdbc);

		}
		
	}
	
	public void alteraQtdConferida(BigDecimal nuConferencia, BigDecimal sequencia, BigDecimal newQtdConf) throws SkalaModelException {
		
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] {nuConferencia, sequencia});
					
					EntityUpdate update = new EntityUpdate("AD_PLTGFCOI", pk);
					update.setField(SklFieldName.QTDECONF, newQtdConf);
					update.fluidUpdate();					
					
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível alterar a quantidade conferida.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
}
