package br.com.zaya.teleworld.flow;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.ModifingFields;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.comercial.AtributosRegras;
import br.com.sankhya.modelcore.comercial.ContextoRegra;
import br.com.sankhya.modelcore.comercial.Regra;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.zaya.teleworld.flow.controller.RevisaoProdutoController;
import br.com.zaya.teleworld.flow.controller.ValidacaoFiscalXmlController;
import br.com.zaya.teleworld.flow.model.service.RevisaoProdutoService;
import br.com.zaya.teleworld.flow.model.service.RotinaLog;

/** Adaptador de confirmacao: cadastrar como Regra OU evento da TGFCAB. */
public class ValidaRevisaoProdutosConfirmacao implements Regra, EventoProgramavelJava {
    private final RevisaoProdutoController controller = new RevisaoProdutoController();

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
        RotinaLog.executar("ValidaRevisaoProdutosConfirmacao.evento.afterUpdate",
                () -> validar((DynamicVO) event.getVo(), event.getModifingFields()));
    }

    @Override
    public void afterUpdate(ContextoRegra ctx) throws Exception {
        RotinaLog.executar("ValidaRevisaoProdutosConfirmacao.regra.afterUpdate", () -> {
            PrePersistEntityState state = ctx.getPrePersistEntityState();
            validar(state.getNewVO(), state.getModifingFields());
        });
    }

    private void validar(DynamicVO nota, ModifingFields campos) throws Exception {
        boolean confirmando = JapeSession.getPropertyAsBoolean(AtributosRegras.CONFIRMANDO, false).booleanValue();
        if (!confirmando) {
            RotinaLog.detalhe("confirmacao ignorada: fora da operacao de confirmacao");
            return;
        }
        String entidade = nota.getValueObjectID();
        if (entidade == null || !entidade.contains(DynamicEntityNames.CABECALHO_NOTA)) {
            RotinaLog.detalhe("confirmacao ignorada: entidade diferente de CabecalhoNota");
            return;
        }
        String tipoMovimento = nota.asString("TIPMOV");
        if (!"C".equals(tipoMovimento) && !"V".equals(tipoMovimento)) {
            RotinaLog.detalhe("confirmacao ignorada: TIPMOV diferente de C ou V");
            return;
        }
        if (!"L".equals(nota.asString("STATUSNOTA"))) {
            RotinaLog.detalhe("confirmacao ignorada: status atual diferente de L");
            return;
        }
        if (campos == null) {
            throw new Exception("Nao foi possivel verificar a transicao de confirmacao da nota "
                    + nota.asBigDecimal("NUNOTA") + ".");
        }
        if (!campos.isModifing("STATUSNOTA")) {
            RotinaLog.detalhe("confirmacao ignorada: STATUSNOTA nao alterado");
            return;
        }
        Object statusAnterior = campos.getOldValue("STATUSNOTA");
        if (!RevisaoProdutoService.deveValidarConfirmacao(confirmando,
                statusAnterior == null ? null : statusAnterior.toString(),
                nota.asString("STATUSNOTA"))) {
            return;
        }
        controller.validarNota(nota.asBigDecimal("NUNOTA"));
        new ValidacaoFiscalXmlController().validarNota(nota.asBigDecimal("NUNOTA"));
    }
    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception { }
    @Override
    public void afterInsert(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception { }
    @Override
    public void afterDelete(PersistenceEvent event) throws Exception { }
    @Override
    public void beforeCommit(TransactionContext context) throws Exception { }
    @Override
    public void beforeInsert(ContextoRegra ctx) throws Exception { }
    @Override
    public void beforeDelete(ContextoRegra ctx) throws Exception { }
    @Override
    public void afterInsert(ContextoRegra ctx) throws Exception { }
    @Override
    public void beforeUpdate(ContextoRegra ctx) throws Exception { }
    @Override
    public void afterDelete(ContextoRegra ctx) throws Exception { }
}
