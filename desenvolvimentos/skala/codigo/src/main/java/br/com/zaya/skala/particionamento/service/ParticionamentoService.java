package br.com.zaya.skala.particionamento.service;

import br.com.zaya.skala.particionamento.model.Carreta;
import br.com.zaya.skala.particionamento.model.ItemCarreta;
import br.com.zaya.skala.particionamento.model.ItemPedido;
import br.com.zaya.skala.particionamento.util.NumericUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParticionamentoService {
    public static final double TOL = 0.000001;
    private static final int MAX_SKUS_POR_OEP = 5;
    private static final int SKUS_FAMILIA_PRINCIPAL = 3;
    private static final int SKUS_OUTRAS_FAMILIAS = 2;

    public List<Carreta> particionar(List<ItemPedido> itens, double capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
        }

        if (itens == null || itens.isEmpty()) {
            return Collections.emptyList();
        }

        List<Carreta> carretas = new ArrayList<Carreta>();
        int[] carretaAtual = {0};
        double[] capacidadeLivre = {0.0};

        for (ItemPedido item : itens) {
            if (isInvalido(item)) {
                continue;
            }

            long parteInteira = (long) item.getQtdPalete();
            if (parteInteira > 0) {
                alocarNasCarretas(item, (double) parteInteira, capacidade, carretas, carretaAtual, capacidadeLivre);
            }
        }

        for (ItemPedido item : itens) {
            if (isInvalido(item)) {
                continue;
            }

            double parteDecimal = NumericUtils.roundTo6(item.getQtdPalete() - (long) item.getQtdPalete());
            if (parteDecimal > TOL) {
                alocarNasCarretas(item, parteDecimal, capacidade, carretas, carretaAtual, capacidadeLivre);
            }
        }

        return carretas;
    }

    public List<Carreta> particionarComConfiguracao(List<ItemPedido> itens, double capacidade) {
        return particionarComConfiguracao(itens, capacidade, null);
    }

    public List<Carreta> particionarComConfiguracao(List<ItemPedido> itens, double capacidade, String modoParticionamento) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
        }

        if (itens == null || itens.isEmpty()) {
            return Collections.emptyList();
        }

        List<ItemPedido> itensValidos = new ArrayList<ItemPedido>();
        for (ItemPedido item : itens) {
            if (!isInvalido(item) && item.isSelecionado()) {
                itensValidos.add(item);
            }
        }

        if (itensValidos.isEmpty()) {
            return Collections.emptyList();
        }

        if (temItensPriorizados(itensValidos)) {
            return particionarPorPrioridadeCompletaDepoisMix(itensValidos, capacidade);
        }

        return particionarPorFamiliasFixas(itensValidos, capacidade);
    }

    public List<Carreta> particionarSelecionadosRegraOriginal(List<ItemPedido> itens, double capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
        }

        if (itens == null || itens.isEmpty()) {
            return Collections.emptyList();
        }

        List<ItemPedido> itensSelecionados = new ArrayList<ItemPedido>();
        for (ItemPedido item : itens) {
            if (!isInvalido(item) && item.isSelecionado()) {
                itensSelecionados.add(item);
            }
        }

        if (itensSelecionados.isEmpty()) {
            return Collections.emptyList();
        }

        return particionar(itensSelecionados, capacidade);
    }

    private List<Carreta> particionarPorMix(List<ItemPedido> itens, double capacidade) {
        List<FilaMix> filas = new ArrayList<FilaMix>();
        for (ItemPedido item : itens) {
            filas.add(new FilaMix(item, normalizarGrupo(item.getGrupoMix()), item.getQtdPalete()));
        }

        List<Carreta> carretas = new ArrayList<Carreta>();
        int numeroCarreta = 0;
        while (!filas.isEmpty()) {
            numeroCarreta++;
            Carreta carreta = new Carreta(numeroCarreta, capacidade);
            double capacidadeLivre = capacidade;
            Set<String> gruposUsados = new HashSet<String>();
            int guard = 0;

            while (capacidadeLivre > TOL && !filas.isEmpty() && guard < 10000) {
                guard++;
                int idx = escolherIndiceMix(filas, gruposUsados);
                FilaMix fila = filas.get(idx);
                double qtdAlocar = fila.restante <= capacidadeLivre + TOL ? fila.restante : capacidadeLivre;
                qtdAlocar = ajustarPaletesParaVolume(fila.item, qtdAlocar, fila.restante, capacidadeLivre);
                if (qtdAlocar <= TOL) {
                    break;
                }

                carreta.getItens().add(criarItemCarreta(fila.item, qtdAlocar));
                gruposUsados.add(fila.grupo);

                capacidadeLivre = NumericUtils.roundTo6(capacidadeLivre - qtdAlocar);
                fila.restante = NumericUtils.roundTo6(fila.restante - qtdAlocar);
                if (fila.restante <= TOL) {
                    filas.remove(idx);
                }
            }

            if (carreta.getItens().isEmpty()) {
                throw new IllegalStateException("Capacidade insuficiente para alocar uma caixa inteira dos SKUs selecionados.");
            }

            carreta.setCapacidadeLivre(capacidadeLivre);
            carretas.add(carreta);
        }
        return carretas;
    }

    private List<Carreta> particionarPorMixDeSkus(List<ItemPedido> itens, double capacidade) {
        List<Carreta> carretas = new ArrayList<Carreta>();
        preencherMixSkusPorFases(itens, capacidade, carretas);

        return carretas;
    }

    private List<Carreta> particionarPorFamiliasFixas(List<ItemPedido> itens, double capacidade) {
        List<PlanoFamilia> familias = criarPlanosFamilias(itens);
        if (familias.isEmpty()) {
            return Collections.emptyList();
        }

        int capacidadeInteira = (int) Math.floor(capacidade + TOL);
        if (capacidadeInteira <= 0 && totalPaletesInteiros(familias) > 0) {
            throw new IllegalStateException("A capacidade da OEP deve comportar ao menos um palete inteiro.");
        }

        double totalPaletesOriginal = totalOriginalFamilias(familias);
        double totalRepresentatividade = totalRepresentatividadeFamilias(familias);
        Map<String, Double> alocadoPorFamilia = new LinkedHashMap<String, Double>();
        for (PlanoFamilia familia : familias) {
            alocadoPorFamilia.put(familia.codigo, 0.0);
        }

        List<Carreta> carretas = new ArrayList<Carreta>();
        double totalInteiroAlocado = 0.0;
        int guardOep = 0;
        while (totalPaletesInteiros(familias) > 0 && guardOep < 10000) {
            guardOep++;
            int limiteSkus = Math.min(MAX_SKUS_POR_OEP, capacidadeInteira);
            List<SaldoFamiliaSku> selecionados = selecionarSkusOep(
                    familias,
                    alocadoPorFamilia,
                    totalInteiroAlocado,
                    totalPaletesOriginal,
                    totalRepresentatividade,
                    capacidadeInteira,
                    limiteSkus
            );
            if (selecionados.isEmpty()) {
                throw new IllegalStateException("Nao foi possivel selecionar SKUs com paletes inteiros para compor a OEP.");
            }

            Carreta carreta = novaCarreta(carretas, capacidade);
            int paletesDisponiveis = capacidadeInteira;

            for (SaldoFamiliaSku saldo : selecionados) {
                if (paletesDisponiveis <= 0 || saldo.inteirosRestantes <= 0) {
                    break;
                }
                alocarPaleteInteiro(carreta, saldo, alocadoPorFamilia);
                paletesDisponiveis--;
                totalInteiroAlocado++;
            }

            while (paletesDisponiveis > 0) {
                SaldoFamiliaSku saldo = escolherSkuSelecionadoPorDeficit(
                        selecionados,
                        familias,
                        alocadoPorFamilia,
                        totalInteiroAlocado + 1.0,
                        totalPaletesOriginal,
                        totalRepresentatividade
                );
                if (saldo == null) {
                    break;
                }
                alocarPaleteInteiro(carreta, saldo, alocadoPorFamilia);
                paletesDisponiveis--;
                totalInteiroAlocado++;
            }

            double paletesUsados = capacidadeInteira - paletesDisponiveis;
            carreta.setCapacidadeLivre(NumericUtils.roundTo6(capacidade - paletesUsados));
            if (carreta.getItens().isEmpty()) {
                throw new IllegalStateException("Nao foi possivel alocar paletes inteiros na OEP.");
            }
        }

        if (guardOep >= 10000 && totalPaletesInteiros(familias) > 0) {
            throw new IllegalStateException("Limite de seguranca atingido ao distribuir paletes inteiros por familia.");
        }

        compactarPaletesInteiros(carretas, familias, capacidade);
        List<Carreta> otimizadas = reconstruirNoMinimoTeorico(familias, capacidade, carretas.size());
        if (otimizadas != null) {
            carretas.clear();
            carretas.addAll(otimizadas);
        }
        alocarPickingsFinais(familias, capacidade, carretas);
        return carretas;
    }

    private List<Carreta> reconstruirNoMinimoTeorico(
            List<PlanoFamilia> familias,
            double capacidade,
            int quantidadeAtual
    ) {
        int capacidadeInteira = (int) Math.floor(capacidade + TOL);
        if (capacidadeInteira <= 0 || familias.isEmpty()) {
            return null;
        }

        List<SaldoReconstrucao> principais = new ArrayList<SaldoReconstrucao>();
        List<SaldoReconstrucao> secundarios = new ArrayList<SaldoReconstrucao>();
        int totalInteiro = 0;
        for (int f = 0; f < familias.size(); f++) {
            PlanoFamilia familia = familias.get(f);
            for (SaldoFamiliaSku saldo : familia.skus) {
                int inteiro = (int) Math.floor(saldo.item.getQtdPalete() + TOL);
                if (inteiro <= 0) {
                    continue;
                }
                SaldoReconstrucao candidato = new SaldoReconstrucao(saldo.item, familia, inteiro);
                if (f == 0) {
                    principais.add(candidato);
                } else {
                    secundarios.add(candidato);
                }
                totalInteiro += inteiro;
            }
        }
        if (totalInteiro <= 0) {
            return null;
        }

        int minimoTeorico = (int) Math.ceil(totalInteiro / (double) capacidadeInteira);
        if (minimoTeorico >= quantidadeAtual) {
            return null;
        }

        int[] metas = criarMetasOeps(totalInteiro, capacidadeInteira, minimoTeorico);
        int totalSecundario = somarSaldosReconstrucao(secundarios);
        int[] metasSecundarias = distribuirMetaProporcional(totalSecundario, metas, totalInteiro);
        List<Map<SaldoReconstrucao, Integer>> alocacoesSecundarias = alocarSecundariosPorMeta(
                secundarios,
                metasSecundarias
        );
        if (alocacoesSecundarias == null) {
            return null;
        }

        int[] metasPrincipais = new int[metas.length];
        for (int i = 0; i < metas.length; i++) {
            metasPrincipais[i] = metas[i] - metasSecundarias[i];
            if (metasPrincipais[i] < 0) {
                return null;
            }
        }
        List<Map<SaldoReconstrucao, Integer>> alocacoesPrincipais = alocarSequencialmente(
                principais,
                metasPrincipais,
                SKUS_FAMILIA_PRINCIPAL
        );
        if (alocacoesPrincipais == null) {
            return null;
        }
        if (!diversificarPrincipais(alocacoesPrincipais, metasPrincipais, principais.size())) {
            return null;
        }

        List<Carreta> resultado = materializarReconstrucao(
                capacidade,
                metas,
                alocacoesPrincipais,
                alocacoesSecundarias
        );
        if (!validarReconstrucao(resultado, familias, metas, minimoTeorico)) {
            return null;
        }
        return resultado;
    }

    private int[] criarMetasOeps(int total, int capacidade, int quantidade) {
        int[] metas = new int[quantidade];
        int restante = total;
        for (int i = 0; i < quantidade; i++) {
            metas[i] = Math.min(capacidade, restante);
            restante -= metas[i];
        }
        return metas;
    }

    private int[] distribuirMetaProporcional(int totalSecundario, int[] metas, int totalInteiro) {
        int[] resultado = new int[metas.length];
        double[] fracoes = new double[metas.length];
        int distribuido = 0;
        for (int i = 0; i < metas.length; i++) {
            double exato = totalInteiro <= 0 ? 0.0 : totalSecundario * metas[i] / (double) totalInteiro;
            resultado[i] = Math.min(metas[i], (int) Math.floor(exato + TOL));
            fracoes[i] = exato - resultado[i];
            distribuido += resultado[i];
        }

        while (distribuido < totalSecundario) {
            int melhor = -1;
            for (int i = 0; i < metas.length; i++) {
                if (resultado[i] >= metas[i]) {
                    continue;
                }
                if (melhor < 0 || fracoes[i] > fracoes[melhor] + TOL
                        || (Math.abs(fracoes[i] - fracoes[melhor]) <= TOL && i < melhor)) {
                    melhor = i;
                }
            }
            if (melhor < 0) {
                break;
            }
            resultado[melhor]++;
            fracoes[melhor] = -1.0;
            distribuido++;
        }
        return resultado;
    }

    private List<Map<SaldoReconstrucao, Integer>> alocarSecundariosPorMeta(
            List<SaldoReconstrucao> originais,
            int[] metas
    ) {
        List<SaldoReconstrucao> saldos = copiarSaldosReconstrucao(originais);
        List<Map<SaldoReconstrucao, Integer>> resultado = novasAlocacoes(metas.length);
        for (int i = 0; i < metas.length; i++) {
            int restante = metas[i];
            Map<SaldoReconstrucao, Integer> alocacao = resultado.get(i);
            while (restante > 0 && alocacao.size() < SKUS_OUTRAS_FAMILIAS) {
                String familiaJaUsada = alocacao.isEmpty()
                        ? null
                        : alocacao.keySet().iterator().next().familia.codigo;
                SaldoReconstrucao melhor = escolherMaiorSaldo(saldos, alocacao, familiaJaUsada);
                if (melhor == null) {
                    return null;
                }
                alocacao.put(melhor, 1);
                melhor.restante--;
                restante--;
            }
            while (restante > 0) {
                SaldoReconstrucao selecionado = maiorSaldoSelecionado(alocacao);
                if (selecionado == null || selecionado.restante <= 0) {
                    return null;
                }
                int quantidade = Math.min(restante, selecionado.restante);
                alocacao.put(selecionado, alocacao.get(selecionado) + quantidade);
                selecionado.restante -= quantidade;
                restante -= quantidade;
            }
        }
        return todosConsumidos(saldos) ? resultado : null;
    }

    private List<Map<SaldoReconstrucao, Integer>> alocarSequencialmente(
            List<SaldoReconstrucao> originais,
            int[] metas,
            int limiteSkus
    ) {
        List<SaldoReconstrucao> saldos = copiarSaldosReconstrucao(originais);
        List<Map<SaldoReconstrucao, Integer>> resultado = novasAlocacoes(metas.length);
        int cursor = 0;
        for (int i = 0; i < metas.length; i++) {
            int restante = metas[i];
            Map<SaldoReconstrucao, Integer> alocacao = resultado.get(i);
            while (restante > 0) {
                while (cursor < saldos.size() && saldos.get(cursor).restante <= 0) {
                    cursor++;
                }
                if (cursor >= saldos.size() || alocacao.size() >= limiteSkus) {
                    return null;
                }
                SaldoReconstrucao saldo = saldos.get(cursor);
                int quantidade = Math.min(restante, saldo.restante);
                alocacao.put(saldo, quantidade);
                saldo.restante -= quantidade;
                restante -= quantidade;
            }
        }
        return todosConsumidos(saldos) ? resultado : null;
    }

    private boolean diversificarPrincipais(
            List<Map<SaldoReconstrucao, Integer>> alocacoes,
            int[] metas,
            int quantidadeSkusPrincipais
    ) {
        for (int i = 0; i < alocacoes.size(); i++) {
            Map<SaldoReconstrucao, Integer> atual = alocacoes.get(i);
            int desejado = Math.min(SKUS_FAMILIA_PRINCIPAL, Math.min(metas[i], quantidadeSkusPrincipais));
            int guard = 0;
            while (atual.size() < desejado && guard++ < 1000) {
                if (!trocarComOutraOepDeficiente(alocacoes, i, desejado)
                        && !trocarComOepDoadora(alocacoes, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean trocarComOutraOepDeficiente(
            List<Map<SaldoReconstrucao, Integer>> alocacoes,
            int indice,
            int desejado
    ) {
        Map<SaldoReconstrucao, Integer> esquerda = alocacoes.get(indice);
        for (int j = 0; j < alocacoes.size(); j++) {
            if (j == indice) {
                continue;
            }
            Map<SaldoReconstrucao, Integer> direita = alocacoes.get(j);
            if (direita.size() >= desejado) {
                continue;
            }
            SaldoReconstrucao itemEsquerda = itemTrocavel(esquerda, direita, false);
            SaldoReconstrucao itemDireita = itemTrocavel(direita, esquerda, false);
            if (itemEsquerda != null && itemDireita != null) {
                trocarUmPalete(esquerda, direita, itemEsquerda, itemDireita);
                return true;
            }
        }
        return false;
    }

    private boolean trocarComOepDoadora(
            List<Map<SaldoReconstrucao, Integer>> alocacoes,
            int indice
    ) {
        Map<SaldoReconstrucao, Integer> deficiente = alocacoes.get(indice);
        for (int j = 0; j < alocacoes.size(); j++) {
            if (j == indice) {
                continue;
            }
            Map<SaldoReconstrucao, Integer> doadora = alocacoes.get(j);
            SaldoReconstrucao itemDeficiente = itemTrocavel(deficiente, doadora, false);
            SaldoReconstrucao itemDoadora = itemTrocavel(doadora, deficiente, true);
            if (itemDeficiente != null && itemDoadora != null) {
                trocarUmPalete(deficiente, doadora, itemDeficiente, itemDoadora);
                return true;
            }
        }
        return false;
    }

    private SaldoReconstrucao itemTrocavel(
            Map<SaldoReconstrucao, Integer> origem,
            Map<SaldoReconstrucao, Integer> destino,
            boolean exigirQuantidadeUm
    ) {
        for (Map.Entry<SaldoReconstrucao, Integer> entrada : origem.entrySet()) {
            int quantidade = entrada.getValue();
            if ((exigirQuantidadeUm ? quantidade == 1 : quantidade >= 2)
                    && !destino.containsKey(entrada.getKey())) {
                return entrada.getKey();
            }
        }
        return null;
    }

    private void trocarUmPalete(
            Map<SaldoReconstrucao, Integer> esquerda,
            Map<SaldoReconstrucao, Integer> direita,
            SaldoReconstrucao itemEsquerda,
            SaldoReconstrucao itemDireita
    ) {
        decrementarAlocacao(esquerda, itemEsquerda);
        decrementarAlocacao(direita, itemDireita);
        incrementarAlocacao(esquerda, itemDireita);
        incrementarAlocacao(direita, itemEsquerda);
    }

    private void decrementarAlocacao(Map<SaldoReconstrucao, Integer> alocacao, SaldoReconstrucao item) {
        int novaQuantidade = alocacao.get(item) - 1;
        if (novaQuantidade <= 0) {
            alocacao.remove(item);
        } else {
            alocacao.put(item, novaQuantidade);
        }
    }

    private void incrementarAlocacao(Map<SaldoReconstrucao, Integer> alocacao, SaldoReconstrucao item) {
        Integer atual = alocacao.get(item);
        alocacao.put(item, atual == null ? 1 : atual + 1);
    }

    private List<Carreta> materializarReconstrucao(
            double capacidade,
            int[] metas,
            List<Map<SaldoReconstrucao, Integer>> principais,
            List<Map<SaldoReconstrucao, Integer>> secundarios
    ) {
        List<Carreta> resultado = new ArrayList<Carreta>();
        for (int i = 0; i < metas.length; i++) {
            Carreta carreta = new Carreta(i + 1, capacidade);
            adicionarAlocacoesNaCarreta(carreta, principais.get(i));
            adicionarAlocacoesNaCarreta(carreta, secundarios.get(i));
            Collections.sort(carreta.getItens(), new Comparator<ItemCarreta>() {
                @Override
                public int compare(ItemCarreta a, ItemCarreta b) {
                    return a.getSequenciaOrigem() - b.getSequenciaOrigem();
                }
            });
            carreta.setCapacidadeLivre(NumericUtils.roundTo6(capacidade - metas[i]));
            resultado.add(carreta);
        }
        return resultado;
    }

    private void adicionarAlocacoesNaCarreta(
            Carreta carreta,
            Map<SaldoReconstrucao, Integer> alocacoes
    ) {
        for (Map.Entry<SaldoReconstrucao, Integer> entrada : alocacoes.entrySet()) {
            adicionarItemCarreta(carreta, entrada.getKey().item, entrada.getValue());
        }
    }

    private boolean validarReconstrucao(
            List<Carreta> carretas,
            List<PlanoFamilia> familias,
            int[] metas,
            int minimoTeorico
    ) {
        if (carretas.size() != minimoTeorico) {
            return false;
        }
        Map<String, Integer> esperado = new HashMap<String, Integer>();
        Map<String, Integer> atual = new HashMap<String, Integer>();
        Map<String, String> familiaPorItem = mapearFamiliasPorItem(familias);
        String familiaPrincipal = familias.get(0).codigo;
        for (PlanoFamilia familia : familias) {
            for (SaldoFamiliaSku saldo : familia.skus) {
                int inteiro = (int) Math.floor(saldo.item.getQtdPalete() + TOL);
                if (inteiro > 0) {
                    esperado.put(chaveItem(saldo.item), inteiro);
                }
            }
        }

        for (int i = 0; i < carretas.size(); i++) {
            Carreta carreta = carretas.get(i);
            if (Math.abs(carreta.getPaletesUsados() - metas[i]) > TOL || contarSkus(carreta) > MAX_SKUS_POR_OEP) {
                return false;
            }
            Set<String> secundarios = new HashSet<String>();
            Set<String> familiasOep = new HashSet<String>();
            for (ItemCarreta item : carreta.getItens()) {
                String chave = chaveItem(item);
                String familia = familiaPorItem.get(chave);
                if (familia == null) {
                    return false;
                }
                familiasOep.add(familia);
                if (!textoIgual(familiaPrincipal, familia)) {
                    secundarios.add(item.getCodProd());
                }
                Integer quantidade = atual.get(chave);
                atual.put(chave, (quantidade == null ? 0 : quantidade)
                        + (int) Math.round(item.getQtdAlocada()));
            }
            if (secundarios.size() > SKUS_OUTRAS_FAMILIAS || familiasOep.size() > 3) {
                return false;
            }
        }
        return esperado.equals(atual);
    }

    private List<Map<SaldoReconstrucao, Integer>> novasAlocacoes(int quantidade) {
        List<Map<SaldoReconstrucao, Integer>> resultado = new ArrayList<Map<SaldoReconstrucao, Integer>>();
        for (int i = 0; i < quantidade; i++) {
            resultado.add(new LinkedHashMap<SaldoReconstrucao, Integer>());
        }
        return resultado;
    }

    private List<SaldoReconstrucao> copiarSaldosReconstrucao(List<SaldoReconstrucao> originais) {
        List<SaldoReconstrucao> resultado = new ArrayList<SaldoReconstrucao>();
        for (SaldoReconstrucao original : originais) {
            resultado.add(new SaldoReconstrucao(original.item, original.familia, original.original));
        }
        return resultado;
    }

    private SaldoReconstrucao escolherMaiorSaldo(
            List<SaldoReconstrucao> saldos,
            Map<SaldoReconstrucao, Integer> selecionados,
            String familiaDiferente
    ) {
        SaldoReconstrucao melhor = null;
        for (SaldoReconstrucao saldo : saldos) {
            if (saldo.restante <= 0 || selecionados.containsKey(saldo)
                    || (familiaDiferente != null && textoIgual(familiaDiferente, saldo.familia.codigo))) {
                continue;
            }
            if (melhor == null || saldo.restante > melhor.restante
                    || (saldo.restante == melhor.restante
                    && saldo.item.getSequenciaOrigem() < melhor.item.getSequenciaOrigem())) {
                melhor = saldo;
            }
        }
        if (melhor == null) {
            if (familiaDiferente != null) {
                for (SaldoReconstrucao saldo : saldos) {
                    if (saldo.restante > 0 && !selecionados.containsKey(saldo)) {
                        return saldo;
                    }
                }
            }
            for (SaldoReconstrucao saldo : saldos) {
                if (saldo.restante > 0 && selecionados.containsKey(saldo)) {
                    return saldo;
                }
            }
        }
        return melhor;
    }

    private SaldoReconstrucao maiorSaldoSelecionado(Map<SaldoReconstrucao, Integer> selecionados) {
        SaldoReconstrucao melhor = null;
        for (SaldoReconstrucao saldo : selecionados.keySet()) {
            if (saldo.restante > 0 && (melhor == null || saldo.restante > melhor.restante)) {
                melhor = saldo;
            }
        }
        return melhor;
    }

    private int somarSaldosReconstrucao(List<SaldoReconstrucao> saldos) {
        int total = 0;
        for (SaldoReconstrucao saldo : saldos) {
            total += saldo.original;
        }
        return total;
    }

    private boolean todosConsumidos(List<SaldoReconstrucao> saldos) {
        for (SaldoReconstrucao saldo : saldos) {
            if (saldo.restante != 0) {
                return false;
            }
        }
        return true;
    }

    private void compactarPaletesInteiros(
            List<Carreta> carretas,
            List<PlanoFamilia> familias,
            double capacidade
    ) {
        if (carretas.size() < 2) {
            return;
        }

        Map<String, String> familiaPorItem = mapearFamiliasPorItem(familias);
        String familiaPrincipal = familias.get(0).codigo;

        boolean houveMovimento = true;
        int guardCompactacao = 0;
        while (houveMovimento && guardCompactacao < 10000) {
            houveMovimento = false;
            guardCompactacao++;
            for (int origemIndice = carretas.size() - 1; origemIndice > 0; origemIndice--) {
                Carreta origem = carretas.get(origemIndice);
                if (origem.getItens().isEmpty()) {
                    continue;
                }
                List<ItemCarreta> itensOrigem = new ArrayList<ItemCarreta>(origem.getItens());
                for (ItemCarreta itemOrigem : itensOrigem) {
                    houveMovimento |= moverItemParaOepsAnteriores(
                            carretas,
                            origemIndice,
                            origem,
                            itemOrigem,
                            familiaPorItem,
                            familiaPrincipal,
                            true
                    );
                    houveMovimento |= moverItemParaOepsAnteriores(
                            carretas,
                            origemIndice,
                            origem,
                            itemOrigem,
                            familiaPorItem,
                            familiaPrincipal,
                            false
                    );
                }
            }
        }

        if (guardCompactacao >= 10000 && houveMovimento) {
            throw new IllegalStateException("Limite de seguranca atingido ao compactar as OEPs de paletes inteiros.");
        }

        List<Carreta> naoVazias = new ArrayList<Carreta>();
        for (Carreta carreta : carretas) {
            if (!carreta.getItens().isEmpty()) {
                naoVazias.add(carreta);
            }
        }

        carretas.clear();
        for (int i = 0; i < naoVazias.size(); i++) {
            Carreta anterior = naoVazias.get(i);
            Carreta renumerada = new Carreta(i + 1, capacidade);
            renumerada.getItens().addAll(anterior.getItens());
            renumerada.setCapacidadeLivre(anterior.getCapacidadeLivre());
            renumerada.setDhAgendamento(anterior.getDhAgendamento());
            carretas.add(renumerada);
        }
    }

    private boolean moverItemParaOepsAnteriores(
            List<Carreta> carretas,
            int origemIndice,
            Carreta origem,
            ItemCarreta itemOrigem,
            Map<String, String> familiaPorItem,
            String familiaPrincipal,
            boolean exigirMesmoSku
    ) {
        boolean moveu = false;
        while (itemOrigem.getQtdAlocada() >= 1.0 - TOL) {
            Carreta destino = escolherDestinoCompactacao(
                    carretas,
                    origemIndice,
                    itemOrigem,
                    familiaPorItem,
                    familiaPrincipal,
                    exigirMesmoSku
            );
            if (destino == null) {
                return moveu;
            }

            double quantidade = Math.min(
                    Math.floor(itemOrigem.getQtdAlocada() + TOL),
                    Math.floor(destino.getCapacidadeLivre() + TOL)
            );
            if (quantidade < 1.0 - TOL) {
                return moveu;
            }

            transferirPaletesInteiros(origem, destino, itemOrigem, quantidade);
            moveu = true;
            if (itemOrigem.getQtdAlocada() <= TOL) {
                origem.getItens().remove(itemOrigem);
                return true;
            }
        }
        return moveu;
    }

    private Carreta escolherDestinoCompactacao(
            List<Carreta> carretas,
            int origemIndice,
            ItemCarreta itemOrigem,
            Map<String, String> familiaPorItem,
            String familiaPrincipal,
            boolean exigirMesmoSku
    ) {
        Carreta melhor = null;
        for (int i = 0; i < origemIndice; i++) {
            Carreta candidata = carretas.get(i);
            if (candidata.getItens().isEmpty() || candidata.getCapacidadeLivre() < 1.0 - TOL) {
                continue;
            }

            boolean mesmoSku = contemSku(candidata, itemOrigem.getCodProd());
            if (exigirMesmoSku != mesmoSku) {
                continue;
            }
            if (!mesmoSku && !podeAdicionarSkuNaCompactacao(
                    candidata,
                    itemOrigem,
                    familiaPorItem,
                    familiaPrincipal
            )) {
                continue;
            }

            if (melhor == null || candidata.getCapacidadeLivre() < melhor.getCapacidadeLivre() - TOL
                    || (Math.abs(candidata.getCapacidadeLivre() - melhor.getCapacidadeLivre()) <= TOL
                    && candidata.getNumero() < melhor.getNumero())) {
                melhor = candidata;
            }
        }
        return melhor;
    }

    private boolean podeAdicionarSkuNaCompactacao(
            Carreta carreta,
            ItemCarreta item,
            Map<String, String> familiaPorItem,
            String familiaPrincipal
    ) {
        if (contarSkus(carreta) >= MAX_SKUS_POR_OEP) {
            return false;
        }

        String familiaNova = familiaPorItem.get(chaveItem(item));
        if (familiaNova == null) {
            return false;
        }

        Set<String> familias = new HashSet<String>();
        Set<String> skusSecundarios = new HashSet<String>();
        for (ItemCarreta existente : carreta.getItens()) {
            String familia = familiaPorItem.get(chaveItem(existente));
            if (familia == null) {
                return false;
            }
            familias.add(familia);
            if (!textoIgual(familiaPrincipal, familia)) {
                skusSecundarios.add(existente.getCodProd());
            }
        }

        familias.add(familiaNova);
        if (!textoIgual(familiaPrincipal, familiaNova)) {
            skusSecundarios.add(item.getCodProd());
        }
        return familias.size() <= 3 && skusSecundarios.size() <= SKUS_OUTRAS_FAMILIAS;
    }

    private Map<String, String> mapearFamiliasPorItem(List<PlanoFamilia> familias) {
        Map<String, String> resultado = new HashMap<String, String>();
        for (PlanoFamilia familia : familias) {
            for (SaldoFamiliaSku saldo : familia.skus) {
                resultado.put(chaveItem(saldo.item), familia.codigo);
            }
        }
        return resultado;
    }

    private String chaveItem(ItemPedido item) {
        return item.getSequenciaOrigem() + "|" + item.getCodProd() + "|" + textoOuVazio(item.getControle());
    }

    private String chaveItem(ItemCarreta item) {
        return item.getSequenciaOrigem() + "|" + item.getCodProd() + "|" + textoOuVazio(item.getControle());
    }

    private String textoOuVazio(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private void transferirPaletesInteiros(
            Carreta origem,
            Carreta destino,
            ItemCarreta itemOrigem,
            double quantidade
    ) {
        double unidadesPorPalete = itemOrigem.getQtdAlocada() <= TOL
                ? 0.0
                : itemOrigem.getQtdUnidade() / itemOrigem.getQtdAlocada();
        double caixasPorPalete = itemOrigem.getQtdAlocada() <= TOL
                ? 0.0
                : itemOrigem.getQtdCaixa() / itemOrigem.getQtdAlocada();

        ItemCarreta itemDestino = localizarMesmoItem(destino, itemOrigem);
        if (itemDestino == null) {
            itemDestino = copiarItemCarreta(itemOrigem);
            itemDestino.setQtdAlocada(0.0);
            itemDestino.setQtdUnidade(0.0);
            itemDestino.setQtdCaixa(0.0);
            destino.getItens().add(itemDestino);
        }

        double novaQtdDestino = NumericUtils.roundTo6(itemDestino.getQtdAlocada() + quantidade);
        itemDestino.setQtdAlocada(novaQtdDestino);
        itemDestino.setQtdUnidade(NumericUtils.roundTo6(itemDestino.getQtdUnidade() + quantidade * unidadesPorPalete));
        itemDestino.setQtdCaixa(NumericUtils.roundTo6(itemDestino.getQtdCaixa() + quantidade * caixasPorPalete));

        double novaQtdOrigem = NumericUtils.roundTo6(itemOrigem.getQtdAlocada() - quantidade);
        itemOrigem.setQtdAlocada(novaQtdOrigem);
        itemOrigem.setQtdUnidade(NumericUtils.roundTo6(novaQtdOrigem * unidadesPorPalete));
        itemOrigem.setQtdCaixa(NumericUtils.roundTo6(novaQtdOrigem * caixasPorPalete));

        destino.setCapacidadeLivre(NumericUtils.roundTo6(destino.getCapacidadeLivre() - quantidade));
        origem.setCapacidadeLivre(NumericUtils.roundTo6(origem.getCapacidadeLivre() + quantidade));
    }

    private ItemCarreta localizarMesmoItem(Carreta carreta, ItemCarreta item) {
        for (ItemCarreta existente : carreta.getItens()) {
            if (existente.getSequenciaOrigem() == item.getSequenciaOrigem()
                    && textoIgual(existente.getCodProd(), item.getCodProd())
                    && textoIgual(existente.getControle(), item.getControle())) {
                return existente;
            }
        }
        return null;
    }

    private ItemCarreta copiarItemCarreta(ItemCarreta origem) {
        ItemCarreta copia = new ItemCarreta();
        copia.setCodProd(origem.getCodProd());
        copia.setDescProd(origem.getDescProd());
        copia.setQtdAlocada(origem.getQtdAlocada());
        copia.setQtdOrigem(origem.getQtdOrigem());
        copia.setQtdUnidade(origem.getQtdUnidade());
        copia.setQtdCaixa(origem.getQtdCaixa());
        copia.setSequenciaOrigem(origem.getSequenciaOrigem());
        copia.setCodLocalOrig(origem.getCodLocalOrig());
        copia.setControle(origem.getControle());
        copia.setStatusLote(origem.getStatusLote());
        copia.setCodVol(origem.getCodVol());
        copia.setUsoProd(origem.getUsoProd());
        copia.setVlrUnit(origem.getVlrUnit());
        return copia;
    }

    private List<PlanoFamilia> criarPlanosFamilias(List<ItemPedido> itens) {
        Map<String, PlanoFamilia> mapa = new HashMap<String, PlanoFamilia>();
        for (ItemPedido item : itens) {
            String familia = normalizarFamilia(item.getCodFamilia());
            if ("SEM_FAMILIA".equals(familia)) {
                throw new IllegalStateException(
                        "Produto " + item.getCodProd() + " sem familia cadastrada. Preencha a familia antes de particionar."
                );
            }

            PlanoFamilia plano = mapa.get(familia);
            if (plano == null) {
                plano = new PlanoFamilia(familia, item.getDescFamilia());
                mapa.put(familia, plano);
            }

            double total = NumericUtils.roundTo6(item.getQtdPalete());
            double representatividade = NumericUtils.roundTo6(item.getQtdOrigem());
            if (representatividade <= TOL) {
                representatividade = NumericUtils.roundTo6(item.getQtdPalete() * item.getUndPorPalete());
            }
            if (representatividade <= TOL) {
                representatividade = total;
            }
            int inteiros = (int) Math.floor(total + TOL);
            double picking = NumericUtils.roundTo6(total - inteiros);
            if (picking < TOL) {
                picking = 0.0;
            }
            plano.totalOriginal = NumericUtils.roundTo6(plano.totalOriginal + total);
            plano.totalRepresentatividade = NumericUtils.roundTo6(
                    plano.totalRepresentatividade + representatividade
            );
            plano.skus.add(new SaldoFamiliaSku(item, plano, inteiros, picking));
        }

        List<PlanoFamilia> familias = new ArrayList<PlanoFamilia>(mapa.values());
        Collections.sort(familias, new Comparator<PlanoFamilia>() {
            @Override
            public int compare(PlanoFamilia a, PlanoFamilia b) {
                int representatividade = Double.compare(b.totalRepresentatividade, a.totalRepresentatividade);
                if (representatividade != 0) {
                    return representatividade;
                }
                int total = Double.compare(b.totalOriginal, a.totalOriginal);
                return total != 0 ? total : a.codigo.compareTo(b.codigo);
            }
        });

        for (int i = 0; i < familias.size(); i++) {
            PlanoFamilia familia = familias.get(i);
            familia.ordem = i;
            Collections.sort(familia.skus, new Comparator<SaldoFamiliaSku>() {
                @Override
                public int compare(SaldoFamiliaSku a, SaldoFamiliaSku b) {
                    int inteiros = b.inteirosRestantes - a.inteirosRestantes;
                    if (inteiros != 0) {
                        return inteiros;
                    }
                    int total = Double.compare(b.item.getQtdPalete(), a.item.getQtdPalete());
                    if (total != 0) {
                        return total;
                    }
                    int sequencia = a.item.getSequenciaOrigem() - b.item.getSequenciaOrigem();
                    return sequencia != 0 ? sequencia : a.item.getCodProd().compareTo(b.item.getCodProd());
                }
            });
        }
        return familias;
    }

    private List<SaldoFamiliaSku> selecionarSkusOep(
            List<PlanoFamilia> familias,
            Map<String, Double> alocadoPorFamilia,
            double totalInteiroAlocado,
            double totalPaletesOriginal,
            double totalRepresentatividade,
            int capacidadeInteira,
            int limiteSkus
    ) {
        List<SaldoFamiliaSku> selecionados = new ArrayList<SaldoFamiliaSku>();
        if (limiteSkus <= 0) {
            return selecionados;
        }

        PlanoFamilia principal = familias.get(0);
        adicionarSkusDistintos(principal, selecionados, Math.min(SKUS_FAMILIA_PRINCIPAL, limiteSkus));

        Set<String> familiasSecundariasUsadas = new HashSet<String>();
        while (selecionados.size() < limiteSkus
                && familiasSecundariasUsadas.size() < SKUS_OUTRAS_FAMILIAS) {
            PlanoFamilia secundaria = escolherFamiliaSecundaria(
                    familias,
                    familiasSecundariasUsadas,
                    alocadoPorFamilia,
                    totalInteiroAlocado + capacidadeInteira,
                    totalPaletesOriginal,
                    totalRepresentatividade
            );
            if (secundaria == null) {
                break;
            }
            SaldoFamiliaSku sku = primeiroSkuElegivel(secundaria, selecionados);
            familiasSecundariasUsadas.add(secundaria.codigo);
            if (sku != null) {
                selecionados.add(sku);
            }
        }

        if (selecionados.size() < limiteSkus
                && totalInteiroSelecionado(selecionados) < capacidadeInteira) {
            adicionarSkusDistintos(principal, selecionados, Math.min(selecionados.size() + 1, limiteSkus));
        }

        return selecionados;
    }

    private void adicionarSkusDistintos(PlanoFamilia familia, List<SaldoFamiliaSku> selecionados, int totalDesejado) {
        for (SaldoFamiliaSku saldo : familia.skus) {
            if (selecionados.size() >= totalDesejado) {
                return;
            }
            if (saldo.inteirosRestantes > 0 && !contemSku(selecionados, saldo.item.getCodProd())) {
                selecionados.add(saldo);
            }
        }
    }

    private PlanoFamilia escolherFamiliaSecundaria(
            List<PlanoFamilia> familias,
            Set<String> familiasUsadas,
            Map<String, Double> alocadoPorFamilia,
            double totalProjetado,
            double totalPaletesOriginal,
            double totalRepresentatividade
    ) {
        PlanoFamilia melhor = null;
        double maiorDeficit = -Double.MAX_VALUE;
        for (int i = 1; i < familias.size(); i++) {
            PlanoFamilia familia = familias.get(i);
            if (familiasUsadas.contains(familia.codigo) || primeiroSkuElegivel(familia, null) == null) {
                continue;
            }
            double deficit = deficitFamilia(
                    familia,
                    alocadoPorFamilia,
                    totalProjetado,
                    totalPaletesOriginal,
                    totalRepresentatividade
            );
            if (melhor == null || deficit > maiorDeficit + TOL
                    || (Math.abs(deficit - maiorDeficit) <= TOL && familia.ordem < melhor.ordem)) {
                melhor = familia;
                maiorDeficit = deficit;
            }
        }
        return melhor;
    }

    private SaldoFamiliaSku primeiroSkuElegivel(PlanoFamilia familia, List<SaldoFamiliaSku> selecionados) {
        for (SaldoFamiliaSku saldo : familia.skus) {
            if (saldo.inteirosRestantes > 0
                    && (selecionados == null || !contemSku(selecionados, saldo.item.getCodProd()))) {
                return saldo;
            }
        }
        return null;
    }

    private SaldoFamiliaSku escolherSkuSelecionadoPorDeficit(
            List<SaldoFamiliaSku> selecionados,
            List<PlanoFamilia> familias,
            Map<String, Double> alocadoPorFamilia,
            double totalProjetado,
            double totalPaletesOriginal,
            double totalRepresentatividade
    ) {
        PlanoFamilia melhorFamilia = null;
        double maiorDeficit = -Double.MAX_VALUE;
        for (PlanoFamilia familia : familias) {
            if (!temSaldoSelecionado(familia, selecionados)) {
                continue;
            }
            double deficit = deficitFamilia(
                    familia,
                    alocadoPorFamilia,
                    totalProjetado,
                    totalPaletesOriginal,
                    totalRepresentatividade
            );
            if (melhorFamilia == null || deficit > maiorDeficit + TOL
                    || (Math.abs(deficit - maiorDeficit) <= TOL && familia.ordem < melhorFamilia.ordem)) {
                melhorFamilia = familia;
                maiorDeficit = deficit;
            }
        }

        SaldoFamiliaSku melhorSku = null;
        for (SaldoFamiliaSku saldo : selecionados) {
            if (saldo.familia == melhorFamilia && saldo.inteirosRestantes > 0
                    && (melhorSku == null || saldo.inteirosRestantes > melhorSku.inteirosRestantes
                    || (saldo.inteirosRestantes == melhorSku.inteirosRestantes
                    && saldo.item.getSequenciaOrigem() < melhorSku.item.getSequenciaOrigem()))) {
                melhorSku = saldo;
            }
        }
        return melhorSku;
    }

    private boolean temSaldoSelecionado(PlanoFamilia familia, List<SaldoFamiliaSku> selecionados) {
        for (SaldoFamiliaSku saldo : selecionados) {
            if (saldo.familia == familia && saldo.inteirosRestantes > 0) {
                return true;
            }
        }
        return false;
    }

    private double deficitFamilia(
            PlanoFamilia familia,
            Map<String, Double> alocadoPorFamilia,
            double totalProjetado,
            double totalPaletesOriginal,
            double totalRepresentatividade
    ) {
        double percentual = totalRepresentatividade <= TOL
                ? 0.0
                : familia.totalRepresentatividade / totalRepresentatividade;
        double meta = Math.min(totalPaletesOriginal, totalProjetado) * percentual;
        return meta - valorOuZero(alocadoPorFamilia.get(familia.codigo));
    }

    private void alocarPaleteInteiro(
            Carreta carreta,
            SaldoFamiliaSku saldo,
            Map<String, Double> alocadoPorFamilia
    ) {
        adicionarItemCarreta(carreta, saldo.item, 1.0);
        saldo.inteirosRestantes--;
        alocadoPorFamilia.put(
                saldo.familia.codigo,
                NumericUtils.roundTo6(valorOuZero(alocadoPorFamilia.get(saldo.familia.codigo)) + 1.0)
        );
    }

    private void alocarPickingsFinais(List<PlanoFamilia> familias, double capacidade, List<Carreta> carretas) {
        List<SaldoFamiliaSku> pickings = new ArrayList<SaldoFamiliaSku>();
        for (PlanoFamilia familia : familias) {
            for (SaldoFamiliaSku saldo : familia.skus) {
                if (saldo.pickingRestante > TOL) {
                    pickings.add(saldo);
                }
            }
        }

        Collections.sort(pickings, new Comparator<SaldoFamiliaSku>() {
            @Override
            public int compare(SaldoFamiliaSku a, SaldoFamiliaSku b) {
                int sequencia = a.item.getSequenciaOrigem() - b.item.getSequenciaOrigem();
                return sequencia != 0 ? sequencia : a.item.getCodProd().compareTo(b.item.getCodProd());
            }
        });

        for (SaldoFamiliaSku saldo : pickings) {
            double restante = saldo.pickingRestante;
            while (restante > TOL) {
                Carreta carreta = carretaAtualOuNova(carretas, capacidade);
                double qtdAlocar = Math.min(restante, carreta.getCapacidadeLivre());
                qtdAlocar = ajustarPaletesParaVolume(saldo.item, qtdAlocar, restante, carreta.getCapacidadeLivre());
                if (qtdAlocar <= TOL) {
                    if (!carreta.getItens().isEmpty()) {
                        novaCarreta(carretas, capacidade);
                        continue;
                    }
                    throw new IllegalStateException(
                            "Capacidade insuficiente para alocar o picking do SKU " + saldo.item.getCodProd() + "."
                    );
                }

                adicionarItemCarreta(carreta, saldo.item, qtdAlocar);
                carreta.setCapacidadeLivre(NumericUtils.roundTo6(carreta.getCapacidadeLivre() - qtdAlocar));
                restante = NumericUtils.roundTo6(restante - qtdAlocar);
            }
            saldo.pickingRestante = 0.0;
        }
    }

    private boolean contemSku(List<SaldoFamiliaSku> saldos, String codProd) {
        for (SaldoFamiliaSku saldo : saldos) {
            if (textoIgual(saldo.item.getCodProd(), codProd)) {
                return true;
            }
        }
        return false;
    }

    private boolean contemSku(Carreta carreta, String codProd) {
        for (ItemCarreta item : carreta.getItens()) {
            if (textoIgual(item.getCodProd(), codProd)) {
                return true;
            }
        }
        return false;
    }

    private int contarSkus(Carreta carreta) {
        Set<String> skus = new HashSet<String>();
        for (ItemCarreta item : carreta.getItens()) {
            skus.add(item.getCodProd());
        }
        return skus.size();
    }

    private int totalInteiroSelecionado(List<SaldoFamiliaSku> selecionados) {
        int total = 0;
        for (SaldoFamiliaSku saldo : selecionados) {
            total += saldo.inteirosRestantes;
        }
        return total;
    }

    private int totalPaletesInteiros(List<PlanoFamilia> familias) {
        int total = 0;
        for (PlanoFamilia familia : familias) {
            for (SaldoFamiliaSku saldo : familia.skus) {
                total += saldo.inteirosRestantes;
            }
        }
        return total;
    }

    private double totalOriginalFamilias(List<PlanoFamilia> familias) {
        double total = 0.0;
        for (PlanoFamilia familia : familias) {
            total += familia.totalOriginal;
        }
        return NumericUtils.roundTo6(total);
    }

    private double totalRepresentatividadeFamilias(List<PlanoFamilia> familias) {
        double total = 0.0;
        for (PlanoFamilia familia : familias) {
            total += familia.totalRepresentatividade;
        }
        return NumericUtils.roundTo6(total);
    }

    private String normalizarFamilia(String familia) {
        return familia == null || familia.trim().isEmpty() ? "SEM_FAMILIA" : familia.trim();
    }

    private List<Carreta> particionarPorPrioridadeCompletaDepoisMix(List<ItemPedido> itens, double capacidade) {
        List<ItemPedido> priorizados = ordenarPriorizados(itens);
        List<ItemPedido> demais = new ArrayList<ItemPedido>();
        for (ItemPedido item : itens) {
            if (!item.isPriorizado() && item.getOrdemPrioridade() <= 0) {
                demais.add(item);
            }
        }

        List<Carreta> carretas = new ArrayList<Carreta>();
        MixFases fasesPriorizadas = criarFasesPrioridade(priorizados);
        MixFases fasesMix = criarFasesMix(ordenarPorPercentualGrupo(demais));

        preencherFilasSequencialmente(fasesPriorizadas.saldosInteiros, capacidade, carretas, true);
        preencherFilasSequencialmente(fasesMix.primeiroPaletePorSku, capacidade, carretas, true);
        preencherFilasSequencialmente(fasesMix.saldosInteiros, capacidade, carretas, true);

        List<FilaMix> quebras = new ArrayList<FilaMix>();
        quebras.addAll(fasesPriorizadas.quebrados);
        quebras.addAll(fasesMix.quebrados);
        preencherMixSkus(quebras, capacidade, carretas);

        return carretas;
    }

    private void preencherMixSkusPorFases(List<ItemPedido> itens, double capacidade, List<Carreta> carretas) {
        MixFases fases = criarFasesMix(itens);
        preencherFilasSequencialmente(fases.primeiroPaletePorSku, capacidade, carretas, true);
        preencherFilasSequencialmente(fases.saldosInteiros, capacidade, carretas, true);
        preencherMixSkus(fases.quebrados, capacidade, carretas);
    }

    private MixFases criarFasesMix(List<ItemPedido> itens) {
        MixFases fases = new MixFases();
        List<FilaMix> saldosInteirosCompactos = new ArrayList<FilaMix>();
        for (ItemPedido item : itens) {
            double total = NumericUtils.roundTo6(item.getQtdPalete());
            if (total <= TOL) {
                continue;
            }

            String grupo = normalizarGrupo(item.getGrupoMix());
            if (total >= 1.0 - TOL) {
                fases.primeiroPaletePorSku.add(new FilaMix(item, grupo, Math.min(1.0, total)));
                double saldoAposPrimeiro = NumericUtils.roundTo6(total - 1.0);
                double saldoInteiro = Math.floor(saldoAposPrimeiro + TOL);
                if (saldoInteiro > TOL) {
                    saldosInteirosCompactos.add(new FilaMix(item, grupo, saldoInteiro));
                }
                double quebra = NumericUtils.roundTo6(saldoAposPrimeiro - saldoInteiro);
                if (quebra > TOL) {
                    fases.quebrados.add(new FilaMix(item, grupo, quebra));
                }
            } else {
                fases.quebrados.add(new FilaMix(item, grupo, total));
            }
        }
        fases.saldosInteiros.addAll(expandirSaldosInteirosPorPalete(saldosInteirosCompactos));
        return fases;
    }

    private MixFases criarFasesPrioridade(List<ItemPedido> itens) {
        MixFases fases = new MixFases();
        for (ItemPedido item : itens) {
            double total = NumericUtils.roundTo6(item.getQtdPalete());
            if (total <= TOL) {
                continue;
            }

            String grupo = normalizarGrupo(item.getGrupoMix());
            int paletesInteiros = (int) Math.floor(total + TOL);
            for (int i = 0; i < paletesInteiros; i++) {
                fases.saldosInteiros.add(new FilaMix(item, grupo, 1.0));
            }

            double quebra = NumericUtils.roundTo6(total - paletesInteiros);
            if (quebra > TOL) {
                fases.quebrados.add(new FilaMix(item, grupo, quebra));
            }
        }
        return fases;
    }

    private List<FilaMix> expandirSaldosInteirosPorPalete(List<FilaMix> saldosCompactos) {
        List<FilaMix> expandidos = new ArrayList<FilaMix>();
        int nivel = 1;
        boolean adicionou = true;
        while (adicionou) {
            adicionou = false;
            for (FilaMix fila : saldosCompactos) {
                if (fila.restante + TOL >= nivel) {
                    expandidos.add(new FilaMix(fila.item, fila.grupo, 1.0));
                    adicionou = true;
                }
            }
            nivel++;
        }
        return expandidos;
    }

    private void alocarItemPriorizadoCompleto(ItemPedido item, double capacidade, List<Carreta> carretas) {
        double restante = item.getQtdPalete();
        while (restante > TOL) {
            Carreta carreta = carretaAtualOuNova(carretas, capacidade);
            double capacidadeLivre = carreta.getCapacidadeLivre();
            if (restante > capacidadeLivre + TOL && restante <= capacidade + TOL) {
                carreta = novaCarreta(carretas, capacidade);
                capacidadeLivre = carreta.getCapacidadeLivre();
            }

            double qtdAlocar = restante <= capacidadeLivre + TOL ? restante : capacidadeLivre;
            qtdAlocar = ajustarPaletesParaVolume(item, qtdAlocar, restante, capacidadeLivre);
            if (qtdAlocar <= TOL) {
                carreta = novaCarreta(carretas, capacidade);
                qtdAlocar = ajustarPaletesParaVolume(item, Math.min(restante, capacidade), restante, carreta.getCapacidadeLivre());
                if (qtdAlocar <= TOL) {
                    throw new IllegalStateException("Capacidade insuficiente para alocar uma caixa inteira do SKU priorizado " + item.getCodProd() + ".");
                }
            }

            adicionarItemCarreta(carreta, item, qtdAlocar);
            carreta.setCapacidadeLivre(NumericUtils.roundTo6(carreta.getCapacidadeLivre() - qtdAlocar));
            restante = NumericUtils.roundTo6(restante - qtdAlocar);
        }
    }

    private void preencherMixSkus(List<FilaMix> filas, double capacidade, List<Carreta> carretas) {

        while (totalRestanteFilas(filas) > TOL) {
            Carreta carreta = carretaAtualOuNova(carretas, capacidade);
            double capacidadeLivre = carreta.getCapacidadeLivre();
            int guard = 0;

            while (capacidadeLivre > TOL && totalRestanteFilas(filas) > TOL && guard < 10000) {
                guard++;
                List<FilaMix> ativas = filasComSaldo(filas);
                if (ativas.isEmpty()) {
                    break;
                }

                double totalRestante = totalRestanteFilas(ativas);
                double capacidadeEfetiva = Math.min(capacidadeLivre, totalRestante);
                double cotaPorSku = capacidadeEfetiva / ativas.size();
                boolean alocouNaRodada = false;

                for (FilaMix fila : ativas) {
                    if (capacidadeLivre <= TOL) {
                        break;
                    }
                    if (!contemSku(carreta, fila.item.getCodProd()) && contarSkus(carreta) >= MAX_SKUS_POR_OEP) {
                        continue;
                    }

                    double qtdAlocar = Math.min(fila.restante, Math.min(cotaPorSku, capacidadeLivre));
                    if (qtdAlocar <= TOL && fila.restante > TOL) {
                        qtdAlocar = Math.min(fila.restante, capacidadeLivre);
                    }

                    qtdAlocar = ajustarPaletesParaVolume(fila.item, qtdAlocar, fila.restante, capacidadeLivre);
                    if (qtdAlocar <= TOL) {
                        continue;
                    }

                    adicionarItemCarreta(carreta, fila.item, qtdAlocar);
                    capacidadeLivre = NumericUtils.roundTo6(capacidadeLivre - qtdAlocar);
                    fila.restante = NumericUtils.roundTo6(fila.restante - qtdAlocar);
                    alocouNaRodada = true;
                }

                removerFilasSemSaldo(filas);
                if (!alocouNaRodada) {
                    if (!carreta.getItens().isEmpty() && capacidadeLivre > TOL) {
                        novaCarreta(carretas, capacidade);
                    }
                    break;
                }
            }

            carreta.setCapacidadeLivre(capacidadeLivre);
        }
    }

    private void preencherFilasSequencialmente(
            List<FilaMix> filas,
            double capacidade,
            List<Carreta> carretas,
            boolean manterFilaInteiraQuandoPossivel
    ) {
        while (totalRestanteFilas(filas) > TOL) {
            removerFilasSemSaldo(filas);
            if (filas.isEmpty()) {
                return;
            }

            FilaMix fila = filas.get(0);
            Carreta carreta = carretaAtualOuNova(carretas, capacidade);
            double capacidadeLivre = carreta.getCapacidadeLivre();
            if (!contemSku(carreta, fila.item.getCodProd()) && contarSkus(carreta) >= MAX_SKUS_POR_OEP) {
                carreta = novaCarreta(carretas, capacidade);
                capacidadeLivre = carreta.getCapacidadeLivre();
            }
            if (manterFilaInteiraQuandoPossivel
                    && !carreta.getItens().isEmpty()
                    && fila.restante > capacidadeLivre + TOL
                    && fila.restante <= capacidade + TOL) {
                carreta = novaCarreta(carretas, capacidade);
                capacidadeLivre = carreta.getCapacidadeLivre();
            }

            double qtdAlocar = Math.min(fila.restante, capacidadeLivre);
            qtdAlocar = ajustarPaletesParaVolume(fila.item, qtdAlocar, fila.restante, capacidadeLivre);
            if (qtdAlocar <= TOL) {
                if (carreta.getItens().isEmpty()) {
                    throw new IllegalStateException("Capacidade insuficiente para alocar uma caixa inteira do SKU " + fila.item.getCodProd() + ".");
                }
                novaCarreta(carretas, capacidade);
                continue;
            }

            adicionarItemCarreta(carreta, fila.item, qtdAlocar);
            capacidadeLivre = NumericUtils.roundTo6(capacidadeLivre - qtdAlocar);
            carreta.setCapacidadeLivre(capacidadeLivre);
            fila.restante = NumericUtils.roundTo6(fila.restante - qtdAlocar);
        }
    }

    private Carreta carretaAtualOuNova(List<Carreta> carretas, double capacidade) {
        if (!carretas.isEmpty()) {
            Carreta atual = carretas.get(carretas.size() - 1);
            if (atual.getCapacidadeLivre() > TOL) {
                return atual;
            }
        }
        return novaCarreta(carretas, capacidade);
    }

    private Carreta novaCarreta(List<Carreta> carretas, double capacidade) {
        Carreta carreta = new Carreta(carretas.size() + 1, capacidade);
        carretas.add(carreta);
        return carreta;
    }

    private List<FilaMix> filasComSaldo(List<FilaMix> filas) {
        List<FilaMix> ativas = new ArrayList<FilaMix>();
        for (FilaMix fila : filas) {
            if (fila.restante > TOL) {
                ativas.add(fila);
            }
        }
        return ativas;
    }

    private double totalRestanteFilas(List<FilaMix> filas) {
        double total = 0.0;
        for (FilaMix fila : filas) {
            total += fila.restante;
        }
        return NumericUtils.roundTo6(total);
    }

    private void removerFilasSemSaldo(List<FilaMix> filas) {
        for (int i = filas.size() - 1; i >= 0; i--) {
            if (filas.get(i).restante <= TOL) {
                filas.remove(i);
            }
        }
    }

    private int escolherIndiceMix(List<FilaMix> filas, Set<String> gruposUsados) {
        for (int i = 0; i < filas.size(); i++) {
            if (!gruposUsados.contains(filas.get(i).grupo)) {
                return i;
            }
        }
        return 0;
    }

    private List<Carreta> particionarPorPercentualGrupo(List<ItemPedido> itens, double capacidade) {
        Map<String, List<FilaMix>> filasPorGrupo = new HashMap<String, List<FilaMix>>();
        Map<String, Double> totalPorGrupo = new HashMap<String, Double>();

        for (ItemPedido item : ordenarPorPercentualGrupo(itens)) {
            String grupo = normalizarGrupo(item.getGrupoMix());
            List<FilaMix> filas = filasPorGrupo.get(grupo);
            if (filas == null) {
                filas = new ArrayList<FilaMix>();
                filasPorGrupo.put(grupo, filas);
                totalPorGrupo.put(grupo, 0.0);
            }
            filas.add(new FilaMix(item, grupo, item.getQtdPalete()));
            totalPorGrupo.put(grupo, totalPorGrupo.get(grupo) + item.getQtdPalete());
        }

        List<Carreta> carretas = new ArrayList<Carreta>();
        int numeroCarreta = 0;

        while (totalRestante(filasPorGrupo) > TOL) {
            numeroCarreta++;
            Carreta carreta = new Carreta(numeroCarreta, capacidade);
            double capacidadeLivre = capacidade;
            double totalInicioCarreta = totalRestante(filasPorGrupo);
            Map<String, Double> alocadoNaCarreta = new HashMap<String, Double>();
            int guard = 0;

            while (capacidadeLivre > TOL && totalRestante(filasPorGrupo) > TOL && guard < 10000) {
                guard++;
                String grupo = escolherGrupoPorDeficit(filasPorGrupo, totalPorGrupo, alocadoNaCarreta, capacidade, totalInicioCarreta);
                FilaMix fila = primeiraFilaComSaldo(filasPorGrupo.get(grupo));
                if (fila == null) {
                    removerGrupoVazio(filasPorGrupo, grupo);
                    continue;
                }

                double metaGrupo = metaGrupoNaCarreta(grupo, totalPorGrupo, capacidade, totalInicioCarreta);
                double jaAlocado = valorOuZero(alocadoNaCarreta.get(grupo));
                double deficit = NumericUtils.roundTo6(metaGrupo - jaAlocado);
                double limitePercentual = deficit > TOL ? deficit : capacidadeLivre;
                double qtdAlocar = menor(fila.restante, capacidadeLivre, limitePercentual);

                if (qtdAlocar <= TOL) {
                    qtdAlocar = menor(fila.restante, capacidadeLivre);
                }

                qtdAlocar = ajustarPaletesParaVolume(fila.item, qtdAlocar, fila.restante, capacidadeLivre);
                if (qtdAlocar <= TOL) {
                    break;
                }

                carreta.getItens().add(criarItemCarreta(fila.item, qtdAlocar));
                capacidadeLivre = NumericUtils.roundTo6(capacidadeLivre - qtdAlocar);
                fila.restante = NumericUtils.roundTo6(fila.restante - qtdAlocar);
                alocadoNaCarreta.put(grupo, jaAlocado + qtdAlocar);

                if (fila.restante <= TOL) {
                    removerFilaSemSaldo(filasPorGrupo.get(grupo));
                }
            }

            carreta.setCapacidadeLivre(capacidadeLivre);
            carretas.add(carreta);
        }

        return carretas;
    }

    private String escolherGrupoPorDeficit(
            Map<String, List<FilaMix>> filasPorGrupo,
            Map<String, Double> totalPorGrupo,
            Map<String, Double> alocadoNaCarreta,
            double capacidade,
            double totalInicioCarreta
    ) {
        String melhorGrupo = null;
        double maiorDeficit = -Double.MAX_VALUE;

        for (String grupo : filasPorGrupo.keySet()) {
            if (primeiraFilaComSaldo(filasPorGrupo.get(grupo)) == null) {
                continue;
            }

            double meta = metaGrupoNaCarreta(grupo, totalPorGrupo, capacidade, totalInicioCarreta);
            double deficit = meta - valorOuZero(alocadoNaCarreta.get(grupo));
            if (melhorGrupo == null || deficit > maiorDeficit) {
                melhorGrupo = grupo;
                maiorDeficit = deficit;
            }
        }

        return melhorGrupo;
    }

    private double metaGrupoNaCarreta(
            String grupo,
            Map<String, Double> totalPorGrupo,
            double capacidade,
            double totalInicioCarreta
    ) {
        if (totalInicioCarreta <= TOL) {
            return 0.0;
        }

        double capacidadeEfetiva = Math.min(capacidade, totalInicioCarreta);
        return capacidadeEfetiva * (valorOuZero(totalPorGrupo.get(grupo)) / totalInicioCarreta);
    }

    private double totalRestante(Map<String, List<FilaMix>> filasPorGrupo) {
        double total = 0.0;
        for (List<FilaMix> filas : filasPorGrupo.values()) {
            for (FilaMix fila : filas) {
                total += fila.restante;
            }
        }
        return NumericUtils.roundTo6(total);
    }

    private FilaMix primeiraFilaComSaldo(List<FilaMix> filas) {
        if (filas == null) {
            return null;
        }
        for (FilaMix fila : filas) {
            if (fila.restante > TOL) {
                return fila;
            }
        }
        return null;
    }

    private void removerFilaSemSaldo(List<FilaMix> filas) {
        if (filas == null) {
            return;
        }
        for (int i = filas.size() - 1; i >= 0; i--) {
            if (filas.get(i).restante <= TOL) {
                filas.remove(i);
            }
        }
    }

    private void removerGrupoVazio(Map<String, List<FilaMix>> filasPorGrupo, String grupo) {
        List<FilaMix> filas = filasPorGrupo.get(grupo);
        if (filas == null || filas.isEmpty()) {
            filasPorGrupo.remove(grupo);
        }
    }

    private boolean temItensPriorizados(List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            if (item.isPriorizado() || item.getOrdemPrioridade() > 0) {
                return true;
            }
        }
        return false;
    }

    private double menor(double primeiro, double segundo) {
        return primeiro < segundo ? primeiro : segundo;
    }

    private double menor(double primeiro, double segundo, double terceiro) {
        return menor(menor(primeiro, segundo), terceiro);
    }

    private double valorOuZero(Double value) {
        return value == null ? 0.0 : value;
    }

    private List<ItemPedido> ordenarParaMix(List<ItemPedido> itens) {
        List<ItemPedido> demais = new ArrayList<ItemPedido>();

        for (ItemPedido item : itens) {
            if (!item.isPriorizado() && item.getOrdemPrioridade() <= 0) {
                demais.add(item);
            }
        }

        List<ItemPedido> resultado = new ArrayList<ItemPedido>();
        resultado.addAll(ordenarPriorizados(itens));
        resultado.addAll(ordenarPorPercentualGrupo(demais));
        return resultado;
    }

    private List<ItemPedido> ordenarPriorizados(List<ItemPedido> itens) {
        List<ItemPedido> priorizados = new ArrayList<ItemPedido>();
        for (ItemPedido item : itens) {
            if (item.isPriorizado() || item.getOrdemPrioridade() > 0) {
                priorizados.add(item);
            }
        }

        Collections.sort(priorizados, new Comparator<ItemPedido>() {
            @Override
            public int compare(ItemPedido a, ItemPedido b) {
                int ordemA = a.getOrdemPrioridade() > 0 ? a.getOrdemPrioridade() : Integer.MAX_VALUE;
                int ordemB = b.getOrdemPrioridade() > 0 ? b.getOrdemPrioridade() : Integer.MAX_VALUE;
                if (ordemA != ordemB) {
                    return ordemA - ordemB;
                }
                return Double.compare(b.getQtdPalete(), a.getQtdPalete());
            }
        });
        return priorizados;
    }

    private List<ItemPedido> ordenarPorPercentualGrupo(List<ItemPedido> itens) {
        Map<String, Double> totalPorGrupo = new HashMap<String, Double>();
        for (ItemPedido item : itens) {
            String grupo = normalizarGrupo(item.getGrupoMix());
            Double total = totalPorGrupo.get(grupo);
            totalPorGrupo.put(grupo, (total == null ? 0.0 : total) + item.getQtdPalete());
        }

        List<ItemPedido> ordenados = new ArrayList<ItemPedido>(itens);
        Collections.sort(ordenados, new Comparator<ItemPedido>() {
            @Override
            public int compare(ItemPedido a, ItemPedido b) {
                String grupoA = normalizarGrupo(a.getGrupoMix());
                String grupoB = normalizarGrupo(b.getGrupoMix());
                int totalGrupo = Double.compare(totalPorGrupo.get(grupoB), totalPorGrupo.get(grupoA));
                if (totalGrupo != 0) {
                    return totalGrupo;
                }
                int grupo = grupoA.compareTo(grupoB);
                if (grupo != 0) {
                    return grupo;
                }
                return Double.compare(b.getQtdPalete(), a.getQtdPalete());
            }
        });
        return ordenados;
    }

    private String normalizarGrupo(String grupo) {
        return grupo == null || grupo.trim().isEmpty() ? "SEM_GRUPO" : grupo.trim();
    }

    private void alocarNasCarretas(
            ItemPedido item,
            double qtdPaletes,
            double capacidade,
            List<Carreta> carretas,
            int[] carretaAtual,
            double[] capacidadeLivre
    ) {
        while (qtdPaletes > TOL) {
            if (capacidadeLivre[0] <= TOL) {
                carretaAtual[0]++;
                capacidadeLivre[0] = capacidade;
                carretas.add(new Carreta(carretaAtual[0], capacidade));
            }

            Carreta carretaCorrente = carretas.get(carretas.size() - 1);
            double qtdAlocar = qtdPaletes <= capacidadeLivre[0] + TOL ? qtdPaletes : capacidadeLivre[0];
            qtdAlocar = ajustarPaletesParaVolume(item, qtdAlocar, qtdPaletes, capacidadeLivre[0]);
            if (qtdAlocar <= TOL) {
                break;
            }

            carretaCorrente.getItens().add(criarItemCarreta(item, qtdAlocar));

            capacidadeLivre[0] = NumericUtils.roundTo6(capacidadeLivre[0] - qtdAlocar);
            carretaCorrente.setCapacidadeLivre(capacidadeLivre[0]);
            qtdPaletes = NumericUtils.roundTo6(qtdPaletes - qtdAlocar);
        }
    }

    private boolean isInvalido(ItemPedido item) {
        return item == null
                || item.getCodProd() == null
                || item.getCodProd().trim().isEmpty()
                || item.getQtdPalete() <= 0;
    }

    private double ajustarPaletesParaVolume(ItemPedido item, double qtdAlocar, double restanteItem, double capacidadeLivre) {
        if (!exigeQuantidadeInteira(item)) {
            return NumericUtils.roundTo6(qtdAlocar);
        }

        double fator = item.getUndPorPalete();
        if (fator <= TOL) {
            return NumericUtils.roundTo6(qtdAlocar);
        }

        long unidadesRestantes = Math.round(restanteItem * fator);
        long unidadesPorCapacidade = (long) Math.floor((capacidadeLivre * fator) + TOL);
        long unidadesDesejadas = Math.round(qtdAlocar * fator);

        if (unidadesDesejadas <= 0 && unidadesRestantes > 0 && unidadesPorCapacidade > 0) {
            unidadesDesejadas = 1;
        }

        long unidadesAlocar = Math.min(unidadesDesejadas, Math.min(unidadesRestantes, unidadesPorCapacidade));
        if (unidadesAlocar <= 0) {
            return 0.0;
        }

        return NumericUtils.roundTo6(unidadesAlocar / fator);
    }

    private boolean exigeQuantidadeInteira(ItemPedido item) {
        return item != null;
    }

    private void adicionarItemCarreta(Carreta carreta, ItemPedido item, double qtdAlocar) {
        for (ItemCarreta existente : carreta.getItens()) {
            if (isMesmoItemOrigem(existente, item)) {
                double novoQtdPalete = NumericUtils.roundTo6(existente.getQtdAlocada() + qtdAlocar);
                existente.setQtdAlocada(novoQtdPalete);
                existente.setQtdUnidade(NumericUtils.roundTo6(novoQtdPalete * item.getUndPorPalete()));
                existente.setQtdCaixa(NumericUtils.roundTo6(novoQtdPalete * item.getCxPorPalete()));
                return;
            }
        }

        carreta.getItens().add(criarItemCarreta(item, qtdAlocar));
    }

    private boolean isMesmoItemOrigem(ItemCarreta existente, ItemPedido item) {
        return existente.getSequenciaOrigem() == item.getSequenciaOrigem()
                && textoIgual(existente.getCodProd(), item.getCodProd())
                && textoIgual(existente.getControle(), item.getControle());
    }

    private boolean textoIgual(String a, String b) {
        if (a == null) {
            return b == null || b.trim().isEmpty();
        }
        if (b == null) {
            return a.trim().isEmpty();
        }
        return a.trim().equals(b.trim());
    }

    private ItemCarreta criarItemCarreta(ItemPedido item, double qtdAlocar) {
        ItemCarreta itemCarreta = new ItemCarreta();
        itemCarreta.setCodProd(item.getCodProd());
        itemCarreta.setDescProd(item.getDescProd());
        itemCarreta.setQtdAlocada(NumericUtils.roundTo6(qtdAlocar));
        itemCarreta.setQtdOrigem(item.getQtdOrigem());
        itemCarreta.setQtdUnidade(NumericUtils.roundTo6(qtdAlocar * item.getUndPorPalete()));
        itemCarreta.setQtdCaixa(NumericUtils.roundTo6(qtdAlocar * item.getCxPorPalete()));
        itemCarreta.setSequenciaOrigem(item.getSequenciaOrigem());
        itemCarreta.setCodLocalOrig(item.getCodLocalOrig());
        itemCarreta.setControle(item.getControle());
        itemCarreta.setStatusLote(item.getStatusLote());
        itemCarreta.setCodVol(item.getCodVol());
        itemCarreta.setUsoProd(item.getUsoProd());
        itemCarreta.setVlrUnit(item.getVlrUnit());
        return itemCarreta;
    }

    private static class FilaMix {
        private final ItemPedido item;
        private final String grupo;
        private double restante;

        private FilaMix(ItemPedido item, String grupo, double restante) {
            this.item = item;
            this.grupo = grupo;
            this.restante = restante;
        }
    }

    private static class MixFases {
        private final List<FilaMix> primeiroPaletePorSku = new ArrayList<FilaMix>();
        private final List<FilaMix> saldosInteiros = new ArrayList<FilaMix>();
        private final List<FilaMix> quebrados = new ArrayList<FilaMix>();
    }

    private static class PlanoFamilia {
        private final String codigo;
        private final String descricao;
        private final List<SaldoFamiliaSku> skus = new ArrayList<SaldoFamiliaSku>();
        private double totalOriginal;
        private double totalRepresentatividade;
        private int ordem;

        private PlanoFamilia(String codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
        }
    }

    private static class SaldoFamiliaSku {
        private final ItemPedido item;
        private final PlanoFamilia familia;
        private int inteirosRestantes;
        private double pickingRestante;

        private SaldoFamiliaSku(
                ItemPedido item,
                PlanoFamilia familia,
                int inteirosRestantes,
                double pickingRestante
        ) {
            this.item = item;
            this.familia = familia;
            this.inteirosRestantes = inteirosRestantes;
            this.pickingRestante = pickingRestante;
        }
    }

    private static class SaldoReconstrucao {
        private final ItemPedido item;
        private final PlanoFamilia familia;
        private final int original;
        private int restante;

        private SaldoReconstrucao(ItemPedido item, PlanoFamilia familia, int quantidade) {
            this.item = item;
            this.familia = familia;
            this.original = quantidade;
            this.restante = quantidade;
        }
    }
}
