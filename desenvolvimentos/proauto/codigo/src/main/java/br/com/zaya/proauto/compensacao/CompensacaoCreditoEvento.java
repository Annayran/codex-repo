package br.com.zaya.proauto.compensacao;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import br.com.sankhya.ws.ServiceContext;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

/** TGFCAB/CabecalhoNota. Usa exclusivamente a transacao e conexao do evento. */
public final class CompensacaoCreditoEvento implements EventoProgramavelJava {
    private static final Logger LOG = Logger.getLogger(CompensacaoCreditoEvento.class.getName());

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {
        DynamicVO atual = (DynamicVO) event.getVo();
        DynamicVO anterior = (DynamicVO) event.getOldVO();
        if (anterior == null || !ConfirmacaoNota.deveExecutar(atual.asString("TIPMOV"),
                anterior.asString("STATUSNOTA"), atual.asString("STATUSNOTA"))) { return; }
        BigDecimal nota = atual.asBigDecimal("NUNOTA");
        if (nota == null || nota.signum() <= 0) {
            throw new IllegalStateException("Compensacao exige uma nota persistida.");
        }
        ControlePopupCompensacao.validarNotaSessao(
                JapeSession.getProperty(ControlePopupCompensacao.NOTA_SESSAO), nota);
        TransactionContext tx = event.getTransactionContext();
        if (tx == null || event.getJdbcWrapper() == null) {
            throw new IllegalStateException("Compensacao exige a transacao da confirmacao.");
        }
        String chave = CompensacaoCreditoEvento.class.getName() + "." + nota.toPlainString();
        if (tx.getGlobalTxProperty(chave) != null) { return; }
        tx.putGlobalTxProperty(chave, Boolean.TRUE);
        String execucao = UUID.randomUUID().toString();
        try {
            BigDecimal parceiro = atual.asBigDecimal("CODPARC");
            BigDecimal empresa = atual.asBigDecimal("CODEMP");
            if (!CompensacaoRepository.parceiroHabilitado(event.getJdbcWrapper(), parceiro)) {
                log(execucao, nota, "IGNORADO flag desabilitada");
                return;
            }
            // Validar antes de qualquer acerto. A propriedade nativa vale para a sessao inteira.
            if (!Boolean.TRUE.equals(JapeSessionContext.getProperty(
                    "br.com.sankhya.com.CentralCompraVenda", Boolean.FALSE))) {
                throw new IllegalStateException("PRACC: fluxo sem contexto da Central de Vendas nao homologado.");
            }
            ControlePopupCompensacao.validarPedido(ServiceContext.getCurrent().getRequestBody(), nota);
            if (MGECoreParameter.getParameterAsBoolean("mge.com.compensacao.credito.automatica")) {
                throw new IllegalStateException("PRACC: COMPENSACREDCLI deve estar desligado para evitar "
                        + "compensacao nativa adicional e preservar o controle por parceiro.");
            }
            ConfiguracaoCompensacao config = ConfiguracaoCompensacao.carregar();
            CompensacaoRepository repo = new CompensacaoRepository(event.getJdbcWrapper(), config, nota);
            AuthenticationInfo auth = AuthenticationInfo.getCurrentOrNull();
            if (auth == null || auth.getUserID() == null) {
                throw new IllegalStateException("Usuario autenticado indisponivel na confirmacao.");
            }
            Timestamp hoje = repo.hoje();
            CompensacaoService service = new CompensacaoService(new SankhyaCompensacaoGateway(
                    auth.getUserID(), config.topReceita, config.topDespesa, hoje));
            Set<String> pares = new HashSet<String>();
            int quantidade = 0;
            log(execucao, nota, "INICIO beforeUpdate dias=" + config.dias);
            while (true) {
                CompensacaoRepository.Titulo credito = repo.primeiro(parceiro, empresa, true, hoje);
                if (credito == null) { break; }
                CompensacaoRepository.Titulo receita = repo.primeiro(parceiro, empresa, false, hoje);
                if (receita == null) {
                    if (quantidade == 0) {
                        throw new IllegalStateException("Credito elegivel encontrado, mas nao ha receita elegivel "
                                + "da nota no beforeUpdate. Verifique a geracao do financeiro e os filtros de homologacao.");
                    }
                    break;
                }
                repo.bloquear(credito.id.min(receita.id));
                repo.bloquear(credito.id.max(receita.id));
                credito = repo.revalidar(credito, parceiro, empresa, true, hoje);
                receita = repo.revalidar(receita, parceiro, empresa, false, hoje);
                String par = credito.id + ":" + credito.valor + ":" + receita.id + ":" + receita.valor;
                if (!pares.add(par)) {
                    throw new IllegalStateException("Helper retornou sem consumir o par financeiro; confirmacao interrompida.");
                }
                BigDecimal acerto = service.executar(credito.id, receita.id, credito.valor, receita.valor);
                quantidade++;
                log(execucao, nota, "PROCESSADO_NA_TRANSACAO acerto=" + acerto
                        + " credito=" + credito.id + " receita=" + receita.id
                        + " valor=" + RegraCompensacao.valor(credito.valor, receita.valor));
            }
            // String, nao Boolean: o helper nativo faz cast para String.
            // Manter ate a validacao nativa posterior; a sessao e encerrada pelo chamador.
            // false dispensa o fluxo nativo; os acertos elegiveis ja foram feitos acima.
            JapeSession.putProperty(ControlePopupCompensacao.NOTA_SESSAO, nota);
            JapeSession.putProperty(ControlePopupCompensacao.PROPRIEDADE, "false");
            log(execucao, nota, "POPUP_NATIVO_DISPENSADO escopo=confirmacao_individual");
            log(execucao, nota, "FIM pares=" + quantidade + " aguardando commit da confirmacao");
        } catch (Exception falha) {
            log(execucao, nota, "FALHA classe=" + falha.getClass().getSimpleName()
                    + "; excecao propagada para cancelar a confirmacao");
            throw falha;
        }
    }

    private static void log(String execucao, BigDecimal nota, String texto) {
        LOG.info("[PRACC " + execucao + "] nota=" + nota + " " + texto);
    }

    @Override public void beforeInsert(PersistenceEvent event) throws Exception { }
    @Override public void beforeDelete(PersistenceEvent event) throws Exception { }
    @Override public void afterInsert(PersistenceEvent event) throws Exception { }
    @Override public void afterUpdate(PersistenceEvent event) throws Exception { }
    @Override public void afterDelete(PersistenceEvent event) throws Exception { }
    @Override public void beforeCommit(TransactionContext context) throws Exception { }
}
