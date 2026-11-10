package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class CandidatoGenesisRepository {
    public List<CandidatoGenesis> listar(JdbcWrapper jdbc, int limite, int maxTentativas,
                                         boolean incluirSimulados) throws Exception {
        String statusElegiveis = incluirSimulados
                ? "('PENDENTE','REPROCESSAR','ERRO','REVISAO','SIMULADO')"
                : "('PENDENTE','REPROCESSAR','ERRO','REVISAO')";
        // maxTentativas is retained for compatibility, not as a retry cutoff.
        String sql = "SELECT * FROM (SELECT V.* FROM (SELECT D.* FROM (SELECT B.*," +
                "COALESCE(TRIM(PREGRA.HSP_TIPOPRODUTO),TRIM(PREGRA.AD_TIPOPRODUTO)) AS TIPO_PRODUTO,PREGRA.CODFAB AS CODFABRICANTE," +
                "TRIM(PREGRA.HSP_IDEXTERNO) AS CODIGO_EXTERNO_MODELO," +
                "(SELECT CASE WHEN COUNT(*)=1 THEN MAX(SIT.IDGENESIS) END FROM AD_GENDOMINIO SIT " +
                "WHERE SIT.IDBASE=1 AND SIT.TIPODOMINIO='SITUACAO' AND SIT.STATUSCONC='CONCILIADO' " +
                "AND UPPER(TRIM(SIT.STATUSGENESIS))='ATIVO' AND SIT.IDGENESIS>0 " +
                "AND UPPER(TRIM(SIT.CODIGOSANKHYA))=UPPER(COALESCE(TRIM(B.STATUS_ESTOQUE),'O'))) ID_SITUACAO_GENESIS," +
                "COALESCE(NULLIF(TRIM(PFAB.NOMEPARC),''),NULLIF(TRIM(PREGRA.MARCA),'')) AS NOME_FABRICANTE," +
                "COALESCE(NULLIF(TRIM(PFAB.CGC_CPF),''),NULLIF(TRIM(PREGRA.CNPJFABRICANTE),'')) AS CNPJ_FABRICANTE," +
                "ROW_NUMBER() OVER (PARTITION BY B.NUNOTA,UPPER(TRIM(B.SERIE)) " +
                "ORDER BY B.DTNEG DESC,B.SEQUENCIA DESC,B.CHAVE_MOVIMENTO DESC) RN " +
                "FROM AD_VW_GENESIS_ESTOQUE B JOIN TGFPRO PREGRA ON PREGRA.CODPROD=B.CODPROD " +
                "LEFT JOIN TGFPAR PFAB ON PFAB.CODPARC=PREGRA.CODFAB " +
                "WHERE B.SERIE IS NOT NULL AND UPPER(COALESCE(TRIM(PREGRA.HSP_TIPOPRODUTO),TRIM(PREGRA.AD_TIPOPRODUTO))) IN ('M1','M3')) D WHERE D.RN=1) V " +
                "LEFT JOIN (SELECT MX.* FROM (SELECT GM.*,ROW_NUMBER() OVER (" +
                "PARTITION BY GM.CHAVE_MOVIMENTO ORDER BY GM.DH_ATUALIZACAO DESC NULLS LAST," +
                "GM.IDINTGENESIS DESC) RN_MOV FROM AD_GENMOVEST GM) MX WHERE MX.RN_MOV=1) M " +
                "ON M.CHAVE_MOVIMENTO=V.CHAVE_MOVIMENTO " +
                "WHERE (M.CHAVE_MOVIMENTO IS NULL OR M.STATUS_INTEGRACAO IN " + statusElegiveis +
                " OR M.STATUS_INTEGRACAO='ENVIO_INCERTO') " +
                "AND (M.PROTOCOLO_GENESIS IS NULL OR NVL(M.HTTP_STATUS,0) NOT BETWEEN 200 AND 299) " +
                "AND (M.STATUS_INTEGRACAO IS NULL OR M.STATUS_INTEGRACAO NOT IN ('REPROCESSAR','ERRO','REVISAO','ENVIO_INCERTO') " +
                "OR NVL(M.TENTATIVAS,0)=0 OR M.DH_ATUALIZACAO IS NULL OR M.DH_ATUALIZACAO<=SYSDATE-" +
                "LEAST(60,POWER(2,LEAST(NVL(M.TENTATIVAS,0),6)))/1440) " +
                "AND NOT EXISTS (SELECT 1 FROM TGFCAN C WHERE C.NUNOTA=V.NUNOTA) " +
                "AND NOT EXISTS (SELECT 1 FROM AD_GENEXCFILA F WHERE F.CHAVE_MOVIMENTO=V.CHAVE_MOVIMENTO) " +
                "ORDER BY M.DH_ATUALIZACAO ASC NULLS FIRST,V.NUNOTA,V.SEQUENCIA) WHERE ROWNUM<=?";
        List<CandidatoGenesis> result = new ArrayList<CandidatoGenesis>();
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, Math.max(1, limite));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(map(rs));
            }
        }
        return result;
    }

    private CandidatoGenesis map(ResultSet rs) throws Exception {
        CandidatoGenesis c = new CandidatoGenesis();
        c.codEmp = rs.getBigDecimal("CODEMP"); c.nuNota = rs.getBigDecimal("NUNOTA");
        c.numNota = rs.getBigDecimal("NUMNOTA"); c.sequencia = rs.getBigDecimal("SEQUENCIA");
        c.codTipOper = rs.getBigDecimal("CODTIPOPER"); c.codParc = rs.getBigDecimal("CODPARC");
        c.codGrupoProd = rs.getBigDecimal("CODGRUPOPROD"); c.codProd = rs.getBigDecimal("CODPROD");
        c.tipoProduto = texto(rs, "TIPO_PRODUTO");
        c.codigoExternoModelo = texto(rs, "CODIGO_EXTERNO_MODELO");
        c.dataMovimento = rs.getTimestamp("DTNEG"); c.valorUnitario = rs.getBigDecimal("VLRUNIT");
        c.quantidade = BigDecimal.ONE;
        c.tipoEvento = texto(rs, "TIPO"); c.tipoMovimento = c.tipoEvento;
        c.situacaoGenesis = texto(rs, "STATUS_ESTOQUE_DESC");
        BigDecimal situacaoId = rs.getBigDecimal("ID_SITUACAO_GENESIS");
        c.idSituacaoGenesis = situacaoId == null ? null : situacaoId.longValueExact();
        c.descricaoProduto = texto(rs, "DESCRPROD"); c.descricaoGrupo = texto(rs, "DESCRGRUPOPROD");
        c.serie = texto(rs, "SERIE"); c.statusEstoque = texto(rs, "STATUS_ESTOQUE");
        c.statusEstoqueDescricao = texto(rs, "STATUS_ESTOQUE_DESC");
        BigDecimal garantia = rs.getBigDecimal("GARANTIA_MESES");
        c.garantiaMeses = garantia == null ? null : garantia.intValue();
        c.nomeParceiro = texto(rs, "NOMEPARC"); c.razaoParceiro = texto(rs, "RAZAO_PARCEIRO");
        c.cnpjCpf = texto(rs, "CNPJ_CPF_PARCEIRO"); c.telefone = texto(rs, "TELEFONE");
        c.cep = texto(rs, "CEP"); c.endereco = texto(rs, "ENDERECO");
        c.parceiroAtivo = texto(rs, "PARC_STATUS"); c.produtoAtivo = texto(rs, "PROD_STATUS");
        c.codAnvisa = texto(rs, "CODANVISA"); c.marca = texto(rs, "MARCA");
        c.codFabricante = rs.getBigDecimal("CODFABRICANTE");
        c.codigoFabricante = c.codFabricante == null
                ? codigoSeguro(c.marca) : c.codFabricante.toPlainString();
        c.nomeFabricante = texto(rs, "NOME_FABRICANTE");
        c.cnpjFabricante = texto(rs, "CNPJ_FABRICANTE");
        c.localizacao = texto(rs, "DESCRLOCAL"); c.poder = texto(rs, "PODER");
        c.chaveMovimento = texto(rs, "CHAVE_MOVIMENTO");
        preencherIdentidades(c);
        return c;
    }

    private static void preencherIdentidades(CandidatoGenesis c) {
        String parceiro = numero(c.codParc); String grupo = numero(c.codGrupoProd); String produto = numero(c.codProd);
        String serie = br.com.zaya.mahospitalar.genesis.service.PayloadGenesisFactory.normalizarSerie(c.serie);
        c.chaveCliente = parceiro; c.chaveSetor = parceiro == null ? null : parceiro + "|PADRAO"; c.chaveMaterial = grupo;
        c.chaveFabricante = c.codigoFabricante; c.chaveModelo = produto;
        c.chaveItem = produto == null || serie == null ? null : produto + "|" + serie;
        c.codigoExternoCliente = prefixo("SNK-CLI-", parceiro); c.codigoExternoSetor = prefixo("SNK-SET-", parceiro);
        c.codigoExternoMaterial = prefixo("SNK-MAT-", grupo); c.codigoExternoFabricante = prefixo("SNK-FAB-", c.codigoFabricante);
        c.codigoExternoItem = produto == null || serie == null ? null : "SNK-ITEM-" + produto + "-" + serie;
    }

    private static String numero(BigDecimal value) {
        return value == null ? null : value.toPlainString();
    }

    private static String prefixo(String prefixo, String value) {
        return value == null || value.trim().isEmpty() ? null : prefixo + value;
    }

    private static String texto(ResultSet rs, String campo) throws Exception {
        String value = rs.getString(campo);
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }

    private static String codigoSeguro(String value) {
        if (value == null) return null;
        String normalizado = value.trim().toUpperCase().replaceAll("[^A-Z0-9]+", "-");
        return normalizado.replaceAll("(^-+|-+$)", "");
    }
}
