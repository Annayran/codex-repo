package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.service.MigracaoModeloGenesisService;
import java.math.BigDecimal;
import java.sql.*;

public final class MigracaoModeloGenesisRepository {
    public static final class Alvo {
        public BigDecimal produto; public long registro,id; public String anterior,destino,lease;
        public CandidatoGenesis controle(){CandidatoGenesis c=new CandidatoGenesis();c.chaveMovimento="API|MIGRA_MODELO|"+produto.toPlainString();c.tipoMovimento="MIGRACAO_CODIGO_MODELO";return c;}
    }
    public Alvo proximo(JdbcWrapper jdbc)throws Exception {
        String sql="SELECT * FROM (SELECT M.IDREGMOD,M.CODPROD,M.IDGENESIS,M.CODIGOEXTERNO,TRIM(P.HSP_IDEXTERNO) DESTINO "+
                "FROM AD_GENMODELO M JOIN TGFPRO P ON P.CODPROD=M.CODPROD LEFT JOIN AD_GENAPICTRL C "+
                "ON C.CHAVE_MOVIMENTO='API|MIGRA_MODELO|'||TO_CHAR(M.CODPROD) "+
                "WHERE M.IDBASE=1 AND M.IDGENESIS>0 AND M.CODIGOEXTERNO='SNK-MOD-'||TO_CHAR(M.CODPROD) "+
                "AND NVL(TRIM(P.HSP_IDEXTERNO),CHR(0))<>M.CODIGOEXTERNO "+
                "AND (C.CHAVE_MOVIMENTO IS NULL OR C.DH_ATUALIZACAO IS NULL OR "+
                "C.DH_ATUALIZACAO<=SYSDATE-CASE WHEN C.STATUS_INTEGRACAO='PROCESSANDO' THEN 10 ELSE "+
                "LEAST(60,POWER(2,LEAST(NVL(C.TENTATIVAS,0),6))) END/1440) "+
                "ORDER BY C.DH_ATUALIZACAO ASC NULLS FIRST,M.IDREGMOD) WHERE ROWNUM=1";
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(sql);ResultSet rs=ps.executeQuery()){
            if(!rs.next())return null;Alvo a=new Alvo();a.produto=rs.getBigDecimal("CODPROD");a.registro=rs.getLong("IDREGMOD");
            a.id=rs.getLong("IDGENESIS");a.anterior=rs.getString("CODIGOEXTERNO");a.destino=rs.getString("DESTINO");return a;
        }
    }
    public void validarFonte(JdbcWrapper jdbc,BigDecimal produto,String destino)throws Exception {
        validarFonte(jdbc.getConnection(),produto,destino);
    }
    void validarFonte(Connection connection,BigDecimal produto,String destino)throws Exception {
        if(produto==null||destino==null||destino.trim().isEmpty()||destino.length()>200)
            throw new IllegalStateException("HSP_IDEXTERNO do modelo vazio/invalido; sem fallback SNK-MOD.");
        try(PreparedStatement ps=connection.prepareStatement(
                "SELECT COUNT(*),SUM(CASE WHEN CODPROD=? AND TRIM(HSP_IDEXTERNO)=? THEN 1 ELSE 0 END) "+
                "FROM TGFPRO WHERE UPPER(TRIM(HSP_IDEXTERNO))=UPPER(?)")){
            ps.setBigDecimal(1,produto);ps.setString(2,destino);ps.setString(3,destino);
            try(ResultSet rs=ps.executeQuery()){rs.next();if(rs.getInt(1)!=1||rs.getInt(2)!=1)
                throw new IllegalStateException("HSP_IDEXTERNO ausente, alterado ou repetido entre produtos; modelo bloqueado.");}
        }
        try(PreparedStatement ps=connection.prepareStatement(
                "SELECT COUNT(*) FROM AD_GENMODELO WHERE IDBASE=1 AND UPPER(TRIM(CODIGOEXTERNO))=UPPER(?) AND (CODPROD IS NULL OR CODPROD<>?)")){
            ps.setString(1,destino);ps.setBigDecimal(2,produto);
            try(ResultSet rs=ps.executeQuery()){rs.next();if(rs.getInt(1)!=0)throw new IllegalStateException("Codigo destino possui outro vinculo local de modelo; revisar sem POST/PUT.");}
        }
    }
    public void validarAlvo(JdbcWrapper jdbc,Alvo a)throws Exception {
        validarFonte(jdbc,a.produto,a.destino);
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM AD_GENMODELO WHERE IDBASE=1 AND CODPROD=?")){
            ps.setBigDecimal(1,a.produto);
            try(ResultSet rs=ps.executeQuery()){rs.next();if(rs.getInt(1)!=1)
                throw new IllegalStateException("Produto associado a varios modelos locais; nenhum ID escolhido automaticamente.");}
        }
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*),SUM(CASE WHEN IDREGMOD=? AND CODPROD=? AND CODIGOEXTERNO IN (?,?) THEN 1 ELSE 0 END) "+
                "FROM AD_GENMODELO WHERE IDBASE=1 AND IDGENESIS=?")){
            ps.setLong(1,a.registro);ps.setBigDecimal(2,a.produto);ps.setString(3,a.anterior);ps.setString(4,a.destino);ps.setLong(5,a.id);
            try(ResultSet rs=ps.executeQuery()){rs.next();if(rs.getInt(1)!=1||rs.getInt(2)!=1)
                throw new IllegalStateException("ID de modelo compartilhado, alterado ou ambiguo; migracao bloqueada.");}
        }
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM AD_GENAPICTRL WHERE CHAVE_MOVIMENTO=? AND PROTOCOLO_GENESIS IS NOT NULL AND PROTOCOLO_GENESIS<>?")){
            ps.setString(1,"API|MODELO|"+a.produto.toPlainString());ps.setString(2,String.valueOf(a.id));
            try(ResultSet rs=ps.executeQuery()){rs.next();if(rs.getInt(1)>0)throw new IllegalStateException("Controle MODELO aponta para outro ID; reconciliar antes da migracao.");}
        }
    }
    public boolean reservar(JdbcWrapper jdbc,Alvo a)throws Exception {
        new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM AD_GENAPICTRL WHERE CHAVE_MOVIMENTO=? AND STATUS_INTEGRACAO='PROCESSANDO' AND DH_ATUALIZACAO>SYSDATE-10/1440")){
            ps.setString(1,a.controle().chaveMovimento);try(ResultSet rs=ps.executeQuery()){rs.next();if(rs.getInt(1)>0)return false;}
        }
        new MovimentoGenesisRepository(true).registrarPendente(jdbc,a.controle());
        a.lease=java.util.UUID.randomUUID().toString();
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENAPICTRL SET STATUS_INTEGRACAO='PROCESSANDO',PAYLOAD_HASH=?,DH_ATUALIZACAO=SYSDATE WHERE CHAVE_MOVIMENTO=?")){
            ps.setString(1,a.lease);ps.setString(2,a.controle().chaveMovimento);if(ps.executeUpdate()!=1)throw new IllegalStateException("Controle de migracao duplicado.");
        }
        return true;
    }
    public void validarLease(JdbcWrapper jdbc,Alvo a)throws Exception {
        new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM AD_GENAPICTRL WHERE CHAVE_MOVIMENTO=? AND PAYLOAD_HASH=? AND STATUS_INTEGRACAO='PROCESSANDO' AND DH_ATUALIZACAO>SYSDATE-10/1440")){
            ps.setString(1,a.controle().chaveMovimento);ps.setString(2,a.lease);
            try(ResultSet rs=ps.executeQuery()){rs.next();if(rs.getInt(1)!=1)throw new IllegalStateException("Reserva da migracao expirada/alterada; nenhum PUT autorizado.");}
        }
        validarAlvo(jdbc,a);
    }
    public void confirmar(JdbcWrapper jdbc,Alvo a,MigracaoModeloGenesisService.Resultado r)throws Exception {
        validarLease(jdbc,a);
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENMODELO SET CODIGOEXTERNO=?,DHCONCILIACAO=SYSDATE WHERE IDBASE=1 AND IDREGMOD=? AND CODPROD=? AND IDGENESIS=? AND CODIGOEXTERNO IN (?,?)")){
            ps.setString(1,a.destino);ps.setLong(2,a.registro);ps.setBigDecimal(3,a.produto);ps.setLong(4,a.id);ps.setString(5,a.anterior);ps.setString(6,a.destino);
            if(ps.executeUpdate()!=1)throw new IllegalStateException("Modelo mudou durante migracao; preservar para reconciliacao.");
        }
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENAPICTRL SET STATUS_INTEGRACAO='REPROCESSAR',PAYLOAD_HASH=NULL,"+
                "MENSAGEM_ERRO=TO_CLOB('Codigo do modelo migrado; aguardando nova validacao por ID.'),DH_ATUALIZACAO=SYSDATE "+
                "WHERE CHAVE_MOVIMENTO=? AND (PROTOCOLO_GENESIS IS NULL OR PROTOCOLO_GENESIS=?)")){
            ps.setString(1,"API|MODELO|"+a.produto.toPlainString());ps.setString(2,String.valueOf(a.id));ps.executeUpdate();
        }
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENAPICTRL SET STATUS_INTEGRACAO='API_SINCRONIZADO',PROTOCOLO_GENESIS=?,HTTP_STATUS=?,"+
                "MENSAGEM_ERRO=TO_CLOB(?),DH_ATUALIZACAO=SYSDATE WHERE CHAVE_MOVIMENTO=? AND PAYLOAD_HASH=?")){
            ps.setString(1,String.valueOf(a.id));ps.setInt(2,r.confirmacao.getHttpStatus());
            ps.setString(3,"Codigo do modelo migrado por ID: "+a.anterior+" -> "+a.destino+"; sem criacao.");
            ps.setString(4,a.controle().chaveMovimento);ps.setString(5,a.lease);if(ps.executeUpdate()!=1)throw new IllegalStateException("Reserva de migracao alterada.");
        }
        String detalhe="IDBASE=1; modelo="+a.id+"; CODPROD="+a.produto+"; codigoExterno: "+a.anterior+" -> "+a.destino+"; persistencia local confirmada.";
        GenesisRequestRepository.persistido(jdbc.getConnection(),r.confirmacao,detalhe);
        if(r.put!=null)GenesisRequestRepository.persistido(jdbc.getConnection(),r.put,detalhe);
    }
    public void falhar(JdbcWrapper jdbc,Alvo a,Integer http,String motivo)throws Exception {
        new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENAPICTRL SET STATUS_INTEGRACAO='REPROCESSAR',HTTP_STATUS=?,MENSAGEM_ERRO=TO_CLOB(?),"+
                "TENTATIVAS=NVL(TENTATIVAS,0)+1,DH_ATUALIZACAO=SYSDATE WHERE CHAVE_MOVIMENTO=? AND PAYLOAD_HASH=? AND STATUS_INTEGRACAO='PROCESSANDO'")){
            if(http==null)ps.setNull(1,Types.NUMERIC);else ps.setInt(1,http);
            ps.setString(2,br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer.sanitize(motivo));ps.setString(3,a.controle().chaveMovimento);ps.setString(4,a.lease);ps.executeUpdate();
        }
    }
}
