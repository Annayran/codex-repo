//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package br.com.zaya.polyvox.apontamentoproducao.sankhya.schedulers;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.util.JapeSessionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.mgeprod.model.helper.ApontamentoHelper;
import br.com.sankhya.mgeprod.model.services.OperacaoProducaoSPBean;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.ws.ServiceContext;
import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.TimeUtils;
import com.sankhya.util.XMLUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import org.jdom.Element;

public class ApontamentoProducaoScheduler implements ScheduledAction {
    private final EntityFacade dwFacade = EntityFacadeFactory.getDWFFacade();
    public String msgErro = " ";
    public JdbcWrapper jdbc;

    public ApontamentoProducaoScheduler() {
        this.jdbc = this.dwFacade.getJdbcWrapper();
    }

    public void onTime(ScheduledActionContext ctx) {
        AuthenticationInfo auth = new AuthenticationInfo("SUP", BigDecimal.ZERO, BigDecimal.ZERO, 0);
        auth.makeCurrent();
        ServiceContext sctx = new ServiceContext((HttpServletRequest)null);
        sctx.setAutentication(auth);
        sctx.makeCurrent();
        JapeSessionContext.putProperty("usuario_logado", auth.getUserID());
        JapeSession.putProperty("usuario_logado", auth.getUserID());
        Element element = null;

        try {
            this.jdbc = this.dwFacade.getJdbcWrapper();
            this.jdbc.openSession();
            NativeSql qryApontamentos = new NativeSql(this.jdbc);
            ResultSet rsApontamentos = null;
            qryApontamentos.setReuseStatements(false);
            qryApontamentos.loadSql(ApontamentoProducaoScheduler.class, "queApo.sql");
            qryApontamentos.cleanParameters();
            rsApontamentos = qryApontamentos.executeQuery();

            try {
                System.out.println("ApontamentoProducaoSchedule.onTime()");

                while(rsApontamentos.next()) {

                    JapeSession.putProperty("br.com.sankhya.mgeprod.confirmado.ult.apontamento.mp.fixa", false);
                    JapeSession.putProperty("br.com.sankhya.mgeprod.popup.apresentado.ult.apontamento.mp.fixa", false);
                    JapeSession.putProperty("br.com.sankhya.mgeprod.gera.nota.producao", true);
                    JapeSession.putProperty("br.com.sankhya.mgeprod.is.ultimo.apontamento", false);
                    JapeSession.putProperty("br.com.sankhya.mgeProd.apontamento.liberaNroSerie", true);

                    JapeWrapper apontamentoDAO = JapeFactory.dao("CabecalhoApontamento");
                    DynamicVO apontamentoVO = null;
                    BigDecimal idAtv = rsApontamentos.getBigDecimal("IDIATV");
                    BigDecimal qtdApontada = rsApontamentos.getBigDecimal("QTDAPONTADA");
                    BigDecimal seqVol = rsApontamentos.getBigDecimal("SEQVOL");
                    BigDecimal idProc = rsApontamentos.getBigDecimal("IDIPROC");

                    System.out.println("idAtv: " + idAtv);
                    System.out.println("qtdApontada: " + qtdApontada);
                    System.out.println("seqVol: " + seqVol);
                    System.out.println("idProc: " + idProc);

                    ApontamentoHelper apt = new ApontamentoHelper(this.jdbc);
                    element = apt.criarApontamentosAtividade(AuthenticationInfo.getCurrent().getUserID(), idAtv);
                    apontamentoVO = apontamentoDAO.findByPK(new Object[]{element.getAttribute("NUAPO").getLongValue()});
                    BigDecimal nuapo = new BigDecimal(element.getAttribute("NUAPO").getLongValue());
                    BigDecimal qtdParaApontar = seqVol != null ? BigDecimal.ONE : qtdApontada;
                    this.alteraQtdApontada(element, qtdParaApontar);
                    this.alteraSeqVolApontamento(element, seqVol);
                    apt.validarConfirmacaoApontamento(apontamentoVO, true);

                    System.out.println("NUAPO: " + nuapo);

                    ConfirmarApontamento(nuapo, idAtv, BigDecimal.ZERO, seqVol);
                }

                rsApontamentos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rsApontamentos.close();
            }
        } catch (Exception e) {
            System.out.println("msgErrorsApontamentos" + e.getMessage());
            this.msgErro = e.getMessage();
            e.getStackTrace();
        } finally {
            JdbcWrapper.closeSession(this.jdbc);
        }

    }

    private void alteraSeqVolApontamento(Element apontamento, BigDecimal seqVol) throws Exception {
        if (seqVol == null) {
            return;
        }

        System.out.println("ApontamentoProducaoSchedule.alteraSeqVolApontamento()");
        JapeWrapper dao = JapeFactory.dao("ApontamentoPA");

        try {
            for(DynamicVO apontamentoVO : dao.find("this.NUAPO = ?", new Object[]{apontamento.getAttribute("NUAPO").getLongValue()})) {
                FluidUpdateVO entityUpd = dao.prepareToUpdate(apontamentoVO);
                entityUpd.set("AD_SEQVOL", seqVol);
                entityUpd.update();

                System.out.println("fim alteraSeqVolApontamento");
            }
        } catch (Exception e) {
            String msg = String.format("[NUAPO: %s] Nao foi possivel alterar o volume do apontamento.", apontamento.getAttribute("NUAPO").getLongValue());

            System.out.println("msgErroalteraSeqVolApontamento" + e.getMessage());

            this.msgErro = e.getMessage();
            throw new Exception(msg, e);
        }
    }

    private void alteraQtdApontada(Element apontamento, BigDecimal qtdApo) throws Exception {
        System.out.println("ApontamentoProducaoSchedule.alteraQtdApontada()");
        JapeWrapper dao = JapeFactory.dao("ApontamentoPA");
        Collection<DynamicVO> apontamentosVO = null;

        try {
            for(DynamicVO apontamentoVO : dao.find("this.NUAPO = ?", new Object[]{apontamento.getAttribute("NUAPO").getLongValue()})) {
                FluidUpdateVO entityUpd = dao.prepareToUpdate(apontamentoVO);
                entityUpd.set("QTDAPONTADA", qtdApo);
                entityUpd.update();

                System.out.println("fim alteraQtdApontada");
            }

        } catch (Exception e) {
            String msg = String.format("[NUAPO: %s] Nfoi possalterar quantidade Apontada.", apontamento.getAttribute("NUAPO").getLongValue());

            System.out.println("msgErroalteraQtdApontada" + e.getMessage());

            this.msgErro = e.getMessage();
            throw new Exception(msg, e);
        }
    }

    public static void ConfirmarApontamento(BigDecimal nuApo, BigDecimal idIAtv, BigDecimal codUsu, BigDecimal seqVol) throws Exception {
        EntityFacade entityFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = entityFacade.getJdbcWrapper();
        NativeSql nativeSql = new NativeSql(jdbc);

        try {
            Element params = new Element("params");
            XMLUtils.addAttributeElement(params, "NUAPO", nuApo);
            XMLUtils.addAttributeElement(params, "IDIATV", idIAtv);
            XMLUtils.addAttributeElement(params, "ACEITARQTDMAIOR", false);
            XMLUtils.addAttributeElement(params, "RESPOSTA_ULTIMO_APONTAMENTO", false);
            XMLUtils.addAttributeElement(params, "RESPOSTA_SERIE_LIBERADO_PERDA", false);
            XMLUtils.addAttributeElement(params, "RESPOSTA_SERIE_LIBERADO", true);
            XMLUtils.addAttributeElement(params, "RESPOSTA_SERIE_LIBERADO_MP", false);
            ServiceContext ctx = ServiceContext.getCurrent();
            Element requestBody = new Element("requestBody");
            ctx.setRequestBody(requestBody);
            ctx.getRequestBody().addContent(params);
            OperacaoProducaoSPBean operacaoProducaoSP = new OperacaoProducaoSPBean();
            operacaoProducaoSP.confirmarApontamento(ctx);
            System.out.println("nuApo: " + nuApo);
            System.out.println("seqVol: " + seqVol);
            AtualizarSerie(nuApo, seqVol);
        } catch (MGEModelException e) {

            System.out.println("ErroConfirmarApontamento (MGEModelException2):" + e.getMessage());

            nativeSql.executeUpdate(" UPDATE TPRAPO SET OBSERVACAO = 'Apontamento com erro, favor verificar. " + e.getMessage() + " ' WHERE NUAPO = " + nuApo);
            e.printStackTrace();
        } catch (Exception e) {

            System.out.println("ErroConfirmarApontamento (Exception):" + e.getMessage());

            nativeSql.executeUpdate(" UPDATE TPRAPO SET OBSERVACAO = 'Apontamento com erro, favor verificar. " + e.getMessage() + " ' WHERE NUAPO = " + nuApo);
            e.toString();
            e.printStackTrace();
        }

    }

    private static void AtualizarSerie(BigDecimal nuApo, BigDecimal seqVol) throws Exception {
        EntityFacade entityFacade = EntityFacadeFactory.getDWFFacade();
        JdbcWrapper jdbc = null;
        NativeSql nativeSql = null;
        ResultSet queryNota = null;
        ResultSet querySerie = null;
        StringBuilder sqlNota = new StringBuilder();

        try {
            jdbc = entityFacade.getJdbcWrapper();
            nativeSql = new NativeSql(jdbc);
            sqlNota.append(" SELECT NUNOTA ");
            sqlNota.append("   FROM TPRAPF    ");
            sqlNota.append("  WHERE NUAPO = " + nuApo);
            queryNota = nativeSql.executeQuery(sqlNota.toString());
            queryNota.next();
            BigDecimal nunota = BigDecimalUtil.getValueOrZero(queryNota.getBigDecimal("NUNOTA"));

            System.out.println("sqlNota: " + sqlNota.toString());
            System.out.println("TPRAPF - NUNOTA: " + nunota);

            StringBuilder sqlSerie = new StringBuilder();
            sqlSerie.append(" SELECT IVC.CODPROD, IVC.CODBARRA AS SERIE, IVC.SEQVOL, ATV.IDIPROC ");
            sqlSerie.append(" FROM TPRIATV ATV, TPRAPO APO, tgfcab cab, tgfcon2 con, tgfivc ivc, TGFPRO PRO ");
            sqlSerie.append(" WHERE atv.idiatv = APO.IDIATV ");
            sqlSerie.append(" AND ATV.IDIPROC = CAB.IDIPROC ");
            sqlSerie.append(" AND cab.nunota = con.nunotaorig ");
            sqlSerie.append(" and con.nuconf = ivc.nuconf ");
            sqlSerie.append(" and ivc.codprod = pro.codprod ");
            sqlSerie.append(" and con.status <> 'R' ");
            sqlSerie.append(" AND APO.NUAPO = " + nuApo);
            sqlSerie.append(" AND IVC.SEQVOL = " + seqVol);
            sqlSerie.append(" ORDER BY IVC.CODBARRA ");
            querySerie = nativeSql.executeQuery(sqlSerie.toString());
            System.out.println("querySerie: " + sqlSerie.toString());

            while(querySerie.next()) {
                System.out.println("Entrou no While: " + nuApo + " SeqVol: " + seqVol);
                String serie = querySerie.getString("SERIE");
                BigDecimal idiproc = BigDecimalUtil.getValueOrZero(querySerie.getBigDecimal("IDIPROC"));
                BigDecimal codprod = BigDecimalUtil.getValueOrZero(querySerie.getBigDecimal("CODPROD"));
                JapeWrapper paDAO = JapeFactory.dao("SerieProduto");
                ((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)((FluidCreateVO)paDAO.create().set("NUNOTA", nunota)).set("SEQUENCIA", BigDecimal.ONE)).set("CODPROD", codprod)).set("SERIE", serie)).set("ATUALESTOQUE", BigDecimal.ONE)).set("CODUSU", BigDecimal.ZERO)).set("DTALTER", TimeUtils.getNow())).save();

                System.out.println("Depois do insert na SERIE_PRODUTO");

                nativeSql.executeUpdate(" UPDATE TPRSERPA SET LIBERADO='S', NUAPO = " + nuApo + "  WHERE IDIPROC = " + idiproc + " AND CODPRODPA = " + codprod + " AND SERIEPA = " + serie);

                System.out.println("Depois do update da TPRSERPA");
            }
        } catch (MGEModelException e) {
            nativeSql.executeUpdate("UPDATE TPRAPO SET OBSERVACAO = 'Apontamento com erro, favor verificar.' WHERE NUAPO = " + nuApo);

            System.out.println("ErroAtualizarSerie(MGEModelException1):" + e.getMessage());

            e.toString();
            e.printStackTrace();
        } catch (Exception e) {
            nativeSql.executeUpdate("UPDATE TPRAPO SET OBSERVACAO = 'Apontamento com erro, favor verificar.' WHERE NUAPO = " + nuApo);

            System.out.println("ErroAtualizarSerie(Exception):" + e.getMessage());

            e.toString();
            e.printStackTrace();
        } finally {
            NativeSql.releaseResources(nativeSql);
            JdbcUtils.closeResultSet(queryNota);
            JdbcUtils.closeResultSet(querySerie);
            JdbcWrapper.closeSession(jdbc);
        }

    }
}
