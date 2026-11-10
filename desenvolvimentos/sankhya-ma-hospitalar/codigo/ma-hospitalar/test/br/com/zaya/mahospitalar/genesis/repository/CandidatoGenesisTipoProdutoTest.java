package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.service.GenesisSyncService;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class CandidatoGenesisTipoProdutoTest {
    @Test void guardaAceitaM1M3ComNormalizacaoSemCondicionarAoCliente() throws Exception {
        for (String tipo : new String[]{"M1", "M3", " m1 ", " m3 "}) {
            CandidatoGenesis candidato = new CandidatoGenesis();
            candidato.tipoProduto = tipo;
            guarda().invoke(null, candidato);
        }
    }

    @Test void guardaBloqueiaDemaisTiposAntesDoFluxoHttp() throws Exception {
        for (String tipo : new String[]{"M2", "SV", "M4", "", " ", null}) {
            CandidatoGenesis candidato = new CandidatoGenesis();
            candidato.tipoProduto = tipo;
            InvocationTargetException erro = assertThrows(InvocationTargetException.class,
                    () -> guarda().invoke(null, candidato));
            assertTrue(erro.getCause() instanceof IllegalStateException);
            assertTrue(erro.getCause().getMessage().contains("tipo efetivo='M1' ou 'M3'"));
        }
    }

    @Test void consultaPreservaFallbackSerieFilaELimiteSemFiltroDeCliente() throws Exception {
        String fonte = new String(Files.readAllBytes(Paths.get(
                "src/br/com/zaya/mahospitalar/genesis/repository/CandidatoGenesisRepository.java")),
                StandardCharsets.UTF_8);
        assertTrue(fonte.contains("UPPER(COALESCE(TRIM(PREGRA.HSP_TIPOPRODUTO),TRIM(PREGRA.AD_TIPOPRODUTO))) IN ('M1','M3')"));
        assertTrue(fonte.contains("B.SERIE IS NOT NULL"));
        assertTrue(fonte.contains("PARTITION BY B.NUNOTA,UPPER(TRIM(B.SERIE))"));
        assertTrue(fonte.contains("WHERE MX.RN_MOV=1"));
        assertTrue(fonte.contains("M.STATUS_INTEGRACAO='ENVIO_INCERTO'"));
        assertTrue(fonte.contains("M.PROTOCOLO_GENESIS IS NULL OR NVL(M.HTTP_STATUS,0) NOT BETWEEN 200 AND 299"));
        assertTrue(fonte.contains("NOT EXISTS (SELECT 1 FROM TGFCAN"));
        assertTrue(fonte.contains("NOT EXISTS (SELECT 1 FROM AD_GENEXCFILA"));
        assertTrue(fonte.contains("ROWNUM<=?"));
        assertTrue(fonte.contains("ps.setInt(1, Math.max(1, limite))"));
        assertFalse(fonte.contains(" B.CODPARC="));
        assertFalse(fonte.contains(" V.CODPARC="));
    }

    private Method guarda() throws Exception {
        Method metodo = GenesisSyncService.class.getDeclaredMethod(
                "validarTipoProduto", CandidatoGenesis.class);
        metodo.setAccessible(true);
        return metodo;
    }
}
