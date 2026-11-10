package br.com.zaya.apiboletos.ports;

import br.com.zaya.apiboletos.domain.Models.*;

public interface ReceiptQueryPort {
  java.util.List<Payment> receipts(Config config, Credentials credentials, Charge charge)
      throws Exception;
}
