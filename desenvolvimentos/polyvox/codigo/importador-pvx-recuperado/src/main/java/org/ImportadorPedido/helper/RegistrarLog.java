/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.com.sankhya.jape.EntityFacade
 *  br.com.sankhya.jape.vo.DynamicVO
 *  br.com.sankhya.jape.vo.EntityVO
 *  br.com.sankhya.modelcore.util.EntityFacadeFactory
 */
package org.ImportadorPedido.helper;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import org.ImportadorPedido.helper.ImportadorLogger;

public class RegistrarLog {
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private static final Logger LOGGER = Logger.getLogger(RegistrarLog.class.getName());

    public void registrarLog(DynamicVO item, BigDecimal nuNota, String status, String msgErro) {
        this.registrarLog(item.asString("PVX"), item.asBigDecimal("EMPRESA"), item.asBigDecimal("COD"), item.asBigDecimal("QTY"), item.asString("UNIT"), item.asBigDecimal("UNITPRICE"), item.asBigDecimal("TOTALPRICE"), nuNota, status, msgErro);
    }

    public void registrarLog(String pvx, BigDecimal codEmp, BigDecimal codProd, BigDecimal qty, String unit, BigDecimal unitPrice, BigDecimal totalPrice, BigDecimal nuNota, String status, String msgErro) {
        try {
            String mensagem;
            DynamicVO logVO = (DynamicVO)this.dwFacade.getDefaultValueObjectInstance("AD_LOGIMPPEDCP");
            logVO.setProperty("PVX", (Object)pvx);
            logVO.setProperty("CODEMP", (Object)codEmp);
            logVO.setProperty("CODPROD", (Object)codProd);
            logVO.setProperty("QTY", (Object)qty);
            logVO.setProperty("UNIT", (Object)unit);
            logVO.setProperty("UNITPRICE", (Object)unitPrice);
            logVO.setProperty("TOTALPRICE", (Object)totalPrice);
            logVO.setProperty("NUNOTA", (Object)nuNota);
            logVO.setProperty("STATUS_PROCESSO", (Object)status);
            String string = mensagem = msgErro == null ? "" : msgErro;
            if (mensagem.length() > 4000) {
                mensagem = mensagem.substring(0, 4000);
            }
            logVO.setProperty("MSG_ERRO", (Object)mensagem.toCharArray());
            logVO.setProperty("DT_LOG", (Object)Timestamp.valueOf(LocalDateTime.now()));
            this.dwFacade.createEntity("AD_LOGIMPPEDCP", (EntityVO)logVO);
            ImportadorLogger.info(LOGGER, "LOG_PERSISTIDO", "Registro operacional salvo na tabela de log", ImportadorLogger.ctx("pvx", pvx, "cod", codProd, "nunota", nuNota, "status", status));
        }
        catch (Exception e) {
            ImportadorLogger.error(LOGGER, "LOG_PERSISTENCIA_ERRO", "Falha ao persistir registro de log operacional", e, ImportadorLogger.ctx("pvx", pvx, "nunota", nuNota, "status", status));
        }
    }
}

