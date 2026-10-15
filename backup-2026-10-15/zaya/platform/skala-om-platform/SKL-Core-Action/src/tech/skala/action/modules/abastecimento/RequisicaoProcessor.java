package tech.skala.action.modules.abastecimento;

import java.math.BigDecimal;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import tech.skala.action.modules.comercial.estoque.beans.CabecalhoNota;
import tech.skala.core.beans.cabecalhonota.CabecalhoNotaProcessor;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class RequisicaoProcessor {

	private BigDecimal nuNota;

	public void liberaRequisicao(BigDecimal nuReq) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					criaLancamentoRequisicao(nuReq);
					alteraStatusRequisicao(nuReq);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível liberar Requisição", e);
		} finally {
			JapeSession.close(hnd);
		}

		if (nuNota != null)
			CabecalhoNotaProcessor.confirmarNota(nuNota);
		
	}

	protected void criaLancamentoRequisicao(BigDecimal nuReq) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.REQUISICOES);

		DynamicVO vo = null;
		try {
			vo = dao.findByPK(nuReq);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Requisição", e);
		}


		if (vo != null) {
			String tipoTransf = vo.asString("TIPOTRANSF");

			CabecalhoNota requisicao = null;
			if (TipoRequisicaoEnum.ALMOX_QUIMICO_PESAGEM.getValue().equals(tipoTransf))
				requisicao = new RequisicaoAlmoxarifadoQuimicoPesagem(nuReq);
			else if (TipoRequisicaoEnum.ALMOX_EMBALAGEM_EMBALAGEM.getValue().equals(tipoTransf))
				requisicao = new RequisicaoAlmoxarifadoEmbalagemEmbalagem(nuReq);
			else
				requisicao = new RequisicaoAlmoxarifadoRotuloRotulo(nuReq);

			requisicao.buildCabecalhoNotaByTemplate();
			requisicao.buildItems();

			EntityUpdate entityUpd = new EntityUpdate(SklEntityName.REQUISICOES, vo);
			entityUpd.setField(SklFieldName.NUNOTA, requisicao.getNuNota());
			entityUpd.fluidUpdate();
			
			this.nuNota = requisicao.getNuNota();
		}

	}

	protected void alteraStatusRequisicao(BigDecimal nuReq) throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(SklEntityName.REQUISICOES);

		DynamicVO vo = null;
		try {
			vo = dao.findByPK(nuReq);
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar Requisição", e);
		}

		if (vo != null) {
			EntityUpdate entityUpd = new EntityUpdate(SklEntityName.REQUISICOES, vo);
			entityUpd.setField(SklFieldName.STATUS, StatusRequisicaoEnum.LIBERADO.getValue());
			entityUpd.fluidUpdate();
		}

	}

}
