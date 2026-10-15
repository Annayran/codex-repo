package br.zaya.telecontrol.osintegracao.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Botao manual para desfazer a geracao de pagamento/documento de OS TLC.
 *
 * Limpa os marcadores gravados pelo processamento documental:
 * - OSPAGA volta para N
 * - NUNOTAGER e NUFINGER voltam para null
 */
public class DesfazerPagamentoOsAction implements AcaoRotinaJava {

    private static final String LOG_PREFIX = "[DESFAZER-PAGAMENTO-OS] ";
    private static final String DAO_OS_INTEGRACAO = "AD_OSTLC";

    @Override
    public void doAction(ContextoAcao ctx) {
        System.out.println(LOG_PREFIX + "===== INICIANDO ACAO DE DESFAZER PAGAMENTO OS =====");
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
            hnd.execWithTX(() -> executar(ctx));
            System.out.println(LOG_PREFIX + "===== ACAO CONCLUIDA COM SUCESSO =====");
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "ERRO NA ACAO: " + e.getMessage());
            e.printStackTrace();
            ctx.setMensagemRetorno(br.zaya.telecontrol.shared.utils.ActionMessageUtil.erroAmigavel("Erro ao desfazer pagamento de OS", e));
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
        } finally {
            JapeSession.close(hnd);
        }
    }

    private void executar(ContextoAcao ctx) throws Exception {
        Set<String> nrosSelecionados = coletarNroOs(ctx);
        Set<String> cnpjsSelecionados = coletarCnpjs(ctx);

        if (nrosSelecionados.isEmpty() && cnpjsSelecionados.isEmpty()) {
            ctx.setMensagemRetorno("Nenhuma OS ou CNPJ valido recebido para desfazer pagamento.");
            return;
        }

        int alteradas = 0;
        JapeWrapper osDAO = JapeFactory.dao(DAO_OS_INTEGRACAO);

        if (!nrosSelecionados.isEmpty()) {
            for (String nroOs : nrosSelecionados) {
                if (!isNumeric(nroOs)) {
                    continue;
                }
                DynamicVO osVO = osDAO.findByPK(new BigDecimal(nroOs.trim()));
                if (osVO != null && desfazer(osDAO, osVO)) {
                    alteradas++;
                }
            }
        } else {
            Collection<DynamicVO> osPersistidas = osDAO.find("this.CNPJ IS NOT NULL AND this.OSPAGA = ?", "S");
            for (DynamicVO osVO : osPersistidas) {
                if (osVO == null) {
                    continue;
                }
                String cnpj = normalizarCnpj(osVO.asString("CNPJ"));
                if (cnpjsSelecionados.contains(cnpj) && desfazer(osDAO, osVO)) {
                    alteradas++;
                }
            }
        }

        String mensagem = "Desfazer pagamento concluido. OS alteradas: " + alteradas + ".";
        System.out.println(LOG_PREFIX + mensagem);
        ctx.setMensagemRetorno(mensagem);
    }

    private boolean desfazer(JapeWrapper osDAO, DynamicVO osVO) throws Exception {
        if (osVO == null) {
            return false;
        }

        BigDecimal nroOs = osVO.asBigDecimal("NROOS");
        FluidUpdateVO updVO = osDAO.prepareToUpdate(osVO);
        boolean alterou = false;

        if (osVO.containsProperty("OSPAGA")) {
            updVO.set("OSPAGA", "N");
            alterou = true;
        }
        if (osVO.containsProperty("NUNOTAGER")) {
            updVO.set("NUNOTAGER", null);
            alterou = true;
        }
        if (osVO.containsProperty("NUFINGER")) {
            updVO.set("NUFINGER", null);
            alterou = true;
        }

        if (alterou) {
            updVO.update();
            System.out.println(LOG_PREFIX + "OS marcada como nao paga | nroOs=" + nroOs);
        }
        return alterou;
    }

    private Set<String> coletarNroOs(ContextoAcao ctx) {
        Set<String> nros = new LinkedHashSet<String>();
        adicionarLista(nros, ctx == null ? null : ctx.getParam("P_NROOS_LIST"));

        Registro[] linhas = ctx == null ? null : ctx.getLinhas();
        if (linhas != null) {
            for (Registro linha : linhas) {
                if (linha == null) {
                    continue;
                }
                Object nro = linha.getCampo("NROOS");
                if (nro != null && !nro.toString().trim().isEmpty()) {
                    nros.add(nro.toString().trim());
                }
            }
        }

        return nros;
    }

    private Set<String> coletarCnpjs(ContextoAcao ctx) {
        Set<String> cnpjs = new LinkedHashSet<String>();
        adicionarCnpjs(cnpjs, ctx == null ? null : ctx.getParam("P_CNPJ_LIST"));

        Registro[] linhas = ctx == null ? null : ctx.getLinhas();
        if (linhas != null) {
            for (Registro linha : linhas) {
                if (linha == null) {
                    continue;
                }
                Object cnpj = linha.getCampo("CNPJ");
                String normalizado = cnpj == null ? "" : normalizarCnpj(cnpj.toString());
                if (!normalizado.isEmpty()) {
                    cnpjs.add(normalizado);
                }
            }
        }

        return cnpjs;
    }

    private void adicionarLista(Set<String> destino, Object valor) {
        if (destino == null || valor == null) {
            return;
        }
        String[] partes = valor.toString().split(",");
        for (String parte : partes) {
            if (parte != null && !parte.trim().isEmpty()) {
                destino.add(parte.trim());
            }
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

    private String normalizarCnpj(String valor) {
        if (valor == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
