package br.com.zaya.topnutri.importacaoformulas.model.dto;

import java.util.Objects;

public final class ValidationIssue {
    private final int lineNumber;
    private final String code;
    private final String message;

    public ValidationIssue(int lineNumber, String code, String message) {
        this.lineNumber = lineNumber;
        this.code = Objects.requireNonNull(code, "code");
        this.message = Objects.requireNonNull(message, "message");
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
