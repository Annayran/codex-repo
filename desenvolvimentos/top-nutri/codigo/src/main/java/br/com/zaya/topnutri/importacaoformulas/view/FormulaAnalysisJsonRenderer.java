package br.com.zaya.topnutri.importacaoformulas.view;

import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaAnalysis;
import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaImportSimulation;
import br.com.zaya.topnutri.importacaoformulas.model.dto.ProductImportSimulation;
import br.com.zaya.topnutri.importacaoformulas.model.dto.SimulatedMaterial;
import br.com.zaya.topnutri.importacaoformulas.model.dto.ValidationIssue;
import br.com.zaya.topnutri.importacaoformulas.model.entity.FormulaComponent;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;

import java.util.Collections;

public final class FormulaAnalysisJsonRenderer {
    public String render(String fileName, FormulaAnalysis analysis) {
        return render(fileName, analysis, new FormulaImportSimulation(
                Collections.emptyList(), Collections.emptyList()));
    }

    public String render(
            String fileName,
            FormulaAnalysis analysis,
            FormulaImportSimulation simulation) {
        StringBuilder json = new StringBuilder(1024);
        int componentCount = 0;
        for (ProductionFormula formula : analysis.getResult().getFormulas()) {
            componentCount += formula.getComponents().size();
        }

        json.append('{');
        property(json, "fileName", fileName).append(',');
        property(json, "sha256", analysis.getSha256()).append(',');
        boolean valid = analysis.getResult().isValid() && simulation.isValid();
        json.append("\"valid\":").append(valid).append(',');
        json.append("\"simulationAvailable\":")
                .append(!simulation.getProducts().isEmpty()).append(',');
        json.append("\"formulaCount\":").append(analysis.getResult().getFormulas().size()).append(',');
        json.append("\"componentCount\":").append(componentCount).append(',');
        json.append("\"formulas\":[");
        for (int index = 0; index < analysis.getResult().getFormulas().size(); index++) {
            if (index > 0) {
                json.append(',');
            }
            appendFormula(json, analysis.getResult().getFormulas().get(index));
        }
        json.append("],\"simulations\":[");
        for (int index = 0; index < simulation.getProducts().size(); index++) {
            if (index > 0) {
                json.append(',');
            }
            appendSimulation(json, simulation.getProducts().get(index));
        }
        json.append("],\"issues\":[");
        int issueIndex = 0;
        for (ValidationIssue issue : analysis.getResult().getIssues()) {
            if (issueIndex++ > 0) {
                json.append(',');
            }
            appendIssue(json, issue);
        }
        for (ValidationIssue issue : simulation.getIssues()) {
            if (issueIndex++ > 0) {
                json.append(',');
            }
            appendIssue(json, issue);
        }
        return json.append("]}").toString();
    }

    private void appendSimulation(StringBuilder json, ProductImportSimulation simulation) {
        json.append('{');
        json.append("\"line\":").append(simulation.getLineNumber()).append(',');
        property(json, "finishedProductCode", simulation.getFinishedProductCode()).append(',');
        property(json, "finishedProductDescription", simulation.getFinishedProductDescription()).append(',');
        property(json, "outputQuantity", simulation.getOutputQuantity().toPlainString()).append(',');
        property(json, "paProcessCode", "1").append(',');
        property(json, "microProductCode", simulation.getMicroProductCode()).append(',');
        property(json, "microProductDescription", simulation.getMicroProductDescription()).append(',');
        property(json, "microQuantity", decimal(simulation.getMicroQuantity())).append(',');
        property(json, "microProcessCode", "6").append(',');
        appendMaterials(json, "finishedProductMaterials", simulation.getFinishedProductMaterials());
        json.append(',');
        appendMaterials(json, "microProductMaterials", simulation.getMicroProductMaterials());
        json.append('}');
    }

    private void appendMaterials(
            StringBuilder json,
            String name,
            java.util.List<SimulatedMaterial> materials) {
        json.append('"').append(escape(name)).append("\":[");
        for (int index = 0; index < materials.size(); index++) {
            if (index > 0) {
                json.append(',');
            }
            SimulatedMaterial material = materials.get(index);
            json.append('{');
            property(json, "role", material.getRole()).append(',');
            property(json, "productCode", material.getProductCode()).append(',');
            property(json, "description", material.getDescription()).append(',');
            property(json, "quantity", material.getQuantity().toPlainString()).append(',');
            property(json, "sourceQuantity", decimal(material.getSourceQuantity())).append(',');
            property(json, "volume", material.getVolume()).append(',');
            property(json, "source", material.getSource());
            json.append('}');
        }
        json.append(']');
    }

    private String decimal(java.math.BigDecimal value) {
        return value == null ? "" : value.toPlainString();
    }

    private void appendFormula(StringBuilder json, ProductionFormula formula) {
        json.append('{');
        json.append("\"line\":").append(formula.getLineNumber()).append(',');
        property(json, "code", formula.getFinishedProductCode()).append(',');
        property(json, "description", formula.getDescription()).append(',');
        property(json, "outputQuantity", formula.getOutputQuantity().toPlainString()).append(',');
        property(json, "componentTotal", formula.getComponentTotal().toPlainString()).append(',');
        property(json, "quantityDifference", formula.getQuantityDifference().toPlainString()).append(',');
        property(json, "ignoredField", formula.getIgnoredField()).append(',');
        json.append("\"components\":[");
        for (int index = 0; index < formula.getComponents().size(); index++) {
            if (index > 0) {
                json.append(',');
            }
            FormulaComponent component = formula.getComponents().get(index);
            json.append('{');
            json.append("\"line\":").append(component.getLineNumber()).append(',');
            property(json, "parentCode", component.getFinishedProductCode()).append(',');
            property(json, "componentCode", component.getComponentCode()).append(',');
            property(json, "quantity", component.getQuantity().toPlainString());
            json.append('}');
        }
        json.append("]}");
    }

    private void appendIssue(StringBuilder json, ValidationIssue issue) {
        json.append('{');
        json.append("\"line\":").append(issue.getLineNumber()).append(',');
        property(json, "code", issue.getCode()).append(',');
        property(json, "message", issue.getMessage());
        json.append('}');
    }

    private StringBuilder property(StringBuilder json, String name, String value) {
        return json.append('"').append(escape(name)).append("\":\"")
                .append(escape(value)).append('"');
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder escaped = new StringBuilder(value.length() + 16);
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            switch (character) {
                case '"':
                    escaped.append("\\\"");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '\b':
                    escaped.append("\\b");
                    break;
                case '\f':
                    escaped.append("\\f");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    if (character < 0x20 || character > 0x7e) {
                        escaped.append(String.format("\\u%04x", (int) character));
                    } else {
                        escaped.append(character);
                    }
            }
        }
        return escaped.toString();
    }
}
