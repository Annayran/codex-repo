package br.com.zaya.belmicro.rateio.infraestrutura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Borrow the connection of the caller's authenticated Sankhya/Jape context.
 * Adapters close statements/results only. Connection, transaction and authorization
 * remain owned by the caller. No URL, credentials, pool or new connection is created.
 */
public interface ConexaoRateio {
    Connection obter() throws SQLException;
}
