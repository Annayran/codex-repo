package tech.skala.action.core.filamensagem;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;

public class FilaMensagem implements EntityBean, Persistable {

	private BigDecimal  				    codFila;
	private String	    				    status;
	private Timestamp   				    dtEntrada;
	private	BigDecimal  				    codCon;
	private BigDecimal  				    maxTentEnvio;
	private BigDecimal  				    tentEnvio;
	private String	    				    mensagem;
	private String	    				    tipoEnvio;
	private	String	    				    assunto;
	private String	    				    email;
	private BigDecimal  				    codUsu;
	private boolean	    				    isReenviar;

	public FilaMensagem(String mensagem, String assunto, String email, BigDecimal codUsu) {
		this.mensagem = mensagem;
		this.assunto = assunto;
		this.email = email;
		this.codUsu = codUsu;
	}

	public BigDecimal getCodFila() {
		return codFila;
	}

	public void setCodFila(BigDecimal codFila) {
		this.codFila = codFila;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(Timestamp dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	public BigDecimal getCodCon() {
		return codCon;
	}

	public void setCodCon(BigDecimal codCon) {
		this.codCon = codCon;
	}

	public BigDecimal getMaxTentEnvio() {
		return maxTentEnvio;
	}

	public void setMaxTentEnvio(BigDecimal maxTentEnvio) {
		this.maxTentEnvio = maxTentEnvio;
	}

	public BigDecimal getTentEnvio() {
		return tentEnvio;
	}

	public void setTentEnvio(BigDecimal tentEnvio) {
		this.tentEnvio = tentEnvio;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(BigDecimal codUsu) {
		this.codUsu = codUsu;
	}

	public boolean isReenviar() {
		return isReenviar;
	}

	public void setReenviar(boolean reenviar) {
		this.isReenviar = reenviar;
	}

	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		EntityInsert entityInsert = new EntityInsert(DynamicEntityNames.FILA_MSG);

		entityInsert.setField("CODSMTP", BigDecimal.ONE);
		entityInsert.setField("DTENTRADA", TimeUtils.getNow());
		entityInsert.setField("STATUS", "Pendente");
		entityInsert.setField("CODCON", BigDecimal.ZERO);
		entityInsert.setField("TENTENVIO", BigDecimal.ONE);
		entityInsert.setField("MENSAGEM", mensagem.toCharArray());
		entityInsert.setField("TIPOENVIO", "E");
		entityInsert.setField("MAXTENTENVIO", BigDecimal.valueOf(3));
		entityInsert.setField("ASSUNTO", assunto);
		entityInsert.setField("EMAIL", email);
		entityInsert.setField("CODUSU", codUsu);
		entityInsert.setField("REENVIAR", "N");

		entityInsert.fluidInsert();

		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"FilaMensagem [codFila=%s, status=%s, dtEntrada=%s, codCon=%s, maxTentEnvio=%s, tentEnvio=%s, mensagem=%s, tipoEnvio=%s, assunto=%s, email=%s, codUsu=%s, isReenviar=%s]",
				codFila, status, dtEntrada, codCon, maxTentEnvio, tentEnvio, mensagem, tipoEnvio, assunto, email,
				codUsu, isReenviar);
	}

}

