package br.com.zaya.teleworld.flow.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class RotinaLogTest {
    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger("br.com.zaya.teleworld.flow");
        List<LogRecord> registros = new ArrayList<LogRecord>();
        Handler handler = new Handler() {
            public void publish(LogRecord record) { registros.add(record); }
            public void flush() { }
            public void close() { }
        };
        Level anterior = logger.getLevel();
        boolean pais = logger.getUseParentHandlers();
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        try {
            String segredo = "CONTEUDO_TECNICO_NAO_EXIBIR";
            try {
                RotinaLog.executar("teste", () -> { throw new Exception(segredo); });
                throw new AssertionError("Falha tecnica foi ignorada");
            } catch (RotinaLog.MensagemCliente e) {
                if (e.getMessage().contains(segredo) || e.getCause() != null) {
                    throw new AssertionError("Detalhe tecnico exposto ao cliente");
                }
                String referencia = e.getMessage().substring(e.getMessage().indexOf("referência ") + 11).replace(".", "");
                if (registros.stream().noneMatch(r -> r.getLevel() == Level.SEVERE && r.getMessage().contains(referencia))) {
                    throw new AssertionError("Referencia sem diagnostico");
                }
            }
            for (LogRecord record : registros) {
                if (record.getMessage().contains(segredo) || record.getThrown() != null) {
                    throw new AssertionError("Excecao bruta no log");
                }
            }
            RotinaLog.MensagemCliente pendencia = new RotinaLog.MensagemCliente("Revisão pendente.");
            try {
                RotinaLog.executar("teste", () -> { throw pendencia; });
                throw new AssertionError("Bloqueio ignorado");
            } catch (RotinaLog.MensagemCliente e) {
                if (e != pendencia) throw new AssertionError("Mensagem funcional substituida");
            }
            final boolean[] executou = {false};
            RotinaLog.executar("teste", () -> executou[0] = true);
            if (!executou[0]) throw new AssertionError("Operacao valida nao executada");
            RotinaLog.info("fora da execucao");
            if (!registros.get(registros.size() - 1).getMessage().contains("referencia=null")) {
                throw new AssertionError("Referencia vazou entre operacoes");
            }
        } finally {
            logger.removeHandler(handler);
            logger.setLevel(anterior);
            logger.setUseParentHandlers(pais);
        }
        System.out.println("PASS: logs, referencia, mensagem funcional e protecao de detalhes tecnicos.");
    }
}
