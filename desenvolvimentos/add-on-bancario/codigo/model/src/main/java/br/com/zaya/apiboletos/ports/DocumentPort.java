package br.com.zaya.apiboletos.ports;

import br.com.zaya.apiboletos.domain.Models.*;

public interface DocumentPort {
  byte[] pdf(Charge charge) throws Exception;
}
