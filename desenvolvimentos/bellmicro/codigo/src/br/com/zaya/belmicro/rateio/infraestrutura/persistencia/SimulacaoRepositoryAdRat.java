package br.com.zaya.belmicro.rateio.infraestrutura.persistencia;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.aplicacao.porta.SimulacaoRepository;
import br.com.zaya.belmicro.rateio.dominio.*;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.TabelaRateio;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/** Current relational projection plus immutable versioned event snapshots, using only manual fields. */
public final class SimulacaoRepositoryAdRat implements SimulacaoRepository {
    // RAT1: 5 + 64 + 1 + 4 + 1 + 4 + 1 + 19 = 99 ASCII characters at most.
    private static final int FRAGMENTO=19;
    private static final int MAX_FRAGMENTOS=4000;
    private final TabelasAdRat tabelas;
    public SimulacaoRepositoryAdRat(TabelasAdRat tabelas){this.tabelas=Objects.requireNonNull(tabelas,"tabelas");}
    public <T>T emTransacao(OrigemCanonica origem,String chave,Trabalho<T> trabalho){return tabelas.emTransacao(origem,trabalho);}

    public SimulacaoRateio correnteDe(OrigemCanonica origem){
        Map<String,Object> filtro=origem.getTipo()==OrigemCanonica.Tipo.NOTA?campos("NUNOTA",n(origem.getId()))
                :campos("NUFIN",n(origem.getId()),"NUNOTA",null);
        SimulacaoRateio atual=corrente(unico(tabelas.selecionar(TabelaRateio.SIMULACAO,filtro)));
        if(atual!=null&&!origem.equals(atual.getOrigem()))throw falha();return atual;
    }
    public SimulacaoRateio correntePorId(long id){return corrente(unico(tabelas.selecionar(TabelaRateio.SIMULACAO,campos("NUSIMULACAO",n(id)))));}
    public long idPara(OrigemCanonica origem){SimulacaoRateio s=correnteDe(origem);if(s==null)throw falha();return s.getId();}
    @Override public long idPara(DadosOrigem dados,EntradaSimulacao entrada,long usuario,Instant data){
        if(correnteDe(dados.getOrigem())!=null)throw new ErroRateio("CONCORRENCIA","Simulacao criada por outra solicitacao.");
        Map<String,Object> linha=cabecalho(dados,entrada,usuario,data,null);
        linha.put("NROVERSAO",n(1));linha.put("SITUACAO","P");
        return numero(tabelas.inserir(TabelaRateio.SIMULACAO,linha).get("NUSIMULACAO"));
    }

    public void salvarNovaRevisao(SimulacaoRateio s){
        Map<String,Object> chave=campos("NUSIMULACAO",n(s.getId()));
        Map<String,Object> existente=unico(tabelas.selecionar(TabelaRateio.SIMULACAO,chave));
        if(existente==null)throw falha();
        SimulacaoRateio anterior=corrente(existente);
        if(anterior!=null && (!anterior.getOrigem().equals(s.getOrigem()) || anterior.getRevisao()+1!=s.getRevisao()))
            throw new ErroRateio("SIMULACAO_DESATUALIZADA","Revisao corrente divergente.");
        if(anterior==null && s.getRevisao()!=1)throw falha();
        Map<String,Object> linha=cabecalho(s.getDadosOrigem(),s.getEntrada(),s.getUsuario(),s.getData(),s.getMotivo());
        linha.put("NROVERSAO",n(s.getRevisao()));linha.put("SITUACAO",s.getSituacao()==SimulacaoRateio.Situacao.VALIDA?"S":"P");
        // Request registration completes this header in the same transaction.
        linha.put("CHAVESOLIC",null);tabelas.atualizar(TabelaRateio.SIMULACAO,chave,linha);
        tabelas.excluirFilhos(TabelaRateio.INTERNO,s.getId());
        tabelas.excluirFilhos(TabelaRateio.GRUPO,s.getId());
        tabelas.excluirFilhos(TabelaRateio.PARCELA,s.getId());
        if(s.getResultado()==null)return;
        List<BigDecimal> pesos=new ArrayList<BigDecimal>();
        for(ParcelaSimulacao p:s.getResultado().getParcelas())pesos.add(p.getPeso());
        List<BigDecimal> percentuais=DistribuidorProporcional.distribuir(new BigDecimal("100"),pesos,6);
        int indice=0;
        for(ParcelaSimulacao p:s.getResultado().getParcelas()){
            tabelas.inserir(TabelaRateio.PARCELA,campos("NUSIMULACAO",n(s.getId()),"CODEMP",n(p.getCodEmp()),
                    "CODCENCUS",n(p.getCodCencus()),"PERCGLOBAL",percentuais.get(indice++),"VLRDESTINO",p.getValor(),
                    "DESTINO",p.isOrigem()?"O":"A","NUCOMPONENTE",componenteNumerico(p.getComponente())));
        }
        for(GrupoPrevisto g:s.getResultado().getGrupos()){
            Map<String,Object> grupo=tabelas.inserir(TabelaRateio.GRUPO,campos("NUSIMULACAO",n(s.getId()),"CODEMP",n(g.getCodEmp()),
                    "VLRGRUPO",g.getValorTotal(),"NUFINAUX",null));
            BigDecimal seq=new BigDecimal(numero(grupo.get("SEQUENCIA")));
            for(LinhaRateio l:g.getLinhasPorCr())tabelas.inserir(TabelaRateio.INTERNO,campos("NUSIMULACAO",n(s.getId()),
                    "SEQUENCIA",seq,"CODCENCUS",n(l.getCodCencus()),"PERCINTERNO",l.getPercentual(),"VLRINTERNO",l.getValor()));
        }
    }

    public void registrarSolicitacao(String chave,String identidade,SimulacaoRateio s){
        if(chave==null||chave.isEmpty()||chave.length()>80)throw falha();
        if(porSolicitacao(chave)!=null)throw new ErroRateio("SOLICITACAO_INCOMPATIVEL","Solicitacao ja registrada.");
        String payload=FotografiaCodec.escrever(identidade,s),hash=hashChave(chave);
        int total=(payload.length()+FRAGMENTO-1)/FRAGMENTO;
        if(total>MAX_FRAGMENTOS)throw new ErroRateio("FOTOGRAFIA_EXCEDE_LIMITE","Fotografia excede o limite de armazenamento do evento.");
        for(int i=0;i<total;i++){
            String fragmento="RAT1|"+hash+"|"+i+"|"+total+"|"+payload.substring(i*FRAGMENTO,Math.min(payload.length(),(i+1)*FRAGMENTO));
            tabelas.inserir(TabelaRateio.EVENTO,campos("NUSIMULACAO",n(s.getId()),"TIPOEVENTO",s.getRevisao()==1?"S":"R",
                    "DHEVENTO",Timestamp.from(s.getData()),"CODUSU",n(s.getUsuario()),"DESCRICAO",fragmento));
        }
        tabelas.atualizar(TabelaRateio.SIMULACAO,campos("NUSIMULACAO",n(s.getId())),campos("CHAVESOLIC",chave));
    }

    public Solicitacao porSolicitacao(String chave){
        List<Map<String,Object>> linhas=tabelas.eventosSolicitacao(hashChave(chave));if(linhas.isEmpty())return null;
        SortedMap<Integer,String> fragmentos=new TreeMap<Integer,String>();int total=-1;Long simulacao=null;
        for(Map<String,Object> linha:linhas){
            String[] partes=String.valueOf(linha.get("DESCRICAO")).split("\\|",5);
            if(partes.length!=5||!"RAT1".equals(partes[0])||!hashChave(chave).equals(partes[1]))throw falha();
            try{
                int indice=Integer.parseInt(partes[2]),q=Integer.parseInt(partes[3]);
                if(q<=0||q>MAX_FRAGMENTOS||indice<0||indice>=q||(total!=-1&&total!=q)||fragmentos.put(indice,partes[4])!=null)throw falha();
                total=q;long id=numero(linha.get("NUSIMULACAO"));if(simulacao!=null&&simulacao!=id)throw falha();simulacao=id;
            }catch(NumberFormatException e){throw falha();}
        }
        if(fragmentos.size()!=total)throw falha();StringBuilder payload=new StringBuilder();
        for(String parte:fragmentos.values())payload.append(parte);
        Solicitacao solicitacao=FotografiaCodec.ler(payload.toString());
        if(solicitacao.getResultado().getId()!=simulacao)throw falha();return solicitacao;
    }

    private SimulacaoRateio corrente(Map<String,Object> cabecalho){
        if(cabecalho==null||cabecalho.get("CHAVESOLIC")==null)return null;
        Solicitacao solicitacao=porSolicitacao(cabecalho.get("CHAVESOLIC").toString());
        if(solicitacao==null)throw falha();SimulacaoRateio s=solicitacao.getResultado();
        if(s.getId()!=numero(cabecalho.get("NUSIMULACAO"))||s.getRevisao()!=numero(cabecalho.get("NROVERSAO")))throw falha();
        return s;
    }
    private static Map<String,Object> cabecalho(DadosOrigem d,EntradaSimulacao entrada,long usuario,Instant data,String motivo){
        OrigemCanonica origem=d.getOrigem();return campos("NUNOTA",origem.getTipo()==OrigemCanonica.Tipo.NOTA?n(origem.getId()):null,
                "NUFIN",origem.getTipo()==OrigemCanonica.Tipo.TITULO?n(origem.getId()):null,
                "MODALIDADE",entrada.getModalidade()==EntradaSimulacao.Modalidade.MANUAL?"M":"C",
                "CODEMPORIG",n(d.getCodEmp()),"CODNAT",n(d.getCodNat()),"CODPARC",d.getCodParc()==null?null:n(d.getCodParc()),
                "CODTIPOPER",d.getCodTipOper()==null?null:n(d.getCodTipOper()),
                "DTCOMPET",d.getCompetencia()==null?null:java.sql.Date.valueOf(d.getCompetencia()),"VLRBASE",d.getBase(),
                "DHREGISTRO",Timestamp.from(data),"CODUSU",n(usuario),"MOTIVORECALC",motivo);
    }
    private static BigDecimal componenteNumerico(String componente){
        try{BigDecimal n=new BigDecimal(componente);if(n.signum()>0&&n.scale()<=0&&n.precision()<=10)return n;}
        catch(NumberFormatException ignorada){/* Named components are preserved losslessly in event snapshots. */}return null;
    }
    public static String hashChave(String chave){
        try{byte[] h=MessageDigest.getInstance("SHA-256").digest(chave.getBytes(StandardCharsets.UTF_8));StringBuilder s=new StringBuilder();
            for(byte b:h){s.append(Character.forDigit((b>>>4)&15,16));s.append(Character.forDigit(b&15,16));}return s.toString();
        }catch(NoSuchAlgorithmException e){throw falha();}
    }
    private static Map<String,Object> unico(List<Map<String,Object>> linhas){if(linhas.size()>1)throw new ErroRateio("REGRA_CONFLITANTE","Cabecalhos duplicados para a origem.");return linhas.isEmpty()?null:linhas.get(0);}
    private static long numero(Object valor){try{return new BigDecimal(String.valueOf(valor)).longValueExact();}catch(RuntimeException e){throw falha();}}
    private static BigDecimal n(long valor){return BigDecimal.valueOf(valor);}
    private static Map<String,Object> campos(Object... pares){Map<String,Object> r=new LinkedHashMap<String,Object>();for(int i=0;i<pares.length;i+=2)r.put((String)pares[i],pares[i+1]);return r;}
    private static IllegalStateException falha(){return new IllegalStateException("Persistencia de rateio inconsistente.");}
}
