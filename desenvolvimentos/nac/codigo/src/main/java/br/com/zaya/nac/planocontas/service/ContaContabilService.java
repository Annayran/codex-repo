package br.com.zaya.nac.planocontas.service;

import br.com.sankhya.jape.core.JapeSession;
import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.model.ContaCriada;
import br.com.zaya.nac.planocontas.model.Parceiro;
import br.com.zaya.nac.planocontas.model.RegraContaContabil;
import br.com.zaya.nac.planocontas.model.ResultadoGeracao;
import br.com.zaya.nac.planocontas.model.TipoParceiro;
import br.com.zaya.nac.planocontas.repository.ParceiroRepository;
import br.com.zaya.nac.planocontas.repository.PlanoContasRepository;
import br.com.zaya.nac.planocontas.util.SysOutLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class ContaContabilService implements ProcessadorContaParceiro {

    private final ParceiroRepository parceiroRepository;
    private final PlanoContasRepository planoContasRepository;
    private final ParceiroValidator parceiroValidator;

    public ContaContabilService(
            ParceiroRepository parceiroRepository,
            PlanoContasRepository planoContasRepository,
            ParceiroValidator parceiroValidator) {
        this.parceiroRepository = parceiroRepository;
        this.planoContasRepository = planoContasRepository;
        this.parceiroValidator = parceiroValidator;
    }

    @Override
    public List<ResultadoGeracao> processar(
            final BigDecimal codigoParceiro,
            final BigDecimal codigoEmpresa,
            final BigDecimal codigoUsuario,
            final String idExecucao) throws Exception {
        final AtomicReference<List<ResultadoGeracao>> resultado =
                new AtomicReference<List<ResultadoGeracao>>();

        JapeSession.execEnsuringTX(new JapeSession.TXBlock() {
            @Override
            public void doWithTx() throws Exception {
                SysOutLog.info(
                        idExecucao,
                        "Transação da ação iniciada para o parceiro " + codigoParceiro + ".");
                resultado.set(processarNaTransacao(
                        codigoParceiro,
                        codigoEmpresa,
                        codigoUsuario,
                        idExecucao));
            }
        });
        return resultado.get();
    }

    /**
     * Executa a regra na transação que já está ativa no contexto chamador.
     * O evento programável usa este método para não aninhar o controle transacional
     * mantido pelo Jape durante o salvamento do parceiro.
     */
    public List<ResultadoGeracao> processarNaTransacao(
            BigDecimal codigoParceiro,
            BigDecimal codigoEmpresa,
            BigDecimal codigoUsuario,
            String idExecucao) throws Exception {
        Parceiro parceiro = parceiroRepository.buscar(codigoParceiro);
        List<TipoParceiro> tiposPendentes = parceiroValidator.validar(parceiro);
        registrarProtecoes(parceiro, tiposPendentes, idExecucao);
        return processarParceiro(
                parceiro,
                codigoEmpresa,
                codigoUsuario,
                idExecucao,
                tiposPendentes,
                true);
    }

    /**
     * Executa o fluxo automático de forma idempotente. Se outra execução do
     * evento já vinculou a conta na mesma transação, não cria uma duplicidade e
     * não impede o salvamento do parceiro.
     */
    public List<ResultadoGeracao> processarEventoNaTransacao(
            BigDecimal codigoParceiro,
            BigDecimal codigoEmpresa,
            BigDecimal codigoUsuario,
            String idExecucao,
            List<TipoParceiro> tiposParaGerar) throws Exception {
        Parceiro parceiro = parceiroRepository.buscar(codigoParceiro);
        registrarEstadoAtual(parceiro, idExecucao);
        List<TipoParceiro> tiposAtuais = parceiroValidator.validarTipos(parceiro);
        for (TipoParceiro tipo : tiposParaGerar) {
            if (!tiposAtuais.contains(tipo)) {
                throw new RegraNegocioException(
                        "A vertente " + tipo
                                + " não está mais ativa no parceiro durante a geração. "
                                + "A operação foi interrompida para evitar vínculo incorreto.");
            }
        }
        List<TipoParceiro> tiposPendentes = parceiroValidator.filtrarTiposPendentes(
                parceiro,
                tiposParaGerar);
        registrarProtecoes(parceiro, tiposPendentes, idExecucao);
        return processarParceiro(
                parceiro,
                codigoEmpresa,
                codigoUsuario,
                idExecucao,
                tiposPendentes,
                true);
    }

    private void registrarEstadoAtual(Parceiro parceiro, String idExecucao) {
        SysOutLog.info(
                idExecucao,
                "Estado atual consultado na TGFPAR para o parceiro " + parceiro.getCodigo()
                        + ": CLIENTE=" + simNao(parceiro.isCliente())
                        + ", FORNECEDOR=" + simNao(parceiro.isFornecedor())
                        + ", CODCTACTB=" + valorLog(parceiro.getContaFornecedor())
                        + ", CODCTACTB2=" + valorLog(parceiro.getContaCliente())
                        + ", AD_ZYNACCTACLI=" + simNao(parceiro.isContaClienteGerada())
                        + ", AD_ZYNACCTAFOR=" + simNao(parceiro.isContaFornecedorGerada())
                        + ".");
    }

    private void registrarProtecoes(
            Parceiro parceiro,
            List<TipoParceiro> tiposPendentes,
            String idExecucao) {
        if (parceiro.isCliente() && parceiro.isContaClienteGerada()) {
            SysOutLog.info(
                    idExecucao,
                    "Cliente já processado pela rotina (AD_ZYNACCTACLI=S); "
                            + "nenhuma nova conta de cliente será criada.");
        }
        if (parceiro.isFornecedor() && parceiro.isContaFornecedorGerada()) {
            SysOutLog.info(
                    idExecucao,
                    "Fornecedor já processado pela rotina (AD_ZYNACCTAFOR=S); "
                            + "nenhuma nova conta de fornecedor será criada.");
        }
        if (tiposPendentes.isEmpty()) {
            SysOutLog.info(
                    idExecucao,
                    "Todas as vertentes aplicáveis já foram processadas pelo desenvolvimento. "
                            + "Execução encerrada sem nova criação e sem alteração de vínculo.");
        }
    }

    private List<ResultadoGeracao> processarParceiro(
            Parceiro parceiro,
            BigDecimal codigoEmpresa,
            BigDecimal codigoUsuario,
            String idExecucao,
            List<TipoParceiro> tipos,
            boolean vinculoExclusivo) throws Exception {
        if (tipos.isEmpty()) {
            return new ArrayList<ResultadoGeracao>(0);
        }
        BigDecimal empresaPlano = planoContasRepository.buscarEmpresaPlano(codigoEmpresa);
        boolean parceiroExclusivo = parceiro.isCliente() != parceiro.isFornecedor();
        List<ResultadoGeracao> resultados = new ArrayList<ResultadoGeracao>(tipos.size());

        for (TipoParceiro tipo : tipos) {
            RegraContaContabil regra = RegraContaContabil.para(tipo);
            BigDecimal contaAnterior = TipoParceiro.CLIENTE.equals(tipo)
                    ? parceiro.getContaCliente()
                    : parceiro.getContaFornecedor();
            if (contaAnterior != null && contaAnterior.signum() > 0) {
                SysOutLog.info(
                        idExecucao,
                        "O campo " + regra.getCampoVinculoParceiro() + " contém a conta "
                                + contaAnterior + ", mas a proteção da rotina não está marcada. "
                                + "O vínculo será substituído pela nova conta do fluxo Zaya.");
            }
            ContaCriada conta = planoContasRepository.criarConta(
                    empresaPlano,
                    codigoUsuario,
                    parceiro,
                    regra);
            if (vinculoExclusivo && parceiroExclusivo) {
                parceiroRepository.vincularContaExclusiva(
                        parceiro.getCodigo(),
                        regra.getCampoVinculoParceiro(),
                        conta.getCodigo());
            } else {
                parceiroRepository.vincularConta(
                        parceiro.getCodigo(),
                        regra.getCampoVinculoParceiro(),
                        conta.getCodigo());
            }

            ResultadoGeracao resultado = new ResultadoGeracao(
                    parceiro.getCodigo(),
                    parceiro.getNome(),
                    conta.getCodigo(),
                    conta.getConta(),
                    regra.getCampoVinculoParceiro());
            resultados.add(resultado);
            SysOutLog.info(
                    idExecucao,
                    "Conta " + conta.getConta() + " criada para " + tipo
                            + " e vinculada ao campo "
                            + regra.getCampoVinculoParceiro() + ". O marcador "
                            + campoProtecao(tipo) + " foi definido como S.");
        }
        return resultados;
    }

    private String simNao(boolean valor) {
        return valor ? "S" : "N";
    }

    private String valorLog(BigDecimal valor) {
        return valor == null ? "<vazio>" : valor.toPlainString();
    }

    private String campoProtecao(TipoParceiro tipo) {
        return TipoParceiro.CLIENTE.equals(tipo)
                ? "AD_ZYNACCTACLI"
                : "AD_ZYNACCTAFOR";
    }
}
