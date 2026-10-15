package tech.skala.action.modules.wms.estoque;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityInsert;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class EstoqueProcessor {

	public void alteraDataValidadeDoProduto(BigDecimal codEmp, BigDecimal codLocal, String controle, BigDecimal codEnd,
			BigDecimal codProd, String codVol, BigDecimal codParc, Timestamp dtVal) throws SkalaModelException {

		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			JapeWrapper estoqueEnderecoDAO = JapeFactory.dao("EstoqueEndereco");
			DynamicVO estoqueEnderecoVO = estoqueEnderecoDAO.findByPK(codEmp, codLocal, controle, codEnd, codProd, codVol, codParc);
			Timestamp dtValOld = estoqueEnderecoVO.asTimestamp(SklFieldName.DTVAL);
			
			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					
					DynamicVO estoqueValidadeVO = null;
					JapeWrapper estoqueValidadeDAO = JapeFactory.dao("EstoqueValidadeWMS");
					
					if(dtValOld != null) {
						estoqueValidadeVO = estoqueValidadeDAO.findByPK(codEmp, codLocal, controle, codEnd, codProd, codVol, codParc, dtValOld);
						
						Timestamp dtValEstoqueValidade = estoqueValidadeVO.asTimestamp(SklFieldName.DTVAL);
						if(dtValEstoqueValidade == dtValOld) {
							estoqueValidadeVO = null;
						}
					}

					alterarDataValidadeDoProduto(estoqueEnderecoVO, estoqueValidadeVO, dtVal);

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível trocar a data de validade dos produtos.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	protected void alterarDataValidadeDoProduto(DynamicVO estoqueEnderecoVO, DynamicVO estoqueValidadeVO, Timestamp dtVal) throws SkalaModelException {
		
		EntityUpdate update = new EntityUpdate("EstoqueEndereco", estoqueEnderecoVO);
		update.setField(SklFieldName.DTVAL, dtVal);
		update.fluidUpdate();
		
		if (estoqueValidadeVO != null) {
//			EntityInsert insert = new EntityInsert("EstoqueValidadeWMS");
//			insert.setField(SklFieldName.CODEMP, estoqueEnderecoVO.asBigDecimal(SklFieldName.CODEMP));
//			insert.setField(SklFieldName.CODLOCAL, estoqueEnderecoVO.asBigDecimal(SklFieldName.CODLOCAL));
//			insert.setField(SklFieldName.CODPROD, estoqueEnderecoVO.asBigDecimal(SklFieldName.CODPROD));
//			insert.setField(SklFieldName.CONTROLE, estoqueEnderecoVO.asString(SklFieldName.CONTROLE));
//			insert.setField("CODEND", estoqueEnderecoVO.asBigDecimal("CODEND"));
//			insert.setField(SklFieldName.CODVOL, estoqueEnderecoVO.asString(SklFieldName.CODVOL));
//			insert.setField(SklFieldName.CODPARC, estoqueEnderecoVO.asBigDecimal(SklFieldName.CODPARC));
//			insert.setField("QUANTIDADEVOLPAD", estoqueEnderecoVO.asBigDecimal("ESTOQUEVOLPAD"));
//			insert.setField(SklFieldName.DTVAL, dtVal);
//			insert.fluidInsert();
//		} else {
			update = new EntityUpdate("EstoqueValidadeWMS", estoqueValidadeVO);
			update.setField(SklFieldName.DTVAL, dtVal);
			update.fluidUpdate();
			
		}
	}

}
