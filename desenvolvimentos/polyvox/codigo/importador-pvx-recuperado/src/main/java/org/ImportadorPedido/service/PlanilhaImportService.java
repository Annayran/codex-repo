/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.com.sankhya.jape.EntityFacade
 *  br.com.sankhya.jape.dao.JdbcWrapper
 *  br.com.sankhya.jape.sql.NativeSql
 *  br.com.sankhya.jape.vo.DynamicVO
 *  br.com.sankhya.jape.wrapper.JapeFactory
 *  br.com.sankhya.jape.wrapper.JapeWrapper
 *  br.com.sankhya.jape.wrapper.fluid.FluidCreateVO
 *  br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO
 *  br.com.sankhya.modelcore.util.EntityFacadeFactory
 *  com.fasterxml.jackson.core.TreeNode
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 */
package org.ImportadorPedido.service;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import org.ImportadorPedido.helper.ImportadorLogger;
import org.ImportadorPedido.helper.PlanilhaJsonHelper;
import org.ImportadorPedido.models.DadosPlanilha;
import org.ImportadorPedido.repository.RegPedidos;

public class PlanilhaImportService {
    private static final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    private static final JdbcWrapper jdbc = dwFacade.getJdbcWrapper();
    private static final Logger LOGGER = Logger.getLogger(PlanilhaImportService.class.getName());

    public static JsonNode trimKeys(JsonNode node, ObjectMapper mapper) {
        return PlanilhaJsonHelper.limparCabecalhos(node, mapper);
    }

    public void processarPlanilhasPendentes() throws Exception {
        ResultSet rsProcessado = null;
        int totalJsonProcessados = 0;
        int totalLinhasImportadas = 0;
        long inicio = System.currentTimeMillis();
        try {
            NativeSql qryProcessado = new NativeSql(jdbc);
            qryProcessado.setReuseStatements(false);
            rsProcessado = qryProcessado.executeQuery("SELECT ID, JSON FROM AD_PRCSJSON WHERE NVL(PROCESSADO, 'N') = 'N' ORDER BY ID");
            while (rsProcessado.next()) {
                BigDecimal idJson = rsProcessado.getBigDecimal("ID");
                String jSon = rsProcessado.getString("JSON");
                if (jSon == null || jSon.trim().isEmpty()) {
                    ImportadorLogger.warn(LOGGER, "MAPA_JSON_VAZIO", "JSON vazio encontrado e ignorado", ImportadorLogger.ctx("id_json", idJson));
                    this.marcarJsonComoImportado(idJson);
                    continue;
                }
                long inicioLote = System.currentTimeMillis();
                int linhasLote = 0;
                try {
                    linhasLote = this.processarJson(jSon, idJson);
                    this.marcarJsonComoImportado(idJson);
                    ++totalJsonProcessados;
                    totalLinhasImportadas += linhasLote;
                    ImportadorLogger.info(LOGGER, "MAPA_JSON_IMPORTADO", "JSON importado para AD_IMPREGPED com status intermediario I", ImportadorLogger.ctx("id_json", idJson, "linhas_importadas", linhasLote, "status", "I", "duracao_ms", System.currentTimeMillis() - inicioLote));
                }
                catch (Exception eJson) {
                    ImportadorLogger.error(LOGGER, "MAPA_JSON_ERRO_PROCESSAMENTO", "Falha ao processar JSON, nao marcado como processado (reatentarao na proxima execucao)", eJson, ImportadorLogger.ctx("id_json", idJson));
                    throw eJson;
                }
            }
            ImportadorLogger.info(LOGGER, "MAPA_FINALIZADO", "Mapeamento de planilha concluido", ImportadorLogger.ctx("json_processados", totalJsonProcessados, "linhas_importadas", totalLinhasImportadas, "duracao_ms", System.currentTimeMillis() - inicio));
        }
        catch (Exception e) {
            ImportadorLogger.error(LOGGER, "MAPA_ERRO", "Falha ao mapear planilha", e, ImportadorLogger.ctx(new Object[0]));
            throw new Exception(e.getMessage(), e);
        }
        finally {
            if (rsProcessado != null) {
                rsProcessado.close();
            }
            jdbc.closeSession();
        }
    }

    private int processarJson(String jSon, BigDecimal idJson) throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(com.fasterxml.jackson.databind.DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        JsonNode root = objMapper.readTree(jSon);
        JsonNode cleanRoot = PlanilhaImportService.trimKeys(root, objMapper);
        DadosPlanilha[] dadosPedidoArray = (DadosPlanilha[])objMapper.treeToValue((TreeNode)cleanRoot, DadosPlanilha[].class);
        int linhasImportadas = 0;
        int linhasErro = 0;
        for (int i = 0; i < dadosPedidoArray.length; ++i) {
            DadosPlanilha dadosPedido = dadosPedidoArray[i];
            try {
                BigDecimal id;
                String primeiroImport;
                JapeWrapper pedidoDao = JapeFactory.dao((String)"AD_IMPPED");
                Collection dynamicVO = pedidoDao.find("this.PVX=?", new Object[]{dadosPedido.getPvx()});
                if (dynamicVO.isEmpty()) {
                    FluidCreateVO pedidoVo = pedidoDao.create();
                    pedidoVo.set("PVX", (Object)dadosPedido.getPvx());
                    DynamicVO salvo = pedidoVo.save();
                    primeiroImport = salvo.asString("PVX");
                    id = salvo.asBigDecimal("ID");
                } else {
                    DynamicVO existePedido = (DynamicVO)dynamicVO.iterator().next();
                    primeiroImport = existePedido.asString("PVX");
                    id = existePedido.asBigDecimal("ID");
                }
                RegPedidos.insereRegistos(dadosPedido, primeiroImport, id, idJson, i + 1);
                ++linhasImportadas;
                ImportadorLogger.info(LOGGER, "MAPA_LINHA_IMPORTADA", "Linha da planilha importada para AD_IMPREGPED", ImportadorLogger.ctx("id_json", idJson, "linha_numero", i + 1, "pvx", dadosPedido.getPvx(), "cod", dadosPedido.getCod(), "referencia", dadosPedido.getRef()));
                continue;
            }
            catch (Exception eLinha) {
                ++linhasErro;
                ImportadorLogger.warn(LOGGER, "MAPA_LINHA_IGNORADA", "Linha da planilha ignorada por erro de validacao ou persistencia", ImportadorLogger.ctx("id_json", idJson, "linha_numero", i + 1, "pvx", dadosPedido.getPvx(), "cod", dadosPedido.getCod(), "referencia", dadosPedido.getRef(), "mensagem_erro", eLinha.getMessage(), "causa_provavel", "ERRO_DADO_PLANILHA_OU_CADASTRO"));
                throw new Exception("Linha " + (i + 1) + ": " + eLinha.getMessage(), eLinha);
            }
        }
        ImportadorLogger.info(LOGGER, "MAPA_JSON_LIDO", "JSON interpretado e registros preparados", ImportadorLogger.ctx("id_json", idJson, "linhas_total", dadosPedidoArray.length, "linhas_importadas", linhasImportadas, "linhas_erro", linhasErro));
        return linhasImportadas;
    }

    private void marcarJsonComoImportado(BigDecimal idJson) throws Exception {
        JapeWrapper jsonDAO = JapeFactory.dao((String)"AD_PRCSJSON");
        Collection<DynamicVO> jsonVOs = jsonDAO.find("this.ID = ?", new Object[]{idJson});
        for (DynamicVO jsonVo : jsonVOs) {
            FluidUpdateVO fluidUpdAnxVO = jsonDAO.prepareToUpdate(jsonVo);
            fluidUpdAnxVO.set("PROCESSADO", (Object)"I");
            fluidUpdAnxVO.update();
            ImportadorLogger.info(LOGGER, "MAPA_JSON_MARCADO_IMPORTADO", "JSON marcado como importado (PROCESSADO=I)", ImportadorLogger.ctx("id_json", idJson));
        }
    }
}

