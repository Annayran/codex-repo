package br.com.zaya.flow.actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;

public class StartProcessHelperEvent implements EventoProgramavelJava {

    private static final String TX_PROPERTY_PREFIX = "teleword.helper.startProcess.parceiro.";

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {
        DynamicVO parceiroVO = (DynamicVO) event.getVo();
        BigDecimal codParc = parceiroVO.asBigDecimal("CODPARC");

        if (!isCampoEmAlteracao(event)) {
            System.out.println("[Teleword][StartProcessHelperEvent] beforeUpdate ignorado: AD_REVISADO nao esta em alteracao.");
            return;
        }

        String revisadoAnterior;
        String revisadoNovo;

        try {
            revisadoAnterior = getString(event.getModifingFields().getOldValue("AD_REVISADO"));
            revisadoNovo = getString(event.getModifingFields().getNewValue("AD_REVISADO"));
        } catch (IllegalStateException e) {
            System.out.println("[Teleword][StartProcessHelperEvent] beforeUpdate ignorado: AD_REVISADO nao esta em alteracao. "
                    + e.getMessage());
            return;
        }

        System.out.println("[Teleword][StartProcessHelperEvent] beforeUpdate TGFPAR. CODPARC=" + codParc
                + ", AD_REVISADO anterior=" + revisadoAnterior + ", novo=" + revisadoNovo);

        if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
            System.out.println("[Teleword][StartProcessHelperEvent] beforeUpdate sem CODPARC valido. Ignorando.");
            return;
        }

        if (isStatusValidoParaIniciar(revisadoNovo) && !revisadoNovo.equals(revisadoAnterior)) {
            JapeSession.getCurrentSession().addTxProperty(getTxPropertyName(codParc), Boolean.TRUE);
            System.out.println("[Teleword][StartProcessHelperEvent] Marcado para iniciar Flow. CODPARC=" + codParc
                    + ", AD_REVISADO=" + revisadoNovo);
        } else {
            System.out.println("[Teleword][StartProcessHelperEvent] Fluxo nao marcado: AD_REVISADO nao mudou para P/N.");
        }
    }

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
        DynamicVO parceiroVO = (DynamicVO) event.getVo();
        BigDecimal codParc = parceiroVO.asBigDecimal("CODPARC");
        String revisadoAtual = getString(parceiroVO.getProperty("AD_REVISADO"));

        System.out.println("[Teleword][StartProcessHelperEvent] afterUpdate TGFPAR. CODPARC=" + codParc
                + ", AD_REVISADO atual=" + revisadoAtual);

        if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
            System.out.println("[Teleword][StartProcessHelperEvent] Fluxo nao iniciado: CODPARC nao informado.");
            return;
        }

        if (!isStatusValidoParaIniciar(revisadoAtual)) {
            System.out.println("[Teleword][StartProcessHelperEvent] Fluxo nao iniciado: AD_REVISADO atual nao esta P/N.");
            return;
        }

        if (!Boolean.TRUE.equals(JapeSession.getCurrentSession().getTxProperty(getTxPropertyName(codParc)))) {
            System.out.println("[Teleword][StartProcessHelperEvent] Fluxo nao iniciado: beforeUpdate nao marcou P/N.");
            return;
        }

        try {
            StartProcessHelperSupport.iniciarProcessoParceiro(codParc, "StartProcessHelperEvent");
        } catch (Exception e) {
            System.out.println("[Teleword][StartProcessHelperEvent] Erro ao iniciar Flow. Rotina nao sera travada: "
                    + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) throws Exception {
    }

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {
    }

    private String getString(Object value) {
        if (value == null) {
            return null;
        }

        return value.toString().trim().toUpperCase();
    }

    private String getTxPropertyName(BigDecimal codParc) {
        return TX_PROPERTY_PREFIX + codParc.toPlainString();
    }

    private boolean isStatusValidoParaIniciar(String status) {
        return "P".equals(status) || "N".equals(status);
    }

    private boolean isCampoEmAlteracao(PersistenceEvent event) {
        try {
            return event.getModifingFields() != null && event.getModifingFields().isModifing("AD_REVISADO");
        } catch (Exception e) {
            System.out.println("[Teleword][StartProcessHelperEvent] Nao foi possivel verificar alteracao de AD_REVISADO: "
                    + e.getMessage());
            return false;
        }
    }
}
