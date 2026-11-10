package br.com.zaya.topnutri.importacaoformulas.model.dto;

import java.util.Objects;

public final class FormulaAnalysis {
    private final String sha256;
    private final FormulaParseResult result;

    public FormulaAnalysis(String sha256, FormulaParseResult result) {
        this.sha256 = Objects.requireNonNull(sha256, "sha256");
        this.result = Objects.requireNonNull(result, "result");
    }

    public String getSha256() {
        return sha256;
    }

    public FormulaParseResult getResult() {
        return result;
    }
}
