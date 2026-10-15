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

public class ColaboradorEscala implements EntityBean, Persistable {

    public static final String ENTITY_NAME = DynamicEntityNames.FUNCIONARIO;

    @SerializedName(value = "externalId")
    private String idFuncionario;

    @SerializedName(value = "tangerinoId")
    private BigDecimal idTangerinoFuncionario;

    @SerializedName(value = "startDate")
    private Long dataInicial;

    @SerializedName(value = "workScheduleExternalId")
    private BigDecimal codCargaHoraria;

    @SerializedName(value = "workScheduleId")
    private BigDecimal idTangerinoCargaHoraria;

    private BigDecimal codFuncionario;
    private BigDecimal codEmpresa;

    public ColaboradorEscala() {
    }

    public ColaboradorEscala(String idFuncionario, BigDecimal idTangerinoFuncionario, Long dataInicial, BigDecimal codCargaHoraria, BigDecimal idTangerinoCargaHoraria) {
        this.idFuncionario = idFuncionario;
        this.idTangerinoFuncionario = idTangerinoFuncionario;
        this.dataInicial = dataInicial;
        this.codCargaHoraria = codCargaHoraria;
        this.idTangerinoCargaHoraria = idTangerinoCargaHoraria;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public BigDecimal getIdTangerinoFuncionario() {
        return idTangerinoFuncionario;
    }

    public void setIdTangerinoFuncionario(BigDecimal idTangerinoFuncionario) {
        this.idTangerinoFuncionario = idTangerinoFuncionario;
    }

    public Long getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Long dataInicial) {
        this.dataInicial = dataInicial;
    }

    public BigDecimal getCodCargaHoraria() {
        return codCargaHoraria;
    }

    public void setCodCargaHoraria(BigDecimal codCargaHoraria) {
        this.codCargaHoraria = codCargaHoraria;
    }

    public BigDecimal getIdTangerinoCargaHoraria() {
        return idTangerinoCargaHoraria;
    }

    public void setIdTangerinoCargaHoraria(BigDecimal idTangerinoCargaHoraria) {
        this.idTangerinoCargaHoraria = idTangerinoCargaHoraria;
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
            new LogItem("Erro ao salvar colaborador escala: ", e.getMessage(), "C", MensagemLog.ERRO);
        } finally {
            JapeSession.close(hnd);
        }
        return null;
    }

    @Override
    public String toString() {
        return "ColaboradorEscala{" +
                "idFuncionario=" + idFuncionario +
                ", idTangerinoFuncionario=" + idTangerinoFuncionario +
                ", dataInicial=" + dataInicial +
                ", codCargaHoraria=" + codCargaHoraria +
                ", idTangerinoCargaHoraria=" + idTangerinoCargaHoraria +
                ", codFuncionario=" + codFuncionario +
                ", codEmpresa=" + codEmpresa +
                '}';
    }
}
