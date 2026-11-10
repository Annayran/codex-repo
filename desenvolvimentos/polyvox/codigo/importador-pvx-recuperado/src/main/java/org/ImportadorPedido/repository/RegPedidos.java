/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.com.sankhya.jape.wrapper.JapeFactory
 *  br.com.sankhya.jape.wrapper.JapeWrapper
 *  br.com.sankhya.jape.wrapper.fluid.FluidCreateVO
 */
package org.ImportadorPedido.repository;

import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.vo.DynamicVO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;
import org.ImportadorPedido.helper.DecimalImportHelper;
import org.ImportadorPedido.helper.ImportadorLogger;
import org.ImportadorPedido.models.DadosPlanilha;

public class RegPedidos {
    private static final Logger LOGGER = Logger.getLogger(RegPedidos.class.getName());
    private static final RoundingMode QTY_ROUNDING_MODE = RoundingMode.HALF_UP;

    public static void insereRegistos(DadosPlanilha dadosPlanilha, String pvx, BigDecimal id) throws Exception {
        RegPedidos.insereRegistos(dadosPlanilha, pvx, id, null, null);
    }

    public static void insereRegistos(DadosPlanilha dadosPlanilha, String pvx, BigDecimal id, BigDecimal idJson, Integer linhaNumero) throws Exception {
        RegPedidos.validarCamposObrigatorios(dadosPlanilha, pvx);
        BigDecimal qtyNormalizada;
        try {
            qtyNormalizada = DecimalImportHelper.validarQuantidadeInteira(dadosPlanilha.getQty());
        } catch (IllegalArgumentException e) {
            throw new Exception("PVX " + pvx + ", COD " + dadosPlanilha.getCod()
                    + ", JSON " + idJson + ": " + e.getMessage(), e);
        }
        BigDecimal unitPriceNormalizado = DecimalImportHelper.normalizarUnitPrice(dadosPlanilha.getUnitPrice());
        BigDecimal totalPriceNormalizado = DecimalImportHelper.normalizarTotalPrice(dadosPlanilha.getTotalPrice());
        try {
            JapeWrapper regPedidoDao = JapeFactory.dao((String)"AD_IMPREGPED");
            FluidCreateVO regPedidoVo = regPedidoDao.create();
            regPedidoVo.set("ID", (Object)id);
            regPedidoVo.set("PVX", (Object)pvx);
            regPedidoVo.set("PA", (Object)dadosPlanilha.getPa());
            regPedidoVo.set("REFERENCIA", (Object)dadosPlanilha.getRef());
            regPedidoVo.set("COD", (Object)dadosPlanilha.getCod());
            regPedidoVo.set("UNIT", (Object)dadosPlanilha.getUnit());
            regPedidoVo.set("UNITPRICE", (Object)unitPriceNormalizado);
            regPedidoVo.set("QTY", (Object)qtyNormalizada);
            regPedidoVo.set("TOTALPRICE", (Object)totalPriceNormalizado);
            regPedidoVo.set("EMPRESA", (Object)dadosPlanilha.getEmpresa());
            regPedidoVo.set("CODPARC", (Object)dadosPlanilha.getParceiro());
            DynamicVO salvo = regPedidoVo.save();
            if (qtyNormalizada.compareTo(salvo.asBigDecimalOrZero("QTY")) != 0
                    || unitPriceNormalizado.compareTo(salvo.asBigDecimalOrZero("UNITPRICE")) != 0
                    || (totalPriceNormalizado != null && totalPriceNormalizado.compareTo(salvo.asBigDecimalOrZero("TOTALPRICE")) != 0)) {
                throw new Exception("Precisao alterada ao gravar AD_IMPREGPED; conferir escala Oracle e metadata de QTY/UNITPRICE/TOTALPRICE");
            }
            ImportadorLogger.info(LOGGER, "REG_PEDIDO_SALVO", "Registro importado salvo em AD_IMPREGPED", ImportadorLogger.ctx("pvx", pvx, "empresa", dadosPlanilha.getEmpresa(), "codparc", dadosPlanilha.getParceiro(), "cod", dadosPlanilha.getCod(), "qty_original", dadosPlanilha.getQty(), "qty_gravada", qtyNormalizada, "unitprice_original", dadosPlanilha.getUnitPrice(), "unitprice_gravado", unitPriceNormalizado, "totalprice_original", dadosPlanilha.getTotalPrice(), "totalprice_gravado", totalPriceNormalizado, "id_json_origem", idJson, "linha_numero_origem", linhaNumero));
        }
        catch (Exception e) {
            ImportadorLogger.error(LOGGER, "REG_PEDIDO_ERRO", "Falha ao inserir registro importado", e, ImportadorLogger.ctx("pvx", pvx, "empresa", dadosPlanilha.getEmpresa(), "codparc", dadosPlanilha.getParceiro(), "cod", dadosPlanilha.getCod(), "id_json_origem", idJson, "linha_numero_origem", linhaNumero));
            throw new Exception("Erro ao inserir registro importado para PVX " + pvx + ": " + e.getMessage(), e);
        }
    }

    private static void validarCamposObrigatorios(DadosPlanilha dadosPlanilha, String pvx) throws Exception {
        boolean semReferencia;
        boolean semCod = dadosPlanilha.getCod() == null;
        boolean bl = semReferencia = dadosPlanilha.getRef() == null || dadosPlanilha.getRef().trim().isEmpty();
        if (semCod && semReferencia) {
            throw new Exception("COD e REFERENCIA do produto nao informados para PVX " + pvx);
        }
        if (dadosPlanilha.getQty() == null) {
            throw new Exception("QTY nao informada para PVX " + pvx + " e COD " + dadosPlanilha.getCod());
        }
        if (dadosPlanilha.getUnitPrice() == null) {
            throw new Exception("UNITPRICE nao informado para PVX " + pvx + " e COD " + dadosPlanilha.getCod());
        }
        if (dadosPlanilha.getEmpresa() == null || dadosPlanilha.getParceiro() == null) {
            throw new Exception("Empresa ou parceiro nao informado para PVX " + pvx + " e COD " + dadosPlanilha.getCod());
        }
    }

    private static BigDecimal normalizarQuantidadeParaEscalaZero(BigDecimal qty, String pvx, BigDecimal codProd) throws Exception {
        BigDecimal qtySemZeros = qty.stripTrailingZeros();
        if (qtySemZeros.scale() <= 0) {
            return qtySemZeros.setScale(0, RoundingMode.UNNECESSARY);
        }
        BigDecimal qtyAjustada = qty.setScale(0, QTY_ROUNDING_MODE);
        ImportadorLogger.warn(LOGGER, "REG_PEDIDO_QTY_AJUSTADA", "QTY fracionada ajustada para escala 0 do AD_IMPREGPED", ImportadorLogger.ctx("pvx", pvx, "cod", codProd, "qty_original", qty, "qty_ajustada", qtyAjustada, "rounding_mode", QTY_ROUNDING_MODE.name()));
        if (qtyAjustada.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("QTY ajustada ficou invalida para AD_IMPREGPED (PVX " + pvx + ", COD " + codProd + ", QTY " + qty + ")");
        }
        return qtyAjustada;
    }
}

