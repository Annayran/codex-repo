package br.com.zaya.teleworld.flow.model.service;

import java.math.BigDecimal;

/** Regras de sinalizacao e conclusao da revisao de produtos. */
public class RevisaoProdutoService {
    private final LiberacaoProcessoService.Sinalizador sinalizador;

    public RevisaoProdutoService(LiberacaoProcessoService.Sinalizador sinalizador) {
        this.sinalizador = sinalizador;
    }

    public boolean possuiVinculo(Object instancia) {
        return instancia != null && !instancia.toString().trim().isEmpty();
    }

    public static boolean deveValidarConfirmacao(boolean confirmando, String statusAnterior, String statusAtual) {
        return confirmando && "L".equals(statusAtual) && !"L".equals(statusAnterior);
    }

    public void sinalizarInclusao(Object instancia) throws Exception {
        if (!possuiVinculo(instancia)) {
            RotinaLog.detalhe("sinal de inclusao ignorado: produto sem vinculo com o fluxo");
            return;
        }
        final String id;
        try {
            BigDecimal numero = new BigDecimal(instancia.toString().trim());
            if (numero.signum() <= 0) {
                throw new IllegalArgumentException("ID deve ser positivo");
            }
            id = numero.toBigIntegerExact().toString();
        } catch (IllegalArgumentException | ArithmeticException e) {
            throw new RotinaLog.MensagemCliente("Não foi possível comunicar a inclusão do produto: "
                    + "o vínculo com o fluxo de revisão é inválido. Solicite ao responsável a correção do vínculo no cadastro do produto.");
        }
        sinalizador.enviar("br.zaya.aguardarNF", id);
    }

    public void validarConfirmacao(BigDecimal nota, BigDecimal produto,
                                   Object instancia, String revisado) throws Exception {
        if (possuiVinculo(instancia) && !"S".equals(revisado)) {
            throw new RotinaLog.MensagemCliente("Não foi possível confirmar a nota " + nota
                    + ": o produto " + produto + " possui revisão pendente. "
                    + "Aguarde a conclusão da revisão pelo responsável e tente confirmar novamente.");
        }
        RotinaLog.detalhe("revisao sem bloqueio nota=" + nota + " produto=" + produto
                + " motivo=" + (possuiVinculo(instancia) ? "revisado" : "sem_vinculo"));
    }
}
