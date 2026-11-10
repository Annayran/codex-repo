package br.com.zaya.mahospitalar.genesis.repository;

import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstoqueSaldoGenesisRepositoryTest {
    @Test void somaDisponivelProprioETerceiroComPrecisao()throws Exception{
        EstoqueSaldoGenesisRepository.Saldo s=consultar(new String[][]{{"P","12.50"},{"T","3.25"}});
        assertEquals(new BigDecimal("15.75"),s.total());
        assertTrue(s.resumo().contains("P=12.50"));assertTrue(s.resumo().contains("T=3.25"));
    }
    @Test void semPosicoesRetornaZeroENaoUm()throws Exception{assertEquals(BigDecimal.ZERO,consultar(new String[0][]).total());}
    @Test void saldoNegativoNaoEConvertidoEmPositivo()throws Exception{assertEquals(new BigDecimal("-3"),consultar(new String[][]{{"P","-3"}}).total());}
    @Test void escopoNaoMultiplicaLinhasPorConfiguracaoDuplicada(){
        String sql=EstoqueSaldoGenesisRepository.SQL;
        assertTrue(sql.contains("NVL(E.ESTOQUE,0)-NVL(E.RESERVADO,0)"));
        assertTrue(sql.contains("E.TIPO IN ('P','T')"));
        assertTrue(sql.contains("C.NUPAR=1 AND C.CODEMP=E.CODEMP"));
        assertTrue(sql.contains("C.NUPAR=1 AND C.CODLOCAL=E.CODLOCAL"));
        assertFalse(sql.contains(" JOIN "));assertFalse(sql.contains("CODPARC=0"));
    }
    private EstoqueSaldoGenesisRepository.Saldo consultar(String[][] rows)throws Exception{
        int[] indice={-1};Map<Integer,Object> bind=new HashMap<Integer,Object>();
        ResultSet rs=(ResultSet)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{ResultSet.class},(p,m,a)->{
            switch(m.getName()){
                case "next":return ++indice[0]<rows.length;
                case "getString":return rows[indice[0]][0];
                case "getBigDecimal":return new BigDecimal(rows[indice[0]][1]);
                case "close":return null;
                default:throw new AssertionError(m.getName());
            }
        });
        PreparedStatement ps=(PreparedStatement)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{PreparedStatement.class},(p,m,a)->{
            if(m.getName().equals("setBigDecimal")){bind.put((Integer)a[0],a[1]);return null;}
            if(m.getName().equals("executeQuery"))return rs;
            if(m.getName().equals("close"))return null;
            throw new AssertionError(m.getName());
        });
        Connection c=(Connection)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Connection.class},(p,m,a)->{
            if(m.getName().equals("prepareStatement")){assertEquals(EstoqueSaldoGenesisRepository.SQL,a[0]);return ps;}
            throw new AssertionError(m.getName());
        });
        EstoqueSaldoGenesisRepository.Saldo saldo=new EstoqueSaldoGenesisRepository().consultar(c,BigDecimal.TEN);
        assertEquals(BigDecimal.TEN,bind.get(1));return saldo;
    }
}
