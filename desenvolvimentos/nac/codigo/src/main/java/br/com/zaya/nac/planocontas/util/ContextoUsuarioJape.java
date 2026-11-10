package br.com.zaya.nac.planocontas.util;

import br.com.sankhya.jape.util.JapeSessionContext;
import java.math.BigDecimal;

/** Mantém uma troca temporária e restaurável do usuário efetivo da sessão Jape. */
public final class ContextoUsuarioJape implements AutoCloseable {

    private static final String PROPRIEDADE_USUARIO_LOGADO = "usuario_logado";

    private final boolean possuiaUsuario;
    private final Object usuarioAnterior;
    private boolean fechado;

    private ContextoUsuarioJape(BigDecimal usuario) {
        this.possuiaUsuario = JapeSessionContext.containsProperty(
                PROPRIEDADE_USUARIO_LOGADO);
        this.usuarioAnterior = JapeSessionContext.getProperty(PROPRIEDADE_USUARIO_LOGADO);
        JapeSessionContext.putProperty(PROPRIEDADE_USUARIO_LOGADO, usuario);
    }

    public static ContextoUsuarioJape abrir(BigDecimal usuario) {
        if (usuario == null || usuario.signum() < 0) {
            throw new IllegalArgumentException("O usuário efetivo da sessão Jape é inválido.");
        }
        return new ContextoUsuarioJape(usuario);
    }

    @Override
    public void close() {
        if (fechado) {
            return;
        }
        if (possuiaUsuario) {
            JapeSessionContext.putProperty(PROPRIEDADE_USUARIO_LOGADO, usuarioAnterior);
        } else {
            JapeSessionContext.removeProperty(PROPRIEDADE_USUARIO_LOGADO);
        }
        fechado = true;
    }
}
