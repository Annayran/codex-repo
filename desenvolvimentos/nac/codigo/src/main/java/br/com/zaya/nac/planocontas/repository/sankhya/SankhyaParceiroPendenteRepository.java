package br.com.zaya.nac.planocontas.repository.sankhya;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.nac.planocontas.exception.RegraNegocioException;
import br.com.zaya.nac.planocontas.repository.ParceiroPendenteRepository;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public final class SankhyaParceiroPendenteRepository
        implements ParceiroPendenteRepository {

    @Override
    public List<BigDecimal> buscarCodigosPendentes(int limite) throws Exception {
        if (limite <= 0 || limite > 200) {
            throw new RegraNegocioException(
                    "O tamanho do lote deve estar entre 1 e 200 parceiros.");
        }

        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet resultado = null;
        List<BigDecimal> codigos = new ArrayList<BigDecimal>(limite);
        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT PAR.CODPARC ");
            sql.appendSql("FROM TGFPAR PAR ");
            sql.appendSql("WHERE PAR.ATIVO = :ATIVO ");
            sql.appendSql("AND EXISTS (SELECT 1 FROM TGFCAB CAB ");
            sql.appendSql("WHERE CAB.CODPARC = PAR.CODPARC ");
            sql.appendSql("AND CAB.DTMOV >= :DATA_INICIAL) ");
            sql.appendSql("AND ((PAR.CLIENTE = :SIM ");
            sql.appendSql("AND (PAR.AD_ZYNACCTACLI IS NULL ");
            sql.appendSql("OR PAR.AD_ZYNACCTACLI <> :SIM)) ");
            sql.appendSql("OR (PAR.FORNECEDOR = :SIM ");
            sql.appendSql("AND (PAR.AD_ZYNACCTAFOR IS NULL ");
            sql.appendSql("OR PAR.AD_ZYNACCTAFOR <> :SIM))) ");
            sql.appendSql("ORDER BY PAR.CODPARC");
            sql.setNamedParameter("ATIVO", "S");
            sql.setNamedParameter("SIM", "S");
            sql.setNamedParameter("DATA_INICIAL", Date.valueOf("2026-01-01"));
            sql.setFetchSize(limite);
            sql.setMaxRows(limite);

            resultado = sql.executeQuery();
            while (resultado.next()) {
                codigos.add(resultado.getBigDecimal("CODPARC"));
            }
            return codigos;
        } finally {
            if (resultado != null) {
                try {
                    resultado.close();
                } catch (Exception ignorada) {
                    // NativeSql.releaseResources fará a liberação complementar.
                }
            }
            if (sql != null) {
                NativeSql.releaseResources(sql);
            }
            if (jdbc != null) {
                JdbcWrapper.closeSession(jdbc);
            }
        }
    }
}
