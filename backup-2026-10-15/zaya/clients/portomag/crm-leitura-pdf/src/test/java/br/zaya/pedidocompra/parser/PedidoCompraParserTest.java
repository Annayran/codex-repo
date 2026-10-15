package br.zaya.pedidocompra.parser;

import br.zaya.pedidocompra.modelo.PedidoCompra;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("PdfPedidoParser")
class PedidoCompraParserTest {

    @Test
    @DisplayName("Deve extrair apenas a primeira pagina do PDF")
    void deveLerSomentePrimeiraPagina() throws Exception {
        File pdf = criarPdfDeTeste();

        PdfPedidoParser parser = new PdfPedidoParser();
        PedidoCompra pedido = parser.parse(pdf);

        assertEquals("5500592071", pedido.getNumeroPedido());
        assertEquals("24.04.2026", pedido.getData());
        assertEquals("PORTOMAG METALURGIA LTDA", pedido.getFornecedor());
        assertEquals("02831257000110", pedido.getCnpj());
        assertEquals(new BigDecimal("2467.67"), pedido.getValorTotal());
        assertEquals("INSTALACAO DE EQUIPAMENTO", pedido.getDescricaoServico());
    }

    @Test
    @DisplayName("Deve falhar quando o arquivo nao existir")
    void deveFalharQuandoArquivoNaoExistir() {
        PdfPedidoParser parser = new PdfPedidoParser();

        assertThrows(IllegalArgumentException.class, () -> parser.parse(new File("arquivo-inexistente.pdf")));
    }

    @Test
    @DisplayName("Deve preservar null em campos nao encontrados")
    void devePreservarCamposNaoEncontrados() throws Exception {
        File pdf = criarPdfSimples();

        PdfPedidoParser parser = new PdfPedidoParser();
        PedidoCompra pedido = parser.parse(pdf);

        assertEquals("1234567890", pedido.getNumeroPedido());
        assertNull(pedido.getFornecedor());
        assertNull(pedido.getCnpj());
        assertEquals(new BigDecimal("100.00"), pedido.getValorTotal());
    }

    private File criarPdfDeTeste() throws Exception {
        File pdf = Files.createTempFile("pedido-compra-teste", ".pdf").toFile();
        pdf.deleteOnExit();

        try (PDDocument document = new PDDocument()) {
            PDPage pagina1 = new PDPage(PDRectangle.A4);
            document.addPage(pagina1);
            escreverTexto(document, pagina1,
                "Pedido de Compra\n" +
                "Documento nº 5500592071\n" +
                "Data 24.04.2026\n" +
                "FORNECEDOR:\n" +
                "PORTOMAG METALURGIA LTDA\n" +
                "CNPJ 02.831.257/0001-10\n" +
                "SERV: INSTALACAO DE EQUIPAMENTO\n" +
                "TOTAL 2.467,67");

            PDPage pagina2 = new PDPage(PDRectangle.A4);
            document.addPage(pagina2);
            escreverTexto(document, pagina2,
                "Documento nº 9999999999\n" +
                "Data 01.01.2099\n" +
                "FORNECEDOR:\n" +
                "OUTRO FORNECEDOR\n" +
                "CNPJ 11.111.111/1111-11\n" +
                "SERV: NAO DEVE SER LIDO\n" +
                "TOTAL 9.999,99");

            document.save(pdf);
        }

        return pdf;
    }

    private File criarPdfSimples() throws Exception {
        File pdf = Files.createTempFile("pedido-compra-teste-texto", ".pdf").toFile();
        pdf.deleteOnExit();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            escreverTexto(document, page, "Documento nº 1234567890\nTOTAL 100,00");
            document.save(pdf);
        }

        return pdf;
    }

    private void escreverTexto(PDDocument document, PDPage page, String texto) throws Exception {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset(50, 750);

            for (String linha : texto.split("\\n")) {
                contentStream.showText(linha);
                contentStream.newLineAtOffset(0, -14);
            }

            contentStream.endText();
        }
    }
}

