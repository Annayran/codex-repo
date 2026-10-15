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

public class Setor implements EntityBean, Persistable {

	public static final String ENTITY_NAME = DynamicEntityNames.DEPARTAMENTO;

	@SerializedName(value = "externalId")
	private BigDecimal codSetor;

	@SerializedName(value = "name")
	private String nomeSetor;

	@SerializedName(value = "id")
	private BigDecimal idTangerino;

	public Setor() {}

	public Setor(BigDecimal codSetor, String nomeSetor) {
		this.codSetor = codSetor;
		this.nomeSetor = nomeSetor;
	}

	public Setor(BigDecimal codSetor, String nomeSetor, BigDecimal idTangerino) {
		this.codSetor = codSetor;
		this.nomeSetor = nomeSetor;
		this.idTangerino = idTangerino;
	}

	public BigDecimal getCodSetor() {
		return codSetor;
	}

	public String getNomeSetor() {
		return nomeSetor;
	}

	public BigDecimal getIdTangerino() {
		return idTangerino;
	}

	public void setCodSetor(BigDecimal codSetor) {
		this.codSetor = codSetor;
	}

	public void setNomeSetor(String nomeSetor) {
		this.nomeSetor = nomeSetor;
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
					EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codSetor });
					EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, pk);
					entityUpd.setField("AD_ID_TANGERINO", idTangerino);
					entityUpd.setField("AD_SINC_TANGERINO", "N");
					entityUpd.fluidUpdate();
				}
			});
		} catch (Exception e) {
			new LogItem("Erro ao salvar setor: ", e.getMessage(), "C", MensagemLog.ERRO);
		} finally {
			JapeSession.close(hnd);
		}
		return null;
	}


	@Override
	public String toString() {
		return "Setor{" +
				"codSetor=" + codSetor +
				", nomeSetor='" + nomeSetor + '\'' +
				", idTangerino=" + idTangerino +
				'}';
	}
}
