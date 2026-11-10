package br.com.zaya.proauto.compensacao;

import java.math.BigDecimal;
import java.util.List;
import org.jdom.Element;

/** Aceita apenas uma nota identificada explicitamente no pedido da Central. */
final class ControlePopupCompensacao {
    static final String PROPRIEDADE = "central.notas.pode.compensar";
    static final String NOTA_SESSAO = "br.com.zaya.proauto.compensacao.notaSemPopup";

    static void validarPedido(Element pedido, BigDecimal nota) {
        if (pedido == null || nota == null) { throw contextoInvalido(); }
        Element notas = pedido.getChild("notas");
        Element unica;
        if (notas != null) {
            List<?> filhos = notas.getChildren();
            if (pedido.getChild("nota") != null || filhos.size() != 1
                    || !"nota".equals(((Element) filhos.get(0)).getName())) { throw contextoInvalido(); }
            unica = (Element) filhos.get(0);
        } else {
            List<?> candidatas = pedido.getChildren("nota");
            if (candidatas.size() != 1) { throw contextoInvalido(); }
            unica = (Element) candidatas.get(0);
        }
        String atributo = unica.getAttributeValue("NUNOTA");
        String texto = unica.getTextTrim();
        if (!unica.getChildren().isEmpty()) { throw contextoInvalido(); }
        if (atributo == null && texto.isEmpty()) { throw contextoInvalido(); }
        if (atributo != null) { conferir(atributo, nota); }
        if (!texto.isEmpty()) { conferir(texto, nota); }
    }

    static void validarNotaSessao(Object anterior, BigDecimal atual) {
        if (anterior != null && (!(anterior instanceof BigDecimal)
                || ((BigDecimal) anterior).compareTo(atual) != 0)) { throw contextoInvalido(); }
    }

    private static void conferir(String valor, BigDecimal nota) {
        try {
            if (new BigDecimal(valor).compareTo(nota) == 0) { return; }
        } catch (NumberFormatException ignored) { }
        throw contextoInvalido();
    }

    private static IllegalStateException contextoInvalido() {
        return new IllegalStateException("PRACC: confirme uma unica nota pela Central de Vendas. "
                + "Contexto em lote, ausente ou divergente nao permite suprimir a pergunta por parceiro.");
    }
}
