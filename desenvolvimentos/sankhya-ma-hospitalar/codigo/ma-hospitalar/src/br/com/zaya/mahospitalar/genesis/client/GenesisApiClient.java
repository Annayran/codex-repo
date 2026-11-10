package br.com.zaya.mahospitalar.genesis.client;

import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;

public interface GenesisApiClient {
    GenesisApiResult buscarPorCodigoExterno(TipoEntidade tipo, String codigoExterno) throws Exception;
    GenesisApiResult buscarPorId(TipoEntidade tipo, String idGenesis) throws Exception;
    GenesisApiResult buscarPorDocumento(TipoEntidade tipo, String documento) throws Exception;
    GenesisApiResult criar(TipoEntidade tipo, Object payload) throws Exception;
    GenesisApiResult atualizar(TipoEntidade tipo, String idGenesis, Object payload) throws Exception;
    default GenesisApiResult inativarEstoque(String idGenesis) throws Exception {
        throw new UnsupportedOperationException("Cliente nao implementa inativacao de estoque.");
    }
}
