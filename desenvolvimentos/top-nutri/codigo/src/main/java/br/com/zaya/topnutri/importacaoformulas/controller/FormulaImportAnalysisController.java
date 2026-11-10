package br.com.zaya.topnutri.importacaoformulas.controller;

import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaAnalysis;
import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaParseResult;
import br.com.zaya.topnutri.importacaoformulas.model.service.FormulaBatchValidator;
import br.com.zaya.topnutri.importacaoformulas.model.service.ImportFileHasher;
import br.com.zaya.topnutri.importacaoformulas.model.service.OptimalFormulaParser;

import java.util.Objects;

public final class FormulaImportAnalysisController {
    private final OptimalFormulaParser parser;
    private final FormulaBatchValidator validator;
    private final ImportFileHasher hasher;

    public FormulaImportAnalysisController() {
        this(new OptimalFormulaParser(), new FormulaBatchValidator(), new ImportFileHasher());
    }

    FormulaImportAnalysisController(
            OptimalFormulaParser parser,
            FormulaBatchValidator validator,
            ImportFileHasher hasher) {
        this.parser = Objects.requireNonNull(parser, "parser");
        this.validator = Objects.requireNonNull(validator, "validator");
        this.hasher = Objects.requireNonNull(hasher, "hasher");
    }

    public FormulaAnalysis analyze(byte[] content) {
        Objects.requireNonNull(content, "content");
        FormulaParseResult parsed = parser.parse(content);
        FormulaParseResult validated = validator.validate(parsed);
        return new FormulaAnalysis(hasher.sha256(content), validated);
    }
}
