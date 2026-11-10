package br.com.zaya.belmicro.rateio.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class AgrupadorAuxiliares {
    private AgrupadorAuxiliares() { }

    private static final Comparator<DestinoPeso> ORDEM = new Comparator<DestinoPeso>() {
        public int compare(DestinoPeso a, DestinoPeso b) {
            int c = Integer.compare(a.getCodEmp(), b.getCodEmp());
            if (c == 0) c = Integer.compare(a.getCodCencus(), b.getCodCencus());
            if (c == 0) c = a.getComponente().compareTo(b.getComponente());
            if (c == 0) c = a.getChaveCompatibilidade().compareTo(b.getChaveCompatibilidade());
            return c;
        }
    };

    public static List<DestinoPeso> consolidarEOrdenar(List<DestinoPeso> destinos) {
        if (destinos == null || destinos.isEmpty()) {
            throw new ErroRateio("BASE_INCOMPLETA", "Composicao ausente.");
        }
        Map<DestinoPeso, List<DestinoPeso>> porDestino = new TreeMap<DestinoPeso, List<DestinoPeso>>(ORDEM);
        for (DestinoPeso destino : destinos) {
            if (destino == null) throw new ErroRateio("BASE_INCOMPLETA", "Destino ausente.");
            List<DestinoPeso> repetidos = porDestino.get(destino);
            if (repetidos == null) {
                repetidos = new ArrayList<DestinoPeso>();
                porDestino.put(destino, repetidos);
            }
            repetidos.add(destino);
        }
        List<DestinoPeso> resultado = new ArrayList<DestinoPeso>();
        for (Map.Entry<DestinoPeso, List<DestinoPeso>> entrada : porDestino.entrySet()) {
            DestinoPeso chave = entrada.getKey();
            BigDecimal peso = BigDecimal.ZERO;
            List<String> evidencias = new ArrayList<String>();
            for (DestinoPeso destino : entrada.getValue()) {
                peso = peso.add(destino.getPeso());
                evidencias.add(destino.getEvidencia());
            }
            Collections.sort(evidencias);
            // Framing preserves each source without delimiter collisions.
            StringBuilder evidencia = new StringBuilder();
            for (String fonte : evidencias) evidencia.append(fonte.length()).append(':').append(fonte);
            resultado.add(new DestinoPeso(chave.getCodEmp(), chave.getCodCencus(), chave.getComponente(),
                    peso, evidencia.toString(), chave.getChaveCompatibilidade()));
        }
        return Collections.unmodifiableList(resultado);
    }

    public static List<GrupoPrevisto> agrupar(List<ParcelaSimulacao> parcelas) {
        Map<Integer, Map<String, Map<Integer, BigDecimal>>> empresas =
                new TreeMap<Integer, Map<String, Map<Integer, BigDecimal>>>();
        for (ParcelaSimulacao parcela : parcelas) {
            if (parcela.isOrigem() || parcela.getValor().signum() == 0) continue;
            Map<String, Map<Integer, BigDecimal>> grupos = empresas.get(parcela.getCodEmp());
            if (grupos == null) {
                grupos = new TreeMap<String, Map<Integer, BigDecimal>>();
                empresas.put(parcela.getCodEmp(), grupos);
            }
            Map<Integer, BigDecimal> crs = grupos.get(parcela.getChaveCompatibilidade());
            if (crs == null) {
                crs = new TreeMap<Integer, BigDecimal>();
                grupos.put(parcela.getChaveCompatibilidade(), crs);
            }
            BigDecimal anterior = crs.get(parcela.getCodCencus());
            crs.put(parcela.getCodCencus(), parcela.getValor().add(anterior == null ? BigDecimal.ZERO : anterior));
        }
        List<GrupoPrevisto> resultado = new ArrayList<GrupoPrevisto>();
        for (Map.Entry<Integer, Map<String, Map<Integer, BigDecimal>>> empresa : empresas.entrySet()) {
            for (Map.Entry<String, Map<Integer, BigDecimal>> grupo : empresa.getValue().entrySet()) {
                List<BigDecimal> valores = new ArrayList<BigDecimal>(grupo.getValue().values());
                BigDecimal total = BigDecimal.ZERO;
                for (BigDecimal valor : valores) total = total.add(valor);
                List<BigDecimal> percentuais = DistribuidorProporcional.distribuir(new BigDecimal("100"), valores, 6);
                List<LinhaRateio> linhas = new ArrayList<LinhaRateio>();
                int i = 0;
                for (Map.Entry<Integer, BigDecimal> cr : grupo.getValue().entrySet()) {
                    linhas.add(new LinhaRateio(cr.getKey(), cr.getValue(), percentuais.get(i++)));
                }
                resultado.add(new GrupoPrevisto(empresa.getKey(), grupo.getKey(), total, linhas));
            }
        }
        return Collections.unmodifiableList(resultado);
    }

    /** Must be called with a homologated native adapter before financial creation. */
    public static void validarRepresentacao(List<GrupoPrevisto> grupos, RepresentacaoNativa mecanismo) {
        if (mecanismo == null) {
            throw new ErroRateio("RATEIO_NAO_REPRESENTAVEL", "Mecanismo nativo nao homologado.");
        }
        for (GrupoPrevisto grupo : grupos) {
            for (LinhaRateio linha : grupo.getLinhasPorCr()) {
                BigDecimal recuperado = mecanismo.recuperarValor(grupo, linha);
                if (recuperado == null || recuperado.compareTo(linha.getValor()) != 0) {
                    throw new ErroRateio("RATEIO_NAO_REPRESENTAVEL", "Percentual nativo nao reproduz os centavos previstos.");
                }
            }
        }
    }

    public interface RepresentacaoNativa {
        BigDecimal recuperarValor(GrupoPrevisto grupo, LinhaRateio linha);
    }
}
