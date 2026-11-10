package br.com.zaya.topnutri.importacaoformulas.controller;

import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaAnalysis;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormulaImportAnalysisControllerTest {
    private final FormulaImportAnalysisController controller = new FormulaImportAnalysisController();

    @Test
    void shouldKeepDifferentFinishedProductBlocksSeparateIncludingLastBlock() {
        String text = "0;000022;PA UM;25.000000;@none;\r\n"
                + "1;000022;000065;10.000000;\r\n"
                + "1;000022;000080;15.000000;\r\n"
                + "0;000023;PA DOIS;30.000000;@none;\r\n"
                + "1;000023;000065;30.000000;";
        FormulaAnalysis analysis = controller.analyze(text.getBytes(StandardCharsets.ISO_8859_1));
        assertTrue(analysis.getResult().isValid());
        assertEquals(2, analysis.getResult().getFormulas().size());
        ProductionFormula first = analysis.getResult().getFormulas().get(0);
        ProductionFormula second = analysis.getResult().getFormulas().get(1);
        assertEquals("000022", first.getFinishedProductCode());
        assertEquals(2, first.getComponents().size());
        assertEquals("000023", second.getFinishedProductCode());
        assertEquals(1, second.getComponents().size());
        assertEquals("30.000000", second.getComponents().get(0).getQuantity().toPlainString());
    }

    @Test
    void shouldRejectWholeAnalysisWhenOnlySecondBlockExceedsFivePercent() {
        String text = "0;000022;PA UM;25.000000;@none;\n"
                + "1;000022;000065;25.000000;\n"
                + "0;000023;PA DOIS;30.000000;@none;\n"
                + "1;000023;000080;28.000000;";
        FormulaAnalysis analysis = controller.analyze(text.getBytes(StandardCharsets.ISO_8859_1));
        assertEquals(2, analysis.getResult().getFormulas().size());
        assertTrue(!analysis.getResult().isValid());
        assertEquals(1, analysis.getResult().getIssues().size());
        assertEquals("MASS_MISMATCH", analysis.getResult().getIssues().get(0).getCode());
        assertEquals(3, analysis.getResult().getIssues().get(0).getLineNumber());
    }

    @Test
    void shouldAcceptDifferencesUpToAndIncludingFivePercentInEitherDirection() {
        String text = "0;000022;ABAIXO;100.000000;@none;\n"
                + "1;000022;000001;95.000000;\n"
                + "0;000023;ACIMA;100.000000;@none;\n"
                + "1;000023;000001;105.000000;";
        FormulaAnalysis analysis = controller.analyze(text.getBytes(StandardCharsets.UTF_8));
        assertTrue(analysis.getResult().isValid());
        assertEquals(0, analysis.getResult().getIssues().size());
    }

    @Test
    void shouldRejectDifferencesAboveFivePercentInEitherDirection() {
        String text = "0;000022;ABAIXO;100.000000;@none;\n"
                + "1;000022;000001;94.999999;\n"
                + "0;000023;ACIMA;100.000000;@none;\n"
                + "1;000023;000001;105.000001;";
        FormulaAnalysis analysis = controller.analyze(text.getBytes(StandardCharsets.UTF_8));
        assertEquals(2, analysis.getResult().getIssues().size());
        assertTrue(!analysis.getResult().isValid());
    }

    @Test
    void shouldReadUtf8AccentsAndKeepIso88591Compatibility() {
        String text = "0;000022;TOPNUTRIFÓS EQUINOS;25.000000;@none;\n"
                + "1;000022;000001;25.000000;";
        FormulaAnalysis utf8 = controller.analyze(text.getBytes(StandardCharsets.UTF_8));
        FormulaAnalysis latin1 = controller.analyze(text.getBytes(StandardCharsets.ISO_8859_1));
        assertEquals("TOPNUTRIFÓS EQUINOS", utf8.getResult().getFormulas().get(0).getDescription());
        assertEquals("TOPNUTRIFÓS EQUINOS", latin1.getResult().getFormulas().get(0).getDescription());
    }

    @Test
    void shouldAcceptUtf8Bom() {
        String text = "\ufeff0;000022;FÓRMULA;25.000000;@none;\n"
                + "1;000022;000001;25.000000;";
        FormulaAnalysis analysis = controller.analyze(text.getBytes(StandardCharsets.UTF_8));
        assertTrue(analysis.getResult().isValid());
        assertEquals("FÓRMULA", analysis.getResult().getFormulas().get(0).getDescription());
    }

    @Test
    void shouldAnalyzeValidIso88591FileAndPreserveCodes() {
        String text = "0;000022;FÓRMULA TESTE;25.000000;@none;\r\n"
                + "1;000022;000001;10.000000;\r\n"
                + "1;000022;000002;15.000000;\r\n";

        FormulaAnalysis analysis = controller.analyze(text.getBytes(StandardCharsets.ISO_8859_1));

        assertTrue(analysis.getResult().isValid());
        assertEquals(64, analysis.getSha256().length());
        assertEquals(1, analysis.getResult().getFormulas().size());
        ProductionFormula formula = analysis.getResult().getFormulas().get(0);
        assertEquals("000022", formula.getFinishedProductCode());
        assertEquals("FÓRMULA TESTE", formula.getDescription());
        assertEquals("@none", formula.getIgnoredField());
        assertEquals("000001", formula.getComponents().get(0).getComponentCode());
    }

    @Test
    void shouldReportAllStructuralAndMassProblems() {
        String text = "1;000022;000001;10.000000;\r\n"
                + "0;000022;TESTE;25.000000;@none;\r\n"
                + "1;999999;000002;15.000000;\r\n";

        FormulaAnalysis analysis = controller.analyze(text.getBytes(StandardCharsets.ISO_8859_1));

        assertEquals(3, analysis.getResult().getIssues().size());
        assertTrue(analysis.getResult().getIssues().stream()
                .anyMatch(issue -> "COMPONENT_WITHOUT_HEADER".equals(issue.getCode())));
        assertTrue(analysis.getResult().getIssues().stream()
                .anyMatch(issue -> "PARENT_MISMATCH".equals(issue.getCode())));
        assertTrue(analysis.getResult().getIssues().stream()
                .anyMatch(issue -> "FORMULA_WITHOUT_COMPONENTS".equals(issue.getCode())));
    }

    @Test
    void shouldReportMassMismatch() {
        String text = "0;000022;TESTE;25.000000;@none;\r\n"
                + "1;000022;000001;10.000000;\r\n"
                + "1;000022;000001;10.000000;\r\n";

        FormulaAnalysis analysis = controller.analyze(text.getBytes(StandardCharsets.ISO_8859_1));

        assertEquals(1, analysis.getResult().getIssues().size());
        assertTrue(analysis.getResult().getIssues().stream()
                .anyMatch(issue -> "MASS_MISMATCH".equals(issue.getCode())));
    }
}
