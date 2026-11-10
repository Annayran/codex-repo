package br.com.zaya.belmicro.rateio.aplicacao;

import br.com.zaya.belmicro.rateio.aplicacao.dto.EntradaSimulacao;
import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Request identity uses supplied input, not live data: a retry never recalculates. */
final class IdentidadeSolicitacao {
    private IdentidadeSolicitacao() { }

    static String calcular(String operacao, OrigemCanonica origem, long simulacaoId, int revisao,
                            EntradaSimulacao entrada, String motivo, long usuario) {
        return calcular(operacao,origem,simulacaoId,revisao,entrada,motivo,usuario,null);
    }
    static String calcular(String operacao, OrigemCanonica origem, long simulacaoId, int revisao,
                            EntradaSimulacao entrada, String motivo, long usuario,OrigemCanonica referencia) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream saida = new DataOutputStream(bytes);
            texto(saida,referencia==null?"solicitacao-v1":"solicitacao-v2-referencia");
            texto(saida,operacao);
            texto(saida,origem.getTipo().name());
            saida.writeLong(origem.getId());
            if(referencia!=null){texto(saida,referencia.getTipo().name());saida.writeLong(referencia.getId());}
            saida.writeLong(simulacaoId);
            saida.writeInt(revisao);
            saida.writeLong(usuario);
            texto(saida,motivo);
            texto(saida,entrada.getModalidade().name());
            texto(saida,entrada.getCriterioId());
            texto(saida,entrada.getCriterioVersao() == null ? null : entrada.getCriterioVersao().toString());
            List<String> linhas = new ArrayList<String>();
            for (EntradaSimulacao.Percentual linha : entrada.getPercentuais()) {
                ByteArrayOutputStream registro = new ByteArrayOutputStream();
                DataOutputStream detalhe = new DataOutputStream(registro);
                detalhe.writeInt(linha.getCodEmp());
                detalhe.writeInt(linha.getCodCencus());
                texto(detalhe,linha.getPercentual() == null ? null
                        : linha.getPercentual().stripTrailingZeros().toPlainString());
                linhas.add(java.util.Base64.getEncoder().encodeToString(registro.toByteArray()));
            }
            Collections.sort(linhas);
            saida.writeInt(linhas.size());
            for (String linha : linhas) texto(saida,linha);
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(bytes.toByteArray());
            StringBuilder hex = new StringBuilder();
            for (byte valor : hash) {
                hex.append(Character.forDigit((valor >>> 4) & 15,16));
                hex.append(Character.forDigit(valor & 15,16));
            }
            return hex.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new IllegalStateException("Falha ao identificar solicitacao.",e);
        }
    }

    private static void texto(DataOutputStream saida, String texto) throws IOException {
        if (texto == null) { saida.writeInt(-1); return; }
        byte[] bytes = texto.getBytes(StandardCharsets.UTF_8);
        saida.writeInt(bytes.length);
        saida.write(bytes);
    }
}
