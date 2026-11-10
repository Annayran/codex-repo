package br.com.zaya.teleworld.fornecedorxml.model.repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.zaya.teleworld.fornecedorxml.model.dto.FornecedorXmlData;
import br.com.zaya.teleworld.fornecedorxml.shared.utils.DocumentoUtils;

public class FlowFornecedorRepository {
    private static final String ENTIDADE = "FilaFlowFornecedor";
    private static final String ENTIDADE_IMPORTACAO_XML = "ImportacaoXMLNotas";

    public boolean parceiroExiste(String documento) throws Exception {
        return buscarCodParc(documento) != null;
    }

    public BigDecimal buscarCodParc(String documento) throws Exception {
        DynamicVO parceiro = JapeFactory.dao("Parceiro").findOne(
                "REPLACE(REPLACE(REPLACE(this.CGC_CPF, '.', ''), '/', ''), '-', '') = ?",
                DocumentoUtils.somenteDigitos(documento));
        return parceiro == null ? null : parceiro.asBigDecimal("CODPARC");
    }

    public void enfileirar(FornecedorXmlData data, BigDecimal codPrn) throws Exception {
        JapeWrapper dao = JapeFactory.dao(ENTIDADE);
        DynamicVO existente = dao.findOne("this.CNPJCPF = ?", data.getCnpjCpf());
        Timestamp agora = new Timestamp(System.currentTimeMillis());

        if (existente == null) {
            FluidCreateVO create = dao.create()
                    .set("CNPJCPF", data.getCnpjCpf())
                    .set("NUARQUIVO", data.getNuArquivo())
                    .set("CODEMP", data.getCodEmp())
                    .set("CHAVEACESSO", data.getChaveAcesso())
                    .set("ORIGEM", DocumentoUtils.limitar(
                            data.getOrigem() == null ? "PORTAL_XML" : data.getOrigem(), 30))
                    .set("CODPRN", codPrn)
                    .set("STATUS", "P")
                    .set("TENTATIVAS", BigDecimal.ZERO)
                    .set("DHCRIACAO", agora)
                    .set("DHALTER", agora);
            preencher(create, data);
            create.save();
        } else if (!"C".equals(existente.asString("STATUS"))) {
            FluidUpdateVO update = dao.prepareToUpdate(existente)
                    .set("NUARQUIVO", data.getNuArquivo())
                    .set("CODEMP", data.getCodEmp())
                    .set("CHAVEACESSO", data.getChaveAcesso())
                    .set("NOMEARQUIVO", DocumentoUtils.limitar(data.getNomeArquivo(), 200))
                    .set("CODPRN", codPrn)
                    .set("DHALTER", agora);
            preencher(update, data);
            update.update();
        }
    }

    public BigDecimal bloquearProximo(JdbcWrapper jdbc, int maxTentativas) throws Exception {
        String sql = "SELECT IDFILA FROM AD_ZFLOWFORN "
                + "WHERE STATUS IN ('P','E') AND TENTATIVAS < ? AND ROWNUM = 1 "
                + "FOR UPDATE SKIP LOCKED";
        try (PreparedStatement ps = jdbc.getPreparedStatement(sql)) {
            ps.setInt(1, maxTentativas);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getBigDecimal(1) : null;
            }
        }
    }

    public BigDecimal bloquearProximoIniciado(JdbcWrapper jdbc, BigDecimal ultimoId) throws Exception {
        String sql = "SELECT IDFILA FROM AD_ZFLOWFORN "
                + "WHERE IDFILA = (SELECT MIN(IDFILA) FROM AD_ZFLOWFORN "
                + "WHERE STATUS = 'I' AND IDFILA > ?) AND STATUS = 'I' "
                + "FOR UPDATE SKIP LOCKED";
        try (PreparedStatement ps = jdbc.getPreparedStatement(sql)) {
            ps.setBigDecimal(1, ultimoId == null ? BigDecimal.ZERO : ultimoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getBigDecimal(1) : null;
            }
        }
    }

    public DynamicVO buscar(BigDecimal idFila) throws Exception {
        return JapeFactory.dao(ENTIDADE).findByPK(idFila);
    }

    public void marcarSucesso(DynamicVO fila, BigDecimal idInstPrn) throws Exception {
        Timestamp agora = new Timestamp(System.currentTimeMillis());
        BigDecimal tentativas = fila.asBigDecimalOrZero("TENTATIVAS").add(BigDecimal.ONE);
        JapeFactory.dao(ENTIDADE).prepareToUpdate(fila)
                .set("STATUS", "I")
                .set("IDINSTPRN", idInstPrn)
                .set("TENTATIVAS", tentativas)
                .set("DHULTTENTATIVA", agora)
                .set("DHINICIOFLOW", agora)
                .set("DHALTER", agora)
                .set("MSGERRO", null)
                .update();
    }

    public void sincronizarParceiro(DynamicVO fila, BigDecimal codParc) throws Exception {
        if (codParc == null) {
            throw new Exception("CODPARC do parceiro nao informado para sincronizacao.");
        }

        BigDecimal nuArquivo = fila.asBigDecimal("NUARQUIVO");
        if (nuArquivo == null) {
            throw new Exception("Fila " + fila.asBigDecimal("IDFILA") + " sem NUARQUIVO.");
        }

        JapeWrapper importacaoDao = JapeFactory.dao(ENTIDADE_IMPORTACAO_XML);
        DynamicVO importacao = importacaoDao.findByPK(nuArquivo);
        if (importacao == null) {
            throw new Exception("TGFIXN nao encontrada para NUARQUIVO " + nuArquivo + '.');
        }

        BigDecimal codParcAtual = importacao.asBigDecimal("CODPARC");
        if (codParcAtual == null || codParcAtual.signum() == 0) {
            importacaoDao.prepareToUpdate(importacao)
                    .set("CODPARC", codParc)
                    .update();
        } else if (codParcAtual.compareTo(codParc) != 0) {
            throw new Exception("TGFIXN " + nuArquivo + " ja possui CODPARC diferente do parceiro localizado.");
        }

        Timestamp agora = new Timestamp(System.currentTimeMillis());
        JapeFactory.dao(ENTIDADE).prepareToUpdate(fila)
                .set("CODPARC", codParc)
                .set("STATUS", "C")
                .set("DHCONCLUSAO", agora)
                .set("DHALTER", agora)
                .set("MSGERRO", null)
                .update();
    }

    public void marcarErro(DynamicVO fila, Throwable erro) throws Exception {
        Timestamp agora = new Timestamp(System.currentTimeMillis());
        BigDecimal tentativas = fila.asBigDecimalOrZero("TENTATIVAS").add(BigDecimal.ONE);
        JapeFactory.dao(ENTIDADE).prepareToUpdate(fila)
                .set("STATUS", "E")
                .set("TENTATIVAS", tentativas)
                .set("DHULTTENTATIVA", agora)
                .set("DHALTER", agora)
                .set("MSGERRO", DocumentoUtils.limitar(mensagem(erro), 4000))
                .update();
    }

    private void preencher(FluidCreateVO vo, FornecedorXmlData data) throws Exception {
        vo.set("NOMEARQUIVO", DocumentoUtils.limitar(data.getNomeArquivo(), 200))
                .set("TIPOCADASTRO", data.getTipoCadastro())
                .set("RAZAOSOCIAL", DocumentoUtils.limitar(data.getRazaoSocial(), 200))
                .set("NOMEFANTASIA", DocumentoUtils.limitar(data.getNomeFantasia(), 200))
                .set("EMAIL", DocumentoUtils.limitar(data.getEmail(), 200))
                .set("TELEFONE", DocumentoUtils.limitar(data.getTelefone(), 50))
                .set("ENDERECO", DocumentoUtils.limitar(data.getEndereco(), 1000));
    }

    private void preencher(FluidUpdateVO vo, FornecedorXmlData data) throws Exception {
        vo.set("TIPOCADASTRO", data.getTipoCadastro())
                .set("RAZAOSOCIAL", DocumentoUtils.limitar(data.getRazaoSocial(), 200))
                .set("NOMEFANTASIA", DocumentoUtils.limitar(data.getNomeFantasia(), 200))
                .set("EMAIL", DocumentoUtils.limitar(data.getEmail(), 200))
                .set("TELEFONE", DocumentoUtils.limitar(data.getTelefone(), 50))
                .set("ENDERECO", DocumentoUtils.limitar(data.getEndereco(), 1000));
    }

    private String mensagem(Throwable erro) {
        StringBuilder out = new StringBuilder();
        Throwable atual = erro;
        int nivel = 0;
        while (atual != null && nivel++ < 12) {
            if (out.length() > 0) out.append(" -> ");
            out.append(atual.getClass().getSimpleName());
            if (atual.getMessage() != null) out.append(": ").append(atual.getMessage());
            if (atual.getCause() == atual) break;
            atual = atual.getCause();
        }
        return out.toString();
    }
}
