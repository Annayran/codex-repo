package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CancelamentoGenesisRepositoryTest {
    private String status(int http, GenesisApiResult.Classificacao classificacao) {
        return CancelamentoGenesisRepository.statusResultado(new GenesisApiResult(http,classificacao,null,""));
    }
    @Test void sucessoDocumentadoConcluiFila() {
        assertEquals("CONCLUIDO",status(200,GenesisApiResult.Classificacao.SUCESSO));
    }
    @Test void itemAusenteConcluiFilaIdempotente() {
        assertEquals("CONCLUIDO",status(404,GenesisApiResult.Classificacao.NAO_ENCONTRADO));
    }
    @Test void respostaAssincronaNaoConfirmaInativacao() {
        assertEquals("REVISAO",status(202,GenesisApiResult.Classificacao.SUCESSO));
    }
    @Test void timeoutEServidorContinuamPendentes() {
        assertEquals("PENDENTE",status(408,GenesisApiResult.Classificacao.TRANSITORIO));
        assertEquals("PENDENTE",status(500,GenesisApiResult.Classificacao.TRANSITORIO));
    }
    @Test void autenticacaoEConflitoPermitemReavaliacaoAutomatica() {
        assertEquals("REVISAO",status(401,GenesisApiResult.Classificacao.AUTENTICACAO));
        assertEquals("REVISAO",status(409,GenesisApiResult.Classificacao.CONFLITO));
    }
}
