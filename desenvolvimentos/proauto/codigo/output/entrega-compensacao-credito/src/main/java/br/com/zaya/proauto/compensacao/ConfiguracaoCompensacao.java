package br.com.zaya.proauto.compensacao;

import br.com.sankhya.modelcore.util.MGECoreParameter;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public final class ConfiguracaoCompensacao {
    static final String FLAG_PARCEIRO = "AD_PRACCFLAG";
    final String flag = FLAG_PARCEIRO;
    final int dias;
    final BigDecimal topReceita;
    final BigDecimal topDespesa;
    final Set<Integer> tipos;

    private ConfiguracaoCompensacao() throws Exception {
        dias = RegraCompensacao.inteiro("PRACCDIAS", MGECoreParameter.getParameter("PRACCDIAS"), 0, 36500);
        tipos = Collections.singleton(RegraCompensacao.inteiro("TIPTITCREDCLI",
                MGECoreParameter.getParameter("com.tipo.titulo.comp.credito"), 1, Integer.MAX_VALUE));
        topReceita = top("TOPBAIRECCOMP", "com.compensacao.credito.baixa.top.receita");
        topDespesa = top("TOPBAIDESPCOMP", "com.compensacao.credito.baixa.top.despesa");
    }

    public static ConfiguracaoCompensacao carregar() throws Exception {
        return new ConfiguracaoCompensacao();
    }

    private static BigDecimal top(String nome, String chaveNativa) throws Exception {
        return BigDecimal.valueOf(RegraCompensacao.inteiro(nome,
                MGECoreParameter.getParameter(chaveNativa), 1, Integer.MAX_VALUE));
    }
}
