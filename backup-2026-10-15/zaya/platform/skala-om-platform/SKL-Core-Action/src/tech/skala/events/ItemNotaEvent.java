package tech.skala.events;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.logging.Logger;

import org.apache.commons.math3.util.Precision;

import com.cbmsoft.modelcore.LoggerFactory;
import com.sankhya.util.BigDecimalUtil;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.ListenerParameters;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ItemNotaEvent implements EventoProgramavelJava {
	private static final String USOPROD_VENDA = "V";
	private final Logger logger = new LoggerFactory(this.getClass().getCanonicalName()).getLogger();

	@Override
	public void afterDelete(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInsert(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterUpdate(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeCommit(TransactionContext var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(PersistenceEvent var1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInsert(PersistenceEvent event) throws Exception {
		final Boolean isFaturando = (Boolean) JapeSession.getProperty(ListenerParameters.NOTA_FATURAMENTO, false);

		DynamicVO vo = (DynamicVO) event.getVo();
		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);

		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		DynamicVO cabVO = null;
		BigDecimal codTipOper = BigDecimal.ZERO;

		try {
			cabVO = cabDAO.findByPK(vo.asBigDecimal(SklFieldName.NUNOTA));
			codTipOper = cabVO.asBigDecimal(SklFieldName.CODTIPOPER);

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Não foi possível encontrar a nota no sistema.", nuNota);
			SkalaModelException.throwMe(msg, e);
		}

		String tipMov = cabVO.asString(SklFieldName.TIPMOV);

		// NAO VALIDAR PARA TOP DE AJUSTE DE ESTOQUE
		if (codTipOper.longValue() == 2196L)
			return;

		// NAO VALIDAR PARA NOTAS DE PRODUCAO (F)
		if ("F".equalsIgnoreCase(tipMov))
			return;

		String usoProd = vo.asString(SklFieldName.USOPROD);

		if (Arrays.asList(2003L, 2006L, 2007L, 2020L).contains(codTipOper.longValue()))
			inicializaCampos(vo);

		if (USOPROD_VENDA.indexOf(usoProd) > -1) {

			if (!Arrays.asList(2196L, 3297L, 3293L, 3270L, 3316L).contains(codTipOper.longValue())) {
				validateTotal(vo);

				validateDiscount(vo, isFaturando);

			}
		}

		BarramentoRegra.validaCFOP(vo);
		BarramentoRegra.validaControle(vo);

	}

	private void validateDiscount(DynamicVO vo, boolean isFaturando) {
		double vlrTot = vo.asDouble("VLRTOT");
		double percDesc = vo.asDouble("PERCDESC");
		double vlrDesc = vo.asDouble("VLRDESC");

		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		BigDecimal codProd = vo.asBigDecimal(SklFieldName.CODPROD);

		double vlrDescCalc = vlrTot * percDesc / 100D;

		vlrDesc = Math.round(vlrDesc * 100D) / 100D;
		vlrDescCalc = Math.round(vlrDescCalc * 100D) / 100D;

		boolean isEqual = Precision.equals(vlrDesc, vlrDescCalc, 0.05D);

		if (isFaturando && !isEqual) {
			String message = String.format(
					"Valor do desconto do item esta incorreto para NUNOTA %s SEQUENCIA %s "
							+ "CODPROD %s<br>VlrTot: %.2f PercDesc %.2f Desconto: %.6f Desconto calculado: %.6f",
					nuNota, sequencia, codProd, vlrTot, percDesc, vlrDesc, vlrDescCalc);

			/*
			 * OS 1453406 Tratamento necessario pois ha casos em que o valor do desconto
			 * fica incorreto em alguns itens da nota. Não foi possível determinar causa ou
			 * um padrao especifico
			 */

			vo.setProperty("VLRDESC", BigDecimal.valueOf(vlrDescCalc));

			logger.info(message);
		}

	}

	private void validateTotal(DynamicVO vo) throws SkalaModelException {
		BigDecimal nuNota = vo.asBigDecimal(SklFieldName.NUNOTA);
		BigDecimal sequencia = vo.asBigDecimal(SklFieldName.SEQUENCIA);
		BigDecimal codProd = vo.asBigDecimal(SklFieldName.CODPROD);

		double vlrTot = vo.asDouble("VLRTOT");

		if (vlrTot == 0D) {
			String message = String.format("[NUNOTA %s SEQUENCIA %s] %s-%s com <b>Valor Total igual a Zero</b>", nuNota,
					sequencia, codProd, vo.asString("Produto.DESCRPROD"));
			message += "<br>Favor procurar o Administrador do Sistema.";

			throw new SkalaModelException(message);
		}

	}

	/**
	 * Este metodo serve para inicializar os campos de Dt.Prev.Entrega. E calcular o
	 * campo Vlr Total Moeda
	 * 
	 * @author hiagoalexandre
	 */

	private void inicializaCampos(DynamicVO vo) throws SkalaModelException {
		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		DynamicVO cabVO;

		/**
		 * Chamado 52639 - Sempre que inserir/duplicar um item, buscar a Data Prevista
		 * de Entrega da TGFCAB
		 */
		try {
			cabVO = cabDAO.findByPK(vo.asBigDecimal(SklFieldName.NUNOTA));

			Timestamp dtPrevEnt = cabVO.asTimestamp("DTPREVENT");
			vo.setProperty(SklFieldName.DTINICIO, dtPrevEnt);

			recalculaCamposMoeda(vo);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível inicializar os campos.", e);
		}

	}

	/**
	 * Este metodo serve para recalcular os campos de Vlr Total Moeda
	 * 
	 * @author hiagoalexandre
	 */

	private void recalculaCamposMoeda(DynamicVO vo) throws SkalaModelException {
		BigDecimal vlrUnitMoeda = vo.asBigDecimalOrZero("AD_VLRUNITMOEDA");
		BigDecimal vlrTaxa = vo.asBigDecimalOrZero("AD_VLRTAXA");

		BigDecimal vlrTot = vlrUnitMoeda.multiply(vlrTaxa, BigDecimalUtil.MATH_CTX);

		vo.setProperty("AD_VLRTOTMOEDA", vlrTot);
	}

	@Override
	public void beforeUpdate(PersistenceEvent event) throws Exception {
		DynamicVO vo = (DynamicVO) event.getVo();

		String usoProd = vo.asString(SklFieldName.USOPROD);

		JapeWrapper cabDAO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
		BigDecimal codTipOper = BigDecimal.ZERO;

		ModifingFields modifingFields = event.getModifingFields();

		DynamicVO cabVO;

		try {
			cabVO = cabDAO.findByPK(vo.asBigDecimal(SklFieldName.NUNOTA));
			codTipOper = cabVO.asBigDecimal(SklFieldName.CODTIPOPER);

		} catch (Exception e) {
			String msg = String.format("[NUNOTA %s] Nao foi possivel encontrar a nota no sistema.",
					vo.asBigDecimal(SklFieldName.NUNOTA));

			SkalaModelException.throwMe(msg, e);
		}

		if (USOPROD_VENDA.indexOf(usoProd) > -1 && modifingFields.isModifingAny("VLRTOT-PERCDESC-VLRDESC")) {

			if (!Arrays.asList(2196L, 3297L, 3293L, 3270L, 3316L).contains(codTipOper.longValue())) {
				validateTotal(vo);

				validateDiscount(vo, true);
			}

		}

		if (Arrays.asList(2003L, 2006L, 2007L, 2020L).contains(codTipOper.longValue()))
			recalculaCamposMoeda(vo);

		BarramentoRegra.validaCFOP(vo);

		BarramentoRegra.validaControle(vo);

	}

}
