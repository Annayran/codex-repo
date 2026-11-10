package br.com.zaya.mahospitalar.genesis.event;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.zaya.mahospitalar.genesis.repository.CancelamentoGenesisRepository;

/** Register on TGFCAB: release integration references before header deletion. */
public class CancelamentoNotaGenesisEvent implements EventoProgramavelJava {
    protected void preparar(PersistenceEvent event) throws Exception {
        br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog log =
                new br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog("EVENTO_CANCELAMENTO", null);
        java.math.BigDecimal nota = ((DynamicVO) event.getVo()).asBigDecimal("NUNOTA");
        log.registro("nota="+nota+", evento="+getClass().getSimpleName());
        try {
            log.info("Preparando EXCFILA e desvinculando referencias locais; sem chamada remota.");
            new CancelamentoGenesisRepository().preparar(event.getJdbcWrapper(), nota);
            log.info("Preparacao concluida, sujeita ao commit do cancelamento.");
        } catch (Exception e) {
            log.info("Falha na preparacao: tipo="+e.getClass().getSimpleName()+", motivo="+e.getMessage());
            throw e;
        } finally { log.finalizar(); }
    }
    @Override public void beforeDelete(PersistenceEvent event) throws Exception { preparar(event); }
    @Override public void beforeInsert(PersistenceEvent event) throws Exception { }
    @Override public void beforeUpdate(PersistenceEvent event) throws Exception { }
    @Override public void afterInsert(PersistenceEvent event) throws Exception { }
    @Override public void afterUpdate(PersistenceEvent event) throws Exception { }
    @Override public void afterDelete(PersistenceEvent event) throws Exception { }
    @Override public void beforeCommit(TransactionContext context) throws Exception { }
}
