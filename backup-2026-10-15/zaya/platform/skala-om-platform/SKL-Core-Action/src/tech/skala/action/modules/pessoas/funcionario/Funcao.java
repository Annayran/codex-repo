package tech.skala.action.modules.pessoas.funcionario;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * A classe {@code Funcao} representa a tabela {@code TFPFCO}.
 *  
 * <p>
 * A classe {@code Funcao} representa uma funcao exercida por um colaborador 
 * no modelo de negocios. Informacoes relevantes da propria entidade e de 
 * entidades relacionadas sao tratadas aqui.
 * </p> 
 * 
 * @author cassiomenezes
 * @since 2023-01-23
 */
public class Funcao implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String					ENTITY_NAME = DynamicEntityNames.FUNCAO;
	
	private BigDecimal							codFuncao;
	private String								descrFuncao;
	private String								codCbo;
	private BigDecimal							tangerinoId;
	
	public BigDecimal getCodFuncao() {
		return codFuncao;
	}
	public void setCodFuncao(BigDecimal codFuncao) {
		this.codFuncao = codFuncao;
	}
	public String getDescrFuncao() {
		return descrFuncao;
	}
	public void setDescrFuncao(String descrFuncao) {
		this.descrFuncao = descrFuncao;
	}
	public String getCodCbo() {
		return codCbo;
	}
	public void setCodCbo(String codCbo) {
		this.codCbo = codCbo;
	}
	public BigDecimal getTangerinoId() {
		return tangerinoId;
	}
	public void setTangerinoId(BigDecimal tangerinoId) {
		this.tangerinoId = tangerinoId;
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
		this.codFuncao = vo.asBigDecimal("CODFUNCAO");
		this.descrFuncao = vo.asString("DESCRFUNCAO");
		this.codCbo = vo.asString("CODCBO");
		this.tangerinoId = vo.asBigDecimal("AD_ID_TANGERINO");
	}
	
	@Override
	public String toString() {
		return String.format("Funcao [codFuncao=%s, descrFuncao=%s, codCbo=%s, tangerinoId=%s]", codFuncao, descrFuncao,
				codCbo, tangerinoId);
	}
	
}
