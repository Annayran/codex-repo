//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.view;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.ws.ServiceContext;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jdom.Element;
import org.projects.NotaMovInterna.service.CriarNota;

public class EventoNotaInterna implements EventoProgramavelJava {
    private static final String LOG_PREFIX = "[NotaMovInterna][EventoNotaInterna] ";

    public EventoNotaInterna() {
    }

    public void beforeInsert(PersistenceEvent event) throws Exception {
    }

    public void beforeUpdate(PersistenceEvent event) throws Exception {
    }

    public void beforeDelete(PersistenceEvent event) throws Exception {
    }

    public void afterInsert(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO)event.getVo();
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        this.log("afterInsert - inicio, NUMOS=" + numOS);
        if (numOS != null) {
            CriarNota criarNota = new CriarNota(vo);
            BigDecimal nunotaInt = criarNota.processarNotas();
            this.log("afterInsert - nota interna criada, NUMOS=" + numOS + ", NUNOTAINT=" + nunotaInt);
        } else {
            this.log("afterInsert - NUMOS nao informado, processo ignorado");
        }

    }

    public void afterUpdate(PersistenceEvent event) throws Exception {
        DynamicVO vo = (DynamicVO)event.getVo();
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        BigDecimal nunotaInt = vo.asBigDecimal("NUNOTAINT");
        BigDecimal codEmp = vo.asBigDecimal("CODEMP");
        this.log("afterUpdate - inicio, NUMOS=" + numOS + ", NUNOTAINT=" + nunotaInt + ", CODEMP=" + codEmp);
        if (!this.houveAlteracaoEmpresa(event, codEmp)) {
            this.log("afterUpdate - CODEMP sem alteracao real para NUMOS=" + numOS + ", retorno sem sincronizar nota interna");
            return;
        }

        if (nunotaInt == null) {
            this.log("afterUpdate - NUNOTAINT nula para NUMOS=" + numOS + ", alteracao de empresa ignorada");
        } else if (codEmp == null) {
            this.log("afterUpdate - CODEMP nula para NUMOS=" + numOS + ", alteracao de empresa ignorada");
        } else if (this.empresaJaEstaSincronizada(nunotaInt, codEmp)) {
            this.log("afterUpdate - empresa ja sincronizada, sem alteracao para NUMOS=" + numOS + ", NUNOTAINT=" + nunotaInt + ", CODEMP=" + codEmp);
        } else {
            try {
                this.log("afterUpdate - empresa divergente identificada, sincronizando nota interna. NUMOS=" + numOS + ", NUNOTAINT=" + nunotaInt + ", CODEMP=" + codEmp);
                this.alterarEmpresaNota(nunotaInt, codEmp);
            } catch (Exception e) {
                if (this.isErroEstoqueInsuficiente(e)) {
                    String codLocais = this.buscarCodLocaisDaNota(nunotaInt);
                    String msg = "Estoque insuficiente para alterar a empresa da nota interna. NUMOS=" + numOS + ", NUNOTAINT=" + nunotaInt + ", CODEMP=" + codEmp + ", CODLOCAL=" + codLocais + ".";
                    this.log("afterUpdate - " + msg + " Erro original: " + e.getMessage());
                    throw new RuntimeException(msg, e);
                } else {
                    throw new RuntimeException("Erro ao alterar empresa da nota interna. NUMOS=" + numOS + ", NUNOTAINT=" + nunotaInt, e);
                }
            }
        }
    }

    private boolean houveAlteracaoEmpresa(PersistenceEvent event, BigDecimal novoCodEmp) {
        try {
            DynamicVO oldVO = (DynamicVO)event.getOldVO();
            if (oldVO == null) {
                this.log("houveAlteracaoEmpresa - oldVO indisponivel, mantendo fluxo para validacao");
                return true;
            }

            BigDecimal codEmpAnterior = oldVO.asBigDecimal("CODEMP");
            if (codEmpAnterior == null && novoCodEmp == null) {
                return false;
            }

            if (codEmpAnterior == null || novoCodEmp == null) {
                return true;
            }

            return codEmpAnterior.compareTo(novoCodEmp) != 0;
        } catch (Exception e) {
            this.log("houveAlteracaoEmpresa - falha ao comparar CODEMP antigo/novo, mantendo fluxo. Erro=" + e.getMessage());
            return true;
        }
    }

    private boolean empresaJaEstaSincronizada(BigDecimal nunota, BigDecimal codEmpOs) {
        if (nunota == null || codEmpOs == null) {
            return false;
        } else {
            try {
                DynamicVO notaAtual = (DynamicVO)JapeFactory.dao("CabecalhoNota").findByPK(new Object[]{nunota});
                if (notaAtual == null) {
                    this.log("empresaJaEstaSincronizada - nota interna nao encontrada para NUNOTA=" + nunota + ", seguindo com tentativa de alteracao");
                    return false;
                } else {
                    BigDecimal codEmpNota = notaAtual.asBigDecimal("CODEMP");
                    BigDecimal codEmpNegocNota = notaAtual.asBigDecimal("CODEMPNEGOC");
                    this.log("empresaJaEstaSincronizada - NUNOTA=" + nunota + ", CODEMP_OS=" + codEmpOs + ", CODEMP_NOTA=" + codEmpNota + ", CODEMPNEGOC_NOTA=" + codEmpNegocNota);
                    return codEmpNota != null && codEmpNegocNota != null && codEmpOs.compareTo(codEmpNota) == 0 && codEmpOs.compareTo(codEmpNegocNota) == 0;
                }
            } catch (Exception e) {
                this.log("empresaJaEstaSincronizada - falha ao consultar empresa da nota, mantendo alteracao. NUNOTA=" + nunota + ", erro=" + e.getMessage());
                return false;
            }
        }
    }

    private void log(String message) {
        System.out.println(LOG_PREFIX + message);
    }

    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    public void beforeCommit(TransactionContext tranCtx) throws Exception {
    }

    public void alterarEmpresaNota(BigDecimal nunota, BigDecimal novaEmpresa) throws Exception {
        ServiceContext previous = ServiceContext.getCurrent();

        try {
            ServiceContext ctx = new ServiceContext((HttpServletRequest)null);
            ctx.setAutentication(AuthenticationInfo.getCurrent());
            ctx.setRequestBody(new Element("requestBody"));
            ctx.makeCurrent();
            Element nota = new Element("nota");
            Element cabecalho = new Element("cabecalho");
            Element nuNotaElem = new Element("NUNOTA");
            nuNotaElem.setText(nunota.toString());
            Element codEmpElem = new Element("CODEMP");
            codEmpElem.setText(novaEmpresa.toString());
            Element codEmpNegocElem = new Element("CODEMPNEGOC");
            codEmpNegocElem.setText(novaEmpresa.toString());
            cabecalho.addContent(nuNotaElem);
            cabecalho.addContent(codEmpElem);
            cabecalho.addContent(codEmpNegocElem);
            nota.addContent(cabecalho);
            ctx.getRequestBody().addContent(nota);
            System.out.println("[NotaMovInterna][EventoNotaInterna] alterarEmpresaNota - atualizando CODEMP/CODEMPNEGOC da NUNOTA=" + nunota + " para " + novaEmpresa);
            CACHelper cacHelper = new CACHelper();
            cacHelper.incluirAlterarCabecalhoNota(ctx);
        } finally {
            if (previous != null) {
                previous.makeCurrent();
            }

        }

    }

    private boolean isErroEstoqueInsuficiente(Throwable throwable) {
        for(Throwable cursor = throwable; cursor != null; cursor = cursor.getCause()) {
            String message = cursor.getMessage();
            if (message != null) {
                String msgUpper = message.toUpperCase();
                if (msgUpper.contains("CORE_E04794") || msgUpper.contains("ESTOQUE INSUFICIENTE")) {
                    return true;
                }
            }
        }

        return false;
    }

    private String buscarCodLocaisDaNota(BigDecimal nunota) {
        if (nunota == null) {
            return "NAO_IDENTIFICADO";
        } else {
            JdbcWrapper jdbc = null;
            NativeSql sql = null;
            ResultSet rs = null;
            List<String> locais = new ArrayList<String>();

            try {
                jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
                jdbc.openSession();
                sql = new NativeSql(jdbc);
                sql.appendSql("SELECT DISTINCT CODLOCALORIG CODLOCAL FROM TGFITE WHERE NUNOTA = :NUNOTA ORDER BY CODLOCALORIG");
                sql.setNamedParameter("NUNOTA", nunota);
                rs = sql.executeQuery();

                while(rs.next()) {
                    BigDecimal codLocal = rs.getBigDecimal("CODLOCAL");
                    if (codLocal != null) {
                        locais.add(codLocal.toPlainString());
                    }
                }
            } catch (Exception e) {
                System.out.println("[NotaMovInterna][EventoNotaInterna] buscarCodLocaisDaNota - falha ao buscar CODLOCAL da NUNOTA=" + nunota + ": " + e.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception var14) {
                }

                JdbcWrapper.closeSession(jdbc);
            }

            if (locais.isEmpty()) {
                return "NAO_IDENTIFICADO";
            } else {
                StringBuilder sb = new StringBuilder();

                for(int i = 0; i < locais.size(); ++i) {
                    if (i > 0) {
                        sb.append(",");
                    }

                    sb.append(locais.get(i));
                }

                return sb.toString();
            }
        }
    }
}
