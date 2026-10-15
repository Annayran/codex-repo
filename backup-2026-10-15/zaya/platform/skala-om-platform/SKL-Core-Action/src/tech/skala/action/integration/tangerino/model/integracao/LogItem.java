package tech.skala.action.integration.tangerino.model.integracao;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import com.sankhya.util.StringUtils;
import tech.skala.action.integration.tangerino.model.sistema.MensagemLog;
import tech.skala.action.integration.tangerino.utils.ManagerUtils;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

import java.math.BigDecimal;

public class LogItem implements EntityBean, Persistable {

    public static final String ENTITY_NAME = "AD_ITGLOGITE";

    private BigDecimal codLog;
    private String descricao;
    private String atividade;
    private String categoria;
    private MensagemLog mensagem;
    private boolean isProducao;

    public LogItem() {
    }

    public LogItem(String descricao, String atividade, String categoria, MensagemLog mensagem) {
        this.descricao = descricao;
        this.atividade = atividade;
        this.categoria = categoria;
        this.mensagem = mensagem;
        this.isProducao = true;
    }

    public LogItem(String descricao, String atividade, String categoria, MensagemLog mensagem, boolean isProducao) {
        this.descricao = descricao;
        this.atividade = atividade;
        this.categoria = categoria;
        this.mensagem = mensagem;
        this.isProducao = isProducao;
    }

    public BigDecimal getCodLog() {
        return codLog;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAtividade() {
        return atividade;
    }

    public String getCategoria() {
        return categoria;
    }

    public MensagemLog getMensagem() {
        return mensagem;
    }

    public boolean isProducao() {
        return isProducao;
    }

    @Override
    public DynamicVO persists() throws DataDictionaryExceptions.InsertNotPossibleException {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setCanTimeout(false);
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                @Override
                public void doWithTx() throws Exception {
                    EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
                    entityInsert.setField("CODLOG", ManagerUtils.getInfo());
                    entityInsert.setField("DESCRICAO", descricao);
                    entityInsert.setField("ATIVIDADE", atividade.toCharArray());
                    entityInsert.setField("CATEGORIA", categoria);
                    entityInsert.setField("MENSAGEM", mensagem.toString());
                    entityInsert.fluidInsert();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JapeSession.close(hnd);
        }
        return null;
    }

    @Override
    public String toString() {
        return "LogItem{" +
                "codLog=" + codLog +
                ", descricao='" + descricao + '\'' +
                ", atividade='" + atividade + '\'' +
                ", categoria='" + categoria + '\'' +
                ", mensagem=" + mensagem +
                ", isProducao=" + isProducao +
                '}';
    }
}
