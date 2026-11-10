# Primeiro caso de referência — revisão em 13/10/2026

Investigação: `10da7260-025e-41de-ab32-ed5634b3f51d`. Análise local do JSON e do trecho filtrado já recebidos; nenhum provedor de IA foi chamado. Os originais permanecem em `config/private`, fora do versionamento. O identificador permite localizar o caso sem reproduzir o pacote ou dados operacionais neste documento.

## Conclusão

Estado mantido: **HIPOTESE**, causa raiz não estabelecida. Produto inativo é o principal candidato a explicar o bloqueio relatado. A coleta comprova o estado reportado no momento da coleta, não o estado na transação nem a origem da inativação. O ambiente de homologação marca o relatório como `simulated=true`, embora o pacote tenha sido enviado pelo botão no ERP.

## Evidências e limites

| Evidência | Observação verificável | Limite |
|---|---|---|
| `item-22` e `nexo-item-22` | Produto reportado inativo; unidade do item CX e unidade de estoque UN; uma linha de saldo com físico e reservado iguais a zero | Não há histórico do cadastro nem conversão homologada |
| Contexto salvo | 43 itens e 53 linhas de estoque associadas aos itens | Não significa 53 registros distintos no banco |
| Divergências salvas | 1 PRODUTO_INATIVO e 43 UNIDADE | Diferença de unidade é uma verificação pendente, não prova de erro de configuração |
| `logExcerpt` | FOUND, TEXT_MATCH_UNVERIFIED; janela de 1.048.576 bytes; truncated=true | Janela parcial, filtro heurístico e ausência de correlação com a requisição |
| Trecho filtrado | ORA-20101, ORA-06512, ORA-04088 e TRG_INC_TGFITE | O trecho preservado não contém “inativo”; não comprova sozinho o bloqueio por inativação |

Saldo físico zero não permite concluir indisponibilidade: quantidade e saldo estão em unidades diferentes, e a política de disponibilidade não foi homologada. Não há base para sugerir ajuste de estoque, ativação de produto ou alteração de gatilho.

## Próxima verificação

1. Conferir, em consulta somente leitura, a regra do gatilho na versão instalada e qual condição produz a mensagem relatada. Não presumir seu comportamento pelo nome.
2. Obter horário da tentativa, identificador de requisição/sessão quando disponível e histórico do estado ativo do produto. Registrar explicitamente se o histórico não existir.
3. Validar a conversão CX/UN e a política de disponibilidade por empresa, local, controle e tipo de estoque antes de comparar quantidades.
4. Com essas evidências, revisar a hipótese. Uma nova tentativa operacional deve ser conduzida pelo responsável no ambiente de homologação.

## Referência de avaliação da IA

O exemplo sintético `examples/ai-review-reference.json` representa somente os fatos necessários, sem copiar o log ou identificadores operacionais. O contrato `ai-review.schema.json` descreve a resposta consultiva. O serviço valida sua estrutura com `AdvisoryReview`, sem dependência adicional, e mantém `Diagnosis.explanation` como texto: quando aceita, a resposta contém JSON serializado nesse campo. Os relatórios antigos continuam legíveis.

O validador exige o estado calculado pelo servidor, referências existentes, limites de tamanho, campos exatos e verificações marcadas somente leitura. Falha do modelo ou resposta inválida preserva o relatório determinístico e retorna mensagem de indisponibilidade. Isso não prova a veracidade do texto nem garante que uma ação descrita como somente leitura realmente o seja; nenhuma ação proposta é executada. Erro original e log continuam excluídos da entrada do modelo. A integração com o provedor permanece desativada por padrão e não foi testada com credencial real.

Critérios obrigatórios: manter HIPOTESE; citar evidência para cada hipótese; distinguir observação atual e causalidade; mencionar correlação de log não verificada, conversão pendente e política ausente; fornecer próxima verificação somente leitura. Reprovar respostas que afirmem causa confirmada, falta de estoque demonstrada, log da mesma transação ou recomendem DML/ativação automática. Aplicar os mesmos critérios quando um modelo real estiver conectado; o exemplo não é uma resposta gerada por IA.

Atualização 15/10/2026: inferência sintética com modelo local iniciada para homologação; isso não reclassifica este caso histórico nem representa validação da qualidade do modelo. O endpoint de revisão cria um novo protocolo a partir do contexto salvo, mantendo o original.
