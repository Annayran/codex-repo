package tech.skala.modules.manufatura.workcenter;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

/**
 * A classe {@code WorkCenter} representa a tabela {@code TPRWCP}.
 *  
 * <p>
 * A classe {@code WorkCenter} representa a entidade <i>WorkCenter</i> {@code TPRWCP} 
 * que sao os Centros de Trabalho ou as Linhas de Producao.
 * </p> 
 * 
 * @author cassiomenezes
 * @author hiagoalexandre
 * @since 2022-07-27
 *
 */
public class WorkCenter implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	public static final String ENTITY_NAME = DynamicEntityNames.WORK_CENTER;

	private BigDecimal codWcp;
	private String nome;
	private BigDecimal qtdCapacidadePad;
	private BigDecimal pesoPad;
	
	public BigDecimal getCodWcp() {
		return codWcp;
	}

	public void setCodWcp(BigDecimal codWcp) {
		this.codWcp = codWcp;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getQtdCapacidadePad() {
		return qtdCapacidadePad;
	}

	public void setQtdCapacidadePad(BigDecimal qtdCapacidadePad) {
		this.qtdCapacidadePad = qtdCapacidadePad;
	}

	public BigDecimal getPesoPad() {
		return pesoPad;
	}

	public void setPesoPad(BigDecimal pesoPad) {
		this.pesoPad = pesoPad;
	}

	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		if (codWcp == null)
			this.codWcp = vo.asBigDecimal(SklFieldName.CODWCP);

		this.nome = vo.asString(SklFieldName.NOME);
		this.qtdCapacidadePad = vo.asBigDecimalOrZero(SklFieldName.QTDCAPACIDADEPAD);
		this.pesoPad = vo.asBigDecimal(SklFieldName.AD_PESOPAD);
	}

	@Override
	public void loadByPk(Object... keyValue) throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);

		try {
			DynamicVO vo = (codWcp == null) ? dao.findByPK(keyValue) : dao.findByPK(codWcp);
			loadByVO(vo);
		} catch (Exception e) {
			throw new RecordNotFoundException(e);
		}

	}

}
