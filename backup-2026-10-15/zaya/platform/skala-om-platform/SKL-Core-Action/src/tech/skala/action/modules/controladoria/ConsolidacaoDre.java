package tech.skala.action.modules.controladoria;

import java.sql.CallableStatement;
import java.sql.SQLException;

import com.sankhya.util.JdbcUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ConsolidacaoDre {


	public void geraConsolidacaoDre() throws Exception {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					gerarConsolidacoesDre();

				}

			});

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			JapeSession.close(hnd);
		}
	
		

	}

	/**
	 * @throws SQLException
	 */
	private void gerarConsolidacoesDre() throws SkalaModelException {
		CallableStatement rsv = null;
		
        JdbcWrapper jdbc = null;

        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();

            rsv = jdbc.getConnection().prepareCall("{call STP_DRE_CONSOLIDADO_PLA()}");
			rsv.setQueryTimeout(420);
            rsv.execute();
            
                     
        } catch (Exception e) {
            SkalaModelException.throwMe("Não foi possível gerar consoliacoes Dre", e);
        } finally {
            JdbcUtils.closeStatement(rsv);
            JdbcWrapper.closeSession(jdbc);
        }
        
        
        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
      
            rsv = jdbc.getConnection().prepareCall("{call STP_DRE_CONSOLIDADO_PLA_EMP()}");
			rsv.setQueryTimeout(420);
            rsv.execute();
            
        } catch (Exception e) {
            SkalaModelException.throwMe("Não foi possível gerar consoliacoes Dre", e);
        } finally {
            JdbcUtils.closeStatement(rsv);
            JdbcWrapper.closeSession(jdbc);
        }
	
	}

}