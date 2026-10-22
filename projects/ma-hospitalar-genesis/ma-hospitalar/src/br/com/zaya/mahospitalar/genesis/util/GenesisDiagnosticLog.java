package br.com.zaya.mahospitalar.genesis.util;

public interface GenesisDiagnosticLog {
    GenesisDiagnosticLog NONE = new GenesisDiagnosticLog() {
        @Override public void info(String mensagem) {
            // Diagnostico desabilitado para chamadas que nao fornecem logger.
        }
    };

    void info(String mensagem);
}
