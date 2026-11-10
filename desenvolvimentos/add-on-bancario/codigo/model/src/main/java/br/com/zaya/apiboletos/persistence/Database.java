package br.com.zaya.apiboletos.persistence;

import java.sql.*;

/** Work deve executar em transação curta; nenhum cliente HTTP pode ser chamado no escopo. */
public interface Database {
  interface Work<T> {
    T run(Connection c) throws Exception;
  }

  <T> T transaction(Work<T> work) throws Exception;
}
