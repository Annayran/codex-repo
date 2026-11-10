import helper.ValorAtividade;
import java.math.BigDecimal;

/** Teste executavel sem SDK ou banco, com o mesmo calculador usado na rotina. */
public class ValorAtividadeTest {
    private static int verificacoes;

    private static void igual(String esperado, BigDecimal resultado) {
        if (new BigDecimal(esperado).compareTo(resultado) != 0) {
            throw new AssertionError("Esperado " + esperado + ", obtido " + resultado);
        }
        verificacoes++;
    }

    private static void invalido(BigDecimal valor, BigDecimal quantidade) {
        try {
            ValorAtividade.unitario(valor, quantidade);
        } catch (IllegalArgumentException esperado) {
            verificacoes++;
            return;
        }
        throw new AssertionError("Entrada invalida aceita");
    }

    public static void main(String[] args) {
        igual("37550.00", ValorAtividade.total(new BigDecimal("37550"), BigDecimal.ONE));
        igual("37550", ValorAtividade.unitario(new BigDecimal("37550"), BigDecimal.ONE));
        igual("37550", ValorAtividade.total(new BigDecimal("37550"), new BigDecimal("3")));
        igual("12516.66666667", ValorAtividade.unitario(new BigDecimal("37550"), new BigDecimal("3")));
        igual("100", ValorAtividade.unitario(new BigDecimal("50"), new BigDecimal("0.5")));
        igual("10.01", ValorAtividade.total(new BigDecimal("10.005"), BigDecimal.ONE));
        invalido(null, BigDecimal.ONE);
        invalido(BigDecimal.ZERO, BigDecimal.ONE);
        invalido(new BigDecimal("-1"), BigDecimal.ONE);
        invalido(new BigDecimal("0.001"), BigDecimal.ONE);
        invalido(BigDecimal.TEN, null);
        invalido(BigDecimal.TEN, BigDecimal.ZERO);
        invalido(BigDecimal.TEN, new BigDecimal("-1"));
        System.out.println(verificacoes + " testes de calculo/validacao OK (sem banco).");
    }
}
