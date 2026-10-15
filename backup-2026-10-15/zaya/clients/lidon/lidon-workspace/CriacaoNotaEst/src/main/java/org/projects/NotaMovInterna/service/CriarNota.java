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
import java.sql.ResultSet;
import java.util.Collection;
import org.projects.NotaMovInterna.builder.CabecalhoBuilder;
import org.projects.NotaMovInterna.model.CabecalhoNotaOS;
import org.projects.NotaMovInterna.repository.CabecalhoNotaRepository;

public class CriarNota {
    private static final String LOG_PREFIX = "[NotaMovInterna][CriarNota] ";
    private static final String PROP_MODELO_CABECALHO = "notamovinterna.modeloCabecalho";
    private static final BigDecimal MODELO_CABECALHO_PADRAO = BigDecimal.valueOf(96174L);
    private static final int RETRY_MAX_TENTATIVAS = 10;
    private static final long RETRY_INTERVALO_MS = 300L;
    private final DynamicVO event;

    public CriarNota(DynamicVO event) {
        this.event = event;
    }

    public BigDecimal processarNotas() throws Exception {
        BigDecimal numOS = this.event.asBigDecimal("NUMOS");
        this.log("Inicio processarNotas - NUMOS=" + numOS);
        if (numOS == null) {
            throw new IllegalArgumentException("NUMOS nao informado para criacao da nota interna.");
        } else {
            BigDecimal nunotaPersistida = this.buscarNunotaIntPersistido(numOS);
            if (nunotaPersistida != null) {
                this.event.setProperty("NUNOTAINT", nunotaPersistida);
                this.log("NUNOTAINT ja persistido para NUMOS=" + numOS + ", reaproveitando NUNOTAINT=" + nunotaPersistida);
                return nunotaPersistida;
            }

            BigDecimal nunotaExistente = this.buscarNunotaInternaJaCriada(numOS);
            if (nunotaExistente != null) {
                this.log("Nota interna ja existe para NUMOS=" + numOS + ", NUNOTA=" + nunotaExistente + ". Persistindo NUNOTAINT na OS.");
                try {
                    this.atualizarNunotaInt(numOS, nunotaExistente);
                    this.event.setProperty("NUNOTAINT", nunotaExistente);
                } catch (Exception e) {
                    if (!this.isErroTriggerNunotaInt(e)) {
                        throw e;
                    }

                    this.log("Persistencia sincrona falhou ao reaproveitar nota existente para NUMOS=" + numOS + ", NUNOTAINT=" + nunotaExistente + ". Agendando retry assincrono. Erro=" + e.getMessage());
                    this.event.setProperty("NUNOTAINT", nunotaExistente);
                    this.agendarAtualizacaoNunotaInt(numOS, nunotaExistente);
                }

                return nunotaExistente;
            }

            EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
            CabecalhoNotaOS cabecalho = (new CabecalhoNotaRepository()).buscarCabecalho(this.event);
            if (cabecalho == null) {
                throw new NullPointerException("CabecalhoNotaOS esta null para NUMOS=" + numOS);
            } else {
                BigDecimal modeloCabecalho = this.resolverModeloCabecalho();
                this.log("Criando nota interna a partir do modelo NUNOTA=" + modeloCabecalho + ", NUMOS=" + numOS + ", CODEMP=" + cabecalho.getCODEMP() + ", CODPARC=" + cabecalho.getCODPARC());
                DynamicVO notaDestino = (new CabecalhoBuilder()).criarNotaPorModelo(dwf, modeloCabecalho, cabecalho.getCODEMP(), cabecalho.getNUMOS(), cabecalho.getCODPARC());
                if (notaDestino == null) {
                    throw new NullPointerException("Falha ao criar nota interna para NUMOS=" + numOS);
                } else {
                    BigDecimal nuNotaDestino = notaDestino.asBigDecimal("NUNOTA");
                    if (nuNotaDestino == null) {
                        throw new NullPointerException("Nota criada sem NUNOTA para NUMOS=" + numOS);
                    } else {
                        try {
                            this.atualizarNunotaInt(numOS, nuNotaDestino);
                            this.event.setProperty("NUNOTAINT", nuNotaDestino);
                        } catch (Exception e) {
                            if (!this.isErroTriggerNunotaInt(e)) {
                                throw e;
                            }

                            this.log("Persistencia sincrona falhou por trigger para NUMOS=" + numOS + ", NUNOTAINT=" + nuNotaDestino + ". Agendando retry assincrono. Erro=" + e.getMessage());
                            this.event.setProperty("NUNOTAINT", nuNotaDestino);
                            this.agendarAtualizacaoNunotaInt(numOS, nuNotaDestino);
                        }

                        this.log("Fim processarNotas - NUMOS=" + numOS + ", NUNOTAINT=" + nuNotaDestino);
                        return nuNotaDestino;
                    }
                }
            }
        }
    }

    private void agendarAtualizacaoNunotaInt(final BigDecimal numOS, final BigDecimal nuNotaDestino) {
        Thread worker = new Thread(new Runnable() {
            public void run() {
                for(int tentativa = 1; tentativa <= CriarNota.RETRY_MAX_TENTATIVAS; ++tentativa) {
                    try {
                        Thread.sleep(CriarNota.RETRY_INTERVALO_MS);
                        CriarNota.this.atualizarNunotaInt(numOS, nuNotaDestino);
                        CriarNota.this.log("Retry persistencia OK - NUMOS=" + numOS + ", NUNOTAINT=" + nuNotaDestino + ", tentativa=" + tentativa);
                        return;
                    } catch (Exception retryEx) {
                        CriarNota.this.log("Retry persistencia falhou - NUMOS=" + numOS + ", NUNOTAINT=" + nuNotaDestino + ", tentativa=" + tentativa + ", erro=" + retryEx.getMessage());
                    }
                }

                CriarNota.this.log("ERRO: Nao foi possivel persistir NUNOTAINT apos retries - NUMOS=" + numOS + ", NUNOTAINT=" + nuNotaDestino);
            }
        }, "NotaMovInterna-NUNOTAINT-" + numOS);
        worker.setDaemon(true);
        worker.start();
    }

    private BigDecimal buscarNunotaIntPersistido(BigDecimal numOS) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            jdbc = JapeFactory.getEntityFacade().getJdbcWrapper();
            jdbc.openSession();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT NUNOTAINT FROM AD_TCSOSE WHERE NUMOS = :NUMOS");
            sql.setNamedParameter("NUMOS", numOS);
            rs = sql.executeQuery();
            return rs.next() ? rs.getBigDecimal("NUNOTAINT") : null;
        } finally {
            if (rs != null) {
                rs.close();
            }

            JdbcWrapper.closeSession(jdbc);
        }
    }

    private BigDecimal buscarNunotaInternaJaCriada(BigDecimal numOS) throws Exception {
        Collection<DynamicVO> notas = JapeFactory.dao("CabecalhoNota").find("this.AD_NUMOSOFICINA = ?", new Object[]{numOS});
        if (notas == null || notas.isEmpty()) {
            return null;
        } else {
            DynamicVO nota = (DynamicVO)notas.iterator().next();
            BigDecimal nunota = nota.asBigDecimal("NUNOTA");
            this.log("Nota interna encontrada por AD_NUMOSOFICINA - NUMOS=" + numOS + ", NUNOTA=" + nunota);
            return nunota;
        }
    }

    private BigDecimal resolverModeloCabecalho() {
        String configurado = System.getProperty(PROP_MODELO_CABECALHO);
        if (configurado != null && !configurado.trim().isEmpty()) {
            return new BigDecimal(configurado.trim());
        }

        return MODELO_CABECALHO_PADRAO;
    }

    private void log(String message) {
        System.out.println(LOG_PREFIX + message);
    }

    private boolean isErroTriggerNunotaInt(Throwable t) {
        for(Throwable atual = t; atual != null; atual = atual.getCause()) {
            String msg = atual.getMessage();
            if (msg != null) {
                String upper = msg.toUpperCase();
                if (upper.contains("ORA-01403") || upper.contains("TRG_UPD_AD_TCSOSE_LIDON") || upper.contains("ORA-04088")) {
                    return true;
                }
            }
        }

        return false;
    }

    public void atualizarNunotaInt(BigDecimal numOS, BigDecimal nuNotaDestino) throws Exception {
        JdbcWrapper jdbc = null;
        NativeSql sql = null;
        ResultSet rs = null;

        try {
            jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();
            sql = new NativeSql(jdbc);
            sql.appendSql("UPDATE AD_TCSOSE SET NUNOTAINT = :NUNOTAINT WHERE NUMOS = :NUMOS");
            sql.setNamedParameter("NUNOTAINT", nuNotaDestino);
            sql.setNamedParameter("NUMOS", numOS);
            boolean updateExecutado = sql.executeUpdate();
            sql = new NativeSql(jdbc);
            sql.appendSql("SELECT NUNOTAINT FROM AD_TCSOSE WHERE NUMOS = :NUMOS");
            sql.setNamedParameter("NUMOS", numOS);
            rs = sql.executeQuery();
            BigDecimal nunotaPersistida = null;
            if (rs.next()) {
                nunotaPersistida = rs.getBigDecimal("NUNOTAINT");
            }

            this.log("Persistencia NUNOTAINT - NUMOS=" + numOS + ", NUNOTAINT=" + nuNotaDestino + ", updateExecutado=" + updateExecutado + ", persistido=" + nunotaPersistida);
            if (nunotaPersistida == null || nuNotaDestino.compareTo(nunotaPersistida) != 0) {
                throw new IllegalStateException("Falha ao persistir NUNOTAINT. NUMOS=" + numOS + ", esperado=" + nuNotaDestino + ", persistido=" + nunotaPersistida);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            JdbcWrapper.closeSession(jdbc);
        }

    }
}
