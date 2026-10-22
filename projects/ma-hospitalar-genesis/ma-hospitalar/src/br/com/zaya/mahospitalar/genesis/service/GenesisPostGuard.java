package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.domain.PayloadGenesis;

public interface GenesisPostGuard {
    default void prepararConsulta(PayloadGenesis payload) throws Exception { }
    default void registrarFalhaConsulta(PayloadGenesis payload, Exception erro) throws Exception { }
    final class EnvioIncerto extends Exception {
        public EnvioIncerto(String message) { super(message); }
    }
    Long idConfirmado(PayloadGenesis payload) throws Exception;
    Long reservar(PayloadGenesis payload) throws Exception;
    void confirmar(PayloadGenesis payload, GenesisApiResult result, boolean criado) throws Exception;
    void falhar(PayloadGenesis payload, GenesisApiResult result) throws Exception;
}
