package br.zaya.telecontrol.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.sql.PreparedStatement;

public class InputImage implements AcaoRotinaJava {
    public void doAction(ContextoAcao ctx) throws Exception {
        String nucontrole = (String) ctx.getParam("NUCONTROLE");
        String logo = (String) ctx.getParam("LOGO");
        JdbcWrapper jdbc = null;
        PreparedStatement ps = null;
        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
            ps = jdbc.getConnection().prepareStatement("UPDATE TRDPCO SET VALOR = ? WHERE NUCONTROLE = ? AND NOME = 'icon-info'");
            ps.setString(1, logo);
            ps.setString(2, nucontrole);
            ps.executeUpdate();
            ctx.setMensagemRetorno("Processo Finalizado, examinar os logs");
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
