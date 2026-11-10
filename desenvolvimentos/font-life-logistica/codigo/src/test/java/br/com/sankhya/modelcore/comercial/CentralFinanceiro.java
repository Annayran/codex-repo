package br.com.sankhya.modelcore.comercial;
import java.math.BigDecimal;
import java.util.List;
public class CentralFinanceiro {
    public static boolean fail;
    public static List<String> events;
    public void inicializaNota(BigDecimal nota) { }
    public void refazerFinanceiro() throws Exception {
        events.add("financeiro");
        if (fail) throw new Exception("Falha financeira simulada");
    }
}
