package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Exact largest remainders. Caller supplies stable destination order for ties. */
public final class DistribuidorProporcional {
    private DistribuidorProporcional() { }

    public static List<BigDecimal> distribuir(BigDecimal total, List<BigDecimal> pesos, int escala) {
        if (total == null || total.signum() < 0 || escala < 0 || pesos == null || pesos.isEmpty()) {
            throw new ErroRateio("BASE_INCOMPLETA", "Total, escala ou pesos invalidos.");
        }
        int escalaPesos = 0;
        for (BigDecimal peso : pesos) {
            if (peso == null || peso.signum() < 0) {
                throw new ErroRateio("BASE_INCOMPLETA", "Peso ausente ou negativo.");
            }
            escalaPesos = Math.max(escalaPesos, peso.stripTrailingZeros().scale());
        }
        List<BigInteger> inteiros = new ArrayList<BigInteger>();
        BigInteger soma = BigInteger.ZERO;
        for (BigDecimal peso : pesos) {
            BigInteger inteiro = peso.setScale(escalaPesos, RoundingMode.UNNECESSARY).unscaledValue();
            inteiros.add(inteiro);
            soma = soma.add(inteiro);
        }
        if (soma.signum() <= 0) throw new ErroRateio("BASE_INCOMPLETA", "Soma dos pesos nao positiva.");
        final BigInteger unidades;
        try {
            unidades = total.setScale(escala, RoundingMode.UNNECESSARY).unscaledValue();
        } catch (ArithmeticException e) {
            throw new ErroRateio("BASE_INCOMPLETA", "Total nao representavel na escala exigida.");
        }
        final List<BigInteger> restos = new ArrayList<BigInteger>();
        List<BigInteger> quocientes = new ArrayList<BigInteger>();
        List<Integer> ordem = new ArrayList<Integer>();
        BigInteger usado = BigInteger.ZERO;
        for (int i = 0; i < pesos.size(); i++) {
            BigInteger[] qr = unidades.multiply(inteiros.get(i)).divideAndRemainder(soma);
            quocientes.add(qr[0]);
            restos.add(qr[1]);
            usado = usado.add(qr[0]);
            ordem.add(i);
        }
        Collections.sort(ordem, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                int comparacao = restos.get(b).compareTo(restos.get(a));
                return comparacao != 0 ? comparacao : Integer.compare(a, b);
            }
        });
        int residual = unidades.subtract(usado).intValueExact();
        for (int i = 0; i < residual; i++) {
            int indice = ordem.get(i);
            quocientes.set(indice, quocientes.get(indice).add(BigInteger.ONE));
        }
        List<BigDecimal> resultado = new ArrayList<BigDecimal>();
        BigInteger conferencia = BigInteger.ZERO;
        for (BigInteger quociente : quocientes) {
            resultado.add(new BigDecimal(quociente, escala));
            conferencia = conferencia.add(quociente);
        }
        if (!conferencia.equals(unidades)) throw new IllegalStateException("Soma divergente.");
        return Collections.unmodifiableList(resultado);
    }
}
