package tech.skala.events;

import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProdutoEvent implements EventoProgramavelJava {
	
	final private Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private String msg;

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

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

		vo.setProperty("DESCRPROD", vo.asString("DESCRPROD").toUpperCase());
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {

		ModifingFields modifingFields = event.getModifingFields();

		DynamicVO vo = (DynamicVO) event.getVo();
		DynamicVO oldVO = (DynamicVO) event.getOldVO();

		if (modifingFields.isModifing("DESCRPROD")) {
			vo.setProperty("DESCRPROD", vo.asString("DESCRPROD").toUpperCase());
		}

		if (modifingFields.isModifing("UTILIZAWMS")) {

			String utilizaWmsNew = vo.asString("UTILIZAWMS");
			String utilizaWmsOld = oldVO.asString("UTILIZAWMS");

			if ("S".equalsIgnoreCase(utilizaWmsOld) && "N".equalsIgnoreCase(utilizaWmsNew)) {
				this.msg = "<bNão é permitido desativar a integração com <b>WMS</b>.<br>Caso seja necessário favor abrir chamado enviando e-mail para suporte@platinacsc.com.br";

				throw new SkalaModelException(msg);
			}
		}

	}

}
