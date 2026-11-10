package br.com.zaya.belmicro.rateio.dominio;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** SHA-256 over length-prefixed UTF-8 fields and sorted, framed records. */
public final class HashComposicao {
    private HashComposicao() { }

    public static String calcular(ContextoHash contexto) {
        if (contexto == null) throw new ErroRateio("BASE_INCOMPLETA", "Contexto do hash ausente.");
        Registro raiz = new Registro();
        raiz.campo("serializacao", "1");
        raiz.campo("algoritmoMapeamento", contexto.getVersaoAlgoritmo());
        raiz.campo("tipoOrigem", contexto.getTipoOrigem());
        raiz.campo("idOrigem", String.valueOf(contexto.getIdOrigem()));
        raiz.campo("empresa", String.valueOf(contexto.getCodEmp()));
        raiz.campo("natureza", String.valueOf(contexto.getCodNat()));
        raiz.campo("parceiroPresente", String.valueOf(contexto.getCodParc() != null));
        raiz.campo("parceiro", contexto.getCodParc() == null ? "" : contexto.getCodParc().toString());
        raiz.campo("competencia", contexto.getCompetencia().toString());
        raiz.campo("base", numero(contexto.getResultado().getBase()));
        raiz.campo("modalidade", contexto.getModalidade());
        raiz.campo("criterioVersao", contexto.getCriterioVersao());
        List<String> materiais = new ArrayList<String>();
        for (Map.Entry<String, String> campo : contexto.getCamposMateriais().entrySet()) {
            Registro registro = new Registro();
            registro.campo("nome", campo.getKey());
            registro.campo("valor", campo.getValue());
            materiais.add(registro.codificado());
        }
        raiz.lista("camposMateriais", materiais);
        List<String> parcelas = new ArrayList<String>();
        for (ParcelaSimulacao parcela : contexto.getResultado().getParcelas()) {
            Registro registro = new Registro();
            registro.campo("empresa", String.valueOf(parcela.getCodEmp()));
            registro.campo("cr", String.valueOf(parcela.getCodCencus()));
            registro.campo("componente", parcela.getComponente());
            registro.campo("compatibilidade", parcela.getChaveCompatibilidade());
            registro.campo("peso", numero(parcela.getPeso()));
            registro.campo("participacaoGlobal", numero(parcela.getParticipacaoGlobal()));
            registro.campo("valor", numero(parcela.getValor()));
            registro.campo("origem", String.valueOf(parcela.isOrigem()));
            registro.campo("evidencia", parcela.getEvidencia());
            parcelas.add(registro.codificado());
        }
        raiz.lista("parcelas", parcelas);
        List<String> grupos = new ArrayList<String>();
        for (GrupoPrevisto grupo : contexto.getResultado().getGrupos()) {
            Registro registro = new Registro();
            registro.campo("empresa", String.valueOf(grupo.getCodEmp()));
            registro.campo("compatibilidade", grupo.getChaveCompatibilidade());
            registro.campo("total", numero(grupo.getValorTotal()));
            List<String> linhas = new ArrayList<String>();
            for (LinhaRateio linha : grupo.getLinhasPorCr()) {
                Registro detalhe = new Registro();
                detalhe.campo("cr", String.valueOf(linha.getCodCencus()));
                detalhe.campo("valor", numero(linha.getValor()));
                detalhe.campo("percentual", numero(linha.getPercentual()));
                linhas.add(detalhe.codificado());
            }
            registro.lista("linhas", linhas);
            grupos.add(registro.codificado());
        }
        raiz.lista("grupos", grupos);
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(raiz.bytes());
            StringBuilder hex = new StringBuilder();
            for (byte valor : hash) {
                hex.append(Character.forDigit((valor >>> 4) & 15, 16));
                hex.append(Character.forDigit(valor & 15, 16));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 indisponivel.", e);
        }
    }

    private static String numero(BigDecimal valor) { return valor.stripTrailingZeros().toPlainString(); }

    private static final class Registro {
        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private final DataOutputStream saida = new DataOutputStream(buffer);

        void campo(String nome, String valor) {
            try {
                texto(nome);
                texto(valor);
            } catch (IOException e) { throw new IllegalStateException(e); }
        }

        void lista(String nome, List<String> registros) {
            List<String> ordenados = new ArrayList<String>(registros);
            Collections.sort(ordenados);
            campo(nome + ".quantidade", String.valueOf(ordenados.size()));
            for (String registro : ordenados) campo(nome + ".registro", registro);
        }

        private void texto(String texto) throws IOException {
            byte[] bytes = texto.getBytes(StandardCharsets.UTF_8);
            saida.writeInt(bytes.length);
            saida.write(bytes);
        }

        byte[] bytes() { return buffer.toByteArray(); }
        String codificado() { return java.util.Base64.getEncoder().encodeToString(bytes()); }
    }
}
