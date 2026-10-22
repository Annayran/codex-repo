package br.com.zaya.mahospitalar.genesis.contract;
import org.junit.jupiter.api.Test;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
class GenesisRastreabilidadeContractTest {
    private String fonte(String s)throws Exception{return new String(Files.readAllBytes(Paths.get("src/br/com/zaya/mahospitalar/genesis/"+s)),StandardCharsets.UTF_8);}
    @Test void controlesTecnicosNaoLeemMovimentosDeNota()throws Exception{
        String guard=fonte("repository/GenesisOperationRepository.java");
        assertTrue(guard.contains("FROM AD_GENAPICTRL"));
        assertFalse(guard.contains("FROM AD_GENMOVEST"));
        assertTrue(guard.contains("STATUS_INTEGRACAO NOT IN ('ENVIO_INCERTO','API_CONFIRMADO')"));
        assertTrue(fonte("repository/BaseGenesisRefreshRepository.java").contains("LEFT JOIN AD_GENAPICTRL"));
    }
    @Test void estoquesUmProdutoEConfirmacaoAutonoma()throws Exception{
        String s=fonte("schedule/EstoqueGenesisScheduleAction.java");
        assertTrue(s.contains("repo.listar(jdbc,1,"));
        assertFalse(s.contains("execEnsuringTX"));
        assertTrue(s.contains("GenesisTransaction.execute(tx -> { repo.confirmar"));
        assertTrue(s.contains("AtomicBoolean"));
    }
    @Test void evidenciaAntesDoHttpEClienteRealRastreado()throws Exception{
        String http=fonte("client/GenesisHttpClient.java");
        assertTrue(http.indexOf("historico.iniciar(")<http.indexOf(".openConnection()"));
        String runner=fonte("service/IntegracaoGenesisRunner.java");
        assertTrue(runner.contains("new GenesisHttpClient(parametros, () -> settings.token, rastreio)"));
        String h=fonte("repository/GenesisRequestRepository.java");
        assertTrue(h.contains("CHAVE_CONTROLE,CHAVE_MOVIMENTO"));
        assertFalse(h.contains(".commit("));assertFalse(h.contains(".rollback("));
    }
    @Test void dashboardIsolaRefreshEPermiteDetalhesSemReprocessamento()throws Exception{
        String s=new String(Files.readAllBytes(Paths.get("webapp/genesis/index.jsp")),StandardCharsets.UTF_8);
        assertTrue(s.contains("data-area-target=\"refresh\""));
        assertTrue(s.contains("data-area-target=\"registrations\""));
        assertTrue(s.contains("data-area-target=\"history\""));
        assertTrue(s.contains("FROM AD_GENREQLOG"));
        assertTrue(s.contains("data-history-context="));
    }
}
