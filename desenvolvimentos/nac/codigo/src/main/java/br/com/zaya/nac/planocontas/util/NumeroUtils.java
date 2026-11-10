package br.com.zaya.nac.planocontas.util;

import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import java.math.BigDecimal;

public final class NumeroUtils {

    private NumeroUtils() {
    }

    public static BigDecimal paraBigDecimal(Object valor, String nomeCampo)
            throws RegraNegocioException {
        if (valor == null) {
            return null;
        }
        if (valor instanceof BigDecimal) {
            return (BigDecimal) valor;
        }
        if (valor instanceof Number) {
            return new BigDecimal(valor.toString());
        }

        String texto = valor.toString().trim();
        if (texto.isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException e) {
            throw new RegraNegocioException(
                    "O campo " + nomeCampo + " possui o valor não numérico '" + texto
                            + "'. Revise a configuração contábil da empresa.",
                    e);
        }
    }
}

