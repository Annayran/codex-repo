package tech.skala.action.integration.tangerino.model.integracao;

import java.math.BigDecimal;

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


public class Cargo implements EntityBean, Persistable {

	public static final String ENTITY_NAME = DynamicEntityNames.CARGO;
	
	@SerializedName(value = "externalId")
	private BigDecimal codCargo;
	
	@SerializedName(value = "description")
	private String nomeCargo; 
	
	@SerializedName(value = "id")
	private BigDecimal idTangerino;
	
	public Cargo() {}

	public Cargo(BigDecimal codCargo, String nomeCargo) {
		this.codCargo = codCargo;
		this.nomeCargo = nomeCargo;
	}

	public Cargo(BigDecimal codCargo, String nomeCargo, BigDecimal idTangerino) {
		this.codCargo = codCargo;
		this.nomeCargo = nomeCargo;
		this.idTangerino = idTangerino;
	}

	public BigDecimal getCodCargo() {
		return codCargo;
	}
	public void setCodCargo(BigDecimal codCargo) {
		this.codCargo = codCargo;
	}
	public String getNomeCargo() {
		return nomeCargo;
	}
	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}
	public BigDecimal getIdTangerino() {
		return idTangerino;
	}
	public void setIdTangerino(BigDecimal idTangerino) {
		this.idTangerino = idTangerino;
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
					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codCargo });
					EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);
					entityUpd.setField("AD_ID_TANGERINO", idTangerino);
					entityUpd.setField("AD_SINC_TANGERINO", "N");
					entityUpd.fluidUpdate();
				}
			});
		} catch (Exception e) {
			new LogItem("Erro salvar cargo: ", e.getMessage(), "C", MensagemLog.ERRO);
		} finally {
			JapeSession.close(hnd);
		}
		return null;
	}

	@Override
	public String toString() {
		return "CargoModel [codCargo=" + codCargo + ", nomeCargo=" + nomeCargo + ", idTangerino=" + idTangerino + "]";
	}
}
