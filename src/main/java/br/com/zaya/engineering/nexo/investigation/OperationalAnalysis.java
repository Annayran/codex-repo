package br.com.zaya.engineering.nexo.investigation;

import br.com.zaya.engineering.nexo.diagnosis.Contracts.Snapshot;
import java.util.ArrayList;
import java.util.List;

/** Human procedure from reviewed official documentation; never an executable ERP action. */
public final class OperationalAnalysis {
    private OperationalAnalysis() {}
    public record Report(String summary, List<String> observations, String likelyExplanation,
                         List<String> manualResolution, List<String> validation,
                         List<String> limits, List<String> sources) {}
    public static Report analyze(Snapshot snapshot) {
        var inactive = snapshot.items().stream().filter(i -> Boolean.FALSE.equals(i.productActive())).toList();
        var observations = new ArrayList<String>();
        var steps = new ArrayList<String>();
        var limits = new ArrayList<String>();
        limits.add("A coleta mostra o estado reportado naquele momento; não demonstra quando ou por que o cadastro foi alterado.");
        limits.add("O trecho do log, quando encontrado, tem correspondência textual sem vínculo comprovado com a mesma requisição.");
        boolean unitsDiffer = snapshot.items().stream().anyMatch(i -> i.unit()!=null && i.stockUnit()!=null && !i.unit().equals(i.stockUnit()));
        if (unitsDiffer) limits.add("Há diferenças de unidade. Validar a conversão antes de comparar quantidades; isso não comprova estoque insuficiente.");
        if (inactive.isEmpty()) return new Report("Investigação da nota " + snapshot.nunota(),
                List.of("Nenhum produto foi reportado explicitamente inativo na coleta disponível."),
                "A evidência atual não permite aplicar o procedimento de produto inativo.",
                List.of("Consultar os itens e as divergências do relatório; obter a regra aplicável antes de definir uma correção."),
                List.of("Recolher evidências após a verificação e comparar os resultados."), List.copyOf(limits), List.of());
        for (var item : inactive.stream().limit(5).toList())
            observations.add("Produto " + item.product() + " reportado inativo, na empresa " + item.company() + ".");
        String products = String.join(", ", inactive.stream().limit(5).map(i -> i.product()).toList());
        steps.add("Abra a tela Produtos e pesquise os códigos: " + products + ". Use registros existentes; não crie novos produtos.");
        steps.add("A documentação mostra Configurações > Cadastros > Produtos e, em outro layout, Comercial > Configuração > Cadastros > Produtos. Se o menu diferir, procure a tela pelo nome.");
        steps.add("Confira o campo Ativo. As referências o situam na aba Propriedades ou Geral, conforme o layout; essa posição ainda precisa ser confirmada na instalação.");
        steps.add("Confirme com o responsável pelo cadastro se a inativação foi intencional. Se o produto deve permanecer inativo, interrompa a tentativa e revise o item/documento com o responsável.");
        steps.add("Se a reativação for autorizada, um usuário com permissão deve editar o cadastro, marcar Ativo e salvar pela interface. Esta orientação não autoriza nem executa alteração automática.");
        if(inactive.size()>5) limits.add("A orientação mostra os primeiros cinco produtos inativos; consulte o contexto para os demais.");
        return new Report("Produto inativo encontrado na nota " + snapshot.nunota(), List.copyOf(observations),
                "O estado inativo é compatível com um bloqueio que informe produto não ativo. A condição observada não comprova a origem histórica nem a regra específica do gatilho.",
                List.copyOf(steps),
                List.of("Após uma correção autorizada, confira o campo salvo e repita a operação em homologação. Envie nova coleta para verificar o estado atualizado.",
                        "Se o erro persistir com o produto ativo, registre a mensagem atual e examine a regra do gatilho e eventuais restrições; não altere o gatilho para contornar o bloqueio."),
                List.copyOf(limits), List.of(
                "https://ajuda.sankhya.com.br/hc/pt-br/articles/360043672613-Produto-n%C3%A3o-existe-ou-est%C3%A1-inativo-ou-n%C3%A3o-pode-ser-usado-aqui",
                "https://ajuda.sankhya.com.br/hc/pt-br/articles/37884846550551-Como-cadastrar-Produtos"));
    }
}
