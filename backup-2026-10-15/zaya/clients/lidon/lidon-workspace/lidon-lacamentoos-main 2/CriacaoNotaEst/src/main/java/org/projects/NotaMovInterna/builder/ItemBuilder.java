//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
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

    public void criarItensNotaPorOS(EntityFacade dwfFacade, List<ItemNotaOS> itensOS, BigDecimal nuNotaDestino, DynamicVO notaDestino) throws Exception {
        System.out.println("=== criarItensNotaPorOS ===");
        System.out.println("NUNOTA DESTINO: " + nuNotaDestino);
        System.out.println("Itens recebidos: " + itensOS.size());
        if (itensOS.isEmpty()) {
            System.out.println("Nenhum item para inserir");
        } else {
            for(ItemNotaOS itemOS : itensOS) {
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
