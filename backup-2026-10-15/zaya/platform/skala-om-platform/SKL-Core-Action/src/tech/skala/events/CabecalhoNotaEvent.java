package tech.skala.events;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import com.cbmsoft.modelcore.LoggerFactory;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.AtributosRegras;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CabecalhoNotaEvent implements EventoProgramavelJava {
	private static final Logger logger = new LoggerFactory(CabecalhoNotaEvent.class.getCanonicalName()).getLogger();

	private String msg;

	@Override
	public void afterDelete(PersistenceEvent event) throws Exception {

	}

	@Override
	public void afterInsert(PersistenceEvent event) throws Exception {

	}

	@Override
	public void afterUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		BigDecimal codTipOper = vo.asBigDecimal(SklFieldName.CODTIPOPER);
		
		List<BigDecimal> topsORFaturamentoAntecipado = Arrays.asList(
			BigDecimal.valueOf(4713),
			BigDecimal.valueOf(4712)
		);
		final boolean isConfirmando = JapeSession.getPropertyAsBoolean(AtributosRegras.CONFIRMANDO, false)
				.booleanValue();

		if (isConfirmando) {
			BarramentoRegra.incluiCTE(vo);
			BarramentoRegra.criaAcompanhamentoDeEntrega(vo);
			
			if(topsORFaturamentoAntecipado.contains(codTipOper))
				BarramentoRegra.copiaObservacaoORParaFaturamentoAntecipado(vo);
			
		}
		BarramentoRegra.atualizaCampoFinanceiro(vo);

	}

	@Override
	public void beforeCommit(TransactionContext event) throws Exception {

	}

	@Override
	public void beforeDelete(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);

		excluiLancamentoOcorrenciaEntrega(nuNota);
		
		excluiRecebimentoMercadoria(nuNota);
	}

	private void excluiRecebimentoMercadoria(BigDecimal nuNota) throws SkalaModelException {
		
		JapeWrapper conferenciaItemDAO = JapeFactory.dao("AD_PLTGFCOI");
		JapeWrapper recebimentoXconferenciaDAO = JapeFactory.dao("AD_PLTGFRECXCON");
		JapeWrapper recebimentoDAO = JapeFactory.dao("AD_PLTGFREC");
		JapeWrapper conferenciaDAO = JapeFactory.dao("AD_PLTGFCON");
		
		try {
			Collection<DynamicVO> recebimentoVOs = recebimentoDAO.find("this.NUNOTA = ?", nuNota);
			
			for (DynamicVO recebimentoVO : recebimentoVOs) {
				BigDecimal nuRecebimento = recebimentoVO.asBigDecimal(SklFieldName.NURECEBIMENTO);
				
				conferenciaItemDAO.deleteByCriteria("this.NUCONFERENCIA = ?", nuRecebimento);
				recebimentoXconferenciaDAO.deleteByCriteria("this.NUCONFERENCIA = ?", nuRecebimento);
				recebimentoDAO.deleteByCriteria("this.NURECEBIMENTO = ?", nuRecebimento);
				conferenciaDAO.deleteByCriteria("this.NUCONFERENCIA = ?", nuRecebimento);
			}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir Recebimento", e);
		}
		
		
		
		
	}

	private void excluiLancamentoOcorrenciaEntrega(BigDecimal nuNota) throws SkalaModelException {

		JapeWrapper ocorrenciaEntregaDAO = JapeFactory.dao("AD_PLTGFENT");
		JapeWrapper ocorrenciasDeEntregaDAO = JapeFactory.dao("AD_PLTGFENTOCO");
		JapeWrapper cteOcorrenciaEntregaDAO = JapeFactory.dao("AD_PLTGFENTCTE");

		try {

			if (ocorrenciaEntregaDAO.findByPK(nuNota) != null) {
				cteOcorrenciaEntregaDAO.deleteByCriteria("this.NUNOTA = ?", nuNota);
				ocorrenciasDeEntregaDAO.deleteByCriteria("this.NUNOTA = ?", nuNota);
				ocorrenciaEntregaDAO.deleteByCriteria("this.NUNOTA = ?", nuNota);
			}

			if (cteOcorrenciaEntregaDAO.find("this.NUNOTACTE = ?", nuNota) != null)
				cteOcorrenciaEntregaDAO.deleteByCriteria("this.NUNOTACTE = ?", nuNota);

		} catch (Exception e) {
			String msg = String
					.format("[NUNOTA %s] Não foi possível realizar a exclusão da NF da Ocorrência de Entrega", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {

		DynamicVO vo = (DynamicVO) event.getVo();

		BigDecimal codTipOper = vo.asBigDecimal(SklFieldName.CODTIPOPER);
//		BarramentoRegraDAO dao = new BarramentoRegraDAO();
//		List<Method> metodos = dao.buscaRegrasExclusivas(event.getType(), codTipOper, null);
//		
//		for (Method method : metodos) {
//			method.invoke(BarramentoRegraDAO.class.newInstance(), vo);
//		}

		BigDecimal codParc = BigDecimal.valueOf(527);

		BigDecimal codTipVenda = BigDecimal.valueOf(109);
		DynamicVO tipoNegociacaoVO = ComercialUtils.getTipoNegociacao(codTipVenda);

		BigDecimal topRequisicaoPesagem = BigDecimal.valueOf(6000);
		BigDecimal codCenCus = BigDecimal.valueOf(300602);

		BigDecimal codEmp = BigDecimal.ONE;

		BigDecimal codNat = BigDecimal.valueOf(2010303);

		if (topRequisicaoPesagem.equals(codTipOper)) {
			vo.setProperty(SklFieldName.CODPARC, codParc);
			vo.setProperty("CODTIPVENDA", tipoNegociacaoVO.asBigDecimal("CODTIPVENDA"));
			vo.setProperty("DHTIPVENDA", tipoNegociacaoVO.asTimestamp(SklFieldName.DHALTER));
			vo.setProperty(SklFieldName.CODEMP, codEmp);
			vo.setProperty("CODNAT", codNat);
			vo.setProperty(SklFieldName.CODCENCUS, codCenCus);
		}
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		final boolean isConfirmando = JapeSession.getPropertyAsBoolean(AtributosRegras.CONFIRMANDO, false)
				.booleanValue();

		if (isConfirmando)
			return;

		String fieldList = null;
		DynamicVO vo = (DynamicVO) event.getVo();
		DynamicVO oldVO = (DynamicVO) event.getOldVO();
		ModifingFields modifingFields = event.getModifingFields();

		BigDecimal codTipVendaBaixaExp = new BigDecimal(901);

		BigDecimal nuNota = vo.asBigDecimal("NUNOTA");
		String tipMov = vo.asString("TIPMOV");
		BigDecimal codTipOper = vo.asBigDecimal("CODTIPOPER");
		BigDecimal codTipVenda = vo.asBigDecimal("CODTIPVENDA");
		BigDecimal ordemCarga = vo.asBigDecimal("ORDEMCARGA");
		BigDecimal codParc = vo.asBigDecimal("CODPARC");
		String razaoSocial = vo.asString("Parceiro.RAZAOSOCIAL");

		/*
		 * RN0007
		 */
		if (modifingFields.isModifing("CODTIPOPER"))
			BarramentoRegra.gravaHistoricoPedidoOrigem(vo);

		/*
		 * RN0009
		 */
		boolean isUpdatingDeliveryDate = JapeSession.getPropertyAsBoolean("br.com.platinacsc.isSetDeliveryDate", false);
		if (!isUpdatingDeliveryDate) {
			fieldList = "CODPARCREDESPACHO,ORDEMCARGA,CODTIPVENDA,OBSERVACAO,VLRNOTA,CODNAT,AD_DTBASEPEDIDO,AD_DTCOMERCIAL,AD_NROUNICOPED,AD_CODPARCORIG,AD_DHAGENDAMENTO";
			if (!modifingFields.isModifingAny(fieldList)) {
				BarramentoRegra.validaAlteracaoExclusaoOr(vo);
			}
		}

		if (modifingFields.isModifing("ORDEMCARGA")) {
			/*
			 * [RN0017]
			 * 
			 * Verifica se o Parceiro possui atraso financeiro, se houver, não pode realizar
			 * a formação de carga.
			 * 
			 * @throws PltException Se parceiro possuir títulos de receita em atraso uma
			 * exceção é disparada.
			 */
			if ((codTipOper.longValue() == 3112) && (BarramentoRegra.validaAtraso(codParc))) {
				msg = String.format("O Parceiro %s-%s possui títulos em atraso.%nNão é possível formar a Carga.%n",
						codParc, razaoSocial);
				msg += "Favor entrar em contato com <b>Setor de Cobrança</b>";
				logger.info(msg);
//				throw new PltException(msg);
			}

			/*
			 * RN0014
			 */
			if(ordemCarga != null) {
				if (("P-V".indexOf(tipMov) > -1) && (ordemCarga.longValue() > 0L)
						&& (codTipOper.longValue() != 3122 || codTipOper.longValue() != 3214))
					BarramentoRegra.defineFreteCifFob(vo);				
			}
		}
		/*
		 * Tratamento para baixa de estoque exportacao
		 */
		try {
			boolean isBaixaExp = (codTipOper.longValue() == 3333);

			if (isBaixaExp && (codTipVenda.longValue() != codTipVendaBaixaExp.longValue())) {

				DynamicVO newTipNegVO = ComercialUtils.getTipoNegociacao(codTipVendaBaixaExp);

				Timestamp dhTipVenda = newTipNegVO.asTimestamp("DHALTER");

				vo.setProperty("CODTIPVENDA", codTipVendaBaixaExp);
				vo.setProperty("DHTIPVENDA", dhTipVenda);

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				this.msg = String.format(
						"[RN0003] Alteracao do Tipo de Negociacao e Moeda para baixa exportacao. NUNOTA %s TIPVENDA %s %s",
						nuNota, codTipVendaBaixaExp, sdf.format(dhTipVenda));
				logger.info(msg);
			}

		} catch (Exception e) {
			this.msg = String.format(
					"[RN0003] Nao foi possivel alterar Tipo de Negociacao ou a Moeda para baixa exportacao. NUNOTA %s",
					nuNota);
			logger.info(msg);

			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
