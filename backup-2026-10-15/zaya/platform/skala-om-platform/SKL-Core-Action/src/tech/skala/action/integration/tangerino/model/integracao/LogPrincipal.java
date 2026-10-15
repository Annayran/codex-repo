package tech.skala.action.integration.tangerino.model.integracao;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.integration.tangerino.model.sistema.MensagemLog;
import tech.skala.action.integration.tangerino.utils.ManagerUtils;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

import java.math.BigDecimal;

public class LogPrincipal  implements EntityBean, Persistable {

    public static final String ENTITY_NAME = "AD_ITGLOG";

    private BigDecimal codLog;
    private String statusExecucao;
    private String statusDosItens;

    public LogPrincipal() {
    }

    public LogPrincipal(BigDecimal codLog, String statusDosItens) {
        this.codLog = codLog;
        this.statusDosItens = statusDosItens;
    }

    public BigDecimal getCodLog() {
        return codLog;
    }

    public void setCodLog(BigDecimal codLog) {
        this.codLog = codLog;
    }

    public String getStatusExecucao() {
        return statusExecucao;
    }

    public void setStatusExecucao(String statusExecucao) {
        this.statusExecucao = statusExecucao;
    }

    public String getStatusDosItens() {
        return statusDosItens;
    }

    public void setStatusDosItens(String statusDosItens) {
        this.statusDosItens = statusDosItens;
    }

    @Override
    public DynamicVO persists() throws DataDictionaryExceptions.InsertNotPossibleException, DataDictionaryExceptions.UpdateNotPossibleException {
        JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
        DynamicVO logVO = null;
        if(codLog != null) {
            try {
                logVO = dao.findByPK(codLog);
            } catch (Exception e) {
                throw new DataDictionaryExceptions.UpdateNotPossibleException(e);
            }
        }
        transacao(logVO, statusDosItens);
        return null;
    }

    private static void transacao(DynamicVO logVO, String statusDosItens ){
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setCanTimeout(false);
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                @Override
                public void doWithTx() throws Exception {
                    if(logVO == null) {
                        EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
                        entityInsert.setField("STATUSEXECUCAO", "A");
                        DynamicVO principalVO = entityInsert.fluidInsert();
                        ManagerUtils.setInfo(principalVO.asBigDecimal("CODLOG"));
                    } else {
                        EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, logVO);
                        entityUpd.setField("STATUSEXECUCAO", "C");
                        entityUpd.setField("STATUS", statusDosItens);
                        entityUpd.fluidUpdate();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JapeSession.close(hnd);
        }
    }
}
