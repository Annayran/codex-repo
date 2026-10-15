package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.sql.PreparedStatement;

public class ApagarLOGBt implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        JdbcWrapper jdbc = null;
        PreparedStatement ps = null;
        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
            ps = jdbc.getPreparedStatement("DELETE AD_TCLLGT");
            ps.executeUpdate();
            ctx.setMensagemRetorno("Logs apagados de AD_TCLLGT");
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (jdbc != null) {
                jdbc.closeSession();
            }
        }
    }
}
