package br.com.zaya.nac.planocontas.controller;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.model.ResultadoProcessamentoLote;
import br.com.zaya.nac.planocontas.service.ContaContabilService;
import br.com.zaya.nac.planocontas.service.EmpresaEventoResolver;
import br.com.zaya.nac.planocontas.service.ProcessamentoLoteService;
import br.com.zaya.nac.planocontas.util.AcaoMensagem;
import br.com.zaya.nac.planocontas.util.NumeroUtils;
import br.com.zaya.nac.planocontas.util.SysOutLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class GerarContaContabilController {

    private static final String PARAMETRO_EMPRESA = "CODEMP";

    private final ContaContabilService service;
    private final EmpresaEventoResolver empresaResolver;

    public GerarContaContabilController(
            ContaContabilService service,
            EmpresaEventoResolver empresaResolver) {
        this.service = service;
        this.empresaResolver = empresaResolver;
    }

    public void executar(ContextoAcao contexto) throws Exception {
        String idExecucao = UUID.randomUUID().toString();
        SysOutLog.info(idExecucao, "Início do botão Gerar Conta Contábil.");

        try {
            Registro[] linhas = obterLinhas(contexto);
            List<BigDecimal> codigosParceiros = obterCodigos(linhas);
            BigDecimal codigoEmpresa = empresaResolver.resolver(
                    contexto.getParam(PARAMETRO_EMPRESA),
                    "parâmetro CODEMP do botão",
                    idExecucao);
            BigDecimal codigoUsuario = NumeroUtils.paraBigDecimal(
                    contexto.getUsuarioLogado(),
                    "usuário logado");
            if (codigoUsuario == null || codigoUsuario.signum() < 0) {
                throw new RegraNegocioException(
                        "Não foi possível identificar o usuário logado. O lote não foi iniciado.");
            }

            ResultadoProcessamentoLote resultado = new ProcessamentoLoteService(service).processar(
                    codigosParceiros,
                    codigoEmpresa,
                    codigoUsuario,
                    idExecucao);

            SysOutLog.info(
                    idExecucao,
                    "Botão finalizado: " + resultado.getParceirosProcessados()
                            + " parceiro(s) processado(s), "
                            + resultado.getParceirosIgnorados() + " ignorado(s) e "
                            + resultado.getQuantidadeFalhas() + " falha(s).");
            contexto.setMensagemRetorno(AcaoMensagem.resultadoLote(resultado, idExecucao));
        } catch (Exception e) {
            SysOutLog.erro(
                    idExecucao,
                    "O botão não conseguiu iniciar o processamento. Nenhum novo parceiro "
                            + "será iniciado por esta execução.",
                    e);
            throw new RegraNegocioException(AcaoMensagem.erro(e, idExecucao), e);
        }
    }

    private Registro[] obterLinhas(ContextoAcao contexto) throws RegraNegocioException {
        Registro[] linhas = contexto.getLinhas();
        if (linhas == null || linhas.length == 0) {
            throw new RegraNegocioException(
                    "Nenhum parceiro foi selecionado. Selecione um ou mais parceiros e execute novamente.");
        }
        return linhas;
    }

    private List<BigDecimal> obterCodigos(Registro[] linhas) throws RegraNegocioException {
        List<BigDecimal> codigos = new ArrayList<BigDecimal>(linhas.length);
        for (Registro linha : linhas) {
            BigDecimal codigo = NumeroUtils.paraBigDecimal(linha.getCampo("CODPARC"), "CODPARC");
            if (codigo == null || codigo.signum() <= 0) {
                throw new RegraNegocioException(
                        "Uma das linhas selecionadas não possui CODPARC válido. "
                                + "O lote não foi iniciado.");
            }
            codigos.add(codigo);
        }
        return codigos;
    }
}
