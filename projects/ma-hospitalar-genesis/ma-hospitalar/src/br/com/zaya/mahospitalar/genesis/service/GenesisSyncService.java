package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiClient;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.domain.PayloadGenesis;
import br.com.zaya.mahospitalar.genesis.domain.ReferenciasBaseInstaladaGenesis;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import br.com.zaya.mahospitalar.genesis.dto.ClienteGenesisDto;
import br.com.zaya.mahospitalar.genesis.dto.FabricanteGenesisDto;
import br.com.zaya.mahospitalar.genesis.dto.SetorGenesisDto;
import br.com.zaya.mahospitalar.genesis.util.GenesisDiagnosticLog;
import br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer;

import java.util.EnumMap;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public final class GenesisSyncService {
    private final ParametrosGenesis parametros;
    private final GenesisApiClient client;
    private final PayloadGenesisFactory factory;
    private final GenesisDiagnosticLog log;
    // One service instance belongs to one batch; never retain results across runs.
    private final Map<List<Object>, Resultado> cadastrosResolvidos = new HashMap<List<Object>, Resultado>();
    private final Map<List<Object>, Exception> cadastrosFalhos = new HashMap<List<Object>, Exception>();

    public GenesisSyncService(ParametrosGenesis parametros, GenesisApiClient client) {
        this(parametros, client, GenesisDiagnosticLog.NONE);
    }

    public GenesisSyncService(ParametrosGenesis parametros, GenesisApiClient client, GenesisDiagnosticLog log) {
        this.parametros = parametros; this.client = client;
        this.factory = new PayloadGenesisFactory(parametros.getUnidadeEpoch());
        this.log = log == null ? GenesisDiagnosticLog.NONE : log;
    }

    public Resultado sincronizar(CandidatoGenesis candidato,
                                 ReferenciasBaseInstaladaGenesis referencias) throws Exception {
        validarCandidato(candidato);
        Map<TipoEntidade, String> ids = new EnumMap<TipoEntidade, String>(TipoEntidade.class);
        ids.put(TipoEntidade.CLIENTE, String.valueOf(referencias.getIdCliente()));
        ids.put(TipoEntidade.SETOR, String.valueOf(referencias.getIdSetor()));
        ids.put(TipoEntidade.MODELO, String.valueOf(referencias.getIdModelo()));
        PayloadGenesis item = factory.item(candidato, ids);
        item = factory.preservarCodigoExterno(item, referencias.getCodigoExternoItem());
        return garantir(item, referencias.getIdItemMaterial());
    }

    public void validarCandidato(CandidatoGenesis candidato) {
        validarTipoProduto(candidato);
    }

    public Resultado sincronizarPorId(CandidatoGenesis candidato,
                                       ReferenciasBaseInstaladaGenesis referencias) throws Exception {
        validarCandidato(candidato);
        if (referencias.getIdCliente() <= 0 || referencias.getIdSetor() <= 0 ||
                referencias.getIdModelo() <= 0) {
            throw new IllegalStateException("Vinculos de cliente, setor e modelo devem estar conciliados.");
        }
        Map<TipoEntidade, String> ids = new EnumMap<TipoEntidade, String>(TipoEntidade.class);
        ids.put(TipoEntidade.CLIENTE, String.valueOf(referencias.getIdCliente()));
        ids.put(TipoEntidade.SETOR, String.valueOf(referencias.getIdSetor()));
        ids.put(TipoEntidade.MODELO, String.valueOf(referencias.getIdModelo()));
        PayloadGenesis item = factory.preservarCodigoExterno(factory.item(candidato, ids),
                referencias.getCodigoExternoItem());
        if (parametros.isSimulacao()) return new Resultado(item,
                new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                        referencias.getIdItemMaterial() == null ? null :
                                String.valueOf(referencias.getIdItemMaterial()), "Simulacao local."),
                true, Operacao.SIMULADO);
        GenesisApiResult cliente = consultarId(TipoEntidade.CLIENTE, referencias.getIdCliente());
        PayloadGenesis clienteEsperado = factory.cliente(candidato);
        if (!identidadeConfere(clienteEsperado, cliente)) {
            throw new FalhaIntegracao(TipoEntidade.CLIENTE, "VALIDAR_ID",
                    new GenesisApiResult(409, GenesisApiResult.Classificacao.CONFLITO, null,
                            "Documento do cliente nao confere com o ID conciliado."));
        }
        if (referencias.getIdItemMaterial() == null) return executarPost(item);
        GenesisApiResult existente = consultarId(TipoEntidade.ITEM_MATERIAL,
                referencias.getIdItemMaterial());
        br.com.zaya.mahospitalar.genesis.dto.ItemMaterialGenesisDto esperado =
                (br.com.zaya.mahospitalar.genesis.dto.ItemMaterialGenesisDto) item.getDto();
        if (!existente.campoCorresponde("nserie", esperado.nserie, false) ||
                !existente.campoCorresponde("idModelo", String.valueOf(esperado.idModelo), false)) {
            throw new FalhaIntegracao(TipoEntidade.ITEM_MATERIAL, "VALIDAR_ID",
                    new GenesisApiResult(409, GenesisApiResult.Classificacao.CONFLITO, null,
                            "Serie/modelo divergentes. O item vinculado nao sera sobrescrito."));
        }
        if (existente.correspondeAo(item.getDto())) {
            return new Resultado(item, existente, false, Operacao.SEM_ALTERACAO);
        }
        return executarPut(item, String.valueOf(referencias.getIdItemMaterial()));
    }

    private GenesisApiResult consultarId(TipoEntidade tipo, long id) throws Exception {
        if (id <= 0) throw new IllegalStateException("ID Genesis invalido para " + tipo);
        GenesisApiResult resultado = client.buscarPorId(tipo, String.valueOf(id));
        if (!resultado.isSucesso()) throw new FalhaIntegracao(tipo, "GET_ID", resultado);
        exigirId(tipo, "GET_ID", resultado);
        if (!String.valueOf(id).equals(resultado.getIdGenesis())) {
            throw new FalhaIntegracao(tipo, "GET_ID",
                    new GenesisApiResult(409, GenesisApiResult.Classificacao.CONFLITO, null,
                            "ID retornado diverge do ID consultado."));
        }
        return resultado;
    }

    public Resultado garantir(PayloadGenesis payload, Long idGenesisLocal) throws Exception {
        if (payload.getTipo() == TipoEntidade.ITEM_MATERIAL) return garantirRemoto(payload, idGenesisLocal);
        List<Object> chave = Arrays.<Object>asList(payload.getTipo(), payload.getChaveSankhya(),
                payload.getCodigoExterno(), payload.getHash(), payload.getJson());
        if (cadastrosResolvidos.containsKey(chave)) {
            Resultado anterior = cadastrosResolvidos.get(chave);
            log.info("Cadastro reutilizado no lote, sem nova chamada HTTP: entidade=" + payload.getTipo() +
                    ", codigoExterno=" + payload.getCodigoExterno() + ", hash=" + payload.getHash() +
                    ", idLocalAtual=" + idGenesisLocal + ", idRemotoConfirmado=" + anterior.api.getIdGenesis() + ".");
            return cadastrosResolvidos.get(chave);
        }
        if (cadastrosFalhos.containsKey(chave)) {
            log.info("Falha de cadastro reutilizada no lote, sem nova chamada HTTP: entidade=" + payload.getTipo() +
                    ", codigoExterno=" + payload.getCodigoExterno() + ", hash=" + payload.getHash() +
                    ", idLocalAtual=" + idGenesisLocal + "; motivo=" +
                    GenesisSanitizer.sanitizeForLog(cadastrosFalhos.get(chave).getMessage()) + ".");
            throw cadastrosFalhos.get(chave);
        }
        try {
            Resultado resultado = garantirRemoto(payload, idGenesisLocal);
            cadastrosResolvidos.put(chave, resultado);
            return resultado;
        } catch (Exception e) {
            cadastrosFalhos.put(chave, e);
            throw e;
        }
    }

    private Resultado garantirRemoto(PayloadGenesis payload, Long idGenesisLocal) throws Exception {
        log.info("Identidade antes da consulta: entidade=" + payload.getTipo() + ", chaveLocal=" +
                payload.getChaveSankhya() + ", codigoExterno=" + payload.getCodigoExterno() +
                ", idGenesisLocal=" + idGenesisLocal + ", hash=" + payload.getHash() + ".");
        registrarPayload(payload);
        if (parametros.isSimulacao()) {
            log.info("Simulacao ativa: nenhuma chamada HTTP sera executada para " + payload.getTipo() + ".");
            return new Resultado(payload, new GenesisApiResult(200,
                    GenesisApiResult.Classificacao.SUCESSO,
                    idGenesisLocal == null ? null : String.valueOf(idGenesisLocal),
                    "Simulacao local."), true, Operacao.SIMULADO);
        }

        GenesisApiResult consulta = client.buscarPorCodigoExterno(payload.getTipo(), payload.getCodigoExterno());
        log.info("Resultado do GET por codigoExterno: entidade=" + payload.getTipo() +
                ", http=" + consulta.getHttpStatus() +
                ", classificacao=" + consulta.getClassificacao() +
                ", idGenesis=" + valor(consulta.getIdGenesis()) + ".");

        Resultado resultado;
        if (consulta.getClassificacao() == GenesisApiResult.Classificacao.NAO_ENCONTRADO) {
            resultado = reconciliarPorIdentidade(payload, idGenesisLocal);
            if (resultado == null) {
                log.info("Decisao de sincronizacao: " + payload.getTipo() +
                        " nao encontrado por nenhuma identidade segura; executar POST.");
                resultado = executarPost(payload);
            }
        } else {
            if (!consulta.isSucesso()) throw new FalhaIntegracao(payload.getTipo(), "GET", consulta);
            exigirId(payload.getTipo(), "GET", consulta);
            validarClienteDoSetor(payload, consulta, "GET_CODIGO_EXTERNO");
            if (consulta.correspondeAo(payload.getDto())) {
                log.info("Decisao de sincronizacao: " + payload.getTipo() +
                        " ja esta alinhado; PUT dispensado.");
                resultado = new Resultado(payload, consulta, false, Operacao.SEM_ALTERACAO);
            } else {
                log.info("Decisao de sincronizacao: " + payload.getTipo() +
                        " encontrado com divergencia; executar PUT no ID Genesis=" +
                        consulta.getIdGenesis() + ".");
                resultado = executarPut(payload, consulta.getIdGenesis());
            }
        }
        log.info("Sincronizacao aceita: entidade=" + payload.getTipo() +
                ", operacao=" + resultado.operacao +
                ", http=" + resultado.api.getHttpStatus() +
                ", idGenesis=" + resultado.api.getIdGenesis() + ".");
        return resultado;
    }

    public GenesisApiResult localizarPorCodigoExterno(TipoEntidade tipo, String codigoExterno) throws Exception {
        GenesisApiResult consulta = client.buscarPorCodigoExterno(tipo, codigoExterno);
        log.info("Localizacao de cadastro existente: entidade=" + tipo +
                ", codigoExterno=" + codigoExterno + ", http=" + consulta.getHttpStatus() +
                ", classificacao=" + consulta.getClassificacao() +
                ", idGenesis=" + valor(consulta.getIdGenesis()) + ".");
        if (consulta.getClassificacao() == GenesisApiResult.Classificacao.NAO_ENCONTRADO) return consulta;
        if (!consulta.isSucesso()) throw new FalhaIntegracao(tipo, "GET", consulta);
        exigirId(tipo, "GET", consulta);
        return consulta;
    }

    private Resultado reconciliarPorIdentidade(PayloadGenesis payload, Long idGenesisLocal) throws Exception {
        boolean possuiDocumento = payload.getTipo() == TipoEntidade.CLIENTE ||
                payload.getTipo() == TipoEntidade.FABRICANTE;
        if (!possuiDocumento && payload.getTipo() != TipoEntidade.SETOR) {
            if (idGenesisLocal != null) {
                throw new FalhaIntegracao(payload.getTipo(), "RECONCILIACAO_ID_LOCAL",
                        new GenesisApiResult(409, GenesisApiResult.Classificacao.CONFLITO,
                                String.valueOf(idGenesisLocal),
                                "Codigo externo nao localizado, mas existe ID local. Revisar identidade antes de criar novamente."));
            }
            return null;
        }

        if (idGenesisLocal != null) {
            GenesisApiResult porId = client.buscarPorId(payload.getTipo(), String.valueOf(idGenesisLocal));
            log.info("Consulta alternativa pelo ID local: entidade=" + payload.getTipo() +
                    ", idGenesisLocal=" + idGenesisLocal + ", http=" + porId.getHttpStatus() +
                    ", classificacao=" + porId.getClassificacao() + ".");
            if (porId.isSucesso()) validarClienteDoSetor(payload, porId, "GET_ID_LOCAL");
            if (porId.isSucesso() && identidadeConfere(payload, porId)) {
                log.info("Identidade confirmada pelo documento; executar PUT no ID Genesis local=" +
                        idGenesisLocal + ".");
                return executarPut(payload, String.valueOf(idGenesisLocal));
            }
            if (porId.getClassificacao() != GenesisApiResult.Classificacao.NAO_ENCONTRADO && !porId.isSucesso()) {
                throw new FalhaIntegracao(payload.getTipo(), "GET_ID_LOCAL", porId);
            }
            if (porId.isSucesso()) {
                log.info("ID Genesis local nao pertence ao mesmo documento; vinculo nao sera reutilizado.");
            }
        }

        if (!possuiDocumento) return null;

        String documento = documento(payload);
        GenesisApiResult porDocumento = client.buscarPorDocumento(payload.getTipo(), documento);
        log.info("Consulta alternativa por documento: entidade=" + payload.getTipo() +
                ", http=" + porDocumento.getHttpStatus() + ", classificacao=" +
                porDocumento.getClassificacao() + ", idGenesis=" + valor(porDocumento.getIdGenesis()) + ".");
        if (porDocumento.getClassificacao() == GenesisApiResult.Classificacao.NAO_ENCONTRADO) {
            return null;
        }
        if (!porDocumento.isSucesso()) throw new FalhaIntegracao(payload.getTipo(), "GET_DOCUMENTO", porDocumento);
        exigirId(payload.getTipo(), "GET_DOCUMENTO", porDocumento);
        if (!identidadeConfere(payload, porDocumento)) {
            throw new FalhaIntegracao(payload.getTipo(), "GET_DOCUMENTO", new GenesisApiResult(409,
                    GenesisApiResult.Classificacao.CONFLITO, null,
                    "Registro retornado pela consulta nao possui o documento solicitado."));
        }
        log.info("Cadastro localizado pelo documento; executar PUT no ID Genesis=" +
                porDocumento.getIdGenesis() + ".");
        return executarPut(payload, porDocumento.getIdGenesis());
    }

    private static boolean identidadeConfere(PayloadGenesis payload, GenesisApiResult resultado) {
        Object dto = payload.getDto();
        if (dto instanceof ClienteGenesisDto) {
            ClienteGenesisDto cliente = (ClienteGenesisDto) dto;
            return cliente.cnpj != null
                    ? resultado.campoCorresponde("cnpj", cliente.cnpj, true)
                    : resultado.campoCorresponde("cpf", cliente.cpf, true);
        }
        if (dto instanceof FabricanteGenesisDto) {
            return resultado.campoCorresponde("cnpj", ((FabricanteGenesisDto) dto).cnpj, true);
        }
        if (dto instanceof SetorGenesisDto) {
            return resultado.campoCorresponde("idCliente", ((SetorGenesisDto) dto).idCliente, false);
        }
        return false;
    }

    private static void validarClienteDoSetor(PayloadGenesis payload, GenesisApiResult resultado,
                                             String etapa) throws FalhaIntegracao {
        if (payload.getTipo() == TipoEntidade.SETOR && !identidadeConfere(payload, resultado)) {
            throw new FalhaIntegracao(TipoEntidade.SETOR, etapa, new GenesisApiResult(409,
                    GenesisApiResult.Classificacao.CONFLITO, resultado.getIdGenesis(),
                    "Setor Genesis retornado pertence a outro cliente ou nao informa idCliente. " +
                    "Cliente esperado=" + ((SetorGenesisDto) payload.getDto()).idCliente +
                    "; revisar o vinculo antes de reprocessar. Nenhum POST ou PUT de setor foi executado."));
        }
    }

    private static String documento(PayloadGenesis payload) {
        Object dto = payload.getDto();
        if (dto instanceof ClienteGenesisDto) {
            ClienteGenesisDto cliente = (ClienteGenesisDto) dto;
            return cliente.cnpj == null ? cliente.cpf : cliente.cnpj;
        }
        return ((FabricanteGenesisDto) dto).cnpj;
    }

    private void registrarPayload(PayloadGenesis item) {
        log.info("Payload final montado: entidade=" + item.getTipo() +
                ", chaveSankhya=" + item.getChaveSankhya() +
                ", codigoExterno=" + item.getCodigoExterno() +
                ", hash=" + item.getHash() + ".");
        log.info("Payload final JSON: " + GenesisSanitizer.sanitizeForLog(item.getJson()));
    }

    private Resultado executarPost(PayloadGenesis payload) throws Exception {
        GenesisApiResult criacao;
        try {
            criacao = client.criar(payload.getTipo(), payload.getDto());
        } catch (Exception e) {
            throw new FalhaIntegracao(payload.getTipo(), "POST",
                    new GenesisApiResult(408, GenesisApiResult.Classificacao.TRANSITORIO, null,
                            "POST sem confirmacao. Reconciliar antes de repetir."));
        }
        if (!criacao.isSucesso()) throw new FalhaIntegracao(payload.getTipo(), "POST", criacao);
        exigirId(payload.getTipo(), "POST", criacao);
        return new Resultado(payload, criacao, false, Operacao.CRIADO);
    }

    private Resultado executarPut(PayloadGenesis payload, String idGenesis) throws Exception {
        GenesisApiResult atualizacao = client.atualizar(payload.getTipo(), idGenesis, payload.getDto());
        if (!atualizacao.isSucesso()) throw new FalhaIntegracao(payload.getTipo(), "PUT", atualizacao);
        if (atualizacao.getIdGenesis() == null || atualizacao.getIdGenesis().trim().isEmpty()) {
            atualizacao = new GenesisApiResult(atualizacao.getHttpStatus(), atualizacao.getClassificacao(),
                    idGenesis, atualizacao.getCorpoSanitizado());
        }
        return new Resultado(payload, atualizacao, false, Operacao.ATUALIZADO);
    }

    private static void exigirId(TipoEntidade tipo, String etapa, GenesisApiResult resultado)
            throws FalhaIntegracao {
        if (resultado.getIdGenesis() == null || resultado.getIdGenesis().trim().isEmpty()) {
            throw new FalhaIntegracao(tipo, etapa, new GenesisApiResult(422,
                    GenesisApiResult.Classificacao.ERRO_FUNCIONAL, null,
                    "Resposta de sucesso sem ID Genesis."));
        }
    }

    private static String valor(String value) {
        return value == null || value.trim().isEmpty() ? "<ausente>" : value;
    }

    private static void validarTipoProduto(CandidatoGenesis candidato) {
        String tipo = candidato == null ? null : candidato.tipoProduto;
        if (!"M1".equalsIgnoreCase(tipo == null ? null : tipo.trim())) {
            throw new IllegalStateException("Produto bloqueado para a integracao Genesis: somente " +
                    "tipo efetivo='M1' (Equipamentos), priorizando HSP_TIPOPRODUTO " +
                    "e usando AD_TIPOPRODUTO somente quando HSP estiver vazio. CODPROD=" +
                    (candidato == null ? null : candidato.codProd) + ", tipo=" + tipo + ".");
        }
    }

    public static final class Resultado {
        public final PayloadGenesis payload;
        public final GenesisApiResult api;
        public final boolean simulado;
        public final Operacao operacao;
        Resultado(PayloadGenesis payload, GenesisApiResult api, boolean simulado, Operacao operacao) {
            this.payload = payload; this.api = api; this.simulado = simulado; this.operacao = operacao;
        }
    }

    public enum Operacao { SIMULADO, CRIADO, ATUALIZADO, SEM_ALTERACAO }

    public static final class FalhaIntegracao extends Exception {
        public final TipoEntidade entidade;
        public final GenesisApiResult resultado;
        public final String etapa;
        FalhaIntegracao(TipoEntidade entidade, GenesisApiResult resultado) {
            this(entidade, null, resultado);
        }
        FalhaIntegracao(TipoEntidade entidade, String etapa, GenesisApiResult resultado) {
            super(mensagem(entidade, etapa, resultado));
            this.entidade = entidade; this.resultado = resultado; this.etapa = etapa;
        }

        private static String mensagem(TipoEntidade entidade, String etapa, GenesisApiResult resultado) {
            StringBuilder mensagem = new StringBuilder("Falha em ").append(entidade);
            if (etapa != null) mensagem.append(" na etapa ").append(etapa);
            mensagem.append(": HTTP ").append(resultado.getHttpStatus())
                    .append(" - ").append(resultado.getClassificacao());
            String corpo = GenesisSanitizer.sanitize(resultado.getCorpoSanitizado());
            if (corpo != null && !corpo.trim().isEmpty()) {
                mensagem.append(". Resposta Genesis: ").append(corpo.trim());
            }
            return mensagem.toString();
        }
    }
}
