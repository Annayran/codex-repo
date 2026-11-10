package br.com.zaya.skala.particionamento.sankhya;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.skala.particionamento.model.Carreta;
import br.com.zaya.skala.particionamento.model.ItemCarreta;
import br.com.zaya.skala.particionamento.model.PedidoInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;

public class RastreabilidadeRepository {
    private static final int CODTIPOPER_OE = 3108;
    private static final String STATUS_GERADO = "GERADO";

    public int registrarParticionamento(
            PedidoInfo pedidoInfo,
            double qtdePaletes,
            List<Carreta> carretas,
            List<Integer> nuNotasOE,
            int codUsuCriacao
    ) throws Exception {
        if (carretas.size() != nuNotasOE.size()) {
            throw new IllegalStateException("Quantidade de carretas diferente da quantidade de OEs geradas.");
        }

        bloquearTabelasDeRastro();

        int idPart = proximoIdPart();
        inserirCabecalho(idPart, pedidoInfo, qtdePaletes, carretas.size(), codUsuCriacao);

        for (int i = 0; i < carretas.size(); i++) {
            Carreta carreta = carretas.get(i);
            int nuNotaOE = nuNotasOE.get(i);
            int idPartOE = proximoId("AD_PARTOE", "IDPARTOE");

            inserirOE(idPart, idPartOE, pedidoInfo.getNuNota(), nuNotaOE, carreta);
            inserirItens(idPart, idPartOE, pedidoInfo.getNuNota(), nuNotaOE, carreta);
        }

        return idPart;
    }

    private void bloquearTabelasDeRastro() throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("LOCK TABLE AD_PARTCAB, AD_PARTOE, AD_PARTITEM IN EXCLUSIVE MODE ");
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private void inserirCabecalho(
            int idPart,
            PedidoInfo pedidoInfo,
            double qtdePaletes,
            int qtdOesGeradas,
            int codUsuCriacao
    ) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("INSERT INTO AD_PARTCAB (");
            sql.appendSql("IDPART, NUNOTA_ORIGEM, CODPARC, CODTIPOPER_ORIGEM, QTDE_PALETES, ");
            sql.appendSql("QTD_OES_GERADAS, STATUS, DHCRIACAO, CODUSUCRIACAO, OBSERVACAO");
            sql.appendSql(") VALUES (");
            sql.appendSql(":IDPART, :NUNOTA_ORIGEM, :CODPARC, :CODTIPOPER_ORIGEM, :QTDE_PALETES, ");
            sql.appendSql(":QTD_OES_GERADAS, :STATUS, SYSDATE, :CODUSUCRIACAO, :OBSERVACAO");
            sql.appendSql(") ");
            sql.setNamedParameter("IDPART", inteiro(idPart));
            sql.setNamedParameter("NUNOTA_ORIGEM", inteiro(pedidoInfo.getNuNota()));
            sql.setNamedParameter("CODPARC", inteiro(pedidoInfo.getCodParc()));
            sql.setNamedParameter("CODTIPOPER_ORIGEM", inteiro(pedidoInfo.getCodTipOper()));
            sql.setNamedParameter("QTDE_PALETES", inteiro(qtdePaletes));
            sql.setNamedParameter("QTD_OES_GERADAS", inteiro(qtdOesGeradas));
            sql.setNamedParameter("STATUS", STATUS_GERADO);
            sql.setNamedParameter("CODUSUCRIACAO", inteiro(codUsuCriacao));
            sql.setNamedParameter("OBSERVACAO", "Particionamento gerado automaticamente pela acao customizada.");
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private void inserirOE(int idPart, int idPartOE, int nuNotaOrigem, int nuNotaOE, Carreta carreta) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("INSERT INTO AD_PARTOE (");
            sql.appendSql("IDPART, IDPARTOE, NUNOTA_ORIGEM, NUNOTA_OE, CODTIPOPER_OE, ");
            sql.appendSql("TOTAL_PALETES, TOTAL_CAIXAS, STATUS, DHCRIACAO");
            sql.appendSql(") VALUES (");
            sql.appendSql(":IDPART, :IDPARTOE, :NUNOTA_ORIGEM, :NUNOTA_OE, :CODTIPOPER_OE, ");
            sql.appendSql(":TOTAL_PALETES, :TOTAL_CAIXAS, :STATUS, SYSDATE");
            sql.appendSql(") ");
            sql.setNamedParameter("IDPART", inteiro(idPart));
            sql.setNamedParameter("IDPARTOE", inteiro(idPartOE));
            sql.setNamedParameter("NUNOTA_ORIGEM", inteiro(nuNotaOrigem));
            sql.setNamedParameter("NUNOTA_OE", inteiro(nuNotaOE));
            sql.setNamedParameter("CODTIPOPER_OE", inteiro(CODTIPOPER_OE));
            sql.setNamedParameter("TOTAL_PALETES", inteiro(totalPaletes(carreta)));
            sql.setNamedParameter("TOTAL_CAIXAS", inteiro(totalCaixas(carreta)));
            sql.setNamedParameter("STATUS", STATUS_GERADO);
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private void inserirItens(int idPart, int idPartOE, int nuNotaOrigem, int nuNotaOE, Carreta carreta) throws Exception {
        int sequenciaOE = 1;
        for (ItemCarreta item : carreta.getItens()) {
            int idPartItem = proximoId("AD_PARTITEM", "IDPARTITEM");
            inserirItem(idPart, idPartOE, idPartItem, nuNotaOrigem, nuNotaOE, item, sequenciaOE);
            sequenciaOE++;
        }
    }

    private void inserirItem(
            int idPart,
            int idPartOE,
            int idPartItem,
            int nuNotaOrigem,
            int nuNotaOE,
            ItemCarreta item,
            int sequenciaOE
    ) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("INSERT INTO AD_PARTITEM (");
            sql.appendSql("IDPART, IDPARTOE, IDPARTITEM, NUNOTA_ORIGEM, NUNOTA_OE, CODPROD, ");
            sql.appendSql("QTD_ORIGEM, QTD_ALOCADA, QTD_PALETES, QTD_CAIXAS");
            sql.appendSql(") VALUES (");
            sql.appendSql(":IDPART, :IDPARTOE, :IDPARTITEM, :NUNOTA_ORIGEM, :NUNOTA_OE, :CODPROD, ");
            sql.appendSql(":QTD_ORIGEM, :QTD_ALOCADA, :QTD_PALETES, :QTD_CAIXAS");
            sql.appendSql(") ");
            sql.setNamedParameter("IDPART", inteiro(idPart));
            sql.setNamedParameter("IDPARTOE", inteiro(idPartOE));
            sql.setNamedParameter("IDPARTITEM", inteiro(idPartItem));
            sql.setNamedParameter("NUNOTA_ORIGEM", inteiro(nuNotaOrigem));
            sql.setNamedParameter("NUNOTA_OE", inteiro(nuNotaOE));
            sql.setNamedParameter("CODPROD", inteiro(item.getCodProd()));
            sql.setNamedParameter("QTD_ORIGEM", inteiro(item.getQtdOrigem()));
            sql.setNamedParameter("QTD_ALOCADA", inteiro(item.getQtdUnidade()));
            sql.setNamedParameter("QTD_PALETES", inteiro(item.getQtdAlocada()));
            sql.setNamedParameter("QTD_CAIXAS", inteiro(item.getQtdCaixa()));
            sql.executeUpdate();
        } finally {
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private int proximoId(String tabela, String campo) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT NVL(MAX(" + campo + "), 0) + 1 AS PROXIMO_ID FROM " + tabela);
            rs = sql.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("PROXIMO_ID").intValue();
            }
            return 1;
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private int proximoIdPart() throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            jdbc = abrirJdbc();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT MAX(IDPART) + 1 AS PROXIMO_ID ");
            sql.appendSql("FROM (");
            sql.appendSql("  SELECT NVL(MAX(IDPART), 0) AS IDPART FROM AD_PARTCAB ");
            sql.appendSql("  UNION ALL ");
            sql.appendSql("  SELECT NVL(MAX(IDPART), 0) AS IDPART FROM AD_PARTOE ");
            sql.appendSql("  UNION ALL ");
            sql.appendSql("  SELECT NVL(MAX(IDPART), 0) AS IDPART FROM AD_PARTITEM ");
            sql.appendSql(") ");
            rs = sql.executeQuery();
            if (rs.next() && rs.getBigDecimal("PROXIMO_ID") != null) {
                return rs.getBigDecimal("PROXIMO_ID").intValue();
            }
            return 1;
        } finally {
            fechar(rs);
            NativeSql.releaseResources(sql);
            fechar(jdbc);
        }
    }

    private double totalPaletes(Carreta carreta) {
        double total = 0.0;
        for (ItemCarreta item : carreta.getItens()) {
            total += item.getQtdAlocada();
        }
        return total;
    }

    private double totalCaixas(Carreta carreta) {
        double total = 0.0;
        for (ItemCarreta item : carreta.getItens()) {
            total += item.getQtdCaixa();
        }
        return total;
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

    private JdbcWrapper abrirJdbc() throws Exception {
        EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = dwfFacade.getJdbcWrapper();
        jdbc.openSession();
        return jdbc;
    }

    private void fechar(JdbcWrapper jdbc) {
        if (jdbc != null) {
            JdbcWrapper.closeSession(jdbc);
        }
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
