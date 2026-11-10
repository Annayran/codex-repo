package br.com.zaya.nac.planocontas.service;

import br.com.zaya.nac.planocontas.model.ContaCriada;
import br.com.zaya.nac.planocontas.model.Parceiro;
import br.com.zaya.nac.planocontas.model.RegraContaContabil;
import br.com.zaya.nac.planocontas.model.ResultadoGeracao;
import br.com.zaya.nac.planocontas.model.TipoParceiro;
import br.com.zaya.nac.planocontas.repository.ParceiroRepository;
import br.com.zaya.nac.planocontas.repository.PlanoContasRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContaContabilServiceTest {

    @Test
    void deveGerarAsDuasContasPeloFluxoDoBotao() throws Exception {
        Parceiro parceiro = parceiro(true, true, null, null, false, false);
        ParceiroRepositoryFake parceiros = new ParceiroRepositoryFake(parceiro);
        PlanoContasRepositoryFake contas = new PlanoContasRepositoryFake();
        ContaContabilService service = service(parceiros, contas);

        List<ResultadoGeracao> resultados = service.processarNaTransacao(
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE,
                "teste-botao");

        assertEquals(2, resultados.size());
        assertEquals(
                Arrays.asList(TipoParceiro.CLIENTE, TipoParceiro.FORNECEDOR),
                contas.tiposCriados);
        assertEquals(Arrays.asList("CODCTACTB2", "CODCTACTB"), parceiros.camposVinculados);
        assertEquals(
                Arrays.asList("AD_ZYNACCTACLI", "AD_ZYNACCTAFOR"),
                parceiros.camposProtegidos);
        assertEquals(0, parceiros.vinculosExclusivos);
    }

    @Test
    void deveGerarAsDuasContasNoEventoMesmoComValoresAutomaticosNaReleitura()
            throws Exception {
        Parceiro parceiro = parceiro(
                true,
                true,
                new BigDecimal("782"),
                new BigDecimal("2725"),
                false,
                false);
        ParceiroRepositoryFake parceiros = new ParceiroRepositoryFake(parceiro);
        PlanoContasRepositoryFake contas = new PlanoContasRepositoryFake();
        ContaContabilService service = service(parceiros, contas);

        List<ResultadoGeracao> resultados = service.processarEventoNaTransacao(
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE,
                "teste-evento",
                Arrays.asList(TipoParceiro.CLIENTE, TipoParceiro.FORNECEDOR));

        assertEquals(2, resultados.size());
        assertEquals(Arrays.asList("CODCTACTB2", "CODCTACTB"), parceiros.camposVinculados);
        assertEquals(
                Arrays.asList("AD_ZYNACCTACLI", "AD_ZYNACCTAFOR"),
                parceiros.camposProtegidos);
        assertEquals(0, parceiros.vinculosExclusivos);
    }

    @Test
    void deveGerarSomenteVertenteSemProtecaoDoParceiroMisto()
            throws Exception {
        Parceiro parceiro = parceiro(true, true, BigDecimal.TEN, null, true, false);
        ParceiroRepositoryFake parceiros = new ParceiroRepositoryFake(parceiro);
        PlanoContasRepositoryFake contas = new PlanoContasRepositoryFake();
        ContaContabilService service = service(parceiros, contas);

        List<ResultadoGeracao> resultados = service.processarEventoNaTransacao(
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE,
                "teste-evento-parcial",
                Arrays.asList(TipoParceiro.CLIENTE, TipoParceiro.FORNECEDOR));

        assertEquals(1, resultados.size());
        assertEquals(Collections.singletonList("CODCTACTB"), parceiros.camposVinculados);
        assertEquals(Collections.singletonList("AD_ZYNACCTAFOR"), parceiros.camposProtegidos);
        assertEquals(0, parceiros.vinculosExclusivos);
    }

    @Test
    void deveEncerrarSemCriarQuandoTodasAsVertentesEstiveremProtegidas()
            throws Exception {
        Parceiro parceiro = parceiro(
                true,
                true,
                BigDecimal.TEN,
                BigDecimal.ONE,
                true,
                true);
        ParceiroRepositoryFake parceiros = new ParceiroRepositoryFake(parceiro);
        PlanoContasRepositoryFake contas = new PlanoContasRepositoryFake();
        ContaContabilService service = service(parceiros, contas);

        List<ResultadoGeracao> resultados = service.processarNaTransacao(
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE,
                "teste-protegido");

        assertEquals(0, resultados.size());
        assertEquals(0, contas.tiposCriados.size());
        assertEquals(0, parceiros.camposVinculados.size());
    }

    @Test
    void deveAplicarVinculoExclusivoNoBotaoParaClienteSemProtecao()
            throws Exception {
        Parceiro parceiro = parceiro(
                true,
                false,
                new BigDecimal("782"),
                new BigDecimal("2725"),
                false,
                false);
        ParceiroRepositoryFake parceiros = new ParceiroRepositoryFake(parceiro);
        ContaContabilService service = service(
                parceiros,
                new PlanoContasRepositoryFake());

        List<ResultadoGeracao> resultados = service.processarNaTransacao(
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE,
                "teste-botao-exclusivo");

        assertEquals(1, resultados.size());
        assertEquals(Collections.singletonList("CODCTACTB2"), parceiros.camposVinculados);
        assertEquals(Collections.singletonList("AD_ZYNACCTACLI"), parceiros.camposProtegidos);
        assertEquals(1, parceiros.vinculosExclusivos);
    }

    private ContaContabilService service(
            ParceiroRepository parceiroRepository,
            PlanoContasRepository planoContasRepository) {
        return new ContaContabilService(
                parceiroRepository,
                planoContasRepository,
                new ParceiroValidator());
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

    private static final class ParceiroRepositoryFake implements ParceiroRepository {

        private final Parceiro parceiro;
        private final List<String> camposVinculados = new ArrayList<String>();
        private final List<String> camposProtegidos = new ArrayList<String>();
        private int vinculosExclusivos;

        private ParceiroRepositoryFake(Parceiro parceiro) {
            this.parceiro = parceiro;
        }

        @Override
        public Parceiro buscar(BigDecimal codigoParceiro) {
            return parceiro;
        }

        @Override
        public void vincularConta(
                BigDecimal codigoParceiro,
                String campo,
                BigDecimal codigoConta) {
            camposVinculados.add(campo);
            camposProtegidos.add(campoProtecao(campo));
        }

        @Override
        public void vincularContaExclusiva(
                BigDecimal codigoParceiro,
                String campo,
                BigDecimal codigoConta) {
            vinculosExclusivos++;
            camposVinculados.add(campo);
            camposProtegidos.add(campoProtecao(campo));
        }

        private String campoProtecao(String campo) {
            return "CODCTACTB2".equals(campo)
                    ? "AD_ZYNACCTACLI"
                    : "AD_ZYNACCTAFOR";
        }
    }

    private static final class PlanoContasRepositoryFake implements PlanoContasRepository {

        private final List<TipoParceiro> tiposCriados = new ArrayList<TipoParceiro>();

        @Override
        public BigDecimal buscarEmpresaPlano(BigDecimal codigoEmpresa) {
            return codigoEmpresa;
        }

        @Override
        public ContaCriada criarConta(
                BigDecimal empresaPlano,
                BigDecimal usuario,
                Parceiro parceiro,
                RegraContaContabil regra) {
            tiposCriados.add(regra.getTipoParceiro());
            int sequencia = tiposCriados.size();
            return new ContaCriada(
                    BigDecimal.valueOf(100L + sequencia),
                    "CONTA-" + sequencia);
        }
    }
}
