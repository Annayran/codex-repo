package br.com.zaya.belmicro.rateio.acao;

import br.com.sankhya.extensions.actionbutton.*;
import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import br.com.zaya.belmicro.rateio.sankhya.*;
import com.google.gson.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/** Action adapter tests with real SDK interfaces, fake runtime and zero database access. */
public final class TesteAcoesFinanceiro {
    private static int cenarios;
    private static final String ENTRADA="{\"modalidade\":\"MANUAL\",\"percentuais\":[{\"codEmp\":1,\"codCencus\":10,\"percentual\":\"100.000000\"}]}";
    private static final String SIMULAR="{\"entrada\":"+ENTRADA+",\"solicitacaoId\":\"pedido-1\"}";
    public static void main(String[] args)throws Exception{
        executar("provider concreto e descoberto pelo ServiceLoader e resolve os comandos",()->{
            List<ConfiguracaoServicosRateio> encontrados=new ArrayList<ConfiguracaoServicosRateio>();
            for(ConfiguracaoServicosRateio c:ServiceLoader.load(ConfiguracaoServicosRateio.class,ConfiguracaoServicosRateio.class.getClassLoader()))encontrados.add(c);
            igual(1,encontrados.size());igual(ConfiguracaoServicosBelmicro.class,encontrados.get(0).getClass());
            verdadeiro(new RuntimeAcoesRateio().operacoes() instanceof ComandosSankhyaRateio);
        });
        executar("bloqueio temporario removido para sessao valida sem ler banco",()->{
            ConfiguracaoServicosRateio c=new ConfiguracaoServicosBelmicro();
            br.com.zaya.belmicro.rateio.aplicacao.porta.AutorizacaoRateio a=c.autorizacao(()->{throw new AssertionError("Nao deve consultar banco");});
            OrigemCanonica origem=new OrigemCanonica(OrigemCanonica.Tipo.TITULO,123);
            for(br.com.zaya.belmicro.rateio.aplicacao.porta.AutorizacaoRateio.Operacao op:br.com.zaya.belmicro.rateio.aplicacao.porta.AutorizacaoRateio.Operacao.values()){
                verdadeiro(a.permite(10,origem,op));a.exigir(10,origem,op);
                verdadeiro(!a.permite(0,origem,op));erro("SEM_PERMISSAO",()->a.exigir(0,origem,op));
                verdadeiro(!a.permite(10,null,op));erro("SEM_PERMISSAO",()->a.exigir(10,null,op));
            }
            verdadeiro(!a.permite(10,origem,null));erro("SEM_PERMISSAO",()->a.exigir(10,origem,null));
        });
        executar("configuracao usa instancias confirmadas sem presumir base ou competencia",()->{
            ConfiguracaoServicosRateio c=new ConfiguracaoServicosBelmicro();igual(11,c.instanciasJape().size());
            br.com.zaya.belmicro.rateio.infraestrutura.jdbc.MapeamentoOrigem.BaseCompetencia m=c.mapeamentoOrigem().calcular(null,()->{throw new AssertionError("Nao deve consultar banco");});
            igual(null,m.getBase());igual(null,m.getCompetencia());igual("NAO_HOMOLOGADO",m.getVersao());
        });
        executar("provider proprio ou legado precede padrao sem esconder conflitos",()->{
            ConfiguracaoServicosRateio padrao=new ConfiguracaoServicosBelmicro();
            ConfiguracaoServicosRateio externo=conexao->null;
            ConfiguracaoRateio legado=new ConfiguracaoRateio(){
                public br.com.zaya.belmicro.rateio.aplicacao.porta.AutorizacaoRateio autorizacao(br.com.zaya.belmicro.rateio.infraestrutura.jdbc.ConexaoRateio conexao){return null;}
                public void abrirModal(ContextoAcao contexto,OrigemCanonica origem){throw new AssertionError("Launcher legado nao deve ser chamado");}
            };
            igual(externo,RuntimeAcoesRateio.escolher(Arrays.asList(padrao,externo),Collections.singletonList(legado)));
            igual(legado,RuntimeAcoesRateio.escolher(Collections.singletonList(padrao),Collections.singletonList(legado)));
            igual(padrao,RuntimeAcoesRateio.escolher(Collections.singletonList(padrao),Collections.<ConfiguracaoRateio>emptyList()));
            erro("REGRA_CONFLITANTE",()->RuntimeAcoesRateio.escolher(Arrays.asList(padrao,externo,conexao->null),Collections.<ConfiguracaoRateio>emptyList()));
            igual(ConfiguracaoServicosBelmicro.class,RuntimeAcoesRateio.escolher(Collections.<ConfiguracaoServicosRateio>emptyList(),Collections.<ConfiguracaoRateio>emptyList()).getClass());
        });
        executar("cadastro descobre diretamente as tres acoes concretas e nao a base",()->{
            for(Class<?> tipo:Arrays.asList(SimularRateioAction.class,RecalcularRateioAction.class,EfetivarRateioAction.class)){
                verdadeiro(Arrays.asList(tipo.getInterfaces()).contains(AcaoRotinaJava.class));
                verdadeiro(Modifier.isPublic(tipo.getModifiers())&&!Modifier.isAbstract(tipo.getModifiers()));
                verdadeiro(tipo.getConstructor().newInstance() instanceof AcaoRotinaJava);
                verdadeiro(Modifier.isPublic(tipo.getMethod("doAction",ContextoAcao.class).getModifiers()));
                verdadeiro(Modifier.isPublic(tipo.getMethod("doAction",ContextoAcao.class).getDeclaringClass().getModifiers()));
            }
            verdadeiro(!AcaoRotinaJava.class.isAssignableFrom(AcaoFinanceiroRateio.class));
        });
        executar("Simular roteia NUFIN e composicao sem abrir HTML",()->{
            Ambiente a=new Ambiente();new SimularRateioAction(a).doAction(a.contexto());
            igual("SIMULAR",a.operacao);igual(OrigemCanonica.Tipo.TITULO,a.origem.getTipo());igual(123L,a.origem.getId());
            igual("100.000000",a.entrada.getPercentuais().get(0).getPercentual().toPlainString());
            JsonObject json=new JsonParser().parse(a.retorno).getAsJsonObject();verdadeiro(json.get("sucesso").getAsBoolean());igual("SIMULAR",json.get("operacao").getAsString());
        });
        executar("consulta usa a mesma acao sem calcular",()->{
            Ambiente a=new Ambiente();a.payload="{\"modo\":\"CONSULTAR\"}";new SimularRateioAction(a).doAction(a.contexto());
            igual("CONSULTAR",a.operacao);igual(null,a.entrada);igual("CONSULTAR",new JsonParser().parse(a.retorno).getAsJsonObject().get("operacao").getAsString());
        });
        executar("Recalcular exige revisao motivo e selecionado",()->{
            Ambiente a=new Ambiente();a.payload="{\"simulacaoId\":\"15\",\"revisaoEsperada\":2,\"entrada\":"+ENTRADA+",\"motivo\":\"conferencia\",\"solicitacaoId\":\"r\"}";
            new RecalcularRateioAction(a).doAction(a.contexto());igual("RECALCULAR",a.operacao);igual(15L,a.id);igual(2,a.revisao);igual("conferencia",a.motivo);
        });
        executar("Efetivar apenas encaminha contrato especifico sem simular",()->{
            Ambiente a=new Ambiente();a.payload=efetivar();new EfetivarRateioAction(a).doAction(a.contexto());
            igual("EFETIVAR",a.operacao);igual(15L,a.id);igual(2,a.revisao);igual(hash(),a.hash);igual(null,a.entrada);
        });
        executar("simulacao nao aceita usuario base ou valores finais",()->{
            for(String campo:Arrays.asList("usuario","base","valorFinal")){
                Ambiente a=new Ambiente();a.payload=SIMULAR.substring(0,SIMULAR.length()-1)+",\""+campo+"\":1}";
                erro("COMPOSICAO_INVALIDA",()->new SimularRateioAction(a).doAction(a.contexto()));igual(null,a.operacao);
            }
        });
        executar("campo monetario extra no destino rejeita",()->{
            Ambiente a=new Ambiente();a.payload=SIMULAR.replace("\"percentual\":","\"valor\":100,\"percentual\":");
            erro("COMPOSICAO_INVALIDA",()->new SimularRateioAction(a).doAction(a.contexto()));igual(null,a.operacao);
        });
        executar("selecao unica NUFIN positivo e sessao consistente",()->{
            Ambiente a=new Ambiente();a.linhas=2;erro("BASE_INCOMPLETA",()->new SimularRateioAction(a).doAction(a.contexto()));
            Ambiente b=new Ambiente();b.nufin=BigDecimal.ZERO;erro("BASE_INCOMPLETA",()->new SimularRateioAction(b).doAction(b.contexto()));
            Ambiente c=new Ambiente();c.usuario=11;erro("SEM_PERMISSAO",()->new SimularRateioAction(c).doAction(c.contexto()));
        });
        executar("JSON ausente malformado ou profundo falha antes provider",()->{
            for(String payload:Arrays.asList(null,"","{","[]",String.join("",Collections.nCopies(34,"["))+"0"+String.join("",Collections.nCopies(34,"]")))){
                Ambiente a=new Ambiente();a.payload=payload;erro("COMPOSICAO_INVALIDA",()->new SimularRateioAction(a).doAction(a.contexto()));igual(0,a.resolucoes);
            }
        });
        executar("criterio usa versao explicita e nenhuma escolha implicita",()->{
            Ambiente a=new Ambiente();a.payload="{\"entrada\":{\"modalidade\":\"CRITERIO\",\"criterioId\":\"4\",\"criterioVersao\":7},\"solicitacaoId\":\"c\"}";
            new SimularRateioAction(a).doAction(a.contexto());igual("4",a.entrada.getCriterioId());igual(7,a.entrada.getCriterioVersao());
        });
        executar("hash revisao e limites invalidos rejeitam sem operacao",()->{
            Ambiente a=new Ambiente();a.payload=efetivar().replace(hash(),"invalido");erro("COMPOSICAO_INVALIDA",()->new EfetivarRateioAction(a).doAction(a.contexto()));
            Ambiente b=new Ambiente();b.payload=SIMULAR.replace("pedido-1",String.join("",Collections.nCopies(81,"x")));erro("COMPOSICAO_INVALIDA",()->new SimularRateioAction(b).doAction(b.contexto()));
            igual(null,a.operacao);igual(null,b.operacao);
        });
        executar("falhas funcionais propagam para rollback sem retorno de sucesso",()->{
            Ambiente a=new Ambiente();a.falhar=true;erro("CADASTRO_INVALIDO",()->new SimularRateioAction(a).doAction(a.contexto()));igual(null,a.retorno);
            try{new SimularRateioAction(a).doAction(a.contexto());throw new AssertionError();}catch(ErroRateio e){verdadeiro(!e.getMessage().contains("privado"));}
        });
        executar("falha tecnica preserva etapa e codigo numerico sem vazar detalhes",()->{
            java.sql.SQLException externa=new java.sql.SQLException("privado","",0,new java.sql.SQLException("privado","",1400));
            igual(Integer.valueOf(1400),new br.com.zaya.belmicro.rateio.infraestrutura.jdbc.FalhaConsultaRateio("INSERIR_SIMULACAO",externa).getCodigoBanco());
            java.sql.SQLException proxima=new java.sql.SQLException("privado");proxima.setNextException(new java.sql.SQLException("privado","",942));
            igual(Integer.valueOf(942),new br.com.zaya.belmicro.rateio.infraestrutura.jdbc.FalhaConsultaRateio("LER_AD_RAT",proxima).getCodigoBanco());
            Ambiente a=new Ambiente();
            a.tecnica=new br.com.zaya.belmicro.rateio.infraestrutura.jdbc.FalhaConsultaRateio("INSERIR_SIMULACAO",new java.sql.SQLException("senha SQL privado","42000",904));
            try{new SimularRateioAction(a).doAction(a.contexto());throw new AssertionError();}catch(ErroRateio e){
                igual("ERRO_TECNICO",e.getCodigo());JsonObject j=new JsonParser().parse(e.getMessage()).getAsJsonObject();
                verdadeiro(!j.get("sucesso").getAsBoolean());String mensagem=j.get("mensagem").getAsString();
                verdadeiro(mensagem.contains("INSERIR_SIMULACAO"));verdadeiro(mensagem.contains("904"));verdadeiro(mensagem.contains("Referencia:"));
                verdadeiro(!mensagem.contains("privado"));verdadeiro(!mensagem.contains("senha"));igual(null,a.retorno);
            }
            Ambiente b=new Ambiente();b.tecnica=new IllegalStateException("privado");
            try{new SimularRateioAction(b).doAction(b.contexto());throw new AssertionError();}catch(ErroRateio e){verdadeiro(e.getMessage().contains("ACAO"));verdadeiro(!e.getMessage().contains("privado"));}
        });
        executar("validacao explica percentual e identifica etapa sem expor entrada",()->{
            Ambiente a=new Ambiente();a.payload=SIMULAR.replace("100.000000","1,5");
            try{new SimularRateioAction(a).doAction(a.contexto());throw new AssertionError();}catch(ErroRateio e){
                JsonObject j=new JsonParser().parse(e.getMessage()).getAsJsonObject();igual("COMPOSICAO_INVALIDA",j.get("codigo").getAsString());
                verdadeiro(j.get("mensagem").getAsString().contains("Linha 1"));
                verdadeiro(j.get("mensagem").getAsString().contains("ponto decimal"));
                JsonObject d=j.getAsJsonObject("diagnostico");igual("VALIDAR_COMPOSICAO",d.get("etapa").getAsString());
                verdadeiro(d.get("duracaoMs").getAsLong()>=0);verdadeiro(d.getAsJsonArray("etapas").size()>0);
                verdadeiro(!e.getMessage().contains("1,5"));igual(0,a.resolucoes);
            }
            verdadeiro(!DiagnosticoAcaoRateio.detalheComposicao("senha privada").contains("privada"));
            verdadeiro(DiagnosticoAcaoRateio.detalheComposicao("Percentuais positivos, ate seis casas e soma 100 sao obrigatorios.").contains("100%"));
        });
        executar("consulta e sucesso recebem diagnostico correlacionado sem dados financeiros",()->{
            Ambiente a=new Ambiente();a.payload="{\"modo\":\"CONSULTAR\"}";new SimularRateioAction(a).doAction(a.contexto());
            JsonObject j=new JsonParser().parse(a.retorno).getAsJsonObject(),d=j.getAsJsonObject("diagnostico");
            igual("CONSULTAR",d.get("operacao").getAsString());igual("SUCESSO",d.get("codigo").getAsString());
            igual("7.14",d.get("versao").getAsString());verdadeiro(d.get("referencia").getAsString().matches("[a-f0-9-]{36}"));
            verdadeiro(!d.toString().contains("NUFIN"));verdadeiro(!d.toString().contains("RATEIO_JSON"));
            Ambiente b=new Ambiente();new SimularRateioAction(b).doAction(b.contexto());
            verdadeiro(!new JsonParser().parse(b.retorno).getAsJsonObject().getAsJsonObject("diagnostico").get("referencia").equals(d.get("referencia")));
        });
        executar("percentual informa linha e motivo sem ecoar valor",()->{
            for(String literal:Arrays.asList("null","true","{}")){
                Ambiente a=new Ambiente();a.payload=SIMULAR.replace("\"100.000000\"",literal);
                try{new SimularRateioAction(a).doAction(a.contexto());throw new AssertionError();}catch(ErroRateio e){
                    verdadeiro(new JsonParser().parse(e.getMessage()).getAsJsonObject().get("mensagem").getAsString().contains("Linha 1"));igual(0,a.resolucoes);
                }
            }
            for(String valor:Arrays.asList("","25%","0.0000001","0","101")){
                Ambiente a=new Ambiente();a.payload=SIMULAR.replace("100.000000",valor);
                try{new SimularRateioAction(a).doAction(a.contexto());throw new AssertionError();}catch(ErroRateio e){
                    JsonObject j=new JsonParser().parse(e.getMessage()).getAsJsonObject();igual("COMPOSICAO_INVALIDA",j.get("codigo").getAsString());
                    verdadeiro(j.get("mensagem").getAsString().contains("Linha 1"));igual(0,a.resolucoes);
                }
            }
        });
        executar("JSON ASCII preserva acentos simbolos e caracteres suplementares",()->{
            Map<String,Object> texto=Collections.<String,Object>singletonMap("mensagem","Operação numérica até seis casas → 😀");
            String json=DiagnosticoAcaoRateio.jsonAscii(texto);
            for(char c:json.toCharArray())verdadeiro(c<128);
            igual(texto.get("mensagem"),new JsonParser().parse(json).getAsJsonObject().get("mensagem").getAsString());
        });
        executar("diagnostico identifica SQL Server sem confundir codigo com Oracle",()->{
            Ambiente a=new Ambiente();a.tecnica=new br.com.zaya.belmicro.rateio.infraestrutura.jdbc.FalhaConsultaRateio("BLOQUEAR_ORIGEM",new java.sql.SQLException("privado","",155),"SQLSERVER");
            try{new SimularRateioAction(a).doAction(a.contexto());throw new AssertionError();}catch(ErroRateio e){
                JsonObject j=new JsonParser().parse(e.getMessage()).getAsJsonObject();igual("SQLSERVER",j.getAsJsonObject("diagnostico").get("bancoTipo").getAsString());
                verdadeiro(j.get("mensagem").getAsString().contains("Motor: SQLSERVER"));verdadeiro(!e.getMessage().contains("privado"));
            }
            igual(null,new br.com.zaya.belmicro.rateio.infraestrutura.jdbc.FalhaConsultaRateio("ACAO",null,"dado privado").getBancoTipo());
        });
        executar("acao antiga apenas informa migracao sem carregar provider",()->{
            Ambiente a=new Ambiente();try{new AbrirModalRateioAction().doAction(a.contexto());throw new AssertionError();}catch(IllegalStateException esperada){verdadeiro(esperada.getMessage().contains("SimularRateioAction"));}
            igual(0,a.resolucoes);igual(null,a.operacao);
        });
        executar("backend sem efetivacao real nao retorna sucesso financeiro",()->{
            ConfiguracaoServicosRateio configuracao=conexao->null;
            erro("EFETIVACAO_NAO_DISPONIVEL",()->configuracao.efetivacao(null,null).efetivar(new OrigemCanonica(OrigemCanonica.Tipo.TITULO,123),15,2,hash(),"ef",10));
        });
        System.out.println("PASS: "+cenarios+" cenarios das acoes Financeiro (SDK real, runtime simulado).");
    }
    private static String hash(){return String.join("",Collections.nCopies(64,"a"));}
    private static String efetivar(){return "{\"simulacaoId\":15,\"revisaoEsperada\":2,\"hashEsperado\":\""+hash()+"\",\"solicitacaoId\":\"ef\"}";}
    private interface Teste{void executar()throws Exception;}
    private static void executar(String nome,Teste teste)throws Exception{try{teste.executar();cenarios++;System.out.println("OK "+nome);}catch(Throwable e){throw new AssertionError(nome,e);}}
    private static void erro(String codigo,Teste teste)throws Exception{try{teste.executar();throw new AssertionError("Falha esperada");}catch(ErroRateio e){igual(codigo,e.getCodigo());}}
    private static void igual(Object a,Object b){if(!Objects.equals(a,b))throw new AssertionError(a+" != "+b);}
    private static void verdadeiro(boolean b){if(!b)throw new AssertionError("Condicao nao atendida");}
    private static final class Ambiente extends RuntimeAcoesRateio implements OperacoesAcaoRateio {
        String payload=SIMULAR,retorno,operacao,motivo,hash;int linhas=1,usuario=10,resolucoes,revisao;long id;BigDecimal nufin=BigDecimal.valueOf(123);boolean falhar;OrigemCanonica origem;EntradaSimulacao entrada;
        long usuario(){return 10;}
        OperacoesAcaoRateio operacoes(){resolucoes++;return this;}
        ContextoAcao contexto(){return (ContextoAcao)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{ContextoAcao.class},(o,m,a)->{
            if(m.getName().equals("getUsuarioLogado"))return BigDecimal.valueOf(usuario);
            if(m.getName().equals("getParam"))return payload;
            if(m.getName().equals("setMensagemRetorno")){retorno=(String)a[0];return null;}
            if(m.getName().equals("mostraErro"))throw new IllegalStateException((String)a[0]);
            if(m.getName().equals("getLinhas")){Registro r=(Registro)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{Registro.class},(x,f,v)->{
                if(f.getName().equals("getCampo")&&"NUFIN".equals(v[0]))return nufin;throw new UnsupportedOperationException(f.getName());
            });Registro[] rs=new Registro[linhas];Arrays.fill(rs,r);return rs;}
            throw new UnsupportedOperationException(m.getName());
        });}
        RuntimeException tecnica;
        private Map<String,Object> receber(String operacao,OrigemCanonica origem){this.operacao=operacao;this.origem=origem;if(tecnica!=null)throw tecnica;if(falhar)throw new ErroRateio("CADASTRO_INVALIDO","detalhe privado");return Collections.<String,Object>singletonMap("id","15");}
        public Map<String,Object> abrirContexto(OrigemCanonica origem){return receber("CONSULTAR",origem);}
        public Map<String,Object> simular(OrigemCanonica origem,EntradaSimulacao entrada,String chave){this.entrada=entrada;return receber("SIMULAR",origem);}
        public Map<String,Object> recalcular(OrigemCanonica origem,long id,int revisao,EntradaSimulacao entrada,String motivo,String chave){this.id=id;this.revisao=revisao;this.entrada=entrada;this.motivo=motivo;return receber("RECALCULAR",origem);}
        public Map<String,Object> efetivar(OrigemCanonica origem,long id,int revisao,String hash,String chave){this.id=id;this.revisao=revisao;this.hash=hash;return receber("EFETIVAR",origem);}
    }
}
