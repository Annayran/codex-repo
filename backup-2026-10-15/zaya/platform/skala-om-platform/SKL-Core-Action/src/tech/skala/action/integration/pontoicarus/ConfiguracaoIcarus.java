package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.vo.DynamicVO;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityUpdate;

public class ConfiguracaoIcarus implements EntityBean, LoadableByValueObject, Persistable {
	public static final String					ENTITY_NAME = "AD_PLTPICFG";

	private BigDecimal							codEmp;
	private String								baseUrl;
	private String								username;
	private String								password;
	private String								token;
	private Timestamp							dtToken;
	private boolean								ativo;
	private boolean								newToken;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getDtToken() {
		return dtToken;
	}
	public void setDtToken(Timestamp dtToken) {
		this.dtToken = dtToken;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public boolean isNewToken() {
		return newToken;
	}
	public void setNewToken(boolean newToken) {
		this.newToken = newToken;
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.baseUrl = vo.asString("BASEURL");
		this.username = vo.asString("USERNAME");		
		this.password = vo.asString("PASSWORD");
		this.token = vo.asString("TOKEN");
		this.dtToken = vo.asTimestamp("DTTOKEN");
		this.ativo = vo.asBoolean(SklFieldName.ATIVO);
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		if (token == null)
			return null;
		
		EntityPrimaryKey pk = new EntityPrimaryKey(new Object[] { codEmp });
		
		EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, pk);
		entityUpdate.setField("TOKEN", token);
		entityUpdate.setField("DTTOKEN", TimeUtils.getNow());
		entityUpdate.fluidUpdate();
		
		return null;
	}

}
