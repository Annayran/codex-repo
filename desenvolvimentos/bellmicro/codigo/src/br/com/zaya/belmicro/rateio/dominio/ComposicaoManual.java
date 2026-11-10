package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;
import java.util.List;

public final class ComposicaoManual {
    private ComposicaoManual() { }

    public static List<DestinoPeso> validar(List<DestinoPeso> destinos) {
        if (destinos == null || destinos.isEmpty()) invalida();
        BigDecimal total = BigDecimal.ZERO;
        for (DestinoPeso destino : destinos) {
            if (destino == null || destino.getPeso().signum() <= 0
                    || destino.getPeso().stripTrailingZeros().scale() > 6) invalida();
            total = total.add(destino.getPeso());
        }
        if (total.compareTo(new BigDecimal("100")) != 0) invalida();
        return java.util.Collections.unmodifiableList(new java.util.ArrayList<DestinoPeso>(destinos));
    }

    private static void invalida() {
        throw new ErroRateio("COMPOSICAO_INVALIDA", "Percentuais positivos, ate seis casas e soma 100 sao obrigatorios.");
    }
}
