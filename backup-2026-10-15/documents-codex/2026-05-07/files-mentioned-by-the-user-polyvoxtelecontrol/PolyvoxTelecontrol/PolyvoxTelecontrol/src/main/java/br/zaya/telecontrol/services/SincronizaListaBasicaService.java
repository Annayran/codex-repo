package br.zaya.telecontrol.services;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.zaya.telecontrol.utils.LogUtil;
import com.sankhya.util.TimeUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SincronizaListaBasicaService {

    private static final String LOG_PREFIX = "[LISTA-BASICA] ";

    public static Resultado sincronizarMateriasPrimas() throws Exception {
        return sincronizarMateriasPrimas("this.AD_ENVLISTTLC = 'S'");
    }

    public static Resultado sincronizarMateriasPrimas(String where) throws Exception {
        Resultado resultado = new Resultado();
        LogUtil.SYNC = "ListaBasica";

        List<MateriaPrimaListaBasica> materiasPrimas = buscarMateriasPrimas(where);

        System.out.println(LOG_PREFIX + "Materias-primas encontradas para envio | quantidade=" + materiasPrimas.size());

        JapeWrapper produtoDAO = JapeFactory.dao("AD_TCLPRO");
        JapeWrapper pecaDAO = JapeFactory.dao("AD_TCLPCA");
        JapeWrapper tgfproDAO = JapeFactory.dao("Produto");
        JapeWrapper listaBasicaDAO = JapeFactory.dao("AD_TCLLTB");

        for (MateriaPrimaListaBasica materiaPrimaVO : materiasPrimas) {
            resultado.processadas++;

            BigDecimal codProdPA = materiaPrimaVO.codProdPa;
            BigDecimal codProdMP = materiaPrimaVO.codProdMp;
            BigDecimal seqMp = materiaPrimaVO.seqMp;

            LogUtil.REGISTRO = "PA=" + codProdPA + " MP=" + codProdMP;

            try {
                if (codProdPA == null || codProdMP == null) {
                    resultado.comErro++;
                    System.out.println(LOG_PREFIX + "Registro ignorado por falta de CODPRODPA ou CODPRODMP | SEQMP=" + seqMp
                            + " | CODPRODPA=" + codProdPA + " | CODPRODMP=" + codProdMP);
                    continue;
                }

                DynamicVO produtoPaVO = produtoDAO.findByPK(codProdPA);
                if (produtoPaVO == null) {
                    resultado.produtoPaNaoEncontrado++;
                    System.out.println(LOG_PREFIX + "Produto PA nao encontrado na AD_TCLPRO | CODPRODPA=" + codProdPA
                            + " | CODPRODMP=" + codProdMP + " | SEQMP=" + seqMp);
                    continue;
                }

                DynamicVO pecaMpVO = pecaDAO.findByPK(codProdMP);
                if (pecaMpVO == null) {
                    resultado.pecaNaoEncontrada++;
                    System.out.println(LOG_PREFIX + "Materia-prima nao encontrada na AD_TCLPCA | CODPRODPA=" + codProdPA
                            + " | CODPRODMP=" + codProdMP + " | SEQMP=" + seqMp);
                    continue;
                }

                DynamicVO tgfProdutoPaVO = tgfproDAO.findByPK(codProdPA);
                DynamicVO tgfProdutoMpVO = tgfproDAO.findByPK(codProdMP);
                String refProduto = tgfProdutoPaVO == null ? null : tgfProdutoPaVO.asString("REFERENCIA");
                String refPeca = tgfProdutoMpVO == null ? null : tgfProdutoMpVO.asString("REFERENCIA");

                DynamicVO listaExistenteVO = listaBasicaDAO.findByPK(codProdPA, codProdMP);
                if (listaExistenteVO != null) {
                    resultado.jaExistentes++;
                    System.out.println(LOG_PREFIX + "Lista basica ja existe, nada a fazer | CODPROD=" + codProdPA
                            + " | CODPECA=" + codProdMP);
                    continue;
                }

                FluidCreateVO creVO = listaBasicaDAO.create();
                creVO.set("CODPROD", codProdPA);
                creVO.set("CODPECA", codProdMP);
                creVO.set("QTD", materiaPrimaVO.qtdMistura);
                creVO.set("DHALTER", TimeUtils.getNow());
                creVO.set("REFPRODUTO", refProduto);
                creVO.set("REFPECA", refPeca);
                creVO.save();

                resultado.inseridas++;
                System.out.println(LOG_PREFIX + "Lista basica inserida | CODPROD=" + codProdPA
                        + " | CODPECA=" + codProdMP
                        + " | QTD=" + materiaPrimaVO.qtdMistura
                        + " | REFPRODUTO=" + refProduto
                        + " | REFPECA=" + refPeca);
            } catch (Exception e) {
                resultado.comErro++;
                System.out.println(LOG_PREFIX + "Erro ao processar materia-prima | CODPRODPA=" + codProdPA
                        + " | CODPRODMP=" + codProdMP
                        + " | SEQMP=" + seqMp
                        + " | erro=" + e.getMessage());
                LogUtil.saveLog(e);
            }
        }

        System.out.println(LOG_PREFIX + "Fim sincronizacao | " + resultado);

        return resultado;
    }

    private static List<MateriaPrimaListaBasica> buscarMateriasPrimas(String where) throws Exception {
        List<MateriaPrimaListaBasica> materiasPrimas = new ArrayList<MateriaPrimaListaBasica>();
        JdbcWrapper jdbc = null;
        ResultSet rset = null;

        try {
            EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
            jdbc = dwfEntityFacade.getJdbcWrapper();
            jdbc.openSession();

            NativeSql sql = new NativeSql(jdbc);
            sql.appendSql(" SELECT SEQMP, CODPRODPA, CODPRODMP, QTDMISTURA ");
            sql.appendSql(" FROM TPRLMP ");
            sql.appendSql(" WHERE " + normalizarWhereSql(where));
            sql.appendSql(" ORDER BY CODPRODPA, SEQMP ");

            rset = sql.executeQuery();
            while (rset.next()) {
                MateriaPrimaListaBasica materiaPrima = new MateriaPrimaListaBasica();
                materiaPrima.seqMp = rset.getBigDecimal("SEQMP");
                materiaPrima.codProdPa = rset.getBigDecimal("CODPRODPA");
                materiaPrima.codProdMp = rset.getBigDecimal("CODPRODMP");
                materiaPrima.qtdMistura = rset.getBigDecimal("QTDMISTURA");
                materiasPrimas.add(materiaPrima);
            }
        } finally {
            if (rset != null) {
                try {
                    rset.close();
                } catch (Exception ignored) {
                }
            }
            JdbcWrapper.closeSession(jdbc);
        }

        return materiasPrimas;
    }

    private static String normalizarWhereSql(String where) {
        if (where == null || where.trim().isEmpty()) {
            return "AD_ENVLISTTLC = 'S'";
        }

        return where.replace("this.", "").trim();
    }

    private static class MateriaPrimaListaBasica {
        private BigDecimal seqMp;
        private BigDecimal codProdPa;
        private BigDecimal codProdMp;
        private BigDecimal qtdMistura;
    }

    public static class Resultado {
        public int processadas = 0;
        public int inseridas = 0;
        public int jaExistentes = 0;
        public int produtoPaNaoEncontrado = 0;
        public int pecaNaoEncontrada = 0;
        public int comErro = 0;

        public void somar(Resultado outro) {
            if (outro == null) {
                return;
            }

            this.processadas += outro.processadas;
            this.inseridas += outro.inseridas;
            this.jaExistentes += outro.jaExistentes;
            this.produtoPaNaoEncontrado += outro.produtoPaNaoEncontrado;
            this.pecaNaoEncontrada += outro.pecaNaoEncontrada;
            this.comErro += outro.comErro;
        }

        @Override
        public String toString() {
            return "Resultado{" +
                    "processadas=" + processadas +
                    ", inseridas=" + inseridas +
                    ", jaExistentes=" + jaExistentes +
                    ", produtoPaNaoEncontrado=" + produtoPaNaoEncontrado +
                    ", pecaNaoEncontrada=" + pecaNaoEncontrada +
                    ", comErro=" + comErro +
                    '}';
        }
    }
}
