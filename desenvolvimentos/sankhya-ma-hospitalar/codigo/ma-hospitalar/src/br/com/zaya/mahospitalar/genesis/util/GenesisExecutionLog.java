package br.com.zaya.mahospitalar.genesis.util;

public final class GenesisExecutionLog implements GenesisDiagnosticLog {
    private final String execucao = java.util.UUID.randomUUID().toString();
    private final String fluxo;
    private final GenesisDiagnosticLog destino;
    private final long inicio = System.currentTimeMillis();
    private int sequencia;
    private int requisicoes;
    private String registro = "preparacao";
    private String controle;

    public GenesisExecutionLog(String fluxo, GenesisDiagnosticLog destino) {
        this.fluxo = fluxo;
        this.destino = destino;
    }

    public void registro(String registro) { this.registro = registro; }
    public String getExecucao() { return execucao; }
    public String getFluxo() { return fluxo; }
    public String getRegistro() { return registro; }
    public String getControle() { return controle; }
    public void controle(String controle) { this.controle=controle; }

    @Override public void info(String mensagem) {
        if (mensagem.startsWith("HTTP request: ")) requisicoes++;
        String linha = "[MAH-GENESIS][fluxo=" + fluxo + "][execucao=" + execucao +
                "][seq=" + (++sequencia) + "][http=" + requisicoes + "][" +
                GenesisSanitizer.sanitizeForLog(registro) + "] " + GenesisSanitizer.sanitizeForLog(mensagem);
        System.out.println(linha);
        if (destino != null) destino.info(linha);
    }

    public void finalizar() {
        registro = "resumo";
        info("Fim do processamento. requisicoes=" + requisicoes + ", duracaoMs=" +
                (System.currentTimeMillis() - inicio) + "; persistencia depende da transacao Sankhya.");
    }
}
