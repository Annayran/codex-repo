package br.com.zaya.proauto.filareserva.infrastructure.database;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.proauto.filareserva.application.ports.AtualizacaoProvisaoGateway;

import java.sql.CallableStatement;
public class OracleAtualizacaoProvisaoRepository implements AtualizacaoProvisaoGateway {
    private static final String CALL_ATUALIZAR_PROVISOES =
            "{call STP_ATUALIZA_PROV_FILA_PRA()}";

    @Override
    public int atualizarProvisoes() throws Exception {
        JdbcWrapper jdbc = null;
        CallableStatement statement = null;

        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
            statement = jdbc.getConnection().prepareCall(CALL_ATUALIZAR_PROVISOES);
            statement.execute();
            return 0;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } finally {
                JdbcWrapper.closeSession(jdbc);
            }
        }
    }
}
