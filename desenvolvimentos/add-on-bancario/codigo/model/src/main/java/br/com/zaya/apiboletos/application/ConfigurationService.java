package br.com.zaya.apiboletos.application;

import static br.com.zaya.apiboletos.domain.Models.require;

import br.com.zaya.apiboletos.domain.Models;
import br.com.zaya.apiboletos.domain.Models.*;
import br.com.zaya.apiboletos.persistence.*;
import br.com.zaya.apiboletos.ports.*;

public final class ConfigurationService {
  private final Database db;
  private final Repository repo;
  private final CredentialStore secrets;
  private final ConnectorRegistry banks;
  private final br.com.zaya.apiboletos.security.TlsMaterial.NativeProvider certificates;

  public ConfigurationService(
      Database db, Repository repo, CredentialStore secrets, ConnectorRegistry banks) {
    this(
        db, repo, secrets, banks, new br.com.zaya.apiboletos.security.SankhyaCertificateProvider());
  }

  public ConfigurationService(
      Database db,
      Repository repo,
      CredentialStore secrets,
      ConnectorRegistry banks,
      br.com.zaya.apiboletos.security.TlsMaterial.NativeProvider certificates) {
    this.certificates = certificates;
    this.db = db;
    this.repo = repo;
    this.secrets = secrets;
    this.banks = banks;
  }

  public Config save(Config v, long user) throws Exception {
    if (v.id == null) {
      v.id = Models.id();
      if (v.certificateSource == null) v.certificateSource = "SANKHYA";
    }
    v.validate();
    return db.transaction(
        c -> {
          Config before = repo.config(c, v.id);
          if (before != null) {
            require(before.revision == v.revision, "CONFIGURACAO_MODIFICADA");
            require(
                before.company == v.company
                    && before.account == v.account
                    && before.bank.equals(v.bank)
                    && before.environment.equals(v.environment)
                    && java.util.Objects.equals(before.wallet, v.wallet)
                    && java.util.Objects.equals(before.covenant, v.covenant)
                    && java.util.Objects.equals(before.workspace, v.workspace)
                    && java.util.Objects.equals(before.beneficiary, v.beneficiary),
                "IDENTIDADE_CONFIGURACAO_IMUTAVEL");
            v.cutover = before.cutover;
            v.activatedAt = before.activatedAt;
            require(v.initialNumber == before.initialNumber, "NUMERACAO_INICIAL_IMUTAVEL");
          } else {
            require(v.revision == 0, "REVISAO_INVALIDA");
            v.cutover = 0;
            v.activatedAt = 0;
          }
          v.active = false;
          v.sendEnabled = false;
          v.collectEnabled = before != null && before.collectEnabled;
          v.revision++;
          repo.saveConfig(c, v);
          repo.invalidateDiagnosis(c, v.id);
          repo.audit(c, v.id, user, "SALVAR_CONFIGURACAO", v.id, "Revalidacao necessaria");
          return v;
        });
  }

  public String fingerprint(Config cfg) throws Exception {
    Config normalized = Json.read(Json.write(cfg), Config.class);
    normalized.active = false;
    normalized.sendEnabled = false;
    normalized.collectEnabled = false;
    normalized.cutover = 0;
    normalized.activatedAt = 0;
    normalized.revision = 0;
    String certificate = "";
    if ("SANKHYA".equals(cfg.effectiveCertificateSource()))
      certificate =
          br.com.zaya.apiboletos.security.TlsMaterial.fingerprint(
              secrets.load(cfg.id), certificates);
    return Json.hash(Json.write(normalized) + ":" + secrets.revision(cfg.id) + ":" + certificate);
  }

  public void diagnose(String id, long user) throws Exception {
    require(secrets.available(), "COFRE_CLOUD_INDISPONIVEL");
    Config cfg = db.transaction(c -> repo.config(c, id));
    require(cfg != null, "CONFIGURACAO_AUSENTE");
    cfg.validate();
    String hash = fingerprint(cfg);
    banks.get(cfg.bank).diagnose(cfg, secrets.load(id));
    db.transaction(
        c -> {
          Config current = repo.config(c, id);
          require(fingerprint(current).equals(hash), "CONFIGURACAO_MODIFICADA_DURANTE_DIAGNOSTICO");
          repo.diagnose(c, id, hash);
          repo.audit(
              c, id, user, "DIAGNOSTICO", id, "Conexao e consulta verificadas; nenhuma emissao");
          return null;
        });
  }

  public void activate(String id, long user) throws Exception {
    require(secrets.available(), "COFRE_CLOUD_INDISPONIVEL");
    db.transaction(
        c -> {
          Config cfg = repo.config(c, id);
          require(cfg != null, "CONFIGURACAO_AUSENTE");
          cfg.validate();
          require(cfg.contractApproved, "HOMOLOGACAO_CONTA_NAO_CONFIRMADA");
          require(
              fingerprint(cfg).equals(repo.diagnosis(c, id)),
              "DIAGNOSTICO_AUSENTE_OU_DESATUALIZADO");
          require(profileReady(cfg.bank), "PERFIL_BANCARIO_AGUARDA_HOMOLOGACAO");
          if (cfg.activatedAt == 0) {
            cfg.cutover = repo.maxTitle(c);
            cfg.activatedAt = System.currentTimeMillis();
          }
          cfg.active = true;
          cfg.sendEnabled = true;
          cfg.collectEnabled = true;
          cfg.revision++;
          repo.saveConfig(c, cfg);
          repo.audit(
              c, id, user, "ATIVAR", id, "Novos titulos apos marco; sem importacao de legado");
          return null;
        });
  }

  /**
   * Manifesto de release: somente alterado após homologação de contrato, nunca por parâmetro de
   * operador.
   */
  public static boolean profileReady(String bank) {
    try (java.io.InputStream in =
        ConfigurationService.class.getResourceAsStream("/bank-profiles.properties")) {
      java.util.Properties p = new java.util.Properties();
      if (in == null) return false;
      p.load(in);
      return "true".equals(p.getProperty(bank + ".homologado"));
    } catch (Exception e) {
      return false;
    }
  }

  public void pause(String id, long user) throws Exception {
    db.transaction(
        c -> {
          Config cfg = repo.config(c, id);
          require(cfg != null, "CONFIGURACAO_AUSENTE");
          cfg.sendEnabled = false;
          cfg.revision++;
          repo.saveConfig(c, cfg);
          repo.audit(c, id, user, "PAUSAR_ENVIO", id, "Coleta de recebimentos preservada");
          return null;
        });
  }
}
