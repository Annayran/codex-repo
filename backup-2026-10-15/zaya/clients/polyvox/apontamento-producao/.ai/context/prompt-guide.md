# Guia de Uso com IA

## Como iniciar uma nova conversa

Ao iniciar uma nova conversa, usar:

```txt
Leia os arquivos em `/.ai/context` antes de propor alterações.
Considere estes arquivos como fonte principal de contexto do projeto.
Não altere código sem explicar antes o plano.
Não invente informações não confirmadas.
Quando algo não estiver claro, marque como “A confirmar”.
```

## Ordem Recomendada de Leitura

1. `checkpoint.md` para entender o estado atual.
2. `architecture.md` para visão estrutural e fluxo principal.
3. `stack.md` para ambiente, dependências e comandos.
4. `business-rules.md` para regras já identificadas no código.
5. `known-issues.md` para riscos e pendências.
6. `decisions.md` para histórico de decisões.
7. `patterns.md` para manter consistência ao alterar código.
8. `current-sprint.md` para saber o foco operacional mais recente.

## Regras para Futuras Interações

- Antes de alterar código, verificar `git status` e preservar alterações existentes.
- Não apagar arquivos legados sem pedido explícito.
- Não assumir regras de negócio Sankhya sem evidência no código ou confirmação do usuário.
- Preferir mudanças pequenas e verificáveis.
- Registrar novas decisões técnicas em `decisions.md`.
- Atualizar `checkpoint.md`, `current-sprint.md` e `known-issues.md` após mudanças relevantes.
- Validar build/testes quando o Gradle estiver funcional.

## Prompt Útil para Manutenção

```txt
Leia `/.ai/context/checkpoint.md`, `architecture.md`, `stack.md`, `patterns.md`, `business-rules.md`, `known-issues.md` e `decisions.md`.
Depois analise a tarefa abaixo, proponha um plano curto e só então altere os arquivos necessários.
Mantenha compatibilidade Java 8 e não invente regras de negócio não confirmadas.
```