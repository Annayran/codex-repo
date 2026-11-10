package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class CalculadoraRateio {
    public ResultadoCalculo calcular(BigDecimal base, List<DestinoPeso> destinos, int codEmpOrigem) {
        if (codEmpOrigem <= 0) throw new ErroRateio("CADASTRO_INVALIDO", "Empresa de origem invalida.");
        if (base == null || base.signum() <= 0) {
            throw new ErroRateio("BASE_INCOMPLETA", "Base zero, negativa ou ausente.");
        }
        List<DestinoPeso> ordenados = AgrupadorAuxiliares.consolidarEOrdenar(destinos);
        List<BigDecimal> pesos = new ArrayList<BigDecimal>();
        BigDecimal soma = BigDecimal.ZERO;
        for (DestinoPeso destino : ordenados) {
            pesos.add(destino.getPeso());
            soma = soma.add(destino.getPeso());
        }
        List<BigDecimal> valores = DistribuidorProporcional.distribuir(base, pesos, 2);
        List<ParcelaSimulacao> parcelas = new ArrayList<ParcelaSimulacao>();
        for (int i = 0; i < ordenados.size(); i++) {
            DestinoPeso destino = ordenados.get(i);
            parcelas.add(new ParcelaSimulacao(destino,
                    destino.getPeso().divide(soma, 12, RoundingMode.HALF_EVEN),
                    valores.get(i), destino.getCodEmp() == codEmpOrigem));
        }
        return new ResultadoCalculo(base.setScale(2, RoundingMode.UNNECESSARY), parcelas,
                AgrupadorAuxiliares.agrupar(parcelas));
    }
}
