package br.com.zaya.nac.planocontas.event;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.zaya.nac.planocontas.controller.GerarContaContabilEventoController;
import br.com.zaya.nac.planocontas.repository.sankhya.SankhyaParceiroRepository;
import br.com.zaya.nac.planocontas.repository.sankhya.SankhyaPlanoContasRepository;
import br.com.zaya.nac.planocontas.service.ContaContabilService;
import br.com.zaya.nac.planocontas.service.EmpresaEventoResolver;
import br.com.zaya.nac.planocontas.service.ParceiroValidator;
import br.com.zaya.nac.planocontas.service.SequenciaContaService;

/** Evento programável para cadastrar automaticamente a conta após incluir o parceiro. */
public final class GerarContaContabilParceiroEvent implements EventoProgramavelJava {

    @Override
    public void afterInsert(PersistenceEvent evento) throws Exception {
        SequenciaContaService sequenciaService = new SequenciaContaService();
        ContaContabilService service = new ContaContabilService(
                new SankhyaParceiroRepository(),
                new SankhyaPlanoContasRepository(sequenciaService),
                new ParceiroValidator());
        new GerarContaContabilEventoController(service, new EmpresaEventoResolver())
                .executar(evento);
    }

    @Override
    public void beforeInsert(PersistenceEvent evento) throws Exception {
        // O parceiro ainda não possui chave persistida; a regra ocorre em afterInsert.
    }

    @Override
    public void beforeUpdate(PersistenceEvent evento) throws Exception {
        // Fora do escopo: a automação atua somente na inclusão do parceiro.
    }

    @Override
    public void beforeDelete(PersistenceEvent evento) throws Exception {
        // Fora do escopo: a rotina não remove contas contábeis.
    }

    @Override
    public void afterUpdate(PersistenceEvent evento) throws Exception {
        // Intencionalmente vazio para impedir reprocessamento recursivo da TGFPAR.
    }

    @Override
    public void afterDelete(PersistenceEvent evento) throws Exception {
        // Fora do escopo: a rotina não remove contas contábeis.
    }

    @Override
    public void beforeCommit(TransactionContext contexto) throws Exception {
        // O commit ou rollback permanece integralmente sob controle do Sankhya/Jape.
    }
}
