package br.com.zaya.like.helper;

import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.ws.ServiceContext;

import com.sankhya.util.BigDecimalUtil;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
  public static ServiceContext ensureContext() {
    ServiceContext ctx = ServiceContext.getCurrent();
    if (ObjectUtil.isNull(ctx)) {
      ctx = new ServiceContext((HttpServletRequest)null);
      AuthenticationInfo authInfo = new AuthenticationInfo("SUP", BigDecimalUtil.ZERO_VALUE, BigDecimalUtil.ZERO_VALUE, new Integer(2147483647));
      authInfo.makeCurrent();
      ctx.setAutentication(authInfo);
      ctx.putHttpSessionAttribute("usuario_logado", BigDecimal.ZERO);
      JapeSessionContext.putProperty("usuario_logado", BigDecimal.ZERO);
      JapeSessionContext.putProperty("authInfo", authInfo);
      ctx.setAttribute("usuario_logado", BigDecimal.ZERO);
    } 
    ctx.makeCurrent();
    return ctx;
  }
  
  public static AuthenticationInfo getCurrentAuth() {
    ServiceContext ctx = ensureContext();
    return (AuthenticationInfo)ctx.getAutentication();
  }
}