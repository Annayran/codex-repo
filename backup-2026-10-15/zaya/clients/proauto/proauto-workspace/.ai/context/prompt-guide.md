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

## Ordem recomendada de leitura

1. `checkpoint.md`
2. `stack.md`
3. `architecture.md`
4. `patterns.md`
5. `business-rules.md`
6. `decisions.md`
7. `current-sprint.md`
8. `known-issues.md`

## Regras para futuras interações

- Começar verificando o estado atual do repositório com `git status`.
- Ler os arquivos de contexto antes de sugerir mudanças.
- Confirmar a stack no código antes de propor implementação.
- Não tratar informações marcadas como “A confirmar” como fatos.
- Registrar decisões técnicas novas em `decisions.md`.
- Atualizar `checkpoint.md` ao final de mudanças relevantes.
- Atualizar `current-sprint.md` quando houver mudança de prioridade, bloqueio ou próxima ação.
- Preservar conteúdo útil existente ao reorganizar qualquer arquivo em `/.ai/context`.

## Como pedir alterações de código

Use um pedido objetivo, por exemplo:

```txt
Leia `/.ai/context` e analise o código antes de alterar.
Explique o plano rapidamente.
Implemente a mudança X.
Não altere arquivos fora do escopo.
Atualize o contexto se descobrir decisões ou regras relevantes.
```

## Como pedir revisão

Use:

```txt
Leia `/.ai/context` e faça uma revisão do estado atual.
Priorize bugs, riscos, regressões e lacunas de teste.
Não modifique arquivos sem aprovação.
```

## Como encerrar uma sessão relevante

Antes de encerrar uma sessão com mudanças importantes, peça:

```txt
Atualize `/.ai/context/checkpoint.md`, `current-sprint.md`, `known-issues.md` e `decisions.md` se houver novas informações confirmadas.
```
