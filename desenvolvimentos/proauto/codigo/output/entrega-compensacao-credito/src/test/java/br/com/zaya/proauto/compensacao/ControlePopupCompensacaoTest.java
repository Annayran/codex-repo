package br.com.zaya.proauto.compensacao;

import java.math.BigDecimal;
import org.jdom.Element;
import org.junit.Test;
import static org.junit.Assert.*;

public class ControlePopupCompensacaoTest {
    private final BigDecimal nota = new BigDecimal("100");
    @Test public void aceitaNotaIndividualPorAtributo() {
        ControlePopupCompensacao.validarPedido(new Element("requestBody")
                .addContent(new Element("nota").setAttribute("NUNOTA", "100")), nota);
    }
    @Test public void aceitaColecaoDeUmaNotaPorTexto() {
        ControlePopupCompensacao.validarPedido(pedido("100"), nota);
    }
    @Test public void rejeitaLoteMistoAntesDaSupressao() {
        Element pedido = pedido("100");
        pedido.getChild("notas").addContent(new Element("nota").setText("200"));
        rejeitar(pedido);
    }
    @Test public void rejeitaOutraNotaEIdentificadoresContraditorios() {
        rejeitar(pedido("200"));
        Element pedido = pedido("200");
        pedido.getChild("notas").getChild("nota").setAttribute("NUNOTA", "100");
        rejeitar(pedido);
    }
    @Test public void rejeitaContextoAusenteOuDesconhecido() {
        rejeitar(null);
        rejeitar(new Element("requestBody"));
        rejeitar(pedido(""));
        rejeitar(pedido("invalido"));
    }
    @Test public void impedeReusoDaSessaoParaOutraNota() {
        ControlePopupCompensacao.validarNotaSessao(null, nota);
        ControlePopupCompensacao.validarNotaSessao(new BigDecimal("100.0"), nota);
        try {
            ControlePopupCompensacao.validarNotaSessao(new BigDecimal("200"), nota);
            fail("Outra nota nao pode herdar a supressao");
        } catch (IllegalStateException expected) { }
    }
    private Element pedido(String numero) {
        return new Element("requestBody").addContent(new Element("notas")
                .addContent(new Element("nota").setText(numero)));
    }
    private void rejeitar(Element pedido) {
        try {
            ControlePopupCompensacao.validarPedido(pedido, nota);
            fail("Contexto deveria ser recusado");
        } catch (IllegalStateException expected) { }
    }
}
