package br.com.zaya.apiboletos.ports;

import br.com.zaya.apiboletos.domain.Models.*;

public interface FinancialPort {
  Title title(long nuFin) throws Exception;

  void settle(Charge charge, Payment payment) throws Exception;

  void persistIdentity(Charge charge) throws Exception;

  String deliver(Charge charge, byte[] pdf) throws Exception;
}
