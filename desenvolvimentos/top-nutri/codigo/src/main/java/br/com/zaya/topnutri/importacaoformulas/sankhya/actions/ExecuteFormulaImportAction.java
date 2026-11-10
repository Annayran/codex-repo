package br.com.zaya.topnutri.importacaoformulas.sankhya.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.topnutri.importacaoformulas.controller.FormulaImportAnalysisController;
import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaAnalysis;
import br.com.zaya.topnutri.importacaoformulas.sankhya.service.SankhyaFormulaImportService;

public final class ExecuteFormulaImportAction implements AcaoRotinaJava {
    private final FormulaImportAnalysisController controller = new FormulaImportAnalysisController();
    private final SankhyaFormulaImportService importService = new SankhyaFormulaImportService();

    @Override
    public void doAction(ContextoAcao context) throws Exception {
        FormulaActionSupport.fileName(context);
        byte[] content = FormulaActionSupport.decodeFile(context);
        String confirmedHash = FormulaActionSupport.requiredString(context, "TPN_HASH_CONFIRMADO");
        FormulaAnalysis analysis = controller.analyze(content);

        if (!analysis.getResult().isValid()) {
            context.mostraErro("O arquivo possui inconsistências. Execute novamente a pré-validação.");
            throw new IllegalStateException("Arquivo inválido na confirmação.");
        }
        if (!analysis.getSha256().equalsIgnoreCase(confirmedHash)) {
            context.mostraErro("O arquivo foi alterado após a pré-validação. Valide-o novamente.");
            throw new IllegalStateException("SHA-256 divergente na confirmação.");
        }

        SankhyaFormulaImportService.ImportResult result = importService.importFormulas(
                analysis.getResult().getFormulas());
        context.setMensagemRetorno(result.toUserMessage());
    }
}
