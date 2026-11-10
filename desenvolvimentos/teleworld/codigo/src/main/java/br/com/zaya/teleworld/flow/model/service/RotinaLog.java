package br.com.zaya.teleworld.flow.model.service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Logs do servidor sem mensagens brutas de excecao ou dados do VO. */
public final class RotinaLog {
    private static final Logger LOG = Logger.getLogger("br.com.zaya.teleworld.flow");
    private static final ThreadLocal<String> REFERENCIA = new ThreadLocal<String>();

    private RotinaLog() { }

    public interface Acao {
        void executar() throws Exception;
    }

    public static final class MensagemCliente extends Exception {
        private static final long serialVersionUID = 1L;
        public MensagemCliente(String mensagem) { super(mensagem); }
    }

    public static void info(String mensagem) { LOG.info(contexto(mensagem)); }
    public static void detalhe(String mensagem) { LOG.fine(contexto(mensagem)); }

    private static String contexto(String mensagem) {
        return "referencia=" + REFERENCIA.get() + " " + mensagem;
    }

    public static void executar(String rotina, Acao acao) throws Exception {
        String referencia = UUID.randomUUID().toString();
        String anterior = REFERENCIA.get();
        REFERENCIA.set(referencia);
        detalhe("rotina=" + rotina + " referencia=" + referencia + " inicio");
        try {
            acao.executar();
            detalhe("rotina=" + rotina + " referencia=" + referencia + " chamada_concluida");
        } catch (MensagemCliente e) {
            LOG.warning("rotina=" + rotina + " referencia=" + referencia + " bloqueio=" + e.getMessage());
            throw e;
        } catch (Exception e) {
            // Nao registrar getMessage/toString da excecao: podem conter SQL ou credenciais.
            StringBuilder diagnostico = new StringBuilder("rotina=").append(rotina)
                    .append(" referencia=").append(referencia).append(" falha_tipo=")
                    .append(e.getClass().getName());
            for (StackTraceElement frame : e.getStackTrace()) {
                diagnostico.append("\n  em ").append(frame);
            }
            LOG.log(Level.SEVERE, diagnostico.toString());
            throw new MensagemCliente("Não foi possível concluir a operação neste momento. "
                    + "Entre em contato com o suporte e informe a referência " + referencia + ".");
        } finally {
            if (anterior == null) REFERENCIA.remove();
            else REFERENCIA.set(anterior);
        }
    }
}
