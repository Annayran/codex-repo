package br.com.zaya.mahospitalar.genesis.repository;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class MigracaoModeloFonteTest {
    @Test void fonteUnicaPreservaZeros()throws Exception{new MigracaoModeloGenesisRepository().validarFonte(connection(1,1,0),BigDecimal.TEN,"000MC");}
    @Test void fonteDuplicadaBloqueia(){assertThrows(Exception.class,()->new MigracaoModeloGenesisRepository().validarFonte(connection(2,1,0),BigDecimal.TEN,"000MC"));}
    @Test void fonteAlteradaBloqueia(){assertThrows(Exception.class,()->new MigracaoModeloGenesisRepository().validarFonte(connection(1,0,0),BigDecimal.TEN,"000MC"));}
    @Test void destinoComOutroVinculoLocalBloqueia(){assertThrows(Exception.class,()->new MigracaoModeloGenesisRepository().validarFonte(connection(1,1,1),BigDecimal.TEN,"000MC"));}
    @Test void fonteVaziaNaoConsultaBanco(){assertThrows(Exception.class,()->new MigracaoModeloGenesisRepository().validarFonte((Connection)null,BigDecimal.TEN," "));}
    Connection connection(int count,int matching,int other){return (Connection)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Connection.class},(c,m,args)->{
        if("prepareStatement".equals(m.getName())){
            String sql=(String)args[0];assertTrue(sql.startsWith("SELECT"));boolean produto=sql.contains("FROM TGFPRO");
            return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{PreparedStatement.class},(p,method,a)->{
                if("setString".equals(method.getName()))assertEquals("000MC",a[1]);
                if("executeQuery".equals(method.getName()))return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{ResultSet.class},(r,rm,ra)->{
                    if("next".equals(rm.getName()))return true;
                    if("getInt".equals(rm.getName()))return produto?((Integer)ra[0]==1?count:matching):other;
                    return null;
                });
                return null;
            });
        }
        if("commit".equals(m.getName()))fail("Consulta nao pode confirmar transacao");return null;
    });}
}
