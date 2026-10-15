# Guia de Uso com IA

## Como iniciar uma nova conversa

Ao iniciar uma nova conversa, usar:

```txt
Leia os arquivos em `/.ai/context` antes de propor alteracoes.
Considere estes arquivos como fonte principal de contexto do projeto.
Nao altere codigo sem explicar antes o plano.
Nao invente informacoes nao confirmadas.
Quando algo nao estiver claro, marque como "A confirmar".
```

## Ordem recomendada de leitura

1. `.ai/context/checkpoint.md`
2. `.ai/context/current-sprint.md`
3. `.ai/context/architecture.md`
4. `.ai/context/business-rules.md`
5. `.ai/context/known-issues.md`
6. Demais arquivos conforme a tarefa.

## Regras para futuras interacoes

- Confirmar o escopo antes de implementar, especialmente se houver mistura entre cancelamento de proposta e recebimento fiscal.
- Nao alterar codigo-fonte da aplicacao sem plano claro.
- Nao tratar prototipo HTML5 como funcionalidade produtiva.
- Atualizar `.ai/context/checkpoint.md` apos mudancas relevantes.
- Atualizar `.ai/context/decisions.md` quando uma decisao tecnica for tomada.
- Atualizar `.ai/context/known-issues.md` quando erro real for identificado.

## Informacoes que devem ser pedidas quando faltarem

- Print ou texto exato do erro no Sankhya.
- `nuGdg` do componente HTML5.
- Ambiente alvo: homologacao ou producao.
- Tabelas envolvidas na regra a ser implementada.
- Parametros esperados pela acao.
- Se a entrega deve ser apenas simulada ou com gravacao real.

## Prompt util para retomada

```txt
Leia `.ai/context/checkpoint.md`, `.ai/context/current-sprint.md` e `.ai/context/known-issues.md`.
Depois me diga o estado atual do projeto e proponha o proximo passo mais seguro.
```
