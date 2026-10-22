package br.com.zaya.mahospitalar.genesis.repository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class EstoqueMensagemTest {
    @Test void resumoCabeEmCemBytesMesmoComUnicode(){
        StringBuilder s=new StringBuilder();for(int i=0;i<200;i++)s.append('\u00e7');
        String resumo=EstoqueGenesisRepository.resumoMensagem(s.toString());
        assertTrue(resumo.getBytes(java.nio.charset.StandardCharsets.UTF_8).length<=100);
        assertNull(EstoqueGenesisRepository.resumoMensagem(null));
    }
}
