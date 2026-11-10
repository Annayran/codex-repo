package br.com.zaya.belmicro.rateio;

import br.com.zaya.belmicro.rateio.dominio.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** Executable regression suite; throws on failure even without JVM assertions enabled. */
public final class TesteDominio {
    private static int cenarios;
    private static final CalculadoraRateio CALCULADORA = new CalculadoraRateio();

    public static void main(String[] args) {
        executar("CT-10 exemplo funcional", () -> {
            ResultadoCalculo r = calcular("10000", destino(1,10,"30"), destino(2,20,"25"),
                    destino(2,21,"15"), destino(3,30,"30"));
            igual(2, r.getGrupos().size());
            decimal("4000", r.getGrupos().get(0).getValorTotal());
            decimal("3000", r.getGrupos().get(1).getValorTotal());
            decimal("62.5", r.getGrupos().get(0).getPercentuaisInternos().get(0));
            decimal("37.5", r.getGrupos().get(0).getPercentuaisInternos().get(1));
            igual(6, r.getGrupos().get(0).getPercentuaisInternos().get(0).scale());
            decimal("3000", r.getParcelas().get(0).getValor());
        });
        executar("CT-11 somente origem", () -> {
            ResultadoCalculo r = calcular("100", destino(1,10,"100"));
            igual(0, r.getGrupos().size());
            decimal("100", r.getParcelas().get(0).getValor());
        });
        executar("CT-17 desempate estavel", () -> {
            ResultadoCalculo r = calcular("100", destino(3,30,"1"), destino(1,10,"1"), destino(2,20,"1"));
            decimal("33.34", r.getParcelas().get(0).getValor());
            decimal("33.33", r.getParcelas().get(1).getValor());
            decimal("66.66", r.getGrupos().get(0).getValorTotal().add(r.getGrupos().get(1).getValorTotal()));
        });
        executar("CT-18 componentes consolidados por CR", () -> {
            ResultadoCalculo r = calcular("100", new DestinoPeso(2,20,"VT", bd("30"), "a"),
                    new DestinoPeso(2,20,"FRETADO", bd("70"), "b"));
            igual(1, r.getGrupos().size());
            igual(1, r.getGrupos().get(0).getLinhasPorCr().size());
            decimal("100", r.getGrupos().get(0).getLinhasPorCr().get(0).getValor());
        });
        executar("CT-19 chave compativel separa grupos", () -> {
            ResultadoCalculo r = calcular("100", new DestinoPeso(2,20,"X", bd("50"), "", "A"),
                    new DestinoPeso(2,20,"X", bd("50"), "", "B"));
            igual(2, r.getGrupos().size());
        });
        executar("CT-20 CR unico 100 por cento", () -> {
            decimal("100.000000", calcular("100", destino(2,20,"1")).getGrupos().get(0).getPercentuaisInternos().get(0));
        });
        executar("CT-21 fechamento percentual", () -> {
            GrupoPrevisto g = calcular("0.03", destino(2,10,"1"), destino(2,20,"1"), destino(2,30,"1")).getGrupos().get(0);
            decimal("33.333334", g.getPercentuaisInternos().get(0));
            decimal("100", somar(g.getPercentuaisInternos()));
        });
        executar("parcelas arredondadas a zero preservadas", () -> {
            ResultadoCalculo r = calcular("0.01", destino(1,10,"1"), destino(2,20,"1"), destino(3,30,"1"));
            igual(3, r.getParcelas().size());
            igual(0, r.getGrupos().size());
        });
        executar("duplicatas consolidadas antes dos centavos", () -> {
            ResultadoCalculo r = calcular("0.02", destino(1,10,"1"), destino(2,20,"1"), destino(2,20,"1"));
            igual(2, r.getParcelas().size());
            decimal("0.01", r.getParcelas().get(0).getValor());
            decimal("0.01", r.getParcelas().get(1).getValor());
            decimal("2", r.getParcelas().get(1).getPeso());
        });
        executar("pesos decimais exatos e peso zero", () -> {
            List<BigDecimal> r = DistribuidorProporcional.distribuir(bd("0.01"), Arrays.asList(bd("0"), bd("1.00000000000000000001"), bd("1")), 2);
            decimal("0", r.get(0)); decimal("0.01", r.get(1)); decimal("0", r.get(2));
        });
        executar("bases invalidas", () -> {
            for (String base : Arrays.asList("0", "-1", "1.001")) {
                erro("BASE_INCOMPLETA", () -> calcular(base, destino(1,10,"1")));
            }
            erro("BASE_INCOMPLETA", () -> CALCULADORA.calcular(null, Arrays.asList(destino(1,10,"1")), 1));
        });
        executar("pesos e destinos invalidos", () -> {
            erro("BASE_INCOMPLETA", () -> calcular("1", destino(1,10,"0")));
            erro("BASE_INCOMPLETA", () -> new DestinoPeso(1,10,"X", null,""));
            erro("BASE_INCOMPLETA", () -> destino(1,10,"-1"));
            erro("BASE_INCOMPLETA", () -> CALCULADORA.calcular(bd("1"), Collections.<DestinoPeso>emptyList(), 1));
            erro("CADASTRO_INVALIDO", () -> destino(1,0,"1"));
        });
        executar("CT-04 composicao manual invalida", () -> {
            erro("COMPOSICAO_INVALIDA", () -> ComposicaoManual.validar(Arrays.asList(destino(1,10,"99"))));
            erro("COMPOSICAO_INVALIDA", () -> ComposicaoManual.validar(Arrays.asList(destino(1,10,"0"),destino(2,20,"100"))));
            erro("COMPOSICAO_INVALIDA", () -> ComposicaoManual.validar(Arrays.asList(destino(1,10,"0.0000001"),destino(2,20,"99.9999999"))));
            erro("COMPOSICAO_INVALIDA", () -> ComposicaoManual.validar(null));
        });
        executar("composicao valida e sem mutacao", () -> {
            List<DestinoPeso> entrada = new ArrayList<DestinoPeso>(Arrays.asList(destino(2,20,"25.0000000"),destino(1,10,"75")));
            ResultadoCalculo r = CALCULADORA.calcular(bd("100"), ComposicaoManual.validar(entrada), 1);
            igual(2, entrada.get(0).getCodEmp());
            try { r.getParcelas().clear(); throw new AssertionError("Lista mutavel"); }
            catch (UnsupportedOperationException esperado) { }
        });
        executar("validacao nativa exige adaptador e centavos exatos", () -> {
            List<GrupoPrevisto> grupos = calcular("100",destino(2,20,"1"),destino(2,21,"1")).getGrupos();
            erro("RATEIO_NAO_REPRESENTAVEL", () -> AgrupadorAuxiliares.validarRepresentacao(grupos, null));
            erro("RATEIO_NAO_REPRESENTAVEL", () -> AgrupadorAuxiliares.validarRepresentacao(grupos, (g,l) -> bd("0")));
            AgrupadorAuxiliares.validarRepresentacao(grupos, (g,l) -> g.getValorTotal().multiply(l.getPercentual()).divide(bd("100")).setScale(2,RoundingMode.HALF_UP));
        });
        executar("hash invariante por ordem e escala numerica", () -> {
            ResultadoCalculo a = calcular("100.00",destino(1,10,"30.0"),destino(2,20,"70"));
            ResultadoCalculo b = calcular("100",destino(2,20,"70.00"),destino(1,10,"30"));
            igual(hash(a, "1", Collections.<String,String>emptyMap()), hash(b, "1", Collections.<String,String>emptyMap()));
        });
        executar("hash preserva evidencias de duplicatas em ordem estavel", () -> {
            DestinoPeso a = new DestinoPeso(2,20,"X",bd("1"),"fonte/A");
            DestinoPeso b = new DestinoPeso(2,20,"X",bd("1"),"fonte/B");
            igual(hash(calcular("100", a,b),"1",Collections.<String,String>emptyMap()),
                    hash(calcular("100", b,a),"1",Collections.<String,String>emptyMap()));
        });
        executar("hash muda com evidencia e compatibilidade", () -> {
            String a = hash(calcular("100",new DestinoPeso(2,20,"X",bd("1"),"a","A")),"1",Collections.<String,String>emptyMap());
            diferente(a,hash(calcular("100",new DestinoPeso(2,20,"X",bd("1"),"b","A")),"1",Collections.<String,String>emptyMap()));
            diferente(a,hash(calcular("100",new DestinoPeso(2,20,"X",bd("1"),"a","B")),"1",Collections.<String,String>emptyMap()));
        });
        executar("hash material sem colisao por separadores ou unicode", () -> {
            ResultadoCalculo r = calcular("100",destino(2,20,"1"));
            Map<String,String> a = new HashMap<String,String>(); a.put("natureza/A", "B/ç😀");
            Map<String,String> b = new HashMap<String,String>(); b.put("natureza", "A/B/ç😀");
            diferente(hash(r,"1",a),hash(r,"1",b));
            diferente(hash(r,"1",a),hash(r,"2",a));
            igual(64, hash(r,"1",a).length());
        });
        executar("hash cobre origem competencia modalidade e cabecalho", () -> {
            ResultadoCalculo r = calcular("100",destino(2,20,"1"));
            String referencia = hash(r,"1",Collections.<String,String>emptyMap());
            diferente(referencia,HashComposicao.calcular(new ContextoHash("1","TITULO",1,1,1,null,LocalDate.of(2026,10,1),"MANUAL","",Collections.<String,String>emptyMap(),r)));
            diferente(referencia,HashComposicao.calcular(new ContextoHash("1","NOTA",1,1,1,null,LocalDate.of(2026,9,1),"MANUAL","",Collections.<String,String>emptyMap(),r)));
            diferente(referencia,HashComposicao.calcular(new ContextoHash("1","NOTA",1,1,1,null,LocalDate.of(2026,10,1),"CRITERIO","1:1",Collections.<String,String>emptyMap(),r)));
            Map<String,String> campos = new HashMap<String,String>(); campos.put("TOP", "2");
            diferente(referencia,hash(r,"1",campos));
        });
        executar("1000 composicoes conservam totais e determinismo", () -> {
            Random random = new Random(20261026L);
            for (int t = 0; t < 1000; t++) {
                List<DestinoPeso> destinos = new ArrayList<DestinoPeso>();
                int n = 1 + random.nextInt(20);
                for (int i = 0; i < n; i++) destinos.add(destino(1+random.nextInt(4),10+random.nextInt(5),String.valueOf(1+random.nextInt(1000))));
                BigDecimal base = BigDecimal.valueOf(1+random.nextInt(10000000),2);
                ResultadoCalculo r = CALCULADORA.calcular(base,destinos,1);
                BigDecimal soma = BigDecimal.ZERO;
                BigDecimal outros = BigDecimal.ZERO;
                for (ParcelaSimulacao p : r.getParcelas()) {
                    soma = soma.add(p.getValor());
                    if (p.getValor().signum() < 0) throw new AssertionError("Valor negativo");
                    if (!p.isOrigem()) outros = outros.add(p.getValor());
                }
                decimal(base.toPlainString(),soma);
                BigDecimal auxiliares = BigDecimal.ZERO;
                for (GrupoPrevisto g : r.getGrupos()) {
                    decimal("100",somar(g.getPercentuaisInternos()));
                    BigDecimal somaLinhas = BigDecimal.ZERO;
                    for (LinhaRateio linha : g.getLinhasPorCr()) {
                        if (linha.getValor().signum() <= 0) throw new AssertionError("Linha sem valor");
                        somaLinhas = somaLinhas.add(linha.getValor());
                    }
                    decimal(g.getValorTotal().toPlainString(),somaLinhas);
                    auxiliares = auxiliares.add(g.getValorTotal());
                }
                decimal(outros.toPlainString(),auxiliares);
                Collections.shuffle(destinos,random);
                igual(hash(r,"1",Collections.<String,String>emptyMap()),hash(CALCULADORA.calcular(base,destinos,1),"1",Collections.<String,String>emptyMap()));
            }
        });
        System.out.println("PASS: " + cenarios + " cenarios, incluindo 1000 composicoes geradas.");
    }

    private static void executar(String nome, Runnable teste) {
        try { teste.run(); cenarios++; System.out.println("OK " + nome); }
        catch (Throwable falha) { throw new AssertionError("Falha em " + nome,falha); }
    }
    private static BigDecimal bd(String valor) { return new BigDecimal(valor); }
    private static DestinoPeso destino(int empresa,int cr,String peso) { return new DestinoPeso(empresa,cr,"PADRAO",bd(peso),"fonte"); }
    private static ResultadoCalculo calcular(String base,DestinoPeso... destinos) { return CALCULADORA.calcular(bd(base),Arrays.asList(destinos),1); }
    private static String hash(ResultadoCalculo r,String algoritmo,Map<String,String> campos) {
        return HashComposicao.calcular(new ContextoHash(algoritmo,"NOTA",1,1,1,null,LocalDate.of(2026,10,1),"MANUAL","",campos,r));
    }
    private static BigDecimal somar(List<BigDecimal> valores) { BigDecimal soma=BigDecimal.ZERO; for(BigDecimal v:valores)soma=soma.add(v); return soma; }
    private static void decimal(String esperado,BigDecimal valor) { if(bd(esperado).compareTo(valor)!=0)throw new AssertionError(esperado+" != "+valor); }
    private static void igual(Object esperado,Object valor) { if(!esperado.equals(valor))throw new AssertionError(esperado+" != "+valor); }
    private static void diferente(Object a,Object b) { if(a.equals(b))throw new AssertionError("Valores deveriam diferir"); }
    private static void erro(String codigo,Runnable acao) {
        try { acao.run(); throw new AssertionError("Erro esperado: "+codigo); }
        catch(ErroRateio e) { igual(codigo,e.getCodigo()); }
    }
}
