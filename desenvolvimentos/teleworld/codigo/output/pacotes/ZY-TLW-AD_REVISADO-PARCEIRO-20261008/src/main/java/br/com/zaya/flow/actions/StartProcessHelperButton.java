package br.com.zaya.flow.actions;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;

public class StartProcessHelperButton implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        Registro[] linhas = contexto.getLinhas();
        System.out.println("[Teleword][StartProcessHelperButton] Botao acionado. Linhas selecionadas=" + linhas.length);

        for (int i = 0; i < linhas.length; i++) {
            BigDecimal codParc = getBigDecimal(linhas[i].getCampo("CODPARC"));
            System.out.println("[Teleword][StartProcessHelperButton] Linha " + (i + 1) + " CODPARC=" + codParc);

            StartProcessHelperSupport.iniciarProcessoParceiro(codParc, "StartProcessHelperButton");
        }
    }

    private BigDecimal getBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        return new BigDecimal(value.toString());
    }
}
