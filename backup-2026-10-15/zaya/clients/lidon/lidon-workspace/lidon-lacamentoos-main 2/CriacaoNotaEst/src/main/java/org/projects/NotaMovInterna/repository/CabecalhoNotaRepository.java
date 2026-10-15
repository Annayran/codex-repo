//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.repository;

import br.com.sankhya.jape.vo.DynamicVO;
import java.math.BigDecimal;
import org.projects.NotaMovInterna.model.CabecalhoNotaOS;

public class CabecalhoNotaRepository {
    public CabecalhoNotaOS buscarCabecalho(DynamicVO vO) throws Exception {
        BigDecimal numOs = vO.asBigDecimal("NUMOS");
        System.out.println("=== buscarCabecalho (JAPE) ===");
        System.out.println("NUMOS recebido: " + numOs);
        System.out.println("Campos da OS:");
        System.out.println("NUMOS: " + vO.asBigDecimal("NUMOS"));
        System.out.println("CODPARC: " + vO.asBigDecimal("CODPARC"));
        System.out.println("DTABERTURA: " + vO.asTimestamp("DTABERTURA"));
        System.out.println("CODEMP: " + vO.asBigDecimal("CODEMP"));
        System.out.println("NUNOTA ORIGEM: " + vO.asBigDecimal("NUNOTA"));
        CabecalhoNotaOS m = new CabecalhoNotaOS();
        m.setNUMOS(vO.asBigDecimal("NUMOS"));
        m.setCODPARC(vO.asBigDecimal("CODPARC"));
        m.setDTABERTURA(vO.asTimestamp("DTABERTURA"));
        m.setCODCENCUS(vO.asBigDecimal("CODCENCUS"));
        m.setCODNAT(vO.asBigDecimal("CODNAT"));
        m.setCODEMP(vO.asBigDecimal("CODEMP"));
        m.setSERIE(vO.asString("SERIE"));
        m.setNUNOTA(vO.asBigDecimal("NUNOTA"));
        m.setCODTIPOPER(BigDecimal.valueOf(1813L));
        return m;
    }
}
