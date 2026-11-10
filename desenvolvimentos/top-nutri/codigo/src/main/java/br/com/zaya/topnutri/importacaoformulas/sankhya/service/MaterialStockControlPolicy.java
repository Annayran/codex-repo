package br.com.zaya.topnutri.importacaoformulas.sankhya.service;

/** Resolve os controles exigidos pela entidade ListaMateriaisAtividade. */
final class MaterialStockControlPolicy {
    private static final String NO_ADDITIONAL_CONTROL = "N";
    private static final String LOT_CONTROL = "L";
    private static final String LIST_CONTROL = "S";
    private static final String SUPPORTED_OTHER_CONTROL = "E";

    private static final String LITERAL = "L";
    private static final String AUTOMATIC = "I";
    private static final String INHERIT_FROM_FINISHED_PRODUCT = "H";

    Selection resolve(
            String finishedProductStockControl,
            String materialStockControl,
            String existingControlType,
            String existingControl) {
        String finishedType = normalize(finishedProductStockControl);
        String materialType = normalize(materialStockControl);
        String currentType = normalize(existingControlType);
        String currentControl = emptyToNull(existingControl);

        if (NO_ADDITIONAL_CONTROL.equals(materialType)) {
            return new Selection(LITERAL, null);
        }
        if (LOT_CONTROL.equals(materialType)) {
            return new Selection(AUTOMATIC, null);
        }
        if (LIST_CONTROL.equals(materialType)) {
            if (LITERAL.equals(currentType) && currentControl != null) {
                return new Selection(LITERAL, currentControl);
            }
            if (LIST_CONTROL.equals(finishedType)) {
                return new Selection(INHERIT_FROM_FINISHED_PRODUCT, null);
            }
            throw new IllegalStateException(
                    "Materia-prima controlada por Lista exige um Controle (MP) literal. "
                            + "O TXT nao informa esse valor e o PA nao permite heranca.");
        }
        if (SUPPORTED_OTHER_CONTROL.equals(materialType)) {
            return currentType == null
                    ? new Selection(AUTOMATIC, null)
                    : new Selection(currentType, currentControl);
        }
        throw new IllegalStateException(
                "Tipo de controle adicional da materia-prima nao suportado para inclusao automatica: "
                        + (materialType == null ? "nao informado" : materialType) + ".");
    }

    private String normalize(String value) {
        String normalized = emptyToNull(value);
        return normalized == null ? null : normalized.toUpperCase();
    }

    private String emptyToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    static final class Selection {
        private final String type;
        private final String control;

        private Selection(String type, String control) {
            this.type = type;
            this.control = control;
        }

        String getType() {
            return type;
        }

        String getControl() {
            return control;
        }
    }
}
