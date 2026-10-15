//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.ordemServico.service;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.QueryExecutor;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.EntityFacade;
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
import com.sankhya.util.SQLUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.projects.ordemServico.repositories.Repository;

public class GeraMovServico implements AcaoRotinaJava {
    private String msgRetorno = "";
    private String msgErro = "";

    public GeraMovServico() {
    }

    public void doAction(ContextoAcao ctx) {
        System.out.println("GeraMovServico");

        for(int i = 0; i < ctx.getLinhas().length; ++i) {
            Registro line = ctx.getLinhas()[i];
            boolean val = this.validaDuplicidade(line, ctx);
            if (val) {
                this.geraNota(line, ctx);
            } else {
                this.msgRetorno = this.msgRetorno + "<b>Concluï¿½do com erro:</b><br/><br/>O.S " + line.getCampo("NUMOS").toString() + " com pedido de venda jï¿½ vinculado!<br/>";
                System.out.println("[GeraMovServico] O.S " + line.getCampo("NUMOS").toString() + " com pedido de venda jï¿½ vinculado!");
            }
        }

        if (this.msgErro.equals("")) {
            ctx.setMensagemRetorno(this.msgRetorno);
        } else {
            ctx.setMensagemRetorno(this.msgErro);
        }

    }

    private void geraNota(Registro line, ContextoAcao ctx) {
        BigDecimal numOs = (BigDecimal)line.getCampo("NUMOS");
        QueryExecutor query = ctx.getQuery();
        QueryExecutor query1 = ctx.getQuery();
        QueryExecutor queryIte = ctx.getQuery();
        QueryExecutor queryUpd = ctx.getQuery();
        QueryExecutor queryFim = ctx.getQuery();
        QueryExecutor queryFim2 = ctx.getQuery();
        System.out.println("[GeraMovServico]: Query dos atributos do cabeï¿½alho do serviï¿½o:");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM AD_VGF_FATUROS_CAB WHERE NUMOS = " + numOs);
        System.out.println(sql.toString());

        try {
            query.nativeSelect(sql.toString());

            while(query.next()) {
                System.out.println("[GeraMovServico]: Inicio inclusï¿½o do Cabeï¿½alho do Serviï¿½o");
                EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
                DynamicVO cabVO = (DynamicVO)dwfEntityFacade.getDefaultValueObjectInstance("CabecalhoNota");
                BigDecimal codUsuLogado = (BigDecimal)JapeSessionContext.getRequiredProperty("usuario_logado");
                BigDecimal codCenCusPadUsu = Repository.buscarCodCenCusPadUsuario(codUsuLogado);
                String tipoOS = Repository.getTipoOs(numOs);
                BigDecimal codCencusPad = BigDecimalUtil.getValueOrZero(query.getBigDecimal("CODCENCUS")).compareTo(BigDecimal.ZERO) == 0 ? codCenCusPadUsu : query.getBigDecimal("CODCENCUS");
                BigDecimal tipVendaGarantia = Repository.getTipoNegociacaoGarantia(query.getBigDecimal("CODEMP"));
                BigDecimal tipVenda = tipVendaGarantia.compareTo(BigDecimal.ZERO) != 0 && "GARANTIA".equals(tipoOS) ? tipVendaGarantia : new BigDecimal(11);
                BigDecimal nuNotaGerado = new BigDecimal(0);
                String tipoFaturamento = query.getString("TIPOFATURAMENTO");
                String tipMov = query.getString("TIPMOV");
                cabVO.setProperty("CODTIPOPER", query.getBigDecimal("CODTIPOPER"));
                cabVO.setProperty("DTNEG", query.getTimestamp("DHABERTURA"));
                cabVO.setProperty("TIPMOV", tipMov);
                if ("V".equals(tipMov)) {
                    cabVO.setProperty("SERIENOTA", "1");
                }

                cabVO.setProperty("CODPARC", query.getBigDecimal("CODPARC"));
                cabVO.setProperty("CODEMP", query.getBigDecimal("CODEMP"));
                cabVO.setProperty("CODNAT", query.getBigDecimal("CODNAT"));
                cabVO.setProperty("CODCENCUS", codCencusPad);
                cabVO.setProperty("CODPROJ", BigDecimal.ZERO);
                cabVO.setProperty("CODVEND", query.getBigDecimal("CODVEND"));
                cabVO.setProperty("CODTIPVENDA", tipVenda);
                cabVO.setProperty("AD_NUMOSSERVICO", numOs);
                cabVO.setProperty("OBSERVACAO", "Nota gerada pela OS " + numOs);

                try {
                    CACHelper cacHelper = new CACHelper();
                    AuthenticationInfo auth = AuthenticationInfo.getCurrent();
                    JapeSessionContext.putProperty("br.com.sankhya.com.CentralCompraVenda", Boolean.TRUE);
                    PrePersistEntityState cabPreState = PrePersistEntityState.build(dwfEntityFacade, "CabecalhoNota", cabVO);
                    BarramentoRegra bRegrasCab = cacHelper.incluirAlterarCabecalho(auth, cabPreState);
                    DynamicVO newCabVO = bRegrasCab.getState().getNewVO();
                    nuNotaGerado = newCabVO.asBigDecimal("NUNOTA");
                    System.out.println("[GeraMovServico]: Cabeï¿½alo inclusï¿½o do: " + nuNotaGerado);
                    System.out.println("[GeraMovServico]: Query para buscar os itens do serviï¿½o:");
                    StringBuffer sqlIte = new StringBuffer();
                    sqlIte.append("SELECT NUMOS, CODPROD, QTDNEG, VLRUNIT, CODVOL, TIPOFATURAMENTO, GERAFIN FROM AD_VGF_FATUROS_ITE WHERE NUMOS = " + numOs);
                    System.out.println(sqlIte.toString());
                    System.out.println("[GeraMovServico]: Inicio inclusaoo dos Itens de Serviï¿½o ");
                    queryIte.nativeSelect(sqlIte.toString());

                    while(queryIte.next()) {
                        if (tipoFaturamento.equals(queryIte.getString("TIPOFATURAMENTO"))) {
                            System.out.println("CODPROD -> " + queryIte.getBigDecimal("CODPROD"));
                            System.out.println("QTDNEG -> " + queryIte.getBigDecimal("QTDNEG"));
                            System.out.println("VLRUNIT -> " + queryIte.getBigDecimal("VLRUNIT"));
                            System.out.println("CODVOL -> " + queryIte.getString("CODVOL"));
                            System.out.println("TIPOFATURAMENTO do item: " + queryIte.getString("TIPOFATURAMENTO"));
                            DynamicVO itemVO = (DynamicVO)dwfEntityFacade.getDefaultValueObjectInstance("ItemNota");
                            Collection<PrePersistEntityState> itensNota = new ArrayList();
                            itemVO.setProperty("CODPROD", queryIte.getBigDecimal("CODPROD"));
                            itemVO.setProperty("QTDNEG", queryIte.getBigDecimal("QTDNEG"));
                            itemVO.setProperty("VLRUNIT", queryIte.getBigDecimal("VLRUNIT"));
                            itemVO.setProperty("VLRTOT", queryIte.getBigDecimal("VLRUNIT"));
                            itemVO.setProperty("PERCDESC", BigDecimal.ZERO);
                            itemVO.setProperty("VLRDESC", BigDecimal.ZERO);
                            itemVO.setProperty("CODVOL", queryIte.getString("CODVOL"));
                            PrePersistEntityState itePreState = PrePersistEntityState.build(dwfEntityFacade, "ItemNota", itemVO);
                            itensNota.add(itePreState);
                            cacHelper.incluirAlterarItem(nuNotaGerado, auth, itensNota, false);
                            System.out.println("[GeraMovServico]: Fim inclusï¿½o produto - " + queryIte.getBigDecimal("CODPROD"));
                        }

                        List<String> tipoFaturamentoList = converterStringParaLista(tipoFaturamento);
                        sql = new StringBuffer();
                        sql.append(" WITH TST AS (  ");
                        sql.append("     SELECT  ");
                        sql.append("         NUMOS,  ");
                        sql.append("         REGEXP_SUBSTR(VALOR, '[^,]+', 1, LEVEL) AS TIPOFATURAMENTO  ");
                        sql.append("     FROM (  ");
                        sql.append("         SELECT NUMOS, TIPOFATURAMENTO AS VALOR  ");
                        sql.append("         FROM AD_VGF_FATUROS_ITE  ");
                        sql.append("         WHERE NUMOS = " + numOs);
                        sql.append("     ) X  ");
                        sql.append("     CONNECT BY LEVEL <= REGEXP_COUNT(VALOR, '[,]') + 1  ");
                        sql.append("         AND PRIOR NUMOS = NUMOS  ");
                        sql.append("         AND PRIOR SYS_GUID() IS NOT NULL  ");
                        sql.append(" )  ");
                        sql.append(" SELECT distinct ");
                        sql.append("     CASE  ");
                        sql.append("         WHEN TIPOFATURAMENTO = 'S' THEN 'AD_TCSITE'  ");
                        sql.append("         WHEN TIPOFATURAMENTO = 'KM' THEN 'AD_APONTAMENTOS'  ");
                        sql.append("         WHEN TIPOFATURAMENTO = 'T' THEN 'AD_TCSSERVTERC'  ");
                        sql.append("     END AS TABELA  ");
                        sql.append(" FROM TST  ");
                        sql.append(" WHERE ").append(SQLUtils.buildINClauseByValuesWithQuotes("TIPOFATURAMENTO", tipoFaturamentoList));
                        System.out.println(sql.toString());
                        query1.nativeSelect(sql.toString());

                        for(; query1.next(); queryUpd.update(sql.toString())) {
                            String tabela = query1.getString("TABELA");
                            String campoNota = "";
                            if ("AD_TCSITE".equals(tabela)) {
                                campoNota = "NUNOTA";
                            } else if ("AD_APONTAMENTOS".equals(tabela)) {
                                campoNota = "NUNOTAAPONT";
                            } else if ("AD_TCSSERVTERC".equals(tabela)) {
                                campoNota = "NUNOTAPED";
                            }

                            sql = new StringBuffer();
                            sql.append("UPDATE " + tabela + " SET " + campoNota + " = " + nuNotaGerado);
                            sql.append(" WHERE NUMOS = ").append(numOs);
                            if (!"AD_TCSSERVTERC".equals(tabela)) {
                                System.out.println(sql.toString());
                            }
                        }

                        String sql2 = "UPDATE AD_TCSOSE SET STATUSSERVICO = 'EA' WHERE NUMOS = " + numOs;
                        queryUpd.update(sql2);
                    }

                    System.out.println("[GeraMovServico]: Inicio confirmaï¿½ï¿½o da nota");
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
                            this.msgRetorno = this.msgRetorno + " - Nota " + nuNotaGerado + " necessidade de liberapara confirma<br>";
                        }

                        if (erros.toString().length() > 0) {
                            throw new Exception("Mensagem ao tentar confirmar a nota: " + erros.toString());
                        }
                    }

                    System.out.println("[GeraMovServico]: " + nuNotaGerado + " confirmada!");
                    String urlPrincipal = "/mge/system.jsp#app/";
                    String classeCentral = "YnIuY29tLnNhbmtoeWEuY29tLm1vdi5DZW50cmFsTm90YXNfVkVOREE=/";
                    String parametroUrlCentral = "{\"NUNOTA\":" + nuNotaGerado + ", \"TIPMOV\":\"" + query.getString("TIPMOV") + "\", \"ehPedidoW\":false, \"CODTIPOPER\":" + query.getString("CODTIPOPER") + ", \"TIPOPORTAL\":\"PV\", \"forceNewHash\":" + System.currentTimeMillis() + "}";
                    byte[] encodedBytes = Base64.encodeBase64(parametroUrlCentral.getBytes());
                    String UrlCentral = urlPrincipal + classeCentral + new String(encodedBytes) + "&pk-refresh=" + System.currentTimeMillis();
                    String descrTipo = "Pedido de venda";
                    this.msgRetorno = this.msgRetorno + descrTipo + " " + nuNotaGerado + " gerado!\t<a id=\"alink\" href=\"" + UrlCentral + "\" target=\"_top\" style=\"background: #fff;\">Abrir na Central  <sk-icon font-icon=\"arrow-right\" class=\"ng-scope ng-isolate-scope\" style=\"color: rgb(221, 195, 64);margin: 0 0 0 1px;\"><span ng-disabled=\"!enabled\" ng-show=\"visible !== false\" ng-class=\"[prefixIcon, prefixIcon + '-' + fontIcon, skClass]\" class=\"glyphicons glyphicons-arrow-right\"></span></sk-icon></a><br>";
                    if ("F".equals(query.getString("GERAFIN"))) {
                        System.out.println("[GeraMovServico]: Alterando parceiro do financeiro...");
                        sql = new StringBuffer();
                        sql.append(" UPDATE TGFFIN SET CODPARC = " + query.getBigDecimal("CODFAB"));
                        sql.append(" WHERE NUNOTA = " + nuNotaGerado);
                        queryUpd.update(sql.toString());
                        System.out.println("[GeraMovServico]: Parceiro do financeiro do movimento " + nuNotaGerado + " eh " + query.getBigDecimal("CODFAB"));
                    }

                    if ("N".equals(query.getString("GERAFIN"))) {
                        System.out.println("[GeraMovServico]: Excluindo financeiro...");
                        sql = new StringBuffer();
                        sql.append(" DELETE TGFFIN WHERE NUNOTA = " + nuNotaGerado);
                        queryUpd.update(sql.toString());
                        System.out.println("[GeraMovServico]: Financeiro do movimento " + nuNotaGerado + " excluÃ\u00addo!");
                    }

                    System.out.println("[GeraMovServico]: Incluindo vendedores comissÃ£o...");
                    sql = new StringBuffer();
                    sql.append(" SELECT DISTINCT CODVEND FROM ( ");
                    sql.append(" SELECT NUMOS, CODPARCRESP AS CODVEND FROM AD_TCSITE ");
                    sql.append(" UNION ALL ");
                    sql.append(" SELECT NUMOS, CODPARCRESP2 AS CODVEND FROM AD_TCSITE ");
                    sql.append(" UNION ALL ");
                    sql.append(" SELECT NUMOS, CODPARCRESP3 AS CODVEND FROM AD_TCSITE ");
                    sql.append(" ) WHERE CODVEND IS NOT NULL AND NUMOS = " + numOs);
                    System.out.println(sql.toString());
                    query.nativeSelect(sql.toString());

                    while(query.next()) {
                        StringBuffer sql1 = new StringBuffer();
                        sql1.append(" INSERT INTO TGFCCM (NUNOTA, CODVEND) VALUES ( " + nuNotaGerado + ", " + query.getBigDecimal("CODVEND") + ")");
                        queryUpd.update(sql1.toString());
                    }
                } catch (Exception e) {
                    System.out.println("[GeraMovServico]: erro NUNOTA: " + nuNotaGerado + " -> " + e.getMessage());
                    if (nuNotaGerado.compareTo(new BigDecimal(0)) > 0) {
                        PersistentLocalEntity entity = dwfEntityFacade.findEntityByPrimaryKey("CabecalhoNota", nuNotaGerado);
                        entity.remove();
                    }

                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            this.msgErro = this.msgErro + e.getMessage();
            e.printStackTrace();
        } finally {
            query.close();
        }

    }

    public static List<String> converterStringParaLista(String tipoFaturamento) {
        List<String> tipoFatList = new ArrayList();
        if (tipoFaturamento != null && !tipoFaturamento.trim().isEmpty()) {
            String[] partes = tipoFaturamento.split(",");

            for(String parte : partes) {
                try {
                    tipoFatList.add(parte.trim());
                } catch (NumberFormatException var8) {
                    System.err.println("Valor invï¿½lido ignorado: " + parte);
                }
            }

            return tipoFatList;
        } else {
            return tipoFatList;
        }
    }

    private boolean validaDuplicidade(Registro line, ContextoAcao ctx) {
        BigDecimal numOs = (BigDecimal)line.getCampo("NUMOS");
        String sql = "SELECT COUNT(*) AS COUNT FROM TGFCAB WHERE AD_NUMOSSERVICO = " + numOs;
        QueryExecutor query = ctx.getQuery();

        try {
            query.nativeSelect(sql.toString());
            if (query.next()) {
                BigDecimal count = query.getBigDecimal("COUNT");
                System.out.println("[GeraMovServico] ValidaDuplicidade count ->" + count);
                return count.compareTo(BigDecimal.ZERO) <= 0;
            }
        } catch (Exception e) {
            this.msgErro = e.getMessage();
        }

        return false;
    }
}
