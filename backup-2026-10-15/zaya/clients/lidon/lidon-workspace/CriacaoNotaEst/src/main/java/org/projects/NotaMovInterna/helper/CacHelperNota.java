//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.helper;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityDAO;
import br.com.sankhya.jape.dao.EntityPropertyDescriptor;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.timimob.model.utils.jape.EntityFacadeW;
import br.com.sankhya.ws.ServiceContext;
import com.sankhya.util.XMLUtils;
import org.jdom.Element;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


public class CacHelperNota {
    public CacHelperNota() {
    }

    public BigDecimal incluirNota(final DynamicVO notaVO, final Collection<DynamicVO> itemColl) throws Exception {
        JapeSession.TXBlock txBlock = new JapeSession.TXBlock() {
            public void doWithTx() throws Exception {
                String lastFieldName = null;
                notaVO.setProperty("NUNOTA", (Object)null);
                notaVO.clearReferences();

                for(DynamicVO itemVO : itemColl) {
                    itemVO.setProperty("NUNOTA", (Object)null);
                    itemVO.setProperty("SEQUENCIA", (Object)null);
                    itemVO.clearReferences();
                }

                EntityFacadeW dwf = EntityFacadeW.newDWF();
                EntityDAO notaDAO = dwf.getDAOInstance("CabecalhoNota");
                Element nota = new Element("nota");
                Element cabecalho = new Element("cabecalho");
                Element itens = new Element("itens");
                Map<String, EntityPropertyDescriptor> notaAllFields = notaDAO.getSQLProvider().getAllFieldsByName();

                for(String fieldName : notaAllFields.keySet()) {
                    Element field = new Element(fieldName);
                    Object rawVal = notaVO.getProperty(fieldName);
                    Object normalized = CacHelperNota.this.normalizeForField(notaDAO, (EntityPropertyDescriptor)notaAllFields.get(fieldName), fieldName, rawVal);

                    try {
                        field.addContent(notaDAO.getFieldAsString(normalized, fieldName));
                    } catch (Exception var31) {
                        field.addContent(normalized != null ? normalized.toString() : "");
                    }

                    cabecalho.addContent(field);
                }

                nota.addContent(cabecalho);
                ServiceContext previousCtx = ServiceContext.getCurrent();

                try {
                    ServiceContext ctx = new ServiceContext((HttpServletRequest)null);
                    ctx.setAutentication(AuthenticationInfo.getCurrent());
                    ctx.setRequestBody(new Element("requestBody"));
                    ctx.makeCurrent();
                    ctx.getRequestBody().addContent(nota);
                    CACHelper cacHelper = new CACHelper();
                    cacHelper.incluirAlterarCabecalhoNota(ctx);
                    Element primaryKey = XMLUtils.getRequiredChild(ctx.getBodyElement(), "pk");

                    for(Object child : primaryKey.getChildren()) {
                        if (!(child instanceof Element)) {
                            continue;
                        }

                        Element f = (Element) child;
                        if (notaVO.containsProperty(f.getName())) {
                            Object typed = notaDAO.getFieldTypedValue(XMLUtils.getContentAsString(f), f.getName());
                            notaVO.setProperty(f.getName(), typed);
                        }
                    }

                    notaDAO.populateDeattachedValueObject(notaVO, (String)null, false, false, true, true);
                    BigDecimal nunota = notaVO.asBigDecimal("NUNOTA");
                    nota.removeContent();
                    nota.addContent(itens);
                    nota.setAttribute("NUNOTA", nunota.toString());
                    EntityDAO itemDAO = dwf.getDAOInstance("ItemNota");
                    Map<String, EntityPropertyDescriptor> itemAllFields = itemDAO.getSQLProvider().getAllFieldsByName();

                    for(DynamicVO it : itemColl) {
                        it.setProperty("NUNOTA", nunota);
                        Element item = new Element("item");

                        for(String fn : itemAllFields.keySet()) {
                            Element fx = new Element(fn);
                            Object rawValX = it.getProperty(fn);
                            Object normalizedX = CacHelperNota.this.normalizeForField(itemDAO, (EntityPropertyDescriptor)itemAllFields.get(fn), fn, rawValX);

                            try {
                                fx.addContent(itemDAO.getFieldAsString(normalizedX, fn));
                            } catch (Exception var29) {
                                fx.addContent(normalizedX != null ? normalizedX.toString() : "");
                            }

                            item.addContent(fx);
                        }

                        itens.addContent(item);
                    }

                    ctx.getRequestBody().removeContent();
                    ctx.getBodyElement().removeContent();
                    ctx.getRequestBody().addContent(nota);
                    cacHelper.incluirAlterarItemNota(ctx);
                } finally {
                    if (previousCtx != null) {
                        previousCtx.makeCurrent();
                    }

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

    private Object normalizeForField(EntityDAO dao, EntityPropertyDescriptor desc, String fieldName, Object value) {
        if (value == null) {
            return null;
        } else {
            Class<?> expectedType = null;
            if (desc != null) {
                try {
                    String[] methods = new String[]{"getType", "getPropertyType", "getFieldType", "getJavaType", "getClazz", "getValueType"};

                    for(String m : methods) {
                        try {
                            Object r = desc.getClass().getMethod(m).invoke(desc);
                            if (r instanceof Class) {
                                expectedType = (Class)r;
                                break;
                            }
                        } catch (Exception var14) {
                        }
                    }
                } catch (Exception var15) {
                }
            }

            try {
                if (expectedType != null) {
                    if (expectedType.equals(BigDecimal.class)) {
                        if (value instanceof BigDecimal) {
                            return value;
                        }

                        return new BigDecimal(value.toString().trim());
                    }

                    if (expectedType.equals(Timestamp.class)) {
                        if (value instanceof Timestamp) {
                            return value;
                        }

                        if (value instanceof Date) {
                            return new Timestamp(((Date)value).getTime());
                        }

                        try {
                            return Timestamp.valueOf(value.toString());
                        } catch (Exception var12) {
                        }
                    }
                }

                return value.toString();
            } catch (Exception var13) {
                return value.toString();
            }
        }
    }
}
