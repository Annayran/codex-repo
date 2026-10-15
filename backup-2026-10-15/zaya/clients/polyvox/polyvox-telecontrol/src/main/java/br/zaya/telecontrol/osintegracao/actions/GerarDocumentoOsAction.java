package br.zaya.telecontrol.osintegracao.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.zaya.telecontrol.models.OsIntegracaoModel;
import br.zaya.telecontrol.osintegracao.application.LegacyOsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoDocumentoService;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.SincronizacaoResultado;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Botao manual para gerar documentos da integracao de OS:
 * - Nota de compra para OS com peca (respeitando parametro do sistema/override do botao)
 * - Financeiro clone para OS sem peca (PROVISAO=S)
 */
public class GerarDocumentoOsAction implements AcaoRotinaJava {
    private static final String LOG_PREFIX = "[GERAR-DOCUMENTO-OS]";
    private static final String STATUS_OS_FINALIZADA = "Finalizada";
    private final OsIntegracaoUseCase osUseCase;

    public GerarDocumentoOsAction() {
        this(new LegacyOsIntegracaoUseCase());
    }

    public GerarDocumentoOsAction(OsIntegracaoUseCase osUseCase) {
        this.osUseCase = osUseCase;
    }

    @Override
    public void doAction(ContextoAcao ctx) {
        System.out.println(LOG_PREFIX + " ===== INICIANDO ACAO DE GERAR DOCUMENTO OS =====");
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            System.out.println(LOG_PREFIX + " Sessao JapeSession aberta com sucesso");
            hnd.execWithTX(() -> executar(ctx));
            System.out.println(LOG_PREFIX + " ===== ACAO CONCLUIDA COM SUCESSO =====");
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + " ERRO NA ACAO: " + e.getMessage());
            e.printStackTrace();
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro ao gerar documento de OS", e));
            try {
                LogUtil.saveLog(e);
                System.out.println(LOG_PREFIX + " Log de erro salvo com sucesso");
            } catch (Exception ignore) {
                System.out.println(LOG_PREFIX + " Falha ao salvar log de erro: " + ignore.getMessage());
            }
        } finally {
            JapeSession.close(hnd);
            System.out.println(LOG_PREFIX + " Sessao JapeSession fechada");
        }
    }

    private void executar(ContextoAcao ctx) throws Exception {
        System.out.println(LOG_PREFIX + "Iniciando processamento de documentos OS");

        Boolean criarNotaOverride = parseBoolean(ctx.getParam("P_CRIAR_NOTA_COMPRA"));
        Registro[] linhas = ctx.getLinhas();

        Set<String> nrosSelecionados = coletarNroOs(ctx, linhas);
        Set<String> cnpjsSelecionados = coletarCnpjs(ctx, linhas);

        if ((linhas == null || linhas.length == 0) && nrosSelecionados.isEmpty() && cnpjsSelecionados.isEmpty()) {
            System.out.println(LOG_PREFIX + "ERRO: Nenhuma linha selecionada");
            ctx.setMensagemRetorno("Nenhuma linha selecionada.");
            return;
        }

        if (nrosSelecionados.isEmpty() && cnpjsSelecionados.isEmpty()) {
            System.out.println(LOG_PREFIX + "ERRO: Nenhuma OS ou CNPJ valido encontrado para processamento");
            ctx.setMensagemRetorno("Nenhuma OS ou CNPJ valido encontrado para processamento.");
            return;
        }

        System.out.println(LOG_PREFIX + "Selecao recebida | nrosOs=" + nrosSelecionados.size()
                + " | cnpjs=" + cnpjsSelecionados.size());

        Collection<DynamicVO> osPersistidas;
        try {
            if (!nrosSelecionados.isEmpty()) {
                osPersistidas = carregarOsPorNumero(nrosSelecionados);
            } else {
                osPersistidas = JapeFactory.dao("AD_OSTLC")
                        .find("this.CNPJ IS NOT NULL AND this.STATUSOS = ? AND (this.OSPAGA IS NULL OR this.OSPAGA <> ?)",
                                STATUS_OS_FINALIZADA, "S");
            }
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "ERRO: Falha ao carregar AD_OSTLC: " + e.getMessage());
            throw e;
        }

        List<OsIntegracaoModel> lista = montarListaProcessamento(osPersistidas, nrosSelecionados, cnpjsSelecionados);
        if (lista.isEmpty()) {
            System.out.println(LOG_PREFIX + "ERRO: Nenhuma OS finalizada pendente encontrada para a selecao recebida");
            ctx.setMensagemRetorno("Nenhuma OS Finalizada pendente encontrada para a selecao recebida.");
            return;
        }

        System.out.println(LOG_PREFIX + "Iniciando processamento de documentos para OS Finalizadas | totalOs=" + lista.size());

        OsIntegracaoDocumentoService.ResultadoDocumento docResultado;
        try {
            docResultado = OsIntegracaoDocumentoService.processarDocumentosComResultado(lista, criarNotaOverride);
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "ERRO: Falha no processamento de documentos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        SincronizacaoResultado resultado = new SincronizacaoResultado(
                docResultado.getTotalOsRecebidas(),
                docResultado.getGruposComErro(),
                docResultado.getAviso(),
                docResultado.getErro()
        );

        if (resultado.getErro() != null) {
            System.out.println(LOG_PREFIX + "ERRO: " + resultado.getErro());
            throw new Exception(resultado.getErro());
        }

        String aviso = resultado.getAviso() == null ? "" : resultado.getAviso();
        String mensagem = "Processamento documental concluido.\n" +
                "OS consideradas: " + resultado.getOsProcessadas() + "\n" +
                "Grupos com erro: " + resultado.getOsComErro() +
                (aviso.isEmpty() ? "" : "\n" + aviso);

        System.out.println(LOG_PREFIX + "Acao finalizada com sucesso");
        ctx.setMensagemRetorno(mensagem);
    }

    private List<OsIntegracaoModel> montarListaProcessamento(Collection<DynamicVO> osPersistidas,
                                                             Set<String> nrosSelecionados,
                                                             Set<String> cnpjsSelecionados) {
        List<OsIntegracaoModel> lista = new ArrayList<OsIntegracaoModel>();
        if (osPersistidas == null) {
            return lista;
        }

        for (DynamicVO osVO : osPersistidas) {
            if (osVO == null) {
                continue;
            }

            BigDecimal nro = null;
            try {
                nro = osVO.asBigDecimal("NROOS");
            } catch (Exception ignore) {
            }
            String nroTexto = nro == null ? null : nro.toPlainString();
            if (!nrosSelecionados.isEmpty() && (nroTexto == null || !nrosSelecionados.contains(nroTexto))) {
                continue;
            }

            String cnpjVo = normalizarCnpj(textoCampo(osVO, "CNPJ"));
            if (cnpjVo.isEmpty()) {
                continue;
            }
            if (nrosSelecionados.isEmpty() && !cnpjsSelecionados.contains(cnpjVo)) {
                continue;
            }

            OsIntegracaoModel model = new OsIntegracaoModel();
            if (nro != null) {
                model.setNumeroOs(nro.toPlainString());
                model.setOs(nro.toPlainString());
            }
            model.setCnpj(textoCampo(osVO, "CNPJ"));
            lista.add(model);
        }
        return lista;
    }

    private Collection<DynamicVO> carregarOsPorNumero(Set<String> nrosSelecionados) throws Exception {
        List<DynamicVO> lista = new ArrayList<DynamicVO>();
        if (nrosSelecionados == null || nrosSelecionados.isEmpty()) {
            return lista;
        }

        for (String nroOs : nrosSelecionados) {
            if (!isNumeric(nroOs)) {
                continue;
            }

            DynamicVO osVO = JapeFactory.dao("AD_OSTLC").findByPK(new BigDecimal(nroOs.trim()));
            if (osVO == null) {
                System.out.println(LOG_PREFIX + "OS informada nao encontrada na AD_OSTLC | nroOs=" + nroOs);
                continue;
            }

            String statusOs = textoCampo(osVO, "STATUSOS");
            String osPaga = textoCampo(osVO, "OSPAGA");
            if (!STATUS_OS_FINALIZADA.equalsIgnoreCase(statusOs == null ? null : statusOs.trim())) {
                System.out.println(LOG_PREFIX + "OS ignorada por status diferente de Finalizada | nroOs=" + nroOs
                        + " | status=" + statusOs);
                continue;
            }
            if ("S".equalsIgnoreCase(osPaga == null ? null : osPaga.trim())) {
                System.out.println(LOG_PREFIX + "OS ignorada porque ja esta paga | nroOs=" + nroOs);
                continue;
            }
            lista.add(osVO);
        }
        return lista;
    }

    private Set<String> coletarNroOs(ContextoAcao ctx, Registro[] linhas) {
        Set<String> nros = new LinkedHashSet<String>();
        adicionarLista(nros, ctx == null ? null : ctx.getParam("P_NROOS_LIST"), true);

        if (linhas != null) {
            for (Registro linha : linhas) {
                if (linha == null) {
                    continue;
                }
                Object nroObj = linha.getCampo("NROOS");
                if (nroObj != null) {
                    adicionarLista(nros, nroObj, true);
                }
            }
        }
        return nros;
    }

    private Set<String> coletarCnpjs(ContextoAcao ctx, Registro[] linhas) {
        Set<String> cnpjs = new LinkedHashSet<String>();
        adicionarCnpjs(cnpjs, ctx == null ? null : ctx.getParam("P_CNPJ_LIST"));

        if (linhas != null) {
            for (Registro linha : linhas) {
                if (linha == null) {
                    continue;
                }
                Object cnpjObj = linha.getCampo("CNPJ");
                if (cnpjObj != null) {
                    adicionarCnpjs(cnpjs, cnpjObj);
                }
            }
        }
        return cnpjs;
    }

    private void adicionarLista(Set<String> destino, Object valor, boolean somenteNumerico) {
        if (destino == null || valor == null) {
            return;
        }
        String[] partes = valor.toString().split(",");
        for (String parte : partes) {
            if (parte == null) {
                continue;
            }
            String texto = parte.trim();
            if (texto.isEmpty()) {
                continue;
            }
            if (somenteNumerico && !isNumeric(texto)) {
                continue;
            }
            destino.add(texto);
        }
    }

    private void adicionarCnpjs(Set<String> destino, Object valor) {
        if (destino == null || valor == null) {
            return;
        }
        String[] partes = valor.toString().split(",");
        for (String parte : partes) {
            String cnpj = normalizarCnpj(parte);
            if (!cnpj.isEmpty()) {
                destino.add(cnpj);
            }
        }
    }

    private String textoCampo(DynamicVO vo, String campo) {
        if (vo == null || campo == null) {
            return null;
        }
        try {
            Object valor = vo.getProperty(campo);
            return valor == null ? null : String.valueOf(valor);
        } catch (Exception e) {
            try {
                return vo.asString(campo);
            } catch (Exception ignore) {
                return null;
            }
        }
    }

    private String normalizarCnpj(String valor) {
        if (valor == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char ch = valor.charAt(i);
            if (Character.isDigit(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private Boolean parseBoolean(Object valor) {
        if (valor == null) {
            return null;
        }
        if (valor instanceof Boolean) {
            return (Boolean) valor;
        }
        String texto = valor.toString();
        if (texto == null) {
            return null;
        }
        texto = texto.trim();
        if (texto.isEmpty()) {
            return null;
        }
        if ("S".equalsIgnoreCase(texto) || "TRUE".equalsIgnoreCase(texto) || "1".equals(texto)) {
            return Boolean.TRUE;
        }
        if ("N".equalsIgnoreCase(texto) || "FALSE".equalsIgnoreCase(texto) || "0".equals(texto)) {
            return Boolean.FALSE;
        }
        return null;
    }

    private boolean isNumeric(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        try {
            new BigDecimal(valor.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
