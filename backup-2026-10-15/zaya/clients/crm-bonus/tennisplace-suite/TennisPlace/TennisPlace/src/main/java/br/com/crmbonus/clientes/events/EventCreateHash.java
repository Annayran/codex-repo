package br.com.crmbonus.clientes.events;

import br.com.crmbonus.clientes.service.RequestHashService;
import br.com.crmbonus.clientes.service.impl.RequestHashServiceImpl;
import br.com.crmbonus.common.integration.EmpresaCredentialsValidationService;
import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.packagerepository.client.infrastructure.util.StringUtils;

import java.math.BigDecimal;

public class EventCreateHash implements EventoProgramavelJava {

    private final RequestHashService requestHashService = new RequestHashServiceImpl();
    private final EmpresaCredentialsValidationService credentialsValidationService = new EmpresaCredentialsValidationService();

    @Override
    public void beforeInsert(PersistenceEvent event) throws Exception {

    }

    @Override
    public void beforeUpdate(PersistenceEvent event) throws Exception {

    }

    @Override
    public void beforeDelete(PersistenceEvent event) throws Exception {

    }

    @Override
    public void afterInsert(PersistenceEvent event) throws Exception {

        DynamicVO vo = (DynamicVO) event.getVo();

        BigDecimal nunota = vo.asBigDecimal("NUNOTA");

        JapeWrapper cabDAO = JapeFactory.dao("CabecalhoNota");

        DynamicVO cabVO = cabDAO.findByPK(nunota);

        if (cabVO == null) {
            throw new Exception("Não foi possível localizar o cabeçalho da nota " + nunota + ".");
        }

        BigDecimal codEmp = cabVO.asBigDecimal("CODEMP");
        System.out.println("[CRM-BONUS] Validando credenciais da empresa para a NUNOTA=" + nunota + ", CODEMP=" + codEmp);

        System.out.println("[CRM-BONUS] Credenciais da empresa validadas com sucesso para CODEMP=" + codEmp);
        JapeWrapper empDAO = JapeFactory.dao(DynamicEntityNames.EMPRESA);
        DynamicVO empVO = empDAO.findByPK(codEmp);
        String crmUsername = empVO.asString("AD_CRM_USERNAME");
        String crmPassword = empVO.asString("AD_CRM_PASSWORD");
        System.out.println("[CRM-BONUS] crmUsername: " + crmUsername);

        if (StringUtils.isEmpty(crmUsername) || StringUtils.isEmpty(crmPassword)) {
            System.out.println("[CRM-BONUS] CRM Username não configurado para a empresa " + codEmp + ". Ignorando processamento.");
            return;
        }
        String hash = requestHashService.generateHash(nunota);

        FluidUpdateVO fluid = cabDAO.prepareToUpdate(cabVO);
        fluid.set("AD_REQHASH", hash);
        fluid.update();

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
