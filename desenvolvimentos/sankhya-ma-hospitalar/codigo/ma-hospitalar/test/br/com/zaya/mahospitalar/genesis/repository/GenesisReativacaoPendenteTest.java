package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.domain.*;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenesisReativacaoPendenteTest {
    @Test void reservaDuravelBloqueiaNovaInstanciaMesmoComIdRemotoAtivo() throws Exception {
        PayloadGenesis p=new PayloadGenesis(TipoEntidade.ITEM_MATERIAL,"30|SERIE","x",Collections.emptyMap(),"{}","hash");
        for(int quantidade:new int[]{1,2}) {
            IllegalStateException erro=assertThrows(IllegalStateException.class,
                    ()->GenesisOperationRepository.validarPendenciaReativacao(connection(quantidade),p));
            assertTrue(erro.getMessage().contains("REATIVACAO_PENDENTE"));
            assertThrows(IllegalStateException.class,
                    ()->GenesisOperationRepository.validarPendenciaReativacao(connection(quantidade),p));
        }
    }
    @Test void semReservaPendentePermiteConsulta() throws Exception {
        PayloadGenesis p=new PayloadGenesis(TipoEntidade.ITEM_MATERIAL,"30|SERIE","x",Collections.emptyMap(),"{}","hash");
        GenesisOperationRepository.validarPendenciaReativacao(connection(0),p);
    }
    @Test void reservaEmTransacaoSeparadaEConferenciaAntesDeLiberar() throws Exception {
        String s=new String(Files.readAllBytes(Paths.get("src/br/com/zaya/mahospitalar/genesis/repository/GenesisOperationRepository.java")),StandardCharsets.UTF_8);
        int reserva=s.indexOf("marcarEtapaReativacao(jdbc,p,\"REATIVACAO_PENDENTE\")");
        int transacaoHttp=s.indexOf("return GenesisTransaction.execute",reserva);
        int executar=s.indexOf("acao.executar()",transacaoHttp);
        assertTrue(reserva>=0 && transacaoHttp>reserva && executar>transacaoHttp);
        assertTrue(s.indexOf("marcarEtapaReativacao(jdbc,p,\"REATIVACAO_VALIDADA\")",executar)>executar);
        int preparar=s.indexOf("void prepararConsulta");
        assertTrue(s.indexOf("validarPendenciaReativacao(jdbc.getConnection(),p)",preparar)>preparar);
        int confirmar=s.indexOf("void confirmar(");
        int lock=s.indexOf("bloquearIntegracao(jdbc)",confirmar);
        int barreira=s.indexOf("validarPendenciaReativacao(jdbc.getConnection(),p)",lock);
        assertTrue(lock>confirmar && barreira>lock && barreira<s.indexOf("movimentos.registrarPendente",confirmar));
        assertTrue(s.contains("OR DBMS_LOB.INSTR(MENSAGEM_ERRO,'REATIVACAO_PENDENTE:',1,1)=1 THEN MENSAGEM_ERRO"));
    }
    private Connection connection(int count) {
        ResultSet rs=(ResultSet)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{ResultSet.class},(p,m,a)->{
            if(m.getName().equals("next"))return true;
            if(m.getName().equals("getInt"))return count;
            return null;
        });
        PreparedStatement ps=(PreparedStatement)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{PreparedStatement.class},(p,m,a)->{
            if(m.getName().equals("executeQuery"))return rs;
            if(m.getName().equals("setString"))assertEquals("API|ITEM_MATERIAL|30|SERIE",a[1]);
            return null;
        });
        return (Connection)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Connection.class},(p,m,a)->{
            if(m.getName().equals("prepareStatement")) {
                assertTrue(a[0].toString().contains("DBMS_LOB.INSTR"));return ps;
            }
            return null;
        });
    }
}
