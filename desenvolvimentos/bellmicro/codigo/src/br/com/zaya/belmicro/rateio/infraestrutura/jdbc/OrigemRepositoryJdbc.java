package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.aplicacao.porta.OrigemRepository;
import br.com.zaya.belmicro.rateio.dominio.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/** Read-only canonical origin and batched registration validation (IntPDD sections 3/4/8). */
public final class OrigemRepositoryJdbc implements OrigemRepository {
    private static final String FINANCEIRO=ConsultasRateio.ler("origem-financeiro.sql");
    private static final String NOTA=ConsultasRateio.ler("origem-nota.sql");
    private static final int TAMANHO_LOTE=500;
    private final ConexaoRateio conexao;
    private final MapeamentoOrigem mapeamento;
    private final MarcadorAuxiliarRepositoryJdbc marcadores;

    public OrigemRepositoryJdbc(ConexaoRateio conexao,MapeamentoOrigem mapeamento){
        this.conexao=Objects.requireNonNull(conexao,"conexao");this.mapeamento=Objects.requireNonNull(mapeamento,"mapeamento");
        marcadores=new MarcadorAuxiliarRepositoryJdbc(conexao);
    }
    public DadosOrigem resolver(OrigemCanonica referencia){
        Objects.requireNonNull(referencia,"referencia");
        DocumentoOrigem documento;
        if(referencia.getTipo()==OrigemCanonica.Tipo.TITULO){
            if(marcadores.marcado(referencia.getId()))throw new ErroRateio("ORIGEM_AUXILIAR","Auxiliar nao pode ser origem de rateio.");
            Financeiro financeiro=financeiro(referencia.getId());
            documento=financeiro.nunota==null?financeiro.documento:nota(financeiro.nunota);
        }else documento=nota(referencia.getId());
        MapeamentoOrigem.BaseCompetencia regra=mapeamento.calcular(documento,conexao,referencia);
        if(regra==null)regra=MapeamentoOrigem.BaseCompetencia.pendente();
        return new DadosOrigem(documento.getOrigem(),documento.getCodEmp(),documento.getCodNat(),documento.getCodParc(),
                documento.getCodTipOper(),regra.getBase(),regra.getCompetencia(),regra.getVersao(),regra.getCamposMateriais(),false);
    }
    public void validarDestinos(DadosOrigem origem,List<DestinoPeso> destinos){
        if(origem==null||destinos==null||destinos.isEmpty())throw erro("BASE_INCOMPLETA","Origem e destinos obrigatorios.");
        Set<Integer> empresas=new TreeSet<Integer>(),crs=new TreeSet<Integer>();empresas.add(origem.getCodEmp());
        for(DestinoPeso destino:destinos){if(destino==null)throw erro("BASE_INCOMPLETA","Destino ausente.");empresas.add(destino.getCodEmp());crs.add(destino.getCodCencus());}
        conferir("TSIEMP","CODEMP",false,empresas);conferir("TSICUS","CODCENCUS",true,crs);
    }
    private DocumentoOrigem nota(long id){
        try(PreparedStatement p=conexao.obter().prepareStatement(NOTA)){
            p.setLong(1,id);try(ResultSet r=p.executeQuery()){
                if(!r.next())throw erro("BASE_INCOMPLETA","Nota de origem indisponivel.");
                if(numero(r.getBigDecimal("NUNOTA"),false)!=id)throw erro("CADASTRO_INVALIDO","Identidade da nota divergente.");
                DocumentoOrigem d=documento(r,new OrigemCanonica(OrigemCanonica.Tipo.NOTA,id));
                if(r.next())throw erro("REGRA_CONFLITANTE","Nota de origem duplicada.");return d;
            }
        }catch(SQLException e){throw new FalhaConsultaRateio("ORIGEM_NOTA");}
    }
    private Financeiro financeiro(long id){
        try(PreparedStatement p=conexao.obter().prepareStatement(FINANCEIRO)){
            p.setLong(1,id);try(ResultSet r=p.executeQuery()){
                if(!r.next())throw erro("BASE_INCOMPLETA","Titulo de origem indisponivel.");
                if(numero(r.getBigDecimal("NUFIN"),false)!=id)throw erro("CADASTRO_INVALIDO","Identidade financeira divergente.");
                BigDecimal nota=r.getBigDecimal("NUNOTA");Long nunota=nota==null||nota.signum()==0?null:numero(nota,false);
                // For a financial installment, the note's header is authoritative; do not use installment amounts.
                DocumentoOrigem d=nunota==null?documento(r,new OrigemCanonica(OrigemCanonica.Tipo.TITULO,id)):null;
                if(r.next())throw erro("REGRA_CONFLITANTE","Titulo de origem duplicado.");return new Financeiro(d,nunota);
            }
        }catch(SQLException e){throw new FalhaConsultaRateio("ORIGEM_FINANCEIRO");}
    }
    private static DocumentoOrigem documento(ResultSet r,OrigemCanonica origem)throws SQLException{
        long empresa=numero(r.getBigDecimal("CODEMP"),false);if(empresa>Integer.MAX_VALUE)throw erro("CADASTRO_INVALIDO","Empresa fora do intervalo suportado.");
        return new DocumentoOrigem(origem,(int)empresa,numero(r.getBigDecimal("CODNAT"),false),
                opcional(r.getBigDecimal("CODPARC")),opcional(r.getBigDecimal("CODTIPOPER")));
    }
    private void conferir(String tabela,String campo,boolean ativo,Set<Integer> codigos){
        List<Integer> lista=new ArrayList<Integer>(codigos);
        for(int inicio=0;inicio<lista.size();inicio+=TAMANHO_LOTE){
            List<Integer> lote=lista.subList(inicio,Math.min(lista.size(),inicio+TAMANHO_LOTE));
            for(Integer codigo:lote)if(codigo==null||codigo<=0)throw erro("CADASTRO_INVALIDO","Destino invalido.");
            String marcadores=String.join(",",Collections.nCopies(lote.size(),"?"));
            // Only fixed internal identifiers and placeholder counts enter the SQL, never request values.
            String sql="SELECT "+campo+" FROM "+tabela+" WHERE "+campo+" IN ("+marcadores+")"+(ativo?" AND ATIVO = 'S'":"");
            Set<Integer> encontrados=new HashSet<Integer>();
            try(PreparedStatement p=conexao.obter().prepareStatement(sql)){
                for(int i=0;i<lote.size();i++)p.setInt(i+1,lote.get(i));
                try(ResultSet r=p.executeQuery()){while(r.next()){
                    long codigo=numero(r.getBigDecimal(campo),false);if(codigo>Integer.MAX_VALUE)throw erro("CADASTRO_INVALIDO","Cadastro fora do intervalo.");encontrados.add((int)codigo);
                }}
            }catch(SQLException e){throw new FalhaConsultaRateio("VALIDAR_DESTINOS");}
            if(!encontrados.equals(new HashSet<Integer>(lote)))throw erro("CADASTRO_INVALIDO","Empresa inexistente ou centro de resultado inexistente/inativo.");
        }
    }
    private static Long opcional(BigDecimal valor){return valor==null?null:numero(valor,false);}
    private static long numero(BigDecimal valor,boolean permiteZero){
        try{if(valor==null||valor.signum()<0||(!permiteZero&&valor.signum()==0))throw new ArithmeticException();return valor.longValueExact();}
        catch(ArithmeticException e){throw erro("CADASTRO_INVALIDO","Identificador de origem invalido.");}
    }
    private static ErroRateio erro(String codigo,String mensagem){return new ErroRateio(codigo,mensagem);}
    private static final class Financeiro{final DocumentoOrigem documento;final Long nunota;Financeiro(DocumentoOrigem d,Long n){documento=d;nunota=n;}}
}
