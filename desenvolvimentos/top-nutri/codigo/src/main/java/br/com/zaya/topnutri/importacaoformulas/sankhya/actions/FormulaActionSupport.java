package br.com.zaya.topnutri.importacaoformulas.sankhya.actions;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;

import java.util.Base64;

final class FormulaActionSupport {
    static final String RESULT_PREFIX = "TPN_RESULT:";

    private FormulaActionSupport() {
    }

    static String requiredString(ContextoAcao context, String name) throws Exception {
        Object value = context.getParam(name);
        String text = value == null ? "" : value.toString().trim();
        if (text.isEmpty()) {
            String message = "Parâmetro obrigatório não informado: " + name + ".";
            context.mostraErro(message);
            throw new IllegalArgumentException(message);
        }
        return text;
    }

    static byte[] decodeFile(ContextoAcao context) throws Exception {
        String encoded = requiredString(context, "TPN_ARQUIVO_BASE64");
        try {
            byte[] content = Base64.getDecoder().decode(encoded);
            if (content.length == 0) {
                context.mostraErro("O arquivo TXT está vazio.");
                throw new IllegalArgumentException("O arquivo TXT está vazio.");
            }
            return content;
        } catch (IllegalArgumentException exception) {
            context.mostraErro("O conteúdo recebido não é um Base64 válido.");
            throw exception;
        }
    }

    static String fileName(ContextoAcao context) throws Exception {
        String fileName = requiredString(context, "TPN_ARQUIVO_NOME");
        if (!fileName.toLowerCase(java.util.Locale.ROOT).endsWith(".txt")) {
            context.mostraErro("Selecione um arquivo com extensão .txt.");
            throw new IllegalArgumentException("Extensão de arquivo inválida.");
        }
        return fileName;
    }
}
