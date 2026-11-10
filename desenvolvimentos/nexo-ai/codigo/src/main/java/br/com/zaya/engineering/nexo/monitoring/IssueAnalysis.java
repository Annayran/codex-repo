package br.com.zaya.engineering.nexo.monitoring;

import br.com.zaya.engineering.nexo.monitoring.MonitorContracts.Issue;
import java.util.*;

/** Evidence-limited playbooks. A reported category never proves root cause. */
public final class IssueAnalysis {
    private IssueAnalysis() {}
    public record Step(String action, String purpose) {}
    public record Report(String state, String title, String summary, List<String> evidence,
                         List<String> hypotheses, List<String> missingEvidence, List<Step> nextChecks,
                         String proposedSolution, String validationPlan, String aiStatus, Object advisory) {}
    public static Report analyze(Issue issue) {
        boolean manual="skala.relato.manual".equals(issue.component());
        String category=issue.category();
        String log=Objects.toString(issue.lastLogExcerpt(), "").toLowerCase(Locale.ROOT);
        if ("UNCLASSIFIED".equals(category)||category.startsWith("ORA_")) {
            String normalized=java.text.Normalizer.normalize(log,java.text.Normalizer.Form.NFD).replaceAll("\\p{M}","");
            if(normalized.contains("produt")&&normalized.contains("sem familia")) category="PRODUCT_FAMILY_MISSING";
            else if(log.contains("quantidade entregue")) category="DELIVERED_QTY";
            else if(log.contains("inativo")||log.contains("nao esta ativo")||log.contains("não está ativo")) category="PRODUCT_INACTIVE";
            else if(log.contains("combina")&&log.contains("global")) category="NO_GLOBAL_COMBINATION";
            else if(log.contains("estoque insuficiente")) category="INSUFFICIENT_STOCK";
        }
        String title,summary,solution; List<String> hypotheses; List<Step> checks;
        switch(category) {
            case "PRODUCT_FAMILY_MISSING" -> {
                title="Produtos sem família bloqueiam o particionamento";
                summary="A rotina reporta produtos sem família. O cadastro exigido pelo algoritmo precisa ser conferido antes de repetir o particionamento.";
                hypotheses=List.of("Os produtos listados podem estar sem a classificação de família exigida pela personalização.","A família pode existir no cadastro, mas não ser reconhecida pela consulta ou vínculo usado na versão implantada.");
                checks=List.of(new Step("Consultar os produtos listados na tela Produtos e a configuração de família usada pelo particionamento.","Conferir cadastro e associação. O nome do campo ou tela personalizada precisa ser validado no cliente."),new Step("Inspecionar a validação de família em ParticionarPedidosAction na versão implantada.","Identificar exatamente qual campo, tabela ou vínculo a rotina exige; não presumir que grupo de produto equivale a família."));
                solution="Se a associação realmente estiver ausente, o responsável funcional deve definir a família correta e aplicar o procedimento aprovado de cadastro. Se a associação existir, reproduzir a consulta da rotina e revisar o código. Não inventar família nem alterar cadastros em lote para contornar o bloqueio.";
            }
            case "DELIVERED_QTY" -> {
                title="Bloqueio de quantidade entregue";
                summary="O sinal reportado indica uma validação entre quantidade entregue e negociada. A causa do desequilíbrio ainda precisa ser demonstrada.";
                hypotheses=List.of("O desfazimento pode estar tentando alterar um item que possui entrega ou vínculo posterior.","A regra personalizada pode estar calculando ou validando quantidades em uma ordem incompatível com o cenário.");
                checks=List.of(new Step("Consultar os itens da nota e seus vínculos de origem e destino.","Comparar quantidade negociada e entregue antes da operação."),new Step("Localizar o gatilho e a linha indicados no log na versão implantada.","Conferir a condição de bloqueio e a sequência do desfazimento, sem alterar dados."));
                solution="Se os vínculos representarem entregas reais, validar o procedimento funcional de reversão com o responsável. Se a regra estiver incorreta, preparar correção no código da versão implantada, com teste e rollback. Não ajustar quantidades diretamente no banco.";
            }
            case "NO_GLOBAL_COMBINATION" -> {
                title="Particionamento sem combinação viável";
                summary="A mensagem aponta que o algoritmo não encontrou combinação sob as restrições usadas. Isso pode ser uma condição de negócio legítima ou uma falha do algoritmo.";
                hypotheses=List.of("Capacidades, quantidades ou restrições podem tornar a combinação inviável.","O algoritmo pode estar descartando uma combinação válida; isso exige reprodução com a entrada exata.");
                checks=List.of(new Step("Registrar nota, itens, quantidades, capacidades e restrições efetivamente usadas.","Reproduzir o mesmo cenário sem inventar parâmetros."),new Step("Comparar a entrada com a implementação da versão instalada.","Distinguir inviabilidade real de defeito na busca de combinações."));
                solution="Validar os parâmetros com a área de negócio. Somente propor alteração de algoritmo quando uma combinação válida puder ser demonstrada em teste. O log isolado não autoriza relaxar restrições.";
            }
            case "NO_ACTIVE_PARTITION" -> {
                title="Particionamento ativo não localizado";
                summary="O sinal sugere ausência de particionamento ativo para a operação solicitada; não comprova indisponibilidade do ERP.";
                hypotheses=List.of("A nota selecionada pode não ter particionamento ativo ou pode já ter sido desfeita.");
                checks=List.of(new Step("Conferir a nota selecionada e o histórico do particionamento.","Verificar se a operação é aplicável ao estado atual."),new Step("Inspecionar a condição de seleção na rotina implantada.","Confirmar que o vínculo esperado está sendo consultado."));
                solution="Usar o procedimento funcional correspondente ao estado confirmado da nota. Se houver vínculo ativo e a rotina não o reconhecer, reproduzir e corrigir a seleção em homologação.";
            }
            case "PRODUCT_INACTIVE" -> {
                title="Produto reportado como inativo";
                summary="O erro menciona inativação de produto. Nesta OS de log não foi coletado o cadastro para confirmar sua situação.";
                hypotheses=List.of("Um produto envolvido pode estar inativo no momento da operação.");
                checks=List.of(new Step("Consultar o produto citado na tela Produtos e conferir o campo Ativo.","Confirmar o cadastro e se a inativação é intencional; os nomes devem ser conferidos na instalação."),new Step("Conferir o produto nos itens da nota e a regra personalizada.","Verificar se o bloqueio corresponde ao item efetivamente processado."));
                solution="Se a inativação for indevida, o responsável autorizado pode corrigir o cadastro pelo procedimento aprovado. Se for intencional, avaliar substituição ou tratamento funcional. Não reativar automaticamente.";
            }
            case "INSUFFICIENT_STOCK" -> {
                title="Estoque reportado como insuficiente";
                summary="O texto informa insuficiência de estoque, mas não demonstra a disponibilidade segundo a regra do cliente.";
                hypotheses=List.of("Saldo, reserva, local, controle ou unidade podem não atender à quantidade exigida.");
                checks=List.of(new Step("Coletar itens, unidade, empresa, local, controle, saldo físico e reserva.","Comparar as mesmas dimensões da operação."),new Step("Conferir a regra de disponibilidade da instalação.","Evitar tratar saldo físico como disponibilidade validada."));
                solution="Aplicar o procedimento funcional de estoque apenas após confirmar dimensões e disponibilidade. Divergência na regra deve ser reproduzida em homologação antes de alterar código.";
            }
            default -> {
                title="Falha em investigação";summary="Existe uma ocorrência registrada, mas faltam evidências suficientes para explicar a causa.";
                hypotheses=List.of();checks=List.of(new Step("Reproduzir o erro e registrar operação, nota e horário.","Relacionar a ocorrência ao cenário do usuário."),new Step("Coletar stack e localizar a versão implantada da personalização.","Identificar o ponto de falha antes de propor correção."));
                solution="Não há correção sustentada pelas evidências atuais. Reunir o contexto indicado e revisar a análise.";
            }
        }
        var evidence=new ArrayList<String>();
        evidence.add("Componente reportado: "+issue.component());evidence.add("Categoria reportada: "+issue.category());
        evidence.add("Ocorrências ao vivo: "+(issue.occurrences()-issue.historicalOccurrences())+"; históricas: "+issue.historicalOccurrences());
        if(!log.isBlank()) evidence.add(manual?"Relato manual fornecido pelo operador, filtrado. Não comprova captura automática nem conteúdo do arquivo de log.":"Trecho filtrado de log disponível; correspondência textual sem correlação transacional comprovada.");
        if("DELIVERED_QTY".equals(category)) {
            var values=java.util.regex.Pattern.compile("(?i)Qtd\\s+Entregue\\s*=\\s*([0-9]{1,8}(?:[.,][0-9]{1,4})?)\\s+Qtd\\s+Neg\\s*=\\s*([0-9]{1,8}(?:[.,][0-9]{1,4})?)").matcher(log);
            if(values.find()) evidence.add("Valores reportados no texto: entregue="+values.group(1)+"; negociada="+values.group(2)+". Não substituem consulta dos itens e vínculos.");
            if(log.contains("trg_upt_tgfite")) evidence.add("O texto cita TRG_UPT_TGFITE; a condição e a linha precisam ser verificadas na versão do banco.");
            if(log.contains("trg_inc_tgfvar")) evidence.add("O texto cita TRG_INC_TGFVAR na cadeia de execução; isso não comprova qual alteração originou o desequilíbrio.");
        }
        var missing=new ArrayList<String>();missing.add("Nota, operação e correlação da execução não confirmadas por este evento de log.");
        missing.add("Dados dos itens e estado anterior à operação não estão anexados a esta OS.");
        missing.add("Regra funcional e código da personalização precisam ser confrontados com a versão implantada.");
        if(log.isBlank()) missing.add("Trecho de log não disponível nesta ocorrência.");
        String state=hypotheses.isEmpty()?"INCONCLUSIVA":"HIPOTESE";
        return new Report(state,title,summary,List.copyOf(evidence),hypotheses,List.copyOf(missing),checks,solution,
                "Reproduzir o cenário original em homologação; registrar entrada, resultado esperado e resultado obtido. Depois da intervenção autorizada, repetir a operação e conferir itens, vínculos e ausência de efeitos indevidos. Anexar a evidência à OS.","NAO_SOLICITADA",null);
    }
}
