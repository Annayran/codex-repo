package org.ImportadorPedido.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DecimalImportHelper {
    public static final int UNIT_PRICE_SCALE = 6;
    public static final int TOTAL_PRICE_SCALE = 2;
    public static final RoundingMode MONEY_ROUNDING_MODE = RoundingMode.HALF_UP;

    private DecimalImportHelper() {
    }

    public static BigDecimal normalizarUnitPrice(BigDecimal valor) {
        return valor == null ? null : valor.setScale(UNIT_PRICE_SCALE, MONEY_ROUNDING_MODE);
    }

    public static BigDecimal normalizarTotalPrice(BigDecimal valor) {
        return valor == null ? null : valor.setScale(TOTAL_PRICE_SCALE, MONEY_ROUNDING_MODE);
    }

    public static BigDecimal calcularTotalComparavel(BigDecimal qty, BigDecimal unitPrice) {
        if (qty == null || unitPrice == null) {
            return null;
        }
        return qty.multiply(unitPrice).setScale(TOTAL_PRICE_SCALE, MONEY_ROUNDING_MODE);
    }
}
