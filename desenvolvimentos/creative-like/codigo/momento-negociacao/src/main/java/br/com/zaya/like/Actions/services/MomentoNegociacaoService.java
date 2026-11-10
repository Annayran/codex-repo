package br.com.zaya.like.Actions.services;

import java.math.BigDecimal;
import java.util.List;

import br.com.zaya.like.Actions.dao.MomentoNegociacaoDAO;
import br.com.zaya.like.Actions.dto.DocumentoGeradoDTO;
import br.com.zaya.like.Actions.dto.ItemMomentoNegociacaoDTO;
import br.com.zaya.like.Actions.dto.ItemSuprimentoDTO;
import br.com.zaya.like.Actions.dto.MomentoNegociacaoDTO;
import br.com.zaya.like.Actions.dto.NotaInternaDTO;
import br.com.zaya.like.Actions.dto.ResultadoSuprimentoDTO;

public class MomentoNegociacaoService {

    public static String gerarHtmlMomento(BigDecimal nunegociacao) throws Exception {
        MomentoNegociacaoDTO momento = MomentoNegociacaoDAO.buscarMomento(nunegociacao);
        List<ItemMomentoNegociacaoDTO> itens = MomentoNegociacaoDAO.buscarItensPrincipais(nunegociacao);

        StringBuilder html = new StringBuilder();

        html.append("<div style=\"font-family:Arial,Helvetica,sans-serif;font-size:13px;color:#222;\">");
        html.append("<h3 style=\"margin:0 0 12px 0;\">Momento da Negociacao ");
        html.append(escapeHtml(toStr(momento.getNumeroNegociacao(), nunegociacao)));
        html.append("</h3>");

        html.append("<div style=\"display:flex;gap:14px;flex-wrap:wrap;margin-bottom:16px;\">");
        html.append(cardStatus("Suprimento", momento.getStatusSuprimento()));
        html.append(cardStatus("Layout", momento.getStatusLayout()));
        html.append(cardStatus("Producao", momento.getStatusProducaoFinal()));
        html.append(cardStatus("Faturamento", momento.getStatusFaturamento()));
        html.append(cardStatus("Entrega", momento.getStatusEntrega()));
        html.append("</div>");

        html.append("<div style=\"margin:8px 0 10px 0;\">");
        html.append("<b>Cliente:</b> ").append(escapeHtml(toStr(momento.getCliente(), "-")));
        html.append(" &nbsp; <b>Vendedor:</b> ").append(escapeHtml(toStr(momento.getVendedor(), "-")));
        html.append(" &nbsp; <b>Status proposta:</b> ").append(escapeHtml(toStr(momento.getStatusNegociacao(), "-")));
        html.append("</div>");

        html.append(montarResumoSuprimentos(nunegociacao));
        html.append(montarResumoItens(itens));
        html.append("</div>");

        return html.toString();
    }

    public static ResultadoSuprimentoDTO diagnosticarSuprimentos(BigDecimal nunegociacao) throws Exception {
        ResultadoSuprimentoDTO resultado = new ResultadoSuprimentoDTO();
        resultado.setNuNegociacao(nunegociacao);

        List<DocumentoGeradoDTO> pedidosVenda = MomentoNegociacaoDAO.buscarPedidosVendaRelacionados(nunegociacao);
        DocumentoGeradoDTO pedidoVenda = pedidosVenda.isEmpty() ? null : pedidosVenda.get(0);
        NotaInternaDTO notaInterna = MomentoNegociacaoDAO.buscarNotaInterna(nunegociacao);

        resultado.setPedidoVenda(pedidoVenda);
        resultado.getPedidosVenda().addAll(pedidosVenda);
        resultado.setNotaInterna(notaInterna);

        if (notaInterna != null && notaInterna.getNuNota() != null) {
            adicionarItensAoResultado(resultado, MomentoNegociacaoDAO.buscarItensSuprimento(notaInterna.getNuNota()));
        }

        for (DocumentoGeradoDTO pedidoVendaRelacionado : pedidosVenda) {
            if (pedidoVendaRelacionado.getNuNota() != null) {
                adicionarItensAoResultado(resultado,
                        MomentoNegociacaoDAO.buscarItensSuprimento(pedidoVendaRelacionado.getNuNota()));
            }
        }

        return resultado;
    }

    public static ResultadoSuprimentoDTO cancelarSuprimentos(BigDecimal nunegociacao, String motivo, BigDecimal codUsuario)
            throws Exception {
        ResultadoSuprimentoDTO resultado = diagnosticarSuprimentos(nunegociacao);

        if (resultado.getPedidoVenda() == null) {
            throw new Exception("Nao foi encontrado Pedido de Venda relacionado na AD_VGLDOC com TIPMOV = 'P'.");
        }

        if (resultado.getItens().isEmpty()) {
            throw new Exception("Nenhum item foi encontrado na TGFITE para a Nota Mov. Interna ou Pedidos de Venda da negociacao "
                    + nunegociacao + ".");
        }

        if (!resultado.possuiCancelaveis()) {
            if (!resultado.getItensCancelados().isEmpty()) {
                throw new Exception("Cancelamento ja tratado. Todos os itens com saldo encontrados ja possuem TGFACT com AD_ITEMCANCELADO = 'S'.");
            }

            throw new Exception("Cancelamento bloqueado. Todos os itens encontrados ja estao com QTDENTREGUE igual a QTDNEG.");
        }

        String ocorrencia = montarOcorrencia(motivo);

        for (ItemSuprimentoDTO item : resultado.getItensCancelaveis()) {
            cancelarItemComTrava(resultado, item, ocorrencia, codUsuario);
        }

        if (resultado.getItensCanceladosAgora() == 0) {
            throw new Exception("Nenhum item foi cancelado agora. Todos os itens aptos ja foram tratados ou mudaram de status. "
                    + montarAvisosTexto(resultado));
        }

        return resultado;
    }

    public static ResultadoSuprimentoDTO cancelarItemPedidoVenda(BigDecimal nunegociacao, BigDecimal nunotaPedidoVenda,
            BigDecimal sequencia, String motivo, BigDecimal codUsuario) throws Exception {
        ResultadoSuprimentoDTO resultado = new ResultadoSuprimentoDTO();
        resultado.setNuNegociacao(nunegociacao);

        DocumentoGeradoDTO pedidoVenda = MomentoNegociacaoDAO.buscarPedidoVendaRelacionado(nunegociacao, nunotaPedidoVenda);

        if (pedidoVenda == null) {
            throw new Exception("Pedido de Venda [" + nunotaPedidoVenda
                    + "] nao foi encontrado na AD_VGLDOC para a negociacao [" + nunegociacao + "] com TIPMOV = 'P'.");
        }

        resultado.setPedidoVenda(pedidoVenda);
        resultado.getPedidosVenda().add(pedidoVenda);

        ItemSuprimentoDTO item = MomentoNegociacaoDAO.buscarItemSuprimento(nunotaPedidoVenda, sequencia);

        if (item == null) {
            throw new Exception("Item [" + sequencia + "] nao encontrado na TGFITE para o Pedido de Venda ["
                    + nunotaPedidoVenda + "].");
        }

        resultado.getItens().add(item);

        if (item.isCancelado()) {
            resultado.getItensCancelados().add(item);
            throw new Exception("Cancelamento ja tratado. O item [" + sequencia + "] do Pedido de Venda ["
                    + nunotaPedidoVenda + "] ja possui TGFACT com AD_ITEMCANCELADO = 'S'. Motivo: "
                    + toStr(item.getOcorrenciaCancelamento(), "nao informado"));
        }

        if (item.isTotalmenteEntregue()) {
            resultado.getItensBloqueados().add(item);
            throw new Exception("Cancelamento bloqueado. O item [" + sequencia
                    + "] do Pedido de Venda [" + nunotaPedidoVenda + "] ja esta com QTDENTREGUE igual a QTDNEG.");
        }

        String ocorrencia = montarOcorrencia(motivo);

        resultado.getItensCancelaveis().add(item);
        cancelarItemComTrava(resultado, item, ocorrencia, codUsuario);

        if (resultado.getItensCanceladosAgora() == 0) {
            throw new Exception("Nenhum item foi cancelado agora. O item [" + sequencia + "] do Pedido de Venda ["
                    + nunotaPedidoVenda + "] ja estava tratado ou mudou de status. " + montarAvisosTexto(resultado));
        }

        return resultado;
    }

    public static ResultadoSuprimentoDTO cancelarItensDocumento(BigDecimal nunegociacao, BigDecimal nunotaDocumento,
            String tipoDocumento, String motivo, BigDecimal codUsuario) throws Exception {
        ResultadoSuprimentoDTO resultado = prepararResultadoDocumento(nunegociacao, nunotaDocumento, tipoDocumento);

        if (resultado.getItens().isEmpty()) {
            throw new Exception("Nenhum item foi encontrado na TGFITE para o documento [" + nunotaDocumento + "].");
        }

        if (!resultado.possuiCancelaveis()) {
            if (!resultado.getItensCancelados().isEmpty()) {
                throw new Exception("Cancelamento ja tratado. Os itens com saldo do documento [" + nunotaDocumento
                        + "] ja possuem TGFACT com AD_ITEMCANCELADO = 'S'.");
            }

            throw new Exception("Cancelamento bloqueado. Todos os itens do documento [" + nunotaDocumento
                    + "] ja estao com QTDENTREGUE igual a QTDNEG ou ja foram tratados.");
        }

        String ocorrencia = montarOcorrencia(motivo);

        for (ItemSuprimentoDTO item : resultado.getItensCancelaveis()) {
            cancelarItemComTrava(resultado, item, ocorrencia, codUsuario);
        }

        if (resultado.getItensCanceladosAgora() == 0) {
            throw new Exception("Nenhum item foi cancelado agora. O documento [" + nunotaDocumento
                    + "] ja estava tratado ou seus itens mudaram de status. " + montarAvisosTexto(resultado));
        }

        return resultado;
    }

    public static ResultadoSuprimentoDTO cancelarItemDocumento(BigDecimal nunegociacao, BigDecimal nunotaDocumento,
            String tipoDocumento, BigDecimal sequencia, String motivo, BigDecimal codUsuario) throws Exception {
        ResultadoSuprimentoDTO resultado = prepararResultadoDocumento(nunegociacao, nunotaDocumento, tipoDocumento);
        ItemSuprimentoDTO item = MomentoNegociacaoDAO.buscarItemSuprimento(nunotaDocumento, sequencia);

        if (item == null) {
            throw new Exception("Item [" + sequencia + "] nao encontrado na TGFITE para o documento ["
                    + nunotaDocumento + "].");
        }

        resultado.getItens().clear();
        resultado.getItensBloqueados().clear();
        resultado.getItensCancelaveis().clear();
        resultado.getItensCancelados().clear();
        resultado.getItens().add(item);

        if (item.isCancelado()) {
            resultado.getItensCancelados().add(item);
            throw new Exception("Cancelamento ja tratado. O item [" + sequencia + "] do documento [" + nunotaDocumento
                    + "] ja possui TGFACT com AD_ITEMCANCELADO = 'S'. Motivo: "
                    + toStr(item.getOcorrenciaCancelamento(), "nao informado"));
        }

        if (item.isTotalmenteEntregue()) {
            resultado.getItensBloqueados().add(item);
            throw new Exception("Cancelamento bloqueado. O item [" + sequencia + "] do documento [" + nunotaDocumento
                    + "] ja esta com QTDENTREGUE igual a QTDNEG.");
        }

        String ocorrencia = montarOcorrencia(motivo);

        resultado.getItensCancelaveis().add(item);
        cancelarItemComTrava(resultado, item, ocorrencia, codUsuario);

        if (resultado.getItensCanceladosAgora() == 0) {
            throw new Exception("Nenhum item foi cancelado agora. O item [" + sequencia + "] do documento ["
                    + nunotaDocumento + "] ja estava tratado ou mudou de status. " + montarAvisosTexto(resultado));
        }

        return resultado;
    }

    public static ResultadoSuprimentoDTO cancelarProducaoNegociacao(BigDecimal nunegociacao, String motivo,
            BigDecimal codUsuario) throws Exception {
        ResultadoSuprimentoDTO resultado = new ResultadoSuprimentoDTO();
        resultado.setNuNegociacao(nunegociacao);

        int processos = MomentoNegociacaoDAO.contarProcessosProducao(nunegociacao);

        if (processos == 0) {
            throw new Exception("Nenhum processo de producao foi encontrado em TPRIPROC.AD_NUNEGOCIACAO para a negociacao ["
                    + nunegociacao + "].");
        }

        int processosSuspensos = MomentoNegociacaoDAO.suspenderProcessosProducao(nunegociacao);
        resultado.setProcessosProducaoSuspensos(processosSuspensos);

        if (processosSuspensos == 0) {
            throw new Exception("Cancelamento de producao ja tratado. Todos os processos da negociacao ["
                    + nunegociacao + "] ja estao com STATUSPROC = S.");
        }

        resultado.addAviso("Producao suspensa em TPRIPROC.STATUSPROC = S. Motivo informado: "
                + toStr(motivo, "Motivo nao informado"));
        return resultado;
    }

    public static ResultadoSuprimentoDTO cancelarTudoNegociacao(BigDecimal nunegociacao, String motivo,
            BigDecimal codUsuario) throws Exception {
        ResultadoSuprimentoDTO resultado = diagnosticarSuprimentos(nunegociacao);
        String ocorrencia = montarOcorrencia(motivo);

        for (ItemSuprimentoDTO item : resultado.getItensCancelaveis()) {
            cancelarItemComTrava(resultado, item, ocorrencia, codUsuario);
        }

        int processos = MomentoNegociacaoDAO.contarProcessosProducao(nunegociacao);
        if (processos > 0) {
            int processosSuspensos = MomentoNegociacaoDAO.suspenderProcessosProducao(nunegociacao);
            resultado.setProcessosProducaoSuspensos(processosSuspensos);

            if (processosSuspensos == 0) {
                resultado.addAviso("Producao ja estava tratada. Nenhum processo novo foi suspenso em TPRIPROC.");
            }
        } else {
            resultado.addAviso("Nenhum processo de producao encontrado em TPRIPROC para esta negociacao.");
        }

        if (resultado.getItensCanceladosAgora() == 0 && resultado.getProcessosProducaoSuspensos() == 0) {
            if (!resultado.getItensCancelados().isEmpty()) {
                throw new Exception("Cancelamento ja tratado. Itens com saldo ja possuem TGFACT com AD_ITEMCANCELADO = 'S' "
                        + "e a producao nao possui processo novo para suspender. " + montarAvisosTexto(resultado));
            }

            throw new Exception("Nenhuma etapa foi cancelada agora. Nao ha item apto em Faturamento/Suprimento "
                    + "e nao ha processo de Producao novo para suspender. " + montarAvisosTexto(resultado));
        }

        return resultado;
    }

    public static String montarMensagemResultado(ResultadoSuprimentoDTO resultado, boolean executado) {
        StringBuilder mensagem = new StringBuilder();

        mensagem.append(executado ? "Cancelamento da negociacao executado." : "Diagnostico da negociacao.");
        mensagem.append("<br><br><b>Negociacao:</b> ").append(escapeHtml(toStr(resultado.getNuNegociacao(), "-")));

        if (!resultado.getPedidosVenda().isEmpty()) {
            mensagem.append("<br><b>Pedidos de Venda:</b> ");

            for (int i = 0; i < resultado.getPedidosVenda().size(); i++) {
                DocumentoGeradoDTO pedido = resultado.getPedidosVenda().get(i);

                if (i > 0) {
                    mensagem.append(", ");
                }

                mensagem.append("NUNOTA ")
                        .append(escapeHtml(toStr(pedido.getNuNota(), "-")));
            }
        }

        if (resultado.getNotaInterna() != null) {
            mensagem.append("<br><b>Nota Mov. Interna:</b> NUNOTA ")
                    .append(escapeHtml(toStr(resultado.getNotaInterna().getNuNota(), "-")))
                    .append(" / NUMNOTA ")
                    .append(escapeHtml(toStr(resultado.getNotaInterna().getNumeroNota(), "-")));
        }

        mensagem.append("<br><b>Itens encontrados:</b> ").append(resultado.getItens().size());
        mensagem.append("<br><b>Bloqueados por entrega total:</b> ").append(resultado.getItensBloqueados().size());
        mensagem.append("<br><b>Cancelaveis:</b> ").append(resultado.getItensCancelaveis().size());

        if (executado) {
            mensagem.append("<br><b>Itens cancelados agora:</b> ").append(resultado.getItensCanceladosAgora());
            mensagem.append("<br><b>Itens ja tratados anteriormente:</b> ")
                    .append(Math.max(0, resultado.getItensCancelados().size() - resultado.getItensCanceladosAgora()));
            mensagem.append("<br><b>Itens ignorados por trava:</b> ").append(resultado.getItensIgnorados());
            mensagem.append("<br><b>Processos de producao suspensos:</b> ")
                    .append(resultado.getProcessosProducaoSuspensos());
        }

        if (!resultado.getAvisos().isEmpty()) {
            mensagem.append("<br><br><b>Avisos:</b>");

            for (String aviso : resultado.getAvisos()) {
                mensagem.append("<br>- ").append(escapeHtml(aviso));
            }
        }

        return mensagem.toString();
    }

    private static String montarResumoSuprimentos(BigDecimal nunegociacao) {
        try {
            ResultadoSuprimentoDTO resultado = diagnosticarSuprimentos(nunegociacao);
            StringBuilder html = new StringBuilder();

            html.append("<div style=\"border:1px solid #e1e4e8;background:#f8fafc;padding:10px;margin:10px 0;\">");
            html.append("<b>Fluxo real de suprimentos:</b>");

            if (resultado.getPedidosVenda().isEmpty()) {
                html.append("<br>Pedido de Venda: nao encontrado em AD_VGLDOC com TIPMOV = 'P'.");
            } else {
                html.append("<br>Pedidos de Venda: ");

                for (int i = 0; i < resultado.getPedidosVenda().size(); i++) {
                    DocumentoGeradoDTO pedido = resultado.getPedidosVenda().get(i);

                    if (i > 0) {
                        html.append(", ");
                    }

                    html.append("NUNOTA ")
                            .append(escapeHtml(toStr(pedido.getNuNota(), "-")));
                }
            }

            if (resultado.getNotaInterna() == null) {
                html.append("<br>Nota Mov. Interna: nao encontrada em TGFCAB.AD_NUNEGOCIACAO.");
            } else {
                html.append("<br>Nota Mov. Interna: NUNOTA ")
                        .append(escapeHtml(toStr(resultado.getNotaInterna().getNuNota(), "-")))
                        .append(" / NUMNOTA ")
                        .append(escapeHtml(toStr(resultado.getNotaInterna().getNumeroNota(), "-")));
            }

            html.append("<br>Itens TGFITE: ").append(resultado.getItens().size());
            html.append(" | Bloqueados: ").append(resultado.getItensBloqueados().size());
            html.append(" | Cancelaveis: ").append(resultado.getItensCancelaveis().size());
            html.append("</div>");

            return html.toString();
        } catch (Exception e) {
            return "<div style=\"border:1px solid #f5c2c7;background:#fff5f5;padding:10px;margin:10px 0;\">"
                    + "<b>Fluxo real de suprimentos:</b> " + escapeHtml(e.getMessage()) + "</div>";
        }
    }

    private static String montarOcorrencia(String motivo) {
        String motivoTratado = toStr(motivo, "Motivo nao informado");
        return "Pedido de compra possui itens cancelados! Motivo: " + motivoTratado;
    }

    private static boolean cancelarItemComTrava(ResultadoSuprimentoDTO resultado, ItemSuprimentoDTO item,
            String ocorrencia, BigDecimal codUsuario) throws Exception {
        ItemSuprimentoDTO itemAtual = MomentoNegociacaoDAO.buscarItemSuprimento(item.getNuNota(), item.getSequencia());

        if (itemAtual == null) {
            resultado.incrementarItensIgnorados();
            resultado.addAviso("Item " + toStr(item.getSequencia(), "-") + " do documento "
                    + toStr(item.getNuNota(), "-") + " nao foi encontrado na revalidacao.");
            return false;
        }

        if (itemAtual.isCancelado()
                || MomentoNegociacaoDAO.existeAcompanhamentoCancelamento(itemAtual.getNuNota(), itemAtual.getSequencia())) {
            resultado.incrementarItensIgnorados();
            adicionarCanceladoSemDuplicar(resultado, itemAtual);
            resultado.addAviso("Item " + toStr(itemAtual.getSequencia(), "-") + " do documento "
                    + toStr(itemAtual.getNuNota(), "-") + " ja possui TGFACT com AD_ITEMCANCELADO = 'S'.");
            return false;
        }

        if (itemAtual.isTotalmenteEntregue()) {
            resultado.incrementarItensIgnorados();
            resultado.addAviso("Item " + toStr(itemAtual.getSequencia(), "-") + " do documento "
                    + toStr(itemAtual.getNuNota(), "-") + " foi ignorado porque QTDENTREGUE = QTDNEG.");
            return false;
        }

        MomentoNegociacaoDAO.marcarItemComoPendenteCancelado(itemAtual.getNuNota(), itemAtual.getSequencia(), ocorrencia);
        MomentoNegociacaoDAO.incluirAcompanhamentoCancelamento(itemAtual.getNuNota(), itemAtual.getSequencia(),
                ocorrencia, codUsuario);
        adicionarCanceladoSemDuplicar(resultado, itemAtual);
        resultado.incrementarItensCanceladosAgora();
        return true;
    }

    private static void adicionarCanceladoSemDuplicar(ResultadoSuprimentoDTO resultado, ItemSuprimentoDTO item) {
        if (!contemItem(resultado.getItensCancelados(), item)) {
            resultado.getItensCancelados().add(item);
        }
    }

    private static boolean contemItem(List<ItemSuprimentoDTO> itens, ItemSuprimentoDTO item) {
        for (ItemSuprimentoDTO existente : itens) {
            if (compararBigDecimal(existente.getNuNota(), item.getNuNota())
                    && compararBigDecimal(existente.getSequencia(), item.getSequencia())) {
                return true;
            }
        }

        return false;
    }

    private static boolean compararBigDecimal(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return a == b;
        }

        return a.compareTo(b) == 0;
    }

    private static String montarAvisosTexto(ResultadoSuprimentoDTO resultado) {
        if (resultado == null || resultado.getAvisos().isEmpty()) {
            return "";
        }

        StringBuilder texto = new StringBuilder("Avisos: ");

        for (int i = 0; i < resultado.getAvisos().size(); i++) {
            if (i > 0) {
                texto.append(" | ");
            }

            texto.append(resultado.getAvisos().get(i));
        }

        return texto.toString();
    }

    private static ResultadoSuprimentoDTO prepararResultadoDocumento(BigDecimal nunegociacao, BigDecimal nunotaDocumento,
            String tipoDocumento) throws Exception {
        ResultadoSuprimentoDTO resultado = new ResultadoSuprimentoDTO();
        resultado.setNuNegociacao(nunegociacao);

        String tipo = tipoDocumento == null ? "" : tipoDocumento.trim().toUpperCase();

        if ("PV".equals(tipo)) {
            DocumentoGeradoDTO pedidoVenda = MomentoNegociacaoDAO.buscarPedidoVendaRelacionado(nunegociacao,
                    nunotaDocumento);

            if (pedidoVenda == null) {
                throw new Exception("Pedido de Venda [" + nunotaDocumento
                        + "] nao foi encontrado na AD_VGLDOC para a negociacao [" + nunegociacao
                        + "] com TIPMOV = 'P'.");
            }

            resultado.setPedidoVenda(pedidoVenda);
            resultado.getPedidosVenda().add(pedidoVenda);
        } else if ("MI".equals(tipo)) {
            NotaInternaDTO notaInterna = MomentoNegociacaoDAO.buscarNotaInterna(nunegociacao);

            if (notaInterna == null || notaInterna.getNuNota() == null
                    || notaInterna.getNuNota().compareTo(nunotaDocumento) != 0) {
                throw new Exception("Nota de Movimentacao Interna [" + nunotaDocumento
                        + "] nao foi encontrada para a negociacao [" + nunegociacao + "].");
            }

            resultado.setNotaInterna(notaInterna);
        } else {
            throw new Exception("Tipo de documento invalido para cancelamento: " + tipoDocumento
                    + ". Use PV para Pedido de Venda ou MI para Movimentacao Interna.");
        }

        adicionarItensAoResultado(resultado, MomentoNegociacaoDAO.buscarItensSuprimento(nunotaDocumento));
        return resultado;
    }

    private static void validarAcompanhamentoPermiteCancelamento(BigDecimal nunegociacao, String tipoDocumento)
            throws Exception {
        MomentoNegociacaoDTO momento = MomentoNegociacaoDAO.buscarMomento(nunegociacao);
        String tipo = tipoDocumento == null ? "" : tipoDocumento.trim().toUpperCase();

        if ("MI".equals(tipo)) {
            validarStatusAreaPendente("Suprimento", "Movimentacao Interna", momento.getStatusSuprimento());
        } else if ("PV".equals(tipo)) {
            validarStatusAreaPendente("Faturamento", "Pedido de Venda", momento.getStatusFaturamento());
        }
    }

    private static void validarStatusAreaPendente(String area, String processo, String status) throws Exception {
        if (isStatusPendente(status)) {
            return;
        }

        throw new Exception("Cancelamento bloqueado para " + processo + ". A area " + area
                + " nao esta pendente no acompanhamento da negociacao. Status atual: "
                + toStr(status, "nao informado")
                + ". Para continuar, sera necessario notificar a area responsavel/liberacao operacional.");
    }

    private static boolean isStatusPendente(String status) {
        String s = status == null ? "" : status.toUpperCase();
        return s.contains("PEND") || s.contains("AGUARD") || s.contains("ANDAMENTO") || s.contains("PARCIAL");
    }

    private static void adicionarItensAoResultado(ResultadoSuprimentoDTO resultado, List<ItemSuprimentoDTO> itens) {
        for (ItemSuprimentoDTO item : itens) {
            resultado.getItens().add(item);

            if (item.isCancelado()) {
                resultado.getItensCancelados().add(item);
            } else if (item.isTotalmenteEntregue()) {
                resultado.getItensBloqueados().add(item);
            } else {
                resultado.getItensCancelaveis().add(item);
            }
        }
    }

    private static String cardStatus(String titulo, String status) {
        String statusFormatado = toStr(status, "Nao informado");
        String cor = definirCorStatus(statusFormatado);

        return "<div style=\"background:#f4f6f8;min-width:210px;padding:18px 20px;border-left:10px solid "
                + cor + ";box-sizing:border-box;\">"
                + "<b>" + escapeHtml(titulo) + ":</b> " + escapeHtml(statusFormatado)
                + "</div>";
    }

    private static String montarResumoItens(List<ItemMomentoNegociacaoDTO> itens) {
        StringBuilder html = new StringBuilder();

        html.append("<div style=\"margin-top:12px;\">");
        html.append("<b>Itens principais da negociacao:</b>");

        if (itens == null || itens.isEmpty()) {
            html.append("<div style=\"border:1px solid #ddd;padding:8px;margin-top:8px;\">Nenhum item principal encontrado.</div>");
        } else {
            for (ItemMomentoNegociacaoDTO item : itens) {
                html.append("<div style=\"border:1px solid #d9dde2;background:#fff;margin-top:8px;padding:10px;\">");

                html.append("<div style=\"font-size:13px;margin-bottom:8px;\">");
                html.append("<b>Seq. ").append(escapeHtml(toStr(item.getSequencia(), "-"))).append("</b>");
                html.append(" &nbsp; <b>Referencia:</b> ").append(escapeHtml(toStr(item.getReferencia(), "-")));
                html.append("</div>");

                html.append("<div style=\"margin-bottom:8px;line-height:1.35;\">");
                html.append("<b>Produto:</b> ").append(escapeHtml(toStr(item.getDescricaoProduto(), "-")));
                html.append("</div>");

                html.append("<table style=\"width:100%;border-collapse:collapse;font-size:12px;table-layout:fixed;\">");
                html.append("<tr>");
                html.append(celulaInfo("Arte", toStr(item.getTituloArte(), "-")));
                html.append(celulaInfo("Status arte", toStr(item.getStatusArte(), "-")));
                html.append(celulaInfo("Suprimento", toStr(item.getStatusSuprimento(), "-")));
                html.append(celulaInfo("Producao", toStr(item.getStatusProducao(), "-")));
                html.append("</tr>");
                html.append("<tr>");
                html.append(celulaInfo("Qtd. neg.", toStr(item.getQtdNegociada(), "0")));
                html.append(celulaInfo("Disp. estoque", toStr(item.getQtdDisponivelEstoque(), "0")));
                html.append(celulaInfo("Pend. compra", toStr(item.getQtdPendenteCompra(), "0")));
                html.append(celulaInfo("Disp. faturamento", toStr(item.getQtdDisponivelFaturamento(), "0")));
                html.append("</tr>");
                html.append("<tr>");
                html.append(celulaInfo("Qtd. faturada", toStr(item.getQtdFaturada(), "0")));
                html.append(celulaInfo("Lib. CQ", toStr(item.getQtdLiberadaCQ(), "0")));
                html.append(celulaInfo("Lib. estoque", toStr(item.getQtdLiberadaEstoque(), "0")));
                html.append(celulaInfo("Tipo", toStr(item.getTipoProduto(), "-")));
                html.append("</tr>");
                html.append("</table>");

                html.append("</div>");
            }
        }

        html.append("</div>");

        return html.toString();
    }

    private static String definirCorStatus(String status) {
        String s = status == null ? "" : status.toUpperCase();

        if (s.contains("COMPLETO") || s.contains("CONCL") || s.contains("FINALIZ") || s.contains("ENTREG")) {
            return "#00a65a";
        }

        if (s.contains("PEND") || s.contains("ANDAMENTO") || s.contains("PARCIAL") || s.contains("AGUARD")) {
            return "#f0ad4e";
        }

        if (s.contains("BLOQUE") || s.contains("CANCEL") || s.contains("ATRAS") || s.contains("ERRO")) {
            return "#dd4b39";
        }

        return "#333333";
    }

    private static String celulaInfo(String titulo, String valor) {
        return "<td style=\"border:1px solid #e1e4e8;padding:7px;vertical-align:top;word-break:normal;\">"
                + "<span style=\"display:block;color:#555;font-size:11px;\"><b>" + escapeHtml(titulo) + "</b></span>"
                + "<span>" + escapeHtml(toStr(valor, "-")) + "</span>"
                + "</td>";
    }

    private static String toStr(Object value, Object defaultValue) {
        if (value == null) {
            return String.valueOf(defaultValue);
        }

        String s = String.valueOf(value);
        return s.trim().isEmpty() ? String.valueOf(defaultValue) : s;
    }

    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
