package br.com.zaya.teleworld.flow.model.service;

import java.math.BigDecimal;

/** Regras independentes de continuidade do processo comercial. */
public class LiberacaoProcessoService {
    public interface Sinalizador {
        void enviar(String sinal, String instancia) throws Exception;
    }

    private final Sinalizador sinalizador;

    public LiberacaoProcessoService(Sinalizador sinalizador) {
        this.sinalizador = sinalizador;
    }

    public void processar(BigDecimal nuNota, Object valorInstancia,
                          String solMin, String libProc, boolean possuiAnterior,
                          String solMinAnterior, String libProcAnterior) throws Exception {
        if (!"S".equals(solMin) && !"N".equals(libProc)) {
            RotinaLog.detalhe("sinais comerciais ignorados nota=" + nuNota + " motivo=condicoes_nao_atendidas");
            return;
        }
        if (!possuiAnterior) {
            throw new Exception("Nao foi possivel verificar os valores anteriores da nota " + nuNota + ".");
        }
        boolean enviarMinuta = "S".equals(solMin) && !"S".equals(solMinAnterior);
        boolean enviarLiberacao = "N".equals(libProc) && !"N".equals(libProcAnterior);
        if (!enviarMinuta && !enviarLiberacao) {
            RotinaLog.detalhe("sinais comerciais ignorados nota=" + nuNota + " motivo=sem_transicao");
            return;
        }
        String instancia = validarInstancia(nuNota, valorInstancia);
        RotinaLog.info("sinais comerciais nota=" + nuNota + " minuta=" + enviarMinuta + " liberacao=" + enviarLiberacao);
        if (enviarMinuta) {
            sinalizador.enviar("br.zaya.solMinComercial", instancia);
        }
        if (enviarLiberacao) {
            sinalizador.enviar("br.zaya.libProcComercial", instancia);
        }
    }

    private String validarInstancia(BigDecimal nuNota, Object valor) throws Exception {
        if (valor == null || valor.toString().trim().length() == 0) {
            throw new RotinaLog.MensagemCliente("A nota " + nuNota + " não possui vínculo com o processo comercial. "
                    + "Solicite ao responsável a correção do vínculo antes de continuar.");
        }
        try {
            BigDecimal id = new BigDecimal(valor.toString().trim());
            if (id.signum() <= 0) {
                throw new NumberFormatException("ID deve ser positivo");
            }
            return id.toBigIntegerExact().toString();
        } catch (IllegalArgumentException e) {
            throw vinculoInvalido(nuNota);
        } catch (ArithmeticException e) {
            throw vinculoInvalido(nuNota);
        }
    }

    private RotinaLog.MensagemCliente vinculoInvalido(BigDecimal nota) {
        return new RotinaLog.MensagemCliente("O vínculo da nota " + nota + " com o processo comercial é inválido. "
                + "Solicite ao responsável a correção do vínculo antes de continuar.");
    }
}
