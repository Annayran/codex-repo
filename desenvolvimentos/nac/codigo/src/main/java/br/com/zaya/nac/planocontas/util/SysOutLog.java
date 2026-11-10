package br.com.zaya.nac.planocontas.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class SysOutLog {

    private static final String PREFIXO = "[PLANO-DE-CONTAS]";
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private SysOutLog() {
    }

    public static void info(String idExecucao, String mensagem) {
        System.out.println(linha("INFO", idExecucao, mensagem));
    }

    public static void erro(String idExecucao, String mensagem, Throwable erro) {
        System.out.println(linha("ERRO", idExecucao, mensagem));
        if (erro != null) {
            StringWriter detalhes = new StringWriter();
            erro.printStackTrace(new PrintWriter(detalhes));
            System.out.println(PREFIXO + "[ERRO][" + idExecucao + "] " + detalhes);
        }
    }

    private static String linha(String nivel, String idExecucao, String mensagem) {
        return PREFIXO
                + "[" + nivel + "]"
                + "[" + LocalDateTime.now().format(FORMATO) + "]"
                + "[" + idExecucao + "] "
                + mensagem;
    }
}

