package br.com.zaya.skala.particionamento.sankhya;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
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
      DynamicVO pedidoOrigem = this.buscarCabecalhoOrigem(nuNotaOrigem);
      TopOperacao topOE = this.buscarTopOperacao(3108);
      List<Integer> nuNotasGeradas = new ArrayList();

      for(Carreta carreta : carretas) {
         DynamicVO oe = this.criarCabecalhoOE(pedidoOrigem, nuNotaOrigem, carreta, topOE);
         BigDecimal nuNotaOE = oe.asBigDecimal("NUNOTA");
         this.excluirVariacaoExistente(nuNotaOE);
         int sequencia = 1;

         for(ItemCarreta item : carreta.getItens()) {
            DynamicVO itemVO = this.criarItemOE(nuNotaOE, item, sequencia, topOE);
            DynamicVO itemOrigemVO = this.buscarItemOrigem(nuNotaOrigem, item.getSequenciaOrigem());
            this.criaTGFVAR(itemOrigemVO, itemVO, itemVO.asBigDecimal("QTDNEG"));
            ++sequencia;
         }

         nuNotasGeradas.add(nuNotaOE.intValue());
      }

      return nuNotasGeradas;
   }

   private DynamicVO buscarCabecalhoOrigem(int nuNotaOrigem) throws Exception {
      DynamicVO origem = this.cabecalhoNotaDao.findByPK(new Object[]{BigDecimal.valueOf((long)nuNotaOrigem)});
      if (origem == null) {
         throw new IllegalStateException("Pedido de origem nao encontrado: " + nuNotaOrigem);
      } else {
         return origem;
      }
   }

   private DynamicVO criarCabecalhoOE(DynamicVO origem, int nuNotaOrigem, Carreta carreta, TopOperacao topOE) throws Exception {
      FluidCreateVO cabecalho = (FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)this.cabecalhoNotaDao.create().set("NUMNOTA", BigDecimal.ZERO)).set("CODEMP", origem.asBigDecimal("CODEMP"))).set("CODPARC", origem.asBigDecimal("CODPARC"))).set("NUMPEDIDO2", origem.asString("NUMPEDIDO2"))).set("CODTIPOPER", BigDecimal.valueOf(3108L))).set("DHTIPOPER", topOE.getDhAlter())).set("SERIENOTA", "1")).set("DTNEG", origem.asTimestamp("DTNEG"))).set("DTENTSAI", origem.asTimestamp("DTENTSAI"))).set("CODNAT", origem.asBigDecimal("CODNAT"))).set("CODCENCUS", origem.asBigDecimal("CODCENCUS"))).set("CODVEND", origem.asBigDecimal("CODVEND"))).set("CODTIPVENDA", origem.asBigDecimal("CODTIPVENDA"))).set("DHTIPVENDA", origem.asTimestamp("DHTIPVENDA"))).set("TIPMOV", topOE.getTipMov())).set("AD_PARTICIONADO", "S")).set("OBSERVACAO", this.montarObservacao(origem.asString("OBSERVACAO"), nuNotaOrigem, carreta));
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
         sql.setNamedParameter("CODTIPOPER", BigDecimal.valueOf((long)codTipOper));
         rs = sql.executeQuery();
         if (rs.next()) {
            Timestamp dhAlter = rs.getTimestamp("DHALTER");
            String tipMov = rs.getString("TIPMOV");
            String atualEst = rs.getString("ATUALEST");
            BigDecimal atualEstMp = rs.getBigDecimal("ATUALESTMP");
            if (dhAlter != null && tipMov != null && !tipMov.trim().isEmpty()) {
               TopOperacao var10 = new TopOperacao(dhAlter, tipMov, atualEst, atualEstMp);
               return var10;
            }
         }

         throw new IllegalStateException("TOP de OE nao encontrada: " + codTipOper);
      } finally {
         this.fechar(rs);
         NativeSql.releaseResources(sql);
         if (jdbc != null) {
            JdbcWrapper.closeSession(jdbc);
         }

      }
   }

   private DynamicVO buscarItemOrigem(int nuNotaOrigem, int sequenciaOrigem) throws Exception {
      DynamicVO itemOrigemVO = this.itemNotaDao.findByPK(new Object[]{BigDecimal.valueOf((long)nuNotaOrigem), this.inteiro(sequenciaOrigem)});
      if (itemOrigemVO == null) {
         throw new IllegalStateException("Item de origem nao encontrado. Pedido " + nuNotaOrigem + ", sequencia " + sequenciaOrigem + ".");
      } else {
         return itemOrigemVO;
      }
   }

   private DynamicVO criarItemOE(BigDecimal nuNotaOE, ItemCarreta item, int sequencia, TopOperacao topOE) throws Exception {
      BigDecimal qtdNeg = this.quantidadeNegociada(item, sequencia);
      BigDecimal vlrUnit = BigDecimal.valueOf(item.getVlrUnit());
      BigDecimal qtdCaixa = item.getQtdCaixaExata().setScale(0, RoundingMode.UNNECESSARY);
      String reserva = this.reservaPelaTop(topOE);
      BigDecimal atualEstoque = this.atualEstoquePelaTop(topOE, item, reserva);
      return ((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)this.itemNotaDao.create().set("NUNOTA", nuNotaOE)).set("SEQUENCIA", this.inteiro(sequencia))).set("CODPROD", this.inteiro(item.getCodProd()))).set("QTDNEG", qtdNeg)).set("CODLOCALORIG", this.inteiro(item.getCodLocalOrig()))).set("CONTROLE", this.emptyToDefault(item.getControle(), " "))).set("STATUSLOTE", this.emptyToDefault(item.getStatusLote(), "N"))).set("RESERVA", reserva)).set("ATUALESTOQUE", atualEstoque)).set("CODVOL", item.getCodVol())).set("USOPROD", item.getUsoProd())).set("VLRUNIT", vlrUnit)).set("VLRTOT", qtdNeg.multiply(vlrUnit))).set("AD_QTDPALETES", BigDecimal.valueOf(item.getQtdAlocada()))).set("AD_QTDCAIXA", qtdCaixa)).save();
   }

   private String reservaPelaTop(TopOperacao topOE) {
      return "R".equalsIgnoreCase(this.emptyToDefault(topOE.getAtualEst(), "N")) ? "S" : "N";
   }

   private BigDecimal atualEstoquePelaTop(TopOperacao topOE, ItemCarreta item, String reserva) {
      if ("N".equalsIgnoreCase(reserva) && "M".equalsIgnoreCase(this.emptyToDefault(item.getUsoProd(), ""))) {
         BigDecimal atualEstMp = topOE.getAtualEstMp();
         return atualEstMp == null ? BigDecimal.ZERO : atualEstMp;
      } else {
         String atualEst = this.emptyToDefault(topOE.getAtualEst(), "N");
         if (!"E".equalsIgnoreCase(atualEst) && !"R".equalsIgnoreCase(atualEst)) {
            return "B".equalsIgnoreCase(atualEst) ? BigDecimal.ONE.negate() : BigDecimal.ZERO;
         } else {
            return BigDecimal.ONE;
         }
      }
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

   public void criaTGFVAR(DynamicVO itemOrigemVO, DynamicVO itemVO, BigDecimal qtd) throws Exception {
      JapeWrapper variosPedidoDAO = JapeFactory.dao("CompraVendavariosPedido");
      ((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)variosPedidoDAO.create().set("NUNOTA", itemVO.asBigDecimal("NUNOTA"))).set("NUNOTAORIG", itemOrigemVO.asBigDecimal("NUNOTA"))).set("SEQUENCIA", itemVO.asBigDecimal("SEQUENCIA"))).set("SEQUENCIAORIG", itemOrigemVO.asBigDecimal("SEQUENCIA"))).set("QTDATENDIDA", qtd)).save();
   }

   private BigDecimal quantidadeNegociada(ItemCarreta item, int sequencia) {
      BigDecimal qtdNeg = item.getQtdUnidadeExata();
      BigDecimal qtdInteira = qtdNeg.setScale(0, RoundingMode.HALF_UP);
      double diferenca = qtdNeg.subtract(qtdInteira).abs().doubleValue();
      if (diferenca <= 0.01) {
         return qtdInteira;
      } else {
         throw new IllegalStateException("Quantidade em UN fracionada antes de gravar a OE. Produto " + item.getCodProd() + ", sequencia OE " + sequencia + ", quantidade calculada " + qtdNeg + ". Ajuste a capacidade/regra para gerar apenas unidades fechadas.");
      }
   }

   private BigDecimal inteiro(double value) {
      return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP);
   }

   private BigDecimal inteiro(int value) {
      return BigDecimal.valueOf((long)value).setScale(0, RoundingMode.HALF_UP);
   }

   private BigDecimal inteiro(String value) {
      return value != null && !value.trim().isEmpty() ? (new BigDecimal(value.trim())).setScale(0, RoundingMode.HALF_UP) : BigDecimal.ZERO;
   }

   private String montarObservacao(String observacaoOrigem, int nuNotaOrigem, Carreta carreta) {
      String prefixo = "Carreta " + carreta.getNumero() + " - Pedido Origem: " + nuNotaOrigem;
      return observacaoOrigem != null && !observacaoOrigem.trim().isEmpty() ? prefixo + "\n" + observacaoOrigem : prefixo;
   }

   private String emptyToDefault(String value, String defaultValue) {
      return value != null && !value.trim().isEmpty() ? value : defaultValue;
   }

   private void fechar(ResultSet rs) {
      try {
         if (rs != null) {
            rs.close();
         }
      } catch (Exception var3) {
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
         return this.dhAlter;
      }

      private String getTipMov() {
         return this.tipMov;
      }

      private String getAtualEst() {
         return this.atualEst;
      }

      private BigDecimal getAtualEstMp() {
         return this.atualEstMp;
      }
   }
}
