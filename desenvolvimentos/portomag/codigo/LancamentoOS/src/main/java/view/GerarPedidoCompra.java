package view;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import helper.SankhyaHelper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerarPedidoCompra implements AcaoRotinaJava {

    private static final Logger LOG = Logger.getLogger("br.com.zaya.portomag.os");

    static {
        LOG.info("[PORTOMAG-OS] CLASSE_CARREGADA classe=view.GerarPedidoCompra logs=habilitados");
    }

    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private final JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
    SankhyaHelper helper = new SankhyaHelper();

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        final String id = UUID.randomUUID().toString();
        final String prefix = "[PORTOMAG-OS][exec=" + id + "] ";
        final long inicio = System.nanoTime();
        String etapa = "LEITURA_SELECAO";
        String resultado = "SEM_PROCESSAMENTO";
        int processados = 0;
        StringBuilder notasGeradas = new StringBuilder();
        LOG.info(prefix + "INICIO classe=view.GerarPedidoCompra");
        try {
            Registro[] arrayRegistros = contexto.getLinhas();
            LOG.info(prefix + "SELECAO quantidade=" + (arrayRegistros == null ? "null" : arrayRegistros.length));
            if (arrayRegistros == null || arrayRegistros.length == 0) {
                LOG.warning(prefix + "SEM_LINHAS selecao ausente ou vazia; processamento encerrado");
                contexto.setMensagemRetorno("[PORTOMAG-OS] Nenhuma linha recebida. Nenhuma OS gerada. Diagnostico: " + id);
                return;
            }
            // Selecao e execucao permanecem no contexto transacional do botao.
            if (arrayRegistros != null && arrayRegistros.length > 0) {
                LOG.info(prefix + "SELECAO_APTA_PROCESSAMENTO linhas=" + arrayRegistros.length);
                etapa = "ITERACAO_SELECAO";
                for (Registro registro : arrayRegistros) {
                    etapa = "LEITURA_CHAVES";
                    LOG.info(prefix + "ETAPA=" + etapa);
                    BigDecimal pkNota = (BigDecimal) registro.getCampo("NUNOTA");
                    BigDecimal pkSeq = (BigDecimal) registro.getCampo("SEQUENCIA");
                    if (pkNota == null || pkSeq == null) {
                        throw new IllegalArgumentException("Selecao sem NUNOTA/SEQUENCIA. Diagnostico: " + id);
                    }
                    etapa = "BLOQUEIO_APONTAMENTO_E_NUOS";
                    LOG.info(prefix + "ETAPA=" + etapa);
                    // A transacao automatica do botao mantem o lock ate commit/rollback.
                    NativeSql bloqueio = new NativeSql(jdbc);
                    try {
                        bloqueio.appendSql("SELECT NUOS FROM AD_APONTAMENTO WHERE NUNOTA = :NUNOTA AND SEQUENCIA = :SEQUENCIA FOR UPDATE NOWAIT");
                        bloqueio.setNamedParameter("NUNOTA", pkNota);
                        bloqueio.setNamedParameter("SEQUENCIA", pkSeq);
                        try (ResultSet rs = bloqueio.executeQuery()) {
                            if (!rs.next()) {
                                throw new IllegalStateException("Apontamento selecionado nao encontrado. Diagnostico: " + id);
                            }
                            BigDecimal osExistente = rs.getBigDecimal("NUOS");
                            if (osExistente != null && osExistente.signum() != 0) {
                                throw new IllegalStateException("Apontamento ja possui NUOS. Confira a nota vinculada antes de gerar novamente. Diagnostico: " + id);
                            }
                        }
                    } finally {
                        NativeSql.releaseResources(bloqueio);
                    }

                    etapa = "PARAMETRO_CODTIPOPERMODCA";
                    LOG.info(prefix + "ETAPA=" + etapa);
                    String modTipOper = MGECoreParameter.getParameterAsString("CODTIPOPERMODCA");
                    LOG.info(prefix + "PARAMETRO presente=" + (modTipOper != null && !modTipOper.trim().isEmpty()));
                    if (modTipOper == null || modTipOper.trim().isEmpty()) {
                        throw new IllegalArgumentException("Parametro CODTIPOPERMODCA ausente ou vazio. Configure o codigo da TOP na TSIPAR. Diagnostico: " + id);
                    }
                    if (!modTipOper.trim().matches("[0-9]+")) {
                        throw new IllegalArgumentException("Parametro CODTIPOPERMODCA invalido: informe um codigo de TOP inteiro, sem sinal. Diagnostico: " + id);
                    }
                    BigDecimal parametroTop = new BigDecimal(modTipOper.trim());
                    LOG.info(prefix + "PARAMETRO_VALIDADO chave=CODTIPOPERMODCA");

                    etapa = "CONSULTA_TOP_MODELO";
                    LOG.info(prefix + "ETAPA=" + etapa);
                    NativeSql sql = new NativeSql(jdbc);
                    boolean topEncontrada;
                    BigDecimal codTop = null;
                    String modNota = null;
                    Timestamp dhTop = null;
                    String tipMov = null;
                    try {
                        sql.appendSql("SELECT T.CODTIPOPER, T.DHALTER, T.TIPMOV, T.AD_MODNOTA FROM TGFTOP T "
                                + "WHERE T.CODTIPOPER = :CODTIPOPER "
                                + "AND T.DHALTER = (SELECT MAX(V.DHALTER) FROM TGFTOP V WHERE V.CODTIPOPER = T.CODTIPOPER)");
                        sql.setNamedParameter("CODTIPOPER", parametroTop);
                        try (ResultSet rs = sql.executeQuery()) {
                            topEncontrada = rs.next();
                            if (topEncontrada) {
                                codTop = rs.getBigDecimal("CODTIPOPER");
                                modNota = rs.getString("AD_MODNOTA");
                                dhTop = rs.getTimestamp("DHALTER");
                                tipMov = rs.getString("TIPMOV");
                            }
                        }
                    } finally {
                        NativeSql.releaseResources(sql);
                    }

                    if (topEncontrada) {
                        LOG.info(prefix + "TOP_ENCONTRADA modeloInformado=" + (modNota != null && !modNota.trim().isEmpty()));
                        etapa = "VALIDACAO_TOP_MODELO";
                        if (!"O".equals(tipMov)) {
                            throw new IllegalStateException("A TOP configurada deve ser de Pedido de Compra (TIPMOV O). Diagnostico: " + id);
                        }
                        if (dhTop == null) {
                            throw new IllegalStateException("Versao da TOP nao informada. Diagnostico: " + id);
                        }
                        if (modNota == null || modNota.trim().isEmpty()) {
                            throw new IllegalStateException("AD_MODNOTA nao informado na versao mais recente da TOP. Diagnostico: " + id);
                        }
                        if (!modNota.trim().matches("[0-9]+")) {
                            throw new IllegalStateException("AD_MODNOTA invalido: informe o NUNOTA inteiro do modelo. Diagnostico: " + id);
                        }
                        BigDecimal nuModelo = new BigDecimal(modNota.trim());
                        LOG.info(prefix + "TOP_VERSAO_SELECIONADA criterio=MAX_DHALTER codTop=" + codTop + " dhTop=" + dhTop);

                        etapa = "BUSCA_NOTA_MODELO";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        DynamicVO cabVO;
                        try {
                            cabVO = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA).findByPK(nuModelo);
                        } catch (Exception e) {
                            throw new IllegalStateException("Falha ao consultar a nota-modelo indicada em AD_MODNOTA. Diagnostico: " + id, e);
                        }
                        if (cabVO == null) {
                            throw new IllegalStateException("Nota-modelo indicada em AD_MODNOTA nao encontrada. Diagnostico: " + id);
                        }
                        LOG.info(prefix + "NOTA_MODELO_ENCONTRADA");

                        etapa = "BUSCA_APONTAMENTO";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        DynamicVO apontamento = JapeFactory.dao("AD_APONTAMENTO").findByPK(pkNota, pkSeq);
                        if (apontamento == null) {
                            throw new IllegalStateException("Apontamento nao encontrado. Diagnostico: " + id);
                        }
                        // Verifica tambem o mapeamento Jape antes de qualquer nota ser criada.
                        apontamento.asBigDecimal("NUOS");
                        BigDecimal codEmp = apontamento.asBigDecimal("CODEMP");
                        if (codEmp == null || codEmp.signum() <= 0 || apontamento.asTimestamp("DTNEG") == null) {
                            throw new IllegalArgumentException("Preencha CODEMP e DTNEG no apontamento. Diagnostico: " + id);
                        }
                        etapa = "VALIDACAO_ITEM_SITE";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        DynamicVO siteVO = helper.validarItem(apontamento);
                        etapa = "BUSCA_TECNICO_POR_CODTECNICO";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        BigDecimal codTecnico = apontamento.asBigDecimal("CODTECNICO");
                        if (codTecnico == null || codTecnico.signum() <= 0) {
                            throw new IllegalArgumentException("Preencha CODTECNICO no apontamento. Diagnostico: " + id);
                        }
                        DynamicVO codParcTec = JapeFactory.dao("AD_TGFTECNICO").findByPK(codTecnico);
                        if (codParcTec == null) {
                            throw new IllegalStateException("Tecnico informado nao encontrado. Diagnostico: " + id);
                        }
                        BigDecimal parceiroTecnico = codParcTec.asBigDecimal("CODPARC");
                        boolean temVinculo = parceiroTecnico != null && parceiroTecnico.signum() > 0;
                        BigDecimal parceiroNota = temVinculo ? parceiroTecnico : apontamento.asBigDecimal("CODPARC");
                        if (parceiroNota == null || parceiroNota.signum() <= 0) {
                            throw new IllegalArgumentException("Informe parceiro no tecnico ou no apontamento. Diagnostico: " + id);
                        }

                        DynamicVO notaCompraOS = cabVO.buildClone();

                        notaCompraOS.setPrimaryKey(null);
                        notaCompraOS.clearReferences();
                        notaCompraOS.setProperty("NUNOTA", null);
                        notaCompraOS.setProperty("NUMNOTA", BigDecimal.ZERO);
                        SankhyaHelper.inicializarValoresCabecalho(notaCompraOS, apontamento);
                        // Nova solicitacao, nunca herdar a liberacao da nota-modelo.
                        notaCompraOS.setProperty("STATUSNOTA", "P");
                        notaCompraOS.setProperty("PENDENTE", "S");
                        notaCompraOS.setProperty("DTFATUR", null);
                        notaCompraOS.setProperty("CHAVENFE", null);

                        etapa = "MAPEAMENTO_CABECALHO_CODEMP";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        notaCompraOS.setProperty("CODEMP", codEmp);
                        notaCompraOS.setProperty("CODTIPOPER", codTop);
                        notaCompraOS.setProperty("DHTIPOPER", dhTop);
                        notaCompraOS.setProperty("TIPMOV", tipMov);
                        notaCompraOS.setProperty("DTNEG", apontamento.asTimestamp("DTNEG"));
                        notaCompraOS.setProperty("AD_DTABREOS", apontamento.asTimestamp("DTABRCRQ")); //criar campo
                        notaCompraOS.setProperty("AD_DTFECHAOS", apontamento.asTimestamp("DTPROG")); //criar campo
                        notaCompraOS.setProperty("AD_UF", apontamento.asString("UF"));
                        notaCompraOS.setProperty("AD_RELFOT", apontamento.asString("AD_RELATORIOFOTOGRAFICO2"));
                        //notaCompraOS.setProperty("AD_RELEX", "");
                        notaCompraOS.setProperty("OBSERVACAO", apontamento.asString("OBSSERVICO"));
                        notaCompraOS.setProperty("AD_NOMETEC", codParcTec.asString("NOMETECNICO")); // criar campo

                        notaCompraOS.setProperty("CODPARC", parceiroNota);
                        notaCompraOS.setProperty("AD_CODTECNICO", codTecnico);
                        notaCompraOS.setProperty("AD_VINCULO", temVinculo ? "S" : "N");

                        etapa = "PERSISTENCIA_CABECALHO";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        PersistentLocalEntity notaPersistida = dwFacade.createEntity(DynamicEntityNames.CABECALHO_NOTA, (EntityVO) notaCompraOS);
                        notaCompraOS = (DynamicVO) notaPersistida.getValueObject();
                        BigDecimal nuNota = notaCompraOS.asBigDecimal("NUNOTA");
                        if (nuNota == null) {
                            throw new IllegalStateException("Persistencia nao retornou NUNOTA. Diagnostico: " + id);
                        }

                        LOG.info(prefix + "CABECALHO_PERSISTIDO identificadorPresente=" + (nuNota != null)
                                + "; transacao ainda depende do contexto Sankhya");
                        etapa = "INSERCAO_ITEM_HELPER";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        helper.inseriItem(notaCompraOS, apontamento, siteVO);

                        etapa = "TOTALIZACAO_VALIDACAO_VALOR_ATIVIDADE";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        BigDecimal totalNota = helper.totalizarEValidar(nuNota, apontamento);
                        LOG.info(prefix + "TOTAL_VALIDADO nunota=" + nuNota + " total=" + totalNota);

                        etapa = "ATUALIZACAO_NUOS";
                        LOG.info(prefix + "ETAPA=" + etapa);
                        JapeWrapper apontamentoUpd = JapeFactory.dao("AD_APONTAMENTO");
                        FluidUpdateVO fluidUpdAnxVO = apontamentoUpd.prepareToUpdate(apontamento);
                        fluidUpdAnxVO.set("NUOS", nuNota);
                        fluidUpdAnxVO.update();
                        if (notasGeradas.length() > 0) notasGeradas.append(", ");
                        notasGeradas.append(nuNota.toPlainString());
                        LOG.info(prefix + "NOTA_E_ITEM_PROCESSADOS nunota=" + nuNota + "; commit depende do contexto");
                        processados++;
                        resultado = "FLUXO_CONCLUIDO_COMMIT_NAO_VERIFICADO";
                    } else {
                        LOG.warning(prefix + "TOP_NAO_ENCONTRADA nenhuma nota gerada para a linha");
                        throw new IllegalStateException("TOP configurada em CODTIPOPERMODCA nao encontrada. Diagnostico: " + id);
                    }
                }
                contexto.setMensagemRetorno("[PORTOMAG-OS] Notas processadas: " + notasGeradas
                        + ". Aguarde a conclusao da transacao e confira no Portal de Compras. Diagnostico: " + id);
            }
        } catch (Exception e) {
            resultado = "EXCECAO";
            LOG.log(Level.SEVERE, prefix + "ERRO etapa=" + etapa + "; excecao propagada ao contexto", e);
            throw new RuntimeException(e);
        } finally {
            LOG.info(prefix + "FIM resultado=" + resultado + " processados=" + processados
                    + " duracaoMs=" + ((System.nanoTime() - inicio) / 1000000L));
        }

    }
}
