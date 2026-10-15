package br.zaya.telecontrol.services;

import br.zaya.telecontrol.models.OsIntegracaoModel;
import br.zaya.telecontrol.models.PecaModel;
import br.zaya.telecontrol.shared.utils.LogUtil;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.jape.vo.DynamicVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OsIntegracaoDAO {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * Verifica se a OS já existe no banco (evita duplicidade)
     */
    public static boolean osExiste(String numeroOs) throws Exception {
        if (numeroOs == null || numeroOs.trim().isEmpty()) {
            return false;
        }

        try {
            JapeWrapper osDAO = JapeFactory.dao("AD_OSTLC");
            BigDecimal osId = new BigDecimal(numeroOs.trim());
            DynamicVO osVO = osDAO.findByPK(osId);
            return osVO != null;
        } catch (Exception e) {
            LogUtil.saveLog(e);
            return false;
        }
    }

    /**
     * Insere ou atualiza a Ordem de Serviço
     */
    public static void salvarOrdemServico(OsIntegracaoModel osIntegracao) throws Exception {
        if (osIntegracao == null) {
            throw new Exception("OsIntegracaoModel não pode ser nulo");
        }

        String numeroOs = obterNumeroOsPersistencia(osIntegracao);
        if (numeroOs == null || numeroOs.trim().isEmpty()) {
            throw new Exception("Número da OS não informado");
        }

        LogUtil.SYNC = "OsIntegracao";
        LogUtil.REGISTRO = numeroOs;

        try {
            JapeWrapper osDAO = JapeFactory.dao("AD_OSTLC");
            BigDecimal osId = new BigDecimal(numeroOs.trim());
            DynamicVO osVO = osDAO.findByPK(osId);

            if (osVO != null) {
                FluidUpdateVO updVO = osDAO.prepareToUpdate(osVO);
                preencherCamposOS(updVO, osIntegracao);
                updVO.update();
            } else {
                FluidCreateVO creVO = osDAO.create();
                creVO.set("NROOS", osId);
                preencherCamposOS(creVO, osIntegracao);
                creVO.save();
            }
        } catch (Exception e) {
            LogUtil.saveLog(e);
            throw new Exception("Erro ao salvar Ordem de Serviço: " + e.getMessage(), e);
        }
    }

    private static String obterNumeroOsPersistencia(OsIntegracaoModel osIntegracao) {
        String numeroOs = osIntegracao.getNumeroOs();
        String os = osIntegracao.getOs();

        if (isNumeric(numeroOs)) {
            return numeroOs.trim();
        }

        if (isNumeric(os)) {
            return os.trim();
        }

        return numeroOs != null ? numeroOs.trim() : null;
    }

    private static boolean isNumeric(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        try {
            new BigDecimal(valor.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Preenche os campos da OS
     */
    private static void preencherCamposOS(Object vo, OsIntegracaoModel osIntegracao) throws Exception {
        setTexto(vo, "CODIGOPOSTO", osIntegracao.getCodigoPosto());
        setTexto(vo, "NOME", osIntegracao.getNome());
        setTexto(vo, "SUAOS", osIntegracao.getSuaOs());
        setTexto(vo, "DESCTIPOATENDIMENTO", osIntegracao.getTipoAtendimento());
        setTexto(vo, "STATUSOS", osIntegracao.getStatusOs());
        setData(vo, "DATAABERTURA", osIntegracao.getDataAbertura());
        setData(vo, "DATADIGITACAO", osIntegracao.getDataDigitacao());
        setData(vo, "DATACONSERTO", osIntegracao.getDataConserto());
        setData(vo, "DATAFECHAMENTO", osIntegracao.getDataFechamento());
        setTexto(vo, "OSREINCIDENTE", defaultZero(osIntegracao.getOsReincidente()));
        setTexto(vo, "REFERENCIA", osIntegracao.getReferenciaProduto());
        setTexto(vo, "DESCRICAO", osIntegracao.getDescricaoProduto());
        setTexto(vo, "DEFEITORECLAMADO", osIntegracao.getDefeitoReclamado());
        setTexto(vo, "DEFEITOCONSTATADO", osIntegracao.getDefeitoConstatado());
        setTexto(vo, "SOLUCAO", osIntegracao.getSolucao());
        setTexto(vo, "CONSUMIDORNOME", osIntegracao.getConsumidor());
        setTexto(vo, "CONSUMIDORFONE", firstNonBlank(osIntegracao.getConsumidorCelular(), osIntegracao.getConsumidorTelefone()));
        setTexto(vo, "CONSUMIDORCPF", osIntegracao.getConsumidorCpf());
        setTexto(vo, "REVENDA", osIntegracao.getRevendaRazaoSocial());
        setTexto(vo, "NOTAFISCAL", defaultZero(osIntegracao.getNfCompra()));
        setData(vo, "DATANF", osIntegracao.getDataNf());
        setTexto(vo, "JSON", serializeOs(osIntegracao));
        setTexto(vo, "CNPJ", firstNonBlank(osIntegracao.getCnpj(), osIntegracao.getRevendaCnpj()));
        setTexto(vo, "DEFEITORECLAMADODESCRICAO", osIntegracao.getDefeitoReclamadoDescricao());
        setTexto(vo, "CONSUMIDORFONECOMERCIAL", osIntegracao.getConsumidorTelefone());
        setTexto(vo, "CONSUMIDOREMAIL", osIntegracao.getConsumidorEmail());
        setTexto(vo, "CONSUMIDORENDERECO", osIntegracao.getConsumidorEndereco());
        setTexto(vo, "CONSUMIDORNUMERO", osIntegracao.getConsumidorNumero());
        setTexto(vo, "CONSUMIDORBAIRRO", osIntegracao.getConsumidorBairro());
        setTexto(vo, "CONSUMIDORCOMPLEMENTO", osIntegracao.getConsumidorComplemento());
        setTexto(vo, "CONSUMIDORCEP", osIntegracao.getConsumidorCep());
        setTexto(vo, "CONSUMIDORCIDADE", osIntegracao.getConsumidorCidade());
        setTexto(vo, "CONSUMIDORESTADO", osIntegracao.getConsumidorUf());
        setTexto(vo, "SERIE", osIntegracao.getSerie());
        setData(vo, "DATAFABRICACAO", osIntegracao.getDataFabricacao());
        setTexto(vo, "LOCALREPARO", osIntegracao.getLocalReparo());
        setTexto(vo, "HDCHAMADO", osIntegracao.getHdChamado());
        setData(vo, "DTATENDIMENTOCALLCENTERRESOLVIDO", osIntegracao.getDtAtendimentoCallcenterResolvido());
        setData(vo, "DATAABERTURAATENDIMENTO", osIntegracao.getDataAberturaAtendimento());
        setNumero(vo, "DIASABERTO", osIntegracao.getDiasAberto());
        setTexto(vo, "OSINTERNA", osIntegracao.getOsInterna());
        setTexto(vo, "PROTOCOLO", osIntegracao.getProtocolo());
    }

    /**
     * Salva peças da OS
     */
    public static void salvarPecasOS(String numeroOs, OsIntegracaoModel osIntegracao) throws Exception {
        if (osIntegracao == null) {
            return;
        }

        LogUtil.SYNC = "OsIntegracao_Pecas";
        LogUtil.REGISTRO = numeroOs;

        List<PecaModel> pecas = osIntegracao.getPecas();
        System.out.println("[OS-INTEGRACAO][PECAS] Inicio salvarPecasOS | NROOS=" + numeroOs
                + " | pecas.size=" + (pecas == null ? 0 : pecas.size()));

        // Se não vier o array direto, tentamos extrair do JSON serializado do objeto
        if (pecas == null || pecas.isEmpty()) {
            try {
                JsonNode root = MAPPER.readTree(MAPPER.writeValueAsString(osIntegracao));
                JsonNode pecasNode = root.get("pecas");
                if (pecasNode != null) {
                    if (pecasNode.isArray()) {
                        pecas = MAPPER.convertValue(pecasNode, new TypeReference<List<PecaModel>>() {});
                        System.out.println("[OS-INTEGRACAO][PECAS] Fallback: pecas extraidas do node array do JSON | NROOS="
                                + numeroOs + " | pecas.size=" + pecas.size());
                    } else if (pecasNode.isTextual()) {
                        String text = pecasNode.asText();
                        if (text != null && !text.trim().isEmpty()) {
                            JsonNode parsed = MAPPER.readTree(text);
                            if (parsed != null && parsed.isArray()) {
                                pecas = MAPPER.convertValue(parsed, new TypeReference<List<PecaModel>>() {});
                                System.out.println("[OS-INTEGRACAO][PECAS] Fallback: pecas extraidas de string JSON interna | NROOS="
                                        + numeroOs + " | pecas.size=" + pecas.size());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("[OS-INTEGRACAO][PECAS][ERRO] Erro ao extrair pecas do JSON | NROOS="
                        + numeroOs + " | erro=" + e.getMessage());
                LogUtil.saveLog(e);
            }
        }

        if (pecas == null || pecas.isEmpty()) {
            System.out.println("[OS-INTEGRACAO][PECAS] Nenhuma peca encontrada para inserir na AD_OSPECA | NROOS=" + numeroOs);
            return;
        }

        try {
            JapeWrapper pecasDAO = JapeFactory.dao("AD_OSPECA");
            BigDecimal osId = new BigDecimal(numeroOs.trim());

            Collection<DynamicVO> pecasExistentes = pecasDAO.find("this.NROOS = ?", osId);
            Map<String, DynamicVO> existentesPorReferencia = new HashMap<String, DynamicVO>();
            for (DynamicVO pecaExistente : pecasExistentes) {
                String referencia = normalizarReferencia(pecaExistente.asString("REFERENCIA"));
                if (referencia != null) {
                    existentesPorReferencia.put(referencia, pecaExistente);
                }
            }

            Set<String> referenciasProcessadas = new HashSet<String>();

            for (int i = 0; i < pecas.size(); i++) {
                PecaModel peca = pecas.get(i);
                String referencia = normalizarReferencia(peca.getReferenciaPeca());
                if (referencia == null) {
                    referencia = normalizarReferencia(peca.getPoPeca());
                }
                if (referencia == null) {
                    referencia = "SEM_REFERENCIA_" + (i + 1);
                }

                referenciasProcessadas.add(referencia);
                DynamicVO existente = existentesPorReferencia.get(referencia);

                System.out.println("[OS-INTEGRACAO][PECAS] Processando peca | NROOS=" + osId
                        + " | REFERENCIA=" + referencia
                        + " | PO_PECA=" + peca.getPoPeca()
                        + " | DATADIGITACAO=" + peca.getDataDigitacaoPeca());

                try {
                    if (existente != null) {
                        FluidUpdateVO updVO = pecasDAO.prepareToUpdate(existente);
                        preencherCamposPeca(updVO, osId, referencia, peca);
                        updVO.update();
                        System.out.println("[OS-INTEGRACAO][PECAS] Peca atualizada com sucesso | NROOS=" + osId
                                + " | REFERENCIA=" + referencia);
                    } else {
                        FluidCreateVO creVO = pecasDAO.create();
                        creVO.set("NROOS", osId);
                        preencherCamposPeca(creVO, osId, referencia, peca);
                        creVO.save();
                        System.out.println("[OS-INTEGRACAO][PECAS] Peca inserida com sucesso | NROOS=" + osId
                                + " | REFERENCIA=" + referencia);
                    }
                } catch (Exception e) {
                    System.out.println("[OS-INTEGRACAO][PECAS][ERRO] Falha ao salvar peca na AD_OSPECA | NROOS=" + osId
                            + " | REFERENCIA=" + referencia
                            + " | PO_PECA=" + peca.getPoPeca()
                            + " | DATADIGITACAO=" + peca.getDataDigitacaoPeca()
                            + " | QTDEPENDENTE=" + peca.getQtdePendente()
                            + " | PEDIDO=" + peca.getPedido()
                            + " | VALORITEM=" + peca.getValorItem()
                            + " | erro=" + e.getMessage());
                    LogUtil.saveLog(e);
                }
            }

            for (Map.Entry<String, DynamicVO> entry : existentesPorReferencia.entrySet()) {
                if (!referenciasProcessadas.contains(entry.getKey())) {
                    pecasDAO.delete(osId, entry.getKey());
                }
            }
        } catch (Exception e) {
            System.out.println("[OS-INTEGRACAO][PECAS][ERRO] Erro geral ao salvar pecas na AD_OSPECA | NROOS="
                    + numeroOs + " | erro=" + e.getMessage());
            LogUtil.saveLog(e);
            // NÃ£o lanÃ§a exceÃ§Ã£o para nÃ£o interromper o processo
        }
    }

    private static void preencherCamposPeca(Object vo, BigDecimal osId, String referencia, PecaModel peca) throws Exception {
        setNumero(vo, "NROOS", osId.toString());
        setTexto(vo, "REFERENCIA", referencia);
        setDataNullable(vo, "DATADIGITACAO", peca.getDataDigitacaoPeca());
        setTextoNullable(vo, "PO_PECA", peca.getPoPeca());
        setNumeroNullable(vo, "QTDEPENDENTE", peca.getQtdePendente());
        setNumeroNullable(vo, "PEDIDO", peca.getPedido());
        setNumeroNullable(vo, "VALORITEM", peca.getValorItem());
        setDataNullable(vo, "DATAPEDIDO", peca.getDataPedido());
        setTextoNullable(vo, "NFFATURAMENTO", peca.getNfFaturamento());
        setDataNullable(vo, "DATAEMISSAONOTA", peca.getDataEmissaoNota());
        setDataNullable(vo, "DATAPOSTAGEM", peca.getDataPostagem());
        setTextoNullable(vo, "DEFEITOPECA", peca.getDefeitoPeca());
        setTextoNullable(vo, "RASTREAMENTO", peca.getRastreamento());
        setDataNullable(vo, "DATAENTREGA", peca.getDataEntrega());
    }

    private static void setTexto(Object vo, String field, String value) {
        // The 'JSON' column in AD_OSTLC expects a char[] (CLOB/CHAR_ARRAY) in the Sankhya API.
        // Convert the JSON string to a char[] when setting that specific field to avoid
        // a type mismatch ('java.lang.String' -> expected 'char[]').
        if (field != null && field.equalsIgnoreCase("JSON")) {
            setField(vo, field, value == null ? new char[0] : value.toCharArray());
        } else {
            setField(vo, field, value == null ? "" : value.trim());
        }
    }

    private static void setTextoNullable(Object vo, String field, String value) {
        setField(vo, field, isBlank(value) ? null : value.trim());
    }

    private static void setNumero(Object vo, String field, String value) {
        setField(vo, field, toBigDecimal(value));
    }

    private static void setNumeroNullable(Object vo, String field, String value) {
        setField(vo, field, isBlank(value) ? null : toBigDecimal(value));
    }

    private static void setData(Object vo, String field, String value) {
        setField(vo, field, normalizeDateText(value));
    }

    private static void setDataNullable(Object vo, String field, String value) {
        setField(vo, field, isBlank(value) ? null : normalizeDateText(value));
    }

    private static void setField(Object vo, String field, Object value) {
        if (vo instanceof FluidUpdateVO) {
            ((FluidUpdateVO) vo).set(field, value);
        } else if (vo instanceof FluidCreateVO) {
            ((FluidCreateVO) vo).set(field, value);
        }
    }

    private static String defaultZero(String value) {
        return value == null || value.trim().isEmpty() ? "0" : value.trim();
    }

    private static String firstNonBlank(String... values) {
        if (values == null) {
            return "";
        }
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }
        return "";
    }

    private static String normalizarReferencia(String valor) {
        if (valor == null) {
            return null;
        }
        String v = valor.trim();
        return v.isEmpty() ? null : v;
    }

    private static boolean isBlank(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private static BigDecimal toBigDecimal(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(valor.trim().replace(',', '.'));
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private static String normalizeDateText(String valor) {
        if (valor == null) {
            return "";
        }

        String texto = valor.trim();
        if (texto.isEmpty()) {
            return "";
        }

        return texto.replace('T', ' ');
    }

    private static String serializeOs(OsIntegracaoModel osIntegracao) {
        try {
            return MAPPER.writeValueAsString(osIntegracao);
        } catch (Exception e) {
            return "";
        }
    }

}
