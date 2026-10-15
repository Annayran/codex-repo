package org.projects.ordemServico.Pecas;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.QueryExecutor;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.PersistenceException;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.BarramentoRegra;
import br.com.sankhya.modelcore.comercial.CentralFaturamento;
import br.com.sankhya.modelcore.comercial.ConfirmacaoNotaHelper;
import br.com.sankhya.modelcore.comercial.LiberacaoSolicitada;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import com.sankhya.util.BigDecimalUtil;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.codec.binary.Base64;
import org.projects.ordemServico.repositories.Repository;

public class GeraMovPeca implements AcaoRotinaJava {
    private String msgRetorno = "";
    private String msgErro = "";

    public void doAction(ContextoAcao ctx) throws PersistenceException {
        System.out.println("GeraMovPeca");

        for(int i = 0; i < ctx.getLinhas().length; ++i) {
            Registro line = ctx.getLinhas()[i];
            this.geraNota(line, ctx);
        }

        if (this.msgErro.equals("")) {
            ctx.setMensagemRetorno(this.msgRetorno);
        } else {
            ctx.setMensagemRetorno(this.msgErro);
        }

    }

    private void geraNota(Registro line, ContextoAcao ctx) throws PersistenceException {
        BigDecimal numOS = (BigDecimal)line.getCampo("NUMOS");
        BigDecimal nunotaInt = (BigDecimal)line.getCampo("NUNOTAINT");
        QueryExecutor query = ctx.getQuery();
        QueryExecutor queryIte = ctx.getQuery();
        QueryExecutor queryUpd = ctx.getQuery();
        QueryExecutor queryFim = ctx.getQuery();
        System.out.println("[GeraMovPeca]: Query dos atributos do cabede Pecas:");
        StringBuffer queLancaOS = new StringBuffer();
        queLancaOS.append(" SELECT DISTINCT ");
        queLancaOS.append("\t  OSE.NUMOS, ");
        queLancaOS.append("\t  OSE.DHABERTURA, ");
        queLancaOS.append("\t  OSE.CODVEICULO, ");
        queLancaOS.append("\t  OSE.CODEMP, ");
        queLancaOS.append("\t  OSE.HORIMETRO, ");
        queLancaOS.append("\t  TIP.FATPECAFIN AS GERAFIN, ");
        queLancaOS.append("\t  TIP.TIPOOS AS TIPOOS, ");
        queLancaOS.append("   NVL(TIP.CODCENCUS, OSE.CODCENCUS) AS CODCENCUS, ");
        queLancaOS.append("\t  OSE.CODPARC,  ");
        queLancaOS.append("\t  NVL(VEI.CODPARC, MAR.AD_CODPARC) AS CODFAB, ");
        queLancaOS.append("\t  NVL(CODVENDPEC, CODUSURESPOFICINA) CODUSURESPOFICINA, ");
        queLancaOS.append("   ('GERADA PELA O.S NUMERO: ' || OSE.NUMOS) AS OBS, ");
        queLancaOS.append("   NVL(TIP.CODNAT, OSE.CODNAT) AS CODNAT, ");
        queLancaOS.append("   OSE.CODPROJE, ");
        queLancaOS.append("   OSE.CODANOSAFRA, ");
        queLancaOS.append("   (SELECT MAX(TIPMOV) FROM TGFTOP WHERE CODTIPOPER = TOPEMP.CODTIPOPERDESTPROD) AS TIPMOV, ");
        queLancaOS.append("\t  PRO.TIPOMOVIMENTO, ");
        queLancaOS.append("\t  TOPEMP.CODTIPOPERDESTPROD CODTIPOPER, ");
        queLancaOS.append("   (SELECT PLACA || '  ' || MARCAMODELO || '   Horimetro :' || HORIMETRO FROM TGFVEI WHERE CODVEICULO = OSE.CODVEICULO) AS OBS_VEICULO, ");
        queLancaOS.append("   (SELECT CASE WHEN COUNT(*) = 0 THEN 'S' ELSE 'N' END FROM AD_TCSOSE O JOIN AD_TCSPRO P ON O.NUMOS = P.NUMOS WHERE O.NUMOS = OSE.NUMOS AND P.TIPOMOVIMENTO = 'P' AND COALESCE(P.CODCOMPRADOR, O.CODCOMPRADOR, 0) = 0) AS INDICADOR ");
        queLancaOS.append(" FROM AD_TCSOSE OSE ");
        queLancaOS.append("\t  JOIN AD_TCSPRO PRO ON OSE.NUMOS = PRO.NUMOS ");
        queLancaOS.append("\t  JOIN AD_TIPOORDEMSERVICO TIP ON TIP.CODTIPODEOS = OSE.CODTIPODEOS ");
        queLancaOS.append("\t  LEFT JOIN TGFVEI VEI ON VEI.CODVEICULO = OSE.CODVEICULO ");
        queLancaOS.append("\t  LEFT JOIN TGFMAR MAR ON MAR.CODIGO = OSE.CODMARCA ");
        queLancaOS.append("\t  LEFT JOIN AD_EMPRESAXTIPOOS TOPEMP ON TOPEMP.CODEMP = OSE.CODEMP AND TOPEMP.CODTIPODEOS = TIP.CODTIPODEOS ");
        queLancaOS.append(" WHERE ");
        queLancaOS.append("\t  OSE.NUMOS = " + numOS);
        queLancaOS.append("\t  AND PRO.NUNOTA IS NULL ");
        System.out.println(queLancaOS.toString());

        try {
            query.nativeSelect(queLancaOS.toString());

            while(query.next()) {
                System.out.println("[GeraMovPeca]: Inicio inclusdo Cabede Pecas: ");
                BigDecimal codTipOper = query.getBigDecimal("CODTIPOPER");
                if (codTipOper == null) {
                    this.msgRetorno = "TOP para faturemento de Pencadastrada no Tipo de OS!";
                    return;
                }

                Timestamp dhAbertura = query.getTimestamp("DHABERTURA") != null ? query.getTimestamp("DHABERTURA") : new Timestamp(System.currentTimeMillis());
                String tipMov = query.getString("TIPMOV");
                BigDecimal nuNotaGerado = new BigDecimal(0);
                EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
                DynamicVO cabVO = (DynamicVO)dwfEntityFacade.getDefaultValueObjectInstance("CabecalhoNota");
                cabVO.setProperty("CODTIPOPER", codTipOper);
                cabVO.setProperty("TIPMOV", tipMov);
                if ("V".equals(tipMov)) {
                    cabVO.setProperty("SERIENOTA", "1");
                }

                BigDecimal codUsuLogado = (BigDecimal)JapeSessionContext.getRequiredProperty("usuario_logado");
                BigDecimal codCenCusPadUsu = Repository.buscarCodCenCusPadUsuario(codUsuLogado);
                BigDecimal codCencusPad = BigDecimalUtil.getValueOrZero(query.getBigDecimal("CODCENCUS")).compareTo(BigDecimal.ZERO) == 0 ? codCenCusPadUsu : query.getBigDecimal("CODCENCUS");
                BigDecimal tipVendaGarantia = Repository.getTipoNegociacaoGarantia(query.getBigDecimal("CODEMP"));
                BigDecimal tipVenda = tipVendaGarantia.compareTo(BigDecimal.ZERO) != 0 && "GARANTIA".equals(query.getString("TIPOOS")) ? tipVendaGarantia : new BigDecimal(11);
                cabVO.setProperty("CODPARC", query.getBigDecimal("CODPARC"));
                cabVO.setProperty("CODEMP", query.getBigDecimal("CODEMP"));
                cabVO.setProperty("DTNEG", dhAbertura);
                cabVO.setProperty("CODNAT", query.getBigDecimal("CODNAT"));
                cabVO.setProperty("CODCENCUS", codCencusPad);
                cabVO.setProperty("CODPROJ", query.getBigDecimal("CODPROJE"));
                cabVO.setProperty("CODVEICULO", query.getBigDecimal("CODVEICULO"));
                cabVO.setProperty("CODTIPVENDA", tipVenda);
                cabVO.setProperty("AD_NUMOSOFICINA", numOS);
                cabVO.setProperty("AD_HORIMETRO", query.getBigDecimal("HORIMETRO"));
                cabVO.setProperty("OBSERVACAO", query.getString("OBS") + "\n " + query.getString("OBS_VEICULO"));
                cabVO.setProperty("CODVEND", query.getBigDecimal("CODUSURESPOFICINA"));

                try {
                    CACHelper cacHelper = new CACHelper();
                    AuthenticationInfo auth = AuthenticationInfo.getCurrent();
                    JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
                    PrePersistEntityState cabPreState = PrePersistEntityState.build(dwfEntityFacade, "CabecalhoNota", cabVO);
                    BarramentoRegra bRegrasCab = cacHelper.incluirAlterarCabecalho(auth, cabPreState);
                    DynamicVO newCabVO = bRegrasCab.getState().getNewVO();
                    nuNotaGerado = newCabVO.asBigDecimal("NUNOTA");
                    BigDecimal codEmp = query.getBigDecimal("CODEMP");
                    System.out.println("Cabeinclu" + nuNotaGerado);
                    System.out.println("[GeraMovPeca]: Query busca dos itens do Pedido de Venda de Pecas:");
                    StringBuffer sql1 = new StringBuffer();
                    sql1.append(" SELECT I.CODPROD, I.QUANTIDADE, NVL(I.VLRUNIT,0) AS VLRUNIT, NVL(I.CODVOL,PRO.CODVOL) AS CODVOL, ");
                    sql1.append(" NVL(I.CODLOCAL,0) AS CODLOCAL, (NVL(I.VLRUNIT,0) * I.QUANTIDADE) AS VLRTOT ");
                    sql1.append(" FROM AD_TCSOSE C ");
                    sql1.append(" JOIN AD_TCSPRO I ON C.NUMOS = I.NUMOS ");
                    sql1.append(" JOIN TGFPRO PRO ON I.CODPROD = PRO.CODPROD ");
                    sql1.append(" WHERE I.NUNOTA IS NULL AND C.NUMOS = " + numOS);
                    System.out.println(sql1.toString());
                    queryIte.nativeSelect(sql1.toString());

                    while(queryIte.next()) {
                        System.out.println("[GeraMovPeca]: Inicio inclusdo Produto " + queryIte.getBigDecimal("CODPROD"));
                        BigDecimal codProd = queryIte.getBigDecimal("CODPROD");
                        BigDecimal qtd = queryIte.getBigDecimal("QUANTIDADE");
                        BigDecimal codLocal = queryIte.getBigDecimal("CODLOCAL");
                        System.out.println("[GeraMovPeca]: Decrementando estoque do produto: " + codProd + " - Local: " + codLocal + " - Quantidade: " + qtd + " - Empresa: " + codEmp);
//                        DecrementarEstoqueOS decrementarEstoqueOS = new DecrementarEstoqueOS();
//                        decrementarEstoqueOS.decrementarReservadoTgfEst(codProd, codLocal, qtd, codEmp);
                        Collection<PrePersistEntityState> itensNota = new ArrayList();
                        DynamicVO itemVO = (DynamicVO)dwfEntityFacade.getDefaultValueObjectInstance("ItemNota");
                        itemVO.setProperty("CODPROD", queryIte.getBigDecimal("CODPROD"));
                        itemVO.setProperty("QTDNEG", queryIte.getBigDecimal("QUANTIDADE"));
                        itemVO.setProperty("CODLOCALORIG", queryIte.getBigDecimal("CODLOCAL"));
                        itemVO.setProperty("VLRUNIT", queryIte.getBigDecimal("VLRUNIT"));
                        itemVO.setProperty("VLRTOT", queryIte.getBigDecimal("VLRTOT"));
                        itemVO.setProperty("PERCDESC", BigDecimal.ZERO);
                        itemVO.setProperty("VLRDESC", BigDecimal.ZERO);
                        itemVO.setProperty("CODVOL", queryIte.getString("CODVOL"));
                        PrePersistEntityState itePreState = PrePersistEntityState.build(dwfEntityFacade, "ItemNota", itemVO);
                        itensNota.add(itePreState);
                        cacHelper.incluirAlterarItem(nuNotaGerado, auth, itensNota, false);
                        System.out.println("[GeraMovPeca]: Fim inclusdo produto -> " + queryIte.getBigDecimal("CODPROD").toString());
                    }

                    BarramentoRegra barramento = BarramentoRegra.build(CentralFaturamento.class, "regrasConfirmacaoSilenciosa.xml", AuthenticationInfo.getCurrent());
                    PrePersistEntityState persistentConf = ConfirmacaoNotaHelper.confirmarNota(nuNotaGerado, barramento);
                    DynamicVO notaVO = persistentConf.getNewVO();
                    if (!"L".equals(notaVO.asString("STATUSNOTA"))) {
                        StringBuffer erros = new StringBuffer();
                        if (!barramento.getErros().isEmpty()) {
                            for(Exception e : barramento.getErros()) {
                                if (erros.toString().length() > 0) {
                                    erros.append("\n");
                                }

                                erros.append(e.getMessage());
                            }
                        }

                        Collection<LiberacaoSolicitada> liberacoes = barramento.getLiberacoesSolicitadas();
                        if (liberacoes.size() > 0) {
                            this.msgRetorno = String.valueOf(this.msgRetorno) + " - Nota " + nuNotaGerado + " necessidade de liberapara confirmacao!<br>";
                        }

                        if (erros.toString().length() > 0) {
                            throw new Exception("Mensagem de erro ao tentar confirmar a nota: " + erros.toString());
                        }
                    }

                    sql1 = new StringBuffer();
                    sql1.append("UPDATE AD_TCSOSE SET NUNOTACAB = " + nuNotaGerado + " WHERE NUMOS = " + numOS);
                    queryUpd.update(sql1.toString());
                    sql1 = new StringBuffer();
                    sql1.append(" UPDATE AD_TCSPRO SET STATUS = 'G', NUNOTA = " + nuNotaGerado + " WHERE NUNOTA IS NULL ");
                    sql1.append(" AND NUMOS = " + numOS);
                    queryUpd.update(sql1.toString());
                    String urlPrincipal = "/mge/system.jsp#app/";
                    String classeCentral = "YnIuY29tLnNhbmtoeWEuY29tLm1vdi5DZW50cmFsTm90YXNfVkVOREE=/";
                    String parametroUrlCentral = "{\"NUNOTA\":" + nuNotaGerado + ", \"TIPMOV\":\"" + query.getString("TIPMOV") + "\", \"ehPedidoW\":false, \"CODTIPOPER\":" + query.getString("CODTIPOPER") + ", \"TIPOPORTAL\":\"PV\", \"forceNewHash\":" + System.currentTimeMillis() + "}";
                    byte[] encodedBytes = Base64.encodeBase64(parametroUrlCentral.getBytes());
                    String UrlCentral = urlPrincipal + classeCentral + new String(encodedBytes) + "&pk-refresh=" + System.currentTimeMillis();
                    String descrTipo = "Pedido de venda";
                    this.msgRetorno = this.msgRetorno + descrTipo + " " + nuNotaGerado + " gerado!\t<a id=\"alink\" href=\"" + UrlCentral + "\" target=\"_top\" style=\"background: #fff;\">Abrir na Central  <sk-icon font-icon=\"arrow-right\" class=\"ng-scope ng-isolate-scope\" style=\"color: rgb(221, 195, 64);margin: 0 0 0 1px;\"><span ng-disabled=\"!enabled\" ng-show=\"visible !== false\" ng-class=\"[prefixIcon, prefixIcon + '-' + fontIcon, skClass]\" class=\"glyphicons glyphicons-arrow-right\"></span></sk-icon></a><br>";
                    if ("N".equals(query.getString("GERAFIN"))) {
                        System.out.println("[GeraMovPeca]: Excluindo financeiro...");
                        sql1 = new StringBuffer();
                        sql1.append(" DELETE TGFFIN WHERE NUNOTA = " + nuNotaGerado);
                        queryUpd.update(sql1.toString());
                        System.out.println("[GeraMovPeca]: Financeiro do movimento " + nuNotaGerado + " exclusao");
                    } else if ("F".equals(query.getString("GERAFIN"))) {
                        System.out.println("[GeraMovPeca]: Alterando parceiro do financeiro...");
                        sql1 = new StringBuffer();
                        sql1.append(" UPDATE TGFFIN SET CODPARC = " + query.getBigDecimal("CODFAB"));
                        sql1.append(" WHERE NUNOTA = " + nuNotaGerado);
                        queryUpd.update(sql1.toString());
                        System.out.println("[GeraMovPeca]: Parceiro do financeiro do movimento " + nuNotaGerado + " eh " + query.getBigDecimal("CODFAB"));
                    }
// METODO PARA MUDAR NOTA PRA PENDENTE = N
                    Repository repo = new Repository();
                    repo.atualizarMovInternaComoPendente(nunotaInt);

                } catch (Exception e) {
                    System.out.println("GeraMovPortal: erro NUNOTA: " + nuNotaGerado);
                    if (nuNotaGerado.compareTo(new BigDecimal(0)) > 0) {
                        PersistentLocalEntity entity = dwfEntityFacade.findEntityByPrimaryKey("CabecalhoNota", nuNotaGerado);
                        entity.remove();
                    }

                    this.msgRetorno = String.valueOf(this.msgRetorno) + e.getMessage();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            this.msgRetorno = String.valueOf(this.msgRetorno) + e.getMessage();
            e.printStackTrace();
        }

    }
}
