package br.com.zaya.teleworld.events;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.extensions.flow.ContextoEvento;
import br.com.sankhya.extensions.flow.EventoProcessoJava;

public class PopularParceiroPendenteEvent implements EventoProcessoJava {

    private static final String FORM_PARCEIRO_PENDENTE = "AD_PARCPEND";
    private static final String STATUS_PENDENTE = "P";

    @Override
    public void executar(ContextoEvento contexto) throws Exception {
        BigDecimal codParc = getBigDecimal(contexto.getCampo("CODPARC"));
        BigDecimal idInstPrn = getBigDecimal(contexto.getIdInstanceProcesso());

        if (codParc == null || BigDecimal.ZERO.compareTo(codParc) == 0) {
            throw new Exception("Nao foi possivel iniciar o Flow de Parceiros: CODPARC nao informado.");
        }

        Registro[] linhas = contexto.getLinhasFormulario(FORM_PARCEIRO_PENDENTE);
        if (linhas != null && linhas.length > 0) {
            return;
        }

        Registro parceiroPendente = contexto.novaLinhaFormulario(FORM_PARCEIRO_PENDENTE);
        parceiroPendente.setCampo("IDINSTPRN", idInstPrn);
        parceiroPendente.setCampo("CODPARC", codParc);
        parceiroPendente.setCampo("AD_REVISADO", STATUS_PENDENTE);
        parceiroPendente.save();
        contexto.saveAll();
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
