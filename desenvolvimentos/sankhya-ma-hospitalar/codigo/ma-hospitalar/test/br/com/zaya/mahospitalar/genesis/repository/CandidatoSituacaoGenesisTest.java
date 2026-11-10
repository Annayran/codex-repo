package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CandidatoSituacaoGenesisTest {
    @Test void carregaIdConciliadoSemPresumirOperacional() throws Exception {
        CandidatoGenesis c=map(new BigDecimal("6"));
        assertEquals(Long.valueOf(6),c.idSituacaoGenesis);
    }
    @Test void preservaAusenciaDeDominioParaValidacaoExplicita() throws Exception {
        assertNull(map(null).idSituacaoGenesis);
    }
    @Test void consultaExigeUmDominioAtivoConciliadoDaMesmaBaseECodigo() throws Exception {
        // The source query is checked without opening a Jape transaction or database.
        String source=new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(
                "src/br/com/zaya/mahospitalar/genesis/repository/CandidatoGenesisRepository.java")),java.nio.charset.StandardCharsets.UTF_8);
        assertTrue(source.contains("CASE WHEN COUNT(*)=1 THEN MAX(SIT.IDGENESIS) END"));
        assertTrue(source.contains("SIT.IDBASE=1 AND SIT.TIPODOMINIO='SITUACAO'"));
        assertTrue(source.contains("SIT.STATUSCONC='CONCILIADO'"));
        assertTrue(source.contains("UPPER(TRIM(SIT.STATUSGENESIS))='ATIVO'"));
        assertTrue(source.contains("UPPER(TRIM(SIT.CODIGOSANKHYA))=UPPER(COALESCE(TRIM(B.STATUS_ESTOQUE),'O'))"));
        assertTrue(source.contains("('PENDENTE','REPROCESSAR','ERRO','REVISAO')"));
    }
    private CandidatoGenesis map(BigDecimal id) throws Exception {
        ResultSet rs=(ResultSet)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{ResultSet.class},(p,m,a)->{
            if("getBigDecimal".equals(m.getName()))return "ID_SITUACAO_GENESIS".equals(a[0])?id:null;
            if("getString".equals(m.getName()))return null;
            return null;
        });
        Method map=CandidatoGenesisRepository.class.getDeclaredMethod("map",ResultSet.class);map.setAccessible(true);
        return (CandidatoGenesis)map.invoke(new CandidatoGenesisRepository(),rs);
    }
}
