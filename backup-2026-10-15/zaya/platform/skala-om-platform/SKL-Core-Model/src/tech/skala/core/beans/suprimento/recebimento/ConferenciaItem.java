package tech.skala.core.beans.suprimento.recebimento;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.EntityBean;
import tech.skala.core.beans.interfaces.LoadableByResultSet;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.beans.interfaces.Persistable;
import tech.skala.core.beans.produto.MateriaPrima;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.InsertNotPossibleException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;
import tech.skala.core.commons.dd.DataDictionaryExceptions.UpdateNotPossibleException;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;

public class ConferenciaItem implements EntityBean, LoadableByValueObject, Persistable, LoadableByResultSet{
	public static final String				ENTITY_NAME = SklEntityName.MOBILE_CONFERENCIA_ITEM;

	private BigDecimal						nuConferencia;
	private BigDecimal						sequencia;
	private BigDecimal						codParc;
	private BigDecimal						codProd;
	private MateriaPrima					produto;
	private String							codBarra;
	private String							codProdParc;
	private String							codBarraParc;
	private BigDecimal						codEmp;
	private BigDecimal  					codLocal;
	private String							controle;
	private BigDecimal						qtdNeg;
	private String							codVol;
	private BigDecimal						qtdUnidPad;
	private String							codVolPad;
	private BigDecimal						fator;
	private BigDecimal						vlrUnit;
	private Timestamp						dtVal;
	private Timestamp						dtFabricacao;
	private BigDecimal    					nuNotaOrig;
	private BigDecimal						sequenciaOrig;

	private boolean							isRecontagem;
	private BigDecimal						qtdAvaria;
	private BigDecimal						qtdConf;
	private String							codVolConf;
	private String							controleConf;
	private Timestamp						dtValConf;
	private Timestamp						dtFabricacaoConf;
	private BigDecimal						qtdVolume;
	private BigDecimal 						qtdUnitVol;

	
	public ConferenciaItem(BigDecimal nuConferencia, BigDecimal codParc) {
		this.nuConferencia = nuConferencia;
		this.codParc = codParc;
	}
		
	public BigDecimal getNuConferencia() {
		return nuConferencia;
	}

	public BigDecimal getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigDecimal sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getCodParc() {
		return codParc;
	}

	public BigDecimal getCodProd() {
		return codProd;
	}

	public void setCodProd(BigDecimal codProd) {
		this.codProd = codProd;
	}

	public MateriaPrima getProduto() {
		return produto;
	}

	public void setProduto(MateriaPrima produto) {
		this.produto = produto;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public String getCodProdParc() {
		return codProdParc;
	}

	public void setCodProdParc(String codProdParc) {
		this.codProdParc = codProdParc;
	}

	public String getCodBarraParc() {
		return codBarraParc;
	}

	public void setCodBarraParc(String codBarraParc) {
		this.codBarraParc = codBarraParc;
	}

	public BigDecimal getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(BigDecimal codEmp) {
		this.codEmp = codEmp;
	}

	public BigDecimal getCodLocal() {
		return codLocal;
	}
	
	public void setCodLocal(BigDecimal codLocal) {
		this.codLocal = codLocal;
	}
	
	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public BigDecimal getQtdNeg() {
		return qtdNeg;
	}

	public void setQtdNeg(BigDecimal qtdNeg) {
		this.qtdNeg = qtdNeg;
	}

	public String getCodVol() {
		return codVol;
	}

	public void setCodVol(String codVol) {
		this.codVol = codVol;
	}

	public BigDecimal getQtdUnidPad() {
		return qtdUnidPad;
	}

	public void setQtdUnidPad(BigDecimal qtdUnidPad) {
		this.qtdUnidPad = qtdUnidPad;
	}

	public String getCodVolPad() {
		return codVolPad;
	}

	public void setCodVolPad(String codVolPad) {
		this.codVolPad = codVolPad;
	}

	public BigDecimal getFator() {
		return fator;
	}

	public void setFator(BigDecimal fator) {
		this.fator = fator;
	}

	public BigDecimal getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(BigDecimal vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public Timestamp getDtVal() {
		return dtVal;
	}

	public void setDtVal(Timestamp dtVal) {
		this.dtVal = dtVal;
	}

	public Timestamp getDtFabricacao() {
		return dtFabricacao;
	}

	public void setDtFabricacao(Timestamp dtFabricacao) {
		this.dtFabricacao = dtFabricacao;
	}

	public BigDecimal getNuNotaOrig() {
		return nuNotaOrig;
	}

	public void setNuNotaOrig(BigDecimal nuNotaOrig) {
		this.nuNotaOrig = nuNotaOrig;
	}

	public BigDecimal getSequenciaOrig() {
		return sequenciaOrig;
	}

	public void setSequenciaOrig(BigDecimal sequenciaOrig) {
		this.sequenciaOrig = sequenciaOrig;
	}

	public boolean isRecontagem() {
		return isRecontagem;
	}
	
	public void setRecontagem(boolean isRecontagem) {
		this.isRecontagem = isRecontagem;
	}
	
	public BigDecimal getQtdAvaria() {
		return qtdAvaria;
	}

	public void setQtdAvaria(BigDecimal qtdAvaria) {
		this.qtdAvaria = qtdAvaria;
	}

	public BigDecimal getQtdConf() {
		return qtdConf;
	}

	public void setQtdConf(BigDecimal qtdConf) {
		this.qtdConf = qtdConf;
	}

	public String getCodVolConf() {
		return codVolConf;
	}

	public void setCodVolConf(String codVolConf) {
		this.codVolConf = codVolConf;
	}

	public String getControleConf() {
		return controleConf;
	}

	public void setControleConf(String controleConf) {
		this.controleConf = controleConf;
	}

	public Timestamp getDtValConf() {
		return dtValConf;
	}

	public void setDtValConf(Timestamp dtValConf) {
		this.dtValConf = dtValConf;
	}

	public Timestamp getDtFabricacaoConf() {
		return dtFabricacaoConf;
	}

	public void setDtFabricacaoConf(Timestamp dtFabricacaoConf) {
		this.dtFabricacaoConf = dtFabricacaoConf;
	}

	public BigDecimal getQtdVolume() {
		return qtdVolume;
	}

	public void setQtdVolume(BigDecimal qtdVolume) {
		this.qtdVolume = qtdVolume;
	}

	public BigDecimal getQtdUnitVol() {
		return qtdUnitVol;
	}

	public void setQtdUnitVol(BigDecimal qtdUnitVol) {
		this.qtdUnitVol = qtdUnitVol;
	}

	@Override
	public void loadByResultSet(ResultSet rs) throws RecordNotFoundException {
		try {
			this.codParc = rs.getBigDecimal(SklFieldName.CODPARC);
			this.codProd = rs.getBigDecimal(SklFieldName.CODPROD);
			this.produto = findProduto();
			this.codBarra = rs.getString(SklFieldName.CODBARRA);
			this.codProdParc = rs.getString(SklFieldName.CODPROPARC);
			this.codBarraParc = rs.getString(SklFieldName.CODBARRAPARC);
			this.codEmp = rs.getBigDecimal(SklFieldName.CODEMP);
			this.codLocal = rs.getBigDecimal(SklFieldName.CODLOCALORIG);
			this.controle = rs.getString(SklFieldName.CONTROLE);
			this.qtdNeg = rs.getBigDecimal(SklFieldName.QTDNEG);
			this.codVol = rs.getString(SklFieldName.CODVOL);
			this.qtdUnidPad = rs.getBigDecimal(SklFieldName.QTDUNIDPAD);
			this.codVolPad = rs.getString(SklFieldName.CODVOLPAD);
			this.vlrUnit = rs.getBigDecimal(SklFieldName.VLRUNIT);
			this.dtVal = rs.getTimestamp(SklFieldName.DTVAL);
			this.dtFabricacao = rs.getTimestamp(SklFieldName.DTFABRICACAO);
			this.nuNotaOrig = rs.getBigDecimal(SklFieldName.NUNOTA);
			this.sequenciaOrig = rs.getBigDecimal(SklFieldName.SEQUENCIA);
			
			this.qtdConf = rs.getBigDecimal(SklFieldName.QTDNEG);
			this.codVolConf = rs.getString(SklFieldName.CODVOL);
			this.controleConf = rs.getNString(SklFieldName.CONTROLE);
			this.dtValConf = rs.getTimestamp(SklFieldName.DTVAL);
			this.dtFabricacaoConf = rs.getTimestamp(SklFieldName.DTFABRICACAO);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordNotFoundException(e);
		}
	}

	private MateriaPrima findProduto() throws RecordNotFoundException {

		MateriaPrima materiaPrima = null;
		try {

			materiaPrima = new MateriaPrima(codProd);
			materiaPrima.loadByPk();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RecordNotFoundException(e);
		}
		
		return materiaPrima;
		
	}
	
	@Override
	public void loadByVO(DynamicVO vo) throws RecordNotFoundException {
		this.nuConferencia = vo.asBigDecimal(SklFieldName.NUCONFERENCIA);
		this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		this.codProd = vo.asBigDecimal(SklFieldName.CODPROD);
		this.produto = findProduto();
		this.codBarra = vo.asString(SklFieldName.CODBARRA);
		this.codProdParc = vo.asString(SklFieldName.CODPROPARC);
		this.codBarraParc = vo.asString(SklFieldName.CODBARRAPARC);
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.codLocal = vo.asBigDecimal(SklFieldName.CODLOCAL);
		this.controle = vo.asString(SklFieldName.CONTROLE);
		this.qtdNeg = vo.asBigDecimal(SklFieldName.QTDNEG);
		this.codVol = vo.asString(SklFieldName.CODVOL);
		this.qtdUnidPad = vo.asBigDecimal(SklFieldName.QTDUNIDPAD);
		this.codVolPad = vo.asString(SklFieldName.CODVOLPAD);
		this.fator = vo.asBigDecimal(SklFieldName.FATOR);
		this.vlrUnit = vo.asBigDecimal(SklFieldName.VLRUNIT);
		this.dtVal = vo.asTimestamp(SklFieldName.DTVAL);
		this.dtFabricacao = vo.asTimestamp(SklFieldName.DTFABRICACAO);
		this.nuNotaOrig = vo.asBigDecimal(SklFieldName.NUNOTAORIG);
		this.sequenciaOrig = vo.asBigDecimal(SklFieldName.SEQUENCIAORIG);

		this.isRecontagem = vo.asBoolean(SklFieldName.RECONTAGEM);
		this.qtdAvaria = vo.asBigDecimal(SklFieldName.QTDAVARIA);
		this.qtdConf = vo.asBigDecimal(SklFieldName.QTDECONF);
		this.codVolConf = vo.asString(SklFieldName.CODVOLCONF);
		this.controleConf = vo.asString(SklFieldName.CONTROLECONF);
		this.dtValConf = vo.asTimestamp(SklFieldName.DTVALCONF);
		this.dtFabricacaoConf = vo.asTimestamp(SklFieldName.DTFABRICACAOCONF);
		this.qtdVolume = vo.asBigDecimal(SklFieldName.QTDVOLUME);
		this.qtdUnitVol = vo.asBigDecimal(SklFieldName.QTDUNITVOL);
	}
	
	@Override
	public DynamicVO persists() throws InsertNotPossibleException, UpdateNotPossibleException {
		
		DynamicVO conferenciaItemVO = null;
		try {
			conferenciaItemVO = findVO();
		} catch (RecordNotFoundException e1) {
			throw new UpdateNotPossibleException(e1);
		} 
		
		if (conferenciaItemVO == null) {
			EntityInsert entityNew = new EntityInsert(ENTITY_NAME);
			entityNew.setField(SklFieldName.NUCONFERENCIA, nuConferencia);
			entityNew.setField(SklFieldName.CODPROD, codProd);
			entityNew.setField(SklFieldName.CODBARRA, codBarra);
			entityNew.setField(SklFieldName.CODPROPARC, codProdParc);
			entityNew.setField(SklFieldName.CODBARRAPARC, codBarraParc);
			entityNew.setField(SklFieldName.CODEMP, codEmp);
			entityNew.setField(SklFieldName.CODLOCAL, codLocal);
			entityNew.setField(SklFieldName.CONTROLE, controle);
			entityNew.setField(SklFieldName.QTDNEG, qtdNeg);
			entityNew.setField(SklFieldName.CODVOL, codVol);
			entityNew.setField(SklFieldName.QTDUNIDPAD, qtdUnidPad);
			entityNew.setField(SklFieldName.CODVOLPAD, codVolPad);
			entityNew.setField(SklFieldName.RECONTAGEM, "N");
			entityNew.setField(SklFieldName.VLRUNIT, vlrUnit);
			entityNew.setField(SklFieldName.DTVAL, dtVal);
			entityNew.setField(SklFieldName.DTFABRICACAO, dtFabricacao);
			entityNew.setField(SklFieldName.NUNOTAORIG, nuNotaOrig);
			entityNew.setField(SklFieldName.SEQUENCIAORIG, sequenciaOrig);
			
			BigDecimal qtdConferida = BigDecimalUtil.getValue(qtdConf, qtdNeg);
			
			entityNew.setField(SklFieldName.QTDECONF, qtdConferida);
			entityNew.setField(SklFieldName.CODVOLCONF, codVol);
			entityNew.setField(SklFieldName.CONTROLECONF, controle);
			entityNew.setField(SklFieldName.DTVALCONF, TimeUtils.getNow());
			entityNew.setField(SklFieldName.DTVALCONF, dtVal);
			entityNew.setField(SklFieldName.DTFABRICACAOCONF, dtFabricacao);
			entityNew.setField(SklFieldName.QTDVOLUME, qtdVolume);
			entityNew.setField(SklFieldName.QTDUNITVOL, qtdUnitVol);
			
			DynamicVO vo = entityNew.fluidInsert();
			this.sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);

		} else {
			EntityUpdate entityUpd = new EntityUpdate(ENTITY_NAME, conferenciaItemVO);
			entityUpd.setField(SklFieldName.RECONTAGEM, (isRecontagem) ? "S" : "N");
			entityUpd.setField(SklFieldName.QTDAVARIA, qtdAvaria);
			entityUpd.setField(SklFieldName.QTDECONF, qtdConf);
			entityUpd.setField(SklFieldName.CODVOLCONF, codVolConf);
			entityUpd.setField(SklFieldName.CONTROLECONF, controleConf);
			entityUpd.setField(SklFieldName.DTVALCONF, dtValConf);
			entityUpd.setField(SklFieldName.DTFABRICACAOCONF, dtFabricacaoConf);
			entityUpd.setField(SklFieldName.QTDVOLUME, qtdVolume);
			entityUpd.setField(SklFieldName.QTDUNITVOL, qtdUnitVol);
			
			entityUpd.fluidUpdate();
		}
		return null;
	}

	private DynamicVO findVO() throws RecordNotFoundException {
		JapeWrapper dao = JapeFactory.dao(ENTITY_NAME);
		DynamicVO vo = null;
		try {
			if (nuConferencia != null && sequencia != null)
				vo = dao.findByPK(nuConferencia, sequencia);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecordNotFoundException(e);
		}
		
		return vo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nuConferencia, sequencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ConferenciaItem))
			return false;
		ConferenciaItem other = (ConferenciaItem) obj;
		return Objects.equals(nuConferencia, other.nuConferencia) && Objects.equals(sequencia, other.sequencia);
	}

	@Override
	public String toString() {
		return String.format(
				"ConferenciaItem [nuConferencia=%s, sequencia=%s, codParc=%s, codProd=%s, produto=%s, codBarra=%s, codProdParc=%s, codBarraParc=%s, codEmp=%s, codLocal=%s, controle=%s, qtdNeg=%s, codVol=%s, qtdUnidPad=%s, codVolPad=%s, fator=%s, vlrUnit=%s, dtVal=%s, dtFabricacao=%s, nuNotaOrig=%s, sequenciaOrig=%s, isRecontagem=%s, qtdAvaria=%s, qtdConf=%s, codVolConf=%s, controleConf=%s, dtValConf=%s, dtFabricacaoConf=%s, qtdVolume=%s, qtdUnitVol=%s]",
				nuConferencia, sequencia, codParc, codProd, produto, codBarra, codProdParc, codBarraParc, codEmp,
				codLocal, controle, qtdNeg, codVol, qtdUnidPad, codVolPad, fator, vlrUnit, dtVal, dtFabricacao,
				nuNotaOrig, sequenciaOrig, isRecontagem, qtdAvaria, qtdConf, codVolConf, controleConf, dtValConf,
				dtFabricacaoConf, qtdVolume, qtdUnitVol);
	}

	

}
