# Guia de Uso com IA

## Como iniciar uma nova conversa

Use:

```text
Leia os arquivos em `/.ai/context` e `/.ai/sankhya` antes de propor alteracoes.
Considere estes arquivos como fonte principal de contexto do projeto.
Nao altere codigo sem explicar antes o plano.
Nao invente informacoes nao confirmadas.
Quando algo nao estiver claro, marque como "A confirmar".
```

## Ordem recomendada de leitura

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

## Como atualizar contexto

Use:

```text
Atualize os arquivos em `/.ai/context` com o estado atual do projeto.
Remova redundancias.
Preserve apenas informacoes uteis para retomada futura.
Atualize:
- checkpoint.md
- current-sprint.md
- decisions.md, se houver nova decisao
- known-issues.md, se houver novos problemas
```

## Como implementar demanda

Use:

```text
Com base no contexto em `/.ai/context` e `/.ai/sankhya`, implemente a seguinte demanda:

[descrever demanda]

Antes de codificar:
1. Liste os arquivos que serao alterados.
2. Explique o plano tecnico.
3. Aponte riscos.
4. Depois implemente.
5. Atualize `/.ai/context/checkpoint.md` e, se necessario, `decisions.md`.
```

## Como diagnosticar problema

Use:

```text
Com base no contexto em `/.ai/context` e `/.ai/sankhya`, analise o seguinte problema:

[descrever problema]

Investigue:
- causa provavel;
- arquivos envolvidos;
- evidencias no codigo;
- proposta de correcao;
- riscos da alteracao.

Nao altere codigo ate apresentar o diagnostico.
```

## Scripts de apoio

- `.ai/scripts/ctx-init.ps1`
- `.ai/scripts/ctx-update.ps1`
- `.ai/scripts/ctx-dev.ps1`
- `.ai/scripts/ctx-diagnose.ps1`
- `.ai/scripts/ctx-review.ps1`

Os scripts executam prompts com `codex exec` a partir da raiz do projeto, quando o CLI estiver disponivel.
