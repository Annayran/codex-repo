package br.com.zaya.mahospitalar.preco.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class PrecoDinamicoCalculator {
    private static final MathContext MC = new MathContext(24, RoundingMode.HALF_UP);
    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private static final BigDecimal ONE = BigDecimal.ONE;

    public CalculoResultado calcularDireto(CalculoEntrada entrada, BigDecimal receitaBrutaReais) {
        validarEntrada(entrada);
        if (receitaBrutaReais == null || receitaBrutaReais.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("A receita bruta deve ser positiva.");
        }

        BigDecimal receitaLiquida = entrada.getCoeficienteReceita()
                .multiply(receitaBrutaReais, MC)
                .add(entrada.getCreditoFixo(), MC);
        if (receitaLiquida.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("A receita liquida calculada deve ser positiva.");
        }

        BigDecimal deducoes = entrada.getCoeficienteOperacional()
                .multiply(receitaBrutaReais, MC)
                .add(entrada.getDespesaFixa(), MC);
        BigDecimal resultado = receitaLiquida
                .subtract(deducoes, MC)
                .subtract(entrada.getCustoGerencial(), MC);
        BigDecimal margem = resultado.divide(receitaLiquida, MC);
        BigDecimal precoUnitario = receitaBrutaReais
                .divide(entrada.getCotacaoMoeda(), MC)
                .divide(entrada.getQuantidade(), MC);

        return new CalculoResultado(receitaBrutaReais, receitaLiquida, deducoes,
                resultado, margem, precoUnitario);
    }

    public CalculoResultado calcularPrecoAlvo(CalculoEntrada entrada, BigDecimal margemAlvo) {
        validarEntrada(entrada);
        if (margemAlvo == null || margemAlvo.compareTo(ZERO) < 0 || margemAlvo.compareTo(ONE) >= 0) {
            throw new IllegalArgumentException("A margem alvo deve estar entre zero (inclusive) e um (exclusive).");
        }

        BigDecimal umMenosMargem = ONE.subtract(margemAlvo, MC);
        BigDecimal denominador = entrada.getCoeficienteReceita()
                .multiply(umMenosMargem, MC)
                .subtract(entrada.getCoeficienteOperacional(), MC);
        if (denominador.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("Nao existe preco positivo para a margem e os coeficientes informados.");
        }

        BigDecimal numerador = entrada.getCustoGerencial()
                .add(entrada.getDespesaFixa(), MC)
                .subtract(entrada.getCreditoFixo().multiply(umMenosMargem, MC), MC);
        BigDecimal receitaBrutaAlvo = numerador.divide(denominador, MC);
        if (receitaBrutaAlvo.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("O preco alvo calculado deve ser positivo.");
        }

        BigDecimal precoUnitarioMoeda = receitaBrutaAlvo
                .divide(entrada.getCotacaoMoeda(), MC)
                .divide(entrada.getQuantidade(), MC)
                .setScale(2, RoundingMode.CEILING);
        BigDecimal receitaBrutaArredondada = precoUnitarioMoeda
                .multiply(entrada.getQuantidade(), MC)
                .multiply(entrada.getCotacaoMoeda(), MC);

        CalculoResultado recalculado = calcularDireto(entrada, receitaBrutaArredondada);
        if (recalculado.getMargemContribuicao().compareTo(margemAlvo) < 0) {
            precoUnitarioMoeda = precoUnitarioMoeda.add(new BigDecimal("0.01"));
            receitaBrutaArredondada = precoUnitarioMoeda
                    .multiply(entrada.getQuantidade(), MC)
                    .multiply(entrada.getCotacaoMoeda(), MC);
            recalculado = calcularDireto(entrada, receitaBrutaArredondada);
        }

        return new CalculoResultado(
                recalculado.getReceitaBruta(),
                recalculado.getReceitaLiquida(),
                recalculado.getDeducoesOperacionais(),
                recalculado.getResultado(),
                recalculado.getMargemContribuicao(),
                precoUnitarioMoeda
        );
    }

    private void validarEntrada(CalculoEntrada entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("Os dados do calculo sao obrigatorios.");
        }
        requireNonNegative(entrada.getCoeficienteReceita(), "coeficiente da receita");
        requireNonNegative(entrada.getCreditoFixo(), "credito fixo");
        requireNonNegative(entrada.getCoeficienteOperacional(), "coeficiente operacional");
        requireNonNegative(entrada.getDespesaFixa(), "despesa fixa");
        requireNonNegative(entrada.getCustoGerencial(), "custo gerencial");
        if (entrada.getQuantidade() == null || entrada.getQuantidade().compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser positiva.");
        }
        if (entrada.getCotacaoMoeda() == null || entrada.getCotacaoMoeda().compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("A cotacao da moeda deve ser positiva.");
        }
    }

    private void requireNonNegative(BigDecimal value, String name) {
        if (value == null || value.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("O " + name + " deve ser informado e nao negativo.");
        }
    }
}
