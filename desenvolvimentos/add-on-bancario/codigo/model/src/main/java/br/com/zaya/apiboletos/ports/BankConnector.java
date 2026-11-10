package br.com.zaya.apiboletos.ports;

import br.com.zaya.apiboletos.domain.Models.*;

public interface BankConnector
    extends RegistrationPort, InstructionPort, ChargeQueryPort, ReceiptQueryPort {
  String bank();

  void diagnose(Config config, Credentials credentials) throws Exception;
}
