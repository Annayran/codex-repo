package br.com.zaya.apiboletos.services;

import static br.com.zaya.apiboletos.application.RuntimeModule.*;
import static br.com.zaya.apiboletos.domain.Models.require;
import static br.com.zaya.apiboletos.persistence.Sql.*;

import br.com.sankhya.studio.annotations.Service;
import br.com.sankhya.studio.annotations.enums.EJBTransactionType;
import br.com.zaya.apiboletos.application.*;
import br.com.zaya.apiboletos.domain.Models.*;
import br.com.zaya.apiboletos.persistence.*;
import br.com.zaya.apiboletos.security.Authorization;
import java.util.*;

/** Toda entrada valida autenticação e empresa no servidor; segredos nunca são retornados. */
@Service(serviceName = "ZycbBillingServiceSP", transactionType = EJBTransactionType.NotSupported)
public final class BillingController {
  @SuppressWarnings("unchecked")
  private static Map<String, Object> map(Object value) {
    return Json.GSON.fromJson(Json.write(value), Map.class);
  }

  private Config config(String id, String permission) throws Exception {
    require(id != null && id.matches("[a-f0-9]{32}"), "IDENTIFICADOR_INVALIDO");
    Config cfg = DB.transaction(c -> REPOSITORY.config(c, id));
    require(cfg != null, "CONFIGURACAO_AUSENTE");
    Authorization.require(cfg.company, permission);
    return cfg;
  }

  public Map<String, Object> salvar(ApiRequest request) throws Exception {
    require(
        request != null
            && request.getConfiguration() != null
            && request.getConfiguration().length() < 32000,
        "CONFIGURACAO_INVALIDA");
    Config cfg = Json.read(request.getConfiguration(), Config.class);
    long user = Authorization.require(cfg.company, "ADMIN");
    if (cfg.id != null) config(cfg.id, "ADMIN");
    br.com.zaya.apiboletos.sankhya.NativeFinancial.validateConfig(cfg);
    return map(CONFIGURATIONS.save(cfg, user));
  }

  public Map<String, Object> credenciais(ApiRequest request) throws Exception {
    Config cfg = config(request.getId(), "ADMIN");
    require(!cfg.active && !cfg.sendEnabled, "PAUSE_E_SALVE_ANTES_DE_TROCAR_CREDENCIAIS");
    require(
        request.getCredentials() != null && request.getCredentials().length() < 12010000,
        "CREDENCIAIS_INVALIDAS");
    Credentials k = Json.read(request.getCredentials(), Credentials.class);
    CREDENTIALS.save(cfg.id, k);
    DB.transaction(
        c -> {
          REPOSITORY.audit(
              c,
              cfg.id,
              Authorization.user(),
              "ROTACIONAR_CREDENCIAIS",
              cfg.id,
              "Valores omitidos");
          return null;
        });
    return Collections.<String, Object>singletonMap("salvo", true);
  }

  public Map<String, Object> diagnosticar(ApiRequest r) throws Exception {
    Config cfg = config(r.getId(), "ADMIN");
    br.com.zaya.apiboletos.sankhya.NativeFinancial.validateConfig(cfg);
    CONFIGURATIONS.diagnose(cfg.id, Authorization.user());
    return diagnostico(r);
  }

  public Map<String, Object> diagnostico(ApiRequest r) throws Exception {
    Config cfg = config(r.getId(), "CONSULTA");
    Map<String, Object> out = new LinkedHashMap<String, Object>();
    out.put("cofreDisponivel", CREDENTIALS.available());
    out.put("perfilHomologado", ConfigurationService.profileReady(cfg.bank));
    out.put("contratoAprovado", cfg.contractApproved);
    out.put("origemCertificado", cfg.effectiveCertificateSource());
    try {
      Credentials credentials = CREDENTIALS.load(cfg.id);
      br.com.zaya.apiboletos.security.TlsMaterial.context(credentials);
      out.put("certificadoDisponivel", true);
      out.put("certificadoFingerprint", credentials.tls.fingerprint);
      out.put("certificadoValidoAte", credentials.tls.validUntil);
      out.put(
          "diagnosticoAtual",
          CONFIGURATIONS
              .fingerprint(cfg)
              .equals(DB.transaction(c -> REPOSITORY.diagnosis(c, cfg.id))));
    } catch (Exception e) {
      out.put("certificadoDisponivel", false);
      out.put("diagnosticoAtual", false);
      out.put(
          "orientacao",
          "Verifique credenciais e certificado da origem selecionada; execute novo diagnóstico.");
    }
    out.put("processamentoGlobal", enabled());
    return out;
  }

  public Map<String, Object> ativar(ApiRequest r) throws Exception {
    Config cfg = config(r.getId(), "ADMIN");
    require(enabled(), "HABILITE_ZYCBHABIL_APOS_HOMOLOGACAO");
    CONFIGURATIONS.activate(cfg.id, Authorization.user());
    return map(DB.transaction(c -> REPOSITORY.config(c, cfg.id)));
  }

  public Map<String, Object> coleta(ApiRequest r) throws Exception {
    Config cfg = config(r.getId(), "ADMIN");
    String enabled = r.getValues().get("enabled");
    require("true".equals(enabled) || "false".equals(enabled), "OPCAO_COLETA_INVALIDA");
    DB.transaction(
        c -> {
          Config live = REPOSITORY.config(c, cfg.id);
          live.collectEnabled = Boolean.parseBoolean(enabled);
          live.revision++;
          REPOSITORY.saveConfig(c, live);
          REPOSITORY.audit(c, live.id, Authorization.user(), "CONFIGURAR_COLETA", live.id, enabled);
          return null;
        });
    return map(DB.transaction(c -> REPOSITORY.config(c, cfg.id)));
  }

  public Map<String, Object> reprocessar(ApiRequest r) throws Exception {
    Operation op = DB.transaction(c -> REPOSITORY.operation(c, r.getId()));
    require(op != null, "OPERACAO_AUSENTE");
    Charge ch = DB.transaction(c -> REPOSITORY.charge(c, op.chargeId));
    config(ch.configId, "OPERADOR");
    DB.transaction(
        c -> {
          require(
              update(
                      c,
                      "UPDATE ZYCB_OPER SET STATUS='PENDENTE',PROXEXEC=? WHERE ID=? AND"
                          + " STATUS='EXCECAO' AND DHENVIO IS NULL",
                      Repository.now(),
                      op.id)
                  == 1,
              "OPERACAO_ENVIADA_EXIGE_CONSULTA");
          REPOSITORY.audit(
              c,
              ch.configId,
              Authorization.user(),
              "REPROCESSAR_NAO_ENVIADA",
              op.id,
              "Sem envio anterior registrado");
          return null;
        });
    return Collections.<String, Object>singletonMap("enfileirado", true);
  }

  public Map<String, Object> pausar(ApiRequest r) throws Exception {
    Config cfg = config(r.getId(), "ADMIN");
    CONFIGURATIONS.pause(cfg.id, Authorization.user());
    return map(DB.transaction(c -> REPOSITORY.config(c, cfg.id)));
  }

  public List<Map<String, Object>> configuracoes(ApiRequest r) throws Exception {
    Authorization.require(r.getCompany(), "CONSULTA");
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (Config cfg : DB.transaction(c -> REPOSITORY.configs(c)))
      if (cfg.company == r.getCompany()) result.add(map(cfg));
    return result;
  }

  public Map<String, Object> registrar(ApiRequest r) throws Exception {
    Config cfg = config(r.getId(), "OPERADOR");
    require(enabled(), "PROCESSAMENTO_GLOBAL_DESABILITADO");
    Title title = FINANCIAL.title(r.getNuFin());
    String id = DB.transaction(c -> ENGINE.enqueue(c, cfg, title, true, Authorization.user()));
    return Collections.<String, Object>singletonMap("id", id);
  }

  public Map<String, Object> instruir(ApiRequest r) throws Exception {
    Charge ch = DB.transaction(c -> REPOSITORY.charge(c, r.getId()));
    require(ch != null, "COBRANCA_AUSENTE");
    config(ch.configId, "OPERADOR");
    String id =
        ENGINE.instruct(
            ch.id, Command.valueOf(r.getCommand()), r.getValues(), Authorization.user());
    return Collections.<String, Object>singletonMap("id", id);
  }

  public Map<String, Object> reemitir(ApiRequest r) throws Exception {
    Charge ch = DB.transaction(c -> REPOSITORY.charge(c, r.getId()));
    require(ch != null, "COBRANCA_AUSENTE");
    config(ch.configId, "OPERADOR");
    String id = ENGINE.reissue(ch.id, Authorization.user());
    return Collections.<String, Object>singletonMap("id", id);
  }

  public Map<String, Object> consultarResultado(ApiRequest r) throws Exception {
    Operation op = DB.transaction(c -> REPOSITORY.operation(c, r.getId()));
    require(op != null, "OPERACAO_AUSENTE");
    Charge ch = DB.transaction(c -> REPOSITORY.charge(c, op.chargeId));
    config(ch.configId, "OPERADOR");
    ENGINE.reconcile(op.id, Authorization.user());
    return Collections.<String, Object>singletonMap("enfileirado", true);
  }

  public List<Map<String, Object>> monitor(ApiRequest r) throws Exception {
    Authorization.require(r.getCompany(), "CONSULTA");
    String kind = r.getCommand();
    if ("COBRANCAS".equals(kind))
      return DB.transaction(
          c ->
              rows(
                  c,
                  200,
                  "SELECT B.ID,B.NUFIN,B.GERACAO,B.ESTADO,B.IDCFG FROM ZYCB_COB B JOIN ZYCB_CFG G"
                      + " ON G.ID=B.IDCFG WHERE G.CODEMP=? ORDER BY B.NUFIN DESC",
                  r.getCompany()));
    if ("OPERACOES".equals(kind))
      return DB.transaction(
          c ->
              rows(
                  c,
                  200,
                  "SELECT O.ID,B.ID AS IDCOB,B.NUFIN,O.TIPO,O.STATUS,O.TENTATIVAS,O.ERRO,O.PROXEXEC"
                      + " FROM ZYCB_OPER O JOIN ZYCB_COB B ON B.ID=O.IDCOB JOIN ZYCB_CFG G ON"
                      + " G.ID=B.IDCFG WHERE G.CODEMP=? ORDER BY O.DHCAD DESC",
                  r.getCompany()));
    if ("RECEBIMENTOS".equals(kind))
      return DB.transaction(
          c ->
              rows(
                  c,
                  200,
                  "SELECT R.ID,B.NUFIN,R.STATUS,R.DHCAD FROM ZYCB_REC R JOIN ZYCB_COB B ON"
                      + " B.ID=R.IDCOB JOIN ZYCB_CFG G ON G.ID=B.IDCFG WHERE G.CODEMP=? ORDER BY"
                      + " R.DHCAD DESC",
                  r.getCompany()));
    if ("AUDITORIA".equals(kind))
      return DB.transaction(
          c ->
              rows(
                  c,
                  200,
                  "SELECT A.CODUSU,A.ACAO,A.OBJETO,A.DETALHE,A.DHCAD FROM ZYCB_AUDIT A JOIN"
                      + " ZYCB_CFG G ON G.ID=A.IDCFG WHERE G.CODEMP=? ORDER BY A.DHCAD DESC",
                  r.getCompany()));
    throw new IllegalArgumentException("MONITOR_INVALIDO");
  }

  public Map<String, Object> concederAcesso(ApiRequest r) throws Exception {
    long actor = Authorization.require(r.getCompany(), "ADMIN");
    require(
        Arrays.asList("ADMIN", "OPERADOR", "CONSULTA").contains(r.getProfile())
            && r.getUserId() > 0,
        "PERFIL_INVALIDO");
    DB.transaction(
        c -> {
          require(
              !rows(c, 1, "SELECT CODUSU FROM TSIUSU WHERE CODUSU=? AND ATIVO='S'", r.getUserId())
                  .isEmpty(),
              "USUARIO_INATIVO");
          if (update(
                  c,
                  "UPDATE ZYCB_ACESSO SET PERFIL=? WHERE CODUSU=? AND CODEMP=?",
                  r.getProfile(),
                  r.getUserId(),
                  r.getCompany())
              == 0)
            update(
                c,
                "INSERT INTO ZYCB_ACESSO (CODUSU,CODEMP,PERFIL) VALUES (?,?,?)",
                r.getUserId(),
                r.getCompany(),
                r.getProfile());
          REPOSITORY.audit(
              c,
              null,
              actor,
              "CONCEDER_ACESSO",
              String.valueOf(r.getUserId()),
              "Empresa " + r.getCompany());
          return null;
        });
    return Collections.<String, Object>singletonMap("salvo", true);
  }
}
