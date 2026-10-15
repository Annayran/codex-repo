package tech.skala.action.integration.tangerino.utils;

import java.math.BigDecimal;

public class ManagerUtils {
    private static final ThreadLocal<BigDecimal> SECURITY_CONTEXT = new ThreadLocal<>();

    public static void setInfo(BigDecimal info) {
        SECURITY_CONTEXT.set(info);
    }
    public static BigDecimal getInfo() {
        return SECURITY_CONTEXT.get();
    }
    public static void clear(){
        SECURITY_CONTEXT.remove();
    }

}
