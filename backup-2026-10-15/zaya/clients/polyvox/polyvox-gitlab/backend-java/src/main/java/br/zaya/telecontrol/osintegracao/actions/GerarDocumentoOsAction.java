package br.zaya.telecontrol.osintegracao.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.vo.DynamicVO;
import br.zaya.telecontrol.models.OsIntegracaoModel;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoDocumentoService;
import br.zaya.telecontrol.osintegracao.application.LegacyOsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.OsIntegracaoUseCase;
import br.zaya.telecontrol.osintegracao.application.SincronizacaoResultado;
import br.zaya.telecontrol.shared.utils.LogUtil;
import java.math.BigDecimal;

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
            ctx.setMensagemRetorno("Erro ao gerar documentos de OS: " + e.getMessage());
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

        // Parse do parâmetro de override
        Boolean criarNotaOverride = parseBoolean(ctx.getParam("P_CRIAR_NOTA_COMPRA"));

        // Pega as linhas selecionadas
        Registro[] linhas = ctx.getLinhas();
        if (linhas == null || linhas.length == 0) {
            System.out.println(LOG_PREFIX + "ERRO: Nenhuma linha selecionada");
            ctx.setMensagemRetorno("Nenhuma linha selecionada.");
            return;
        }

        // Coleta os CNPJs selecionados
        java.util.Set<String> cnpjsSelecionados = new java.util.LinkedHashSet<>();
        for (Registro linha : linhas) {
            if (linha == null) continue;
            Object cnpjObj = linha.getCampo("CNPJ");
            if (cnpjObj == null) continue;
            String cnpjNormalizado = normalizarCnpj(cnpjObj.toString());
            if (!cnpjNormalizado.isEmpty()) {
                cnpjsSelecionados.add(cnpjNormalizado);
            }
        }

        if (cnpjsSelecionados.isEmpty()) {
            System.out.println(LOG_PREFIX + "ERRO: Nenhum CNPJ valido encontrado nas linhas selecionadas");
            ctx.setMensagemRetorno("Nenhum CNPJ válido encontrado nas linhas selecionadas.");
            return;
        }

        System.out.println(LOG_PREFIX + "CNPJs selecionados para processamento: " + cnpjsSelecionados.size());

        // Carregar OS persistidas
        java.util.Collection<DynamicVO> osPersistidas = null;
        try {
            osPersistidas = JapeFactory.dao("AD_OSTLC")
                    .find("this.CNPJ IS NOT NULL AND this.STATUSOS = ? AND (this.OSPAGA IS NULL OR this.OSPAGA <> ?)",
                            STATUS_OS_FINALIZADA, "S");
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "ERRO: Falha ao carregar AD_OSTLC: " + e.getMessage());
            throw e;
        }

        java.util.List<OsIntegracaoModel> lista = new java.util.ArrayList<>();
        if (osPersistidas != null) {
            for (DynamicVO osVO : osPersistidas) {
                if (osVO == null) continue;
                String cnpjVo = normalizarCnpj(osVO.asString("CNPJ"));
                if (cnpjVo.isEmpty()) continue;
                if (cnpjsSelecionados.contains(cnpjVo)) {
                    OsIntegracaoModel model = new OsIntegracaoModel();
                    try {
                        BigDecimal nro = osVO.asBigDecimal("NROOS");
                        if (nro != null) {
                            model.setNumeroOs(nro.toPlainString());
                            model.setOs(nro.toPlainString());
                        }
                    } catch (Exception ignore) {
                    }
                    model.setCnpj(osVO.asString("CNPJ"));
                    lista.add(model);
                }
            }
        }

        if (lista.isEmpty()) {
            System.out.println(LOG_PREFIX + "ERRO: Nenhuma OS finalizada persistida encontrada para os CNPJs selecionados");
            ctx.setMensagemRetorno("Nenhuma OS Finalizada persistida encontrada para os CNPJs selecionados.");
            return;
        }

        System.out.println(LOG_PREFIX + "Iniciando processamento de documentos para OS Finalizadas | totalOs=" + lista.size());

        OsIntegracaoDocumentoService.ResultadoDocumento docResultado = null;
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
            throw new Exception("Erro ao gerar documentos de OS: " + resultado.getErro());
        }

        String aviso = resultado.getAviso() == null ? "" : resultado.getAviso();
        String mensagem = "Processamento documental concluido.\n" +
                "OS consideradas: " + resultado.getOsProcessadas() + "\n" +
                "Grupos com erro: " + resultado.getOsComErro() +
                (aviso.isEmpty() ? "" : "\n" + aviso);

        System.out.println(LOG_PREFIX + "Acao finalizada com sucesso");
        ctx.setMensagemRetorno(mensagem);
    }

    /**
     * Normaliza CNPJ/CPF removendo caracteres não-dígitos
     */
    private String normalizarCnpj(String valor) {
        if (valor == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char ch = valor.charAt(i);
            if (Character.isDigit(ch)) sb.append(ch);
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
}
