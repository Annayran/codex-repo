package br.zaya.telecontrol.services;

import br.zaya.telecontrol.models.OrdemServico;
import br.zaya.telecontrol.models.OrdemServicoList;
import br.zaya.telecontrol.parsers.ParseOrdemServico;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.infrastructure.http.Api;
import br.zaya.telecontrol.shared.utils.LogUtil;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import com.sankhya.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class BuscaOrdemServico {

    public static void getOrdemServico(String os) throws Exception {
        getOrdemServico(os, null);
    }

    public static void getOrdemServico(String os, BigDecimal nuNota) throws Exception {
        LogUtil.SYNC = "OrdemDeServico";

        ConfigTelecontrol serv = new ConfigTelecontrol();
        if (serv.epOrdemServico == null) {
            return;
        }
        String url = String.valueOf(serv.url) + serv.epOrdemServico.replaceFirst("\\{OS\\}", os);
        LogUtil.URL = url;
        String result = Api.get(url);

        OrdemServicoList ordensServicoList = ParseOrdemServico.getOrdemServicoResultado(result);

        if (ordensServicoList != null && ordensServicoList.getOrdensServico() != null) {
            List<OrdemServico> response = ordensServicoList.getOrdensServico();
            for (OrdemServico item : response) {
                try {
                    gravarOrdemServico(item, nuNota);
                } catch (Exception var11) {
                    LogUtil.saveLog(var11);
                }
            }
        }
    }

    public static void getGeralNaoIntegradas() throws Exception {
        LogUtil.SYNC = "OrdemDeServicoGeral";

        ConfigTelecontrol serv = new ConfigTelecontrol();
        if (serv.epOrdemServico == null) {
            return;
        }

        // Para busca geral, assumimos que se não houver um endpoint específico, podemos tentar uma busca por data ou status se a API suportar.
        // No entanto, conforme o plano, vamos buscar as OSs que "não viraram pedido".
        // Se a API da Telecontrol não tiver um endpoint específico de "OS não integradas", talvez tenhamos que usar o de OS com filtros.
        // Por enquanto, usaremos o epOrdemServico se ele permitir busca sem o parâmetro {OS} ou se houver um específico para lista.

        String url = String.valueOf(serv.url) + serv.epOrdemServico.replace("/{OS}", "");
        LogUtil.URL = url;
        String result = Api.get(url);

        OrdemServicoList ordensServicoList = ParseOrdemServico.getOrdemServicoResultado(result);

        if (ordensServicoList != null && ordensServicoList.getOrdensServico() != null) {
            List<OrdemServico> response = ordensServicoList.getOrdensServico();
            for (OrdemServico item : response) {
                try {
                    gravarOrdemServico(item, null);
                } catch (Exception var11) {
                    LogUtil.saveLog(var11);
                }
            }
        }
    }

    public static void gravarOrdemServico(OrdemServico ordemServico, BigDecimal nuNota) throws Exception {
        BigDecimal ordemServicoId = new BigDecimal(ordemServico.getOs());
        JapeWrapper ordemServicoDAO = JapeFactory.dao("AD_OSTLC");
        DynamicVO ordemServicoVO = ordemServicoDAO.findByPK(new Object[]{ordemServicoId});

        FluidUpdateVO updVO = null;
        FluidCreateVO creVO = null;

        if (ordemServicoVO != null) {
            updVO = ordemServicoDAO.prepareToUpdate(ordemServicoVO);
        } else {
            creVO = ordemServicoDAO.create();
            creVO.set("NROOS", ordemServicoId);
        }

        if (updVO != null || creVO != null) {
            if (updVO != null) {
                setCamposOS(updVO, ordemServico);
                updVO.update();
            } else {
                setCamposOS(creVO, ordemServico);
                creVO.save();
            }
        }

        // Atualizar CABECALHO_NOTA
        if (nuNota != null) {
            JapeWrapper cabDao = JapeFactory.dao(DynamicEntityNames.CABECALHO_NOTA);
            DynamicVO cabecalhoVo = cabDao.findByPK(nuNota);

            if (cabecalhoVo != null) {
                String tipoAtendimento = ordemServico.getDescricaoTipoAtendimento();
                FluidUpdateVO cabUpdVO = cabDao.prepareToUpdate(cabecalhoVo);
                cabUpdVO.set("AD_CLIENTEFINAL", ordemServico.getConsumidorNome());
                cabUpdVO.set("AD_DATAOS", StringUtils.toDate(ordemServico.getDataAbertura()));
                cabUpdVO.set("AD_TIPGARANTIA",
                        (tipoAtendimento != null && "Garantia".equalsIgnoreCase(tipoAtendimento)) ? "Sim" : "Não");
                cabUpdVO.update();
            }
        }
    }

    public static void syncOrdemServicoByNota(BigDecimal nuNota) throws Exception {
        LogUtil.SYNC = "SyncOrdemDeServico";
        JapeSession.SessionHandle hnd = null;
        try {
            hnd = JapeSession.open();
            JapeWrapper itemDao = JapeFactory.dao(DynamicEntityNames.ITEM_NOTA);
            // Busca itens da nota que possuem AD_NUMOS preenchido
            Collection<DynamicVO> itens = itemDao.find("this.NUNOTA = ? AND this.AD_NUMOS IS NOT NULL", nuNota);
            
            String ultimaOS = "";
            for (DynamicVO item : itens) {
                String os = item.asString("AD_NUMOS");
                if (os != null && !os.equals(ultimaOS)) {
                    getOrdemServico(os, nuNota);
                    ultimaOS = os;
                }
            }
        } finally {
            JapeSession.close(hnd);
        }
    }

    public static void setCamposOS(Object vo, OrdemServico os) throws Exception {
        // Usa reflexão ou polimorfismo de interface se o JAPE permitisse, mas como FluidUpdateVO e FluidCreateVO são diferentes, fazemos o mapeamento manual
        // Nota: Ambos implementam métodos set.
        if (vo instanceof FluidUpdateVO) {
            FluidUpdateVO itemVO = (FluidUpdateVO) vo;
            fillVO(itemVO, os);
        } else if (vo instanceof FluidCreateVO) {
            FluidCreateVO itemVO = (FluidCreateVO) vo;
            fillVO(itemVO, os);
        }
    }

    private static void fillVO(Object itemVO, OrdemServico os) throws Exception {
        // Helper para evitar repetição
        setField(itemVO, "CODIGOPOSTO", os.getCodigoPosto());
        setField(itemVO, "NOME", os.getNome());
        setField(itemVO, "SUAOS", os.getSuaOs());
        setField(itemVO, "DESCTIPOATENDIMENTO", os.getDescricaoTipoAtendimento());
        setField(itemVO, "STATUSOS", os.getStatusOs());
        setField(itemVO, "DATAABERTURA", os.getDataAbertura());
        setField(itemVO, "DATADIGITACAO", os.getDataDigitacao());
        setField(itemVO, "DATACONSERTO", os.getDataConserto());
        setField(itemVO, "DATAFECHAMENTO", os.getDataFechamento());
        setField(itemVO, "OSREINCIDENTE", os.getOsReincidente());
        setField(itemVO, "REFERENCIA", os.getReferencia());
        setField(itemVO, "DESCRICAO", os.getDescricao());
        setField(itemVO, "DEFEITORECLAMADO", os.getDefeitoReclamado());
        setField(itemVO, "DEFEITOCONSTATADO", os.getDefeitoConstatado());
        setField(itemVO, "SOLUCAO", os.getSolucao());
        setField(itemVO, "CONSUMIDORNOME", os.getConsumidorNome());
        setField(itemVO, "CONSUMIDORFONE", os.getConsumidorFone());
        setField(itemVO, "CONSUMIDORCPF", os.getConsumidorCpf());
        setField(itemVO, "REVENDA", os.getRevenda());
        setField(itemVO, "NOTAFISCAL", os.getNotaFiscal());
        setField(itemVO, "DATANF", os.getDataNf());
        setField(itemVO, "JSON", ParseOrdemServico.toPrettyJson(os).toCharArray());
        setField(itemVO, "CNPJ", os.getCnpj());
        setField(itemVO, "DEFEITORECLAMADODESCRICAO", os.getDefeitoReclamadoDescricao());
        setField(itemVO, "CONSUMIDORFONECOMERCIAL", os.getConsumidorFoneComercial());
        setField(itemVO, "CONSUMIDOREMAIL", os.getConsumidorEmail());
        setField(itemVO, "CONSUMIDORENDERECO", os.getConsumidorEndereco());
        setField(itemVO, "CONSUMIDORNUMERO", os.getConsumidorNumero());
        setField(itemVO, "CONSUMIDORBAIRRO", os.getConsumidorBairro());
        setField(itemVO, "CONSUMIDORCOMPLEMENTO", os.getConsumidorComplemento());
        setField(itemVO, "CONSUMIDORCEP", os.getConsumidorCep());
        setField(itemVO, "CONSUMIDORCIDADE", os.getConsumidorCidade());
        setField(itemVO, "CONSUMIDORESTADO", os.getConsumidorEstado());
        setField(itemVO, "SERIE", os.getSerie());
        setField(itemVO, "DATAFABRICACAO", os.getDataFabricacao());
        setField(itemVO, "LOCALREPARO", os.getLocalReparo());
        setField(itemVO, "DIASABERTO", BigDecimal.valueOf(os.getDiasAberto()));
        setField(itemVO, "HDCHAMADO", os.getHdChamado());
        setField(itemVO, "DTATENDIMENTOCALLCENTERRESOLVIDO", os.getDataAtendimentoCallcenterResolvido());
        setField(itemVO, "DATAABERTURAATENDIMENTO", os.getDataAtendimentoCallcenterResolvido());
    }

    private static void setField(Object vo, String field, Object value) throws Exception {
        if (vo instanceof FluidUpdateVO) {
            ((FluidUpdateVO) vo).set(field, value);
        } else if (vo instanceof FluidCreateVO) {
            ((FluidCreateVO) vo).set(field, value);
        }
    }
}
