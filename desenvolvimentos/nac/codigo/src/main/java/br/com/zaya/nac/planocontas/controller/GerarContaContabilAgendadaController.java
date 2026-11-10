package br.com.zaya.nac.planocontas.controller;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.model.ResultadoProcessamentoLote;
import br.com.zaya.nac.planocontas.repository.ParceiroPendenteRepository;
import br.com.zaya.nac.planocontas.service.EmpresaEventoResolver;
import br.com.zaya.nac.planocontas.service.ProcessamentoLoteService;
import br.com.zaya.nac.planocontas.util.ContextoUsuarioJape;
import br.com.zaya.nac.planocontas.util.SysOutLog;
import java.math.BigDecimal;
import java.util.List;
import org.cuckoo.core.ScheduledActionContext;

public final class GerarContaContabilAgendadaController {

    public static final int TAMANHO_LOTE = 100;

    private final ParceiroPendenteRepository parceiroPendenteRepository;
    private final ProcessamentoLoteService processamentoLoteService;
    private final EmpresaEventoResolver empresaResolver;

    public GerarContaContabilAgendadaController(
            ParceiroPendenteRepository parceiroPendenteRepository,
            ProcessamentoLoteService processamentoLoteService,
            EmpresaEventoResolver empresaResolver) {
        this.parceiroPendenteRepository = parceiroPendenteRepository;
        this.processamentoLoteService = processamentoLoteService;
        this.empresaResolver = empresaResolver;
    }

    public ResultadoProcessamentoLote executar(
            ScheduledActionContext contexto,
            String idExecucao) throws Exception {
        BigDecimal usuario = resolverUsuario();
        SysOutLog.info(
                idExecucao,
                "Contexto Jape preparado com o usuário " + usuario
                        + " para os listeners do Plano de Contas.");
        try (ContextoUsuarioJape contextoUsuario = ContextoUsuarioJape.abrir(usuario)) {
            BigDecimal empresa = empresaResolver.resolver(
                    null,
                    "empresa da ação agendada",
                    idExecucao);
            List<BigDecimal> pendentes = parceiroPendenteRepository.buscarCodigosPendentes(
                    TAMANHO_LOTE);

            if (pendentes.isEmpty()) {
                informar(contexto, idExecucao, "Nenhum parceiro ativo pendente foi encontrado.");
                return new ResultadoProcessamentoLote();
            }

            informar(
                    contexto,
                    idExecucao,
                    "Foram encontrados " + pendentes.size()
                            + " parceiro(s) para o lote de até " + TAMANHO_LOTE + ".");
            ResultadoProcessamentoLote resultado = processamentoLoteService.processar(
                    pendentes,
                    empresa,
                    usuario,
                    idExecucao);
            String resumo = "Lote finalizado: " + resultado.getParceirosProcessados()
                    + " parceiro(s) atualizado(s), " + resultado.getContasCriadas()
                    + " conta(s) criada(s), " + resultado.getParceirosIgnorados()
                    + " ignorado(s) e " + resultado.getQuantidadeFalhas() + " falha(s).";
            informar(contexto, idExecucao, resumo);

            if (resultado.possuiFalhas()) {
                throw new RegraNegocioException(
                        resumo + " Os parceiros com falha permanecem pendentes para nova execução. "
                                + "Consulte a execução " + idExecucao + " no log.");
            }
            return resultado;
        }
    }

    private BigDecimal resolverUsuario() throws RegraNegocioException {
        AuthenticationInfo autenticacao = AuthenticationInfo.getCurrentOrNull();
        BigDecimal usuario = autenticacao == null ? null : autenticacao.getUserID();
        if (usuario == null || usuario.signum() < 0) {
            throw new RegraNegocioException(
                    "A ação agendada não possui um usuário de execução válido. "
                            + "Configure o agendamento com um usuário autorizado a criar contas contábeis.");
        }
        return usuario;
    }

    private void informar(
            ScheduledActionContext contexto,
            String idExecucao,
            String mensagem) {
        SysOutLog.info(idExecucao, mensagem);
        if (contexto != null) {
            contexto.log(mensagem);
            contexto.info(mensagem);
        }
    }

}
