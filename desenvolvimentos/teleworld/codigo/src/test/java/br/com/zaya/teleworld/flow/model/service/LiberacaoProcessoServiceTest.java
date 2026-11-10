package br.com.zaya.teleworld.flow.model.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Testes executaveis sem bibliotecas adicionais, integrados ao check Gradle. */
public class LiberacaoProcessoServiceTest {
    public static void main(String[] args) throws Exception {
        verificar("S", "S", "N", "S", "br.zaya.solMinComercial:123");
        verificar("N", "N", "N", "S", "br.zaya.libProcComercial:123");
        verificar("S", "N", "N", "S", "br.zaya.solMinComercial:123", "br.zaya.libProcComercial:123");
        verificar("S", "N", "S", "N");
        verificar("N", "S", "S", "N");
        verificar("S", null, null, null, "br.zaya.solMinComercial:123");
        verificar(null, "N", null, null, "br.zaya.libProcComercial:123");
        for (Object id : new Object[] {null, "", "abc", "0", "-1", "1.5"}) {
            List<String> enviados = new ArrayList<String>();
            LiberacaoProcessoService service = new LiberacaoProcessoService((s, i) -> enviados.add(s));
            boolean falhou = false;
            try { service.processar(BigDecimal.TEN, id, "S", "N", true, "N", "S"); }
            catch (Exception expected) { falhou = expected instanceof RotinaLog.MensagemCliente && expected.getMessage().contains("vínculo"); }
            if (!falhou || !enviados.isEmpty()) throw new AssertionError("ID invalido aceito");
        }
        Exception falha = new Exception("Falha simulada do Flow");
        LiberacaoProcessoService service = new LiberacaoProcessoService((s, i) -> { throw falha; });
        try {
            service.processar(BigDecimal.TEN, "123", "S", "S", true, "N", "S");
            throw new AssertionError("Falha do Flow ignorada");
        } catch (Exception expected) {
            if (expected != falha) throw new AssertionError("Excecao substituida");
        }
        try {
            service.processar(BigDecimal.TEN, "123", "S", "S", false, null, null);
            throw new AssertionError("VO anterior ausente aceito");
        } catch (Exception expected) {
            if (!expected.getMessage().contains("anteriores")) throw expected;
        }
        System.out.println("PASS: sinais independentes, simultaneos, transicoes, IDs e propagacao de falhas.");
    }

    private static void verificar(String sol, String lib, String solAntes, String libAntes,
                                  String... esperado) throws Exception {
        List<String> enviados = new ArrayList<String>();
        LiberacaoProcessoService service = new LiberacaoProcessoService((s, i) -> enviados.add(s + ":" + i));
        service.processar(BigDecimal.TEN, new BigDecimal("123.0"), sol, lib, true, solAntes, libAntes);
        if (!enviados.equals(Arrays.asList(esperado))) throw new AssertionError(enviados.toString());
    }
}
