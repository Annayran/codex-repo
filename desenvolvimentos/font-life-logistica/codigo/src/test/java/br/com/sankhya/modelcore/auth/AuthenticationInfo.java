package br.com.sankhya.modelcore.auth;
import java.math.BigDecimal;
public class AuthenticationInfo {
    public static AuthenticationInfo getCurrent() { return new AuthenticationInfo(); }
    public BigDecimal getUserID() { return BigDecimal.ONE; }
}
