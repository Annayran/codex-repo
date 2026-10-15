package tech.skala.action.modules.centrais;

import java.math.BigDecimal;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.beans.interfaces.LoadableByPrimaryKey;
import tech.skala.core.beans.interfaces.LoadableByValueObject;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.DataDictionaryExceptions.RecordNotFoundException;

public class CabecalhoNotaCompra extends CabecalhoNota implements LoadableByPrimaryKey, LoadableByValueObject  {
	
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
		this.codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		this.nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		this.numNota = vo.asBigDecimal(SklFieldName.NUMNOTA);
		this.serieNota = vo.asString("SERIENOTA");
		this.codParc = vo.asBigDecimal(SklFieldName.CODPARC);
		this.codParcTransp = vo.asBigDecimal(SklFieldName.CODPARCTRANSP);
		this.codTipOper = vo.asBigDecimal(SklFieldName.CODTIPOPER);
		this.dhTipOper = vo.asTimestamp("DHTIPOPER");
		this.codTipVenda = vo.asBigDecimal("CODTIPVENDA");
		this.dhTipVenda = vo.asTimestamp("DHTIPVENDA");
		this.tipMov = vo.asString(SklFieldName.TIPMOV);
		this.dtNeg = vo.asTimestamp(SklFieldName.DTNEG);
		this.dtFatur = vo.asTimestamp(SklFieldName.DTFATUR);
		this.codNat = vo.asBigDecimal("CODNAT");
		this.codCencus = vo.asBigDecimal(SklFieldName.CODCENCUS);
		this.statusNota = vo.asString(SklFieldName.STATUSNOTA);
		this.pendente = vo.asString(SklFieldName.PENDENTE);
		this.chaveNFe = vo.asString("CHAVENFE");
		this.ordemCarga = vo.asBigDecimal(SklFieldName.ORDEMCARGA);
		
		BigDecimal ocPai = vo.asBigDecimal("OrdemCarga.AD_ORDEMCARGA");
		this.ordemCargaPai = (ocPai == null) ? ordemCarga : ocPai;
		
		this.isConfirmada = StatusNotaEnum.LIBERADA.getValue().equals(statusNota);
	}
}
