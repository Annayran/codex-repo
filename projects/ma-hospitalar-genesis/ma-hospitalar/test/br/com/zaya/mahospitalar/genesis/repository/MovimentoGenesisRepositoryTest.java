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
