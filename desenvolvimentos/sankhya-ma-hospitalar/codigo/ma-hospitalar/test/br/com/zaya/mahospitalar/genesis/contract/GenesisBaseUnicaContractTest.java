package br.com.zaya.mahospitalar.genesis.contract;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

class GenesisBaseUnicaContractTest {
    private String read(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get("src/br/com/zaya/mahospitalar/genesis/" + path)), StandardCharsets.UTF_8);
    }

    @Test void fluxoAtivoNaoExecutaPutNemServicoLegado() throws Exception {
        String runner = read("service/IntegracaoGenesisRunner.java");
        String service = read("service/GenesisCadastroSyncService.java");
        assertTrue(runner.contains("CadastroGenesisPreparationService"));
        assertTrue(runner.contains("sync.resolver("));
        assertFalse(runner.contains("sync.garantir("));
        assertFalse(runner.contains("sincronizarPorId("));
        assertFalse(service.contains("client.atualizar("));
    }

    @Test void reservaEConfirmacaoUsamTransacaoAutonoma() throws Exception {
        String tx = read("util/GenesisTransaction.java");
        String guard = read("repository/GenesisOperationRepository.java");
        assertTrue(tx.contains("execWithAutonomousTX"));
        assertFalse(tx.contains(".commit("));
        assertFalse(tx.contains(".rollback("));
        assertTrue(guard.contains("ENVIO_INCERTO"));
        assertTrue(guard.contains("notaCancelada"));
        assertTrue(guard.contains("API_CONFIRMADO"));
        assertTrue(guard.contains("origem.chaveMovimento"));
    }

    @Test void cancelamentoTardioNaoRevinculaNota() throws Exception {
        String runner = read("service/IntegracaoGenesisRunner.java");
        assertTrue(runner.contains("gravacao.nuNota = null"));
        assertTrue(runner.contains("registrarRetornoCancelado"));
        assertTrue(runner.contains("enfileirarRetornoTardio"));
        String fila = read("repository/CancelamentoGenesisRepository.java");
        assertTrue(fila.contains("F.STATUS<>'CONCLUIDO'"));
    }

    @Test void reconsultaEReadOnlyRemotaComJanelaELimite() throws Exception {
        String action = read("schedule/BaseGenesisRefreshScheduleAction.java");
        assertTrue(action.contains(".buscarPorId("));
        assertFalse(action.contains(".criar("));
        assertFalse(action.contains(".atualizar("));
        String repo = read("repository/BaseGenesisRefreshRepository.java");
        assertTrue(repo.contains("SYSDATE-1"));
        assertTrue(repo.contains("ROWNUM<=1"));
        assertTrue(repo.contains("IDBASE=1"));
    }
}
