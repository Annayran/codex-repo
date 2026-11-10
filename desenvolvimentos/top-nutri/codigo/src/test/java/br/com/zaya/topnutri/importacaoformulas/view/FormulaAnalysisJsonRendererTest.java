package br.com.zaya.topnutri.importacaoformulas.view;

import br.com.zaya.topnutri.importacaoformulas.controller.FormulaImportAnalysisController;
import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaAnalysis;
import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaImportSimulation;
import br.com.zaya.topnutri.importacaoformulas.model.dto.ProductImportSimulation;
import br.com.zaya.topnutri.importacaoformulas.model.dto.SimulatedMaterial;
import br.com.zaya.topnutri.importacaoformulas.model.dto.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FormulaAnalysisJsonRendererTest {
    @Test
    void shouldPreserveDecimalScaleAndEscapeText() {
        String text = "0;000022;FÓRMULA \"TESTE\";25.000000;@none;\r\n"
                + "1;000022;000080;0.003226;\r\n"
                + "1;000022;000065;24.996774;\r\n";
        FormulaAnalysis analysis = new FormulaImportAnalysisController()
                .analyze(text.getBytes(StandardCharsets.ISO_8859_1));

        String json = new FormulaAnalysisJsonRenderer().render("formula.txt", analysis);

        assertTrue(json.contains("\"outputQuantity\":\"25.000000\""));
        assertTrue(json.contains("\"quantity\":\"0.003226\""));
        assertTrue(json.contains("F\\u00d3RMULA \\\"TESTE\\\""));
        assertTrue(json.contains("\"componentTotal\":\"25.000000\""));
        assertTrue(json.contains("\"quantityDifference\":\"0.000000\""));
        assertTrue(json.contains("\"componentCount\":2"));
    }

    @Test
    void shouldExposeOneMillionthDifferenceWithoutInvalidatingFile() {
        String text = "0;000022;TESTE;30.000000;@none;\n"
                + "1;000022;000065;29.999999;";
        FormulaAnalysis analysis = new FormulaImportAnalysisController()
                .analyze(text.getBytes(StandardCharsets.ISO_8859_1));
        String json = new FormulaAnalysisJsonRenderer().render("formula.txt", analysis);
        assertTrue(json.contains("\"componentTotal\":\"29.999999\""));
        assertTrue(json.contains("\"quantityDifference\":\"-0.000001\""));
        assertTrue(analysis.getResult().isValid());
    }

    @Test
    void shouldRenderReadOnlyPaAndMicroSimulation() {
        String text = "0;000022;TOPNUTRIFÓS EQUINOS;25.000000;@none;\n"
                + "1;000022;000065;24.000000;\n"
                + "1;000022;000080;1.000000;";
        FormulaAnalysis analysis = new FormulaImportAnalysisController()
                .analyze(text.getBytes(StandardCharsets.ISO_8859_1));
        SimulatedMaterial macro = new SimulatedMaterial(
                "MACRO", "000065", "CALCÁRIO BRANCO", new BigDecimal("24.000000"),
                new BigDecimal("24.000000"), "KG", "TXT Optimal");
        SimulatedMaterial pi = new SimulatedMaterial(
                "PI_MICRO", "20022", "BAT. MICRO", new BigDecimal("1.000000"),
                null, "KG", "Calculado dos micros");
        SimulatedMaterial micro = new SimulatedMaterial(
                "MICRO", "000080", "IODATO", BigDecimal.ONE,
                new BigDecimal("1.000000"), "KG", "TXT normalizado");
        ProductImportSimulation product = new ProductImportSimulation(
                1, "000022", "TOPNUTRIFÓS EQUINOS", new BigDecimal("25.000000"),
                "20022", "BAT. MICRO", new BigDecimal("1.000000"),
                Arrays.asList(macro, pi), Collections.singletonList(micro));
        FormulaImportSimulation simulation = new FormulaImportSimulation(
                Collections.singletonList(product), Collections.emptyList());

        String json = new FormulaAnalysisJsonRenderer().render(
                "formula.txt", analysis, simulation);

        assertTrue(json.contains("\"simulationAvailable\":true"));
        assertTrue(json.contains("\"paProcessCode\":\"1\""));
        assertTrue(json.contains("\"microProcessCode\":\"6\""));
        assertTrue(json.contains("\"role\":\"PI_MICRO\""));
        assertTrue(json.contains("\"sourceQuantity\":\"1.000000\""));
    }

    @Test
    void shouldBlockConfirmationWhenSimulationHasPendingIssue() {
        String text = "0;000022;TESTE;1.000000;@none;\n"
                + "1;000022;000065;1.000000;";
        FormulaAnalysis analysis = new FormulaImportAnalysisController()
                .analyze(text.getBytes(StandardCharsets.ISO_8859_1));
        FormulaImportSimulation simulation = new FormulaImportSimulation(
                Collections.emptyList(), Collections.singletonList(
                        new ValidationIssue(1, "PACKAGING_NOT_FOUND", "Embalagens ausentes.")));

        String json = new FormulaAnalysisJsonRenderer().render(
                "formula.txt", analysis, simulation);

        assertTrue(json.contains("\"valid\":false"));
        assertTrue(json.contains("\"code\":\"PACKAGING_NOT_FOUND\""));
    }
}
