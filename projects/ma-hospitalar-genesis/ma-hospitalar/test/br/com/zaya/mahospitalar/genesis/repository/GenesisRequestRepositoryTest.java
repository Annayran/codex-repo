package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;

class GenesisRequestRepositoryTest {
    @Test void mascaraDocumentosNumericosESegredosAninhados() {
        String s=GenesisSanitizer.sanitizeForHistory("{\"cnpj\":12345,\"nested\":[{\"email\":\"private@example.com\",\"password\":\"sensitive\"}],\"id\":7}");
        assertFalse(s.contains("12345"));assertFalse(s.contains("private@example.com"));assertFalse(s.contains("sensitive"));
        assertTrue(s.contains("\"id\":7"));assertTrue(s.contains("MASCARADO"));
    }
    @Test void semChamadaRastreadaNaoInventaEvidencia() throws Exception {
        GenesisRequestRepository.persistido(null,new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"7","{}"),"teste");
    }
    @Test void confirmacaoUsaConexaoDoNegocioSemCommit() throws Exception {
        AtomicReference<String> sql=new AtomicReference<String>();
        Connection c=conexao(sql,1);
        GenesisRequestRepository.persistido(c,resultado(),"IDBASE=1; campos alterados=[]");
        assertTrue(sql.get().contains("PERSISTENCIA='CONFIRMADA'"));
    }
    @Test void evidenciaAusenteBloqueiaConfirmacao() {
        assertThrows(IllegalStateException.class,()->GenesisRequestRepository.persistido(conexao(new AtomicReference<String>(),0),resultado(),"teste"));
    }
    private GenesisApiResult resultado(){return new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"7","{}").comRastreio("req-1");}
    private Connection conexao(AtomicReference<String> sql,int quantidade){
        PreparedStatement ps=(PreparedStatement)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{PreparedStatement.class},(p,m,a)->{
            if(m.getName().equals("executeUpdate"))return quantidade;
            if(m.getName().startsWith("set")||m.getName().equals("close"))return null;
            throw new AssertionError("Metodo inesperado: "+m.getName());
        });
        return (Connection)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Connection.class},(p,m,a)->{
            if(m.getName().equals("prepareStatement")){sql.set((String)a[0]);return ps;}
            throw new AssertionError("Transacao nao pode ser finalizada pelo historico: "+m.getName());
        });
    }
}
