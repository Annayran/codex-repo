package tech.skala.action.modules.comercial.lifecycle;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class SalesOrderLifeCycleHelper extends SalesOrderLifeCycle {
	
	public SalesOrderLifeCycleHelper() {
		super();		
	}
	
	public SalesOrderLifeCycleHelper(BigDecimal nuNotaPv, Timestamp dtComercial) throws InsertNotPossibleException {
		super(nuNotaPv, dtComercial);		
	}
	
	@Override
	public void buildItems(BigDecimal nuNotaPv, String etapa, Timestamp dhInc, BigDecimal nuNota) throws SkalaModelException {
		SalesOrderLifeCycleItem item = new SalesOrderLifeCycleItem();
		item.setNuNotaPv(nuNotaPv);
		item.setEtapa(etapa);
		item.setNuNota(nuNota);
		item.setCodUsuInc(AuthenticationInfo.getCurrent().getUserID());
		item.setDhInc(dhInc);
		item.persists();
	}
}
