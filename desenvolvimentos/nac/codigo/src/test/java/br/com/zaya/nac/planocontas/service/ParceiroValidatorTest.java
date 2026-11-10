package br.com.zaya.nac.planocontas.service;

import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.model.Parceiro;
import br.com.zaya.nac.planocontas.model.TipoParceiro;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParceiroValidatorTest {

    private final ParceiroValidator validator = new ParceiroValidator();

    @Test
    void deveIdentificarClienteSemConta() throws Exception {
        Parceiro parceiro = parceiro(true, false, null, null, false, false);
        assertEquals(
                Collections.singletonList(TipoParceiro.CLIENTE),
                validator.validar(parceiro));
    }

    @Test
    void deveIdentificarFornecedorSemConta() throws Exception {
        Parceiro parceiro = parceiro(false, true, null, null, false, false);
        assertEquals(
                Collections.singletonList(TipoParceiro.FORNECEDOR),
                validator.validar(parceiro));
    }

    @Test
    void deveIdentificarClienteEFornecedorSemConta() throws Exception {
        assertEquals(
                Arrays.asList(TipoParceiro.CLIENTE, TipoParceiro.FORNECEDOR),
                validator.validar(parceiro(true, true, null, null, false, false)));
    }

    @Test
    void deveProcessarClienteComContaExistenteSemProtecao() throws Exception {
        assertEquals(
                Collections.singletonList(TipoParceiro.CLIENTE),
                validator.validar(
                        parceiro(true, false, BigDecimal.TEN, null, false, false)));
    }

    @Test
    void deveProcessarFornecedorComContaExistenteSemProtecao() throws Exception {
        assertEquals(
                Collections.singletonList(TipoParceiro.FORNECEDOR),
                validator.validar(
                        parceiro(false, true, null, BigDecimal.TEN, false, false)));
    }

    @Test
    void deveIgnorarVertenteJaProtegidaNoParceiroMisto() throws Exception {
        assertEquals(
                Collections.singletonList(TipoParceiro.FORNECEDOR),
                validator.validar(
                        parceiro(true, true, BigDecimal.TEN, null, true, false)));
    }

    @Test
    void deveIgnorarParceiroComTodasAsVertentesProtegidas() throws Exception {
        assertEquals(
                Collections.<TipoParceiro>emptyList(),
                validator.validar(
                        parceiro(true, true, BigDecimal.TEN, BigDecimal.ONE, true, true)));
    }

    @Test
    void deveBloquearParceiroSemTipo() {
        assertThrows(
                RegraNegocioException.class,
                () -> validator.validar(
                        parceiro(false, false, null, null, false, false)));
    }

    private Parceiro parceiro(
            boolean cliente,
            boolean fornecedor,
            BigDecimal contaCliente,
            BigDecimal contaFornecedor,
            boolean contaClienteGerada,
            boolean contaFornecedorGerada) {
        return new Parceiro(
                BigDecimal.ONE,
                "PARCEIRO TESTE",
                cliente,
                fornecedor,
                contaCliente,
                contaFornecedor,
                contaClienteGerada,
                contaFornecedorGerada);
    }
}
