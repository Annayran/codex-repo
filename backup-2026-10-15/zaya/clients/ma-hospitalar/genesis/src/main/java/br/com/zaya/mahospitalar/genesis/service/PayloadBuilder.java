package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.domain.MovimentoEstoque;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayloadBuilder {
    public List<String> validar(MovimentoEstoque movimento) {
        List<String> faltantes = new ArrayList<String>();
        if (isBlank(codigoProduto(movimento))) faltantes.add("codigo");
        if (isBlank(movimento.getProduto())) faltantes.add("produto");
        if (isBlank(movimento.getSerie())) faltantes.add("serie");
        if (dataEntrada(movimento) == null) faltantes.add("dataEntrada");
        if (!isStatusValido(movimento.getStatusEstoque())) faltantes.add("statusEstoque");
        return faltantes;
    }

    public EstoqueGenesisDTO montar(MovimentoEstoque movimento) {
        BigDecimal qtd = quantidade(movimento);
        EstoqueGenesisDTO dto = new EstoqueGenesisDTO();
        dto.setCodigo(codigoProduto(movimento));
        dto.setCodigoExterno(codigoExterno(movimento));
        dto.setDescServico(movimento.getProduto());
        dto.setDescTecnica(movimento.getProduto());
        dto.setNserie(movimento.getSerie());
        dto.setIdCategoria(movimento.getIdCategoria());
        dto.setIdFabricante(movimento.getIdFabricante());
        dto.setIdFornecedor(movimento.getIdFornecedor());
        dto.setIdSubCategoria(movimento.getIdSubCategoria());
        dto.setStatus(defaultStatus(movimento.getStatusEstoque()));
        dto.setRgAnvisa(movimento.getRgAnvisa());
        dto.setPartNumber(movimento.getMarca());
        dto.setTag(movimento.getMarca());
        dto.setPeriodoGarantia(movimento.getGarantiaMeses() == null ? null : String.valueOf(movimento.getGarantiaMeses()));
        dto.setQtd(qtd);
        dto.setVlrCusto(movimento.getValorUnitario());
        dto.setVlrVenda(movimento.getValorUnitario());
        dto.setVlrCustoTotal(total(movimento.getValorUnitario(), qtd));
        dto.setVlrVendaTotal(total(movimento.getValorUnitario(), qtd));
        dto.setLocalizacao(isBlank(movimento.getLocalizacao()) ? movimento.getPoder() : movimento.getLocalizacao());
        dto.setDtEntrada(toEpochSeconds(dataEntrada(movimento)));
        dto.setControlarEstoque(Boolean.TRUE);
        dto.setConsumo(Boolean.FALSE);
        dto.setServico(Boolean.FALSE);
        dto.setVenda(Boolean.FALSE);
        return dto;
    }

    private String codigoProduto(MovimentoEstoque movimento) {
        if (!isBlank(movimento.getCodProduto())) return movimento.getCodProduto().trim();
        String produto = movimento.getProduto();
        if (isBlank(produto)) return null;
        int separator = produto.indexOf(" - ");
        return separator > 0 ? produto.substring(0, separator).trim() : produto.trim();
    }

    private String codigoExterno(MovimentoEstoque movimento) {
        if (!isBlank(movimento.getChaveMovimento())) return movimento.getChaveMovimento();
        List<String> partes = new ArrayList<String>();
        if (movimento.getCodEmpresa() != null) partes.add(String.valueOf(movimento.getCodEmpresa()));
        if (movimento.getNumUnico() != null) partes.add(String.valueOf(movimento.getNumUnico()));
        String codigo = codigoProduto(movimento);
        if (!isBlank(codigo)) partes.add(codigo);
        if (!isBlank(movimento.getSerie())) partes.add(movimento.getSerie());
        return partes.isEmpty() ? null : join(partes, "|");
    }

    private BigDecimal quantidade(MovimentoEstoque movimento) {
        return movimento.getQuantidade() == null ? BigDecimal.ONE : movimento.getQuantidade();
    }

    private BigDecimal total(BigDecimal valor, BigDecimal quantidade) {
        return valor == null || quantidade == null ? null : valor.multiply(quantidade);
    }

    private Date dataEntrada(MovimentoEstoque movimento) {
        return movimento.getDataInstalacao() == null ? movimento.getDataNegociacao() : movimento.getDataInstalacao();
    }

    private String join(List<String> values, String separator) {
        StringBuilder result = new StringBuilder();
        for (String value : values) {
            if (result.length() > 0) result.append(separator);
            result.append(value);
        }
        return result.toString();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isStatusValido(String status) {
        String value = defaultStatus(status);
        return "O".equals(value) || "A".equals(value) || "I".equals(value)
                || "V".equals(value) || "D".equals(value) || "R".equals(value);
    }

    private String defaultStatus(String status) {
        return isBlank(status) ? "O" : status.trim().toUpperCase();
    }

    private Long toEpochSeconds(Date date) {
        return date == null ? null : Long.valueOf(date.getTime() / 1000L);
    }
}