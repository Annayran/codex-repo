package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiClient;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDto;

public final class EstoqueGenesisSyncService {
    private final GenesisApiClient client;
    public interface ReservaCriacao { void reservar() throws Exception; }
    private final ReservaCriacao reserva;
    public EstoqueGenesisSyncService(GenesisApiClient client) { this(client,()->{}); }
    public EstoqueGenesisSyncService(GenesisApiClient client,ReservaCriacao reserva) { this.client = client; this.reserva=reserva; }

    public Resultado sincronizar(EstoqueGenesisDto dto, String tipoHsp, String tipoAd,
                                 boolean ativo, Long idLocal) throws Exception {
        String tipo = tipoHsp == null || tipoHsp.trim().isEmpty() ? tipoAd : tipoHsp;
        tipo = tipo == null ? null : tipo.trim();
        boolean elegivel = "M2".equalsIgnoreCase(tipo) || "M3".equalsIgnoreCase(tipo) || "SV".equalsIgnoreCase(tipo);
        if (!ativo || !elegivel) {
            if (idLocal == null) return new Resultado("IGNORADO", null, null);
            validarCodigo(dto);
            GenesisApiResult remoto = client.buscarPorId(TipoEntidade.ESTOQUE, id(idLocal));
            if (remoto.getClassificacao() == GenesisApiResult.Classificacao.NAO_ENCONTRADO)
                throw falha("GET_ID", remoto);
            validarIdentidade(dto, remoto);
            if (id(remoto) != idLocal.longValue())
                throw new IllegalStateException("GET por ID retornou outro estoque; DELETE bloqueado.");
            GenesisApiResult exclusao = client.inativarEstoque(id(idLocal));
            if (exclusao.getHttpStatus() != 200) throw falha("DELETE", exclusao);
            return new Resultado("INATIVADO", idLocal, exclusao);
        }
        validarPayload(dto);
        dto.servico = "SV".equalsIgnoreCase(tipo);
        GenesisApiResult remoto = client.buscarPorCodigoExterno(TipoEntidade.ESTOQUE, dto.codigoExterno);
        if (remoto.getClassificacao() == GenesisApiResult.Classificacao.NAO_ENCONTRADO) {
            if (idLocal != null) {
                remoto = client.buscarPorId(TipoEntidade.ESTOQUE, id(idLocal));
                validarIdentidade(dto, remoto);
            } else {
                reserva.reservar();
                GenesisApiResult criado = client.criar(TipoEntidade.ESTOQUE, dto);
                if (criado.getHttpStatus() != 201 || !criado.isSucesso()) throw falha("POST", criado);
                return new Resultado("CRIADO", id(criado), criado);
            }
        }
        validarIdentidade(dto, remoto);
        long id = id(remoto);
        if (idLocal != null && idLocal.longValue() != id) {
            throw new IllegalStateException("ID de estoque divergente; revisar conciliacao antes de PUT.");
        }
        if (remoto.correspondeAo(dto)) return new Resultado("SEM_ALTERACAO", id, remoto);
        GenesisApiResult atualizado = client.atualizar(TipoEntidade.ESTOQUE, String.valueOf(id), dto);
        if (atualizado.getHttpStatus() != 200 || !atualizado.isSucesso()) throw falha("PUT", atualizado);
        if (atualizado.getIdGenesis() != null && id(atualizado) != id)
            throw new IllegalStateException("PUT retornou outro ID de estoque; revisar resultado.");
        return new Resultado("ATUALIZADO", id, atualizado);
    }

    private static void validarIdentidade(EstoqueGenesisDto dto, GenesisApiResult remoto) throws Exception {
        if (!remoto.isSucesso()) throw falha("GET", remoto);
        id(remoto);
        if (!remoto.campoCorresponde("codigoExterno", dto.codigoExterno, false))
            throw new IllegalStateException("Codigo externo do estoque divergente; nenhuma mutacao autorizada.");
        br.com.zaya.mahospitalar.genesis.repository.GenesisRequestRepository.identidade(remoto);
    }
    private static void validarCodigo(EstoqueGenesisDto dto) {
        if (dto == null || vazio(dto.codigoExterno)) throw new IllegalArgumentException("Codigo externo obrigatorio.");
    }
    private static void validarPayload(EstoqueGenesisDto dto) {
        validarCodigo(dto);
        if (vazio(dto.codigo) || vazio(dto.descServico) || vazio(dto.descTecnica)
                || dto.idCategoria == null || dto.idCategoria <= 0 || dto.idFabricante == null || dto.idFabricante <= 0
                || dto.idFornecedor == null || dto.idFornecedor <= 0 || dto.qtd == null || dto.qtd.signum() < 0
                || dto.vlrVenda == null || dto.vlrVenda.signum() <= 0
                || dto.ipi == null || dto.ipi.signum() < 0 || dto.ipi.compareTo(new java.math.BigDecimal("100")) > 0
                || vazio(dto.status))
            throw new IllegalArgumentException("Cadastro, status, preco ou IPI nao configurado; envio bloqueado.");
    }
    private static boolean vazio(String s) { return s == null || s.trim().isEmpty(); }
    private static String id(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID de estoque invalido.");
        return id.toString();
    }
    private static long id(GenesisApiResult resultado) {
        String valor = resultado.getIdGenesis();
        if (valor == null || !valor.matches("[1-9][0-9]*")) throw new IllegalStateException("Resposta sem ID de estoque valido.");
        return Long.parseLong(valor);
    }
    private static GenesisSyncService.FalhaIntegracao falha(String etapa, GenesisApiResult resultado) {
        return new GenesisSyncService.FalhaIntegracao(TipoEntidade.ESTOQUE, etapa, resultado);
    }
    public static final class Resultado {
        public final String operacao;
        public final Long idGenesis;
        public final GenesisApiResult api;
        Resultado(String operacao, Long idGenesis, GenesisApiResult api) {
            this.operacao = operacao; this.idGenesis = idGenesis; this.api = api;
        }
    }
}
