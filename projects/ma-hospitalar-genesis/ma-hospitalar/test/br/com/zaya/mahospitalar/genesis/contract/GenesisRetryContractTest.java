package br.com.zaya.mahospitalar.genesis.contract;

import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class GenesisRetryContractTest {
    private String source(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get("src/br/com/zaya/mahospitalar/genesis/" + path)), StandardCharsets.UTF_8);
    }

    @Test void equipamentosRetomamErrosSemLimiteENaoReenviamCancelados() throws Exception {
        String s = source("repository/CandidatoGenesisRepository.java");
        assertTrue(s.contains("'REPROCESSAR','ERRO','REVISAO'"));
        assertFalse(s.contains("NVL(M.TENTATIVAS,0)<?"));
        assertFalse(s.contains("ps.setInt(1,maxTentativas)"));
        assertTrue(s.contains("LEAST(60,POWER(2"));
        assertTrue(s.contains("NOT EXISTS (SELECT 1 FROM TGFCAN"));
        assertTrue(s.contains("NOT EXISTS (SELECT 1 FROM AD_GENEXCFILA"));
        assertFalse(s.contains("'ENVIADO'"));
        assertFalse(s.contains("'CANCELADO'"));
    }

    @Test void catalogoRetomaRevisaoPreservandoAgendamentoEIdentidade() throws Exception {
        String s = source("repository/EstoqueGenesisRepository.java");
        assertFalse(s.contains("TENTATIVAS,0)<?"));
        assertFalse(s.contains("NOT IN ('ERRO','REVISAO')"));
        assertFalse(s.contains("TENTATIVAS+1>=?"));
        assertTrue(s.contains("C.PROXIMATENTATIVA<=SYSDATE"));
        String falha = s.substring(s.indexOf("public void falha"), s.indexOf("public void observar"));
        assertFalse(falha.contains("SET IDGENESIS"));
        assertFalse(falha.contains("STATUSREMOTO="));
        assertTrue(falha.contains("LEAST(60,POWER(2"));
    }

    @Test void cancelamentosRetomamSemLimiteMasNuncaIgnoramOrigem() throws Exception {
        String s = source("schedule/InativacaoGenesisScheduleAction.java");
        assertTrue(s.contains("F.STATUS IN ('PENDENTE','ERRO','REVISAO')"));
        assertFalse(s.contains("F.TENTATIVAS<?"));
        assertTrue(s.contains("M.ORIGEM_ITEM='CRIADO'"));
        assertTrue(s.contains("M.PROTOCOLO_GENESIS=F.IDGENESIS"));
        assertTrue(s.contains("rs.getInt(\"IDENTIDADE_SEGURA\") != 1"));
        assertTrue(s.indexOf("rs.getInt(\"IDENTIDADE_SEGURA\")") < s.indexOf("client.inativarItem"));
        assertTrue(s.contains("C.DTCANC IS NOT NULL"));
        assertTrue(s.contains("F.DH_ATUALIZACAO ASC NULLS FIRST"));
        String repo = source("repository/CancelamentoGenesisRepository.java");
        assertFalse(repo.contains("TENTATIVAS+1>=?"));
        assertTrue(repo.contains("STATUS IN ('PENDENTE','ERRO','REVISAO')"));
    }

    @Test void parametroLegadoZeradoNaoBloqueiaExecucao() {
        ParametrosGenesis p = new ParametrosGenesis(1, "HML", "https://example.test", true,
                true, 1000, 1000, 0, 25, "MS");
        assertDoesNotThrow(p::validarParaExecucao);
    }
}
