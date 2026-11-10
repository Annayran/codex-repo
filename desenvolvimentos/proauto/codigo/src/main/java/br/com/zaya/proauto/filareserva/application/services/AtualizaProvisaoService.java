package br.com.zaya.proauto.filareserva.application.services;

import br.com.zaya.proauto.filareserva.application.ports.AtualizacaoProvisaoGateway;

public class AtualizaProvisaoService {
    private final AtualizacaoProvisaoGateway gateway;

    public AtualizaProvisaoService(AtualizacaoProvisaoGateway gateway) {
        if (gateway == null) {
            throw new IllegalArgumentException("O gateway de atualizacao de provisao e obrigatorio.");
        }
        this.gateway = gateway;
    }

    public int executar() throws Exception {
        return gateway.atualizarProvisoes();
    }
}
