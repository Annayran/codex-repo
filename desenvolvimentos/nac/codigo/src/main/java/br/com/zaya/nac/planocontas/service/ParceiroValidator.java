package br.com.zaya.nac.planocontas.service;

import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.model.Parceiro;
import br.com.zaya.nac.planocontas.model.TipoParceiro;
import java.util.ArrayList;
import java.util.List;

public final class ParceiroValidator {

    public List<TipoParceiro> validar(Parceiro parceiro) throws RegraNegocioException {
        return filtrarTiposPendentes(parceiro, validarTipos(parceiro));
    }

    public List<TipoParceiro> validarTipos(Parceiro parceiro)
            throws RegraNegocioException {
        if (!parceiro.isCliente() && !parceiro.isFornecedor()) {
            throw new RegraNegocioException(
                    "O parceiro não está marcado como Cliente nem como Fornecedor. "
                            + "Não foi possível determinar o grupo contábil.");
        }
        List<TipoParceiro> tipos = new ArrayList<TipoParceiro>(2);
        if (parceiro.isCliente()) {
            tipos.add(TipoParceiro.CLIENTE);
        }
        if (parceiro.isFornecedor()) {
            tipos.add(TipoParceiro.FORNECEDOR);
        }
        return tipos;
    }

    public List<TipoParceiro> filtrarTiposPendentes(
            Parceiro parceiro,
            List<TipoParceiro> tipos) {
        List<TipoParceiro> pendentes = new ArrayList<TipoParceiro>(tipos.size());
        for (TipoParceiro tipo : tipos) {
            if (TipoParceiro.CLIENTE.equals(tipo) && !parceiro.isContaClienteGerada()) {
                pendentes.add(tipo);
            } else if (TipoParceiro.FORNECEDOR.equals(tipo)
                    && !parceiro.isContaFornecedorGerada()) {
                pendentes.add(tipo);
            }
        }
        return pendentes;
    }
}
