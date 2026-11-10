package br.com.zaya.apiboletos.domain;

import java.math.BigDecimal;
import java.util.*;

/** Valores independentes dos contratos bancários; segredos não pertencem ao domínio. */
public final class Models {
  private Models() {}

  public enum Bank {
    ITAU,
    SANTANDER
  }

  public enum State {
    PENDENTE,
    PROCESSANDO,
    AGUARDANDO,
    INCERTO,
    REJEITADO,
    CONCLUIDO,
    EXCECAO
  }

  public enum ChargeState {
    AGUARDANDO_REGISTRO,
    REGISTRADA,
    CANCELADA,
    PAGA,
    DIVERGENTE
  }

  public enum Command {
    REGISTRAR,
    VENCIMENTO,
    VALOR,
    JUROS,
    MULTA,
    DESCONTO,
    ABATIMENTO,
    CANCELAR,
    ENTREGAR
  }

  public static final class Config {
    public String id, bank, environment = "HOMOLOGACAO", beneficiary, wallet, covenant, workspace;

    /** Ausência preserva configurações legadas por upload; novas usam SANKHYA. */
    public String certificateSource;

    public String effectiveCertificateSource() {
      return certificateSource == null ? "UPLOAD" : certificateSource;
    }

    public long company,
        account,
        technicalUser,
        settlementTop,
        bankEntry,
        smtp,
        revision,
        cutover,
        activatedAt,
        initialNumber;
    public boolean active, sendEnabled, collectEnabled, contractApproved;
    public int batchSize = 20, collectMinutes = 15, fineDays = 1;
    public BigDecimal monthlyInterestPct = BigDecimal.ZERO, finePct = BigDecimal.ZERO;
    public Set<Long> titleTypes = new HashSet<Long>();

    /** NFE/NFSE/CONFIRMADA é selecionado por TOP; ausência bloqueia emissão automática. */
    public Map<Long, String> fiscalRules = new HashMap<Long, String>();

    public String emailSubject = "Boleto de cobrança",
        emailBody = "Segue o boleto registrado em anexo.";

    public void validate() {
      require(id != null && id.matches("[a-f0-9]{32}"), "CONFIGURACAO_INVALIDA");
      Bank.valueOf(bank);
      require(
          Arrays.asList("SANKHYA", "UPLOAD").contains(effectiveCertificateSource()),
          "ORIGEM_CERTIFICADO_INVALIDA");
      require(
          "HOMOLOGACAO".equals(environment) || "PRODUCAO".equals(environment), "AMBIENTE_INVALIDO");
      require(
          company > 0 && account > 0 && technicalUser > 0 && settlementTop > 0 && bankEntry > 0,
          "REGRAS_FINANCEIRAS_INCOMPLETAS");
      require(initialNumber >= 1 && initialNumber <= 99999999, "NUMERACAO_INICIAL_INVALIDA");
      require(
          monthlyInterestPct != null
              && finePct != null
              && monthlyInterestPct.signum() >= 0
              && finePct.signum() >= 0
              && monthlyInterestPct.scale() <= 2
              && finePct.scale() <= 2
              && monthlyInterestPct.compareTo(new BigDecimal("999.99")) <= 0
              && finePct.compareTo(new BigDecimal("999.99")) <= 0,
          "ENCARGOS_INVALIDOS");
      require(fineDays >= 1 && fineDays <= 999, "DIAS_MULTA_INVALIDOS");
      require(
          batchSize >= 1 && batchSize <= 100 && collectMinutes >= 1 && collectMinutes <= 1440,
          "LIMITES_INVALIDOS");
      require(titleTypes != null && !titleTypes.isEmpty(), "TIPOS_TITULO_AUSENTES");
      for (Long type : titleTypes) require(type != null && type > 0, "TIPO_TITULO_INVALIDO");
      require(fiscalRules != null && !fiscalRules.isEmpty(), "REGRAS_FISCAIS_AUSENTES");
      for (String v : fiscalRules.values())
        require(Arrays.asList("NFE", "NFSE", "CONFIRMADA").contains(v), "REGRA_FISCAL_INVALIDA");
      if (Bank.ITAU.name().equals(bank))
        require(digits(beneficiary, 12, 12) && digits(wallet, 3, 3), "CONTRATO_ITAU_INVALIDO");
      else
        require(
            digits(covenant, 1, 9) && workspace != null && workspace.matches("[A-Za-z0-9-]{1,80}"),
            "CONTRATO_SANTANDER_INVALIDO");
    }
  }

  public static final class Payer {
    public String document, name, address, number, neighborhood, city, state, zip, email;

    public void validate() {
      require(
          document != null && (document.matches("[0-9]{11}") || document.matches("[0-9]{14}")),
          "DOCUMENTO_PAGADOR_INVALIDO");
      require(
          zip != null && zip.matches("[0-9]{8}") && state != null && state.matches("[A-Z]{2}"),
          "CEP_UF_INVALIDOS");
      for (String v : Arrays.asList(name, address, neighborhood, city, state, zip))
        require(v != null && !v.trim().isEmpty(), "ENDERECO_PAGADOR_INCOMPLETO");
    }
  }

  public static final class Title {
    public long nuFin, company, account, titleType, note, top;
    public boolean income, provisional, settled, noteConfirmed, nfeAuthorized, nfseAuthorized;
    public boolean bankSlipType = true;
    public String dueDate, issueDate, nativeBankId;
    public BigDecimal amount;
    public Payer payer;

    public void validate(Config c, boolean manual) {
      require(company == c.company && account == c.account, "CONTA_EMPRESA_DIVERGENTE");
      require(
          income && !provisional && !settled && amount != null && amount.signum() > 0,
          "TITULO_INAPTO");
      require(bankSlipType, "TIPO_NAO_BOLETO");
      require(c.titleTypes.contains(titleType), "TIPO_TITULO_NAO_HABILITADO");
      require(dueDate != null && issueDate != null, "DATAS_AUSENTES");
      java.time.LocalDate.parse(dueDate);
      java.time.LocalDate.parse(issueDate);
      require(
          nativeBankId == null || nativeBankId.trim().isEmpty(),
          "IDENTIDADE_BANCARIA_PREEXISTENTE");
      if (note > 0) {
        require(noteConfirmed, "NOTA_NAO_CONFIRMADA");
        String rule = c.fiscalRules.get(top);
        require(rule != null, "TOP_NAO_HABILITADA");
        if ("NFE".equals(rule)) require(nfeAuthorized, "NFE_NAO_AUTORIZADA");
        if ("NFSE".equals(rule)) require(nfseAuthorized, "NFSE_NAO_AUTORIZADA");
      } else require(manual, "FINANCEIRO_MANUAL_REQUER_LIBERACAO");
      require(manual || nuFin > c.cutover, "TITULO_ANTERIOR_ATIVACAO");
      require(payer != null, "PAGADOR_AUSENTE");
      payer.validate();
      require(amount.scale() <= 2, "VALOR_COM_PRECISAO_INVALIDA");
    }
  }

  public static final class Identity {
    public String bankId, ourNumber, digitableLine, barcode, registrationDate, externalReference;
  }

  public static final class Charge {
    public String id, configId;
    public long nuFin;
    public int generation = 1;
    public ChargeState state = ChargeState.AGUARDANDO_REGISTRO;
    public Config config;
    public Title snapshot;
    public Identity identity;
  }

  public static final class Operation {
    public String id, chargeId, key, lease, tracking, error;
    public Command command;
    public State state = State.PENDENTE;
    public int attempts;
    public long sentAt, sequence;
    public Map<String, String> values = new LinkedHashMap<String, String>();
  }

  public static final class Result {
    public State state;
    public Identity identity;
    public String tracking, code;
    public boolean cancelled;
    public List<Payment> payments = new ArrayList<Payment>();

    public static Result of(State state) {
      Result r = new Result();
      r.state = state;
      return r;
    }
  }

  public static final class Payment {
    public String eventId, chargeId, date, creditDate, rawStatus;
    public BigDecimal total,
        interest = BigDecimal.ZERO,
        fine = BigDecimal.ZERO,
        discount = BigDecimal.ZERO,
        abatement = BigDecimal.ZERO;
    public boolean definitive, reversed, partial;

    public BigDecimal principal() {
      return total.subtract(interest).subtract(fine).add(discount).add(abatement);
    }
  }

  public static final class Credentials {
    public String clientId, clientSecret, certificateBase64, certificatePassword;
    // Contexto exclusivamente interno: Gson nunca aceita nem persiste estes campos.
    public transient String certificateSource;
    public transient long certificateCompany;
    public transient br.com.zaya.apiboletos.security.TlsMaterial.Resolved tls;

    @Override
    public String toString() {
      return "Credentials[PROTEGIDAS]";
    }
  }

  public static void require(boolean ok, String code) {
    if (!ok) throw new IllegalArgumentException(code);
  }

  public static boolean digits(String s, int min, int max) {
    return s != null && s.matches("[0-9]{" + min + "," + max + "}");
  }

  public static String id() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
