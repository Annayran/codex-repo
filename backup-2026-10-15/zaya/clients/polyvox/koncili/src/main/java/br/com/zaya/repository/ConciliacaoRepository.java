package br.com.zaya.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.zaya.models.ConciliacaoResponse;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ConciliacaoRepository {

    private static final Logger LOG = Logger.getLogger(ConciliacaoRepository.class.getName());

    public boolean existsByIdTransacaoAndCodVenda(String idTransacao, String codVenda) throws Exception {
        JapeWrapper dao = JapeFactory.dao("AD_TLNFKNCL");
        Collection<DynamicVO> existentes = dao.find("this.IDTRA = ? AND this.CODVEND = ?", safe(idTransacao), safe(codVenda));
        return !existentes.isEmpty();
    }

    public boolean existsByIdTransacaoAndIdExtrato(String idTransacao, String idExtrato) throws Exception {
        JapeWrapper dao = JapeFactory.dao("AD_TLNFKNCL");
        Collection<DynamicVO> existentes = dao.find("this.IDTRA = ? AND this.IDEX = ?", safe(idTransacao), safe(idExtrato));
        return !existentes.isEmpty();
    }

    public BigDecimal buscarMaiorId(JdbcWrapper jdbc) throws Exception {
        NativeSql ns = new NativeSql(jdbc);
        ns.setReuseStatements(false);

        ResultSet rs = null;
        try {
            rs = ns.executeQuery("SELECT NVL(MAX(ID), 0) AS MAXID FROM AD_TLNFKNCL");
            if (rs.next()) {
                BigDecimal max = rs.getBigDecimal("MAXID");
                return max != null ? max : BigDecimal.ZERO;
            }
            return BigDecimal.ZERO;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public void inserir(ConciliacaoResponse item, BigDecimal idRegistro) throws Exception {
        Timestamp dhcria = toTimestampDateTime(item.created_at);
        Timestamp dhatt = toTimestampDateTime(item.updated_at);
        if (dhcria == null) {
            dhcria = new Timestamp(System.currentTimeMillis());
        }
        if (dhatt == null) {
            dhatt = dhcria;
        }

        JapeFactory.dao("AD_TLNFKNCL").create()
                .set("ID", idRegistro)
                .set("NOMEMP", safe(item.nome_empresa))
                .set("CNPJ", safe(item.cnpj_empresa))
                .set("DHCRIA", dhcria)
                .set("DHATT", dhatt)
                .set("IDTRA", safe(item.id_transacao))
                .set("IDEX", safe(item.id_extrato))
                .set("IDKNCL", limparPontoFinal(safe(item.id_koncili)))
                .set("CODVEND", safe(item.cod_venda))
                .set("DTVEND", toSqlDate(item.data_venda))
                .set("DTPAG", toSqlDate(item.data_pagamento))
                .set("NRONF", toBigDecimalInteiro(item.numero_nota_fiscal))
                .set("DTNFE", toSqlDate(item.data_nota_fiscal))
                .set("FORMPAG", safe(item.forma_pagamento))
                .set("VLRESP", toBigDecimal(item.valor_esperado))
                .set("PERCOM", toBigDecimal(item.percentual_comissao_esperado))
                .set("DTESP", toSqlDate(item.data_esperada))
                .set("DTLIB", toSqlDate(item.data_liberacao))
                .set("CONC", boolAsText(item.conciliado))
                .set("CONCERP", boolAsText(item.resolvido_erp))
                .set("IDCONC", toBigDecimalInteiro(item.id_conciliacao))
                .set("OBS", safe(item.observacao))
                .set("CANAL", safe(item.canal))
                .set("CONTA", safe(item.nome_conta))
                .set("CLIENTE", safe(item.nome_cliente))
                .set("DOC", safe(item.documento_cliente))
                .set("SIT", safe(item.situacao))
                .set("VLRVEND", toBigDecimal(item.valor_venda))
                .set("VLRCOM", toBigDecimal(item.valor_comissao))
                .set("VLRTX", toBigDecimal(item.valor_taxas))
                .set("VLRFRETE", toBigDecimal(item.valor_frete))
                .set("DTCNLINI", toSqlDate(item.data_conciliacao_inicial))
                .set("DTCNLFIM", toSqlDate(item.data_conciliacao_final))
                .set("DTCNLFEC", toSqlDate(item.data_conciliacao_fechamento))
                .set("CNLPAR", boolAsText(item.conciliado_parcialmente))
                .set("RSLDKNCLI", boolAsText(item.resolvido_koncili))
                .set("SERIENOTA", toBigDecimalInteiro(item.serie_nota_fiscal))
                .set("IDVENDA", safe(item.id_venda))
                .set("TIPEX", safe(item.tipo_extrato))
                .set("VLRLIB", toBigDecimal(item.valor_liberado))
                .save();
    }

    public int vincularNotasPendentes(JdbcWrapper jdbc) throws Exception {
        int atualizados = 0;

        NativeSql sql = new NativeSql(jdbc);
        sql.setReuseStatements(false);

        ResultSet rs = null;
        try {
            rs = sql.executeQuery(
                    "SELECT CAB.NUNOTA, KNCL.ID FROM TGFCAB CAB " +
                            "JOIN AD_TLNFKNCL KNCL ON CAB.NUMNOTA = KNCL.NRONF " +
                            "AND CAB.SERIENOTA = KNCL.SERIENOTA " +
                            "AND CAB.VLRNOTA = KNCL.VLRVEND " +
                            "WHERE KNCL.NUNOTA IS NULL"
            );

            JapeWrapper dao = JapeFactory.dao("AD_TLNFKNCL");
            while (rs.next()) {
                BigDecimal nuNota = rs.getBigDecimal("NUNOTA");
                BigDecimal idKncl = rs.getBigDecimal("ID");

                dao.prepareToUpdateByPK(idKncl)
                        .set("NUNOTA", nuNota)
                        .update();

                atualizados++;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return atualizados;
    }

    public List<ConciliacaoPendente> buscarPendentesConciliacao(JdbcWrapper jdbc, int limite) throws Exception {
        return buscarPendentesCompensacao(jdbc, limite);
    }

    public List<ConciliacaoPendente> buscarPendentesCompensacao(JdbcWrapper jdbc, int limite) throws Exception {
        List<ConciliacaoPendente> pendencias = new ArrayList<ConciliacaoPendente>();

        String sqlText = "SELECT KCL.NUNOTA AS NUNOTA, KCL.CODVEND AS CODVEND, KCL.VLRVEND AS VLRVEND, KCL.VLRCOM AS VLRCOM, KCL.VLRFRETE AS VLRFRETE, KCL.VLRTX AS VLRTX, KCL.DTPAG AS DTPAG, KCL.DTLIB AS DTLIB, KCL.VLRLIB AS VLRLIB, KCL.ID AS ID, KCL.IDTRA AS IDTRA " +
                "FROM AD_TLNFKNCL KCL " +
                "WHERE NVL(UPPER(TRIM(KCL.CONCERP)), 'FALSE') <> 'TRUE' " +
                "AND KCL.NUNOTA IS NOT NULL " +
                "AND ROWNUM <= " + limite;

        LOG.info(String.format("[KONCILI_BUSCA_PENDENTES_INICIO] limite=%s", limite));

        NativeSql sql = new NativeSql(jdbc);
        ResultSet rs = null;
        try {
            rs = sql.executeQuery(sqlText);
            while (rs.next()) {
                ConciliacaoPendente p = new ConciliacaoPendente(
                        rs.getBigDecimal("ID"),
                        rs.getString("NUNOTA"),
                        rs.getString("CODVEND"),
                        rs.getBigDecimal("VLRVEND"),
                        rs.getBigDecimal("VLRCOM"),
                        rs.getBigDecimal("VLRFRETE"),
                        rs.getBigDecimal("VLRTX"),
                        rs.getTimestamp("DTPAG"),
                        rs.getTimestamp("DTLIB"),
                        rs.getBigDecimal("VLRLIB"),
                        rs.getString("IDTRA")
                );
                pendencias.add(p);
                LOG.info(String.format(
                        "[KONCILI_PENDENTE_LOCALIZADA] id=%s nunota=%s codvend=%s vlrvend=%s vlrcom=%s vlrfrete=%s vlrtx=%s vrlib=%s dtpag=%s dtlib=%s idtra=%s",
                        p.getId(),
                        p.getNuNota(),
                        p.getCodVenda(),
                        p.getValorVenda(),
                        p.getValorComissao(),
                        p.getValorFrete(),
                        p.getValorTaxas(),
                        p.getValorLiberado(),
                        p.getDataPagamento(),
                        p.getDataLiberacao(),
                        p.getIdTransacao()
                ));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        LOG.info(String.format("[KONCILI_BUSCA_PENDENTES_FIM] total=%s limite=%s", pendencias.size(), limite));

        return pendencias;
    }

    public ConciliacaoPendente buscarPendenciaPorId(JdbcWrapper jdbc, BigDecimal id) throws Exception {

        LOG.info(String.format("[KONCILI_BUSCA_PENDENCIA_UNICA] id=%s", id));

        NativeSql sql = new NativeSql(jdbc);
        ResultSet rs = null;
        try {
            sql.setReuseStatements(false);
            Collection<BigDecimal> params = new ArrayList<>();
            params.add(id);
            rs = sql.executeQuery("SELECT KCL.NUNOTA AS NUNOTA, KCL.CODVEND AS CODVEND, KCL.VLRVEND AS VLRVEND, KCL.VLRCOM AS VLRCOM, KCL.VLRFRETE AS VLRFRETE, KCL.VLRTX AS VLRTX, KCL.DTPAG AS DTPAG, KCL.DTLIB AS DTLIB, KCL.VLRLIB AS VLRLIB, KCL.ID AS ID, KCL.IDTRA AS IDTRA FROM AD_TLNFKNCL KCL WHERE KCL.ID = ? AND NVL(UPPER(TRIM(KCL.CONCERP)), 'FALSE') <> 'TRUE'", params);
            if (rs.next()) {
                ConciliacaoPendente p = new ConciliacaoPendente(
                        rs.getBigDecimal("ID"),
                        rs.getString("NUNOTA"),
                        rs.getString("CODVEND"),
                        rs.getBigDecimal("VLRVEND"),
                        rs.getBigDecimal("VLRCOM"),
                        rs.getBigDecimal("VLRFRETE"),
                        rs.getBigDecimal("VLRTX"),
                        rs.getTimestamp("DTPAG"),
                        rs.getTimestamp("DTLIB"),
                        rs.getBigDecimal("VLRLIB"),
                        rs.getString("IDTRA")
                );
                LOG.info(String.format("[KONCILI_PENDENCIA_UNICA_LOCALIZADA] id=%s nunota=%s", p.getId(), p.getNuNota()));
                return p;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        LOG.warning(String.format("[KONCILI_PENDENCIA_UNICA_NAO_ENCONTRADA] id=%s", id));
        return null;
    }

    public void marcarComoConciliado(BigDecimal idKncl, BigDecimal nuFin) throws Exception {
        LOG.info(String.format("[KONCILI_MARCAR_CONCILIADO] id=%s nufin=%s", idKncl, nuFin));
        JapeFactory.dao("AD_TLNFKNCL")
                .prepareToUpdateByPK(idKncl)
                .set("CONCERP", "true")
                .set("RSLDKNCLI", "S")
                .set("NUFIN", nuFin)
                .update();
    }

    public void marcarEtapaProcessamento(
            BigDecimal idKncl,
            String etapa,
            BigDecimal nuFinReceita,
            BigDecimal nuFinDespesa
    ) throws Exception {
        LOG.info(String.format("[KONCILI_MARCAR_ETAPA] id=%s etapa=%s nufin_receita=%s nufin_despesa=%s", idKncl, etapa, nuFinReceita, nuFinDespesa));
        JapeWrapper dao = JapeFactory.dao("AD_TLNFKNCL");
        dao.prepareToUpdateByPK(idKncl)
                .set("ETAPA_STATUS", etapa)
                .set("NUFIN_RECEITA", nuFinReceita)
                .set("NUFIN_DESPESA", nuFinDespesa)
                .set("DHATT", new Timestamp(System.currentTimeMillis()))
                .update();
    }

    public static class EtapaProcessamento {
        public static final String RATEADO = "RATEADO";
        public static final String COMPENSADO = "COMPENSADO";
        public static final String BAIXADO = "BAIXADO";
        public static final String ERRO = "ERRO";

        private EtapaProcessamento() {
        }
    }

    public static class ConciliacaoPendente {
        private final BigDecimal id;
        private final String nuNota;
        private final String codVenda;
        private final BigDecimal valorVenda;
        private final BigDecimal valorComissao;
        private final BigDecimal valorFrete;
        private final BigDecimal valorTaxas;
        private final Timestamp dataPagamento;
        private final Timestamp dataLiberacao;
        private final BigDecimal valorLiberado;
        private final String idTransacao;

        public ConciliacaoPendente(
                BigDecimal id,
                String nuNota,
                String codVenda,
                BigDecimal valorVenda,
                BigDecimal valorComissao,
                BigDecimal valorFrete,
                BigDecimal valorTaxas,
                Timestamp dataPagamento,
                Timestamp dataLiberacao,
                BigDecimal valorLiberado,
                String idTransacao
        ) {
            this.id = id;
            this.nuNota = nuNota;
            this.codVenda = codVenda;
            this.valorVenda = valorVenda;
            this.valorComissao = valorComissao;
            this.valorFrete = valorFrete;
            this.valorTaxas = valorTaxas;
            this.dataPagamento = dataPagamento;
            this.dataLiberacao = dataLiberacao;
            this.valorLiberado = valorLiberado;
            this.idTransacao = idTransacao;
        }

        public BigDecimal getId() {
            return id;
        }

        public String getNuNota() {
            return nuNota;
        }

        public String getCodVenda() {
            return codVenda;
        }

        public BigDecimal getValorVenda() {
            return valorVenda;
        }

        public BigDecimal getValorComissao() {
            return valorComissao;
        }

        public BigDecimal getValorFrete() {
            return valorFrete;
        }

        public BigDecimal getValorTaxas() {
            return valorTaxas;
        }

        public Timestamp getDataPagamento() {
            return dataPagamento;
        }

        public Timestamp getDataLiberacao() {
            return dataLiberacao;
        }

        public BigDecimal getValorLiberado() {
            return valorLiberado;
        }

        public String getIdTransacao() {
            return idTransacao;
        }
    }

    private static Date toSqlDate(String valor) {
        String v = safe(valor);
        if (v == null) {
            return null;
        }
        if (v.length() > 10) {
            v = v.substring(0, 10);
        }
        try {
            java.util.Date parsed = new SimpleDateFormat("yyyy-MM-dd").parse(v);
            return new Date(parsed.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    private static Timestamp toTimestampDateTime(String valor) {
        String v = safe(valor);
        if (v == null) {
            return null;
        }
        v = v.replace('T', ' ');
        if (v.endsWith("Z")) {
            v = v.substring(0, v.length() - 1);
        }
        int idxPonto = v.indexOf('.');
        if (idxPonto > 0) {
            v = v.substring(0, idxPonto);
        }

        try {
            java.util.Date parsed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(v);
            return new Timestamp(parsed.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    private static BigDecimal toBigDecimal(Object valor) {
        if (valor == null) {
            return null;
        }
        try {
            return new BigDecimal(valor.toString().trim());
        } catch (Exception e) {
            return null;
        }
    }

    private static BigDecimal toBigDecimalInteiro(Object valor) {
        BigDecimal bd = toBigDecimal(valor);
        if (bd == null) {
            return null;
        }

        BigDecimal normalizado = bd.stripTrailingZeros();
        if (normalizado.scale() > 0) {
            return null;
        }
        return normalizado;
    }

    private static String boolAsText(Boolean valor) {
        return Boolean.TRUE.equals(valor) ? "true" : "false";
    }

    private static String safe(String valor) {
        if (valor == null) {
            return null;
        }
        String trim = valor.trim();
        return trim.isEmpty() ? null : trim;
    }

    private static String limparPontoFinal(String valor) {
        if (valor == null) {
            return null;
        }
        return valor.endsWith(".") ? valor.substring(0, valor.length() - 1) : valor;
    }
}

