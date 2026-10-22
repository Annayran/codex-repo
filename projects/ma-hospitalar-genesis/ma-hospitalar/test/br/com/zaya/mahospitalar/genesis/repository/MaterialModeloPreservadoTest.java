package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.domain.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class MaterialModeloPreservadoTest {
    @Test void origemHerdadaNaoAlteraReferenciaOriginal() {
        ReferenciaGenesisLocal original=new ReferenciaGenesisLocal(1L,288L,"LEGADO");
        ReferenciaGenesisLocal herdada=original.herdadaDoModelo();
        assertFalse(original.isHerdadaDoModelo()); assertTrue(herdada.isHerdadaDoModelo());
        assertEquals(original.getIdGenesis(),herdada.getIdGenesis());
        assertEquals("LEGADO",herdada.getCodigoExterno());
    }
    @Test void preservaGrupoValidandoSomenteRelacionamento() throws Exception {
        new BaseGenesisApiRepository().validarMaterialModelo(connection(1),candidato(),local(),288L);
    }
    @Test void vinculoRemovidoOuDuplicadoBloqueia() {
        for(int n:new int[]{0,2})assertThrows(IllegalStateException.class,()->
                new BaseGenesisApiRepository().validarMaterialModelo(connection(n),candidato(),local(),288L));
    }
    @Test void idDivergenteNaoChegaAoBanco() {
        assertThrows(IllegalStateException.class,()->new BaseGenesisApiRepository().validarMaterialModelo(null,candidato(),local(),289L));
    }
    private CandidatoGenesis candidato(){CandidatoGenesis c=new CandidatoGenesis();c.codProd=BigDecimal.TEN;c.codGrupoProd=new BigDecimal("999");return c;}
    private ReferenciaGenesisLocal local(){return new ReferenciaGenesisLocal(1L,288L,"LEGADO").herdadaDoModelo();}
    private Connection connection(int count){
        return (Connection)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{Connection.class},(p,m,a)->{
            if("prepareStatement".equals(m.getName())) {
                String sql=(String)a[0];assertTrue(sql.startsWith("SELECT"));assertFalse(sql.contains("CODGRUPOPROD"));
                assertTrue(sql.contains("M.IDMATERIALGEN=E.IDGENESIS"));assertTrue(sql.contains("E.IDBASE=1"));
                return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{PreparedStatement.class},(ps,method,args)->{
                    if("executeQuery".equals(method.getName()))return Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{ResultSet.class},(rs,rm,ra)->{
                        if("next".equals(rm.getName()))return true;
                        if("getInt".equals(rm.getName()))return count;
                        return null;
                    });
                    if("executeUpdate".equals(method.getName()))fail("Nao pode atualizar grupo herdado");
                    return null;
                });
            }
            return null;
        });
    }
}
