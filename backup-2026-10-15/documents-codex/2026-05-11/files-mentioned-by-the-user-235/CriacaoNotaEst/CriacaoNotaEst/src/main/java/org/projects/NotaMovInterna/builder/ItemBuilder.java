//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.projects.NotaMovInterna.builder;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import java.math.BigDecimal;
import java.util.List;
import org.projects.NotaMovInterna.model.ItemNotaOS;
import org.projects.NotaMovInterna.utils.SankhyaUtils;

public class ItemBuilder {
    private final EntityFacade dwf = EntityFacadeFactory.getDWFFacade();
    private final AuthenticationInfo auth = AuthenticationInfo.getCurrent();

    public ItemBuilder() {
    }

    public void criarItensNotaPorOS(EntityFacade dwfFacade, List<ItemNotaOS> itensOS, BigDecimal nuNotaDestino, DynamicVO notaDestino) throws Exception {
        System.out.println("=== criarItensNotaPorOS ===");
        System.out.println("NUNOTA DESTINO: " + nuNotaDestino);
        System.out.println("Itens recebidos: " + itensOS.size());
        if (nuNotaDestino == null) {
            throw new IllegalArgumentException("NUNOTA destino nao informado para criacao dos itens da nota.");
        } else if (notaDestino == null) {
            throw new IllegalArgumentException("Nota destino nao informada para criacao dos itens da nota.");
        } else if (itensOS.isEmpty()) {
            System.out.println("Nenhum item para inserir");
        } else {
            for(ItemNotaOS itemOS : itensOS) {
                if (itemOS == null) {
                    System.out.println("Item OS nulo ignorado");
                    continue;
                }

                if (itemOS.getNUMITEM() == null || itemOS.getQTDNEG() == null || itemOS.getCODVOL() == null || itemOS.getCODVOL().trim().isEmpty()) {
                    throw new IllegalStateException("Item OS incompleto para insercao na nota. NUNOTA=" + nuNotaDestino + ", NUMITEM=" + itemOS.getNUMITEM() + ", QTDNEG=" + itemOS.getQTDNEG() + ", CODVOL=" + itemOS.getCODVOL());
                }

                System.out.println("Inserindo item OS:");
                System.out.println("PROD: " + itemOS.getNUMITEM() + " QTD: " + itemOS.getQTDNEG());
                DynamicVO itemVO = (DynamicVO)dwfFacade.getDefaultValueObjectInstance("ItemNota");
                itemVO.setProperty("NUNOTA", nuNotaDestino);
                itemVO.setProperty("CODPROD", itemOS.getNUMITEM());
                itemVO.setProperty("QTDNEG", itemOS.getQTDNEG());
                itemVO.setProperty("VLRUNIT", itemOS.getVALORSERVICO());
                itemVO.setProperty("CODVOL", itemOS.getCODVOL());
                itemVO.setProperty("CODEMP", notaDestino.asBigDecimal("CODEMP"));
                SankhyaUtils.insereItem(dwfFacade, itemVO, this.auth);
                System.out.println("Item inserido com sucesso");
            }
        }

    }
}
