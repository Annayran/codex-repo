package helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Valor total contratado para a atividade; nunca o preco do produto instalado. */
public final class ValorAtividade {
    private ValorAtividade() { }

    public static BigDecimal total(BigDecimal valor, BigDecimal quantidade) {
        if (quantidade == null || quantidade.signum() <= 0) {
            throw new IllegalArgumentException("QTDNEG deve ser positiva no apontamento.");
        }
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("Preencha VLRSERVICO (Vlr. Atividade) maior que zero no apontamento.");
        }
        BigDecimal total = valor.setScale(2, RoundingMode.HALF_UP);
        if (total.signum() <= 0) {
            throw new IllegalArgumentException("VLRSERVICO deve representar um valor monetario positivo.");
        }
        return total;
    }

    public static BigDecimal unitario(BigDecimal valor, BigDecimal quantidade) {
        return total(valor, quantidade).divide(quantidade, 8, RoundingMode.HALF_UP);
    }
}
