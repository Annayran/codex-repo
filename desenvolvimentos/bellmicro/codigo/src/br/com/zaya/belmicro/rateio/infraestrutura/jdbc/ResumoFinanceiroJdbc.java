package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/** Nominal total and installments of the canonical document; never group by NUMNOTA. */
public final class ResumoFinanceiroJdbc {
    private final ConexaoRateio conexao;
    public ResumoFinanceiroJdbc(ConexaoRateio conexao){this.conexao=Objects.requireNonNull(conexao,"conexao");}
    public Resumo carregar(OrigemCanonica origem){
        String filtro=origem.getTipo()==OrigemCanonica.Tipo.NOTA?"F.NUNOTA=?":"F.NUFIN=? AND (F.NUNOTA IS NULL OR F.NUNOTA=0)";
        String sql="SELECT F.NUFIN,F.VLRDESDOB,F.DTVENC,F.DESDOBRAMENTO,F.NURENEG FROM TGFFIN F WHERE "+filtro
                +" AND F.RECDESP=-1 AND (F.PROVISAO IS NULL OR F.PROVISAO='N')"
                +" AND NOT EXISTS (SELECT 1 FROM AD_RATFIN M WHERE M.NUFIN=F.NUFIN AND M.MARCAUX='S') ORDER BY F.NUFIN";
        BigDecimal total=BigDecimal.ZERO;LocalDate primeiro=null;List<Map<String,String>> parcelas=new ArrayList<Map<String,String>>();
        Set<String> ids=new HashSet<String>();
        try(PreparedStatement p=conexao.obter().prepareStatement(sql)){
            p.setLong(1,origem.getId());
            try(ResultSet r=p.executeQuery()){
                while(r.next()){
                    BigDecimal id=r.getBigDecimal("NUFIN"),valor=r.getBigDecimal("VLRDESDOB"),reneg=r.getBigDecimal("NURENEG");
                    if(id==null||id.signum()<=0||id.stripTrailingZeros().scale()>0||!ids.add(id.toBigIntegerExact().toString()))throw erro("CADASTRO_INVALIDO","IDENTIDADE_PARCELA_INVALIDA");
                    if(reneg!=null&&reneg.signum()!=0)throw erro("BASE_INCOMPLETA","DOCUMENTO_RENEGOCIADO");
                    if(valor==null||valor.signum()<=0||valor.stripTrailingZeros().scale()>2)throw erro("BASE_INCOMPLETA","VALOR_PARCELA_INVALIDO");
                    java.sql.Date data=r.getDate("DTVENC");LocalDate venc=data==null?null:data.toLocalDate();
                    if(venc!=null&&(primeiro==null||venc.isBefore(primeiro)))primeiro=venc;
                    total=total.add(valor);
                    Map<String,String> linha=new LinkedHashMap<String,String>();linha.put("nufin",id.toBigIntegerExact().toString());
                    linha.put("desdobramento",r.getString("DESDOBRAMENTO"));linha.put("valor",valor.setScale(2).toPlainString());linha.put("vencimento",venc==null?null:venc.toString());
                    parcelas.add(Collections.unmodifiableMap(linha));
                }
            }
        }catch(SQLException e){throw new FalhaConsultaRateio("RESUMO_FINANCEIRO");}
        if(parcelas.isEmpty())throw erro("BASE_INCOMPLETA","SEM_PARCELAS_ELEGIVEIS");
        return new Resumo(total.setScale(2),primeiro,parcelas);
    }
    public static final class Resumo {
        private final BigDecimal total;
        private final LocalDate primeiroVencimento;
        private final List<Map<String,String>> parcelas;
        private Resumo(BigDecimal total,LocalDate primeiro,List<Map<String,String>> parcelas){this.total=total;this.primeiroVencimento=primeiro;this.parcelas=Collections.unmodifiableList(new ArrayList<Map<String,String>>(parcelas));}
        public BigDecimal getTotal(){return total;}
        public LocalDate getPrimeiroVencimento(){return primeiroVencimento;}
        public List<Map<String,String>> getParcelas(){return parcelas;}
        public Map<String,Object> paraDto(){Map<String,Object> m=new LinkedHashMap<String,Object>();m.put("valorTotal",total.toPlainString());m.put("quantidadeParcelas",parcelas.size());m.put("primeiroVencimento",primeiroVencimento==null?null:primeiroVencimento.toString());m.put("parcelas",parcelas);return Collections.unmodifiableMap(m);}
    }
    private static ErroRateio erro(String codigo,String mensagem){return new ErroRateio(codigo,mensagem);}
}
