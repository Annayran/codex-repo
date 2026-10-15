package br.zaya.telecontrol.shared.utils;

import br.com.sankhya.dwf.services.ServiceUtils;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityDAO;
import br.com.sankhya.jape.dao.EntityPropertyDescriptor;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.mgecomercial.model.centrais.cac.CACSP;
import br.com.sankhya.mgecomercial.model.centrais.cac.CACSPHome;
import br.com.sankhya.mgecomercial.model.facades.SelecaoDocumentoSP;
import br.com.sankhya.mgecomercial.model.facades.SelecaoDocumentoSPHome;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;

import br.com.sankhya.timimob.model.utils.jape.EntityFacadeW;
import br.com.sankhya.timimob.model.utils.jape.WrapperVO;
import br.com.sankhya.ws.ServiceContext;
import com.sankhya.util.XMLUtils;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.jdom.Content;
import org.jdom.Element;

public class PedidoNotaHelper {
	public boolean confimarNota(BigDecimal nuNota) throws Exception {
		EntityFacadeW dwf = EntityFacadeW.newDWF();
		WrapperVO notaVO = dwf.findByPK("CabecalhoNota", nuNota);
		if (!"L".equals(notaVO.asString("STATUSNOTA"))) {
			try {
				CACHelper cacHelper = new CACHelper();
				cacHelper.processarConfirmacao(nuNota);
				notaVO.reloadVO();
			} catch (Exception var5) {
				notaVO.reloadVO();
				if (!"L".equals(notaVO.asString("STATUSNOTA")))
					throw var5;
			}
			if (!"L".equals(notaVO.asString("STATUSNOTA")))
				throw new MGEModelException("Falha na confirmaNota não confirmada.");
		}
		return "L".equals(notaVO.asString("STATUSNOTA"));
	}

	public BigDecimal incluirNota(final DynamicVO notaVO, final Collection<DynamicVO> itemColl) throws Exception {
		JapeSession.TXBlock txBlock = new JapeSession.TXBlock() {
			public void doWithTx() throws Exception {
				notaVO.setProperty("NUNOTA", null);
				notaVO.clearReferences();
				Iterator<DynamicVO> var2 = itemColl.iterator();
				while (var2.hasNext()) {
					DynamicVO itemVO = var2.next();
					itemVO.setProperty("NUNOTA", null);
					itemVO.setProperty("SEQUENCIA", null);
					itemVO.clearReferences();
				}
				EntityFacadeW dwf = EntityFacadeW.newDWF();
				EntityDAO notaDAO = dwf.getDAOInstance("CabecalhoNota");
				Element nota = new Element("nota");
				Element cabecalho = new Element("cabecalho");
				Element itens = new Element("itens");
				Map<String, EntityPropertyDescriptor> notaAllFields = notaDAO.getSQLProvider().getAllFieldsByName();
				Iterator<String> var8 = notaAllFields.keySet().iterator();
				while (var8.hasNext()) {
					String fieldName = var8.next();
					Element field = new Element(fieldName);
					field.addContent(notaDAO.getFieldAsString(notaVO.getProperty(fieldName), fieldName));
					cabecalho.addContent((Content) field);
				}
				nota.addContent((Content) cabecalho);
				ServiceContext currentContext = ServiceContext.getCurrent();
				try {
					ServiceContext ctx = new ServiceContext(null);
					ctx.setAutentication(AuthenticationInfo.getCurrent());
					ctx.setRequestBody(new Element("requestBody"));
					ctx.makeCurrent();
					ctx.getRequestBody().addContent((Content) nota);
					CACHelper cacHelper = new CACHelper();
					cacHelper.incluirAlterarCabecalhoNota(ctx);
					Element primaryKey = XMLUtils.getRequiredChild(ctx.getBodyElement(), "pk");
					Iterator<Element> var12 = primaryKey.getChildren().iterator();
					while (var12.hasNext()) {
						Element fieldx = var12.next();
						if (notaVO.containsProperty(fieldx.getName()))
							notaVO.setProperty(fieldx.getName(),
									notaDAO.getFieldTypedValue(XMLUtils.getContentAsString(fieldx), fieldx.getName()));
					}
					notaDAO.populateDeattachedValueObject(notaVO, null, false, false, true, true);
					nota.removeContent();
					nota.addContent((Content) itens);
					nota.setAttribute("NUNOTA", notaDAO.getFieldAsString(notaVO.getProperty("NUNOTA"), "NUNOTA"));
					EntityDAO itemDAO = dwf.getDAOInstance("ItemNota");
					Map<String, EntityPropertyDescriptor> itemAllFields = itemDAO.getSQLProvider().getAllFieldsByName();
					Iterator<DynamicVO> var14 = itemColl.iterator();
					while (var14.hasNext()) {
						DynamicVO itemVOx = var14.next();
						itemVOx.setProperty("NUNOTA", notaVO.getProperty("NUNOTA"));
						Element item = new Element("item");
						Iterator<String> var17 = itemAllFields.keySet().iterator();
						while (var17.hasNext()) {
							String fieldNamex = var17.next();
							Element fieldxx = new Element(fieldNamex);
							fieldxx.addContent(itemDAO.getFieldAsString(itemVOx.getProperty(fieldNamex), fieldNamex));
							item.addContent((Content) fieldxx);
						}
						itens.addContent((Content) item);
					}
					ctx.getRequestBody().removeContent();
					ctx.getBodyElement().removeContent();
					ctx.getRequestBody().addContent((Content) nota);
					try {
						cacHelper.incluirAlterarItemNota(ctx);
					} catch (Exception var22) {
						JapeFactory.dao("CabecalhoNota").delete(new Object[] { notaVO.asBigDecimal("NUNOTA") });
						throw var22;
					}
				} finally {
					if (currentContext != null)
						currentContext.makeCurrent();
				}
			}
		};
		if (JapeSession.getCurrentSession().hasTransaction()) {
			txBlock.doWithTx();
		} else {
			JapeSession.getCurrentSession().getTopMostHandle().execWithTX(txBlock);
		}
		return notaVO.asBigDecimal("NUNOTA");
	}
}

