package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class MovimentoGenesisRepositoryTest {
    @Test void idCriadoFicaNoMovimentoSemMarcarEnvioNemAlterarHttp() throws Exception {
        String[] sql={null};Map<Integer,Object> bind=new HashMap<>();
        PreparedStatement ps=(PreparedStatement)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{PreparedStatement.class},(p,m,a)->{
            if(m.getName().equals("setString")){bind.put((Integer)a[0],a[1]);return null;}
            if(m.getName().equals("executeUpdate"))return 1;
            return null;
        });
        Connection c=(Connection)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Connection.class},(p,m,a)->{
            if(m.getName().equals("prepareStatement")){sql[0]=(String)a[0];return ps;}return null;
        });
        CandidatoGenesis origem=new CandidatoGenesis();origem.chaveMovimento="2|10|1|30|SERIE";
        new MovimentoGenesisRepository().registrarIdCriado(c,origem,"66618");
        assertEquals("66618",bind.get(1));assertEquals(origem.chaveMovimento,bind.get(2));
        assertEquals("66618",bind.get(3));
        assertTrue(sql[0].contains("ORIGEM_ITEM=COALESCE(ORIGEM_ITEM,'CRIADO')"));
        assertTrue(sql[0].contains("PROTOCOLO_GENESIS IS NULL OR PROTOCOLO_GENESIS=?"));
        assertFalse(sql[0].contains("STATUS_INTEGRACAO="));assertFalse(sql[0].contains("HTTP_STATUS="));
        assertThrows(IllegalStateException.class,()->new MovimentoGenesisRepository().registrarIdCriado(c,origem,"0"));
    }
    @Test void confirmacaoDoPostPreservaIdentidadeETrataCancelamentoTardioNaMesmaTransacao() throws Exception {
        String source=new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(
                "src/br/com/zaya/mahospitalar/genesis/repository/GenesisOperationRepository.java")),java.nio.charset.StandardCharsets.UTF_8);
        int confirmacao=source.indexOf("@Override public void confirmar");
        String trecho=source.substring(confirmacao,source.indexOf("@Override public void falhar",confirmacao));
        assertTrue(trecho.indexOf("registrarIdCriado")>trecho.indexOf("API_CONFIRMADO"));
        assertTrue(trecho.contains("registrarIdCriado(jdbc.getConnection(),origem,result.getIdGenesis())"));
        assertTrue(trecho.contains("cancelamento.notaCancelada(jdbc,origem.nuNota)"));
        assertTrue(trecho.contains("cancelamento.enfileirarRetornoTardio(jdbc,origem.nuNota,origem.chaveMovimento)"));
    }
    @Test void falhaPreservaIdSemEncerrarPorLimiteLegado() throws Exception {
        Map<Integer,Object> valores = new HashMap<Integer,Object>();
        String[] sql = new String[1];
        PreparedStatement ps = (PreparedStatement) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{PreparedStatement.class},(proxy,method,args) -> {
                    if (method.getName().startsWith("set")) {
                        valores.put((Integer)args[0],"setNull".equals(method.getName()) ? null : args[1]);
                        return null;
                    }
                    if (method.getName().equals("executeUpdate")) return 1;
                    return null;
                });
        Connection conn = (Connection) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{Connection.class},(proxy,method,args) -> {
                    if (method.getName().equals("prepareStatement")) {sql[0]=(String)args[0]; return ps;}
                    return null;
                });
        CandidatoGenesis candidato = new CandidatoGenesis(); candidato.chaveMovimento="1|10|1|20|ABC";
        new MovimentoGenesisRepository().atualizarResultado(conn,candidato,"REPROCESSAR",503,
                "Falha temporaria",null,null,true,5);
        assertTrue(sql[0].contains("PROTOCOLO_GENESIS=COALESCE(?,PROTOCOLO_GENESIS)"));
        assertFalse(sql[0].contains("THEN 'ERRO'"));
        assertTrue(sql[0].contains("STATUS_INTEGRACAO NOT IN ('ENVIADO','CANCELADO')"));
        assertEquals("REPROCESSAR",valores.get(1));
        assertEquals(503,valores.get(2)); assertNull(valores.get(4));
        assertEquals(1,valores.get(6)); assertEquals(candidato.chaveMovimento,valores.get(8));
    }
}
