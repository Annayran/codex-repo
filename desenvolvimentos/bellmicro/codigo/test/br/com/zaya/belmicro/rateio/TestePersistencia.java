package br.com.zaya.belmicro.rateio;

import br.com.zaya.belmicro.rateio.aplicacao.SimulacaoService;
import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.aplicacao.porta.*;
import br.com.zaya.belmicro.rateio.dominio.*;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.TabelaRateio;
import br.com.zaya.belmicro.rateio.infraestrutura.persistencia.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

/** Tests the durable layout against a simulated Jape table gateway, not a database. */
public final class TestePersistencia {
    private static int cenarios;
    private static final OrigemCanonica ORIGEM=new OrigemCanonica(OrigemCanonica.Tipo.NOTA,1);
    public static void main(String[] args){
        executar("reabre fotografia com nova instancia do repositorio",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular("s");
            SimulacaoRateio lido=new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM);
            igual(s.getHash(),lido.getHash());igual(s.getId(),lido.getId());igual(1,lido.getRevisao());
            decimal("1000",lido.getResultado().getBase());igual(2,a.tabelas.linhas(TabelaRateio.PARCELA).size());
        });
        executar("recalculo conserva eventos anteriores e replay da revisao antiga",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular("s");int eventos=a.tabelas.linhas(TabelaRateio.EVENTO).size();
            a.base=new BigDecimal("2000");SimulacaoRateio r=a.servico().recalcular(s.getId(),1,entrada("50","50"),"conferencia",10,"r");
            verdadeiro(a.tabelas.linhas(TabelaRateio.EVENTO).size()>eventos);
            igual(s.getHash(),a.servico().simular(ORIGEM,entrada("30","70"),10,"s").getHash());
            igual(r.getHash(),new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM).getHash());
            igual(1,a.tabelas.linhas(TabelaRateio.SIMULACAO).size());
        });
        executar("criterio e versao sobrevivem na fotografia completa",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.servico().simular(ORIGEM,EntradaSimulacao.criterio("4",7),10,"c");
            SimulacaoRateio lido=new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM);
            igual("4",lido.getEntrada().getCriterioId());igual(7,lido.getEntrada().getCriterioVersao());igual(s.getHash(),lido.getHash());
            igual("fonte-1",lido.getResultado().getParcelas().get(0).getEvidencia().substring(2));
        });
        executar("pendencia invalida projecao antiga preservando fotografia anterior",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular("s");
            SimulacaoRateio p=a.servico().recalcular(s.getId(),1,entrada("30","60"),"base incompleta",10,"p");
            igual("COMPOSICAO_INVALIDA",p.getPendencia());igual(0,a.tabelas.linhas(TabelaRateio.PARCELA).size());
            igual(0,a.tabelas.linhas(TabelaRateio.GRUPO).size());igual(0,a.tabelas.linhas(TabelaRateio.INTERNO).size());
            igual(null,new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM).getResultado());
            igual(s.getHash(),new SimulacaoRepositoryAdRat(a.tabelas).porSolicitacao("s").getResultado().getHash());
        });
        executar("falha ao gravar evento reverte cabecalho e detalhes novos",()->{
            Ambiente a=new Ambiente();a.tabelas.falharEvento=true;
            falha(()->a.simular("s"));igual(0,a.tabelas.linhas(TabelaRateio.SIMULACAO).size());
            igual(0,a.tabelas.linhas(TabelaRateio.PARCELA).size());igual(0,a.tabelas.linhas(TabelaRateio.EVENTO).size());
        });
        executar("rollback no recalculo mantem corrente e replay anterior",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular("s");a.tabelas.falharEvento=true;
            falha(()->a.servico().recalcular(s.getId(),1,entrada("50","50"),"teste",10,"r"));
            igual(s.getHash(),new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM).getHash());
            igual(null,new SimulacaoRepositoryAdRat(a.tabelas).porSolicitacao("r"));
        });
        executar("mesma chave em outra origem nao cria cabecalho",()->{
            Ambiente a=new Ambiente();a.simular("s");
            try{a.servico().simular(new OrigemCanonica(OrigemCanonica.Tipo.NOTA,2),entrada("30","70"),10,"s");throw new AssertionError();}
            catch(ErroRateio e){igual("SOLICITACAO_INCOMPATIVEL",e.getCodigo());}
            igual(1,a.tabelas.linhas(TabelaRateio.SIMULACAO).size());
        });
        executar("payload fragmentado respeita 100 com indices de quatro digitos e recompoe sem perda",()->{
            Ambiente a=new Ambiente();Random random=new Random(42);StringBuilder texto=new StringBuilder();
            for(int i=0;i<50000;i++)texto.append((char)('!'+random.nextInt(90)));a.material=texto.toString();
            SimulacaoRateio s=a.simular("grande");verdadeiro(a.tabelas.linhas(TabelaRateio.EVENTO).size()>1000);
            for(Map<String,Object> evento:a.tabelas.linhas(TabelaRateio.EVENTO))verdadeiro(evento.get("DESCRICAO").toString().length()<=100);
            SimulacaoRateio lido=new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM);
            igual(a.material,lido.getDadosOrigem().getCamposMateriais().get("teste"));igual(s.getHash(),lido.getHash());
        });
        executar("fotografia legada reabre e recalcula com fragmentos curtos sem perder replay",()->{
            Ambiente a=new Ambiente();SimulacaoRateio s=a.simular("legado");
            List<Map<String,Object>> eventos=a.tabelas.linhas(TabelaRateio.EVENTO);StringBuilder payload=new StringBuilder();
            Map<String,Object> modelo=new LinkedHashMap<String,Object>(eventos.get(0));
            for(Map<String,Object> evento:eventos)payload.append(evento.get("DESCRICAO").toString().split("\\|",5)[4]);
            eventos.clear();int total=(payload.length()+3499)/3500;
            for(int i=0;i<total;i++){
                Map<String,Object> antigo=new LinkedHashMap<String,Object>(modelo);antigo.put("SEQUENCIA",BigDecimal.valueOf(i+1));
                antigo.put("DESCRICAO","RAT1|"+SimulacaoRepositoryAdRat.hashChave("legado")+"|"+i+"|"+total+"|"+payload.substring(i*3500,Math.min(payload.length(),(i+1)*3500)));eventos.add(antigo);
            }
            igual(s.getHash(),new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM).getHash());
            SimulacaoRateio nova=a.servico().recalcular(s.getId(),1,entrada("50","50"),"conferencia",10,"nova");igual(2,nova.getRevisao());
            igual(s.getHash(),new SimulacaoRepositoryAdRat(a.tabelas).porSolicitacao("legado").getResultado().getHash());
            igual(nova.getHash(),new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM).getHash());
        });
        executar("limite de fragmentos rejeita e reverte antes de gravar fotografia parcial",()->{
            Ambiente a=new Ambiente();Random random=new Random(43);StringBuilder texto=new StringBuilder();
            for(int i=0;i<300000;i++)texto.append((char)('!'+random.nextInt(90)));a.material=texto.toString();
            try{a.simular("excesso");throw new AssertionError();}catch(ErroRateio e){igual("FOTOGRAFIA_EXCEDE_LIMITE",e.getCodigo());}
            igual(0,a.tabelas.linhas(TabelaRateio.SIMULACAO).size());igual(0,a.tabelas.linhas(TabelaRateio.EVENTO).size());igual(0,a.tabelas.linhas(TabelaRateio.PARCELA).size());
        });
        executar("fragmentos curtos conservam SHA256 inteiro e formato anterior",()->{
            Ambiente a=new Ambiente();a.simular("chave");String hash=SimulacaoRepositoryAdRat.hashChave("chave");igual(64,hash.length());
            for(Map<String,Object> evento:a.tabelas.linhas(TabelaRateio.EVENTO)){
                String[] partes=evento.get("DESCRICAO").toString().split("\\|",5);igual("RAT1",partes[0]);igual(hash,partes[1]);verdadeiro(partes[4].length()<=19);
            }
        });
        executar("fragmento ausente ou duplicado falha sem inventar fotografia",()->{
            Ambiente a=new Ambiente();a.simular("s");Map<String,Object> copia=new LinkedHashMap<String,Object>(a.tabelas.linhas(TabelaRateio.EVENTO).get(0));
            a.tabelas.linhas(TabelaRateio.EVENTO).add(copia);falha(()->new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM));
            a.tabelas.linhas(TabelaRateio.EVENTO).clear();falha(()->new SimulacaoRepositoryAdRat(a.tabelas).correnteDe(ORIGEM));
        });
        executar("codec rejeita versao e payload invalidos",()->{
            falha(()->FotografiaCodec.ler("nao-base64"));falha(()->FotografiaCodec.ler(""));
        });
        executar("limite de colecao e simetrico antes de gravar",()->{
            Ambiente a=new Ambiente();EntradaSimulacao.Percentual linha=new EntradaSimulacao.Percentual(1,10,BigDecimal.ZERO);
            EntradaSimulacao entrada=EntradaSimulacao.manual(Collections.nCopies(100001,linha));
            falha(()->a.servico().simular(ORIGEM,entrada,10,"limite"));igual(0,a.tabelas.linhas(TabelaRateio.SIMULACAO).size());
        });
        executar("chaves compostas internas usam sequencia do grupo",()->{
            Ambiente a=new Ambiente();a.simular("s");Map<String,Object> g=a.tabelas.linhas(TabelaRateio.GRUPO).get(0);
            Map<String,Object> i=a.tabelas.linhas(TabelaRateio.INTERNO).get(0);igual(g.get("SEQUENCIA"),i.get("SEQUENCIA"));
            decimal("100",(BigDecimal)i.get("PERCINTERNO"));
        });
        executar("fronteira do modal obtem usuario da sessao e omite evidencias",()->{
            Ambiente a=new Ambiente();br.com.zaya.belmicro.rateio.aplicacao.ComandosModalRateio comandos=
                    new br.com.zaya.belmicro.rateio.aplicacao.ComandosModalRateio(a.servico(),()->10);
            SimulacaoRateio s=comandos.simular(ORIGEM,entrada("30","70"),"modal");
            Map<String,Object> wire=br.com.zaya.belmicro.rateio.aplicacao.DtoModalRateio.simulacao(s);
            igual("10",wire.get("usuario"));verdadeiro(!wire.toString().contains("fonte-1"));
            verdadeiro(!wire.toString().contains("camposMateriais"));
            @SuppressWarnings("unchecked")Map<String,Object> resultado=(Map<String,Object>)wire.get("resultado");
            igual("1000.00",resultado.get("base"));
            igual(Boolean.FALSE,br.com.zaya.belmicro.rateio.aplicacao.DtoModalRateio.contexto(comandos.abrirContexto(ORIGEM)).get("podeEfetivar"));
            try{new br.com.zaya.belmicro.rateio.aplicacao.ComandosModalRateio(a.servico(),()->0).abrirContexto(ORIGEM);throw new AssertionError();}
            catch(ErroRateio e){igual("SEM_PERMISSAO",e.getCodigo());}
        });
        System.out.println("PASS: "+cenarios+" cenarios de persistencia (gateway simulado).");
    }

    private static EntradaSimulacao entrada(String origem,String outra){return EntradaSimulacao.manual(Arrays.asList(
            new EntradaSimulacao.Percentual(1,10,new BigDecimal(origem)),new EntradaSimulacao.Percentual(2,20,new BigDecimal(outra))));}
    private static void executar(String nome,Runnable teste){try{teste.run();cenarios++;System.out.println("OK "+nome);}catch(Throwable e){throw new AssertionError(nome,e);}}
    private static void igual(Object a,Object b){if(!Objects.equals(a,b))throw new AssertionError(a+" != "+b);}
    private static void verdadeiro(boolean b){if(!b)throw new AssertionError("Condicao nao atendida");}
    private static void decimal(String a,BigDecimal b){verdadeiro(new BigDecimal(a).compareTo(b)==0);}
    private static void falha(Runnable r){try{r.run();throw new AssertionError("Falha esperada");}catch(RuntimeException esperada){}}

    private static final class Ambiente implements OrigemRepository,CriterioRepository,ExecucaoRepository,AutorizacaoRateio {
        final Memoria tabelas=new Memoria();BigDecimal base=new BigDecimal("1000");String material="contexto";
        SimulacaoService servico(){return new SimulacaoService(this,this,new SimulacaoRepositoryAdRat(tabelas),this,this,Clock.fixed(Instant.parse("2026-10-26T12:00:00Z"),ZoneOffset.UTC));}
        SimulacaoRateio simular(String chave){return servico().simular(ORIGEM,entrada("30","70"),10,chave);}
        public DadosOrigem resolver(OrigemCanonica origem){return new DadosOrigem(origem,1,100,200L,300L,base,
                LocalDate.of(2026,10,1),"1",Collections.singletonMap("teste",material),false);}
        public void validarDestinos(DadosOrigem dados,List<DestinoPeso> pesos){}
        public List<CriterioDisponivel> aplicaveis(DadosOrigem dados){return Arrays.asList(new CriterioDisponivel("4",7,"Criterio"));}
        public List<DestinoPeso> carregarPesos(String id,int versao,DadosOrigem dados){return Arrays.asList(
                new DestinoPeso(1,10,"1",new BigDecimal("30"),"fonte-1"),new DestinoPeso(2,20,"2",new BigDecimal("70"),"fonte-2"));}
        public boolean concluidaDe(OrigemCanonica origem){return false;}
        public void exigir(long usuario,OrigemCanonica origem,Operacao operacao){if(usuario<=0)throw new ErroRateio("SEM_PERMISSAO","Usuario invalido.");}
        public boolean permite(long usuario,OrigemCanonica origem,Operacao operacao){return usuario>0;}
    }

    private static final class Memoria implements TabelasAdRat {
        Map<TabelaRateio,List<Map<String,Object>>> dados=new EnumMap<TabelaRateio,List<Map<String,Object>>>(TabelaRateio.class);
        boolean transacao,falharEvento;
        List<Map<String,Object>> linhas(TabelaRateio t){return dados.computeIfAbsent(t,k->new ArrayList<Map<String,Object>>());}
        public synchronized <T>T emTransacao(OrigemCanonica origem,SimulacaoRepository.Trabalho<T> trabalho){
            Map<TabelaRateio,List<Map<String,Object>>> copia=new EnumMap<TabelaRateio,List<Map<String,Object>>>(TabelaRateio.class);
            for(TabelaRateio t:TabelaRateio.values()){List<Map<String,Object>> lista=new ArrayList<Map<String,Object>>();for(Map<String,Object> r:linhas(t))lista.add(new LinkedHashMap<String,Object>(r));copia.put(t,lista);}
            transacao=true;try{return trabalho.executar();}catch(RuntimeException|Error e){dados=copia;throw e;}finally{transacao=false;}
        }
        public synchronized List<Map<String,Object>> selecionar(TabelaRateio t,Map<String,Object> filtro){
            List<Map<String,Object>> r=new ArrayList<Map<String,Object>>();for(Map<String,Object> linha:linhas(t)){
                boolean incluir=true;for(Map.Entry<String,Object> f:filtro.entrySet())if(!Objects.equals(linha.get(f.getKey()),f.getValue()))incluir=false;
                if(incluir)r.add(new LinkedHashMap<String,Object>(linha));}return r;
        }
        public synchronized List<Map<String,Object>> eventosSolicitacao(String hash){
            List<Map<String,Object>> r=new ArrayList<Map<String,Object>>();for(Map<String,Object> linha:linhas(TabelaRateio.EVENTO))
                if(linha.get("DESCRICAO").toString().startsWith("RAT1|"+hash+"|"))r.add(new LinkedHashMap<String,Object>(linha));return r;
        }
        public synchronized Map<String,Object> inserir(TabelaRateio t,Map<String,Object> campos){
            if(!transacao)throw new AssertionError("Escrita fora transacao");if(t==TabelaRateio.EVENTO&&falharEvento)throw new IllegalStateException("Falha simulada");
            if(t==TabelaRateio.EVENTO&&String.valueOf(campos.get("DESCRICAO")).length()>100)throw new AssertionError("DESCRICAO excedeu varchar100");
            Map<String,Object> linha=new LinkedHashMap<String,Object>(campos);String ultima=t.getChave().get(t.getChave().size()-1);
            if(!linha.containsKey(ultima)){
                long max=0;for(Map<String,Object> antiga:linhas(t)){
                    boolean mesmoPai=true;for(String chave:t.getChave())if(!chave.equals(ultima)&&!Objects.equals(antiga.get(chave),linha.get(chave)))mesmoPai=false;
                    if(mesmoPai)max=Math.max(max,((BigDecimal)antiga.get(ultima)).longValueExact());
                }linha.put(ultima,BigDecimal.valueOf(max+1));
            }
            linhas(t).add(linha);return new LinkedHashMap<String,Object>(linha);
        }
        public synchronized void atualizar(TabelaRateio t,Map<String,Object> chave,Map<String,Object> campos){
            if(!transacao)throw new AssertionError("Sem transacao");for(Map<String,Object> linha:linhas(t)){
                boolean corresponde=true;for(Map.Entry<String,Object> c:chave.entrySet())if(!Objects.equals(c.getValue(),linha.get(c.getKey())))corresponde=false;
                if(corresponde){linha.putAll(campos);return;}}throw new IllegalStateException("Linha ausente");
        }
        public synchronized void excluirFilhos(TabelaRateio t,long id){if(!transacao)throw new AssertionError("Sem transacao");linhas(t).removeIf(r->BigDecimal.valueOf(id).equals(r.get("NUSIMULACAO")));}
    }
}
