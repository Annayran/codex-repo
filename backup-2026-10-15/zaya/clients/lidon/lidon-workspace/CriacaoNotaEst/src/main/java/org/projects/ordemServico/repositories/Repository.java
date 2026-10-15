//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.ordemServico.repositories;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.CentralFaturamento;
import br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper;
import com.sankhya.util.BigDecimalUtil;
import java.math.BigDecimal;
import java.util.List;

public class Repository {
    private static final String LOG_PREFIX = "[NotaMovInterna][Repository] ";

    public Repository() {
    }

    public static BigDecimal buscarCodCenCusPadUsuario(BigDecimal codUsu) {
        try {
            return NativeSql.getBigDecimal("CODCENCUSPAD", "TSIUSU", "CODUSU = ?", new Object[]{codUsu});
        } catch (Exception e) {
            System.out.println("[NotaMovInterna][Repository] Erro ao buscar CODCENCUSPAD para CODUSU=" + codUsu + ": " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getTipoNegociacaoGarantia(BigDecimal codemp) throws Exception {
        return BigDecimalUtil.getValueOrZero(NativeSql.getBigDecimal("AD_CODTIPVENDA", "TGFEMP", "CODEMP = ? ", new Object[]{codemp}));
    }

    public static String getTipoOs(BigDecimal numOs) throws Exception {
        return NativeSql.getString("TIP.TIPOOS", "AD_TCSOSE OSE INNER JOIN AD_TIPOORDEMSERVICO TIP ON TIP.CODTIPODEOS = OSE.CODTIPODEOS ", "OSE.NUMOS  = ? ", new Object[]{numOs});
    }

    public void confirmarELiberarMovInterna(BigDecimal nunotaInt) throws Exception {
        if (nunotaInt == null) {
            System.out.println("[NotaMovInterna][Repository] confirmarELiberarMovInterna - nunotaInt nula, operacao ignorada");
        } else {
            System.out.println("[NotaMovInterna][Repository] confirmarELiberarMovInterna - inicio para NUNOTAINT=" + nunotaInt);

            try {
                AuthenticationInfo auth = AuthenticationInfo.getCurrent();
                BarramentoRegra barramento = BarramentoRegra.build(CentralFaturamento.class, "regrasConfirmacaoSilenciosa.xml", auth);
                PrePersistEntityState persistentConf = ConfirmacaoNotaHelper.confirmarNota(nunotaInt, barramento);
                DynamicVO notaVO = persistentConf.getNewVO();
                String statusAtual = notaVO != null ? notaVO.asString("STATUSNOTA") : "?";
                System.out.println("[NotaMovInterna][Repository] confirmarELiberarMovInterna - STATUSNOTA apos confirmacao=" + statusAtual + ", NUNOTAINT=" + nunotaInt);
                if (!"L".equals(statusAtual)) {
                    StringBuilder erros = new StringBuilder();
                    if (!barramento.getErros().isEmpty()) {
                        for(Exception e : barramento.getErros()) {
                            if (erros.length() > 0) {
                                erros.append(" | ");
                            }

                            erros.append(e.getMessage());
                        }
                    }

                    if (erros.length() > 0) {
                        throw new Exception("Falha ao confirmar MOV. Interna NUNOTA=" + nunotaInt + ": " + erros);
                    }

                    System.out.println("[NotaMovInterna][Repository] Aviso: nota nao atingiu STATUSNOTA=L apos confirmacao, mas sem erros. NUNOTAINT=" + nunotaInt);
                }
            } catch (Exception e) {
                System.out.println("[NotaMovInterna][Repository] confirmarELiberarMovInterna - erro na confirmacao da nota. Seguindo para atualizar pendencia dos itens via JAPE. NUNOTAINT=" + nunotaInt + " -> " + e.getMessage());
            }

            int qtdAtualizada = this.atualizarPendenteItensViaJape(nunotaInt);
            System.out.println("[NotaMovInterna][Repository] confirmarELiberarMovInterna - concluido para NUNOTAINT=" + nunotaInt + ", itens atualizados=" + qtdAtualizada);
        }
    }

    public int atualizarItensMovInternaComoNaoPendentes(BigDecimal nunotaInt) throws Exception {
        if (nunotaInt == null || nunotaInt.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(LOG_PREFIX + "atualizarItensMovInternaComoNaoPendentes - NUNOTAINT invalida: " + nunotaInt);
            return 0;
        }

        int atualizados = this.atualizarPendenteItensViaJape(nunotaInt);
        System.out.println(LOG_PREFIX + "atualizarItensMovInternaComoNaoPendentes - fim NUNOTAINT=" + nunotaInt + ", itens atualizados=" + atualizados);
        return atualizados;
    }

    private int atualizarPendenteItensViaJape(BigDecimal nunotaInt) throws Exception {
        JapeSession.SessionHandle hnd = null;

        try {
            hnd = JapeSession.open();
            List<DynamicVO> itensNota = (List)JapeFactory.dao("ItemNota").find("this.NUNOTA = ?", new Object[]{nunotaInt});
            if (itensNota != null && !itensNota.isEmpty()) {
                int atualizados = 0;

                for(DynamicVO itemVO : itensNota) {
                    BigDecimal sequencia = itemVO.asBigDecimal("SEQUENCIA");
                    ((FluidUpdateVO)JapeFactory.dao("ItemNota").prepareToUpdateByPK(new Object[]{nunotaInt, sequencia}).set("PENDENTE", "N")).update();
                    ++atualizados;
                }

                System.out.println("[NotaMovInterna][Repository] atualizarPendenteItensViaJape - itens atualizados com PENDENTE=N: " + atualizados + ", NUNOTA=" + nunotaInt);
                return atualizados;
            }

            System.out.println("[NotaMovInterna][Repository] atualizarPendenteItensViaJape - nenhum item encontrado para NUNOTA=" + nunotaInt);
            return 0;
        } finally {
            JapeSession.close(hnd);
        }

    }

    /** @deprecated */
    @Deprecated
    public void atualizarMovInternaComoPendente(BigDecimal nunotaInt) throws Exception {
        System.out.println("[NotaMovInterna][Repository] atualizarMovInternaComoPendente chamado (deprecated) - redirecionando para confirmarELiberarMovInterna. NUNOTAINT=" + nunotaInt);
        this.confirmarELiberarMovInterna(nunotaInt);
    }
}
