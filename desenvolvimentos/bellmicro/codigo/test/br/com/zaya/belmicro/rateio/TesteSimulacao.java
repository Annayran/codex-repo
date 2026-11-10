package br.com.zaya.belmicro.rateio;

import br.com.zaya.belmicro.rateio.aplicacao.SimulacaoService;
import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.aplicacao.porta.*;
import br.com.zaya.belmicro.rateio.dominio.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/** Ports implemented in memory exclusively for tests; never packaged as production persistence. */
public final class TesteSimulacao {
    private static int cenarios;
    private static final OrigemCanonica ORIGEM = new OrigemCanonica(OrigemCanonica.Tipo.NOTA,1);
    private static final long USUARIO = 10;

    public static void main(String[] args) throws Exception {
        executar("abrir contexto somente leitura", () -> {
            Ambiente a = new Ambiente();
            ContextoModal contexto = a.servico.abrirContexto(ORIGEM,USUARIO);
            igual(null,contexto.getSimulacao()); igual(0,a.store.gravacoes);
            igual(0,a.carregamentos); igual(2,contexto.getCriterios().size());
        });
        executar("simular manual persiste fotografia e totais", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio r = a.simular();
            igual(SimulacaoRateio.Situacao.VALIDA,r.getSituacao());
            igual(1,r.getRevisao()); igual(USUARIO,r.getUsuario());
            igual(Instant.parse("2026-10-26T12:00:00Z"),r.getData());
            decimal("700",r.getResultado().getGrupos().get(0).getValorTotal());
            igual(1,a.store.gravacoes); igual(0,a.carregamentos);
        });
        executar("reabrir nao recalcula com origem alterada", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio antes = a.simular();
            a.base = new BigDecimal("2000");
            ContextoModal contexto = a.servico.abrirContexto(ORIGEM,USUARIO);
            igual(antes,contexto.getSimulacao()); decimal("1000",antes.getResultado().getBase());
            igual(1,a.store.gravacoes);
        });
        executar("recalculo preserva fotografia e exige nova revisao", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular();
            a.base = new BigDecimal("2000");
            SimulacaoRateio segunda = a.recalcular(primeira,manual("50","50"),"recalculo");
            igual(primeira.getId(),segunda.getId()); igual(2,segunda.getRevisao());
            decimal("1000",primeira.getResultado().getBase()); decimal("2000",segunda.getResultado().getBase());
            igual(SimulacaoRateio.Situacao.SUBSTITUIDA,a.store.historico.get(ORIGEM).get(0).getSituacao());
            diferente(primeira.getHash(),segunda.getHash()); igual("conferencia",segunda.getMotivo());
        });
        executar("reenvio de simulacao nao calcula nem cria revisao", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular();
            a.base = null;
            igual(primeira,a.servico.simular(ORIGEM,manual("30.00","70.0"),USUARIO,"inicial"));
            igual(1,a.store.gravacoes);
        });
        executar("reenvio de recalculo com revisao antiga retorna mesmo resultado", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular();
            SimulacaoRateio segunda = a.recalcular(primeira,manual("50","50"),"recalculo");
            igual(segunda,a.recalcular(primeira,manual("50","50"),"recalculo"));
            igual(2,a.store.gravacoes);
        });
        executar("chave reutilizada com outro conteudo rejeita", () -> {
            Ambiente a = new Ambiente(); a.simular();
            erro("SOLICITACAO_INCOMPATIVEL",() -> a.servico.simular(ORIGEM,manual("50","50"),USUARIO,"inicial"));
            erro("SOLICITACAO_INCOMPATIVEL",() -> a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,2),manual("30","70"),USUARIO,"inicial"));
            erro("SOLICITACAO_INCOMPATIVEL",() -> a.servico.simular(ORIGEM,manual("30","70"),11,"inicial"));
            igual(1,a.store.gravacoes);
        });
        executar("chave reutilizada com outro comando motivo ou revisao rejeita", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular();
            erro("SOLICITACAO_INCOMPATIVEL",() -> a.recalcular(primeira,manual("30","70"),"inicial"));
            SimulacaoRateio segunda = a.recalcular(primeira,manual("50","50"),"recalculo");
            erro("SOLICITACAO_INCOMPATIVEL",() -> a.servico.recalcular(primeira.getId(),1,manual("50","50"),"outro",USUARIO,"recalculo"));
            erro("SOLICITACAO_INCOMPATIVEL",() -> a.recalcular(segunda,manual("50","50"),"recalculo"));
        });
        executar("revisao obsoleta e novo simular nao sobrescrevem", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular();
            a.recalcular(primeira,manual("50","50"),"r1");
            erro("SIMULACAO_DESATUALIZADA",() -> a.recalcular(primeira,manual("60","40"),"r2"));
            erro("SIMULACAO_DESATUALIZADA",() -> a.servico.simular(ORIGEM,manual("30","70"),USUARIO,"nova"));
            igual(2,a.store.gravacoes);
        });
        executar("autorizacao antes de resolver e consultar replay", () -> {
            Ambiente a = new Ambiente(); a.simular(); int leituras = a.leiturasOrigem;
            a.autorizado = false;
            erro("SEM_PERMISSAO",() -> a.servico.simular(ORIGEM,manual("30","70"),USUARIO,"inicial"));
            erro("SEM_PERMISSAO",() -> a.servico.abrirContexto(ORIGEM,USUARIO));
            igual(leituras,a.leiturasOrigem); igual(1,a.store.gravacoes);
        });
        executar("permissao consultar nao concede simular", () -> {
            Ambiente a = new Ambiente(); a.podeSimular = false;
            igual(false,a.servico.abrirContexto(ORIGEM,USUARIO).isPodeSimular());
            erro("SEM_PERMISSAO",() -> a.simular());
        });
        executar("origem auxiliar rejeitada", () -> {
            Ambiente a = new Ambiente(); a.auxiliar = true;
            erro("ORIGEM_AUXILIAR",() -> a.simular());
            erro("ORIGEM_AUXILIAR",() -> a.servico.abrirContexto(ORIGEM,USUARIO));
            igual(0,a.store.gravacoes);
        });
        executar("parcelas da mesma nota convergem para uma origem", () -> {
            Ambiente a = new Ambiente();
            SimulacaoRateio r = a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11),manual("30","70"),USUARIO,"parcelas");
            igual(ORIGEM,r.getOrigem());
            igual(r,a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,12),manual("30","70"),USUARIO,"parcelas"));
            igual(1,a.store.gravacoes);
        });
        executar("autorizacao tambem valida origem canonica", () -> {
            Ambiente a = new Ambiente(); a.negarCanonica = true;
            erro("SEM_PERMISSAO",() -> a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11),manual("30","70"),USUARIO,"p"));
            igual(0,a.store.gravacoes);
        });
        executar("execucao existente impede simulacao e recalculo novos", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular(); a.efetivada = true;
            erro("RATEIO_JA_EFETIVADO",() -> a.recalcular(primeira,manual("50","50"),"novo"));
            ContextoModal contexto = a.servico.abrirContexto(ORIGEM,USUARIO);
            igual(true,contexto.isEfetivada()); igual(false,contexto.isPodeSimular());
            Ambiente b = new Ambiente(); b.efetivada = true;
            erro("RATEIO_JA_EFETIVADO",() -> b.simular()); igual(0,b.store.gravacoes);
        });
        executar("pendencia substitui revisao valida sem hash ou calculo antigo", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular();
            SimulacaoRateio pendente = a.recalcular(primeira,manual("30","60"),"pendente");
            igual(SimulacaoRateio.Situacao.COM_PENDENCIA,pendente.getSituacao());
            igual("COMPOSICAO_INVALIDA",pendente.getPendencia());
            igual(null,pendente.getResultado()); igual(null,pendente.getHash());
            igual(pendente,a.store.correnteDe(ORIGEM));
            erro("SIMULACAO_DESATUALIZADA",() -> a.recalcular(primeira,manual("30","70"),"velha"));
        });
        executar("competencia e base ausentes viram pendencia", () -> {
            Ambiente a = new Ambiente(); a.competencia = null;
            igual("BASE_INCOMPLETA",a.simular().getPendencia());
            Ambiente b = new Ambiente(); b.base = null;
            igual("BASE_INCOMPLETA",b.simular().getPendencia());
        });
        executar("cadastro invalido guarda apenas codigo sanitizado", () -> {
            Ambiente a = new Ambiente(); a.erroCadastro = true;
            SimulacaoRateio r = a.simular(); igual("CADASTRO_INVALIDO",r.getPendencia());
            igual(null,r.getResultado());
        });
        executar("criterio exige selecao explicita e aceita apenas versao disponivel", () -> {
            Ambiente a = new Ambiente();
            erro("CRITERIO_NAO_SELECIONADO",() -> EntradaSimulacao.criterio(null,1));
            SimulacaoRateio r = a.servico.simular(ORIGEM,EntradaSimulacao.criterio("MATRIZ",1),USUARIO,"criterio");
            igual(SimulacaoRateio.Situacao.VALIDA,r.getSituacao()); igual(1,a.carregamentos);
            Ambiente b = new Ambiente();
            igual("CRITERIO_NAO_SELECIONADO",b.servico.simular(ORIGEM,EntradaSimulacao.criterio("MATRIZ",2),USUARIO,"criterio").getPendencia());
            igual(0,b.carregamentos);
        });
        executar("criterio ambiguo e falta de fonte nao fazem fallback", () -> {
            Ambiente a = new Ambiente(); a.criterioDuplicado = true;
            igual("REGRA_CONFLITANTE",a.servico.simular(ORIGEM,EntradaSimulacao.criterio("MATRIZ",1),USUARIO,"c").getPendencia());
            Ambiente b = new Ambiente(); b.fonteAusente = true;
            igual("BASE_INCOMPLETA",b.servico.simular(ORIGEM,EntradaSimulacao.criterio("MATRIZ",1),USUARIO,"c").getPendencia());
            igual(1,b.carregamentos);
        });
        executar("falha tecnica nao grava revisao ou solicitacao", () -> {
            Ambiente a = new Ambiente(); a.falhaTecnica = true;
            try { a.simular(); throw new AssertionError("Falha esperada"); }
            catch (IllegalStateException esperado) { }
            igual(0,a.store.gravacoes); igual(0,a.store.solicitacoes.size());
        });
        executar("rollback de gravacao incompleta preserva revisao anterior", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular(); a.store.falharSolicitacao = true;
            try { a.recalcular(primeira,manual("50","50"),"falha"); throw new AssertionError("Falha esperada"); }
            catch (IllegalStateException esperado) { }
            igual(primeira,a.store.correnteDe(ORIGEM)); igual(1,a.store.historico.get(ORIGEM).size());
            igual(null,a.store.porSolicitacao("falha"));
        });
        executar("entrada e listas sao fotografias imutaveis", () -> {
            List<EntradaSimulacao.Percentual> linhas = new ArrayList<EntradaSimulacao.Percentual>();
            linhas.add(new EntradaSimulacao.Percentual(1,10,new BigDecimal("100")));
            EntradaSimulacao entrada = EntradaSimulacao.manual(linhas); linhas.clear();
            igual(1,entrada.getPercentuais().size());
            try { entrada.getPercentuais().clear(); throw new AssertionError("Lista mutavel"); }
            catch (UnsupportedOperationException esperado) { }
        });
        executar("motivo chave e usuario sao obrigatorios", () -> {
            Ambiente a = new Ambiente();
            erro("SOLICITACAO_INCOMPATIVEL",() -> a.servico.simular(ORIGEM,manual("30","70"),USUARIO,""));
            erro("SEM_PERMISSAO",() -> a.servico.simular(ORIGEM,manual("30","70"),0,"a"));
            SimulacaoRateio r = a.simular();
            erro("COMPOSICAO_INVALIDA",() -> a.servico.recalcular(r.getId(),1,manual("30","70")," ",USUARIO,"r"));
        });
        executar("hash muda quando mapeamento material muda", () -> {
            Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular();
            a.versaoMapeamento = "mapeamento-2";
            diferente(primeira.getHash(),a.recalcular(primeira,manual("30","70"),"novo-mapa").getHash());
        });
        executar("CHAVESOLIC aceita 80 e rejeita 81 sem truncamento", () -> {
            Ambiente a=new Ambiente();
            String chave=String.join("",Collections.nCopies(80,"a"));
            a.servico.simular(ORIGEM,manual("30","70"),USUARIO,chave);
            erro("SOLICITACAO_INCOMPATIVEL",() -> a.servico.simular(ORIGEM,manual("30","70"),USUARIO,chave+"b"));
            igual(1,a.store.gravacoes);
        });
        executar("MOTIVORECALC aceita 255 e rejeita 256 sem truncamento", () -> {
            Ambiente a=new Ambiente(); SimulacaoRateio s=a.simular();
            String motivo=String.join("",Collections.nCopies(255,"a"));
            SimulacaoRateio r=a.servico.recalcular(s.getId(),1,manual("50","50"),motivo,USUARIO,"r");
            igual(motivo,r.getMotivo());
            erro("COMPOSICAO_INVALIDA",() -> a.servico.recalcular(s.getId(),2,manual("50","50"),motivo+"b",USUARIO,"r2"));
            igual(2,a.store.gravacoes);
        });
        executar("abrir com base competencia ausentes devolve pendencia sem gravar",()->{
            Ambiente a=new Ambiente();a.base=null;a.competencia=null;a.erroCriterios="BASE_INCOMPLETA";
            ContextoModal contexto=a.servico.abrirContexto(ORIGEM,USUARIO);
            igual(Arrays.asList("BASE_INCOMPLETA"),contexto.getPendencias());igual(0,contexto.getCriterios().size());
            igual(null,contexto.getOrigem().getCompetencia());igual(0,a.store.gravacoes);igual(0,a.carregamentos);
        });
        executar("conflito de criterio aparece no contexto sem escolha implicita",()->{
            Ambiente a=new Ambiente();a.erroCriterios="REGRA_CONFLITANTE";
            ContextoModal contexto=a.servico.abrirContexto(ORIGEM,USUARIO);
            igual(Arrays.asList("REGRA_CONFLITANTE"),contexto.getPendencias());igual(0,contexto.getCriterios().size());igual(0,a.store.gravacoes);
        });
        executar("falha tecnica de consulta nao vira pendencia funcional",()->{
            Ambiente a=new Ambiente();a.erroCriterios="TECNICA";
            try{a.servico.abrirContexto(ORIGEM,USUARIO);throw new AssertionError("Falha esperada");}catch(IllegalStateException esperada){}
            igual(0,a.store.gravacoes);
        });
        executar("acao de recalculo amarra NUFIN selecionado a simulacao",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular();
            SimulacaoRateio r=a.servico.recalcularParaOrigem(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11),s.getId(),1,manual("50","50"),"conferencia",USUARIO,"acao-r");
            igual(2,r.getRevisao());igual(ORIGEM,r.getOrigem());
        });
        executar("acao nao recalcula outra origem mesmo com usuario autorizado",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular();
            erro("SIMULACAO_DESATUALIZADA",()->a.servico.recalcularParaOrigem(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,2),s.getId(),1,manual("50","50"),"conferencia",USUARIO,"acao-r"));
            igual(1,a.store.gravacoes);
        });
        executar("replay da acao nao contorna origem selecionada divergente",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular();
            a.servico.recalcularParaOrigem(ORIGEM,s.getId(),1,manual("50","50"),"conferencia",USUARIO,"acao-r");
            erro("SIMULACAO_DESATUALIZADA",()->a.servico.recalcularParaOrigem(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,2),s.getId(),1,manual("50","50"),"conferencia",USUARIO,"acao-r"));
            igual(2,a.store.gravacoes);
        });
        executar("acao rejeita auxiliar selecionado mesmo com simulacao existente",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular();a.auxiliar=true;
            erro("ORIGEM_AUXILIAR",()->a.servico.recalcularParaOrigem(ORIGEM,s.getId(),1,manual("50","50"),"conferencia",USUARIO,"acao-r"));igual(1,a.store.gravacoes);
        });
        testarConcorrencia();
        executar("referencia de vencimento permanece na releitura transacional",()->{
            Ambiente a=new Ambiente();a.referenciaFinanceira=true;a.base=new BigDecimal("300.00");
            SimulacaoRateio r=a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11),manual("30","70"),USUARIO,"nf");
            igual(ORIGEM,r.getOrigem());igual(LocalDate.of(2026,10,15),r.getDadosOrigem().getCompetencia());igual("11",r.getDadosOrigem().getCamposMateriais().get("NUFIN_REFERENCIA"));
        });
        executar("mesma chave nao troca parcela de referencia da mesma nota",()->{
            Ambiente a=new Ambiente();a.referenciaFinanceira=true;
            a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11),manual("30","70"),USUARIO,"nf");
            erro("SOLICITACAO_INCOMPATIVEL",()->a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,12),manual("30","70"),USUARIO,"nf"));igual(1,a.store.gravacoes);
        });
        executar("recalculo explicito pode trocar vencimento sem alterar revisao anterior",()->{
            Ambiente a=new Ambiente();a.referenciaFinanceira=true;
            SimulacaoRateio r=a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11),manual("30","70"),USUARIO,"nf");
            SimulacaoRateio novo=a.servico.recalcularParaOrigem(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,12),r.getId(),r.getRevisao(),manual("30","70"),"Outra parcela",USUARIO,"nf-r");
            igual(LocalDate.of(2026,10,15),r.getDadosOrigem().getCompetencia());igual(LocalDate.of(2026,11,15),novo.getDadosOrigem().getCompetencia());igual(false,r.getHash().equals(novo.getHash()));
            igual(novo,a.servico.recalcularParaOrigem(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,12),r.getId(),1,manual("30","70"),"Outra parcela",USUARIO,"nf-r"));
        });
        executar("replay legado recupera referencia original apos troca em outra revisao",()->{
            Ambiente a=new Ambiente();a.referenciaFinanceira=true;
            SimulacaoRateio r=a.servico.simular(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,11),manual("30","70"),USUARIO,"nf");
            SimulacaoRateio r2=a.servico.recalcular(r.getId(),1,manual("30","70"),"Motivo",USUARIO,"nf-r1");
            a.servico.recalcularParaOrigem(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,12),r.getId(),2,manual("30","70"),"Nova referencia",USUARIO,"nf-r2");
            a.negarReferencia=12;
            igual(r2,a.servico.recalcular(r.getId(),1,manual("30","70"),"Motivo",USUARIO,"nf-r1"));igual(3,a.store.gravacoes);
        });
        System.out.println("PASS: " + cenarios + " cenarios de aplicacao.");
    }

    private static void testarConcorrencia() throws Exception {
        Ambiente a = new Ambiente(); SimulacaoRateio primeira = a.simular();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            CountDownLatch largada = new CountDownLatch(1);
            Callable<String> trabalho = () -> {
                largada.await();
                try { a.recalcular(primeira,manual("50","50"),Thread.currentThread().getName()); return "OK"; }
                catch (ErroRateio e) { return e.getCodigo(); }
            };
            Future<String> um = executor.submit(trabalho); Future<String> dois = executor.submit(trabalho);
            largada.countDown(); List<String> respostas = Arrays.asList(um.get(10,TimeUnit.SECONDS),dois.get(10,TimeUnit.SECONDS));
            igual(1,Collections.frequency(respostas,"OK"));
            igual(1,Collections.frequency(respostas,"SIMULACAO_DESATUALIZADA")); igual(2,a.store.gravacoes);
            cenarios++; System.out.println("OK dois recalculos simultaneos preservam revisao corrente");
        } finally { executor.shutdownNow(); }
        Ambiente b = new Ambiente();
        ExecutorService repeticao = Executors.newFixedThreadPool(2);
        try {
            Future<SimulacaoRateio> um = repeticao.submit(() -> b.simular());
            Future<SimulacaoRateio> dois = repeticao.submit(() -> b.simular());
            igual(um.get(10,TimeUnit.SECONDS),dois.get(10,TimeUnit.SECONDS)); igual(1,b.store.gravacoes);
            cenarios++; System.out.println("OK mesma solicitacao simultanea retorna uma fotografia");
        } finally { repeticao.shutdownNow(); }
    }

    private static EntradaSimulacao manual(String origem,String destino) {
        return EntradaSimulacao.manual(Arrays.asList(new EntradaSimulacao.Percentual(1,10,new BigDecimal(origem)),
                new EntradaSimulacao.Percentual(2,20,new BigDecimal(destino))));
    }
    private static void executar(String nome,Runnable teste) {
        try { teste.run(); cenarios++; System.out.println("OK " + nome); }
        catch(Throwable e) { throw new AssertionError(nome,e); }
    }
    private static void igual(Object a,Object b) { if(!Objects.equals(a,b))throw new AssertionError(a+" != "+b); }
    private static void diferente(Object a,Object b) { if(Objects.equals(a,b))throw new AssertionError("Deveriam diferir"); }
    private static void decimal(String esperado,BigDecimal valor) { if(new BigDecimal(esperado).compareTo(valor)!=0)throw new AssertionError("Valor divergente"); }
    private static void erro(String codigo,Runnable acao) {
        try { acao.run(); throw new AssertionError("Esperado: "+codigo); }
        catch(ErroRateio e) { igual(codigo,e.getCodigo()); }
    }

    private static final class Ambiente implements OrigemRepository,CriterioRepository,ExecucaoRepository,AutorizacaoRateio {
        final Memoria store = new Memoria();
        final SimulacaoService servico = new SimulacaoService(this,this,store,this,this,Clock.fixed(Instant.parse("2026-10-26T12:00:00Z"),ZoneOffset.UTC));
        BigDecimal base = new BigDecimal("1000");
        LocalDate competencia = LocalDate.of(2026,10,1);
        String versaoMapeamento = "mapeamento-1";
        String erroCriterios;
        volatile boolean autorizado = true;
        boolean podeSimular = true, negarCanonica, auxiliar, efetivada, erroCadastro, criterioDuplicado, fonteAusente, falhaTecnica;
        int carregamentos, leiturasOrigem;
        boolean referenciaFinanceira;
        long negarReferencia;
        SimulacaoRateio simular() { return servico.simular(ORIGEM,manual("30","70"),USUARIO,"inicial"); }
        SimulacaoRateio recalcular(SimulacaoRateio s,EntradaSimulacao entrada,String chave) {
            return servico.recalcular(s.getId(),s.getRevisao(),entrada,"conferencia",USUARIO,chave);
        }
        public DadosOrigem resolver(OrigemCanonica ref) {
            leiturasOrigem++;
            OrigemCanonica canonica = ref.getTipo() == OrigemCanonica.Tipo.TITULO && (ref.getId() == 11 || ref.getId() == 12) ? ORIGEM : ref;
            if(referenciaFinanceira){
                if(ref.getTipo()!=OrigemCanonica.Tipo.TITULO)throw new AssertionError("Referencia de vencimento foi perdida");
                return new DadosOrigem(canonica,1,1,null,base,LocalDate.of(2026,ref.getId()==11?10:11,15),versaoMapeamento,Collections.singletonMap("NUFIN_REFERENCIA",String.valueOf(ref.getId())),auxiliar);
            }
            return new DadosOrigem(canonica,1,1,null,base,competencia,versaoMapeamento,Collections.<String,String>emptyMap(),auxiliar);
        }
        public void validarDestinos(DadosOrigem dados,List<DestinoPeso> destinos) {
            if (falhaTecnica) throw new IllegalStateException("Falha tecnica de teste.");
            if (erroCadastro) throw new ErroRateio("CADASTRO_INVALIDO","Conteudo privado do adaptador nao deve ser persistido.");
        }
        public List<CriterioDisponivel> aplicaveis(DadosOrigem dados) {
            if("TECNICA".equals(erroCriterios))throw new IllegalStateException("Falha tecnica simulada.");
            if(erroCriterios!=null)throw new ErroRateio(erroCriterios,"Detalhe privado nao exposto.");
            return Arrays.asList(new CriterioDisponivel("MATRIZ",1,"Matriz teste"),
                    new CriterioDisponivel(criterioDuplicado ? "MATRIZ" : "ANALITICO",1,"Outro criterio"));
        }
        public List<DestinoPeso> carregarPesos(String id,int versao,DadosOrigem dados) {
            carregamentos++;
            if(fonteAusente)throw new ErroRateio("BASE_INCOMPLETA","Fonte indisponivel");
            return Arrays.asList(new DestinoPeso(1,10,"TESTE",new BigDecimal("30"),"ref-1"),
                    new DestinoPeso(2,20,"TESTE",new BigDecimal("70"),"ref-2"));
        }
        public boolean concluidaDe(OrigemCanonica origem) { return efetivada; }
        public void exigir(long usuario,OrigemCanonica origem,Operacao operacao) {
            if(!permite(usuario,origem,operacao))throw new ErroRateio("SEM_PERMISSAO","Acesso negado.");
        }
        public boolean permite(long usuario,OrigemCanonica origem,Operacao operacao) {
            return autorizado && usuario > 0 && !(origem.getTipo()==OrigemCanonica.Tipo.TITULO && origem.getId()==negarReferencia) && !(negarCanonica && origem.equals(ORIGEM)) && (operacao != Operacao.SIMULAR || podeSimular);
        }
    }

    private static final class Memoria implements SimulacaoRepository {
        Map<OrigemCanonica,List<SimulacaoRateio>> historico = new HashMap<OrigemCanonica,List<SimulacaoRateio>>();
        Map<String,Solicitacao> solicitacoes = new HashMap<String,Solicitacao>();
        Map<OrigemCanonica,Long> ids = new HashMap<OrigemCanonica,Long>();
        long sequencia;
        int gravacoes;
        boolean falharSolicitacao;
        public synchronized <T> T emTransacao(OrigemCanonica origem,String chave,Trabalho<T> trabalho) {
            Map<OrigemCanonica,List<SimulacaoRateio>> anterior = new HashMap<OrigemCanonica,List<SimulacaoRateio>>();
            for(Map.Entry<OrigemCanonica,List<SimulacaoRateio>> e:historico.entrySet())anterior.put(e.getKey(),new ArrayList<SimulacaoRateio>(e.getValue()));
            Map<String,Solicitacao> requests = new HashMap<String,Solicitacao>(solicitacoes);
            Map<OrigemCanonica,Long> identidades = new HashMap<OrigemCanonica,Long>(ids);
            int numeroGravacoes = gravacoes; long seq = sequencia;
            try { return trabalho.executar(); }
            catch(RuntimeException | Error e) {
                historico=anterior; solicitacoes=requests; ids=identidades; gravacoes=numeroGravacoes; sequencia=seq; throw e;
            }
        }
        public synchronized SimulacaoRateio correnteDe(OrigemCanonica origem) {
            List<SimulacaoRateio> lista=historico.get(origem); return lista==null || lista.isEmpty() ? null : lista.get(lista.size()-1);
        }
        public synchronized SimulacaoRateio correntePorId(long id) {
            for(OrigemCanonica origem:historico.keySet())if(correnteDe(origem).getId()==id)return correnteDe(origem); return null;
        }
        public synchronized long idPara(OrigemCanonica origem) {
            if(!ids.containsKey(origem))ids.put(origem,++sequencia); return ids.get(origem);
        }
        public synchronized void salvarNovaRevisao(SimulacaoRateio s) {
            List<SimulacaoRateio> lista=historico.get(s.getOrigem());
            if(lista==null){lista=new ArrayList<SimulacaoRateio>();historico.put(s.getOrigem(),lista);}
            if(s.getRevisao()!=lista.size()+1)throw new ErroRateio("CONCORRENCIA","Revisao divergente.");
            if(!lista.isEmpty())lista.set(lista.size()-1,lista.get(lista.size()-1).substituida());
            lista.add(s); gravacoes++;
        }
        public synchronized Solicitacao porSolicitacao(String id) { return solicitacoes.get(id); }
        public synchronized void registrarSolicitacao(String id,String identidade,SimulacaoRateio resultado) {
            if(falharSolicitacao)throw new IllegalStateException("Falha de gravacao da solicitacao.");
            if(solicitacoes.containsKey(id))throw new ErroRateio("CONCORRENCIA","Solicitacao duplicada.");
            solicitacoes.put(id,new Solicitacao(identidade,resultado));
        }
    }
}

