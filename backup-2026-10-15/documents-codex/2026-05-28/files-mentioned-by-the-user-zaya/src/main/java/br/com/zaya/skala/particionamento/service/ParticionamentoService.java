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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParticionamentoService {
    public static final double TOL = 0.000001;

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

        return particionarPorMixDeSkus(ordenarParaMix(itensValidos), capacidade);
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
        List<FilaMix> filas = new ArrayList<FilaMix>();
        for (ItemPedido item : itens) {
            filas.add(new FilaMix(item, normalizarGrupo(item.getGrupoMix()), item.getQtdPalete()));
        }

        List<Carreta> carretas = new ArrayList<Carreta>();
        int numeroCarreta = 0;

        while (totalRestanteFilas(filas) > TOL) {
            numeroCarreta++;
            Carreta carreta = new Carreta(numeroCarreta, capacidade);
            double capacidadeLivre = capacidade;
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
                    break;
                }
            }

            carreta.setCapacidadeLivre(capacidadeLivre);
            carretas.add(carreta);
        }

        return carretas;
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
        List<ItemPedido> priorizados = new ArrayList<ItemPedido>();
        List<ItemPedido> demais = new ArrayList<ItemPedido>();

        for (ItemPedido item : itens) {
            if (item.isPriorizado() || item.getOrdemPrioridade() > 0) {
                priorizados.add(item);
            } else {
                demais.add(item);
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

        List<ItemPedido> resultado = new ArrayList<ItemPedido>();
        resultado.addAll(priorizados);
        resultado.addAll(ordenarPorPercentualGrupo(demais));
        return resultado;
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
}
