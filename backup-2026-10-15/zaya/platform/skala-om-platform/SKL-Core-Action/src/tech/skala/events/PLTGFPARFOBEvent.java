package tech.skala.events;

import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class PLTGFPARFOBEvent implements EventoProgramavelJava {
	private Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	private String msg;

	@Override
	public void beforeDelete(PersistenceEvent event) throws SkalaModelException {
		impedirAlteracaoOuExclusao();
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
	public void beforeInsert(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		impedirAlteracaoOuExclusao();
	}

	private void impedirAlteracaoOuExclusao() throws SkalaModelException {
		msg = "Não é permitido alterar ou excluir o registro!\n"
				+ "Se necessário, inclua um novo registro com a data adequada.";
		logger.info(msg);

		throw new SkalaModelException(msg);
	}

	@Override
	public void afterDelete(PersistenceEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
