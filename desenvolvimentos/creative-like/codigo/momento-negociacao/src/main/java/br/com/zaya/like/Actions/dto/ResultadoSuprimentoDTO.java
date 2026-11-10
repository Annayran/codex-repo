package br.com.zaya.like.Actions.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ResultadoSuprimentoDTO {

    private BigDecimal nuNegociacao;
    private DocumentoGeradoDTO pedidoVenda;
    private final List<DocumentoGeradoDTO> pedidosVenda = new ArrayList<DocumentoGeradoDTO>();
    private NotaInternaDTO notaInterna;
    private final List<ItemSuprimentoDTO> itens = new ArrayList<ItemSuprimentoDTO>();
    private final List<ItemSuprimentoDTO> itensBloqueados = new ArrayList<ItemSuprimentoDTO>();
    private final List<ItemSuprimentoDTO> itensCancelaveis = new ArrayList<ItemSuprimentoDTO>();
    private final List<ItemSuprimentoDTO> itensCancelados = new ArrayList<ItemSuprimentoDTO>();
    private final List<String> avisos = new ArrayList<String>();
    private int itensCanceladosAgora;
    private int itensIgnorados;
    private int processosProducaoSuspensos;

    public BigDecimal getNuNegociacao() {
        return nuNegociacao;
    }

    public void setNuNegociacao(BigDecimal nuNegociacao) {
        this.nuNegociacao = nuNegociacao;
    }

    public DocumentoGeradoDTO getPedidoVenda() {
        return pedidoVenda;
    }

    public void setPedidoVenda(DocumentoGeradoDTO pedidoVenda) {
        this.pedidoVenda = pedidoVenda;
    }

    public List<DocumentoGeradoDTO> getPedidosVenda() {
        return pedidosVenda;
    }

    public NotaInternaDTO getNotaInterna() {
        return notaInterna;
    }

    public void setNotaInterna(NotaInternaDTO notaInterna) {
        this.notaInterna = notaInterna;
    }

    public List<ItemSuprimentoDTO> getItens() {
        return itens;
    }

    public List<ItemSuprimentoDTO> getItensBloqueados() {
        return itensBloqueados;
    }

    public List<ItemSuprimentoDTO> getItensCancelaveis() {
        return itensCancelaveis;
    }

    public List<ItemSuprimentoDTO> getItensCancelados() {
        return itensCancelados;
    }

    public List<String> getAvisos() {
        return avisos;
    }

    public void addAviso(String aviso) {
        if (aviso != null && aviso.trim().length() > 0) {
            avisos.add(aviso);
        }
    }

    public int getItensCanceladosAgora() {
        return itensCanceladosAgora;
    }

    public void incrementarItensCanceladosAgora() {
        itensCanceladosAgora++;
    }

    public int getItensIgnorados() {
        return itensIgnorados;
    }

    public void incrementarItensIgnorados() {
        itensIgnorados++;
    }

    public boolean possuiCancelaveis() {
        return !itensCancelaveis.isEmpty();
    }

    public boolean possuiBloqueados() {
        return !itensBloqueados.isEmpty();
    }

    public int getProcessosProducaoSuspensos() {
        return processosProducaoSuspensos;
    }

    public void setProcessosProducaoSuspensos(int processosProducaoSuspensos) {
        this.processosProducaoSuspensos = processosProducaoSuspensos;
    }
}
