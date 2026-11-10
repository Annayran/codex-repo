package br.com.zaya.nac.planocontas.exception;

public class RegraNegocioException extends Exception {

    public RegraNegocioException(String message) {
        super(message);
    }

    public RegraNegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}

