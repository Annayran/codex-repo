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

        // Parse do parÃ¢metro de override
        Boolean criarNotaOverride = parseBoolean(ctx.getParam("P_CRIAR_NOTA_COMPRA"));

        // Coleta os CNPJs e NROOS por parametros ou pelas linhas selecionadas.
        // O dashboard HTML5 envia parametros para nao depender da chave primaria da tela.
        java.util.Set<String> cnpjsSelecionados = new java.util.LinkedHashSet<>();
        java.util.Set<String> nrosOsSelecionados = new java.util.LinkedHashSet<>();

        adicionarNumerosParam(nrosOsSelecionados, ctx.getParam("P_NROOS_LIST"));
        adicionarCnpjsParam(cnpjsSelecionados, ctx.getParam("P_CNPJ_LIST"));

        Registro[] linhas = ctx.getLinhas();
        if (linhas != null && linhas.length > 0) {
            for (Registro linha : linhas) {
                if (linha == null) continue;
                Object nroOsObj = linha.getCampo("NROOS");
                if (nroOsObj != null) {
                    String nroNormalizado = normalizarNumero(nroOsObj.toString());
                    if (!nroNormalizado.isEmpty()) {
                        nrosOsSelecionados.add(nroNormalizado);
                    }
                }
                Object cnpjObj = linha.getCampo("CNPJ");
                if (cnpjObj == null) continue;
                String cnpjNormalizado = normalizarCnpj(cnpjObj.toString());
                if (!cnpjNormalizado.isEmpty()) {
                    cnpjsSelecionados.add(cnpjNormalizado);
                }
            }
        }

        if (cnpjsSelecionados.isEmpty() && nrosOsSelecionados.isEmpty()) {
            System.out.println(LOG_PREFIX + "ERRO: Nenhum CNPJ ou NROOS valido encontrado nas linhas selecionadas");
            ctx.setMensagemRetorno("Nenhum CNPJ ou NROOS valido encontrado nas linhas selecionadas.");
            return;
        }

        System.out.println(LOG_PREFIX + "CNPJs selecionados para processamento: " + cnpjsSelecionados.size());
        System.out.println(LOG_PREFIX + "NROOS selecionados para processamento: " + nrosOsSelecionados.size());

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
                BigDecimal nro = null;
                try {
                    nro = osVO.asBigDecimal("NROOS");
                } catch (Exception ignore) {
                }
                String nroVo = nro == null ? "" : nro.toPlainString();
                if (!nrosOsSelecionados.isEmpty() && !nrosOsSelecionados.contains(nroVo)) {
                    continue;
                }

                String cnpjVo = normalizarCnpj(osVO.asString("CNPJ"));
                if (cnpjVo.isEmpty()) continue;
                if (!nrosOsSelecionados.isEmpty() || cnpjsSelecionados.contains(cnpjVo)) {
                    OsIntegracaoModel model = new OsIntegracaoModel();
                    if (nro != null) {
                        model.setNumeroOs(nro.toPlainString());
                        model.setOs(nro.toPlainString());
                    }
                    model.setCnpj(osVO.asString("CNPJ"));
                    lista.add(model);
                }
            }
        }

        if (lista.isEmpty()) {
            System.out.println(LOG_PREFIX + "ERRO: Nenhuma OS finalizada persistida encontrada para os CNPJs/NROOS selecionados");
            ctx.setMensagemRetorno("Nenhuma OS Finalizada persistida encontrada para os CNPJs/NROOS selecionados.");
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
     * Normaliza CNPJ/CPF removendo caracteres nÃ£o-dÃ­gitos
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

    private String normalizarNumero(String valor) {
        if (valor == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char ch = valor.charAt(i);
            if (Character.isDigit(ch)) sb.append(ch);
        }
        return sb.toString();
    }

    private void adicionarNumerosParam(java.util.Set<String> destino, Object valor) {
        if (valor == null) return;
        String[] partes = valor.toString().split("[,;|\\s]+");
        for (String parte : partes) {
            String normalizado = normalizarNumero(parte);
            if (!normalizado.isEmpty()) {
                destino.add(normalizado);
            }
        }
    }

    private void adicionarCnpjsParam(java.util.Set<String> destino, Object valor) {
        if (valor == null) return;
        String[] partes = valor.toString().split("[,;|\\s]+");
        for (String parte : partes) {
            String normalizado = normalizarCnpj(parte);
            if (!normalizado.isEmpty()) {
                destino.add(normalizado);
            }
        }
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

