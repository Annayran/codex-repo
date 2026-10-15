package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.domain.MovimentoEstoque;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PayloadBuilderTest {
    @Test
    void deveMontarPayloadBasico() {
        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setChaveMovimento("1|100|394|ABC123");
        movimento.setProduto("394 - MONITOR MULTIPARAMETRICO");
        movimento.setSerie("ABC123");
        movimento.setStatusEstoque("O");
        movimento.setRgAnvisa("80123456789");
        movimento.setGarantiaMeses(12);
        movimento.setValorUnitario(new BigDecimal("1850.00"));
        movimento.setDataNegociacao(new Date(1751068800000L));

        PayloadBuilder builder = new PayloadBuilder();
        EstoqueGenesisDTO dto = builder.montar(movimento);

        assertEquals("394", dto.getCodigo());
        assertEquals("1|100|394|ABC123", dto.getCodigoExterno());
        assertEquals("394 - MONITOR MULTIPARAMETRICO", dto.getDescServico());
        assertEquals("394 - MONITOR MULTIPARAMETRICO", dto.getDescTecnica());
        assertEquals("ABC123", dto.getNserie());
        assertEquals("O", dto.getStatus());
        assertEquals("12", dto.getPeriodoGarantia());
        assertEquals(BigDecimal.ONE, dto.getQtd());
        assertEquals(new BigDecimal("1850.00"), dto.getVlrCusto());
        assertEquals(Long.valueOf(1751068800L), dto.getDtEntrada());
    }

    @Test
    void deveGerarCodigoExternoQuandoChaveNaoVierDaView() {
        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setCodEmpresa(1);
        movimento.setNumUnico(100L);
        movimento.setProduto("394 - MONITOR MULTIPARAMETRICO");
        movimento.setSerie("ABC123");
        movimento.setDataInstalacao(new Date(1751068800000L));

        EstoqueGenesisDTO dto = new PayloadBuilder().montar(movimento);

        assertEquals("1|100|394|ABC123", dto.getCodigoExterno());
    }

    @Test
    void deveValidarObrigatorios() {
        PayloadBuilder builder = new PayloadBuilder();
        assertTrue(builder.validar(new MovimentoEstoque()).size() > 0);
    }
}