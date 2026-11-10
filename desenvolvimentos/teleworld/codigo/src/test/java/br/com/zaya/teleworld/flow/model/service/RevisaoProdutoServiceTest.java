package br.com.zaya.teleworld.flow.model.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Matriz funcional de revisao e propagacao de falhas do sinal. */
public class RevisaoProdutoServiceTest {
    public static void main(String[] args) throws Exception {
        verificarGatilho(true, "A", "L", true);
        verificarGatilho(true, null, "L", true);
        verificarGatilho(false, "A", "L", false);
        verificarGatilho(true, "L", "L", false);
        verificarGatilho(false, "L", "L", false);
        verificarGatilho(true, "L", "A", false);
        verificarGatilho(true, "A", "A", false);
        verificarGatilho(true, "A", null, false);
        List<String> sinais = new ArrayList<String>();
        RevisaoProdutoService service = new RevisaoProdutoService((s, i) -> sinais.add(s + ":" + i));
        for (Object instancia : new Object[] {null, "", "  "}) {
            service.sinalizarInclusao(instancia);
            for (String estado : new String[] {"S", "N", "", null}) {
                service.validarConfirmacao(BigDecimal.ONE, BigDecimal.TEN, instancia, estado);
            }
        }
        if (!sinais.isEmpty()) throw new AssertionError("Sinal sem vinculo");
        service.sinalizarInclusao(new BigDecimal("123.0"));
        if (sinais.size() != 1 || !"br.zaya.aguardarNF:123".equals(sinais.get(0))) {
            throw new AssertionError("Destinatario ou sinal incorreto");
        }
        service.validarConfirmacao(BigDecimal.ONE, BigDecimal.TEN, "123", "S");
        for (String estado : new String[] {"N", "", null}) {
            try {
                service.validarConfirmacao(BigDecimal.ONE, BigDecimal.TEN, "123", estado);
                throw new AssertionError("Revisao pendente liberada");
            } catch (Exception expected) {
                if (!expected.getMessage().contains("produto 10")) throw expected;
            }
        }
        for (String id : new String[] {"abc", "0", "-1", "1.5"}) {
            try {
                service.sinalizarInclusao(id);
                throw new AssertionError("Instancia invalida aceita");
            } catch (Exception expected) {
                if (!(expected instanceof RotinaLog.MensagemCliente) || !expected.getMessage().contains("vínculo")) throw expected;
            }
        }
        if (sinais.size() != 1) throw new AssertionError("Sinal para instancia invalida");
        Exception falha = new Exception("Falha do Flow");
        RevisaoProdutoService indisponivel = new RevisaoProdutoService((s, i) -> { throw falha; });
        try {
            indisponivel.sinalizarInclusao("123");
            throw new AssertionError("Falha ignorada");
        } catch (Exception expected) {
            if (expected != falha) throw expected;
        }
        System.out.println("PASS: gatilho de confirmacao, revisao de produtos, vinculos, sinal e propagacao de falhas.");
        RotinaLogTest.main(args);
    }

    private static void verificarGatilho(boolean confirmando, String anterior, String atual, boolean esperado) {
        if (RevisaoProdutoService.deveValidarConfirmacao(confirmando, anterior, atual) != esperado) {
            throw new AssertionError("Gatilho incorreto: " + confirmando + ", " + anterior + " -> " + atual);
        }
    }
}
