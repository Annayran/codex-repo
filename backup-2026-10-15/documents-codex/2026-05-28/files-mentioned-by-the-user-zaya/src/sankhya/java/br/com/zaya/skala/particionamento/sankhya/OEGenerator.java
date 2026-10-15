package br.com.zaya.skala.particionamento.sankhya;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.skala.particionamento.config.ParticionamentoConfig;
import br.com.zaya.skala.particionamento.model.Carreta;
import br.com.zaya.skala.particionamento.model.ItemCarreta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OEGenerator {
    private static final int CODTIPOPER_OE = 3108;
    private static final String SERIE_OE = "1";
    private static final double TOLERANCIA_CAIXA_FRACIONADA = 0.01;

    private final JapeWrapper cabecalhoNotaDao = JapeFactory.dao("CabecalhoNota");
    private final JapeWrapper itemNotaDao = JapeFactory.dao("ItemNota");

    public List<Integer> gerarOEs(int nuNotaOrigem, List<Carreta> carretas) throws Exception {
        if (CODTIPOPER_OE <= 0) {
            throw new IllegalStateException("Configure o CODTIPOPER_OE antes de gerar pedidos de OE.");
        }

        DynamicVO pedidoOrigem = buscarCabecalhoOrigem(nuNotaOrigem);
        TopOperacao topOE = buscarTopOperacao(CODTIPOPER_OE);
        List<Integer> nuNotasGeradas = new ArrayList<Integer>();

        for (Carreta carreta : carretas) {
            DynamicVO oe = criarCabecalhoOE(pedidoOrigem, nuNotaOrigem, carreta, topOE);
            BigDecimal nuNotaOE = oe.asBigDecimal("NUNOTA");

            excluirVariacaoExistente(nuNotaOE);

            int sequencia = 1;
            for (ItemCarreta item : carreta.getItens()) {
                BigDecimal qtdNeg = criarItemOE(nuNotaOE, item, sequencia, topOE);
                criarVariacao(nuNotaOE, sequencia, nuNotaOrigem, item.getSequenciaOrigem(), qtdNeg);
                sequencia++;
            }

            nuNotasGeradas.add(nuNotaOE.intValue());
        }

        return nuNotasGeradas;
    }

    private DynamicVO buscarCabecalhoOrigem(int nuNotaOrigem) throws Exception {
        DynamicVO origem = cabecalhoNotaDao.findByPK(BigDecimal.valueOf(nuNotaOrigem));
        if (origem == null) {
            throw new IllegalStateException("Pedido de origem nao encontrado: " + nuNotaOrigem);
        }
        return origem;
    }

    private DynamicVO criarCabecalhoOE(DynamicVO origem, int nuNotaOrigem, Carreta carreta, TopOperacao topOE) throws Exception {
        br.com.sankhya.jape.wrapper.fluid.FluidCreateVO cabecalho = cabecalhoNotaDao.create()
                .set("NUMNOTA", BigDecimal.ZERO)
                .set("CODEMP", origem.asBigDecimal("CODEMP"))
                .set("CODPARC", origem.asBigDecimal("CODPARC"))
                .set("CODTIPOPER", BigDecimal.valueOf(CODTIPOPER_OE))
                .set("DHTIPOPER", topOE.getDhAlter())
                .set("SERIENOTA", SERIE_OE)
                .set("DTNEG", origem.asTimestamp("DTNEG"))
                .set("DTENTSAI", origem.asTimestamp("DTENTSAI"))
                .set("CODNAT", origem.asBigDecimal("CODNAT"))
                .set("CODCENCUS", origem.asBigDecimal("CODCENCUS"))
                .set("CODVEND", origem.asBigDecimal("CODVEND"))
                .set("CODTIPVENDA", origem.asBigDecimal("CODTIPVENDA"))
                .set("DHTIPVENDA", origem.asTimestamp("DHTIPVENDA"))
                .set("TIPMOV", topOE.getTipMov())
                .set(ParticionamentoConfig.CAMPO_PARTICIONADO, ParticionamentoConfig.VALOR_SIM)
                .set("OBSERVACAO", montarObservacao(origem.asString("OBSERVACAO"), nuNotaOrigem, carreta));

        if (carreta.getDhAgendamento() != null) {
            cabecalho.set("AD_DHAGENDAMENTO", carreta.getDhAgendamento());
        }

        return cabecalho.save();
    }

    private TopOperacao buscarTopOperacao(int codTipOper) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfFacade.getJdbcWrapper();
            jdbc.openSession();

            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT DHALTER, TIPMOV, ATUALEST, ATUALESTMP ");
            sql.appendSql("FROM TGFTOP ");
            sql.appendSql("WHERE CODTIPOPER = :CODTIPOPER ");
            sql.appendSql("  AND DHALTER = ( ");
            sql.appendSql("      SELECT MAX(TOP2.DHALTER) ");
            sql.appendSql("      FROM TGFTOP TOP2 ");
            sql.appendSql("      WHERE TOP2.CODTIPOPER = :CODTIPOPER ");
            sql.appendSql("  ) ");
            sql.setNamedParameter("CODTIPOPER", BigDecimal.valueOf(codTipOper));

            rs = sql.executeQuery();
            if (rs.next()) {
                Timestamp dhAlter = rs.getTimestamp("DHALTER");
                String tipMov = rs.getString("TIPMOV");
                String atualEst = rs.getString("ATUALEST");
                BigDecimal atualEstMp = rs.getBigDecimal("ATUALESTMP");
                if (dhAlter != null && tipMov != null && !tipMov.trim().isEmpty()) {
                    return new TopOperacao(dhAlter, tipMov, atualEst, atualEstMp);
                }
            }

            throw new IllegalStateException("TOP de OE nao encontrada: " + codTipOper);
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            if (jdbc != null) {
                JdbcWrapper.closeSession(jdbc);
            }
        }
    }

    private static class TopOperacao {
        private final Timestamp dhAlter;
        private final String tipMov;
        private final String atualEst;
        private final BigDecimal atualEstMp;

        private TopOperacao(Timestamp dhAlter, String tipMov, String atualEst, BigDecimal atualEstMp) {
            this.dhAlter = dhAlter;
            this.tipMov = tipMov;
            this.atualEst = atualEst;
            this.atualEstMp = atualEstMp;
        }

        private Timestamp getDhAlter() {
            return dhAlter;
        }

        private String getTipMov() {
            return tipMov;
        }

        private String getAtualEst() {
            return atualEst;
        }

        private BigDecimal getAtualEstMp() {
            return atualEstMp;
        }
    }

    private BigDecimal criarItemOE(BigDecimal nuNotaOE, ItemCarreta item, int sequencia, TopOperacao topOE) throws Exception {
        BigDecimal qtdNeg = quantidadeNegociada(item, sequencia);
        BigDecimal vlrUnit = BigDecimal.valueOf(item.getVlrUnit());
        BigDecimal qtdCaixa = inteiro(item.getQtdCaixa());
        String reserva = reservaPelaTop(topOE);
        BigDecimal atualEstoque = atualEstoquePelaTop(topOE, item, reserva);

        itemNotaDao.create()
                .set("NUNOTA", nuNotaOE)
                .set("SEQUENCIA", inteiro(sequencia))
                .set("CODPROD", inteiro(item.getCodProd()))
                .set("QTDNEG", qtdNeg)
                .set("CODLOCALORIG", inteiro(item.getCodLocalOrig()))
                .set("CONTROLE", emptyToDefault(item.getControle(), " "))
                .set("STATUSLOTE", emptyToDefault(item.getStatusLote(), "N"))
                .set("RESERVA", reserva)
                .set("ATUALESTOQUE", atualEstoque)
                .set("CODVOL", item.getCodVol())
                .set("USOPROD", item.getUsoProd())
                .set("VLRUNIT", vlrUnit)
                .set("VLRTOT", qtdNeg.multiply(vlrUnit))
                .set(ParticionamentoConfig.CAMPO_QTD_PALETE_ITEM_OE, BigDecimal.valueOf(item.getQtdAlocada()))
                .set(ParticionamentoConfig.CAMPO_QTD_CAIXA_ITEM_OE, qtdCaixa)
                .save();

        return qtdNeg;
    }

    private String reservaPelaTop(TopOperacao topOE) {
        return "R".equalsIgnoreCase(emptyToDefault(topOE.getAtualEst(), "N")) ? "S" : "N";
    }

    private BigDecimal atualEstoquePelaTop(TopOperacao topOE, ItemCarreta item, String reserva) {
        if ("N".equalsIgnoreCase(reserva) && "M".equalsIgnoreCase(emptyToDefault(item.getUsoProd(), ""))) {
            BigDecimal atualEstMp = topOE.getAtualEstMp();
            return atualEstMp == null ? BigDecimal.ZERO : atualEstMp;
        }

        String atualEst = emptyToDefault(topOE.getAtualEst(), "N");
        if ("E".equalsIgnoreCase(atualEst) || "R".equalsIgnoreCase(atualEst)) {
            return BigDecimal.ONE;
        }
        if ("B".equalsIgnoreCase(atualEst)) {
            return BigDecimal.ONE.negate();
        }
        return BigDecimal.ZERO;
    }

    private void excluirVariacaoExistente(BigDecimal nuNotaOE) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfFacade.getJdbcWrapper();
            jdbc.openSession();

            sql = new NativeSql(jdbc);
            sql.appendSql("DELETE FROM TGFVAR WHERE NUNOTA = :NUNOTA ");
            sql.setNamedParameter("NUNOTA", nuNotaOE);
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            if (jdbc != null) {
                JdbcWrapper.closeSession(jdbc);
            }
        }
    }

    private void criarVariacao(
            BigDecimal nuNotaOE,
            int sequenciaOE,
            int nuNotaOrigem,
            int sequenciaOrigem,
            BigDecimal qtdAtendida
    ) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfFacade.getJdbcWrapper();
            jdbc.openSession();

            definirAdiarAtualizacaoOrigem(jdbc, "S");
            sql = new NativeSql(jdbc);
            sql.appendSql("INSERT INTO TGFVAR (");
            sql.appendSql("NUNOTA, SEQUENCIA, NUNOTAORIG, SEQUENCIAORIG, QTDATENDIDA, STATUSNOTA");
            sql.appendSql(") VALUES (");
            sql.appendSql(":NUNOTA, :SEQUENCIA, :NUNOTAORIG, :SEQUENCIAORIG, :QTDATENDIDA, :STATUSNOTA");
            sql.appendSql(") ");
            sql.setNamedParameter("NUNOTA", nuNotaOE);
            sql.setNamedParameter("SEQUENCIA", inteiro(sequenciaOE));
            sql.setNamedParameter("NUNOTAORIG", inteiro(nuNotaOrigem));
            sql.setNamedParameter("SEQUENCIAORIG", inteiro(sequenciaOrigem));
            sql.setNamedParameter("QTDATENDIDA", qtdAtendida);
            sql.setNamedParameter("STATUSNOTA", "L");
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            if (jdbc != null) {
                try {
                    definirAdiarAtualizacaoOrigem(jdbc, "N");
                } catch (Exception ignored) {
                    // Reset defensivo da variavel de sessao.
                }
            }
            if (jdbc != null) {
                JdbcWrapper.closeSession(jdbc);
            }
        }
    }

    private void definirAdiarAtualizacaoOrigem(JdbcWrapper jdbc, String valor) throws Exception {
        NativeSql sql = null;
        try {
            sql = new NativeSql(jdbc);
            sql.appendSql("BEGIN VARIAVEIS_PKG.V_ADIARATUALIZACAOORIGEM := :VALOR; END; ");
            sql.setNamedParameter("VALOR", valor);
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
        }
    }

    private BigDecimal quantidadeNegociada(ItemCarreta item, int sequencia) {
        BigDecimal qtdNeg = BigDecimal.valueOf(item.getQtdUnidade());
        BigDecimal qtdInteira = qtdNeg.setScale(0, RoundingMode.HALF_UP);
        double diferenca = qtdNeg.subtract(qtdInteira).abs().doubleValue();
        if (diferenca <= TOLERANCIA_CAIXA_FRACIONADA) {
            return qtdInteira;
        }

        throw new IllegalStateException(
                "Quantidade em UN fracionada antes de gravar a OE. Produto " + item.getCodProd()
                        + ", sequencia OE " + sequencia
                        + ", quantidade calculada " + qtdNeg
                        + ". Ajuste a capacidade/regra para gerar apenas unidades fechadas."
        );
    }

    private BigDecimal inteiro(double value) {
        return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP);
    }

    private BigDecimal inteiro(int value) {
        return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP);
    }

    private BigDecimal inteiro(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value.trim()).setScale(0, RoundingMode.HALF_UP);
    }

    private String montarObservacao(String observacaoOrigem, int nuNotaOrigem, Carreta carreta) {
        String prefixo = "Carreta " + carreta.getNumero() + " - Pedido Origem: " + nuNotaOrigem;
        if (observacaoOrigem == null || observacaoOrigem.trim().isEmpty()) {
            return prefixo;
        }
        return prefixo + "\n" + observacaoOrigem;
    }

    private String emptyToDefault(String value, String defaultValue) {
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }

    private void fechar(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ignored) {
            // Fechamento defensivo.
        }
    }
}
