package br.com.zaya.mahospitalar.genesis.util;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

public final class GenesisTransaction {
    private GenesisTransaction() { }
    public interface Work<T> { T execute(JdbcWrapper jdbc) throws Exception; }

    public static <T> T execute(final Work<T> work) throws Exception {
        final Object[] value = new Object[1];
        JapeSession.execWithAutonomousTX(() -> {
            JdbcWrapper jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            try {
                jdbc.openSession();
                value[0] = work.execute(jdbc);
                return null;
            } finally {
                JdbcWrapper.closeSession(jdbc);
            }
        });
        @SuppressWarnings("unchecked") T result = (T) value[0];
        return result;
    }
}
