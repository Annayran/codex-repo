package br.com.zaya.skala.particionamento;

import br.com.zaya.skala.particionamento.model.Carreta;
import br.com.zaya.skala.particionamento.model.ItemCarreta;
import br.com.zaya.skala.particionamento.model.ItemPedido;
import br.com.zaya.skala.particionamento.service.ParticionamentoService;
import br.com.zaya.skala.particionamento.util.NumericUtils;

import java.util.Arrays;
import java.util.List;

public class ParticionamentoServiceTestRunner {
    private static final double DELTA = 0.000001;

    public static void main(String[] args) {
        cenario1SimplesSemSplit();
        cenario2ComSplit();
        cenario3ComFracoes();
        cenario4FracoesCriamNovaCarreta();
        cenario5CapacidadeCustomizada();
        cenario6ValidacoesETolerancia();
        cenario7ConfiguracaoSelecionaItens();
        cenario8PrioridadeAntesDoGrupo();
        cenario9BalanceiaSkusNoMix();
        cenario10MixCxSempreInteiro();
        numericUtilsConverteValores();

        System.out.println("Todos os cenarios passaram.");
    }

    private static void cenario1SimplesSemSplit() {
        List<Carreta> carretas = particionar(Arrays.asList(
                item("PROD-A", 10.0, 100.0, 10.0),
                item("PROD-B", 8.0, 60.0, 6.0)
        ), 28.0);

        assertEquals(1, carretas.size(), "cenario1 qtd carretas");
        assertDouble(18.0, carretas.get(0).getPaletesUsados(), "cenario1 paletes usados");
        assertDouble(1000.0, carretas.get(0).getItens().get(0).getQtdUnidade(), "cenario1 produto A unidades");
        assertDouble(480.0, carretas.get(0).getItens().get(1).getQtdUnidade(), "cenario1 produto B unidades");
    }

    private static void cenario2ComSplit() {
        List<Carreta> carretas = particionar(Arrays.asList(
                item("PROD-A", 20.0, 100.0, 10.0),
                item("PROD-B", 15.0, 60.0, 6.0)
        ), 28.0);

        assertEquals(2, carretas.size(), "cenario2 qtd carretas");
        assertDouble(28.0, carretas.get(0).getPaletesUsados(), "cenario2 carreta1 paletes");
        assertDouble(7.0, carretas.get(1).getPaletesUsados(), "cenario2 carreta2 paletes");
        assertDouble(8.0, find(carretas.get(0), "PROD-B").getQtdAlocada(), "cenario2 prod b carreta1");
        assertDouble(7.0, find(carretas.get(1), "PROD-B").getQtdAlocada(), "cenario2 prod b carreta2");
        assertDouble(420.0, find(carretas.get(1), "PROD-B").getQtdUnidade(), "cenario2 prod b unidades carreta2");
    }

    private static void cenario3ComFracoes() {
        List<Carreta> carretas = particionar(Arrays.asList(
                item("PROD-A", 10.75, 100.0, 10.0),
                item("PROD-B", 4.20, 60.0, 6.0),
                item("PROD-C", 8.00, 80.0, 8.0)
        ), 28.0);

        assertEquals(1, carretas.size(), "cenario3 qtd carretas");
        assertDouble(22.95, carretas.get(0).getPaletesUsados(), "cenario3 paletes usados");
        assertEquals(5, carretas.get(0).getItens().size(), "cenario3 itens alocados");
    }

    private static void cenario4FracoesCriamNovaCarreta() {
        List<Carreta> carretas = particionar(Arrays.asList(
                item("PROD-A", 27.8, 100.0, 10.0),
                item("PROD-B", 0.5, 60.0, 6.0)
        ), 28.0);

        assertEquals(2, carretas.size(), "cenario4 qtd carretas");
        assertDouble(28.0, carretas.get(0).getPaletesUsados(), "cenario4 carreta1 paletes");
        assertDouble(0.3, carretas.get(1).getPaletesUsados(), "cenario4 carreta2 paletes");
        assertDouble(0.2, find(carretas.get(0), "PROD-B").getQtdAlocada(), "cenario4 prod b parte carreta1");
        assertDouble(0.3, find(carretas.get(1), "PROD-B").getQtdAlocada(), "cenario4 prod b parte carreta2");
    }

    private static void cenario5CapacidadeCustomizada() {
        List<Carreta> carretas = particionar(Arrays.asList(
                item("PROD-A", 20.0, 100.0, 10.0),
                item("PROD-B", 15.0, 60.0, 6.0)
        ), 16.0);

        assertEquals(3, carretas.size(), "cenario5 qtd carretas");
        assertDouble(16.0, carretas.get(0).getPaletesUsados(), "cenario5 carreta1");
        assertDouble(16.0, carretas.get(1).getPaletesUsados(), "cenario5 carreta2");
        assertDouble(3.0, carretas.get(2).getPaletesUsados(), "cenario5 carreta3");
    }

    private static void cenario6ValidacoesETolerancia() {
        List<Carreta> carretas = particionar(Arrays.asList(
                item("", 10.0, 100.0, 10.0),
                item("PROD-ZERO", 0.0, 100.0, 10.0),
                item("PROD-TOL", 0.0000005, 100.0, 10.0),
                item("PROD-OK", 1.0, 50.0, 5.0)
        ), 28.0);

        assertEquals(1, carretas.size(), "cenario6 qtd carretas");
        assertEquals(1, carretas.get(0).getItens().size(), "cenario6 qtd itens");
        assertDouble(1.0, carretas.get(0).getPaletesUsados(), "cenario6 paletes usados");

        try {
            particionar(Arrays.asList(item("PROD-A", 1.0, 1.0, 1.0)), 0.0);
            throw new AssertionError("cenario6 deveria rejeitar capacidade zero");
        } catch (IllegalArgumentException expected) {
            // esperado
        }
    }

    private static void numericUtilsConverteValores() {
        assertDouble(1234.56, NumericUtils.toDouble("1.234,56"), "numeric pt-br");
        assertDouble(1234.56, NumericUtils.toDouble("1234.56"), "numeric en-us");
        assertDouble(0.0, NumericUtils.toDouble(""), "numeric vazio");
        assertDouble(0.0, NumericUtils.toDouble(null), "numeric null");
    }

    private static void cenario7ConfiguracaoSelecionaItens() {
        ItemPedido a = item("PROD-A", 10.0, 100.0, 10.0);
        ItemPedido b = item("PROD-B", 8.0, 60.0, 6.0);
        ItemPedido c = item("PROD-C", 4.0, 50.0, 5.0);
        b.setSelecionado(false);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(Arrays.asList(a, b, c), 28.0);

        assertEquals(1, carretas.size(), "cenario7 qtd carretas");
        assertEquals(2, carretas.get(0).getItens().size(), "cenario7 itens selecionados");
        assertDouble(14.0, carretas.get(0).getPaletesUsados(), "cenario7 paletes usados");
        find(carretas.get(0), "PROD-A");
        find(carretas.get(0), "PROD-C");
    }

    private static void cenario8PrioridadeAntesDoGrupo() {
        ItemPedido a = item("PROD-A", 5.0, 100.0, 10.0);
        a.setGrupoMix("A");
        ItemPedido b = item("PROD-B", 5.0, 60.0, 6.0);
        b.setGrupoMix("B");
        b.setPriorizado(true);
        b.setOrdemPrioridade(1);
        ItemPedido c = item("PROD-C", 5.0, 50.0, 5.0);
        c.setGrupoMix("C");

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(Arrays.asList(a, b, c), 28.0);

        assertEquals(1, carretas.size(), "cenario8 qtd carretas");
        assertEquals("PROD-B", carretas.get(0).getItens().get(0).getCodProd(), "cenario8 prioridade primeiro");
    }

    private static void cenario9BalanceiaSkusNoMix() {
        ItemPedido a1 = item("PROD-A1", 10.0, 100.0, 10.0);
        a1.setGrupoMix("A");
        ItemPedido a2 = item("PROD-A2", 10.0, 100.0, 10.0);
        a2.setGrupoMix("A");
        ItemPedido b1 = item("PROD-B1", 10.0, 100.0, 10.0);
        b1.setGrupoMix("B");

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(Arrays.asList(a1, a2, b1), 20.0);

        assertEquals(2, carretas.size(), "cenario9 qtd carretas");
        assertEquals("PROD-A1", carretas.get(0).getItens().get(0).getCodProd(), "cenario9 primeiro item");
        assertEquals("PROD-A2", carretas.get(0).getItens().get(1).getCodProd(), "cenario9 segundo sku");
        assertEquals("PROD-B1", carretas.get(0).getItens().get(2).getCodProd(), "cenario9 terceiro sku");
        assertEquals(3, carretas.get(0).getItens().size(), "cenario9 mix na carreta cheia");
        assertDouble(20.0, carretas.get(0).getPaletesUsados(), "cenario9 carreta1 cheia");
    }

    private static void cenario10MixCxSempreInteiro() {
        ItemPedido a = item("PROD-A", 1224.0 / 720.0, 720.0, 720.0);
        a.setCodVol("CX");
        ItemPedido b = item("PROD-B", 144.0 / 720.0, 720.0, 720.0);
        b.setCodVol("CX");
        ItemPedido c = item("PROD-C", 72.0 / 720.0, 720.0, 720.0);
        c.setCodVol("CX");

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(Arrays.asList(a, b, c), 1.0);

        assertEquals(2, carretas.size(), "cenario10 qtd carretas");
        for (Carreta carreta : carretas) {
            for (ItemCarreta item : carreta.getItens()) {
                assertDouble(Math.rint(item.getQtdUnidade()), item.getQtdUnidade(), "cenario10 qtdneg inteiro " + item.getCodProd());
            }
        }
    }

    private static List<Carreta> particionar(List<ItemPedido> itens, double capacidade) {
        return new ParticionamentoService().particionar(itens, capacidade);
    }

    private static ItemPedido item(String codProd, double qtdPalete, double undPorPalete, double cxPorPalete) {
        return new ItemPedido(codProd, codProd, qtdPalete, undPorPalete, cxPorPalete);
    }

    private static ItemCarreta find(Carreta carreta, String codProd) {
        for (ItemCarreta item : carreta.getItens()) {
            if (codProd.equals(item.getCodProd())) {
                return item;
            }
        }
        throw new AssertionError("Item nao encontrado: " + codProd + " na carreta " + carreta.getNumero());
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + ". Esperado: " + expected + ", atual: " + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + ". Esperado: " + expected + ", atual: " + actual);
        }
    }

    private static void assertDouble(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > DELTA) {
            throw new AssertionError(message + ". Esperado: " + expected + ", atual: " + actual);
        }
    }
}
