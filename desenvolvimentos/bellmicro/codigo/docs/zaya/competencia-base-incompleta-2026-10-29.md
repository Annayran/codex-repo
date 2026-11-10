# Competência e diagnóstico da base — 29/10/2026

Demanda: ZY-BLM-2026-RATEIO-001. Entrega local: Java e dashboard 7.6.

## Evidência e defeito confirmado

A tela apresentada contém vencimento da parcela em 02/02/2026, mas competência vazia e BASE_INCOMPLETA. O mapeamento descartava a competência quando a apuração do total falhava. O print não permite determinar qual condição impediu a base no ambiente do cliente; não houve consulta ao banco real.

## Correção

A referência selecionada é consultada separadamente e conferida contra a origem canônica. O vencimento preserva fevereiro de 2026 mesmo quando a base permanece pendente. Mudança de documento ou divergência do vencimento durante a leitura impede uma base inconsistente.

O retorno inclui motivo específico, com mensagens controladas para tipo diferente de despesa, provisão, renegociação, ausência de parcelas elegíveis, valor inválido ou referência divergente. Não expõe SQL ou erros internos. Uma fotografia antiga com pendência genérica não apaga o diagnóstico atual no dashboard.

Permanecem as regras aprovadas: total nominal do documento por NUNOTA, avulso por NUFIN, competência pelo DTVENC selecionado, exclusão de provisões e auxiliares e tratamento conservador de renegociações. O valor da parcela selecionada não substitui artificialmente o total. A autorização temporária já aprovada permanece; efetivação financeira continua indisponível.

## Validação e entrega

- Build Java com SDK local: 129 cenários aprovados, incluindo 37 JDBC.
- Dashboard no navegador: regressões de competência com base pendente e fotografia antiga aprovadas; layout preservado.
- JSP final compilado com alvo Java 8: zero erros.
- Revisão independente estática: sem achados concretos.
- JAR conferido: classes Java 8, nova consulta de referência e JSP embutido igual à fonte final.

Arquivos: `build/libs/ZAYA-BLM-V7.6.jar` e `build/315_html5Component.zip`. Atualizar ambos no ambiente; instalação não executada. IDs de ações e componente 315 preservados.

Próxima ação: validar a nova mensagem da base no ambiente e investigar a condição concreta do título. Não há confirmação de que a base desse documento esteja elegível.
