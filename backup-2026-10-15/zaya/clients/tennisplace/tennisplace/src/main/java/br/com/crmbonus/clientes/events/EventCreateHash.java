package br.com.crmbonus.clientes.events;

import br.com.crmbonus.clientes.integration.client.CrmBonusClient;
import br.com.crmbonus.clientes.service.RequestHashService;
import br.com.crmbonus.clientes.service.impl.CrmBonusClientImpl;
import br.com.crmbonus.clientes.service.impl.RequestHashServiceImpl;
import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;

import java.math.BigDecimal;

public class EventCreateHash implements EventoProgramavelJava {
    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {

    }

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {

    }

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {

    }
    private final RequestHashService requestHashService = new RequestHashServiceImpl();
    private final CrmBonusClient crmClient = new CrmBonusClientImpl();

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {

        DynamicVO vo = (DynamicVO)event.getVo();

        BigDecimal nunota = vo.asBigDecimal("NUNOTA");
        String hash = requestHashService.generateHash(nunota);

        JapeWrapper cabDAO = JapeFactory.dao("CabecalhoNota");

        DynamicVO cabVO = cabDAO.findByPK(nunota);

        if (cabVO != null) {
            FluidUpdateVO fluid = cabDAO.prepareToUpdate(cabVO);
            fluid.set("AD_REQHASH", hash);
            fluid.update();
        }

        System.out.println("==== EVENTO AFTER INSERT TGFCAB ====");


    }



    @Override
    public void afterUpdate(PersistenceEvent event) throws Exception {

    }

    @Override
    public void afterDelete(PersistenceEvent event) throws Exception {

    }

    @Override
    public void beforeCommit(TransactionContext tranCtx) throws Exception {

    }
}
