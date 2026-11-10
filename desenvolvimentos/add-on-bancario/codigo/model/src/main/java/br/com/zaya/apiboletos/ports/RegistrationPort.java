package br.com.zaya.apiboletos.ports;

import br.com.zaya.apiboletos.domain.Models.*;

public interface RegistrationPort {
  Result register(Config config, Credentials credentials, Charge charge, String correlation)
      throws Exception;
}
