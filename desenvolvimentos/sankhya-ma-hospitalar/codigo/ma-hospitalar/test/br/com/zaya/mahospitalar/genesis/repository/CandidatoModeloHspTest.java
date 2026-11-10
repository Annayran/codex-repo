package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.lang.reflect.*;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CandidatoModeloHspTest {
    @Test void mapUsaHspPreservandoZerosESemPrefixoGerado()throws Exception {
        CandidatoGenesis c=map(" 000MC-EP90 ");
        assertEquals("000MC-EP90",c.codigoExternoModelo);assertEquals("14862",c.chaveModelo);
    }
    @Test void mapSemHspNaoGeraSnkMod()throws Exception{assertNull(map(null).codigoExternoModelo);}
    private CandidatoGenesis map(String hsp)throws Exception {
        ResultSet rs=(ResultSet)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{ResultSet.class},(p,m,a)->{
            if("getBigDecimal".equals(m.getName()))return "CODPROD".equals(a[0])?new BigDecimal("14862"):null;
            if("getString".equals(m.getName()))return "CODIGO_EXTERNO_MODELO".equals(a[0])?hsp:null;
            return null;
        });
        Method method=CandidatoGenesisRepository.class.getDeclaredMethod("map",ResultSet.class);method.setAccessible(true);
        return (CandidatoGenesis)method.invoke(new CandidatoGenesisRepository(),rs);
    }
}
