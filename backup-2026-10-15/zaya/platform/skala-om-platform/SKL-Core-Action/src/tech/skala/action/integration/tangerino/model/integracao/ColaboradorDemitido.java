package tech.skala.action.integration.tangerino.model.integracao;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import com.google.gson.annotations.SerializedName;
import tech.skala.action.integration.tangerino.model.sistema.MensagemLog;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityUpdate;

import java.math.BigDecimal;

public class ColaboradorDemitido  implements EntityBean, Persistable {

    public static final String ENTITY_NAME = DynamicEntityNames.FUNCIONARIO;

    @SerializedName(value = "externalId")
    private String idFuncionario;
    @SerializedName(value = "resignationDate")
    private Long dataDemissao;
    @SerializedName(value = "tangerinoId")
    private BigDecimal idTangerino;

    private BigDecimal codFuncionario;
    private BigDecimal codEmpresa;

    public ColaboradorDemitido() {
    }

    public ColaboradorDemitido(String idFuncionario, Long dataDemissao, BigDecimal idTangerino) {
        this.idFuncionario = idFuncionario;
        this.dataDemissao = dataDemissao;
        this.idTangerino = idTangerino;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Long getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(Long dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public BigDecimal getIdTangerino() {
        return idTangerino;
    }

    public void setIdTangerino(BigDecimal idTangerino) {
        this.idTangerino = idTangerino;
    }

    public BigDecimal getCodFuncionario() {
        return codFuncionario;
    }

    public void setCodFuncionario(BigDecimal codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    public BigDecimal getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(BigDecimal codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    @Override
    public DynamicVO persists() throws DataDictionaryExceptions.UpdateNotPossibleException {
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setCanTimeout(false);
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(new JapeSession.TXBlock() {
                @Override
                public void doWithTx() throws Exception {
                    EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codEmpresa, codFuncionario });
                    EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);
                    entityUpd.setField("AD_SINC_TANGERINO", "N");
                    entityUpd.fluidUpdate();
                }
            });
        } catch (Exception e) {
            new LogItem("Erro ao salvar colaborador demitido: ", e.getMessage(), "C", MensagemLog.ERRO);
        } finally {
            JapeSession.close(hnd);
        }
        return null;
    }

    @Override
    public String toString() {
        return "ColaboradorDemitido{" +
                "idFuncionario=" + idFuncionario +
                ", dataDemissao=" + dataDemissao +
                ", idTangerino=" + idTangerino +
                ", codFuncionario=" + codFuncionario +
                ", codEmpresa=" + codEmpresa +
                '}';
    }
}
