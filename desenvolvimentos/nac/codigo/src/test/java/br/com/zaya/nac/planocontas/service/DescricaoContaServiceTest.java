package br.com.zaya.nac.planocontas.service;

import br.com.zaya.nac.planocontas.model.Parceiro;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DescricaoContaServiceTest {

    private final DescricaoContaService service = new DescricaoContaService();

    @Test
    void deveLimitarDescricaoAQuarentaCaracteres() {
        Parceiro parceiro = parceiro(
                "PROTTEGE SOLAR PELICULAS E ACESSORIOS AUTOMOTIVOS");

        String descricao = service.gerar(parceiro);

        assertEquals(40, descricao.length());
        assertEquals("PROTTEGE SOLAR PELICULAS E ACESSORIOS AU", descricao);
    }

    @Test
    void devePreservarDescricaoDentroDoLimite() {
        assertEquals("PARCEIRO TESTE", service.gerar(parceiro("  PARCEIRO TESTE  ")));
    }

    @Test
    void deveUsarCodigoQuandoNomeEstiverVazio() {
        assertEquals("PARCEIRO 123", service.gerar(parceiro("  ")));
    }

    private Parceiro parceiro(String nome) {
        return new Parceiro(
                new BigDecimal("123"),
                nome,
                true,
                false,
                null,
                null,
                false,
                false);
    }
}
