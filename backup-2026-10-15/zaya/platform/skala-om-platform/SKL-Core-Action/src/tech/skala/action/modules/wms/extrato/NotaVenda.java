package tech.skala.action.modules.wms.extrato;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.beans.parceiro.Cliente;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class NotaVenda implements EntityBean, LoadableByResultSet {
	private BigDecimal						codEmp;
	private BigDecimal						ordemCarga;
	private BigDecimal						nuNota;
	private BigDecimal						codTipOper;
	private String							tipMov;
	private BigDecimal						numNota;
	private Timestamp						dtNeg;
	private Timestamp						dtFatur;
	private Cliente							cliente;

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public BigDecimal getOrdemCarga() {
		return ordemCarga;
	}

	public BigDecimal getNuNota() {
		return nuNota;
	}

	public BigDecimal getCodTipOper() {
		return codTipOper;
	}

	public String getTipMov() {
		return tipMov;
	}

	public BigDecimal getNumNota() {
		return numNota;
	}

	public Timestamp getDtNeg() {
		return dtNeg;
	}

	public Timestamp getDtFatur() {
		return dtFatur;
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	public BigDecimal getCodParc() {
		return cliente.getCodParc();
	}
	
	public String getRazaoSocial() {
		return cliente.getRazaoSocial();
	}
	
	public String getCgcCnpj() {
		return cliente.getCgcCnpj();
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			this.codEmp = rs.getBigDecimal(SklFieldName.CODEMP);
			this.ordemCarga = rs.getBigDecimal(SklFieldName.ORDEMCARGA);
			this.nuNota = rs.getBigDecimal(SklFieldName.NUNOTA);
			this.codTipOper = rs.getBigDecimal(SklFieldName.CODTIPOPER);
			this.tipMov = rs.getString(SklFieldName.TIPMOV);
			this.numNota = rs.getBigDecimal(SklFieldName.NUMNOTA);
			this.dtNeg = rs.getTimestamp(SklFieldName.DTNEG);
			this.dtFatur = rs.getTimestamp(SklFieldName.DTFATUR);
			
			this.cliente = new Cliente(rs.getBigDecimal(SklFieldName.CODPARC));
			this.cliente.loadByPk(rs.getBigDecimal(SklFieldName.CODPARC));
			
		} catch (SQLException e) {
			throw new DataDictionaryExceptions.RecordNotFoundException(e);
		}

	}

	@Override
	public String toString() {
		return String.format(
				"NotaVenda [codEmp=%s, ordemCarga=%s, nuNota=%s, codTipOper=%s, tipMov=%s, numNota=%s, dtNeg=%s, dtFatur=%s, cliente=%s]",
				codEmp, ordemCarga, nuNota, codTipOper, tipMov, numNota, dtNeg, dtFatur, cliente);
	}

}
