package br.com.zaya.apiboletos.sankhya;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.apiboletos.persistence.Database;

public final class SankhyaDatabase implements Database {
  public static <T> T current(Work<T> work) throws Exception {
    JdbcWrapper jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
    try {
      jdbc.openSession();
      return work.run(jdbc.getConnection());
    } finally {
      JdbcWrapper.closeSession(jdbc);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T transaction(final Work<T> work) throws Exception {
    JapeSession.SessionHandle handle = JapeSession.open();
    try {
      return (T)
          JapeSession.execWithAutonomousTX(
              new JapeSession.NewTXBody() {
                public Object run() throws Exception {
                  return current(work);
                }
              });
    } finally {
      JapeSession.close(handle);
    }
  }
}
