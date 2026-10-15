package br.com.zaya.skala.particionamento.util;

public final class NumericUtils {
    private NumericUtils() {
    }

    public static double toDouble(Object value) {
        if (value == null) {
            return 0.0;
        }

        String text = value.toString().trim();
        if (text.isEmpty()) {
            return 0.0;
        }

        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException ignored) {
            try {
                String normalized = text.replace(".", "").replace(",", ".");
                return Double.parseDouble(normalized);
            } catch (NumberFormatException ignoredAgain) {
                return 0.0;
            }
        }
    }

    public static double roundTo6(double value) {
        return Math.round(value * 1_000_000.0) / 1_000_000.0;
    }
}

