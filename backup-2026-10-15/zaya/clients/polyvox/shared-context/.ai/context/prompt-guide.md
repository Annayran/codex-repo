# Guia de Uso com IA

## Como iniciar uma nova conversa

Ao iniciar uma nova conversa, usar:

```txt
Leia os arquivos em `/.ai/context` antes de propor alterações.
Considere estes arquivos como fonte principal de contexto do projeto.
Não altere código sem explicar antes o plano.
Não invente informações não confirmadas.
Quando algo não estiver claro, marque como "A confirmar".
```

## Ordem recomendada de leitura

1. `checkpoint.md`
2. `architecture.md`
3. `stack.md`
4. `patterns.md`
5. `business-rules.md`
6. `known-issues.md`
7. `decisions.md`
8. `current-sprint.md`

## Instruções para a IA

- Antes de alterar código, identificar se a mudança afeta metadata Sankhya, actions legadas ou dashboard.
- Não remover classes em `actions/*` sem plano explícito de migração.
- Preferir mudanças pequenas e validáveis.
- Em Java, seguir a organização por feature e preservar compatibilidade com o legado.
- Em dashboard, confirmar a fonte oficial antes de editar.
- Sempre registrar decisões relevantes em `decisions.md`.
- Atualizar `checkpoint.md` e `current-sprint.md` ao final de sessões importantes.

## Prompt curto para retomada

```txt
Você está no projeto Polyvox. Leia `/.ai/context/checkpoint.md`, `architecture.md`, `patterns.md` e `business-rules.md` antes de agir. Trabalhe de forma incremental, preserve compatibilidade com Sankhya e marque incertezas como "A confirmar".
```

## Cuidados específicos deste projeto

- O projeto depende de jars locais do Sankhya.
- O runtime real é o Sankhya, não uma aplicação standalone tradicional.
- Actions e schedulers podem estar vinculados por nome de classe no cadastro do Sankhya.
- O dashboard executa consultas e dispara rotinas, então alterações visuais podem ter impacto operacional.
