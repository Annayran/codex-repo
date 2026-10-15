package br.zaya.pedidocompra.parser;

import br.zaya.pedidocompra.modelo.PedidoCompra;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser de pedido de compra baseado em PDFBox 2.x.
 * Lê apenas a primeira página e extrai os campos solicitados.
 */
public class PdfPedidoParser {

    private static final Logger logger = LoggerFactory.getLogger(PdfPedidoParser.class);

    private static final Pattern PADRAO_NUMERO_PEDIDO = Pattern.compile(
        "(?im)Documento\\s*n[º°o.]?\\s*[:#-]?\\s*(\\d{6,})"
    );

    private static final Pattern PADRAO_DATA = Pattern.compile(
        "(?im)\\b(\\d{2}[./-]\\d{2}[./-]\\d{4})\\b"
    );

    private static final Pattern PADRAO_CNPJ = Pattern.compile(
        "(?i)\\b(\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2})\\b"
    );

    private static final Pattern PADRAO_VALOR = Pattern.compile(
        "(?im)\\bTOTAL\\b[^\\d]*(\\d{1,3}(?:\\.\\d{3})*,\\d{2})"
    );

    private static final Pattern PADRAO_SERVICO = Pattern.compile(
        "(?im)^.*\\bSERV:\\s*(.*)$"
    );

    private String textoExtraido;
    private String textoNormalizado;

    public PedidoCompra parse(File pdfFile) throws IOException {
        validarArquivo(pdfFile);

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(1);

            this.textoExtraido = stripper.getText(document);
            this.textoNormalizado = normalizarTexto(this.textoExtraido);

            if (textoNormalizado.isEmpty()) {
                throw new IllegalStateException("Nao foi possivel extrair texto da primeira pagina do PDF: " + pdfFile.getAbsolutePath());
            }

            PedidoCompra pedido = new PedidoCompra();
            pedido.setNumeroPedido(extrairNumeroPedido(textoNormalizado));
            pedido.setData(extrairData(textoNormalizado));
            pedido.setFornecedor(extrairFornecedor(textoNormalizado));
            pedido.setCnpj(extrairCnpj(textoNormalizado));
            pedido.setValorTotal(extrairValorTotal(textoNormalizado));
            pedido.setDescricaoServico(extrairDescricaoServico(textoNormalizado));

            return pedido;
        }
    }

    private void validarArquivo(File pdfFile) {
        if (pdfFile == null) {
            throw new IllegalArgumentException("O arquivo PDF nao pode ser nulo.");
        }
        if (!pdfFile.exists()) {
            throw new IllegalArgumentException("Arquivo PDF nao encontrado: " + pdfFile.getAbsolutePath());
        }
        if (!pdfFile.isFile()) {
            throw new IllegalArgumentException("O caminho informado nao eh um arquivo: " + pdfFile.getAbsolutePath());
        }
        if (!pdfFile.canRead()) {
            throw new IllegalArgumentException("Sem permissao de leitura para: " + pdfFile.getAbsolutePath());
        }
    }

    private String extrairNumeroPedido(String texto) {
        Matcher matcher = PADRAO_NUMERO_PEDIDO.matcher(texto);
        if (matcher.find()) {
            return matcher.group(1);
        }

        logger.warn("Numero do pedido nao encontrado no PDF.");
        return null;
    }

    private String extrairData(String texto) {
        Matcher matcher = PADRAO_DATA.matcher(texto);
        if (matcher.find()) {
            return matcher.group(1);
        }

        logger.warn("Data nao encontrada no PDF.");
        return null;
    }

    private String extrairFornecedor(String texto) {
        String[] linhas = linhasDoTexto(texto);
        for (int i = 0; i < linhas.length; i++) {
            String linha = linhas[i].trim();
            if (linha.matches("(?i)^FORNECEDOR\\s*:?.*$")) {
                String valorNaMesmaLinha = linha.replaceFirst("(?i)^FORNECEDOR", "").trim();
                if (valorNaMesmaLinha.startsWith(":")) {
                    valorNaMesmaLinha = valorNaMesmaLinha.substring(1).trim();
                }
                if (!valorNaMesmaLinha.isEmpty()) {
                    return valorNaMesmaLinha;
                }

                for (int j = i + 1; j < linhas.length; j++) {
                    String candidata = limparLinha(linhas[j]);
                    if (!candidata.isEmpty() && !candidata.matches("(?i)^(CNPJ|CPF|IE|ENDERECO|ENDEREÇO).*$")) {
                        return candidata;
                    }
                }
            }
        }

        logger.warn("Fornecedor nao encontrado no PDF.");
        return null;
    }

    private String extrairCnpj(String texto) {
        Matcher matcher = PADRAO_CNPJ.matcher(texto);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("\\D", "");
        }

        logger.warn("CNPJ nao encontrado no PDF.");
        return null;
    }

    private BigDecimal extrairValorTotal(String texto) {
        Matcher matcher = PADRAO_VALOR.matcher(texto);
        if (matcher.find()) {
            return converterValorBrasileiro(matcher.group(1));
        }

        logger.warn("Valor total nao encontrado no PDF.");
        return BigDecimal.ZERO;
    }

    private String extrairDescricaoServico(String texto) {
        String[] linhas = linhasDoTexto(texto);
        for (int i = 0; i < linhas.length; i++) {
            String linha = linhas[i].trim();
            Matcher matcher = PADRAO_SERVICO.matcher(linha);
            if (matcher.matches()) {
                String descricao = limparLinha(matcher.group(1));
                if (!descricao.isEmpty()) {
                    return descricao;
                }

                for (int j = i + 1; j < linhas.length; j++) {
                    String candidata = limparLinha(linhas[j]);
                    if (!candidata.isEmpty()) {
                        return candidata;
                    }
                }
            }
        }

        logger.warn("Descricao do servico nao encontrada no PDF.");
        return null;
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        String normalizado = texto
            .replace('\u00A0', ' ')
            .replace("\r\n", "\n")
            .replace('\r', '\n');

        StringBuilder builder = new StringBuilder();
        String[] linhas = normalizado.split("\\n");
        for (String linha : linhas) {
            String limpa = limparLinha(linha);
            if (!limpa.isEmpty()) {
                if (builder.length() > 0) {
                    builder.append('\n');
                }
                builder.append(limpa);
            }
        }

        return builder.toString();
    }

    private String limparLinha(String linha) {
        if (linha == null) {
            return "";
        }
        return linha.replace('\t', ' ').trim().replaceAll("\\s+", " ");
    }

    private String[] linhasDoTexto(String texto) {
        if (texto == null || texto.isEmpty()) {
            return new String[0];
        }
        return texto.split("\\n");
    }

    private BigDecimal converterValorBrasileiro(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        try {
            return new BigDecimal(valor.replace(".", "").replace(",", "."));
        } catch (NumberFormatException e) {
            logger.warn("Falha ao converter valor brasileiro: {}", valor, e);
            return BigDecimal.ZERO;
        }
    }

    public String getTextoExtraido() {
        return textoExtraido;
    }

    public String getTextoNormalizado() {
        return textoNormalizado;
    }
}

