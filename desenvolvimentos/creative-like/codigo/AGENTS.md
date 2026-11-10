# Instrucoes Obrigatorias para Agentes IA

## Idioma

- Responder sempre em portugues do Brasil.

## Leitura obrigatoria antes de alterar qualquer arquivo

Antes de propor ou executar alteracoes, leia:

1. `/.ai/context/checkpoint.md`
2. `/.ai/context/architecture.md`
3. `/.ai/context/stack.md`
4. `/.ai/context/patterns.md`
5. `/.ai/context/business-rules.md`
6. `/.ai/context/current-sprint.md`
7. `/.ai/context/known-issues.md`
8. `/.ai/context/decisions.md`
9. `/.ai/sankhya/sankhya-core.md`
10. `/.ai/sankhya/nota-fiscal-playbook.md`
11. `/.ai/sankhya/tgfvar-guide.md`
12. `/.ai/sankhya/transaction-guidelines.md`
13. `/.ai/sankhya/data-access-patterns.md`
14. `/.ai/sankhya/top-and-business-flow.md`
15. `/.ai/sankhya/checklist-before-code.md`
16. `/.ai/sankhya/anti-patterns.md`

## Regras de trabalho

- Nao alterar codigo antes de explicar o plano tecnico.
- Sempre listar arquivos que serao alterados.
- Nao inventar tabela, campo, rotina, endpoint, classe, servico ou regra de negocio.
- Quando algo nao estiver claro, marcar como **A confirmar**.
- Preservar comportamento existente.
- Nao apagar arquivos existentes.
- Nao criar dependencias novas sem justificativa.
- Fazer a menor alteracao segura.
- Antes de alterar uma regra Sankhya, analisar impacto em documento, estoque, financeiro, TOP, confirmacao, origem/destino e integracoes.
- Sempre sugerir testes manuais e tecnicos.
- Ao final de alteracao relevante, atualizar `/.ai/context/checkpoint.md`.
- Se houver nova decisao tecnica, atualizar `/.ai/context/decisions.md`.
- Se houver novo problema identificado, atualizar `/.ai/context/known-issues.md`.

## Regras Sankhya obrigatorias

- Nao tratar customizacao Sankhya como CRUD Java/SQL comum.
- Nao inserir ou alterar documento em `TGFCAB`/`TGFITE` sem validar fluxo nativo, TOP, origem/destino e efeitos colaterais.
- Nao ignorar `TGFVAR` quando houver relacao entre documento de origem e destino.
- Nao simular confirmacao/liberacao apenas alterando status/campo.
- Nao abrir transacao concorrente em Action que ja roda sob transacao do Sankhya.
- Nao usar `contexto.confirmarSimNao(...)` em Action chamada por JSP/HTML5 via `ActionButtonsSP.executeJava`.

