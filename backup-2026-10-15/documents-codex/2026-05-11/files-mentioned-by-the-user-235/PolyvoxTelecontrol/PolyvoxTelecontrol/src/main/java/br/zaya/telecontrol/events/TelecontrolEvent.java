package br.zaya.telecontrol.events;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.comercial.util.TipoOperacaoUtils;

public class TelecontrolEvent implements EventoProgramavelJava {
	public void afterDelete(PersistenceEvent arg0) throws Exception {
	}

	public void afterInsert(PersistenceEvent event) throws Exception {
	}

	public void afterUpdate(PersistenceEvent event) throws Exception {
	}

	public void beforeCommit(TransactionContext arg0) throws Exception {
	}

	public void beforeDelete(PersistenceEvent arg0) throws Exception {
	}

	public void beforeInsert(PersistenceEvent event) throws Exception {
		DynamicVO itemVO = (DynamicVO) event.getVo();
		if (itemVO.asString("AD_TCLPEDITE") != null) {
			DynamicVO cabVO = JapeFactory.dao("CabecalhoNota").findByPK(new Object[] { itemVO.asBigDecimal("NUNOTA") });
			// Ajuste de lógica conforme MQHairTelecontrolEvent mas genérico para o pacote
            if (cabVO != null && cabVO.asBigDecimal("CODTIPOPER") != null) {
                // Se houver lógica específica por TOP, adicionar aqui
                // Exemplo: if (cabVO.asInt("CODTIPOPER") == 3143) { ... }
            }
		}
	}

	public void beforeUpdate(PersistenceEvent event) throws Exception {
	}
}
