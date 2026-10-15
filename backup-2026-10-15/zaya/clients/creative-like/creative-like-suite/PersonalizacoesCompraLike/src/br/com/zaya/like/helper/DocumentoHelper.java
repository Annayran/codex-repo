package br.com.zaya.like.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.TimeUtils;

import br.com.sankhya.commons.xml.Element;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.mgecomercial.model.facades.helpper.FaturamentoHelper;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.CentralCabecalhoNota;
import br.com.sankhya.modelcore.comercial.CentralFaturamento;
import br.com.sankhya.modelcore.comercial.CentralFinanceiro;
import br.com.sankhya.modelcore.comercial.ComercialUtils;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.comercial.util.LoteAutomaticoHelper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.modelcore.util.SPBeanUtils;
import br.com.sankhya.timimob.model.aluguel.Negociacao;
import br.com.sankhya.ws.ServiceContext;

public class DocumentoHelper {
  private final Map<String, Object> cabecalhoNotaInfo = new HashMap<>();
  
  private final static List<Map<String, Object>> itemNotaInfo = new ArrayList<>();
  
  private final static List<Map<String, Object>> financeiroNotaInfo = new ArrayList<>();
  
  private BigDecimal nuNotaModelo;
  
  private BigDecimal nuNota;
  
  private Negociacao negociacao;
  
  public DocumentoHelper(BigDecimal nuNotaModelo) {
    setNuNotaModelo(nuNotaModelo);
  }
  
  public DocumentoHelper(Negociacao negociacao) {
    setNegociacao(negociacao);
  }
  
  private void setNegociacao(Negociacao negociacao) {
    this.negociacao = negociacao;
  }
  
  public BigDecimal getNuNotaModelo() {
    return this.nuNotaModelo;
  }
  
  public List<Map<String, Object>> getItemNotaInfo() {
    return this.itemNotaInfo;
  }
  
  public void setNuNotaModelo(BigDecimal nuNotaModelo) {
    this.nuNotaModelo = nuNotaModelo;
  }
  
  public void setNuNota(BigDecimal nuNota) {
    this.nuNota = nuNota;
  }
  
  public BigDecimal getNuNota() {
    return this.nuNota;
  }
  
  public void addNotaProperty(String property, Object value) {
    this.cabecalhoNotaInfo.put(property, value);
  }
  
  public void addItem() {
    this.itemNotaInfo.add(new HashMap<>());
  }
  
  public void addItemProperty(String property, Object value) {
    ((Map<String, Object>)this.itemNotaInfo.get(this.itemNotaInfo.size() - 1)).put(property, value);
  }
  
  public void addFinanceiro() {
    this.financeiroNotaInfo.add(new HashMap<>());
  }
  
  public void addFinanceiroProperty(String property, Object value) {
    ((Map<String, Object>)this.financeiroNotaInfo.get(this.financeiroNotaInfo.size() - 1)).put(property, value);
  }
  
  public BigDecimal criarNota() throws Exception {
    if (ObjectUtil.isNull(this.nuNotaModelo))
      throw new MGEModelException("Modelo de nota nao informado!"); 
    JapeSession.SessionHandle hnd = null;
    hnd = JapeSession.open();
    hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
    hnd.execEnsuringTX(new JapeSession.TXBlock() {
          public void doWithTx() throws Exception {
            ServiceContext ctx = SessionUtil.ensureContext();
            ServiceContext.getCurrent().setRequestBody(new Element("requestBody"));
            EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
            PersistentLocalEntity modeloNotaPLE = null;
            DynamicVO oldNotaVO = null;
            BigDecimal nunota = (BigDecimal)DocumentoHelper.this.cabecalhoNotaInfo.get("NUNOTA");
            if (BigDecimalUtil.isEmpty(nunota)) {
              modeloNotaPLE = dwf.findEntityByPrimaryKey("CabecalhoNota", DocumentoHelper.this.nuNotaModelo);
            } else {
              modeloNotaPLE = dwf.findEntityByPrimaryKey("CabecalhoNota", nunota);
              oldNotaVO = ((DynamicVO)modeloNotaPLE.getValueObject()).buildClone();
            } 
            DynamicVO cabNotaVO = (DynamicVO)modeloNotaPLE.getValueObject();
            if (BigDecimalUtil.isEmpty(nunota)) {
              cabNotaVO.setProperty("NUNOTA", null);
              if (DocumentoHelper.this.cabecalhoNotaInfo.containsKey("CODTIPVENDA")) {
                DynamicVO dynamicVO = ComercialUtils.getTipoNegociacao((BigDecimal)DocumentoHelper.this.cabecalhoNotaInfo.get("CODTIPVENDA"));
                cabNotaVO.setProperty("DHTIPVENDA", dynamicVO.getProperty("DHALTER"));
                cabNotaVO.setProperty("TipoNegociacao", dynamicVO);
              } 
              if (DocumentoHelper.this.cabecalhoNotaInfo.containsKey("CODTIPOPER")) {
                DynamicVO dynamicVO = ComercialUtils.getTipoOperacao((BigDecimal)DocumentoHelper.this.cabecalhoNotaInfo.get("CODTIPOPER"));
                cabNotaVO.setProperty("DHTIPOPER", dynamicVO.getProperty("DHALTER"));
                cabNotaVO.setProperty("TipoOperacao", dynamicVO);
              } 
              cabNotaVO.setProperty("TIPMOV", cabNotaVO.asString("TipoOperacao.TIPMOV"));
              cabNotaVO.setProperty("DTMOV", TimeUtils.getNow());
              cabNotaVO.setProperty("HRMOV", null);
              cabNotaVO.setProperty("DTNEG", TimeUtils.getNow());
              cabNotaVO.setProperty("DTENTSAI", TimeUtils.getNow());
              cabNotaVO.setProperty("DTFATUR", TimeUtils.getNow());
              if ("T".equals(cabNotaVO.asString("TIPMOV")) && DocumentoHelper.this.cabecalhoNotaInfo.get("CODEMPNEGOC") == null) {
                DynamicVO dynamicVO = cabNotaVO.asDymamicVO("Parceiro");
                BigDecimal codEmpDest = NativeSql.getBigDecimal("CODEMP", "TSIEMP", "CGC = ?", dynamicVO.asString("CGC_CPF"));
                if (BigDecimalUtil.isEmpty(codEmpDest))
                  throw new Exception("Empresa de destino nao encontrada"); 
                cabNotaVO.setProperty("CODEMPNEGOC", codEmpDest);
              } 
            } else {
              if (DocumentoHelper.this.cabecalhoNotaInfo.containsKey("CODTIPVENDA")) {
                DynamicVO dynamicVO = ComercialUtils.getTipoNegociacao((BigDecimal)DocumentoHelper.this.cabecalhoNotaInfo.get("CODTIPVENDA"));
                cabNotaVO.setProperty("DHTIPVENDA", dynamicVO.getProperty("DHALTER"));
                cabNotaVO.setProperty("TipoNegociacao", dynamicVO);
              } 
              if (DocumentoHelper.this.cabecalhoNotaInfo.containsKey("CODTIPOPER")) {
                DynamicVO dynamicVO = ComercialUtils.getTipoOperacao((BigDecimal)DocumentoHelper.this.cabecalhoNotaInfo.get("CODTIPOPER"));
                cabNotaVO.setProperty("DHTIPOPER", dynamicVO.getProperty("DHALTER"));
                cabNotaVO.setProperty("TipoOperacao", dynamicVO);
              } 
            } 
            JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
            Iterator<Map.Entry<String, Object>> var17 = DocumentoHelper.this.cabecalhoNotaInfo.entrySet().iterator();
            while (var17.hasNext()) {
              Map.Entry<String, Object> entryx = var17.next();
              cabNotaVO.setProperty(entryx.getKey(), entryx.getValue());
            } 
            PrePersistEntityState cabPreState = PrePersistEntityState.build(dwf, "CabecalhoNota", cabNotaVO, oldNotaVO, modeloNotaPLE);
            CACHelper cacHelper = new CACHelper();
            cacHelper.addIncluirAlterarListener((CACHelper.IncluirAlterarItemListener)new LoteAutomaticoHelper());
            BarramentoRegra bRegrasCab = cacHelper.incluirAlterarCabecalho(ctx, cabPreState);
            DynamicVO newCabVO = bRegrasCab.getState().getNewVO();
            if (newCabVO == null)
              throw new MGEModelException("Erro interno: nao foi possivel incluir a nota."); 
            DocumentoHelper.this.nuNota = newCabVO.asBigDecimal("NUNOTA");
            if (!DocumentoHelper.this.itemNotaInfo.isEmpty()) {
              Collection<PrePersistEntityState> itensNotaPPS = new ArrayList<>();
              Iterator<Map<String, Object>> mapItemNotaInfo = DocumentoHelper.itemNotaInfo.iterator();
              while (mapItemNotaInfo.hasNext()) {
                Map financeiroInfo = mapItemNotaInfo.next();
                DynamicVO finVO = (DynamicVO)dwf.getDefaultValueObjectInstance("ItemNota");
                finVO.setAceptTransientProperties(true);
                finVO.setProperty("NUNOTA", newCabVO.getProperty("NUNOTA"));
                finVO.setProperty("CODEMP", newCabVO.getProperty("CODEMP"));
                Iterator<Map.Entry> var15 = financeiroInfo.entrySet().iterator();
                while (var15.hasNext()) {
                  Map.Entry entry = var15.next();
                  finVO.setProperty((String)entry.getKey(), entry.getValue());
                } 
                ComercialUtils.atualizarAtualEstoque(finVO, newCabVO);
                PrePersistEntityState itePreState = PrePersistEntityState.build(dwf, "ItemNota", finVO, (DynamicVO)null, modeloNotaPLE);
                itensNotaPPS.add(itePreState);
              } 
              cacHelper.incluirAlterarItem(DocumentoHelper.this.nuNota, ctx, itensNotaPPS, true);
            } 
            if (DocumentoHelper.this.financeiroNotaInfo.size() > 0) {
              CentralFinanceiro centralFinanceiro = new CentralFinanceiro();
              centralFinanceiro.excluiFinanceiro(DocumentoHelper.this.nuNota);
              Iterator<Map<String, Object>> mapFinanceiroNotaInfo = DocumentoHelper.financeiroNotaInfo.iterator();
              while (mapFinanceiroNotaInfo.hasNext()) {
                Map financeiroInfo = mapFinanceiroNotaInfo.next();
                DynamicVO finVO = (DynamicVO)dwf.getDefaultValueObjectInstance("Financeiro");
                if (!financeiroInfo.containsKey("VLRDESDOB"))
                  throw new IllegalArgumentException("necessinformar a propriedade Valor do Desdobramento (VLRDESDOB)!"); 
                if (!financeiroInfo.containsKey("CODTIPTIT"))
                  throw new IllegalArgumentException("necessinformar a propriedade Tipo de T(CODTIPTIT)!"); 
                finVO.setProperty("NUNOTA", newCabVO.getProperty("NUNOTA"));
                finVO.setProperty("VLRDESDOB", financeiroInfo.get("VLRDESDOB"));
                finVO.setProperty("ORIGEM", "E");
                finVO.setProperty("CODTIPTIT", financeiroInfo.get("CODTIPTIT"));
                finVO.setProperty("NUNOTA", DocumentoHelper.this.nuNota);
                finVO.setProperty("NUMNOTA", newCabVO.getProperty("NUMNOTA"));
                finVO.setProperty("CODEMP", newCabVO.getProperty("CODEMP"));
                finVO.setProperty("CODPARC", newCabVO.getProperty("CODPARC"));
                finVO.setProperty("CODCONTATO", newCabVO.getProperty("CODCONTATO"));
                finVO.setProperty("CODCENCUS", newCabVO.getProperty("CODCENCUS"));
                finVO.setProperty("CODPROJ", newCabVO.getProperty("CODPROJ"));
                finVO.setProperty("CODNAT", newCabVO.getProperty("CODNAT"));
                finVO.setProperty("DTENTSAI", newCabVO.getProperty("DTENTSAI"));
                finVO.setProperty("DHMOV", ComercialUtils.buscarDHMOV(newCabVO.asTimestamp("DTMOV"), newCabVO.asBigDecimal("HRMOV")));
                finVO.setProperty("CODTIPOPER", newCabVO.getProperty("CODTIPOPER"));
                finVO.setProperty("DHTIPOPER", newCabVO.getProperty("DHTIPOPER"));
                finVO.setProperty("CODVEND", newCabVO.getProperty("CODVEND"));
                finVO.setProperty("ORDEMCARGA", newCabVO.getProperty("ORDEMCARGA"));
                finVO.setProperty("NUMCONTRATO", newCabVO.getProperty("NUMCONTRATO"));
                finVO.setProperty("CODUSU", newCabVO.getProperty("CODUSUINC"));
                finVO.setProperty("PROVISAO", "N");
                finVO.setProperty("DTNEG", newCabVO.getProperty("DTNEG"));
                finVO.setProperty("DTVENCINIC", TimeUtils.getNow());
                finVO.setProperty("DTVENC", TimeUtils.getNow());
                Iterator<Map.Entry> var15 = financeiroInfo.entrySet().iterator();
                while (var15.hasNext()) {
                  Map.Entry entry = var15.next();
                  finVO.setProperty((String)entry.getKey(), entry.getValue());
                } 
                dwf.createEntity("Financeiro", (EntityVO)finVO);
              } 
            } 
          }
        });
    return this.nuNota;
  }
  
  public void confirmarNota() throws Exception {
    confirmarNota(this.nuNota);
  }
  
  public void confirmarNota(BigDecimal nuNota) throws Exception {
    if (nuNota == null)
      throw new MGEModelException("Informe uma nota para continuar"); 
    SessionUtil.ensureContext();
    ServiceContext.getCurrent().setRequestBody(new Element("requestBody"));
    String statusNota = NativeSql.getString("STATUSNOTA", "TGFCAB", "NUNOTA = ?", nuNota);
    if (!"L".equals(statusNota)) {
      CACHelper cacHelper = new CACHelper();
      cacHelper.processarConfirmacao(nuNota);
    } 
    statusNota = NativeSql.getString("STATUSNOTA", "TGFCAB", "NUNOTA = ?", nuNota);
    if (!"L".equals(statusNota))
      throw new MGEModelException("Nao foi possivel confirmar a nota " + nuNota + ". Verifique na Central de Notas"); 
  }
  
  public void removerNota() throws Exception {
    removerNota(this.nuNota);
  }
  
  public void removerNota(final BigDecimal nunota) throws Exception {
    JapeSession.SessionHandle hnd = null;
    try {
      hnd = JapeSession.open();
      hnd.execEnsuringTX(new JapeSession.TXBlock() {
            public void doWithTx() throws Exception {
              JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
              SessionUtil.ensureContext();
              CACHelper cacHelper = new CACHelper();
              cacHelper.excluirNota(nunota);
              JapeSessionContext.removeProperty("br.com.sankhya.com.CentralCompraVenda");
            }
          });
    } finally {
      JapeSession.close(hnd);
    } 
  }
  
  public void cancelarNota() throws Exception {
    cancelarNota(this.nuNota);
  }
  
  public void cancelarNota(BigDecimal nuNota) throws Exception {
    JapeSession.putProperty("br.com.sankhya.mgecom.centralnotas.NotaSendoCancelada", "S");
    try {
      CentralCabecalhoNota centralCabecalhoNota = new CentralCabecalhoNota();
      CentralCabecalhoNota.CancelamentoNotaCtx cancelamentoNotaCtx = new CentralCabecalhoNota.CancelamentoNotaCtx(BigDecimal.ZERO, "Cancelamento de Nota");
      Collection<BigDecimal> nuNotas = new ArrayList<>();
      nuNotas.add(nuNota);
      centralCabecalhoNota.cancelarNotasBatch(nuNotas, cancelamentoNotaCtx);
    } finally {
      JapeSession.putProperty("br.com.sankhya.mgecom.centralnotas.NotaSendoCancelada", "N");
    } 
  }
  
  public boolean cancelarOuExcluir(final BigDecimal nuNota) throws Exception {
    boolean var9;
    JapeSession.SessionHandle hnd = null;
    try {
      hnd = JapeSession.open();
      EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
      DynamicVO cabVO = (DynamicVO)dwf.findEntityByPrimaryKeyAsVO("CabecalhoNota", nuNota);
      boolean isVenda = ComercialUtils.ehPedidoOuVenda(cabVO.asString("TIPMOV"));
      boolean isConfirmada = "L".equals(cabVO.asString("STATUSNOTA"));
      boolean isDFe = (ComercialUtils.ehNFE(cabVO) || ComercialUtils.ehNFSE(cabVO) || ComercialUtils.ehModeloDocumentoFiscalNFCE(cabVO));
      hnd.execEnsuringTX(new JapeSession.TXBlock() {
            public void doWithTx() throws Exception {
              DocumentoHelper.this.estornarFinanceiros(nuNota);
            }
          });
      if (isVenda && isDFe && isConfirmada) {
        cancelarNota(nuNota);
      } else {
        removerNota(nuNota);
      } 
      BigDecimal nunota = NativeSql.getBigDecimal("NUNOTA", "TGFCAB", "NUNOTA = " + nuNota);
      var9 = (nunota == null);
    } finally {
      try {
        JapeSession.close(hnd);
      } catch (Exception exception) {}
    } 
    return var9;
  }
  
  private void estornarFinanceiros(BigDecimal nuNota) throws Exception {
    DynamicVO topVO = ComercialUtils.getTipoOperacao(BigDecimal.ZERO);
    NativeSql nSql = new NativeSql(EntityFacadeFactory.getDWFFacade().getJdbcWrapper());
    nSql.appendSql("UPDATE TGFFIN SET DHBAIXA = NULL, VLRBAIXA = 0, CODTIPOPERBAIXA = :TOP, DHTIPOPERBAIXA = :DHALTER, NUBCO = NULL  WHERE NUNOTA = :NUNOTA");
    nSql.setNamedParameter("NUNOTA", nuNota);
    nSql.setNamedParameter("TOP", topVO.asBigDecimal("CODTIPOPER"));
    nSql.setNamedParameter("DHALTER", topVO.asTimestamp("DHALTER"));
    nSql.executeUpdate();
  }
  
  public BarramentoRegra incluirAlterarCabecalho(DynamicVO cabVO) throws MGEModelException {
    JapeSession.SessionHandle hnd = null;
    BarramentoRegra bRegrasCab = null;
    try {
      hnd = JapeSession.open();
      AuthenticationInfo auth = AuthenticationInfo.getCurrent();
      EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
      CACHelper cacHelper = new CACHelper();
      if (cabVO.asBigDecimal("NUNOTA") == null) {
        JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
        PrePersistEntityState cabPreState = PrePersistEntityState.build(dwfFacade, "CabecalhoNota", cabVO);
        bRegrasCab = cacHelper.incluirAlterarCabecalho(auth, cabPreState);
      } else {
        JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
        PrePersistEntityState cabPreState = PrePersistEntityState.build(dwfFacade, "CabecalhoNota", cabVO);
        bRegrasCab = cacHelper.incluirAlterarCabecalho(auth, cabPreState);
      } 
    } catch (Exception e) {
      MGEModelException.throwMe(e);
    } finally {
      JapeSession.close(hnd);
    } 
    return bRegrasCab;
  }
  
  public BarramentoRegra incluirAlterarCabecalho(DynamicVO oldCabVO, DynamicVO newCabVO) throws MGEModelException {
    JapeSession.SessionHandle hnd = null;
    BarramentoRegra bRegrasCab = null;
    try {
      hnd = JapeSession.open();
      AuthenticationInfo auth = AuthenticationInfo.getCurrent();
      EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
      CACHelper cacHelper = new CACHelper();
      if (newCabVO.asBigDecimal("NUNOTA") == null) {
        JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
        PrePersistEntityState cabPreState = PrePersistEntityState.build(dwfFacade, "CabecalhoNota", newCabVO);
        bRegrasCab = cacHelper.incluirAlterarCabecalho(auth, cabPreState);
      } else {
        JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
        PrePersistEntityState cabPreState = PrePersistEntityState.build(dwfFacade, "CabecalhoNota", newCabVO);
        PersistentLocalEntity cabPle = dwfFacade.findEntityByPrimaryKey("CabecalhoNota", new Object[] { newCabVO.asBigDecimal("NUNOTA") });
        PrePersistEntityState cabPpes = PrePersistEntityState.build(dwfFacade, "CabecalhoNota", newCabVO, oldCabVO, cabPle);
        bRegrasCab = cacHelper.incluirAlterarCabecalho(auth, cabPpes);
      } 
    } catch (Exception e) {
      MGEModelException.throwMe(e);
    } finally {
      JapeSession.close(hnd);
    } 
    return bRegrasCab;
  }
  
  public BarramentoRegra.DadosBarramento incluirAlterarItem(DynamicVO cabVO, Collection itensNota) throws MGEModelException {
    JapeSession.SessionHandle hnd = null;
    BarramentoRegra.DadosBarramento dadosBarramento = null;
    try {
      hnd = JapeSession.open();
      AuthenticationInfo auth = AuthenticationInfo.getCurrent();
      EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
      CACHelper cacHelper = new CACHelper();
      JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
      try {
        dadosBarramento = cacHelper.incluirAlterarItem(cabVO.asBigDecimal("NUNOTA"), auth, itensNota, true);
      } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("Nfoi Possivel Incluir os Itens \n" + e.getMessage());
      } 
    } catch (Exception e) {
      e.printStackTrace();
      MGEModelException.throwMe(e);
    } finally {
      JapeSession.close(hnd);
    } 
    return dadosBarramento;
  }
  
  public BarramentoRegra.DadosBarramento alterarItem(DynamicVO cabVO, Collection itensNota) throws MGEModelException {
    JapeSession.SessionHandle hnd = null;
    BarramentoRegra.DadosBarramento dadosBarramento = null;
    try {
      hnd = JapeSession.open();
      AuthenticationInfo auth = AuthenticationInfo.getCurrent();
      EntityFacade dwfFacade = EntityFacadeFactory.getDWFFacade();
      CACHelper cacHelper = new CACHelper();
      JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
      JapeSessionContext.putProperty("ItemNota.incluindo.alterando.pela.central", Boolean.TRUE);
      try {
        dadosBarramento = cacHelper.incluirAlterarItem(cabVO.asBigDecimal("NUNOTA"), auth, itensNota, true);
      } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("Nfoi Possivel Incluir os Itens \n" + ExceptionUtils.getStackTrace(e));
      } 
    } catch (Exception e) {
      e.printStackTrace();
      MGEModelException.throwMe(e);
    } finally {
      JapeSession.close(hnd);
    } 
    return dadosBarramento;
  }
  
  public void faturarAnulacao(BigDecimal nunota, BigDecimal codTipOperacao, String serie) throws Exception {
    ServiceContext ctx = new ServiceContext(null);
    ctx.setAutentication(AuthenticationInfo.getCurrent());
    ctx.makeCurrent();
    SPBeanUtils.setupContext(ctx);
    JapeSession.SessionHandle hnd = null;
    try {
      hnd = JapeSession.open();
      hnd.setCanTimeout(false);
      hnd.setPriorityLevel(JapeSession.LOW_PRIORITY);
      CentralFaturamento.ConfiguracaoFaturamento cfg = getConfiguracaoFaturamento(codTipOperacao, serie);
      Collection<BigDecimal> notasSelecao = new ArrayList<>();
      notasSelecao.add(nunota);
      Map<BigDecimal, BigDecimal> notaMoedaMap = new HashMap<>();
      FaturamentoHelper.faturar(ctx, hnd, cfg, notasSelecao, notaMoedaMap);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Erro ao faturar pedido: " + ExceptionUtils.getStackTrace(e));
    } finally {
      JapeSession.close(hnd);
    } 
  }
  
  private CentralFaturamento.ConfiguracaoFaturamento getConfiguracaoFaturamento(BigDecimal codTipOperacao, String serie) {
    CentralFaturamento.ConfiguracaoFaturamento cfg = new CentralFaturamento.ConfiguracaoFaturamento();
    cfg.setCodTipOper(codTipOperacao);
    cfg.setSerie(serie);
    cfg.setDtFaturamento(TimeUtils.getNow());
    cfg.setDtEntSai(TimeUtils.getNow());
    cfg.setValidarData(true);
    cfg.setFaturamentoNormal(true);
    cfg.setUmaNota(false);
    cfg.setEhWizardFaturamento(true);
    cfg.setNfeDevolucaoViaRecusa(false);
    cfg.setFatDanfeSeguranca(false);
    cfg.setConfirmarNota(false);
    return cfg;
  }
  
  public DocumentoHelper() {}
}
