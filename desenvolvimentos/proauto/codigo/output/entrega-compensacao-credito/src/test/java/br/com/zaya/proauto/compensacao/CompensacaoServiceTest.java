package br.com.zaya.proauto.compensacao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.junit.Test;
import static org.junit.Assert.*;

public class CompensacaoServiceTest {
    @Test public void tipoNativoExigeCodigoNumericoPositivo() {
        assertEquals(1100, RegraCompensacao.inteiro("TIPTITCREDCLI", bd("1100"), 1, Integer.MAX_VALUE));
        for (Object valor : new Object[] {null, "", "0", "-1", "10,20", "10-20", "1.5"}) {
            try { RegraCompensacao.inteiro("TIPTITCREDCLI", valor, 1, Integer.MAX_VALUE); fail(); }
            catch (IllegalArgumentException esperado) { /* nao ampliar selecao implicitamente */ }
        }
    }
    @Test public void incluiTrintaETrintaEUmDiasMasExcluiVinteENove() {
        Timestamp limite = RegraCompensacao.limiteInclusao(Timestamp.valueOf("2026-09-17 00:00:00"), 30);
        assertTrue(Timestamp.valueOf("2026-08-17 23:59:59").before(limite));
        assertTrue(Timestamp.valueOf("2026-08-18 23:59:59").before(limite));
        assertFalse(Timestamp.valueOf("2026-08-19 00:00:00").before(limite));
    }
    @Test public void corteAtravessaAnoBissexto() {
        assertEquals(Timestamp.valueOf("2024-03-01 00:00:00"),
                RegraCompensacao.limiteInclusao(Timestamp.valueOf("2024-03-30 00:00:00"), 30));
    }
    @Test public void validaLimitesEInteiroExato() {
        assertEquals(30, RegraCompensacao.inteiro("DIAS", "30", 0, 36500));
        for (String valor : new String[] {"-1", "30.1", "36501", "null"}) {
            try { RegraCompensacao.inteiro("DIAS", valor, 0, 36500); fail(); }
            catch (IllegalArgumentException esperado) { /* esperado */ }
        }
    }
    @Test public void limitaCompensacaoAoMenorSaldoSemArredondamento() throws Exception {
        for (BigDecimal[] saldos : new BigDecimal[][] {
                {bd("100.01"), bd("80.23")}, {bd("80.23"), bd("100.01")}, {bd("80.23"), bd("80.23")}}) {
            final int[] chamadas = {0};
            CompensacaoGateway gateway = (c, r, v) -> {
                chamadas[0]++;
                assertEquals(bd("1"), c); assertEquals(bd("2"), r);
                assertEquals(bd("80.23"), v);
                return bd("123");
            };
            assertEquals(bd("123"), new CompensacaoService(gateway).executar(
                    bd("1"), bd("2"), saldos[0], saldos[1]));
            assertEquals(1, chamadas[0]);
        }
    }
    @Test public void propagaFalhaParaRollback() {
        Exception causa = new Exception("falha controlada");
        CompensacaoService service = new CompensacaoService((c, r, v) -> { throw causa; });
        try { service.executar( bd("1"), bd("2"), bd("10"), bd("10")); fail(); }
        catch (Exception recebida) { assertSame(causa, recebida); }
    }
    @Test public void rejeitaRetornoSemAcerto() throws Exception {
        for (BigDecimal acerto : new BigDecimal[] {null, BigDecimal.ZERO, bd("-1")}) {
            try {
                new CompensacaoService((c, r, v) -> acerto).executar( bd("1"), bd("2"), bd("1"), bd("2"));
                fail();
            } catch (IllegalStateException esperado) { /* esperado */ }
        }
    }
    @Test public void rejeitaAutoCompensacaoESaldosInvalidos() throws Exception {
        CompensacaoService service = new CompensacaoService((c, r, v) -> { throw new AssertionError(); });
        try { service.executar( bd("1"), bd("1"), bd("1"), bd("1")); fail(); }
        catch (IllegalArgumentException esperado) { /* esperado */ }
        for (BigDecimal saldo : new BigDecimal[] {null, bd("0"), bd("-1")}) {
            try { service.executar( bd("1"), bd("2"), saldo, bd("1")); fail(); }
            catch (IllegalArgumentException esperado) { /* esperado */ }
        }
    }
    private static BigDecimal bd(String texto) { return new BigDecimal(texto); }
}
