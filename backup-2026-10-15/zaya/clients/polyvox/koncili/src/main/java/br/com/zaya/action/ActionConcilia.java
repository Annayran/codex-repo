//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.zaya.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.zaya.controller.ConciliacaoErpController;
import br.com.zaya.service.ConciliacaoErpService;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ActionConcilia implements AcaoRotinaJava {
    private final ConciliacaoErpController controller = new ConciliacaoErpController();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        List<BigDecimal> idsSelecionados = extrairIdsSelecionados(contexto);
        if (idsSelecionados.isEmpty()) {
            if (contexto != null) {
                contexto.setMensagemRetorno("Nenhuma linha selecionada. Selecione ao menos uma pendencia.");
            }
            return;
        }

        StringBuilder mensagens = new StringBuilder();
        for (BigDecimal id : idsSelecionados) {
            ConciliacaoErpService.CompensacaoResultado resultado = controller.executarCompensacaoUnica(id);
            if (mensagens.length() > 0) {
                mensagens.append("\n");
            }
            mensagens.append(resultado.toMensagemRetorno());
        }

        if (contexto != null) {
            contexto.setMensagemRetorno(mensagens.toString());
        }
    }

    private static List<BigDecimal> extrairIdsSelecionados(ContextoAcao contexto) {
        List<BigDecimal> ids = new ArrayList<BigDecimal>();
        if (contexto == null) {
            return ids;
        }

        try {
            Method getLinhas = contexto.getClass().getMethod("getLinhas");
            Object linhasObj = getLinhas.invoke(contexto);
            if (!(linhasObj instanceof Object[])) {
                return ids;
            }

            Object[] linhas = (Object[]) linhasObj;
            for (Object linha : linhas) {
                BigDecimal id = extrairIdLinha(linha);
                if (id != null && id.signum() > 0) {
                    ids.add(id);
                }
            }
        } catch (Exception ignored) {
        }

        return ids;
    }

    private static BigDecimal extrairIdLinha(Object linha) {
        if (linha == null) {
            return null;
        }

        try {
            if (linha instanceof DynamicVO) {
                return ((DynamicVO) linha).asBigDecimal("ID");
            }

            Method getCampo = linha.getClass().getMethod("getCampo", String.class);
            Object valor = getCampo.invoke(linha, "ID");
            return toBigDecimal(valor);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static BigDecimal toBigDecimal(Object valor) {
        if (valor == null) {
            return null;
        }
        try {
            return new BigDecimal(valor.toString().trim());
        } catch (Exception e) {
            return null;
        }
    }
}
