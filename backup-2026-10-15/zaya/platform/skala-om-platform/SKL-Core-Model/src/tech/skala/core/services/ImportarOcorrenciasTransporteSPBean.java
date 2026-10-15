package tech.skala.core.services;

import java.util.logging.Logger;

import javax.ejb.SessionBean;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.modelcore.util.BaseSPBean;

/**
 * 
 * @author cassiomenezes
 * @since 2023-02-06
 * 
 * @ejb.bean name="ImportarOcorrenciasTransporteSP"
 *           jndi-name="tech/skala/core/services/ImportarOcorrenciasTransporteSP"
 *           type="Stateless" transaction-type="Container" view-type="remote"
 * @ejb.transaction type="Supports"
 * @ejb.util generate="false"
 */
public class ImportarOcorrenciasTransporteSPBean extends BaseSPBean implements SessionBean {

	private static final long serialVersionUID = 1016367709630998910L;
	private static final Logger LOGGER = new LoggerFactory(ImportarOcorrenciasTransporteSPBean.class.getCanonicalName()).getLogger();

}
