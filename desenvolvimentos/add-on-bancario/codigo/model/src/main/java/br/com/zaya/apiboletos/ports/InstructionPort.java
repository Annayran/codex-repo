package br.com.zaya.apiboletos.ports;

import br.com.zaya.apiboletos.domain.Models.*;

public interface InstructionPort {
  Result instruct(Config config, Credentials credentials, Charge charge, Operation operation)
      throws Exception;
}
