package br.com.zaya.apiboletos.banks.santander;

import static br.com.zaya.apiboletos.banks.BankJson.*;
import static br.com.zaya.apiboletos.domain.Models.require;

import br.com.zaya.apiboletos.banks.*;
import br.com.zaya.apiboletos.domain.Models.*;
import br.com.zaya.apiboletos.ports.BankConnector;
import com.google.gson.*;
import java.util.*;

/** Perfil Cobrança v2.6; parâmetros privados/variações devem ser homologados antes de ativar. */
public final class SantanderConnector implements BankConnector {
  private final AuthenticatedClient http;

  public SantanderConnector(AuthenticatedClient h) {
    http = h;
  }

  public String bank() {
    return "SANTANDER";
  }

  private String slips(Config c) {
    return "/workspaces/" + c.workspace + "/bank_slips";
  }

  public void diagnose(Config c, Credentials k) throws Exception {
    http.diagnose(c, k);
    http.call(c, k, "GET", "/workspaces/" + c.workspace, null, "diagnostico");
  }

  public Result register(Config c, Credentials k, Charge ch, String correlation) throws Exception {
    Title t = ch.snapshot;
    Payer p = t.payer;
    JsonObject body =
        obj(
            "nsuCode",
            ch.identity.ourNumber,
            "nsuDate",
            ch.identity.registrationDate,
            "environment",
            "PRODUCAO".equals(c.environment) ? "PRODUCAO" : "TESTE",
            "covenantCode",
            c.covenant,
            "bankNumber",
            ch.identity.ourNumber,
            "clientNumber",
            ch.identity.externalReference,
            "dueDate",
            t.dueDate,
            "issueDate",
            t.issueDate,
            "nominalValue",
            t.amount,
            "documentKind",
            "DUPLICATA_MERCANTIL",
            "payer",
            obj(
                "documentType",
                p.document.length() == 14 ? "CNPJ" : "CPF",
                "documentNumber",
                p.document,
                "name",
                p.name,
                "address",
                p.address,
                "neighborhood",
                p.neighborhood,
                "city",
                p.city,
                "state",
                p.state,
                "zipCode",
                p.zip));
    if (c.monthlyInterestPct.signum() > 0)
      body.add("interest", obj("interestPercentage", c.monthlyInterestPct));
    if (c.finePct.signum() > 0) {
      body.addProperty("finePercentage", c.finePct);
      body.addProperty(
          "fineDate", java.time.LocalDate.parse(t.dueDate).plusDays(c.fineDays).toString());
    }
    return response(http.call(c, k, "POST", slips(c), body.toString(), correlation), ch);
  }

  public Result instruct(Config c, Credentials k, Charge ch, Operation op) throws Exception {
    JsonObject body = obj("covenantCode", c.covenant, "bankNumber", ch.identity.ourNumber);
    switch (op.command) {
      case CANCELAR:
        body.addProperty("operation", "BAIXAR");
        break;
      case VENCIMENTO:
        body.addProperty("dueDate", op.values.get("data_vencimento"));
        break;
      case VALOR:
        body.addProperty("nominalValue", op.values.get("valor_nominal"));
        break;
      case ABATIMENTO:
        body.addProperty("deductionValue", op.values.get("valor_abatimento"));
        break;
      case MULTA:
        require(
            op.values.containsKey("percentual_multa") && !op.values.containsKey("valor_multa"),
            "SANTANDER_MULTA_FIXA_NAO_SUPORTADA");
        body.addProperty("finePercentage", op.values.get("percentual_multa"));
        body.addProperty("fineDate", op.values.get("data_multa"));
        break;
      case JUROS:
        require(
            op.values.containsKey("percentual_juros") && !op.values.containsKey("valor_juros"),
            "SANTANDER_JURO_FIXO_NAO_SUPORTADO");
        body.add("interest", obj("interestPercentage", op.values.get("percentual_juros")));
        break;
      case DESCONTO:
        body.add(
            "discount",
            obj(
                "type",
                "VALOR_DATA_FIXA",
                "discountOne",
                obj(
                    "value",
                    op.values.get("valor_desconto"),
                    "limitDate",
                    op.values.get("data_desconto"))));
        break;
      default:
        throw new IllegalArgumentException("INSTRUCAO_NAO_SUPORTADA");
    }
    Result result = response(http.call(c, k, "PATCH", slips(c), body.toString(), op.id), ch);
    // A instrução aceita não comprova cancelamento. A consulta resolve seu resultado.
    if (op.command == Command.CANCELAR) result.state = State.AGUARDANDO;
    return result;
  }

  public Result query(Config c, Credentials k, Charge ch, String tracking) throws Exception {
    String path =
        tracking != null
            ? tracking
            : "/bills/" + c.covenant + "." + ch.identity.ourNumber + "?tipoConsulta=bankslip";
    return response(http.call(c, k, "GET", path, null, ch.id), ch);
  }

  public List<Payment> receipts(Config c, Credentials k, Charge ch) throws Exception {
    return response(
            http.call(
                c,
                k,
                "GET",
                "/bills/" + c.covenant + "." + ch.identity.ourNumber + "?tipoConsulta=settlement",
                null,
                ch.id),
            ch)
        .payments;
  }

  public static Result response(HttpTransport.Response response, Charge ch) {
    Result r = Result.of(State.AGUARDANDO);
    if (response.status == 202) {
      r.tracking = response.header("Content-Location");
      return r;
    }
    JsonObject o = unwrap(parse(response.body));
    String bankNumber = text(o, "bankNumber");
    require(
        bankNumber == null || ch.identity.ourNumber.equals(bankNumber),
        "IDENTIDADE_SANTANDER_DIVERGENTE");
    if (bankNumber != null && ch.identity.ourNumber.equals(bankNumber)) {
      Identity i = new Identity();
      i.ourNumber = bankNumber;
      i.bankId = text(o, "id");
      i.barcode = text(o, "barCode");
      i.digitableLine = text(o, "digitableLine");
      i.registrationDate = ch.identity.registrationDate;
      i.externalReference = ch.identity.externalReference;
      if (i.barcode != null && i.digitableLine != null) {
        r.identity = i;
        r.state = State.CONCLUIDO;
      }
    }
    String status = text(o, "status");
    // BAIXADO isolado pode significar Pix e nunca comprova cancelamento nem pagamento.
    if ("BAIXADO".equalsIgnoreCase(status) && "BAIXA_COMANDADA".equals(text(o, "writeOffReason"))) {
      r.cancelled = true;
      r.state = State.CONCLUIDO;
    }
    if ("LIQUIDADO".equalsIgnoreCase(status) || "LIQUIDADO PARCIALMENTE".equalsIgnoreCase(status)) {
      require(bankNumber != null, "SANTANDER_LIQUIDACAO_SEM_IDENTIDADE");
      JsonObject s = object(o.get("settlement"));
      Payment p = new Payment();
      p.chargeId = ch.id;
      p.eventId = text(s, "id");
      p.date = text(s, "paymentDate");
      p.total = money(s, "paidValue");
      p.interest = money(s, "interestValue");
      p.fine = money(s, "fineValue");
      p.discount = money(s, "discountValue");
      p.abatement = money(s, "deductionValue");
      p.partial = !"LIQUIDADO".equalsIgnoreCase(status);
      p.rawStatus = status;
      require(
          p.date != null && p.total.signum() > 0, "SANTANDER_LIQUIDACAO_EXIGE_CONTRATO_CONFIRMADO");
      p.definitive = true;
      r.payments.add(p);
    }
    return r;
  }
}
