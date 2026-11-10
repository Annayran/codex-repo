package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDto;
import br.com.zaya.mahospitalar.genesis.service.EstoqueGenesisSyncService;
import br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class EstoqueGenesisRepository {
    public boolean instalado(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps=jdbc.getConnection().prepareStatement("SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME='AD_GENESTCTRL'"); ResultSet rs=ps.executeQuery()) {
            return rs.next() && rs.getInt(1)==1;
        }
    }
    public List<Candidato> listar(JdbcWrapper jdbc, int limite, int maxTentativas) throws Exception {
        String sql="SELECT * FROM (SELECT P.CODPROD,P.DESCRPROD,P.ATIVO,"+
                "COALESCE(TRIM(P.HSP_TIPOPRODUTO),TRIM(P.AD_TIPOPRODUTO)) TIPO,"+
                "TRIM(P.HSP_IDEXTERNO) CODIGOEXTERNO_FONTE,C.STATUS STATUS_CONTROLE,"+
                "C.IDGENESIS,C.CODIGOEXTERNO,C.HASHCONFIRMADO,C.STATUSREMOTO,"+
                "P.CODIPI,I.PERCENTUAL FROM TGFPRO P LEFT JOIN TGFIPI I ON I.CODIPI=P.CODIPI "+
                "LEFT JOIN AD_GENESTCTRL C ON C.CODPROD=P.CODPROD WHERE "+
                "((P.ATIVO='S' AND UPPER(COALESCE(TRIM(P.HSP_TIPOPRODUTO),TRIM(P.AD_TIPOPRODUTO))) IN ('M2','M3','SV')) OR C.CODPROD IS NOT NULL) "+
                "AND (C.PROXIMATENTATIVA IS NULL OR C.PROXIMATENTATIVA<=SYSDATE) "+
                "ORDER BY C.DH_ATUALIZACAO ASC NULLS FIRST,P.CODPROD) WHERE ROWNUM<=?";
        List<Candidato> lista=new ArrayList<Candidato>();
        try (PreparedStatement ps=jdbc.getConnection().prepareStatement(sql)) {
            ps.setInt(1,limite);
            try(ResultSet rs=ps.executeQuery()) { while(rs.next()) {
                Candidato c=new Candidato(); c.produto=rs.getBigDecimal("CODPROD"); c.tipo=rs.getString("TIPO"); c.ativo="S".equals(rs.getString("ATIVO"));
                c.id=rs.getObject("IDGENESIS")==null ? null : rs.getLong("IDGENESIS"); c.hash=rs.getString("HASHCONFIRMADO"); c.statusRemoto=rs.getString("STATUSREMOTO");
                c.codigoControle=rs.getString("CODIGOEXTERNO"); c.status=rs.getString("STATUS_CONTROLE");
                c.dto=new EstoqueGenesisDto(); c.dto.codigoExterno=rs.getString("CODIGOEXTERNO_FONTE");
                c.dto.codigo=c.dto.codigoExterno;
                c.dto.descServico=rs.getString("DESCRPROD"); c.dto.descTecnica=c.dto.descServico; c.dto.status="Ativo";
                c.dto.ipi=rs.getBigDecimal("PERCENTUAL"); if(rs.getObject("CODIPI")==null || rs.getInt("CODIPI")==0) c.dto.ipi=BigDecimal.ZERO;
                lista.add(c);
            }}
        }
        return lista;
    }
    public void preparar(JdbcWrapper jdbc,Candidato c) throws Exception {
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement("INSERT INTO AD_GENESTCTRL (CODPROD,CODIGOEXTERNO,STATUS,TENTATIVAS,DH_ATUALIZACAO) SELECT ?,?,'PENDENTE',0,SYSDATE FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM AD_GENESTCTRL WHERE CODPROD=?)")) {
            ps.setBigDecimal(1,c.produto); ps.setString(2,c.dto.codigoExterno); ps.setBigDecimal(3,c.produto); ps.executeUpdate();
        }
    }
    public void validarIdentidadeFonte(JdbcWrapper jdbc,Candidato c) throws Exception {
        validarCodigoControle(c);
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM TGFPRO WHERE UPPER(TRIM(HSP_IDEXTERNO))=UPPER(?)")) {
            ps.setString(1,c.dto.codigoExterno);
            try(ResultSet rs=ps.executeQuery()) {
                rs.next();if(rs.getInt(1)!=1)throw new IllegalStateException("HSP_IDEXTERNO repetido ou ausente no cadastro de produtos; envio bloqueado.");
            }
        }
    }
    static void validarCodigoControle(Candidato c) {
        String codigo=c.dto.codigoExterno;
        if(codigo==null || codigo.trim().isEmpty())
            throw new IllegalStateException("HSP_IDEXTERNO vazio; nao utilizar CODPROD como identidade Genesis.");
    }
    public static boolean precisaReconciliar(Candidato c) {
        if(c.codigoControle==null || c.codigoControle.trim().isEmpty())return c.id!=null;
        return !c.codigoControle.trim().equals(c.dto.codigoExterno);
    }
    public static void validarReconciliacao(Candidato c,br.com.zaya.mahospitalar.genesis.client.GenesisApiResult r) throws Exception {
        if(!r.isSucesso())throw new IllegalStateException("Reconciliacao nao confirmada: HTTP="+r.getHttpStatus()+"; nenhum POST autorizado.");
        if(r.getIdGenesis()==null || !r.getIdGenesis().matches("[1-9][0-9]*") ||
                !r.campoCorresponde("codigoExterno",c.dto.codigoExterno,false))
            throw new IllegalStateException("Consulta por HSP_IDEXTERNO nao confirmou identidade unica; nenhum POST autorizado.");
        long id=Long.parseLong(r.getIdGenesis());
        if(c.id!=null && c.id.longValue()!=id)
            throw new IllegalStateException("ID legado difere do cadastro encontrado por HSP_IDEXTERNO; possivel duplicidade, revisar sem novo POST.");
    }
    public void conciliarFonte(JdbcWrapper jdbc,Candidato c,br.com.zaya.mahospitalar.genesis.client.GenesisApiResult r) throws Exception {
        validarReconciliacao(c,r);
        new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENESTCTRL SET CODIGOEXTERNO=?,IDGENESIS=?,HASHCONFIRMADO=NULL,STATUS='REPROCESSAR',"+
                "HTTP_STATUS=?,MENSAGEM=NULL,PROXIMATENTATIVA=NULL,DH_ATUALIZACAO=SYSDATE " +
                "WHERE CODPROD=? AND ((? IS NULL AND IDGENESIS IS NULL) OR IDGENESIS=?) " +
                "AND NVL(CODIGOEXTERNO,CHR(0))=NVL(?,CHR(0))")) {
            ps.setString(1,c.dto.codigoExterno);ps.setLong(2,Long.parseLong(r.getIdGenesis()));ps.setInt(3,r.getHttpStatus());
            ps.setBigDecimal(4,c.produto);
            if(c.id==null){ps.setNull(5,java.sql.Types.NUMERIC);ps.setNull(6,java.sql.Types.NUMERIC);}
            else{ps.setLong(5,c.id);ps.setLong(6,c.id);}
            ps.setString(7,c.codigoControle);
            if(ps.executeUpdate()!=1)throw new IllegalStateException("Controle mudou durante conciliacao; nenhuma mutacao remota autorizada.");
        }
        GenesisRequestRepository.persistido(jdbc.getConnection(),r,"Identidade do catalogo reconciliada com HSP_IDEXTERNO; CODPROD="+c.produto);
    }

    public void reservarCriacao(JdbcWrapper jdbc,Candidato c) throws Exception {
        new CancelamentoGenesisRepository().bloquearIntegracao(jdbc);
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "SELECT IDGENESIS,STATUS FROM AD_GENESTCTRL WHERE CODPROD=? FOR UPDATE")) {
            ps.setBigDecimal(1,c.produto);
            try(ResultSet rs=ps.executeQuery()) {
                if(!rs.next())throw new IllegalStateException("Controle de estoque ausente antes do POST.");
                if(rs.getObject(1)!=null || "ENVIO_INCERTO".equals(rs.getString(2)))
                    throw new IllegalStateException("POST bloqueado: ID existente ou criacao anterior incerta; reconciliar por GET.");
                if(rs.next())throw new IllegalStateException("Controle de estoque duplicado.");
            }
        }
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENESTCTRL SET STATUS='ENVIO_INCERTO',DH_ATUALIZACAO=SYSDATE WHERE CODPROD=?")) {
            ps.setBigDecimal(1,c.produto);if(ps.executeUpdate()!=1)throw new IllegalStateException("Reserva de estoque ambiguo.");
        }
    }
    public void liberarRejeicaoCriacao(JdbcWrapper jdbc,Candidato c) throws Exception {
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENESTCTRL SET STATUS='REPROCESSAR' WHERE CODPROD=? AND IDGENESIS IS NULL AND STATUS='ENVIO_INCERTO'")) {
            ps.setBigDecimal(1,c.produto);ps.executeUpdate();
        }
    }
    public void fabricante(JdbcWrapper jdbc,Candidato c) throws Exception {
        String sql="SELECT E.IDGENESIS FROM AD_GENEMPRESA E JOIN TGFPRO P ON UPPER(TRIM(P.MARCA))=UPPER(TRIM(E.MARCASANKHYA)) WHERE P.CODPROD=? AND E.IDBASE=1 AND E.STATUSCONC='CONCILIADO' AND E.IDGENESIS>0";
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(sql)) {
            ps.setBigDecimal(1,c.produto);
            try(ResultSet rs=ps.executeQuery()) {
                if(!rs.next()) throw new IllegalStateException("Fabricante nao conciliado para catalogo.");
                long id=rs.getLong(1); if(rs.next()) throw new IllegalStateException("Marca possui multiplos fabricantes conciliados.");
                c.dto.idFabricante=id; c.dto.idFornecedor=id; c.dto.idCategoria=1L;
            }
        }
    }
    public void confirmar(JdbcWrapper jdbc,Candidato c,EstoqueGenesisSyncService.Resultado r,String hash) throws Exception {
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement("UPDATE AD_GENESTCTRL SET IDGENESIS=COALESCE(?,IDGENESIS),HASHCONFIRMADO=COALESCE(?,HASHCONFIRMADO),STATUSREMOTO=?,STATUS='ENVIADO',OPERACAO=?,HTTP_STATUS=?,TENTATIVAS=0,MENSAGEM=NULL,PROXIMATENTATIVA=NULL,DH_CONFIRMACAO=SYSDATE,DH_ATUALIZACAO=SYSDATE WHERE CODPROD=?")) {
            if(r.idGenesis==null) ps.setNull(1,java.sql.Types.NUMERIC); else ps.setLong(1,r.idGenesis);
            ps.setString(2,hash); ps.setString(3,"INATIVADO".equals(r.operacao)?"Inativo":"Ativo"); ps.setString(4,r.operacao);
            if(r.api==null) ps.setNull(5,java.sql.Types.NUMERIC); else ps.setInt(5,r.api.getHttpStatus());
            ps.setBigDecimal(6,c.produto); ps.executeUpdate();
        }
        GenesisRequestRepository.persistido(jdbc.getConnection(),r.api,"AD_GENESTCTRL; CODPROD="+c.produto+"; operacao="+r.operacao+
                "; HSP_IDEXTERNO="+c.dto.codigoExterno+"; "+(c.saldo==null?"":c.saldo.resumo()));
    }
    public void falha(JdbcWrapper jdbc,Candidato c,String status,Integer http,String erro,int max) throws Exception {
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement("UPDATE AD_GENESTCTRL SET STATUS=CASE WHEN STATUS='ENVIO_INCERTO' THEN STATUS ELSE ? END,HTTP_STATUS=?,MENSAGEM=?,TENTATIVAS=NVL(TENTATIVAS,0)+1,PROXIMATENTATIVA=SYSDATE+LEAST(60,POWER(2,LEAST(NVL(TENTATIVAS,0)+1,6)))/1440,DH_ATUALIZACAO=SYSDATE WHERE CODPROD=?")) {
            ps.setString(1,status); if(http==null)ps.setNull(2,java.sql.Types.NUMERIC);else ps.setInt(2,http);
            String s=GenesisSanitizer.sanitize(erro); ps.setString(3,resumoMensagem(s));ps.setBigDecimal(4,c.produto);ps.executeUpdate();
        }
    }
    static String resumoMensagem(String s) {
        if(s==null)return null;
        // Constructor deployments may have a 100 BYTE column. Full text belongs in request history.
        String ascii=java.text.Normalizer.normalize(s,java.text.Normalizer.Form.NFD).replaceAll("[^\\x00-\\x7F]","?");
        return ascii.substring(0,Math.min(100,ascii.length()));
    }
    public void observar(JdbcWrapper jdbc,Candidato c) throws Exception {
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement("UPDATE AD_GENESTCTRL SET DH_ATUALIZACAO=SYSDATE WHERE CODPROD=?")) {
            ps.setBigDecimal(1,c.produto); ps.executeUpdate();
        }
    }
    public static final class Candidato {
        public BigDecimal produto; public boolean ativo; public String tipo,hash,statusRemoto,codigoControle,status; public Long id; public EstoqueGenesisDto dto;
        public EstoqueSaldoGenesisRepository.Saldo saldo;
        public boolean elegivel(){ return ativo && ("M2".equalsIgnoreCase(tipo)||"M3".equalsIgnoreCase(tipo)||"SV".equalsIgnoreCase(tipo)); }
    }
}
