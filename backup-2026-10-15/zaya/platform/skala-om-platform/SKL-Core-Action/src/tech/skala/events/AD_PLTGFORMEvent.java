package tech.skala.events;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class AD_PLTGFORMEvent implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		BigDecimal idiProc = vo.asBigDecimal(SklFieldName.IDIPROC);
		
		JapeWrapper workCenterInstanciaDAO = JapeFactory.dao(DynamicEntityNames.WORK_CENTER_INSTANCIA_PROCESSO);
		DynamicVO workCenterInstanciaVO = workCenterInstanciaDAO.findOne("this.IDIPROC = ?", idiProc);

		BigDecimal codWcp = workCenterInstanciaVO.asBigDecimal(SklFieldName.CODWCP);

		JapeWrapper ordemProducaoDAO = JapeFactory.dao(DynamicEntityNames.PRODUTO_ACABADO_A_PRODUZIR);
		DynamicVO ordemProducaoVO = ordemProducaoDAO.findOne("this.IDIPROC = ?", idiProc);

		BigDecimal codProd = ordemProducaoVO.asBigDecimal(SklFieldName.CODPRODPA);
		String nroLote = ordemProducaoVO.asString(SklFieldName.NROLOTE);
		BigDecimal capacidade = ordemProducaoVO.asBigDecimal(SklFieldName.QTDPRODUZIR);
		
		try {
			EntityUpdate entityUpdate = new EntityUpdate("AD_PLTGFORM", vo);

			entityUpdate.setField(SklFieldName.CODWCP, codWcp);
			entityUpdate.setField(SklFieldName.CAPACIDADE, capacidade);
			entityUpdate.setField(SklFieldName.CODPROD, codProd);
			entityUpdate.setField(SklFieldName.NROLOTE, nroLote);

			entityUpdate.fluidUpdate();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar informações da formulação", e);
		}

	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		ModifingFields modifingFields = event.getModifingFields();

		String fieldList = "HRINI,HRFIN";

		if (modifingFields.isModifingAny(fieldList)) {
			String status = vo.asString(SklFieldName.STATUS);
			
			if("F".equals(status)) {
				throw new SkalaModelException("A formulação não pode ser alterada após estar <b>Finalizada</b>!");
			}
			
			Timestamp hrIni = vo.asTimestamp("HRINI");
			Timestamp hrFin = vo.asTimestamp("HRFIN");
			String statusNovo = status;

			EntityUpdate entityUpdate = new EntityUpdate("AD_PLTGFORM", vo);

			if("C".equals(status)) {
			
				if (hrIni != null && hrFin == null) {
					statusNovo = "I";
				} else {
					throw new SkalaModelException("Hora Final não pode ser preenchida antes da Hora Inicial");
				}		
				
			}

			if ("I".equals(status) && (hrIni != null & hrFin != null)) {
				statusNovo = "F";
			}

			entityUpdate.setField(SklFieldName.STATUS, statusNovo);

			entityUpdate.fluidUpdate();
		}
	}

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();
		
		BigDecimal idiProc = vo.asBigDecimal(SklFieldName.IDIPROC);
		
		JapeWrapper formulacaoDAO = JapeFactory.dao("AD_PLTGFORM"); 
		DynamicVO formulacaoVO = formulacaoDAO.findOne("this.IDIPROC = ?", idiProc);
		
		if(formulacaoVO != null) {
			String msg = String.format("A OP %s já foi inserida anteriormente na Formulação %s", idiProc, formulacaoVO.asBigDecimal("NUFORM"));
			throw new SkalaModelException(msg);
		}

	}

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
