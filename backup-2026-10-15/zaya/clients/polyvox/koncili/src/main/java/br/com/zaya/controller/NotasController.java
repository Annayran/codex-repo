package br.com.zaya.controller;

import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.models.ImportacaoResultado;
import br.com.zaya.service.ImportacaoConciliacaoService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class NotasController {
    private static final Logger LOG = Logger.getLogger(NotasController.class.getName());
    private static final String WEBHOOK_URL_PADRAO = "https://hub.zaya.it/webhook/koncili_polyvox";

    private final ImportacaoConciliacaoService importacaoService;

    public NotasController() {
        this.importacaoService = new ImportacaoConciliacaoService();
    }

    public void executar(ContextoAcao ctx) {
        Object dataIniParam = ctx.getParam("DATAINI");
        Object dataFimParam = ctx.getParam("DATAFIM");

        if (dataIniParam == null || dataFimParam == null) {
            ctx.setMensagemRetorno("Parametros obrigatorios ausentes: DATAINI e DATAFIM.");
            return;
        }

        String dataIni = formatarDataParaWebhook(dataIniParam.toString());
        String dataFim = formatarDataParaWebhook(dataFimParam.toString());
        String webhookUrl = obterWebhookUrl(ctx);

        ImportacaoResultado resultado = importacaoService.importarPorPeriodo(
                webhookUrl,
                dataIni,
                dataFim,
                ImportacaoConciliacaoService.DuplicidadeStrategy.IDTRA_CODVEND,
                false
        );

        String mensagem = resultado.getRecebidos() == 0
                ? "Integracao concluida sem dados para o periodo informado."
                : resultado.toMensagemRetorno();

        LOG.info("[NOTAS_CONTROLLER_RESULTADO] " + mensagem);
        ctx.setMensagemRetorno(mensagem);
    }

    private String obterWebhookUrl(ContextoAcao ctx) {
        Object urlParam = ctx.getParam("URLWEBHOOK");
        if (urlParam == null) {
            return WEBHOOK_URL_PADRAO;
        }
        String url = urlParam.toString();
        if (url.trim().isEmpty()) {
            return WEBHOOK_URL_PADRAO;
        }
        return url.trim();
    }

    private static String formatarDataParaWebhook(String valor) {
        LocalDateTime data = parseDataHora(valor);
        java.util.Date dataDate = java.util.Date.from(data.atZone(ZoneId.systemDefault()).toInstant());
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(dataDate);
    }

    private static LocalDateTime parseDataHora(String valor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS]");
        try {
            return LocalDateTime.parse(valor, formatter);
        } catch (Exception ex) {
            DateTimeFormatter fallback = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(valor.substring(0, 19), fallback);
        }
    }
}

