package br.com.zaya.topnutri.importacaoformulas.model.service;

import br.com.zaya.topnutri.importacaoformulas.model.repository.MicroIntermediateProductResolver;

import java.math.BigDecimal;
import java.util.Optional;

/** Resolve o PI pelo prefixo funcional 200 seguido do codigo numerico do PA. */
public final class PrefixedMicroIntermediateProductResolver
        implements MicroIntermediateProductResolver {
    private static final String MICRO_PREFIX = "200";

    @Override
    public Optional<String> findExclusiveIntermediateProduct(String finishedProductCode) {
        if (finishedProductCode == null || finishedProductCode.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            BigDecimal numericCode = new BigDecimal(finishedProductCode.trim());
            if (numericCode.compareTo(BigDecimal.ZERO) <= 0
                    || numericCode.stripTrailingZeros().scale() > 0) {
                return Optional.empty();
            }
            return Optional.of(MICRO_PREFIX
                    + numericCode.toBigIntegerExact().toString());
        } catch (ArithmeticException | NumberFormatException exception) {
            return Optional.empty();
        }
    }
}
