//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.builder;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.bmp.PersistentLocalEntity;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class CabecalhoBuilder {
    public CabecalhoBuilder() {
    }

    public DynamicVO criarNotaPorModelo(EntityFacade dwfFacade, BigDecimal nuNotaModelo, BigDecimal codemp, BigDecimal nuOs, BigDecimal codParc) throws Exception {
        System.out.println("=== criarNotaPorModelo ===");
        System.out.println("nuNotaModelo: " + nuNotaModelo);
        if (codemp == null) {
            throw new IllegalArgumentException("CODEMP nao informado para criacao da nota interna.");
        } else if (codParc == null) {
            throw new IllegalArgumentException("CODPARC nao informado para criacao da nota interna.");
        } else {
        PersistentLocalEntity notaModeloEntity = dwfFacade.findEntityByPrimaryKey("CabecalhoNota", new Object[]{nuNotaModelo});
        System.out.println("notaModeloEntity: " + notaModeloEntity);
        if (notaModeloEntity == null) {
            throw new NullPointerException("Nota modelo NÃƒO encontrada: " + nuNotaModelo);
        } else {
            DynamicVO modeloVO = (DynamicVO)notaModeloEntity.getValueObject();
            System.out.println("modeloVO: " + modeloVO);
            DynamicVO clonedVO = modeloVO.buildClone();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            clonedVO.setPrimaryKey((Object)null);
            clonedVO.clearReferences();
            clonedVO.setProperty("NUNOTA", (Object)null);
            clonedVO.setProperty("CODEMP", codemp);
            clonedVO.setProperty("CODPARC", codParc);
            clonedVO.setProperty("CODEMPNEGOC", codemp);
            clonedVO.setProperty("CODTIPOPER", modeloVO.asBigDecimal("CODTIPOPER"));
            clonedVO.setProperty("DHTIPOPER", modeloVO.asTimestamp("DHTIPOPER"));
            clonedVO.setProperty("CODCENCUS", modeloVO.asBigDecimal("CODCENCUS"));
            clonedVO.setProperty("AD_NUMOSOFICINA", nuOs);
            clonedVO.setProperty("OBSERVACAO", nuOs != null ? nuOs.toPlainString() : null);
            clonedVO.setProperty("DTNEG", now);
            clonedVO.setProperty("DTENTSAI", now);
            clonedVO.setProperty("DTFATUR", now);
            clonedVO.setProperty("DTMOV", now);
            clonedVO.setProperty("DTALTER", now);
            clonedVO.setProperty("STATUSNOTA", "A");
            clonedVO.setProperty("NUMNOTA", BigDecimal.ZERO);
            clonedVO.setProperty("CHAVENFE", (Object)null);
            clonedVO.setProperty("SERIENOTA", (Object)null);
            clonedVO.setProperty("TIPMOV", "J");
            clonedVO.setProperty("ORDEMCARGA", (Object)null);
            System.out.println("clonedVO criado");
            System.out.println("Antes do createEntity, clonedVO = " + clonedVO);
            PersistentLocalEntity created = dwfFacade.createEntity("CabecalhoNota", (EntityVO)clonedVO);
            System.out.println("Entity criada: " + created);
            System.out.println("VO criado: " + created.getValueObject());
            System.out.println("NUNOTA gerado: " + ((DynamicVO)created.getValueObject()).asBigDecimal("NUNOTA"));
            return (DynamicVO)created.getValueObject();
        }
        }
    }

    public DynamicVO obterModeloCabecalho() throws Exception {
        DynamicVO modelo = JapeFactory.dao("CabecalhoNota").findOne("this.NUNOTA = ?", new Object[]{64120});
        if (modelo == null) {
            throw new Exception("Modelo de cabeÃ§alho nÃ£o encontrado ");
        } else {
            return modelo;
        }
    }
}
