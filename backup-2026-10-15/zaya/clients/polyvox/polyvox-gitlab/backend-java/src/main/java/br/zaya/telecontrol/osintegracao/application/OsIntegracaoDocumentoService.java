package br.zaya.telecontrol.osintegracao.application;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.comercial.util.TipoOperacaoUtils;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.MGECoreParameter;
import br.com.sankhya.modelcore.util.ParameterUtils;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.zaya.telecontrol.models.OsIntegracaoModel;
import br.zaya.telecontrol.shared.config.ConfigTelecontrol;
import br.zaya.telecontrol.shared.utils.LogUtil;
import br.zaya.telecontrol.shared.utils.PedidoNotaHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;

/**
 * Regra de pós-processamento da integração de OS.
 *
 * - Se a OS tiver registro em AD_OSPECA, gera nota de compra agrupada por parceiro.
 * - Se a OS não tiver registro em AD_OSPECA, gera financeiro direto com PROVISAO = 'N'.
 */
public final class OsIntegracaoDocumentoService {

    private static final String LOG_PREFIX = "[OS-INTEGRACAO][DOC] ";
    private static final String DAO_OS_PECAS = "AD_OSPECA";
    private static final String DAO_FINANCEIRO = "Financeiro";
    private static final String DAO_CABECALHO_NOTA = "CabecalhoNota";
    private static final String DAO_ITEM_NOTA = "ItemNota";
    private static final String DAO_OS_INTEGRACAO = "AD_OSTLC";
    private static final String STATUS_OS_FINALIZADA = "Finalizada";
    private static final String PARAM_NUFIN_CLONE = "AD_NUFINCLONETC";
    private static final String PARAM_CRIAR_NOTA_COMPRA = "TLC_GERA_NOTA_COMPRA_OS";

    private OsIntegracaoDocumentoService() {
    }

    public static void processarDocumentos(List<OsIntegracaoModel> osIntegracoes) {
        processarDocumentosComResultado(osIntegracoes, null);
    }

    public static ResultadoDocumento processarDocumentosComResultado(List<OsIntegracaoModel> osIntegracoes,
                                                                     Boolean criarNotaCompraOverride) {
        ResultadoDocumento resultado = new ResultadoDocumento();
        if (osIntegracoes == null || osIntegracoes.isEmpty()) {
            resultado.aviso = "Nenhuma OS recebida para processar documentos";
            return resultado;
        }

        boolean criarNotaCompra = deveCriarNotaCompra(criarNotaCompraOverride);
        resultado.criacaoNotaCompraHabilitada = criarNotaCompra;

        try {
            Map<String, GrupoParceiroOs> grupos = agruparPorParceiro(osIntegracoes, resultado);
            resultado.totalOsRecebidas = osIntegracoes.size();
            resultado.totalGrupos = grupos.size();
            System.out.println(LOG_PREFIX + "Agrupamento concluido | totalOs=" + osIntegracoes.size()
                    + " | totalGrupos=" + grupos.size()
                    + " | criarNotaCompra=" + criarNotaCompra);
            for (GrupoParceiroOs grupo : grupos.values()) {
                processarGrupo(grupo, criarNotaCompra, resultado);
            }
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "Falha ao processar documentos da integração: " + e.getMessage());
            resultado.erro = e.getMessage();
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
        }

        return resultado;
    }

    public static ResultadoDocumento processarOsPersistidas(Boolean criarNotaCompraOverride) {
        try {
            Collection<DynamicVO> osPersistidas = JapeFactory.dao(DAO_OS_INTEGRACAO)
                    .find("this.CNPJ IS NOT NULL AND this.STATUSOS = ? AND (this.OSPAGA IS NULL OR this.OSPAGA <> ?)",
                            STATUS_OS_FINALIZADA, "S");

            List<OsIntegracaoModel> lista = new ArrayList<OsIntegracaoModel>();
            for (DynamicVO osVO : osPersistidas) {
                if (osVO == null) {
                    continue;
                }
                BigDecimal nroOs = osVO.asBigDecimal("NROOS");
                if (nroOs == null) {
                    continue;
                }

                OsIntegracaoModel model = new OsIntegracaoModel();
                model.setNumeroOs(nroOs.toPlainString());
                model.setOs(nroOs.toPlainString());
                model.setCnpj(osVO.asString("CNPJ"));
                lista.add(model);
            }

            System.out.println(LOG_PREFIX + "OS finalizadas carregadas da AD_OSTLC para processamento documental | total=" + lista.size());
            return processarDocumentosComResultado(lista, criarNotaCompraOverride);
        } catch (Exception e) {
            ResultadoDocumento erro = new ResultadoDocumento();
            erro.erro = e.getMessage();
            System.out.println(LOG_PREFIX + "Erro ao carregar OS persistidas: " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
            return erro;
        }
    }

    private static Map<String, GrupoParceiroOs> agruparPorParceiro(List<OsIntegracaoModel> osIntegracoes,
                                                                    ResultadoDocumento resultado) throws Exception {
        Map<String, GrupoParceiroOs> grupos = new LinkedHashMap<>();

        for (OsIntegracaoModel osIntegracao : osIntegracoes) {
            if (osIntegracao == null) {
                continue;
            }

            String numeroOs = resolverNumeroOs(osIntegracao);
            if (numeroOs == null || numeroOs.trim().isEmpty() || !isNumeric(numeroOs)) {
                continue;
            }

            if (isOsPaga(numeroOs)) {
                if (resultado != null) {
                    resultado.osIgnoradasPorOsPaga++;
                }
                System.out.println(LOG_PREFIX + "OS já processada (OSPAGA=S), ignorando | nroOs=" + numeroOs);
                continue;
            }

            if (!isOsFinalizada(numeroOs)) {
                if (resultado != null) {
                    resultado.osIgnoradasPorStatus++;
                }
                System.out.println(LOG_PREFIX + "OS ignorada por STATUSOS diferente de Finalizada | nroOs=" + numeroOs);
                continue;
            }

            // Agrupar exclusivamente pelo CNPJ do posto (campo AD_OSTLC.CNPJ)
            String cnpj = normalizarCnpj(osIntegracao.getCnpj());
            if (cnpj == null) {
                System.out.println(LOG_PREFIX + "OS sem CNPJ do posto será ignorada | numeroOs=" + numeroOs);
                continue;
            }

            GrupoParceiroOs grupo = grupos.get(cnpj);
            if (grupo == null) {
                grupo = new GrupoParceiroOs(cnpj, buscarDescricaoParceiroPorCnpj(cnpj));
                grupos.put(cnpj, grupo);
            }

            boolean possuiPecas = possuiPecas(numeroOs);
            grupo.adicionarOs(osIntegracao, possuiPecas);
        }

        return grupos;
    }

    private static void processarGrupo(GrupoParceiroOs grupo, boolean criarNotaCompra, ResultadoDocumento resultado) {
        if (grupo == null || grupo.quantidadeTotal() == 0) {
            return;
        }

        try {
            marcarOsComoPendentes(grupo.getTodasOs());

            BigDecimal valorFixoServico = buscarValorFixoServicoPorCnpj(grupo.getCnpj());
            if (valorFixoServico == null) {
                System.out.println(LOG_PREFIX + "Parceiro sem AD_VLRFIXSER configurado | cnpj=" + grupo.getCnpj());
                return;
            }

            System.out.println(LOG_PREFIX + "Processando grupo por CNPJ | cnpj=" + grupo.getCnpj()
                    + " | parceiro=" + grupo.getDescricaoParceiro()
                    + " | comPecas=" + grupo.getQuantidadeComPecas()
                    + " | semPecas=" + grupo.getQuantidadeSemPecas()
                    + " | valorFixoServico=" + valorFixoServico);

            if (grupo.getQuantidadeComPecas() > 0) {
                BigDecimal total = valorFixoServico.multiply(BigDecimal.valueOf(grupo.getQuantidadeComPecas()))
                        .setScale(2, RoundingMode.HALF_UP);
                if (criarNotaCompra) {
                    System.out.println(LOG_PREFIX + "Iniciando criacao de nota por modelo | cnpj=" + grupo.getCnpj()
                            + " | qtdOsComPecas=" + grupo.getQuantidadeComPecas() + " | total=" + total);
                    BigDecimal nuNota = criarNotaCompra(grupo, grupo.getQuantidadeComPecas(), valorFixoServico, total);
                    if (nuNota == null || nuNota.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new Exception("NuNota nao retornado apos criacao da nota por modelo | cnpj=" + grupo.getCnpj());
                    }
                    marcarOsComoPagas(grupo.getOsComPecas(), nuNota, null);
                    resultado.notasGeradas++;
                    System.out.println(LOG_PREFIX + "Nota gerada e OS marcadas como pagas | cnpj=" + grupo.getCnpj()
                            + " | nuNota=" + nuNota + " | os=" + grupo.resumoOsComPecas());
                } else {
                    resultado.notasIgnoradasPorParametro++;
                    System.out.println(LOG_PREFIX + "Criação de nota de compra desabilitada por parâmetro do sistema | cnpj=" + grupo.getCnpj()
                            + " | qtdOsComPecas=" + grupo.getQuantidadeComPecas());
                }
            }

            if (grupo.getQuantidadeSemPecas() > 0) {
                BigDecimal total = valorFixoServico.multiply(BigDecimal.valueOf(grupo.getQuantidadeSemPecas()))
                        .setScale(2, RoundingMode.HALF_UP);
                System.out.println(LOG_PREFIX + "Financeiro direto | cnpj=" + grupo.getCnpj() + " | qtd=" + grupo.getQuantidadeSemPecas() + " | total=" + total);
                BigDecimal nuFin = criarFinanceiroDireto(grupo, grupo.getQuantidadeSemPecas(), valorFixoServico, total);
                if (nuFin == null || nuFin.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new Exception("NUFIN invalido apos criarFinanceiroDireto | cnpj=" + grupo.getCnpj());
                }
                marcarOsComoPagas(grupo.getOsSemPecas(), null, nuFin);
                resultado.financeirosGerados++;
                System.out.println(LOG_PREFIX + "OK Financeiro | nuFin=" + nuFin + " | os=" + grupo.resumoOsSemPecas());
            }

        } catch (Exception e) {
            resultado.gruposComErro++;
            System.out.println(LOG_PREFIX + "Erro ao processar grupo do parceiro (cnpj) " + grupo.getCnpj() + ": " + e.getMessage());
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
            throw new RuntimeException("Erro ao processar grupo do parceiro " + grupo.getCnpj(), e);
        }
    }

    private static boolean deveCriarNotaCompra(Boolean criarNotaCompraOverride) {
        if (criarNotaCompraOverride != null) {
            return criarNotaCompraOverride.booleanValue();
        }

        try {
            Boolean porParametroSistema = parseBoolean(ParameterUtils.getParameter(PARAM_CRIAR_NOTA_COMPRA));
            if (porParametroSistema != null) {
                return porParametroSistema.booleanValue();
            }
        } catch (Exception ignore) {
        }

        // Compatibilidade: se o parâmetro global não existir, tenta configuração em AD_TCLCFG.
        try {
            BigDecimal idCfg = (BigDecimal) ParameterUtils.getParameter("IDCONFIGTLC");
            if (idCfg != null) {
                DynamicVO cfgVO = JapeFactory.dao("AD_TCLCFG").findByPK(idCfg);
                if (cfgVO != null) {
                    Boolean valorCfg = parseBoolean(cfgVO.asString("GERANOTACOMPRAOS"));
                    if (valorCfg == null) {
                        valorCfg = parseBoolean(cfgVO.asString("GERANOTACOMPRA"));
                    }
                    if (valorCfg != null) {
                        return valorCfg.booleanValue();
                    }
                }
            }
        } catch (Exception ignore) {
        }

        // Comportamento legado: sem parâmetro explícito, mantém criação habilitada.
        return true;
    }

    private static Boolean parseBoolean(Object valor) {
        if (valor == null) {
            return null;
        }
        if (valor instanceof Boolean) {
            return (Boolean) valor;
        }
        if (valor instanceof BigDecimal) {
            return ((BigDecimal) valor).compareTo(BigDecimal.ZERO) != 0;
        }

        String texto = valor.toString();
        if (texto == null) {
            return null;
        }
        texto = texto.trim();
        if (texto.isEmpty()) {
            return null;
        }
        if ("S".equalsIgnoreCase(texto) || "TRUE".equalsIgnoreCase(texto) || "1".equals(texto)) {
            return Boolean.TRUE;
        }
        if ("N".equalsIgnoreCase(texto) || "FALSE".equalsIgnoreCase(texto) || "0".equals(texto)) {
            return Boolean.FALSE;
        }
        return null;
    }

    private static BigDecimal criarNotaCompra(GrupoParceiroOs grupo, int quantidade, BigDecimal valorUnitario, BigDecimal total) throws Exception {
        BigDecimal codProdutoServico = obterParametroBigDecimal(
                "CODPRODSERVICOOS",
                "CODPRODSERVICO",
                "CODPROD_SERVICO_OS",
                "CODPRODSERV"
        );

        if (codProdutoServico == null) {
            System.out.println(LOG_PREFIX + "Configuração do produto de serviço para nota de compra não encontrada. Grupo será ignorado | cnpj=" + grupo.getCnpj());
            return null;
        }

        EntityFacade entityFacade = EntityFacadeFactory.getDWFFacade();
        DynamicVO notaVO = obterNotaVOModeloPecas(entityFacade);
        System.out.println(LOG_PREFIX + "Modelo de nota carregado para pecas | cnpj=" + grupo.getCnpj()
                + " | codTipOperModelo=" + (notaVO != null ? notaVO.asBigDecimal("CODTIPOPER") : null));
        DynamicVO topVO = notaVO != null && notaVO.containsProperty("CODTIPOPER") && notaVO.asBigDecimal("CODTIPOPER") != null
                ? TipoOperacaoUtils.getTopVO(notaVO.asBigDecimal("CODTIPOPER"))
                : null;

        if (notaVO == null) {
            System.out.println(LOG_PREFIX + "NotaVO nulo apos obterNotaVOModeloPecas | cnpj=" + grupo.getCnpj());
            return null;
        }

        String resumoOs = grupo.resumoOsComPecas();
        // Resolve partner code (CODPARC) from CNPJ using exact CGC_CPF match
        BigDecimal codParc = buscarCodParcPorCnpjExato(grupo.getCnpj());
        if (codParc == null) {
            System.out.println(LOG_PREFIX + "Parceiro não encontrado para CNPJ na criação da nota | cnpj=" + grupo.getCnpj());
            return null;
        }
        preencherCabecalhoNota(notaVO, codParc, topVO, total, resumoOs);
        System.out.println(LOG_PREFIX + "Cabecalho da nota preenchido | cnpj=" + grupo.getCnpj()
                + " | codParc=" + codParc + " | total=" + total + " | resumoOs=" + resumoOs);

        Collection<DynamicVO> itens = new ArrayList<>();
        DynamicVO itemVO = (DynamicVO) entityFacade.getDefaultValueObjectInstance(DAO_ITEM_NOTA);
        preencherItemNota(itemVO, codProdutoServico, quantidade, valorUnitario, total);
        itens.add(itemVO);
        System.out.println(LOG_PREFIX + "Item da nota preparado | codProd=" + codProdutoServico
                + " | qtd=" + quantidade + " | vlrUnit=" + valorUnitario + " | vlrTot=" + total);

        BigDecimal nuNota = new PedidoNotaHelper().incluirNota(notaVO, itens);
        if (nuNota == null || nuNota.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("PedidoNotaHelper.incluirNota retornou nuNota invalido | cnpj=" + grupo.getCnpj()
                    + " | qtd=" + quantidade + " | total=" + total + " | OS=" + resumoOs);
        }
        System.out.println(LOG_PREFIX + "Nota de compra criada com sucesso | cnpj=" + grupo.getCnpj()
                + " | nuNota=" + nuNota + " | qtdOs=" + quantidade + " | total=" + total + " | OS=" + resumoOs);
        return nuNota;
    }

    private static DynamicVO obterNotaVOModeloPecas(EntityFacade entityFacade) throws Exception {
        String modeloStr = MGECoreParameter.getParameterAsString("OSPAGMOD");
        if (modeloStr != null && !modeloStr.trim().isEmpty()) {
            try {
                BigDecimal modelo = new BigDecimal(modeloStr.trim());
                DynamicVO modeloVO = JapeFactory.dao(DAO_CABECALHO_NOTA).findByPK(new Object[]{modelo});
                if (modeloVO != null) {
                    System.out.println(LOG_PREFIX + "Modelo OSPAGMOD encontrado | nuNotaModelo=" + modelo);
                    DynamicVO clone = modeloVO.buildClone();
                    clone.setPrimaryKey(null);
                    clone.clearReferences();
                    if (clone.containsProperty("NUNOTA")) {
                        clone.setProperty("NUNOTA", null);
                    }
                    return clone;
                }
                System.out.println(LOG_PREFIX + "Modelo OSPAGMOD nao encontrado no CabecalhoNota | nuNotaModelo=" + modelo);
            } catch (Exception e) {
                System.out.println(LOG_PREFIX + "Falha ao carregar modelo OSPAGMOD, usando instancia padrao | valor=" + modeloStr + " | erro=" + e.getMessage());
            }
        }

        System.out.println(LOG_PREFIX + "Usando instancia padrao de CabecalhoNota (sem modelo OSPAGMOD valido)");
        return (DynamicVO) entityFacade.getDefaultValueObjectInstance(DAO_CABECALHO_NOTA);
    }

    private static BigDecimal criarFinanceiroDireto(GrupoParceiroOs grupo, int quantidade, BigDecimal valorUnitario, BigDecimal total) throws Exception {
        String resumoOs = grupo.resumoOsSemPecas();
        System.out.println(LOG_PREFIX + "criarFinanceiroDireto | cnpj=" + grupo.getCnpj() + " | qtd=" + quantidade + " | total=" + total);

        BigDecimal codParc = buscarCodParcPorCnpjExato(grupo.getCnpj());
        if (codParc == null) {
            System.out.println(LOG_PREFIX + "Parceiro nao encontrado para CNPJ na criacao do financeiro | cnpj=" + grupo.getCnpj());
            return null;
        }

        DynamicVO osBase = buscarOsBaseGrupo(grupo.getOsSemPecas());
        if (osBase == null) {
            throw new Exception("Nao foi encontrada OS base na AD_OSTLC para criar financeiro | cnpj=" + grupo.getCnpj());
        }

        DynamicVO financeiroCriado = criarFinanceiroPorOsBase(osBase, grupo, codParc, quantidade, valorUnitario, total, resumoOs);
        if (financeiroCriado == null) {
            return null;
        }

        BigDecimal nuFin = financeiroCriado.asBigDecimal("NUFIN");
        if (nuFin == null || nuFin.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("NUFIN invalido apos createEntity | cnpj=" + grupo.getCnpj() + " | nuFin=" + nuFin);
        }

        System.out.println(LOG_PREFIX + "Financeiro direto criado com sucesso | cnpj=" + grupo.getCnpj()
                + " | nuFin=" + nuFin + " | qtdOs=" + quantidade + " | total=" + total + " | OS=" + resumoOs);
        return nuFin;
    }

    private static DynamicVO buscarOsBaseGrupo(List<String> osNumeros) throws Exception {
        if (osNumeros == null || osNumeros.isEmpty()) {
            return null;
        }

        JapeWrapper osDAO = JapeFactory.dao(DAO_OS_INTEGRACAO);
        for (String nroOs : osNumeros) {
            if (!isNumeric(nroOs)) {
                continue;
            }
            DynamicVO osVO = osDAO.findByPK(new Object[]{new BigDecimal(nroOs.trim())});
            if (osVO != null) {
                System.out.println(LOG_PREFIX + "OS base encontrada para criacao do financeiro | nroOs=" + nroOs);
                return osVO;
            }
        }

        return null;
    }

    private static DynamicVO criarFinanceiroPorOsBase(DynamicVO osBase,
                                                      GrupoParceiroOs grupo,
                                                      BigDecimal codParc,
                                                      int quantidade,
                                                      BigDecimal valorUnitario,
                                                      BigDecimal total,
                                                      String resumoOs) throws Exception {
        EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
        DynamicVO financeiroReferencia = buscarFinanceiroReferenciaSeguro();
        if (financeiroReferencia == null) {
            throw new Exception("Financeiro de referencia nao encontrado para clonagem do financeiro de OS. "
                    + "Configure o parametro " + PARAM_NUFIN_CLONE + ".");
        }

        DynamicVO finVO = financeiroReferencia.buildClone();
        finVO.setPrimaryKey(null);
        finVO.clearReferences();

        BigDecimal codTipOper = obterBigDecimalSePresente(financeiroReferencia, "CODTIPOPER");
        BigDecimal codNat = obterBigDecimalSePresente(financeiroReferencia, "CODNAT");
        BigDecimal codBco = obterBigDecimalSePresente(financeiroReferencia, "CODBCO");
        BigDecimal codCta = obterBigDecimalSePresente(financeiroReferencia, "CODCTABCOINT");
        BigDecimal codCencus = obterBigDecimalSePresente(financeiroReferencia, "CODCENCUS");
        BigDecimal codVend = obterBigDecimalSePresente(financeiroReferencia, "CODVEND");
        BigDecimal codTipTit = obterBigDecimalSePresente(financeiroReferencia, "CODTIPTIT");
        String origem = obterTextoSePresente(financeiroReferencia, "ORIGEM");

        validarCentroResultadoObrigatorio(codCencus);

        Date dataBase = new Date();
        BigDecimal numNota = parseBigDecimalSeguro(osBase.asString("NOTAFISCAL"));

        setIfPresent(finVO, "NUFIN", null);
        setIfPresent(finVO, "CODPARC", codParc);
        setIfPresent(finVO, "NUMNOTA", numNota);
        setIfPresent(finVO, "VLRDESDOB", total);
        setIfPresent(finVO, "RECDESP", BigDecimal.ONE.negate());
        setIfPresent(finVO, "PROVISAO", "N");
        setTimestampIfPresent(finVO, "DTNEG", dataBase);
        setTimestampIfPresent(finVO, "DTVENC", dataBase);
        setTimestampIfPresent(finVO, "DTVENCINIC", dataBase);
        setTimestampIfPresent(finVO, "DHMOV", dataBase);
        setTimestampIfPresent(finVO, "DTALTER", new Date());
        setIfPresent(finVO, "ORIGEM", "F");

        setIfPresent(finVO, "CODTIPOPER", codTipOper);
        setIfPresent(finVO, "VLRCHEQUE", null);
        setIfPresent(finVO, "NUMNOTA", BigDecimal.ZERO);
        setIfPresent(finVO, "NUNOTA", null);
        setIfPresent(finVO, "CODEMPBAIXA", null);
        setIfPresent(finVO, "CODTIPOPERBAIXA",null);
        setIfPresent(finVO, "DHTIPOPERBAIXA", null);
        setIfPresent(finVO, "VLRBAIXA", BigDecimal.ZERO);
        setIfPresent(finVO, "DHBAIXA", BigDecimal.ZERO);
        setIfPresent(finVO, "NUBCO", BigDecimal.ZERO);

        if (codTipOper != null && finVO.containsProperty("DHTIPOPER")) {
            finVO.setProperty("DHTIPOPER", TipoOperacaoUtils.getTopVO(codTipOper).asTimestamp("DHALTER"));
        }
        setIfPresent(finVO, "CODNAT", codNat);
        setIfPresent(finVO, "CODBCO", codBco);
        setIfPresent(finVO, "CODCTABCOINT", codCta);
        setIfPresent(finVO, "CODCENCUS", codCencus);
        setIfPresent(finVO, "CODVEND", codVend);
        setIfPresent(finVO, "CODTIPTIT", codTipTit);
        setIfPresent(finVO, "ORIGEM", origem);
        //setIfPresent(finVO, "HISTORICO", "OS integracao | qtd=" + quantidade + " | os=" + resumoOs);
        setIfPresent(finVO, "AD_OSHIST", "OS integracao | qtd=" + quantidade);
        setIfPresent(finVO, "OBSERVACAO", montarHistoricoFinanceiro(grupo, quantidade, valorUnitario, total));
        setIfPresent(finVO, "DESDOBRAMENTO", "0");
      //  setIfPresent(finVO, "AD_OSRESUMO", resumoOs);

        PersistentLocalEntity created = dwFacade.createEntity(DynamicEntityNames.FINANCEIRO, (EntityVO) finVO);
        DynamicVO createdVO = created == null ? null : (DynamicVO) created.getValueObject();

        System.out.println(LOG_PREFIX + "Financeiro persistido | cnpj=" + grupo.getCnpj()
                + " | nuFin=" + (createdVO == null ? null : createdVO.asBigDecimal("NUFIN"))
                + " | valor=" + total);

        return createdVO;
    }

    private static Date resolverDataBaseFinanceiro(DynamicVO osBase) {
        return new Date();
    }

    private static BigDecimal parseBigDecimalSeguro(String valor) {
        String txt = normalizarTexto(valor);
        if (txt == null) {
            return null;
        }
        try {
            return new BigDecimal(txt.replace(",", "."));
        } catch (Exception e) {
            return null;
        }
    }

    private static void setIfPresent(DynamicVO vo, String campo, Object valor) {
        if (vo != null && vo.containsProperty(campo)) {
            vo.setProperty(campo, valor);
        }
    }

    private static void setTimestampIfPresent(DynamicVO vo, String campo, Date valor) {
        if (vo != null && vo.containsProperty(campo)) {
            vo.setProperty(campo, valor == null ? null : new Timestamp(valor.getTime()));
        }
    }

    // Mantido por compatibilidade; nao utilizado no novo fluxo default VO.
    private static DynamicVO clonarFinanceiroComoDespesa(DynamicVO financeiroOrig,
                                                         BigDecimal topDespesa,
                                                         BigDecimal sequencia,
                                                         String vendaMl,
                                                         BigDecimal vlrDesdob,
                                                         BigDecimal codParc,
                                                         BigDecimal codNat,
                                                         String historico) throws Exception {
        DynamicVO novoFinVO = financeiroOrig.buildClone();
        novoFinVO.setPrimaryKey(null);
        novoFinVO.clearReferences();

        DynamicVO centroResultadoVO = JapeFactory.dao(DynamicEntityNames.CENTRO_RESULTADO)
                .findByPK(BigDecimal.valueOf(novoFinVO.asBigDecimalOrZero("CODCENCUS").longValue()));

        BigDecimal codCta = centroResultadoVO == null ? BigDecimal.ZERO : centroResultadoVO.asBigDecimalOrZero("AD_CODCTABCOINT");
        BigDecimal codNatCentro = centroResultadoVO == null ? BigDecimal.ZERO : centroResultadoVO.asBigDecimalOrZero("AD_CODNAT");
        BigDecimal codNatFinal = codNat != null ? codNat : codNatCentro;

        BigDecimal codBco = BigDecimal.ZERO;
        if (codCta != null && codCta.compareTo(BigDecimal.ZERO) > 0) {
            DynamicVO contaVO = JapeFactory.dao(DynamicEntityNames.CONTA_BANCARIA).findByPK(new Object[]{codCta});
            codBco = contaVO == null ? BigDecimal.ZERO : contaVO.asBigDecimalOrZero("CODBCO");
        }

        if (codParc != null && novoFinVO.containsProperty("CODPARC")) {
            novoFinVO.setProperty("CODPARC", codParc);
        }
        if (novoFinVO.containsProperty("PROVISAO")) {
            novoFinVO.setProperty("PROVISAO", "N");
        }
        Date dataBase = new Date();
        setTimestampIfPresent(novoFinVO, "DTNEG", dataBase);
        setTimestampIfPresent(novoFinVO, "DTVENC", dataBase);
        setTimestampIfPresent(novoFinVO, "DTVENCINIC", dataBase);
        setTimestampIfPresent(novoFinVO, "DHMOV", dataBase);
        setTimestampIfPresent(novoFinVO, "DTALTER", dataBase);
        setIfPresent(novoFinVO, "CODEMPBAIXA", null);
        setIfPresent(novoFinVO, "CODTIPOPERBAIXA", null);
        setIfPresent(novoFinVO, "DHTIPOPERBAIXA", null);
        setIfPresent(novoFinVO, "VLRBAIXA", null);
        setIfPresent(novoFinVO, "DHBAIXA", null);
        setIfPresent(novoFinVO, "NUBCO", null);
        novoFinVO.setProperty("CODTIPOPER", topDespesa);
        if (topDespesa != null) {
            novoFinVO.setProperty("DHTIPOPER", TipoOperacaoUtils.getTopVO(topDespesa).asTimestamp("DHALTER"));
        }
        novoFinVO.setProperty("HISTORICO", "NUREG: " + sequencia + " - Número de Venda ML: " + vendaMl);
        if (historico != null && !historico.trim().isEmpty() && novoFinVO.containsProperty("OBSERVACAO")) {
            novoFinVO.setProperty("OBSERVACAO", historico);
        }
        novoFinVO.setProperty("RECDESP", BigDecimal.ONE.negate());
        novoFinVO.setProperty("VLRDESDOB", vlrDesdob);
        novoFinVO.setProperty("CODCTABCOINT", codCta);
        novoFinVO.setProperty("CODBCO", codBco);
        novoFinVO.setProperty("CODNAT", codNatFinal);
        novoFinVO.setProperty("NUFIN", null);

        EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
        dwFacade.createEntity(DynamicEntityNames.FINANCEIRO, (EntityVO) novoFinVO);

        return novoFinVO;
    }

    // Mantido por compatibilidade; nao utilizado no novo fluxo default VO.
    private static DynamicVO buscarFinanceiroReferencia() throws Exception {
        BigDecimal nuFinReferencia = obterNuFinReferencia();
        JapeWrapper financeiroDAO = JapeFactory.dao(DAO_FINANCEIRO);
        return financeiroDAO.findByPK(new Object[]{nuFinReferencia});
    }

    private static DynamicVO buscarFinanceiroReferenciaSeguro() {
        try {
            return buscarFinanceiroReferencia();
        } catch (Exception e) {
            System.out.println(LOG_PREFIX + "Nao foi possivel carregar financeiro de referencia configurado no parametro "
                    + PARAM_NUFIN_CLONE + ": " + e.getMessage());
            return null;
        }
    }

    private static BigDecimal obterNuFinReferencia() throws Exception {
        Object valor = ParameterUtils.getParameter(PARAM_NUFIN_CLONE);
        if (valor == null) {
            throw new Exception("Parametro " + PARAM_NUFIN_CLONE + " nao configurado.");
        }
        if (valor instanceof BigDecimal) {
            return (BigDecimal) valor;
        }

        String texto = normalizarTexto(valor.toString());
        if (texto == null) {
            throw new Exception("Parametro " + PARAM_NUFIN_CLONE + " sem valor.");
        }
        try {
            return new BigDecimal(texto);
        } catch (Exception e) {
            throw new Exception("Parametro " + PARAM_NUFIN_CLONE + " invalido para NUFIN: " + texto, e);
        }
    }

    private static BigDecimal obterBigDecimalSePresente(DynamicVO vo, String campo) {
        if (vo == null || campo == null) {
            return null;
        }
        try {
            if (!vo.containsProperty(campo)) {
                return null;
            }
            BigDecimal valor = vo.asBigDecimal(campo);
            return valor != null && valor.compareTo(BigDecimal.ZERO) > 0 ? valor : null;
        } catch (Exception ignore) {
            return null;
        }
    }

    private static String obterTextoSePresente(DynamicVO vo, String campo) {
        if (vo == null || campo == null) {
            return null;
        }
        try {
            if (!vo.containsProperty(campo)) {
                return null;
            }
            return normalizarTexto(vo.asString(campo));
        } catch (Exception ignore) {
            return null;
        }
    }

    private static void validarCentroResultadoObrigatorio(BigDecimal codCencus) throws Exception {
        if (codCencus != null && codCencus.compareTo(BigDecimal.ZERO) > 0) {
            return;
        }

        try {
            Boolean exigeCentroResultado = parseBoolean(ParameterUtils.getParameter("EXIGCRCFR"));
            if (Boolean.TRUE.equals(exigeCentroResultado)) {
                throw new Exception("Centro de Resultado obrigatorio para financeiro. Informe CODCENCUS no financeiro de referencia "
                        + "configurado no parametro " + PARAM_NUFIN_CLONE + ".");
            }
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Centro de Resultado obrigatorio")) {
                throw e;
            }
        }
    }

    private static void preencherCabecalhoNota(DynamicVO notaVO, BigDecimal codParc, DynamicVO topVO, BigDecimal total, String resumoOs) {
        if (notaVO.containsProperty("CODPARC")) {
            notaVO.setProperty("CODPARC", codParc);
        }
        if (notaVO.containsProperty("TIPMOV") && topVO != null) {
            notaVO.setProperty("TIPMOV", topVO.asString("TIPMOV"));
        }
        if (notaVO.containsProperty("CODTIPOPER")) {
            notaVO.setProperty("CODTIPOPER", topVO != null ? topVO.asBigDecimal("CODTIPOPER") : null);
        }
        if (notaVO.containsProperty("DHTIPOPER") && topVO != null) {
            notaVO.setProperty("DHTIPOPER", topVO.asTimestamp("DHALTER"));
        }
        if (notaVO.containsProperty("VLRNOTA")) {
            notaVO.setProperty("VLRNOTA", total);
        }
        if (notaVO.containsProperty("VLRFRETE")) {
            notaVO.setProperty("VLRFRETE", BigDecimal.ZERO);
        }
        setTimestampIfPresent(notaVO, "DTNEG", new Date());
        setTimestampIfPresent(notaVO, "DTALTER", new Date());
        if (notaVO.containsProperty("CODUSU")) {
            notaVO.setProperty("CODUSU", BigDecimal.ZERO);
        }




        if (notaVO.containsProperty("OBSERVACAO")) {
            notaVO.setProperty("OBSERVACAO", montarHistoricoNota(codParc, resumoOs, total));
        }
        if (notaVO.containsProperty("AD_NUMOS")) {
            notaVO.setProperty("AD_NUMOS", resumoOs);
        }
    }

    private static void preencherItemNota(DynamicVO itemVO, BigDecimal codProdutoServico, int quantidade, BigDecimal valorUnitario, BigDecimal total) {
        if (itemVO.containsProperty("NUNOTA")) {
            itemVO.setProperty("NUNOTA", null);
        }
        if (itemVO.containsProperty("CODPROD")) {
            itemVO.setProperty("CODPROD", codProdutoServico);
        }
        if (itemVO.containsProperty("CODVOL")) {
            itemVO.setProperty("CODVOL", getCodVol(codProdutoServico));
        }
        if (itemVO.containsProperty("QTDNEG")) {
            itemVO.setProperty("QTDNEG", BigDecimal.valueOf(quantidade));
        }
        if (itemVO.containsProperty("PERCDESC")) {
            itemVO.setProperty("PERCDESC", BigDecimal.ZERO);
        }
        if (itemVO.containsProperty("VLRDESC")) {
            itemVO.setProperty("VLRDESC", BigDecimal.ZERO);
        }
        if (itemVO.containsProperty("VLRUNIT")) {
            itemVO.setProperty("VLRUNIT", valorUnitario);
        }
        if (itemVO.containsProperty("VLRTOT")) {
            itemVO.setProperty("VLRTOT", total);
        }
        if (itemVO.containsProperty("CODLOCALORIG")) {
            itemVO.setProperty("CODLOCALORIG", buscarCodLocalPadrao());
        }
    }


    private static BigDecimal buscarValorFixoServicoPorCnpj(String cnpj) {
        if (cnpj == null) {
            return null;
        }
        try {
            String cnpjConsulta = normalizarTexto(cnpj);
            System.out.println(LOG_PREFIX + "buscarValorFixoServicoPorCnpj | inputCnpj=" + cnpj + " | consulta=" + cnpjConsulta);
            if (cnpjConsulta == null) {
                return null;
            }

            DynamicVO parVO = JapeFactory.dao("Parceiro").findOne("CGC_CPF = ?", cnpjConsulta);
            if (parVO == null) {
                System.out.println(LOG_PREFIX + "Nenhum parceiro encontrado em Parceiro para o CNPJ=" + cnpjConsulta);
                return null;
            }

            BigDecimal valor = parVO.asBigDecimalOrZero("AD_VLRFIXSER");
            System.out.println(LOG_PREFIX + "Parceiro encontrado em Parceiro | NOMEPARC=" + parVO.asString("NOMEPARC") + " | AD_VLRFIXSER=" + valor);
            return valor;
        } catch (Exception e) {
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
            System.out.println(LOG_PREFIX + "Erro ao buscar valor fixo por CNPJ: " + e.getMessage());
            return null;
        }
    }

    private static String buscarDescricaoParceiroPorCnpj(String cnpj) {
        try {
            String cnpjConsulta = normalizarTexto(cnpj);
            if (cnpjConsulta == null) {
                return "";
            }
            DynamicVO parVO = JapeFactory.dao("Parceiro").findOne("CGC_CPF = ?", cnpjConsulta);
            if (parVO == null) return "";
            String nome = parVO.asString("NOMEPARC");
            if (nome != null && !nome.trim().isEmpty()) return nome.trim();
            String c = parVO.asString("CGC_CPF");
            return c == null ? "" : c.trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Busca o parceiro usando a entidade "Parceiro" com correspondência exata
     * do CGC_CPF informado.
     */
    private static DynamicVO buscarParceiroPorCnpjNaEntidadeParceiros(String cnpj) {
        try {
            String cnpjConsulta = normalizarTexto(cnpj);
            System.out.println(LOG_PREFIX + "buscarParceiroPorCnpjNaEntidadeParceiros | inputCnpj=" + cnpj + " | consulta=" + cnpjConsulta);
            if (cnpjConsulta == null) return null;

            DynamicVO parVO = JapeFactory.dao("Parceiro").findOne("CGC_CPF = ?", cnpjConsulta);
            if (parVO == null) {
                System.out.println(LOG_PREFIX + "Nenhum parceiro encontrado em Parceiro para o CNPJ=" + cnpjConsulta);
                return null;
            }

            System.out.println(LOG_PREFIX + "Parceiro encontrado | NOMEPARC=" + parVO.asString("NOMEPARC") + " | CGC_CPF=" + parVO.asString("CGC_CPF"));
            return parVO;
        } catch (Exception e) {
            try { LogUtil.saveLog(e); } catch (Exception ignore) {}
            System.out.println(LOG_PREFIX + "Erro ao buscar parceiro na entidade Parceiro: " + e.getMessage());
            return null;
        }
    }

    private static BigDecimal buscarCodParcPorCnpjExato(String cnpj) {
        try {
            DynamicVO parVO = buscarParceiroPorCnpjNaEntidadeParceiros(cnpj);
            return parVO == null ? null : parVO.asBigDecimal("CODPARC");
        } catch (Exception e) {
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
            return null;
        }
    }

    private static boolean possuiPecas(String numeroOs) throws Exception {
        if (numeroOs == null || numeroOs.trim().isEmpty()) {
            return false;
        }

        String numeroNormalizado = numeroOs.trim();
        if (!isNumeric(numeroNormalizado)) {
            return false;
        }

        BigDecimal osId = new BigDecimal(numeroNormalizado);
        Collection<DynamicVO> pecas = JapeFactory.dao(DAO_OS_PECAS).find("this.NROOS = ?", osId);
        return pecas != null && !pecas.isEmpty();
    }

    private static String resolverNumeroOs(OsIntegracaoModel osIntegracao) {
        if (osIntegracao == null) {
            return null;
        }
        if (isNumeric(osIntegracao.getNumeroOs())) {
            return osIntegracao.getNumeroOs().trim();
        }
        if (isNumeric(osIntegracao.getOs())) {
            return osIntegracao.getOs().trim();
        }
        return normalizarTexto(osIntegracao.getNumeroOs());
    }

    private static String montarHistoricoNota(BigDecimal codParc, String resumoOs, BigDecimal total) {
        StringBuilder sb = new StringBuilder();
        sb.append("OS agrupadas para parceiro ").append(codParc);
        if (resumoOs != null && !resumoOs.trim().isEmpty()) {
            sb.append(" | OS: ").append(resumoOs);
        }
        sb.append(" | Total: ").append(total);
        return sb.toString();
    }

    private static String montarHistoricoFinanceiro(GrupoParceiroOs grupo, int quantidade, BigDecimal valorUnitario, BigDecimal total) {
        StringBuilder sb = new StringBuilder();
        sb.append("OS integracao - cnpj ").append(grupo.getCnpj());
        if (grupo.getDescricaoParceiro() != null && !grupo.getDescricaoParceiro().trim().isEmpty()) {
            sb.append(" (").append(grupo.getDescricaoParceiro().trim()).append(")");
        }
        sb.append(" | qtdOS=").append(quantidade);
        sb.append(" | valorUnitario=").append(valorUnitario);
        sb.append(" | total=").append(total);
        sb.append(" | OS=").append(grupo.resumoOsSemPecas());
        return sb.toString();
    }

    private static BigDecimal obterParametroBigDecimal(String... nomesCampos) {
        try {
            BigDecimal idCfg = (BigDecimal) ParameterUtils.getParameter("IDCONFIGTLC");
            if (idCfg == null) {
                return null;
            }

            JapeWrapper cfgDAO = JapeFactory.dao("AD_TCLCFG");
            DynamicVO cfgVO = cfgDAO.findByPK(idCfg);
            if (cfgVO == null) {
                return null;
            }

            for (String nomeCampo : nomesCampos) {
                try {
                    BigDecimal valor = cfgVO.asBigDecimal(nomeCampo);
                    if (valor != null) {
                        return valor;
                    }
                } catch (Exception ignore) {
                }
            }
        } catch (Exception e) {
            try {
                LogUtil.saveLog(e);
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private static String normalizarTexto(String valor) {
        if (valor == null) {
            return null;
        }
        String texto = valor.trim();
        return texto.isEmpty() ? null : texto;
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

    private static String getCodVol(BigDecimal codprod) {
        try {
            JapeWrapper produtoDAO = JapeFactory.dao("Produto");
            DynamicVO produtoVO = produtoDAO.findByPK(codprod);
            return produtoVO == null ? "" : produtoVO.asString("CODVOL");
        } catch (Exception e) {
            return "";
        }
    }

    private static BigDecimal buscarCodLocalPadrao() {
        try {
            ConfigTelecontrol config = new ConfigTelecontrol();
            return config.codLocal;
        } catch (Exception e) {
            return null;
        }
    }

    private static String normalizarCnpj(String valor) {
        String texto = normalizarTexto(valor);
        if (texto == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char ch = texto.charAt(i);
            if (Character.isDigit(ch)) {
                sb.append(ch);
            }
        }

        String cnpj = sb.toString();
        return cnpj.isEmpty() ? null : cnpj;
    }

    private static final class GrupoParceiroOs {
        private final String cnpj;
        private final String descricaoParceiro;
        private final List<String> osComPecas = new ArrayList<>();
        private final List<String> osSemPecas = new ArrayList<>();

        private GrupoParceiroOs(String cnpj, String descricaoParceiro) {
            this.cnpj = cnpj;
            this.descricaoParceiro = descricaoParceiro;
        }

        private void adicionarOs(OsIntegracaoModel osIntegracao, boolean possuiPecas) {
            String numeroOs = resolverNumeroOs(osIntegracao);
            if (numeroOs == null || numeroOs.trim().isEmpty()) {
                return;
            }
            if (possuiPecas) {
                osComPecas.add(numeroOs.trim());
            } else {
                osSemPecas.add(numeroOs.trim());
            }
        }

        private String getCnpj() {
            return cnpj;
        }

        private String getDescricaoParceiro() {
            return descricaoParceiro;
        }

        private int getQuantidadeComPecas() {
            return osComPecas.size();
        }

        private int getQuantidadeSemPecas() {
            return osSemPecas.size();
        }

        private List<String> getOsComPecas() {
            return new ArrayList<String>(osComPecas);
        }

        private List<String> getOsSemPecas() {
            return new ArrayList<String>(osSemPecas);
        }

        private List<String> getTodasOs() {
            List<String> todas = new ArrayList<String>(osComPecas);
            todas.addAll(osSemPecas);
            return todas;
        }

        private int quantidadeTotal() {
            return osComPecas.size() + osSemPecas.size();
        }

        private String resumoOsComPecas() {
            return resumirLista(osComPecas);
        }

        private String resumoOsSemPecas() {
            return resumirLista(osSemPecas);
        }

        private String resumirLista(List<String> lista) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lista.size(); i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(lista.get(i));
            }
            return sb.toString();
        }
    }

    public static final class ResultadoDocumento {
        private int totalOsRecebidas;
        private int totalGrupos;
        private int notasGeradas;
        private int notasIgnoradasPorParametro;
        private int financeirosGerados;
        private int gruposComErro;
        private int osIgnoradasPorOsPaga;
        private int osIgnoradasPorStatus;
        private boolean criacaoNotaCompraHabilitada;
        private String aviso;
        private String erro;

        public int getTotalOsRecebidas() {
            return totalOsRecebidas;
        }

        public int getTotalGrupos() {
            return totalGrupos;
        }

        public int getNotasGeradas() {
            return notasGeradas;
        }

        public int getNotasIgnoradasPorParametro() {
            return notasIgnoradasPorParametro;
        }

        public int getFinanceirosGerados() {
            return financeirosGerados;
        }

        public int getGruposComErro() {
            return gruposComErro;
        }

        public int getOsIgnoradasPorOsPaga() {
            return osIgnoradasPorOsPaga;
        }

        public int getOsIgnoradasPorStatus() {
            return osIgnoradasPorStatus;
        }

        public boolean isCriacaoNotaCompraHabilitada() {
            return criacaoNotaCompraHabilitada;
        }

        public String getAviso() {
            return aviso;
        }

        public String getErro() {
            return erro;
        }
    }

    private static boolean isOsPaga(String numeroOs) {
        try {
            if (numeroOs == null || !isNumeric(numeroOs)) {
                return false;
            }
            DynamicVO osVO = JapeFactory.dao(DAO_OS_INTEGRACAO).findByPK(new BigDecimal(numeroOs.trim()));
            if (osVO == null) {
                return false;
            }
            String osPaga = osVO.asString("OSPAGA");
            return "S".equalsIgnoreCase(osPaga != null ? osPaga.trim() : null);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isOsFinalizada(String numeroOs) {
        try {
            if (numeroOs == null || !isNumeric(numeroOs)) {
                return false;
            }
            DynamicVO osVO = JapeFactory.dao(DAO_OS_INTEGRACAO).findByPK(new BigDecimal(numeroOs.trim()));
            if (osVO == null) {
                return false;
            }
            String statusOs = normalizarTexto(osVO.asString("STATUSOS"));
            return STATUS_OS_FINALIZADA.equalsIgnoreCase(statusOs);
        } catch (Exception e) {
            return false;
        }
    }

    private static void marcarOsComoPagas(List<String> osNumeros, BigDecimal nuNota, BigDecimal nuFin) throws Exception {
        if (osNumeros == null || osNumeros.isEmpty()) {
            return;
        }

        JapeWrapper osDAO = JapeFactory.dao(DAO_OS_INTEGRACAO);
        Set<String> unicas = new LinkedHashSet<String>(osNumeros);
        for (String nroOs : unicas) {
            if (nroOs == null || !isNumeric(nroOs)) {
                continue;
            }

            DynamicVO osVO = osDAO.findByPK(new BigDecimal(nroOs.trim()));
            if (osVO == null) {
                continue;
            }

            if ("S".equalsIgnoreCase(normalizarTexto(osVO.asString("OSPAGA")))) {
                continue;
            }

            br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO updVO = osDAO.prepareToUpdate(osVO);
            if (osVO.containsProperty("OSPAGA")) {
                updVO.set("OSPAGA", "S");
            }
            if (nuNota != null && osVO.containsProperty("NUNOTAGER")) {
                updVO.set("NUNOTAGER", nuNota);
            }
            if (nuFin != null && osVO.containsProperty("NUFINGER")) {
                updVO.set("NUFINGER", nuFin);
            }
            updVO.update();
        }
    }

    private static void marcarOsComoPendentes(List<String> osNumeros) throws Exception {
        if (osNumeros == null || osNumeros.isEmpty()) {
            return;
        }

        JapeWrapper osDAO = JapeFactory.dao(DAO_OS_INTEGRACAO);
        for (String nroOs : new LinkedHashSet<String>(osNumeros)) {
            if (nroOs == null || !isNumeric(nroOs)) {
                continue;
            }

            DynamicVO osVO = osDAO.findByPK(new BigDecimal(nroOs.trim()));
            if (osVO == null) {
                continue;
            }

            br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO updVO = osDAO.prepareToUpdate(osVO);
            if (osVO.containsProperty("OSPAGA")) {
                updVO.set("OSPAGA", "N");
            }
            if (osVO.containsProperty("NUNOTAGER")) {
                updVO.set("NUNOTAGER", null);
            }
            if (osVO.containsProperty("NUFINGER")) {
                updVO.set("NUFINGER", null);
            }
            updVO.update();
        }
    }
}
