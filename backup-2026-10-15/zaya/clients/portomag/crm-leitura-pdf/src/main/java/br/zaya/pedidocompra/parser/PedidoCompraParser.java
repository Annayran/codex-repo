package br.zaya.pedidocompra.parser;

import br.zaya.pedidocompra.modelo.PedidoCompra;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Adaptador de compatibilidade para o novo {@link PdfPedidoParser}.
 *
 * Mantido para não quebrar código legado que ainda chama os métodos antigos.
 */
public class PedidoCompraParser {

    private final PdfPedidoParser delegate = new PdfPedidoParser();

    public PedidoCompra parse(File pdfFile) throws IOException {
        return delegate.parse(pdfFile);
    }

    public PedidoCompra processar(String caminhoArquivo) throws IOException {
        if (caminhoArquivo == null) {
            throw new IllegalArgumentException("O caminho do PDF nao pode ser nulo.");
        }
        return parse(new File(caminhoArquivo));
    }

    public PedidoCompra processar(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("O InputStream do PDF nao pode ser nulo.");
        }

        byte[] bytes = lerBytes(inputStream);
        File tempFile = File.createTempFile("pedido-compra", ".pdf");
        tempFile.deleteOnExit();

        try {
            java.nio.file.Files.write(tempFile.toPath(), bytes);
            return parse(tempFile);
        } finally {
            // O arquivo temporario sera removido pelo deleteOnExit.
        }
    }

    public String getTextoExtraido() {
        return delegate.getTextoExtraido();
    }

    public String getTextoNormalizado() {
        return delegate.getTextoNormalizado();
    }

    private byte[] lerBytes(InputStream inputStream) throws IOException {
        try (InputStream in = inputStream; ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            byte[] chunk = new byte[8192];
            int bytesLidos;

            while ((bytesLidos = in.read(chunk)) != -1) {
                buffer.write(chunk, 0, bytesLidos);
            }

            return buffer.toByteArray();
        }
    }
}
