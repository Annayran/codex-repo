package br.com.zaya.mahospitalar.genesis.schedule;

import br.com.zaya.mahospitalar.genesis.client.GenesisHttpClient;
import br.com.zaya.mahospitalar.genesis.client.GenesisResponse;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.MovimentoEstoque;
import br.com.zaya.mahospitalar.genesis.dto.EstoqueGenesisDTO;
import br.com.zaya.mahospitalar.genesis.repository.MovimentoEstoqueRepository;
import br.com.zaya.mahospitalar.genesis.service.PayloadBuilder;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

import java.util.List;

public class IntegracaoGenesisScheduleAction implements ScheduledAction {
    private final MovimentoEstoqueRepository repository;
    private final PayloadBuilder payloadBuilder;

    public IntegracaoGenesisScheduleAction() {
        this(new MovimentoEstoqueRepository(), new PayloadBuilder());
    }

    public IntegracaoGenesisScheduleAction(MovimentoEstoqueRepository repository, PayloadBuilder payloadBuilder) {
        this.repository = repository;
        this.payloadBuilder = payloadBuilder;
    }

    public void onTime(ScheduledActionContext context) {
        log(context, "Inicio da integracao MA Hospitalar x Genesis.");
        try {
            int processados = runOnce(context);
            info(context, "Integracao Genesis finalizada. Registros processados: " + processados + ".");
        } catch (Exception e) {
            info(context, "Integracao Genesis finalizada com erro: " + e.getMessage());
            log(context, "Erro na integracao Genesis: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int runOnce() {
        return runOnce(null);
    }

    public int runOnce(ScheduledActionContext context) {
        ParametrosGenesis parametros = repository.lerParametros();
        GenesisHttpClient client = new GenesisHttpClient(parametros);
        List<MovimentoEstoque> pendentes = repository.buscarPendentes(parametros.getTopsElegiveis());
        int processados = 0;

        log(context, "Registros pendentes encontrados para Genesis: " + pendentes.size() + ".");

        for (MovimentoEstoque movimento : pendentes) {
            List<String> faltantes = payloadBuilder.validar(movimento);
            if (!faltantes.isEmpty()) {
                repository.marcarErroFuncional(movimento, "Campos obrigatorios nao preenchidos: " + faltantes);
                log(context, "Movimento rejeitado por campos obrigatorios. Chave: "
                        + movimento.getChaveMovimento() + " Campos: " + faltantes + ".");
                processados++;
                continue;
            }

            EstoqueGenesisDTO dto = payloadBuilder.montar(movimento);
            repository.registrarPendente(movimento, dto);
            GenesisResponse response = client.enviarEstoque(dto);

            if (response.isSucesso()) {
                repository.marcarEnviado(movimento, response);
                log(context, "Movimento enviado para Genesis. Chave: "
                        + movimento.getChaveMovimento() + " HTTP: " + response.getHttpStatus() + ".");
            } else if (response.isTransitorio()) {
                repository.incrementarTentativa(movimento, response);
                log(context, "Movimento marcado para reprocessamento. Chave: "
                        + movimento.getChaveMovimento() + " Mensagem: " + response.getMensagem() + ".");
            } else {
                repository.marcarErroFuncional(movimento, response);
                log(context, "Movimento com erro funcional. Chave: "
                        + movimento.getChaveMovimento() + " HTTP: " + response.getHttpStatus()
                        + " Mensagem: " + response.getMensagem() + ".");
            }
            processados++;
        }
        return processados;
    }

    private void info(ScheduledActionContext context, String message) {
        if (context != null) {
            context.info(message);
        }
    }

    private void log(ScheduledActionContext context, String message) {
        if (context != null) {
            context.log(message);
        }
    }
}