//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.repository;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.projects.NotaMovInterna.model.ItemNotaOS;

public class ItemNotaRepository {
    public ItemNotaRepository() {
    }

    public List<ItemNotaOS> buscarItensPorOS(BigDecimal numOS) throws Exception {
        System.out.println("\n==============================");
        System.out.println("INÍCIO buscarItensPorOS");
        System.out.println("numOS recebido: " + numOS);
        System.out.println("numOS é null? " + (numOS == null));
        System.out.println("==============================");
        if (numOS == null) {
            throw new NullPointerException("numOS está NULL ao entrar no repository");
        } else {
            List<DynamicVO> itensVO = null;

            try {
                System.out.println("Buscando registros em AD_TCSPRO...");
                itensVO = (List)JapeFactory.dao("AD_TCSPRO").find("this.NUMOS = ?", new Object[]{numOS});
                System.out.println("DAO retornou lista? " + (itensVO != null));
                if (itensVO == null) {
                    System.out.println("❌ itensVO é NULL (isso NÃO deveria acontecer)");
                    return new ArrayList();
                }

                System.out.println("Quantidade de registros encontrados: " + itensVO.size());
            } catch (Exception e) {
                System.out.println("❌ ERRO ao executar DAO.find()");
                e.printStackTrace();
                throw e;
            }

            List<ItemNotaOS> lista = new ArrayList();
            int idx = 0;

            for(DynamicVO vo : itensVO) {
                System.out.println("\n--- Registro AD_TCSPRO [" + idx + "] ---");
                System.out.println("VO é null? " + (vo == null));
                if (vo == null) {
                    System.out.println("❌ VO NULL — pulando registro");
                    ++idx;
                } else {
                    System.out.println("VO completo: " + vo);
                    System.out.println("CODPROD: " + vo.asBigDecimal("CODPROD"));
                    System.out.println("QTDNEG: " + vo.asBigDecimal("QTDNEG"));
                    System.out.println("VALOR: " + vo.asBigDecimal("VALOR"));
                    System.out.println("CODVOL: " + vo.asString("CODVOL"));
                    System.out.println("CODEMP: " + vo.asBigDecimal("CODEMP"));
                    ItemNotaOS it = new ItemNotaOS();
                    it.setNUMITEM(vo.asBigDecimal("CODPROD"));
                    it.setQTDNEG(vo.asBigDecimal("QTDNEG"));
                    it.setVALORSERVICO(vo.asBigDecimal("VALOR"));
                    it.setCODVOL(vo.asString("CODVOL"));
                    it.setCODEMP(vo.asBigDecimal("CODEMP"));
                    System.out.println("ItemNotaOS montado:");
                    System.out.println(" - NUMITEM: " + it.getNUMITEM());
                    System.out.println(" - QTDNEG: " + it.getQTDNEG());
                    System.out.println(" - VALORSERVICO: " + it.getVALORSERVICO());
                    System.out.println(" - CODVOL: " + it.getCODVOL());
                    System.out.println(" - CODEMP: " + it.getCODEMP());
                    lista.add(it);
                    ++idx;
                }
            }

            System.out.println("\n==============================");
            System.out.println("FIM buscarItensPorOS");
            System.out.println("Total de itens retornados: " + lista.size());
            System.out.println("==============================\n");
            return lista;
        }
    }
}
