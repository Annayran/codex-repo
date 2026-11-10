package br.com.zaya.teleworld.fornecedorxml.sankhya.schedulers;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.teleworld.fornecedorxml.model.repository.FlowFornecedorRepository;
import br.com.zaya.teleworld.fornecedorxml.sankhya.services.SankhyaFlowGateway;
import br.com.zaya.teleworld.fornecedorxml.shared.config.FornecedorXmlConfig;
import br.com.zaya.teleworld.fornecedorxml.shared.utils.DocumentoUtils;

public class FlowFornecedorScheduler implements ScheduledAction {
    private static final AtomicBoolean EXECUTANDO = new AtomicBoolean(false);
    private final FlowFornecedorRepository repository = new FlowFornecedorRepository();
    private final SankhyaFlowGateway gateway = new SankhyaFlowGateway();

    @Override
    public void onTime(ScheduledActionContext context) {
        if (!EXECUTANDO.compareAndSet(false, true)) {
            context.log("[Teleworld][FlowFornecedorScheduler] Execucao ignorada: rotina ja ativa nesta JVM.");
            return;
        }

        try {
            FornecedorXmlConfig config = FornecedorXmlConfig.carregar();
            int sincronizados = revisarFilasIniciadas(config, context);
            int avaliados = 0;
            for (int i = 0; i < config.getLote(); i++) {
                if (!processarUm(config, context)) break;
                avaliados++;
            }
            context.log("[Teleworld][FlowFornecedorScheduler] Execucao concluida. Filas iniciadas revisadas="
                    + sincronizados + ", itens novos avaliados=" + avaliados + '.');
        } catch (Exception erro) {
            context.log("[Teleworld][FlowFornecedorScheduler] Erro geral: " + erro.getMessage());
            erro.printStackTrace();
        } finally {
            EXECUTANDO.set(false);
        }
    }

    private int revisarFilasIniciadas(final FornecedorXmlConfig config,
            final ScheduledActionContext context) throws Exception {
        int revisadas = 0;
        BigDecimal ultimoId = BigDecimal.ZERO;
        for (int i = 0; i < config.getLote(); i++) {
            BigDecimal idProcessado = revisarUmaFilaIniciada(ultimoId, context);
            if (idProcessado == null) break;
            ultimoId = idProcessado;
            revisadas++;
        }
        return revisadas;
    }

    private BigDecimal revisarUmaFilaIniciada(final BigDecimal ultimoId,
            final ScheduledActionContext context) throws Exception {
        JapeSession.SessionHandle hnd = null;
        final BigDecimal[] idProcessado = { null };

        try {
            hnd = JapeSession.open();
            hnd.execWithTX(new JapeSession.TXBlock() {
                @Override
                public void doWithTx() throws Exception {
                    EntityFacade facade = EntityFacadeFactory.getDWFFacade();
                    JdbcWrapper jdbc = null;
                    try {
                        jdbc = facade.getJdbcWrapper();
                        jdbc.openSession();
                        BigDecimal idFila = repository.bloquearProximoIniciado(jdbc, ultimoId);
                        if (idFila == null) return;

                        idProcessado[0] = idFila;
                        DynamicVO fila = repository.buscar(idFila);
                        String documento = fila.asString("CNPJCPF");
                        BigDecimal codParc = repository.buscarCodParc(documento);
                        if (codParc == null) {
                            context.log("[Teleworld][FlowFornecedorScheduler] Fila " + idFila
                                    + " permanece iniciada: parceiro ainda nao localizado para "
                                    + DocumentoUtils.mascarar(documento) + '.');
                            return;
                        }

                        repository.sincronizarParceiro(fila, codParc);
                        context.log("[Teleworld][FlowFornecedorScheduler] Fila " + idFila
                                + " concluida. CODPARC=" + codParc + " persistido na fila e na TGFIXN.");
                    } finally {
                        JdbcWrapper.closeSession(jdbc);
                    }
                }
            });
        } finally {
            JapeSession.close(hnd);
        }
        return idProcessado[0];
    }

    private boolean processarUm(final FornecedorXmlConfig config,
            final ScheduledActionContext context) throws Exception {
        JapeSession.SessionHandle hnd = null;
        final boolean[] encontrou = { false };
        final boolean[] continuar = { true };

        try {
            hnd = JapeSession.open();
            hnd.execWithTX(new JapeSession.TXBlock() {
                @Override
                public void doWithTx() throws Exception {
                    EntityFacade facade = EntityFacadeFactory.getDWFFacade();
                    JdbcWrapper jdbc = null;
                    try {
                        jdbc = facade.getJdbcWrapper();
                        jdbc.openSession();
                        BigDecimal idFila = repository.bloquearProximo(jdbc, config.getMaxTentativas());
                        if (idFila == null) return;

                        encontrou[0] = true;
                        DynamicVO fila = repository.buscar(idFila);
                        String documento = fila.asString("CNPJCPF");

                        BigDecimal codParc = repository.buscarCodParc(documento);
                        if (codParc != null) {
                            repository.sincronizarParceiro(fila, codParc);
                            context.log("[Teleworld][FlowFornecedorScheduler] Fila " + idFila
                                    + " concluida sem novo Flow. CODPARC=" + codParc
                                    + " persistido na fila e na TGFIXN.");
                            return;
                        }

                        try {
                            BigDecimal idInstPrn = gateway.iniciar(fila, config);
                            repository.marcarSucesso(fila, idInstPrn);
                            context.log("[Teleworld][FlowFornecedorScheduler] Flow "
                                    + config.getCodPrn() + " iniciado. IDFILA=" + idFila
                                    + ", IDINSTPRN=" + idInstPrn + '.');
                        } catch (Exception erro) {
                            repository.marcarErro(fila, erro);
                            continuar[0] = false;
                            context.log("[Teleworld][FlowFornecedorScheduler] Falha na fila "
                                    + idFila + " (" + DocumentoUtils.mascarar(documento)
                                    + "): " + erro.getMessage());
                        }
                    } finally {
                        JdbcWrapper.closeSession(jdbc);
                    }
                }
            });
        } finally {
            JapeSession.close(hnd);
        }
        return encontrou[0] && continuar[0];
    }
}
