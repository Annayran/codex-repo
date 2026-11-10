package br.com.zaya.topnutri.importacaoformulas.sankhya.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.topnutri.importacaoformulas.controller.FormulaImportAnalysisController;
import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaAnalysis;
import br.com.zaya.topnutri.importacaoformulas.model.dto.FormulaImportSimulation;
import br.com.zaya.topnutri.importacaoformulas.sankhya.service.SankhyaFormulaPreviewService;
import br.com.zaya.topnutri.importacaoformulas.view.FormulaAnalysisJsonRenderer;

import java.util.Collections;

public final class PreviewFormulaImportAction implements AcaoRotinaJava {
    private final FormulaImportAnalysisController controller = new FormulaImportAnalysisController();
    private final FormulaAnalysisJsonRenderer renderer = new FormulaAnalysisJsonRenderer();
    private final SankhyaFormulaPreviewService previewService = new SankhyaFormulaPreviewService();

    @Override
    public void doAction(ContextoAcao context) throws Exception {
        String fileName = FormulaActionSupport.fileName(context);
        byte[] content = FormulaActionSupport.decodeFile(context);
        FormulaAnalysis analysis = controller.analyze(content);
        FormulaImportSimulation simulation = analysis.getResult().isValid()
                ? previewService.simulate(analysis.getResult().getFormulas())
                : new FormulaImportSimulation(
                        Collections.emptyList(), Collections.emptyList());
        context.setMensagemRetorno(
                FormulaActionSupport.RESULT_PREFIX
                        + renderer.render(fileName, analysis, simulation));
    }
}
