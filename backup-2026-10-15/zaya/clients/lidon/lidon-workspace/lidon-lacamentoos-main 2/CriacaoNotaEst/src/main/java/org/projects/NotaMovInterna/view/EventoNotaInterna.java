//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.projects.NotaMovInterna.view;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.ws.ServiceContext;
import org.jdom.Element;
import org.projects.NotaMovInterna.helper.CacHelperNota;
import org.projects.NotaMovInterna.service.CriarNota;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class EventoNotaInterna implements EventoProgramavelJava {
    public void beforeInsert(PersistenceEvent event) throws Exception {
    }

    public void beforeUpdate(PersistenceEvent event) throws Exception {
    }

    public void beforeDelete(PersistenceEvent event) throws Exception {
    }

    public void afterInsert(PersistenceEvent event) throws Exception {
        System.out.println("INICIANDO PROCESSO DE CRIACAO DE NOTA");
        JapeSession.SessionHandle hnd = null;
        DynamicVO vo = (DynamicVO)event.getVo();
        BigDecimal numOS = vo.asBigDecimal("NUMOS");
        if (numOS != null) {
            System.out.println("=== PROCESSANDO ITENS PARA A OS: " + numOS + " ===");
            CriarNota criarNota = new CriarNota(vo);
            criarNota.processarNotas();
        }

    }

    public void afterUpdate(PersistenceEvent event) throws Exception {
        System.out.println("===========INICIO afterUpdate===========");
        CacHelperNota cacHelper = new CacHelperNota();
        DynamicVO vo = (DynamicVO) event.getVo();
        BigDecimal nunotaInt = vo.asBigDecimal("NUNOTAINT");
        BigDecimal codEmp = vo.asBigDecimal("CODEMP");
        System.out.println("Nota Interna da OS: " + nunotaInt);
        System.out.println("Nota Interna da Cod Emp: " + codEmp);

        try {
            alterarEmpresaNota(nunotaInt, codEmp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void afterDelete(PersistenceEvent event) throws Exception {
    }

    public void beforeCommit(TransactionContext tranCtx) throws Exception {
    }

    public void alterarEmpresaNota(BigDecimal nunota, BigDecimal novaEmpresa) throws Exception {

        ServiceContext previous = ServiceContext.getCurrent();

        try {
            ServiceContext ctx = new ServiceContext((HttpServletRequest) null);
            ctx.setAutentication(AuthenticationInfo.getCurrent());
            ctx.setRequestBody(new Element("requestBody"));
            ctx.makeCurrent();

            Element nota = new Element("nota");
            Element cabecalho = new Element("cabecalho");

            Element nuNotaElem = new Element("NUNOTA");
            nuNotaElem.setText(nunota.toString());

            Element codEmpElem = new Element("CODEMP");
            codEmpElem.setText(novaEmpresa.toString());

            cabecalho.addContent(nuNotaElem);
            cabecalho.addContent(codEmpElem);
            nota.addContent(cabecalho);

            ctx.getRequestBody().addContent(nota);

            CACHelper cacHelper = new CACHelper();
            cacHelper.incluirAlterarCabecalhoNota(ctx);

        } finally {
            if (previous != null) previous.makeCurrent();
        }
    }
}
