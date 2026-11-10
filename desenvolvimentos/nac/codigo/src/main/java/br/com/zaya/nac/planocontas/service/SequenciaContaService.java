package br.com.zaya.nac.planocontas.service;

import br.com.zaya.nac.planocontas.exception.RegraNegocioException;

public final class SequenciaContaService {

    private static final int TAMANHO_SEQUENCIA = 6;

    public String proxima(String contaPai, String ultimaConta) throws RegraNegocioException {
        String prefixo = extrairPrefixo(contaPai);
        int sequencia = 1;

        if (ultimaConta != null && !ultimaConta.trim().isEmpty()) {
            if (!ultimaConta.startsWith(prefixo)) {
                throw new RegraNegocioException(
                        "A última conta encontrada não pertence ao grupo " + contaPai + ".");
            }
            String sufixo = ultimaConta.substring(prefixo.length());
            validarSufixo(sufixo, ultimaConta);
            sequencia = Integer.parseInt(sufixo) + 1;
        }

        if (sequencia > 999999) {
            throw new RegraNegocioException(
                    "A sequência do grupo " + contaPai + " atingiu o limite de seis dígitos.");
        }
        return prefixo + String.format("%0" + TAMANHO_SEQUENCIA + "d", sequencia);
    }

    public String extrairPrefixo(String contaPai) throws RegraNegocioException {
        if (contaPai == null || contaPai.length() <= TAMANHO_SEQUENCIA) {
            throw new RegraNegocioException("A máscara da conta sintética não é válida.");
        }
        String sufixo = contaPai.substring(contaPai.length() - TAMANHO_SEQUENCIA);
        if (!"000000".equals(sufixo)) {
            throw new RegraNegocioException(
                    "A conta sintética " + contaPai
                            + " deve terminar com seis zeros para gerar a sequência.");
        }
        return contaPai.substring(0, contaPai.length() - TAMANHO_SEQUENCIA);
    }

    private void validarSufixo(String sufixo, String conta) throws RegraNegocioException {
        if (sufixo.length() != TAMANHO_SEQUENCIA || !sufixo.matches("\\d{6}")) {
            throw new RegraNegocioException(
                    "A conta " + conta + " não respeita a sequência numérica de seis dígitos.");
        }
    }
}

