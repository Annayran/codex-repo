//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.zaya.utils;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class ToBigDecimal {
    private static final Logger LOG = Logger.getLogger(ToBigDecimal.class.getName());

    public BigDecimal toBigDecimal(Object valor) {
        if (valor == null) {
            return null;
        } else if (valor instanceof Number) {
            return new BigDecimal(((Number)valor).toString());
        } else if (valor instanceof String) {
            try {
                return new BigDecimal((String)valor);
            } catch (NumberFormatException var3) {
                LOG.warning(String.format("[TO_BIGDECIMAL_ERRO] Conversao invalida | valor=%s erro=%s", valor, var3.getMessage()));
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Converte valor para BigDecimal com limpeza de caracteres extras.
     * Remove pontos finais, espacos, e tenta converter.
     * Útil para valores que vêm com formatação estranha do webhook.
     */
    public BigDecimal toBigDecimalClean(Object valor) {
        if (valor == null) {
            return null;
        }

        String strValor;
        if (valor instanceof String) {
            strValor = ((String)valor).trim();
        } else if (valor instanceof Number) {
            strValor = ((Number)valor).toString();
        } else {
            strValor = valor.toString().trim();
        }

        // Remover pontos finais soltos (ex: "259065268." -> "259065268")
        if (strValor.endsWith(".")) {
            strValor = strValor.substring(0, strValor.length() - 1);
            LOG.fine(String.format("[TO_BIGDECIMAL_CLEAN] Removido ponto final | antes=%s depois=%s", valor, strValor));
        }

        // Remover espacos extras
        strValor = strValor.replaceAll("\\s+", "");

        // Tentar conversao
        try {
            return new BigDecimal(strValor);
        } catch (NumberFormatException e) {
            LOG.warning(String.format("[TO_BIGDECIMAL_CLEAN_ERRO] Conversao invalida apos limpeza | original=%s cleaned=%s erro=%s",
                    valor, strValor, e.getMessage()));
            return null;
        }
    }

    /**
     * Converte para BigDecimal com fallback para valor padrão se null.
     */
    public BigDecimal toBigDecimalOrDefault(Object valor, BigDecimal defaultValue) {
        BigDecimal result = toBigDecimal(valor);
        return result != null ? result : defaultValue;
    }

    /**
     * Converte para BigDecimal com fallback para valor padrão, com limpeza.
     */
    public BigDecimal toBigDecimalCleanOrDefault(Object valor, BigDecimal defaultValue) {
        BigDecimal result = toBigDecimalClean(valor);
        return result != null ? result : defaultValue;
    }
}
