package tech.skala.action.integration.tangerino.model.integracao;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import com.google.gson.annotations.SerializedName;
import com.sankhya.util.StringUtils;
import tech.skala.action.integration.tangerino.model.sistema.MensagemLog;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.EntityUpdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EscalaRelacao implements EntityBean, Persistable {
	public static final String					ENTITY_NAME = "CargaHoraria";
	
	private BigDecimal							codCargaHoraria;
	
	@SerializedName(value = "day")
	private BigDecimal							dia;
	private BigDecimal							endMainInterval;
	@SerializedName(value = "endShift1")
	private BigDecimal							fimTurno1;
	@SerializedName(value = "endShift2")
	private BigDecimal							fimTurno2;
	@SerializedName(value = "id")
	private BigDecimal							idTangerino;
	private String								mainInterval;
	private BigDecimal							startMainInterval;
	@SerializedName(value = "startShift1")
	private BigDecimal							inicioTurno1;
	@SerializedName(value = "startShift2")
	private BigDecimal							inicioTurno2;
	private List<BigDecimal>					turnos = new ArrayList<>();
	
	public EscalaRelacao() {
		this.mainInterval = "BETWEEN_PERIOD_1_AND_2";
	}
	
    public BigDecimal getCodCargaHoraria() {
		return codCargaHoraria;
	}

	public void setCodCargaHoraria(BigDecimal codCargaHoraria) {
		this.codCargaHoraria = codCargaHoraria;
	}

	public BigDecimal getDia() {
		return dia;
	}

	public void setDia(BigDecimal dia) {
		this.dia = dia;
	}

	public BigDecimal getEndMainInterval() {
		return endMainInterval;
	}

	public void setEndMainInterval(BigDecimal endMainInterval) {
		this.endMainInterval = endMainInterval;
	}

	public BigDecimal getFimTurno1() {
		return fimTurno1;
	}

	public void setFimTurno1(BigDecimal fimTurno1) {
		this.fimTurno1 = fimTurno1;
	}

	public BigDecimal getFimTurno2() {
		return fimTurno2;
	}

	public void setFimTurno2(BigDecimal fimTurno2) {
		this.fimTurno2 = fimTurno2;
	}

	public BigDecimal getIdTangerino() {
		return idTangerino;
	}

	public void setIdTangerino(BigDecimal idTangerino) {
		this.idTangerino = idTangerino;
	}

	public String getMainInterval() {
		return mainInterval;
	}

	public void setMainInterval(String mainInterval) {
		this.mainInterval = mainInterval;
	}

	public BigDecimal getStartMainInterval() {
		return startMainInterval;
	}

	public void setStartMainInterval(BigDecimal startMainInterval) {
		this.startMainInterval = startMainInterval;
	}

	public BigDecimal getInicioTurno1() {
		return inicioTurno1;
	}

	public void setInicioTurno1(BigDecimal inicioTurno1) {
		this.inicioTurno1 = inicioTurno1;
	}

	public BigDecimal getInicioTurno2() {
		return inicioTurno2;
	}

	public void setInicioTurno2(BigDecimal inicioTurno2) {
		this.inicioTurno2 = inicioTurno2;
	}

	public List<BigDecimal> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<BigDecimal> turnos) {
		this.turnos = turnos;
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
                	for (BigDecimal turno: getTurnos()) {
                		EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codCargaHoraria, dia, turno });
                		EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);
                		entityUpd.setField("AD_ID_TANGERINO", idTangerino);
                		entityUpd.setField("AD_SINC_TANGERINO", "N");
                		entityUpd.fluidUpdate();
                	}
                }
            });
            
        } catch (Exception e) {
            new LogItem("Erro ao salvar escala - relação: ", e.getMessage(), "C", MensagemLog.ERRO);
        } finally {
            JapeSession.close(hnd);
        }
        return null;
    }

    @Override
	public String toString() {
		return String.format(
				"EscalaRelacao [codCargaHoraria=%s, dia=%s, endMainInterval=%s, fimTurno1=%s, fimTurno2=%s, idTangerino=%s, mainInterval=%s, startMainInterval=%s, inicioTurno1=%s, inicioTurno2=%s, turnos=%s]",
				codCargaHoraria, dia, endMainInterval, fimTurno1, fimTurno2, idTangerino, mainInterval,
				startMainInterval, inicioTurno1, inicioTurno2, turnos);
	}
}
