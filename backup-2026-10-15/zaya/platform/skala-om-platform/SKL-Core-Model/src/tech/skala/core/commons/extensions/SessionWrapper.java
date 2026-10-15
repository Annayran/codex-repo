package tech.skala.core.commons.extensions;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.modelcore.helper.WmsHelper.CacheCenter;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

/**
 * 
 * @author marcosdamasceno
 * @since 2022-07-05
 *
 */
public final class SessionWrapper {
	private JapeSession.SessionHandle		hnd;
	private EntityFacade					dwfEntityFacade;
	
	protected SessionWrapper() {
		this.hnd = JapeSession.open();
		hnd.setFindersMaxRows(-1);
		hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
		hnd.setCanTimeout(false);
		
		this.dwfEntityFacade = EntityFacadeFactory.getDWFFacade();

		CacheCenter.registryInstance(dwfEntityFacade);
	}
	
	public static SessionWrapper getInstance() {
		return new SessionWrapper();
	}

	public void close() {
		CacheCenter.unregistry();
		JapeSession.close(hnd);
	}
	
	
}
