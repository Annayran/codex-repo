package br.com.zaya.mahospitalar.genesis.contract;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenesisEstoqueContractTest {
    String read(String p)throws Exception{return new String(Files.readAllBytes(Paths.get(p)),StandardCharsets.UTF_8);}
    @Test void precoUsaVigencia101EHelperNativo()throws Exception{
        String s=read("src/br/com/zaya/mahospitalar/genesis/service/EstoquePrecoSankhya.java");
        assertTrue(s.contains("getNumeroUnicoTabela(jdbc, data, new BigDecimal(\"101\"))"));
        assertTrue(s.contains("ComercialUtils.obtemPreco2"));assertFalse(s.contains("TipoCusto.CUSTO_MEDIO"));
        assertFalse(s.contains("TGFCUS"));
    }
    @Test void catalogoTemControleSeparadoEJoinIpi()throws Exception{
        String s=read("src/br/com/zaya/mahospitalar/genesis/repository/EstoqueGenesisRepository.java");
        assertTrue(s.contains("AD_GENESTCTRL"));assertTrue(s.contains("I.CODIPI=P.CODIPI"));
        assertTrue(s.contains("HSP_TIPOPRODUTO"));assertFalse(s.contains("UPDATE AD_GENMOVEST"));
        assertFalse(s.contains("INSERT INTO AD_GENEXCFILA"));
    }
    @Test void dashboardCondicionaTabelaNovaEPaginaCatalogo()throws Exception{
        String s=read("webapp/genesis/index.jsp");
        assertTrue(s.contains("catalogoDisponivel.rows[0].TOTAL > 0"));
        assertTrue(s.contains("RN BETWEEN ${catalogStart} AND ${catalogEnd}"));
        assertTrue(s.contains("<c:out value=\"${item.STATUSREMOTO}\""));
    }
}
