package br.com.zaya.engineering.nexobutton;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import java.math.BigDecimal;
import java.net.URI;
import java.util.regex.*;
import java.util.Iterator;
import java.util.ServiceLoader;

/** Compile against the extensions JAR exported by the target ERP. No ERP writes or background threads. */
public final class SendIncidentAction implements AcaoRotinaJava {
    public void doAction(ContextoAcao context) throws Exception {
        Registro[] selected = context.getLinhas();
        if (selected == null || selected.length != 1) throw new IllegalArgumentException("Selecione uma nota para investigar.");
        Object note = selected[0].getCampo("NUNOTA");
        if (note == null) throw new IllegalArgumentException("A tela selecionada não disponibiliza NUNOTA.");
        long nunota = new BigDecimal(note.toString()).longValueExact();
        Object report = context.getParam("MENSAGEM_ERRO");
        if (report == null) throw new IllegalArgumentException("Informe a mensagem exata do erro.");
        ButtonConfigurationProvider.Configuration configuration = configuration(context);
        String logExcerpt=IncidentLogCollector.collect(nunota, report.toString());
        String packet = NoteStockCollector.collect(nunota, report.toString(), configuration.erpVersion, LogSignals.collect(),logExcerpt);
        String response = new NexoButtonClient(URI.create(configuration.endpoint), configuration.principal, configuration.password, 30000).send(packet);
        // Display only an opaque investigation ID, never untrusted HTML or the response body in the ERP UI.
        Matcher id = Pattern.compile("\"id\"\\s*:\\s*\"([a-f0-9-]{36})\"").matcher(response);
        if (!id.find()) throw new IllegalStateException("O serviço retornou uma resposta sem identificador de investigação.");
        boolean simulated = Pattern.compile("\"simulated\"\\s*:\\s*true").matcher(response).find();
        String logStatus=logExcerpt.contains("\"status\":\"FOUND\"") ? "trecho filtrado encontrado (correspondência textual)"
                : logExcerpt.contains("\"status\":\"NOT_FOUND\"") ? "erro não localizado na janela consultada" : "log indisponível";
        context.setMensagemRetorno((simulated ? "Homologação: " : "") + "incidente enviado. Investigação: " + id.group(1)
                + ". Coleta: itens, produto ativo e saldos de estoque; log: "+logStatus
                + ". Disponibilidade depende de regra homologada.");
    }
    private static ButtonConfigurationProvider.Configuration configuration(ContextoAcao context) throws Exception {
        Iterator<ButtonConfigurationProvider> providers = ServiceLoader.load(ButtonConfigurationProvider.class,
                SendIncidentAction.class.getClassLoader()).iterator();
        if (providers.hasNext()) {
            ButtonConfigurationProvider provider = providers.next();
            if (providers.hasNext()) throw new IllegalStateException("Mais de um provedor de configuração cadastrado.");
            ButtonConfigurationProvider.Configuration configuration = provider.load(context);
            if (configuration == null) throw new IllegalStateException("Provedor sem configuração administrativa.");
            return configuration;
        }
        throw new IllegalStateException("Instalação Nexo ainda não provisionada pela Zaya: "
                + "receptor HTTPS e identidade protegida pendentes. "
                + "Não é necessário criar parâmetros NEXO no Sankhya. Contate a equipe de implantação.");
    }
}
