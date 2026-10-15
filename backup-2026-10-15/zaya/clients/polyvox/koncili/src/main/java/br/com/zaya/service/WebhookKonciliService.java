//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.zaya.service;

import br.com.zaya.integration.KonciliWebhookClient;
import br.com.zaya.models.ConciliacaoResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebhookKonciliService {
    private static final Logger LOG = Logger.getLogger(WebhookKonciliService.class.getName());
    private final KonciliWebhookClient webhookClient = new KonciliWebhookClient();

    public List<ConciliacaoResponse> buscarConciliacoesViaWebhook(String cnpj, String dataIni, String dataFim) throws IOException {
        try {
            List<ConciliacaoResponse> conciliacoes = webhookClient.buscarConciliacoes(cnpj, dataIni, dataFim);
            return conciliacoes;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, String.format("[WEBHOOK_KONCILI_ERRO_IO] Falha na requisicao | cnpj=%s dataIni=%s dataFim=%s",
                    cnpj, dataIni, dataFim), e);
            throw new IOException(e.getMessage(), e);
        }
    }

    public void conciliacaoResolvidaERP(String cnpjEmpresa, Integer idTransacao) {
        try {
            webhookClient.notificarConciliacaoResolvida(cnpjEmpresa, idTransacao);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, String.format("[WEBHOOK_KONCILI_RESOLVIDO_EXCECAO] Erro ao notificar webhook | cnpj=%s idTransacao=%s",
                    cnpjEmpresa, idTransacao), e);
        }

    }
}
