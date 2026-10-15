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
import java.util.List;

public class Escala implements EntityBean, Persistable {

    public static final String ENTITY_NAME = "IdentificacaoCargaHoraria";

    @SerializedName(value = "id")
    private BigDecimal idTangerino;

    @SerializedName(value = "externalId")
    private BigDecimal codCargaHoraria;

    @SerializedName(value = "name")
    private String nomeEscala;

    @SerializedName(value = "standard")
    private boolean isPadrao;

    @SerializedName(value = "workScheduleTimetableList")
    private List<EscalaRelacao> escalas;

    public Escala() {
    }

    public Escala(BigDecimal idTangerino, BigDecimal codCargaHoraria, String nomeEscala, boolean isPadrao, List<EscalaRelacao> escalas) {
        this.idTangerino = idTangerino;
        this.codCargaHoraria = codCargaHoraria;
        this.nomeEscala = nomeEscala;
        this.isPadrao = isPadrao;
        this.escalas = escalas;
    }

    public BigDecimal getIdTangerino() {
        return idTangerino;
    }

    public void setIdTangerino(BigDecimal idTangerino) {
        this.idTangerino = idTangerino;
    }

    public String getNomeEscala() {
        return nomeEscala;
    }

    public void setNomeEscala(String nomeEscala) {
        this.nomeEscala = nomeEscala;
    }

    public boolean isPadrao() {
        return isPadrao;
    }

    public void setPadrao(boolean padrao) {
        isPadrao = padrao;
    }

    public List<EscalaRelacao> getEscalas() {
        return escalas;
    }

    public void setEscalas(List<EscalaRelacao> escalas) {
        this.escalas = escalas;
    }

    public BigDecimal getCodCargaHoraria() {
        return codCargaHoraria;
    }

    public void setCodCargaHoraria(BigDecimal codCargaHoraria) {
        this.codCargaHoraria = codCargaHoraria;
    }

    @Override
    public String toString() {
        return "Escala{" +
                "idTangerino=" + idTangerino +
                ", codCargaHoraria=" + codCargaHoraria +
                ", nomeEscala='" + nomeEscala + '\'' +
                ", isPadrao=" + isPadrao +
                ", escalas=" + escalas +
                '}';
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
                    EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codCargaHoraria });
                    EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);
                    entityUpd.setField("AD_ID_TANGERINO", idTangerino);
                    entityUpd.setField("AD_SINC_TANGERINO", "N");
                    entityUpd.fluidUpdate();
                }
            });
        } catch (Exception e) {
            new LogItem("Erro ao salvar escala: ", e.getMessage(), "C", MensagemLog.ERRO);
        } finally {
            JapeSession.close(hnd);
        }
        return null;
    }
}
