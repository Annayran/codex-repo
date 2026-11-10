package br.com.zaya.apiboletos.application;

import br.com.zaya.apiboletos.banks.*;
import br.com.zaya.apiboletos.banks.itau.*;
import br.com.zaya.apiboletos.banks.santander.*;
import br.com.zaya.apiboletos.persistence.*;
import br.com.zaya.apiboletos.ports.*;
import br.com.zaya.apiboletos.sankhya.*;
import br.com.zaya.apiboletos.security.*;

public final class RuntimeModule {
  public static final Database DB = new SankhyaDatabase();
  public static final Repository REPOSITORY = new Repository();
  public static final CredentialStore CREDENTIALS =
      new EncryptedCredentialStore(DB, new EncryptedCredentialStore.ManagedKeyProvider());
  private static final AuthenticatedClient HTTP = new AuthenticatedClient(new ApacheTransport());
  public static final ConnectorRegistry BANKS =
      new ConnectorRegistry(new ItauConnector(HTTP), new SantanderConnector(HTTP));
  public static final NativeFinancial FINANCIAL = new NativeFinancial();
  public static final ConfigurationService CONFIGURATIONS =
      new ConfigurationService(DB, REPOSITORY, CREDENTIALS, BANKS);
  public static final BillingEngine ENGINE =
      new BillingEngine(DB, REPOSITORY, BANKS, CREDENTIALS, FINANCIAL, FINANCIAL);

  public static boolean enabled() {
    try {
      return "S"
          .equals(br.com.sankhya.modelcore.util.MGECoreParameter.getParameterAsString("ZYCBHABIL"));
    } catch (Exception e) {
      return false;
    }
  }

  private RuntimeModule() {}
}
