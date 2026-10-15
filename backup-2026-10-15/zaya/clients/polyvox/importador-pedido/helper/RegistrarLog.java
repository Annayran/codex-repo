package org.ImportadorPedido.helper;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class RegistrarLog {
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private static final Logger LOGGER = Logger.getLogger(RegistrarLog.class.getName());

    public void registrarLog(DynamicVO item, BigDecimal nuNota, String status, String msgErro) {
        registrarLog(
                item.asString("PVX"),
                item.asBigDecimal("EMPRESA"),
                item.asBigDecimal("COD"),
                item.asBigDecimal("QTY"),
                item.asString("UNIT"),
                item.asBigDecimal("UNITPRICE"),
                item.asBigDecimal("TOTALPRICE"),
                nuNota,
                status,
                msgErro
        );
    }

    public void registrarLog(
            String pvx,
            BigDecimal codEmp,
            BigDecimal codProd,
            BigDecimal qty,
            String unit,
            BigDecimal unitPrice,
            BigDecimal totalPrice,
            BigDecimal nuNota,
            String status,
            String msgErro
    ) {
        try {
            DynamicVO logVO = (DynamicVO) dwFacade.getDefaultValueObjectInstance("AD_LOGIMPPEDCP");

            logVO.setProperty("PVX", pvx);
            logVO.setProperty("CODEMP", codEmp);
            logVO.setProperty("CODPROD", codProd);
            logVO.setProperty("QTY", qty);
            logVO.setProperty("UNIT", unit);
            logVO.setProperty("UNITPRICE", unitPrice);
            logVO.setProperty("TOTALPRICE", totalPrice);
            logVO.setProperty("NUNOTA", nuNota);
            logVO.setProperty("STATUS_PROCESSO", status);

            String mensagem = msgErro == null ? "" : msgErro;
            if (mensagem.length() > 4000) {
                mensagem = mensagem.substring(0, 4000);
            }
            logVO.setProperty("MSG_ERRO", mensagem.toCharArray());
            logVO.setProperty("DT_LOG", java.sql.Timestamp.valueOf(LocalDateTime.now()));

            dwFacade.createEntity("AD_LOGIMPPEDCP", (EntityVO) logVO);

            ImportadorLogger.info(
                    LOGGER,
                    "LOG_PERSISTIDO",
                    "Registro operacional salvo na tabela de log",
                    ImportadorLogger.ctx(
                            "pvx", pvx,
                            "cod", codProd,
                            "nunota", nuNota,
                            "status", status
                    )
            );
        } catch (Exception e) {
            ImportadorLogger.error(
                    LOGGER,
                    "LOG_PERSISTENCIA_ERRO",
                    "Falha ao persistir registro de log operacional",
                    e,
                    ImportadorLogger.ctx(
                            "pvx", pvx,
                            "nunota", nuNota,
                            "status", status
                    )
            );
        }
    }
}
