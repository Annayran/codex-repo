package br.zaya.telecontrol.interfaces;

import br.zaya.telecontrol.models.OsIntegracaoModel;

/**
 * DAO interface for OS integration operations (wrapper around legacy static DAO).
 */
public interface IOsIntegracaoDAO {
    boolean osExiste(String numeroOs) throws Exception;
    void salvarOrdemServico(OsIntegracaoModel osIntegracao) throws Exception;
    void salvarPecasOS(String numeroOs, OsIntegracaoModel osIntegracao) throws Exception;
}

