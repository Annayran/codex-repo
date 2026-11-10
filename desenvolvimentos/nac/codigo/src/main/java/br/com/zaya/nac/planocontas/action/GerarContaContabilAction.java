package br.com.zaya.nac.planocontas.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.zaya.nac.planocontas.controller.GerarContaContabilController;
import br.com.zaya.nac.planocontas.repository.sankhya.SankhyaParceiroRepository;
import br.com.zaya.nac.planocontas.repository.sankhya.SankhyaPlanoContasRepository;
import br.com.zaya.nac.planocontas.service.ContaContabilService;
import br.com.zaya.nac.planocontas.service.EmpresaEventoResolver;
import br.com.zaya.nac.planocontas.service.ParceiroValidator;
import br.com.zaya.nac.planocontas.service.SequenciaContaService;

public final class GerarContaContabilAction implements AcaoRotinaJava {

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        SequenciaContaService sequenciaService = new SequenciaContaService();
        ContaContabilService service = new ContaContabilService(
                new SankhyaParceiroRepository(),
                new SankhyaPlanoContasRepository(sequenciaService),
                new ParceiroValidator());
        new GerarContaContabilController(service, new EmpresaEventoResolver())
                .executar(contexto);
    }
}
