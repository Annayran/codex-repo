package br.com.zaya.engineering.nexo.sankhya;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.*;

/** Implementations must enforce scope, read-only access, parameter binding and server query timeout. */
public interface SankhyaGateway {
    Snapshot readConfirmationContext(Scope scope, long nunota, int maxItems, long timeoutMs);
}
