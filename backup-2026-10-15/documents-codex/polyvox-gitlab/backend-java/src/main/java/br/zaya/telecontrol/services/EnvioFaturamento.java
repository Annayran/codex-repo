package br.zaya.telecontrol.services;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.zaya.telecontrol.models.Faturamento;
import br.zaya.telecontrol.models.FaturamentoGet;
import br.zaya.telecontrol.models.FaturamentoItem;
import br.zaya.telecontrol.parsers.ParseFaturamento;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.infrastructure.http.Api;
import br.zaya.telecontrol.shared.utils.LogUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EnvioFaturamento {

    public static void setFaturamento(BigDecimal pedidoSelecionado) throws Exception {
        LogUtil.SYNC = "Faturamento";
        LogUtil.REGISTRO = String.valueOf(pedidoSelecionado);
        JapeSession.SessionHandle hnd = null;
        JdbcWrapper jdbc = null;

        try {
            hnd = JapeSession.open();
            EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfEntityFacade.getJdbcWrapper();
            jdbc.openSession();

            Faturamento fat = null;
            NativeSql sql = new NativeSql(jdbc);
            sql.appendSql(" SELECT PED.PEDIDO, PED.NUNOTA PEDNUNOTA, NF.NUNOTA, ITE.SEQUENCIA, ITE.CODPROD, VAR.QTDATENDIDA, ORIG.AD_TCLPEDITE");
            sql.appendSql(" FROM AD_TCLPED PED, TGFVAR VAR, TGFCAB NF, TGFITE ITE, TGFITE ORIG");
            sql.appendSql(" WHERE VAR.NUNOTAORIG = PED.NUNOTA");
            sql.appendSql(" AND ORIG.NUNOTA = VAR.NUNOTAORIG");
            sql.appendSql(" AND ORIG.SEQUENCIA = VAR.SEQUENCIAORIG");
            sql.appendSql(" AND ITE.NUNOTA = VAR.NUNOTA");
            sql.appendSql(" AND ITE.SEQUENCIA = VAR.SEQUENCIA ");
            sql.appendSql(" AND NF.NUNOTA = ITE.NUNOTA");
            sql.appendSql(" AND NF.STATUSNOTA = 'L' ");
            sql.appendSql(" AND NF.STATUSNFE = 'A' ");
            sql.appendSql(" AND ORIG.AD_TCLPEDITE IS NOT NULL ");
            sql.appendSql(" AND PED.PEDIDO = " + pedidoSelecionado);
            sql.appendSql(" ORDER BY NF.NUNOTA ");
            
            ResultSet rset = sql.executeQuery();

            while (rset.next()) {
                BigDecimal pedido = rset.getBigDecimal("PEDIDO");
                BigDecimal nfNunota = rset.getBigDecimal("NUNOTA");
                BigDecimal seq = rset.getBigDecimal("SEQUENCIA");
                BigDecimal codprod = rset.getBigDecimal("CODPROD");
                BigDecimal qtdAtendida = rset.getBigDecimal("QTDATENDIDA");
                String pedItem = rset.getString("AD_TCLPEDITE");

                JapeWrapper notaDAO = JapeFactory.dao("CabecalhoNota");
                DynamicVO notaVO = notaDAO.findByPK(new Object[]{nfNunota});
                JapeWrapper itemDAO = JapeFactory.dao("ItemNota");
                DynamicVO itemVO = itemDAO.findByPK(new Object[]{nfNunota, seq});

                if (fat == null || !fat.notaFiscal.equals(notaVO.asBigDecimal("NUMNOTA").toString())) {
                    if (fat != null) {
                        postFaturamento(fat);
                    }

                    fat = new Faturamento();
                    fat.itens = new ArrayList<>();
                    JapeWrapper parceiroDAO = JapeFactory.dao("Parceiro");
                    DynamicVO parceiroVO = parceiroDAO.findByPK(new Object[]{notaVO.asBigDecimal("CODPARC")});
                    fat.posto = parceiroVO.asString("CGC_CPF");
                    fat.notaFiscal = notaVO.asBigDecimal("NUMNOTA").toString();
                    fat.serie = notaVO.asString("SERIENOTA");
                    fat.emissao = new SimpleDateFormat("yyyy-MM-dd").format(notaVO.asTimestamp("DTNEG"));
                    fat.totalNota = notaVO.asBigDecimalOrZero("VLRNOTA").toString();
                    fat.totalIpi = notaVO.asBigDecimalOrZero("VLRIPI").toString();
                    fat.totalIcms = notaVO.asBigDecimalOrZero("VLRICMS").toString();
                    fat.cfop = itemVO.asBigDecimalOrZero("CODCFO").toString();
                    fat.transportadora = getCnpjTransportadora(notaVO.asBigDecimal("CODPARCTRANSP"));
                    fat.valorFrete = notaVO.asBigDecimalOrZero("VLRFRETE").toString();
                }

                BigDecimal baseIcms = calcularImposto(itemVO, qtdAtendida, "BASEICMS");
                BigDecimal valorIcms = calcularImposto(itemVO, qtdAtendida, "VLRICMS");
                BigDecimal baseIpi = calcularImposto(itemVO, qtdAtendida, "BASEIPI");
                BigDecimal valorIpi = calcularImposto(itemVO, qtdAtendida, "VLRIPI");
                BigDecimal baseSt = calcularImposto(itemVO, qtdAtendida, "BASESUBSTIT");
                BigDecimal valorSt = calcularImposto(itemVO, qtdAtendida, "VLRSUBST");
                
                JapeWrapper produtoDAO = JapeFactory.dao("AD_TCLPCA");
                DynamicVO produtoVO = produtoDAO.findByPK(new Object[]{codprod});
                String referencia = (produtoVO != null) ? produtoVO.asString("REFERENCIA") : "";

                FaturamentoItem fatItem = new FaturamentoItem();
                fatItem.pedido = pedido.toString();
                fatItem.pedidoItem = pedItem;
                fatItem.peca = referencia;
                fatItem.quantidade = qtdAtendida.toString();
                fatItem.preco = itemVO.asBigDecimalOrZero("VLRUNIT").toString();
                fatItem.baseIcms = baseIcms.toString();
                fatItem.valorIcms = valorIcms.toString();
                fatItem.aliquotaIcms = itemVO.asBigDecimalOrZero("ALIQICMS").toString();
                fatItem.baseIpi = baseIpi.toString();
                fatItem.valorIpi = valorIpi.toString();
                fatItem.aliquotaIpi = itemVO.asBigDecimalOrZero("ALIQIPI").toString();
                fatItem.baseSubsTributaria = baseSt.toString();
                fatItem.valorSubsTributaria = valorSt.toString();
                fatItem.pecaPedida = referencia;
                fat.itens.add(fatItem);
            }

            if (fat != null) {
                postFaturamento(fat);
            }

        } catch (Exception e) {
            LogUtil.saveLog(e);
            throw e;
        } finally {
            JdbcWrapper.closeSession(jdbc);
            JapeSession.close(hnd);
        }
    }

    public static void setFaturamento() throws Exception {
        LogUtil.SYNC = "Faturamento";
        JapeSession.SessionHandle hnd = null;
        JdbcWrapper jdbc = null;

        try {
            hnd = JapeSession.open();
            EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfEntityFacade.getJdbcWrapper();
            jdbc.openSession();
            
            Faturamento fat = null;
            NativeSql sql = new NativeSql(jdbc);
            sql.appendSql(" SELECT PED.PEDIDO, PED.NUNOTA PEDNUNOTA, NF.NUNOTA , ITE.SEQUENCIA, ITE.CODPROD, VAR.QTDATENDIDA, ORIG.AD_TCLPEDITE");
            sql.appendSql(" FROM AD_TCLPED PED, TGFVAR VAR, TGFCAB NF, TGFITE ITE, TGFITE ORIG");
            sql.appendSql(" WHERE VAR.NUNOTAORIG = PED.NUNOTA");
            sql.appendSql(" AND ORIG.NUNOTA = VAR.NUNOTAORIG");
            sql.appendSql(" AND ORIG.SEQUENCIA = VAR.SEQUENCIAORIG");
            sql.appendSql(" AND ITE.NUNOTA = VAR.NUNOTA");
            sql.appendSql(" AND ITE.SEQUENCIA = VAR.SEQUENCIA ");
            sql.appendSql(" AND NF.NUNOTA = ITE.NUNOTA");
            sql.appendSql(" AND NF.STATUSNOTA = 'L' ");
            sql.appendSql(" AND NF.STATUSNFE = 'A' ");
            sql.appendSql(" AND (PED.FATURADO IS NULL OR PED.FATURADO = 'N') ");
            sql.appendSql(" AND ORIG.AD_TCLPEDITE IS NOT NULL ");
            sql.appendSql(" ORDER BY NF.NUNOTA ");
            
            ResultSet rset = sql.executeQuery();

            while(rset.next()) {
                BigDecimal pedido = rset.getBigDecimal("PEDIDO");
                BigDecimal nfNunota = rset.getBigDecimal("NUNOTA");
                BigDecimal seq = rset.getBigDecimal("SEQUENCIA");
                BigDecimal codprod = rset.getBigDecimal("CODPROD");
                BigDecimal qtdAtendida = rset.getBigDecimal("QTDATENDIDA");
                String pedItem = rset.getString("AD_TCLPEDITE");
                
                LogUtil.REGISTRO = pedido.toString();
                
                JapeWrapper notaDAO = JapeFactory.dao("CabecalhoNota");
                DynamicVO notaVO = notaDAO.findByPK(new Object[]{nfNunota});
                JapeWrapper itemDAO = JapeFactory.dao("ItemNota");
                DynamicVO itemVO = itemDAO.findByPK(new Object[]{nfNunota, seq});
                
                if (fat == null || !fat.notaFiscal.equals(notaVO.asBigDecimal("NUMNOTA").toString())) {
                    if (fat != null) {
                        postFaturamento(fat);
                    }

                    fat = new Faturamento();
                    fat.itens = new ArrayList<>();
                    JapeWrapper parceiroDAO = JapeFactory.dao("Parceiro");
                    DynamicVO parceiroVO = parceiroDAO.findByPK(new Object[]{notaVO.asBigDecimal("CODPARC")});
                    fat.posto = parceiroVO.asString("CGC_CPF");
                    fat.notaFiscal = notaVO.asBigDecimal("NUMNOTA").toString();
                    fat.serie = notaVO.asString("SERIENOTA");
                    fat.emissao = (new SimpleDateFormat("yyyy-MM-dd")).format(notaVO.asTimestamp("DTNEG"));
                    fat.totalNota = notaVO.asBigDecimalOrZero("VLRNOTA").toString();
                    fat.totalIpi = notaVO.asBigDecimalOrZero("VLRIPI").toString();
                    fat.totalIcms = notaVO.asBigDecimalOrZero("VLRICMS").toString();
                    fat.cfop = itemVO.asBigDecimalOrZero("CODCFO").toString();
                    fat.transportadora = getCnpjTransportadora(notaVO.asBigDecimal("CODPARCTRANSP"));
                    fat.valorFrete = notaVO.asBigDecimalOrZero("VLRFRETE").toString();
                }

                BigDecimal baseIcms = calcularImposto(itemVO, qtdAtendida, "BASEICMS");
                BigDecimal valorIcms = calcularImposto(itemVO, qtdAtendida, "VLRICMS");
                BigDecimal baseIpi = calcularImposto(itemVO, qtdAtendida, "BASEIPI");
                BigDecimal valorIpi = calcularImposto(itemVO, qtdAtendida, "VLRIPI");
                BigDecimal baseSt = calcularImposto(itemVO, qtdAtendida, "BASESUBSTIT");
                BigDecimal valorSt = calcularImposto(itemVO, qtdAtendida, "VLRSUBST");
                
                JapeWrapper produtoDAO = JapeFactory.dao("AD_TCLPCA");
                DynamicVO produtoVO = produtoDAO.findByPK(new Object[]{codprod});
                String referencia = (produtoVO != null) ? produtoVO.asString("REFERENCIA") : "";
                
                FaturamentoItem fatItem = new FaturamentoItem();
                fatItem.pedido = pedido.toString();
                fatItem.pedidoItem = pedItem;
                fatItem.peca = referencia;
                fatItem.quantidade = qtdAtendida.toString();
                fatItem.preco = itemVO.asBigDecimalOrZero("VLRUNIT").toString();
                fatItem.baseIcms = baseIcms.toString();
                fatItem.valorIcms = valorIcms.toString();
                fatItem.aliquotaIcms = itemVO.asBigDecimalOrZero("ALIQICMS").toString();
                fatItem.baseIpi = baseIpi.toString();
                fatItem.valorIpi = valorIpi.toString();
                fatItem.aliquotaIpi = itemVO.asBigDecimalOrZero("ALIQIPI").toString();
                fatItem.baseSubsTributaria = baseSt.toString();
                fatItem.valorSubsTributaria = valorSt.toString();
                fatItem.pecaPedida = referencia;
                fat.itens.add(fatItem);
            }

            if (fat != null) {
                postFaturamento(fat);
            }
        } catch (Exception e) {
            LogUtil.saveLog(e);
            throw e;
        } finally {
            JdbcWrapper.closeSession(jdbc);
            JapeSession.close(hnd);
        }
    }

    public static void postFaturamento(Faturamento fat) throws Exception {
        ConfigTelecontrol serv = new ConfigTelecontrol();
        JapeWrapper pedidoDAO = JapeFactory.dao("AD_TCLPED");

        try {
            if (fat.itens == null || fat.itens.isEmpty()) {
                throw new Exception("Erro: Lista de itens no faturamento está vazia.");
            }

            LogUtil.REGISTRO = fat.itens.get(0).pedido;
            String url = serv.url + serv.epFaturamento;

            String content = ParseFaturamento.toJson(fat);
            LogUtil.REQUEST = content;

            String response = Api.post(url, content);
            
            FaturamentoGet fatGet = ParseFaturamento.getFaturamento(response);

            if (fatGet != null && fatGet.pedidos != null) {
                for (String pedido : fatGet.pedidos) {
                    FluidUpdateVO pedidoVO = pedidoDAO.prepareToUpdateByPK(new Object[]{new BigDecimal(pedido)});
                    pedidoVO.set("FATURADO", "S");
                    pedidoVO.update();
                }
            }

        } catch (Exception e) {
            if (e.getMessage().contains("faturados")) {
                for (FaturamentoItem item : fat.itens) {
                    FluidUpdateVO pedidoVO = pedidoDAO.prepareToUpdateByPK(new Object[]{new BigDecimal(item.pedido)});
                    pedidoVO.set("FATURADO", "S");
                    pedidoVO.update();
                }
            } else {
                LogUtil.saveLog(e);
            }
        }
    }

    private static BigDecimal calcularImposto(DynamicVO itemVO, BigDecimal qtdAtendida, String campo) throws Exception {
        try {
            BigDecimal qtdNeg = itemVO.asBigDecimal("QTDNEG");
            if (itemVO.asBigDecimal(campo) != null && qtdNeg != null && qtdNeg.compareTo(BigDecimal.ZERO) > 0) {
                return itemVO.asBigDecimalOrZero(campo)
                        .divide(qtdNeg, 4, RoundingMode.HALF_UP)
                        .multiply(qtdAtendida)
                        .setScale(2, RoundingMode.HALF_UP);
            }
            return BigDecimal.ZERO;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private static String getCnpjTransportadora(BigDecimal codParcTransp) throws Exception {
        if (codParcTransp == null) return "";
        JapeWrapper parDAO = JapeFactory.dao("Parceiro");
        DynamicVO parVO = parDAO.findByPK(new Object[]{codParcTransp});
        return (parVO != null) ? parVO.asString("CGC_CPF") : "";
    }
}
