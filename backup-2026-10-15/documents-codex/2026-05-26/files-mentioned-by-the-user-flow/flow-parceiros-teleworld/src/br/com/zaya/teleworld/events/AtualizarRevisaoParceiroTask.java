package br.com.zaya.teleworld.events;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.extensions.flow.ContextoTarefa;
import br.com.sankhya.extensions.flow.TarefaJava;
import br.com.sankhya.jape.wrapper.JapeFactory;

public class AtualizarRevisaoParceiroTask implements TarefaJava {

    private static final String FORM_PARCEIRO_PENDENTE = "AD_PARCPEND";

    @Override
    public void executar(ContextoTarefa contexto) throws Exception {
        Registro[] parceirosPendentes = contexto.getLinhasFormulario(FORM_PARCEIRO_PENDENTE);

        if (parceirosPendentes == null || parceirosPendentes.length == 0) {
            throw new Exception("Nenhum parceiro encontrado na tabela do Flow AD_PARCPEND.");
        }

        for (Registro parceiroPendente : parceirosPendentes) {
            BigDecimal codParc = getBigDecimal(parceiroPendente.getCampo("CODPARC"));
            String revisado = getString(parceiroPendente.getCampo("AD_REVISADO"));

            if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
                throw new Exception("CODPARC nao informado na tabela do Flow AD_PARCPEND.");
            }

            if (!"P".equals(revisado) && !"N".equals(revisado)) {
                throw new Exception("Valor invalido para AD_REVISADO. Use P ou N.");
            }

            JapeFactory.dao("Parceiro")
                    .prepareToUpdateByPK(codParc)
                    .set("AD_REVISADO", revisado)
                    .update();
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

    private String getString(Object value) {
        if (value == null) {
            return null;
        }

        return value.toString().trim().toUpperCase();
    }
}
