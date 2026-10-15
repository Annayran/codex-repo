package tech.skala.action.modules.comercial.estoque.helpers;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Logger;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.comercial.estoque.beans.EstoquePorParceiro;
import tech.skala.action.modules.comercial.estoque.beans.ItemEstoque;
import tech.skala.core.beans.parceiro.ParceiroFinder;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class NotaCompraEstoqueNegativoHelper {
	private static final Logger					LOGGER = Logger.getLogger("NotaCompraEstoqueNegativoHelper");

	private Action								action;
	private BigDecimal							codEmp;
	private BigDecimal							codParc;
	private BigDecimal							codParcLancamento;
	private BigDecimal							codLocal;
	private BigDecimal							nuNota;
	private String								tipo;

	private BigDecimal nuNotaMod = BigDecimal.ZERO;

	public NotaCompraEstoqueNegativoHelper(Action action, BigDecimal codEmp, BigDecimal codParc,
			BigDecimal codLocal, String tipo) {
		this.action = action;
		this.codEmp = codEmp;
		this.codParc = codParc;
		this.codParcLancamento = codParc;
		this.codLocal = codLocal;
		this.tipo = tipo;
	}

	public void process() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					validateStock();
					createOrder();
					relateOrder();
				}
				
			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar a ação.", e);
		} finally {
			hnd = null;
		}
	}

	private void validateStock() throws SkalaModelException {
		
		JapeWrapper dao = JapeFactory.dao("AD_PLVGFESTNEG");
		DynamicVO stockVO = null;

		Timestamp dtVal = null;
		Timestamp dtFabricacao = null;
		final Timestamp now = TimeUtils.clearTime(TimeUtils.getNow());
		Collection<DynamicVO> estoqueSemInfoDatasVOs = null;
		try {
			Collection<DynamicVO> itensVO = dao.find("this.CODEMP = ? AND this.CODPARC = ? AND this.CODLOCAL = ?",
					codEmp, codParc, codLocal);

			String controle;
			BigDecimal codProd;
			setAtualizando(true);
			for (DynamicVO itemVO : itensVO) {
				codProd = itemVO.asBigDecimal(SklFieldName.CODPROD);
				controle = itemVO.asString(SklFieldName.CONTROLE);

				stockVO = EstoqueFinder.buscaEstoqueComDataValidade(codProd, controle);

				if (stockVO == null) {
					dtFabricacao = now;
					dtVal = TimeUtils.dataAdd(now, 2, Calendar.YEAR);
				} else {
					dtVal = stockVO.asTimestamp(SklFieldName.DTVAL);
					dtFabricacao = stockVO.asTimestamp(SklFieldName.DTFABRICACAO);
				}

				estoqueSemInfoDatasVOs = EstoqueFinder.buscaEstoquesSemDataValidade(codProd, controle);
				EntityUpdate entityUpdate;
				for (DynamicVO vo : estoqueSemInfoDatasVOs) {
					entityUpdate = new EntityUpdate(DynamicEntityNames.ESTOQUE, vo);
					entityUpdate.setField(SklFieldName.DTFABRICACAO, dtFabricacao);
					entityUpdate.setField(SklFieldName.DTVAL, dtVal);
					entityUpdate.fluidUpdate();
				}

			}
			setAtualizando(false);

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível realizar validação de Estoque para os produtos.", e);
		}

	}
	
	private void setAtualizando(boolean value) throws SkalaModelException {
		CallableStatement rsv = null;
		JdbcWrapper jdbc = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			rsv = jdbc.getConnection().prepareCall("{call STP_SET_ATUALIZANDO(?)}");
			rsv.setString(1, (value ? "S" : "N"));
			rsv.execute();
		} catch (SQLException e) {
			SkalaModelException.throwMe("Não foi possível alterar Variável de sessão.", e);
		} finally {
			JdbcUtils.closeStatement(rsv);
			JdbcWrapper.closeSession(jdbc);
		}
	}
	
	private void relateOrder() throws SkalaModelException {
		JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);
		
		JapeWrapper estoqueNegDAO = JapeFactory.dao("AD_PLVGFPAREST");
		DynamicVO estoqueNegVO = null;
		
		try {
			
			estoqueNegVO = estoqueNegDAO.findOne("this.CODEMP = ? AND this.CODPARC = ? AND this.CODLOCAL = ?", codEmp, codParc, codLocal);
			
			EstoquePorParceiro estoque = new EstoquePorParceiro();
			estoque.setCodEmp(codEmp);
			estoque.setCodLocal(codLocal);
			estoque.setCodParc(codParc);
			estoque.setTipo(estoqueNegVO.asString("TIPO"));
			estoque.persists();
			
			ItemEstoque itemEstoque;
			for(DynamicVO itemVO : dao.find("this.NUNOTA = ?", nuNota)) {
				itemEstoque = new ItemEstoque();
				itemEstoque.setCodEmp(codEmp);
				itemEstoque.setCodParc(codParc);
				itemEstoque.setCodLocal(codLocal);
				itemEstoque.setControle(itemVO.asString(SklFieldName.CONTROLE));
				itemEstoque.setTipo(estoqueNegVO.asString("TIPO"));
				itemEstoque.setCodProd(itemVO.asBigDecimal(SklFieldName.CODPROD));
				itemEstoque.setSequencia(itemVO.asBigDecimal(SklFieldName.SEQUENCIA));
				itemEstoque.setNuNota(nuNota);
				
				Double qtdNeg = Math.abs(itemVO.asDouble(SklFieldName.QTDNEG));
				itemEstoque.setEstoque(BigDecimalUtil.getValueOrZero(BigDecimal.valueOf(qtdNeg)));
				
				itemEstoque.persists();
			}
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir registro da Nota de Ajuste criada.", e);
		}
	}

	private void createOrder() throws SkalaModelException {
		try {
			resolveModelByCompany();

			String observacao = "NOTA DE COMPRA GERADO AUTOMATICAMENTE VIA ROTINA: Controle de Estoque Negativo.";
			CabecalhoNotaEstoqueNegativo cabecalho = new CabecalhoNotaEstoqueNegativo(nuNotaMod, codEmp,
					codParcLancamento, codParc, codLocal, observacao);
			cabecalho.buildCabecalhoNotaByTemplate();
			cabecalho.buildItems();
			
			this.nuNota = cabecalho.getNuNota();
			action.appendMessage(cabecalho.toString());
			
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível gerar a Nota de Compra.", e);
		} 
	}


	private void resolveModelByCompany() throws SkalaModelException {
		String msg;
		LOGGER.info("resolveModelByCompany");
		
		try {
			JapeWrapper dao = JapeFactory.dao(DynamicEntityNames.EMPRESA_FINANCEIRO);
			DynamicVO vo = dao.findByPK(codEmp);
			
			if ("P".equals(tipo) && BigDecimal.ZERO.compareTo(codParc) == 0) {
				this.nuNotaMod = vo.asBigDecimal("NOTAENTAJUSTEST");
				this.codParcLancamento = ParceiroFinder.buscaParceiroDaEmpresa(codEmp).asBigDecimal(SklFieldName.CODPARC);
			}
			else if ("P".equals(tipo) && codParc.longValue() > 0L) {
				this.nuNotaMod = vo.asBigDecimal("NOTAENTAJUSTESTCTER");
			}
			
			msg = String.format("Nota Modelo - Tipo %s codEmp %s codParc %s nuNotaMod %s", tipo, codEmp, codParc, nuNotaMod);
			
		} catch (Exception e) {
			msg = String.format("Não foi possível localizar o Modelo de Nota para a Empresa [%s]", this.codEmp);
			SkalaModelException.throwMe(msg, e);
		}

		LOGGER.info(msg);

	}

}
