package tech.skala.action.modules.manufatura.planta;

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
 * A classe {@code PlantaManufatura} representa a tabela {@code TPRPLP}.
 *  
 * <p>
 * A classe {@code PlantaManufatura} representa a entidade <i>PlantaManufatura</i> {@code TPRPLP} 
 * que sao os Plantas de Manufaturas utilizadas na producao.
 * </p> 
 * 
 * @author hiagoalexandre
 * @since 2022-08-05
 *
 */
public class PlantaManufatura implements EntityBean, LoadableByPrimaryKey, LoadableByValueObject {
	
	public static final String ENTITY_NAME = DynamicEntityNames.PLANTA_MANUFATURA;
	
	private	BigDecimal								codPlp;
	private	String	 								nome;
	private	BigDecimal								codEmp;
	private	BigDecimal 								codCargaHor;
	private	BigDecimal 								codCenCus;
	
	public BigDecimal getCodPlp() {
		return codPlp;
	}

	public void setCodPlp(BigDecimal codPlp) {
		this.codPlp = codPlp;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodCargaHor() {
		return codCargaHor;
	}

	public void setCodCargaHor(BigDecimal codCargaHor) {
		this.codCargaHor = codCargaHor;
	}

	public BigDecimal getCodCenCus() {
		return codCenCus;
	}

	public void setCodCenCus(BigDecimal codCenCus) {
		this.codCenCus = codCenCus;
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
		this.codPlp = vo.asBigDecimal(SklFieldName.CODPLP);
		this.nome = vo.asString("NOME");
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.codCargaHor = vo.asBigDecimal("CODCARGAHOR");
		this.codCenCus = vo.asBigDecimal(SklFieldName.CODCENCUS);
	}

	@Override
	public String toString() {
		return String.format("PlantaManufatura [codPlp=%s, nome=%s, codEmp=%s, codCargaHor=%s, codCenCus=%s]", codPlp,
				nome, codEmp, codCargaHor, codCenCus);
	}

}
