package br.com.zaya.apiboletos.ports;

import br.com.zaya.apiboletos.domain.Models.Credentials;

public interface CredentialStore {
  boolean available();

  void save(String configId, Credentials credentials) throws Exception;

  Credentials load(String configId) throws Exception;

  String revision(String configId) throws Exception;
}
