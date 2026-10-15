package br.com.zaya.utils;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.EntityPropertyDescriptor;
import br.com.sankhya.jape.metadata.EntityField;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.dwfdata.vo.tsi.UsuarioVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.ParameterUtils;
import br.com.sankhya.ws.ServiceContext;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.regex.Pattern;
import org.jboss.logging.Logger;

public class SankhyaUtils {
  private static Logger logger = Logger.getLogger(SankhyaUtils.class);
  
  public static final int TYPE_TIMESTAMP = 0;
  
  public static final int TYPE_STRING = 1;
  
  public static final int TYPE_NUMBER = 2;
  
  public static final int TYPE_BLOB = 3;
  
  public static final int TYPE_CLOB = 4;
  
  public static final int TYPE_DATE = 5;
  
  public static Boolean isDebugging() {
    for (String it : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
      if (Pattern.compile("-Xdebug|jdwp").matcher(it).find())
        return Boolean.valueOf(true); 
    } 
    return Boolean.valueOf(false);
  }
  
  public static Boolean isEmpty(String value) {
    return Boolean.valueOf((value == null || value.isEmpty()));
  }
  
  public static void debug(String text) {
    if (!isDebugging().booleanValue())
      return; 
    logger.info(text);
  }
  
  public static void debug(String text, Exception ex) {
    if (!isDebugging().booleanValue())
      return; 
    logger.info(text, ex);
  }
  
  public static void debug(Object clazz, String text) {
    if (!isDebugging().booleanValue())
      return; 
    getLogger(clazz.getClass()).info(text);
  }
  
  public static Logger getLogger(Class<?> cls) {
    return Logger.getLogger(cls.getName());
  }
  
  public static Exception getRealException(Exception ex) {
    if (ex instanceof InvocationTargetException)
      return new Exception(((InvocationTargetException)ex).getTargetException().getMessage(), ex); 
    if (ex instanceof UndeclaredThrowableException) {
      Throwable e = ((UndeclaredThrowableException)ex).getUndeclaredThrowable();
      if (e instanceof InvocationTargetException)
        return new Exception(((InvocationTargetException)e).getTargetException().getMessage(), e); 
    } 
    return ex;
  }
  
  public static Object getPropertyValue(Map<String, EntityPropertyDescriptor> fieldsByName, Map.Entry<String, Object> property) throws UnsupportedEncodingException {
    Object value = property.getValue();
    if (value != null) {
      EntityField field = ((EntityPropertyDescriptor)fieldsByName.get(property.getKey())).getField();
      switch (field.getType()) {
        case 0:
          if ("${cur.date}".equals(property.getValue())) {
            value = new Timestamp(TimeUtils.clearTime(System.currentTimeMillis()));
            break;
          } 
          if ("${cur.datetime}".equals(property.getValue())) {
            value = new Timestamp(System.currentTimeMillis());
            break;
          } 
          value = TimeUtils.buildTimestamp(property.getValue().toString());
          break;
        case 1:
          value = property.getValue();
          break;
        case 2:
          value = new BigDecimal(Double.valueOf(property.getValue().toString()).doubleValue());
          break;
        case 3:
          value = property.getValue().toString().getBytes("ISO-8859-1");
          break;
        case 4:
          value = property.getValue().toString().toCharArray();
          break;
      } 
    } 
    return value;
  }
  
  public static void autenticateSUP(ServiceContext ctx) throws Exception {
    JapeSession.SessionHandle hnd = null;
    try {
      hnd = JapeSession.open();
      hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
      hnd.setCanTimeout(false);
      hnd.setFindersMaxRows(-1);
      AuthenticationInfo authInfo = new AuthenticationInfo("SUP", BigDecimalUtil.ZERO_VALUE, BigDecimalUtil.ZERO_VALUE, Integer.valueOf(2147483647));
      authInfo.makeCurrent();
      EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
      DynamicVO userVO = (DynamicVO)dwfFacade.findEntityByPrimaryKeyAsVO("Usuario", new Object[] { authInfo.getUserID() });
      UsuarioVO usuVO = (UsuarioVO)userVO.wrapInterface(UsuarioVO.class);
      JapeSessionContext.putProperty("usuario_logado", authInfo.getUserID());
      JapeSessionContext.putProperty("emp_usu_logado", BigDecimalUtil.getValueOrZero(usuVO.getCODEMP()));
      JapeSessionContext.putProperty("dh_atual", new Timestamp(System.currentTimeMillis()));
      JapeSessionContext.putProperty("d_atual", new Timestamp(TimeUtils.getToday()));
      JapeSessionContext.putProperty("usuarioVO", usuVO);
      JapeSessionContext.putProperty("authInfo", authInfo);
      ctx.setAutentication(authInfo);
      ctx.makeCurrent();
    } catch (Exception ex) {
      MGEModelException.throwMe(ex);
    } finally {
      JapeSession.close(hnd);
    } 
  }
  
  public static Boolean isBase64(String value) {
    return Boolean.valueOf((value != null && value.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")));
  }
  
  public static BigDecimal getAsBigDecimal(Object source) {
    if (source == null)
      return null; 
    BigDecimal value = null;
    try {
      value = BigDecimalUtil.valueOf(source.toString());
    } catch (NumberFormatException ex) {
      logger.error(ex.getMessage());
    } 
    return value;
  }
  
  public static Object parameter(String name) {
    return parameter(name, null);
  }
  
  public static Object parameter(String name, Object otherwise) {
    try {
      Object value = ParameterUtils.getParameter(name);
      if (value == null)
        return otherwise; 
      return value;
    } catch (Exception ex) {
      logger.warn("Erro ao carregar o par" + name + ", exception: " + ex.getMessage());
      return otherwise;
    } 
  }
}

