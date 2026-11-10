package br.com.zaya.belmicro.rateio.infraestrutura.persistencia;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.dominio.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
import java.util.zip.*;

/** Versioned length-prefixed format, not Java deserialization. No arbitrary class loading. */
public final class FotografiaCodec {
    private static final int LIMITE=8*1024*1024;
    private FotografiaCodec() { }

    public static String escrever(String identidade, SimulacaoRateio s) {
        try {
            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            DataOutputStream o=new DataOutputStream(bytes);
            o.writeInt(1); texto(o,identidade); o.writeLong(s.getId()); o.writeInt(s.getRevisao());
            DadosOrigem d=s.getDadosOrigem();
            texto(o,d.getOrigem().getTipo().name()); o.writeLong(d.getOrigem().getId());
            o.writeInt(d.getCodEmp()); o.writeLong(d.getCodNat()); opcional(o,d.getCodParc()); opcional(o,d.getCodTipOper());
            decimal(o,d.getBase()); texto(o,d.getCompetencia()==null?null:d.getCompetencia().toString());
            texto(o,d.getVersaoAlgoritmoMapeamento()); o.writeBoolean(d.isAuxiliar());
            quantidade(o,d.getCamposMateriais().size());
            for(Map.Entry<String,String> e:d.getCamposMateriais().entrySet()){texto(o,e.getKey());texto(o,e.getValue());}
            EntradaSimulacao e=s.getEntrada(); texto(o,e.getModalidade().name());
            if(e.getModalidade()==EntradaSimulacao.Modalidade.CRITERIO){texto(o,e.getCriterioId());o.writeInt(e.getCriterioVersao());}
            else {quantidade(o,e.getPercentuais().size());for(EntradaSimulacao.Percentual p:e.getPercentuais()){
                o.writeInt(p.getCodEmp());o.writeInt(p.getCodCencus());decimal(o,p.getPercentual());}}
            texto(o,s.getSituacao().name());texto(o,s.getHash());texto(o,s.getPendencia());o.writeLong(s.getUsuario());
            texto(o,s.getData().toString());texto(o,s.getMotivo());
            ResultadoCalculo r=s.getResultado();o.writeBoolean(r!=null);
            if(r!=null){
                decimal(o,r.getBase());quantidade(o,r.getParcelas().size());
                for(ParcelaSimulacao p:r.getParcelas()){
                    o.writeInt(p.getCodEmp());o.writeInt(p.getCodCencus());texto(o,p.getComponente());
                    decimal(o,p.getPeso());texto(o,p.getEvidencia());texto(o,p.getChaveCompatibilidade());
                    decimal(o,p.getParticipacaoGlobal());decimal(o,p.getValor());o.writeBoolean(p.isOrigem());
                }
                quantidade(o,r.getGrupos().size());for(GrupoPrevisto g:r.getGrupos()){
                    o.writeInt(g.getCodEmp());texto(o,g.getChaveCompatibilidade());decimal(o,g.getValorTotal());
                    quantidade(o,g.getLinhasPorCr().size());for(LinhaRateio l:g.getLinhasPorCr()){
                        o.writeInt(l.getCodCencus());decimal(o,l.getValor());decimal(o,l.getPercentual());}
                }
            }
            o.flush();if(bytes.size()>LIMITE)throw falha();
            ByteArrayOutputStream compactado=new ByteArrayOutputStream();
            try(GZIPOutputStream gzip=new GZIPOutputStream(compactado)){gzip.write(bytes.toByteArray());}
            return Base64.getEncoder().encodeToString(compactado.toByteArray());
        }catch(IOException ex){throw falha();}
    }

    public static br.com.zaya.belmicro.rateio.aplicacao.porta.SimulacaoRepository.Solicitacao ler(String base64) {
        try {
            if(base64==null||base64.length()>LIMITE*2)throw falha();
            ByteArrayOutputStream descompactado=new ByteArrayOutputStream();
            try(GZIPInputStream gzip=new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(base64)))){
                byte[] buffer=new byte[4096];int n;while((n=gzip.read(buffer))!=-1){
                    if(descompactado.size()+n>LIMITE)throw falha();descompactado.write(buffer,0,n);}}
            DataInputStream i=new DataInputStream(new ByteArrayInputStream(descompactado.toByteArray()));
            if(i.readInt()!=1)throw falha();String identidade=texto(i);long id=i.readLong();int revisao=i.readInt();
            OrigemCanonica origem=new OrigemCanonica(OrigemCanonica.Tipo.valueOf(texto(i)),i.readLong());
            int empresa=i.readInt();long natureza=i.readLong();Long parceiro=opcional(i),top=opcional(i);
            BigDecimal base=decimal(i);String competencia=texto(i),versao=texto(i);boolean auxiliar=i.readBoolean();
            Map<String,String> campos=new TreeMap<String,String>();int tamanho=quantidade(i);
            for(int n=0;n<tamanho;n++){String nome=texto(i),valor=texto(i);if(nome==null||campos.containsKey(nome))throw falha();campos.put(nome,valor);}
            DadosOrigem dados=new DadosOrigem(origem,empresa,natureza,parceiro,top,base,
                    competencia==null?null:LocalDate.parse(competencia),versao,campos,auxiliar);
            String modalidade=texto(i);EntradaSimulacao entrada;
            if("CRITERIO".equals(modalidade))entrada=EntradaSimulacao.criterio(texto(i),i.readInt());
            else if("MANUAL".equals(modalidade)){
                List<EntradaSimulacao.Percentual> p=new ArrayList<EntradaSimulacao.Percentual>();tamanho=quantidade(i);
                for(int n=0;n<tamanho;n++)p.add(new EntradaSimulacao.Percentual(i.readInt(),i.readInt(),decimal(i)));
                entrada=EntradaSimulacao.manual(p);
            }else throw falha();
            SimulacaoRateio.Situacao situacao=SimulacaoRateio.Situacao.valueOf(texto(i));
            String hash=texto(i),pendencia=texto(i);long usuario=i.readLong();Instant data=Instant.parse(texto(i));String motivo=texto(i);
            ResultadoCalculo resultado=null;
            if(i.readBoolean()){
                BigDecimal total=decimal(i);List<ParcelaSimulacao> parcelas=new ArrayList<ParcelaSimulacao>();tamanho=quantidade(i);
                for(int n=0;n<tamanho;n++){
                    DestinoPeso d=new DestinoPeso(i.readInt(),i.readInt(),texto(i),decimal(i),texto(i),texto(i));
                    parcelas.add(new ParcelaSimulacao(d,decimal(i),decimal(i),i.readBoolean()));
                }
                List<GrupoPrevisto> grupos=new ArrayList<GrupoPrevisto>();tamanho=quantidade(i);
                for(int n=0;n<tamanho;n++){
                    int emp=i.readInt();String chave=texto(i);BigDecimal valor=decimal(i);List<LinhaRateio> linhas=new ArrayList<LinhaRateio>();
                    int q=quantidade(i);for(int k=0;k<q;k++)linhas.add(new LinhaRateio(i.readInt(),decimal(i),decimal(i)));
                    grupos.add(new GrupoPrevisto(emp,chave,valor,linhas));
                }
                resultado=new ResultadoCalculo(total,parcelas,grupos);
            }
            if(i.available()!=0)throw falha();
            SimulacaoRateio s=new SimulacaoRateio(id,revisao,dados,entrada,resultado,hash,situacao,pendencia,usuario,data,motivo);
            if(resultado!=null&&!Objects.equals(hash,HashComposicao.calcular(dados.contextoHash(entrada,resultado))))throw falha();
            if(identidade==null||!identidade.matches("[0-9a-f]{64}"))throw falha();
            return new br.com.zaya.belmicro.rateio.aplicacao.porta.SimulacaoRepository.Solicitacao(identidade,s);
        }catch(IOException|IllegalArgumentException ex){throw falha();}
    }

    private static int quantidade(DataInputStream i)throws IOException{int n=i.readInt();if(n<0||n>100000)throw falha();return n;}
    private static void quantidade(DataOutputStream o,int n)throws IOException{if(n<0||n>100000)throw falha();o.writeInt(n);}
    private static void texto(DataOutputStream o,String s)throws IOException{
        if(s==null){o.writeInt(-1);return;}byte[] b=s.getBytes(StandardCharsets.UTF_8);if(b.length>LIMITE)throw falha();o.writeInt(b.length);o.write(b);}
    private static String texto(DataInputStream i)throws IOException{
        int n=i.readInt();if(n==-1)return null;if(n<0||n>LIMITE||n>i.available())throw falha();byte[] b=new byte[n];i.readFully(b);return new String(b,StandardCharsets.UTF_8);}
    private static void decimal(DataOutputStream o,BigDecimal v)throws IOException{texto(o,v==null?null:v.toPlainString());}
    private static BigDecimal decimal(DataInputStream i)throws IOException{String v=texto(i);return v==null?null:new BigDecimal(v);}
    private static void opcional(DataOutputStream o,Long v)throws IOException{o.writeBoolean(v!=null);if(v!=null)o.writeLong(v);}
    private static Long opcional(DataInputStream i)throws IOException{return i.readBoolean()?i.readLong():null;}
    private static IllegalStateException falha(){return new IllegalStateException("Fotografia de rateio invalida ou incompativel.");}
}
