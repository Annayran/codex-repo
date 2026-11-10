package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import java.math.BigDecimal;
import java.sql.*;

/** Borrows the current managed transaction; never commits or closes its connection. */
public final class BloqueioRateioJdbc {
    private BloqueioRateioJdbc() { }
    public static String identificarBanco(Connection conexao){
        String produto;
        try{produto=conexao.getMetaData().getDatabaseProductName();}
        catch(SQLException e){throw new FalhaConsultaRateio("IDENTIFICAR_BANCO",e);}
        if(produto!=null&&produto.toLowerCase(java.util.Locale.ROOT).contains("sql server"))return "SQLSERVER";
        if(produto!=null&&produto.toLowerCase(java.util.Locale.ROOT).contains("oracle"))return "ORACLE";
        return "NAO_SUPORTADO";
    }
    public static void adquirir(Connection conexao,OrigemCanonica origem){
        String tipo=identificarBanco(conexao);
        if("SQLSERVER".equals(tipo)){
            sqlServer(conexao,origem);return;
        }
        if(!"ORACLE".equals(tipo))
            throw new FalhaConsultaRateio("DIALETO_NAO_SUPORTADO");
        oracle(conexao,origem);
    }
    private static void oracle(Connection conexao,OrigemCanonica origem){
        String tabela=origem.getTipo()==OrigemCanonica.Tipo.NOTA?"TGFCAB":"TGFFIN";
        String chave=origem.getTipo()==OrigemCanonica.Tipo.NOTA?"NUNOTA":"NUFIN";
        // Keep the locking cursor inside Oracle. No JDBC ResultSet is fetched or cached.
        // Table/key come exclusively from the canonical origin enum; the ID stays bound.
        String bloco="DECLARE v_origem NUMBER; BEGIN SELECT "+chave+" INTO v_origem FROM "+tabela+" WHERE "+chave+" = ? FOR UPDATE NOWAIT; END;";
        try(CallableStatement lock=conexao.prepareCall(bloco)){
            lock.setBigDecimal(1,BigDecimal.valueOf(origem.getId()));
            lock.execute();
        }catch(SQLException e){throw falha("BLOQUEAR_ORIGEM",e,"ORACLE");}
        // Static transaction-control SQL has no bind parameters or result set.
        try(Statement lock=conexao.createStatement()){
            lock.executeUpdate("LOCK TABLE AD_RATSIM IN EXCLUSIVE MODE NOWAIT");
        }catch(SQLException e){throw falha("BLOQUEAR_SIMULACOES",e,"ORACLE");}
    }
    private static void sqlServer(Connection conexao,OrigemCanonica origem){
        int anterior;
        try(Statement consulta=conexao.createStatement();ResultSet r=consulta.executeQuery("SELECT @@LOCK_TIMEOUT")){
            if(!r.next())throw new SQLException("Configuracao de timeout indisponivel.");
            anterior=r.getInt(1);if(r.wasNull()||anterior< -1)throw new SQLException("Timeout invalido.");
        }catch(SQLException e){throw falha("LER_LOCK_TIMEOUT",e,"SQLSERVER");}
        RuntimeException principal=null;
        try{
            try(Statement configuracao=conexao.createStatement()){configuracao.execute("SET LOCK_TIMEOUT 0");}
            catch(SQLException e){throw falha("CONFIGURAR_LOCK_TIMEOUT",e,"SQLSERVER");}
            String tabela=origem.getTipo()==OrigemCanonica.Tipo.NOTA?"TGFCAB":"TGFFIN";
            String chave=origem.getTipo()==OrigemCanonica.Tipo.NOTA?"NUNOTA":"NUFIN";
            try(PreparedStatement lock=conexao.prepareStatement("SELECT "+chave+" FROM "+tabela+" WITH (UPDLOCK, HOLDLOCK) WHERE "+chave+" = ?")){
                lock.setBigDecimal(1,BigDecimal.valueOf(origem.getId()));
                try(ResultSet r=lock.executeQuery()){
                    if(!r.next())throw new ErroRateio("BASE_INCOMPLETA","Origem indisponivel.");
                }
            }catch(SQLException e){throw falha("BLOQUEAR_ORIGEM",e,"SQLSERVER");}
            // TABLOCKX also locks an empty table; SET LOCK_TIMEOUT supplies NOWAIT semantics.
            try(Statement lock=conexao.createStatement();ResultSet r=lock.executeQuery("SELECT TOP (1) NUSIMULACAO FROM AD_RATSIM WITH (TABLOCKX, HOLDLOCK)")){
                r.next();
            }catch(SQLException e){throw falha("BLOQUEAR_SIMULACOES",e,"SQLSERVER");}
        }catch(RuntimeException e){principal=e;throw e;}
        finally{
            // LOCK_TIMEOUT belongs to the pooled session and is not reset by rollback.
            try(Statement restaurar=conexao.createStatement()){restaurar.execute("SET LOCK_TIMEOUT "+anterior);}
            catch(SQLException e){RuntimeException falha=new FalhaConsultaRateio("RESTAURAR_LOCK_TIMEOUT",e,"SQLSERVER");if(principal!=null)falha.addSuppressed(principal);throw falha;}
        }
    }
    private static RuntimeException falha(String etapa,SQLException e,String bancoTipo){
        FalhaConsultaRateio falha=new FalhaConsultaRateio(etapa,e,bancoTipo);
        if("ORACLE".equals(bancoTipo)&&"BLOQUEAR_ORIGEM".equals(etapa)&&Integer.valueOf(1403).equals(falha.getCodigoBanco()))return new ErroRateio("BASE_INCOMPLETA","Origem indisponivel.");
        if(("ORACLE".equals(bancoTipo)&&Integer.valueOf(54).equals(falha.getCodigoBanco()))||("SQLSERVER".equals(bancoTipo)&&Integer.valueOf(1222).equals(falha.getCodigoBanco())))return new ErroRateio("CONCORRENCIA","Outra operacao de rateio esta em andamento. Atualize a consulta.");
        return falha;
    }
}
