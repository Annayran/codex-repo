package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GenesisExecutionLogTest {
    @Test void correlacionaRegistroChamadasEResumo() {
        List<String> linhas = new ArrayList<String>();
        GenesisExecutionLog log = new GenesisExecutionLog("ESTOQUES_PRECOS", linhas::add);
        log.registro("CODPROD=10");
        log.info("HTTP request: metodo=GET");
        log.info("HTTP response: status=200");
        log.finalizar();
        assertTrue(linhas.get(0).contains("[fluxo=ESTOQUES_PRECOS]"));
        assertTrue(linhas.get(0).contains("[seq=1][http=1][CODPROD=10]"));
        assertTrue(linhas.get(1).contains("[seq=2][http=1]"));
        assertTrue(linhas.get(2).contains("requisicoes=1"));
        String execucao = linhas.get(0).split("\\[execucao=")[1].split("\\]")[0];
        assertTrue(linhas.get(2).contains("[execucao="+execucao+"]"));
    }
}
