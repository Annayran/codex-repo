package br.com.zaya.apiboletos.banks.itau;

import static br.com.zaya.apiboletos.banks.BankJson.*;
import static br.com.zaya.apiboletos.domain.Models.require;

import br.com.zaya.apiboletos.banks.*;
import br.com.zaya.apiboletos.domain.Models.*;
import br.com.zaya.apiboletos.ports.BankConnector;
import com.google.gson.*;
import java.util.*;

/** Perfil do OpenAPI 2.75.150 fornecido. Nenhum endpoint pode ser informado pelo operador. */
public final class ItauConnector implements BankConnector {
  private final AuthenticatedClient http;

  public ItauConnector(AuthenticatedClient h) {
    http = h;
  }

  public String bank() {
    return "ITAU";
  }

  public void diagnose(Config c, Credentials k) throws Exception {
    http.diagnose(c, k);
    http.call(
        c,
        k,
        "GET",
        "/boletos?id_beneficiario=" + c.beneficiary + "&view=basic",
        null,
        "diagnostico");
  }

  public Result register(Config c, Credentials k, Charge ch, String correlation) throws Exception {
    Title t = ch.snapshot;
    Payer p = t.payer;
    JsonObject person =
        obj(
            "nome_pessoa",
            p.name,
            "tipo_pessoa",
            p.document.length() == 14
                ? obj(
                    "codigo_tipo_pessoa",
                    "J",
                    "numero_cadastro_nacional_pessoa_juridica",
                    p.document)
                : obj("codigo_tipo_pessoa", "F", "numero_cadastro_pessoa_fisica", p.document));
    JsonObject address =
        obj(
            "nome_logradouro",
            p.address,
            "nome_bairro",
            p.neighborhood,
            "nome_cidade",
            p.city,
            "sigla_UF",
            p.state,
            "numero_CEP",
            p.zip);
    JsonArray individual = new JsonArray();
    individual.add(
        obj(
            "numero_nosso_numero",
            ch.identity.ourNumber,
            "data_vencimento",
            t.dueDate,
            "valor_titulo",
            t.amount.toPlainString(),
            "texto_seu_numero",
            ch.identity.externalReference));
    JsonObject body =
        obj(
            "etapa_processo_boleto",
            "efetivacao",
            "codigo_canal_operacao",
            "API",
            "beneficiario",
            obj("id_beneficiario", c.beneficiary),
            "dado_boleto",
            obj(
                "tipo_boleto",
                "a vista",
                "codigo_carteira",
                c.wallet,
                "codigo_especie",
                "01",
                "codigo_aceite",
                "N",
                "data_emissao",
                t.issueDate,
                "pagamento_parcial",
                false,
                "pagador",
                obj("pessoa", person, "endereco", address),
                "dados_individuais_boleto",
                individual));
    JsonObject debt = body.getAsJsonObject("dado_boleto");
    if (c.monthlyInterestPct.signum() > 0)
      debt.add(
          "juros",
          obj(
              "codigo_tipo_juros",
              "90",
              "percentual_juros",
              c.monthlyInterestPct.toPlainString(),
              "data_juros",
              java.time.LocalDate.parse(t.dueDate).plusDays(1).toString()));
    if (c.finePct.signum() > 0)
      debt.add(
          "multa",
          obj(
              "codigo_tipo_multa",
              "02",
              "percentual_multa",
              c.finePct.toPlainString(),
              "quantidade_dias_multa",
              c.fineDays));
    return response(http.call(c, k, "POST", "/boletos", body.toString(), correlation), ch);
  }

  private String id(Config c, Charge ch) {
    require(ch.identity != null && ch.identity.ourNumber != null, "IDENTIDADE_AUSENTE");
    return c.beneficiary + c.wallet + ch.identity.ourNumber;
  }

  public Result instruct(Config c, Credentials k, Charge ch, Operation op) throws Exception {
    String field;
    switch (op.command) {
      case CANCELAR:
        field = "baixa";
        break;
      case VENCIMENTO:
        field = "data_vencimento";
        break;
      case VALOR:
        field = "valor_nominal";
        break;
      case JUROS:
        field = "juros";
        break;
      case MULTA:
        field = "multa";
        break;
      case DESCONTO:
        field = "desconto";
        break;
      case ABATIMENTO:
        field = "abatimento";
        break;
      default:
        throw new IllegalArgumentException("INSTRUCAO_NAO_SUPORTADA");
    }
    JsonObject body = null;
    if (op.command != Command.CANCELAR) {
      body = new JsonObject();
      for (Map.Entry<String, String> e : op.values.entrySet())
        body.addProperty(e.getKey(), e.getValue());
      if (op.command == Command.MULTA) {
        long days =
            java.time.temporal.ChronoUnit.DAYS.between(
                java.time.LocalDate.parse(ch.snapshot.dueDate),
                java.time.LocalDate.parse(op.values.get("data_multa")));
        require(days >= 1 && days <= 999, "DATA_MULTA_INVALIDA");
        body.remove("data_multa");
        body.addProperty("quantidade_dias_multa", days);
      }
      if (op.command == Command.DESCONTO) {
        JsonArray discounts = new JsonArray();
        discounts.add(
            obj(
                "valor_desconto",
                op.values.get("valor_desconto"),
                "data_desconto",
                op.values.get("data_desconto")));
        body = obj("codigo_tipo_desconto", "01", "descontos", discounts);
      }
    }
    Result r =
        response(
            http.call(
                c,
                k,
                "PATCH",
                "/boletos/" + id(c, ch) + "/" + field,
                body == null ? null : body.toString(),
                op.id),
            ch);
    if (op.command == Command.CANCELAR && r.state == State.CONCLUIDO) r.cancelled = true;
    return r;
  }

  public Result query(Config c, Credentials k, Charge ch, String tracking) throws Exception {
    if (tracking != null && !tracking.isEmpty())
      return response(http.call(c, k, "GET", tracking, null, ch.id), ch);
    String path =
        "/boletos?id_beneficiario="
            + c.beneficiary
            + "&codigo_carteira="
            + c.wallet
            + "&nosso_numero="
            + ch.identity.ourNumber
            + "&data_inclusao="
            + ch.identity.registrationDate
            + "&view=specific";
    return response(http.call(c, k, "GET", path, null, ch.id), ch);
  }

  public List<Payment> receipts(Config c, Credentials k, Charge ch) throws Exception {
    return query(c, k, ch, null).payments;
  }

  public static Result response(HttpTransport.Response response, Charge ch) {
    Result r = Result.of(State.AGUARDANDO);
    if (response.status == 202 || response.status == 303) {
      r.tracking = response.header(response.status == 303 ? "Location" : "Content-Location");
      return r;
    }
    if (response.status == 204) {
      r.state = State.CONCLUIDO;
      return r;
    }
    JsonElement raw = parse(response.body);
    JsonObject top = object(raw);
    if (top.has("value") && top.get("value").isJsonObject()) top = top.getAsJsonObject("value");
    JsonElement data = top.has("data") ? top.get("data") : raw;
    if (data.isJsonArray()) {
      JsonObject match = null;
      for (JsonElement e : data.getAsJsonArray()) {
        JsonObject o = object(e);
        JsonObject d = object(o.get("dado_boleto"));
        for (JsonElement x : array(d, "dados_individuais_boleto")) {
          if (ch.identity.ourNumber.equals(text(object(x), "numero_nosso_numero"))) {
            require(match == null, "CONSULTA_AMBIGUA");
            match = o;
          }
        }
      }
      if (match == null) return Result.of(State.INCERTO);
      data = match;
    }
    JsonObject boleto = object(data);
    String message = text(boleto, "mensagem");
    if (message != null) {
      String m =
          java.text.Normalizer.normalize(message, java.text.Normalizer.Form.NFD)
              .replaceAll("\\p{M}", "")
              .toUpperCase(Locale.ROOT);
      if (m.contains("AGUARD") || m.contains("APROVA")) return r;
    }
    JsonObject d = object(boleto.get("dado_boleto"));
    for (JsonElement e : array(d, "dados_individuais_boleto")) {
      JsonObject x = object(e);
      String our = text(x, "numero_nosso_numero");
      if (!ch.identity.ourNumber.equals(our)) continue;
      Identity i = new Identity();
      i.ourNumber = our;
      i.bankId = text(boleto, "id_boleto");
      i.barcode = text(x, "codigo_barras");
      i.digitableLine = text(x, "numero_linha_digitavel");
      i.registrationDate = ch.identity.registrationDate;
      i.externalReference = ch.identity.externalReference;
      if (i.barcode != null && i.digitableLine != null) {
        r.identity = i;
        r.state = State.CONCLUIDO;
      }
    }
    boolean paid = false, partial = false;
    for (JsonElement item : array(d, "dados_individuais_boleto")) {
      String status = text(object(item), "situacao_geral_boleto");
      if (status != null) {
        status = status.toUpperCase(Locale.ROOT);
        paid |= "LIQUIDADO".equals(status) || "PAGO".equals(status);
        partial |= status.contains("PARCIAL");
      }
    }
    for (JsonElement e : array(d, "pagamentos_cobranca")) {
      JsonObject x = object(e);
      Payment p = new Payment();
      p.chargeId = ch.id;
      p.date = text(x, "data_inclusao_pagamento");
      p.total = money(x, "valor_pago_total_cobranca");
      p.interest = money(x, "valor_pago_juro_cobranca");
      p.fine = money(x, "valor_pago_multa_cobranca");
      p.discount = money(x, "valor_pago_desconto_cobranca");
      p.abatement = money(x, "valor_pago_abatimento_cobranca");
      p.definitive = paid && p.total.signum() > 0 && p.date != null;
      p.partial = partial;
      p.eventId = text(x, "id_pagamento");
      p.rawStatus = "PAGAMENTO_CONSULTADO";
      r.payments.add(p);
    }
    return r;
  }
}
