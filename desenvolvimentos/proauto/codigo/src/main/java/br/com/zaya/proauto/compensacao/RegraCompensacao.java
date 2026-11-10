package br.com.zaya.proauto.compensacao;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** Regras de validacao independentes do ERP. */
public final class RegraCompensacao {
    private RegraCompensacao() { }

    public static int inteiro(String nome, Object valor, int minimo, int maximo) {
        try {
            int numero = new BigDecimal(String.valueOf(valor)).intValueExact();
            if (numero < minimo || numero > maximo) {
                throw new ArithmeticException();
            }
            return numero;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(nome + ": inteiro entre " + minimo + " e " + maximo + ".");
        }
    }

    public static BigDecimal valor(BigDecimal credito, BigDecimal receita) {
        if (credito == null || receita == null || credito.signum() <= 0 || receita.signum() <= 0) {
            throw new IllegalArgumentException("Compensacao exige dois saldos positivos.");
        }
        return credito.min(receita);
    }

    /** Limite exclusivo: inclui o dia completo em que o credito completa N dias. */
    public static Timestamp limiteInclusao(Timestamp hoje, int dias) {
        if (hoje == null || dias < 0) { throw new IllegalArgumentException("Data/prazo invalidos."); }
        return Timestamp.valueOf(hoje.toLocalDateTime().toLocalDate().minusDays(dias).plusDays(1).atStartOfDay());
    }
}
