package br.com.zaya.apiboletos.application;

import br.com.zaya.apiboletos.ports.BankConnector;
import java.util.*;

public final class ConnectorRegistry {
  private final Map<String, BankConnector> connectors = new HashMap<String, BankConnector>();

  public ConnectorRegistry(BankConnector... values) {
    for (BankConnector c : values) {
      if (connectors.put(c.bank(), c) != null)
        throw new IllegalArgumentException("BANCO_DUPLICADO");
    }
  }

  public BankConnector get(String bank) {
    BankConnector c = connectors.get(bank);
    if (c == null) throw new IllegalArgumentException("BANCO_NAO_SUPORTADO");
    return c;
  }
}
