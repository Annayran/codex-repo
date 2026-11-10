import br.com.sankhya.jape.vo.DynamicVO;
import helper.SankhyaHelper;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/** Executa a inicializacao real com VO simulado; nao instancia EntityFacade. */
public class ValoresCabecalhoTest {
    private static int verificacoes;

    private static DynamicVO vo(final Map<String, Object> valores) {
        return (DynamicVO) Proxy.newProxyInstance(DynamicVO.class.getClassLoader(),
                new Class<?>[]{DynamicVO.class}, (proxy, method, args) -> {
                    if ("setProperty".equals(method.getName())) {
                        valores.put((String) args[0], args[1]);
                        return null;
                    }
                    if ("asBigDecimal".equals(method.getName())) return valores.get(args[0]);
                    throw new UnsupportedOperationException(method.getName());
                });
    }

    private static void igual(String esperado, Object obtido) {
        if (!(obtido instanceof BigDecimal)
                || new BigDecimal(esperado).compareTo((BigDecimal) obtido) != 0) {
            throw new AssertionError("Esperado " + esperado + ", obtido " + obtido);
        }
        verificacoes++;
    }

    public static void main(String[] args) {
        String[] campos = {"VLRNOTA", "VLRDESCTOT", "VLRDESCTOTITEM", "VLRDESCSERV", "PERCDESC", "VLRFRETE"};
        for (String atividade : new String[]{"5980", "12650", "98120", "37550"}) {
            Map<String, Object> modelo = new HashMap<>();
            for (String campo : campos) modelo.put(campo, new BigDecimal("16970.16"));
            modelo.put("CODTIPOPER", new BigDecimal("1706"));
            Map<String, Object> apontamento = new HashMap<>();
            apontamento.put("VLRSERVICO", new BigDecimal(atividade));
            apontamento.put("QTDNEG", BigDecimal.ONE);
            SankhyaHelper.inicializarValoresCabecalho(vo(modelo), vo(apontamento));
            igual(atividade, modelo.get("VLRNOTA"));
            for (String campo : campos) if (!"VLRNOTA".equals(campo)) igual("0", modelo.get(campo));
            igual("1706", modelo.get("CODTIPOPER"));
        }
        System.out.println(verificacoes + " verificacoes de inicializacao do cabecalho OK (VO simulado, sem banco).");
    }
}
