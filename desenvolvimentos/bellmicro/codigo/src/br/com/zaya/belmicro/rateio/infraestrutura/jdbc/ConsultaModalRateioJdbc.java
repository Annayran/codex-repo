package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import java.sql.*;
import java.util.*;

/** Read-only modal enrichment. Caller must authorize the selected and canonical origins first. */
public final class ConsultaModalRateioJdbc {
    private final ConexaoRateio conexao;
    public ConsultaModalRateioJdbc(ConexaoRateio conexao){this.conexao=Objects.requireNonNull(conexao,"conexao");}
    public Map<String,Object> financeiro(long nufin){
        if(nufin<=0)throw new ErroRateio("BASE_INCOMPLETA","NUFIN invalido.");
        String sql="SELECT F.NUFIN,F.NUNOTA,F.NUMNOTA,F.CODEMP,E.NOMEFANTASIA,F.CODNAT,F.CODPARC,P.NOMEPARC,F.CODTIPOPER,F.VLRDESDOB,F.DTVENC,F.DESDOBRAMENTO FROM TGFFIN F LEFT JOIN TSIEMP E ON E.CODEMP=F.CODEMP LEFT JOIN TGFPAR P ON P.CODPARC=F.CODPARC WHERE F.NUFIN=?";
        try(PreparedStatement p=conexao.obter().prepareStatement(sql)){
            p.setLong(1,nufin);
            try(ResultSet r=p.executeQuery()){
                if(!r.next())throw new ErroRateio("BASE_INCOMPLETA","Titulo selecionado indisponivel.");
                Map<String,Object> f=new LinkedHashMap<String,Object>();
                for(String campo:Arrays.asList("NUFIN","NUNOTA","NUMNOTA","CODEMP","CODNAT","CODPARC","CODTIPOPER")){
                    java.math.BigDecimal n=r.getBigDecimal(campo);
                    try{f.put(campo,n==null?null:n.toBigIntegerExact().toString());}
                    catch(ArithmeticException e){throw new ErroRateio("CADASTRO_INVALIDO","Identificador financeiro invalido.");}
                }
                if(!String.valueOf(nufin).equals(f.get("NUFIN")))throw new ErroRateio("CADASTRO_INVALIDO","NUFIN divergente.");
                f.put("NOMEFANTASIA",r.getString("NOMEFANTASIA"));f.put("NOMEPARC",r.getString("NOMEPARC"));
                f.put("DESDOBRAMENTO",r.getString("DESDOBRAMENTO"));
                java.math.BigDecimal valor=r.getBigDecimal("VLRDESDOB");f.put("VLRDESDOB",valor==null?null:valor.toPlainString());
                java.sql.Date venc=r.getDate("DTVENC");f.put("DTVENC",venc==null?null:venc.toLocalDate().toString());
                if(r.next())throw new ErroRateio("REGRA_CONFLITANTE","Titulo selecionado duplicado.");
                return Collections.unmodifiableMap(f);
            }
        }catch(SQLException e){throw new FalhaConsultaRateio("FINANCEIRO_MODAL");}
    }
    public Map<String,Object> catalogos(){
        Map<String,Object> mapa=new LinkedHashMap<String,Object>();boolean[] limite={false};
        mapa.put("empresas",lista("SELECT CODEMP AS CODIGO,NOMEFANTASIA AS NOME FROM TSIEMP WHERE CODEMP>0 ORDER BY CODEMP",limite));
        mapa.put("centros",lista("SELECT CODCENCUS AS CODIGO,DESCRCENCUS AS NOME FROM TSICUS WHERE CODCENCUS>0 AND ATIVO='S' ORDER BY CODCENCUS",limite));
        mapa.put("limiteAtingido",limite[0]);return Collections.unmodifiableMap(mapa);
    }
    private List<Map<String,String>> lista(String sql,boolean[] limite){
        List<Map<String,String>> lista=new ArrayList<Map<String,String>>();
        try(PreparedStatement p=conexao.obter().prepareStatement(sql);ResultSet r=p.executeQuery()){
            while(r.next()){
                if(lista.size()==10000){limite[0]=true;break;}
                java.math.BigDecimal codigo=r.getBigDecimal("CODIGO");
                if(codigo==null||codigo.signum()<=0||codigo.stripTrailingZeros().scale()>0)throw new ErroRateio("CADASTRO_INVALIDO","Codigo de catalogo invalido.");
                Map<String,String> linha=new LinkedHashMap<String,String>();linha.put("id",codigo.toBigIntegerExact().toString());linha.put("nome",r.getString("NOME"));
                lista.add(Collections.unmodifiableMap(linha));
            }
        }catch(SQLException e){throw new FalhaConsultaRateio("CATALOGOS_MODAL");}
        return Collections.unmodifiableList(lista);
    }
}
