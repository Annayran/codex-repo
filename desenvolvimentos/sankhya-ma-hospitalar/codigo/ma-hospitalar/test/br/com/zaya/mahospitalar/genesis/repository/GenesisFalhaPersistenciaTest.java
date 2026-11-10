package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.Reader;
import static org.junit.jupiter.api.Assertions.*;

class GenesisFalhaPersistenciaTest {
    @Test void diagnosticoNaoSobrescreveConfirmacaoNemIdentidade() throws Exception {
        final String[] sql={null}, detalhe={null}, id={null};
        Connection c=(Connection)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Connection.class},(p,m,a)->{
            if("prepareStatement".equals(m.getName())) {
                sql[0]=(String)a[0];
                return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{PreparedStatement.class},(ps,method,args)->{
                    if("setCharacterStream".equals(method.getName())) {
                        Reader reader=(Reader)args[1]; StringBuilder value=new StringBuilder(); int ch;
                        while((ch=reader.read())!=-1)value.append((char)ch); detalhe[0]=value.toString();
                    }
                    if("setString".equals(method.getName()))id[0]=(String)args[1];
                    if("executeUpdate".equals(method.getName()))return 0;
                    return null;
                });
            }
            if("commit".equals(m.getName()))fail("Nao deve controlar commit do chamador");
            return null;
        });
        GenesisApiResult r=new GenesisApiResult(200,GenesisApiResult.Classificacao.SUCESSO,"288","{}").comRastreio("req-teste");
        GenesisRequestRepository.falhaPersistencia(c,r,new IllegalStateException("Vinculo local em conflito"));
        assertTrue(sql[0].contains("PERSISTENCIA<>'CONFIRMADA'"));
        assertFalse(sql[0].contains("SET IDENTIDADE"));
        assertEquals("req-teste",id[0]);
        assertTrue(detalhe[0].startsWith("FALHA_PERSISTENCIA_LOCAL:"));
    }
}
