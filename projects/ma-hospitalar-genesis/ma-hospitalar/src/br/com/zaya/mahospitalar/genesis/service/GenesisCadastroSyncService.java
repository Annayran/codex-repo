package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.client.GenesisApiClient;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.PayloadGenesis;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class GenesisCadastroSyncService {
    private final GenesisApiClient client;
    private final GenesisPostGuard guard;
    private final ParametrosGenesis parametros;

    public GenesisCadastroSyncService(ParametrosGenesis parametros, GenesisApiClient client,
                                      GenesisPostGuard guard) {
        this.parametros = parametros; this.client = client; this.guard = guard;
    }

    public GenesisSyncService.Resultado resolver(PayloadGenesis payload, Long idLocal) throws Exception {
        if (!parametros.isSimulacao()) guard.prepararConsulta(payload);
        try { return resolverInterno(payload,idLocal); }
        catch (Exception e) {
            try { if (!parametros.isSimulacao()) guard.registrarFalhaConsulta(payload,e); }
            catch (Exception registro) { e.addSuppressed(registro); }
            throw e;
        }
    }

    private GenesisSyncService.Resultado resolverInterno(PayloadGenesis payload, Long idLocal) throws Exception {
        if (parametros.isSimulacao()) return resultado(payload,
                new GenesisApiResult(200, GenesisApiResult.Classificacao.SUCESSO,
                        idLocal == null ? null : idLocal.toString(), "{}"), true, false);
        Long id;
        try {
            id = idLocal != null ? idLocal : guard.idConfirmado(payload);
        } catch (Exception e) {
            if (!reservaIncerta(e)) throw e;
            GenesisApiResult recuperado = client.buscarPorCodigoExterno(payload.getTipo(), payload.getCodigoExterno());
            if (!recuperado.isSucesso()) {
                if (recuperado.getClassificacao() == GenesisApiResult.Classificacao.NAO_ENCONTRADO)
                    throw new GenesisPostGuard.EnvioIncerto("ENVIO_INCERTO: GET_RECONCILIACAO executado; HTTP="+
                            recuperado.getHttpStatus()+"; cadastro nao encontrado; reserva mantida, nenhum novo POST autorizado.");
                throw new GenesisSyncService.FalhaIntegracao(payload.getTipo(), "GET_RECONCILIACAO", recuperado);
            }
            validar(payload, recuperado, null);
            guard.confirmar(payload, recuperado, false);
            return resultado(payload, recuperado, false, false);
        }
        if (id != null) return consultar(payload, id);

        // No local ID is not proof of remote absence: reconcile the stable external code first.
        GenesisApiResult consulta = client.buscarPorCodigoExterno(payload.getTipo(), payload.getCodigoExterno());
        if (consulta.isSucesso()) {
            validar(payload, consulta, null);
            guard.confirmar(payload, consulta, false);
            return resultado(payload, consulta, false, false);
        }
        if (consulta.getClassificacao() != GenesisApiResult.Classificacao.NAO_ENCONTRADO) {
            throw new GenesisSyncService.FalhaIntegracao(payload.getTipo(), "GET_CODIGO_EXTERNO", consulta);
        }
        validarPost(payload);
        Long concorrente = guard.reservar(payload);
        if (concorrente != null) return consultar(payload, concorrente);
        GenesisApiResult criacao;
        try {
            criacao = client.criar(payload.getTipo(), payload.getDto());
        } catch (Exception e) {
            throw new IllegalStateException("ENVIO_INCERTO: POST sem confirmacao; reserva preservada.", e);
        }
        if (!criacao.isSucesso()) {
            guard.falhar(payload, criacao);
            throw new GenesisSyncService.FalhaIntegracao(payload.getTipo(), "POST", criacao);
        }
        if (!idValido(criacao.getIdGenesis())) {
            throw new GenesisPostGuard.EnvioIncerto("ENVIO_INCERTO: entidade=" + payload.getTipo() +
                    "; operacao=POST; HTTP=" + criacao.getHttpStatus() +
                    "; codigoExterno=" + payload.getCodigoExterno() +
                    "; resposta sem ID positivo; corpo=" +
                    br.com.zaya.mahospitalar.genesis.util.GenesisSanitizer.sanitizeForHistory(criacao.getCorpoSanitizado()) +
                    "; criacao nao confirmada. Reserva mantida; consultar identidade remota antes de novo POST.");
        }
        guard.confirmar(payload, criacao, true);
        return resultado(payload, criacao, false, true);
    }

    private static boolean reservaIncerta(Throwable erro) {
        // Autonomous Jape transactions may wrap the checked guard exception.
        for (int i=0; erro!=null && i<16; i++, erro=erro.getCause()) {
            if (erro instanceof GenesisPostGuard.EnvioIncerto) return true;
        }
        return false;
    }

    private GenesisSyncService.Resultado consultar(PayloadGenesis p, Long id) throws Exception {
        if (id <= 0) throw new IllegalStateException("ID Genesis invalido.");
        GenesisApiResult r = client.buscarPorId(p.getTipo(), id.toString());
        if (!r.isSucesso()) throw new GenesisSyncService.FalhaIntegracao(p.getTipo(), "GET_ID", r);
        validar(p, r, id);
        guard.confirmar(p, r, false);
        return resultado(p, r, false, false);
    }

    private void validar(PayloadGenesis p, GenesisApiResult r, Long id) throws Exception {
        if (!idValido(r.getIdGenesis()) || (id != null && !id.toString().equals(r.getIdGenesis()))) {
            throw new IllegalStateException("Identidade Genesis divergente do ID consultado.");
        }
        // Imported/confirmed manufacturer IDs are authoritative; legacy records may have CNPJ=0 and no external code.
        if (p.getTipo() == TipoEntidade.FABRICANTE && id != null) {
            if (!r.campoCorresponde("id",id.toString(),false)) {
                throw new IllegalStateException("ID do fabricante no corpo diverge do vinculo confirmado.");
            }
            return;
        }
        JsonNode esperado = new ObjectMapper().valueToTree(p.getDto());
        boolean confere;
        if (p.getTipo() == TipoEntidade.CLIENTE || p.getTipo() == TipoEntidade.FABRICANTE) {
            String campo = esperado.hasNonNull("cnpj") ? "cnpj" : esperado.hasNonNull("cpf") ? "cpf" : "codigoExterno";
            confere = confere(r, esperado, campo, !"codigoExterno".equals(campo));
        } else if (p.getTipo() == TipoEntidade.SETOR) {
            confere = confere(r, esperado, "idCliente", false);
        } else if (p.getTipo() == TipoEntidade.ITEM_MATERIAL) {
            confere = confere(r, esperado, "nserie", false) && confere(r, esperado, "idModelo", false);
        } else {
            confere = confere(r, esperado, "codigoExterno", false);
        }
        if (!confere) throw new IllegalStateException("Identidade remota nao corresponde ao vinculo " + p.getTipo() + ".");
    }

    private boolean confere(GenesisApiResult r, JsonNode esperado, String campo, boolean digitos) {
        return esperado.hasNonNull(campo) && r.campoCorresponde(campo, esperado.get(campo).asText(), digitos);
    }

    private void validarPost(PayloadGenesis p) {
        if (p.getTipo() == TipoEntidade.FABRICANTE) {
            JsonNode dados = new ObjectMapper().valueToTree(p.getDto());
            if (!dados.hasNonNull("cnpj") || !dados.get("cnpj").asText().matches("[0-9]{14}")) {
                throw new IllegalStateException("CNPJ de 14 digitos obrigatorio para criar fabricante.");
            }
        }
    }

    private static boolean idValido(String id) {
        try { return id != null && Long.parseLong(id) > 0; } catch (Exception e) { return false; }
    }

    private GenesisSyncService.Resultado resultado(PayloadGenesis p, GenesisApiResult r,
                                                   boolean simulado, boolean criado) {
        return new GenesisSyncService.Resultado(p, r, simulado, simulado ?
                GenesisSyncService.Operacao.SIMULADO : criado ? GenesisSyncService.Operacao.CRIADO :
                GenesisSyncService.Operacao.SEM_ALTERACAO);
    }
}
