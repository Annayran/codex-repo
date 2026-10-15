package br.com.zaya.engineering.nexobutton;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;

/** Implement with the hosting's approved native protected configuration store. Never read user form parameters for credentials. */
public interface ButtonConfigurationProvider {
    Configuration load(ContextoAcao context) throws Exception;
    final class Configuration {
        public final String endpoint;
        public final String principal;
        public final String password;
        public final String erpVersion;
        public Configuration(String endpoint, String principal, String password, String erpVersion) {
            this.endpoint = endpoint; this.principal = principal; this.password = password; this.erpVersion = erpVersion;
        }
        // No toString: avoid accidentally exposing a credential in logs.
    }
}
