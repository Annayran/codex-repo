package br.com.zaya.mahospitalar.preco.event;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.LiberacaoAlcadaHelper;
import br.com.sankhya.modelcore.comercial.LiberacaoSolicitada;
import br.com.zaya.mahospitalar.preco.model.DocumentoMargemResultado;
import br.com.zaya.mahospitalar.preco.repository.DocumentoMargemRepository;

import java.math.BigDecimal;

public class ValidaMargemContribuicaoEvent implements EventoProgramavelJava {
    private static final String TABELA_CABECALHO = "TGFCAB";
    private final DocumentoMargemRepository repository = new DocumentoMargemRepository();

    @Override
    public void beforeInsert(PersistenceEvent event) {
    }

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {
        if (!estaConfirmando(event)) return;

        DynamicVO cab = (DynamicVO) event.getVo();
        BigDecimal nuNota = cab.asBigDecimal("NUNOTA");
        BigDecimal codEmp = cab.asBigDecimal("CODEMP");
        if (nuNota == null || codEmp == null || !repository.empresaHabilitada(event.getJdbcWrapper(), codEmp)) {
            return;
        }

        DocumentoMargemResultado avaliacao = repository.avaliar(event.getJdbcWrapper(), nuNota);
        setIfPresent(cab, "AD_MAH_MC", avaliacao.getMargem());
        setIfPresent(cab, "AD_MAH_RECEITALIQ", avaliacao.getReceitaLiquida());
        setIfPresent(cab, "AD_MAH_RESULTADO", avaliacao.getResultado());
        setIfPresent(cab, "AD_MAH_CALCHASH", avaliacao.getCalculoHash());

        if (!avaliacao.exigeLiberacao()) {
            setIfPresent(cab, "AD_MAH_LIBHASH", null);
            return;
        }

        LiberacaoSolicitada solicitacao = new LiberacaoSolicitada(
                nuNota,
                TABELA_CABECALHO,
                avaliacao.getEventoLiberacao(),
                BigDecimal.ZERO
        );
        solicitacao.setTipoEvento(LiberacaoSolicitada.TIPO_EVENTO_PORCENTAGEM);
        solicitacao.setVlrAtual(avaliacao.getMargem());
        solicitacao.setVlrLimite(avaliacao.getMargemMinima());
        solicitacao.setVlrTotal(avaliacao.getReceitaLiquida());
        solicitacao.setDescricao("Margem de contribuicao MA Hospitalar abaixo do minimo");
        preencherMemoriaQuandoSuportado(solicitacao,
                "HASH=" + avaliacao.getCalculoHash() +
                ";MC=" + avaliacao.getMargem().toPlainString() +
                ";MIN=" + avaliacao.getMargemMinima().toPlainString()
        );
        solicitacao.setSolicitante(usuarioAtual());
        solicitacao.setCodTipOper(cab.asBigDecimal("CODTIPOPER"));

        setIfPresent(cab, "AD_MAH_LIBHASH", avaliacao.getCalculoHash());
        LiberacaoAlcadaHelper.processarLiberacao(solicitacao);
    }

    private boolean estaConfirmando(PersistenceEvent event) {
        if (event.getModifingFields() == null || !event.getModifingFields().isModifing("STATUSNOTA")) {
            return false;
        }
        Object novoStatus = event.getModifingFields().getNewValue("STATUSNOTA");
        return "L".equals(String.valueOf(novoStatus));
    }

    private BigDecimal usuarioAtual() {
        AuthenticationInfo auth = AuthenticationInfo.getCurrentOrNull();
        return auth == null || auth.getUserID() == null ? BigDecimal.ZERO : auth.getUserID();
    }

    private void setIfPresent(DynamicVO vo, String field, Object value) {
        if (vo.containsProperty(field)) vo.setProperty(field, value);
    }

    private void preencherMemoriaQuandoSuportado(LiberacaoSolicitada solicitacao, String memoria) throws Exception {
        try {
            solicitacao.getClass().getMethod("setMemoriaCalculo", String.class).invoke(solicitacao, memoria);
        } catch (NoSuchMethodException ignored) {
            // Algumas revisoes da 4.35 nao publicam este campo na API de liberacao.
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable causa = e.getCause();
            if (causa instanceof Exception) throw (Exception) causa;
            throw e;
        }
    }

    @Override
    public void beforeDelete(PersistenceEvent event) {
    }

    @Override
    public void afterInsert(PersistenceEvent event) {
    }

    @Override
    public void afterUpdate(PersistenceEvent event) {
    }

    @Override
    public void afterDelete(PersistenceEvent event) {
    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) {
    }
}
