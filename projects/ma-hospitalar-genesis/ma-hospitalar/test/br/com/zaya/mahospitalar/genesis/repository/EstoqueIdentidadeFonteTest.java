package br.com.zaya.mahospitalar.genesis.repository;

import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstoqueIdentidadeFonteTest {
    private EstoqueGenesisRepository.Candidato candidato(String fonte,String controle){
        EstoqueGenesisRepository.Candidato c=new EstoqueGenesisRepository.Candidato();c.produto=BigDecimal.TEN;
        c.dto=new EstoqueGenesisDto();c.dto.codigoExterno=fonte;c.codigoControle=controle;return c;
    }
    @Test void aceitaCodigoHspPreservandoZeros(){
        EstoqueGenesisRepository.Candidato c=candidato("000010","000010");
        EstoqueGenesisRepository.validarCodigoControle(c);assertEquals("000010",c.dto.codigoExterno);
    }
    @Test void fonteVaziaNuncaUsaCodigoProduto(){
        for(String v:new String[]{null,""," "})assertThrows(IllegalStateException.class,()->EstoqueGenesisRepository.validarCodigoControle(candidato(v,"10")));
    }
    @Test void controleLegadoDivergenteExigeReconciliacao(){
        assertTrue(EstoqueGenesisRepository.precisaReconciliar(candidato("000010","10")));
    }
    @Test void produtoSemControlePodeSerConciliado(){
        EstoqueGenesisRepository.validarCodigoControle(candidato("000010",null));
    }
    @Test void quantidadeNaoTemDefaultArtificial(){assertNull(new EstoqueGenesisDto().qtd);}
    @Test void idSemCodigoExigeReconciliacao(){
        EstoqueGenesisRepository.Candidato c=candidato("000010",null);
        assertFalse(EstoqueGenesisRepository.precisaReconciliar(c));
        c.id=9L;assertTrue(EstoqueGenesisRepository.precisaReconciliar(c));
    }
}
