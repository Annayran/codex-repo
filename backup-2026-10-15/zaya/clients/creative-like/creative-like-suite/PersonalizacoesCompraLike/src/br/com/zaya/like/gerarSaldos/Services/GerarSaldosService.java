package br.com.zaya.like.gerarSaldos.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.CollectionUtils;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.EntityDAO;
import br.com.sankhya.jape.dao.EntityPropertyDescriptor;
import br.com.sankhya.jape.util.FinderWrapper;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.CentralFaturamento;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import br.com.sankhya.ws.ServiceContext;
import br.com.zaya.like.helper.DocumentoHelper;

public class GerarSaldosService {
	public static String executarAcao(StringBuilder stringRetorno, BigDecimal nunotaModelo, DynamicVO modeloNotaVO,
			EntityDAO cabecalhoNotaDAO, Map<String, EntityPropertyDescriptor> fieldsCabecalho, Registro linha,
			EntityFacade dwfFacade) throws Exception, MGEModelException {
		DynamicVO negociacaoVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("AD_TGLCAB", new Object[] { linha.getCampo("NUNEGOCIACAO") });
		String msg = "";

		DocumentoHelper documentoHelper = new DocumentoHelper();

		documentoHelper.setNuNotaModelo(nunotaModelo);

		for (String fieldName : fieldsCabecalho.keySet()) {
			if (cabecalhoNotaDAO.containsProperty(fieldName)) {
				documentoHelper.addNotaProperty(fieldName, negociacaoVO.getProperty(fieldName));
			}
		}

		documentoHelper.addNotaProperty("NUNOTA", null);
		documentoHelper.addNotaProperty("NUMNOTA", BigDecimal.ZERO);
		documentoHelper.addNotaProperty("DTNEG", TimeUtils.getNow());

		documentoHelper.addNotaProperty("CODPARC", BigDecimal.ONE);
		documentoHelper.addNotaProperty("CODCONTATO", null);
		documentoHelper.addNotaProperty("CODEMP", negociacaoVO.asBigDecimal("CODEMP"));

		DynamicVO tipoOperacaoVO = ComercialUtils.getTipoOperacao(modeloNotaVO.asBigDecimal("CODTIPOPER"));
		documentoHelper.addNotaProperty("CODTIPOPER", tipoOperacaoVO.asBigDecimal("CODTIPOPER"));
		documentoHelper.addNotaProperty("DHTIPOPER", tipoOperacaoVO.asTimestamp("DHALTER"));
		documentoHelper.addNotaProperty("TIPMOV", tipoOperacaoVO.asString("TIPMOV"));

		DynamicVO tipoNegociacaoVO = ComercialUtils.getTipoNegociacao(negociacaoVO.asBigDecimal("CODTIPVENDA"));
		documentoHelper.addNotaProperty("CODTIPVENDA", tipoNegociacaoVO.asBigDecimal("CODTIPVENDA"));
		documentoHelper.addNotaProperty("DHTIPVENDA", tipoNegociacaoVO.asTimestamp("DHALTER"));

		documentoHelper.addNotaProperty("AD_NUNEGOCIACAO", negociacaoVO.asBigDecimal("NUNEGOCIACAO"));

		FinderWrapper finderItens = new FinderWrapper("AD_TGLITE", " (this.NUNEGOCIACAO = ?) ", new Object[] { negociacaoVO.asBigDecimal("NUNEGOCIACAO") });
		Collection<DynamicVO> collectionItensVO = dwfFacade.findByDynamicFinderAsVO(finderItens);

		boolean temCosturavel = false;
		if (CollectionUtils.isEmpty(collectionItensVO)) {
			msg = ("Não foram localizados itens pendentes para a Geração do Documento a partir da Negociação/Proposta [" + negociacaoVO.asBigDecimal("NUNEGOCIACAO") + "].\nVerifique a Aba de Itens.");

		} else {

			for (DynamicVO itemVO : collectionItensVO) {

				DynamicVO produtoVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("Produto", new Object[] { itemVO.asBigDecimal("CODPROD") });

				if ("C".equalsIgnoreCase(produtoVO.asString("AD_TIPO"))) {
					temCosturavel = true;
					documentoHelper.addItem();
					documentoHelper.addItemProperty("CODPROD", itemVO.asBigDecimal("CODPROD"));
					documentoHelper.addItemProperty("QTDNEG", itemVO.asBigDecimal("QTDNEG"));
					documentoHelper.addItemProperty("VLRUNIT", itemVO.asBigDecimal("VLRUNIT"));
					documentoHelper.addItemProperty("CONTROLE", negociacaoVO.asBigDecimal("NUNEGOCIACAO").toString());
					documentoHelper.addItemProperty("CODLOCAORIG", BigDecimal.valueOf(2090000L));
				}
			}
		}

		if (!temCosturavel) {
			msg = ("Operação Cancelada.\nNão foi possivel identificar Produtos com o Tipo igual a Fabricação Propria (Costuravel) para a Negociação de Nro [" + negociacaoVO.asBigDecimal("NUNEGOCIACAO") + "] ");

			return msg;
		}
		BigDecimal nuNota = BigDecimal.ZERO;

		try {
			nuNota = documentoHelper.criarNota();
		} catch (Exception e) {
			e.printStackTrace();
			MGEModelException.throwMe(e);
		}

		alterarLocalItem(nuNota, dwfFacade);

		BarramentoRegra bRegrasCab = BarramentoRegra.build(CentralFaturamento.class, "regrasConfirmacaoSilenciosa.xml", AuthenticationInfo.getCurrent());
		bRegrasCab.setValidarSilencioso(true);
		ConfirmacaoNotaHelper.confirmarNota(nuNota, bRegrasCab);

		try {
			JapeWrapper logRegistroJW = JapeFactory.dao("AD_TGLDOC");
			DynamicVO dynamicVO = ((FluidCreateVO) ((FluidCreateVO) ((FluidCreateVO) ((FluidCreateVO) logRegistroJW
					.create().set("NUNEGOCIACAO", negociacaoVO.asBigDecimal("NUNEGOCIACAO"))).set("NUNOTA", nuNota))
					.set("DHINC", TimeUtils.getNow())).set("CODUSUINC", AuthenticationInfo.getCurrent().getUserID()))
					.save();
		} catch (Exception e) {
			e.printStackTrace();
			MGEModelException.throwMe(e);
		}

		stringRetorno.append("Documento de Nro. Ãšnico [<b>" + nuNota + "</b>] gerado pela Proposta / Negociação [<b>"
				+ negociacaoVO.asBigDecimal("NUNEGOCIACAO") + "</b>].\n");
		return msg;
	}

	public static DynamicVO buscarModeloNotaVO(BigDecimal nunotaModelo, EntityFacade dwfFacade) throws Exception {
		DynamicVO modeloNotaVO = null;
		try {
			modeloNotaVO = (DynamicVO) dwfFacade.findEntityByPrimaryKeyAsVO("CabecalhoNota", nunotaModelo);
		} catch (Exception e) {
			throw new Exception("Não foi possivel localizar o Modelo de Nota/Pedidos [" + nunotaModelo
					+ "].\nVerificar configuração do Parâmetro <b>FBZMODAJUESTPNE</b>.");
		}

		return modeloNotaVO;
	}

	public static BigDecimal buscarNunotaModelo(EntityFacade dwfFacade) throws Exception {
		BigDecimal nunotaModelo = BigDecimal.ZERO;
		try {
			nunotaModelo = BigDecimalUtil.getBigDecimal(Integer.valueOf(MGECoreParameter.getParameterAsInt("FBZMODAJUESTPNE")));
			if (nunotaModelo == null) {
				throw new Exception( "Não foi localizado Modelo de Nota/Pedido para a Geração do Documento.\nVerificar configuração do Parâmetro <b>FBZMODAJUESTPNE</b>.");
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return nunotaModelo;
	}

	private static void alterarLocalItem(BigDecimal nuNota, EntityFacade dwfFacade) throws Exception {
		StringBuffer where = new StringBuffer();
		where.append(" ( this.NUNOTA = ? ) ");

		FinderWrapper finderItens = new FinderWrapper("ItemNota", where.toString(), new Object[] { nuNota });
		Collection<DynamicVO> collItens = dwfFacade.findByDynamicFinderAsVO(finderItens);

		CACHelper cacHelper = new CACHelper();

		if (CollectionUtils.isNotEmpty(collItens))
			for (DynamicVO itemVO : collItens) {

				DynamicVO itemOldVO = itemVO.buildClone();
				DynamicVO itemNewVO = itemVO.buildClone();

				itemNewVO.setProperty("CODLOCALORIG", BigDecimal.valueOf(2090000L));

				PersistentLocalEntity itePLE = dwfFacade.findEntityByPrimaryKey("ItemNota",
						new Object[] { itemVO.asBigDecimal("NUNOTA"), itemVO.asBigDecimal("SEQUENCIA") });
				ArrayList<PrePersistEntityState> itensNotaPPS = new ArrayList<>();
				ServiceContext ctx = ServiceContext.getCurrent();

				try {
					JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.valueOf(true));
					JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.valueOf(true));

					PrePersistEntityState itePreStateM = PrePersistEntityState.build(dwfFacade, "ItemNota", itemNewVO,
							itemOldVO, itePLE);
					itensNotaPPS.add(itePreStateM);

					cacHelper.incluirAlterarItem(itemNewVO.asBigDecimal("NUNOTA"), ctx, itensNotaPPS, true);
				} catch (Exception e) {
					MGEModelException.throwMe(e);
				} finally {
					JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.valueOf(false));
					JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.valueOf(false));
				}
			}
	}

}
