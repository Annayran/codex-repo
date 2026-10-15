package br.com.zaya.like.Actions.services;

import java.math.BigDecimal;

import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.zaya.like.Actions.dao.CancelarNegociacaoDAO;

public class CancelarNegociacaoService {

	private static boolean suprimentoCancelado = false;
	private static boolean producaoCancelado = false;
	private static boolean pedidoVendaCancelado = false;
	private static boolean layoutCancelado = false;
	private static boolean entregaCancelado = false;
	
	private static String statusSuprimento;
	private static String statusProducao;
	private static String statusFaturamento;
	private static String statusLayout;
	private static String statusEntrega;
	
	
	private static StringBuilder motivoImpedimento = new StringBuilder();

	public static String processarCancelamento(EntityFacade dwfFacade, String motivoCancelamento, BigDecimal nunegociacao) throws Exception {
		
		 if(verificarNegociacaoFinalizada(nunegociacao)) {
			 return "Negociação Finalizada, não pode ser cancelada";
		 };

		cancelarSuprimento(nunegociacao);

		cancelarProducao(nunegociacao);

		cancelarPedidoFaturamento(nunegociacao);

		cancelarLayout(nunegociacao);

		cancelarEntrega(nunegociacao);

		if (suprimentoCancelado && producaoCancelado && pedidoVendaCancelado && layoutCancelado && entregaCancelado) {
			String msg = cancelarNegociacao(nunegociacao, motivoCancelamento, dwfFacade);
			motivoImpedimento.append(msg);
			return motivoImpedimento.toString();
		}

		return motivoImpedimento.toString();
	}

	private static String cancelarNegociacao(BigDecimal nunegociacao, String motivoCancelamento, EntityFacade dwfFacade) throws Exception {

		DynamicVO negociacaoVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("AD_TGLCAB", new Object[] { nunegociacao });

		JapeFactory.dao("AD_TGLCAB").prepareToUpdateByPK(new Object[] { negociacaoVO.asBigDecimal("NUNEGOCIACAO") })
				.set("STATUSPROPOSTA", "C").set("DHALTER", TimeUtils.getNow())
				.set("CODUSUALTER", AuthenticationInfo.getCurrent().getUserID()).update();

		return ("Proposta / Negociação [" + negociacaoVO.asBigDecimal("NUMNEGOCIACAO") + "] foi Cancelada com Sucesso");

	}

	private static boolean cancelarEntrega(BigDecimal nunegociacao) {
		String msg = null;

		gravarMensagem(msg);
		return false;
	}

	private static boolean cancelarLayout(BigDecimal nunegociacao) {
		String msg = null;

		gravarMensagem(msg);
		return false;
	}

	private static boolean cancelarPedidoFaturamento(BigDecimal nunegociacao) {
		String msg = null;

		gravarMensagem(msg);
		return false;
	}

	private static boolean cancelarProducao(BigDecimal nunegociacao) {
		String msg = null;

		gravarMensagem(msg);
		return false;
	}

	private static boolean cancelarSuprimento(BigDecimal nunegociacao) {
		String msg = null;
		
		itensNegociacaoVO itens =  ;
		
		verificarStatusItens(nunegociacao, "SUPRIMENTO");

		gravarMensagem(msg);
		return false;
		
		
		/*
			SELECT 
			    NUNEGOCIACAO, 
			    STATUS_FAT, 
			    STATUS_PROD,
			    STATUS_LAYOUT, 
			    STATUS_SUPRIMENTO
			FROM AD_TCRNEGITE 
			WHERE NUNEGOCIACAO = 26586 
			AND  CLASSIFICACAO_PRODUTO = 'Principal'
		 */
	}

	private static void gravarMensagem(String msg) {
		if (motivoImpedimento == null) {
			String m = motivoImpedimento.toString();
			motivoImpedimento.append(msg +  " | " + m);
		} else {
			motivoImpedimento.toString();

		}

	}

	private static boolean verificarNegociacaoFinalizada(BigDecimal nunegociacao) {
		CancelarNegociacaoDAO.consultarStatusNegociacao(nunegociacao);
		return false;
	}
}
