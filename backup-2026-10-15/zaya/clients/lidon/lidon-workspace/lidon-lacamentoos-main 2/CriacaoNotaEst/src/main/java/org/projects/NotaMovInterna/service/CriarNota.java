//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.service;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

import java.math.BigDecimal;

import org.projects.NotaMovInterna.builder.CabecalhoBuilder;
import org.projects.NotaMovInterna.model.CabecalhoNotaOS;
import org.projects.NotaMovInterna.repository.CabecalhoNotaRepository;

public class CriarNota {
    private final DynamicVO event;

    public CriarNota(DynamicVO event) {
        this.event = event;
    }

    public void processarNotas() throws Exception {
        BigDecimal numOS = this.event.asBigDecimal("NUMOS");
        System.out.println("=== INÍCIO processarNotas ===");
        System.out.println("NUM OS: " + this.event);
        EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
        System.out.println("EntityFacade obtido: " + dwf);
        CabecalhoNotaOS cabecalho = (new CabecalhoNotaRepository()).buscarCabecalho(this.event);
        System.out.println("Cabecalho OS retornado: " + cabecalho);
        if (cabecalho == null) {
            throw new NullPointerException("CabecalhoNotaOS está NULL");
        } else {
            System.out.println("CODTIPOPER: " + cabecalho.getCODTIPOPER());
            System.out.println("DTABERTURA: " + cabecalho.getDTABERTURA());
            DynamicVO notaDestino = (new CabecalhoBuilder()).criarNotaPorModelo(dwf, BigDecimal.valueOf(64120L),
                    cabecalho.getCODEMP(), cabecalho.getNUMOS(), cabecalho.getCODPARC());
            System.out.println("notaDestino retornada: " + notaDestino);
            if (notaDestino == null) {
                throw new NullPointerException("notaDestino está NULL");
            } else {
                BigDecimal nuNotaDestino = notaDestino.asBigDecimal("NUNOTA");
                System.out.println("NUNOTA DESTINO: " + nuNotaDestino);
                JdbcWrapper jdbc = JapeFactory.getEntityFacade().getJdbcWrapper();
                new NativeSql(jdbc);

                try {
                    (new Thread(() -> {
                        try {
                            Thread.sleep(150L);
                            this.atualizarNunotaInt(numOS, nuNotaDestino);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    })).start();
                } catch (Exception e) {
                    System.out.println("Erro ao executar o UPDATE: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    jdbc.closeSession();
                }

            }
        }
    }

    public void atualizarNunotaInt(BigDecimal numOS, BigDecimal nuNotaDestino) {
        JdbcWrapper jdbc = null;

        try {
            System.out.println("THREAD POS-COMMIT update AD_TCSOSE");
            jdbc = JapeFactory.getEntityFacade().getJdbcWrapper();
            jdbc.openSession();
            NativeSql sql = new NativeSql(jdbc);
            sql.appendSql("UPDATE AD_TCSOSE SET NUNOTAINT = :NUNOTAINT WHERE NUMOS = :NUMOS");
            sql.setNamedParameter("NUNOTAINT", nuNotaDestino);
            sql.setNamedParameter("NUMOS", numOS);
            sql.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro thread update:");
            e.printStackTrace();
        } finally {
            JdbcWrapper.closeSession(jdbc);
        }

    }
}
