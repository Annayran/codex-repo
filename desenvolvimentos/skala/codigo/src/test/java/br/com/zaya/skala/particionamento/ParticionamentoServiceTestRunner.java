package br.com.zaya.skala.particionamento;

import br.com.zaya.skala.particionamento.model.Carreta;
import br.com.zaya.skala.particionamento.model.ItemCarreta;
import br.com.zaya.skala.particionamento.model.ItemPedido;
import br.com.zaya.skala.particionamento.service.ParticionamentoService;
import br.com.zaya.skala.particionamento.util.NumericUtils;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
        cenario11PriorizadosEntramCompletosAntesDoMix();
        cenario12QuebrasFicamNosUltimosParticionamentos();
        cenario13FracaoPriorizadaFicaNaUltimaFase();
        cenario14FamiliaPrincipalTresSkusEDuasSecundarias();
        cenario15RankingDaFamiliaPrincipalPermaneceFixo();
        cenario16PickingsSaoUnificadosAteACapacidade();
        cenario17ProdutoSemFamiliaEhBloqueado();
        cenario18MesmoSkuSecundarioRecebeVariosPaletes();
        cenario19PrioridadeTambemRespeitaCincoSkus();
        cenario20MassaAleatoriaPreservaCapacidadeSkusETotal();
        cenario21CompactacaoReduzOepsInteirasIncompletas();
        cenario22Pedido5086134FechaNoMinimoTeorico();
        cenario23FamiliaPrincipalUsaQuantidadeDoPedido();
        cenario24PickingsPreenchemUltimaOepEContinuamNaProxima();
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

    private static void cenario11PriorizadosEntramCompletosAntesDoMix() {
        ItemPedido p1 = item("3108", 5.0, 120.0, 120.0);
        p1.setPriorizado(true);
        p1.setOrdemPrioridade(1);
        ItemPedido p2 = item("3110", 4.0, 120.0, 120.0);
        p2.setPriorizado(true);
        p2.setOrdemPrioridade(2);
        ItemPedido p3 = item("3113", 5.0, 120.0, 120.0);
        p3.setPriorizado(true);
        p3.setOrdemPrioridade(3);
        ItemPedido mixA = item("4155", 8.0, 120.0, 120.0);
        mixA.setGrupoMix("A");
        ItemPedido mixB = item("6656", 8.0, 120.0, 120.0);
        mixB.setGrupoMix("B");

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(
                Arrays.asList(mixA, p3, p1, mixB, p2),
                28.0
        );

        assertEquals(2, carretas.size(), "cenario11 qtd carretas");
        assertEquals("3108", carretas.get(0).getItens().get(0).getCodProd(), "cenario11 primeiro priorizado");
        assertEquals("3110", carretas.get(0).getItens().get(1).getCodProd(), "cenario11 segundo priorizado");
        assertEquals("3113", carretas.get(0).getItens().get(2).getCodProd(), "cenario11 terceiro priorizado");
        assertDouble(5.0, carretas.get(0).getItens().get(0).getQtdAlocada(), "cenario11 3108 completo");
        assertDouble(4.0, carretas.get(0).getItens().get(1).getQtdAlocada(), "cenario11 3110 completo");
        assertDouble(5.0, carretas.get(0).getItens().get(2).getQtdAlocada(), "cenario11 3113 completo");
        assertDouble(28.0, carretas.get(0).getPaletesUsados(), "cenario11 primeira carreta completa");
    }

    private static void cenario12QuebrasFicamNosUltimosParticionamentos() {
        ItemPedido a = item("PROD-A", 3.4, 100.0, 100.0);
        ItemPedido b = item("PROD-B", 2.3, 100.0, 100.0);
        ItemPedido c = item("PROD-C", 0.2, 100.0, 100.0);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(Arrays.asList(a, b, c), 3.0);

        assertEquals(2, carretas.size(), "cenario12 qtd carretas");
        assertEquals(2, carretas.get(0).getItens().size(), "cenario12 primeira carreta apenas paletes inteiros");
        assertDouble(2.0, find(carretas.get(0), "PROD-A").getQtdAlocada(), "cenario12 prod a paletes inteiros");
        assertDouble(1.0, find(carretas.get(0), "PROD-B").getQtdAlocada(), "cenario12 prod b primeiro palete");
        assertDouble(1.4, find(carretas.get(1), "PROD-A").getQtdAlocada(), "cenario12 segunda carreta completa saldo e quebra");
        assertDouble(1.3, find(carretas.get(1), "PROD-B").getQtdAlocada(), "cenario12 segunda carreta prod b com quebra");
        assertDouble(0.2, find(carretas.get(1), "PROD-C").getQtdAlocada(), "cenario12 quebra usa capacidade da ultima carreta");
        assertDouble(2.9, carretas.get(1).getPaletesUsados(), "cenario12 ultima carreta reaproveitada");
    }

    private static void cenario13FracaoPriorizadaFicaNaUltimaFase() {
        ItemPedido p1 = item("PRIO-A", 2.4, 100.0, 100.0);
        p1.setPriorizado(true);
        p1.setOrdemPrioridade(1);
        ItemPedido p2 = item("PRIO-B", 2.3, 100.0, 100.0);
        p2.setPriorizado(true);
        p2.setOrdemPrioridade(2);
        ItemPedido mix = item("MIX-A", 5.0, 100.0, 100.0);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(Arrays.asList(mix, p2, p1), 5.0);

        assertEquals(2, carretas.size(), "cenario13 qtd carretas");
        assertDouble(5.0, carretas.get(0).getPaletesUsados(), "cenario13 primeira carreta fechada");
        assertDouble(Math.rint(carretas.get(0).getPaletesUsados()), carretas.get(0).getPaletesUsados(), "cenario13 primeira carreta sem fracao");
        assertDouble(4.7, carretas.get(1).getPaletesUsados(), "cenario13 segunda carreta recebe as fracoes");
        assertDouble(0.4, find(carretas.get(1), "PRIO-A").getQtdAlocada(), "cenario13 fracao prio a na ultima carreta");
        assertDouble(0.3, find(carretas.get(1), "PRIO-B").getQtdAlocada(), "cenario13 fracao prio b na ultima carreta");
    }

    private static void cenario14FamiliaPrincipalTresSkusEDuasSecundarias() {
        ItemPedido a1 = itemFamilia("A1", "A", 20.0);
        ItemPedido a2 = itemFamilia("A2", "A", 18.0);
        ItemPedido a3 = itemFamilia("A3", "A", 16.0);
        ItemPedido a4 = itemFamilia("A4", "A", 10.0);
        ItemPedido b1 = itemFamilia("B1", "B", 20.0);
        ItemPedido c1 = itemFamilia("C1", "C", 16.0);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(
                Arrays.asList(a1, a2, a3, a4, b1, c1),
                28.0
        );

        Carreta primeira = carretas.get(0);
        assertDouble(28.0, primeira.getPaletesUsados(), "cenario14 primeira OEP fechada");
        assertEquals(5, primeira.getItens().size(), "cenario14 limite de cinco SKUs");
        find(primeira, "A1");
        find(primeira, "A2");
        find(primeira, "A3");
        find(primeira, "B1");
        find(primeira, "C1");
    }

    private static void cenario15RankingDaFamiliaPrincipalPermaneceFixo() {
        ItemPedido a1 = itemFamilia("A1", "A", 10.0);
        ItemPedido a2 = itemFamilia("A2", "A", 10.0);
        ItemPedido a3 = itemFamilia("A3", "A", 10.0);
        ItemPedido b1 = itemFamilia("B1", "B", 25.0);
        ItemPedido c1 = itemFamilia("C1", "C", 20.0);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(
                Arrays.asList(a1, a2, a3, b1, c1),
                20.0
        );

        assertEquals(5, carretas.get(0).getItens().size(), "cenario15 primeira OEP com ranking original");
        assertEquals(5, carretas.get(1).getItens().size(), "cenario15 segunda OEP mantem familia principal");
        find(carretas.get(1), "A1");
        find(carretas.get(1), "A2");
        find(carretas.get(1), "A3");
    }

    private static void cenario16PickingsSaoUnificadosAteACapacidade() {
        ItemPedido[] itens = new ItemPedido[12];
        for (int i = 0; i < itens.length; i++) {
            itens[i] = itemFamilia("P" + (i + 1), i < 6 ? "A" : "B", 0.5);
        }

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(Arrays.asList(itens), 28.0);

        assertEquals(1, carretas.size(), "cenario16 unifica os pickings em uma OEP");
        assertEquals(12, carretas.get(0).getItens().size(), "cenario16 mantem todos os SKUs de picking");
        assertDouble(6.0, carretas.get(0).getPaletesUsados(), "cenario16 total dos pickings");
    }

    private static void cenario17ProdutoSemFamiliaEhBloqueado() {
        ItemPedido semFamilia = item("SEM-FAMILIA", 2.0, 100.0, 100.0);
        semFamilia.setCodFamilia(null);
        try {
            new ParticionamentoService().particionarComConfiguracao(Arrays.asList(semFamilia), 28.0);
            throw new AssertionError("cenario17 deveria bloquear produto sem familia");
        } catch (IllegalStateException expected) {
            if (!expected.getMessage().contains("sem familia")) {
                throw expected;
            }
        }
    }

    private static void cenario18MesmoSkuSecundarioRecebeVariosPaletes() {
        ItemPedido a1 = itemFamilia("A1", "A", 10.0);
        ItemPedido a2 = itemFamilia("A2", "A", 10.0);
        ItemPedido a3 = itemFamilia("A3", "A", 10.0);
        ItemPedido b1 = itemFamilia("B1", "B", 20.0);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(
                Arrays.asList(a1, a2, a3, b1),
                28.0
        );

        if (find(carretas.get(0), "B1").getQtdAlocada() <= 1.0) {
            throw new AssertionError("cenario18 SKU secundario deveria receber dois ou mais paletes");
        }
    }

    private static void cenario19PrioridadeTambemRespeitaCincoSkus() {
        ItemPedido[] itens = new ItemPedido[7];
        for (int i = 0; i < itens.length; i++) {
            itens[i] = itemFamilia("PRIO-" + (i + 1), "A", 1.0);
            itens[i].setPriorizado(true);
            itens[i].setOrdemPrioridade(i + 1);
        }

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(Arrays.asList(itens), 28.0);
        assertEquals(2, carretas.size(), "cenario19 OEPs da prioridade");
        assertEquals(5, carretas.get(0).getItens().size(), "cenario19 primeira OEP com cinco SKUs");
        assertEquals(2, carretas.get(1).getItens().size(), "cenario19 segunda OEP com saldo priorizado");
    }

    private static void cenario20MassaAleatoriaPreservaCapacidadeSkusETotal() {
        Random random = new Random(20260805L);
        for (int rodada = 0; rodada < 100; rodada++) {
            List<ItemPedido> itens = new ArrayList<ItemPedido>();
            double totalEsperado = 0.0;
            for (int i = 0; i < 30; i++) {
                double paletes = (random.nextInt(129) + 1) / 10.0;
                ItemPedido item = itemFamilia("R" + rodada + "-" + i, "F" + (i % 6), paletes);
                item.setSequenciaOrigem(i + 1);
                itens.add(item);
                totalEsperado += paletes;
            }

            List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(itens, 28.0);
            double totalAtual = 0.0;
            for (Carreta carreta : carretas) {
                if (carreta.getPaletesUsados() > 28.0 + DELTA) {
                    throw new AssertionError("cenario20 excedeu capacidade na rodada " + rodada);
                }
                Set<String> skus = new HashSet<String>();
                for (ItemCarreta item : carreta.getItens()) {
                    skus.add(item.getCodProd());
                    totalAtual += item.getQtdAlocada();
                }
                if (skus.size() > 5 && !temAlocacaoFracionada(carreta)) {
                    throw new AssertionError("cenario20 excedeu cinco SKUs na rodada " + rodada);
                }
            }
            assertDouble(NumericUtils.roundTo6(totalEsperado), NumericUtils.roundTo6(totalAtual), "cenario20 total rodada " + rodada);
        }
    }

    private static void cenario21CompactacaoReduzOepsInteirasIncompletas() {
        double[] paletes = {
                1, 37, 22, 37, 22, 24, 50, 8,
                29, 10, 6, 45, 22, 6, 2, 49,
                41, 4, 33, 11, 23, 9, 41, 9,
                14, 40, 3, 10, 20, 41, 48, 41
        };
        List<ItemPedido> itens = new ArrayList<ItemPedido>();
        double totalEsperado = 0.0;
        for (int i = 0; i < paletes.length; i++) {
            ItemPedido item = itemFamilia("COMP-" + i, "F" + (i % 2), paletes[i]);
            item.setSequenciaOrigem(i + 1);
            itens.add(item);
            totalEsperado += paletes[i];
        }

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(itens, 28.0);

        assertEquals(29, carretas.size(), "cenario21 compactacao de OEPs");
        double totalAtual = 0.0;
        for (Carreta carreta : carretas) {
            if (carreta.getPaletesUsados() > 28.0 + DELTA) {
                throw new AssertionError("cenario21 excedeu capacidade na OEP " + carreta.getNumero());
            }
            Set<String> skus = new HashSet<String>();
            Set<String> skusFamiliaSecundaria = new HashSet<String>();
            for (ItemCarreta item : carreta.getItens()) {
                skus.add(item.getCodProd());
                int indiceProduto = Integer.parseInt(item.getCodProd().substring("COMP-".length()));
                if (indiceProduto % 2 == 0) {
                    skusFamiliaSecundaria.add(item.getCodProd());
                }
                totalAtual += item.getQtdAlocada();
            }
            if (skus.size() > 5) {
                throw new AssertionError("cenario21 excedeu cinco SKUs na OEP " + carreta.getNumero());
            }
            if (skusFamiliaSecundaria.size() > 2) {
                throw new AssertionError("cenario21 excedeu dois SKUs da familia secundaria na OEP " + carreta.getNumero());
            }
        }
        assertDouble(totalEsperado, totalAtual, "cenario21 preserva total de paletes");
    }

    private static void cenario22Pedido5086134FechaNoMinimoTeorico() {
        List<ItemPedido> itens = new ArrayList<ItemPedido>();
        adicionarItemFamilia(itens, 1, "3108", "10101", 24); adicionarItemFamilia(itens, 2, "3109", "10101", 28);
        adicionarItemFamilia(itens, 3, "3110", "10101", 28); adicionarItemFamilia(itens, 4, "3113", "10101", 28);
        adicionarItemFamilia(itens, 5, "4155", "10101", 28); adicionarItemFamilia(itens, 6, "4307", "10501", 2);
        adicionarItemFamilia(itens, 7, "4309", "10501", 2); adicionarItemFamilia(itens, 8, "4855", "10101", 28);
        adicionarItemFamilia(itens, 9, "6142", "10501", 3); adicionarItemFamilia(itens, 10, "6144", "10501", 2);
        adicionarItemFamilia(itens, 11, "6146", "10501", 3); adicionarItemFamilia(itens, 12, "6656", "10101", 28);
        adicionarItemFamilia(itens, 13, "6951", "10501", 2); adicionarItemFamilia(itens, 14, "8559", "10501", 3);
        adicionarItemFamilia(itens, 15, "9098", "10101", 28); adicionarItemFamilia(itens, 16, "10312", "10101", 28);
        adicionarItemFamilia(itens, 17, "13469", "10501", 2); adicionarItemFamilia(itens, 18, "14423", "10101", 28);
        adicionarItemFamilia(itens, 19, "14606", "10101", 56); adicionarItemFamilia(itens, 20, "14922", "10101", 28);
        adicionarItemFamilia(itens, 21, "15139", "10101", 28); adicionarItemFamilia(itens, 22, "15140", "10101", 28);
        adicionarItemFamilia(itens, 23, "15637", "10101", 28); adicionarItemFamilia(itens, 24, "15658", "10101", 28);
        adicionarItemFamilia(itens, 25, "15664", "10101", 28); adicionarItemFamilia(itens, 26, "20966", "10101", 28);
        adicionarItemFamilia(itens, 27, "25356", "10101", 42); adicionarItemFamilia(itens, 28, "25357", "10101", 42);
        adicionarItemFamilia(itens, 29, "25358", "10101", 42); adicionarItemFamilia(itens, 30, "26955", "10607", 2);
        adicionarItemFamilia(itens, 31, "26956", "10607", 4); adicionarItemFamilia(itens, 32, "26957", "10607", 3);
        adicionarItemFamilia(itens, 33, "26958", "10607", 5); adicionarItemFamilia(itens, 34, "26960", "10607", 3);
        adicionarItemFamilia(itens, 35, "26973", "10607", 3); adicionarItemFamilia(itens, 36, "26975", "10607", 3);
        adicionarItemFamilia(itens, 37, "26976", "10607", 4); adicionarItemFamilia(itens, 38, "26977", "10607", 4);
        adicionarItemFamilia(itens, 39, "27762", "10101", 42); adicionarItemFamilia(itens, 40, "27763", "10101", 28);
        adicionarItemFamilia(itens, 41, "27766", "10607", 6); adicionarItemFamilia(itens, 42, "27940", "11602", 3);
        adicionarItemFamilia(itens, 43, "27941", "11602", 4); adicionarItemFamilia(itens, 44, "27942", "11602", 3);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(itens, 28.0);
        assertEquals(29, carretas.size(), "cenario22 minimo teorico do pedido 5086134");
        Map<String, String> familiaPorProduto = new HashMap<String, String>();
        for (ItemPedido item : itens) {
            familiaPorProduto.put(item.getCodProd(), item.getCodFamilia());
        }
        double total = 0.0;
        for (int i = 0; i < carretas.size(); i++) {
            Carreta carreta = carretas.get(i);
            total += carreta.getPaletesUsados();
            assertEquals(i < 28 ? 28 : 6, (int) carreta.getPaletesUsados(), "cenario22 capacidade OEP " + (i + 1));
            if (carreta.getItens().size() > 5) {
                throw new AssertionError("cenario22 excedeu cinco SKUs na OEP " + (i + 1));
            }
            int principais = 0;
            int secundarios = 0;
            Set<String> familias = new HashSet<String>();
            Set<String> familiasSecundarias = new HashSet<String>();
            for (ItemCarreta item : carreta.getItens()) {
                String familia = familiaPorProduto.get(item.getCodProd());
                familias.add(familia);
                if ("10101".equals(familia)) {
                    principais++;
                } else {
                    secundarios++;
                    familiasSecundarias.add(familia);
                }
            }
            assertEquals(3, principais, "cenario22 tres SKUs principais na OEP " + (i + 1));
            if (secundarios > 2 || familias.size() < 2) {
                throw new AssertionError("cenario22 mix de familias invalido na OEP " + (i + 1));
            }
            if (secundarios == 2 && familiasSecundarias.size() != 2) {
                throw new AssertionError("cenario22 nao preferiu familias secundarias distintas na OEP " + (i + 1));
            }
        }
        assertDouble(790.0, total, "cenario22 preserva os 790 paletes");
    }

    private static void cenario23FamiliaPrincipalUsaQuantidadeDoPedido() {
        ItemPedido a1 = itemFamilia("A1", "A", 2.0);
        a1.setQtdOrigem(10000.0);
        a1.setSequenciaOrigem(1);
        ItemPedido a2 = itemFamilia("A2", "A", 2.0);
        a2.setQtdOrigem(10000.0);
        a2.setSequenciaOrigem(2);
        ItemPedido a3 = itemFamilia("A3", "A", 2.0);
        a3.setQtdOrigem(10000.0);
        a3.setSequenciaOrigem(3);
        ItemPedido b1 = itemFamilia("B1", "B", 20.0);
        b1.setQtdOrigem(2000.0);
        b1.setSequenciaOrigem(4);
        ItemPedido c1 = itemFamilia("C1", "C", 15.0);
        c1.setQtdOrigem(1500.0);
        c1.setSequenciaOrigem(5);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(
                Arrays.asList(a1, a2, a3, b1, c1),
                10.0
        );

        find(carretas.get(0), "A1");
        find(carretas.get(0), "A2");
        find(carretas.get(0), "A3");
    }

    private static void cenario24PickingsPreenchemUltimaOepEContinuamNaProxima() {
        List<ItemPedido> itens = new ArrayList<ItemPedido>();
        adicionarItemFamilia(itens, 1, "A1", "A", 10.0);
        adicionarItemFamilia(itens, 2, "A2", "A", 8.0);
        adicionarItemFamilia(itens, 3, "A3", "A", 5.0);
        adicionarItemFamilia(itens, 4, "B1", "B", 2.0);
        adicionarItemFamilia(itens, 5, "C1", "C", 2.0);
        adicionarItemFamilia(itens, 6, "P1", "A", 0.5);
        adicionarItemFamilia(itens, 7, "P2", "B", 0.5);
        adicionarItemFamilia(itens, 8, "P3", "C", 0.5);

        List<Carreta> carretas = new ParticionamentoService().particionarComConfiguracao(itens, 28.0);

        assertEquals(2, carretas.size(), "cenario24 quantidade de OEPs");
        assertDouble(28.0, carretas.get(0).getPaletesUsados(), "cenario24 completa a ultima OEP inteira");
        assertDouble(0.5, carretas.get(1).getPaletesUsados(), "cenario24 continua o picking na proxima OEP");
        if (carretas.get(0).getItens().size() <= 5) {
            throw new AssertionError("cenario24 deveria permitir SKUs adicionais apenas na fase de picking");
        }
    }

    private static void adicionarItemFamilia(
            List<ItemPedido> itens,
            int sequencia,
            String produto,
            String familia,
            double paletes
    ) {
        ItemPedido item = itemFamilia(produto, familia, paletes);
        item.setSequenciaOrigem(sequencia);
        itens.add(item);
    }

    private static List<Carreta> particionar(List<ItemPedido> itens, double capacidade) {
        return new ParticionamentoService().particionar(itens, capacidade);
    }

    private static ItemPedido item(String codProd, double qtdPalete, double undPorPalete, double cxPorPalete) {
        ItemPedido item = new ItemPedido(codProd, codProd, qtdPalete, undPorPalete, cxPorPalete);
        item.setQtdOrigem(qtdPalete * undPorPalete);
        item.setCodFamilia("FAM-GERAL");
        item.setDescFamilia("Familia geral");
        return item;
    }

    private static boolean temAlocacaoFracionada(Carreta carreta) {
        for (ItemCarreta item : carreta.getItens()) {
            if (Math.abs(item.getQtdAlocada() - Math.floor(item.getQtdAlocada() + DELTA)) > DELTA) {
                return true;
            }
        }
        return false;
    }

    private static ItemPedido itemFamilia(String codProd, String familia, double qtdPalete) {
        ItemPedido item = item(codProd, qtdPalete, 100.0, 100.0);
        item.setCodFamilia(familia);
        item.setDescFamilia("Familia " + familia);
        return item;
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
