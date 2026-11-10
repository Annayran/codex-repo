package br.com.zaya.nac.planocontas.controller;

import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.model.ResultadoGeracao;
import br.com.zaya.nac.planocontas.model.TipoParceiro;
import br.com.zaya.nac.planocontas.service.ContaContabilService;
import br.com.zaya.nac.planocontas.service.EmpresaEventoResolver;
import br.com.zaya.nac.planocontas.util.ContextoUsuarioJape;
import br.com.zaya.nac.planocontas.util.EventoMensagem;
import br.com.zaya.nac.planocontas.util.NumeroUtils;
import br.com.zaya.nac.planocontas.util.SysOutLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class GerarContaContabilEventoController {

    private static final String MARCADOR_TRANSACAO =
            "ZYNAC.PLANO_CONTAS.EVENTO_PARCEIRO.";
    private static final BigDecimal USUARIO_EFETIVO_EVENTO = BigDecimal.ZERO;

    private final ContaContabilService service;
    private final EmpresaEventoResolver empresaResolver;

    public GerarContaContabilEventoController(
            ContaContabilService service,
            EmpresaEventoResolver empresaResolver) {
        this.service = service;
        this.empresaResolver = empresaResolver;
    }

    public void executar(PersistenceEvent evento) throws Exception {
        String idExecucao = UUID.randomUUID().toString();
        SysOutLog.info(idExecucao, "Início do evento automático de parceiro.");

        try {
            DynamicVO parceiroVO = obterParceiro(evento);
            BigDecimal codigoParceiro = obrigatorio(
                    propriedade(parceiroVO, "CODPARC"),
                    "TGFPAR.CODPARC");
            registrarEstadoRecebido(parceiroVO, codigoParceiro, idExecucao);
            if (!registrarPrimeiraExecucao(evento, codigoParceiro, idExecucao)) {
                return;
            }
            List<TipoParceiro> tiposParaGerar = tiposParaProcessar(parceiroVO, idExecucao);
            if (tiposParaGerar.isEmpty()) {
                return;
            }

            SysOutLog.info(
                    idExecucao,
                    "O evento executará a criação contábil com o usuário efetivo 0 (SUP). "
                            + "O usuário original será restaurado ao final.");
            try (ContextoUsuarioJape contextoUsuario =
                         ContextoUsuarioJape.abrir(USUARIO_EFETIVO_EVENTO)) {
                BigDecimal codigoEmpresa = empresaResolver.resolver(parceiroVO, idExecucao);
                List<ResultadoGeracao> resultados = service.processarEventoNaTransacao(
                        codigoParceiro,
                        codigoEmpresa,
                        USUARIO_EFETIVO_EVENTO,
                        idExecucao,
                        tiposParaGerar);
                if (resultados == null || resultados.isEmpty()) {
                    SysOutLog.info(
                            idExecucao,
                            "Evento encerrado sem nova criação. O cadastro do parceiro "
                                    + "pode prosseguir normalmente.");
                    return;
                }
                SysOutLog.info(
                        idExecucao,
                        "Evento concluído para o parceiro "
                                + resultados.get(0).getCodigoParceiro() + " com "
                                + resultados.size() + " conta(s) criada(s) e vinculada(s). "
                                + "O commit permanece sob controle da transação do Sankhya.");
            }
        } catch (Exception e) {
            SysOutLog.erro(
                    idExecucao,
                    "O evento falhou. Foi solicitado rollback integral do cadastro do parceiro.",
                    e);
            throw new RegraNegocioException(EventoMensagem.erro(e, idExecucao), e);
        }
    }

    private boolean registrarPrimeiraExecucao(
            PersistenceEvent evento,
            BigDecimal codigoParceiro,
            String idExecucao) throws Exception {
        TransactionContext contexto = evento.getTransactionContext();
        if (contexto == null) {
            SysOutLog.info(
                    idExecucao,
                    "O evento não disponibilizou TransactionContext; a proteção por "
                            + "estado do parceiro permanecerá ativa.");
            return true;
        }

        String chave = MARCADOR_TRANSACAO + codigoParceiro.toPlainString();
        if (Boolean.TRUE.equals(contexto.getGlobalTxProperty(chave))) {
            SysOutLog.info(
                    idExecucao,
                    "Execução duplicada detectada na mesma transação para o parceiro "
                            + codigoParceiro + ". O segundo disparo será ignorado.");
            return false;
        }
        contexto.putGlobalTxProperty(chave, Boolean.TRUE);
        return true;
    }

    private void registrarEstadoRecebido(
            DynamicVO parceiroVO,
            BigDecimal codigoParceiro,
            String idExecucao) {
        SysOutLog.info(
                idExecucao,
                "Estado recebido do parceiro " + codigoParceiro
                        + ": CLIENTE=" + valorLog(parceiroVO, "CLIENTE")
                        + ", FORNECEDOR=" + valorLog(parceiroVO, "FORNECEDOR")
                        + ", CODCTACTB=" + valorLog(parceiroVO, "CODCTACTB")
                        + ", CODCTACTB2=" + valorLog(parceiroVO, "CODCTACTB2")
                        + ", AD_ZYNACCTACLI=" + valorLog(parceiroVO, "AD_ZYNACCTACLI")
                        + ", AD_ZYNACCTAFOR=" + valorLog(parceiroVO, "AD_ZYNACCTAFOR")
                        + ".");
    }

    private DynamicVO obterParceiro(PersistenceEvent evento) throws RegraNegocioException {
        if (evento == null || !(evento.getVo() instanceof DynamicVO)) {
            throw new RegraNegocioException(
                    "O evento não recebeu um registro dinâmico da entidade Parceiro.");
        }
        return (DynamicVO) evento.getVo();
    }

    private List<TipoParceiro> tiposParaProcessar(
            DynamicVO parceiroVO,
            String idExecucao)
            throws RegraNegocioException {
        boolean cliente = "S".equals(texto(parceiroVO, "CLIENTE"));
        boolean fornecedor = "S".equals(texto(parceiroVO, "FORNECEDOR"));
        List<TipoParceiro> tipos = new ArrayList<TipoParceiro>(2);

        if (!cliente && !fornecedor) {
            SysOutLog.info(
                    idExecucao,
                    "Parceiro sem marcação de Cliente ou Fornecedor; evento ignorado sem erro.");
            return tipos;
        }
        if (cliente) {
            if (marcadoComoGerado(parceiroVO, "AD_ZYNACCTACLI")) {
                SysOutLog.info(
                        idExecucao,
                        "Cliente já processado pela rotina (AD_ZYNACCTACLI=S); "
                                + "a conta de cliente não será gerada novamente.");
            } else {
                tipos.add(TipoParceiro.CLIENTE);
            }
        }
        if (fornecedor) {
            if (marcadoComoGerado(parceiroVO, "AD_ZYNACCTAFOR")) {
                SysOutLog.info(
                        idExecucao,
                        "Fornecedor já processado pela rotina (AD_ZYNACCTAFOR=S); "
                                + "a conta de fornecedor não será gerada novamente.");
            } else {
                tipos.add(TipoParceiro.FORNECEDOR);
            }
        }
        if (tipos.isEmpty()) {
            SysOutLog.info(
                    idExecucao,
                    "Todas as vertentes aplicáveis já possuem proteção S; "
                            + "evento encerrado sem nova criação.");
        }
        return tipos;
    }

    private boolean marcadoComoGerado(DynamicVO vo, String campo) {
        return "S".equals(texto(vo, campo));
    }

    private BigDecimal obrigatorio(Object valor, String campo) throws RegraNegocioException {
        BigDecimal numero = NumeroUtils.paraBigDecimal(valor, campo);
        if (numero == null || numero.signum() <= 0) {
            throw new RegraNegocioException(
                    "O " + campo + " não foi informado com um valor válido.");
        }
        return numero;
    }

    private String texto(DynamicVO vo, String campo) {
        Object valor = propriedade(vo, campo);
        return valor == null ? null : valor.toString();
    }

    private Object propriedade(DynamicVO vo, String campo) {
        return vo.containsProperty(campo) ? vo.getProperty(campo) : null;
    }

    private String valorLog(DynamicVO vo, String campo) {
        Object valor = propriedade(vo, campo);
        return valor == null || valor.toString().trim().isEmpty()
                ? "<vazio>"
                : valor.toString();
    }
}
