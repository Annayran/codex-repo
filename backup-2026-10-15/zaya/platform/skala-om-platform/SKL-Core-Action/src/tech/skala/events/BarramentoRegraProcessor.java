package tech.skala.events;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.dwfdata.vo.ParceiroVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import br.com.sankhya.util.troubleshooting.SKError;
import br.com.sankhya.util.troubleshooting.TSLevel;
import tech.skala.action.integration.transport.callcenter.CallCenterProcessor;
import tech.skala.action.integration.transport.ocorrencianaentrega.Entregas;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaDeEntrega;
import tech.skala.action.integration.transport.ocorrencianaentrega.OcorrenciaOrigemEnum;
import tech.skala.action.modules.centrais.CabecalhoNotaDadosPedidoOrigem;
import tech.skala.action.modules.centrais.CentralProcessor;
import tech.skala.action.modules.comercial.lifecycle.SalesOrderLifeCycleHelper;
import tech.skala.action.modules.comercial.lifecycle.SalesOrderLifeCycleStageFinder;
import tech.skala.action.modules.comercial.lifecycle.StageLifeCycleEnum;
import tech.skala.core.commons.constants.SklEntityName;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class BarramentoRegraProcessor {

	public void validarConferencia(DynamicVO vo) throws SkalaModelException {
		Logger logger = new LoggerFactory("BarramentoRegraProcessor.validaConferencia").getLogger();

		BigDecimal codEmp = vo.asBigDecimal(SklFieldName.CODEMP);
		BigDecimal codTipOper = vo.asBigDecimal(SklFieldName.CODTIPOPER);

		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);

		String msg;
		JapeWrapper recebimentoDAO = JapeFactory.dao(SklEntityName.MOBILE_RECEBIMENTO);
		DynamicVO recebimentoVO;

		try {

			if (codTipOper.equals(BigDecimal.valueOf(2103)) && codEmp.equals(BigDecimal.ONE)) {
				recebimentoVO = recebimentoDAO.findOne("this.NUNOTA = ? AND this.SITUACAO IN (6,2)", nuNota);

				if (recebimentoVO == null) {
					msg = String.format("[NUNOTA %s] Só é possível confirmar lançamentos que possuem recebimento.",
							nuNota);
					throw new SkalaModelException(msg);
				}
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível validar conferência.", e);
		}

	}

	public void incluirCTE(DynamicVO vo) throws SkalaModelException {
		Logger logger = new LoggerFactory("BarramentoRegraProcessor.IncluirCTE").getLogger();
		String msg;

		BigDecimal codTipOper = vo.asBigDecimal(SklFieldName.CODTIPOPER);
		if (codTipOper.longValue() == 2122) {

			msg = "[RN0002] Incluindo CT-e ...";
			logger.info(msg);

			CallCenterProcessor.processarCTe(vo);
		}

	}

	public void validarCFOP(DynamicVO itemNotaVO) throws SkalaModelException {
		Logger logger = new LoggerFactory("BarramentoRegraProcessor.validarCFOP").getLogger();
		final List<Long> CODEMP_MASTER_LIST = Arrays.asList(1L, 103L, 104L, 105L);
		final List<Long> CODEMP_PLATINA_LIST = Arrays.asList(2L, 203L, 205L);

		final List<Long> CFOP_BLOQUEADAS_MASTER = Arrays.asList(1102L, 2102L, 5102L, 6102L, 1403L, 2403L, 1409L, 2409L);
		final List<Long> CFOP_BLOQUEADAS_PLATINA = Arrays.asList(1101L, 2101L, 5101L, 6101L, 1401L, 2401L, 1408L,
				2408L);

		BigDecimal codEmp = itemNotaVO.asBigDecimalOrZero(SklFieldName.CODEMP);
		BigDecimal codCFO = itemNotaVO.asBigDecimalOrZero("CODCFO");

		String msg = String.format(
				"<b>CFOP %s</b> nao pode ser utilizado com a <b>Empresa %s</b>.%nReveja o CFOP utilizado no lancamento ou entre em contato com o departamento fiscal!",
				codCFO, codEmp);

		if (CODEMP_MASTER_LIST.contains(codEmp.longValue()) && CFOP_BLOQUEADAS_MASTER.contains(codCFO.longValue())) {
			throw new SkalaModelException(msg);
		}

		if (CODEMP_PLATINA_LIST.contains(codEmp.longValue()) && CFOP_BLOQUEADAS_PLATINA.contains(codCFO.longValue())) {
			throw new SkalaModelException(msg);
		}

	}

	public void gravarHistoricoPedidoOrigem(DynamicVO vo) throws SkalaModelException {
		Logger logger = new LoggerFactory("BarramentoRegraProcessor.gravaHistoricoPedidoOrigem").getLogger();
		CentralProcessor processor = new CentralProcessor();
		String msg = null;

		try {
			CabecalhoNotaDadosPedidoOrigem origem = processor.getDadosPedidoOrigem(vo);
			if (origem == null)
				return;

			vo.setProperty("AD_DTBASEPEDIDO", TimeUtils.clearTime(origem.getDataComercial()));
			vo.setProperty("AD_DTCOMERCIAL", TimeUtils.clearTime(origem.getDataComercial()));
			vo.setProperty("AD_NROUNICOPED", origem.getNuNota());
			vo.setProperty("AD_CODPARCORIG", origem.getCodParc());

			msg = String.format("[RN0004] Campos historicos atualizados. NUNOTA %s",
					vo.asBigDecimal(SklFieldName.NUNOTA));
			logger.info(msg);
		} catch (Exception e) {
			msg = "[RN0007] Não foi possível atualizar campos históricos.";
			logger.severe(msg);
			e.printStackTrace();

		}
	}

	public void incluirCicloVida(DynamicVO vo) throws SkalaModelException {
		Logger logger = new LoggerFactory("BarramentoRegraProcessor.incluirCicloVida").getLogger();

		String msg;

		String stage = SalesOrderLifeCycleStageFinder.getOrderLifeCycleStage(vo.asBigDecimal(SklFieldName.CODTIPOPER));

		if (stage != null) {
			BigDecimal nuNotaPv = BigDecimal.ZERO;
			BigDecimal nuNota = BigDecimal.ZERO;

			SalesOrderLifeCycleHelper helper;

			logger.info("Cadastrando ciclo de vida do pedido...");
			try {
				if (StageLifeCycleEnum.PEDIDO_VENDAS.getValue().equalsIgnoreCase(stage)) {
					nuNotaPv = vo.asBigDecimal(SklFieldName.NUNOTA);
					Timestamp dtComercial = vo.asTimestamp("AD_DTCOMERCIAL");

					msg = String.format("[NUNOTA %s]Preparando para cadastrar cabecalho Ciclo de Vida Pedido.",
							nuNotaPv);
					logger.info(msg);

					helper = new SalesOrderLifeCycleHelper(nuNotaPv, dtComercial);

					msg = String.format("[NUNOTAORIG %s][NUNOTA %s] Inserindo no Ciclo de Vida do Pedido, etapa %s.",
							nuNotaPv, nuNotaPv, stage);
					helper.buildItems(nuNotaPv, stage, TimeUtils.getNow(), nuNotaPv);
					logger.info(msg);
				} else {
					nuNotaPv = vo.asBigDecimal("AD_NROUNICOPED");
					nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);

					helper = new SalesOrderLifeCycleHelper();

					msg = String.format("[NUNOTAORIG %s][NUNOTA %s] Inserindo no Ciclo de Vida do Pedido, etapa %s.",
							nuNotaPv, nuNota, stage);
					helper.buildItems(nuNotaPv, stage, TimeUtils.getNow(), nuNota);
					logger.info(msg);
				}

			} catch (Exception e) {
				msg = String.format("Ocorreu um erro ao persistir Ciclo de Vida de Pedido. Causa: %s!", e.getMessage());
				logger.info(msg);
			}

		}
	}

	public void validarCamposObrigatoriosParceiro(DynamicVO vo) throws SkalaModelException {
		Logger logger = new LoggerFactory("BarramentoRegraProcessor.validarCamposObrigatoriosParceiro").getLogger();

		if (clienteNaoTemEmpresaPreferencialTabelaPreco(vo)) {
			String msg = "[RN0006] - Os campos <b>Empresa preferencial</b> e <b>Tabela de Preço</b> devem estar preenchidos para <b>Clientes</b>.";
			
			throw new SkalaModelException(msg);
		}

		if (clienteNaoTemVendedorPreferencial(vo)) {
			String msg = "[RN0006] - Não é possivel cadastrar um parceiro sem informar o Vendedor Preferencial!\n"
					+ "É necessario informar o vendedor Preferencial!";

			throw new SkalaModelException(msg);
		}

	}

	private static boolean clienteNaoTemEmpresaPreferencialTabelaPreco(DynamicVO vo) {
		return vo.asBoolean("CLIENTE") && (vo.asBigDecimal("CODEMPPREF") == null || vo.asBigDecimal("CODTAB") == null);
	}

	private static boolean clienteNaoTemVendedorPreferencial(DynamicVO vo) {
		return vo.asBoolean("CLIENTE") && vo.asBoolean(SklFieldName.ATIVO)
				&& vo.asBigDecimalOrZero("CODVEND").longValue() == 0L;
	}

	public void validarAlteracaoCredito(DynamicVO vo) throws SkalaModelException {

		Logger logger = new LoggerFactory("BarramentoRegra.validaAlteracaoCredito").getLogger();
		String liberadoresList = "21-273-615";

		String userID = AuthenticationInfo.getCurrent().getUserID().toString();

		if (liberadoresList.indexOf(userID) == -1) {
			String msg = "<b>[RN0007]<b><br>Limite de crédito não pode ser alterado.<br>Favor solicitar ao <b>Departamento de Análise de Crédito</b>.";
			logger.info(msg);

			throw new SkalaModelException(msg);
		}

	}

	public void criarAcompanhamentoDeEntrega(DynamicVO cabecalhoNotaVO) throws SkalaModelException {
		Logger logger = new LoggerFactory("BarramentoRegra.criarAcompanhamentoDeEntrega").getLogger();

		BigDecimal nuNota = cabecalhoNotaVO.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal ordemCarga = cabecalhoNotaVO.asBigDecimal(SklFieldName.ORDEMCARGA);
		BigDecimal codEmp = cabecalhoNotaVO.asBigDecimal(SklFieldName.CODEMP);

		try {
			DynamicVO entregaVO = JapeFactory.dao("AD_PLTGFENT").findByPK(nuNota);

			if (entregaVO == null) {
				Entregas entrega = new Entregas();
				entrega.setNuNota(nuNota);
				entrega.setCodEmp(codEmp);
				entrega.setOrdemCarga(ordemCarga);
//				entrega.setDtLimiteEntrega(EntregasDAO.getDataLimiteDeEntregaPorNuNota(nuNota));
				entrega.setNumNota(cabecalhoNotaVO.asBigDecimal(SklFieldName.NUMNOTA));
				entrega.setCodParc(cabecalhoNotaVO.asBigDecimal(SklFieldName.CODPARC));
				entrega.persists();

				OcorrenciaDeEntrega ocorrenciaDeEntrega = new OcorrenciaDeEntrega();
				ocorrenciaDeEntrega.setNuNota(nuNota);
				ocorrenciaDeEntrega.setCodOco(BigDecimal.ONE);
				ocorrenciaDeEntrega.setCodUsu(AuthenticationInfo.getCurrent().getUserID());
				ocorrenciaDeEntrega.setOrigem(OcorrenciaOrigemEnum.INTEGRACAO.getValue());
				ocorrenciaDeEntrega.setDhOcorrencia(TimeUtils.getNow());
				ocorrenciaDeEntrega.persists();

				String msg = String.format("[NUNOTA %s] OcorrenciaEntrega criada com sucesso.", nuNota);
				logger.info(msg);

			}

		} catch (Exception e) {
			SkalaModelException.throwMe("[RN0008] - Não foi possível criar Acompanhamento de Entrega para a NF-e", e);
		}

	}

	public void atualizarCampoFinanceiro(DynamicVO cabVO) throws SkalaModelException {
		BigDecimal nuNota = cabVO.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal codCenCusCab = cabVO.asBigDecimal("CODCENCUS");

		try {
			JapeWrapper financeiroDAO = JapeFactory.dao(DynamicEntityNames.FINANCEIRO);

			BigDecimal codCenCusFin;
			Collection<DynamicVO> financeirosVO = financeiroDAO.find("this.NUNOTA = ?", nuNota);

			for (DynamicVO financeiroVO : financeirosVO) {
				codCenCusFin = financeiroVO.asBigDecimal("CODCENCUS");

				if (codCenCusFin != codCenCusCab) {
					FluidUpdateVO updVO = financeiroDAO.prepareToUpdate(financeiroVO);
					updVO.set("CODCENCUS", codCenCusCab);
					updVO.update();
				}

			}
		} catch (Exception e) {
			String msg = String.format(
					"[RN0009] - [NUNOTA %s] Não foi possível atualizar o campo de Centro de resultado do(s) financeiro(s) gerado(s) a partir desta nota.",
					nuNota);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			SkalaModelException.throwMe(msg, e);
		}

	}

	public void validarExclusaoItemOr(DynamicVO vo) throws SkalaModelException {
		Logger logger = new LoggerFactory("BarramentoRegra.validaAlteracaoExclusaoOr").getLogger();
		String msg = null;
		String SUBTIPO_ORDEMRESERVA_LIST = "R-RB-RE";

		String tipMov = vo.asString("TIPMOV");

		// Validar somente pedidos de venda
		if ("P".equalsIgnoreCase(tipMov)) {
			BigDecimal codTipOper = vo.asBigDecimal("CODTIPOPER");
			String statusNota = vo.asString("STATUSNOTA");

			// Validar somente pedidos confirmados
			if ("L".equalsIgnoreCase(statusNota)) {
				DynamicVO topCarteiraVO = null;

				try {

					JapeWrapper topCarteiraDAO = JapeFactory.dao("AD_TPLTTOPCART");
					topCarteiraVO = topCarteiraDAO.findByPK(codTipOper);

				} catch (Exception e) {
					msg = SkalaModelException.buildErrorMessage("[RN0009] Não foi possível efetuar a validação.", e);
					logger.severe(msg);
				}

				if (topCarteiraVO != null) {
					String tipoCarteira = topCarteiraVO.asString("TIPO");

					BigDecimal ordemCarga = BigDecimalUtil.getValueOrZero(vo.asBigDecimal("ORDEMCARGA"));

					// Subtipo O.R. e Ordem de Carga vï¿½lida
					if (SUBTIPO_ORDEMRESERVA_LIST.indexOf(tipoCarteira) > -1 && ordemCarga.longValue() > 0L) {
						msg = "Ordem de Reserva confirmada e vinculada a uma Ordem de Carga não pode ser <b>alterada</b> ou <b>excluída</b>.";
						msg += String.format("%s<br>ï¿½ necessário desvincular a O.R da Carga: %s", msg, ordemCarga);

						SkalaModelException.throwMe(msg, null);
					}

				}

			}

		}

	}

	public void definirFreteCifFob(DynamicVO vo) throws Exception {
		Logger logger = new LoggerFactory("BarramentoRegra.setFreteCifFob").getLogger();
		String msg = null;

		BigDecimal nuNota = vo.asBigDecimal("NUNOTA");
		BigDecimal codEmp = vo.asBigDecimal("CODEMP");
		BigDecimal ordemCarga = vo.asBigDecimal("ORDEMCARGA");
		BigDecimal codParc = vo.asBigDecimal("CODPARC");
		BigDecimal codParcTransp = BigDecimal.ZERO;

		JapeWrapper ordemDAO = JapeFactory.dao(DynamicEntityNames.ORDEM_CARGA);
		DynamicVO ordemVO = ordemDAO.findByPK(codEmp, ordemCarga);
		if (ordemVO != null)
			codParcTransp = BigDecimalUtil.getValueOrZero(ordemVO.asBigDecimal("CODPARCTRANSP"));

		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("this.CODPARC = ? AND TRUNC(this.DTLIMITE) >= TRUNC(SYSDATE) AND this.SEQUENCIA = ");
		sbWhere.append("(SELECT MAX(T.SEQUENCIA) FROM AD_PLTGFPARFOB T WHERE T.CODPARC = this.CODPARC)");

		JapeWrapper parceiroFreteFobDAO = JapeFactory.dao("AD_PLTGFPARFOB");
		Collection<DynamicVO> parceirosFreteFobVO = parceiroFreteFobDAO.find(sbWhere.toString(), codParc);
		String tipoFrete = (parceirosFreteFobVO.isEmpty() ? "C" : "F");

		// Adicionado após chamado do Marcelo - 43744, dia 27/01

		// não temos mais particularidades com Fretes fob
//		tipoFrete = "C";

		vo.setProperty("CIF_FOB", tipoFrete);
		vo.setProperty("TIPFRETE", "N");
		vo.setProperty("CODPARCTRANSP", codParcTransp);

		msg = String.format("[RN0014] NUNOTA %s; tipoFrete %s. Atualizando...", nuNota, tipoFrete);
		logger.info(msg);

		if ("F".equalsIgnoreCase(tipoFrete)) {

			msg = String.format("[RN0014] NUNOTA %s; CODPARC %s - alteracao para frete FOB tipoFrete %s", nuNota,
					codParc, tipoFrete);
			logger.info(msg);
		}
	}

	public boolean validarAtraso(final BigDecimal codParc) throws SkalaModelException {
		try {
			boolean usaToleranciaInadimplencia = (boolean) MGECoreParameter
					.getParameter("com.usa.tolerancia.inadimplencia");
			BigDecimal diasCarencia = (BigDecimal) MGECoreParameter.getParameter("com.dias.carencia.atraso");
			boolean consideraBaixaFutura = (boolean) MGECoreParameter.getParameter("com.considera.baixa.futura");
			Timestamp dataBase = new Timestamp(System.currentTimeMillis());

			ParceiroVO parcVO = carregarParceiroVO(codParc);

			StringBuilder sbWhere = new StringBuilder();
			sbWhere.append("SELECT MIN(DTVENC) AS DTVENC, SUM(VLRDESDOB * RECDESP) AS DEBITO ");
			sbWhere.append("FROM TGFFIN ");
			sbWhere.append("WHERE CODPARC = ? ");
			sbWhere.append("AND DTVENC < ? ");
			sbWhere.append("AND (RECDESP = 1 AND PROVISAO = 'N' AND CODTIPTIT = 4 AND CODCTABCOINT NOT IN (28))");
			sbWhere.append("AND (DHBAIXA IS NULL OR DHBAIXA >= ?) ");

			long dataVencto;
			if (usaToleranciaInadimplencia) {
				dataVencto = TimeUtils.add(dataBase.getTime(),
						-1 * ((parcVO.getTOLERINADIMP() == null) ? 0 : parcVO.getTOLERINADIMP().intValue()), 5);
			} else {
				dataVencto = TimeUtils.add(dataBase.getTime(), -1 * diasCarencia.intValue(), 5);
			}

			long dataBaixaFutura;
			if (consideraBaixaFutura) {
				dataBaixaFutura = TimeUtils.add(dataBase.getTime(), 1, 5);
			} else {
				dataBaixaFutura = TimeUtils.add(dataBase.getTime(), 50, 1);
			}

			JdbcWrapper jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();
			
			PreparedStatement psAtraso = jdbc.getPreparedStatement(sbWhere.toString());
			psAtraso.setBigDecimal(1, parcVO.getCODPARC());
			psAtraso.setTimestamp(2, new Timestamp(TimeUtils.clearTime(dataVencto)));
			psAtraso.setTimestamp(3, new Timestamp(TimeUtils.clearTime(dataBaixaFutura)));

			ResultSet rs = psAtraso.executeQuery();
			BigDecimal debito = BigDecimal.ZERO;
			if (rs.next())
				debito = BigDecimalUtil.getValueOrZero(rs.getBigDecimal("DEBITO"));

			return debito.compareTo(BigDecimal.ZERO) > 0;

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível validar atraso para Parceiro", e);
		}

		return false;
	}

	public ParceiroVO carregarParceiroVO(BigDecimal codParc) throws Exception {
		PersistentLocalEntity entityParceiro = null;

		try {
			final EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
			entityParceiro = dwfEntityFacade.findEntityByPrimaryKey("Parceiro", (Object) codParc);
		} catch (FinderException ex) {
		}

		if (entityParceiro == null) {
			throw (IllegalArgumentException) SKError.registry(TSLevel.ERROR, "CORE_E04148",
					(Throwable) new IllegalArgumentException(
							String.format("Código de Parceiro %s não encontrado.", codParc.toString())));
		}

		return (ParceiroVO) ((DynamicVO) entityParceiro.getValueObject()).wrapInterface(ParceiroVO.class);
	}

	public void alterarMoedaParaZero(DynamicVO vo) throws Exception {
		Logger logger = new LoggerFactory("BarramentoRegra.alteraMoedaParaZero").getLogger();
		String msg = null;

		BigDecimal nuFin = vo.asBigDecimal("NUFIN");
		BigDecimal nuNota = vo.asBigDecimal("NUNOTA");
		BigDecimal codTipOper = vo.asBigDecimal("CODTIPOPER");
		String origem = vo.asString("ORIGEM").trim().toUpperCase();

		try {
			boolean isBaixaExp = (codTipOper.longValue() == 3333) && "E".equalsIgnoreCase(origem);

			if (isBaixaExp && vo.asInt("CODMOEDA") > 0) {
				vo.setProperty("CODMOEDA", BigDecimal.ZERO);

				msg = String.format("[RN0003] Alteração da moeda para baixa exportação. NUFIN %s NUNOTA %s", nuFin,
						nuNota);
				logger.info(msg);
			}

		} catch (Exception e) {
			msg = String.format("[RN0003] Não foi possível alterar a moeda para baixa exportação. NUFIN %s NUNOTA %s",
					nuFin, nuNota);
			msg = SkalaModelException.buildErrorMessage(msg, e);
			logger.info(msg);

			e.printStackTrace();
		}
	}

	public void calcularDescontoFinanceiroPeloAcordoComercial(DynamicVO vo) throws Exception {
		Logger logger = new LoggerFactory("BarramentoRegra.calculateFinancialDiscountByCommercialAgreement")
				.getLogger();
		String msg = null;

		logger.info("entrou em calculateFinancialDiscountByCommercialAgreement");

		String origem = vo.asString("ORIGEM").trim().toUpperCase();
		int recDesp = vo.asInt("RECDESP");

		msg = String.format("Origem eh igual a E? %s recDesp eh igual a 1? %s", "E".equalsIgnoreCase(origem),
				recDesp == 1);
		logger.info(msg);

		if ("E".equalsIgnoreCase(origem) && recDesp == 1) {
			BigDecimal nuNotaOrig = getOriginOrder(vo);

			msg = String.format("NUNOTA: %s NUNOTAORIG = %s", vo.asBigDecimal(SklFieldName.NUNOTA), nuNotaOrig);
			logger.info(msg);

			DynamicVO acordoVO = null;

			msg = String.format("Preparando para encontrar o Desconto para o Pedido: %s", nuNotaOrig);
			logger.info(msg);

			if (nuNotaOrig != null) {
				JapeWrapper acordoDAO = JapeFactory.dao("AD_PLTGFACO");
				acordoVO = acordoDAO.findOne("this.NUNOTA = ?", nuNotaOrig);
			}

			if (acordoVO != null) {
				logger.info("Caiu no calcular desconto buscando pelo desconto do acordo do pedido");

				logger.info("acordoVO foi encontrado...");

				double percDescFin = BigDecimalUtil.getValueOrZero(acordoVO.asBigDecimal("PERCDESC")).doubleValue();

				logger.info("Percentual de desconto encontrado: " + percDescFin);

				if (percDescFin > 0D) {
					double vlrDesdob = vo.asDouble("VLRDESDOB");
					double vlrDesc = Math.round(vlrDesdob * percDescFin) / 100D;

					logger.info("Valor Desdobramento: " + vlrDesdob);
					logger.info("Valor do Desconto: " + vlrDesc);

					vo.setProperty("VLRDESC", BigDecimal.valueOf(vlrDesc));

					logger.info("Valor do Desconto aplicado no VO do Financeiro = " + vo.asBigDecimal("VLRDESC"));
				}
			} else {
				logger.info("Caiu no calcular desconto de forma normal");

				calcularDescontoFinanceiro(vo);
			}
		}

	}

	public void calcularDescontoFinanceiro(DynamicVO vo) {
		Logger logger = new LoggerFactory("BarramentoRegra.calculaDescontoFinanceiro").getLogger();
		String msg = null;

		String origem = vo.asString("ORIGEM").trim().toUpperCase();
		int recDesp = vo.asInt("RECDESP");

		if ("E".equalsIgnoreCase(origem) && recDesp == 1) {
			JapeWrapper parceiroDAO = JapeFactory.dao(DynamicEntityNames.PARCEIRO);
			DynamicVO parceiroVO;
			try {
				parceiroVO = parceiroDAO.findByPK(new Object[] { vo.asBigDecimal("CODPARC") });

				if (parceiroVO.containsProperty("AD_DESCFINA")) {
					double percDescFin = BigDecimalUtil.getValueOrZero(parceiroVO.asBigDecimal("AD_DESCFINA"))
							.doubleValue();

					if (percDescFin > 0D) {
						double vlrDesdob = vo.asDouble("VLRDESDOB");
						double vlrDesc = Math.round(vlrDesdob * percDescFin) / 100D;

						vo.setProperty("VLRDESC", BigDecimal.valueOf(vlrDesc));

						msg = String.format("[RN0006] %s Desconto financeiro: %.2f", vo.asBigDecimal("NUFIN"),
								vo.asBigDecimal("VLRDESC"));
						logger.info(msg);
					}
				}
			} catch (Exception e) {
				logger.severe("[RN0006] Não foi possível calcular o desconto financeiro.");
			}

		}
	}

	private BigDecimal getOriginOrder(DynamicVO vo) throws SkalaModelException {

		BigDecimal nuNotaOrig = null;
		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);

		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		try {
			DynamicVO cabVO = cabDAO.findByPK(vo.asBigDecimal(SklFieldName.NUNOTA));
			nuNotaOrig = cabVO.asBigDecimal("AD_NROUNICOPED");
		} catch (Exception e) {
			String errMsg = String.format("Não foi possível localizar Pedido de Origem para lançamento %s.", nuNota);
			SkalaModelException.throwMe(errMsg, e);
		}
		return nuNotaOrig;

	}

	public void validarControle(DynamicVO vo) throws SkalaModelException {
		
		BigDecimal codProd = vo.asBigDecimal(SklFieldName.CODPROD);
		String controle = vo.asString(SklFieldName.CONTROLE);
		
		if (controle == null)
			return;

//		if(controle.contains(".")) {
//			String msg = String.format("[CODPROD %s][CONTROLE %s] Não é permitido a utilização de Lote contendo ponto (.)", codProd, controle);
//			throw new SkalaModelException(msg);
//		}
		

		// Retira espaço no inicio do lote
		if (!" ".equals(controle) && controle.contains(" ")) {
			controle = controle.trim();

			vo.setProperty(SklFieldName.CONTROLE, controle.toUpperCase());
		}
	}

}
