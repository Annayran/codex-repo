package br.com.zaya.apiboletos.ports;

import br.com.zaya.apiboletos.domain.Models.*;

public interface ChargeQueryPort {
  Result query(Config config, Credentials credentials, Charge charge, String tracking)
      throws Exception;
}
