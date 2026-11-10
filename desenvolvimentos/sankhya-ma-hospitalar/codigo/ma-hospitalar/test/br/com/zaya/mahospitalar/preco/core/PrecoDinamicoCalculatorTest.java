package br.com.zaya.mahospitalar.preco.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrecoDinamicoCalculatorTest {
    private final PrecoDinamicoCalculator calculator = new PrecoDinamicoCalculator();

    @ParameterizedTest(name = "{0}")
    @MethodSource("cenariosPlanilha")
    void reproduzMemoriaDeCalculoDaPlanilha(Cenario cenario) {
        CalculoResultado resultado = calculator.calcularDireto(cenario.entrada, cenario.receitaBruta);

        assertProximo(cenario.receitaLiquida, resultado.getReceitaLiquida());
        assertProximo(cenario.resultado, resultado.getResultado());
        assertProximo(cenario.margem, resultado.getMargemContribuicao());
    }

    @ParameterizedTest(name = "preco alvo - {0}")
    @MethodSource("cenariosPlanilha")
    void precoInversoAtingeMargemAlvo(Cenario cenario) {
        BigDecimal margemAlvo = new BigDecimal("0.25");
        CalculoResultado resultado = calculator.calcularPrecoAlvo(cenario.entrada, margemAlvo);

        assertTrue(resultado.getMargemContribuicao().compareTo(margemAlvo) >= 0);
        assertEquals(2, resultado.getPrecoUnitario().scale());
    }

    @Test
    void convertePrecoFinalParaMoedaDoDocumento() {
        CalculoEntrada entrada = entrada("1", "0", "0.07", "0", "1000", "2", "5");
        CalculoResultado resultado = calculator.calcularPrecoAlvo(entrada, new BigDecimal("0.30"));

        BigDecimal totalEmReais = resultado.getPrecoUnitario()
                .multiply(new BigDecimal("2"))
                .multiply(new BigDecimal("5"));
        assertTrue(totalEmReais.compareTo(new BigDecimal("1587")) > 0);
    }

    @Test
    void bloqueiaMargemImpossivel() {
        CalculoEntrada entrada = entrada("0.20", "0", "0.19", "0", "1000", "1", "1");
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calcularPrecoAlvo(entrada, new BigDecimal("0.20")));
    }

    private static Stream<Cenario> cenariosPlanilha() {
        return Stream.of(
                c("PR1 direta SC", "1", "0", ".07", "1000", "1700", "1700", "581", ".3417647058823529"),
                c("PR1 direta fora SC", "1", "0", ".07", "3159", "5900", "5900", "2328", ".3945762711864407"),
                c("PR1 importada local", "1", "0", ".07", "1000", "1700", "1700", "581", ".3417647058823529"),
                c("PR1 nacional", "1", "0", ".07", "1000", "1700", "1700", "581", ".3417647058823529"),

                c("PR2 direta SC", ".9075", "117.5", ".07", "1000", "1700", "1660.25", "541.25", ".3260051197108869"),
                c("PR2 direta fora SC", ".9075", "89.8875", ".05", "765", "950", "952.0125", "139.5125", ".14654481952705456"),
                c("PR2 importada local", ".9075", "7.03", ".07", "76", "1700", "1549.78", "1354.78", ".8741756894526965"),
                c("PR2 nacional", ".9075", "92.5", ".07", "1000", "1700", "1635.25", "516.25", ".3157009631554808"),

                c("PR3 direta SC", ".95797", "0", ".07", "1000", "1700", "1628.549", "509.549", ".3128852739463166"),
                c("PR3 direta fora SC", ".82955", "0", "0", "6800", "10500", "8710.275", "1910.275", ".21931282307389832"),
                c("PR3 importada local", ".86", "0", ".07", "1000", "1700", "1462", "343", ".23461012311901505"),
                c("PR3 nacional", ".94", "0", ".07", "1000", "1700", "1598", "479", ".2997496871088861"),

                c("PR4 direta SC", ".86547", "117.5", ".07", "1000", "1700", "1588.799", "469.799", ".29569442075429303"),
                c("PR4 direta fora SC", ".7657", "2506.8625", ".04", "21335", "1700", "3808.5525", "-17594.4475", "-4.619720353073774"),
                c("PR4 importada local", ".7275", "39750", ".07", "300000", "495000", "399862.5", "65212.5", ".16308731126324674"),
                c("PR4 nacional", ".7275", "212.5", ".07", "1000", "1700", "1449.25", "330.25", ".22787648783853717")
        );
    }

    private static Cenario c(String nome, String a, String b, String o, String custo,
                             String bruto, String liquido, String resultado, String margem) {
        return new Cenario(nome, entrada(a, b, o, "0", custo, "1", "1"),
                bd(bruto), bd(liquido), bd(resultado), bd(margem));
    }

    private static CalculoEntrada entrada(String a, String b, String o, String d,
                                          String custo, String quantidade, String cotacao) {
        return new CalculoEntrada(bd(a), bd(b), bd(o), bd(d), bd(custo), bd(quantidade), bd(cotacao));
    }

    private static BigDecimal bd(String value) {
        return new BigDecimal(value);
    }

    private static void assertProximo(BigDecimal esperado, BigDecimal atual) {
        assertTrue(esperado.subtract(atual).abs().compareTo(new BigDecimal("0.0000001")) <= 0,
                () -> "Esperado " + esperado + ", obtido " + atual);
    }

    private static final class Cenario {
        private final String nome;
        private final CalculoEntrada entrada;
        private final BigDecimal receitaBruta;
        private final BigDecimal receitaLiquida;
        private final BigDecimal resultado;
        private final BigDecimal margem;

        private Cenario(String nome, CalculoEntrada entrada, BigDecimal receitaBruta,
                        BigDecimal receitaLiquida, BigDecimal resultado, BigDecimal margem) {
            this.nome = nome;
            this.entrada = entrada;
            this.receitaBruta = receitaBruta;
            this.receitaLiquida = receitaLiquida;
            this.resultado = resultado;
            this.margem = margem;
        }

        @Override
        public String toString() {
            return nome;
        }
    }
}
