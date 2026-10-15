package br.zaya.pedidocompra;

import br.zaya.pedidocompra.modelo.PedidoCompra;
import br.zaya.pedidocompra.parser.PdfPedidoParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Classe de exemplo de uso do PedidoCompraParser
 * Demonstra como processar um PDF de pedido de compra
 */
public class ExemploUso {

    private static final Logger logger = LoggerFactory.getLogger(ExemploUso.class);
    private static final String CAMINHO_PADRAO_PDF = "pedido.pdf";

    public static void main(String[] args) {
        try {
            String caminhoPdf = (args != null && args.length > 0 && args[0] != null && !args[0].trim().isEmpty())
                ? args[0].trim()
                : CAMINHO_PADRAO_PDF;

            exemploProcessarArquivo(caminhoPdf);
        } catch (Throwable t) {
            logger.error("Falha inesperada na execução do exemplo", t);
            System.err.println("Falha inesperada na execução do exemplo: " + t.getMessage());
        }
    }

    private static void exemploProcessarArquivo(String caminhoArquivo) {
        System.out.println("\n===== EXEMPLO: Processar PDF de Arquivo =====\n");

        PdfPedidoParser parser = new PdfPedidoParser();

        try {
            if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
                System.out.println("Nenhum caminho de PDF informado. Pulando leitura de arquivo.");
                return;
            }

            File arquivo = new File(caminhoArquivo);
            if (!arquivo.exists()) {
                System.out.println("Arquivo não encontrado: " + caminhoArquivo);
                return;
            }

            PedidoCompra pedido = parser.parse(arquivo);
            imprimirPedido(pedido);

        } catch (Exception e) {
            logger.error("Erro ao processar arquivo", e);
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void imprimirPedido(PedidoCompra pedido) {
        if (pedido == null) {
            System.out.println("Pedido nulo!");
            return;
        }

        System.out.println("=======================================================");
        System.out.println("           PEDIDO DE COMPRA EXTRAIDO");
        System.out.println("=======================================================");
        System.out.println();

        System.out.println("INFORMACOES DO PEDIDO");
        System.out.println("-----------------------------------------------------");
        System.out.println("Numero do Pedido: " + pedido.getNumeroPedido());
        System.out.println("Data do Pedido:   " + pedido.getData());
        System.out.println("Descricao Servico: " + pedido.getDescricaoServico());
        System.out.println();

        System.out.println("FORNECEDOR");
        System.out.println("-----------------------------------------------------");
        System.out.println("Nome:  " + pedido.getFornecedor());
        System.out.println("CNPJ:  " + formatarCNPJ(pedido.getCnpj()));
        System.out.println();

        System.out.println("VALOR TOTAL");
        System.out.println("-----------------------------------------------------");
        System.out.printf("R$ %,.2f%n", pedido.getValorTotal().doubleValue());
        System.out.println();
    }

    private static String formatarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return cnpj;
        }
        return cnpj.substring(0, 2) + "." +
               cnpj.substring(2, 5) + "." +
               cnpj.substring(5, 8) + "/" +
               cnpj.substring(8, 12) + "-" +
               cnpj.substring(12, 14);
    }
}
