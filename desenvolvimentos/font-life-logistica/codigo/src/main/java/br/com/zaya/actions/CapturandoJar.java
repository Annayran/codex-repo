package br.com.zaya.actions;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.ws.ServiceContext;
import com.sankhya.util.SessionFile;
import com.sankhya.util.UIDGenerator;

public class CapturandoJar implements AcaoRotinaJava {
    @Override
    public void doAction(ContextoAcao ctx) throws Exception {
        Registro[] registros = ctx.getLinhas();
        if (registros == null || registros.length != 1) {
            ctx.mostraErro("Selecione exatamente um registro para baixar o JAR.");
            return;
        }

        SessionHandle hnd = null;
        String chave = "jar_" + UIDGenerator.getNextID();
        try {
            hnd = JapeSession.open();
            Object arquivo = registros[0].getCampo("ARQUIVO");
            if (!(arquivo instanceof byte[]) || ((byte[]) arquivo).length == 0) {
                ctx.mostraErro("O campo ARQUIVO deve conter um arquivo binario nao vazio.");
                return;
            }

            SessionFile sessionFile = SessionFile.createSessionFile(
                    "FileViewer.jar", "application/java-archive", (byte[]) arquivo);
            ServiceContext.getCurrent().putHttpSessionAttribute(chave, sessionFile);
            ctx.setMensagemRetorno("<p>Baixe o arquivo e mantenha a extensao .jar.</p>"
                    + "<a href=\"/mge/visualizadorArquivos.mge?chaveArquivo="
                    + chave
                    + "&amp;ignoraPodeExportarjar=N&amp;forcarDownload=N\" target=\"_blank\">Baixar JAR</a>");
        } finally {
            JapeSession.close(hnd);
        }
    }
}
