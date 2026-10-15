package tech.skala.action.modules.manufatura.processoprodutivo;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class ProcessoProdutivoResumo implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	
	public static final String ENTITY_NAME = "AD_PLVPRPRC";
	
	private BigDecimal								idProc;
	private BigDecimal								codPrc;
	private BigDecimal								versao;
	private boolean									padrao;
	private String									descrAbrev;
	private boolean									usaTerceiro;
	
	public BigDecimal getIdProc() {
		return idProc;
	}

	public void setIdProc(BigDecimal idProc) {
		this.idProc = idProc;
	}

	public BigDecimal getCodPrc() {
		return codPrc;
	}

	public void setCodPrc(BigDecimal codPrc) {
		this.codPrc = codPrc;
	}

	public BigDecimal getVersao() {
		return versao;
	}

	public void setVersao(BigDecimal versao) {
		this.versao = versao;
	}

	public boolean getPadrao() {
		return padrao;
	}

	public void setPadrao(boolean padrao) {
		this.padrao = padrao;
	}

	public String getDescrAbrev() {
		return descrAbrev;
	}

	public void setDescrAbrev(String descrAbrev) {
		this.descrAbrev = descrAbrev;
	}

	public boolean getUsaTerceiro() {
		return usaTerceiro;
	}
	
	public void setUsaTerceiro(boolean usaTerceiro) {
		this.usaTerceiro = usaTerceiro;
	}

	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = dao.findByPK(keyValue);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}		
	}


	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.idProc = vo.asBigDecimal("IDPROC");
		this.codPrc = vo.asBigDecimal("CODPRC");
		this.versao = vo.asBigDecimal("VERSAO");
		this.padrao = "S".equals(vo.asString("PADRAO"));
		this.descrAbrev = vo.asString("DESCRABREV");
		this.usaTerceiro = "S".equals(vo.asString("USATERCEIRO"));
	}
	
	
}
