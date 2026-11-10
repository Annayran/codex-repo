package br.com.zaya.mahospitalar.genesis.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.zaya.mahospitalar.genesis.client.GenesisCooldown;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Locale;

public final class AcaoReprocessarGenesis implements AcaoRotinaJava {
    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        Registro[] linhas = contexto.getLinhas();
        if (linhas == null || linhas.length == 0) {
            contexto.setMensagemRetorno("Selecione ao menos um registro da fila Genesis.");
            return;
        }
        int count = 0;
        int emEspera = 0;
        Timestamp agora = new Timestamp(System.currentTimeMillis());
        BigDecimal usuario = contexto.getUsuarioLogado();
        for (Registro linha : linhas) {
            validarId(linha);
            String status = texto(linha.getCampo("STATUS_INTEGRACAO"));
            if (!"ERRO".equals(status) && !"REPROCESSAR".equals(status)) {
                continue;
            }
            Object http = linha.getCampo("HTTP_STATUS");
            if (http instanceof Number && ((Number) http).intValue() == 429) {
                // Keep persisted rate-limit evidence; the action must not bypass the jobs' cooldown.
                Object atualizado = linha.getCampo("DH_ATUALIZACAO");
                Object mensagem = linha.getCampo("MENSAGEM_ERRO");
                long prazo = GenesisCooldown.prazoPersistidoMs(mensagem == null ? null : mensagem.toString());
                if (!(atualizado instanceof java.util.Date) ||
                        agora.getTime() - ((java.util.Date) atualizado).getTime() < prazo) {
                    emEspera++;
                    continue;
                }
            }
            linha.setCampo("STATUS_INTEGRACAO", "REPROCESSAR");
            linha.setCampo("TENTATIVAS", BigDecimal.ZERO);
            linha.setCampo("HTTP_STATUS", null);
            linha.setCampo("MENSAGEM_ERRO", null);
            linha.setCampo("DH_REPROCESSAMENTO", agora);
            linha.setCampo("DH_ATUALIZACAO", agora);
            linha.setCampo("USU_REPROCESSAMENTO", usuario);
            linha.save();
            count++;
        }
        contexto.setMensagemRetorno(count + " registro(s) liberado(s) para reprocessamento controlado. " +
                emEspera + " registro(s) preservado(s) em espera por limite da API.");
    }

    private void validarId(Registro linha) {
        Object value = linha.getCampo("IDINTGENESIS");
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Linha sem IDINTGENESIS valido.");
        }
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString().trim().toUpperCase(Locale.ROOT);
    }
}
