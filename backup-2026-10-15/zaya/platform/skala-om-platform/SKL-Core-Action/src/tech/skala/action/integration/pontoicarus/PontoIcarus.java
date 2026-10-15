package tech.skala.action.integration.pontoicarus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class PontoIcarus implements EntityBean, Persistable {
	public static final String					ENTITY_NAME = "AD_PLTPIPON";
	
	private BigDecimal							codEmp;
	private BigDecimal							codFuncionario;
	private Timestamp							dtMovimento;
	private String								matricula;
	private BigDecimal							total;
	private String								batidas;
	private BigDecimal							normal;
	private BigDecimal							faltas;
	private BigDecimal							noturna;
	private BigDecimal							atestado;
	private BigDecimal							trabalhado;
	
	public BigDecimal getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}
	public BigDecimal getCodFuncionario() {
		return codFuncionario;
	}
	public void setCodFuncionario(BigDecimal codFuncionario) {
		this.codFuncionario = codFuncionario;
	}
	public Timestamp getDtMovimento() {
		return dtMovimento;
	}
	public void setDtMovimento(Timestamp dtMovimento) {
		this.dtMovimento = dtMovimento;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getBatidas() {
		return batidas;
	}
	public void setBatidas(String batidas) {
		this.batidas = batidas;
	}
	public BigDecimal getNormal() {
		return normal;
	}
	public void setNormal(BigDecimal normal) {
		this.normal = normal;
	}
	public BigDecimal getFaltas() {
		return faltas;
	}
	public void setFaltas(BigDecimal faltas) {
		this.faltas = faltas;
	}
	public BigDecimal getNoturna() {
		return noturna;
	}
	public void setNoturna(BigDecimal noturna) {
		this.noturna = noturna;
	}
	public BigDecimal getAtestado() {
		return atestado;
	}
	public void setAtestado(BigDecimal atestado) {
		this.atestado = atestado;
	}
	public BigDecimal getTrabalhado() {
		return trabalhado;
	}
	public void setTrabalhado(BigDecimal trabalhado) {
		this.trabalhado = trabalhado;
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		
		DynamicVO vo = null;
		try {
			this.dtMovimento = TimeUtils.clearTime(dtMovimento);
			
			vo = dao.findByPK(codEmp, codFuncionario, dtMovimento);
			if (vo == null) {
				EntityInsert entityInsert = new EntityInsert(ENTITY_NAME);
				entityInsert.setField(SklFieldName.CODEMP, codEmp);
				entityInsert.setField(SklFieldName.CODFUNC, codFuncionario);
				entityInsert.setField("DTMOV", dtMovimento);
				entityInsert.setField("BATIDAS", batidas);
				entityInsert.setField("NORMAL", normal);
				entityInsert.setField("FALTAS", faltas);
				entityInsert.setField("NOTURNA", noturna);
				entityInsert.setField("ATESTADO", atestado);
				entityInsert.setField("TRABALHADO", trabalhado);
				entityInsert.setField("TOTAL", total);
				
				vo = entityInsert.fluidInsert();
			} else {
				EntityUpdate entityUpdate = new EntityUpdate(ENTITY_NAME, vo);
				entityUpdate.setField("BATIDAS", batidas);
				entityUpdate.setField("NORMAL", normal);
				entityUpdate.setField("FALTAS", faltas);
				entityUpdate.setField("NOTURNA", noturna);
				entityUpdate.setField("ATESTADO", atestado);
				entityUpdate.setField("TRABALHADO", trabalhado);
				entityUpdate.setField("TOTAL", total);

				entityUpdate.fluidUpdate();
			}			
			
		} catch (Exception e) {
			throw new DataDictionaryExceptions.InsertNotPossibleException(e);
		}

		return vo;
	}
	
	@Override
	public String toString() {
		return String.format(
				"PontoIcarus [codEmp=%s, codFuncionario=%s, dtMovimento=%s, matricula=%s, total=%s, batidas=%s, normal=%s, faltas=%s, noturna=%s, atestado=%s, trabalhado=%s]",
				codEmp, codFuncionario, dtMovimento, matricula, total, batidas, normal, faltas, noturna, atestado,
				trabalhado);
	}
}
