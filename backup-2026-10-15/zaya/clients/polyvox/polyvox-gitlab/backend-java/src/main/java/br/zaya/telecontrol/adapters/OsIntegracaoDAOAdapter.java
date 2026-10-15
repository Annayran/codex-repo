package br.zaya.telecontrol.adapters;

import br.zaya.telecontrol.interfaces.IOsIntegracaoDAO;
import br.zaya.telecontrol.models.OsIntegracaoModel;
import br.zaya.telecontrol.services.OsIntegracaoDAO;

/**
 * Adapter implementing IOsIntegracaoDAO that delegates to the existing
 * OsIntegracaoDAO static methods. Non-destructive compatibility layer.
 */
public class OsIntegracaoDAOAdapter implements IOsIntegracaoDAO {

    @Override
    public boolean osExiste(String numeroOs) throws Exception {
        return OsIntegracaoDAO.osExiste(numeroOs);
    }

    @Override
    public void salvarOrdemServico(OsIntegracaoModel osIntegracao) throws Exception {
        OsIntegracaoDAO.salvarOrdemServico(osIntegracao);
    }

    @Override
    public void salvarPecasOS(String numeroOs, OsIntegracaoModel osIntegracao) throws Exception {
        OsIntegracaoDAO.salvarPecasOS(numeroOs, osIntegracao);
    }
}

