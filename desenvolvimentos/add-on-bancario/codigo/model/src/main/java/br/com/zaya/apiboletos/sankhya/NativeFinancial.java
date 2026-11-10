package br.com.zaya.apiboletos.sankhya;

import static br.com.zaya.apiboletos.domain.Models.require;
import static br.com.zaya.apiboletos.persistence.Sql.*;

import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BoletoHelper;
import br.com.sankhya.modelcore.financeiro.helper.BaixaHelper;
import br.com.sankhya.modelcore.financeiro.util.DadosBaixa;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.apiboletos.domain.Models.*;
import br.com.zaya.apiboletos.persistence.*;
import br.com.zaya.apiboletos.ports.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public final class NativeFinancial implements FinancialPort, DocumentPort {
  public static void validateConfig(Config cfg) throws Exception {
    DynamicVO account = JapeFactory.dao("ContaBancaria").findByPK(BigDecimal.valueOf(cfg.account));
    require(account != null, "CONTA_INTERNA_AUSENTE");
    require(
        account.asBigDecimal("CODEMP") != null
            && account.asBigDecimal("CODEMP").longValue() == cfg.company,
        "CONTA_NAO_PERTENCE_EMPRESA");
    long expected = "ITAU".equals(cfg.bank) ? 341 : 33;
    require(
        account.asBigDecimal("CODBCO") != null
            && account.asBigDecimal("CODBCO").longValue() == expected,
        "BANCO_DA_CONTA_DIVERGENTE");
    DynamicVO user = JapeFactory.dao("Usuario").findByPK(BigDecimal.valueOf(cfg.technicalUser));
    require(user != null && "S".equals(user.asString("ATIVO")), "USUARIO_TECNICO_INATIVO");
  }

  public Title title(long nuFin) throws Exception {
    return SankhyaDatabase.current(c -> load(c, nuFin));
  }

  public static Title load(Connection c, long fin) throws Exception {
    String amount =
        "(COALESCE(F.VLRDESDOB,0)+COALESCE(F.DESPCART,0)+COALESCE(F.VLRVENDOR,0)-COALESCE(F.VLRDESC,0)-CASE"
            + " WHEN F.IRFRETIDO='S' THEN COALESCE(F.VLRIRF,0) ELSE 0 END-CASE WHEN F.ISSRETIDO='S'"
            + " THEN COALESCE(F.VLRISS,0) ELSE 0 END-CASE WHEN F.INSSRETIDO='S' THEN"
            + " COALESCE(F.VLRINSS,0) ELSE 0 END-COALESCE(F.CARTAODESC,0)+COALESCE((SELECT"
            + " ROUND(SUM(I.VALOR*I.TIPIMP),2) FROM TGFIMF I WHERE"
            + " I.NUFIN=F.NUFIN),0)+COALESCE(F.VLRVARCAMBIAL,0))";
    List<Map<String, Object>> rows =
        rows(
            c,
            1,
            "SELECT F.*, "
                + amount
                + " AS VALORCOBRANCA,T.ESPDOC,T.ATIVO AS"
                + " TITATIVO,T.PROIBIMPBOL,P.CGC_CPF,P.NOMEPARC,P.EMAIL,P.NUMEND,P.CEP,E.NOMEEND,B.NOMEBAI,C.NOMECID,U.UF,N.STATUSNOTA,N.STATUSNFE,N.STATUSNFSE,N.CODTIPOPER"
                + " AS TOPNOTA FROM TGFFIN F JOIN TGFTIT T ON T.CODTIPTIT=F.CODTIPTIT JOIN TGFPAR P"
                + " ON P.CODPARC=F.CODPARC LEFT JOIN TSIEND E ON E.CODEND=P.CODEND LEFT JOIN TSIBAI"
                + " B ON B.CODBAI=P.CODBAI LEFT JOIN TSICID C ON C.CODCID=P.CODCID LEFT JOIN TSIUFS"
                + " U ON U.CODUF=C.UF LEFT JOIN TGFCAB N ON N.NUNOTA=F.NUNOTA WHERE F.NUFIN=?",
            fin);
    require(!rows.isEmpty(), "FINANCEIRO_AUSENTE");
    Map<String, Object> r = rows.get(0);
    Title t = new Title();
    t.nuFin = fin;
    t.company = number(r, "CODEMP");
    t.account = number(r, "CODCTABCOINT");
    t.titleType = number(r, "CODTIPTIT");
    t.note = number(r, "NUNOTA");
    t.top = number(r, "TOPNOTA");
    t.income = number(r, "RECDESP") == 1;
    t.provisional = "S".equals(text(r, "PROVISAO"));
    t.settled = r.get("DHBAIXA") != null;
    t.bankSlipType =
        "BO".equalsIgnoreCase(text(r, "ESPDOC"))
            && "S".equals(text(r, "TITATIVO"))
            && !"S".equals(text(r, "PROIBIMPBOL"));
    t.noteConfirmed = "L".equals(text(r, "STATUSNOTA"));
    t.nfeAuthorized = "A".equals(text(r, "STATUSNFE"));
    t.nfseAuthorized = "A".equals(text(r, "STATUSNFSE"));
    t.amount = ((BigDecimal) r.get("VALORCOBRANCA")).setScale(2, java.math.RoundingMode.HALF_UP);
    t.nativeBankId = text(r, "NOSSONUM");
    t.dueDate = day(r.get("DTVENC"));
    t.issueDate = day(r.get("DTNEG"));
    Payer p = new Payer();
    p.document = digits(text(r, "CGC_CPF"));
    p.name = text(r, "NOMEPARC");
    p.address = text(r, "NOMEEND");
    p.number = text(r, "NUMEND");
    p.neighborhood = text(r, "NOMEBAI");
    p.city = text(r, "NOMECID");
    p.state = text(r, "UF");
    p.zip = digits(text(r, "CEP"));
    p.email = text(r, "EMAIL");
    t.payer = p;
    return t;
  }

  private static String day(Object o) {
    if (o == null) return null;
    return o.toString().substring(0, 10);
  }

  private static String digits(String s) {
    return s == null ? null : s.replaceAll("[^0-9]", "");
  }

  public void persistIdentity(Charge ch) throws Exception {
    DynamicVO fin = JapeFactory.dao("Financeiro").findByPK(BigDecimal.valueOf(ch.nuFin));
    require(fin != null, "FINANCEIRO_AUSENTE");
    JapeFactory.dao("Financeiro")
        .prepareToUpdate(fin)
        .set("NOSSONUM", ch.identity.ourNumber)
        .set("LINHADIGITAVEL", ch.identity.digitableLine)
        .set("CODIGOBARRA", ch.identity.barcode)
        .update();
  }

  private interface Work<T> {
    T run() throws Exception;
  }

  private <T> T technical(Config cfg, Work<T> work) throws Exception {
    AuthenticationInfo previous = AuthenticationInfo.getCurrentOrNull();
    DynamicVO user = JapeFactory.dao("Usuario").findByPK(BigDecimal.valueOf(cfg.technicalUser));
    require(user != null && "S".equals(user.asString("ATIVO")), "USUARIO_TECNICO_INATIVO");
    AuthenticationInfo auth =
        new AuthenticationInfo(
            user.asString("NOMEUSU"),
            BigDecimal.valueOf(cfg.technicalUser),
            user.asBigDecimal("CODGRUPO"),
            0);
    auth.makeCurrent();
    try {
      return work.run();
    } finally {
      AuthenticationInfo.unregistry();
      if (previous != null) previous.makeCurrent();
    }
  }

  public void settle(Charge ch, Payment p) throws Exception {
    technical(
        ch.config,
        () -> {
          DynamicVO fin = JapeFactory.dao("Financeiro").findByPK(BigDecimal.valueOf(ch.nuFin));
          require(
              fin != null && fin.asTimestamp("DHBAIXA") == null,
              "BAIXA_PREEXISTENTE_REQUER_CONFERENCIA");
          require(
              fin.asBigDecimal("CODEMP").longValue() == ch.config.company
                  && fin.asBigDecimal("CODCTABCOINT").longValue() == ch.config.account,
              "CONTA_FINANCEIRA_MODIFICADA");
          BaixaHelper helper =
              new BaixaHelper(
                  BigDecimal.valueOf(ch.nuFin), BigDecimal.valueOf(ch.config.technicalUser));
          helper.setBaixandoPelaBaixaAutomatica(true);
          helper.setIncluirMovimentoBancario(true);
          helper.setImprimirComprovanteCartao(false);
          Timestamp date = Timestamp.valueOf(java.time.LocalDate.parse(p.date).atStartOfDay());
          DadosBaixa seed = new DadosBaixa(date);
          seed.getDadosAdicionais().setCodTipoOperacao(BigDecimal.valueOf(ch.config.settlementTop));
          seed.getDadosAdicionais()
              .setCodTipOpercaoFinanceiro(BigDecimal.valueOf(ch.config.settlementTop));
          helper.setDadosBaixa(seed);
          DadosBaixa data = helper.montaDadosBaixa(date, true, false);
          data.getDadosAdicionais().setCodTipoOperacao(BigDecimal.valueOf(ch.config.settlementTop));
          data.getDadosAdicionais()
              .setCodTipOpercaoFinanceiro(BigDecimal.valueOf(ch.config.settlementTop));
          data.getDadosAdicionais().setCodEmpresa(BigDecimal.valueOf(ch.config.company));
          data.getDadosBancarios().setCodConta(BigDecimal.valueOf(ch.config.account));
          data.getDadosBancarios().setCodLancamento(BigDecimal.valueOf(ch.config.bankEntry));
          data.getDadosBancarios().setConciliado("N");
          data.getValoresBaixa().setVlrDesdob(p.principal().doubleValue());
          data.getValoresBaixa().setVlrOriginal(p.principal());
          data.getValoresBaixa().setVlrJuros(p.interest.doubleValue());
          data.getValoresBaixa().setVlrMulta(p.fine.doubleValue());
          data.getValoresBaixa().setVlrDesconto(p.discount.add(p.abatement).doubleValue());
          data.getValoresBaixa().setVlrBaixa(p.principal().doubleValue());
          data.getValoresBaixa().setVlrTotal(p.total.doubleValue());
          helper.setDadosBaixa(data);
          helper.baixar(data);
          return null;
        });
  }

  public byte[] pdf(Charge ch) throws Exception {
    return technical(
        ch.config,
        () -> {
          // Reimpressão exclusivamente de título já registrado; nunca permitir gerar nosso número.
          BoletoHelper helper = new BoletoHelper();
          BoletoHelper.ConfiguracaoBoleto cfg = new BoletoHelper.ConfiguracaoBoleto();
          cfg.setFinanceirosSelecionados(Collections.singleton(BigDecimal.valueOf(ch.nuFin)));
          cfg.setCodigoConta(BigDecimal.valueOf(ch.config.account));
          cfg.setCodCtaBcoInt(BigDecimal.valueOf(ch.config.account));
          cfg.setCodEmp(BigDecimal.valueOf(ch.config.company));
          cfg.setGerarNumeroBoleto(false);
          cfg.setReimpressao(true);
          cfg.setReimprimirBoleta(true);
          cfg.setReimpressaoPeloModeloDaConta(true);
          cfg.setEnviarEmail(false);
          cfg.setEnviarEmailParceiro(false);
          cfg.setRegistraConta(false);
          cfg.setVisualizaPDFBoleto(true);
          cfg.setTipoSaidaBoleto(BoletoHelper.SAIDA_BOLETO_VISUALIZACAO_PDF);
          helper.gerarBoleto(cfg);
          byte[] result = helper.getBoletosPDF();
          require(
              result != null
                  && result.length > 4
                  && new String(result, 0, 4, "US-ASCII").equals("%PDF"),
              "PDF_NAO_GERADO");
          return result;
        });
  }

  public String deliver(Charge ch, byte[] pdf) throws Exception {
    return technical(
        ch.config,
        () -> {
          String recipient = ch.snapshot.payer.email;
          require(
              recipient != null && recipient.matches("[^\\s@<>]+@[^\\s@<>]+\\.[^\\s@<>]+"),
              "EMAIL_PAGADOR_INVALIDO");
          br.com.sankhya.jape.EntityFacade facade = EntityFacadeFactory.getDWFFacade();
          DynamicVO attachment = (DynamicVO) facade.getDefaultValueObjectInstance("AnexoMensagem");
          attachment.setProperty("NOMEARQUIVO", "boleto-" + ch.identity.ourNumber + ".pdf");
          attachment.setProperty("TIPO", "application/pdf");
          attachment.setProperty("ANEXO", pdf);
          PersistentLocalEntity added = facade.createEntity("AnexoMensagem", (EntityVO) attachment);
          BigDecimal attachmentId = ((DynamicVO) added.getValueObject()).asBigDecimal("NUANEXO");
          DynamicVO queue = (DynamicVO) facade.getDefaultValueObjectInstance("MSDFilaMensagem");
          queue.setProperty("EMAIL", recipient);
          queue.setProperty("CODCON", BigDecimal.ZERO);
          queue.setProperty("CODMSG", null);
          queue.setProperty("STATUS", "Pendente");
          queue.setProperty("TIPOENVIO", "E");
          queue.setProperty("MAXTENTENVIO", BigDecimal.valueOf(3));
          queue.setProperty("ASSUNTO", ch.config.emailSubject);
          queue.setProperty("TIPODOC", "B");
          queue.setProperty("MENSAGEM", ch.config.emailBody.toCharArray());
          queue.setProperty("MIMETYPE", "text/plain");
          if (ch.config.smtp > 0) queue.setProperty("CODSMTP", BigDecimal.valueOf(ch.config.smtp));
          PersistentLocalEntity created = facade.createEntity("MSDFilaMensagem", (EntityVO) queue);
          BigDecimal code = ((DynamicVO) created.getValueObject()).asBigDecimal("CODFILA");
          DynamicVO link = (DynamicVO) facade.getDefaultValueObjectInstance("AnexoPorMensagem");
          link.setProperty("CODFILA", code);
          link.setProperty("NUANEXO", attachmentId);
          facade.createEntity("AnexoPorMensagem", (EntityVO) link);
          return code.toPlainString();
        });
  }
}
