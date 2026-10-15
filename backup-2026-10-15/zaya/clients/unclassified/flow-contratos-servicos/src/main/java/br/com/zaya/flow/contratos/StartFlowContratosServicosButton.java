package br.com.zaya.flow.contratos;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;

public class StartFlowContratosServicosButton implements AcaoRotinaJava {

    private static final String LOG_PREFIX = "[FlowContratosServicos][StartProcessButton] ";

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        Registro[] linhas = contexto.getLinhas();
        log("Botao acionado. Linhas selecionadas=" + linhas.length);

        if (linhas.length == 0) {
            throw new Exception("Selecione ao menos uma linha para iniciar o Flow Contratos / Servicos.");
        }

        for (int i = 0; i < linhas.length; i++) {
            Registro registro = linhas[i];

            BigDecimal idNegocio = getBigDecimal(registro.getCampo("AD_IDNEGOCIO"));
            BigDecimal codParc = getBigDecimal(registro.getCampo("CODPARC"));
            BigDecimal nuNota = getBigDecimal(registro.getCampo("NUNOTA"));

            log("Linha " + (i + 1) + ": AD_IDNEGOCIO=" + idNegocio + ", CODPARC=" + codParc
                    + ", NUNOTA=" + nuNota);

            if (idNegocio == null || codParc == null || nuNota == null) {
                throw new Exception("Nao foi possivel iniciar o Flow Contratos / Servicos: AD_IDNEGOCIO, CODPARC ou NUNOTA nao informado.");
            }

            BigDecimal idInstPrn = StartFlowContratosServicosHelperSupport.iniciarProcesso(
                    idNegocio,
                    codParc,
                    nuNota,
                    "StartProcessButton");
            log("Linha " + (i + 1) + " iniciada com sucesso. IDINSTPRN=" + idInstPrn);
        }

        contexto.setMensagemRetorno("Flow Contratos / Servicos iniciado para " + linhas.length + " linha(s).");
    }

    private BigDecimal getBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        String texto = value.toString().trim();
        if (texto.length() == 0) {
            return null;
        }

        return new BigDecimal(texto);
    }

    private void log(String msg) {
        System.out.println(LOG_PREFIX + msg);
    }
}
