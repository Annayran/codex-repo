package br.com.zaya.skala;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.zaya.skala.particionamento.config.ParticionamentoConfig;
import br.com.zaya.skala.particionamento.sankhya.DesfazerParticionamentoRepository;

import java.math.BigDecimal;

public class BloquearExclusaoOEParticionadaEvent implements EventoProgramavelJava {
    private final DesfazerParticionamentoRepository repository = new DesfazerParticionamentoRepository();

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {
        if ("S".equals(JapeSession.getProperty(ParticionamentoConfig.JAPE_PROPERTY_DESFAZENDO_PARTICIONAMENTO))) {
            return;
        }

        DynamicVO vo = (DynamicVO) event.getVo();
        String adParticionado = vo.asString(ParticionamentoConfig.CAMPO_PARTICIONADO);
        BigDecimal nuNota = vo.asBigDecimal("NUNOTA");
        if (ParticionamentoConfig.VALOR_SIM.equalsIgnoreCase(nullToEmpty(adParticionado))) {
            throw new IllegalStateException(
                    "Esta nota foi gerada ou marcada por particionamento. Utilize a acao \"Desfazer Particionamento\" para desfazer o processo completo."
            );
        }

        if (nuNota != null && repository.ehOEParticionadaAtiva(nuNota.intValue())) {
            throw new IllegalStateException(
                    "Esta OE foi gerada por particionamento. Utilize a acao \"Desfazer Particionamento\" para desfazer o processo completo."
            );
        }
    }

    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {
    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {
    }

    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {
    }

    @Override
    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    @Override
    public void beforeCommit(TransactionContext transactionContext) throws Exception {
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}
