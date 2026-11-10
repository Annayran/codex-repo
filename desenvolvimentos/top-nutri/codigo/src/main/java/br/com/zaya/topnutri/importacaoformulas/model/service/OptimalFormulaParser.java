package br.com.zaya.topnutri.importacaoformulas.model.service;

import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaParseResult;
import br.com.zaya.topnutri.importacaoformulas.model.dto.ValidationIssue;
import br.com.zaya.topnutri.importacaoformulas.model.entity.FormulaComponent;
import br.com.zaya.topnutri.importacaoformulas.model.entity.ProductionFormula;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

public final class OptimalFormulaParser {
    public FormulaParseResult parse(byte[] content) {
        List<ProductionFormula> formulas = new ArrayList<ProductionFormula>();
        List<ValidationIssue> issues = new ArrayList<ValidationIssue>();
        FormulaBuilder current = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(content), detectCharset(content)))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 && !line.isEmpty() && line.charAt(0) == '\ufeff') {
                    line = line.substring(1);
                }
                if (line.trim().isEmpty()) {
                    issues.add(issue(lineNumber, "EMPTY_LINE", "Linha vazia não é permitida."));
                    continue;
                }

                String[] fields = line.split(";", -1);
                if (fields.length < 2) {
                    issues.add(issue(lineNumber, "INVALID_LAYOUT", "Linha sem campos separados por ponto e vírgula."));
                    continue;
                }

                if ("0".equals(fields[0])) {
                    if (current != null) {
                        formulas.add(current.build());
                    }
                    current = parseHeader(fields, lineNumber, issues);
                } else if ("1".equals(fields[0])) {
                    parseComponent(fields, lineNumber, current, issues);
                } else {
                    issues.add(issue(lineNumber, "UNKNOWN_RECORD_TYPE",
                            "Tipo de registro diferente de 0 ou 1."));
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Falha inesperada ao ler o conteúdo em memória.", exception);
        }

        if (current != null) {
            formulas.add(current.build());
        }
        if (formulas.isEmpty()) {
            issues.add(issue(0, "NO_FORMULA", "O arquivo não contém fórmula válida."));
        }
        return new FormulaParseResult(formulas, issues);
    }

    private java.nio.charset.Charset detectCharset(byte[] content) {
        try {
            CharBuffer ignored = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(content));
            return StandardCharsets.UTF_8;
        } catch (CharacterCodingException exception) {
            return StandardCharsets.ISO_8859_1;
        }
    }

    private FormulaBuilder parseHeader(
            String[] fields,
            int lineNumber,
            List<ValidationIssue> issues) {
        if (!hasExpectedLayout(fields, 6)) {
            issues.add(issue(lineNumber, "INVALID_HEADER_LAYOUT",
                    "Registro 0 deve possuir cinco campos e terminador ponto e vírgula."));
            return null;
        }
        if (isBlank(fields[1]) || isBlank(fields[2]) || isBlank(fields[3])) {
            issues.add(issue(lineNumber, "REQUIRED_HEADER_FIELD",
                    "Código, descrição e quantidade do produto acabado são obrigatórios."));
            return null;
        }
        BigDecimal quantity = parseQuantity(fields[3], lineNumber, issues);
        if (quantity == null) {
            return null;
        }
        return new FormulaBuilder(lineNumber, fields[1], fields[2], quantity, fields[4]);
    }

    private void parseComponent(
            String[] fields,
            int lineNumber,
            FormulaBuilder current,
            List<ValidationIssue> issues) {
        if (!hasExpectedLayout(fields, 5)) {
            issues.add(issue(lineNumber, "INVALID_COMPONENT_LAYOUT",
                    "Registro 1 deve possuir quatro campos e terminador ponto e vírgula."));
            return;
        }
        if (current == null) {
            issues.add(issue(lineNumber, "COMPONENT_WITHOUT_HEADER",
                    "Componente informado sem registro 0 válido anteriormente."));
            return;
        }
        if (isBlank(fields[1]) || isBlank(fields[2]) || isBlank(fields[3])) {
            issues.add(issue(lineNumber, "REQUIRED_COMPONENT_FIELD",
                    "Produto acabado, componente e quantidade são obrigatórios."));
            return;
        }
        if (!current.finishedProductCode.equals(fields[1])) {
            issues.add(issue(lineNumber, "PARENT_MISMATCH",
                    "Código do produto acabado não corresponde ao registro 0 aberto."));
            return;
        }
        BigDecimal quantity = parseQuantity(fields[3], lineNumber, issues);
        if (quantity != null) {
            current.components.add(new FormulaComponent(
                    lineNumber, fields[1], fields[2], quantity));
        }
    }

    private boolean hasExpectedLayout(String[] fields, int expectedLength) {
        return fields.length == expectedLength && fields[expectedLength - 1].isEmpty();
    }

    private BigDecimal parseQuantity(
            String value,
            int lineNumber,
            List<ValidationIssue> issues) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException exception) {
            issues.add(issue(lineNumber, "INVALID_QUANTITY",
                    "Quantidade deve usar formato decimal com ponto."));
            return null;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private ValidationIssue issue(int lineNumber, String code, String message) {
        return new ValidationIssue(lineNumber, code, message);
    }

    private static final class FormulaBuilder {
        private final int lineNumber;
        private final String finishedProductCode;
        private final String description;
        private final BigDecimal outputQuantity;
        private final String ignoredField;
        private final List<FormulaComponent> components = new ArrayList<FormulaComponent>();

        private FormulaBuilder(
                int lineNumber,
                String finishedProductCode,
                String description,
                BigDecimal outputQuantity,
                String ignoredField) {
            this.lineNumber = lineNumber;
            this.finishedProductCode = finishedProductCode;
            this.description = description;
            this.outputQuantity = outputQuantity;
            this.ignoredField = ignoredField;
        }

        private ProductionFormula build() {
            return new ProductionFormula(
                    lineNumber,
                    finishedProductCode,
                    description,
                    outputQuantity,
                    ignoredField,
                    components);
        }
    }
}
