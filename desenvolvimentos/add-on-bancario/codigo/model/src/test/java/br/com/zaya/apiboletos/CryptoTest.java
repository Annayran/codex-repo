package br.com.zaya.apiboletos;

import static org.junit.jupiter.api.Assertions.*;

import br.com.zaya.apiboletos.security.EncryptedCredentialStore;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;

class CryptoTest {
  @Test
  void credentialsAreAuthenticatedAndBoundToConfiguration() throws Exception {
    EncryptedCredentialStore store =
        new EncryptedCredentialStore(null, () -> new SecretKeySpec(new byte[32], "AES"));
    String sealed = store.encrypt("config1", "fake-secret");
    assertFalse(sealed.contains("fake-secret"));
    assertEquals("fake-secret", store.decrypt("config1", sealed));
    assertThrows(Exception.class, () -> store.decrypt("config2", sealed));
    assertNotEquals(sealed, store.encrypt("config1", "fake-secret"));
  }

  @Test
  void noKeyProviderFailsClosed() {
    EncryptedCredentialStore store =
        new EncryptedCredentialStore(
            null,
            () -> {
              throw new IllegalStateException();
            });
    assertFalse(store.available());
    assertThrows(Exception.class, () -> store.encrypt("cfg", "fake"));
  }
}
