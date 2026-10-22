package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class BaseGenesisApiRepositoryTest {
    @Test void getAtualizaSomenteCamposPresentesSemAlterarVinculosSankhya() throws Exception {
        Capture c = new Capture();
        new BaseGenesisApiRepository().atualizarRetorno(c.connection(), TipoEntidade.CLIENTE, 90,
                api("{\"id\":90,\"nomeFantasia\":\"Nome remoto\",\"email\":null,\"CODPARC\":999}"), "MS");
        assertTrue(c.sql.contains("NOMEFANTASIA=?"));
        assertTrue(c.sql.contains("EMAIL=?"));
        assertFalse(c.sql.contains("CODPARC"));
        assertFalse(c.sql.contains("RAZAOSOCIAL=?"));
        assertTrue(c.sql.contains("WHERE IDBASE=1 AND IDGENESIS=?"));
        assertTrue(c.values.containsValue("Nome remoto"));
        assertTrue(c.values.containsValue(null));
        assertEquals(90L, c.values.get(c.values.size()));
    }

    @Test void converteDatasEpochEPreservaCamposOperacionais() throws Exception {
        Capture c = new Capture();
        new BaseGenesisApiRepository().atualizarRetorno(c.connection(), TipoEntidade.ITEM_MATERIAL, 90,
                api("{\"dataAquisicao\":1700000000,\"valorUnitario\":12.34,\"marca\":\"Remota\"}"), "S");
        assertTrue(c.values.containsValue(new Timestamp(1700000000000L)));
        assertTrue(c.values.containsValue(new java.math.BigDecimal("12.34")));
        assertFalse(c.sql.contains("NUNOTA"));
        assertFalse(c.sql.contains("CHAVEMOVIMENTO"));
        assertFalse(c.sql.contains("CODPROD"));
    }

    @Test void retornoAmbiguoNaoEConfirmado() {
        Capture c = new Capture(); c.affected = 2;
        assertThrows(IllegalStateException.class, () -> new BaseGenesisApiRepository().atualizarRetorno(
                c.connection(), TipoEntidade.MODELO, 90, api("{\"nome\":\"Modelo\"}"), "MS"));
    }

    @Test void usaRespostaOriginalENaoTextoMascarado() throws Exception {
        Capture c = new Capture();
        GenesisApiResult r = new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO, "90",
                "{\"cnpj\":\"12345678000190\"}", "{\"cnpj\":\"***\"}");
        new BaseGenesisApiRepository().atualizarRetorno(c.connection(), TipoEntidade.CLIENTE, 90, r, "MS");
        assertTrue(c.values.containsValue("12345678000190"));
        assertFalse(c.values.containsValue("***"));
    }

    private static GenesisApiResult api(String body) {
        return new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO, "90", body);
    }
    private static class Capture {
        String sql; int affected = 1;
        final Map<Integer,Object> values = new LinkedHashMap<Integer,Object>();
        Connection connection() {
            return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Connection.class},
                    (p,m,a) -> {
                        if ("prepareStatement".equals(m.getName())) {
                            sql = (String)a[0];
                            return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{PreparedStatement.class},
                                    (ps,method,args) -> {
                                        if ("setObject".equals(method.getName()) || "setLong".equals(method.getName()))
                                            values.put((Integer)args[0], args[1]);
                                        if ("executeUpdate".equals(method.getName())) return affected;
                                        return null;
                                    });
                        }
                        return null;
                    });
        }
    }
}
