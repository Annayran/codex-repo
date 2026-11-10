package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

/** Selected due date is independent of whether the document total can be calculated. */
public final class ReferenciaFinanceiraJdbc {
    private final ConexaoRateio conexao;
    public ReferenciaFinanceiraJdbc(ConexaoRateio conexao){this.conexao=conexao;}
    public Referencia carregar(OrigemCanonica documento,long nufin){
        try(PreparedStatement p=conexao.obter().prepareStatement("SELECT NUFIN,NUNOTA,DTVENC,RECDESP,PROVISAO,NURENEG FROM TGFFIN WHERE NUFIN=?")){
            p.setLong(1,nufin);
            try(ResultSet r=p.executeQuery()){
                if(!r.next())throw new ErroRateio("BASE_INCOMPLETA","Referencia financeira indisponivel.");
                BigDecimal id=r.getBigDecimal("NUFIN"),nota=r.getBigDecimal("NUNOTA");
                boolean mesma=id!=null&&id.compareTo(BigDecimal.valueOf(nufin))==0;
                if(documento.getTipo()==OrigemCanonica.Tipo.NOTA)mesma=mesma&&nota!=null&&nota.compareTo(BigDecimal.valueOf(documento.getId()))==0;
                else mesma=mesma&&documento.getId()==nufin&&(nota==null||nota.signum()==0);
                if(!mesma)throw new ErroRateio("SIMULACAO_DESATUALIZADA","Referencia financeira mudou de origem.");
                java.sql.Date data=r.getDate("DTVENC");BigDecimal tipo=r.getBigDecimal("RECDESP"),reneg=r.getBigDecimal("NURENEG");
                String motivo=null;
                if(tipo==null||tipo.compareTo(BigDecimal.valueOf(-1))!=0)motivo="TIPO_NAO_DESPESA";
                else if("S".equals(r.getString("PROVISAO")))motivo="TITULO_PROVISIONADO";
                else if(reneg!=null&&reneg.signum()!=0)motivo="DOCUMENTO_RENEGOCIADO";
                else if(data==null)motivo="VENCIMENTO_SELECIONADO_AUSENTE";
                Referencia resultado=new Referencia(data==null?null:data.toLocalDate(),motivo);
                if(r.next())throw new ErroRateio("CADASTRO_INVALIDO","Referencia financeira duplicada.");
                return resultado;
            }
        }catch(SQLException e){throw new FalhaConsultaRateio("REFERENCIA_FINANCEIRA");}
    }
    public static final class Referencia {
        private final LocalDate vencimento;
        private final String motivo;
        private Referencia(LocalDate vencimento,String motivo){this.vencimento=vencimento;this.motivo=motivo;}
        public LocalDate getVencimento(){return vencimento;}
        public String getMotivo(){return motivo;}
    }
}
