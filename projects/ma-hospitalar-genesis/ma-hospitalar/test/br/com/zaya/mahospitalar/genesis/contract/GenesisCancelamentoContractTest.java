package br.com.zaya.mahospitalar.genesis.contract;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class GenesisCancelamentoContractTest {
    private String read(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)),StandardCharsets.UTF_8);
    }
    @Test void filaNaoImpedeExclusaoDaNotaENaoPerdeAuditoria() throws Exception {
        String sql = read("sql/genesis/cancelamento/01_deploy.sql");
        assertTrue(sql.contains("C.CONSTRAINT_TYPE IN ('P','R')"));
        assertFalse(sql.contains("CREATE TABLE AD_GENEXCFILA"));
        String manual = read("docs/genesis/cadastro-manual-cancelamento.md");
        assertTrue(manual.contains("Chave primaria"));
        assertTrue(manual.contains("SEM relacionamento com TGFCAB"));
        String repo = read("src/br/com/zaya/mahospitalar/genesis/repository/CancelamentoGenesisRepository.java");
        assertTrue(repo.indexOf("INSERT INTO AD_GENEXCFILA") < repo.indexOf("DELETE FROM AD_GENMOVEST"));
        assertTrue(repo.contains("SET NUNOTA=NULL"));
        assertTrue(repo.contains("M.ORIGEM_ITEM='CRIADO'"));
        assertTrue(repo.contains("ELSE 'REVISAO'"));
        assertFalse(repo.contains(".commit("));
    }
    @Test void jobExigeCancelamentoConfirmadoEProtegeIdentidade() throws Exception {
        String job = read("src/br/com/zaya/mahospitalar/genesis/schedule/InativacaoGenesisScheduleAction.java");
        assertTrue(job.contains("FROM TGFCAN"));
        assertTrue(job.contains("C.DTCANC IS NOT NULL"));
        assertTrue(job.contains("NOT EXISTS (SELECT 1 FROM TGFCAB"));
        assertTrue(job.contains("I.CHAVEMOVIMENTO=F.CHAVE_MOVIMENTO"));
        assertTrue(job.contains("parametros.isSimulacao()"));
        assertTrue(job.contains("execEnsuringTX"));
        assertTrue(job.contains("ROWNUM<=?"));
    }
    @Test void eventoNaoChamaApiENaoConfirmaTransacao() throws Exception {
        String event = read("src/br/com/zaya/mahospitalar/genesis/event/CancelamentoNotaGenesisEvent.java");
        assertTrue(event.contains("beforeDelete"));
        assertFalse(event.contains("GenesisHttpClient"));
        assertFalse(event.contains(".commit("));
        assertTrue(event.contains("throw e;"));
    }
    @Test void recuperacaoLegadaEReprocessamentoTemGuardas() throws Exception {
        String repo = read("src/br/com/zaya/mahospitalar/genesis/repository/CancelamentoGenesisRepository.java");
        assertTrue(repo.contains("REGEXP_SUBSTR(I.CHAVEMOVIMENTO,'[^|]+',1,2)"));
        assertTrue(repo.contains("rs.getInt(\"QTD\")==1"));
        assertTrue(repo.contains("STATUS_INTEGRACAO='CANCELADO'"));
        String candidatos = read("src/br/com/zaya/mahospitalar/genesis/repository/CandidatoGenesisRepository.java");
        assertTrue(candidatos.contains("LEAST(60,POWER(2"));
        assertTrue(candidatos.contains("NOT EXISTS (SELECT 1 FROM TGFCAN"));
        assertTrue(candidatos.contains("NOT EXISTS (SELECT 1 FROM AD_GENEXCFILA"));
        String jsp = read("webapp/genesis/index.jsp");
        assertTrue(jsp.contains("monitorCancelamentos"));
        assertTrue(jsp.contains("row.CANCELADOS"));
    }
}
