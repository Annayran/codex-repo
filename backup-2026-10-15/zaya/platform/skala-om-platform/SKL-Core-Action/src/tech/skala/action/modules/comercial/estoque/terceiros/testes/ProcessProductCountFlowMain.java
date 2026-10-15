package tech.skala.action.modules.comercial.estoque.terceiros.testes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Runner local para simular o fluxo de sequencia (2a ate 4a contagem)
 * sem depender de Jape, banco ou botao Sankhya.
 */
public class ProcessProductCountFlowMain {

    private static final BigDecimal INITIAL_SEQUENCE = new BigDecimal("2");
    private static final BigDecimal MAX_SEQUENCE = new BigDecimal("5");

    public static void main(String[] args) {
        Timestamp countDate = Timestamp.valueOf("2026-03-31 00:00:00");

        ProductInput p1 = new ProductInput(countDate, bd(1), bd(100), bd(200), " ", "UN", bd(300), "P");
        ProductInput p2 = new ProductInput(countDate, bd(1), bd(100), bd(200), " ", "UN", bd(300), "P");

        List<ProductInput> sameWorksheet = Arrays.asList(p1, p2);

        SequenceEngine engine = new SequenceEngine();

        for (int run = 1; run <= 5; run++) {
            System.out.println("=== Execucao " + run + " ===");
            for (ProductInput input : sameWorksheet) {
                try {
                    BigDecimal sequence = engine.resolveNextSequence(input);
                    System.out.println(input.shortLabel() + " -> SEQUENCIA " + sequence);
                } catch (IllegalStateException e) {
                    System.out.println(input.shortLabel() + " -> BLOQUEADO: " + e.getMessage());
                }
            }
        }
    }

    private static BigDecimal bd(int value) {
        return new BigDecimal(value);
    }

    private static final class SequenceEngine {
        private final Map<ProductKey, BigDecimal> currentMaxByKey = new HashMap<>();

        BigDecimal resolveNextSequence(ProductInput input) {
            ProductKey key = ProductKey.from(input);
            BigDecimal max = currentMaxByKey.getOrDefault(key, BigDecimal.ZERO);

            BigDecimal next = max.compareTo(BigDecimal.ZERO) <= 0 ? INITIAL_SEQUENCE : max.add(BigDecimal.ONE);
            if (next.compareTo(MAX_SEQUENCE) > 0) {
                throw new IllegalStateException("Ja existe 4a contagem para este produto/data.");
            }

            currentMaxByKey.put(key, next);
            return next;
        }
    }

    private static final class ProductInput {
        final Timestamp countDate;
        final BigDecimal companyId;
        final BigDecimal locationId;
        final BigDecimal productId;
        final String control;
        final String volumeId;
        final BigDecimal partnerId;
        final String type;

        ProductInput(Timestamp countDate, BigDecimal companyId, BigDecimal locationId, BigDecimal productId,
                     String control, String volumeId, BigDecimal partnerId, String type) {
            this.countDate = countDate;
            this.companyId = companyId;
            this.locationId = locationId;
            this.productId = productId;
            this.control = normalize(control);
            this.volumeId = normalize(volumeId);
            this.partnerId = partnerId;
            this.type = normalize(type);
        }

        String shortLabel() {
            return "PROD=" + productId + " PARC=" + partnerId + " CTRL='" + control + "'";
        }

        private static String normalize(String value) {
            if (value == null || value.trim().isEmpty()) {
                return " ";
            }
            return value;
        }
    }

    private static final class ProductKey {
        final Timestamp countDate;
        final BigDecimal companyId;
        final BigDecimal locationId;
        final BigDecimal productId;
        final String control;
        final String volumeId;
        final BigDecimal partnerId;
        final String type;

        private ProductKey(Timestamp countDate, BigDecimal companyId, BigDecimal locationId, BigDecimal productId,
                           String control, String volumeId, BigDecimal partnerId, String type) {
            this.countDate = countDate;
            this.companyId = companyId;
            this.locationId = locationId;
            this.productId = productId;
            this.control = control;
            this.volumeId = volumeId;
            this.partnerId = partnerId;
            this.type = type;
        }

        static ProductKey from(ProductInput input) {
            return new ProductKey(
                    input.countDate,
                    input.companyId,
                    input.locationId,
                    input.productId,
                    input.control,
                    input.volumeId,
                    input.partnerId,
                    input.type
            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProductKey)) return false;
            ProductKey that = (ProductKey) o;
            return Objects.equals(countDate, that.countDate)
                    && Objects.equals(companyId, that.companyId)
                    && Objects.equals(locationId, that.locationId)
                    && Objects.equals(productId, that.productId)
                    && Objects.equals(control, that.control)
                    && Objects.equals(volumeId, that.volumeId)
                    && Objects.equals(partnerId, that.partnerId)
                    && Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(countDate, companyId, locationId, productId, control, volumeId, partnerId, type);
        }
    }
}

